package com.android.systemui.statusbar.notification;

import android.animation.ValueAnimator;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationDozeHelper$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ Consumer f$0;

    public /* synthetic */ NotificationDozeHelper$$ExternalSyntheticLambda0(ShellCommandHandlerImpl$$ExternalSyntheticLambda2 shellCommandHandlerImpl$$ExternalSyntheticLambda2) {
        this.f$0 = shellCommandHandlerImpl$$ExternalSyntheticLambda2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.accept((Float) valueAnimator.getAnimatedValue());
    }
}
