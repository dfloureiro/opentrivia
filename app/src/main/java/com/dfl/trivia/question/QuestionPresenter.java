package com.dfl.trivia.question;

import android.util.Log;
import com.dfl.trivia.data.questions.QuestionsResponse;
import com.dfl.trivia.data.questions.Result;
import com.dfl.trivia.networking.RequestFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

public class QuestionPresenter implements QuestionContract.Presenter {

  private final QuestionContract.View view;
  private RequestFactory requestFactory;
  private final String token;
  private final int amount;
  private final String categoryId;
  private final String difficulty;
  private final String questionType;
  private CompositeDisposable compositeDisposable;

  private List<Result> questionsList;
  private int questionPosition;
  private int numberOfCorrectAnswers;

  public QuestionPresenter(QuestionContract.View view, RequestFactory requestFactory, String token,
      int amount, String categoryId, String difficulty, String questionType) {
    this.view = view;
    this.requestFactory = requestFactory;
    this.token = token;
    this.amount = amount;
    this.categoryId = categoryId;
    this.difficulty = difficulty;
    this.questionType = questionType;

    questionsList = new ArrayList<>();
    questionPosition = 0;
    numberOfCorrectAnswers = 0;

    compositeDisposable = new CompositeDisposable();
    view.setPresenter(this);
  }

  @Override public void subscribe(QuestionContract.State state) {
    if (state != null) {

    }
    getQuestionList();
  }

  @Override public void unsubscribe() {
    compositeDisposable.clear();
  }

  private void getQuestionList() {
    compositeDisposable.add(
        requestFactory.getQuestionsRequest(token, amount, categoryId, difficulty, questionType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(QuestionsResponse::getResults)
            .subscribe(results -> {
              questionsList = results;
              showNextQuestion();
            }, error -> Log.e("Error", error.getMessage())));
  }

  @Override public void showNextQuestion() {
    if (questionPosition < questionsList.size()) {
      Result question = questionsList.get(questionPosition);
      view.setCategoryTitle(question.getCategory());
      view.setDifficultyTitle(question.getDifficulty());
      view.setQuestionText(question.getQuestion());
      List<String> answersList = question.getIncorrectAnswers();
      answersList.add(question.getCorrectAnswer());
      view.setAnswers(question.getType()
          .equals("multiple"), answersList);
    } else {
      view.showResults(numberOfCorrectAnswers, questionsList.size());
    }
  }

  @Override public void answerQuestion(String answer) {
    if (questionsList.get(questionPosition)
        .getCorrectAnswer()
        .equalsIgnoreCase(answer)) {
      view.showResponse("Correct!");
      numberOfCorrectAnswers++;
    } else {
      view.showResponse("Incorrect! The answer is " + questionsList.get(questionPosition)
          .getCorrectAnswer());
    }
    questionPosition++;
    showNextQuestion();
  }
}
