package com.android.systemui.p006qs.tileimpl;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.p005qs.QSTileView;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileViewImpl */
/* compiled from: QSTileViewImpl.kt */
public class QSTileViewImpl extends QSTileView implements HeightOverrideable, LaunchableView {
    public final QSIconView _icon;
    public String accessibilityClass;
    public boolean blockVisibilityChanges;
    public ImageView chevronView;
    public final int colorActive;
    public Drawable colorBackgroundDrawable;
    public final int colorInactive;
    public final int colorLabelActive;
    public final int colorLabelInactive;
    public final int colorLabelUnavailable;
    public final int colorSecondaryLabelActive;
    public final int colorSecondaryLabelInactive;
    public final int colorSecondaryLabelUnavailable;
    public final int colorUnavailable;
    public ImageView customDrawableView;
    public int heightOverride = -1;
    public TextView label;
    public IgnorableChildLinearLayout labelContainer;
    public int lastState;
    public CharSequence lastStateDescription;
    public int lastVisibility;
    public final int[] locInScreen;
    public int paintColor;
    public RippleDrawable ripple;
    public TextView secondaryLabel;
    public boolean showRippleEffect;
    public ViewGroup sideView;
    public final ValueAnimator singleAnimator;
    public float squishinessFraction = 1.0f;
    public CharSequence stateDescriptionDeltas;
    public boolean tileState;

    /* renamed from: getSecondaryLabel  reason: collision with other method in class */
    public final TextView m217getSecondaryLabel() {
        TextView textView = this.secondaryLabel;
        if (textView != null) {
            return textView;
        }
        return null;
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void resetOverride() {
        setHeightOverride(-1);
        updateHeight();
    }

    public final int getBackgroundColorForState(int i) {
        if (i == 0) {
            return this.colorUnavailable;
        }
        if (i == 1) {
            return this.colorInactive;
        }
        if (i == 2) {
            return this.colorActive;
        }
        Log.e("QSTileViewImpl", Intrinsics.stringPlus("Invalid state ", Integer.valueOf(i)));
        return 0;
    }

    public final View getLabel() {
        TextView textView = this.label;
        if (textView == null) {
            return null;
        }
        return textView;
    }

    public final int getLabelColorForState(int i) {
        if (i == 0) {
            return this.colorLabelUnavailable;
        }
        if (i == 1) {
            return this.colorLabelInactive;
        }
        if (i == 2) {
            return this.colorLabelActive;
        }
        Log.e("QSTileViewImpl", Intrinsics.stringPlus("Invalid state ", Integer.valueOf(i)));
        return 0;
    }

    public final View getLabelContainer() {
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            return null;
        }
        return ignorableChildLinearLayout;
    }

    public final View getSecondaryLabel() {
        return getSecondaryLabel();
    }

    public final int getSecondaryLabelColorForState(int i) {
        if (i == 0) {
            return this.colorSecondaryLabelUnavailable;
        }
        if (i == 1) {
            return this.colorSecondaryLabelInactive;
        }
        if (i == 2) {
            return this.colorSecondaryLabelActive;
        }
        Log.e("QSTileViewImpl", Intrinsics.stringPlus("Invalid state ", Integer.valueOf(i)));
        return 0;
    }

    public final ViewGroup getSideView() {
        ViewGroup viewGroup = this.sideView;
        if (viewGroup != null) {
            return viewGroup;
        }
        return null;
    }

    public final void init(QSTile qSTile) {
        QSTileViewImpl$init$1 qSTileViewImpl$init$1 = new QSTileViewImpl$init$1(qSTile, this);
        QSTileViewImpl$init$2 qSTileViewImpl$init$2 = new QSTileViewImpl$init$2(qSTile, this);
        setOnClickListener(qSTileViewImpl$init$1);
        setOnLongClickListener(qSTileViewImpl$init$2);
    }

    public final void onMeasure(int i, int i2) {
        Trace.traceBegin(4096, "QSTileViewImpl#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    public final void onStateChanged(QSTile.State state) {
        post(new QSTileViewImpl$onStateChanged$1(this, state));
    }

    public final void setHeightOverride(int i) {
        if (this.heightOverride != i) {
            this.heightOverride = i;
            updateHeight();
        }
    }

    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.blockVisibilityChanges = z;
        if (z) {
            this.lastVisibility = getVisibility();
        } else {
            setVisibility(this.lastVisibility);
        }
    }

