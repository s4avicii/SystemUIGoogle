package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.ColumbusSettings;
import java.util.Objects;

/* compiled from: ColumbusServiceWrapper.kt */
public final class ColumbusServiceWrapper$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ ColumbusServiceWrapper this$0;

    public final void onAlertSilenceEnabledChange(boolean z) {
    }

    public final void onLowSensitivityChange(boolean z) {
    }

    public final void onSelectedActionChange(String str) {
    }

    public final void onSelectedAppChange(String str) {
    }

    public final void onSelectedAppShortcutChange(String str) {
    }

    public final void onUseApSensorChange() {
    }

    public ColumbusServiceWrapper$settingsChangeListener$1(ColumbusServiceWrapper columbusServiceWrapper) {
        this.this$0 = columbusServiceWrapper;
    }

    public final void onColumbusEnabledChange(boolean z) {
        if (z) {
            ColumbusServiceWrapper columbusServiceWrapper = this.this$0;
            Objects.requireNonNull(columbusServiceWrapper);
            ColumbusSettings columbusSettings = columbusServiceWrapper.columbusSettings;
            ColumbusServiceWrapper$settingsChangeListener$1 columbusServiceWrapper$settingsChangeListener$1 = columbusServiceWrapper.settingsChangeListener;
            Objects.requireNonNull(columbusSettings);
            columbusSettings.listeners.remove(columbusServiceWrapper$settingsChangeListener$1);
            columbusServiceWrapper.started = true;
            columbusServiceWrapper.columbusService.get();
        }
    }
}
