package com.android.systemui.controls.p004ui;

import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ThumbnailBehavior$initialize$1 */
/* compiled from: ThumbnailBehavior.kt */
public final class ThumbnailBehavior$initialize$1 implements View.OnClickListener {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ ThumbnailBehavior this$0;

    public ThumbnailBehavior$initialize$1(ControlViewHolder controlViewHolder, ThumbnailBehavior thumbnailBehavior) {
        this.$cvh = controlViewHolder;
        this.this$0 = thumbnailBehavior;
    }

    public final void onClick(View view) {
        ControlViewHolder controlViewHolder = this.$cvh;
        Objects.requireNonNull(controlViewHolder);
        ControlActionCoordinator controlActionCoordinator = controlViewHolder.controlActionCoordinator;
        ControlViewHolder controlViewHolder2 = this.$cvh;
        ThumbnailBehavior thumbnailBehavior = this.this$0;
        Objects.requireNonNull(thumbnailBehavior);
        ControlTemplate controlTemplate = thumbnailBehavior.template;
        Control control = null;
        if (controlTemplate == null) {
            controlTemplate = null;
        }
        String templateId = controlTemplate.getTemplateId();
        ThumbnailBehavior thumbnailBehavior2 = this.this$0;
        Objects.requireNonNull(thumbnailBehavior2);
        Control control2 = thumbnailBehavior2.control;
        if (control2 != null) {
            control = control2;
        }
        controlActionCoordinator.touch(controlViewHolder2, templateId, control);
    }
}
