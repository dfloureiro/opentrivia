package com.dfl.trivia.question;

import com.dfl.trivia.data.questions.Result;

import java.util.List;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

@Parcel public class QuestionState implements QuestionContract.State {

  static final String QUESTION_STATE_KEY = "QUESTION_STATE_KEY";

  private final List<Result> results;
  private final int questionPosition;
  private final int numberOfCorrectAnswers;

  @ParcelConstructor QuestionState(List<Result> results, int questionPosition,
      int numberOfCorrectAnswers) {

    this.results = results;
    this.questionPosition = questionPosition;
    this.numberOfCorrectAnswers = numberOfCorrectAnswers;
  }

  @Override public List<Result> getListOfQuestionResults() {
    return results;
  }

  @Override public int getCurrentQuestionPosition() {
    return questionPosition;
  }

  @Override public int getNumberOfCorrectAnswers() {
    return numberOfCorrectAnswers;
  }
}
