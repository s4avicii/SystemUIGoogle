package com.android.systemui.controls.p004ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.TouchBehavior */
/* compiled from: TouchBehavior.kt */
public final class TouchBehavior implements Behavior {
    public Control control;
    public ControlViewHolder cvh;
    public int lastColorOffset;
    public boolean statelessTouch;
    public ControlTemplate template;

    public final void bind(ControlWithState controlWithState, int i) {
        Control control2 = controlWithState.control;
        Intrinsics.checkNotNull(control2);
        this.control = control2;
        this.lastColorOffset = i;
        ControlViewHolder controlViewHolder = this.cvh;
        ControlViewHolder controlViewHolder2 = null;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        CharSequence statusText = control2.getStatusText();
        Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
        int i2 = 0;
        controlViewHolder.setStatusText(statusText, false);
        Control control3 = this.control;
        if (control3 == null) {
            control3 = null;
        }
        this.template = control3.getControlTemplate();
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 == null) {
            controlViewHolder3 = null;
        }
        Objects.requireNonNull(controlViewHolder3);
        Drawable background = controlViewHolder3.layout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        Drawable findDrawableByLayerId = ((LayerDrawable) background).findDrawableByLayerId(C1777R.C1779id.clip_layer);
        if (getEnabled()) {
            i2 = 10000;
        }
        findDrawableByLayerId.setLevel(i2);
        ControlViewHolder controlViewHolder4 = this.cvh;
        if (controlViewHolder4 != null) {
            controlViewHolder2 = controlViewHolder4;
        }
        controlViewHolder2.mo7819x3918d5b8(getEnabled(), i, true);
    }

    public final boolean getEnabled() {
        if (this.lastColorOffset > 0 || this.statelessTouch) {
            return true;
        }
        return false;
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        controlViewHolder.layout.setOnClickListener(new TouchBehavior$initialize$1(controlViewHolder, this));
    }
}
