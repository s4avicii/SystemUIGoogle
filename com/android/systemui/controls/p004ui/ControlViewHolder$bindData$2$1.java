package com.android.systemui.controls.p004ui;

import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$bindData$2$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$bindData$2$1 implements View.OnLongClickListener {
    public final /* synthetic */ ControlViewHolder this$0;

    public ControlViewHolder$bindData$2$1(ControlViewHolder controlViewHolder) {
        this.this$0 = controlViewHolder;
    }

    public final boolean onLongClick(View view) {
        ControlViewHolder controlViewHolder = this.this$0;
        Objects.requireNonNull(controlViewHolder);
        controlViewHolder.controlActionCoordinator.longPress(this.this$0);
        return true;
    }
}
