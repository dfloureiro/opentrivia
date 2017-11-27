package com.dfl.trivia;

import com.dfl.trivia.datamodel.category.TriviaCategory;
import com.dfl.trivia.datamodel.category.TriviaCategoryList;
import com.dfl.trivia.datamodel.token.SessionTokenResponse;
import com.dfl.trivia.main.MainContract;
import com.dfl.trivia.main.MainPresenter;
import com.dfl.trivia.network.RequestFactory;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by diogoloureiro on 24/11/2017.
 *
 * Test suite for Main Presenter
 */

@RunWith(MockitoJUnitRunner.class) public class MainPresenterTest {

  @Mock private MainContract.View mainView;
  @Mock private RequestFactory requestFactory;
  @Mock private TriviaSharedPreferences triviaSharedPreferences;

  private MainContract.Presenter mainPresenter;

  @Before public void setUp() throws Exception {
    Scheduler immediate = new Scheduler() {
      @Override
      public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
        return super.scheduleDirect(run, 0, unit);
      }

      @Override public Worker createWorker() {
        return new ExecutorScheduler.ExecutorWorker(Runnable::run);
      }
    };
    RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    mainPresenter = new MainPresenter(mainView, requestFactory, triviaSharedPreferences);
  }

  @Test public void testGetTriviaCategoriesSuccess() {
    TriviaCategory triviaCategory = new TriviaCategory();
    triviaCategory.setId(1);
    triviaCategory.setName("Cat1");
    ArrayList<TriviaCategory> triviaCategoryArrayList = new ArrayList<>();
    triviaCategoryArrayList.add(triviaCategory);
    TriviaCategoryList triviaCategoryList = new TriviaCategoryList();
    triviaCategoryList.setTriviaCategories(triviaCategoryArrayList);

    when(requestFactory.getTriviaCategoryListRequest()).thenReturn(
        Flowable.just(triviaCategoryList));

    mainPresenter.getTriviaCategoryList();

    verify(mainView).setPresenter(mainPresenter);
    verify(mainView).finishLoading(false);
    verify(mainView).ShowCategory(any());
  }

  @Test public void testGetTriviaCategoriesFail() {
    when(requestFactory.getTriviaCategoryListRequest()).thenReturn(Flowable.error(new Exception()));

    mainPresenter.getTriviaCategoryList();

    verify(mainView).setPresenter(mainPresenter);
    verify(mainView).finishLoading(true);
  }

  @Test public void testSessionTokenSuccess() {
    final String token = "mytoken";
    SessionTokenResponse sessionTokenResponse = new SessionTokenResponse();
    sessionTokenResponse.setToken(token);

    when(requestFactory.getSessionTokenRequest()).thenReturn(Flowable.just(sessionTokenResponse));

    mainPresenter.getSessionToken();

    verify(triviaSharedPreferences).saveSessionToken(token);
  }

  @Test public void testSessionTokenFail() {
    when(requestFactory.getSessionTokenRequest()).thenReturn(Flowable.error(new Exception()));

    mainPresenter.getSessionToken();

    verify(mainView).finishLoading(true);
  }
}
