package com.android.systemui.controls.p004ui;

import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ToggleBehavior$initialize$1 */
/* compiled from: ToggleBehavior.kt */
public final class ToggleBehavior$initialize$1 implements View.OnClickListener {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ ToggleBehavior this$0;

    public ToggleBehavior$initialize$1(ControlViewHolder controlViewHolder, ToggleBehavior toggleBehavior) {
        this.$cvh = controlViewHolder;
        this.this$0 = toggleBehavior;
    }

    public final void onClick(View view) {
        ControlViewHolder controlViewHolder = this.$cvh;
        Objects.requireNonNull(controlViewHolder);
        ControlActionCoordinator controlActionCoordinator = controlViewHolder.controlActionCoordinator;
        ControlViewHolder controlViewHolder2 = this.$cvh;
        ToggleBehavior toggleBehavior = this.this$0;
        Objects.requireNonNull(toggleBehavior);
        ControlTemplate controlTemplate = toggleBehavior.template;
        ToggleTemplate toggleTemplate = null;
        if (controlTemplate == null) {
            controlTemplate = null;
        }
        String templateId = controlTemplate.getTemplateId();
        ToggleBehavior toggleBehavior2 = this.this$0;
        Objects.requireNonNull(toggleBehavior2);
        ToggleTemplate toggleTemplate2 = toggleBehavior2.template;
        if (toggleTemplate2 != null) {
            toggleTemplate = toggleTemplate2;
        }
        controlActionCoordinator.toggle(controlViewHolder2, templateId, toggleTemplate.isChecked());
    }
}
