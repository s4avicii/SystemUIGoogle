package com.android.p012wm.shell.bubbles;

import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda11 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ BubbleController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Bubble f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda11(BubbleController bubbleController, boolean z, Bubble bubble) {
        this.f$0 = bubbleController;
        this.f$1 = z;
        this.f$2 = bubble;
    }

    public final void accept(Object obj) {
        BubbleController bubbleController = this.f$0;
        boolean z = this.f$1;
        Bubble bubble = this.f$2;
        Objects.requireNonNull(bubbleController);
        bubbleController.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda6(bubbleController, (BubbleEntry) obj, z, bubble, 0));
    }
}
