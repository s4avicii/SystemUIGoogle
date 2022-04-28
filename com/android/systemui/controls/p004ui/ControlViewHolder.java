package com.android.systemui.controls.p004ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.p004ui.RenderInfo;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder {
    public static final int[] ATTR_DISABLED = {-16842910};
    public static final int[] ATTR_ENABLED = {16842910};
    public static final Set<Integer> FORCE_PANEL_DEVICES = SetsKt__SetsKt.setOf(49, 50);
    public final GradientDrawable baseLayer;
    public Behavior behavior;
    public final DelayableExecutor bgExecutor;
    public final ImageView chevronIcon;
    public final ClipDrawable clipLayer;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public ControlWithState cws;
    public final ImageView icon;
    public boolean isLoading;
    public ControlAction lastAction;
    public Dialog lastChallengeDialog;
    public final ViewGroup layout;
    public CharSequence nextStatusText = "";
    public final Function0<Unit> onDialogCancel;
    public ValueAnimator stateAnimator;
    public final TextView status;
    public Animator statusAnimator;
    public final TextView subtitle;
    public final TextView title;
    public final float toggleBackgroundIntensity;
    public final DelayableExecutor uiExecutor;
    public final int uid;
    public boolean userInteractionInProgress;
    public Dialog visibleDialog;

    /* renamed from: com.android.systemui.controls.ui.ControlViewHolder$Companion */
    /* compiled from: ControlViewHolder.kt */
    public static final class Companion {
        public static ClassReference findBehaviorClass(int i, ControlTemplate controlTemplate, int i2) {
            Class cls;
            Class<ToggleRangeBehavior> cls2 = ToggleRangeBehavior.class;
            Class<TouchBehavior> cls3 = TouchBehavior.class;
            if (i != 1) {
                return Reflection.getOrCreateKotlinClass(StatusBehavior.class);
            }
            if (Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
                return Reflection.getOrCreateKotlinClass(cls3);
            }
            if (controlTemplate instanceof ThumbnailTemplate) {
                return Reflection.getOrCreateKotlinClass(ThumbnailBehavior.class);
            }
            if (i2 == 50) {
                return Reflection.getOrCreateKotlinClass(cls3);
            }
            if (controlTemplate instanceof ToggleTemplate) {
                return Reflection.getOrCreateKotlinClass(ToggleBehavior.class);
            }
            if (controlTemplate instanceof StatelessTemplate) {
                return Reflection.getOrCreateKotlinClass(cls3);
            }
            if (controlTemplate instanceof ToggleRangeTemplate) {
                return Reflection.getOrCreateKotlinClass(cls2);
            }
            if (controlTemplate instanceof RangeTemplate) {
                return Reflection.getOrCreateKotlinClass(cls2);
            }
            if (controlTemplate instanceof TemperatureControlTemplate) {
                cls = TemperatureControlBehavior.class;
            } else {
                cls = DefaultBehavior.class;
            }
            return Reflection.getOrCreateKotlinClass(cls);
        }
    }

    public final void action(ControlAction controlAction) {
        this.lastAction = controlAction;
        ControlsController controlsController2 = this.controlsController;
        ControlWithState cws2 = getCws();
        Objects.requireNonNull(cws2);
        ComponentName componentName = cws2.componentName;
        ControlWithState cws3 = getCws();
        Objects.requireNonNull(cws3);
        controlsController2.action(componentName, cws3.f48ci, controlAction);
    }

    public final void animateStatusChange(boolean z, Function0<Unit> function0) {
        Animator animator = this.statusAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            function0.invoke();
        } else if (this.isLoading) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.status, "alpha", new float[]{0.45f});
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900);
            ofFloat.start();
            this.statusAnimator = ofFloat;
        } else {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.status, "alpha", new float[]{0.0f});
            ofFloat2.setDuration(200);
            LinearInterpolator linearInterpolator = Interpolators.LINEAR;
            ofFloat2.setInterpolator(linearInterpolator);
            ofFloat2.addListener(new ControlViewHolder$animateStatusChange$fadeOut$1$1(function0));
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.status, "alpha", new float[]{1.0f});
            ofFloat3.setDuration(200);
            ofFloat3.setInterpolator(linearInterpolator);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(new Animator[]{ofFloat2, ofFloat3});
            animatorSet.addListener(new ControlViewHolder$animateStatusChange$2$1(this));
            animatorSet.start();
            this.statusAnimator = animatorSet;
        }
    }

    public final Behavior bindBehavior(Behavior behavior2, ClassReference classReference, int i) {
        if (behavior2 == null || !Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(behavior2.getClass()), classReference)) {
            behavior2 = (Behavior) classReference.getJClass().newInstance();
            behavior2.initialize(this);
            this.layout.setAccessibilityDelegate((View.AccessibilityDelegate) null);
        }
        behavior2.bind(getCws(), i);
        return behavior2;
    }

    public final void bindData(ControlWithState controlWithState, boolean z) {
        ControlTemplate controlTemplate;
        int i;
        if (!this.userInteractionInProgress) {
            this.cws = controlWithState;
            boolean z2 = false;
            if (getControlStatus() == 0 || getControlStatus() == 2) {
                TextView textView = this.title;
                ControlInfo controlInfo = controlWithState.f48ci;
                Objects.requireNonNull(controlInfo);
                textView.setText(controlInfo.controlTitle);
                TextView textView2 = this.subtitle;
                ControlInfo controlInfo2 = controlWithState.f48ci;
                Objects.requireNonNull(controlInfo2);
                textView2.setText(controlInfo2.controlSubtitle);
            } else {
                Control control = controlWithState.control;
                if (control != null) {
                    this.title.setText(control.getTitle());
                    this.subtitle.setText(control.getSubtitle());
                    ImageView imageView = this.chevronIcon;
                    if (usePanel()) {
                        i = 0;
                    } else {
                        i = 4;
                    }
                    imageView.setVisibility(i);
                }
            }
            if (controlWithState.control != null) {
                this.layout.setClickable(true);
                this.layout.setOnLongClickListener(new ControlViewHolder$bindData$2$1(this));
                ControlActionCoordinator controlActionCoordinator2 = this.controlActionCoordinator;
                ControlInfo controlInfo3 = controlWithState.f48ci;
                Objects.requireNonNull(controlInfo3);
                controlActionCoordinator2.runPendingAction(controlInfo3.controlId);
            }
            boolean z3 = this.isLoading;
            this.isLoading = false;
            Behavior behavior2 = this.behavior;
            int controlStatus = getControlStatus();
            ControlWithState cws2 = getCws();
            Objects.requireNonNull(cws2);
            Control control2 = cws2.control;
            if (control2 == null) {
                controlTemplate = null;
            } else {
                controlTemplate = control2.getControlTemplate();
            }
            if (controlTemplate == null) {
                controlTemplate = ControlTemplate.NO_TEMPLATE;
            }
            this.behavior = bindBehavior(behavior2, Companion.findBehaviorClass(controlStatus, controlTemplate, getDeviceType()), 0);
            updateContentDescription();
            if (z3 && !this.isLoading) {
                z2 = true;
            }
            if (z2) {
                this.controlsMetricsLogger.refreshEnd(this, z);
            }
        }
    }

    public final ControlWithState getCws() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState != null) {
            return controlWithState;
        }
        return null;
    }

    public final void setErrorStatus() {
        animateStatusChange(true, new ControlViewHolder$setErrorStatus$1(this, this.context.getResources().getString(C1777R.string.controls_error_failed)));
    }

    public final void setStatusText(CharSequence charSequence, boolean z) {
        if (z) {
            this.status.setAlpha(1.0f);
            this.status.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    public final void updateContentDescription() {
        ViewGroup viewGroup = this.layout;
        StringBuilder sb = new StringBuilder();
        sb.append(this.title.getText());
        sb.append(' ');
        sb.append(this.subtitle.getText());
        sb.append(' ');
        sb.append(this.status.getText());
        viewGroup.setContentDescription(sb.toString());
    }

    /* renamed from: updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo7828xcd94ff85(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        int[] iArr;
        Icon customIcon;
        this.status.setEnabled(z);
        this.icon.setEnabled(z);
        this.status.setText(charSequence);
        updateContentDescription();
        this.status.setTextColor(colorStateList);
        this.chevronIcon.setImageTintList(colorStateList);
        Unit unit = null;
        if (!(control == null || (customIcon = control.getCustomIcon()) == null)) {
            this.icon.setImageIcon(customIcon);
            this.icon.setImageTintList(customIcon.getTintList());
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (this.icon.getDrawable() == null || !(this.icon.getDrawable() instanceof StateListDrawable)) {
                    this.icon.setImageDrawable(drawable);
                }
                if (z) {
                    iArr = ATTR_ENABLED;
                } else {
                    iArr = ATTR_DISABLED;
                }
                this.icon.setImageState(iArr, true);
            } else {
                this.icon.setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                this.icon.setImageTintList(colorStateList);
            }
        }
    }

    public final boolean usePanel() {
        ControlTemplate controlTemplate;
        if (!FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType()))) {
            ControlWithState cws2 = getCws();
            Objects.requireNonNull(cws2);
            Control control = cws2.control;
            if (control == null) {
                controlTemplate = null;
            } else {
                controlTemplate = control.getControlTemplate();
            }
            if (controlTemplate == null) {
                controlTemplate = ControlTemplate.NO_TEMPLATE;
            }
            if (Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator2, ControlsMetricsLogger controlsMetricsLogger2, int i) {
        this.layout = viewGroup;
        this.controlsController = controlsController2;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator2;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        this.uid = i;
        this.toggleBackgroundIntensity = viewGroup.getContext().getResources().getFraction(C1777R.fraction.controls_toggle_bg_intensity, 1, 1);
        this.icon = (ImageView) viewGroup.requireViewById(C1777R.C1779id.icon);
        TextView textView = (TextView) viewGroup.requireViewById(C1777R.C1779id.status);
        this.status = textView;
        this.title = (TextView) viewGroup.requireViewById(C1777R.C1779id.title);
        this.subtitle = (TextView) viewGroup.requireViewById(C1777R.C1779id.subtitle);
        this.chevronIcon = (ImageView) viewGroup.requireViewById(C1777R.C1779id.chevron_icon);
        this.context = viewGroup.getContext();
        this.onDialogCancel = new ControlViewHolder$onDialogCancel$1(this);
        Drawable background = viewGroup.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        LayerDrawable layerDrawable = (LayerDrawable) background;
        layerDrawable.mutate();
        Drawable findDrawableByLayerId = layerDrawable.findDrawableByLayerId(C1777R.C1779id.clip_layer);
        Objects.requireNonNull(findDrawableByLayerId, "null cannot be cast to non-null type android.graphics.drawable.ClipDrawable");
        this.clipLayer = (ClipDrawable) findDrawableByLayerId;
        Drawable findDrawableByLayerId2 = layerDrawable.findDrawableByLayerId(C1777R.C1779id.background);
        Objects.requireNonNull(findDrawableByLayerId2, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
        this.baseLayer = (GradientDrawable) findDrawableByLayerId2;
        textView.setSelected(true);
    }

    /* renamed from: applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo7819x3918d5b8(boolean z, int i, boolean z2) {
        int i2;
        List list;
        int i3;
        int i4;
        ColorStateList color;
        int i5;
        ColorStateList customColor;
        if (getControlStatus() == 1 || getControlStatus() == 0) {
            i2 = getDeviceType();
        } else {
            i2 = -1000;
        }
        SparseArray<Drawable> sparseArray = RenderInfo.iconMap;
        Context context2 = this.context;
        ControlWithState cws2 = getCws();
        Objects.requireNonNull(cws2);
        RenderInfo lookup = RenderInfo.Companion.lookup(context2, cws2.componentName, i2, i);
        ColorStateList colorStateList = this.context.getResources().getColorStateList(lookup.foreground, this.context.getTheme());
        CharSequence charSequence = this.nextStatusText;
        ControlWithState cws3 = getCws();
        Objects.requireNonNull(cws3);
        Control control = cws3.control;
        if (Intrinsics.areEqual(charSequence, this.status.getText())) {
            z2 = false;
        }
        animateStatusChange(z2, new ControlViewHolder$applyRenderInfo$1(this, z, charSequence, lookup, colorStateList, control));
        int i6 = lookup.enabledBackground;
        int color2 = this.context.getResources().getColor(C1777R.color.control_default_background, this.context.getTheme());
        if (z) {
            ControlWithState cws4 = getCws();
            Objects.requireNonNull(cws4);
            Control control2 = cws4.control;
            Integer num = null;
            if (!(control2 == null || (customColor = control2.getCustomColor()) == null)) {
                num = Integer.valueOf(customColor.getColorForState(new int[]{16842910}, customColor.getDefaultColor()));
            }
            if (num == null) {
                i5 = this.context.getResources().getColor(i6, this.context.getTheme());
            } else {
                i5 = num.intValue();
            }
            list = SetsKt__SetsKt.listOf(Integer.valueOf(i5), 255);
        } else {
            list = SetsKt__SetsKt.listOf(Integer.valueOf(this.context.getResources().getColor(C1777R.color.control_default_background, this.context.getTheme())), 0);
        }
        int intValue = ((Number) list.get(0)).intValue();
        int intValue2 = ((Number) list.get(1)).intValue();
        if (this.behavior instanceof ToggleRangeBehavior) {
            color2 = ColorUtils.blendARGB(color2, intValue, this.toggleBackgroundIntensity);
        }
        int i7 = color2;
        Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            this.clipLayer.setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (z2) {
                if (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) {
                    i3 = intValue;
                } else {
                    i3 = color.getDefaultColor();
                }
                ColorStateList color3 = this.baseLayer.getColor();
                if (color3 == null) {
                    i4 = i7;
                } else {
                    i4 = color3.getDefaultColor();
                }
                float alpha = this.layout.getAlpha();
                ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.clipLayer.getAlpha(), intValue2});
                ofInt.addUpdateListener(new ControlViewHolder$startBackgroundAnimation$1$1(i3, intValue, i4, i7, alpha, this, drawable));
                ofInt.addListener(new ControlViewHolder$startBackgroundAnimation$1$2(this));
                ofInt.setDuration(700);
                ofInt.setInterpolator(Interpolators.CONTROL_STATE);
                ofInt.start();
                this.stateAnimator = ofInt;
                return;
            }
            drawable.setAlpha(intValue2);
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setColor(intValue);
            }
            this.baseLayer.setColor(i7);
            this.layout.setAlpha(1.0f);
        }
    }

    public final int getControlStatus() {
        ControlWithState cws2 = getCws();
        Objects.requireNonNull(cws2);
        Control control = cws2.control;
        if (control == null) {
            return 0;
        }
        return control.getStatus();
    }

    public final int getDeviceType() {
        Integer num;
        ControlWithState cws2 = getCws();
        Objects.requireNonNull(cws2);
        Control control = cws2.control;
        if (control == null) {
            num = null;
        } else {
            num = Integer.valueOf(control.getDeviceType());
        }
        if (num != null) {
            return num.intValue();
        }
        ControlWithState cws3 = getCws();
        Objects.requireNonNull(cws3);
        ControlInfo controlInfo = cws3.f48ci;
        Objects.requireNonNull(controlInfo);
        return controlInfo.deviceType;
    }
}
