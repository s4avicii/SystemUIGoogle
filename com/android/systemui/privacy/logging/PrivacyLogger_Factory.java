package com.android.systemui.privacy.logging;

import android.content.Context;
import android.os.SystemProperties;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class PrivacyLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bufferProvider;

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PrivacyLogger((LogBuffer) this.bufferProvider.get());
            case 1:
                BatteryMeterView batteryMeterView = (BatteryMeterView) Preconditions.checkNotNull((BatteryMeterView) ((DreamOverlayContainerView) this.bufferProvider.get()).findViewById(C1777R.C1779id.dream_overlay_battery), "R.id.battery must not be null");
                Objects.requireNonNull(batteryMeterView, "Cannot return null from a non-@Nullable @Provides method");
                return batteryMeterView;
            case 2:
                return Boolean.valueOf(((Context) this.bufferProvider.get()).getResources().getBoolean(C1777R.bool.config_quickSettingsMediaLandscapeCollapsed));
            case 3:
                return new RemoteInputNotificationRebuilder((Context) this.bufferProvider.get());
            case 4:
                return new NotifCollectionLogger((LogBuffer) this.bufferProvider.get());
            case 5:
                return get();
            default:
                return get();
        }
    }

    public /* synthetic */ PrivacyLogger_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.bufferProvider = provider;
    }

    /* renamed from: get  reason: collision with other method in class */
    public final Optional m212get() {
        switch (this.$r8$classId) {
            case 5:
                Optional optional = (Optional) this.bufferProvider.get();
                if (!SystemProperties.getBoolean("ro.support_one_handed_mode", false)) {
                    optional = Optional.empty();
                }
                Objects.requireNonNull(optional, "Cannot return null from a non-@Nullable @Provides method");
                return optional;
            default:
                return Optional.empty();
        }
    }
}
