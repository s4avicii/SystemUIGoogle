package com.android.systemui.statusbar.notification;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ViewGroupFadeHelper.kt */
public final class ViewGroupFadeHelper$Companion$visibilityIncluder$1 extends Lambda implements Function1<View, Boolean> {
    public static final ViewGroupFadeHelper$Companion$visibilityIncluder$1 INSTANCE = new ViewGroupFadeHelper$Companion$visibilityIncluder$1();

    public ViewGroupFadeHelper$Companion$visibilityIncluder$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (((View) obj).getVisibility() == 0) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
