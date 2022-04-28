package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.AnimatedDialog;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DialogLaunchAnimator.kt */
final class AnimatedDialog$hideDialogIntoView$2 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ Function1<Boolean, Unit> $onAnimationFinished;
    public final /* synthetic */ AnimatedDialog this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AnimatedDialog$hideDialogIntoView$2(AnimatedDialog animatedDialog, Function1<? super Boolean, Unit> function1) {
        super(0);
        this.this$0 = animatedDialog;
        this.$onAnimationFinished = function1;
    }

    public final Object invoke() {
        LaunchableView launchableView;
        AnimatedDialog animatedDialog = this.this$0;
        Objects.requireNonNull(animatedDialog);
        View view = animatedDialog.touchSurface;
        if (view instanceof LaunchableView) {
            launchableView = (LaunchableView) view;
        } else {
            launchableView = null;
        }
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(false);
        }
        AnimatedDialog animatedDialog2 = this.this$0;
        Objects.requireNonNull(animatedDialog2);
        animatedDialog2.touchSurface.setVisibility(0);
        AnimatedDialog animatedDialog3 = this.this$0;
        Objects.requireNonNull(animatedDialog3);
        ViewGroup viewGroup = animatedDialog3.dialogContentWithBackground;
        Intrinsics.checkNotNull(viewGroup);
        viewGroup.setVisibility(4);
        AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener = this.this$0.backgroundLayoutListener;
        if (animatedBoundsLayoutListener != null) {
            viewGroup.removeOnLayoutChangeListener(animatedBoundsLayoutListener);
        }
        final AnimatedDialog animatedDialog4 = this.this$0;
        final Function1<Boolean, Unit> function1 = this.$onAnimationFinished;
        C06651 r1 = new Function0<Unit>() {
            public final Object invoke() {
                function1.invoke(Boolean.TRUE);
                AnimatedDialog animatedDialog = animatedDialog4;
                animatedDialog.onDialogDismissed.invoke(animatedDialog);
                return Unit.INSTANCE;
            }
        };
        Objects.requireNonNull(animatedDialog4);
        if (animatedDialog4.forceDisableSynchronization) {
            r1.invoke();
        } else {
            boolean z = ViewRootSync.forceDisableSynchronization;
            ViewRootSync.synchronizeNextDraw(animatedDialog4.touchSurface, animatedDialog4.getDecorView(), r1);
        }
        return Unit.INSTANCE;
    }
}
