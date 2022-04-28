package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.actions.Action;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$sublistener$1 implements Action.Listener {
    public final /* synthetic */ UserSelectedAction this$0;

    public UserSelectedAction$sublistener$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    public final void onActionAvailabilityChanged(Action action) {
        if (Intrinsics.areEqual(this.this$0.currentAction, action)) {
            this.this$0.updateAvailable();
        }
    }
}
