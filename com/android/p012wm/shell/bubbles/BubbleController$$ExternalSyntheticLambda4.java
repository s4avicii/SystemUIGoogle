package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.Bubbles;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda4 implements Bubbles.BubbleExpandListener {
    public final /* synthetic */ Bubbles.BubbleExpandListener f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda4(Bubbles.BubbleExpandListener bubbleExpandListener) {
        this.f$0 = bubbleExpandListener;
    }

    public final void onBubbleExpandChanged(boolean z, String str) {
        Bubbles.BubbleExpandListener bubbleExpandListener = this.f$0;
        if (bubbleExpandListener != null) {
            bubbleExpandListener.onBubbleExpandChanged(z, str);
        }
    }
}
