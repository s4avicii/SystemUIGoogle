package com.google.android.systemui.columbus.actions;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    public final /* synthetic */ UserSelectedAction this$0;

    public UserSelectedAction$wakefulnessLifecycleObserver$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    public final void onFinishedGoingToSleep() {
        this.this$0.updateAvailable();
    }

    public final void onStartedWakingUp() {
        this.this$0.updateAvailable();
    }
}
