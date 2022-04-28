package com.android.systemui.controls.controller;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: StatefulControlSubscriber.kt */
public final class StatefulControlSubscriber$onError$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ String $error;
    public final /* synthetic */ StatefulControlSubscriber this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public StatefulControlSubscriber$onError$1(StatefulControlSubscriber statefulControlSubscriber, String str) {
        super(0);
        this.this$0 = statefulControlSubscriber;
        this.$error = str;
    }

    public final Object invoke() {
        StatefulControlSubscriber statefulControlSubscriber = this.this$0;
        if (statefulControlSubscriber.subscriptionOpen) {
            statefulControlSubscriber.subscriptionOpen = false;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onError receive from '");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0.provider;
            Objects.requireNonNull(controlsProviderLifecycleManager);
            m.append(controlsProviderLifecycleManager.componentName);
            m.append("': ");
            m.append(this.$error);
            Log.e("StatefulControlSubscriber", m.toString());
        }
        return Unit.INSTANCE;
    }
}
