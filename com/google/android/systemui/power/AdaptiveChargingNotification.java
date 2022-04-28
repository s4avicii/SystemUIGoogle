package com.google.android.systemui.power;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.google.android.systemui.adaptivecharging.AdaptiveChargingManager;
import java.util.Objects;

public final class AdaptiveChargingNotification {
    public final AdaptiveChargingManager mAdaptiveChargingManager;
    @VisibleForTesting
    public boolean mAdaptiveChargingQueryInBackground = true;
    public final Context mContext;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final NotificationManager mNotificationManager;
    @VisibleForTesting
    public boolean mWasActive = false;

    public final void cancelNotification() {
        if (this.mWasActive) {
            this.mNotificationManager.cancelAsUser("adaptive_charging", C1777R.string.adaptive_charging_notify_title, UserHandle.ALL);
            this.mWasActive = false;
        }
    }

    public final void checkAdaptiveChargingStatus(final boolean z) {
        Objects.requireNonNull(this.mAdaptiveChargingManager);
        if (DeviceConfig.getBoolean("adaptive_charging", "adaptive_charging_notification", false)) {
            C23001 r0 = new AdaptiveChargingManager.AdaptiveChargingStatusReceiver() {
                public final void onDestroyInterface() {
                }

                public final void onReceiveStatus(String str, int i) {
                    AdaptiveChargingNotification.this.mHandler.post(new AdaptiveChargingNotification$1$$ExternalSyntheticLambda0(this, str, i, z));
                }
            };
            if (!this.mAdaptiveChargingQueryInBackground) {
                this.mAdaptiveChargingManager.queryStatus(r0);
            } else {
                AsyncTask.execute(new KeyguardPINView$$ExternalSyntheticLambda0(this, r0, 4));
            }
        }
    }

    @VisibleForTesting
    public void resolveBatteryChangedIntent(Intent intent) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (intent.getIntExtra("plugged", 0) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (intent.getIntExtra("status", 1) == 5) {
            z2 = true;
        } else {
            z2 = false;
        }
        int i = -1;
        int intExtra = intent.getIntExtra("level", -1);
        int intExtra2 = intent.getIntExtra("scale", 0);
        if (intExtra2 != 0) {
            i = Math.round((((float) intExtra) / ((float) intExtra2)) * 100.0f);
        }
        if (!z2 && i < 100) {
            z3 = false;
        }
        if (!z || z3) {
            cancelNotification();
        } else {
            checkAdaptiveChargingStatus(false);
        }
    }

    @VisibleForTesting
    public AdaptiveChargingNotification(Context context, AdaptiveChargingManager adaptiveChargingManager) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mAdaptiveChargingManager = adaptiveChargingManager;
    }
}
