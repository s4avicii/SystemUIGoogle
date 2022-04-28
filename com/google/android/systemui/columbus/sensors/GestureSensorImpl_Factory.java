package com.google.android.systemui.columbus.sensors;

import android.content.Context;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dagger.NightDisplayListenerModule;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class GestureSensorImpl_Factory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Provider contextProvider;
    public final Object handlerProvider;
    public final Provider uiEventLoggerProvider;

    public GestureSensorImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.handlerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new GestureSensorImpl((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (Handler) ((Provider) this.handlerProvider).get());
            default:
                Objects.requireNonNull((NightDisplayListenerModule) this.handlerProvider);
                return new NightDisplayListener((Context) this.contextProvider.get(), (Handler) this.uiEventLoggerProvider.get());
        }
    }

    public GestureSensorImpl_Factory(NightDisplayListenerModule nightDisplayListenerModule, Provider provider, Provider provider2) {
        this.handlerProvider = nightDisplayListenerModule;
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
    }
}
