package com.android.systemui.controls.controller;

import android.service.controls.IControlsSubscription;

/* compiled from: StatefulControlSubscriber.kt */
public final class StatefulControlSubscriber$cancel$1 implements Runnable {
    public final /* synthetic */ StatefulControlSubscriber this$0;

    public StatefulControlSubscriber$cancel$1(StatefulControlSubscriber statefulControlSubscriber) {
        this.this$0 = statefulControlSubscriber;
    }

    public final void run() {
        StatefulControlSubscriber statefulControlSubscriber = this.this$0;
        if (statefulControlSubscriber.subscriptionOpen) {
            statefulControlSubscriber.subscriptionOpen = false;
            IControlsSubscription iControlsSubscription = statefulControlSubscriber.subscription;
            if (iControlsSubscription != null) {
                statefulControlSubscriber.provider.cancelSubscription(iControlsSubscription);
            }
            this.this$0.subscription = null;
        }
    }
}
