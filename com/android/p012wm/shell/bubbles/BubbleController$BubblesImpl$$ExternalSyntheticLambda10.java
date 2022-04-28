package com.android.p012wm.shell.bubbles;

import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda10 implements IntConsumer {
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ IntConsumer f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda10(Executor executor, BubblesManager$$ExternalSyntheticLambda1 bubblesManager$$ExternalSyntheticLambda1) {
        this.f$0 = executor;
        this.f$1 = bubblesManager$$ExternalSyntheticLambda1;
    }

    public final void accept(int i) {
        this.f$0.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda8(this.f$1, i));
    }
}
