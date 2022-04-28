package com.google.android.systemui.columbus.actions;

import android.app.IActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.KeyguardVisibility;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: LaunchApp.kt */
public final class LaunchApp extends UserAction {
    public final ActivityStarter activityStarter;
    public final Set<String> allowCertList;
    public final Set<String> allowPackageList;
    public final LinkedHashMap availableApps;
    public final LinkedHashMap availableShortcuts;
    public final Executor bgExecutor;
    public final Handler bgHandler;
    public final LaunchApp$broadcastReceiver$1 broadcastReceiver;
    public ComponentName currentApp;
    public String currentShortcut;
    public final LinkedHashSet denyPackageList;
    public final LaunchApp$deviceConfigPropertiesChangedListener$1 deviceConfigPropertiesChangedListener;
    public final LaunchApp$gateListener$1 gateListener;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final LaunchApp$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final KeyguardVisibility keyguardVisibility;
    public final LauncherApps launcherApps;
    public final Handler mainHandler;
    public final MessageDigest messageDigest;
    public final LaunchApp$onDismissKeyguardAction$1 onDismissKeyguardAction;
    public final LaunchApp$settingsListener$1 settingsListener = new LaunchApp$settingsListener$1(this);
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final String tag = "Columbus/LaunchApp";
    public final UiEventLogger uiEventLogger;
    public final UserManager userManager;
    public final UserTracker userTracker;

