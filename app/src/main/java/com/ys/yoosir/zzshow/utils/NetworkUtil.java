package com.ys.yoosir.zzshow.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ys.yoosir.zzshow.MyApplication;
import com.ys.yoosir.zzshow.R;

/**
 *  网络管理类
 * @version 1.0
 * Created by Yoosir on 2016/11/10 0010.
 */
public class NetworkUtil {

    /**
     *  检查当前网络是否可用
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkErrThenShowMsg(){
        if(!isNetworkAvailable()){
            Toast.makeText(MyApplication.getInstance(),
                    MyApplication.getInstance().getString(R.string.network_error),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

}
