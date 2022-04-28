package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DreamOverlayModule_ProvidesMaxBurnInOffsetFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider resourcesProvider;

    /* renamed from: get  reason: collision with other method in class */
    public final Object m197get() {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((Resources) this.resourcesProvider.get()).getDimensionPixelSize(C1777R.dimen.default_burn_in_prevention_offset));
            case 1:
                return get();
            case 2:
                OngoingPrivacyChip ongoingPrivacyChip = (OngoingPrivacyChip) ((View) this.resourcesProvider.get()).findViewById(C1777R.C1779id.privacy_chip);
                Objects.requireNonNull(ongoingPrivacyChip, "Cannot return null from a non-@Nullable @Provides method");
                return ongoingPrivacyChip;
            default:
                return get();
        }
    }

    public /* synthetic */ DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.resourcesProvider = provider;
    }

    public final IndividualSensorPrivacyController get() {
        switch (this.$r8$classId) {
            case 1:
                IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = new IndividualSensorPrivacyControllerImpl((SensorPrivacyManager) this.resourcesProvider.get());
                individualSensorPrivacyControllerImpl.init();
                return individualSensorPrivacyControllerImpl;
            default:
                IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl2 = new IndividualSensorPrivacyControllerImpl((SensorPrivacyManager) this.resourcesProvider.get());
                individualSensorPrivacyControllerImpl2.init();
                return individualSensorPrivacyControllerImpl2;
        }
    }
}
