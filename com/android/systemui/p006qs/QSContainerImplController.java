package com.android.systemui.p006qs;

import android.content.res.Configuration;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* renamed from: com.android.systemui.qs.QSContainerImplController */
public final class QSContainerImplController extends ViewController<QSContainerImpl> {
    public final ConfigurationController mConfigurationController;
    public final C09871 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            QSContainerImplController qSContainerImplController = QSContainerImplController.this;
            ((QSContainerImpl) qSContainerImplController.mView).updateResources(qSContainerImplController.mQsPanelController, qSContainerImplController.mQuickStatusBarHeaderController);
        }
    };
    public final QSPanelController mQsPanelController;
    public final QuickStatusBarHeaderController mQuickStatusBarHeaderController;

    public final void onInit() {
        this.mQuickStatusBarHeaderController.init();
    }

    public final void onViewAttached() {
        ((QSContainerImpl) this.mView).updateResources(this.mQsPanelController, this.mQuickStatusBarHeaderController);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
    }

    public final void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public QSContainerImplController(QSContainerImpl qSContainerImpl, QSPanelController qSPanelController, QuickStatusBarHeaderController quickStatusBarHeaderController, ConfigurationController configurationController) {
        super(qSContainerImpl);
        this.mQsPanelController = qSPanelController;
        this.mQuickStatusBarHeaderController = quickStatusBarHeaderController;
        this.mConfigurationController = configurationController;
    }
}
