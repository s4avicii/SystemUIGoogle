package com.android.systemui.controls.p004ui;

import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.view.View;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;
import java.util.Set;

/* renamed from: com.android.systemui.controls.ui.TouchBehavior$initialize$1 */
/* compiled from: TouchBehavior.kt */
public final class TouchBehavior$initialize$1 implements View.OnClickListener {
    public final /* synthetic */ ControlViewHolder $cvh;
    public final /* synthetic */ TouchBehavior this$0;

    public TouchBehavior$initialize$1(ControlViewHolder controlViewHolder, TouchBehavior touchBehavior) {
        this.$cvh = controlViewHolder;
        this.this$0 = touchBehavior;
    }

    public final void onClick(View view) {
        ControlViewHolder controlViewHolder = this.$cvh;
        Objects.requireNonNull(controlViewHolder);
        ControlActionCoordinator controlActionCoordinator = controlViewHolder.controlActionCoordinator;
        ControlViewHolder controlViewHolder2 = this.$cvh;
        TouchBehavior touchBehavior = this.this$0;
        Objects.requireNonNull(touchBehavior);
        ControlTemplate controlTemplate = touchBehavior.template;
        ControlTemplate controlTemplate2 = null;
        if (controlTemplate == null) {
            controlTemplate = null;
        }
        String templateId = controlTemplate.getTemplateId();
        TouchBehavior touchBehavior2 = this.this$0;
        Objects.requireNonNull(touchBehavior2);
        Control control = touchBehavior2.control;
        if (control == null) {
            control = null;
        }
        controlActionCoordinator.touch(controlViewHolder2, templateId, control);
        TouchBehavior touchBehavior3 = this.this$0;
        Objects.requireNonNull(touchBehavior3);
        ControlTemplate controlTemplate3 = touchBehavior3.template;
        if (controlTemplate3 != null) {
            controlTemplate2 = controlTemplate3;
        }
        if (controlTemplate2 instanceof StatelessTemplate) {
            TouchBehavior touchBehavior4 = this.this$0;
            touchBehavior4.statelessTouch = true;
            this.$cvh.mo7819x3918d5b8(touchBehavior4.getEnabled(), this.this$0.lastColorOffset, true);
            ControlViewHolder controlViewHolder3 = this.$cvh;
            Objects.requireNonNull(controlViewHolder3);
            DelayableExecutor delayableExecutor = controlViewHolder3.uiExecutor;
            final TouchBehavior touchBehavior5 = this.this$0;
            final ControlViewHolder controlViewHolder4 = this.$cvh;
            delayableExecutor.executeDelayed(new Runnable() {
                public final void run() {
                    TouchBehavior touchBehavior = touchBehavior5;
                    touchBehavior.statelessTouch = false;
                    ControlViewHolder controlViewHolder = controlViewHolder4;
                    boolean enabled = touchBehavior.getEnabled();
                    int i = touchBehavior5.lastColorOffset;
                    Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
                    controlViewHolder.mo7819x3918d5b8(enabled, i, true);
                }
            }, 3000);
        }
    }
}
