package com.google.android.setupdesign.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.google.android.setupdesign.R$styleable;
import java.io.IOException;
import java.util.Map;

@TargetApi(14)
public class IllustrationVideoView extends TextureView implements Animatable, TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener {
    public float aspectRatio = 1.0f;
    public boolean isMediaPlayerLoading = false;
    public MediaPlayer mediaPlayer;
    public boolean prepared;
    public boolean shouldPauseVideoWhenFinished = true;
    public Surface surface;
    public int videoResId = 0;
    public String videoResPackageName;
    public int visibility = 0;

    public final boolean onInfo(MediaPlayer mediaPlayer2, int i, int i2) {
        if (i == 3) {
            this.isMediaPlayerLoading = false;
            setVisibility(this.visibility);
        }
        return false;
    }

    public final void onPrepared(MediaPlayer mediaPlayer2) {
        float f;
        this.prepared = true;
        mediaPlayer2.setLooping(true);
        if (mediaPlayer2.getVideoWidth() <= 0 || mediaPlayer2.getVideoHeight() <= 0) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unexpected video size=");
            m.append(mediaPlayer2.getVideoWidth());
            m.append("x");
            m.append(mediaPlayer2.getVideoHeight());
            Log.w("IllustrationVideoView", m.toString());
            f = 0.0f;
        } else {
            f = ((float) mediaPlayer2.getVideoHeight()) / ((float) mediaPlayer2.getVideoWidth());
        }
        if (Float.compare(this.aspectRatio, f) != 0) {
            this.aspectRatio = f;
            requestLayout();
        }
        if (getWindowVisibility() == 0) {
            start();
        }
    }

    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.isMediaPlayerLoading = true;
        setVisibility(this.visibility);
        initVideo();
    }

    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public final void createMediaPlayer() {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.release();
        }
        if (this.surface != null && this.videoResId != 0) {
            MediaPlayer mediaPlayer3 = new MediaPlayer();
            this.mediaPlayer = mediaPlayer3;
            mediaPlayer3.setSurface(this.surface);
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnInfoListener(this);
            this.mediaPlayer.setOnErrorListener(this);
            int i = this.videoResId;
            String str = this.videoResPackageName;
            try {
                this.mediaPlayer.setDataSource(getContext(), Uri.parse("android.resource://" + str + "/" + i), (Map) null);
                this.mediaPlayer.prepareAsync();
            } catch (IOException e) {
                Log.e("IllustrationVideoView", "Unable to set video data source: " + i, e);
            }
        }
    }

    public final boolean isRunning() {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 == null || !mediaPlayer2.isPlaying()) {
            return false;
        }
        return true;
    }

    public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
        Log.w("IllustrationVideoView", "MediaPlayer error. what=" + i + " extra=" + i2);
        return false;
    }

    public final void onSeekComplete(MediaPlayer mediaPlayer2) {
        if (this.prepared) {
            mediaPlayer2.start();
        } else {
            Log.e("IllustrationVideoView", "Seek complete but media player not prepared");
        }
    }

    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.release();
            this.mediaPlayer = null;
            this.prepared = false;
        }
        Surface surface2 = this.surface;
        if (surface2 == null) {
            return true;
        }
        surface2.release();
        this.surface = null;
        return true;
    }

    public final void setVisibility(int i) {
        this.visibility = i;
        if (this.isMediaPlayerLoading && i == 0) {
            i = 4;
        }
        super.setVisibility(i);
    }

    public final void start() {
        MediaPlayer mediaPlayer2;
        if (this.prepared && (mediaPlayer2 = this.mediaPlayer) != null && !mediaPlayer2.isPlaying()) {
            this.mediaPlayer.start();
        }
    }

    public final void stop() {
        MediaPlayer mediaPlayer2;
        if (this.shouldPauseVideoWhenFinished && this.prepared && (mediaPlayer2 = this.mediaPlayer) != null) {
            mediaPlayer2.pause();
        }
    }

    public IllustrationVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (!isInEditMode()) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIllustrationVideoView);
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            this.shouldPauseVideoWhenFinished = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
            String packageName = getContext().getPackageName();
            if (resourceId != this.videoResId || (packageName != null && !packageName.equals(this.videoResPackageName))) {
                this.videoResId = resourceId;
                this.videoResPackageName = packageName;
                createMediaPlayer();
            }
            setScaleX(0.9999999f);
            setScaleX(0.9999999f);
            setSurfaceTextureListener(this);
        }
    }

    public final void initVideo() {
        if (getWindowVisibility() == 0) {
            Surface surface2 = this.surface;
            if (surface2 != null) {
                surface2.release();
                this.surface = null;
            }
            SurfaceTexture surfaceTexture = getSurfaceTexture();
            if (surfaceTexture != null) {
                this.isMediaPlayerLoading = true;
                setVisibility(this.visibility);
                this.surface = new Surface(surfaceTexture);
            }
            if (this.surface != null) {
                createMediaPlayer();
            } else {
                Log.i("IllustrationVideoView", "Surface is null");
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        float f = (float) size2;
        float f2 = (float) size;
        float f3 = this.aspectRatio;
        if (f < f2 * f3) {
            size = (int) (f / f3);
        } else {
            size2 = (int) (f2 * f3);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            start();
        } else {
            stop();
        }
    }

    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            MediaPlayer mediaPlayer2 = this.mediaPlayer;
            if (mediaPlayer2 != null) {
                mediaPlayer2.release();
                this.mediaPlayer = null;
                this.prepared = false;
            }
            Surface surface2 = this.surface;
            if (surface2 != null) {
                surface2.release();
                this.surface = null;
            }
        } else if (this.surface == null) {
            initVideo();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
}
