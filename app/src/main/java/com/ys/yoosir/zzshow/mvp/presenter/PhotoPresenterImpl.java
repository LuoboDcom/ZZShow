package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.PhotoModuleApiImpl;
import com.ys.yoosir.zzshow.apis.interfaces.PhotoModuleApi;
import com.ys.yoosir.zzshow.mvp.modle.photos.PhotoGirl;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoPresenter;
import com.ys.yoosir.zzshow.mvp.view.PhotoGirlView;

import java.util.List;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/29.
 */

public class PhotoPresenterImpl extends BasePresenterImpl<PhotoGirlView,List<PhotoGirl>> implements PhotoPresenter {

    private PhotoModuleApi<List<PhotoGirl>> mModuleApi;
    private int mSize = 20;
    private int mStartPage;

    public PhotoPresenterImpl(){
        mModuleApi= new PhotoModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            beforeRequest();
            mStartPage = 1;
            loadPhotoData();
        }
    }

    @Override
    public void loadPhotoData() {
        mSubscription = mModuleApi.getPhotoList(this,mSize,mStartPage);
    }

    @Override
    public void success(List<PhotoGirl> data) {
        super.success(data);

    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
