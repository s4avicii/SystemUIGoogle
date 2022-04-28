package com.android.keyguard;

import android.content.Context;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: KeyguardUnfoldTransition.kt */
public final class KeyguardUnfoldTransition {
    public final Context context;
    public final Function0<Boolean> filterNever = KeyguardUnfoldTransition$filterNever$1.INSTANCE;
    public final Function0<Boolean> filterSplitShadeOnly = new KeyguardUnfoldTransition$filterSplitShadeOnly$1(this);
    public boolean statusViewCentered;
    public final Lazy translateAnimator$delegate;

    public KeyguardUnfoldTransition(Context context2, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.context = context2;
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardUnfoldTransition$translateAnimator$2(this, naturalRotationUnfoldProgressProvider));
    }
}
