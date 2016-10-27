package com.ys.yoosir.zzshow.widget.video;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ys.yoosir.zzshow.R;
import com.ys.yoosir.zzshow.utils.mediavideo.CustomMediaController;
import com.ys.yoosir.zzshow.utils.mediavideo.IjkVideoView;

/**
 *  Video Play View
 * Created by Yoosir on 2016/10/26 0026.
 */
public class VideoPlayView extends RelativeLayout {

    private CustomMediaController mMediaController;
    private View playerBtn,view;
    private IjkVideoView mVideoView;
    private Handler handler = new Handler();
    private boolean isPause;

    private View rView;
    private Context mContext;
    private boolean portrait;

    public VideoPlayView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    private void initData() {

    }

    private void initViews() {
        rView = LayoutInflater.from(mContext).inflate(R.layout.)
    }

    private void initActions() {

    }

}
