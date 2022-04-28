package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Objects;

public final class ShadeViewManagerFactory_Impl implements ShadeViewManagerFactory {
    public final ShadeViewManager_Factory delegateFactory;

    public final ShadeViewManager create(NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl) {
        ShadeViewManager_Factory shadeViewManager_Factory = this.delegateFactory;
        Objects.requireNonNull(shadeViewManager_Factory);
        return new ShadeViewManager(shadeViewManager_Factory.contextProvider.get(), notificationListContainerImpl, notifStackControllerImpl, shadeViewManager_Factory.mediaContainerControllerProvider.get(), shadeViewManager_Factory.featureManagerProvider.get(), shadeViewManager_Factory.sectionHeaderVisibilityProvider.get(), shadeViewManager_Factory.loggerProvider.get(), shadeViewManager_Factory.viewBarnProvider.get());
    }

    public ShadeViewManagerFactory_Impl(ShadeViewManager_Factory shadeViewManager_Factory) {
        this.delegateFactory = shadeViewManager_Factory;
    }
}
