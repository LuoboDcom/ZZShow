package com.ys.yoosir.zzshow.mvp.presenter;

import com.ys.yoosir.zzshow.apis.PhotoModuleApiImpl;
import com.ys.yoosir.zzshow.apis.common.LoadDataType;
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
    private int mLoadType = LoadDataType.TYPE_FIRST_LOAD;
    private int mSize = 20;
    private int mStartPage;

    public PhotoPresenterImpl(){
        mModuleApi= new PhotoModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            loadPhotoData();
        }
    }

    @Override
    public void loadPhotoData() {
        beforeRequest();
        mLoadType = LoadDataType.TYPE_FIRST_LOAD;
        mStartPage = 1;
        loadPhotoDataRequest(mStartPage);
    }

    @Override
    public void refreshData() {
        mStartPage = 1;
        mLoadType = LoadDataType.TYPE_REFRESH;
        loadPhotoDataRequest(mStartPage);
    }

    @Override
    public void loadMore() {
        mStartPage++;
        mLoadType = LoadDataType.TYPE_LOAD_MORE;
        loadPhotoDataRequest(mStartPage);
    }

    private void loadPhotoDataRequest(int starPage){
        mSubscription = mModuleApi.getPhotoList(this,mSize,starPage);
    }

    @Override
    public void success(List<PhotoGirl> data) {
        super.success(data);
        mView.updateListView(data,mLoadType);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if(mLoadType == LoadDataType.TYPE_LOAD_MORE){
            mLoadType--;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
