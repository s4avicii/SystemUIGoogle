package com.android.systemui.controls.p004ui;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.DetailDialog */
/* compiled from: DetailDialog.kt */
public final class DetailDialog extends Dialog {
    public final Context activityContext;
    public int detailTaskId = -1;
    public final Intent fillInIntent;
    public final PendingIntent pendingIntent;
    public final DetailDialog$stateCallback$1 stateCallback;
    public final TaskView taskView;
    public View taskViewContainer;
    public final float taskWidthPercentWidth;

    public DetailDialog(Context context, TaskView taskView2, PendingIntent pendingIntent2, ControlViewHolder controlViewHolder) {
        super(context, 2132018185);
        this.activityContext = context;
        this.taskView = taskView2;
        this.pendingIntent = pendingIntent2;
        this.taskWidthPercentWidth = context.getResources().getFloat(C1777R.dimen.controls_task_view_width_percentage);
        Intent intent = new Intent();
        intent.putExtra("controls.DISPLAY_IN_PANEL", true);
        intent.addFlags(524288);
        intent.addFlags(134217728);
        this.fillInIntent = intent;
        DetailDialog$stateCallback$1 detailDialog$stateCallback$1 = new DetailDialog$stateCallback$1(this);
        this.stateCallback = detailDialog$stateCallback$1;
        getWindow().addFlags(32);
        getWindow().addPrivateFlags(536870912);
        setContentView(C1777R.layout.controls_detail_dialog);
        this.taskViewContainer = requireViewById(C1777R.C1779id.control_task_view_container);
        ViewGroup viewGroup = (ViewGroup) requireViewById(C1777R.C1779id.controls_activity_view);
        viewGroup.addView(taskView2);
        viewGroup.setAlpha(0.0f);
        ((ImageView) requireViewById(C1777R.C1779id.control_detail_close)).setOnClickListener(new DetailDialog$2$1(this));
        requireViewById(C1777R.C1779id.control_detail_root).setOnClickListener(new DetailDialog$3$1(this));
        ImageView imageView = (ImageView) requireViewById(C1777R.C1779id.control_detail_open_in_app);
        imageView.setOnClickListener(new DetailDialog$4$1(this, imageView));
        getWindow().getDecorView().setOnApplyWindowInsetsListener(C07475.INSTANCE);
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(getContext().getResources())) {
            taskView2.setCornerRadius((float) getContext().getResources().getDimensionPixelSize(C1777R.dimen.controls_activity_view_corner_radius));
        }
        Objects.requireNonNull(controlViewHolder);
        DelayableExecutor delayableExecutor = controlViewHolder.uiExecutor;
        Objects.requireNonNull(taskView2);
        if (taskView2.mListener == null) {
            taskView2.mListener = detailDialog$stateCallback$1;
            taskView2.mListenerExecutor = delayableExecutor;
            return;
        }
        throw new IllegalStateException("Trying to set a listener when one has already been set");
    }

    public final void dismiss() {
        if (isShowing()) {
            TaskView taskView2 = this.taskView;
            Objects.requireNonNull(taskView2);
            taskView2.performRelease();
            super.dismiss();
        }
    }
}
