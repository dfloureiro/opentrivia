package com.dfl.trivia.base;

/**
 * Created by Loureiro on 13/11/2017.
 *
 * Base View interface
 */

public interface BaseView<T> {

  /**
   * gets the presenter to interact with. the responsability of seting the presenter if from the
   * presenter;
   *
   * @param presenter given presenter
   */
  void setPresenter(T presenter);
}
