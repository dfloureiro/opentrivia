package com.dfl.trivia.question;

import com.dfl.trivia.base.BasePresenter;
import com.dfl.trivia.base.BaseState;
import com.dfl.trivia.base.BaseView;
import com.dfl.trivia.question.model.Question;
import java.util.List;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

public interface QuestionContract {

  interface View extends BaseView<Presenter> {

    void setCategoryTitle(String text);

    void setDifficultyTitle(String text);

    void setQuestionText(String text);

    void setAnswers(boolean isMultiple, List<String> answers);

    void showResponseCorrect();

    void showResponseIncorrect(String correctAnswer);

    void showResults(int numberOfCorrectAnswers, int totalNumberOfAnswers);

    void finishLoading();

    void finishLoadingError();

    void finishLoadingNoResults();
  }

  interface Presenter extends BasePresenter<State> {

    State getState();

    void loadQuestion();

    void answerQuestion(String answer);
  }

  interface State extends BaseState {

    List<Question> getListOfQuestionResults();

    int getCurrentQuestionPosition();

    int getNumberOfCorrectAnswers();
  }
}
