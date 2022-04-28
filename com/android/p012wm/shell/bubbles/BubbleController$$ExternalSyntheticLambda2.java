package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleViewInfoTask;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda2 implements BubbleViewInfoTask.Callback {
    public final /* synthetic */ BubbleController f$0;
    public final /* synthetic */ Bubble f$1;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda2(BubbleController bubbleController, Bubble bubble) {
        this.f$0 = bubbleController;
        this.f$1 = bubble;
    }

    public final void onBubbleViewsReady(Bubble bubble) {
        BubbleController bubbleController = this.f$0;
        Bubble bubble2 = this.f$1;
        Objects.requireNonNull(bubbleController);
        bubbleController.mBubbleData.overflowBubble(15, bubble2);
    }
}
