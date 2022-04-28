package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.bubbles.BubbleController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$3$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$3$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ BubbleController.C17933 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleController$3$$ExternalSyntheticLambda1(BubbleController.C17933 r1, int i) {
        this.f$0 = r1;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        BubbleController.C17933 r0 = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(r0);
        BubbleController.this.mMainExecutor.execute(new BubbleController$3$$ExternalSyntheticLambda0(r0, (Boolean) obj, i, 0));
    }
}
