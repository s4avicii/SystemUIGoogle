package com.android.p012wm.shell.bubbles;

import android.widget.FrameLayout;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda27 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda27 implements Consumer {
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ BubbleViewProvider f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda27(BubbleStackView bubbleStackView, BubbleViewProvider bubbleViewProvider) {
        this.f$0 = bubbleStackView;
        this.f$1 = bubbleViewProvider;
    }

    public final void accept(Object obj) {
        int i;
        BubbleStackView bubbleStackView = this.f$0;
        BubbleViewProvider bubbleViewProvider = this.f$1;
        Objects.requireNonNull(bubbleStackView);
        FrameLayout frameLayout = bubbleStackView.mAnimatingOutSurfaceContainer;
        if (((Boolean) obj).booleanValue()) {
            i = 0;
        } else {
            i = 4;
        }
        frameLayout.setVisibility(i);
        bubbleStackView.showNewlySelectedBubble(bubbleViewProvider);
    }
}
