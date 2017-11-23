package com.dfl.trivia.question;

import com.dfl.trivia.base.BasePresenter;
import com.dfl.trivia.base.BaseState;
import com.dfl.trivia.base.BaseView;
import com.dfl.trivia.question.model.Question;
import java.util.List;

/**
 * Created by diogoloureiro on 17/11/2017.
 *
 * Questions contract between the view, presenter and state implementations
 */

public interface QuestionContract {

  interface View extends BaseView<Presenter> {

    /**
     * sets the category text view
     *
     * @param text category text to set
     */
    void setCategoryTitle(String text);

    /**
     * sets the difficulty text view
     *
     * @param text difficulty text to set
     */
    void setDifficultyTitle(String text);

    /**
     * sets the question text view
     *
     * @param text question text to set
     */
    void setQuestionText(String text);

    /**
     * set the answers screen
     *
     * @param isMultiple screen type, if true if a multiples screen else if a true/false screen
     * @param answers list of answers
     */
    void setAnswers(boolean isMultiple, List<String> answers);

    /**
     * show message that the response is correct
     */
    void showResponseCorrect();

    /**
     * show message that the response is incorrect and shows what's the correct answer
     *
     * @param correctAnswer correct answer
     */
    void showResponseIncorrect(String correctAnswer);

    /**
     * show message with results of game
     *
     * @param numberOfCorrectAnswers number of correct answer
     * @param totalNumberOfAnswers number of total answers
     */
    void showResults(int numberOfCorrectAnswers, int totalNumberOfAnswers);

    /**
     * normal finish loading screen
     */
    void finishLoading();

    /**
     * finish loading screen when a error occurred
     */
    void finishLoadingError();

    /**
     * finish loading screen when no results returned
     */
    void finishLoadingNoResults();
  }

  interface Presenter extends BasePresenter<State> {

    /**
     * instantiates and returns a state with variables to save
     *
     * @return state to save
     */
    State getState();

    /**
     * load the question, if all questions where already loaded, loads results
     */
    void loadQuestion();

    /**
     * answer the current question
     *
     * @param answer given answer
     */
    void answerQuestion(String answer);
  }

  interface State extends BaseState {

    List<Question> getListOfQuestionResults();

    int getCurrentQuestionPosition();

    int getNumberOfCorrectAnswers();
  }
}
