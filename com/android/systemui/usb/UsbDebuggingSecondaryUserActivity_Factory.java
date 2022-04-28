package com.android.systemui.usb;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class UsbDebuggingSecondaryUserActivity_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider broadcastDispatcherProvider;

    public /* synthetic */ UsbDebuggingSecondaryUserActivity_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.broadcastDispatcherProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new UsbDebuggingSecondaryUserActivity((BroadcastDispatcher) this.broadcastDispatcherProvider.get());
            case 1:
                PowerManager powerManager = (PowerManager) ((Context) this.broadcastDispatcherProvider.get()).getSystemService(PowerManager.class);
                Objects.requireNonNull(powerManager, "Cannot return null from a non-@Nullable @Provides method");
                return powerManager;
            case 2:
                return new AccessibilityController((Context) this.broadcastDispatcherProvider.get());
            case 3:
                Uri uriFor = Settings.Secure.getUriFor((String) this.broadcastDispatcherProvider.get());
                Objects.requireNonNull(uriFor, "Cannot return null from a non-@Nullable @Provides method");
                return uriFor;
            default:
                IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = new IndividualSensorPrivacyControllerImpl((SensorPrivacyManager) this.broadcastDispatcherProvider.get());
                individualSensorPrivacyControllerImpl.init();
                return individualSensorPrivacyControllerImpl;
        }
    }
}