    public LaunchApp(Context context, LauncherApps launcherApps2, ActivityStarter activityStarter2, StatusBarKeyguardViewManager statusBarKeyguardViewManager2, IActivityManager iActivityManager, UserManager userManager2, ColumbusSettings columbusSettings, KeyguardVisibility keyguardVisibility2, KeyguardUpdateMonitor keyguardUpdateMonitor2, Handler handler, Handler handler2, Executor executor, UiEventLogger uiEventLogger2, UserTracker userTracker2) {
        super(context, (Set<? extends FeedbackEffect>) null);
        this.launcherApps = launcherApps2;
        this.activityStarter = activityStarter2;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        this.userManager = userManager2;
        this.keyguardVisibility = keyguardVisibility2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.mainHandler = handler;
        this.bgHandler = handler2;
        this.bgExecutor = executor;
        this.uiEventLogger = uiEventLogger2;
        this.userTracker = userTracker2;
        LaunchApp$userSwitchCallback$1 launchApp$userSwitchCallback$1 = new LaunchApp$userSwitchCallback$1(this);
        this.broadcastReceiver = new LaunchApp$broadcastReceiver$1(this);
        this.gateListener = new LaunchApp$gateListener$1(this);
        this.keyguardUpdateMonitorCallback = new LaunchApp$keyguardUpdateMonitorCallback$1(this, context);
        LaunchApp$deviceConfigPropertiesChangedListener$1 launchApp$deviceConfigPropertiesChangedListener$1 = new LaunchApp$deviceConfigPropertiesChangedListener$1(this);
        this.deviceConfigPropertiesChangedListener = launchApp$deviceConfigPropertiesChangedListener$1;
        this.onDismissKeyguardAction = new LaunchApp$onDismissKeyguardAction$1(this);
        this.messageDigest = MessageDigest.getInstance("SHA-256");
        String[] stringArray = context.getResources().getStringArray(C1777R.array.columbus_sumatra_package_allow_list);
        this.allowPackageList = SetsKt__SetsKt.setOf(Arrays.copyOf(stringArray, stringArray.length));
        String[] stringArray2 = context.getResources().getStringArray(C1777R.array.columbus_sumatra_cert_allow_list);
        this.allowCertList = SetsKt__SetsKt.setOf(Arrays.copyOf(stringArray2, stringArray2.length));
        this.denyPackageList = new LinkedHashSet();
        this.availableApps = new LinkedHashMap();
        this.availableShortcuts = new LinkedHashMap();
        String str = "";
        this.currentShortcut = str;
        DeviceConfig.addOnPropertiesChangedListener("systemui", executor, launchApp$deviceConfigPropertiesChangedListener$1);
        updateDenyList(DeviceConfig.getString("systemui", "systemui_google_columbus_secure_deny_list", (String) null));
        try {
            iActivityManager.registerUserSwitchObserver(launchApp$userSwitchCallback$1, "Columbus/LaunchApp");
        } catch (RemoteException e) {
            Log.e("Columbus/LaunchApp", "Failed to register user switch observer", e);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(this.broadcastReceiver, intentFilter);
        context.registerReceiver(this.broadcastReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        updateAvailableAppsAndShortcutsAsync();
        columbusSettings.registerColumbusSettingsChangeListener(this.settingsListener);
        this.currentApp = ComponentName.unflattenFromString(columbusSettings.selectedApp());
        String stringForUser = Settings.Secure.getStringForUser(columbusSettings.contentResolver, "columbus_launch_app_shortcut", columbusSettings.userTracker.getUserId());
        this.currentShortcut = stringForUser != null ? stringForUser : str;
        this.keyguardVisibility.registerListener(this.gateListener);
        updateAvailable();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTrigger(com.google.android.systemui.columbus.sensors.GestureSensor.DetectionProperties r20) {
        /*
            r19 = this;
            r1 = r19
            com.google.android.systemui.columbus.gates.KeyguardVisibility r0 = r1.keyguardVisibility
            boolean r0 = r0.isBlocking()
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0021
            com.google.android.systemui.columbus.gates.KeyguardVisibility r0 = r1.keyguardVisibility
            java.util.Objects.requireNonNull(r0)
            dagger.Lazy<com.android.systemui.statusbar.policy.KeyguardStateController> r0 = r0.keyguardStateController
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = (com.android.systemui.statusbar.policy.KeyguardStateController) r0
            boolean r0 = r0.isMethodSecure()
            if (r0 == 0) goto L_0x0021
            r0 = r2
            goto L_0x0022
        L_0x0021:
            r0 = r3
        L_0x0022:
            r4 = 0
            if (r0 == 0) goto L_0x00db
            android.content.ComponentName r0 = r1.currentApp
            if (r0 != 0) goto L_0x002b
            r0 = r4
            goto L_0x002f
        L_0x002b:
            java.lang.String r0 = r0.getPackageName()
        L_0x002f:
            java.util.LinkedHashSet r5 = r1.denyPackageList
            boolean r5 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r5, r0)
            if (r5 != 0) goto L_0x00ac
            if (r0 == 0) goto L_0x00a7
            java.util.Set<java.lang.String> r5 = r1.allowPackageList
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L_0x0042
            goto L_0x00a7
        L_0x0042:
            android.content.Context r5 = r1.context
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            r6 = 134217728(0x8000000, float:3.85186E-34)
            android.content.pm.PackageInfo r0 = r5.getPackageInfo(r0, r6)
            android.content.pm.SigningInfo r5 = r0.signingInfo
            boolean r5 = r5.hasMultipleSigners()
            if (r5 == 0) goto L_0x005d
            android.content.pm.SigningInfo r0 = r0.signingInfo
            android.content.pm.Signature[] r0 = r0.getApkContentsSigners()
            goto L_0x0063
        L_0x005d:
            android.content.pm.SigningInfo r0 = r0.signingInfo
            android.content.pm.Signature[] r0 = r0.getSigningCertificateHistory()
        L_0x0063:
            java.util.ArrayList r5 = new java.util.ArrayList
            int r6 = r0.length
            r5.<init>(r6)
            int r6 = r0.length
            r7 = r3
        L_0x006b:
            if (r7 >= r6) goto L_0x0086
            r8 = r0[r7]
            int r7 = r7 + 1
            java.lang.String r9 = new java.lang.String
            java.security.MessageDigest r10 = r1.messageDigest
            byte[] r8 = r8.toByteArray()
            byte[] r8 = r10.digest(r8)
            java.nio.charset.Charset r10 = kotlin.text.Charsets.UTF_16
            r9.<init>(r8, r10)
            r5.add(r9)
            goto L_0x006b
        L_0x0086:
            boolean r0 = r5.isEmpty()
            if (r0 == 0) goto L_0x008d
            goto L_0x00a7
        L_0x008d:
            java.util.Iterator r0 = r5.iterator()
        L_0x0091:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L_0x00a7
            java.lang.Object r5 = r0.next()
            java.lang.String r5 = (java.lang.String) r5
            java.util.Set<java.lang.String> r6 = r1.allowCertList
            boolean r5 = r6.contains(r5)
            if (r5 == 0) goto L_0x0091
            r0 = r2
            goto L_0x00a8
        L_0x00a7:
            r0 = r3
        L_0x00a8:
            if (r0 == 0) goto L_0x00ac
            r0 = r2
            goto L_0x00ad
        L_0x00ac:
            r0 = r3
        L_0x00ad:
            if (r0 != 0) goto L_0x00b0
            goto L_0x00db
        L_0x00b0:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r5 = "android.media.action.STILL_IMAGE_CAMERA_SECURE"
            r0.<init>(r5)
            android.content.ComponentName r5 = r1.currentApp
            if (r5 != 0) goto L_0x00bd
            r5 = r4
            goto L_0x00c1
        L_0x00bd:
            java.lang.String r5 = r5.getPackageName()
        L_0x00c1:
            android.content.Intent r0 = r0.setPackage(r5)
            java.lang.String r5 = "systemui_google_quick_tap_is_source"
            android.content.Intent r0 = r0.putExtra(r5, r2)
            android.content.Context r5 = r1.context
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            android.content.ComponentName r5 = r0.resolveActivity(r5)
            if (r5 != 0) goto L_0x00d9
            goto L_0x00db
        L_0x00d9:
            r10 = r0
            goto L_0x00dc
        L_0x00db:
            r10 = r4
        L_0x00dc:
            if (r10 != 0) goto L_0x00df
            goto L_0x0134
        L_0x00df:
            android.app.ActivityOptions r0 = android.app.ActivityOptions.makeBasic()
            r0.setDisallowEnterPictureInPictureWhileLaunching(r2)
            r5 = 3
            r0.setRotationAnimationHint(r5)
            android.app.IActivityTaskManager r6 = android.app.ActivityTaskManager.getService()     // Catch:{ RemoteException -> 0x012c }
            r7 = 0
            android.content.Context r5 = r1.context     // Catch:{ RemoteException -> 0x012c }
            java.lang.String r8 = r5.getBasePackageName()     // Catch:{ RemoteException -> 0x012c }
            android.content.Context r5 = r1.context     // Catch:{ RemoteException -> 0x012c }
            java.lang.String r9 = r5.getAttributionTag()     // Catch:{ RemoteException -> 0x012c }
            android.content.Context r5 = r1.context     // Catch:{ RemoteException -> 0x012c }
            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch:{ RemoteException -> 0x012c }
            java.lang.String r11 = r10.resolveTypeIfNeeded(r5)     // Catch:{ RemoteException -> 0x012c }
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 268435456(0x10000000, float:2.5243549E-29)
            r16 = 0
            android.os.Bundle r17 = r0.toBundle()     // Catch:{ RemoteException -> 0x012c }
            com.android.systemui.settings.UserTracker r0 = r1.userTracker     // Catch:{ RemoteException -> 0x012c }
            int r18 = r0.getUserId()     // Catch:{ RemoteException -> 0x012c }
            r6.startActivityAsUser(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ RemoteException -> 0x012c }
            com.android.internal.logging.UiEventLogger r0 = r1.uiEventLogger     // Catch:{ RemoteException -> 0x012c }
            com.google.android.systemui.columbus.ColumbusEvent r5 = com.google.android.systemui.columbus.ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_APP_SECURE     // Catch:{ RemoteException -> 0x012c }
            android.content.ComponentName r6 = r1.currentApp     // Catch:{ RemoteException -> 0x012c }
            if (r6 != 0) goto L_0x0123
            r6 = r4
            goto L_0x0127
        L_0x0123:
            java.lang.String r6 = r6.flattenToString()     // Catch:{ RemoteException -> 0x012c }
        L_0x0127:
            r0.log(r5, r3, r6)     // Catch:{ RemoteException -> 0x012c }
            r3 = r2
            goto L_0x0134
        L_0x012c:
            r0 = move-exception
            java.lang.String r5 = "Columbus/LaunchApp"
            java.lang.String r6 = "Unable to start secure activity for"
            android.util.Log.e(r5, r6, r0)
        L_0x0134:
            if (r3 != 0) goto L_0x016a
            com.google.android.systemui.columbus.gates.KeyguardVisibility r0 = r1.keyguardVisibility
            java.util.Objects.requireNonNull(r0)
            dagger.Lazy<com.android.systemui.statusbar.policy.KeyguardStateController> r0 = r0.keyguardStateController
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = (com.android.systemui.statusbar.policy.KeyguardStateController) r0
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x0163
            com.google.android.systemui.columbus.gates.KeyguardVisibility r0 = r1.keyguardVisibility
            java.util.Objects.requireNonNull(r0)
            dagger.Lazy<com.android.systemui.statusbar.policy.KeyguardStateController> r0 = r0.keyguardStateController
            java.lang.Object r0 = r0.get()
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = (com.android.systemui.statusbar.policy.KeyguardStateController) r0
            boolean r0 = r0.isMethodSecure()
            if (r0 == 0) goto L_0x0163
            com.android.keyguard.KeyguardUpdateMonitor r0 = r1.keyguardUpdateMonitor
            com.google.android.systemui.columbus.actions.LaunchApp$keyguardUpdateMonitorCallback$1 r3 = r1.keyguardUpdateMonitorCallback
            r0.registerCallback(r3)
        L_0x0163:
            com.android.systemui.plugins.ActivityStarter r0 = r1.activityStarter
            com.google.android.systemui.columbus.actions.LaunchApp$onDismissKeyguardAction$1 r1 = r1.onDismissKeyguardAction
            r0.dismissKeyguardThenExecute(r1, r4, r2)
        L_0x016a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.actions.LaunchApp.onTrigger(com.google.android.systemui.columbus.sensors.GestureSensor$DetectionProperties):void");
    }

    public final void updateAvailableAppsAndShortcutsAsync() {
        this.bgHandler.post(new LaunchApp$updateAvailableAppsAndShortcutsAsync$1(this));
    }

    public final void updateDenyList(String str) {
        this.denyPackageList.clear();
        if (str != null) {
            LinkedHashSet linkedHashSet = this.denyPackageList;
            List<String> split$default = StringsKt__StringsKt.split$default(str, new String[]{","});
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(split$default, 10));
            for (String trim : split$default) {
                arrayList.add(StringsKt__StringsKt.trim(trim).toString());
            }
            linkedHashSet.addAll(arrayList);
        }
    }

    public final boolean usingShortcut() {
        boolean z;
        String str;
        if (this.currentShortcut.length() > 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            String str2 = this.currentShortcut;
            ComponentName componentName = this.currentApp;
            if (componentName == null) {
                str = null;
            } else {
                str = componentName.flattenToString();
            }
            if (!Intrinsics.areEqual(str2, str)) {
                return true;
            }
        }
        return false;
    }

    public static final void access$addShortcutsForApp(LaunchApp launchApp, LauncherActivityInfo launcherActivityInfo, List list) {
        Objects.requireNonNull(launchApp);
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (Object next : list) {
                if (Intrinsics.areEqual(((ShortcutInfo) next).getPackage(), launcherActivityInfo.getComponentName().getPackageName())) {
                    arrayList.add(next);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ShortcutInfo shortcutInfo = (ShortcutInfo) it.next();
                launchApp.availableShortcuts.putIfAbsent(shortcutInfo.getPackage(), new LinkedHashMap());
                Object obj = launchApp.availableShortcuts.get(shortcutInfo.getPackage());
                Intrinsics.checkNotNull(obj);
                ((Map) obj).put(shortcutInfo.getId(), shortcutInfo);
            }
        }
    }

    public final String toString() {
        if (!usingShortcut()) {
            return Intrinsics.stringPlus("Launch ", this.currentApp);
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Launch ");
        m.append(this.currentApp);
        m.append(" shortcut ");
        m.append(this.currentShortcut);
        return m.toString();
    }

    public final void updateAvailable() {
        String str;
        if (usingShortcut()) {
            LinkedHashMap linkedHashMap = this.availableShortcuts;
            ComponentName componentName = this.currentApp;
            if (componentName == null) {
                str = null;
            } else {
                str = componentName.getPackageName();
            }
            Map map = (Map) linkedHashMap.get(str);
            boolean z = false;
            if (map != null && map.containsKey(this.currentShortcut)) {
                z = true;
            }
            setAvailable(z);
            return;
        }
        setAvailable(this.availableApps.containsKey(this.currentApp));
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
