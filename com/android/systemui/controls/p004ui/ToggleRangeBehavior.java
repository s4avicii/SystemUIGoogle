package com.android.systemui.controls.p004ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.util.Log;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ToggleRangeBehavior */
/* compiled from: ToggleRangeBehavior.kt */
public final class ToggleRangeBehavior implements Behavior {
    public Drawable clipLayer;
    public int colorOffset;
    public Context context;
    public Control control;
    public String currentRangeValue = "";
    public CharSequence currentStatusText = "";
    public ControlViewHolder cvh;
    public boolean isChecked;
    public boolean isToggleable;
    public ValueAnimator rangeAnimator;
    public RangeTemplate rangeTemplate;
    public String templateId;

    /* renamed from: com.android.systemui.controls.ui.ToggleRangeBehavior$ToggleRangeGestureListener */
    /* compiled from: ToggleRangeBehavior.kt */
    public final class ToggleRangeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean isDragging;

        /* renamed from: v */
        public final View f49v;

        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public ToggleRangeGestureListener(ViewGroup viewGroup) {
            this.f49v = viewGroup;
        }

        public final void onLongPress(MotionEvent motionEvent) {
            if (!this.isDragging) {
                ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
                Objects.requireNonNull(cvh);
                cvh.controlActionCoordinator.longPress(ToggleRangeBehavior.this.getCvh());
            }
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isDragging) {
                this.f49v.getParent().requestDisallowInterceptTouchEvent(true);
                ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                Objects.requireNonNull(toggleRangeBehavior);
                ControlViewHolder cvh = toggleRangeBehavior.getCvh();
                Objects.requireNonNull(cvh);
                cvh.userInteractionInProgress = true;
                ControlViewHolder cvh2 = toggleRangeBehavior.getCvh();
                Context context = toggleRangeBehavior.context;
                if (context == null) {
                    context = null;
                }
                Objects.requireNonNull(cvh2);
                cvh2.status.setTextSize(0, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.control_status_expanded));
                this.isDragging = true;
            }
            ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
            toggleRangeBehavior2.updateRange(toggleRangeBehavior2.getClipLayer().getLevel() + ((int) (((float) 10000) * ((-f) / ((float) this.f49v.getWidth())))), true, true);
            return true;
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            Objects.requireNonNull(toggleRangeBehavior);
            if (!toggleRangeBehavior.isToggleable) {
                return false;
            }
            ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
            Objects.requireNonNull(cvh);
            ControlActionCoordinator controlActionCoordinator = cvh.controlActionCoordinator;
            ControlViewHolder cvh2 = ToggleRangeBehavior.this.getCvh();
            ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
            Objects.requireNonNull(toggleRangeBehavior2);
            String str = toggleRangeBehavior2.templateId;
            if (str == null) {
                str = null;
            }
            ToggleRangeBehavior toggleRangeBehavior3 = ToggleRangeBehavior.this;
            Objects.requireNonNull(toggleRangeBehavior3);
            controlActionCoordinator.toggle(cvh2, str, toggleRangeBehavior3.isChecked);
            return true;
        }
    }

    public final String format(String str, String str2, float f) {
        try {
            return String.format(str, Arrays.copyOf(new Object[]{Float.valueOf(findNearestStep(f))}, 1));
        } catch (IllegalFormatException e) {
            Log.w("ControlsUiController", "Illegal format in range template", e);
            if (Intrinsics.areEqual(str2, "")) {
                return "";
            }
            return format(str2, "", f);
        }
    }

    public final void bind(ControlWithState controlWithState, int i) {
        Control control2 = controlWithState.control;
        Intrinsics.checkNotNull(control2);
        this.control = control2;
        this.colorOffset = i;
        this.currentStatusText = control2.getStatusText();
        ControlViewHolder cvh2 = getCvh();
        Objects.requireNonNull(cvh2);
        Control control3 = null;
        cvh2.layout.setOnLongClickListener((View.OnLongClickListener) null);
        ControlViewHolder cvh3 = getCvh();
        Objects.requireNonNull(cvh3);
        Drawable background = cvh3.layout.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        this.clipLayer = ((LayerDrawable) background).findDrawableByLayerId(C1777R.C1779id.clip_layer);
        Control control4 = this.control;
        if (control4 != null) {
            control3 = control4;
        }
        ControlTemplate controlTemplate = control3.getControlTemplate();
        if (setupTemplate(controlTemplate)) {
            this.templateId = controlTemplate.getTemplateId();
            updateRange((int) MathUtils.constrainedMap(0.0f, 10000.0f, getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), getRangeTemplate().getCurrentValue()), this.isChecked, false);
            getCvh().mo7819x3918d5b8(this.isChecked, i, true);
            ControlViewHolder cvh4 = getCvh();
            Objects.requireNonNull(cvh4);
            cvh4.layout.setAccessibilityDelegate(new ToggleRangeBehavior$bind$1(this));
        }
    }

    public final Drawable getClipLayer() {
        Drawable drawable = this.clipLayer;
        if (drawable != null) {
            return drawable;
        }
        return null;
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public final RangeTemplate getRangeTemplate() {
        RangeTemplate rangeTemplate2 = this.rangeTemplate;
        if (rangeTemplate2 != null) {
            return rangeTemplate2;
        }
        return null;
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        this.context = controlViewHolder.context;
        ToggleRangeGestureListener toggleRangeGestureListener = new ToggleRangeGestureListener(controlViewHolder.layout);
        Context context2 = this.context;
        if (context2 == null) {
            context2 = null;
        }
        controlViewHolder.layout.setOnTouchListener(new ToggleRangeBehavior$initialize$1(new GestureDetector(context2, toggleRangeGestureListener), toggleRangeGestureListener, this));
    }

    public final boolean setupTemplate(ControlTemplate controlTemplate) {
        boolean z = false;
        if (controlTemplate instanceof ToggleRangeTemplate) {
            ToggleRangeTemplate toggleRangeTemplate = (ToggleRangeTemplate) controlTemplate;
            this.rangeTemplate = toggleRangeTemplate.getRange();
            this.isToggleable = true;
            this.isChecked = toggleRangeTemplate.isChecked();
            return true;
        } else if (controlTemplate instanceof RangeTemplate) {
            this.rangeTemplate = (RangeTemplate) controlTemplate;
            if (getRangeTemplate().getCurrentValue() == getRangeTemplate().getMinValue()) {
                z = true;
            }
            this.isChecked = !z;
            return true;
        } else if (controlTemplate instanceof TemperatureControlTemplate) {
            return setupTemplate(((TemperatureControlTemplate) controlTemplate).getTemplate());
        } else {
            Log.e("ControlsUiController", Intrinsics.stringPlus("Unsupported template type: ", controlTemplate));
            return false;
        }
    }

    public final void updateRange(int i, boolean z, boolean z2) {
        boolean z3;
        int max = Math.max(0, Math.min(10000, i));
        if (getClipLayer().getLevel() == 0 && max > 0) {
            getCvh().mo7819x3918d5b8(z, this.colorOffset, false);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (z2) {
            if (max == 0 || max == 10000) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (getClipLayer().getLevel() != max) {
                ControlViewHolder cvh2 = getCvh();
                Objects.requireNonNull(cvh2);
                cvh2.controlActionCoordinator.drag(z3);
                getClipLayer().setLevel(max);
            }
        } else if (max != getClipLayer().getLevel()) {
            ControlViewHolder cvh3 = getCvh();
            Objects.requireNonNull(cvh3);
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{cvh3.clipLayer.getLevel(), max});
            ofInt.addUpdateListener(new ToggleRangeBehavior$updateRange$1$1(this));
            ofInt.addListener(new ToggleRangeBehavior$updateRange$1$2(this));
            ofInt.setDuration(700);
            ofInt.setInterpolator(Interpolators.CONTROL_STATE);
            ofInt.start();
            this.rangeAnimator = ofInt;
        }
        if (z) {
            this.currentRangeValue = format(getRangeTemplate().getFormatString().toString(), "%.1f", levelToRangeValue(max));
            if (z2) {
                getCvh().setStatusText(this.currentRangeValue, true);
                return;
            }
            Set<Integer> set = ControlViewHolder.FORCE_PANEL_DEVICES;
            getCvh().setStatusText(this.currentStatusText + ' ' + this.currentRangeValue, false);
            return;
        }
        ControlViewHolder cvh4 = getCvh();
        CharSequence charSequence = this.currentStatusText;
        Set<Integer> set2 = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh4.setStatusText(charSequence, false);
    }

    public final void endUpdateRange() {
        ControlViewHolder cvh2 = getCvh();
        Context context2 = this.context;
        if (context2 == null) {
            context2 = null;
        }
        Objects.requireNonNull(cvh2);
        cvh2.status.setTextSize(0, (float) context2.getResources().getDimensionPixelSize(C1777R.dimen.control_status_normal));
        ControlViewHolder cvh3 = getCvh();
        cvh3.setStatusText(this.currentStatusText + ' ' + this.currentRangeValue, true);
        ControlViewHolder cvh4 = getCvh();
        Objects.requireNonNull(cvh4);
        cvh4.controlActionCoordinator.setValue(getCvh(), getRangeTemplate().getTemplateId(), findNearestStep(levelToRangeValue(getClipLayer().getLevel())));
        ControlViewHolder cvh5 = getCvh();
        Objects.requireNonNull(cvh5);
        cvh5.userInteractionInProgress = false;
    }

    public final float findNearestStep(float f) {
        float minValue = getRangeTemplate().getMinValue();
        float f2 = Float.MAX_VALUE;
        while (minValue <= getRangeTemplate().getMaxValue()) {
            float abs = Math.abs(f - minValue);
            if (abs >= f2) {
                return minValue - getRangeTemplate().getStepValue();
            }
            minValue += getRangeTemplate().getStepValue();
            f2 = abs;
        }
        return getRangeTemplate().getMaxValue();
    }

    public final float levelToRangeValue(int i) {
        return MathUtils.constrainedMap(getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), 0.0f, 10000.0f, (float) i);
    }
}
