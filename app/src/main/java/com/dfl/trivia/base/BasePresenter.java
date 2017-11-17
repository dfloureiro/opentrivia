package com.dfl.trivia.base;

import io.reactivex.annotations.Nullable;

/**
 * Created by Loureiro on 13/11/2017.
 */

public interface BasePresenter<S extends BaseState> {

    void subscribe(@Nullable S state);

    void unsubscribe();
}
