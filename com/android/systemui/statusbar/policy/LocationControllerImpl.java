package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import java.util.ArrayList;
import java.util.Objects;

public final class LocationControllerImpl extends BroadcastReceiver implements LocationController, AppOpsController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppOpsController mAppOpsController;
    public boolean mAreActiveLocationRequests;
    public final Handler mBackgroundHandler;
    public final BootCompleteCache mBootCompleteCache;
    public final C16321 mContentObserver;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final C1633H mHandler;
    public final PackageManager mPackageManager;
    public final SecureSettings mSecureSettings;
    public boolean mShouldDisplayAllAccesses = DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false);
    public boolean mShowSystemAccessesFlag;
    public boolean mShowSystemAccessesSetting;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;

    /* renamed from: com.android.systemui.statusbar.policy.LocationControllerImpl$H */
    public final class C1633H extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ArrayList<LocationController.LocationChangeCallback> mSettingsChangeCallbacks = new ArrayList<>();

        public C1633H(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Utils.safeForeach(this.mSettingsChangeCallbacks, new LocationControllerImpl$H$$ExternalSyntheticLambda0(LocationControllerImpl.this.isLocationEnabled(), 0));
            } else if (i == 2) {
                Utils.safeForeach(this.mSettingsChangeCallbacks, new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(this, 3));
            } else if (i == 3) {
                this.mSettingsChangeCallbacks.add((LocationController.LocationChangeCallback) message.obj);
            } else if (i == 4) {
                this.mSettingsChangeCallbacks.remove((LocationController.LocationChangeCallback) message.obj);
            }
        }
    }

    public enum LocationIndicatorEvent implements UiEventLogger.UiEventEnum {
        LOCATION_INDICATOR_MONITOR_HIGH_POWER(935),
        LOCATION_INDICATOR_SYSTEM_APP(936),
        LOCATION_INDICATOR_NON_SYSTEM_APP(937);
        
        private final int mId;

        /* access modifiers changed from: public */
        LocationIndicatorEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void addCallback(Object obj) {
        this.mHandler.obtainMessage(3, (LocationController.LocationChangeCallback) obj).sendToTarget();
        this.mHandler.sendEmptyMessage(1);
    }

    public boolean areActiveHighPowerLocationRequests() {
        ArrayList activeAppOps = this.mAppOpsController.getActiveAppOps();
        int size = activeAppOps.size();
        for (int i = 0; i < size; i++) {
            AppOpItem appOpItem = (AppOpItem) activeAppOps.get(i);
            Objects.requireNonNull(appOpItem);
            if (appOpItem.mCode == 42) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void areActiveLocationRequests() {
        /*
            r18 = this;
            r0 = r18
            boolean r1 = r0.mShouldDisplayAllAccesses
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            boolean r1 = r0.mAreActiveLocationRequests
            boolean r2 = r0.mShowSystemAccessesFlag
            r4 = 1
            if (r2 != 0) goto L_0x0015
            boolean r2 = r0.mShowSystemAccessesSetting
            if (r2 == 0) goto L_0x0013
            goto L_0x0015
        L_0x0013:
            r2 = 0
            goto L_0x0016
        L_0x0015:
            r2 = r4
        L_0x0016:
            com.android.systemui.appops.AppOpsController r5 = r0.mAppOpsController
            java.util.ArrayList r5 = r5.getActiveAppOps()
            com.android.systemui.settings.UserTracker r6 = r0.mUserTracker
            java.util.List r6 = r6.getUserProfiles()
            int r7 = r5.size()
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x002a:
            if (r8 >= r7) goto L_0x00b2
            java.lang.Object r12 = r5.get(r8)
            com.android.systemui.appops.AppOpItem r12 = (com.android.systemui.appops.AppOpItem) r12
            java.util.Objects.requireNonNull(r12)
            int r12 = r12.mCode
            if (r12 == r4) goto L_0x0046
            java.lang.Object r12 = r5.get(r8)
            com.android.systemui.appops.AppOpItem r12 = (com.android.systemui.appops.AppOpItem) r12
            java.util.Objects.requireNonNull(r12)
            int r12 = r12.mCode
            if (r12 != 0) goto L_0x00ad
        L_0x0046:
            java.lang.Object r12 = r5.get(r8)
            com.android.systemui.appops.AppOpItem r12 = (com.android.systemui.appops.AppOpItem) r12
            java.util.Objects.requireNonNull(r12)
            int r13 = r12.mCode
            java.lang.String r13 = android.app.AppOpsManager.opToPermission(r13)
            int r14 = r12.mUid
            android.os.UserHandle r14 = android.os.UserHandle.getUserHandleForUid(r14)
            int r15 = r6.size()
            r3 = 0
            r16 = 0
        L_0x0062:
            if (r3 >= r15) goto L_0x007a
            java.lang.Object r17 = r6.get(r3)
            android.content.pm.UserInfo r17 = (android.content.pm.UserInfo) r17
            android.os.UserHandle r4 = r17.getUserHandle()
            boolean r4 = r4.equals(r14)
            if (r4 == 0) goto L_0x0076
            r16 = 1
        L_0x0076:
            int r3 = r3 + 1
            r4 = 1
            goto L_0x0062
        L_0x007a:
            if (r16 != 0) goto L_0x007d
            goto L_0x009b
        L_0x007d:
            android.content.pm.PackageManager r3 = r0.mPackageManager
            java.lang.String r4 = r12.mPackageName
            int r3 = r3.getPermissionFlags(r13, r4, r14)
            android.content.Context r4 = r0.mContext
            r14 = -1
            int r15 = r12.mUid
            java.lang.String r12 = r12.mPackageName
            int r4 = android.content.PermissionChecker.checkPermissionForPreflight(r4, r13, r14, r15, r12)
            if (r4 != 0) goto L_0x0097
            r3 = r3 & 256(0x100, float:3.59E-43)
            if (r3 != 0) goto L_0x009d
            goto L_0x009b
        L_0x0097:
            r3 = r3 & 512(0x200, float:7.175E-43)
            if (r3 != 0) goto L_0x009d
        L_0x009b:
            r3 = 1
            goto L_0x009e
        L_0x009d:
            r3 = 0
        L_0x009e:
            if (r3 == 0) goto L_0x00a2
            r10 = 1
            goto L_0x00a3
        L_0x00a2:
            r11 = 1
        L_0x00a3:
            if (r2 != 0) goto L_0x00ac
            if (r9 != 0) goto L_0x00ac
            if (r3 != 0) goto L_0x00aa
            goto L_0x00ac
        L_0x00aa:
            r9 = 0
            goto L_0x00ad
        L_0x00ac:
            r9 = 1
        L_0x00ad:
            int r8 = r8 + 1
            r4 = 1
            goto L_0x002a
        L_0x00b2:
            boolean r2 = r18.areActiveHighPowerLocationRequests()
            r0.mAreActiveLocationRequests = r9
            if (r9 == r1) goto L_0x00c0
            com.android.systemui.statusbar.policy.LocationControllerImpl$H r3 = r0.mHandler
            r4 = 2
            r3.sendEmptyMessage(r4)
        L_0x00c0:
            if (r1 != 0) goto L_0x00e3
            if (r2 != 0) goto L_0x00c8
            if (r10 != 0) goto L_0x00c8
            if (r11 == 0) goto L_0x00e3
        L_0x00c8:
            if (r2 == 0) goto L_0x00d1
            com.android.internal.logging.UiEventLogger r1 = r0.mUiEventLogger
            com.android.systemui.statusbar.policy.LocationControllerImpl$LocationIndicatorEvent r2 = com.android.systemui.statusbar.policy.LocationControllerImpl.LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER
            r1.log(r2)
        L_0x00d1:
            if (r10 == 0) goto L_0x00da
            com.android.internal.logging.UiEventLogger r1 = r0.mUiEventLogger
            com.android.systemui.statusbar.policy.LocationControllerImpl$LocationIndicatorEvent r2 = com.android.systemui.statusbar.policy.LocationControllerImpl.LocationIndicatorEvent.LOCATION_INDICATOR_SYSTEM_APP
            r1.log(r2)
        L_0x00da:
            if (r11 == 0) goto L_0x00e3
            com.android.internal.logging.UiEventLogger r0 = r0.mUiEventLogger
            com.android.systemui.statusbar.policy.LocationControllerImpl$LocationIndicatorEvent r1 = com.android.systemui.statusbar.policy.LocationControllerImpl.LocationIndicatorEvent.LOCATION_INDICATOR_NON_SYSTEM_APP
            r0.log(r1)
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.LocationControllerImpl.areActiveLocationRequests():void");
    }

    public final boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) this.mContext.getSystemService("location");
        if (!this.mBootCompleteCache.isBootComplete() || !locationManager.isLocationEnabledForUser(this.mUserTracker.getUserHandle())) {
            return false;
        }
        return true;
    }

    public final void removeCallback(Object obj) {
        this.mHandler.obtainMessage(4, (LocationController.LocationChangeCallback) obj).sendToTarget();
    }

    public final boolean setLocationEnabled(boolean z) {
        int userId = this.mUserTracker.getUserId();
        if (((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_share_location", UserHandle.of(userId))) {
            return false;
        }
        Context context = this.mContext;
        Settings.Secure.putIntForUser(context.getContentResolver(), "location_changer", 2, userId);
        ((LocationManager) context.getSystemService(LocationManager.class)).setLocationEnabledForUser(z, UserHandle.of(userId));
        return true;
    }

    public final void updateActiveLocationRequests() {
        if (this.mShouldDisplayAllAccesses) {
            this.mBackgroundHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda10(this, 6));
            return;
        }
        boolean z = this.mAreActiveLocationRequests;
        boolean areActiveHighPowerLocationRequests = areActiveHighPowerLocationRequests();
        this.mAreActiveLocationRequests = areActiveHighPowerLocationRequests;
        if (areActiveHighPowerLocationRequests != z) {
            this.mHandler.sendEmptyMessage(2);
            if (this.mAreActiveLocationRequests) {
                this.mUiEventLogger.log(LocationIndicatorEvent.LOCATION_INDICATOR_MONITOR_HIGH_POWER);
            }
        }
    }

    public LocationControllerImpl(Context context, AppOpsController appOpsController, DeviceConfigProxy deviceConfigProxy, Looper looper, Handler handler, BroadcastDispatcher broadcastDispatcher, BootCompleteCache bootCompleteCache, UserTracker userTracker, PackageManager packageManager, UiEventLogger uiEventLogger, SecureSettings secureSettings) {
        this.mContext = context;
        this.mAppOpsController = appOpsController;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mBootCompleteCache = bootCompleteCache;
        C1633H h = new C1633H(looper);
        this.mHandler = h;
        this.mUserTracker = userTracker;
        this.mUiEventLogger = uiEventLogger;
        this.mSecureSettings = secureSettings;
        this.mBackgroundHandler = handler;
        this.mPackageManager = packageManager;
        Objects.requireNonNull(deviceConfigProxy);
        Objects.requireNonNull(deviceConfigProxy);
        this.mShowSystemAccessesFlag = DeviceConfig.getBoolean("privacy", "location_indicators_show_system", false);
        this.mShowSystemAccessesSetting = secureSettings.getInt("locationShowSystemOps", 0) != 1 ? false : true;
        C16321 r3 = new ContentObserver(handler) {
            public final void onChange(boolean z) {
                LocationControllerImpl locationControllerImpl = LocationControllerImpl.this;
                Objects.requireNonNull(locationControllerImpl);
                boolean z2 = false;
                if (locationControllerImpl.mSecureSettings.getInt("locationShowSystemOps", 0) == 1) {
                    z2 = true;
                }
                locationControllerImpl.mShowSystemAccessesSetting = z2;
            }
        };
        this.mContentObserver = r3;
        secureSettings.registerContentObserver(r3);
        Objects.requireNonNull(handler);
        DeviceConfig.addOnPropertiesChangedListener("privacy", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), new LocationControllerImpl$$ExternalSyntheticLambda0(this, 0));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.location.MODE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(this, intentFilter, h, UserHandle.ALL);
        appOpsController.addCallback(new int[]{0, 1, 42}, this);
        handler.post(new AccessPoint$$ExternalSyntheticLambda0(this, 9));
    }

    public final void onReceive(Context context, Intent intent) {
        if ("android.location.MODE_CHANGED".equals(intent.getAction())) {
            C1633H h = this.mHandler;
            int i = C1633H.$r8$clinit;
            Objects.requireNonNull(h);
            Utils.safeForeach(h.mSettingsChangeCallbacks, new LocationControllerImpl$H$$ExternalSyntheticLambda0(LocationControllerImpl.this.isLocationEnabled(), 0));
        }
    }

    public final void onActiveStateChanged(int i, int i2, String str, boolean z) {
        updateActiveLocationRequests();
    }

    public final boolean isLocationActive() {
        return this.mAreActiveLocationRequests;
    }
}
