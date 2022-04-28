package com.android.systemui.statusbar.lockscreen;

import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController {
    public final ActivityStarter activityStarter;
    public final LockscreenSmartspaceController$configChangeListener$1 configChangeListener;
    public final ConfigurationController configurationController;
    public final ContentResolver contentResolver;
    public final Context context;
    public final DeviceProvisionedController deviceProvisionedController;
    public final LockscreenSmartspaceController$deviceProvisionedListener$1 deviceProvisionedListener;
    public final Execution execution;
    public final FalsingManager falsingManager;
    public final FeatureFlags featureFlags;
    public UserHandle managedUserHandle;
    public final BcSmartspaceDataPlugin plugin;
    public final SecureSettings secureSettings;
    public SmartspaceSession session;
    public final LockscreenSmartspaceController$sessionListener$1 sessionListener = new LockscreenSmartspaceController$sessionListener$1(this);
    public final LockscreenSmartspaceController$settingsObserver$1 settingsObserver;
    public boolean showSensitiveContentForCurrentUser;
    public boolean showSensitiveContentForManagedUser;
    public final SmartspaceManager smartspaceManager;
    public LinkedHashSet smartspaceViews = new LinkedHashSet();
    public LockscreenSmartspaceController$stateChangeListener$1 stateChangeListener = new LockscreenSmartspaceController$stateChangeListener$1(this);
    public final StatusBarStateController statusBarStateController;
    public final LockscreenSmartspaceController$statusBarStateListener$1 statusBarStateListener;
    public final Executor uiExecutor;
    public final UserTracker userTracker;
    public final LockscreenSmartspaceController$userTrackerCallback$1 userTrackerCallback = new LockscreenSmartspaceController$userTrackerCallback$1(this);

    public final View buildAndConnectView(ViewGroup viewGroup) {
        View view;
        this.execution.assertIsMainThread();
        if (isEnabled()) {
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
            if (bcSmartspaceDataPlugin == null) {
                view = null;
            } else {
                BcSmartspaceDataPlugin.SmartspaceView view2 = bcSmartspaceDataPlugin.getView(viewGroup);
                view2.registerDataProvider(this.plugin);
                view2.setIntentStarter(new LockscreenSmartspaceController$buildView$1(this));
                view2.setFalsingManager(this.falsingManager);
                view = (View) view2;
                view.addOnAttachStateChangeListener(this.stateChangeListener);
            }
            connectSession();
            return view;
        }
        throw new RuntimeException("Cannot build view when not enabled");
    }

    public final void connectSession() {
        if (this.plugin != null && this.session == null && !this.smartspaceViews.isEmpty() && this.deviceProvisionedController.isDeviceProvisioned() && this.deviceProvisionedController.isCurrentUserSetup()) {
            SmartspaceSession createSmartspaceSession = this.smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(this.context, "lockscreen").build());
            Log.d("LockscreenSmartspaceController", "Starting smartspace session for lockscreen");
            createSmartspaceSession.addOnTargetsAvailableListener(this.uiExecutor, this.sessionListener);
            this.session = createSmartspaceSession;
            this.deviceProvisionedController.removeCallback(this.deviceProvisionedListener);
            this.userTracker.addCallback(this.userTrackerCallback, this.uiExecutor);
            this.contentResolver.registerContentObserver(this.secureSettings.getUriFor("lock_screen_allow_private_notifications"), true, this.settingsObserver, -1);
            this.configurationController.addCallback(this.configChangeListener);
            this.statusBarStateController.addCallback(this.statusBarStateListener);
            this.plugin.registerSmartspaceEventNotifier(new LockscreenSmartspaceController$connectSession$1(this));
            reloadSmartspace();
        }
    }

    public final boolean isEnabled() {
        this.execution.assertIsMainThread();
        if (!this.featureFlags.isEnabled(Flags.SMARTSPACE) || this.plugin == null) {
            return false;
        }
        return true;
    }

    public final void reloadSmartspace() {
        boolean z;
        Integer num;
        UserHandle userHandle;
        boolean z2 = false;
        if (this.secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, this.userTracker.getUserId()) == 1) {
            z = true;
        } else {
            z = false;
        }
        this.showSensitiveContentForCurrentUser = z;
        Iterator<UserInfo> it = this.userTracker.getUserProfiles().iterator();
        while (true) {
            num = null;
            if (!it.hasNext()) {
                userHandle = null;
                break;
            }
            UserInfo next = it.next();
            if (next.isManagedProfile()) {
                userHandle = next.getUserHandle();
                break;
            }
        }
        this.managedUserHandle = userHandle;
        if (userHandle != null) {
            num = Integer.valueOf(userHandle.getIdentifier());
        }
        if (num != null) {
            if (this.secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, num.intValue()) == 1) {
                z2 = true;
            }
            this.showSensitiveContentForManagedUser = z2;
        }
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    public LockscreenSmartspaceController(Context context2, FeatureFlags featureFlags2, SmartspaceManager smartspaceManager2, ActivityStarter activityStarter2, FalsingManager falsingManager2, SecureSettings secureSettings2, UserTracker userTracker2, ContentResolver contentResolver2, ConfigurationController configurationController2, StatusBarStateController statusBarStateController2, DeviceProvisionedController deviceProvisionedController2, Execution execution2, Executor executor, Handler handler, Optional<BcSmartspaceDataPlugin> optional) {
        this.context = context2;
        this.featureFlags = featureFlags2;
        this.smartspaceManager = smartspaceManager2;
        this.activityStarter = activityStarter2;
        this.falsingManager = falsingManager2;
        this.secureSettings = secureSettings2;
        this.userTracker = userTracker2;
        this.contentResolver = contentResolver2;
        this.configurationController = configurationController2;
        this.statusBarStateController = statusBarStateController2;
        this.deviceProvisionedController = deviceProvisionedController2;
        this.execution = execution2;
        this.uiExecutor = executor;
        this.plugin = optional.orElse((Object) null);
        this.settingsObserver = new LockscreenSmartspaceController$settingsObserver$1(this, handler);
        this.configChangeListener = new LockscreenSmartspaceController$configChangeListener$1(this);
        this.statusBarStateListener = new LockscreenSmartspaceController$statusBarStateListener$1(this);
        LockscreenSmartspaceController$deviceProvisionedListener$1 lockscreenSmartspaceController$deviceProvisionedListener$1 = new LockscreenSmartspaceController$deviceProvisionedListener$1(this);
        this.deviceProvisionedListener = lockscreenSmartspaceController$deviceProvisionedListener$1;
        deviceProvisionedController2.addCallback(lockscreenSmartspaceController$deviceProvisionedListener$1);
    }

    public static final void access$updateTextColorFromWallpaper(LockscreenSmartspaceController lockscreenSmartspaceController) {
        Objects.requireNonNull(lockscreenSmartspaceController);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(lockscreenSmartspaceController.context, C1777R.attr.wallpaperTextColor);
        for (BcSmartspaceDataPlugin.SmartspaceView primaryTextColor : lockscreenSmartspaceController.smartspaceViews) {
            primaryTextColor.setPrimaryTextColor(colorAttrDefaultColor);
        }
    }
}
