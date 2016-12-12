package com.ys.yoosir.zzshow.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  Url Host Type
 *  @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class HostType {

    /**
     *  多少种 Host 类型
     */
    public static final int TYPE_COUNT = 5;

    /**
     *  网易新闻的 Host
     */
    public static final int NETEASE_NEWS_VIDEO = 1;

    /**
     *  新浪图片的 Host
     */
    public static final int GANK_GIRL_PHOTO = 2;

    /**
     * 新浪详情 HTML图片的 Host
     */
    public static final int NEWS_DETAIL_HTML_PHOTO = 3;

    /**
     *  今日头条的 Host
     */
    public static final int TOUTIAO_PHOTO = 4;

    /**
     *  网易视频的 Host
     */
    public static final int VIDEO_HOST = 5;

    /**
     * 替代枚举的方案，使用 IntDef保证类型安全
     */
    @IntDef({NETEASE_NEWS_VIDEO,GANK_GIRL_PHOTO,NEWS_DETAIL_HTML_PHOTO,TOUTIAO_PHOTO,VIDEO_HOST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker{

    }

}
