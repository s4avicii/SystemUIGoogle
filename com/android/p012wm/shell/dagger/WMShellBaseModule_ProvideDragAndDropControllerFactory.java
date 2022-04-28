package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideDragAndDropControllerFactory */
public final class WMShellBaseModule_ProvideDragAndDropControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider iconProvider;
    public final Provider mainExecutorProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideDragAndDropControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.displayControllerProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.iconProvider = provider4;
        this.mainExecutorProvider = provider5;
    }

    public static WMShellBaseModule_ProvideDragAndDropControllerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new WMShellBaseModule_ProvideDragAndDropControllerFactory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public static WMShellBaseModule_ProvideDragAndDropControllerFactory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new WMShellBaseModule_ProvideDragAndDropControllerFactory(provider, provider2, provider3, provider4, provider5, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.contextProvider.get();
                return new DragAndDropController((DisplayController) this.displayControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (IconProvider) this.iconProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
            default:
                return new RemoteInputCoordinator((DumpManager) this.contextProvider.get(), (RemoteInputNotificationRebuilder) this.displayControllerProvider.get(), (NotificationRemoteInputManager) this.uiEventLoggerProvider.get(), (Handler) this.iconProvider.get(), (SmartReplyController) this.mainExecutorProvider.get());
        }
    }
}
