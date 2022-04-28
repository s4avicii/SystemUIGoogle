package com.google.android.systemui.columbus.actions;

import android.util.Log;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ UserSelectedAction this$0;

    public final void onAlertSilenceEnabledChange(boolean z) {
    }

    public final void onColumbusEnabledChange(boolean z) {
    }

    public final void onLowSensitivityChange(boolean z) {
    }

    public final void onSelectedAppChange(String str) {
    }

    public final void onSelectedAppShortcutChange(String str) {
    }

    public final void onUseApSensorChange() {
    }

    public UserSelectedAction$settingsChangeListener$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    public final void onSelectedActionChange(String str) {
        UserSelectedAction userSelectedAction = this.this$0;
        Objects.requireNonNull(userSelectedAction);
        UserAction orDefault = userSelectedAction.userSelectedActions.getOrDefault(str, userSelectedAction.takeScreenshot);
        if (!Intrinsics.areEqual(orDefault, this.this$0.currentAction)) {
            this.this$0.currentAction.onGestureDetected(0, (GestureSensor.DetectionProperties) null);
            this.this$0.currentAction = orDefault;
            Log.i("Columbus/SelectedAction", Intrinsics.stringPlus("User Action selected: ", orDefault));
            this.this$0.updateAvailable();
        }
    }
}
