package com.android.systemui.power;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerUI;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Objects;

public class PowerNotificationWarnings implements PowerUI.WarningsUI {
    public static final boolean DEBUG = PowerUI.DEBUG;
    public static final String[] SHOWING_STRINGS = {"SHOWING_NOTHING", "SHOWING_WARNING", "SHOWING_SAVER", "SHOWING_INVALID_CHARGER", "SHOWING_AUTO_SAVER_SUGGESTION"};
    public ActivityStarter mActivityStarter;
    public int mBatteryLevel;
    public int mBucket;
    public final Context mContext;
    public BatteryStateSnapshot mCurrentBatterySnapshot;
    public final Handler mHandler;
    public SystemUIDialog mHighTempDialog;
    public boolean mHighTempWarning;
    public boolean mInvalidCharger;
    public final KeyguardManager mKeyguard;
    public final NotificationManager mNoMan;
    public final Intent mOpenBatterySettings = new Intent("android.intent.action.POWER_USAGE_SUMMARY").setFlags(1551892480);
    public boolean mPlaySound;
    public final PowerManager mPowerMan;
    public SystemUIDialog mSaverConfirmation;
    public boolean mShowAutoSaverSuggestion;
    public int mShowing;
    public SystemUIDialog mThermalShutdownDialog;
    public SystemUIDialog mUsbHighTempDialog;
    public boolean mWarning;
    public long mWarningTriggerTimeMs;

    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            CharSequence charSequence;
            String action = intent.getAction();
            Slog.i("PowerUI.Notification", "Received " + action);
            if (action.equals("PNW.batterySettings")) {
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings powerNotificationWarnings = PowerNotificationWarnings.this;
                powerNotificationWarnings.mContext.startActivityAsUser(powerNotificationWarnings.mOpenBatterySettings, UserHandle.CURRENT);
            } else if (action.equals("PNW.startSaver")) {
                PowerNotificationWarnings powerNotificationWarnings2 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings2);
                BatterySaverUtils.setPowerSaveMode(powerNotificationWarnings2.mContext, true, true);
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
            } else if (action.equals("PNW.startSaverConfirmation")) {
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings powerNotificationWarnings3 = PowerNotificationWarnings.this;
                Bundle extras = intent.getExtras();
                Objects.requireNonNull(powerNotificationWarnings3);
                if (powerNotificationWarnings3.mSaverConfirmation == null) {
                    SystemUIDialog systemUIDialog = new SystemUIDialog(powerNotificationWarnings3.mContext);
                    boolean z = extras.getBoolean("extra_confirm_only");
                    int i = extras.getInt("extra_power_save_mode_trigger", 0);
                    int i2 = extras.getInt("extra_power_save_mode_trigger_level", 0);
                    String charSequence2 = powerNotificationWarnings3.mContext.getText(C1777R.string.help_uri_battery_saver_learn_more_link_target).toString();
                    if (TextUtils.isEmpty(charSequence2)) {
                        charSequence = powerNotificationWarnings3.mContext.getText(17039783);
                    } else {
                        SpannableString spannableString = new SpannableString(powerNotificationWarnings3.mContext.getText(17039784));
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
                        for (Annotation annotation : (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class)) {
                            if ("url".equals(annotation.getValue())) {
                                int spanStart = spannableString.getSpanStart(annotation);
                                int spanEnd = spannableString.getSpanEnd(annotation);
                                C09653 r15 = new URLSpan(charSequence2) {
                                    public final void onClick(View view) {
                                        SystemUIDialog systemUIDialog = PowerNotificationWarnings.this.mSaverConfirmation;
                                        if (systemUIDialog != null) {
                                            systemUIDialog.dismiss();
                                        }
                                        PowerNotificationWarnings.this.mContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").setFlags(268435456));
                                        Uri parse = Uri.parse(getURL());
                                        Context context = view.getContext();
                                        Intent flags = new Intent("android.intent.action.VIEW", parse).setFlags(268435456);
                                        try {
                                            context.startActivity(flags);
                                        } catch (ActivityNotFoundException unused) {
                                            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Activity was not found for intent, ");
                                            m.append(flags.toString());
                                            Log.w("PowerUI.Notification", m.toString());
                                        }
                                    }

                                    public final void updateDrawState(TextPaint textPaint) {
                                        super.updateDrawState(textPaint);
                                        textPaint.setUnderlineText(false);
                                    }
                                };
                                spannableStringBuilder.setSpan(r15, spanStart, spanEnd, spannableString.getSpanFlags(r15));
                            }
                        }
                        charSequence = spannableStringBuilder;
                    }
                    systemUIDialog.setMessage(charSequence);
                    if (Objects.equals(Locale.getDefault().getLanguage(), Locale.ENGLISH.getLanguage())) {
                        systemUIDialog.setMessageHyphenationFrequency(0);
                    }
                    systemUIDialog.setMessageMovementMethod(LinkMovementMethod.getInstance());
                    if (z) {
                        systemUIDialog.setTitle(C1777R.string.battery_saver_confirmation_title_generic);
                        systemUIDialog.setPositiveButton(17040061, new PowerNotificationWarnings$$ExternalSyntheticLambda3(powerNotificationWarnings3, i, i2));
                    } else {
                        systemUIDialog.setTitle(C1777R.string.battery_saver_confirmation_title);
                        systemUIDialog.setPositiveButton(C1777R.string.battery_saver_confirmation_ok, new PowerNotificationWarnings$$ExternalSyntheticLambda1(powerNotificationWarnings3));
                        systemUIDialog.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
                    }
                    SystemUIDialog.setShowForAllUsers(systemUIDialog);
                    systemUIDialog.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda6(powerNotificationWarnings3));
                    systemUIDialog.show();
                    powerNotificationWarnings3.mSaverConfirmation = systemUIDialog;
                }
            } else if (action.equals("PNW.dismissedWarning")) {
                PowerNotificationWarnings.this.dismissLowBatteryWarning();
            } else if ("PNW.clickedTempWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
                PowerNotificationWarnings powerNotificationWarnings4 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings4);
                if (powerNotificationWarnings4.mHighTempDialog == null) {
                    SystemUIDialog systemUIDialog2 = new SystemUIDialog(powerNotificationWarnings4.mContext);
                    systemUIDialog2.setIconAttribute(16843605);
                    systemUIDialog2.setTitle(C1777R.string.high_temp_title);
                    systemUIDialog2.setMessage(C1777R.string.high_temp_dialog_message);
                    systemUIDialog2.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
                    SystemUIDialog.setShowForAllUsers(systemUIDialog2);
                    systemUIDialog2.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda4(powerNotificationWarnings4));
                    String string = powerNotificationWarnings4.mContext.getString(C1777R.string.high_temp_dialog_help_url);
                    if (!string.isEmpty()) {
                        systemUIDialog2.setButton(-3, C1777R.string.high_temp_dialog_help_text, new DialogInterface.OnClickListener(string) {
                            public final /* synthetic */ String val$url;

                            {
                                this.val$url = r2;
                            }

                            public final void onClick(DialogInterface dialogInterface, int i) {
                                PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(268435456), true, (ActivityStarter.Callback) new PowerNotificationWarnings$1$$ExternalSyntheticLambda0(this));
                            }
                        }, true);
                    }
                    systemUIDialog2.show();
                    powerNotificationWarnings4.mHighTempDialog = systemUIDialog2;
                }
            } else if ("PNW.dismissedTempWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
            } else if ("PNW.clickedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                PowerNotificationWarnings powerNotificationWarnings5 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings5);
                if (powerNotificationWarnings5.mThermalShutdownDialog == null) {
                    SystemUIDialog systemUIDialog3 = new SystemUIDialog(powerNotificationWarnings5.mContext);
                    systemUIDialog3.setIconAttribute(16843605);
                    systemUIDialog3.setTitle(C1777R.string.thermal_shutdown_title);
                    systemUIDialog3.setMessage(C1777R.string.thermal_shutdown_dialog_message);
                    systemUIDialog3.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
                    SystemUIDialog.setShowForAllUsers(systemUIDialog3);
                    systemUIDialog3.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda5(powerNotificationWarnings5));
                    String string2 = powerNotificationWarnings5.mContext.getString(C1777R.string.thermal_shutdown_dialog_help_url);
                    if (!string2.isEmpty()) {
                        systemUIDialog3.setButton(-3, C1777R.string.thermal_shutdown_dialog_help_text, new DialogInterface.OnClickListener(string2) {
                            public final /* synthetic */ String val$url;

                            {
                                this.val$url = r2;
                            }

                            public final void onClick(DialogInterface dialogInterface, int i) {
                                PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(268435456), true, (ActivityStarter.Callback) new PowerNotificationWarnings$2$$ExternalSyntheticLambda0(this));
                            }
                        }, true);
                    }
                    systemUIDialog3.show();
                    powerNotificationWarnings5.mThermalShutdownDialog = systemUIDialog3;
                }
            } else if ("PNW.dismissedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
            } else if ("PNW.autoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings6 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings6);
                powerNotificationWarnings6.mShowAutoSaverSuggestion = true;
                powerNotificationWarnings6.updateNotification();
            } else if ("PNW.dismissAutoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings7 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings7);
                powerNotificationWarnings7.mShowAutoSaverSuggestion = false;
                powerNotificationWarnings7.updateNotification();
            } else if ("PNW.enableAutoSaver".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings8 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings8);
                powerNotificationWarnings8.mShowAutoSaverSuggestion = false;
                powerNotificationWarnings8.updateNotification();
                PowerNotificationWarnings powerNotificationWarnings9 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings9);
                Intent intent2 = new Intent("com.android.settings.BATTERY_SAVER_SCHEDULE_SETTINGS");
                intent2.setFlags(268468224);
                powerNotificationWarnings9.mActivityStarter.startActivity(intent2, true);
            } else if ("PNW.autoSaverNoThanks".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings10 = PowerNotificationWarnings.this;
                Objects.requireNonNull(powerNotificationWarnings10);
                powerNotificationWarnings10.mShowAutoSaverSuggestion = false;
                powerNotificationWarnings10.updateNotification();
                Settings.Secure.putInt(context.getContentResolver(), "suppress_auto_battery_saver_suggestion", 1);
            }
        }
    }

    public final void showInvalidChargerWarning() {
        this.mInvalidCharger = true;
        updateNotification();
    }

    static {
        new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    }

    public final void dismissHighTemperatureWarning() {
        if (this.mHighTempWarning) {
            dismissHighTemperatureWarningInternal();
        }
    }

    public final void dismissHighTemperatureWarningInternal() {
        this.mNoMan.cancelAsUser("high_temp", 4, UserHandle.ALL);
        this.mHighTempWarning = false;
    }

    public final void dismissInvalidChargerWarning() {
        if (this.mInvalidCharger) {
            Slog.i("PowerUI.Notification", "dismissing invalid charger notification");
        }
        this.mInvalidCharger = false;
        updateNotification();
    }

    public final void dismissLowBatteryNotification() {
        if (this.mWarning) {
            Slog.i("PowerUI.Notification", "dismissing low battery notification");
        }
        this.mWarning = false;
        updateNotification();
    }

    public final void dismissLowBatteryWarning() {
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("dismissing low battery warning: level=");
            m.append(this.mBatteryLevel);
            Slog.d("PowerUI.Notification", m.toString());
        }
        dismissLowBatteryNotification();
    }

    public void dismissThermalShutdownWarning() {
        this.mNoMan.cancelAsUser("high_temp", 39, UserHandle.ALL);
    }

    public final void dump(PrintWriter printWriter) {
        String str;
        String str2;
        String str3;
        printWriter.print("mWarning=");
        printWriter.println(this.mWarning);
        printWriter.print("mPlaySound=");
        printWriter.println(this.mPlaySound);
        printWriter.print("mInvalidCharger=");
        printWriter.println(this.mInvalidCharger);
        printWriter.print("mShowing=");
        printWriter.println(SHOWING_STRINGS[this.mShowing]);
        printWriter.print("mSaverConfirmation=");
        String str4 = "not null";
        if (this.mSaverConfirmation != null) {
            str = str4;
        } else {
            str = null;
        }
        printWriter.println(str);
        printWriter.print("mSaverEnabledConfirmation=");
        printWriter.print("mHighTempWarning=");
        printWriter.println(this.mHighTempWarning);
        printWriter.print("mHighTempDialog=");
        if (this.mHighTempDialog != null) {
            str2 = str4;
        } else {
            str2 = null;
        }
        printWriter.println(str2);
        printWriter.print("mThermalShutdownDialog=");
        if (this.mThermalShutdownDialog != null) {
            str3 = str4;
        } else {
            str3 = null;
        }
        printWriter.println(str3);
        printWriter.print("mUsbHighTempDialog=");
        if (this.mUsbHighTempDialog == null) {
            str4 = null;
        }
        printWriter.println(str4);
    }

    public final PendingIntent pendingBroadcast(String str) {
        return PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent(str).setPackage(this.mContext.getPackageName()).setFlags(268435456), 67108864, UserHandle.CURRENT);
    }

    public final void showHighTemperatureWarning() {
        if (!this.mHighTempWarning) {
            this.mHighTempWarning = true;
            String string = this.mContext.getString(C1777R.string.high_temp_notif_message);
            Notification.Builder color = new Notification.Builder(this.mContext, "ALR").setSmallIcon(C1777R.C1778drawable.ic_device_thermostat_24).setWhen(0).setShowWhen(false).setContentTitle(this.mContext.getString(C1777R.string.high_temp_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(pendingBroadcast("PNW.clickedTempWarning")).setDeleteIntent(pendingBroadcast("PNW.dismissedTempWarning")).setColor(Utils.getColorAttrDefaultColor(this.mContext, 16844099));
            SystemUIApplication.overrideNotificationAppName(this.mContext, color, false);
            this.mNoMan.notifyAsUser("high_temp", 4, color.build(), UserHandle.ALL);
        }
    }

    public final void showThermalShutdownWarning() {
        String string = this.mContext.getString(C1777R.string.thermal_shutdown_message);
        Notification.Builder color = new Notification.Builder(this.mContext, "ALR").setSmallIcon(C1777R.C1778drawable.ic_device_thermostat_24).setWhen(0).setShowWhen(false).setContentTitle(this.mContext.getString(C1777R.string.thermal_shutdown_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(pendingBroadcast("PNW.clickedThermalShutdownWarning")).setDeleteIntent(pendingBroadcast("PNW.dismissedThermalShutdownWarning")).setColor(Utils.getColorAttrDefaultColor(this.mContext, 16844099));
        SystemUIApplication.overrideNotificationAppName(this.mContext, color, false);
        this.mNoMan.notifyAsUser("high_temp", 39, color.build(), UserHandle.ALL);
    }

    public final void showUsbHighTemperatureAlarm() {
        this.mHandler.post(new WMShell$7$$ExternalSyntheticLambda0(this, 2));
    }

    public final void update(int i, int i2) {
        this.mBatteryLevel = i;
        if (i2 >= 0) {
            this.mWarningTriggerTimeMs = 0;
        } else if (i2 < this.mBucket) {
            this.mWarningTriggerTimeMs = System.currentTimeMillis();
        }
        this.mBucket = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0277, code lost:
        if (r5 < r2.severeThresholdMillis) goto L_0x0279;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateNotification() {
        /*
            r18 = this;
            r0 = r18
            boolean r1 = DEBUG
            if (r1 == 0) goto L_0x002f
            java.lang.String r1 = "updateNotification mWarning="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            boolean r2 = r0.mWarning
            r1.append(r2)
            java.lang.String r2 = " mPlaySound="
            r1.append(r2)
            boolean r2 = r0.mPlaySound
            r1.append(r2)
            java.lang.String r2 = " mInvalidCharger="
            r1.append(r2)
            boolean r2 = r0.mInvalidCharger
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "PowerUI.Notification"
            android.util.Slog.d(r2, r1)
        L_0x002f:
            boolean r1 = r0.mInvalidCharger
            r2 = 2131232214(0x7f0805d6, float:1.808053E38)
            r3 = 2
            r4 = 1
            r5 = 0
            java.lang.String r7 = "low_battery"
            r8 = 0
            r9 = 3
            if (r1 == 0) goto L_0x0099
            android.app.Notification$Builder r1 = new android.app.Notification$Builder
            android.content.Context r10 = r0.mContext
            java.lang.String r11 = "ALR"
            r1.<init>(r10, r11)
            android.app.Notification$Builder r1 = r1.setSmallIcon(r2)
            android.app.Notification$Builder r1 = r1.setWhen(r5)
            android.app.Notification$Builder r1 = r1.setShowWhen(r8)
            android.app.Notification$Builder r1 = r1.setOngoing(r4)
            android.content.Context r2 = r0.mContext
            r4 = 2131952479(0x7f13035f, float:1.9541402E38)
            java.lang.String r2 = r2.getString(r4)
            android.app.Notification$Builder r1 = r1.setContentTitle(r2)
            android.content.Context r2 = r0.mContext
            r4 = 2131952478(0x7f13035e, float:1.95414E38)
            java.lang.String r2 = r2.getString(r4)
            android.app.Notification$Builder r1 = r1.setContentText(r2)
            android.content.Context r2 = r0.mContext
            r4 = 17170460(0x106001c, float:2.4611991E-38)
            int r2 = r2.getColor(r4)
            android.app.Notification$Builder r1 = r1.setColor(r2)
            android.content.Context r2 = r0.mContext
            com.android.systemui.SystemUIApplication.overrideNotificationAppName(r2, r1, r8)
            android.app.Notification r1 = r1.build()
            android.app.NotificationManager r2 = r0.mNoMan
            android.os.UserHandle r4 = android.os.UserHandle.ALL
            r2.cancelAsUser(r7, r9, r4)
            android.app.NotificationManager r2 = r0.mNoMan
            android.os.UserHandle r4 = android.os.UserHandle.ALL
            r2.notifyAsUser(r7, r3, r1, r4)
            r0.mShowing = r9
            goto L_0x035e
        L_0x0099:
            boolean r1 = r0.mWarning
            if (r1 == 0) goto L_0x02c4
            java.text.NumberFormat r1 = java.text.NumberFormat.getPercentInstance()
            com.android.systemui.power.BatteryStateSnapshot r10 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.batteryLevel
            double r10 = (double) r10
            r12 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r10 = r10 / r12
            java.lang.String r1 = r1.format(r10)
            android.content.Context r10 = r0.mContext
            r11 = 2131951916(0x7f13012c, float:1.954026E38)
            java.lang.String r10 = r10.getString(r11)
            com.android.systemui.power.BatteryStateSnapshot r11 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r11)
            boolean r11 = r11.isHybrid
            if (r11 == 0) goto L_0x01f1
            android.content.Context r11 = r0.mContext
            com.android.systemui.power.BatteryStateSnapshot r12 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r12)
            long r12 = r12.timeRemainingMillis
            com.android.systemui.power.BatteryStateSnapshot r14 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r14)
            boolean r14 = r14.isBasedOnUsage
            int r15 = com.android.settingslib.utils.PowerUtil.$r8$clinit
            int r5 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x01ed
            long r5 = com.android.settingslib.utils.PowerUtil.SEVEN_MINUTES_MILLIS
            int r5 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r5 > 0) goto L_0x00fa
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 == 0) goto L_0x00ed
            r1 = 2131953012(0x7f130574, float:1.9542483E38)
            java.lang.String r1 = r11.getString(r1)
            goto L_0x017b
        L_0x00ed:
            r5 = 2131953013(0x7f130575, float:1.9542485E38)
            java.lang.Object[] r6 = new java.lang.Object[r4]
            r6[r8] = r1
            java.lang.String r1 = r11.getString(r5, r6)
            goto L_0x017b
        L_0x00fa:
            long r5 = com.android.settingslib.utils.PowerUtil.FIFTEEN_MINUTES_MILLIS
            int r15 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r15 > 0) goto L_0x0125
            double r5 = (double) r5
            android.text.SpannableStringBuilder r5 = com.android.settingslib.utils.StringUtil.formatElapsedTime(r11, r5, r8)
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            if (r6 == 0) goto L_0x0117
            r1 = 2131953015(0x7f130577, float:1.954249E38)
            java.lang.Object[] r6 = new java.lang.Object[r4]
            r6[r8] = r5
            java.lang.String r1 = r11.getString(r1, r6)
            goto L_0x017b
        L_0x0117:
            r6 = 2131953014(0x7f130576, float:1.9542487E38)
            java.lang.Object[] r12 = new java.lang.Object[r3]
            r12[r8] = r5
            r12[r4] = r1
            java.lang.String r1 = r11.getString(r6, r12)
            goto L_0x017b
        L_0x0125:
            long r5 = com.android.settingslib.utils.PowerUtil.TWO_DAYS_MILLIS
            int r5 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x017f
            android.content.res.Resources r5 = r11.getResources()
            android.content.res.Configuration r5 = r5.getConfiguration()
            android.os.LocaleList r5 = r5.getLocales()
            java.util.Locale r5 = r5.get(r8)
            android.icu.text.MeasureFormat$FormatWidth r6 = android.icu.text.MeasureFormat.FormatWidth.SHORT
            android.icu.text.MeasureFormat r5 = android.icu.text.MeasureFormat.getInstance(r5, r6)
            android.icu.util.Measure r6 = new android.icu.util.Measure
            java.lang.Integer r12 = java.lang.Integer.valueOf(r3)
            android.icu.util.TimeUnit r13 = android.icu.util.MeasureUnit.DAY
            r6.<init>(r12, r13)
            boolean r12 = android.text.TextUtils.isEmpty(r1)
            if (r12 == 0) goto L_0x0166
            r1 = 2131953017(0x7f130579, float:1.9542493E38)
            java.lang.Object[] r12 = new java.lang.Object[r4]
            android.icu.util.Measure[] r13 = new android.icu.util.Measure[r4]
            r13[r8] = r6
            java.lang.String r5 = r5.formatMeasures(r13)
            r12[r8] = r5
            java.lang.String r1 = r11.getString(r1, r12)
            goto L_0x017b
        L_0x0166:
            r12 = 2131953016(0x7f130578, float:1.9542491E38)
            java.lang.Object[] r13 = new java.lang.Object[r3]
            android.icu.util.Measure[] r14 = new android.icu.util.Measure[r4]
            r14[r8] = r6
            java.lang.String r5 = r5.formatMeasures(r14)
            r13[r8] = r5
            r13[r4] = r1
            java.lang.String r1 = r11.getString(r12, r13)
        L_0x017b:
            r17 = r7
            goto L_0x0200
        L_0x017f:
            long r5 = com.android.settingslib.utils.PowerUtil.ONE_DAY_MILLIS
            int r5 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            r15 = 2131953009(0x7f130571, float:1.9542477E38)
            r16 = 2131953006(0x7f13056e, float:1.954247E38)
            if (r5 < 0) goto L_0x01bf
            r17 = r7
            long r6 = com.android.settingslib.utils.PowerUtil.ONE_HOUR_MILLIS
            long r6 = com.android.settingslib.utils.PowerUtil.roundTimeToNearestThreshold(r12, r6)
            double r6 = (double) r6
            android.text.SpannableStringBuilder r6 = com.android.settingslib.utils.StringUtil.formatElapsedTime(r11, r6, r4)
            boolean r7 = android.text.TextUtils.isEmpty(r1)
            if (r7 == 0) goto L_0x01ac
            if (r14 == 0) goto L_0x01a3
            r15 = 2131953010(0x7f130572, float:1.9542479E38)
        L_0x01a3:
            java.lang.Object[] r1 = new java.lang.Object[r4]
            r1[r8] = r6
            java.lang.String r1 = r11.getString(r15, r1)
            goto L_0x0200
        L_0x01ac:
            if (r14 == 0) goto L_0x01af
            goto L_0x01b2
        L_0x01af:
            r16 = 2131953005(0x7f13056d, float:1.9542469E38)
        L_0x01b2:
            r5 = r16
            java.lang.Object[] r7 = new java.lang.Object[r3]
            r7[r8] = r6
            r7[r4] = r1
            java.lang.String r1 = r11.getString(r5, r7)
            goto L_0x0200
        L_0x01bf:
            r17 = r7
            double r6 = (double) r12
            android.text.SpannableStringBuilder r6 = com.android.settingslib.utils.StringUtil.formatElapsedTime(r11, r6, r4)
            boolean r7 = android.text.TextUtils.isEmpty(r1)
            if (r7 == 0) goto L_0x01da
            if (r14 == 0) goto L_0x01d1
            r15 = 2131953010(0x7f130572, float:1.9542479E38)
        L_0x01d1:
            java.lang.Object[] r1 = new java.lang.Object[r4]
            r1[r8] = r6
            java.lang.String r1 = r11.getString(r15, r1)
            goto L_0x0200
        L_0x01da:
            if (r14 == 0) goto L_0x01dd
            goto L_0x01e0
        L_0x01dd:
            r16 = 2131953005(0x7f13056d, float:1.9542469E38)
        L_0x01e0:
            r5 = r16
            java.lang.Object[] r7 = new java.lang.Object[r3]
            r7[r8] = r6
            r7[r4] = r1
            java.lang.String r1 = r11.getString(r5, r7)
            goto L_0x0200
        L_0x01ed:
            r17 = r7
            r1 = 0
            goto L_0x0200
        L_0x01f1:
            r17 = r7
            android.content.Context r5 = r0.mContext
            r6 = 2131951915(0x7f13012b, float:1.9540258E38)
            java.lang.Object[] r7 = new java.lang.Object[r4]
            r7[r8] = r1
            java.lang.String r1 = r5.getString(r6, r7)
        L_0x0200:
            android.app.Notification$Builder r5 = new android.app.Notification$Builder
            android.content.Context r6 = r0.mContext
            java.lang.String r7 = "BAT"
            r5.<init>(r6, r7)
            android.app.Notification$Builder r2 = r5.setSmallIcon(r2)
            long r5 = r0.mWarningTriggerTimeMs
            android.app.Notification$Builder r2 = r2.setWhen(r5)
            android.app.Notification$Builder r2 = r2.setShowWhen(r8)
            android.app.Notification$Builder r2 = r2.setContentText(r1)
            android.app.Notification$Builder r2 = r2.setContentTitle(r10)
            android.app.Notification$Builder r2 = r2.setOnlyAlertOnce(r4)
            java.lang.String r5 = "PNW.dismissedWarning"
            android.app.PendingIntent r5 = r0.pendingBroadcast(r5)
            android.app.Notification$Builder r2 = r2.setDeleteIntent(r5)
            android.app.Notification$BigTextStyle r5 = new android.app.Notification$BigTextStyle
            r5.<init>()
            android.app.Notification$BigTextStyle r1 = r5.bigText(r1)
            android.app.Notification$Builder r1 = r2.setStyle(r1)
            android.app.Notification$Builder r1 = r1.setVisibility(r4)
            android.content.Intent r2 = r0.mOpenBatterySettings
            android.content.Context r5 = r0.mContext
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            android.content.ComponentName r2 = r2.resolveActivity(r5)
            if (r2 == 0) goto L_0x024e
            r2 = r4
            goto L_0x024f
        L_0x024e:
            r2 = r8
        L_0x024f:
            if (r2 == 0) goto L_0x025a
            java.lang.String r2 = "PNW.batterySettings"
            android.app.PendingIntent r2 = r0.pendingBroadcast(r2)
            r1.setContentIntent(r2)
        L_0x025a:
            com.android.systemui.power.BatteryStateSnapshot r2 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.isHybrid
            if (r2 == 0) goto L_0x0279
            int r2 = r0.mBucket
            if (r2 < 0) goto L_0x0279
            com.android.systemui.power.BatteryStateSnapshot r2 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r2)
            long r5 = r2.timeRemainingMillis
            com.android.systemui.power.BatteryStateSnapshot r2 = r0.mCurrentBatterySnapshot
            java.util.Objects.requireNonNull(r2)
            long r10 = r2.severeThresholdMillis
            int r2 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r2 >= 0) goto L_0x0285
        L_0x0279:
            android.content.Context r2 = r0.mContext
            r5 = 16844099(0x1010543, float:2.3697333E-38)
            int r2 = com.android.settingslib.Utils.getColorAttrDefaultColor(r2, r5)
            r1.setColor(r2)
        L_0x0285:
            android.os.PowerManager r2 = r0.mPowerMan
            boolean r2 = r2.isPowerSaveMode()
            if (r2 != 0) goto L_0x029f
            android.content.Context r2 = r0.mContext
            r5 = 2131951921(0x7f130131, float:1.954027E38)
            java.lang.String r2 = r2.getString(r5)
            java.lang.String r5 = "PNW.startSaver"
            android.app.PendingIntent r5 = r0.pendingBroadcast(r5)
            r1.addAction(r8, r2, r5)
        L_0x029f:
            boolean r2 = r0.mPlaySound
            r2 = r2 ^ r4
            r1.setOnlyAlertOnce(r2)
            r0.mPlaySound = r8
            android.content.Context r2 = r0.mContext
            com.android.systemui.SystemUIApplication.overrideNotificationAppName(r2, r1, r8)
            android.app.Notification r1 = r1.build()
            android.app.NotificationManager r2 = r0.mNoMan
            android.os.UserHandle r5 = android.os.UserHandle.ALL
            r7 = r17
            r2.cancelAsUser(r7, r3, r5)
            android.app.NotificationManager r2 = r0.mNoMan
            android.os.UserHandle r3 = android.os.UserHandle.ALL
            r2.notifyAsUser(r7, r9, r1, r3)
            r0.mShowing = r4
            goto L_0x035e
        L_0x02c4:
            boolean r1 = r0.mShowAutoSaverSuggestion
            r2 = 49
            java.lang.String r4 = "auto_saver"
            if (r1 == 0) goto L_0x0347
            int r1 = r0.mShowing
            r3 = 4
            if (r1 == r3) goto L_0x0344
            android.content.Context r1 = r0.mContext
            r7 = 2131951898(0x7f13011a, float:1.9540224E38)
            java.lang.String r1 = r1.getString(r7)
            android.app.Notification$Builder r7 = new android.app.Notification$Builder
            android.content.Context r9 = r0.mContext
            java.lang.String r10 = "HNT"
            r7.<init>(r9, r10)
            r9 = 2131232215(0x7f0805d7, float:1.8080533E38)
            android.app.Notification$Builder r7 = r7.setSmallIcon(r9)
            android.app.Notification$Builder r5 = r7.setWhen(r5)
            android.app.Notification$Builder r5 = r5.setShowWhen(r8)
            android.content.Context r6 = r0.mContext
            r7 = 2131951899(0x7f13011b, float:1.9540226E38)
            java.lang.String r6 = r6.getString(r7)
            android.app.Notification$Builder r5 = r5.setContentTitle(r6)
            android.app.Notification$BigTextStyle r6 = new android.app.Notification$BigTextStyle
            r6.<init>()
            android.app.Notification$BigTextStyle r6 = r6.bigText(r1)
            android.app.Notification$Builder r5 = r5.setStyle(r6)
            android.app.Notification$Builder r1 = r5.setContentText(r1)
            java.lang.String r5 = "PNW.enableAutoSaver"
            android.app.PendingIntent r5 = r0.pendingBroadcast(r5)
            r1.setContentIntent(r5)
            java.lang.String r5 = "PNW.dismissAutoSaverSuggestion"
            android.app.PendingIntent r5 = r0.pendingBroadcast(r5)
            r1.setDeleteIntent(r5)
            android.content.Context r5 = r0.mContext
            r6 = 2131952871(0x7f1304e7, float:1.9542197E38)
            java.lang.String r5 = r5.getString(r6)
            java.lang.String r6 = "PNW.autoSaverNoThanks"
            android.app.PendingIntent r6 = r0.pendingBroadcast(r6)
            r1.addAction(r8, r5, r6)
            android.content.Context r5 = r0.mContext
            com.android.systemui.SystemUIApplication.overrideNotificationAppName(r5, r1, r8)
            android.app.Notification r1 = r1.build()
            android.app.NotificationManager r5 = r0.mNoMan
            android.os.UserHandle r6 = android.os.UserHandle.ALL
            r5.notifyAsUser(r4, r2, r1, r6)
        L_0x0344:
            r0.mShowing = r3
            goto L_0x035e
        L_0x0347:
            android.app.NotificationManager r1 = r0.mNoMan
            android.os.UserHandle r5 = android.os.UserHandle.ALL
            r1.cancelAsUser(r7, r3, r5)
            android.app.NotificationManager r1 = r0.mNoMan
            android.os.UserHandle r3 = android.os.UserHandle.ALL
            r1.cancelAsUser(r7, r9, r3)
            android.app.NotificationManager r1 = r0.mNoMan
            android.os.UserHandle r3 = android.os.UserHandle.ALL
            r1.cancelAsUser(r4, r2, r3)
            r0.mShowing = r8
        L_0x035e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.PowerNotificationWarnings.updateNotification():void");
    }

    public PowerNotificationWarnings(Context context, ActivityStarter activityStarter) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        Receiver receiver = new Receiver();
        this.mContext = context;
        this.mNoMan = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mPowerMan = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
        this.mKeyguard = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PNW.batterySettings");
        intentFilter.addAction("PNW.startSaver");
        intentFilter.addAction("PNW.dismissedWarning");
        intentFilter.addAction("PNW.clickedTempWarning");
        intentFilter.addAction("PNW.dismissedTempWarning");
        intentFilter.addAction("PNW.clickedThermalShutdownWarning");
        intentFilter.addAction("PNW.dismissedThermalShutdownWarning");
        intentFilter.addAction("PNW.startSaverConfirmation");
        intentFilter.addAction("PNW.autoSaverSuggestion");
        intentFilter.addAction("PNW.enableAutoSaver");
        intentFilter.addAction("PNW.autoSaverNoThanks");
        intentFilter.addAction("PNW.dismissAutoSaverSuggestion");
        context.registerReceiverAsUser(receiver, UserHandle.ALL, intentFilter, "android.permission.DEVICE_POWER", handler, 2);
        this.mActivityStarter = activityStarter;
    }

    public final void showLowBatteryWarning(boolean z) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("show low battery warning: level=");
        m.append(this.mBatteryLevel);
        m.append(" [");
        m.append(this.mBucket);
        m.append("] playSound=");
        m.append(z);
        Slog.i("PowerUI.Notification", m.toString());
        this.mPlaySound = z;
        this.mWarning = true;
        updateNotification();
    }

    public final void updateSnapshot(BatteryStateSnapshot batteryStateSnapshot) {
        this.mCurrentBatterySnapshot = batteryStateSnapshot;
    }

    public final boolean isInvalidChargerWarningShowing() {
        return this.mInvalidCharger;
    }

    public final void updateLowBatteryWarning() {
        updateNotification();
    }

    public final void userSwitched() {
        updateNotification();
    }
}
