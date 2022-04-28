package com.android.systemui.controls.p004ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ToggleBehavior */
/* compiled from: ToggleBehavior.kt */
public final class ToggleBehavior implements Behavior {
    public Control control;
    public ControlViewHolder cvh;
    public ToggleTemplate template;

    public final void bind(ControlWithState controlWithState, int i) {
        ToggleTemplate toggleTemplate;
        Control control2 = controlWithState.control;
        Intrinsics.checkNotNull(control2);
        this.control = control2;
        ControlViewHolder controlViewHolder = this.cvh;
        ControlViewHolder controlViewHolder2 = null;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        CharSequence statusText = control2.getStatusText();
        Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
        controlViewHolder.setStatusText(statusText, false);
        Control control3 = this.control;
        if (control3 == null) {
            control3 = null;
        }
        TemperatureControlTemplate controlTemplate = control3.getControlTemplate();
        if (controlTemplate instanceof ToggleTemplate) {
            toggleTemplate = (ToggleTemplate) controlTemplate;
        } else if (controlTemplate instanceof TemperatureControlTemplate) {
            ControlTemplate template2 = controlTemplate.getTemplate();
            Objects.requireNonNull(template2, "null cannot be cast to non-null type android.service.controls.templates.ToggleTemplate");
            toggleTemplate = (ToggleTemplate) template2;
        } else {
            Log.e("ControlsUiController", Intrinsics.stringPlus("Unsupported template type: ", controlTemplate));
            return;
        }
        this.template = toggleTemplate;
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 == null) {
            controlViewHolder3 = null;
        }
        Objects.requireNonNull(controlViewHolder3);
        Drawable background = controlViewHolder3.layout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        ((LayerDrawable) background).findDrawableByLayerId(C1777R.C1779id.clip_layer).setLevel(10000);
        ToggleTemplate toggleTemplate2 = this.template;
        if (toggleTemplate2 == null) {
            toggleTemplate2 = null;
        }
        boolean isChecked = toggleTemplate2.isChecked();
        ControlViewHolder controlViewHolder4 = this.cvh;
        if (controlViewHolder4 != null) {
            controlViewHolder2 = controlViewHolder4;
        }
        controlViewHolder2.mo7819x3918d5b8(isChecked, i, true);
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        controlViewHolder.layout.setOnClickListener(new ToggleBehavior$initialize$1(controlViewHolder, this));
    }
}
