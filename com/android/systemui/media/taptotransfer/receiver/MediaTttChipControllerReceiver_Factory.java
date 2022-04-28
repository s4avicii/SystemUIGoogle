package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaTttChipControllerReceiver_Factory implements Factory<MediaTttChipControllerReceiver> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public final Object get() {
        return new MediaTttChipControllerReceiver(this.commandQueueProvider.get(), this.contextProvider.get(), this.windowManagerProvider.get(), this.mainExecutorProvider.get(), this.mainHandlerProvider.get());
    }

    public MediaTttChipControllerReceiver_Factory(Provider<CommandQueue> provider, Provider<Context> provider2, Provider<WindowManager> provider3, Provider<DelayableExecutor> provider4, Provider<Handler> provider5) {
        this.commandQueueProvider = provider;
        this.contextProvider = provider2;
        this.windowManagerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.mainHandlerProvider = provider5;
    }
}
