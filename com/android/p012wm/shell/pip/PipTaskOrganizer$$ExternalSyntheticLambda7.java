package com.android.p012wm.shell.pip;

import android.app.ActivityManager;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenTaskListener;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ WindowContainerTransaction f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda7(PipTaskOrganizer pipTaskOrganizer, int i, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = i;
        this.f$2 = windowContainerTransaction;
    }

    public final void accept(Object obj) {
        WindowContainerToken windowContainerToken;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        int i = this.f$1;
        WindowContainerTransaction windowContainerTransaction = this.f$2;
        LegacySplitScreenController legacySplitScreenController = (LegacySplitScreenController) obj;
        if (i == 4) {
            WindowContainerToken windowContainerToken2 = pipTaskOrganizer.mToken;
            Objects.requireNonNull(legacySplitScreenController);
            LegacySplitScreenTaskListener legacySplitScreenTaskListener = legacySplitScreenController.mSplits;
            if (legacySplitScreenTaskListener == null || (runningTaskInfo = legacySplitScreenTaskListener.mSecondary) == null) {
                windowContainerToken = null;
            } else {
                windowContainerToken = runningTaskInfo.token;
            }
            windowContainerTransaction.reparent(windowContainerToken2, windowContainerToken, true);
            return;
        }
        Objects.requireNonNull(pipTaskOrganizer);
    }
}
