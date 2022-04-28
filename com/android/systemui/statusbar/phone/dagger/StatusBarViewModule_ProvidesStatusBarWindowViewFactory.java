package com.android.systemui.statusbar.phone.dagger;

import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.notification.row.dagger.NotificationShelfComponent;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class StatusBarViewModule_ProvidesStatusBarWindowViewFactory implements Factory<NotificationShelfController> {
    public final Provider<NotificationShelfComponent.Builder> notificationShelfComponentBuilderProvider;
    public final Provider<NotificationShelf> notificationShelfProvider;

    public final Object get() {
        NotificationShelfController notificationShelfController = this.notificationShelfComponentBuilderProvider.get().notificationShelf(this.notificationShelfProvider.get()).build().getNotificationShelfController();
        Objects.requireNonNull(notificationShelfController);
        notificationShelfController.mActivatableNotificationViewController.init();
        NotificationShelf notificationShelf = notificationShelfController.mView;
        Objects.requireNonNull(notificationShelf);
        notificationShelf.mController = notificationShelfController;
        notificationShelfController.mView.addOnAttachStateChangeListener(notificationShelfController.mOnAttachStateChangeListener);
        if (notificationShelfController.mView.isAttachedToWindow()) {
            notificationShelfController.mOnAttachStateChangeListener.onViewAttachedToWindow(notificationShelfController.mView);
        }
        return notificationShelfController;
    }

    public StatusBarViewModule_ProvidesStatusBarWindowViewFactory(Provider<NotificationShelfComponent.Builder> provider, Provider<NotificationShelf> provider2) {
        this.notificationShelfComponentBuilderProvider = provider;
        this.notificationShelfProvider = provider2;
    }
}
