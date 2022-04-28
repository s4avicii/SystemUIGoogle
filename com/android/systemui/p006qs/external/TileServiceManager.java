package com.android.systemui.p006qs.external;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.external.TileServiceManager */
public final class TileServiceManager {
    public static final String PREFS_FILE = "CustomTileModes";
    public boolean mBindAllowed;
    public boolean mBindRequested;
    public boolean mBound;
    public final Handler mHandler;
    public boolean mJustBound;
    public final Runnable mJustBoundOver = new Runnable() {
        public final void run() {
            TileServiceManager tileServiceManager = TileServiceManager.this;
            tileServiceManager.mJustBound = false;
            tileServiceManager.mServices.recalculateBindAllowance();
        }
    };
    public long mLastUpdate;
    public boolean mPendingBind = true;
    public int mPriority;
    public final TileServices mServices;
    public boolean mShowingDialog;
    public boolean mStarted = false;
    public final TileLifecycleManager mStateManager;
    public final C10241 mUnbind = new Runnable() {
        public final void run() {
            TileServiceManager tileServiceManager = TileServiceManager.this;
            boolean z = tileServiceManager.mBound;
            if (z && !tileServiceManager.mBindRequested) {
                if (!z) {
                    Log.e("TileServiceManager", "Service not bound");
                    return;
                }
                tileServiceManager.mBound = false;
                tileServiceManager.mJustBound = false;
                tileServiceManager.mStateManager.setBindService(false);
            }
        }
    };
    public final C10263 mUninstallReceiver;
    public final UserTracker mUserTracker;

    public final void bindService() {
        if (this.mBound) {
            Log.e("TileServiceManager", "Service already bound");
            return;
        }
        this.mPendingBind = true;
        this.mBound = true;
        this.mJustBound = true;
        this.mHandler.postDelayed(this.mJustBoundOver, 5000);
        this.mStateManager.setBindService(true);
    }

    public final boolean isActiveTile() {
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        Objects.requireNonNull(tileLifecycleManager);
        try {
            PackageManagerAdapter packageManagerAdapter = tileLifecycleManager.mPackageManagerAdapter;
            ComponentName component = tileLifecycleManager.mIntent.getComponent();
            Objects.requireNonNull(packageManagerAdapter);
            Bundle bundle = packageManagerAdapter.mPackageManager.getServiceInfo(component, 794752).metaData;
            if (bundle == null || !bundle.getBoolean("android.service.quicksettings.ACTIVE_TILE", false)) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isToggleableTile() {
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        Objects.requireNonNull(tileLifecycleManager);
        try {
            PackageManagerAdapter packageManagerAdapter = tileLifecycleManager.mPackageManagerAdapter;
            ComponentName component = tileLifecycleManager.mIntent.getComponent();
            Objects.requireNonNull(packageManagerAdapter);
            Bundle bundle = packageManagerAdapter.mPackageManager.getServiceInfo(component, 794752).metaData;
            if (bundle == null || !bundle.getBoolean("android.service.quicksettings.TOGGLEABLE_TILE", false)) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void setBindAllowed(boolean z) {
        boolean z2;
        if (this.mBindAllowed != z) {
            this.mBindAllowed = z;
            if (z || !(z2 = this.mBound)) {
                if (z && this.mBindRequested && !this.mBound) {
                    bindService();
                }
            } else if (!z2) {
                Log.e("TileServiceManager", "Service not bound");
            } else {
                this.mBound = false;
                this.mJustBound = false;
                this.mStateManager.setBindService(false);
            }
        }
    }

    public final void setBindRequested(boolean z) {
        if (this.mBindRequested != z) {
            this.mBindRequested = z;
            if (!this.mBindAllowed || !z || this.mBound) {
                this.mServices.recalculateBindAllowance();
            } else {
                this.mHandler.removeCallbacks(this.mUnbind);
                bindService();
            }
            if (this.mBound && !this.mBindRequested) {
                this.mHandler.postDelayed(this.mUnbind, 30000);
            }
        }
    }

    public TileServiceManager(TileServices tileServices, Handler handler, UserTracker userTracker, TileLifecycleManager tileLifecycleManager) {
        C10263 r2 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                    TileLifecycleManager tileLifecycleManager = TileServiceManager.this.mStateManager;
                    Objects.requireNonNull(tileLifecycleManager);
                    ComponentName component = tileLifecycleManager.mIntent.getComponent();
                    if (Objects.equals(encodedSchemeSpecificPart, component.getPackageName())) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE");
                            intent2.setPackage(encodedSchemeSpecificPart);
                            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentServicesAsUser(intent2, 0, TileServiceManager.this.mUserTracker.getUserId())) {
                                if (Objects.equals(resolveInfo.serviceInfo.packageName, component.getPackageName()) && Objects.equals(resolveInfo.serviceInfo.name, component.getClassName())) {
                                    return;
                                }
                            }
                        }
                        TileServices tileServices = TileServiceManager.this.mServices;
                        Objects.requireNonNull(tileServices);
                        QSTileHost qSTileHost = tileServices.mHost;
                        Objects.requireNonNull(qSTileHost);
                        ArrayList arrayList = new ArrayList(qSTileHost.mTileSpecs);
                        arrayList.remove(CustomTile.toSpec(component));
                        qSTileHost.changeTiles(qSTileHost.mTileSpecs, arrayList);
                    }
                }
            }
        };
        this.mUninstallReceiver = r2;
        this.mServices = tileServices;
        this.mHandler = handler;
        this.mStateManager = tileLifecycleManager;
        this.mUserTracker = userTracker;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        Objects.requireNonNull(tileServices);
        tileServices.mContext.registerReceiverAsUser(r2, userTracker.getUserHandle(), intentFilter, (String) null, handler, 2);
    }
}
