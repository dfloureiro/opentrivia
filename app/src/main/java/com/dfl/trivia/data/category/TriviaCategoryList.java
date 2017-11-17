
package com.dfl.trivia.data.category;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TriviaCategoryList {

    @SerializedName("trivia_categories")
    @Expose
    private List<TriviaCategory> triviaCategories = null;

    public List<TriviaCategory> getTriviaCategories() {
        return triviaCategories;
    }

    public void setTriviaCategories(List<TriviaCategory> triviaCategories) {
        this.triviaCategories = triviaCategories;
    }

}
