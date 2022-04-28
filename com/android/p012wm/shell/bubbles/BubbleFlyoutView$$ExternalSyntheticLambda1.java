package com.android.p012wm.shell.bubbles;

import android.graphics.PointF;
import com.android.p012wm.shell.bubbles.Bubble;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleFlyoutView$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleFlyoutView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ BubbleFlyoutView f$0;
    public final /* synthetic */ Bubble.FlyoutMessage f$1;
    public final /* synthetic */ PointF f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ BubbleFlyoutView$$ExternalSyntheticLambda1(BubbleFlyoutView bubbleFlyoutView, Bubble.FlyoutMessage flyoutMessage, PointF pointF, boolean z) {
        this.f$0 = bubbleFlyoutView;
        this.f$1 = flyoutMessage;
        this.f$2 = pointF;
        this.f$3 = z;
    }

    public final void run() {
        BubbleFlyoutView bubbleFlyoutView = this.f$0;
        Bubble.FlyoutMessage flyoutMessage = this.f$1;
        PointF pointF = this.f$2;
        boolean z = this.f$3;
        Objects.requireNonNull(bubbleFlyoutView);
        bubbleFlyoutView.updateFlyoutMessage(flyoutMessage);
        bubbleFlyoutView.post(new BubbleFlyoutView$$ExternalSyntheticLambda0(bubbleFlyoutView, pointF, z));
    }
}
