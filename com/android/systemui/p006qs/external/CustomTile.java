package com.android.systemui.p006qs.external;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.quicksettings.Tile;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.widget.Switch;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.external.TileLifecycleManager;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import dagger.Lazy;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.android.systemui.qs.external.CustomTile */
public final class CustomTile extends QSTileImpl<QSTile.State> implements TileLifecycleManager.TileChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ComponentName mComponent;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public Icon mDefaultIcon;
    public CharSequence mDefaultLabel;
    public final AtomicBoolean mInitialDefaultIconFetched = new AtomicBoolean(false);
    public boolean mIsShowingDialog;
    public boolean mIsTokenGranted;
    public final TileServiceKey mKey;
    public boolean mListening;
    public final TileLifecycleManager mService;
    public final TileServiceManager mServiceManager;
    public final Tile mTile;
    public final TileServices mTileServices;
    public final Binder mToken = new Binder();
    public final int mUser;
    public final Context mUserContext;
    public final IWindowManager mWindowManager;

    /* renamed from: com.android.systemui.qs.external.CustomTile$Builder */
    public static class Builder {
        public final ActivityStarter mActivityStarter;
        public final Looper mBackgroundLooper;
        public final CustomTileStatePersister mCustomTileStatePersister;
        public final FalsingManager mFalsingManager;
        public final Handler mMainHandler;
        public final MetricsLogger mMetricsLogger;
        public final Lazy<QSHost> mQSHostLazy;
        public final QSLogger mQSLogger;
        public String mSpec = "";
        public final StatusBarStateController mStatusBarStateController;
        public TileServices mTileServices;
        public Context mUserContext;

        @VisibleForTesting
        public CustomTile build() {
            Objects.requireNonNull(this.mUserContext, "UserContext cannot be null");
            String str = this.mSpec;
            int i = CustomTile.$r8$clinit;
            if (str == null || !str.startsWith("custom(") || !str.endsWith(")")) {
                throw new IllegalArgumentException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("Bad custom tile spec: ", str));
            }
            String substring = str.substring(7, str.length() - 1);
            if (!substring.isEmpty()) {
                return new CustomTile(this.mQSHostLazy.get(), this.mBackgroundLooper, this.mMainHandler, this.mFalsingManager, this.mMetricsLogger, this.mStatusBarStateController, this.mActivityStarter, this.mQSLogger, substring, this.mUserContext, this.mCustomTileStatePersister, this.mTileServices);
            }
            throw new IllegalArgumentException("Empty custom tile spec action");
        }

        public Builder(Lazy<QSHost> lazy, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CustomTileStatePersister customTileStatePersister, TileServices tileServices) {
            this.mQSHostLazy = lazy;
            this.mBackgroundLooper = looper;
            this.mMainHandler = handler;
            this.mFalsingManager = falsingManager;
            this.mMetricsLogger = metricsLogger;
            this.mStatusBarStateController = statusBarStateController;
            this.mActivityStarter = activityStarter;
            this.mQSLogger = qSLogger;
            this.mCustomTileStatePersister = customTileStatePersister;
            this.mTileServices = tileServices;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CustomTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        TileServices tileServices2 = tileServices;
        this.mTileServices = tileServices2;
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        this.mComponent = unflattenFromString;
        this.mTile = new Tile();
        this.mUserContext = context;
        int userId = context.getUserId();
        this.mUser = userId;
        this.mKey = new TileServiceKey(unflattenFromString, userId);
        Objects.requireNonNull(tileServices);
        BroadcastDispatcher broadcastDispatcher = tileServices2.mBroadcastDispatcher;
        Handler handler2 = tileServices2.mHandler;
        UserTracker userTracker = tileServices2.mUserTracker;
        Context context2 = tileServices2.mContext;
        TileServiceManager tileServiceManager = new TileServiceManager(tileServices2, handler2, userTracker, new TileLifecycleManager(handler2, context2, tileServices, new PackageManagerAdapter(context2), broadcastDispatcher, new Intent().setComponent(unflattenFromString), userTracker.getUserHandle()));
        synchronized (tileServices2.mServices) {
            tileServices2.mServices.put(this, tileServiceManager);
            tileServices2.mTiles.put(unflattenFromString, this);
            ArrayMap<IBinder, CustomTile> arrayMap = tileServices2.mTokenMap;
            TileLifecycleManager tileLifecycleManager = tileServiceManager.mStateManager;
            Objects.requireNonNull(tileLifecycleManager);
            arrayMap.put(tileLifecycleManager.mToken, this);
        }
        tileServiceManager.mStarted = true;
        TileLifecycleManager tileLifecycleManager2 = tileServiceManager.mStateManager;
        Objects.requireNonNull(tileLifecycleManager2);
        ComponentName component = tileLifecycleManager2.mIntent.getComponent();
        TileServices tileServices3 = tileServiceManager.mServices;
        Objects.requireNonNull(tileServices3);
        Context context3 = tileServices3.mContext;
        if (!context3.getSharedPreferences("tiles_prefs", 0).getBoolean(component.flattenToString(), false)) {
            context3.getSharedPreferences("tiles_prefs", 0).edit().putBoolean(component.flattenToString(), true).commit();
            tileServiceManager.mStateManager.onTileAdded();
            TileLifecycleManager tileLifecycleManager3 = tileServiceManager.mStateManager;
            Objects.requireNonNull(tileLifecycleManager3);
            tileLifecycleManager3.mUnbindImmediate = true;
            tileLifecycleManager3.setBindService(true);
        }
        this.mServiceManager = tileServiceManager;
        this.mService = tileServiceManager.mStateManager;
        this.mCustomTileStatePersister = customTileStatePersister;
    }

    public final int getMetricsCategory() {
        return 268;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006b, code lost:
        if (java.util.Objects.equals(r5.getResPackage(), r7.getResPackage()) == false) goto L_0x006d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0070 A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0071 A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0076 A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0081 A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0086 A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00aa A[Catch:{ NameNotFoundException -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateDefaultTileAndIcon() {
        /*
            r10 = this;
            r0 = 0
            android.content.Context r1 = r10.mUserContext     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch:{ NameNotFoundException -> 0x00b0 }
            r2 = 786432(0xc0000, float:1.102026E-39)
            android.content.ComponentName r3 = r10.mComponent     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ NameNotFoundException -> 0x00b0 }
            r4 = 0
            android.content.pm.ApplicationInfo r3 = r1.getApplicationInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x00b0 }
            boolean r3 = r3.isSystemApp()     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r3 == 0) goto L_0x001d
            r2 = 786944(0xc0200, float:1.102743E-39)
        L_0x001d:
            android.content.ComponentName r3 = r10.mComponent     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.content.pm.ServiceInfo r2 = r1.getServiceInfo(r3, r2)     // Catch:{ NameNotFoundException -> 0x00b0 }
            int r3 = r2.icon     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r3 == 0) goto L_0x0028
            goto L_0x002c
        L_0x0028:
            android.content.pm.ApplicationInfo r3 = r2.applicationInfo     // Catch:{ NameNotFoundException -> 0x00b0 }
            int r3 = r3.icon     // Catch:{ NameNotFoundException -> 0x00b0 }
        L_0x002c:
            android.service.quicksettings.Tile r5 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.graphics.drawable.Icon r5 = r5.getIcon()     // Catch:{ NameNotFoundException -> 0x00b0 }
            r6 = 1
            if (r5 == 0) goto L_0x0073
            android.service.quicksettings.Tile r5 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.graphics.drawable.Icon r5 = r5.getIcon()     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.graphics.drawable.Icon r7 = r10.mDefaultIcon     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r5 != r7) goto L_0x0041
        L_0x003f:
            r5 = r6
            goto L_0x006e
        L_0x0041:
            if (r5 == 0) goto L_0x006d
            if (r7 != 0) goto L_0x0046
            goto L_0x006d
        L_0x0046:
            int r8 = r5.getType()     // Catch:{ NameNotFoundException -> 0x00b0 }
            r9 = 2
            if (r8 != r9) goto L_0x006d
            int r8 = r7.getType()     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r8 == r9) goto L_0x0054
            goto L_0x006d
        L_0x0054:
            int r8 = r5.getResId()     // Catch:{ NameNotFoundException -> 0x00b0 }
            int r9 = r7.getResId()     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r8 == r9) goto L_0x005f
            goto L_0x006d
        L_0x005f:
            java.lang.String r5 = r5.getResPackage()     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.String r7 = r7.getResPackage()     // Catch:{ NameNotFoundException -> 0x00b0 }
            boolean r5 = java.util.Objects.equals(r5, r7)     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r5 != 0) goto L_0x003f
        L_0x006d:
            r5 = r4
        L_0x006e:
            if (r5 == 0) goto L_0x0071
            goto L_0x0073
        L_0x0071:
            r5 = r4
            goto L_0x0074
        L_0x0073:
            r5 = r6
        L_0x0074:
            if (r3 == 0) goto L_0x0081
            android.content.ComponentName r7 = r10.mComponent     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x00b0 }
            android.graphics.drawable.Icon r3 = android.graphics.drawable.Icon.createWithResource(r7, r3)     // Catch:{ NameNotFoundException -> 0x00b0 }
            goto L_0x0082
        L_0x0081:
            r3 = r0
        L_0x0082:
            r10.mDefaultIcon = r3     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r5 == 0) goto L_0x008b
            android.service.quicksettings.Tile r5 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            r5.setIcon(r3)     // Catch:{ NameNotFoundException -> 0x00b0 }
        L_0x008b:
            android.service.quicksettings.Tile r3 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.CharSequence r3 = r3.getLabel()     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r3 == 0) goto L_0x00a1
            android.service.quicksettings.Tile r3 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.CharSequence r3 = r3.getLabel()     // Catch:{ NameNotFoundException -> 0x00b0 }
            java.lang.CharSequence r5 = r10.mDefaultLabel     // Catch:{ NameNotFoundException -> 0x00b0 }
            boolean r3 = android.text.TextUtils.equals(r3, r5)     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r3 == 0) goto L_0x00a2
        L_0x00a1:
            r4 = r6
        L_0x00a2:
            java.lang.CharSequence r1 = r2.loadLabel(r1)     // Catch:{ NameNotFoundException -> 0x00b0 }
            r10.mDefaultLabel = r1     // Catch:{ NameNotFoundException -> 0x00b0 }
            if (r4 == 0) goto L_0x00b4
            android.service.quicksettings.Tile r2 = r10.mTile     // Catch:{ NameNotFoundException -> 0x00b0 }
            r2.setLabel(r1)     // Catch:{ NameNotFoundException -> 0x00b0 }
            goto L_0x00b4
        L_0x00b0:
            r10.mDefaultIcon = r0
            r10.mDefaultLabel = r0
        L_0x00b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.external.CustomTile.updateDefaultTileAndIcon():void");
    }

    public static String toSpec(ComponentName componentName) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("custom(");
        m.append(componentName.flattenToShortString());
        m.append(")");
        return m.toString();
    }

    public final Intent getLongClickIntent() {
        Intent intent;
        Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
        intent2.setPackage(this.mComponent.getPackageName());
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent2, 0, this.mUser);
        if (resolveActivityAsUser != null) {
            Intent intent3 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            intent = intent3.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent = null;
        }
        if (intent == null) {
            return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", this.mComponent.getPackageName(), (String) null));
        }
        intent.putExtra("android.intent.extra.COMPONENT_NAME", this.mComponent);
        intent.putExtra("state", this.mTile.getState());
        return intent;
    }

    public final String getMetricsSpec() {
        return this.mComponent.getPackageName();
    }

    public final long getStaleTimeout() {
        return (((long) this.mHost.indexOf(this.mTileSpec)) * 60000) + 3600000;
    }

    public final CharSequence getTileLabel() {
        return this.mState.label;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:3|4|5|6|7|(1:9)|10|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0017 */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001f A[Catch:{ RemoteException -> 0x0030 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleClick(android.view.View r6) {
        /*
            r5 = this;
            android.service.quicksettings.Tile r6 = r5.mTile
            int r6 = r6.getState()
            if (r6 != 0) goto L_0x0009
            return
        L_0x0009:
            r6 = 1
            android.view.IWindowManager r0 = r5.mWindowManager     // Catch:{ RemoteException -> 0x0017 }
            android.os.Binder r1 = r5.mToken     // Catch:{ RemoteException -> 0x0017 }
            r2 = 2035(0x7f3, float:2.852E-42)
            r3 = 0
            r4 = 0
            r0.addWindowToken(r1, r2, r3, r4)     // Catch:{ RemoteException -> 0x0017 }
            r5.mIsTokenGranted = r6     // Catch:{ RemoteException -> 0x0017 }
        L_0x0017:
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager     // Catch:{ RemoteException -> 0x0030 }
            boolean r0 = r0.isActiveTile()     // Catch:{ RemoteException -> 0x0030 }
            if (r0 == 0) goto L_0x0029
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager     // Catch:{ RemoteException -> 0x0030 }
            r0.setBindRequested(r6)     // Catch:{ RemoteException -> 0x0030 }
            com.android.systemui.qs.external.TileLifecycleManager r6 = r5.mService     // Catch:{ RemoteException -> 0x0030 }
            r6.onStartListening()     // Catch:{ RemoteException -> 0x0030 }
        L_0x0029:
            com.android.systemui.qs.external.TileLifecycleManager r6 = r5.mService     // Catch:{ RemoteException -> 0x0030 }
            android.os.Binder r5 = r5.mToken     // Catch:{ RemoteException -> 0x0030 }
            r6.onClick(r5)     // Catch:{ RemoteException -> 0x0030 }
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.external.CustomTile.handleClick(android.view.View):void");
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        Drawable drawable;
        int state2 = this.mTile.getState();
        TileServiceManager tileServiceManager = this.mServiceManager;
        Objects.requireNonNull(tileServiceManager);
        boolean z = false;
        if (tileServiceManager.mPendingBind) {
            state2 = 0;
        }
        state.state = state2;
        try {
            drawable = this.mTile.getIcon().loadDrawable(this.mUserContext);
        } catch (Exception unused) {
            Log.w(this.TAG, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            drawable = this.mDefaultIcon.loadDrawable(this.mUserContext);
        }
        state.iconSupplier = new CustomTile$$ExternalSyntheticLambda1(drawable, 0);
        state.label = this.mTile.getLabel();
        CharSequence subtitle = this.mTile.getSubtitle();
        if (subtitle == null || subtitle.length() <= 0) {
            state.secondaryLabel = null;
        } else {
            state.secondaryLabel = subtitle;
        }
        if (this.mTile.getContentDescription() != null) {
            state.contentDescription = this.mTile.getContentDescription();
        } else {
            state.contentDescription = state.label;
        }
        if (this.mTile.getStateDescription() != null) {
            state.stateDescription = this.mTile.getStateDescription();
        } else {
            state.stateDescription = null;
        }
        if (state instanceof QSTile.BooleanState) {
            state.expandedAccessibilityClassName = Switch.class.getName();
            QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
            if (state.state == 2) {
                z = true;
            }
            booleanState.value = z;
        }
    }

    public final boolean isAvailable() {
        if (!this.mInitialDefaultIconFetched.get() || this.mDefaultIcon != null) {
            return true;
        }
        return false;
    }

    public final QSTile.State newTileState() {
        TileServiceManager tileServiceManager = this.mServiceManager;
        if (tileServiceManager == null || !tileServiceManager.isToggleableTile()) {
            return new QSTile.State();
        }
        return new QSTile.BooleanState();
    }

    public static ComponentName getComponentFromSpec(String str) {
        String substring = str.substring(7, str.length() - 1);
        if (!substring.isEmpty()) {
            return ComponentName.unflattenFromString(substring);
        }
        throw new IllegalArgumentException("Empty custom tile spec action");
    }

    public final void applyTileState(Tile tile, boolean z) {
        if (tile.getIcon() != null || z) {
            this.mTile.setIcon(tile.getIcon());
        }
        if (tile.getLabel() != null || z) {
            this.mTile.setLabel(tile.getLabel());
        }
        if (tile.getSubtitle() != null || z) {
            this.mTile.setSubtitle(tile.getSubtitle());
        }
        if (tile.getContentDescription() != null || z) {
            this.mTile.setContentDescription(tile.getContentDescription());
        }
        if (tile.getStateDescription() != null || z) {
            this.mTile.setStateDescription(tile.getStateDescription());
        }
        this.mTile.setState(tile.getState());
    }

    public final void handleDestroy() {
        super.handleDestroy();
        if (this.mIsTokenGranted) {
            try {
                this.mWindowManager.removeWindowToken(this.mToken, 0);
            } catch (RemoteException unused) {
            }
        }
        TileServices tileServices = this.mTileServices;
        TileServiceManager tileServiceManager = this.mServiceManager;
        Objects.requireNonNull(tileServices);
        synchronized (tileServices.mServices) {
            tileServiceManager.setBindAllowed(false);
            tileServiceManager.setBindAllowed(false);
            TileServices tileServices2 = tileServiceManager.mServices;
            Objects.requireNonNull(tileServices2);
            tileServices2.mContext.unregisterReceiver(tileServiceManager.mUninstallReceiver);
            TileLifecycleManager tileLifecycleManager = tileServiceManager.mStateManager;
            Objects.requireNonNull(tileLifecycleManager);
            if (tileLifecycleManager.mPackageReceiverRegistered.get() || tileLifecycleManager.mUserReceiverRegistered.get()) {
                tileLifecycleManager.stopPackageListening();
            }
            tileLifecycleManager.mChangeListener = null;
            tileServices.mServices.remove(this);
            ArrayMap<IBinder, CustomTile> arrayMap = tileServices.mTokenMap;
            TileLifecycleManager tileLifecycleManager2 = tileServiceManager.mStateManager;
            Objects.requireNonNull(tileLifecycleManager2);
            arrayMap.remove(tileLifecycleManager2.mToken);
            tileServices.mTiles.remove(this.mComponent);
            tileServices.mMainHandler.post(new TileServices$$ExternalSyntheticLambda0(tileServices, this.mComponent.getClassName(), 0));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleInitialize() {
        /*
            r5 = this;
            r5.updateDefaultTileAndIcon()
            java.util.concurrent.atomic.AtomicBoolean r0 = r5.mInitialDefaultIconFetched
            r1 = 0
            r2 = 1
            boolean r0 = r0.compareAndSet(r1, r2)
            if (r0 == 0) goto L_0x0031
            android.graphics.drawable.Icon r0 = r5.mDefaultIcon
            if (r0 != 0) goto L_0x0031
            java.lang.String r0 = r5.TAG
            java.lang.String r2 = "No default icon for "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.lang.String r3 = r5.mTileSpec
            r2.append(r3)
            java.lang.String r3 = ", destroying tile"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.w(r0, r2)
            com.android.systemui.qs.QSHost r0 = r5.mHost
            java.lang.String r2 = r5.mTileSpec
            r0.removeTile(r2)
        L_0x0031:
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager
            boolean r0 = r0.isToggleableTile()
            if (r0 == 0) goto L_0x004d
            com.android.systemui.plugins.qs.QSTile$State r0 = r5.newTileState()
            r5.mState = r0
            com.android.systemui.plugins.qs.QSTile$State r0 = r5.newTileState()
            r5.mTmpState = r0
            TState r2 = r5.mState
            java.lang.String r3 = r5.mTileSpec
            r2.spec = r3
            r0.spec = r3
        L_0x004d:
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.qs.external.TileLifecycleManager r0 = r0.mStateManager
            java.util.Objects.requireNonNull(r0)
            r0.mChangeListener = r5
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager
            boolean r0 = r0.isActiveTile()
            if (r0 == 0) goto L_0x0095
            com.android.systemui.qs.external.CustomTileStatePersister r0 = r5.mCustomTileStatePersister
            com.android.systemui.qs.external.TileServiceKey r2 = r5.mKey
            java.util.Objects.requireNonNull(r0)
            android.content.SharedPreferences r0 = r0.sharedPreferences
            java.lang.String r2 = r2.string
            r3 = 0
            java.lang.String r0 = r0.getString(r2, r3)
            if (r0 != 0) goto L_0x0074
            goto L_0x0085
        L_0x0074:
            android.service.quicksettings.Tile r0 = com.android.systemui.p006qs.external.CustomTileStatePersisterKt.readTileFromString(r0)     // Catch:{ JSONException -> 0x0079 }
            goto L_0x0086
        L_0x0079:
            r2 = move-exception
            java.lang.String r4 = "Bad saved state: "
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r4, r0)
            java.lang.String r4 = "TileServicePersistence"
            android.util.Log.e(r4, r0, r2)
        L_0x0085:
            r0 = r3
        L_0x0086:
            if (r0 == 0) goto L_0x0095
            r5.applyTileState(r0, r1)
            com.android.systemui.qs.external.TileServiceManager r0 = r5.mServiceManager
            java.util.Objects.requireNonNull(r0)
            r0.mPendingBind = r1
            r5.refreshState(r3)
        L_0x0095:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.external.CustomTile.handleInitialize():void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:13|14|15|16) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleSetListening(boolean r3) {
        /*
            r2 = this;
            super.handleSetListening(r3)
            boolean r0 = r2.mListening
            if (r0 != r3) goto L_0x0008
            return
        L_0x0008:
            r2.mListening = r3
            if (r3 == 0) goto L_0x0027
            r2.updateDefaultTileAndIcon()     // Catch:{ RemoteException -> 0x0045 }
            r3 = 0
            r2.refreshState(r3)     // Catch:{ RemoteException -> 0x0045 }
            com.android.systemui.qs.external.TileServiceManager r3 = r2.mServiceManager     // Catch:{ RemoteException -> 0x0045 }
            boolean r3 = r3.isActiveTile()     // Catch:{ RemoteException -> 0x0045 }
            if (r3 != 0) goto L_0x0045
            com.android.systemui.qs.external.TileServiceManager r3 = r2.mServiceManager     // Catch:{ RemoteException -> 0x0045 }
            r0 = 1
            r3.setBindRequested(r0)     // Catch:{ RemoteException -> 0x0045 }
            com.android.systemui.qs.external.TileLifecycleManager r2 = r2.mService     // Catch:{ RemoteException -> 0x0045 }
            r2.onStartListening()     // Catch:{ RemoteException -> 0x0045 }
            goto L_0x0045
        L_0x0027:
            com.android.systemui.qs.external.TileLifecycleManager r3 = r2.mService     // Catch:{ RemoteException -> 0x0045 }
            r3.onStopListening()     // Catch:{ RemoteException -> 0x0045 }
            boolean r3 = r2.mIsTokenGranted     // Catch:{ RemoteException -> 0x0045 }
            r0 = 0
            if (r3 == 0) goto L_0x003e
            boolean r3 = r2.mIsShowingDialog     // Catch:{ RemoteException -> 0x0045 }
            if (r3 != 0) goto L_0x003e
            android.view.IWindowManager r3 = r2.mWindowManager     // Catch:{ RemoteException -> 0x003c }
            android.os.Binder r1 = r2.mToken     // Catch:{ RemoteException -> 0x003c }
            r3.removeWindowToken(r1, r0)     // Catch:{ RemoteException -> 0x003c }
        L_0x003c:
            r2.mIsTokenGranted = r0     // Catch:{ RemoteException -> 0x0045 }
        L_0x003e:
            r2.mIsShowingDialog = r0     // Catch:{ RemoteException -> 0x0045 }
            com.android.systemui.qs.external.TileServiceManager r2 = r2.mServiceManager     // Catch:{ RemoteException -> 0x0045 }
            r2.setBindRequested(r0)     // Catch:{ RemoteException -> 0x0045 }
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.external.CustomTile.handleSetListening(boolean):void");
    }

    public final LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).setComponentName(this.mComponent);
    }
}
