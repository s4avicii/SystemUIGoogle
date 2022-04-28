package com.android.systemui.p008tv;

import android.content.Context;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.p008tv.DaggerTvGlobalRootComponent;
import java.util.Objects;

/* renamed from: com.android.systemui.tv.TvSystemUIFactory */
public class TvSystemUIFactory extends SystemUIFactory {
    public final GlobalRootComponent buildGlobalRootComponent(Context context) {
        DaggerTvGlobalRootComponent.Builder builder = (DaggerTvGlobalRootComponent.Builder) DaggerTvGlobalRootComponent.builder();
        Objects.requireNonNull(builder);
        Objects.requireNonNull(context);
        builder.context = context;
        return builder.build();
    }
}
