package com.android.systemui;

import android.os.HandlerThread;
import com.android.systemui.dagger.WMComponent;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIFactory$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SystemUIFactory f$0;
    public final /* synthetic */ WMComponent.Builder f$1;
    public final /* synthetic */ HandlerThread f$2;

    public /* synthetic */ SystemUIFactory$$ExternalSyntheticLambda0(SystemUIFactory systemUIFactory, WMComponent.Builder builder, HandlerThread handlerThread) {
        this.f$0 = systemUIFactory;
        this.f$1 = builder;
        this.f$2 = handlerThread;
    }

    public final void run() {
        SystemUIFactory systemUIFactory = this.f$0;
        WMComponent.Builder builder = this.f$1;
        HandlerThread handlerThread = this.f$2;
        Objects.requireNonNull(systemUIFactory);
        builder.setShellMainThread(handlerThread);
        systemUIFactory.mWMComponent = builder.build();
    }
}
