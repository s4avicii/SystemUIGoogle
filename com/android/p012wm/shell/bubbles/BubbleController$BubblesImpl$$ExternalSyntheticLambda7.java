package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda7(BubbleController.BubblesImpl bubblesImpl, boolean z) {
        this.f$0 = bubblesImpl;
        this.f$1 = z;
    }

    public final void run() {
        boolean z;
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        boolean z2 = this.f$1;
        Objects.requireNonNull(bubblesImpl);
        BubbleController bubbleController = BubbleController.this;
        Objects.requireNonNull(bubbleController);
        BubbleStackView bubbleStackView = bubbleController.mStackView;
        if (bubbleStackView != null) {
            if (z2 || bubbleController.isStackExpanded()) {
                z = false;
            } else {
                z = true;
            }
            bubbleStackView.mTemporarilyInvisible = z;
            bubbleStackView.updateTemporarilyInvisibleAnimation(z);
        }
    }
}
