package com.android.systemui.unfold;

import java.util.function.Function;

/* compiled from: UnfoldTransitionModule.kt */
public final class UnfoldTransitionModule$providesFoldStateLogger$1<T, R> implements Function {
    public static final UnfoldTransitionModule$providesFoldStateLogger$1<T, R> INSTANCE = new UnfoldTransitionModule$providesFoldStateLogger$1<>();

    public final Object apply(Object obj) {
        return new FoldStateLogger((FoldStateLoggingProvider) obj);
    }
}
