package com.ys.yoosir.zzshow.mvp.model.apis;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.common.ApiConstants;
import com.ys.yoosir.zzshow.common.HostType;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsModuleApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsDetail;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsSummary;
import com.ys.yoosir.zzshow.repository.localdb.NewsChannelTableManager;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsChannelTable;
import com.ys.yoosir.zzshow.repository.network.NewsService;
import com.ys.yoosir.zzshow.utils.DateUtil;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * @version 1.0
 *          Created by Yoosir on 2016/11/14 0014.
 */
public class NewsModuleApiImpl implements NewsModuleApi {

    public NewsModuleApiImpl(){

    }

    /**
     * 加载 新闻 频道数据
     * @param callBack
     * @return
     */
    @Override
    public Subscription loadNewsChannel(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelTableManager.initDB();
                List<NewsChannelTable> list = NewsChannelTableManager.loadNewsChannelsMine();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        })
                .compose(RxJavaCustomTransform.<List<NewsChannelTable>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsChannelTable>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(MyApplication.getInstance().getString(R.string.db_error));
                    }

                    @Override
                    public void onNext(List<NewsChannelTable> newsChannelTables) {
                        callBack.success(newsChannelTables);
                    }
                });
    }

    /**
     * 加载新闻列表数据
     * @param listener
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    @Override
    public Subscription loadNews(final RequestCallBack<List<NewsSummary>> listener, String type, final String id, int startPage) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .createService(NewsService.class)
                .getNewsList(OkHttpUtil.getCacheControl(),type,id,startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        if(ApiConstants.NETEASE_ID_HOUSE.equals(id)){
                            return Observable.from(stringListMap.get("北京"));
                        }else{
                            return Observable.from(stringListMap.get(id));
                        }
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = DateUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(RxJavaCustomTransform.<List<NewsSummary>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {
                        KLog.d(TAG,"-- onCompleted--");
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(TAG,e.toString());
                        if(listener != null) {
                            listener.onError(e.toString());
                        }
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        for (NewsSummary bean:newsSummaries) {
                            KLog.d(TAG,"onNext -- bean = "+bean.toString());
                        }
                        if(listener != null) {
                            listener.success(newsSummaries);
                        }
                    }
                });
    }

    /**
     * 加载新闻详情数据
     * @param callBack
     * @param postId
     * @return
     */
    @Override
    public Subscription getNewsDetail(final RequestCallBack<NewsDetail> callBack, final String postId) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).createService(NewsService.class)
                .getNewsDetail(OkHttpUtil.getCacheControl(),postId)
                .map(new Func1<Map<String,NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> stringNewsDetailMap) {
                        NewsDetail newsDetail = stringNewsDetailMap.get(postId);
                        changeNewsDetail(newsDetail);
                        return newsDetail;
                    }
                })
                .compose(RxJavaCustomTransform.<NewsDetail>defaultSchedulers())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        callBack.success(newsDetail);
                    }
                });
    }

    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if(imgSrcs != null){
            String newsBody = newsDetail.getBody();
            //替换 newsBody 中的 img 标签
            newsBody = changeNewsBody(imgSrcs,newsBody);
            System.out.println("body = " + newsBody);
            newsDetail.setBody(newsBody);
        }
    }

    /**
     * 替换 newsBody 的 IMG 标签
     * @param imgSrcs   图片路径集合
     * @param newsBody  新闻主体
     * @return String
     */
    private String changeNewsBody(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (NewsDetail.ImgBean bean:imgSrcs) {
            String oldChars = bean.getRef();
            String newsChars = "<img src='"+bean.getSrc()+"' />";
            newsBody = newsBody.replace(oldChars,newsChars);
        }
        return newsBody;
    }

    @Override
    public void onDestroy() {

    }
}
