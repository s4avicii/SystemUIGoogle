package com.android.systemui.statusbar.notification;

import android.app.ActivityTaskManager;
import android.app.NotificationManager;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.util.ArraySet;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InstantAppNotifier$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ InstantAppNotifier f$0;
    public final /* synthetic */ NotificationManager f$1;
    public final /* synthetic */ IPackageManager f$2;

    public /* synthetic */ InstantAppNotifier$$ExternalSyntheticLambda0(InstantAppNotifier instantAppNotifier, NotificationManager notificationManager, IPackageManager iPackageManager) {
        this.f$0 = instantAppNotifier;
        this.f$1 = notificationManager;
        this.f$2 = iPackageManager;
    }

    public final void run() {
        int windowingMode;
        InstantAppNotifier instantAppNotifier = this.f$0;
        NotificationManager notificationManager = this.f$1;
        IPackageManager iPackageManager = this.f$2;
        Objects.requireNonNull(instantAppNotifier);
        ArraySet arraySet = new ArraySet(instantAppNotifier.mCurrentNotifs);
        try {
            ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = ActivityTaskManager.getService().getFocusedRootTaskInfo();
            if (focusedRootTaskInfo != null && ((windowingMode = focusedRootTaskInfo.configuration.windowConfiguration.getWindowingMode()) == 1 || windowingMode == 4 || windowingMode == 5)) {
                instantAppNotifier.checkAndPostForStack(focusedRootTaskInfo, arraySet, notificationManager, iPackageManager);
            }
            if (instantAppNotifier.mDockedStackExists) {
                try {
                    instantAppNotifier.checkAndPostForStack(ActivityTaskManager.getService().getRootTaskInfo(3, 0), arraySet, notificationManager, iPackageManager);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        } catch (RemoteException e2) {
            e2.rethrowFromSystemServer();
        }
        arraySet.forEach(new InstantAppNotifier$$ExternalSyntheticLambda1(instantAppNotifier, notificationManager, 0));
    }
}