    public final void setSquishinessFraction(float f) {
        boolean z;
        if (this.squishinessFraction == f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            this.squishinessFraction = f;
            updateHeight();
        }
    }

    public final void setTransitionVisibility(int i) {
        if (this.blockVisibilityChanges) {
            this.lastVisibility = i;
        } else {
            super.setTransitionVisibility(i);
        }
    }

    public final void setVisibility(int i) {
        if (this.blockVisibilityChanges) {
            this.lastVisibility = i;
        } else {
            super.setVisibility(i);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("locInScreen=(" + this.locInScreen[0] + ", " + this.locInScreen[1] + ')');
        sb.append(Intrinsics.stringPlus(", iconView=", this._icon));
        sb.append(Intrinsics.stringPlus(", tileState=", Boolean.valueOf(this.tileState)));
        sb.append("]");
        return sb.toString();
    }

    public final View updateAccessibilityOrder(View view) {
        int i;
        if (view == null) {
            i = 0;
        } else {
            i = view.getId();
        }
        setAccessibilityTraversalAfter(i);
        return this;
    }

    public final void updateHeight() {
        int i = this.heightOverride;
        if (i == -1) {
            i = getMeasuredHeight();
        }
        setBottom(getTop() + ((int) (((float) i) * ((this.squishinessFraction * 0.9f) + 0.1f))));
        setScrollY((i - getHeight()) / 2);
    }

    public QSTileViewImpl(Context context, QSIconView qSIconView, boolean z) {
        super(context);
        this._icon = qSIconView;
        this.colorActive = Utils.getColorAttrDefaultColor(context, 17956900);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, C1777R.attr.offStateColor);
        this.colorInactive = colorAttrDefaultColor;
        this.colorUnavailable = Utils.applyAlpha(colorAttrDefaultColor);
        this.colorLabelActive = Utils.getColorAttrDefaultColor(context, 17957103);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(context, 16842806);
        this.colorLabelInactive = colorAttrDefaultColor2;
        this.colorLabelUnavailable = Utils.applyAlpha(colorAttrDefaultColor2);
        this.colorSecondaryLabelActive = Utils.getColorAttrDefaultColor(context, 16842810);
        int colorAttrDefaultColor3 = Utils.getColorAttrDefaultColor(context, 16842808);
        this.colorSecondaryLabelInactive = colorAttrDefaultColor3;
        this.colorSecondaryLabelUnavailable = Utils.applyAlpha(colorAttrDefaultColor3);
        this.showRippleEffect = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350);
        valueAnimator.addUpdateListener(new QSTileViewImpl$singleAnimator$1$1(this));
        this.singleAnimator = valueAnimator;
        this.lastState = -1;
        this.locInScreen = new int[2];
        setId(View.generateViewId());
        setOrientation(0);
        setGravity(8388627);
        setImportantForAccessibility(1);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        Drawable drawable = this.mContext.getDrawable(C1777R.C1778drawable.qs_tile_background);
        Objects.requireNonNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.RippleDrawable");
        RippleDrawable rippleDrawable = (RippleDrawable) drawable;
        this.ripple = rippleDrawable;
        this.colorBackgroundDrawable = rippleDrawable.findDrawableByLayerId(C1777R.C1779id.background);
        RippleDrawable rippleDrawable2 = this.ripple;
        ImageView imageView = null;
        setBackground(rippleDrawable2 == null ? null : rippleDrawable2);
        int backgroundColorForState = getBackgroundColorForState(2);
        Drawable drawable2 = this.colorBackgroundDrawable;
        (drawable2 == null ? null : drawable2).mutate().setTint(backgroundColorForState);
        this.paintColor = backgroundColorForState;
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(C1777R.dimen.qs_tile_start_padding), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.qs_icon_size);
        addView(qSIconView, new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize2));
        View inflate = LayoutInflater.from(getContext()).inflate(C1777R.layout.qs_tile_label, this, false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.systemui.qs.tileimpl.IgnorableChildLinearLayout");
        IgnorableChildLinearLayout ignorableChildLinearLayout = (IgnorableChildLinearLayout) inflate;
        this.labelContainer = ignorableChildLinearLayout;
        this.label = (TextView) ignorableChildLinearLayout.requireViewById(C1777R.C1779id.tile_label);
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = this.labelContainer;
        this.secondaryLabel = (TextView) (ignorableChildLinearLayout2 == null ? null : ignorableChildLinearLayout2).requireViewById(C1777R.C1779id.app_label);
        if (z) {
            IgnorableChildLinearLayout ignorableChildLinearLayout3 = this.labelContainer;
            ignorableChildLinearLayout3 = ignorableChildLinearLayout3 == null ? null : ignorableChildLinearLayout3;
            Objects.requireNonNull(ignorableChildLinearLayout3);
            ignorableChildLinearLayout3.ignoreLastView = true;
            IgnorableChildLinearLayout ignorableChildLinearLayout4 = this.labelContainer;
            ignorableChildLinearLayout4 = ignorableChildLinearLayout4 == null ? null : ignorableChildLinearLayout4;
            Objects.requireNonNull(ignorableChildLinearLayout4);
            ignorableChildLinearLayout4.forceUnspecifiedMeasure = true;
            getSecondaryLabel().setAlpha(0.0f);
        }
        int labelColorForState = getLabelColorForState(2);
        TextView textView = this.label;
        (textView == null ? null : textView).setTextColor(labelColorForState);
        getSecondaryLabel().setTextColor(getSecondaryLabelColorForState(2));
        IgnorableChildLinearLayout ignorableChildLinearLayout5 = this.labelContainer;
        addView(ignorableChildLinearLayout5 == null ? null : ignorableChildLinearLayout5);
        View inflate2 = LayoutInflater.from(getContext()).inflate(C1777R.layout.qs_tile_side_icon, this, false);
        Objects.requireNonNull(inflate2, "null cannot be cast to non-null type android.view.ViewGroup");
        this.sideView = (ViewGroup) inflate2;
        this.customDrawableView = (ImageView) getSideView().requireViewById(C1777R.C1779id.customDrawable);
        this.chevronView = (ImageView) getSideView().requireViewById(C1777R.C1779id.chevron);
        int secondaryLabelColorForState = getSecondaryLabelColorForState(2);
        ImageView imageView2 = this.chevronView;
        (imageView2 != null ? imageView2 : imageView).setImageTintList(ColorStateList.valueOf(secondaryLabelColorForState));
        addView(getSideView());
    }

    public boolean animationsEnabled() {
        boolean z;
        if (!isShown()) {
            return false;
        }
        if (getAlpha() == 1.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        getLocationOnScreen(this.locInScreen);
        if (this.locInScreen[1] >= (-getHeight())) {
            return true;
        }
        return false;
    }

    public final int getDetailY() {
        return (getHeight() / 2) + getTop();
    }

    public final View getSecondaryIcon() {
        return getSideView();
    }

    public void handleStateChanged(QSTile.State state) {
        boolean z;
        String str;
        String str2;
        int i;
        int i2;
        boolean z2;
        boolean animationsEnabled = animationsEnabled();
        this.showRippleEffect = state.showRippleEffect;
        if (state.state != 0) {
            z = true;
        } else {
            z = false;
        }
        setClickable(z);
        setLongClickable(state.handlesLongClick);
        this._icon.setIcon(state, animationsEnabled);
        setContentDescription(state.contentDescription);
        StringBuilder sb = new StringBuilder();
        if (state.disabledByPolicy) {
            str = getContext().getString(C1777R.string.tile_disabled);
        } else if (state.state == 0 || (state instanceof QSTile.BooleanState)) {
            SubtitleArrayMapping subtitleArrayMapping = SubtitleArrayMapping.INSTANCE;
            String str3 = state.spec;
            Objects.requireNonNull(subtitleArrayMapping);
            str = getResources().getStringArray(SubtitleArrayMapping.subtitleIdsMap.getOrDefault(str3, Integer.valueOf(C1777R.array.tile_states_default)).intValue())[state.state];
        } else {
            str = "";
        }
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            if (TextUtils.isEmpty(state.secondaryLabel)) {
                state.secondaryLabel = str;
            }
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            sb.append(", ");
            sb.append(state.stateDescription);
            int i3 = this.lastState;
            if (i3 != -1 && state.state == i3 && !Intrinsics.areEqual(state.stateDescription, this.lastStateDescription)) {
                this.stateDescriptionDeltas = state.stateDescription;
            }
        }
        setStateDescription(sb.toString());
        this.lastStateDescription = state.stateDescription;
        TextView textView = null;
        if (state.state == 0) {
            str2 = null;
        } else {
            str2 = state.expandedAccessibilityClassName;
        }
        this.accessibilityClass = str2;
        boolean z3 = state instanceof QSTile.BooleanState;
        if (z3 && this.tileState != (z2 = ((QSTile.BooleanState) state).value)) {
            this.tileState = z2;
        }
        TextView textView2 = this.label;
        if (textView2 == null) {
            textView2 = null;
        }
        if (!Objects.equals(textView2.getText(), state.label)) {
            TextView textView3 = this.label;
            if (textView3 == null) {
                textView3 = null;
            }
            textView3.setText(state.label);
        }
        if (!Objects.equals(getSecondaryLabel().getText(), state.secondaryLabel)) {
            getSecondaryLabel().setText(state.secondaryLabel);
            TextView secondaryLabel2 = getSecondaryLabel();
            if (TextUtils.isEmpty(state.secondaryLabel)) {
                i2 = 8;
            } else {
                i2 = 0;
            }
            secondaryLabel2.setVisibility(i2);
        }
        if (state.state != this.lastState) {
            this.singleAnimator.cancel();
            if (animationsEnabled) {
                ValueAnimator valueAnimator = this.singleAnimator;
                PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[4];
                propertyValuesHolderArr[0] = QSTileViewImplKt.access$colorValuesHolder("background", this.paintColor, getBackgroundColorForState(state.state));
                int[] iArr = new int[2];
                TextView textView4 = this.label;
                if (textView4 == null) {
                    textView4 = null;
                }
                iArr[0] = textView4.getCurrentTextColor();
                iArr[1] = getLabelColorForState(state.state);
                propertyValuesHolderArr[1] = QSTileViewImplKt.access$colorValuesHolder("label", iArr);
                propertyValuesHolderArr[2] = QSTileViewImplKt.access$colorValuesHolder("secondaryLabel", getSecondaryLabel().getCurrentTextColor(), getSecondaryLabelColorForState(state.state));
                int[] iArr2 = new int[2];
                ImageView imageView = this.chevronView;
                if (imageView == null) {
                    imageView = null;
                }
                ColorStateList imageTintList = imageView.getImageTintList();
                if (imageTintList == null) {
                    i = 0;
                } else {
                    i = imageTintList.getDefaultColor();
                }
                iArr2[0] = i;
                iArr2[1] = getSecondaryLabelColorForState(state.state);
                propertyValuesHolderArr[3] = QSTileViewImplKt.access$colorValuesHolder("chevron", iArr2);
                valueAnimator.setValues(propertyValuesHolderArr);
                this.singleAnimator.start();
            } else {
                int backgroundColorForState = getBackgroundColorForState(state.state);
                int labelColorForState = getLabelColorForState(state.state);
                int secondaryLabelColorForState = getSecondaryLabelColorForState(state.state);
                int secondaryLabelColorForState2 = getSecondaryLabelColorForState(state.state);
                Drawable drawable = this.colorBackgroundDrawable;
                if (drawable == null) {
                    drawable = null;
                }
                drawable.mutate().setTint(backgroundColorForState);
                this.paintColor = backgroundColorForState;
                TextView textView5 = this.label;
                if (textView5 == null) {
                    textView5 = null;
                }
                textView5.setTextColor(labelColorForState);
                getSecondaryLabel().setTextColor(secondaryLabelColorForState);
                ImageView imageView2 = this.chevronView;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                imageView2.setImageTintList(ColorStateList.valueOf(secondaryLabelColorForState2));
            }
        }
        Drawable drawable2 = state.sideViewCustomDrawable;
        if (drawable2 != null) {
            ImageView imageView3 = this.customDrawableView;
            if (imageView3 == null) {
                imageView3 = null;
            }
            imageView3.setImageDrawable(drawable2);
            ImageView imageView4 = this.customDrawableView;
            if (imageView4 == null) {
                imageView4 = null;
            }
            imageView4.setVisibility(0);
            ImageView imageView5 = this.chevronView;
            if (imageView5 == null) {
                imageView5 = null;
            }
            imageView5.setVisibility(8);
        } else if (!z3 || ((QSTile.BooleanState) state).forceExpandIcon) {
            ImageView imageView6 = this.customDrawableView;
            if (imageView6 == null) {
                imageView6 = null;
            }
            imageView6.setImageDrawable((Drawable) null);
            ImageView imageView7 = this.customDrawableView;
            if (imageView7 == null) {
                imageView7 = null;
            }
            imageView7.setVisibility(8);
            ImageView imageView8 = this.chevronView;
            if (imageView8 == null) {
                imageView8 = null;
            }
            imageView8.setVisibility(0);
        } else {
            ImageView imageView9 = this.customDrawableView;
            if (imageView9 == null) {
                imageView9 = null;
            }
            imageView9.setImageDrawable((Drawable) null);
            ImageView imageView10 = this.customDrawableView;
            if (imageView10 == null) {
                imageView10 = null;
            }
            imageView10.setVisibility(8);
            ImageView imageView11 = this.chevronView;
            if (imageView11 == null) {
                imageView11 = null;
            }
            imageView11.setVisibility(8);
        }
        TextView textView6 = this.label;
        if (textView6 != null) {
            textView = textView6;
        }
        textView.setEnabled(!state.disabledByPolicy);
        this.lastState = state.state;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TextView textView = this.label;
        ImageView imageView = null;
        if (textView == null) {
            textView = null;
        }
        R$raw.updateFontSize(textView, C1777R.dimen.qs_tile_text_size);
        R$raw.updateFontSize(getSecondaryLabel(), C1777R.dimen.qs_tile_text_size);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_icon_size);
        ViewGroup.LayoutParams layoutParams = this._icon.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(C1777R.dimen.qs_tile_start_padding), dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(C1777R.dimen.qs_label_container_margin);
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout = null;
        }
        ViewGroup.LayoutParams layoutParams2 = ignorableChildLinearLayout.getLayoutParams();
        Objects.requireNonNull(layoutParams2, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ((ViewGroup.MarginLayoutParams) layoutParams2).setMarginStart(dimensionPixelSize3);
        ViewGroup.LayoutParams layoutParams3 = getSideView().getLayoutParams();
        Objects.requireNonNull(layoutParams3, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ((ViewGroup.MarginLayoutParams) layoutParams3).setMarginStart(dimensionPixelSize3);
        ImageView imageView2 = this.chevronView;
        if (imageView2 == null) {
            imageView2 = null;
        }
        ViewGroup.LayoutParams layoutParams4 = imageView2.getLayoutParams();
        Objects.requireNonNull(layoutParams4, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams4;
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(C1777R.dimen.qs_drawable_end_margin);
        ImageView imageView3 = this.customDrawableView;
        if (imageView3 != null) {
            imageView = imageView3;
        }
        ViewGroup.LayoutParams layoutParams5 = imageView.getLayoutParams();
        Objects.requireNonNull(layoutParams5, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams5;
        marginLayoutParams2.height = dimensionPixelSize;
        marginLayoutParams2.setMarginEnd(dimensionPixelSize4);
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityEvent.setClassName(this.accessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() == 64 && this.stateDescriptionDeltas != null) {
            accessibilityEvent.getText().add(this.stateDescriptionDeltas);
            this.stateDescriptionDeltas = null;
        }
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityNodeInfo.setClassName(this.accessibilityClass);
            if (Intrinsics.areEqual(Switch.class.getName(), this.accessibilityClass)) {
                Resources resources = getResources();
                if (this.tileState) {
                    i = C1777R.string.switch_bar_on;
                } else {
                    i = C1777R.string.switch_bar_off;
                }
                accessibilityNodeInfo.setText(resources.getString(i));
                accessibilityNodeInfo.setChecked(this.tileState);
                accessibilityNodeInfo.setCheckable(true);
                if (isLongClickable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(C1777R.string.accessibility_long_click_tile)));
                }
            }
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateHeight();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        if (r3 == null) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setClickable(boolean r3) {
        /*
            r2 = this;
            super.setClickable(r3)
            r0 = 0
            if (r3 == 0) goto L_0x0019
            boolean r3 = r2.showRippleEffect
            if (r3 == 0) goto L_0x0019
            android.graphics.drawable.RippleDrawable r3 = r2.ripple
            if (r3 != 0) goto L_0x000f
            r3 = r0
        L_0x000f:
            android.graphics.drawable.Drawable r1 = r2.colorBackgroundDrawable
            if (r1 != 0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r0 = r1
        L_0x0015:
            r0.setCallback(r3)
            goto L_0x001e
        L_0x0019:
            android.graphics.drawable.Drawable r3 = r2.colorBackgroundDrawable
            if (r3 != 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r0 = r3
        L_0x001f:
            r2.setBackground(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tileimpl.QSTileViewImpl.setClickable(boolean):void");
    }

    public final QSIconView getIcon() {
        return this._icon;
    }

    public final View getIconWithBackground() {
        return this._icon;
    }
}
