package com.ys.yoosir.zzshow.utils.mediavideo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ys.yoosir.zzshow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  视频播放控制器
 * Created by Yoosir on 2016/10/26 0026.
 */
public class CustomMediaController implements IMediaController{

    private static final int SET_VIEW_HIDE = 1;
    private static final int TIME_OUT = 5000;
    private static final int MESSAGE_SHOW_PROGRESS = 2;
    private static final int PAUSE_IMAGE_HIDE = 3;
    private static final int MESSAGE_SEEK_NEW_POSITION = 4;
    private static final int MESSAGE_HIDE_CONTROLL = 5;

    private View view;

    @BindView(R.id.media_controller)
    private View itemView;

    @BindView(R.id.main_video)
    private IjkVideoView videoView;

    @BindView(R.id.loading)
    private ProgressBar progressBar;

    @BindView(R.id.seekBar)
    private SeekBar seekBar;

    @BindView(R.id.all_time)
    private TextView allTime;

    @BindView(R.id.time)
    private TextView time;

    @BindView(R.id.full)
    private ImageView fullIv;

    @BindView(R.id.sound)
    private ImageView soundIv;

    @BindView(R.id.player_btn)
    private ImageView playIv;

    @BindView(R.id.pause_image)
    private ImageView   pauseIv;

    @BindView(R.id.seekTxt)
    private TextView seekTxt;

    @BindView(R.id.brightness_seek)
    private VSeekBar brightnessSeek;

    @BindView(R.id.sound_seek)
    private VSeekBar soundSeek;

    @BindView(R.id.show)
    private RelativeLayout show;

    @BindView(R.id.brightness_layout)
    private LinearLayout brightnessLayout;

    @BindView(R.id.sound_layout)
    private LinearLayout soundLayout;


    private Context context;
    private AudioManager audioManager;

    private boolean isShow;
    private boolean isScroll;
    private boolean isSound;
    private boolean isDragging;
    private boolean isPause;
    private boolean isShowController;

    private PointF  lastPoint;
    private Bitmap      bitmap;
    private GestureDetector detector;

