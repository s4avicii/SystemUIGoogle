package com.android.systemui.controls.p004ui;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ThumbnailTemplate;
import android.util.TypedValue;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ThumbnailBehavior */
/* compiled from: ThumbnailBehavior.kt */
public final class ThumbnailBehavior implements Behavior {
    public Control control;
    public ControlViewHolder cvh;
    public int shadowColor;
    public float shadowOffsetX;
    public float shadowOffsetY;
    public float shadowRadius;
    public ThumbnailTemplate template;

    public final void bind(ControlWithState controlWithState, int i) {
        int i2;
        Control control2 = controlWithState.control;
        Intrinsics.checkNotNull(control2);
        this.control = control2;
        ControlViewHolder cvh2 = getCvh();
        Control control3 = this.control;
        ThumbnailTemplate thumbnailTemplate = null;
        if (control3 == null) {
            control3 = null;
        }
        CharSequence statusText = control3.getStatusText();
        Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh2.setStatusText(statusText, false);
        Control control4 = this.control;
        if (control4 == null) {
            control4 = null;
        }
        ThumbnailTemplate controlTemplate = control4.getControlTemplate();
        Objects.requireNonNull(controlTemplate, "null cannot be cast to non-null type android.service.controls.templates.ThumbnailTemplate");
        this.template = controlTemplate;
        ControlViewHolder cvh3 = getCvh();
        Objects.requireNonNull(cvh3);
        Drawable background = cvh3.layout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        Drawable findDrawableByLayerId = ((LayerDrawable) background).findDrawableByLayerId(C1777R.C1779id.clip_layer);
        Objects.requireNonNull(findDrawableByLayerId, "null cannot be cast to non-null type android.graphics.drawable.ClipDrawable");
        ClipDrawable clipDrawable = (ClipDrawable) findDrawableByLayerId;
        ThumbnailTemplate thumbnailTemplate2 = this.template;
        if (thumbnailTemplate2 == null) {
            thumbnailTemplate2 = null;
        }
        if (thumbnailTemplate2.isActive()) {
            i2 = 10000;
        } else {
            i2 = 0;
        }
        clipDrawable.setLevel(i2);
        ThumbnailTemplate thumbnailTemplate3 = this.template;
        if (thumbnailTemplate3 == null) {
            thumbnailTemplate3 = null;
        }
        if (thumbnailTemplate3.isActive()) {
            ControlViewHolder cvh4 = getCvh();
            Objects.requireNonNull(cvh4);
            cvh4.title.setVisibility(4);
            ControlViewHolder cvh5 = getCvh();
            Objects.requireNonNull(cvh5);
            cvh5.subtitle.setVisibility(4);
            ControlViewHolder cvh6 = getCvh();
            Objects.requireNonNull(cvh6);
            cvh6.status.setShadowLayer(this.shadowOffsetX, this.shadowOffsetY, this.shadowRadius, this.shadowColor);
            ControlViewHolder cvh7 = getCvh();
            Objects.requireNonNull(cvh7);
            cvh7.bgExecutor.execute(new ThumbnailBehavior$bind$1(this, clipDrawable, i));
        } else {
            ControlViewHolder cvh8 = getCvh();
            Objects.requireNonNull(cvh8);
            cvh8.title.setVisibility(0);
            ControlViewHolder cvh9 = getCvh();
            Objects.requireNonNull(cvh9);
            cvh9.subtitle.setVisibility(0);
            ControlViewHolder cvh10 = getCvh();
            Objects.requireNonNull(cvh10);
            cvh10.status.setShadowLayer(0.0f, 0.0f, 0.0f, this.shadowColor);
        }
        ControlViewHolder cvh11 = getCvh();
        ThumbnailTemplate thumbnailTemplate4 = this.template;
        if (thumbnailTemplate4 != null) {
            thumbnailTemplate = thumbnailTemplate4;
        }
        cvh11.mo7819x3918d5b8(thumbnailTemplate.isActive(), i, true);
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
        TypedValue typedValue = new TypedValue();
        controlViewHolder.context.getResources().getValue(C1777R.dimen.controls_thumbnail_shadow_x, typedValue, true);
        this.shadowOffsetX = typedValue.getFloat();
        controlViewHolder.context.getResources().getValue(C1777R.dimen.controls_thumbnail_shadow_y, typedValue, true);
        this.shadowOffsetY = typedValue.getFloat();
        controlViewHolder.context.getResources().getValue(C1777R.dimen.controls_thumbnail_shadow_radius, typedValue, true);
        this.shadowRadius = typedValue.getFloat();
        this.shadowColor = controlViewHolder.context.getResources().getColor(C1777R.color.control_thumbnail_shadow_color);
        controlViewHolder.layout.setOnClickListener(new ThumbnailBehavior$initialize$1(controlViewHolder, this));
    }
}
