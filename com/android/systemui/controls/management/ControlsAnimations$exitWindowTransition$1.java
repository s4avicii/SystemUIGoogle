package com.android.systemui.controls.management;

import android.animation.Animator;
import android.view.View;
import androidx.mediarouter.R$bool;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ControlsAnimations.kt */
final class ControlsAnimations$exitWindowTransition$1 extends Lambda implements Function1<View, Animator> {
    public static final ControlsAnimations$exitWindowTransition$1 INSTANCE = new ControlsAnimations$exitWindowTransition$1();

    public ControlsAnimations$exitWindowTransition$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return R$bool.exitAnimation((View) obj, (Runnable) null);
    }
}
