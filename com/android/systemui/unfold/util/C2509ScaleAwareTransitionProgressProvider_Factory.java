package com.android.systemui.unfold.util;

import android.content.ContentResolver;
import javax.inject.Provider;

/* renamed from: com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider_Factory  reason: case insensitive filesystem */
public final class C2509ScaleAwareTransitionProgressProvider_Factory {
    public final Provider<ContentResolver> contentResolverProvider;

    public C2509ScaleAwareTransitionProgressProvider_Factory(Provider<ContentResolver> provider) {
        this.contentResolverProvider = provider;
    }
}
