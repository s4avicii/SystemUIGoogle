package com.android.systemui.statusbar.policy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.ShellInitImpl$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.ShellInitImpl$$ExternalSyntheticLambda6;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.SettingObserver;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.volume.VolumeDialogComponent$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda7;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class ZenModeControllerImpl extends CurrentUserTracker implements ZenModeController, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AlarmManager mAlarmManager;
    public final ArrayList<ZenModeController.Callback> mCallbacks = new ArrayList<>();
    public final Object mCallbacksLock = new Object();
    public ZenModeConfig mConfig;
    public final C16572 mConfigSetting;
    public NotificationManager.Policy mConsolidatedNotificationPolicy;
    public final Context mContext;
    public final C16561 mModeSetting;
    public final NotificationManager mNoMan;
    public final C16583 mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(intent.getAction())) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                Objects.requireNonNull(zenModeControllerImpl);
                synchronized (zenModeControllerImpl.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl.mCallbacks, ShellInitImpl$$ExternalSyntheticLambda6.INSTANCE$2);
                }
            }
            if ("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED".equals(intent.getAction())) {
                ZenModeControllerImpl zenModeControllerImpl2 = ZenModeControllerImpl.this;
                Objects.requireNonNull(zenModeControllerImpl2);
                synchronized (zenModeControllerImpl2.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl2.mCallbacks, ShellInitImpl$$ExternalSyntheticLambda5.INSTANCE$1);
                }
            }
        }
    };
    public boolean mRegistered;
    public final SetupObserver mSetupObserver;
    public int mUserId;
    public int mZenMode;
    public long mZenUpdateTime;

    public final class SetupObserver extends ContentObserver {
        public boolean mRegistered;
        public final ContentResolver mResolver;

        public SetupObserver(Handler handler) {
            super(handler);
            this.mResolver = ZenModeControllerImpl.this.mContext.getContentResolver();
        }

        public final void onChange(boolean z, Uri uri) {
            if (Settings.Global.getUriFor("device_provisioned").equals(uri) || Settings.Secure.getUriFor("user_setup_complete").equals(uri)) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                boolean isZenAvailable = zenModeControllerImpl.isZenAvailable();
                synchronized (zenModeControllerImpl.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda1(isZenAvailable));
                }
            }
        }

        public final void register() {
            if (this.mRegistered) {
                this.mResolver.unregisterContentObserver(this);
            }
            this.mResolver.registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this);
            this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this, ZenModeControllerImpl.this.mUserId);
            this.mRegistered = true;
            ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
            boolean isZenAvailable = zenModeControllerImpl.isZenAvailable();
            synchronized (zenModeControllerImpl.mCallbacksLock) {
                Utils.safeForeach(zenModeControllerImpl.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda1(isZenAvailable));
            }
        }
    }

    static {
        Log.isLoggable("ZenModeController", 3);
    }

    public final void addCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            this.mCallbacks.add(callback);
        }
    }

    public final boolean areNotificationsHiddenInShade() {
        if (this.mZenMode == 0 || (this.mConsolidatedNotificationPolicy.suppressedVisualEffects & 256) == 0) {
            return false;
        }
        return true;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "ZenModeControllerImpl:", "  mZenMode=");
        m.append(this.mZenMode);
        printWriter.println(m.toString());
        printWriter.println("  mConfig=" + this.mConfig);
        printWriter.println("  mConsolidatedNotificationPolicy=" + this.mConsolidatedNotificationPolicy);
        printWriter.println("  mZenUpdateTime=" + DateFormat.format("MM-dd HH:mm:ss", this.mZenUpdateTime));
    }

    @VisibleForTesting
    public void fireConfigChanged(ZenModeConfig zenModeConfig) {
        synchronized (this.mCallbacksLock) {
            Utils.safeForeach(this.mCallbacks, new VolumeDialogComponent$$ExternalSyntheticLambda0(zenModeConfig, 2));
        }
    }

    public final long getNextAlarm() {
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(this.mUserId);
        if (nextAlarmClock != null) {
            return nextAlarmClock.getTriggerTime();
        }
        return 0;
    }

    public final boolean isZenAvailable() {
        boolean z;
        boolean z2;
        SetupObserver setupObserver = this.mSetupObserver;
        Objects.requireNonNull(setupObserver);
        if (Settings.Global.getInt(setupObserver.mResolver, "device_provisioned", 0) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        SetupObserver setupObserver2 = this.mSetupObserver;
        Objects.requireNonNull(setupObserver2);
        if (Settings.Secure.getIntForUser(setupObserver2.mResolver, "user_setup_complete", 0, ZenModeControllerImpl.this.mUserId) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        return false;
    }

    public final void onUserSwitched(int i) {
        this.mUserId = i;
        if (this.mRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
        IntentFilter intentFilter = new IntentFilter("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
        this.mContext.registerReceiverAsUser(this.mReceiver, new UserHandle(this.mUserId), intentFilter, (String) null, (Handler) null);
        this.mRegistered = true;
        this.mSetupObserver.register();
    }

    public final void removeCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            this.mCallbacks.remove(callback);
        }
    }

    public final void setZen(int i, Uri uri, String str) {
        this.mNoMan.setZenMode(i, uri, str);
    }

    @VisibleForTesting
    public void updateConsolidatedNotificationPolicy() {
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (!Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
            synchronized (this.mCallbacksLock) {
                Utils.safeForeach(this.mCallbacks, new WMShell$$ExternalSyntheticLambda2(consolidatedNotificationPolicy, 3));
            }
        }
    }

    @VisibleForTesting
    public void updateZenMode(int i) {
        this.mZenMode = i;
        this.mZenUpdateTime = System.currentTimeMillis();
    }

    @VisibleForTesting
    public void updateZenModeConfig() {
        ZenModeConfig.ZenRule zenRule;
        ZenModeConfig zenModeConfig = this.mNoMan.getZenModeConfig();
        if (!Objects.equals(zenModeConfig, this.mConfig)) {
            ZenModeConfig zenModeConfig2 = this.mConfig;
            ZenModeConfig.ZenRule zenRule2 = null;
            if (zenModeConfig2 != null) {
                zenRule = zenModeConfig2.manualRule;
            } else {
                zenRule = null;
            }
            this.mConfig = zenModeConfig;
            this.mZenUpdateTime = System.currentTimeMillis();
            fireConfigChanged(zenModeConfig);
            if (zenModeConfig != null) {
                zenRule2 = zenModeConfig.manualRule;
            }
            if (!Objects.equals(zenRule, zenRule2)) {
                synchronized (this.mCallbacksLock) {
                    Utils.safeForeach(this.mCallbacks, new WMShell$$ExternalSyntheticLambda7(zenRule2, 1));
                }
            }
            NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
            if (!Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
                this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
                synchronized (this.mCallbacksLock) {
                    Utils.safeForeach(this.mCallbacks, new WMShell$$ExternalSyntheticLambda2(consolidatedNotificationPolicy, 3));
                }
            }
        }
    }

    public ZenModeControllerImpl(Context context, Handler handler, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, GlobalSettings globalSettings) {
        super(broadcastDispatcher);
        this.mContext = context;
        C16561 r4 = new SettingObserver(globalSettings, handler) {
            public final void handleValueChanged(int i, boolean z) {
                ZenModeControllerImpl.this.updateZenMode(i);
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                Objects.requireNonNull(zenModeControllerImpl);
                synchronized (zenModeControllerImpl.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda0(i));
                }
            }
        };
        this.mModeSetting = r4;
        C16572 r0 = new SettingObserver(globalSettings, handler) {
            public final void handleValueChanged(int i, boolean z) {
                ZenModeControllerImpl.this.updateZenModeConfig();
            }
        };
        this.mConfigSetting = r0;
        this.mNoMan = (NotificationManager) context.getSystemService("notification");
        r4.setListening(true);
        updateZenMode(r4.getValue());
        r0.setListening(true);
        updateZenModeConfig();
        updateConsolidatedNotificationPolicy();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SetupObserver setupObserver = new SetupObserver(handler);
        this.mSetupObserver = setupObserver;
        setupObserver.register();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        startTracking();
        dumpManager.registerDumpable("ZenModeControllerImpl", this);
    }

    public final ZenModeConfig getConfig() {
        return this.mConfig;
    }

    public final NotificationManager.Policy getConsolidatedPolicy() {
        return this.mConsolidatedNotificationPolicy;
    }

    public final int getZen() {
        return this.mZenMode;
    }
}
