package com.ys.yoosir.zzshow.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *   RxJava Demo 类
 * Created by ys on 2016/10/13 0013.
 */
public class RxJavaSimple {

    private final static String TAG = RxJavaSimple.class.getSimpleName();

    public void testHelloWorld(){
        //创建一个Observable 的对象
        Observable<String> mObservable = Observable.create(
            new Observable.OnSubscribe<String>(){

                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext("Hello world!");
                    subscriber.onCompleted();
                }
            }
        );
        //创建一个Subscriber对象
        Subscriber<String> mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                //完成
                System.out.println("-- onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //异常
                System.out.println("-- onError -error="+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                //下一步
                System.out.print("-- onNext -s="+s);
            }
        };
        //关联 Observable 和 Subscriber
        mObservable.subscribe(mSubscriber);
    }

    public void testObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext");
            }
        };
    }

    //订阅
    public void testSubscribe(){
        //创建一个Observable 的对象
        Observable<String> mObservable = Observable.create(
                new Observable.OnSubscribe<String>(){

                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello world!");
                        subscriber.onCompleted();
                    }
                }
        );

        //onNext
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"onNext");
                System.out.println(s);
            }
        };

        //onError
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //Error handling
                Log.d(TAG,"onError");
            }
        };

        //onComplete
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.d(TAG,"completed");
            }
        };

        //自动创建 Subscriber ,并使用 onNextAction 来定义 onNext()
        mObservable.subscribe(onNextAction);
        //自动创建 Subscriber ,并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        mObservable.subscribe(onNextAction,onErrorAction);
        //自动创建 Subscriber ,并使用 onNextAction、onErrorAction 和 onCompleteAction 来定义 onNext()、onError() 和 onCompleted()
        mObservable.subscribe(onNextAction,onErrorAction,onCompleteAction);
    }

    /**
     *  测试线程控制
     */
    public void testScheduler(){
        Observable.just(1,2,3,4,5)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG,"number:"+integer);
                        Log.d(TAG,"currentThread:"+Thread.currentThread().toString());
                    }
                });
    }

}
