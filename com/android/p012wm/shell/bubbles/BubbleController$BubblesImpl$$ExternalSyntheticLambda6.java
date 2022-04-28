package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda6(BubbleController.BubblesImpl bubblesImpl, boolean z) {
        this.f$0 = bubblesImpl;
        this.f$1 = z;
    }

    public final void run() {
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(bubblesImpl);
        BubbleController bubbleController = BubbleController.this;
        Objects.requireNonNull(bubbleController);
        bubbleController.mIsStatusBarShade = z;
        if (!z) {
            bubbleController.collapseStack();
        }
        BubbleEntry bubbleEntry = bubbleController.mNotifEntryToExpandOnShadeUnlock;
        if (bubbleEntry != null) {
            bubbleController.expandStackAndSelectBubble(bubbleEntry);
            bubbleController.mNotifEntryToExpandOnShadeUnlock = null;
        }
        bubbleController.updateStack();
    }
}
