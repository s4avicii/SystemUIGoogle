package com.android.systemui.scrim;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrimView$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ ScrimView f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ ScrimView$$ExternalSyntheticLambda3(ScrimView scrimView, float f) {
        this.f$0 = scrimView;
        this.f$1 = f;
    }

    public final void run() {
        ScrimView scrimView = this.f$0;
        float f = this.f$1;
        int i = ScrimView.$r8$clinit;
        Objects.requireNonNull(scrimView);
        if (f != scrimView.mViewAlpha) {
            scrimView.mViewAlpha = f;
            scrimView.mDrawable.setAlpha((int) (f * 255.0f));
            Runnable runnable = scrimView.mChangeRunnable;
            if (runnable != null) {
                scrimView.mChangeRunnableExecutor.execute(runnable);
            }
        }
    }
}
