package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import com.android.systemui.plugins.ActivityStarter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GameMenuActivity_Factory implements Factory<GameMenuActivity> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Context> contextProvider;
    public final Provider<EntryPointController> entryPointControllerProvider;
    public final Provider<GameModeDndController> gameModeDndControllerProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<ShortcutBarController> shortcutBarControllerProvider;
    public final Provider<GameDashboardUiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new GameMenuActivity(this.contextProvider.get(), this.entryPointControllerProvider.get(), this.activityStarterProvider.get(), this.shortcutBarControllerProvider.get(), this.gameModeDndControllerProvider.get(), this.layoutInflaterProvider.get(), this.mainHandlerProvider.get(), this.uiEventLoggerProvider.get());
    }

    public GameMenuActivity_Factory(Provider<Context> provider, Provider<EntryPointController> provider2, Provider<ActivityStarter> provider3, Provider<ShortcutBarController> provider4, Provider<GameModeDndController> provider5, Provider<LayoutInflater> provider6, Provider<Handler> provider7, Provider<GameDashboardUiEventLogger> provider8) {
        this.contextProvider = provider;
        this.entryPointControllerProvider = provider2;
        this.activityStarterProvider = provider3;
        this.shortcutBarControllerProvider = provider4;
        this.gameModeDndControllerProvider = provider5;
        this.layoutInflaterProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.uiEventLoggerProvider = provider8;
    }
}
