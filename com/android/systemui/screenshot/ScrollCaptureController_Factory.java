package com.android.systemui.screenshot;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ScrollCaptureController_Factory implements Factory<ScrollCaptureController> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<ScrollCaptureClient> clientProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ImageTileSet> imageTileSetProvider;
    public final Provider<UiEventLogger> loggerProvider;

    public final Object get() {
        return new ScrollCaptureController(this.contextProvider.get(), this.bgExecutorProvider.get(), this.clientProvider.get(), this.imageTileSetProvider.get(), this.loggerProvider.get());
    }

    public ScrollCaptureController_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<ScrollCaptureClient> provider3, Provider<ImageTileSet> provider4, Provider<UiEventLogger> provider5) {
        this.contextProvider = provider;
        this.bgExecutorProvider = provider2;
        this.clientProvider = provider3;
        this.imageTileSetProvider = provider4;
        this.loggerProvider = provider5;
    }
}
