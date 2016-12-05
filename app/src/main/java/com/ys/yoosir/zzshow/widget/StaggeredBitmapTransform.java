package com.ys.yoosir.zzshow.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.ys.yoosir.zzshow.utils.DimenUtil;

/**
 * @version 1.0
 * @author  yoosir
 * Created by Administrator on 2016/10/26 0026.
 */
public class StaggeredBitmapTransform extends BitmapTransformation{

    private Matrix matrix;

    public StaggeredBitmapTransform(Context context) {
        super(context);
        if(matrix == null){
            matrix  = new Matrix();
        }
    }

    public StaggeredBitmapTransform(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        outWidth = DimenUtil.getScreenSize()/2;
        float scale = ((float) outWidth) / ((float) toTransform.getWidth());
//        outHeight = outWidth *  (toTransform.getHeight() / toTransform.getWidth());
        Log.d("BitmapTransformation","outWidth="+outWidth+";scale="+scale+";width="+toTransform.getWidth()+";height="+toTransform.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(toTransform, 0, 0,
                toTransform.getWidth(), toTransform.getHeight(),
                matrix, true);
//        return Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true);
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }
}
