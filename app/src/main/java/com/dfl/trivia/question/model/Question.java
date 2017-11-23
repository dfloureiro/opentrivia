package com.dfl.trivia.question.model;

import java.util.List;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by diogoloureiro on 23/11/2017.
 */

@Parcel public class Question {

  String category;
  String type;
  String difficulty;
  String question;
  String correctAnswer;
  List<String> incorrectAnswers;

  @ParcelConstructor
  public Question(String category, String type, String difficulty, String question,
      String correctAnswer, List<String> incorrectAnswers) {
    this.category = category;
    this.type = type;
    this.difficulty = difficulty;
    this.question = question;
    this.correctAnswer = correctAnswer;
    this.incorrectAnswers = incorrectAnswers;
  }

  public String getCategory() {
    return category;
  }

  public String getType() {
    return type;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public String getQuestion() {
    return question;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  public List<String> getIncorrectAnswers() {
    return incorrectAnswers;
  }
}
