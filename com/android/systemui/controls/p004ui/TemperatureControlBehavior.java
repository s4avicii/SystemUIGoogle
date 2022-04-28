package com.android.systemui.controls.p004ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.p004ui.ControlViewHolder;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.TemperatureControlBehavior */
/* compiled from: TemperatureControlBehavior.kt */
public final class TemperatureControlBehavior implements Behavior {
    public Drawable clipLayer;
    public Control control;
    public ControlViewHolder cvh;
    public Behavior subBehavior;

    public final void bind(ControlWithState controlWithState, int i) {
        boolean z;
        Control control2 = controlWithState.control;
        Intrinsics.checkNotNull(control2);
        this.control = control2;
        ControlViewHolder cvh2 = getCvh();
        Control control3 = this.control;
        Control control4 = null;
        if (control3 == null) {
            control3 = null;
        }
        CharSequence statusText = control3.getStatusText();
        Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
        int i2 = 0;
        cvh2.setStatusText(statusText, false);
        ControlViewHolder cvh3 = getCvh();
        Objects.requireNonNull(cvh3);
        Drawable background = cvh3.layout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        this.clipLayer = ((LayerDrawable) background).findDrawableByLayerId(C1777R.C1779id.clip_layer);
        Control control5 = this.control;
        if (control5 == null) {
            control5 = null;
        }
        TemperatureControlTemplate controlTemplate = control5.getControlTemplate();
        Objects.requireNonNull(controlTemplate, "null cannot be cast to non-null type android.service.controls.templates.TemperatureControlTemplate");
        TemperatureControlTemplate temperatureControlTemplate = controlTemplate;
        int currentActiveMode = temperatureControlTemplate.getCurrentActiveMode();
        ControlTemplate template = temperatureControlTemplate.getTemplate();
        if (Intrinsics.areEqual(template, ControlTemplate.getNoTemplateObject()) || Intrinsics.areEqual(template, ControlTemplate.getErrorTemplate())) {
            if (currentActiveMode == 0 || currentActiveMode == 1) {
                z = false;
            } else {
                z = true;
            }
            Control control6 = this.clipLayer;
            if (control6 != null) {
                control4 = control6;
            }
            if (z) {
                i2 = 10000;
            }
            control4.setLevel(i2);
            getCvh().mo7819x3918d5b8(z, currentActiveMode, true);
            ControlViewHolder cvh4 = getCvh();
            Objects.requireNonNull(cvh4);
            cvh4.layout.setOnClickListener(new TemperatureControlBehavior$bind$1(this, temperatureControlTemplate));
            return;
        }
        ControlViewHolder cvh5 = getCvh();
        Behavior behavior = this.subBehavior;
        Control control7 = this.control;
        if (control7 == null) {
            control7 = null;
        }
        int status = control7.getStatus();
        Control control8 = this.control;
        if (control8 != null) {
            control4 = control8;
        }
        this.subBehavior = cvh5.bindBehavior(behavior, ControlViewHolder.Companion.findBehaviorClass(status, template, control4.getDeviceType()), currentActiveMode);
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
