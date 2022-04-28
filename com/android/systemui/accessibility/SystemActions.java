package com.android.systemui.accessibility;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda1;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.recents.Recents;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.Assert;
import dagger.Lazy;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class SystemActions extends CoreStartable {
    public final AccessibilityManager mA11yManager = ((AccessibilityManager) this.mContext.getSystemService("accessibility"));
    public boolean mDismissNotificationShadeActionRegistered;
    public Locale mLocale = this.mContext.getResources().getConfiguration().getLocales().get(0);
    public final SystemActions$$ExternalSyntheticLambda0 mNotificationShadeCallback;
    public final NotificationShadeWindowController mNotificationShadeController;
    public final SystemActionsBroadcastReceiver mReceiver = new SystemActionsBroadcastReceiver();
    public final Optional<Recents> mRecentsOptional;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;

    public class SystemActionsBroadcastReceiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public SystemActionsBroadcastReceiver() {
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                java.lang.String r8 = r9.getAction()
                java.util.Objects.requireNonNull(r8)
                int r9 = r8.hashCode()
                r0 = 7
                r1 = 4
                r2 = 3
                r3 = 0
                switch(r9) {
                    case -1103811776: goto L_0x00ed;
                    case -1103619272: goto L_0x00e2;
                    case -720484549: goto L_0x00d7;
                    case -535129457: goto L_0x00cc;
                    case -181386672: goto L_0x00c1;
                    case -153384569: goto L_0x00b6;
                    case 42571871: goto L_0x00ab;
                    case 526987266: goto L_0x00a0;
                    case 689657964: goto L_0x0092;
                    case 815482418: goto L_0x0084;
                    case 1245940668: goto L_0x0076;
                    case 1493428793: goto L_0x0068;
                    case 1579999269: goto L_0x005a;
                    case 1668921710: goto L_0x004c;
                    case 1698779909: goto L_0x003e;
                    case 1894867256: goto L_0x0030;
                    case 1994051193: goto L_0x0022;
                    case 1994279390: goto L_0x0014;
                    default: goto L_0x0012;
                }
            L_0x0012:
                goto L_0x00f8
            L_0x0014:
                java.lang.String r9 = "SYSTEM_ACTION_DPAD_LEFT"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x001e
                goto L_0x00f8
            L_0x001e:
                r8 = 17
                goto L_0x00f9
            L_0x0022:
                java.lang.String r9 = "SYSTEM_ACTION_DPAD_DOWN"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x002c
                goto L_0x00f8
            L_0x002c:
                r8 = 16
                goto L_0x00f9
            L_0x0030:
                java.lang.String r9 = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x003a
                goto L_0x00f8
            L_0x003a:
                r8 = 15
                goto L_0x00f9
            L_0x003e:
                java.lang.String r9 = "SYSTEM_ACTION_DPAD_RIGHT"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x0048
                goto L_0x00f8
            L_0x0048:
                r8 = 14
                goto L_0x00f9
            L_0x004c:
                java.lang.String r9 = "SYSTEM_ACTION_QUICK_SETTINGS"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x0056
                goto L_0x00f8
            L_0x0056:
                r8 = 13
                goto L_0x00f9
            L_0x005a:
                java.lang.String r9 = "SYSTEM_ACTION_TAKE_SCREENSHOT"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x0064
                goto L_0x00f8
            L_0x0064:
                r8 = 12
                goto L_0x00f9
            L_0x0068:
                java.lang.String r9 = "SYSTEM_ACTION_HEADSET_HOOK"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x0072
                goto L_0x00f8
            L_0x0072:
                r8 = 11
                goto L_0x00f9
            L_0x0076:
                java.lang.String r9 = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x0080
                goto L_0x00f8
            L_0x0080:
                r8 = 10
                goto L_0x00f9
            L_0x0084:
                java.lang.String r9 = "SYSTEM_ACTION_DPAD_UP"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x008e
                goto L_0x00f8
            L_0x008e:
                r8 = 9
                goto L_0x00f9
            L_0x0092:
                java.lang.String r9 = "SYSTEM_ACTION_DPAD_CENTER"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x009c
                goto L_0x00f8
            L_0x009c:
                r8 = 8
                goto L_0x00f9
            L_0x00a0:
                java.lang.String r9 = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00a9
                goto L_0x00f8
            L_0x00a9:
                r8 = r0
                goto L_0x00f9
            L_0x00ab:
                java.lang.String r9 = "SYSTEM_ACTION_RECENTS"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00b4
                goto L_0x00f8
            L_0x00b4:
                r8 = 6
                goto L_0x00f9
            L_0x00b6:
                java.lang.String r9 = "SYSTEM_ACTION_LOCK_SCREEN"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00bf
                goto L_0x00f8
            L_0x00bf:
                r8 = 5
                goto L_0x00f9
            L_0x00c1:
                java.lang.String r9 = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00ca
                goto L_0x00f8
            L_0x00ca:
                r8 = r1
                goto L_0x00f9
            L_0x00cc:
                java.lang.String r9 = "SYSTEM_ACTION_NOTIFICATIONS"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00d5
                goto L_0x00f8
            L_0x00d5:
                r8 = r2
                goto L_0x00f9
            L_0x00d7:
                java.lang.String r9 = "SYSTEM_ACTION_POWER_DIALOG"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00e0
                goto L_0x00f8
            L_0x00e0:
                r8 = 2
                goto L_0x00f9
            L_0x00e2:
                java.lang.String r9 = "SYSTEM_ACTION_HOME"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00eb
                goto L_0x00f8
            L_0x00eb:
                r8 = 1
                goto L_0x00f9
            L_0x00ed:
                java.lang.String r9 = "SYSTEM_ACTION_BACK"
                boolean r8 = r8.equals(r9)
                if (r8 != 0) goto L_0x00f6
                goto L_0x00f8
            L_0x00f6:
                r8 = r3
                goto L_0x00f9
            L_0x00f8:
                r8 = -1
            L_0x00f9:
                java.lang.String r9 = "SystemActions"
                switch(r8) {
                    case 0: goto L_0x0231;
                    case 1: goto L_0x0228;
                    case 2: goto L_0x0215;
                    case 3: goto L_0x0202;
                    case 4: goto L_0x01f7;
                    case 5: goto L_0x01d2;
                    case 6: goto L_0x01c5;
                    case 7: goto L_0x019f;
                    case 8: goto L_0x0193;
                    case 9: goto L_0x0187;
                    case 10: goto L_0x0177;
                    case 11: goto L_0x016b;
                    case 12: goto L_0x014c;
                    case 13: goto L_0x0138;
                    case 14: goto L_0x012c;
                    case 15: goto L_0x0118;
                    case 16: goto L_0x010c;
                    case 17: goto L_0x0100;
                    default: goto L_0x00fe;
                }
            L_0x00fe:
                goto L_0x0239
            L_0x0100:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 21
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x010c:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 20
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x0118:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                dagger.Lazy<java.util.Optional<com.android.systemui.statusbar.phone.StatusBar>> r7 = r7.mStatusBarOptionalLazy
                java.lang.Object r7 = r7.get()
                java.util.Optional r7 = (java.util.Optional) r7
                com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda4 r8 = com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda4.INSTANCE$1
                r7.ifPresent(r8)
                goto L_0x0239
            L_0x012c:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 22
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x0138:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                dagger.Lazy<java.util.Optional<com.android.systemui.statusbar.phone.StatusBar>> r7 = r7.mStatusBarOptionalLazy
                java.lang.Object r7 = r7.get()
                java.util.Optional r7 = (java.util.Optional) r7
                com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda5 r8 = com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda5.INSTANCE$1
                r7.ifPresent(r8)
                goto L_0x0239
            L_0x014c:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                com.android.internal.util.ScreenshotHelper r0 = new com.android.internal.util.ScreenshotHelper
                android.content.Context r7 = r7.mContext
                r0.<init>(r7)
                android.os.Handler r5 = new android.os.Handler
                android.os.Looper r7 = android.os.Looper.getMainLooper()
                r5.<init>(r7)
                r1 = 1
                r2 = 1
                r3 = 1
                r4 = 4
                r6 = 0
                r0.takeScreenshot(r1, r2, r3, r4, r5, r6)
                goto L_0x0239
            L_0x016b:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 79
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x0177:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                android.content.Context r7 = r7.mContext
                android.view.accessibility.AccessibilityManager r7 = android.view.accessibility.AccessibilityManager.getInstance(r7)
                r7.notifyAccessibilityButtonClicked(r3)
                goto L_0x0239
            L_0x0187:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 19
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x0193:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                r7 = 23
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r7)
                goto L_0x0239
            L_0x019f:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                android.content.Intent r8 = new android.content.Intent
                java.lang.String r9 = "com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON"
                r8.<init>(r9)
                r9 = 268468224(0x10008000, float:2.5342157E-29)
                r8.addFlags(r9)
                java.lang.Class<com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity> r9 = com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity.class
                java.lang.String r9 = r9.getName()
                java.lang.String r0 = "android"
                r8.setClassName(r0, r9)
                android.content.Context r7 = r7.mContext
                android.os.UserHandle r9 = android.os.UserHandle.CURRENT
                r7.startActivityAsUser(r8, r9)
                goto L_0x0239
            L_0x01c5:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                java.util.Optional<com.android.systemui.recents.Recents> r7 = r7.mRecentsOptional
                com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda1 r8 = com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda1.INSTANCE
                r7.ifPresent(r8)
                goto L_0x0239
            L_0x01d2:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                android.view.IWindowManager r8 = android.view.WindowManagerGlobal.getWindowManagerService()
                android.content.Context r7 = r7.mContext
                java.lang.Class<android.os.PowerManager> r1 = android.os.PowerManager.class
                java.lang.Object r7 = r7.getSystemService(r1)
                android.os.PowerManager r7 = (android.os.PowerManager) r7
                long r1 = android.os.SystemClock.uptimeMillis()
                r7.goToSleep(r1, r0, r3)
                r7 = 0
                r8.lockNow(r7)     // Catch:{ RemoteException -> 0x01f1 }
                goto L_0x0239
            L_0x01f1:
                java.lang.String r7 = "failed to lock screen."
                android.util.Log.e(r9, r7)
                goto L_0x0239
            L_0x01f7:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                android.view.accessibility.AccessibilityManager r7 = r7.mA11yManager
                r7.performAccessibilityShortcut()
                goto L_0x0239
            L_0x0202:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                dagger.Lazy<java.util.Optional<com.android.systemui.statusbar.phone.StatusBar>> r7 = r7.mStatusBarOptionalLazy
                java.lang.Object r7 = r7.get()
                java.util.Optional r7 = (java.util.Optional) r7
                com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2 r8 = com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2.INSTANCE
                r7.ifPresent(r8)
                goto L_0x0239
            L_0x0215:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                android.view.IWindowManager r7 = android.view.WindowManagerGlobal.getWindowManagerService()
                r7.showGlobalActions()     // Catch:{ RemoteException -> 0x0222 }
                goto L_0x0239
            L_0x0222:
                java.lang.String r7 = "failed to display power dialog."
                android.util.Log.e(r9, r7)
                goto L_0x0239
            L_0x0228:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r2)
                goto L_0x0239
            L_0x0231:
                com.android.systemui.accessibility.SystemActions r7 = com.android.systemui.accessibility.SystemActions.this
                java.util.Objects.requireNonNull(r7)
                com.android.systemui.accessibility.SystemActions.sendDownAndUpKeyEvents(r1)
            L_0x0239:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.SystemActions.SystemActionsBroadcastReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public static void sendKeyEventIdentityCleared(int i, int i2, long j, long j2) {
        KeyEvent obtain = KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, 257, (String) null);
        InputManager.getInstance().injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    public final RemoteAction createRemoteAction(int i, String str) {
        PendingIntent pendingIntent;
        Icon createWithResource = Icon.createWithResource(this.mContext, 17301684);
        String string = this.mContext.getString(i);
        String string2 = this.mContext.getString(i);
        SystemActionsBroadcastReceiver systemActionsBroadcastReceiver = this.mReceiver;
        Context context = this.mContext;
        int i2 = SystemActionsBroadcastReceiver.$r8$clinit;
        Objects.requireNonNull(systemActionsBroadcastReceiver);
        char c = 65535;
        switch (str.hashCode()) {
            case -1103811776:
                if (str.equals("SYSTEM_ACTION_BACK")) {
                    c = 0;
                    break;
                }
                break;
            case -1103619272:
                if (str.equals("SYSTEM_ACTION_HOME")) {
                    c = 1;
                    break;
                }
                break;
            case -720484549:
                if (str.equals("SYSTEM_ACTION_POWER_DIALOG")) {
                    c = 2;
                    break;
                }
                break;
            case -535129457:
                if (str.equals("SYSTEM_ACTION_NOTIFICATIONS")) {
                    c = 3;
                    break;
                }
                break;
            case -181386672:
                if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT")) {
                    c = 4;
                    break;
                }
                break;
            case -153384569:
                if (str.equals("SYSTEM_ACTION_LOCK_SCREEN")) {
                    c = 5;
                    break;
                }
                break;
            case 42571871:
                if (str.equals("SYSTEM_ACTION_RECENTS")) {
                    c = 6;
                    break;
                }
                break;
            case 526987266:
                if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU")) {
                    c = 7;
                    break;
                }
                break;
            case 689657964:
                if (str.equals("SYSTEM_ACTION_DPAD_CENTER")) {
                    c = 8;
                    break;
                }
                break;
            case 815482418:
                if (str.equals("SYSTEM_ACTION_DPAD_UP")) {
                    c = 9;
                    break;
                }
                break;
            case 1245940668:
                if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON")) {
                    c = 10;
                    break;
                }
                break;
            case 1493428793:
                if (str.equals("SYSTEM_ACTION_HEADSET_HOOK")) {
                    c = 11;
                    break;
                }
                break;
            case 1579999269:
                if (str.equals("SYSTEM_ACTION_TAKE_SCREENSHOT")) {
                    c = 12;
                    break;
                }
                break;
            case 1668921710:
                if (str.equals("SYSTEM_ACTION_QUICK_SETTINGS")) {
                    c = 13;
                    break;
                }
                break;
            case 1698779909:
                if (str.equals("SYSTEM_ACTION_DPAD_RIGHT")) {
                    c = 14;
                    break;
                }
                break;
            case 1894867256:
                if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE")) {
                    c = 15;
                    break;
                }
                break;
            case 1994051193:
                if (str.equals("SYSTEM_ACTION_DPAD_DOWN")) {
                    c = 16;
                    break;
                }
                break;
            case 1994279390:
                if (str.equals("SYSTEM_ACTION_DPAD_LEFT")) {
                    c = 17;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case FalsingManager.VERSION /*6*/:
            case 7:
            case 8:
            case 9:
            case 10:
            case QSTileImpl.C1034H.STALE /*11*/:
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
            case C0961QS.VERSION /*13*/:
            case 14:
            case 15:
            case 16:
            case 17:
                Intent intent = new Intent(str);
                intent.setPackage(context.getPackageName());
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 67108864);
                break;
            default:
                pendingIntent = null;
                break;
        }
        return new RemoteAction(createWithResource, string, string2, pendingIntent);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(this.mLocale)) {
            this.mLocale = locale;
            registerActions();
        }
    }

    public final void registerActions() {
        RemoteAction createRemoteAction = createRemoteAction(17039597, "SYSTEM_ACTION_BACK");
        RemoteAction createRemoteAction2 = createRemoteAction(17039606, "SYSTEM_ACTION_HOME");
        RemoteAction createRemoteAction3 = createRemoteAction(17039613, "SYSTEM_ACTION_RECENTS");
        RemoteAction createRemoteAction4 = createRemoteAction(17039608, "SYSTEM_ACTION_NOTIFICATIONS");
        RemoteAction createRemoteAction5 = createRemoteAction(17039612, "SYSTEM_ACTION_QUICK_SETTINGS");
        RemoteAction createRemoteAction6 = createRemoteAction(17039611, "SYSTEM_ACTION_POWER_DIALOG");
        RemoteAction createRemoteAction7 = createRemoteAction(17039607, "SYSTEM_ACTION_LOCK_SCREEN");
        RemoteAction createRemoteAction8 = createRemoteAction(17039614, "SYSTEM_ACTION_TAKE_SCREENSHOT");
        RemoteAction createRemoteAction9 = createRemoteAction(17039605, "SYSTEM_ACTION_HEADSET_HOOK");
        RemoteAction createRemoteAction10 = createRemoteAction(17039604, "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        RemoteAction createRemoteAction11 = createRemoteAction(17039603, "SYSTEM_ACTION_DPAD_UP");
        RemoteAction createRemoteAction12 = createRemoteAction(17039600, "SYSTEM_ACTION_DPAD_DOWN");
        RemoteAction createRemoteAction13 = createRemoteAction(17039601, "SYSTEM_ACTION_DPAD_LEFT");
        RemoteAction createRemoteAction14 = createRemoteAction(17039602, "SYSTEM_ACTION_DPAD_RIGHT");
        RemoteAction createRemoteAction15 = createRemoteAction(17039599, "SYSTEM_ACTION_DPAD_CENTER");
        this.mA11yManager.registerSystemAction(createRemoteAction, 1);
        this.mA11yManager.registerSystemAction(createRemoteAction2, 2);
        this.mA11yManager.registerSystemAction(createRemoteAction3, 3);
        this.mA11yManager.registerSystemAction(createRemoteAction4, 4);
        this.mA11yManager.registerSystemAction(createRemoteAction5, 5);
        this.mA11yManager.registerSystemAction(createRemoteAction6, 6);
        this.mA11yManager.registerSystemAction(createRemoteAction7, 8);
        this.mA11yManager.registerSystemAction(createRemoteAction8, 9);
        this.mA11yManager.registerSystemAction(createRemoteAction9, 10);
        this.mA11yManager.registerSystemAction(createRemoteAction10, 13);
        this.mA11yManager.registerSystemAction(createRemoteAction11, 16);
        this.mA11yManager.registerSystemAction(createRemoteAction12, 17);
        this.mA11yManager.registerSystemAction(createRemoteAction13, 18);
        this.mA11yManager.registerSystemAction(createRemoteAction14, 19);
        this.mA11yManager.registerSystemAction(createRemoteAction15, 20);
        registerOrUnregisterDismissNotificationShadeAction();
    }

    public final void start() {
        this.mNotificationShadeController.registerCallback(this.mNotificationShadeCallback);
        Context context = this.mContext;
        SystemActionsBroadcastReceiver systemActionsBroadcastReceiver = this.mReceiver;
        int i = SystemActionsBroadcastReceiver.$r8$clinit;
        Objects.requireNonNull(systemActionsBroadcastReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("SYSTEM_ACTION_BACK");
        intentFilter.addAction("SYSTEM_ACTION_HOME");
        intentFilter.addAction("SYSTEM_ACTION_RECENTS");
        intentFilter.addAction("SYSTEM_ACTION_NOTIFICATIONS");
        intentFilter.addAction("SYSTEM_ACTION_QUICK_SETTINGS");
        intentFilter.addAction("SYSTEM_ACTION_POWER_DIALOG");
        intentFilter.addAction("SYSTEM_ACTION_LOCK_SCREEN");
        intentFilter.addAction("SYSTEM_ACTION_TAKE_SCREENSHOT");
        intentFilter.addAction("SYSTEM_ACTION_HEADSET_HOOK");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_UP");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_DOWN");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_LEFT");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_RIGHT");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_CENTER");
        context.registerReceiverForAllUsers(systemActionsBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", (Handler) null, 2);
        registerActions();
    }

    public SystemActions(Context context, NotificationShadeWindowController notificationShadeWindowController, Lazy<Optional<StatusBar>> lazy, Optional<Recents> optional) {
        super(context);
        this.mRecentsOptional = optional;
        this.mNotificationShadeController = notificationShadeWindowController;
        this.mNotificationShadeCallback = new SystemActions$$ExternalSyntheticLambda0(this);
        this.mStatusBarOptionalLazy = lazy;
    }

    public static void sendDownAndUpKeyEvents(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        int i2 = i;
        long j = uptimeMillis;
        sendKeyEventIdentityCleared(i2, 0, j, uptimeMillis);
        sendKeyEventIdentityCleared(i2, 1, j, SystemClock.uptimeMillis());
    }

    public final void registerOrUnregisterDismissNotificationShadeAction() {
        Assert.isMainThread();
        Optional optional = this.mStatusBarOptionalLazy.get();
        if (!((Boolean) optional.map(PhoneStateMonitor$$ExternalSyntheticLambda1.INSTANCE$1).orElse(Boolean.FALSE)).booleanValue() || ((StatusBar) optional.get()).isKeyguardShowing()) {
            if (this.mDismissNotificationShadeActionRegistered) {
                this.mA11yManager.unregisterSystemAction(15);
                this.mDismissNotificationShadeActionRegistered = false;
            }
        } else if (!this.mDismissNotificationShadeActionRegistered) {
            this.mA11yManager.registerSystemAction(createRemoteAction(17039598, "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE"), 15);
            this.mDismissNotificationShadeActionRegistered = true;
        }
    }
}
