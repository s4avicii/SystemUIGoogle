package com.android.p012wm.shell.bubbles;

import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda26 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda26 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda26(int i) {
        this.f$0 = i;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        Bubble bubble = (Bubble) obj;
        Objects.requireNonNull(bubble);
        BubbleExpandedView bubbleExpandedView = bubble.mExpandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.setLayoutDirection(i);
        }
    }
}
