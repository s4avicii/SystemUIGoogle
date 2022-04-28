package com.android.systemui.controls.p004ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ResolveInfo;
import com.android.p012wm.shell.TaskView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1 */
/* compiled from: ControlActionCoordinatorImpl.kt */
public final class ControlActionCoordinatorImpl$showDetail$1 implements Runnable {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ PendingIntent $pendingIntent;
    public final /* synthetic */ ControlActionCoordinatorImpl this$0;

    public ControlActionCoordinatorImpl$showDetail$1(ControlActionCoordinatorImpl controlActionCoordinatorImpl, PendingIntent pendingIntent, ControlViewHolder controlViewHolder) {
        this.this$0 = controlActionCoordinatorImpl;
        this.$pendingIntent = pendingIntent;
        this.$cvh = controlViewHolder;
    }

    public final void run() {
        final List<ResolveInfo> queryIntentActivities = this.this$0.context.getPackageManager().queryIntentActivities(this.$pendingIntent.getIntent(), 65536);
        final ControlActionCoordinatorImpl controlActionCoordinatorImpl = this.this$0;
        DelayableExecutor delayableExecutor = controlActionCoordinatorImpl.uiExecutor;
        final ControlViewHolder controlViewHolder = this.$cvh;
        final PendingIntent pendingIntent = this.$pendingIntent;
        delayableExecutor.execute(new Runnable() {
            public final void run() {
                if (!(!queryIntentActivities.isEmpty()) || !controlActionCoordinatorImpl.taskViewFactory.isPresent()) {
                    controlViewHolder.setErrorStatus();
                    return;
                }
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = controlActionCoordinatorImpl;
                Context context = controlActionCoordinatorImpl.context;
                DelayableExecutor delayableExecutor = controlActionCoordinatorImpl.uiExecutor;
                final PendingIntent pendingIntent = pendingIntent;
                final ControlViewHolder controlViewHolder = controlViewHolder;
                controlActionCoordinatorImpl.taskViewFactory.get().create(context, delayableExecutor, new Consumer() {
                    public final void accept(Object obj) {
                        TaskView taskView = (TaskView) obj;
                        ControlActionCoordinatorImpl controlActionCoordinatorImpl = controlActionCoordinatorImpl;
                        ControlActionCoordinatorImpl controlActionCoordinatorImpl2 = controlActionCoordinatorImpl;
                        Objects.requireNonNull(controlActionCoordinatorImpl2);
                        Context context = controlActionCoordinatorImpl2.activityContext;
                        if (context == null) {
                            context = null;
                        }
                        DetailDialog detailDialog = new DetailDialog(context, taskView, pendingIntent, controlViewHolder);
                        detailDialog.setOnDismissListener(new ControlActionCoordinatorImpl$showDetail$1$1$1$1$1(controlActionCoordinatorImpl));
                        detailDialog.show();
                        controlActionCoordinatorImpl.dialog = detailDialog;
                    }
                });
            }
        });
    }
}
