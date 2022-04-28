package com.android.systemui.screenrecord;

import android.app.NotificationManager;
import android.view.IWindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class RecordingService_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider controllerProvider;
    public final Provider executorProvider;
    public final Provider keyguardDismissUtilProvider;
    public final Provider notificationManagerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider userContextTrackerProvider;

    public /* synthetic */ RecordingService_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.controllerProvider = provider;
        this.executorProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.notificationManagerProvider = provider4;
        this.userContextTrackerProvider = provider5;
        this.keyguardDismissUtilProvider = provider6;
    }

    public static RecordingService_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new RecordingService_Factory(provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public static RecordingService_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new RecordingService_Factory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public final Object get() {
        DisplayImeController displayImeController;
        switch (this.$r8$classId) {
            case 0:
                return new RecordingService((RecordingController) this.controllerProvider.get(), (Executor) this.executorProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (UserContextProvider) this.userContextTrackerProvider.get(), (KeyguardDismissUtil) this.keyguardDismissUtilProvider.get());
            default:
                Optional optional = (Optional) this.controllerProvider.get();
                IWindowManager iWindowManager = (IWindowManager) this.executorProvider.get();
                DisplayController displayController = (DisplayController) this.uiEventLoggerProvider.get();
                DisplayInsetsController displayInsetsController = (DisplayInsetsController) this.notificationManagerProvider.get();
                ShellExecutor shellExecutor = (ShellExecutor) this.userContextTrackerProvider.get();
                TransactionPool transactionPool = (TransactionPool) this.keyguardDismissUtilProvider.get();
                if (optional.isPresent()) {
                    displayImeController = (DisplayImeController) optional.get();
                } else {
                    displayImeController = new DisplayImeController(iWindowManager, displayController, displayInsetsController, shellExecutor, transactionPool);
                }
                Objects.requireNonNull(displayImeController, "Cannot return null from a non-@Nullable @Provides method");
                return displayImeController;
        }
    }
}
