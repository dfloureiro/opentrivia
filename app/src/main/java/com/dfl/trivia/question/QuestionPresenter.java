package com.dfl.trivia.question;

import android.util.Log;
import com.dfl.trivia.Base64Decoder;
import com.dfl.trivia.TriviaSharedPreferences;
import com.dfl.trivia.network.RequestFactory;
import com.dfl.trivia.question.model.Question;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

public class QuestionPresenter implements QuestionContract.Presenter {

  private final static String TAG = QuestionPresenter.class.getCanonicalName();
  private final static String MULTIPLE_GAME_TYPE = "multiple";

  private final QuestionContract.View view;
  private final RequestFactory requestFactory;
  private final TriviaSharedPreferences triviaSharedPreferences;
  private final int amount;
  private final String categoryId;
  private final String difficulty;
  private final String questionType;
  private final CompositeDisposable compositeDisposable;

  private final String sessionToken;
  private List<Question> questionsList;
  private int questionPosition;
  private int numberOfCorrectAnswers;

  QuestionPresenter(QuestionContract.View view, RequestFactory requestFactory,
      TriviaSharedPreferences triviaSharedPreferences, int amount, String categoryId,
      String difficulty, String questionType) {
    this.view = view;
    this.requestFactory = requestFactory;
    this.triviaSharedPreferences = triviaSharedPreferences;
    this.amount = amount;
    this.categoryId = categoryId;
    this.difficulty = difficulty;
    this.questionType = questionType;

    sessionToken = triviaSharedPreferences.getSessionToken();
    questionsList = new ArrayList<>();
    questionPosition = 0;
    numberOfCorrectAnswers = 0;

    compositeDisposable = new CompositeDisposable();
    view.setPresenter(this);
  }

  @Override public void subscribe(QuestionContract.State state) {
    if (state != null) {
      if (!state.getListOfQuestionResults()
          .isEmpty()) {
        questionsList = state.getListOfQuestionResults();
        questionPosition = state.getCurrentQuestionPosition();
        numberOfCorrectAnswers = state.getNumberOfCorrectAnswers();
        loadQuestion();
        view.finishLoading();
        return;
      }
    }
    getQuestionList();
  }

  @Override public void unsubscribe() {
    compositeDisposable.clear();
  }

  @Override public QuestionContract.State getState() {
    return new QuestionState(questionsList, questionPosition, numberOfCorrectAnswers);
  }

  private void getQuestionList() {
    compositeDisposable.add(
        requestFactory.getQuestionsRequest(sessionToken, amount, categoryId, difficulty,
            questionType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(questionsResponse -> {
              if (questionsResponse.getResponseCode() == 0
                  | questionsResponse.getResponseCode() == 1) {
                return Flowable.just(questionsResponse);
              } else if (questionsResponse.getResponseCode() == 3) {
                //session token does not exist
                return requestFactory.getSessionTokenRequest()
                    .flatMap(getSessionTokenResponse -> {
                      triviaSharedPreferences.saveSessionToken(getSessionTokenResponse.getToken());
                      return requestFactory.getQuestionsRequest(getSessionTokenResponse.getToken(),
                          amount, categoryId, difficulty, questionType);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
              } else if (questionsResponse.getResponseCode() == 4) {
                //reset session token
                return requestFactory.resetSessionTokenRequest(sessionToken)
                    .flatMap(resetSessionTokenResponse -> {
                      triviaSharedPreferences.saveSessionToken(
                          resetSessionTokenResponse.getToken());
                      return requestFactory.getQuestionsRequest(
                          resetSessionTokenResponse.getToken(), amount, categoryId, difficulty,
                          questionType);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
              } else {
                return Flowable.error(new IllegalArgumentException("error "
                    + questionsResponse.getResponseCode()
                    + ": Illegal parameter or unknown error occurred"));
              }
            })
            .map(questionsResponse -> Base64Decoder.decodeResults(questionsResponse.getResults()))
            .subscribe(questionList -> {
              if (!questionList.isEmpty()) {
                questionsList = questionList;
                loadQuestion();
                view.finishLoading();
              } else {
                view.finishLoadingNoResults();
              }
            }, error -> {
              Log.e(TAG, error.getMessage());
              view.finishLoadingError();
            }));
  }

  private void showCurrentQuestion(Question question) {
    view.setCategoryTitle(question.getCategory());
    view.setDifficultyTitle(question.getDifficulty());
    view.setQuestionText(question.getQuestion());
    List<String> answersList = new ArrayList<>();
    answersList.addAll(question.getIncorrectAnswers());
    answersList.add(question.getCorrectAnswer());
    Collections.shuffle(answersList);
    view.setAnswers(question.getType()
        .equals(MULTIPLE_GAME_TYPE), answersList);
  }

  @Override public void loadQuestion() {
    if (questionPosition < questionsList.size()) {
      showCurrentQuestion(questionsList.get(questionPosition));
    } else {
      view.showResults(numberOfCorrectAnswers, questionsList.size());
    }
  }

  @Override public void answerQuestion(String answer) {
    if (questionsList.get(questionPosition)
        .getCorrectAnswer()
        .equalsIgnoreCase(answer)) {
      view.showResponseCorrect();
      numberOfCorrectAnswers++;
    } else {
      view.showResponseIncorrect(questionsList.get(questionPosition)
          .getCorrectAnswer());
    }
    questionPosition++;
    loadQuestion();
  }
}
