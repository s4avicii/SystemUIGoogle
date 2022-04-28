package com.google.android.systemui.gamedashboard;

import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EntryPointController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ EntryPointController$$ExternalSyntheticLambda0(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    public final void accept(Object obj) {
        ((TaskSurfaceHelper) obj).setGameModeForTask(this.f$0, this.f$1);
    }
}
