package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import java.util.Set;

/* compiled from: UserAction.kt */
public abstract class UserAction extends Action {
    public UserAction(Context context) {
        super(context, (Set<? extends FeedbackEffect>) null);
    }

    public boolean availableOnLockscreen() {
        return this instanceof LaunchApp;
    }

    public boolean availableOnScreenOff() {
        return this instanceof LaunchOpa;
    }

    public UserAction(Context context, Set<? extends FeedbackEffect> set) {
        super(context, set);
    }
}
