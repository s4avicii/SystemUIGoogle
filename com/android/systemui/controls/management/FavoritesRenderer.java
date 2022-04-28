package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.res.Resources;
import kotlin.jvm.functions.Function1;

/* compiled from: AppAdapter.kt */
public final class FavoritesRenderer {
    public final Function1<ComponentName, Integer> favoriteFunction;
    public final Resources resources;

    public FavoritesRenderer(Resources resources2, Function1<? super ComponentName, Integer> function1) {
        this.resources = resources2;
        this.favoriteFunction = function1;
    }
}
