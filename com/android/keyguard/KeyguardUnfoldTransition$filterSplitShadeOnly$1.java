package com.android.keyguard;

import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: KeyguardUnfoldTransition.kt */
public final class KeyguardUnfoldTransition$filterSplitShadeOnly$1 extends Lambda implements Function0<Boolean> {
    public final /* synthetic */ KeyguardUnfoldTransition this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardUnfoldTransition$filterSplitShadeOnly$1(KeyguardUnfoldTransition keyguardUnfoldTransition) {
        super(0);
        this.this$0 = keyguardUnfoldTransition;
    }

    public final Object invoke() {
        KeyguardUnfoldTransition keyguardUnfoldTransition = this.this$0;
        Objects.requireNonNull(keyguardUnfoldTransition);
        return Boolean.valueOf(!keyguardUnfoldTransition.statusViewCentered);
    }
}
