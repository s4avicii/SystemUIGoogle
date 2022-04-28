package com.android.p012wm.shell.tasksurfacehelper;

import android.view.SurfaceControl;
import com.android.systemui.keyguard.KeyguardViewMediator$9$$ExternalSyntheticLambda0;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelperController$TaskSurfaceHelperImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1936x3c1cf255 implements Consumer {
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ C1936x3c1cf255(Executor executor, Consumer consumer) {
        this.f$0 = executor;
        this.f$1 = consumer;
    }

    public final void accept(Object obj) {
        this.f$0.execute(new KeyguardViewMediator$9$$ExternalSyntheticLambda0(this.f$1, (SurfaceControl.ScreenshotHardwareBuffer) obj, 3));
    }
}
