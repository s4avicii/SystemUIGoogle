package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

public class MediaNowPlayingView extends LinearLayout {
    public final ImageView mImage1;
    public final ImageView mImage2;
    public final ImageView mImage3;
    public final ObjectAnimator mObjectAnimator1;
    public final ObjectAnimator mObjectAnimator2;
    public final ObjectAnimator mObjectAnimator3;

    public final void startAnimation() {
        ObjectAnimator objectAnimator = this.mObjectAnimator1;
        if (!objectAnimator.isStarted()) {
            objectAnimator.start();
        }
        ObjectAnimator objectAnimator2 = this.mObjectAnimator2;
        if (!objectAnimator2.isStarted()) {
            objectAnimator2.start();
        }
        ObjectAnimator objectAnimator3 = this.mObjectAnimator3;
        if (!objectAnimator3.isStarted()) {
            objectAnimator3.start();
        }
        this.mImage1.setVisibility(0);
        this.mImage2.setVisibility(0);
        this.mImage3.setVisibility(0);
    }

    public final void stopAnimation() {
        ObjectAnimator objectAnimator = this.mObjectAnimator1;
        ImageView imageView = this.mImage1;
        if (objectAnimator.isStarted()) {
            objectAnimator.cancel();
            imageView.setScaleY(0.083333336f);
        }
        ObjectAnimator objectAnimator2 = this.mObjectAnimator2;
        ImageView imageView2 = this.mImage2;
        if (objectAnimator2.isStarted()) {
            objectAnimator2.cancel();
            imageView2.setScaleY(0.083333336f);
        }
        ObjectAnimator objectAnimator3 = this.mObjectAnimator3;
        ImageView imageView3 = this.mImage3;
        if (objectAnimator3.isStarted()) {
            objectAnimator3.cancel();
            imageView3.setScaleY(0.083333336f);
        }
        this.mImage1.setVisibility(8);
        this.mImage2.setVisibility(8);
        this.mImage3.setVisibility(8);
    }

    public MediaNowPlayingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        LayoutInflater.from(context).inflate(C1777R.layout.lb_playback_now_playing_bars, this, true);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.bar1);
        this.mImage1 = imageView;
        ImageView imageView2 = (ImageView) findViewById(C1777R.C1779id.bar2);
        this.mImage2 = imageView2;
        ImageView imageView3 = (ImageView) findViewById(C1777R.C1779id.bar3);
        this.mImage3 = imageView3;
        imageView.setPivotY((float) imageView.getDrawable().getIntrinsicHeight());
        imageView2.setPivotY((float) imageView2.getDrawable().getIntrinsicHeight());
        imageView3.setPivotY((float) imageView3.getDrawable().getIntrinsicHeight());
        imageView.setScaleY(0.083333336f);
        imageView2.setScaleY(0.083333336f);
        imageView3.setScaleY(0.083333336f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, "scaleY", new float[]{0.41666666f, 0.25f, 0.41666666f, 0.5833333f, 0.75f, 0.8333333f, 0.9166667f, 1.0f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.5f, 0.33333334f, 0.16666667f, 0.33333334f, 0.5f, 0.5833333f, 0.75f, 0.9166667f, 0.75f, 0.5833333f, 0.41666666f, 0.25f, 0.41666666f, 0.6666667f, 0.41666666f, 0.25f, 0.33333334f, 0.41666666f});
        this.mObjectAnimator1 = ofFloat;
        ofFloat.setRepeatCount(-1);
        ofFloat.setDuration(2320);
        ofFloat.setInterpolator(linearInterpolator);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(imageView2, "scaleY", new float[]{1.0f, 0.9166667f, 0.8333333f, 0.9166667f, 1.0f, 0.9166667f, 0.75f, 0.5833333f, 0.75f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.8333333f, 1.0f, 0.9166667f, 0.75f, 0.41666666f, 0.25f, 0.41666666f, 0.6666667f, 0.8333333f, 1.0f, 0.8333333f, 0.75f, 0.6666667f, 1.0f});
        this.mObjectAnimator2 = ofFloat2;
        ofFloat2.setRepeatCount(-1);
        ofFloat2.setDuration(2080);
        ofFloat2.setInterpolator(linearInterpolator);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(imageView3, "scaleY", new float[]{0.6666667f, 0.75f, 0.8333333f, 1.0f, 0.9166667f, 0.75f, 0.5833333f, 0.41666666f, 0.5833333f, 0.6666667f, 0.75f, 1.0f, 0.9166667f, 1.0f, 0.75f, 0.5833333f, 0.75f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.75f, 0.5833333f, 0.41666666f, 0.25f, 0.6666667f});
        this.mObjectAnimator3 = ofFloat3;
        ofFloat3.setRepeatCount(-1);
        ofFloat3.setDuration(2000);
        ofFloat3.setInterpolator(linearInterpolator);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == 0) {
            startAnimation();
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 8) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }
}
