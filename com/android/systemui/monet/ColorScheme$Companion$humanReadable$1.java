package com.android.systemui.monet;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ColorScheme.kt */
final class ColorScheme$Companion$humanReadable$1 extends Lambda implements Function1<Integer, CharSequence> {
    public static final ColorScheme$Companion$humanReadable$1 INSTANCE = new ColorScheme$Companion$humanReadable$1();

    public ColorScheme$Companion$humanReadable$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("#", Integer.toHexString(((Number) obj).intValue()));
    }
}
