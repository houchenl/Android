package com.hc.rxjava.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by liulei0905 on 2016/8/1.
 * Test RxBus.
 */
public enum RxBus {

    INSTANCE;

    private final Subject<Object, Object> bus;

    RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * Send a new event.
     * */
    public void post(Object obj) {
        bus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
