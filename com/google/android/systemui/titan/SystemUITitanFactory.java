package com.google.android.systemui.titan;

import android.content.Context;
import com.android.systemui.dagger.GlobalRootComponent;
import com.google.android.systemui.SystemUIGoogleFactory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

/* compiled from: SystemUITitanFactory.kt */
public final class SystemUITitanFactory extends SystemUIGoogleFactory {
    public final GlobalRootComponent buildGlobalRootComponent(Context context) {
        DaggerTitanGlobalRootComponent.Builder builder = (DaggerTitanGlobalRootComponent.Builder) DaggerTitanGlobalRootComponent.builder();
        Objects.requireNonNull(builder);
        builder.context = context;
        return builder.build();
    }
}
