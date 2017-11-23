package com.dfl.trivia.main;

import com.dfl.trivia.base.BasePresenter;
import com.dfl.trivia.base.BaseState;
import com.dfl.trivia.base.BaseView;
import com.dfl.trivia.main.model.Category;
import java.util.ArrayList;

/**
 * Created by Loureiro on 13/11/2017.
 *
 * Main contract between the view, presenter and state implementations
 */

public interface MainContract {

  interface View extends BaseView<Presenter> {

    /**
     * adds a category to the spinner
     *
     * @param category category to add
     */
    void ShowCategory(Category category);

    /**
     * finish loading screen
     *
     * @param hasError if true, show the error screen
     */
    void finishLoading(boolean hasError);
  }

  interface Presenter extends BasePresenter<State> {

    /**
     * instantiates and returns a state with variables to save
     *
     * @return state to save
     */
    State getState();

    /**
     * get a new session token from server
     */
    void getSessionToken();

    /**
     * get list of categories from the server
     */
    void getTriviaCategoryList();

    /**
     * get id from the selected category in the view
     */
    String getSelectedCategoryId(int position);
  }

  interface State extends BaseState {

    ArrayList<Category> getCategoriesArrayList();
  }
}
