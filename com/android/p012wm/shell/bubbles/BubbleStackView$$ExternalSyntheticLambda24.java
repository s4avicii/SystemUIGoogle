package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.Bubbles;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda24 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda24 implements Runnable {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ BubbleViewProvider f$1;
    public final /* synthetic */ BubbleViewProvider f$2;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda24(BubbleStackView bubbleStackView, BubbleViewProvider bubbleViewProvider, BubbleViewProvider bubbleViewProvider2) {
        this.f$0 = bubbleStackView;
        this.f$1 = bubbleViewProvider;
        this.f$2 = bubbleViewProvider2;
    }

    public final void run() {
        BubbleStackView bubbleStackView = this.f$0;
        BubbleViewProvider bubbleViewProvider = this.f$1;
        BubbleViewProvider bubbleViewProvider2 = this.f$2;
        if (bubbleViewProvider != null) {
            Objects.requireNonNull(bubbleStackView);
            bubbleViewProvider.setTaskViewVisibility();
        }
        bubbleStackView.updateExpandedBubble();
        bubbleStackView.requestUpdate();
        bubbleStackView.logBubbleEvent(bubbleViewProvider, 4);
        bubbleStackView.logBubbleEvent(bubbleViewProvider2, 3);
        Bubbles.BubbleExpandListener bubbleExpandListener = bubbleStackView.mExpandListener;
        if (!(bubbleExpandListener == null || bubbleViewProvider == null)) {
            bubbleExpandListener.onBubbleExpandChanged(false, bubbleViewProvider.getKey());
        }
        Bubbles.BubbleExpandListener bubbleExpandListener2 = bubbleStackView.mExpandListener;
        if (bubbleExpandListener2 != null && bubbleViewProvider2 != null) {
            bubbleExpandListener2.onBubbleExpandChanged(true, bubbleViewProvider2.getKey());
        }
    }
}
