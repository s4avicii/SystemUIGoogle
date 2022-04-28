package com.android.systemui.p006qs.external;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.Tile;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda3;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.external.TileServices */
public final class TileServices extends IQSService.Stub {
    public static final C10313 SERVICE_SORT = new Comparator<TileServiceManager>() {
        public final int compare(Object obj, Object obj2) {
            TileServiceManager tileServiceManager = (TileServiceManager) obj;
            TileServiceManager tileServiceManager2 = (TileServiceManager) obj2;
            Objects.requireNonNull(tileServiceManager);
            int i = tileServiceManager.mPriority;
            Objects.requireNonNull(tileServiceManager2);
            return -Integer.compare(i, tileServiceManager2.mPriority);
        }
    };
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final Handler mHandler;
    public final QSTileHost mHost;
    public final KeyguardStateController mKeyguardStateController;
    public final Handler mMainHandler;
    public int mMaxBound = 3;
    public final C10302 mRequestListeningReceiver;
    public final ArrayMap<CustomTile, TileServiceManager> mServices = new ArrayMap<>();
    public final ArrayMap<ComponentName, CustomTile> mTiles = new ArrayMap<>();
    public final ArrayMap<IBinder, CustomTile> mTokenMap = new ArrayMap<>();
    public final UserTracker mUserTracker;

    public final CustomTile getTileForToken(IBinder iBinder) {
        CustomTile customTile;
        synchronized (this.mServices) {
            customTile = this.mTokenMap.get(iBinder);
        }
        return customTile;
    }

    public final boolean isLocked() {
        return this.mKeyguardStateController.isShowing();
    }

    public final boolean isSecure() {
        if (!this.mKeyguardStateController.isMethodSecure() || !this.mKeyguardStateController.isShowing()) {
            return false;
        }
        return true;
    }