    private int volume = -1;
    private float brightness = -1;
    private long newPosition = 1;
    private int mMaxVolume;
    private long duration;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SET_VIEW_HIDE:
                    isShow = false;
                    itemView.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_PROGRESS:
                    setProgress();
                    if(!isDragging && isShow){
                        msg = obtainMessage(MESSAGE_SHOW_PROGRESS);
                        sendMessageDelayed(msg,1000);
                    }
                    break;
                case PAUSE_IMAGE_HIDE:
                    pauseIv.setVisibility(View.GONE);
                    break;
                case MESSAGE_SEEK_NEW_POSITION:
                    if(newPosition >= 0){
                        videoView.seekTo((int) newPosition);
                        newPosition = -1;
                    }
                    break;
                case MESSAGE_HIDE_CONTROLL:
                    seekTxt.setVisibility(View.GONE);
                    brightnessLayout.setVisibility(View.GONE);
                    soundLayout.setVisibility(View.GONE);
                    break;
            }
        }
    };

    public CustomMediaController(Context context,View view){
        this.view = view;
        ButterKnife.bind(view);
        itemView.setVisibility(View.GONE);
        isShow = false;
        isDragging = false;

        isShowController = true;
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        initAction();
    }

    private void initAction() {
        soundSeek.setEnabled(false);
        brightnessSeek.setEnabled(false);
        isSound = false;

        detector = new GestureDetector(context,new PlayGestureListener());
        mMaxVolume = ((AudioManager)context.getSystemService(Context.AUDIO_SERVICE))
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String string = generateTime((long)(duration * progress * 1.0f / 100));
                time.setText(string);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setProgress();
                isDragging = true;
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
                handler.removeMessages(MESSAGE_SHOW_PROGRESS);
                show();
                handler.removeMessages(SET_VIEW_HIDE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isDragging = false;
                videoView.seekTo((int) (duration * seekBar.getProgress() * 1.0f / 100));
                handler.removeMessages(MESSAGE_SHOW_PROGRESS);
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC,false);
                isDragging = false;
                handler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS,1000);
                show();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(detector.onTouchEvent(event))
                    return true;

                //处理手势结束
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        endGesture();
                        break;
                }
                return false;
            }
        });

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect seekRect = new Rect();
                seekBar.getHitRect(seekRect);
                if((event.getY() >= (seekRect.top - 50))&&(event.getY() <= (seekRect.bottom + 50))){
                    float y = seekRect.top + seekRect.height() / 2;
                    //seekBar only accept relative x;
                    float x = event.getX() - seekRect.left;
                    if(x < 0){
                        x = 0;
                    }else if(x > seekRect.width()){
                        x = seekRect.width();
                    }
                    MotionEvent me = MotionEvent.obtain(event.getDownTime(),
                            event.getEventTime(),
                            event.getAction(),x,y,event.getMetaState());
                    return seekBar.onTouchEvent(me);
                }
                return false;
            }
        });

        //TODO videoView.setOnInfoListener
    }


    @Override
    public void hide() {

    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {

    }

    @Override
    public void show(int timeout) {

    }

    @Override
    public void show() {

    }

    @Override
    public void showOnce(View view) {

    }

    public int getScreenOrientation(Activity activity){
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                && height > width
                || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                && width > height){
            switch (rotation){
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else{
            switch (rotation){
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }
        return orientation;
    }

    public class PlayGestureListener extends GestureDetector.SimpleOnGestureListener{
        private boolean firstTouch;
        private boolean volumeControll;
        private boolean seek;

        @Override
        public boolean onDown(MotionEvent e) {
            firstTouch = true;
            handler.removeMessages(SET_VIEW_HIDE);
            //横屏下拦截事件
            if(getScreenOrientation((Activity) context) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                return true;
            }else{
                return super.onDown(e);
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float x = e1.getX() - e2.getX();
            float y = e1.getY() - e1.getY();
            if(firstTouch){
                seek = Math.abs(distanceX) >= Math.abs(distanceY);
                volumeControll = e1.getX() < view.getMeasuredWidth() * 0.5;
                firstTouch = false;
            }

            if(seek){
                onProgressSlide( -x / view.getWidth(),e1.getX()/view.getWidth());
            }else{
                float percent = y / view.getHeight();
                if(volumeControll){
                    onVolumeSlide(percent);
                }else{
                    onBrightnessSlide(percent);
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void onProgressSlide(float percent,float downPer){
        long position = videoView.getCurrentPosition();
        long duration = videoView.getDuration();
        long deltaMax = Math.min(100 * 1000 , duration - position);
        long delta = (long)(deltaMax * percent);

        newPosition = delta + position;
        if(newPosition > duration){
            newPosition = duration;
        }else if(newPosition <= 0){
            newPosition = 0;
            delta = -position;
        }
        int showDelta = (int) (delta / 1000);
        if(showDelta != 0 ){
            if(seekTxt.getVisibility() == View.GONE ){
                seekTxt.setVisibility(View.VISIBLE);
            }
            String current = generateTime(newPosition);
            seekTxt.setText(current + "/" + allTime.getText());
        }
    }

    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /**
     *  滑动改变声音大小
     * @param percent
     */
    private void onVolumeSlide(float percent){
        if(volume == -1){
            volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if(volume <= 0 ){
                volume = 0;
            }
        }

        int index = (int)(percent * mMaxVolume) + volume;
        if(index > mMaxVolume )
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        //变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,index,0);

        if(soundLayout.getVisibility() == View.GONE)
            soundLayout.setVisibility(View.VISIBLE);

        //变更进度条
        int i = (int) (index * 1.0f / mMaxVolume * 100);
        if(i == 0){
            soundIv.setImageResource(R.mipmap.sound_mult_icon);
        }else{
            soundIv.setImageResource(R.mipmap.sound_open_icon);
        }
        soundSeek.setProgress(i);
    }

    /**
     *  滑动改变亮度
     * @param percent
     */
    private void onBrightnessSlide(float percent){
        if(brightness < 0){
            brightness = ((Activity)context).getWindow().getAttributes().screenBrightness;
            if(brightness <= 0.00f){
                brightness = 0.50f;
            }else if(brightness < 0.01f){
                brightness = 0.01f;
            }
        }
        WindowManager.LayoutParams lpa = ((Activity)context).getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if(lpa.screenBrightness > 1.0f){
            lpa.screenBrightness = 1.0f;
        }else if(lpa.screenBrightness < 0.01f){
            lpa.screenBrightness = 0.01f;
        }

        if(brightnessLayout.getVisibility() == View.GONE){
            brightnessLayout.setVisibility(View.VISIBLE);
        }

        brightnessSeek.setProgress((int)(lpa.screenBrightness * 100));
        ((Activity)context).getWindow().setAttributes(lpa);
    }
}
