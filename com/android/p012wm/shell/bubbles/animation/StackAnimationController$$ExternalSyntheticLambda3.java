package com.android.p012wm.shell.bubbles.animation;

import android.view.View;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ StackAnimationController f$0;
    public final /* synthetic */ Runnable f$1;
    public final /* synthetic */ View f$2;
    public final /* synthetic */ Runnable f$3;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda3(StackAnimationController stackAnimationController, BubblesManager$5$$ExternalSyntheticLambda2 bubblesManager$5$$ExternalSyntheticLambda2, View view, BubbleStackView$$ExternalSyntheticLambda20 bubbleStackView$$ExternalSyntheticLambda20) {
        this.f$0 = stackAnimationController;
        this.f$1 = bubblesManager$5$$ExternalSyntheticLambda2;
        this.f$2 = view;
        this.f$3 = bubbleStackView$$ExternalSyntheticLambda20;
    }

    public final void run() {
        StackAnimationController stackAnimationController = this.f$0;
        Runnable runnable = this.f$1;
        View view = this.f$2;
        Runnable runnable2 = this.f$3;
        Objects.requireNonNull(stackAnimationController);
        runnable.run();
        stackAnimationController.moveToFinalIndex(view, 0, runnable2);
    }
}
