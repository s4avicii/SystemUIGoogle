package com.android.systemui.statusbar.phone;

import android.graphics.Color;
import android.os.Trace;
import com.android.systemui.dock.DockManager;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4;
import java.util.Objects;

public enum ScrimState {
    UNINITIALIZED;
    
    public boolean mAnimateChange;
    public long mAnimationDuration;
    public float mAodFrontScrimAlpha;
    public float mBehindAlpha;
    public int mBehindTint;
    public boolean mBlankScreen;
    public boolean mClipQsScrim;
    public float mDefaultScrimAlpha;
    public boolean mDisplayRequiresBlanking;
    public DockManager mDockManager;
    public DozeParameters mDozeParameters;
    public float mFrontAlpha;
    public int mFrontTint;
    public boolean mHasBackdrop;
    public boolean mKeyguardFadingAway;
    public long mKeyguardFadingAwayDuration;
    public boolean mLaunchingAffordanceWithPreview;
    public float mNotifAlpha;
    public int mNotifTint;
    public ScrimView mScrimBehind;
    public float mScrimBehindAlphaKeyguard;
    public ScrimView mScrimInFront;
    public boolean mWakeLockScreenSensorActive;
    public boolean mWallpaperSupportsAmbientMode;

    public float getMaxLightRevealScrimAlpha() {
        return 1.0f;
    }

    public boolean isLowPowerState() {
        return this instanceof C15171;
    }

    public void prepare(ScrimState scrimState) {
    }

    public final void updateScrimColor(ScrimView scrimView) {
        String str;
        String str2;
        if (scrimView == this.mScrimInFront) {
            str = "front_scrim_alpha";
        } else {
            str = "back_scrim_alpha";
        }
        Trace.traceCounter(4096, str, (int) 255.0f);
        if (scrimView == this.mScrimInFront) {
            str2 = "front_scrim_tint";
        } else {
            str2 = "back_scrim_tint";
        }
        Trace.traceCounter(4096, str2, Color.alpha(-16777216));
        Objects.requireNonNull(scrimView);
        scrimView.executeOnExecutor(new ScrimView$$ExternalSyntheticLambda4(scrimView, -16777216));
        scrimView.setViewAlpha(1.0f);
    }

    public int getBehindTint() {
        return this.mBehindTint;
    }
}
