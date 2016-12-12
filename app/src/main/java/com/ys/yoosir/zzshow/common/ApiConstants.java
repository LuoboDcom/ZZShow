package com.ys.yoosir.zzshow.common;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class ApiConstants {

    public static final String HOST_NETEASE = "http://c.m.163.com/";

    //头条TYPE
    public static final String NETEASE_TYPE_HEADLINE = "headline";
    //房产TYPE
    public static final String NETEASE_TYPE_HOUSE = "house";
    //其他TYPE
    public static final String NETEASE_TYPE_OTHER = "list";
    /**
     * 请求
     *
     * example：http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
     *
     * example：http://c.m.163.com/nc/article/BG6CGA9M00264N2N/full.html
     *
     * @param newsType ：headline为头条,house为房产，list为其他
     */
    /** --------------------------  ID ------------------------------**/
    //头条
    public static final String NETEASE_ID_HEADLINE = "T1348647909107";
    // 房产id
    public static final String NETEASE_ID_HOUSE = "5YyX5Lqs";
    //体育
    public static final String NETEASE_ID_SPORTS = "T1348649079062";
    //娱乐
    public static final String NETEASE_ID_ENTERTAINMENT = "T1348648517839";
    //财经
    public static final String NETEASE_ID_FINANCE = "T1348648756099";
    //科技
    public static final String NETEASE_ID_TECH = "T1348649580692";
    //电影
    public static final String NETEASE_ID_MOVIE = "T1348648650048";
    //汽车
    public static final String NETEASE_ID_CAR = "T1348654060988";
    //笑话
    public static final String NETEASE_ID_JOKE = "T1350383429665";
    //游戏
    public static final String NETEASE_ID_GAME = "T1348654151579";
    //时尚
    public static final String NETEASE_ID_FASHION = "T1348650593803";
    //情感
    public static final String NETEASE_ID_EMOTION = "T1348650839000";
    //精选
    public static final String NETEASE_ID_CHOICE = "T1370583240249";
    //电台
    public static final String NETEASE_ID_RADIO = "T1379038288239";
    //NBA
    public static final String NETEASE_ID_NAB = "T1348649145984";
    //数码
    public static final String NETEASE_ID_DIGITAL = "T1348649776727";
    //移动
    public static final String NETEASE_ID_MOBILE = "T1351233117091";
    //彩票
    public static final String NETEASE_ID_LOTTERY = "T1356600029035";
    //教育
    public static final String NETEASE_ID_EDUCATION = "T1348654225495";
    //论坛
    public static final String NETEASE_ID_FORUM = "T1349837670307";
    //旅游
    public static final String NETEASE_ID_TOUR = "T1348654204705";
    //手机
    public static final String NETEASE_ID_PHONE = "T1348649654285";
    //博客
    public static final String NETEASE_ID_BLOG = "T1349837698345";
    //社会
    public static final String NETEASE_ID_SOCIETY = "T1348648037603";
    //家居
    public static final String NETEASE_ID_FURNISHING = "T1348654105308";
    //军事
    public static final String NETEASE_ID_MILITARY = "T1348648141035";


    /**
     * 视频 http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
     */
    public static final String HOST_VIDEO = "http://c.3g.163.com/";
    // 热点视频
    public static final String VIDEO_HOT_ID = "V9LG4B3A0";
    // 娱乐视频
    public static final String VIDEO_ENTERTAINMENT_ID = "V9LG4CHOR";
    // 搞笑视频
    public static final String VIDEO_FUN_ID = "V9LG4E6VR";
    // 精品视频
    public static final String VIDEO_CHOICE_ID = "00850FRB";

    /**
     * 天气预报url
     */
    public static final String WEATHER_HOST = "http://wthrcdn.etouch.cn/";


    /** ---------------------------- 新浪 --------------------------------------**/
    /**
     *  新浪图片新闻
     */
    public static final String HOST_SINA_PHOTO = "http://gank.io/api/";

    //精选列表
    public static final String SINA_ID_PHOTO_CHOICE = "hdpic_toutiao";
    //趣图列表
    public static final String SINA_ID_PHOTO_FUN = "hdpic_funny";
    //美图列表
    public static final String SINA_ID_PHOTO_PRETTY = "hdpic_pretty";
    //故事列表
    public static final String SINA_ID_PHOTO_STORY= "hdpic_story";

    //图片详情
    public static final String SINA_ID_PHOTO_DETAIL = "hdpic_hdpic_toutiao_4";

    /** ---------------------------   图片详情  ------------------------------------ **/
    public static final String HOST_HTML_PHOTO = "http://kaku.com/";

    /** ------------------------------  今日头条   ---------------------------------- **/
    public static final String HOST_TOUTIAO = "http://www.toutiao.com/api/";

    /**
     *  获取对应的 Url Host
     * @param hostType  host 类型
     * @return host
     */
    public static String getHost(@HostType.HostTypeChecker int hostType){
        switch (hostType){
            case HostType.NETEASE_NEWS_VIDEO:
                return HOST_NETEASE;
            case HostType.GANK_GIRL_PHOTO:
                return HOST_SINA_PHOTO;
            case HostType.NEWS_DETAIL_HTML_PHOTO:
                return HOST_HTML_PHOTO;
            case HostType.TOUTIAO_PHOTO:
                return HOST_TOUTIAO;
            case HostType.VIDEO_HOST:
                return HOST_VIDEO;
            default:
                return "";
        }
    }

    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    public static String getType(String id) {
        switch (id) {
            case NETEASE_ID_HEADLINE:
                return NETEASE_TYPE_HEADLINE;
            case NETEASE_ID_HOUSE:
                return NETEASE_TYPE_HOUSE;
            default:
                break;
        }
        return NETEASE_TYPE_OTHER;
    }
}
