package com.ys.yoosir.zzshow.apis;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.ys.yoosir.zzshow.common.ApiConstants;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.model.apis.NewsListModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/11 0011.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NewsModuleApiImplTest{

    private NewsListModuleApiImpl moduleApi;

    @Before
    public void setUp() throws Exception {
        moduleApi = new NewsListModuleApiImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadNews() throws Exception {
        System.out.println("-- 1.testLoadNews --");
        // 让Schedulers.io()返回当前线程
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        moduleApi.loadNews(new RequestCallBack<List<NewsSummary>>() {
            @Override
            public void beforeRequest() {

            }

            @Override
            public void success(List<NewsSummary> data) {

            }

            @Override
            public void onError(String errorMsg) {
            }
        }, ApiConstants.NETEASE_TYPE_OTHER, ApiConstants.NETEASE_ID_GAME, 0);

        assertEquals(1,1);
    }
}