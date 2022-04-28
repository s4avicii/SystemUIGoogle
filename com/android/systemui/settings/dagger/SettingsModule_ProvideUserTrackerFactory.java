package com.android.systemui.settings.dagger;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.screenshot.SmartActionsReceiver_Factory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SettingsModule_ProvideUserTrackerFactory implements Factory<UserTracker> {
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<UserManager> userManagerProvider;

    public final Object get() {
        int currentUser = ActivityManager.getCurrentUser();
        UserTrackerImpl userTrackerImpl = new UserTrackerImpl(this.contextProvider.get(), this.userManagerProvider.get(), this.dumpManagerProvider.get(), this.handlerProvider.get());
        if (!userTrackerImpl.initialized) {
            userTrackerImpl.initialized = true;
            userTrackerImpl.setUserIdInternal(currentUser);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
            userTrackerImpl.context.registerReceiverForAllUsers(userTrackerImpl, intentFilter, (String) null, userTrackerImpl.backgroundHandler);
            userTrackerImpl.dumpManager.registerDumpable("UserTrackerImpl", userTrackerImpl);
        }
        return userTrackerImpl;
    }

    public SettingsModule_ProvideUserTrackerFactory(Provider provider, Provider provider2, Provider provider3, SmartActionsReceiver_Factory smartActionsReceiver_Factory) {
        this.contextProvider = provider;
        this.userManagerProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.handlerProvider = smartActionsReceiver_Factory;
    }
}
