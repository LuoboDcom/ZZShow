package com.ys.yoosir.zzshow.rxjavademo;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
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

    public void operatorCreate(){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    public void operatorJust(){
        Observable.just(1, 2, 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void operatorFrom(){
        Integer[] nums = {1,3,7,2,5};
        Observable.from(nums)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void operatorFromFuture(){

        Observable.from(new Future<String>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {

                return "Hello world !";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                System.out.println("timeout-"+timeout+"--unit-"+unit);
                return "Hello world!";
            }
        }).subscribe(new Subscriber<String>() {

            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    private Integer[] nums = {1,2,3,4};
    private Integer[] yy = {7,8,9,0};
    private String str = "hello world";
    public void operatorDefer(){
        Observable<Integer> mObservable = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.from(nums);
            }
        });
        //改变数组值
        nums = yy;

        mObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    public void operatorStart(){
//        Observable.Start()
    }

    public void operatorTimer(){
        System.out.println("开始："+System.currentTimeMillis()/1000);
        Observable.timer(3,TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("执行："+System.currentTimeMillis()/1000);
                        System.out.println("hello world");
                    }
                });
    }

    public void operatorBuffer(){
        Integer[] nums = {1,2,3,4,5};
        Observable.from(nums).buffer(2)
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.print("integers = ");
                        for (Integer i: integers) {
                            System.out.print(i +" ");
                        }
                        System.out.println();
                    }
                });
    }

    public void operatorBufferSkip(){
        Integer[] nums = {1,2,3,4,5,6,7,8,9};
        Observable.from(nums).buffer(2,3)
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.print("integers = ");
                        for (Integer i: integers) {
                            System.out.print(i +" ");
                        }
                        System.out.println();
                    }
                });


    }

    String[] a = {"hello",",","world",",","!",","};
    public void operatorClosingSelector(){
//        String[] a = {"hello",",","world",",","!",","};
        Observable.from(a).buffer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.from(a);
            }
        }).subscribe(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onNext(List<String> strings) {
                System.out.print("onNext = " + strings.toString()+"-");
                for (String str: strings) {
                    System.out.print(str +" ");
                }
                System.out.println();
            }
        });
    }

    public void operatorMap(){
        Observable.from(nums).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer i) {
                return "this is i="+i;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext = " + s);
            }
        });
    }

    public void operatorFlatMap(){
        Observable.from(nums).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                double d = Math.pow(integer.intValue(),3);
                return Observable.just("this is i="+d);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext = " + s);
            }
        });
    }

    public void operatorScan(){
        Observable.just(1, 2, 3, 4, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer sum, Integer item) {
                        return sum + item;
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    /**
     *  #############################################
     *  过滤操作符
     *  #############################################
     */
    public void operatorFilter(){
        Observable.just(1, 2, 3, 4, 5)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer item) {
                        return( item < 4 );
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    public void operatorTake(){
        /**
         * 只返回前面 2个数据
         */
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    public void operatorTakeLastBuffer(){

        Observable.just(1, 2, 3, 4, 5)
                .takeLastBuffer(3)
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.print("onNext: ");
                        for (int i = 0; i < integers.size(); i++) {
                            System.out.print(integers.get(i));
                            if(i != integers.size() - 1){
                                System.out.print(",");
                            }
                        }
                        System.out.println();
                    }
                });
    }

    public void operatorSkip(){
        /**
         * 跳过前面 2个数据
         */
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void operatorElementAt(){

        Observable.just(1, 2, 3, 4, 5)
                .elementAt(2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void operatorDistinct(){

        Integer[] nums = {1,1,2,3,1,5,2,3,7,4};

        Observable.from(nums)
                .distinct()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void operatorStartWith(){
        Integer[] nums = {1,2,3,4};
        Observable.from(nums)
                .startWith(9,8)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }


    public void operatorJoin(){

        Observable<Integer> create1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 6; i++) {
                    subscriber.onNext(i);

                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());

        Observable<String> create2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 4; i++) {
                    subscriber.onNext("hello_"+ i);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());

        create1.join(create2,
                new Func1<Integer, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Integer integer) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func2<Integer, String, String>() {
                    @Override
                    public String call(Integer integer1, String s) {
                        return integer1 + "-" + s;
                    }
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }
        });
    }

    public void operatorZip(){

        Observable.zip(
                Observable.just("a1","a2","a3","a4","a5"),
                Observable.just(1,2,3,4),
                Observable.just("b1","b2","b3","b4","b5","b6"),
                new Func3<String,Integer,String,String>(){

                    @Override
                    public String call(String s, Integer integer, String s2) {
                        return s+"_"+integer+"_"+s2;
                    }
                }

        ).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }
        });

    }

    public void scheduler(){
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        System.out.println("create -  当前线程信息："+Thread.currentThread().getName());
                        int i = 1+2+3+4+5+6+7+8+9;
                        subscriber.onNext(i);
                        subscriber.onCompleted();
                    }
                 })
                .subscribeOn(Schedulers.computation())  // Observable.from 在 computation 调度器上执行
                .observeOn(Schedulers.io())             // Subscriber 在 io 调度器上执行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("next - 当前线程信息："+Thread.currentThread().getName());
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
    }

    public void testJava(){
        String str = "asdc|";
        String[] array = str.split("\\|");
        System.out.println("length = "+ array.length);
        for (int i = 0; i < array.length; i++) {
            System.out.println("--"+ array[i] +"--");
        }
    }
}
