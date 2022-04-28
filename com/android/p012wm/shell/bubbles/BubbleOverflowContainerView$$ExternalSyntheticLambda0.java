package com.android.p012wm.shell.bubbles;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleOverflowContainerView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleOverflowContainerView$$ExternalSyntheticLambda0 implements View.OnKeyListener {
    public final /* synthetic */ BubbleOverflowContainerView f$0;

    public /* synthetic */ BubbleOverflowContainerView$$ExternalSyntheticLambda0(BubbleOverflowContainerView bubbleOverflowContainerView) {
        this.f$0 = bubbleOverflowContainerView;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        BubbleOverflowContainerView bubbleOverflowContainerView = this.f$0;
        int i2 = BubbleOverflowContainerView.$r8$clinit;
        Objects.requireNonNull(bubbleOverflowContainerView);
        if (keyEvent.getAction() != 1 || keyEvent.getKeyCode() != 4) {
            return false;
        }
        bubbleOverflowContainerView.mController.collapseStack();
        return true;
    }
}
