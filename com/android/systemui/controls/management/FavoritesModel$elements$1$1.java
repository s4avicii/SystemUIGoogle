package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.CustomIconCache;
import java.util.Objects;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FavoritesModel.kt */
final /* synthetic */ class FavoritesModel$elements$1$1 extends FunctionReferenceImpl implements Function2<ComponentName, String, Icon> {
    public FavoritesModel$elements$1$1(CustomIconCache customIconCache) {
        super(2, customIconCache, CustomIconCache.class, "retrieve", "retrieve(Landroid/content/ComponentName;Ljava/lang/String;)Landroid/graphics/drawable/Icon;", 0);
    }

    public final Object invoke(Object obj, Object obj2) {
        Icon icon;
        String str = (String) obj2;
        CustomIconCache customIconCache = (CustomIconCache) this.receiver;
        Objects.requireNonNull(customIconCache);
        if (!Intrinsics.areEqual((ComponentName) obj, customIconCache.currentComponent)) {
            return null;
        }
        synchronized (customIconCache.cache) {
            icon = (Icon) customIconCache.cache.get(str);
        }
        return icon;
    }
}
