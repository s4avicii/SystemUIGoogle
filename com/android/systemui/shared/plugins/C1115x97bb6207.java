package com.android.systemui.shared.plugins;

import java.lang.Thread;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.shared.plugins.PluginManagerImpl$PluginExceptionHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1115x97bb6207 implements Consumer {
    public final /* synthetic */ Thread f$0;
    public final /* synthetic */ Throwable f$1;

    public /* synthetic */ C1115x97bb6207(Thread thread, Throwable th) {
        this.f$0 = thread;
        this.f$1 = th;
    }

    public final void accept(Object obj) {
        ((Thread.UncaughtExceptionHandler) obj).uncaughtException(this.f$0, this.f$1);
    }
}
