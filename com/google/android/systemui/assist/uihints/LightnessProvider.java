package com.google.android.systemui.assist.uihints;

import android.os.Handler;
import android.os.Looper;
import android.view.CompositionSamplingListener;
import com.android.systemui.screenshot.SaveImageInBackgroundTask$$ExternalSyntheticLambda0;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;

public final class LightnessProvider implements NgaMessageHandler.CardInfoListener {
    public boolean mCardVisible = false;
    public int mColorMode = 0;
    public final C21821 mColorMonitor = new CompositionSamplingListener(this) {
        public final /* synthetic */ LightnessProvider this$0;

        {
            SaveImageInBackgroundTask$$ExternalSyntheticLambda0 saveImageInBackgroundTask$$ExternalSyntheticLambda0 = SaveImageInBackgroundTask$$ExternalSyntheticLambda0.INSTANCE;
            this.this$0 = r2;
        }

        public final void onSampleCollected(float f) {
            this.this$0.mUiHandler.post(new LightnessProvider$1$$ExternalSyntheticLambda0(this, f));
        }
    };
    public boolean mIsMonitoringColor = false;
    public NgaUiController$$ExternalSyntheticLambda1 mListener;
    public boolean mMuted = false;
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());

    public final void onCardInfo(boolean z, int i, boolean z2, boolean z3) {
        this.mCardVisible = z;
        this.mColorMode = i;
        NgaUiController$$ExternalSyntheticLambda1 ngaUiController$$ExternalSyntheticLambda1 = this.mListener;
        if (ngaUiController$$ExternalSyntheticLambda1 != null && z) {
            if (i == 1) {
                ngaUiController$$ExternalSyntheticLambda1.onLightnessUpdate(0.0f);
            } else if (i == 2) {
                ngaUiController$$ExternalSyntheticLambda1.onLightnessUpdate(1.0f);
            }
        }
    }
}
