package com.android.p012wm.shell.bubbles;

import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BadgedImageView$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BadgedImageView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ BadgedImageView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Runnable f$2 = null;

    public /* synthetic */ BadgedImageView$$ExternalSyntheticLambda1(BadgedImageView badgedImageView, boolean z) {
        this.f$0 = badgedImageView;
        this.f$1 = z;
    }

    public final void run() {
        float f;
        BadgedImageView badgedImageView = this.f$0;
        boolean z = this.f$1;
        Runnable runnable = this.f$2;
        int i = BadgedImageView.$r8$clinit;
        Objects.requireNonNull(badgedImageView);
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        badgedImageView.mDotScale = f;
        badgedImageView.invalidate();
        badgedImageView.mDotIsAnimating = false;
        if (runnable != null) {
            runnable.run();
        }
    }
}
