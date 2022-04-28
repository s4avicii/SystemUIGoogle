package com.android.systemui.unfold.util;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import java.util.Objects;

public final class ScaleAwareTransitionProgressProvider_Factory_Impl implements ScaleAwareTransitionProgressProvider.Factory {
    public final C2509ScaleAwareTransitionProgressProvider_Factory delegateFactory;

    public final ScaleAwareTransitionProgressProvider wrap(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        C2509ScaleAwareTransitionProgressProvider_Factory scaleAwareTransitionProgressProvider_Factory = this.delegateFactory;
        Objects.requireNonNull(scaleAwareTransitionProgressProvider_Factory);
        return new ScaleAwareTransitionProgressProvider(unfoldTransitionProgressProvider, scaleAwareTransitionProgressProvider_Factory.contentResolverProvider.get());
    }

    public ScaleAwareTransitionProgressProvider_Factory_Impl(C2509ScaleAwareTransitionProgressProvider_Factory scaleAwareTransitionProgressProvider_Factory) {
        this.delegateFactory = scaleAwareTransitionProgressProvider_Factory;
    }
}
