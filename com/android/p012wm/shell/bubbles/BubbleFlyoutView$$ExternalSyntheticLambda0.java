package com.android.p012wm.shell.bubbles;

import android.graphics.PointF;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleFlyoutView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleFlyoutView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ BubbleFlyoutView f$0;
    public final /* synthetic */ PointF f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubbleFlyoutView$$ExternalSyntheticLambda0(BubbleFlyoutView bubbleFlyoutView, PointF pointF, boolean z) {
        this.f$0 = bubbleFlyoutView;
        this.f$1 = pointF;
        this.f$2 = z;
    }

    public final void run() {
        BubbleFlyoutView bubbleFlyoutView = this.f$0;
        PointF pointF = this.f$1;
        boolean z = this.f$2;
        Objects.requireNonNull(bubbleFlyoutView);
        bubbleFlyoutView.fade(true, pointF, z, BubbleFlyoutView$$ExternalSyntheticLambda2.INSTANCE);
    }
}
