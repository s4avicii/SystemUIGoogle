package com.android.systemui.biometrics;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

public class UdfpsEnrollView extends UdfpsAnimationView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UdfpsEnrollDrawable mFingerprintDrawable = new UdfpsEnrollDrawable(this.mContext);
    public final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    public ImageView mFingerprintProgressView;
    public ImageView mFingerprintView;
    public final Handler mHandler;

    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintProgressDrawable = new UdfpsEnrollProgressBarDrawable(context);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public final void onFinishInflate() {
        this.mFingerprintView = (ImageView) findViewById(C1777R.C1779id.udfps_enroll_animation_fp_view);
        this.mFingerprintProgressView = (ImageView) findViewById(C1777R.C1779id.udfps_enroll_animation_fp_progress_view);
        this.mFingerprintView.setImageDrawable(this.mFingerprintDrawable);
        this.mFingerprintProgressView.setImageDrawable(this.mFingerprintProgressDrawable);
    }

    public final UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }
}
