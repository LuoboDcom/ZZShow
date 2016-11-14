package com.ys.yoosir.zzshow.apis;

import com.ys.yoosir.zzshow.ApplicationTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class PostModuleApiImplTest extends ApplicationTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetArticles() throws Exception {
        long maxmaxBehotTime = System.currentTimeMillis()/1000 - 3 * 60 * 60;
        VideoModuleApiImpl.getInstance().getVideoList(maxmaxBehotTime);
    }
}