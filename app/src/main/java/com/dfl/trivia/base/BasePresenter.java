package com.dfl.trivia.base;

import io.reactivex.annotations.Nullable;

/**
 * Created by Loureiro on 13/11/2017.
 *
 * Base presenter interface
 */

public interface BasePresenter<S extends BaseState> {

  /**
   * on presenter subscribe a state is passed. if not null it should use the saved values instead;
   *
   * @param state saved state. can be null
   */
  void subscribe(@Nullable S state);

  /**
   * on unsubscribe the presenter should clear all the saved values and dispose all subscriptions;
   */
  void unsubscribe();
}
