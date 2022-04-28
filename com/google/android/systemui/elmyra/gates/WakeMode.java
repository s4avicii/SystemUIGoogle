package com.google.android.systemui.elmyra.gates;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import com.google.android.systemui.elmyra.UserContentObserver;
import java.util.Objects;

public final class WakeMode extends PowerState {
    public final UserContentObserver mSettingsObserver;
    public boolean mWakeSettingEnabled;

    public final boolean isBlocked() {
        if (this.mWakeSettingEnabled) {
            return false;
        }
        return super.isBlocked();
    }

    public final void onActivate() {
        boolean z = true;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_gesture_wake_enabled", 1, -2) == 0) {
            z = false;
        }
        this.mWakeSettingEnabled = z;
        this.mSettingsObserver.activate();
    }

    public final void onDeactivate() {
        UserContentObserver userContentObserver = this.mSettingsObserver;
        Objects.requireNonNull(userContentObserver);
        userContentObserver.mContext.getContentResolver().unregisterContentObserver(userContentObserver);
        try {
            ActivityManager.getService().unregisterUserSwitchObserver(userContentObserver.mUserSwitchCallback);
        } catch (RemoteException e) {
            Log.e("Elmyra/UserContentObserver", "Failed to unregister user switch observer", e);
        }
    }

    public final String toString() {
        return super.toString() + " [mWakeSettingEnabled -> " + this.mWakeSettingEnabled + "]";
    }

    public WakeMode(Context context) {
        super(context);
        this.mSettingsObserver = new UserContentObserver(context, Settings.Secure.getUriFor("assist_gesture_wake_enabled"), new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(this, 6), false);
    }
}
