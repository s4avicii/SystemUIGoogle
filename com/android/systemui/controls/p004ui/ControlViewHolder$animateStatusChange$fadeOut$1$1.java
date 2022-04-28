package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$animateStatusChange$fadeOut$1$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Function0<Unit> $statusRowUpdater;

    public ControlViewHolder$animateStatusChange$fadeOut$1$1(Function0<Unit> function0) {
        this.$statusRowUpdater = function0;
    }

    public final void onAnimationEnd(Animator animator) {
        this.$statusRowUpdater.invoke();
    }
}
