package com.android.systemui.controls.management;

import android.animation.Animator;
import android.view.View;
import androidx.mediarouter.R$bool;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ControlsAnimations.kt */
final class ControlsAnimations$enterWindowTransition$1 extends Lambda implements Function1<View, Animator> {
    public static final ControlsAnimations$enterWindowTransition$1 INSTANCE = new ControlsAnimations$enterWindowTransition$1();

    public ControlsAnimations$enterWindowTransition$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return R$bool.enterAnimation((View) obj);
    }
}
