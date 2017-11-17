package com.dfl.trivia.main;

import android.util.Log;
import com.dfl.trivia.main.model.Category;
import com.dfl.trivia.networking.RequestFactory;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 * Created by Loureiro on 13/11/2017.
 */

public class MainPresenter implements MainContract.Presenter {

  private final MainContract.View view;
  private RequestFactory requestFactory;
  private CompositeDisposable compositeDisposable;

  private ArrayList<Category> categoryArrayList;

  public MainPresenter(MainContract.View view, RequestFactory requestFactory) {
    this.view = view;
    this.requestFactory = requestFactory;

    compositeDisposable = new CompositeDisposable();
    categoryArrayList = new ArrayList<>();
    view.setPresenter(this);
  }

  @Override public void subscribe(MainContract.State state) {
    if (state != null) {
      categoryArrayList = state.getCategoriesArrayList();
      if (!categoryArrayList.isEmpty()) {
        for (Category category : categoryArrayList) {
          view.ShowCategory(category);
        }
        return;
      }
    }
    getTriviaCategoryList();
    getSessionToken();
  }

  @Override public void unsubscribe() {
    compositeDisposable.clear();
  }

  @Override public MainContract.State getState() {
    return new MainState(categoryArrayList);
  }

  public void getSessionToken() {
    compositeDisposable.add(requestFactory.getSessionTokenRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(sessionTokenResponse -> saveSessionToken(sessionTokenResponse.getToken()),
            error -> Log.e("Error", error.getMessage())));
  }

  @Override public void getTriviaCategoryList() {
    compositeDisposable.add(requestFactory.getTriviaCategoryListRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(
            triviaCategoryList -> Flowable.fromIterable(triviaCategoryList.getTriviaCategories()))
        .map(triviaCategory -> new Category(triviaCategory.getId(), triviaCategory.getName()))
        .subscribe(category -> {
          categoryArrayList.add(category);
          view.ShowCategory(category);
        }, error -> Log.e("Error", error.getMessage())));
  }

  @Override public String getSelectedCategoryId(int position) {
    return String.valueOf(categoryArrayList.get(position)
        .getId());
  }

  private void saveSessionToken(String sessionToken) {
    Log.d("TOKEN", sessionToken);
  }
}
