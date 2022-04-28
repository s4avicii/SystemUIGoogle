package com.android.systemui.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DialogLaunchAnimator.kt */
public final class DialogLaunchAnimator$showFromView$animatedDialog$1 extends Lambda implements Function1<AnimatedDialog, Unit> {
    public final /* synthetic */ DialogLaunchAnimator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DialogLaunchAnimator$showFromView$animatedDialog$1(DialogLaunchAnimator dialogLaunchAnimator) {
        super(1);
        this.this$0 = dialogLaunchAnimator;
    }

    public final Object invoke(Object obj) {
        this.this$0.openedDialogs.remove((AnimatedDialog) obj);
        return Unit.INSTANCE;
    }
}
