package com.android.systemui.statusbar.gesture;

import android.content.Context;
import com.android.systemui.screenshot.SmartActionsReceiver_Factory;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SwipeStatusBarAwayGestureHandler_Factory implements Factory<SwipeStatusBarAwayGestureHandler> {
    public final Provider<Context> contextProvider;
    public final Provider<SwipeStatusBarAwayGestureLogger> loggerProvider;
    public final Provider<StatusBarWindowController> statusBarWindowControllerProvider;

    public final Object get() {
        return new SwipeStatusBarAwayGestureHandler(this.contextProvider.get(), this.statusBarWindowControllerProvider.get(), this.loggerProvider.get());
    }

    public SwipeStatusBarAwayGestureHandler_Factory(Provider provider, Provider provider2, SmartActionsReceiver_Factory smartActionsReceiver_Factory) {
        this.contextProvider = provider;
        this.statusBarWindowControllerProvider = provider2;
        this.loggerProvider = smartActionsReceiver_Factory;
    }
}
