package com.google.android.systemui.columbus.gates;

import kotlin.jvm.functions.Function0;

/* compiled from: TransientGate.kt */
public final class TransientGate$sam$java_lang_Runnable$0 implements Runnable {
    public final /* synthetic */ Function0 function;

    public TransientGate$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    public final /* synthetic */ void run() {
        this.function.invoke();
    }
}
