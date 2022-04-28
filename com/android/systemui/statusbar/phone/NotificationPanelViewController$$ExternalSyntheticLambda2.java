package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda7;
import com.android.systemui.communal.CommunalSourceMonitor;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda2 implements CommunalSourceMonitor.Callback {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda2(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onSourceAvailable(WeakReference weakReference) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mUiExecutor.execute(new TaskView$$ExternalSyntheticLambda7(notificationPanelViewController, weakReference, 1));
    }
}
