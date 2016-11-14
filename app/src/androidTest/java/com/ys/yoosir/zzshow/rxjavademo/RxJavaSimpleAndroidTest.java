package com.ys.yoosir.zzshow.rxjavademo;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *   RxJava simple test
 * Created by ys on 2016/10/14 0014.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RxJavaSimpleAndroidTest {

    private RxJavaSimple mRxJavaSimple;

    @Before
    public void createRxJavaSimple(){
        mRxJavaSimple = new RxJavaSimple();
    }

    @Test
    public void testScheduler(){
        mRxJavaSimple.testScheduler();
    }

    @Test
    public void testOperatorCreate(){
        mRxJavaSimple.operatorCreate();
    }

}
