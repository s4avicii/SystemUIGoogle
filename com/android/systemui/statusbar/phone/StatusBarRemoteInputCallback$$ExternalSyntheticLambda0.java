package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.os.RemoteException;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager$1$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarRemoteInputCallback$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ StatusBarRemoteInputCallback f$0;
    public final /* synthetic */ PendingIntent f$1;
    public final /* synthetic */ NotificationRemoteInputManager.ClickHandler f$2;

    public /* synthetic */ StatusBarRemoteInputCallback$$ExternalSyntheticLambda0(StatusBarRemoteInputCallback statusBarRemoteInputCallback, PendingIntent pendingIntent, NotificationRemoteInputManager$1$$ExternalSyntheticLambda0 notificationRemoteInputManager$1$$ExternalSyntheticLambda0) {
        this.f$0 = statusBarRemoteInputCallback;
        this.f$1 = pendingIntent;
        this.f$2 = notificationRemoteInputManager$1$$ExternalSyntheticLambda0;
    }

    public final boolean onDismiss() {
        StatusBarRemoteInputCallback statusBarRemoteInputCallback = this.f$0;
        PendingIntent pendingIntent = this.f$1;
        NotificationRemoteInputManager.ClickHandler clickHandler = this.f$2;
        Objects.requireNonNull(statusBarRemoteInputCallback);
        statusBarRemoteInputCallback.mActionClickLogger.logKeyguardGone(pendingIntent);
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException unused) {
        }
        if (!((NotificationRemoteInputManager$1$$ExternalSyntheticLambda0) clickHandler).handleClick()) {
            return false;
        }
        statusBarRemoteInputCallback.mShadeController.closeShadeIfOpen();
        return false;
    }
}
