package com.dfl.trivia.main;

import com.dfl.trivia.main.model.Category;
import java.util.ArrayList;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Loureiro on 17/11/2017.
 */

@Parcel public class MainState implements MainContract.State {

  public static final String CATEGORY_STATE_KEY = "CATEGORY_STATE_KEY";

  private ArrayList<Category> categoryArrayList;
  private int selectedCategoryPosition;

  @ParcelConstructor
  public MainState(ArrayList<Category> categoryArrayList, int selectedCategoryPosition) {
    this.categoryArrayList = categoryArrayList;
    this.selectedCategoryPosition = selectedCategoryPosition;
  }

  @Override public ArrayList<Category> getCategoriesArrayList() {
    return categoryArrayList;
  }

  @Override public int getSelectedCategoryPosition() {
    return selectedCategoryPosition;
  }
}
