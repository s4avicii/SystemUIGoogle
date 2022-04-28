package com.google.android.systemui.power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.transition.Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;

public final class PowerNotificationWarningsGoogleImpl extends PowerNotificationWarnings {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AdaptiveChargingNotification mAdaptiveChargingNotification;
    public BatteryDefenderNotification mBatteryDefenderNotification;
    public BatteryInfoBroadcast mBatteryInfoBroadcast;
    public final BroadcastDispatcher mBroadcastDispatcher;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x03f4, code lost:
            if (r14.equals("android.intent.action.BATTERY_CHANGED") == false) goto L_0x03f6;
         */
        /* JADX WARNING: Removed duplicated region for block: B:134:0x03f9  */
        /* JADX WARNING: Removed duplicated region for block: B:147:0x0426  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onReceive(android.content.Context r14, android.content.Intent r15) {
            /*
                r13 = this;
                if (r15 == 0) goto L_0x0429
                java.lang.String r14 = r15.getAction()
                if (r14 != 0) goto L_0x000a
                goto L_0x0429
            L_0x000a:
                java.lang.String r14 = "onReceive: "
                java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r14)
                java.lang.String r1 = r15.getAction()
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "PowerNotificationWarningsGoogleImpl"
                android.util.Log.d(r1, r0)
                com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl r0 = com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl.this
                com.google.android.systemui.power.BatteryInfoBroadcast r0 = r0.mBatteryInfoBroadcast
                java.util.Objects.requireNonNull(r0)
                java.lang.String r1 = r15.getAction()
                java.util.Objects.requireNonNull(r1)
                int r2 = r1.hashCode()
                java.lang.String r3 = "android.intent.action.BATTERY_CHANGED"
                r4 = 5
                r5 = 3
                r6 = -1
                r7 = 1
                r8 = 0
                switch(r2) {
                    case -1538406691: goto L_0x0099;
                    case -1530327060: goto L_0x008e;
                    case -612790895: goto L_0x0083;
                    case 545516589: goto L_0x0078;
                    case 579327048: goto L_0x006d;
                    case 1123270207: goto L_0x0062;
                    case 1174571750: goto L_0x0057;
                    case 1244161670: goto L_0x004c;
                    case 1779291251: goto L_0x003e;
                    default: goto L_0x003c;
                }
            L_0x003c:
                goto L_0x00a2
            L_0x003e:
                java.lang.String r2 = "android.os.action.POWER_SAVE_MODE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0048
                goto L_0x00a2
            L_0x0048:
                r2 = 8
                goto L_0x00a3
            L_0x004c:
                java.lang.String r2 = "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0055
                goto L_0x00a2
            L_0x0055:
                r2 = 7
                goto L_0x00a3
            L_0x0057:
                java.lang.String r2 = "android.bluetooth.device.action.ALIAS_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0060
                goto L_0x00a2
            L_0x0060:
                r2 = 6
                goto L_0x00a3
            L_0x0062:
                java.lang.String r2 = "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x006b
                goto L_0x00a2
            L_0x006b:
                r2 = r4
                goto L_0x00a3
            L_0x006d:
                java.lang.String r2 = "android.bluetooth.device.action.BATTERY_LEVEL_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0076
                goto L_0x00a2
            L_0x0076:
                r2 = 4
                goto L_0x00a3
            L_0x0078:
                java.lang.String r2 = "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0081
                goto L_0x00a2
            L_0x0081:
                r2 = r5
                goto L_0x00a3
            L_0x0083:
                java.lang.String r2 = "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x008c
                goto L_0x00a2
            L_0x008c:
                r2 = 2
                goto L_0x00a3
            L_0x008e:
                java.lang.String r2 = "android.bluetooth.adapter.action.STATE_CHANGED"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0097
                goto L_0x00a2
            L_0x0097:
                r2 = r7
                goto L_0x00a3
            L_0x0099:
                boolean r2 = r1.equals(r3)
                if (r2 != 0) goto L_0x00a0
                goto L_0x00a2
            L_0x00a0:
                r2 = r8
                goto L_0x00a3
            L_0x00a2:
                r2 = r6
            L_0x00a3:
                java.lang.String r9 = "com.google.android.settings.intelligence"
                switch(r2) {
                    case 0: goto L_0x00bf;
                    case 1: goto L_0x00a9;
                    case 2: goto L_0x00a9;
                    case 3: goto L_0x00a9;
                    case 4: goto L_0x00a9;
                    case 5: goto L_0x00a9;
                    case 6: goto L_0x00a9;
                    case 7: goto L_0x00a9;
                    case 8: goto L_0x00bf;
                    default: goto L_0x00a8;
                }
            L_0x00a8:
                goto L_0x0103
            L_0x00a9:
                android.content.Intent r14 = new android.content.Intent
                java.lang.String r2 = "PNW.bluetoothStatusChanged"
                r14.<init>(r2)
                android.content.Intent r14 = r14.setPackage(r9)
                r14.putExtra(r1, r15)
                android.content.Context r0 = r0.mContext
                android.os.UserHandle r1 = android.os.UserHandle.ALL
                r0.sendBroadcastAsUser(r14, r1)
                goto L_0x0103
            L_0x00bf:
                android.content.Intent r2 = new android.content.Intent
                java.lang.String r10 = "PNW.batteryStatusChanged"
                r2.<init>(r10)
                android.content.Intent r2 = r2.setPackage(r9)
                boolean r9 = r3.equals(r1)
                if (r9 == 0) goto L_0x00d5
                java.lang.String r9 = "battery_changed_intent"
                r2.putExtra(r9, r15)
            L_0x00d5:
                android.os.PowerManager r9 = r0.mPowerManager
                boolean r9 = r9.isPowerSaveMode()
                java.lang.String r10 = "battery_save"
                r2.putExtra(r10, r9)
                android.content.Context r0 = r0.mContext
                android.os.UserHandle r10 = android.os.UserHandle.ALL
                r0.sendBroadcastAsUser(r2, r10)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                r0.append(r14)
                r0.append(r1)
                java.lang.String r14 = " isPowerSaveMode: "
                r0.append(r14)
                r0.append(r9)
                java.lang.String r14 = r0.toString()
                java.lang.String r0 = "BatteryInfoBroadcast"
                android.util.Log.d(r0, r14)
            L_0x0103:
                com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl r14 = com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl.this
                com.google.android.systemui.power.BatteryDefenderNotification r14 = r14.mBatteryDefenderNotification
                java.util.Objects.requireNonNull(r14)
                java.lang.String r0 = r15.getAction()
                boolean r1 = r3.equals(r0)
                java.lang.String r2 = "PNW.defenderResumeCharging"
                if (r1 == 0) goto L_0x0397
                java.lang.String r0 = "level"
                int r1 = r15.getIntExtra(r0, r6)
                java.lang.String r9 = "scale"
                int r10 = r15.getIntExtra(r9, r8)
                r11 = 1120403456(0x42c80000, float:100.0)
                if (r10 != 0) goto L_0x0129
                r1 = r6
                goto L_0x0131
            L_0x0129:
                float r1 = (float) r1
                float r10 = (float) r10
                float r1 = r1 / r10
                float r1 = r1 * r11
                int r1 = java.lang.Math.round(r1)
            L_0x0131:
                r14.mBatteryLevel = r1
                java.lang.String r1 = "plugged"
                int r1 = r15.getIntExtra(r1, r8)
                if (r1 == 0) goto L_0x013d
                r1 = r7
                goto L_0x013e
            L_0x013d:
                r1 = r8
            L_0x013e:
                java.lang.String r10 = "health"
                int r10 = r15.getIntExtra(r10, r7)
                if (r10 != r5) goto L_0x0148
                r5 = r7
                goto L_0x0149
            L_0x0148:
                r5 = r8
            L_0x0149:
                java.lang.String r10 = "status"
                int r10 = r15.getIntExtra(r10, r7)
                if (r10 != r4) goto L_0x0154
                r4 = r7
                goto L_0x0155
            L_0x0154:
                r4 = r8
            L_0x0155:
                int r0 = r15.getIntExtra(r0, r6)
                int r9 = r15.getIntExtra(r9, r8)
                if (r9 != 0) goto L_0x0160
                goto L_0x0168
            L_0x0160:
                float r0 = (float) r0
                float r6 = (float) r9
                float r0 = r0 / r6
                float r0 = r0 * r11
                int r6 = java.lang.Math.round(r0)
            L_0x0168:
                if (r4 != 0) goto L_0x0170
                r0 = 100
                if (r6 < r0) goto L_0x016f
                goto L_0x0170
            L_0x016f:
                r7 = r8
            L_0x0170:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r4 = "isPlugged: "
                r0.append(r4)
                r0.append(r1)
                java.lang.String r4 = " | isOverheated: "
                r0.append(r4)
                r0.append(r5)
                java.lang.String r4 = " | defenderEnabled: "
                r0.append(r4)
                boolean r4 = r14.mDefenderEnabled
                r0.append(r4)
                java.lang.String r4 = " | isCharged: "
                r0.append(r4)
                r0.append(r7)
                java.lang.String r0 = r0.toString()
                java.lang.String r4 = "BatteryDefenderNotification"
                android.util.Log.d(r4, r0)
                r0 = 2131952267(0x7f13028b, float:1.9540972E38)
                java.lang.String r6 = "battery_defender"
                if (r7 == 0) goto L_0x01c3
                boolean r7 = r14.mPostNotificationVisible
                if (r7 == 0) goto L_0x01c3
                r14.mPostNotificationVisible = r8
                android.content.SharedPreferences r7 = r14.getSharedPreferences()
                android.content.SharedPreferences$Editor r7 = r7.edit()
                android.content.SharedPreferences$Editor r7 = r7.clear()
                r7.apply()
                android.app.NotificationManager r7 = r14.mNotificationManager
                android.os.UserHandle r9 = android.os.UserHandle.ALL
                r7.cancelAsUser(r6, r0, r9)
            L_0x01c3:
                java.lang.String r7 = "trigger_time"
                r9 = 2131952265(0x7f130289, float:1.9540968E38)
                r10 = 17303574(0x1080816, float:2.4985056E-38)
                java.lang.String r11 = "android.intent.action.VIEW"
                r12 = 2131951906(0x7f130122, float:1.954024E38)
                if (r5 == 0) goto L_0x02a5
                boolean r4 = r14.mDefenderEnabled
                if (r4 != 0) goto L_0x020a
                boolean r4 = r14.mPostNotificationVisible
                if (r4 == 0) goto L_0x01f3
                r14.mPostNotificationVisible = r8
                android.content.SharedPreferences r4 = r14.getSharedPreferences()
                android.content.SharedPreferences$Editor r4 = r4.edit()
                android.content.SharedPreferences$Editor r4 = r4.clear()
                r4.apply()
                android.app.NotificationManager r4 = r14.mNotificationManager
                android.os.UserHandle r5 = android.os.UserHandle.ALL
                r4.cancelAsUser(r6, r0, r5)
            L_0x01f3:
                android.content.SharedPreferences r0 = r14.getSharedPreferences()
                android.content.SharedPreferences$Editor r0 = r0.edit()
                java.time.Clock r4 = java.time.Clock.systemUTC()
                long r4 = r4.millis()
                android.content.SharedPreferences$Editor r0 = r0.putLong(r7, r4)
                r0.apply()
            L_0x020a:
                boolean r0 = r14.mDefenderEnabled
                if (r0 == 0) goto L_0x0214
                boolean r0 = r14.mPrvPluggedState
                if (r0 != r1) goto L_0x0214
                goto L_0x0395
            L_0x0214:
                r14.mPrvPluggedState = r1
                androidx.core.app.NotificationCompat$Builder r0 = new androidx.core.app.NotificationCompat$Builder
                android.content.Context r4 = r14.mContext
                r0.<init>(r4)
                android.app.Notification r4 = r0.mNotification
                r4.icon = r10
                android.content.Context r4 = r14.mContext
                java.lang.String r4 = r4.getString(r9)
                java.lang.CharSequence r4 = androidx.core.app.NotificationCompat$Builder.limitCharSequenceLength(r4)
                r0.mContentTitle = r4
                android.content.Context r4 = r14.mContext
                r5 = 2131952262(0x7f130286, float:1.9540962E38)
                java.lang.String r4 = r4.getString(r5)
                java.lang.CharSequence r4 = androidx.core.app.NotificationCompat$Builder.limitCharSequenceLength(r4)
                r0.mContentText = r4
                android.content.Context r4 = r14.mContext
                java.lang.String r4 = r4.getString(r12)
                android.content.Context r5 = r14.mContext
                android.content.Intent r7 = new android.content.Intent
                r8 = 2131952263(0x7f130287, float:1.9540964E38)
                java.lang.String r8 = r5.getString(r8)
                android.net.Uri r8 = android.net.Uri.parse(r8)
                r7.<init>(r11, r8)
                r8 = 67108864(0x4000000, float:1.5046328E-36)
                r10 = 0
                android.app.PendingIntent r5 = android.app.PendingIntent.getActivity(r5, r10, r7, r8)
                java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r7 = r0.mActions
                androidx.core.app.NotificationCompat$Action r8 = new androidx.core.app.NotificationCompat$Action
                r8.<init>(r4, r5)
                r7.add(r8)
                if (r1 == 0) goto L_0x0280
                android.content.Context r1 = r14.mContext
                r4 = 2131952264(0x7f130288, float:1.9540966E38)
                java.lang.String r1 = r1.getString(r4)
                android.content.Context r4 = r14.mContext
                android.app.PendingIntent r2 = com.google.android.systemui.power.PowerUtils.createNormalChargingIntent(r4, r2)
                java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r4 = r0.mActions
                androidx.core.app.NotificationCompat$Action r5 = new androidx.core.app.NotificationCompat$Action
                r5.<init>(r1, r2)
                r4.add(r5)
            L_0x0280:
                android.content.Context r1 = r14.mContext
                r2 = 17040852(0x10405d4, float:2.4248752E-38)
                com.google.android.systemui.power.PowerUtils.overrideNotificationAppName(r1, r0, r2)
                android.app.NotificationManager r1 = r14.mNotificationManager
                android.app.Notification r0 = r0.build()
                android.os.UserHandle r2 = android.os.UserHandle.ALL
                r1.notifyAsUser(r6, r9, r0, r2)
                boolean r0 = r14.mDefenderEnabled
                if (r0 != 0) goto L_0x0395
                r0 = 1
                r14.mDefenderEnabled = r0
                com.android.internal.logging.UiEventLogger r14 = r14.mUiEventLogger
                if (r14 == 0) goto L_0x0395
                com.google.android.systemui.power.BatteryDefenderNotification$BatteryDefenderEvent r0 = com.google.android.systemui.power.BatteryDefenderNotification.BatteryDefenderEvent.BATTERY_DEFENDER_NOTIFICATION
                r14.log(r0)
                goto L_0x0395
            L_0x02a5:
                boolean r1 = r14.mDefenderEnabled
                if (r1 == 0) goto L_0x0395
                r1 = 0
                r14.mDefenderEnabled = r1
                android.app.NotificationManager r1 = r14.mNotificationManager
                android.os.UserHandle r2 = android.os.UserHandle.ALL
                r1.cancelAsUser(r6, r9, r2)
                android.content.SharedPreferences r1 = r14.getSharedPreferences()
                boolean r1 = r1.contains(r7)
                if (r1 == 0) goto L_0x0395
                android.content.SharedPreferences r1 = r14.getSharedPreferences()
                r8 = -1
                long r1 = r1.getLong(r7, r8)
                r7 = 0
                int r5 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                if (r5 <= 0) goto L_0x02d4
                android.content.Context r7 = r14.mContext
                java.lang.String r7 = com.google.android.systemui.power.PowerUtils.getCurrentTime(r7, r1)
                goto L_0x02d5
            L_0x02d4:
                r7 = 0
            L_0x02d5:
                if (r7 == 0) goto L_0x037e
                java.time.Clock r8 = java.time.Clock.systemUTC()
                long r8 = r8.millis()
                long r8 = r8 - r1
                if (r5 <= 0) goto L_0x02eb
                r1 = 600000(0x927c0, double:2.964394E-318)
                int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
                if (r1 < 0) goto L_0x02eb
                r1 = 1
                goto L_0x02ec
            L_0x02eb:
                r1 = 0
            L_0x02ec:
                if (r1 == 0) goto L_0x037e
                android.content.Context r1 = r14.mContext
                java.time.Clock r2 = java.time.Clock.systemUTC()
                long r4 = r2.millis()
                java.lang.String r1 = com.google.android.systemui.power.PowerUtils.getCurrentTime(r1, r4)
                java.text.NumberFormat r2 = java.text.NumberFormat.getPercentInstance()
                r4 = 4605380979056443392(0x3fe99999a0000000, double:0.800000011920929)
                java.lang.String r2 = r2.format(r4)
                android.content.Context r4 = r14.mContext
                r5 = 2131952266(0x7f13028a, float:1.954097E38)
                r8 = 3
                java.lang.Object[] r8 = new java.lang.Object[r8]
                r9 = 0
                r8[r9] = r2
                r2 = 1
                r8[r2] = r7
                r2 = 2
                r8[r2] = r1
                java.lang.String r1 = r4.getString(r5, r8)
                androidx.core.app.NotificationCompat$Builder r2 = new androidx.core.app.NotificationCompat$Builder
                android.content.Context r4 = r14.mContext
                r2.<init>(r4)
                android.app.Notification r4 = r2.mNotification
                r4.icon = r10
                android.content.Context r4 = r14.mContext
                java.lang.String r4 = r4.getString(r0)
                java.lang.CharSequence r4 = androidx.core.app.NotificationCompat$Builder.limitCharSequenceLength(r4)
                r2.mContentTitle = r4
                java.lang.CharSequence r1 = androidx.core.app.NotificationCompat$Builder.limitCharSequenceLength(r1)
                r2.mContentText = r1
                android.content.Context r1 = r14.mContext
                r4 = 2131951906(0x7f130122, float:1.954024E38)
                java.lang.String r1 = r1.getString(r4)
                android.content.Context r4 = r14.mContext
                android.content.Intent r5 = new android.content.Intent
                r7 = 2131952263(0x7f130287, float:1.9540964E38)
                java.lang.String r7 = r4.getString(r7)
                android.net.Uri r7 = android.net.Uri.parse(r7)
                r5.<init>(r11, r7)
                r7 = 67108864(0x4000000, float:1.5046328E-36)
                r8 = 0
                android.app.PendingIntent r4 = android.app.PendingIntent.getActivity(r4, r8, r5, r7)
                java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r5 = r2.mActions
                androidx.core.app.NotificationCompat$Action r7 = new androidx.core.app.NotificationCompat$Action
                r7.<init>(r1, r4)
                r5.add(r7)
                android.content.Context r1 = r14.mContext
                r4 = 17040852(0x10405d4, float:2.4248752E-38)
                com.google.android.systemui.power.PowerUtils.overrideNotificationAppName(r1, r2, r4)
                android.app.NotificationManager r1 = r14.mNotificationManager
                android.app.Notification r2 = r2.build()
                android.os.UserHandle r4 = android.os.UserHandle.ALL
                r1.notifyAsUser(r6, r0, r2, r4)
                r0 = 1
                r14.mPostNotificationVisible = r0
                goto L_0x0385
            L_0x037e:
                r0 = 0
                java.lang.String r1 = "error getting trigger time"
                android.util.Log.w(r4, r1)
                r8 = r0
            L_0x0385:
                android.content.SharedPreferences r14 = r14.getSharedPreferences()
                android.content.SharedPreferences$Editor r14 = r14.edit()
                android.content.SharedPreferences$Editor r14 = r14.clear()
                r14.apply()
                goto L_0x03b0
            L_0x0395:
                r8 = 0
                goto L_0x03b0
            L_0x0397:
                boolean r1 = r2.equals(r0)
                if (r1 == 0) goto L_0x03a3
                com.google.android.systemui.power.BatteryDefenderNotification$BatteryDefenderEvent r0 = com.google.android.systemui.power.BatteryDefenderNotification.BatteryDefenderEvent.BATTERY_DEFENDER_BYPASS_LIMIT
                r14.resumeCharging(r0)
                goto L_0x03b0
            L_0x03a3:
                java.lang.String r1 = "PNW.defenderResumeCharging.settings"
                boolean r0 = r1.equals(r0)
                if (r0 == 0) goto L_0x03b0
                com.google.android.systemui.power.BatteryDefenderNotification$BatteryDefenderEvent r0 = com.google.android.systemui.power.BatteryDefenderNotification.BatteryDefenderEvent.BATTERY_DEFENDER_BYPASS_LIMIT_FOR_TIPS
                r14.resumeCharging(r0)
            L_0x03b0:
                com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl r13 = com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl.this
                com.google.android.systemui.power.AdaptiveChargingNotification r13 = r13.mAdaptiveChargingNotification
                java.util.Objects.requireNonNull(r13)
                java.lang.String r14 = r15.getAction()
                if (r14 != 0) goto L_0x03bf
                goto L_0x0429
            L_0x03bf:
                java.lang.String r14 = r15.getAction()
                java.util.Objects.requireNonNull(r14)
                int r0 = r14.hashCode()
                r1 = -1538406691(0xffffffffa44dc6dd, float:-4.4620733E-17)
                if (r0 == r1) goto L_0x03f0
                r1 = -220907649(0xfffffffff2d5377f, float:-8.446387E30)
                if (r0 == r1) goto L_0x03e5
                r1 = 1384397091(0x52843923, float:2.83947139E11)
                if (r0 == r1) goto L_0x03da
                goto L_0x03f6
            L_0x03da:
                java.lang.String r0 = "com.google.android.systemui.adaptivecharging.ADAPTIVE_CHARGING_DEADLINE_SET"
                boolean r14 = r14.equals(r0)
                if (r14 != 0) goto L_0x03e3
                goto L_0x03f6
            L_0x03e3:
                r8 = 2
                goto L_0x03f7
            L_0x03e5:
                java.lang.String r0 = "PNW.acChargeNormally"
                boolean r14 = r14.equals(r0)
                if (r14 != 0) goto L_0x03ee
                goto L_0x03f6
            L_0x03ee:
                r8 = 1
                goto L_0x03f7
            L_0x03f0:
                boolean r14 = r14.equals(r3)
                if (r14 != 0) goto L_0x03f7
            L_0x03f6:
                r8 = -1
            L_0x03f7:
                if (r8 == 0) goto L_0x0426
                r14 = 1
                if (r8 == r14) goto L_0x0404
                r15 = 2
                if (r8 == r15) goto L_0x0400
                goto L_0x0429
            L_0x0400:
                r13.checkAdaptiveChargingStatus(r14)
                goto L_0x0429
            L_0x0404:
                com.google.android.systemui.adaptivecharging.AdaptiveChargingManager r14 = r13.mAdaptiveChargingManager
                java.util.Objects.requireNonNull(r14)
                r14 = 0
                vendor.google.google_battery.V1_2.IGoogleBattery r14 = com.google.android.systemui.adaptivecharging.AdaptiveChargingManager.initHalInterface(r14)
                if (r14 != 0) goto L_0x0411
                goto L_0x0422
            L_0x0411:
                r14.setChargingDeadline()     // Catch:{ RemoteException -> 0x0415 }
                goto L_0x041e
            L_0x0415:
                r15 = move-exception
                java.lang.String r0 = "AdaptiveChargingManager"
                java.lang.String r1 = "setChargingDeadline failed: "
                android.util.Log.e(r0, r1, r15)
            L_0x041e:
                r15 = 0
                com.google.android.systemui.adaptivecharging.AdaptiveChargingManager.destroyHalInterface(r14, r15)
            L_0x0422:
                r13.cancelNotification()
                goto L_0x0429
            L_0x0426:
                r13.resolveBatteryChangedIntent(r15)
            L_0x0429:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl.C23011.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    public final Handler mHandler;

    public PowerNotificationWarningsGoogleImpl(Context context, ActivityStarter activityStarter, BroadcastDispatcher broadcastDispatcher, UiEventLogger uiEventLogger) {
        super(context, activityStarter);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mBroadcastDispatcher = broadcastDispatcher;
        handler.post(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0(this, context, uiEventLogger, 2));
    }
}
