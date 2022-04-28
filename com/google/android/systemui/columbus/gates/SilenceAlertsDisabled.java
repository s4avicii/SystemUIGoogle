package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.provider.Settings;
import com.google.android.systemui.columbus.ColumbusSettings;
import java.util.Objects;

/* compiled from: SilenceAlertsDisabled.kt */
public final class SilenceAlertsDisabled extends Gate {
    public final ColumbusSettings columbusSettings;
    public final SilenceAlertsDisabled$settingsChangeListener$1 settingsChangeListener = new SilenceAlertsDisabled$settingsChangeListener$1(this);

    public final void onActivate() {
        boolean z;
        this.columbusSettings.registerColumbusSettingsChangeListener(this.settingsChangeListener);
        ColumbusSettings columbusSettings2 = this.columbusSettings;
        Objects.requireNonNull(columbusSettings2);
        if (Settings.Secure.getIntForUser(columbusSettings2.contentResolver, "columbus_silence_alerts", 1, columbusSettings2.userTracker.getUserId()) != 0) {
            z = true;
        } else {
            z = false;
        }
        setBlocking(!z);
    }

    public final void onDeactivate() {
        ColumbusSettings columbusSettings2 = this.columbusSettings;
        SilenceAlertsDisabled$settingsChangeListener$1 silenceAlertsDisabled$settingsChangeListener$1 = this.settingsChangeListener;
        Objects.requireNonNull(columbusSettings2);
        columbusSettings2.listeners.remove(silenceAlertsDisabled$settingsChangeListener$1);
    }

    public SilenceAlertsDisabled(Context context, ColumbusSettings columbusSettings2) {
        super(context);
        this.columbusSettings = columbusSettings2;
    }
}
