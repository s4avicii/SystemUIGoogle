package com.android.systemui.controls.p004ui;

import android.service.controls.Control;
import android.service.controls.templates.TemperatureControlTemplate;
import android.view.View;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.TemperatureControlBehavior$bind$1 */
/* compiled from: TemperatureControlBehavior.kt */
public final class TemperatureControlBehavior$bind$1 implements View.OnClickListener {
    public final /* synthetic */ TemperatureControlTemplate $template;
    public final /* synthetic */ TemperatureControlBehavior this$0;

    public TemperatureControlBehavior$bind$1(TemperatureControlBehavior temperatureControlBehavior, TemperatureControlTemplate temperatureControlTemplate) {
        this.this$0 = temperatureControlBehavior;
        this.$template = temperatureControlTemplate;
    }

    public final void onClick(View view) {
        ControlViewHolder cvh = this.this$0.getCvh();
        Objects.requireNonNull(cvh);
        ControlActionCoordinator controlActionCoordinator = cvh.controlActionCoordinator;
        ControlViewHolder cvh2 = this.this$0.getCvh();
        String templateId = this.$template.getTemplateId();
        TemperatureControlBehavior temperatureControlBehavior = this.this$0;
        Objects.requireNonNull(temperatureControlBehavior);
        Control control = temperatureControlBehavior.control;
        if (control == null) {
            control = null;
        }
        controlActionCoordinator.touch(cvh2, templateId, control);
    }
}
