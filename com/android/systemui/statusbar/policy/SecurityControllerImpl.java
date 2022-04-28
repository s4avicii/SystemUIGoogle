package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.VpnManager;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.SecurityController;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParserException;

public final class SecurityControllerImpl extends CurrentUserTracker implements SecurityController {
    public static final boolean DEBUG = Log.isLoggable("SecurityController", 3);
    public static final NetworkRequest REQUEST = new NetworkRequest.Builder().clearCapabilities().build();
    public final Executor mBgExecutor;
    public final C16402 mBroadcastReceiver;
    @GuardedBy({"mCallbacks"})
    public final ArrayList<SecurityController.SecurityControllerCallback> mCallbacks = new ArrayList<>();
    public final Context mContext;
    public int mCurrentUserId;
    public SparseArray<VpnConfig> mCurrentVpns = new SparseArray<>();
    public final DevicePolicyManager mDevicePolicyManager;
    public ArrayMap<Integer, Boolean> mHasCACerts = new ArrayMap<>();
    public final C16391 mNetworkCallback;
    public final PackageManager mPackageManager;
    public final UserManager mUserManager;
    public final VpnManager mVpnManager;
    public int mVpnUserId;

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addCallback(java.lang.Object r5) {
        /*
            r4 = this;
            com.android.systemui.statusbar.policy.SecurityController$SecurityControllerCallback r5 = (com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback) r5
            java.util.ArrayList<com.android.systemui.statusbar.policy.SecurityController$SecurityControllerCallback> r0 = r4.mCallbacks
            monitor-enter(r0)
            if (r5 == 0) goto L_0x0031
            java.util.ArrayList<com.android.systemui.statusbar.policy.SecurityController$SecurityControllerCallback> r1 = r4.mCallbacks     // Catch:{ all -> 0x0033 }
            boolean r1 = r1.contains(r5)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0031
        L_0x0010:
            boolean r1 = DEBUG     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x002a
            java.lang.String r1 = "SecurityController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0033 }
            r2.<init>()     // Catch:{ all -> 0x0033 }
            java.lang.String r3 = "addCallback "
            r2.append(r3)     // Catch:{ all -> 0x0033 }
            r2.append(r5)     // Catch:{ all -> 0x0033 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0033 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0033 }
        L_0x002a:
            java.util.ArrayList<com.android.systemui.statusbar.policy.SecurityController$SecurityControllerCallback> r4 = r4.mCallbacks     // Catch:{ all -> 0x0033 }
            r4.add(r5)     // Catch:{ all -> 0x0033 }
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            goto L_0x0032
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
        L_0x0032:
            return
        L_0x0033:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SecurityControllerImpl.addCallback(java.lang.Object):void");
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("SecurityController state:");
        printWriter.print("  mCurrentVpns={");
        for (int i = 0; i < this.mCurrentVpns.size(); i++) {
            if (i > 0) {
                printWriter.print(", ");
            }
            printWriter.print(this.mCurrentVpns.keyAt(i));
            printWriter.print('=');
            printWriter.print(this.mCurrentVpns.valueAt(i).user);
        }
        printWriter.println("}");
    }

    public final void fireCallbacks() {
        synchronized (this.mCallbacks) {
            Iterator<SecurityController.SecurityControllerCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onStateChanged();
            }
        }
    }

    public final DeviceAdminInfo getDeviceAdminInfo() {
        ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(new UserHandle(this.mCurrentUserId));
        try {
            ResolveInfo resolveInfo = new ResolveInfo();
            resolveInfo.activityInfo = this.mPackageManager.getReceiverInfo(profileOwnerOrDeviceOwnerSupervisionComponent, 128);
            return new DeviceAdminInfo(this.mContext, resolveInfo);
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException unused) {
            return null;
        }
    }

    public final ComponentName getDeviceOwnerComponentOnAnyUser() {
        return this.mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser();
    }

    public final CharSequence getDeviceOwnerOrganizationName() {
        return this.mDevicePolicyManager.getDeviceOwnerOrganizationName();
    }

    public final int getDeviceOwnerType(ComponentName componentName) {
        return this.mDevicePolicyManager.getDeviceOwnerType(componentName);
    }

    public final Drawable getIcon(DeviceAdminInfo deviceAdminInfo) {
        if (deviceAdminInfo == null) {
            return null;
        }
        return deviceAdminInfo.loadIcon(this.mPackageManager);
    }

    public final CharSequence getLabel(DeviceAdminInfo deviceAdminInfo) {
        if (deviceAdminInfo == null) {
            return null;
        }
        return deviceAdminInfo.loadLabel(this.mPackageManager);
    }

    public final String getNameForVpnConfig(VpnConfig vpnConfig, UserHandle userHandle) {
        if (vpnConfig.legacy) {
            return this.mContext.getString(C1777R.string.legacy_vpn_name);
        }
        String str = vpnConfig.user;
        try {
            Context context = this.mContext;
            return VpnConfig.getVpnLabel(context.createPackageContextAsUser(context.getPackageName(), 0, userHandle), str).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SecurityController", "Package " + str + " is not present", e);
            return null;
        }
    }

    public final String getPrimaryVpnName() {
        VpnConfig vpnConfig = this.mCurrentVpns.get(this.mVpnUserId);
        if (vpnConfig != null) {
            return getNameForVpnConfig(vpnConfig, new UserHandle(this.mVpnUserId));
        }
        return null;
    }

    public final CharSequence getWorkProfileOrganizationName() {
        int workProfileUserId = getWorkProfileUserId(this.mCurrentUserId);
        if (workProfileUserId == -10000) {
            return null;
        }
        return this.mDevicePolicyManager.getOrganizationNameForUser(workProfileUserId);
    }

    public final int getWorkProfileUserId(int i) {
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    public final String getWorkProfileVpnName() {
        VpnConfig vpnConfig;
        int workProfileUserId = getWorkProfileUserId(this.mVpnUserId);
        if (workProfileUserId == -10000 || (vpnConfig = this.mCurrentVpns.get(workProfileUserId)) == null) {
            return null;
        }
        return getNameForVpnConfig(vpnConfig, UserHandle.of(workProfileUserId));
    }

    public final boolean hasCACertInCurrentUser() {
        Boolean bool = this.mHasCACerts.get(Integer.valueOf(this.mCurrentUserId));
        if (bool == null || !bool.booleanValue()) {
            return false;
        }
        return true;
    }

    public final boolean hasCACertInWorkProfile() {
        Boolean bool;
        int workProfileUserId = getWorkProfileUserId(this.mCurrentUserId);
        if (workProfileUserId == -10000 || (bool = this.mHasCACerts.get(Integer.valueOf(workProfileUserId))) == null || !bool.booleanValue()) {
            return false;
        }
        return true;
    }

    public final boolean hasWorkProfile() {
        if (getWorkProfileUserId(this.mCurrentUserId) != -10000) {
            return true;
        }
        return false;
    }

    public final boolean isDeviceManaged() {
        return this.mDevicePolicyManager.isDeviceManaged();
    }

    public final boolean isNetworkLoggingEnabled() {
        return this.mDevicePolicyManager.isNetworkLoggingEnabled((ComponentName) null);
    }

    public final boolean isParentalControlsEnabled() {
        if (this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(new UserHandle(this.mCurrentUserId)) != null) {
            return true;
        }
        return false;
    }

    public final boolean isProfileOwnerOfOrganizationOwnedDevice() {
        return this.mDevicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
    }

    public final boolean isVpnBranded() {
        String str;
        VpnConfig vpnConfig = this.mCurrentVpns.get(this.mVpnUserId);
        if (vpnConfig == null) {
            return false;
        }
        if (vpnConfig.legacy) {
            str = null;
        } else {
            str = vpnConfig.user;
        }
        if (str == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 128);
            if (applicationInfo == null || applicationInfo.metaData == null) {
                return false;
            }
            if (!applicationInfo.isSystemApp()) {
                return false;
            }
            return applicationInfo.metaData.getBoolean("com.android.systemui.IS_BRANDED", false);
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isVpnEnabled() {
        for (int i : this.mUserManager.getProfileIdsWithDisabled(this.mVpnUserId)) {
            if (this.mCurrentVpns.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean isWorkProfileOn() {
        UserHandle of = UserHandle.of(getWorkProfileUserId(this.mCurrentUserId));
        if (of == null || this.mUserManager.isQuietModeEnabled(of)) {
            return false;
        }
        return true;
    }

    public final void onUserSwitched(int i) {
        this.mCurrentUserId = i;
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isRestricted()) {
            this.mVpnUserId = userInfo.restrictedProfileParentId;
        } else {
            this.mVpnUserId = this.mCurrentUserId;
        }
        fireCallbacks();
    }

    public final void removeCallback(Object obj) {
        SecurityController.SecurityControllerCallback securityControllerCallback = (SecurityController.SecurityControllerCallback) obj;
        synchronized (this.mCallbacks) {
            if (securityControllerCallback != null) {
                if (DEBUG) {
                    Log.d("SecurityController", "removeCallback " + securityControllerCallback);
                }
                this.mCallbacks.remove(securityControllerCallback);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateState  reason: not valid java name */
    public static void m254$$Nest$mupdateState(SecurityControllerImpl securityControllerImpl) {
        LegacyVpnInfo legacyVpnInfo;
        Objects.requireNonNull(securityControllerImpl);
        SparseArray<VpnConfig> sparseArray = new SparseArray<>();
        for (UserInfo userInfo : securityControllerImpl.mUserManager.getUsers()) {
            VpnConfig vpnConfig = securityControllerImpl.mVpnManager.getVpnConfig(userInfo.id);
            if (vpnConfig != null && (!vpnConfig.legacy || ((legacyVpnInfo = securityControllerImpl.mVpnManager.getLegacyVpnInfo(userInfo.id)) != null && legacyVpnInfo.state == 3))) {
                sparseArray.put(userInfo.id, vpnConfig);
            }
        }
        securityControllerImpl.mCurrentVpns = sparseArray;
    }

    public SecurityControllerImpl(Context context, Handler handler, BroadcastDispatcher broadcastDispatcher, Executor executor, DumpManager dumpManager) {
        super(broadcastDispatcher);
        C16391 r0 = new ConnectivityManager.NetworkCallback() {
            public final void onAvailable(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onAvailable ");
                    m.append(network.getNetId());
                    Log.d("SecurityController", m.toString());
                }
                SecurityControllerImpl.m254$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }

            public final void onLost(Network network) {
                if (SecurityControllerImpl.DEBUG) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onLost ");
                    m.append(network.getNetId());
                    Log.d("SecurityController", m.toString());
                }
                SecurityControllerImpl.m254$$Nest$mupdateState(SecurityControllerImpl.this);
                SecurityControllerImpl.this.fireCallbacks();
            }
        };
        this.mNetworkCallback = r0;
        C16402 r1 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                int intExtra;
                if ("android.security.action.TRUST_STORE_CHANGED".equals(intent.getAction())) {
                    SecurityControllerImpl securityControllerImpl = SecurityControllerImpl.this;
                    int sendingUserId = getSendingUserId();
                    Objects.requireNonNull(securityControllerImpl);
                    securityControllerImpl.mBgExecutor.execute(new SecurityControllerImpl$$ExternalSyntheticLambda0(securityControllerImpl, sendingUserId));
                } else if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction()) && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) != -10000) {
                    SecurityControllerImpl securityControllerImpl2 = SecurityControllerImpl.this;
                    Objects.requireNonNull(securityControllerImpl2);
                    securityControllerImpl2.mBgExecutor.execute(new SecurityControllerImpl$$ExternalSyntheticLambda0(securityControllerImpl2, intExtra));
                }
            }
        };
        this.mBroadcastReceiver = r1;
        this.mContext = context;
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mVpnManager = (VpnManager) context.getSystemService(VpnManager.class);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mBgExecutor = executor;
        dumpManager.registerDumpable("SecurityControllerImpl", this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.security.action.TRUST_STORE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        broadcastDispatcher.registerReceiverWithHandler(r1, intentFilter, handler, UserHandle.ALL);
        ((ConnectivityManager) context.getSystemService("connectivity")).registerNetworkCallback(REQUEST, r0);
        onUserSwitched(ActivityManager.getCurrentUser());
        startTracking();
    }
}
