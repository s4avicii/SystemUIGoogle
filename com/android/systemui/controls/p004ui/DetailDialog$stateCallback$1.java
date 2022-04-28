package com.android.systemui.controls.p004ui;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.DetailDialog$stateCallback$1 */
/* compiled from: DetailDialog.kt */
public final class DetailDialog$stateCallback$1 implements TaskView.Listener {
    public final /* synthetic */ DetailDialog this$0;

    public DetailDialog$stateCallback$1(DetailDialog detailDialog) {
        this.this$0 = detailDialog;
    }

    public final void onBackPressedOnTaskRoot(int i) {
        this.this$0.dismiss();
    }

    public final void onInitialized() {
        DetailDialog detailDialog = this.this$0;
        View view = detailDialog.taskViewContainer;
        if (view == null) {
            view = null;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (((float) view.getWidth()) * detailDialog.taskWidthPercentWidth);
        view.setLayoutParams(layoutParams);
        DetailDialog detailDialog2 = this.this$0;
        Objects.requireNonNull(detailDialog2);
        ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(detailDialog2.activityContext, 0, 0);
        DetailDialog detailDialog3 = this.this$0;
        Objects.requireNonNull(detailDialog3);
        TaskView taskView = detailDialog3.taskView;
        DetailDialog detailDialog4 = this.this$0;
        Objects.requireNonNull(detailDialog4);
        PendingIntent pendingIntent = detailDialog4.pendingIntent;
        DetailDialog detailDialog5 = this.this$0;
        Intent intent = detailDialog5.fillInIntent;
        Objects.requireNonNull(detailDialog5);
        WindowMetrics currentWindowMetrics = ((WindowManager) detailDialog5.getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics();
        Rect bounds = currentWindowMetrics.getBounds();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        taskView.startActivity(pendingIntent, intent, makeCustomAnimation, new Rect(bounds.left - insetsIgnoringVisibility.left, bounds.top + insetsIgnoringVisibility.top + detailDialog5.getContext().getResources().getDimensionPixelSize(C1777R.dimen.controls_detail_dialog_header_height), bounds.right - insetsIgnoringVisibility.right, bounds.bottom - insetsIgnoringVisibility.bottom));
    }

    public final void onReleased() {
        DetailDialog detailDialog = this.this$0;
        Objects.requireNonNull(detailDialog);
        if (detailDialog.detailTaskId != -1) {
            ActivityTaskManager.getInstance().removeTask(detailDialog.detailTaskId);
            detailDialog.detailTaskId = -1;
        }
    }

    public final void onTaskCreated(int i) {
        DetailDialog detailDialog = this.this$0;
        Objects.requireNonNull(detailDialog);
        detailDialog.detailTaskId = i;
        ((ViewGroup) this.this$0.requireViewById(C1777R.C1779id.controls_activity_view)).setAlpha(1.0f);
    }

    public final void onTaskRemovalStarted() {
        DetailDialog detailDialog = this.this$0;
        Objects.requireNonNull(detailDialog);
        detailDialog.detailTaskId = -1;
        this.this$0.dismiss();
    }
}
