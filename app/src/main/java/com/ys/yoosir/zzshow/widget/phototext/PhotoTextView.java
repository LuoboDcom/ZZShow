package com.ys.yoosir.zzshow.widget.phototext;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.socks.library.KLog;
import com.ys.yoosir.zzshow.R;

import org.xml.sax.XMLReader;

/**
 * @version 1.0
 * @author yoosir
 * Created by Administrator on 2016/11/19.
 */

public class PhotoTextView extends TextView {

    UrlImageGetter imgGetter;

    public PhotoTextView(Context context) {
        super(context);
    }

    public PhotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhotoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setContainText(String text,boolean useLocalDrawables){
        Log.d("YHtml","-------------setContainText");
        int width = getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin) * 2;
        UrlImageGetter imgGetter = new UrlImageGetter(this,getContext(),width);
        setText(Html.fromHtml(text, imgGetter,null));
        //加这句才能让里面的超链接生效,
        //修复卡机崩溃的问题
//        setLongClickable(false);
//        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     *  解绑ImageGetterSubscription
     */
    public void cancelImageGetterSubscription(){
        if(imgGetter != null){
            try {
                imgGetter.unSubscribe();
            }catch (Exception e){
                KLog.e("解绑 UrlImageGetter Subscription 异常");
            }
        }
    }
}
