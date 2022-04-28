package com.android.systemui.power;

import android.content.DialogInterface;
import android.content.Intent;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda2 implements DialogInterface.OnClickListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda2(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        Objects.requireNonNull(powerNotificationWarnings);
        String string = powerNotificationWarnings.mContext.getString(C1777R.string.high_temp_alarm_help_url);
        Intent intent = new Intent();
        intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.HelpTrampoline");
        intent.putExtra("android.intent.extra.TEXT", string);
        powerNotificationWarnings.mActivityStarter.startActivity(intent, true, (ActivityStarter.Callback) new PowerNotificationWarnings$$ExternalSyntheticLambda8(powerNotificationWarnings));
    }
}
