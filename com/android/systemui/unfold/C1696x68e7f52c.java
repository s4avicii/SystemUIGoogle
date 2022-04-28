package com.android.systemui.unfold;

import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import java.util.function.Function;

/* renamed from: com.android.systemui.unfold.UnfoldTransitionModule$provideStatusBarScopedTransitionProvider$1 */
/* compiled from: UnfoldTransitionModule.kt */
public final class C1696x68e7f52c<T, R> implements Function {
    public static final C1696x68e7f52c<T, R> INSTANCE = new C1696x68e7f52c<>();

    public final Object apply(Object obj) {
        return new ScopedUnfoldTransitionProgressProvider((NaturalRotationUnfoldProgressProvider) obj);
    }
}
