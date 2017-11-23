package com.dfl.trivia.question;

import com.dfl.trivia.question.model.Question;
import java.util.List;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

@Parcel public class QuestionState implements QuestionContract.State {

  @Transient static final String QUESTION_STATE_KEY = "QUESTION_STATE_KEY";

  List<Question> results;
  int questionPosition;
  int numberOfCorrectAnswers;

  @ParcelConstructor QuestionState(List<Question> results, int questionPosition,
      int numberOfCorrectAnswers) {

    this.results = results;
    this.questionPosition = questionPosition;
    this.numberOfCorrectAnswers = numberOfCorrectAnswers;
  }

  @Override public List<Question> getListOfQuestionResults() {
    return results;
  }

  @Override public int getCurrentQuestionPosition() {
    return questionPosition;
  }

  @Override public int getNumberOfCorrectAnswers() {
    return numberOfCorrectAnswers;
  }
}
