package com.android.systemui.statusbar.notification.people;

import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationPersonExtractorPlugin;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: PeopleHubNotificationListener.kt */
public final class NotificationPersonExtractorPluginBoundary implements NotificationPersonExtractor {
    public NotificationPersonExtractorPlugin plugin;

    public NotificationPersonExtractorPluginBoundary(ExtensionController extensionController) {
        Class<NotificationPersonExtractorPlugin> cls = NotificationPersonExtractorPlugin.class;
        ExtensionControllerImpl.ExtensionBuilder newExtension = extensionController.newExtension();
        Objects.requireNonNull(newExtension);
        String action = PluginManager.Helper.getAction(cls);
        ExtensionControllerImpl.ExtensionImpl<T> extensionImpl = newExtension.mExtension;
        Objects.requireNonNull(extensionImpl);
        extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.PluginItem(action, cls, (ExtensionController.PluginConverter) null));
        newExtension.mExtension.mCallbacks.add(new Consumer(this) {
            public final /* synthetic */ NotificationPersonExtractorPluginBoundary this$0;

            {
                this.this$0 = r1;
            }

            public final void accept(Object obj) {
                this.this$0.plugin = (NotificationPersonExtractorPlugin) obj;
            }
        });
        ExtensionControllerImpl.ExtensionImpl build = newExtension.build();
        Objects.requireNonNull(build);
        this.plugin = (NotificationPersonExtractorPlugin) build.mItem;
    }

    public final boolean isPersonNotification(StatusBarNotification statusBarNotification) {
        NotificationPersonExtractorPlugin notificationPersonExtractorPlugin = this.plugin;
        if (notificationPersonExtractorPlugin == null) {
            return false;
        }
        return notificationPersonExtractorPlugin.isPersonNotification(statusBarNotification);
    }
}
