package com.ys.yoosir.zzshow.rxjavademo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/11/2.
 */
public class RxJavaSimpleTest {
    private RxJavaSimple mRxJavaSimple;

    @Before
    public void setUp() throws Exception {
        mRxJavaSimple = new RxJavaSimple();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void operatorCreate() throws Exception {
        mRxJavaSimple.operatorCreate();
    }

    @Test
    public void operatorJust() throws Exception {
        mRxJavaSimple.operatorJust();
    }

    @Test
    public void operatorFrom() throws Exception {
        mRxJavaSimple.operatorFrom();
    }

    @Test
    public void operatorFromFuture() throws Exception {
        mRxJavaSimple.operatorFromFuture();
    }

    @Test
    public void operatorDefer() throws Exception {
        mRxJavaSimple.operatorDefer();
    }

    @Test
    public void operatorTimer() throws Exception {
        mRxJavaSimple.operatorTimer();
    }

    @Test
    public void operatorBuffer() throws Exception {
        mRxJavaSimple.operatorBuffer();
    }

    @Test
    public void operatorClosingSelector() throws Exception {
        mRxJavaSimple.operatorClosingSelector();
    }

    @Test
    public void operatorBufferSkip() throws Exception {
        mRxJavaSimple.operatorBufferSkip();
    }

    @Test
    public void operatorMap() throws Exception {
        mRxJavaSimple.operatorMap();
    }

    @Test
    public void operatorFlatMap() throws Exception {
        mRxJavaSimple.operatorFlatMap();
    }

    @Test
    public void operatorScan() throws Exception {
        mRxJavaSimple.operatorScan();
    }

    @Test
    public void operatorFilter() throws Exception {
        mRxJavaSimple.operatorFilter();
    }

    @Test
    public void operatorTake() throws Exception {
        mRxJavaSimple.operatorTake();
    }

    @Test
    public void operatorTakeLastBuffer() throws Exception{
        mRxJavaSimple.operatorTakeLastBuffer();
    }

    @Test
    public void operatorSkip() throws Exception {
        mRxJavaSimple.operatorSkip();
    }

    @Test
    public void operatorElementAt() throws Exception {
        mRxJavaSimple.operatorElementAt();
    }

    @Test
    public void operatorDistinct() throws Exception {
        mRxJavaSimple.operatorDistinct();
    }

    @Test
    public void operatorStartWith() throws Exception {
        mRxJavaSimple.operatorStartWith();
    }

    @Test
    public void operatorJoin() throws Exception {
        mRxJavaSimple.operatorJoin();
    }

    @Test
    public void operatorZip() throws Exception {
        mRxJavaSimple.operatorZip();
    }

    @Test
    public void scheduler() throws Exception {
        mRxJavaSimple.scheduler();
    }

    @Test
    public void testJava() throws Exception{
        mRxJavaSimple.testJava();
    }

}