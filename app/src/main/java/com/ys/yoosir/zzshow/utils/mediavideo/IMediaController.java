package com.ys.yoosir.zzshow.utils.mediavideo;

import android.view.View;
import android.widget.MediaController;

/**
 *  控制接口
 * Created by Yoosir on 2016/10/26 0026.
 */
public interface IMediaController {

    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean enabled);

    void setMediaPlayer(MediaController.MediaPlayerControl player);

    void show(int timeout);

    void show();

    //---------
    // Extends
    //---------
    void showOnce(View view);

}
