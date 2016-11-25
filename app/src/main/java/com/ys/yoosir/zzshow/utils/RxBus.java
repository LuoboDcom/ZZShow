package com.ys.yoosir.zzshow.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 *  RxBus 实现事件总线
 *
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/22.
 */
public class RxBus {

    private static volatile RxBus sRxBus;

    private final Subject<Object,Object> mBus;

    //PublishSubject 只会把在订阅发生的事件点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    //单例RxBus
    public static RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                sRxBus = new RxBus();
            }
        }
        return sRxBus;
    }

    // 提供了一个新的事件
    public void post(Object o){
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型（eventType）的 被观察者
    public <T>Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

}
