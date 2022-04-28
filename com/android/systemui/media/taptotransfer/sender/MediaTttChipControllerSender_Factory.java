package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaTttChipControllerSender_Factory implements Factory<MediaTttChipControllerSender> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public final Object get() {
        return new MediaTttChipControllerSender(this.commandQueueProvider.get(), this.contextProvider.get(), this.windowManagerProvider.get(), this.mainExecutorProvider.get());
    }

    public MediaTttChipControllerSender_Factory(Provider<CommandQueue> provider, Provider<Context> provider2, Provider<WindowManager> provider3, Provider<DelayableExecutor> provider4) {
        this.commandQueueProvider = provider;
        this.contextProvider = provider2;
        this.windowManagerProvider = provider3;
        this.mainExecutorProvider = provider4;
    }
}
