package com.android.systemui.media;

import android.content.Context;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.TaskViewFactoryController;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.google.android.systemui.columbus.ContentResolverWrapper;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class RingtonePlayer_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ RingtonePlayer_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new RingtonePlayer((Context) this.contextProvider.get());
            case 1:
                WindowManager windowManager = (WindowManager) ((Context) this.contextProvider.get()).getSystemService(WindowManager.class);
                Objects.requireNonNull(windowManager, "Cannot return null from a non-@Nullable @Provides method");
                return windowManager;
            case 2:
                return new DismissCallbackRegistry((Executor) this.contextProvider.get());
            case 3:
                return new GutsCoordinatorLogger((LogBuffer) this.contextProvider.get());
            case 4:
                return new LockscreenGestureLogger((MetricsLogger) this.contextProvider.get());
            case 5:
                TaskViewFactoryController taskViewFactoryController = (TaskViewFactoryController) this.contextProvider.get();
                Objects.requireNonNull(taskViewFactoryController);
                Optional of = Optional.of(taskViewFactoryController.mImpl);
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
            default:
                return new ContentResolverWrapper((Context) this.contextProvider.get());
        }
    }
}
