package com.dfl.trivia.main;

import com.dfl.trivia.base.BasePresenter;
import com.dfl.trivia.base.BaseState;
import com.dfl.trivia.base.BaseView;
import com.dfl.trivia.main.model.Category;
import java.util.ArrayList;

/**
 * Created by Loureiro on 13/11/2017.
 */

public interface MainContract {

  interface View extends BaseView<Presenter> {

    void ShowCategory(Category category);

    void setSelectedCategory(int position);
  }

  interface Presenter extends BasePresenter<State> {

    State getState();

    void getSessionToken();

    void getTriviaCategoryList();
  }

  interface State extends BaseState {

    ArrayList<Category> getCategoriesArrayList();

    int getSelectedCategoryPosition();
  }
}
