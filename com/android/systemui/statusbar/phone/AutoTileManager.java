package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.AutoAddTracker;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.p006qs.SettingObserver;
import com.android.systemui.p006qs.external.CustomTile;
import com.android.systemui.p006qs.tiles.DndTile$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceControlsController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.WalletController;
import com.android.systemui.util.UserAwareController;
import com.android.systemui.util.settings.SecureSettings;
import com.google.android.systemui.elmyra.actions.Action$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class AutoTileManager implements UserAwareController {
    public final ArrayList<AutoAddSetting> mAutoAddSettingList = new ArrayList<>();
    public final AutoAddTracker mAutoTracker;
    @VisibleForTesting
    public final CastController.Callback mCastCallback = new CastController.Callback() {
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
            r0 = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onCastDevicesChanged() {
            /*
                r6 = this;
                com.android.systemui.statusbar.phone.AutoTileManager r0 = com.android.systemui.statusbar.phone.AutoTileManager.this
                com.android.systemui.qs.AutoAddTracker r0 = r0.mAutoTracker
                java.lang.String r1 = "cast"
                boolean r0 = r0.isAdded(r1)
                if (r0 == 0) goto L_0x000d
                return
            L_0x000d:
                r0 = 0
                com.android.systemui.statusbar.phone.AutoTileManager r2 = com.android.systemui.statusbar.phone.AutoTileManager.this
                com.android.systemui.statusbar.policy.CastController r2 = r2.mCastController
                java.util.ArrayList r2 = r2.getCastDevices()
                java.util.Iterator r2 = r2.iterator()
            L_0x001a:
                boolean r3 = r2.hasNext()
                r4 = 1
                if (r3 == 0) goto L_0x002f
                java.lang.Object r3 = r2.next()
                com.android.systemui.statusbar.policy.CastController$CastDevice r3 = (com.android.systemui.statusbar.policy.CastController.CastDevice) r3
                int r3 = r3.state
                r5 = 2
                if (r3 == r5) goto L_0x002e
                if (r3 != r4) goto L_0x001a
            L_0x002e:
                r0 = r4
            L_0x002f:
                if (r0 == 0) goto L_0x0050
                com.android.systemui.statusbar.phone.AutoTileManager r0 = com.android.systemui.statusbar.phone.AutoTileManager.this
                com.android.systemui.qs.QSTileHost r0 = r0.mHost
                java.util.Objects.requireNonNull(r0)
                r2 = -1
                r0.addTile((java.lang.String) r1, (int) r2)
                com.android.systemui.statusbar.phone.AutoTileManager r0 = com.android.systemui.statusbar.phone.AutoTileManager.this
                com.android.systemui.qs.AutoAddTracker r0 = r0.mAutoTracker
                r0.setTileAdded(r1)
                com.android.systemui.statusbar.phone.AutoTileManager r0 = com.android.systemui.statusbar.phone.AutoTileManager.this
                android.os.Handler r0 = r0.mHandler
                com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18 r1 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18
                r2 = 5
                r1.<init>(r6, r2)
                r0.post(r1)
            L_0x0050:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.AutoTileManager.C14057.onCastDevicesChanged():void");
        }
    };
    public final CastController mCastController;
    public final Context mContext;
    public UserHandle mCurrentUser;
    public final DataSaverController mDataSaverController;
    public final C14002 mDataSaverListener = new DataSaverController.Listener() {
        public final void onDataSaverChanged(boolean z) {
            if (!AutoTileManager.this.mAutoTracker.isAdded("saver") && z) {
                QSTileHost qSTileHost = AutoTileManager.this.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.addTile("saver", -1);
                AutoTileManager.this.mAutoTracker.setTileAdded("saver");
                AutoTileManager.this.mHandler.post(new LockIconViewController$$ExternalSyntheticLambda2(this, 5));
            }
        }
    };
    public final C14024 mDeviceControlsCallback = new DeviceControlsController.Callback() {
    };
    public final DeviceControlsController mDeviceControlsController;
    public final Handler mHandler;
    public final QSTileHost mHost;
    public final C14013 mHotspotCallback = new HotspotController.Callback() {
        public final void onHotspotChanged(boolean z, int i) {
            if (!AutoTileManager.this.mAutoTracker.isAdded("hotspot") && z) {
                QSTileHost qSTileHost = AutoTileManager.this.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.addTile("hotspot", -1);
                AutoTileManager.this.mAutoTracker.setTileAdded("hotspot");
                AutoTileManager.this.mHandler.post(new LockIconViewController$$ExternalSyntheticLambda1(this, 4));
            }
        }
    };
    public final HotspotController mHotspotController;
    public boolean mInitialized;
    public final boolean mIsReduceBrightColorsAvailable;
    public final ManagedProfileController mManagedProfileController;
    @VisibleForTesting
    public final NightDisplayListener.Callback mNightDisplayCallback = new NightDisplayListener.Callback() {
        public final void onAutoModeChanged(int i) {
            if (i == 1 || i == 2) {
                addNightTile();
            }
        }

        public final void addNightTile() {
            if (!AutoTileManager.this.mAutoTracker.isAdded("night")) {
                QSTileHost qSTileHost = AutoTileManager.this.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.addTile("night", -1);
                AutoTileManager.this.mAutoTracker.setTileAdded("night");
                AutoTileManager.this.mHandler.post(new BubbleStackView$$ExternalSyntheticLambda16(this, 2));
            }
        }

        public final void onActivated(boolean z) {
            if (z) {
                addNightTile();
            }
        }
    };
    public final NightDisplayListener mNightDisplayListener;
    public final C13991 mProfileCallback = new ManagedProfileController.Callback() {
        public final void onManagedProfileRemoved() {
        }

        public final void onManagedProfileChanged() {
            if (!AutoTileManager.this.mAutoTracker.isAdded("work") && AutoTileManager.this.mManagedProfileController.hasActiveProfile()) {
                QSTileHost qSTileHost = AutoTileManager.this.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.addTile("work", -1);
                AutoTileManager.this.mAutoTracker.setTileAdded("work");
            }
        }
    };
    @VisibleForTesting
    public final ReduceBrightColorsController.Listener mReduceBrightColorsCallback = new ReduceBrightColorsController.Listener() {
        public final void onActivated(boolean z) {
            if (z && !AutoTileManager.this.mAutoTracker.isAdded("reduce_brightness")) {
                QSTileHost qSTileHost = AutoTileManager.this.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.addTile("reduce_brightness", -1);
                AutoTileManager.this.mAutoTracker.setTileAdded("reduce_brightness");
                AutoTileManager.this.mHandler.post(new StatusBar$$ExternalSyntheticLambda18(this, 10));
            }
        }
    };
    public final ReduceBrightColorsController mReduceBrightColorsController;
    public final SecureSettings mSecureSettings;
    public final WalletController mWalletController;

    public class AutoAddSetting extends SettingObserver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final String mSpec;

        public AutoAddSetting(SecureSettings secureSettings, Handler handler, String str, int i, String str2) {
            super(secureSettings, handler, str, i);
            this.mSpec = str2;
        }

        public final void handleValueChanged(int i, boolean z) {
            if (AutoTileManager.this.mAutoTracker.isAdded(this.mSpec)) {
                AutoTileManager.this.mHandler.post(new Action$$ExternalSyntheticLambda0(this, 4));
            } else if (i != 0) {
                if (this.mSpec.startsWith("custom(")) {
                    AutoTileManager.this.mHost.addTile(CustomTile.getComponentFromSpec(this.mSpec), true);
                } else {
                    QSTileHost qSTileHost = AutoTileManager.this.mHost;
                    String str = this.mSpec;
                    Objects.requireNonNull(qSTileHost);
                    qSTileHost.addTile(str, -1);
                }
                AutoTileManager.this.mAutoTracker.setTileAdded(this.mSpec);
                AutoTileManager.this.mHandler.post(new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 3));
            }
        }
    }

    public AutoTileManager(Context context, AutoAddTracker.Builder builder, QSTileHost qSTileHost, Handler handler, SecureSettings secureSettings, HotspotController hotspotController, DataSaverController dataSaverController, ManagedProfileController managedProfileController, NightDisplayListener nightDisplayListener, CastController castController, ReduceBrightColorsController reduceBrightColorsController, DeviceControlsController deviceControlsController, WalletController walletController, boolean z) {
        AutoAddTracker.Builder builder2 = builder;
        QSTileHost qSTileHost2 = qSTileHost;
        this.mContext = context;
        this.mHost = qSTileHost2;
        this.mSecureSettings = secureSettings;
        Objects.requireNonNull(qSTileHost);
        UserHandle user = qSTileHost2.mUserContext.getUser();
        this.mCurrentUser = user;
        int identifier = user.getIdentifier();
        Objects.requireNonNull(builder);
        builder2.userId = identifier;
        this.mAutoTracker = new AutoAddTracker(builder2.secureSettings, builder2.broadcastDispatcher, builder2.qsHost, builder2.dumpManager, builder2.handler, builder2.executor, builder2.userId);
        this.mHandler = handler;
        this.mHotspotController = hotspotController;
        this.mDataSaverController = dataSaverController;
        this.mManagedProfileController = managedProfileController;
        this.mNightDisplayListener = nightDisplayListener;
        this.mCastController = castController;
        this.mReduceBrightColorsController = reduceBrightColorsController;
        this.mIsReduceBrightColorsAvailable = z;
        this.mDeviceControlsController = deviceControlsController;
        this.mWalletController = walletController;
    }

    public final void changeUser(UserHandle userHandle) {
        if (!this.mInitialized) {
            throw new IllegalStateException("AutoTileManager not initialized");
        } else if (!Thread.currentThread().equals(this.mHandler.getLooper().getThread())) {
            this.mHandler.post(new DndTile$$ExternalSyntheticLambda0(this, userHandle, 1));
        } else if (userHandle.getIdentifier() != this.mCurrentUser.getIdentifier()) {
            stopListening();
            this.mCurrentUser = userHandle;
            int size = this.mAutoAddSettingList.size();
            for (int i = 0; i < size; i++) {
                this.mAutoAddSettingList.get(i).setUserId(userHandle.getIdentifier());
            }
            this.mAutoTracker.changeUser(userHandle);
            startControllersAndSettingsListeners();
        }
    }

    @VisibleForTesting
    public SettingObserver getSecureSettingForKey(String str) {
        Iterator<AutoAddSetting> it = this.mAutoAddSettingList.iterator();
        while (it.hasNext()) {
            SettingObserver next = it.next();
            Objects.requireNonNull(next);
            if (Objects.equals(str, next.mSettingName)) {
                return next;
            }
        }
        return null;
    }

    public void init() {
        if (this.mInitialized) {
            Log.w("AutoTileManager", "Trying to re-initialize");
            return;
        }
        AutoAddTracker autoAddTracker = this.mAutoTracker;
        Objects.requireNonNull(autoAddTracker);
        autoAddTracker.dumpManager.registerDumpable("AutoAddTracker", autoAddTracker);
        autoAddTracker.loadTiles();
        SecureSettings secureSettings = autoAddTracker.secureSettings;
        secureSettings.registerContentObserverForUser(secureSettings.getUriFor("qs_auto_tiles"), (ContentObserver) autoAddTracker.contentObserver, -1);
        BroadcastDispatcher.registerReceiver$default(autoAddTracker.broadcastDispatcher, autoAddTracker.restoreReceiver, AutoAddTracker.FILTER, autoAddTracker.backgroundExecutor, UserHandle.of(autoAddTracker.userId), 16);
        try {
            for (String str : this.mContext.getResources().getStringArray(C1777R.array.config_quickSettingsAutoAdd)) {
                String[] split = str.split(":");
                if (split.length == 2) {
                    this.mAutoAddSettingList.add(new AutoAddSetting(this.mSecureSettings, this.mHandler, split[0], this.mCurrentUser.getIdentifier(), split[1]));
                } else {
                    MotionLayout$$ExternalSyntheticOutline0.m9m("Malformed item in array: ", str, "AutoTileManager");
                }
            }
        } catch (Resources.NotFoundException unused) {
            Log.w("AutoTileManager", "Missing config resource");
        }
        startControllersAndSettingsListeners();
        this.mInitialized = true;
    }

    public void startControllersAndSettingsListeners() {
        Integer walletPosition;
        if (!this.mAutoTracker.isAdded("hotspot")) {
            this.mHotspotController.addCallback(this.mHotspotCallback);
        }
        if (!this.mAutoTracker.isAdded("saver")) {
            this.mDataSaverController.addCallback(this.mDataSaverListener);
        }
        if (!this.mAutoTracker.isAdded("work")) {
            this.mManagedProfileController.addCallback(this.mProfileCallback);
        }
        if (!this.mAutoTracker.isAdded("night") && ColorDisplayManager.isNightDisplayAvailable(this.mContext)) {
            this.mNightDisplayListener.setCallback(this.mNightDisplayCallback);
        }
        if (!this.mAutoTracker.isAdded("cast")) {
            this.mCastController.addCallback(this.mCastCallback);
        }
        if (!this.mAutoTracker.isAdded("reduce_brightness") && this.mIsReduceBrightColorsAvailable) {
            this.mReduceBrightColorsController.addCallback(this.mReduceBrightColorsCallback);
        }
        if (!this.mAutoTracker.isAdded("controls")) {
            this.mDeviceControlsController.setCallback(this.mDeviceControlsCallback);
        }
        if (!this.mAutoTracker.isAdded("wallet") && !this.mAutoTracker.isAdded("wallet") && (walletPosition = this.mWalletController.getWalletPosition()) != null) {
            this.mHost.addTile("wallet", walletPosition.intValue());
            this.mAutoTracker.setTileAdded("wallet");
        }
        int size = this.mAutoAddSettingList.size();
        for (int i = 0; i < size; i++) {
            if (!this.mAutoTracker.isAdded(this.mAutoAddSettingList.get(i).mSpec)) {
                this.mAutoAddSettingList.get(i).setListening(true);
            }
        }
    }

    public void stopListening() {
        this.mHotspotController.removeCallback(this.mHotspotCallback);
        this.mDataSaverController.removeCallback(this.mDataSaverListener);
        this.mManagedProfileController.removeCallback(this.mProfileCallback);
        if (ColorDisplayManager.isNightDisplayAvailable(this.mContext)) {
            this.mNightDisplayListener.setCallback((NightDisplayListener.Callback) null);
        }
        if (this.mIsReduceBrightColorsAvailable) {
            this.mReduceBrightColorsController.removeCallback(this.mReduceBrightColorsCallback);
        }
        this.mCastController.removeCallback(this.mCastCallback);
        this.mDeviceControlsController.removeCallback();
        int size = this.mAutoAddSettingList.size();
        for (int i = 0; i < size; i++) {
            this.mAutoAddSettingList.get(i).setListening(false);
        }
    }
}
