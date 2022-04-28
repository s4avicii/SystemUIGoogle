package com.android.systemui.p006qs;

import android.util.DisplayMetrics;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.policy.BatteryController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSSquishinessController_Factory */
public final class QSSquishinessController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider qsAnimatorProvider;
    public final Provider qsPanelControllerProvider;
    public final Provider quickQSPanelControllerProvider;

    public /* synthetic */ QSSquishinessController_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.qsAnimatorProvider = provider;
        this.qsPanelControllerProvider = provider2;
        this.quickQSPanelControllerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new QSSquishinessController((QSAnimator) this.qsAnimatorProvider.get(), (QSPanelController) this.qsPanelControllerProvider.get(), (QuickQSPanelController) this.quickQSPanelControllerProvider.get());
            default:
                return new FalsingDataProvider((DisplayMetrics) this.qsAnimatorProvider.get(), (BatteryController) this.qsPanelControllerProvider.get(), (DockManager) this.quickQSPanelControllerProvider.get());
        }
    }
}
