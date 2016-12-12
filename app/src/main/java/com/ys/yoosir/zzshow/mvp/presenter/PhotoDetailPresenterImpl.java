package com.ys.yoosir.zzshow.mvp.presenter;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.mvp.apis.PhotoDetailModuleApiImpl;
import com.ys.yoosir.zzshow.mvp.apis.interfaces.PhotoDetailModuleApi;
import com.ys.yoosir.zzshow.mvp.presenter.interfaces.PhotoDetailPresenter;
import com.ys.yoosir.zzshow.mvp.view.PhotoDetailView;

import java.io.File;
import java.io.IOException;

/**
 *
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/11/30.
 */

public class PhotoDetailPresenterImpl extends BasePresenterImpl<PhotoDetailView,Uri> implements PhotoDetailPresenter {

    private static final int TYPE_SAVE = 1;
    private static final int TYPE_SHARE = 2;
    private static final int TYPE_WALL_PAGE = 3;

    private int mType;

    public PhotoDetailModuleApi<Uri> photoDetailModuleApi;
    public Activity mActivity;

    public PhotoDetailPresenterImpl(Activity activity){
        mActivity = activity;
        photoDetailModuleApi = new PhotoDetailModuleApiImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    @Override
    public void savePhoto(String photoUrl) {
        mType = TYPE_SAVE;
        getPhotoUri(photoUrl);
    }

    @Override
    public void sharePhoto(String photoUrl) {
        mType = TYPE_SHARE;
        getPhotoUri(photoUrl);
    }

    @Override
    public void setWallpaper(String photoUrl) {
        mType = TYPE_WALL_PAGE;
        getPhotoUri(photoUrl);
    }

    private void getPhotoUri(String photoUrl){
        mSubscription = photoDetailModuleApi.saveImageAndGetImageUri(this,photoUrl);
    }

    @Override
    public void success(Uri data) {
        super.success(data);
        switch (mType){
            case TYPE_SAVE:
                mView.showMsg(MyApplication.getInstance().getString(R.string.picture_already_save_to,data.getPath()));
                break;
            case TYPE_SHARE:
                share(data);
                break;
            case TYPE_WALL_PAGE:
                toSetWallPage(data);
                break;
        }
    }

    private void toSetWallPage(Uri data) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mActivity);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            File wallpageFile = new File(data.getPath());
            Uri contentUri = getImageContentUri(mActivity,wallpageFile.getAbsolutePath());
            mActivity.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(contentUri));
        }else{
            try {
                wallpaperManager.setStream(mActivity.getContentResolver().openInputStream(data));
                mView.showMsg(MyApplication.getInstance().getString(R.string.set_wallpaper_success));
            } catch (IOException e) {
                e.printStackTrace();
                mView.showMsg(e.getMessage());
            }
        }
    }

    // http://stackoverflow.com/questions/23207604/get-a-content-uri-from-a-file-uri
    private Uri getImageContentUri(Context context, String absolutePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?",
                new String[]{absolutePath},
                null);
        if(cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,Integer.toString(id));
        } else if (!absolutePath.isEmpty()){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA,absolutePath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        }else {
            return null;
        }
    }

    private void share(Uri data) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,data);
        intent.setType("image/jpeg");
        mActivity.startActivity(Intent.createChooser(intent, MyApplication.getInstance().getString(R.string.share)));
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
