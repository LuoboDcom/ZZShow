package com.ys.yoosir.zzshow;

/**
 *  常量
 * Created by Yoosir on 2016/10/20 0020.
 */
public class Constants {

    public static final int NEWS_CHANNEL_MINE = 1;
    public static final int NEWS_CHANNEL_RECOMMEND = 0;

    /**
     * http://www.toutiao.com/api/article/recent/?source=2&count=20&category=gallery_detail
     *          &max_behot_time=1476919670&utm_source=toutiao
     *          &device_platform=web
     *          &offset=0&as=A1B53800B8C5001&cp=5808458090012E1
     *          &max_create_time=1476849570&_=1476939739038
     *
     * http://www.toutiao.com/api/article/recent/?source=2&count=20&category=gallery_story
     *          &max_behot_time=1476915696&utm_source=toutiao
     *          &device_platform=web
     *          &offset=0&as=A1F558D0E8153A2&cp=58085523EA727E1
     *          &max_create_time=1471017973&_=1476940675594
     */

    public static final String ARTICLE_PATH = "article/recent/";

    /**
    http://www.toutiao.com/api/article/feed/
     ?category=video
     &utm_source=toutiao
     &widen=0
     &max_behot_time=1477183446
     &max_behot_time_tmp=1477183446
     &as=A19598408C0614F
     &cp=580C36D1944F8E1

     **/
    public static final String VIDEO_PATH = "article/feed/";

    public static final String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";
}
