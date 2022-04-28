package com.android.p012wm.shell.bubbles;

import android.view.View;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleOverflowAdapter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleOverflowAdapter$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ BubbleOverflowAdapter f$0;
    public final /* synthetic */ Bubble f$1;

    public /* synthetic */ BubbleOverflowAdapter$$ExternalSyntheticLambda0(BubbleOverflowAdapter bubbleOverflowAdapter, Bubble bubble) {
        this.f$0 = bubbleOverflowAdapter;
        this.f$1 = bubble;
    }

    public final void onClick(View view) {
        BubbleOverflowAdapter bubbleOverflowAdapter = this.f$0;
        Bubble bubble = this.f$1;
        Objects.requireNonNull(bubbleOverflowAdapter);
        bubbleOverflowAdapter.mBubbles.remove(bubble);
        bubbleOverflowAdapter.notifyDataSetChanged();
        bubbleOverflowAdapter.mPromoteBubbleFromOverflow.accept(bubble);
    }
}
