package com.android.systemui.navigationbar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import androidx.leanback.R$color;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class NavBarHelper implements AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable {
    public int mA11yButtonState;
    public final ArrayList mA11yEventListeners = new ArrayList();
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityManager mAccessibilityManager;
    public final C09121 mAssistContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
        public final void onChange(boolean z, Uri uri) {
            NavBarHelper.this.updateAssistantAvailability();
        }
    };
    public final Lazy<AssistManager> mAssistManagerLazy;
    public boolean mAssistantAvailable;
    public boolean mAssistantTouchGestureEnabled;
    public ContentResolver mContentResolver;
    public final Context mContext;
    public boolean mLongPressHomeEnabled;
    public int mNavBarMode;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final SystemActions mSystemActions;
    public final UserTracker mUserTracker;

    public interface NavbarTaskbarStateUpdater {
        void updateAccessibilityServicesState();

        void updateAssistantAvailable(boolean z);
    }

    public final void dispatchA11yEventUpdate() {
        Iterator it = this.mA11yEventListeners.iterator();
        while (it.hasNext()) {
            ((NavbarTaskbarStateUpdater) it.next()).updateAccessibilityServicesState();
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "NavbarTaskbarFriendster", "  longPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mAssistantTouchGestureEnabled="), this.mAssistantTouchGestureEnabled, printWriter, "  mAssistantAvailable="), this.mAssistantAvailable, printWriter, "  mNavBarMode=");
        m.append(this.mNavBarMode);
        printWriter.println(m.toString());
    }

    public final void init() {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), false, this.mAssistContentObserver, -1);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mAssistContentObserver, -1);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_touch_gesture_enabled"), false, this.mAssistContentObserver, -1);
        updateAssistantAvailability();
    }

    public final boolean isImeShown(int i) {
        boolean z;
        StatusBar statusBar = (StatusBar) this.mStatusBarOptionalLazy.get().get();
        Objects.requireNonNull(statusBar);
        NotificationShadeWindowView notificationShadeWindowView = statusBar.mNotificationShadeWindowView;
        boolean isKeyguardShowing = ((StatusBar) this.mStatusBarOptionalLazy.get().get()).isKeyguardShowing();
        if (notificationShadeWindowView == null || !notificationShadeWindowView.isAttachedToWindow() || !notificationShadeWindowView.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            return true;
        }
        if (isKeyguardShowing || (i & 2) == 0) {
            return false;
        }
        return true;
    }

    public final void onConnectionChanged(boolean z) {
        if (z) {
            updateAssistantAvailability();
        }
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
        updateAssistantAvailability();
    }

    public final void startAssistant(Bundle bundle) {
        this.mAssistManagerLazy.get().startAssist(bundle);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateA11yState() {
        /*
            r6 = this;
            int r0 = r6.mA11yButtonState
            com.android.systemui.accessibility.AccessibilityButtonModeObserver r1 = r6.mAccessibilityButtonModeObserver
            java.util.Objects.requireNonNull(r1)
            android.content.ContentResolver r2 = r1.mContentResolver
            java.lang.String r1 = r1.mKey
            r3 = -2
            java.lang.String r1 = android.provider.Settings.Secure.getStringForUser(r2, r1, r3)
            int r1 = com.android.systemui.accessibility.AccessibilityButtonModeObserver.parseAccessibilityButtonMode(r1)
            r2 = 1
            r3 = 0
            if (r1 != r2) goto L_0x001c
            r6.mA11yButtonState = r3
            r2 = r3
            goto L_0x003e
        L_0x001c:
            android.view.accessibility.AccessibilityManager r1 = r6.mAccessibilityManager
            java.util.List r1 = r1.getAccessibilityShortcutTargets(r3)
            int r1 = r1.size()
            if (r1 < r2) goto L_0x002a
            r4 = r2
            goto L_0x002b
        L_0x002a:
            r4 = r3
        L_0x002b:
            r5 = 2
            if (r1 < r5) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            r2 = r3
        L_0x0030:
            if (r4 == 0) goto L_0x0035
            r1 = 16
            goto L_0x0036
        L_0x0035:
            r1 = r3
        L_0x0036:
            if (r2 == 0) goto L_0x003a
            r3 = 32
        L_0x003a:
            r1 = r1 | r3
            r6.mA11yButtonState = r1
            r3 = r4
        L_0x003e:
            int r1 = r6.mA11yButtonState
            if (r0 == r1) goto L_0x004c
            r0 = 11
            r6.updateSystemAction(r3, r0)
            r0 = 12
            r6.updateSystemAction(r2, r0)
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavBarHelper.updateA11yState():void");
    }

    public final void updateAssistantAvailability() {
        boolean z;
        boolean z2;
        boolean z3;
        AssistManager assistManager = this.mAssistManagerLazy.get();
        Objects.requireNonNull(assistManager);
        boolean z4 = true;
        if (assistManager.mAssistUtils.getAssistComponentForUser(-2) != null) {
            z = true;
        } else {
            z = false;
        }
        if (Settings.Secure.getIntForUser(this.mContentResolver, "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(17891368) ? 1 : 0, this.mUserTracker.getUserId()) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mLongPressHomeEnabled = z2;
        if (Settings.Secure.getIntForUser(this.mContentResolver, "assist_touch_gesture_enabled", this.mContext.getResources().getBoolean(17891369) ? 1 : 0, this.mUserTracker.getUserId()) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mAssistantTouchGestureEnabled = z3;
        if (!z || !z3 || !R$color.isGesturalMode(this.mNavBarMode)) {
            z4 = false;
        }
        this.mAssistantAvailable = z4;
        Iterator it = this.mA11yEventListeners.iterator();
        while (it.hasNext()) {
            ((NavbarTaskbarStateUpdater) it.next()).updateAssistantAvailable(z4);
        }
    }

    public final void updateSystemAction(boolean z, int i) {
        int i2;
        String str;
        if (z) {
            SystemActions systemActions = this.mSystemActions;
            switch (i) {
                case 1:
                    i2 = 17039597;
                    str = "SYSTEM_ACTION_BACK";
                    break;
                case 2:
                    i2 = 17039606;
                    str = "SYSTEM_ACTION_HOME";
                    break;
                case 3:
                    i2 = 17039613;
                    str = "SYSTEM_ACTION_RECENTS";
                    break;
                case 4:
                    i2 = 17039608;
                    str = "SYSTEM_ACTION_NOTIFICATIONS";
                    break;
                case 5:
                    i2 = 17039612;
                    str = "SYSTEM_ACTION_QUICK_SETTINGS";
                    break;
                case FalsingManager.VERSION /*6*/:
                    i2 = 17039611;
                    str = "SYSTEM_ACTION_POWER_DIALOG";
                    break;
                case 8:
                    i2 = 17039607;
                    str = "SYSTEM_ACTION_LOCK_SCREEN";
                    break;
                case 9:
                    i2 = 17039614;
                    str = "SYSTEM_ACTION_TAKE_SCREENSHOT";
                    break;
                case 10:
                    i2 = 17039605;
                    str = "SYSTEM_ACTION_HEADSET_HOOK";
                    break;
                case QSTileImpl.C1034H.STALE /*11*/:
                    i2 = 17039610;
                    str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON";
                    break;
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                    i2 = 17039609;
                    str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU";
                    break;
                case C0961QS.VERSION /*13*/:
                    i2 = 17039604;
                    str = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT";
                    break;
                case 15:
                    i2 = 17039598;
                    str = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE";
                    break;
                case 16:
                    i2 = 17039603;
                    str = "SYSTEM_ACTION_DPAD_UP";
                    break;
                case 17:
                    i2 = 17039600;
                    str = "SYSTEM_ACTION_DPAD_DOWN";
                    break;
                case 18:
                    i2 = 17039601;
                    str = "SYSTEM_ACTION_DPAD_LEFT";
                    break;
                case 19:
                    i2 = 17039602;
                    str = "SYSTEM_ACTION_DPAD_RIGHT";
                    break;
                case 20:
                    i2 = 17039599;
                    str = "SYSTEM_ACTION_DPAD_CENTER";
                    break;
                default:
                    Objects.requireNonNull(systemActions);
                    return;
            }
            systemActions.mA11yManager.registerSystemAction(systemActions.createRemoteAction(i2, str), i);
            return;
        }
        SystemActions systemActions2 = this.mSystemActions;
        Objects.requireNonNull(systemActions2);
        systemActions2.mA11yManager.unregisterSystemAction(i);
    }

    public NavBarHelper(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, SystemActions systemActions, OverviewProxyService overviewProxyService, Lazy<AssistManager> lazy, Lazy<Optional<StatusBar>> lazy2, NavigationModeController navigationModeController, UserTracker userTracker, DumpManager dumpManager) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAccessibilityManager = accessibilityManager;
        this.mAssistManagerLazy = lazy;
        this.mStatusBarOptionalLazy = lazy2;
        this.mUserTracker = userTracker;
        this.mSystemActions = systemActions;
        accessibilityManager.addAccessibilityServicesStateChangeListener(new NavBarHelper$$ExternalSyntheticLambda0(this));
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        accessibilityButtonModeObserver.addListener(this);
        accessibilityButtonTargetsObserver.addListener(this);
        this.mNavBarMode = navigationModeController.addListener(this);
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        dumpManager.registerDumpable(this);
    }

    public final void onAccessibilityButtonModeChanged(int i) {
        updateA11yState();
        dispatchA11yEventUpdate();
    }

    public final void onAccessibilityButtonTargetsChanged(String str) {
        updateA11yState();
        dispatchA11yEventUpdate();
    }
}
