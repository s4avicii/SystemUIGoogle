package com.android.systemui.dump;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DumpHandler.kt */
public final class DumpHandler$parseArgs$2 extends Lambda implements Function1<String, Integer> {
    public static final DumpHandler$parseArgs$2 INSTANCE = new DumpHandler$parseArgs$2();

    public DumpHandler$parseArgs$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Integer.valueOf(Integer.parseInt((String) obj));
    }
}
