package com.android.p012wm.shell.startingsurface;

import android.app.ActivityManager;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4;

/* renamed from: com.android.wm.shell.startingsurface.StartingSurface */
public interface StartingSurface {

    /* renamed from: com.android.wm.shell.startingsurface.StartingSurface$SysuiProxy */
    public interface SysuiProxy {
    }

    IStartingWindow createExternalInterface() {
        return null;
    }

    int getBackgroundColor(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return -16777216;
    }

    void setSysuiProxy(StatusBar$$ExternalSyntheticLambda4 statusBar$$ExternalSyntheticLambda4);
}
