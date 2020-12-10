package com.finalwork.marvelview.viewmodel;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

public abstract class AbsViewModel<V> {

    protected V mView;

    protected AbsViewModel() {
    }


    @CallSuper
    public void attachView(@NonNull V view) {
        mView = view;
    }

    @CallSuper
    public void detachView() {
        mView = null;
    }


    protected final boolean isViewAttached() {
        return mView != null;
    }

}
