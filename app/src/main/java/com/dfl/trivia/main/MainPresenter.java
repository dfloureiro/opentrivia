package com.dfl.trivia.main;

import android.util.Log;
import com.dfl.trivia.TriviaSharedPreferences;
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

  private final static String TAG = MainPresenter.class.getCanonicalName();

  private MainContract.View view;
  private RequestFactory requestFactory;
  private TriviaSharedPreferences triviaSharedPreferences;

  private CompositeDisposable compositeDisposable;
  private ArrayList<Category> categoryArrayList;

  MainPresenter(MainContract.View view, RequestFactory requestFactory,
      TriviaSharedPreferences triviaSharedPreferences) {
    this.view = view;
    this.requestFactory = requestFactory;
    this.triviaSharedPreferences = triviaSharedPreferences;

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
        view.finishLoading(false);
        return;
      }
    }
    getTriviaCategoryList();
    if (triviaSharedPreferences.getSessionToken()
        .isEmpty()) {
      getSessionToken();
    }
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
        .subscribe(sessionTokenResponse -> triviaSharedPreferences.saveSessionToken(
            sessionTokenResponse.getToken()), error -> Log.e(TAG, error.getMessage())));
  }

  @Override public void getTriviaCategoryList() {
    compositeDisposable.add(requestFactory.getTriviaCategoryListRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(
            triviaCategoryList -> Flowable.fromIterable(triviaCategoryList.getTriviaCategories()))
        .map(triviaCategory -> new Category(triviaCategory.getId(), triviaCategory.getName()))
        .subscribe(category -> {
          view.finishLoading(false);
          categoryArrayList.add(category);
          view.ShowCategory(category);
        }, error -> {
          view.finishLoading(true);
          Log.e(TAG, error.getMessage());
        }));
  }

  @Override public String getSelectedCategoryId(int position) {
    return String.valueOf(categoryArrayList.get(position)
        .getId());
  }
}
