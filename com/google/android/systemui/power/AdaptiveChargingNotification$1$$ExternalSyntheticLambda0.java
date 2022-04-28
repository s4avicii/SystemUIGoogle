package com.google.android.systemui.power;

import android.os.UserHandle;
import androidx.core.app.NotificationCompat$Action;
import androidx.core.app.NotificationCompat$Builder;
import com.android.p012wm.shell.C1777R;
import com.google.android.systemui.adaptivecharging.AdaptiveChargingManager;
import com.google.android.systemui.power.AdaptiveChargingNotification;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AdaptiveChargingNotification$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AdaptiveChargingNotification.C23001 f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ AdaptiveChargingNotification$1$$ExternalSyntheticLambda0(AdaptiveChargingNotification.C23001 r1, String str, int i, boolean z) {
        this.f$0 = r1;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = z;
    }

    public final void run() {
        boolean z;
        boolean z2;
        AdaptiveChargingNotification.C23001 r0 = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        boolean z3 = this.f$3;
        Objects.requireNonNull(r0);
        AdaptiveChargingNotification adaptiveChargingNotification = AdaptiveChargingNotification.this;
        Objects.requireNonNull(adaptiveChargingNotification);
        boolean z4 = AdaptiveChargingManager.DEBUG;
        if ("Active".equals(str) || "Enabled".equals(str)) {
            z = true;
        } else {
            z = false;
        }
        if (!z || i <= 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            adaptiveChargingNotification.cancelNotification();
        } else if (!adaptiveChargingNotification.mWasActive || z3) {
            String formatTimeToFull = adaptiveChargingNotification.mAdaptiveChargingManager.formatTimeToFull(TimeUnit.SECONDS.toMillis((long) (i + 29)) + System.currentTimeMillis());
            NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(adaptiveChargingNotification.mContext);
            notificationCompat$Builder.mShowWhen = false;
            notificationCompat$Builder.mNotification.icon = C1777R.C1778drawable.ic_battery_charging;
            notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(adaptiveChargingNotification.mContext.getString(C1777R.string.adaptive_charging_notify_title));
            notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(adaptiveChargingNotification.mContext.getString(C1777R.string.adaptive_charging_notify_des, new Object[]{formatTimeToFull}));
            notificationCompat$Builder.mActions.add(new NotificationCompat$Action(adaptiveChargingNotification.mContext.getString(C1777R.string.adaptive_charging_notify_turn_off_once), PowerUtils.createNormalChargingIntent(adaptiveChargingNotification.mContext, "PNW.acChargeNormally")));
            PowerUtils.overrideNotificationAppName(adaptiveChargingNotification.mContext, notificationCompat$Builder, 17039658);
            adaptiveChargingNotification.mNotificationManager.notifyAsUser("adaptive_charging", C1777R.string.adaptive_charging_notify_title, notificationCompat$Builder.build(), UserHandle.ALL);
            adaptiveChargingNotification.mWasActive = true;
        }
    }
}
