package com.dfl.trivia.main;

import com.dfl.trivia.main.model.Category;
import java.util.ArrayList;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Loureiro on 17/11/2017.
 */

@Parcel public class MainState implements MainContract.State {

  static final String CATEGORY_STATE_KEY = "CATEGORY_STATE_KEY";

  private ArrayList<Category> categoryArrayList;

  @ParcelConstructor MainState(ArrayList<Category> categoryArrayList) {
    this.categoryArrayList = categoryArrayList;
  }

  @Override public ArrayList<Category> getCategoriesArrayList() {
    return categoryArrayList;
  }
}
