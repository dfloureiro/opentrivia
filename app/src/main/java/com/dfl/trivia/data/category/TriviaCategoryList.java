package com.dfl.trivia.data.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TriviaCategoryList {

  @SerializedName("trivia_categories") @Expose private List<TriviaCategory> triviaCategories = null;

  public List<TriviaCategory> getTriviaCategories() {
    return triviaCategories;
  }

  public void setTriviaCategories(List<TriviaCategory> triviaCategories) {
    this.triviaCategories = triviaCategories;
  }
}
