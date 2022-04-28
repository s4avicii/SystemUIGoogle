package com.android.systemui.controls.p004ui;

import android.service.controls.Control;
import java.util.Set;

/* renamed from: com.android.systemui.controls.ui.DefaultBehavior */
/* compiled from: DefaultBehavior.kt */
public final class DefaultBehavior implements Behavior {
    public ControlViewHolder cvh;

    public final void bind(ControlWithState controlWithState, int i) {
        CharSequence charSequence;
        ControlViewHolder controlViewHolder = this.cvh;
        ControlViewHolder controlViewHolder2 = null;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        Control control = controlWithState.control;
        if (control == null || (charSequence = control.getStatusText()) == null) {
            charSequence = "";
        }
        Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
        controlViewHolder.setStatusText(charSequence, false);
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 != null) {
            controlViewHolder2 = controlViewHolder3;
        }
        controlViewHolder2.mo7819x3918d5b8(false, i, true);
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
