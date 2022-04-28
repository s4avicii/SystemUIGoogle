package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction extends Action {
    public UserAction currentAction;
    public final UserSelectedAction$keyguardMonitorCallback$1 keyguardMonitorCallback = new UserSelectedAction$keyguardMonitorCallback$1(this);
    public final KeyguardStateController keyguardStateController;
    public final PowerManagerWrapper powerManager;
    public final TakeScreenshot takeScreenshot;
    public final Map<String, UserAction> userSelectedActions;
    public final UserSelectedAction$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new UserSelectedAction$wakefulnessLifecycleObserver$1(this);

    public UserSelectedAction(Context context, ColumbusSettings columbusSettings, Map<String, UserAction> map, TakeScreenshot takeScreenshot2, KeyguardStateController keyguardStateController2, PowerManagerWrapper powerManagerWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        super(context, (Set<? extends FeedbackEffect>) null);
        this.userSelectedActions = map;
        this.takeScreenshot = takeScreenshot2;
        this.keyguardStateController = keyguardStateController2;
        this.powerManager = powerManagerWrapper;
        UserSelectedAction$settingsChangeListener$1 userSelectedAction$settingsChangeListener$1 = new UserSelectedAction$settingsChangeListener$1(this);
        UserAction orDefault = map.getOrDefault(columbusSettings.selectedAction(), takeScreenshot2);
        this.currentAction = orDefault;
        Log.i("Columbus/SelectedAction", Intrinsics.stringPlus("User Action selected: ", orDefault));
        columbusSettings.registerColumbusSettingsChangeListener(userSelectedAction$settingsChangeListener$1);
        UserSelectedAction$sublistener$1 userSelectedAction$sublistener$1 = new UserSelectedAction$sublistener$1(this);
        for (UserAction userAction : map.values()) {
            Objects.requireNonNull(userAction);
            userAction.listeners.add(userSelectedAction$sublistener$1);
        }
        this.keyguardStateController.addCallback(this.keyguardMonitorCallback);
        wakefulnessLifecycle.addObserver(this.wakefulnessLifecycleObserver);
        updateAvailable();
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.currentAction.mo19228xa00bbd41();
    }

    public final void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.onGestureDetected(i, detectionProperties);
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.onTrigger(detectionProperties);
    }

    public final String toString() {
        return super.toString() + " [currentAction -> " + this.currentAction + ']';
    }

    public final void updateAvailable() {
        Boolean bool;
        UserAction userAction = this.currentAction;
        Objects.requireNonNull(userAction);
        if (!userAction.isAvailable) {
            setAvailable(false);
            return;
        }
        if (!this.currentAction.availableOnScreenOff()) {
            PowerManagerWrapper powerManagerWrapper = this.powerManager;
            Objects.requireNonNull(powerManagerWrapper);
            PowerManager powerManager2 = powerManagerWrapper.powerManager;
            if (powerManager2 == null) {
                bool = null;
            } else {
                bool = Boolean.valueOf(powerManager2.isInteractive());
            }
            if (!Intrinsics.areEqual(bool, Boolean.TRUE)) {
                setAvailable(false);
                return;
            }
        }
        if (this.currentAction.availableOnLockscreen() || !this.keyguardStateController.isShowing()) {
            setAvailable(true);
        } else {
            setAvailable(false);
        }
    }

    public final void updateFeedbackEffects(int i, GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.updateFeedbackEffects(i, detectionProperties);
    }
}
