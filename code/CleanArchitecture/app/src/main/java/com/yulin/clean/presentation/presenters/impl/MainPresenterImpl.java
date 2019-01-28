package com.yulin.clean.presentation.presenters.impl;

import com.yulin.clean.domain.executor.Executor;
import com.yulin.clean.domain.executor.MainThread;
import com.yulin.clean.domain.interactors.SampleInteractor;
import com.yulin.clean.presentation.presenters.MainPresenter;
import com.yulin.clean.presentation.presenters.base.AbstractPresenter;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        SampleInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
        mView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
