package com.dfl.trivia.question.model;

import java.util.List;

/**
 * Created by diogoloureiro on 17/11/2017.
 */

public class Question {

  private final String questionType;
  private final String categoryTitle;
  private final String difficultyTitle;
  private final String questionText;
  private final String correctAnswer;
  private final List<String> incorrectAnswers;

  public Question(String questionType, String categoryTitle, String difficultyTitle,
      String questionText, String correctAnswer, List<String> incorrectAnswers) {
    this.questionType = questionType;
    this.categoryTitle = categoryTitle;
    this.difficultyTitle = difficultyTitle;
    this.questionText = questionText;
    this.correctAnswer = correctAnswer;
    this.incorrectAnswers = incorrectAnswers;
  }

  public String getQuestionType() {
    return questionType;
  }

  public String getCategoryTitle() {
    return categoryTitle;
  }

  public String getDifficultyTitle() {
    return difficultyTitle;
  }

  public String getQuestionText() {
    return questionText;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  public List<String> getIncorrectAnswers() {
    return incorrectAnswers;
  }
}
