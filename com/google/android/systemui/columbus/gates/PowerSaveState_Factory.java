package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.os.Looper;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.statusbar.notification.SectionHeaderVisibilityProvider;
import com.android.systemui.util.concurrency.ExecutorImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class PowerSaveState_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ PowerSaveState_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PowerSaveState((Context) this.contextProvider.get());
            case 1:
                SensorPrivacyManager sensorPrivacyManager = (SensorPrivacyManager) ((Context) this.contextProvider.get()).getSystemService(SensorPrivacyManager.class);
                Objects.requireNonNull(sensorPrivacyManager, "Cannot return null from a non-@Nullable @Provides method");
                return sensorPrivacyManager;
            case 2:
                return ((LogBufferFactory) this.contextProvider.get()).create("CollapsedSbFragmentLog", 20);
            case 3:
                return new SectionHeaderVisibilityProvider((Context) this.contextProvider.get());
            default:
                return new ExecutorImpl((Looper) this.contextProvider.get());
        }
    }
}