    public final void recalculateBindAllowance() {
        ArrayList arrayList;
        boolean contains;
        synchronized (this.mServices) {
            arrayList = new ArrayList(this.mServices.values());
        }
        int size = arrayList.size();
        if (size > this.mMaxBound) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                TileServiceManager tileServiceManager = (TileServiceManager) arrayList.get(i);
                Objects.requireNonNull(tileServiceManager);
                TileLifecycleManager tileLifecycleManager = tileServiceManager.mStateManager;
                Objects.requireNonNull(tileLifecycleManager);
                synchronized (tileLifecycleManager.mQueuedMessages) {
                    contains = tileLifecycleManager.mQueuedMessages.contains(2);
                }
                if (contains) {
                    tileServiceManager.mPriority = Integer.MAX_VALUE;
                } else if (tileServiceManager.mShowingDialog) {
                    tileServiceManager.mPriority = 2147483646;
                } else if (tileServiceManager.mJustBound) {
                    tileServiceManager.mPriority = 2147483645;
                } else if (!tileServiceManager.mBindRequested) {
                    tileServiceManager.mPriority = Integer.MIN_VALUE;
                } else {
                    long j = currentTimeMillis - tileServiceManager.mLastUpdate;
                    if (j > 2147483644) {
                        tileServiceManager.mPriority = 2147483644;
                    } else {
                        tileServiceManager.mPriority = (int) j;
                    }
                }
            }
            Collections.sort(arrayList, SERVICE_SORT);
        }
        int i2 = 0;
        while (i2 < this.mMaxBound && i2 < size) {
            ((TileServiceManager) arrayList.get(i2)).setBindAllowed(true);
            i2++;
        }
        while (i2 < size) {
            ((TileServiceManager) arrayList.get(i2)).setBindAllowed(false);
            i2++;
        }
    }

    public final void verifyCaller(CustomTile customTile) {
        try {
            if (Binder.getCallingUid() != this.mContext.getPackageManager().getPackageUidAsUser(customTile.mComponent.getPackageName(), Binder.getCallingUserHandle().getIdentifier())) {
                throw new SecurityException("Component outside caller's uid");
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new SecurityException(e);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:23|24|25|26|27|38) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0061 */
    /* renamed from: -$$Nest$mrequestListening  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m215$$Nest$mrequestListening(com.android.systemui.p006qs.external.TileServices r3, android.content.ComponentName r4) {
        /*
            java.util.Objects.requireNonNull(r3)
            android.util.ArrayMap<com.android.systemui.qs.external.CustomTile, com.android.systemui.qs.external.TileServiceManager> r0 = r3.mServices
            monitor-enter(r0)
            android.util.ArrayMap<com.android.systemui.qs.external.CustomTile, com.android.systemui.qs.external.TileServiceManager> r1 = r3.mServices     // Catch:{ all -> 0x0066 }
            monitor-enter(r1)     // Catch:{ all -> 0x0066 }
            android.util.ArrayMap<android.content.ComponentName, com.android.systemui.qs.external.CustomTile> r2 = r3.mTiles     // Catch:{ all -> 0x0063 }
            java.lang.Object r2 = r2.get(r4)     // Catch:{ all -> 0x0063 }
            com.android.systemui.qs.external.CustomTile r2 = (com.android.systemui.p006qs.external.CustomTile) r2     // Catch:{ all -> 0x0063 }
            monitor-exit(r1)     // Catch:{ all -> 0x0063 }
            if (r2 != 0) goto L_0x002c
            java.lang.String r3 = "TileServices"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
            r1.<init>()     // Catch:{ all -> 0x0066 }
            java.lang.String r2 = "Couldn't find tile for "
            r1.append(r2)     // Catch:{ all -> 0x0066 }
            r1.append(r4)     // Catch:{ all -> 0x0066 }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x0066 }
            android.util.Log.d(r3, r4)     // Catch:{ all -> 0x0066 }
            monitor-exit(r0)     // Catch:{ all -> 0x0066 }
            goto L_0x0062
        L_0x002c:
            android.util.ArrayMap<com.android.systemui.qs.external.CustomTile, com.android.systemui.qs.external.TileServiceManager> r3 = r3.mServices     // Catch:{ all -> 0x0066 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x0066 }
            com.android.systemui.qs.external.TileServiceManager r3 = (com.android.systemui.p006qs.external.TileServiceManager) r3     // Catch:{ all -> 0x0066 }
            if (r3 != 0) goto L_0x0050
            java.lang.String r3 = "TileServices"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
            r4.<init>()     // Catch:{ all -> 0x0066 }
            java.lang.String r1 = "No TileServiceManager found in requestListening for tile "
            r4.append(r1)     // Catch:{ all -> 0x0066 }
            java.lang.String r1 = r2.mTileSpec     // Catch:{ all -> 0x0066 }
            r4.append(r1)     // Catch:{ all -> 0x0066 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0066 }
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0066 }
            monitor-exit(r0)     // Catch:{ all -> 0x0066 }
            goto L_0x0062
        L_0x0050:
            boolean r4 = r3.isActiveTile()     // Catch:{ all -> 0x0066 }
            if (r4 != 0) goto L_0x0058
            monitor-exit(r0)     // Catch:{ all -> 0x0066 }
            goto L_0x0062
        L_0x0058:
            r4 = 1
            r3.setBindRequested(r4)     // Catch:{ all -> 0x0066 }
            com.android.systemui.qs.external.TileLifecycleManager r3 = r3.mStateManager     // Catch:{ RemoteException -> 0x0061 }
            r3.onStartListening()     // Catch:{ RemoteException -> 0x0061 }
        L_0x0061:
            monitor-exit(r0)     // Catch:{ all -> 0x0066 }
        L_0x0062:
            return
        L_0x0063:
            r3 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0063 }
            throw r3     // Catch:{ all -> 0x0066 }
        L_0x0066:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0066 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.external.TileServices.m215$$Nest$mrequestListening(com.android.systemui.qs.external.TileServices, android.content.ComponentName):void");
    }

    public TileServices(QSTileHost qSTileHost, Looper looper, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, KeyguardStateController keyguardStateController) {
        C10302 r0 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("android.service.quicksettings.action.REQUEST_LISTENING".equals(intent.getAction())) {
                    try {
                        TileServices.m215$$Nest$mrequestListening(TileServices.this, (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME"));
                    } catch (ClassCastException e) {
                        Log.e("TileServices", "Bad component name", e);
                    }
                }
            }
        };
        this.mRequestListeningReceiver = r0;
        this.mHost = qSTileHost;
        this.mKeyguardStateController = keyguardStateController;
        Objects.requireNonNull(qSTileHost);
        this.mContext = qSTileHost.mContext;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mHandler = new Handler(looper);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mUserTracker = userTracker;
        broadcastDispatcher.registerReceiver(r0, new IntentFilter("android.service.quicksettings.action.REQUEST_LISTENING"), (Executor) null, UserHandle.ALL);
    }

    public final Tile getTile(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken == null) {
            return null;
        }
        verifyCaller(tileForToken);
        tileForToken.updateDefaultTileAndIcon();
        return tileForToken.mTile;
    }

    public final void onDialogHidden(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.mShowingDialog = false;
            tileForToken.mIsShowingDialog = false;
            try {
                tileForToken.mWindowManager.removeWindowToken(tileForToken.mToken, 0);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onShowDialog(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.mIsShowingDialog = true;
            QSTileHost qSTileHost = this.mHost;
            Objects.requireNonNull(qSTileHost);
            qSTileHost.mStatusBarOptional.ifPresent(QSTileHost$$ExternalSyntheticLambda3.INSTANCE);
            TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.mShowingDialog = true;
        }
    }

    public final void onStartActivity(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            QSTileHost qSTileHost = this.mHost;
            Objects.requireNonNull(qSTileHost);
            qSTileHost.mStatusBarOptional.ifPresent(QSTileHost$$ExternalSyntheticLambda3.INSTANCE);
        }
    }

    public final void onStartSuccessful(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
                if (tileServiceManager != null) {
                    if (tileServiceManager.mStarted) {
                        tileServiceManager.mPendingBind = false;
                        tileForToken.refreshState((Object) null);
                        return;
                    }
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.mComponent, new IllegalStateException());
            }
        }
    }

    public final void startUnlockAndRun(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.mActivityStarter.postQSRunnableDismissingKeyguard(new AccessPoint$$ExternalSyntheticLambda0(tileForToken, 5));
        }
    }

    public final void updateQsTile(Tile tile, IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
                if (tileServiceManager != null) {
                    if (tileServiceManager.mStarted) {
                        tileServiceManager.mPendingBind = false;
                        tileServiceManager.mLastUpdate = System.currentTimeMillis();
                        if (tileServiceManager.mBound && tileServiceManager.isActiveTile()) {
                            tileServiceManager.mStateManager.onStopListening();
                            tileServiceManager.setBindRequested(false);
                        }
                        tileServiceManager.mServices.recalculateBindAllowance();
                        tileForToken.mHandler.post(new CustomTile$$ExternalSyntheticLambda0(tileForToken, tile, 0));
                        tileForToken.refreshState((Object) null);
                        return;
                    }
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.mComponent, new IllegalStateException());
            }
        }
    }

    public final void updateStatusIcon(IBinder iBinder, Icon icon, String str) {
        final StatusBarIcon statusBarIcon;
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            try {
                final ComponentName componentName = tileForToken.mComponent;
                String packageName = componentName.getPackageName();
                UserHandle callingUserHandle = Binder.getCallingUserHandle();
                if (this.mContext.getPackageManager().getPackageInfoAsUser(packageName, 0, callingUserHandle.getIdentifier()).applicationInfo.isSystemApp()) {
                    if (icon != null) {
                        statusBarIcon = new StatusBarIcon(callingUserHandle, packageName, icon, 0, 0, str);
                    } else {
                        statusBarIcon = null;
                    }
                    this.mMainHandler.post(new Runnable() {
                        public final void run() {
                            QSTileHost qSTileHost = TileServices.this.mHost;
                            Objects.requireNonNull(qSTileHost);
                            StatusBarIconController statusBarIconController = qSTileHost.mIconController;
                            statusBarIconController.setIcon(componentName.getClassName(), statusBarIcon);
                            statusBarIconController.setExternalIcon(componentName.getClassName());
                        }
                    });
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }
}
