package com.android.systemui;

import android.hardware.SensorPrivacyManager;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.TapAgainView;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.WalletControllerImpl;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class ActivityStarterDelegate_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider statusBarOptionalLazyProvider;

    /* renamed from: get  reason: collision with other method in class */
    public final Object m164get() {
        switch (this.$r8$classId) {
            case 0:
                return new ActivityStarterDelegate(DoubleCheck.lazy(this.statusBarOptionalLazyProvider));
            case 1:
                return new HistoryTracker((SystemClock) this.statusBarOptionalLazyProvider.get());
            case 2:
                return get();
            case 3:
                return new TvUnblockSensorActivity((IndividualSensorPrivacyController) this.statusBarOptionalLazyProvider.get());
            case 4:
                NotificationPanelView notificationPanelView = (NotificationPanelView) this.statusBarOptionalLazyProvider.get();
                Objects.requireNonNull(notificationPanelView);
                TapAgainView tapAgainView = (TapAgainView) notificationPanelView.findViewById(C1777R.C1779id.shade_falsing_tap_again);
                Objects.requireNonNull(tapAgainView, "Cannot return null from a non-@Nullable @Provides method");
                return tapAgainView;
            case 5:
                return new WalletControllerImpl((QuickAccessWalletClient) this.statusBarOptionalLazyProvider.get());
            default:
                return get();
        }
    }

    public /* synthetic */ ActivityStarterDelegate_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.statusBarOptionalLazyProvider = provider;
    }

    public final SensorPrivacyController get() {
        switch (this.$r8$classId) {
            case 2:
                SensorPrivacyControllerImpl sensorPrivacyControllerImpl = new SensorPrivacyControllerImpl((SensorPrivacyManager) this.statusBarOptionalLazyProvider.get());
                sensorPrivacyControllerImpl.mSensorPrivacyEnabled = sensorPrivacyControllerImpl.mSensorPrivacyManager.isAllSensorPrivacyEnabled();
                sensorPrivacyControllerImpl.mSensorPrivacyManager.addAllSensorPrivacyListener(sensorPrivacyControllerImpl);
                return sensorPrivacyControllerImpl;
            default:
                SensorPrivacyControllerImpl sensorPrivacyControllerImpl2 = new SensorPrivacyControllerImpl((SensorPrivacyManager) this.statusBarOptionalLazyProvider.get());
                sensorPrivacyControllerImpl2.mSensorPrivacyEnabled = sensorPrivacyControllerImpl2.mSensorPrivacyManager.isAllSensorPrivacyEnabled();
                sensorPrivacyControllerImpl2.mSensorPrivacyManager.addAllSensorPrivacyListener(sensorPrivacyControllerImpl2);
                return sensorPrivacyControllerImpl2;
        }
    }
}
