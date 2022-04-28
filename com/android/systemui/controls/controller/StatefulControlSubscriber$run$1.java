package com.android.systemui.controls.controller;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: StatefulControlSubscriber.kt */
public final class StatefulControlSubscriber$run$1 implements Runnable {

    /* renamed from: $f */
    public final /* synthetic */ Function0<Unit> f46$f;

    public StatefulControlSubscriber$run$1(Function0<Unit> function0) {
        this.f46$f = function0;
    }

    public final void run() {
        this.f46$f.invoke();
    }
}
