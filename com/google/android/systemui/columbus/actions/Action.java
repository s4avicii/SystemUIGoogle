package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: Action.kt */
public abstract class Action {
    public final Context context;
    public final Set<FeedbackEffect> feedbackEffects;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public boolean isAvailable = true;
    public final LinkedHashSet listeners = new LinkedHashSet();

    /* compiled from: Action.kt */
    public interface Listener {
        void onActionAvailabilityChanged(Action action);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public abstract String mo19228xa00bbd41();

    public abstract void onTrigger(GestureSensor.DetectionProperties detectionProperties);

    public final void setAvailable(boolean z) {
        if (this.isAvailable != z) {
            this.isAvailable = z;
            for (Listener action$setAvailable$1$1 : this.listeners) {
                this.handler.post(new Action$setAvailable$1$1(action$setAvailable$1$1, this));
            }
            if (!this.isAvailable) {
                this.handler.post(new Action$setAvailable$2(this));
            }
        }
    }

    public void updateFeedbackEffects(int i, GestureSensor.DetectionProperties detectionProperties) {
        Set<FeedbackEffect> set = this.feedbackEffects;
        if (set != null) {
            for (FeedbackEffect onGestureDetected : set) {
                onGestureDetected.onGestureDetected(i, detectionProperties);
            }
        }
    }

    public Action(Context context2, Set<? extends FeedbackEffect> set) {
        this.context = context2;
        this.feedbackEffects = set;
    }

    public void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        updateFeedbackEffects(i, detectionProperties);
        if (i == 1) {
            Log.i(mo19228xa00bbd41(), "Triggering");
            onTrigger(detectionProperties);
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
