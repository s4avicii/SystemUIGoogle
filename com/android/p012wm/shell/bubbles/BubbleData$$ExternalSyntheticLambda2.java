package com.android.p012wm.shell.bubbles;

import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ BubbleData f$0;
    public final /* synthetic */ int f$1 = 13;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda2(BubbleData bubbleData) {
        this.f$0 = bubbleData;
    }

    public final void accept(Object obj) {
        BubbleData bubbleData = this.f$0;
        int i = this.f$1;
        Bubble bubble = (Bubble) obj;
        Objects.requireNonNull(bubbleData);
        Objects.requireNonNull(bubble);
        bubbleData.dismissBubbleWithKey(bubble.mKey, i);
    }
}
