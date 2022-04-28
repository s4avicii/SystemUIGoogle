package com.google.android.systemui.elmyra.gates;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda4;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.actions.Action;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class KeyguardDeferredSetup extends Gate {
    public boolean mDeferredSetupComplete;
    public final ArrayList mExceptions;
    public final KeyguardVisibility mKeyguardGate;
    public final C22491 mKeyguardGateListener;
    public final UserContentObserver mSettingsObserver;

    public final boolean isBlocked() {
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (((Action) this.mExceptions.get(i)).isAvailable()) {
                return false;
            }
        }
        return !this.mDeferredSetupComplete && this.mKeyguardGate.isBlocking();
    }

    public final void onActivate() {
        this.mKeyguardGate.activate();
        boolean z = false;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_gesture_setup_complete", 0, -2) != 0) {
            z = true;
        }
        this.mDeferredSetupComplete = z;
        this.mSettingsObserver.activate();
    }

    public final void onDeactivate() {
        this.mKeyguardGate.deactivate();
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
        return super.toString() + " [mDeferredSetupComplete -> " + this.mDeferredSetupComplete + "]";
    }

    public KeyguardDeferredSetup(Context context, List<Action> list) {
        super(context);
        C22491 r0 = new Gate.Listener() {
            public final void onGateChanged(Gate gate) {
                KeyguardDeferredSetup.this.notifyListener();
            }
        };
        this.mKeyguardGateListener = r0;
        this.mExceptions = new ArrayList(list);
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        keyguardVisibility.mListener = r0;
        this.mSettingsObserver = new UserContentObserver(context, Settings.Secure.getUriFor("assist_gesture_setup_complete"), new OverviewProxyService$$ExternalSyntheticLambda4(this, 2), false);
    }
}
