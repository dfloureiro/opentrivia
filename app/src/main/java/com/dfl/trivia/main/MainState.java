package com.dfl.trivia.main;

import com.dfl.trivia.main.model.Category;
import java.util.ArrayList;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

/**
 * Created by Loureiro on 17/11/2017.
 *
 * Main state, this is parcelable and it's saved on view lifecycle
 */

@Parcel public class MainState implements MainContract.State {

  @Transient static final String CATEGORY_STATE_KEY = "CATEGORY_STATE_KEY";

  ArrayList<Category> categoryArrayList;

  @ParcelConstructor MainState(ArrayList<Category> categoryArrayList) {
    this.categoryArrayList = categoryArrayList;
  }

  @Override public ArrayList<Category> getCategoriesArrayList() {
    return categoryArrayList;
  }
}
