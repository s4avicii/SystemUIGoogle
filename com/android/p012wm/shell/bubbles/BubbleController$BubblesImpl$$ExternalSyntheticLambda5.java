package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.systemui.wmshell.BubblesManager$8$$ExternalSyntheticLambda0;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ Consumer f$1;
    public final /* synthetic */ Executor f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda5(BubbleController.BubblesImpl bubblesImpl, BubblesManager$8$$ExternalSyntheticLambda0 bubblesManager$8$$ExternalSyntheticLambda0, Executor executor, String str) {
        this.f$0 = bubblesImpl;
        this.f$1 = bubblesManager$8$$ExternalSyntheticLambda0;
        this.f$2 = executor;
        this.f$3 = str;
    }

    public final void run() {
        BubbleController$BubblesImpl$$ExternalSyntheticLambda9 bubbleController$BubblesImpl$$ExternalSyntheticLambda9;
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        Consumer consumer = this.f$1;
        Executor executor = this.f$2;
        String str = this.f$3;
        if (consumer != null) {
            Objects.requireNonNull(bubblesImpl);
            bubbleController$BubblesImpl$$ExternalSyntheticLambda9 = new BubbleController$BubblesImpl$$ExternalSyntheticLambda9(executor, consumer);
        } else {
            bubbleController$BubblesImpl$$ExternalSyntheticLambda9 = null;
        }
        BubbleController bubbleController = BubbleController.this;
        Objects.requireNonNull(bubbleController);
        if (bubbleController.mBubbleData.isSummarySuppressed(str)) {
            BubbleData bubbleData = bubbleController.mBubbleData;
            Objects.requireNonNull(bubbleData);
            bubbleData.mSuppressedGroupKeys.remove(str);
            BubbleData.Update update = bubbleData.mStateChange;
            update.suppressedSummaryChanged = true;
            update.suppressedSummaryGroup = str;
            bubbleData.dispatchPendingChanges();
            if (bubbleController$BubblesImpl$$ExternalSyntheticLambda9 != null) {
                BubbleData bubbleData2 = bubbleController.mBubbleData;
                Objects.requireNonNull(bubbleData2);
                bubbleController$BubblesImpl$$ExternalSyntheticLambda9.accept(bubbleData2.mSuppressedGroupKeys.get(str));
            }
        }
    }
}
