package com.fd.baselibrary.base;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public interface IBasePresenter {
    void attachView(IBaseDisplay view);

    void detachView();
}
