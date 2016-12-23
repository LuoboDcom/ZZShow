package com.ys.yoosir.zzshow.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.ys.yoosir.zzshow.events.AppManagerEvent;
import com.ys.yoosir.zzshow.mvp.ui.activities.base.BaseActivity;
import com.ys.yoosir.zzshow.utils.RxBus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 用于管理所有activity,和在前台的 activity
 * 可以通过直接持有AppManager对象执行对应方法
 * 也可以通过eventbus post事件,远程遥控执行对应方法
 *
 * @version 1.0
 * @author   yoosir
 * Created by Administrator on 2016/12/23 0023.
 */

public class AppManager {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String APP_MANAGER_MESSAGE = "app_manager_message";
    public static final int START_ACTIVITY = 0;
    public static final int SHOW_SNACKBAR = 1;
    public static final int KILL_ALL = 2;
    public static final int APP_EXIT = 3;

    Subscription mSubscription;
    private Application mApplication;
    //管理所有activity
    public List<BaseActivity> mActivityList;
    //当前在前台的activity
    private BaseActivity mCurrentActivity;


    public AppManager(Application application){
        this.mApplication = application;

        mSubscription = RxBus.getInstance().toObservable(AppManagerEvent.class)
                .subscribe(new Action1<AppManagerEvent>() {
                    @Override
                    public void call(AppManagerEvent managerEvent) {
                        switch (managerEvent.getType()){
                            case START_ACTIVITY:
                                break;
                            case SHOW_SNACKBAR:
                                break;
                            case KILL_ALL:
                                break;
                            case APP_EXIT:
                                break;
                        }
                    }
                });
    }

    /**
     * 释放资源
     */
    public void release(){
        if(mSubscription != null && mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
            mSubscription = null;
        }
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
        mApplication = null;
    }

    /**
     * 将在前台的activity保存
     * @param currentActivity
     */
    public void setCurrentActivity(BaseActivity currentActivity){
        this.mCurrentActivity = currentActivity;
    }


    /**
     *  获得当前在前台的activity
     * @return
     */
    public BaseActivity getCurrentActivity(){
        return mCurrentActivity;
    }

    /**
     * 返回一个存储所有未销毁的activity的集合
     * @return
     */
    public List<BaseActivity> getActivityList(){
        if(mActivityList == null){
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 添加Activity的集合
     * @param activity
     */
    public void addActivity(BaseActivity activity){
        if(mActivityList == null){
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class){
            if(!mActivityList.contains(activity)){
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 删除集合里的指定activity
     * @param activity
     */
    public void removeActivity(BaseActivity activity){
        if(mActivityList == null){
            // mActivityList is null
            return;
        }
        synchronized (AppManager.class){
            if(mActivityList.contains(activity)){
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     * @param location
     * @return
     */
    public BaseActivity removeActivity(int location){
        if(mActivityList == null) {
            //mActivity is null
            return null;
        }
        synchronized (AppManager.class){
            if(location > 0 && location < mActivityList.size()){
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     *  关闭指定activity
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass){
        if(mActivityList == null){
            //mActivityList is null
            return;
        }
        for (BaseActivity activity : mActivityList){
            if(activity.getClass().equals(activityClass)){
                activity.finish();
            }
        }
    }

    /**
     * 指定的activity实例是否存活
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(BaseActivity activity){
        if(mActivityList == null){
            //mActivityList is null
            return false;
        }
        return mActivityList.contains(activity);
    }

    /**
     * 指定的activity class 是否存活（一个activity可能有多个实例）
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass){
        if(mActivityList == null){
            //mActivity is null
            return false;
        }
        for (BaseActivity activity : mActivityList){
            if(activity.getClass().equals(activityClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭所有Activity
     */
    public void killAll(){
        Iterator<BaseActivity> iterator = getActivityList().iterator();
        while (iterator.hasNext()){
            iterator.next().finish();
            iterator.remove();
        }
    }

    /**
     *  退出应用程序
     */
    public void AppExit(){
        try{
            killAll();
            if(mActivityList != null){
                mActivityList = null;
            }
            ActivityManager activityManager = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(mApplication.getPackageName());
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
