package com.ys.yoosir.zzshow.mvp.model.apis;

import com.ys.yoosir.zzshow.common.HostType;
import com.ys.yoosir.zzshow.mvp.model.apis.interfaces.NewsDetailModuleApi;
import com.ys.yoosir.zzshow.common.RequestCallBack;
import com.ys.yoosir.zzshow.repository.network.NewsService;
import com.ys.yoosir.zzshow.mvp.model.entity.netease.NewsDetail;
import com.ys.yoosir.zzshow.utils.httputil.OkHttpUtil;
import com.ys.yoosir.zzshow.utils.httputil.RetrofitManager;
import com.ys.yoosir.zzshow.utils.httputil.RxJavaCustomTransform;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @version 1.0
 * Created by Yoosir on 2016/11/15 0015.
 */
public class NewsDetailModuleApiImpl implements NewsDetailModuleApi<NewsDetail>{

    @Inject
    public NewsDetailModuleApiImpl(){

    }

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
}
