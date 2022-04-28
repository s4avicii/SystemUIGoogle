package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.PendingIntent;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationTemplateViewWrapper$$ExternalSyntheticLambda0 implements PendingIntent.CancelListener {
    public final /* synthetic */ NotificationTemplateViewWrapper f$0;
    public final /* synthetic */ PendingIntent f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ NotificationTemplateViewWrapper$$ExternalSyntheticLambda0(NotificationTemplateViewWrapper notificationTemplateViewWrapper, PendingIntent pendingIntent, ExecutorUtils$$ExternalSyntheticLambda0 executorUtils$$ExternalSyntheticLambda0) {
        this.f$0 = notificationTemplateViewWrapper;
        this.f$1 = pendingIntent;
        this.f$2 = executorUtils$$ExternalSyntheticLambda0;
    }

    public final void onCanceled(PendingIntent pendingIntent) {
        NotificationTemplateViewWrapper notificationTemplateViewWrapper = this.f$0;
        PendingIntent pendingIntent2 = this.f$1;
        Runnable runnable = this.f$2;
        Objects.requireNonNull(notificationTemplateViewWrapper);
        notificationTemplateViewWrapper.mView.post(new StackStateAnimator$$ExternalSyntheticLambda0(notificationTemplateViewWrapper, pendingIntent2, runnable, 1));
    }
}
