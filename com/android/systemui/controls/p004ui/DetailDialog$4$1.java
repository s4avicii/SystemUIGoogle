package com.android.systemui.controls.p004ui;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.DetailDialog$4$1 */
/* compiled from: DetailDialog.kt */
public final class DetailDialog$4$1 implements View.OnClickListener {
    public final /* synthetic */ ImageView $this_apply;
    public final /* synthetic */ DetailDialog this$0;

    public DetailDialog$4$1(DetailDialog detailDialog, ImageView imageView) {
        this.this$0 = detailDialog;
        this.$this_apply = imageView;
    }

    public final void onClick(View view) {
        DetailDialog detailDialog = this.this$0;
        Objects.requireNonNull(detailDialog);
        if (detailDialog.detailTaskId != -1) {
            ActivityTaskManager.getInstance().removeTask(detailDialog.detailTaskId);
            detailDialog.detailTaskId = -1;
        }
        this.this$0.dismiss();
        this.$this_apply.getContext().sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        DetailDialog detailDialog2 = this.this$0;
        Objects.requireNonNull(detailDialog2);
        detailDialog2.pendingIntent.send();
    }
}
