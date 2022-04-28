package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayApi18;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlinx.atomicfu.AtomicFU;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
    public final AccessibilityHelper accessibilityHelper;
    public final AccessibilityManager accessibilityManager;
    public int activeThumbIdx;
    public final Paint activeTicksPaint;
    public final Paint activeTrackPaint;
    public final ArrayList changeListeners;
    public int defaultThumbRadius;
    public boolean dirtyConfig;
    public int focusedThumbIdx;
    public boolean forceDrawCompatHalo;
    public ColorStateList haloColor;
    public final Paint haloPaint;
    public int haloRadius;
    public final Paint inactiveTicksPaint;
    public final Paint inactiveTrackPaint;
    public boolean isLongPress;
    public int labelBehavior;
    public final C20801 labelMaker;
    public int labelPadding;
    public final ArrayList labels;
    public boolean labelsAreAnimatedIn;
    public ValueAnimator labelsInAnimator;
    public ValueAnimator labelsOutAnimator;
    public MotionEvent lastEvent;
    public int minTrackSidePadding;
    public final int scaledTouchSlop;
    public int separationUnit;
    public float stepSize;
    public final MaterialShapeDrawable thumbDrawable;
    public boolean thumbIsPressed;
    public final Paint thumbPaint;
    public int thumbRadius;
    public ColorStateList tickColorActive;
    public ColorStateList tickColorInactive;
    public boolean tickVisible;
    public float[] ticksCoordinates;
    public float touchDownX;
    public final ArrayList touchListeners;
    public float touchPosition;
    public ColorStateList trackColorActive;
    public ColorStateList trackColorInactive;
    public int trackHeight;
    public int trackSidePadding;
    public int trackTop;
    public int trackWidth;
    public float valueFrom;
    public float valueTo;
    public ArrayList<Float> values;
    public int widgetHeight;

    public class AccessibilityEventSender implements Runnable {
        public int virtualViewId = -1;

        public AccessibilityEventSender() {
        }

        public final void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    public static class AccessibilityHelper extends ExploreByTouchHelper {
        public final BaseSlider<?, ?, ?> slider;
        public Rect virtualViewBounds = new Rect();

        public final int getVirtualViewAt(float f, float f2) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                this.slider.updateBoundsForVirturalViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) f, (int) f2)) {
                    return i;
                }
            }
            return -1;
        }

        public final void getVisibleVirtualViews(ArrayList arrayList) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                arrayList.add(Integer.valueOf(i));
            }
        }

        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (!this.slider.isEnabled()) {
                return false;
            }
            if (i2 == 4096 || i2 == 8192) {
                BaseSlider<?, ?, ?> baseSlider = this.slider;
                int i3 = BaseSlider.$r8$clinit;
                Objects.requireNonNull(baseSlider);
                float f = baseSlider.stepSize;
                if (f == 0.0f) {
                    f = 1.0f;
                }
                float f2 = (baseSlider.valueTo - baseSlider.valueFrom) / f;
                float f3 = (float) 20;
                if (f2 > f3) {
                    f *= (float) Math.round(f2 / f3);
                }
                if (i2 == 8192) {
                    f = -f;
                }
                if (this.slider.isRtl()) {
                    f = -f;
                }
                float floatValue = ((Float) this.slider.getValues().get(i)).floatValue() + f;
                BaseSlider<?, ?, ?> baseSlider2 = this.slider;
                Objects.requireNonNull(baseSlider2);
                float f4 = baseSlider2.valueFrom;
                BaseSlider<?, ?, ?> baseSlider3 = this.slider;
                Objects.requireNonNull(baseSlider3);
                if (!this.slider.snapThumbToValue(i, AtomicFU.clamp(floatValue, f4, baseSlider3.valueTo))) {
                    return false;
                }
                this.slider.updateHaloHotspot();
                this.slider.postInvalidate();
                invalidateVirtualView(i);
                return true;
            }
            if (i2 == 16908349 && bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                float f5 = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                BaseSlider<?, ?, ?> baseSlider4 = this.slider;
                int i4 = BaseSlider.$r8$clinit;
                if (baseSlider4.snapThumbToValue(i, f5)) {
                    this.slider.updateHaloHotspot();
                    this.slider.postInvalidate();
                    invalidateVirtualView(i);
                    return true;
                }
            }
            return false;
        }

        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            String str;
            String str2;
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            ArrayList values = this.slider.getValues();
            float floatValue = ((Float) values.get(i)).floatValue();
            BaseSlider<?, ?, ?> baseSlider = this.slider;
            Objects.requireNonNull(baseSlider);
            float f = baseSlider.valueFrom;
            BaseSlider<?, ?, ?> baseSlider2 = this.slider;
            Objects.requireNonNull(baseSlider2);
            float f2 = baseSlider2.valueTo;
            if (this.slider.isEnabled()) {
                if (floatValue > f) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (floatValue < f2) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.mInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(1, f, f2, floatValue));
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder sb = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                sb.append(this.slider.getContentDescription());
                sb.append(",");
            }
            if (values.size() > 1) {
                if (i == this.slider.getValues().size() - 1) {
                    str = this.slider.getContext().getString(C1777R.string.material_slider_range_end);
                } else if (i == 0) {
                    str = this.slider.getContext().getString(C1777R.string.material_slider_range_start);
                } else {
                    str = "";
                }
                sb.append(str);
                Objects.requireNonNull(this.slider);
                if (((float) ((int) floatValue)) == floatValue) {
                    str2 = "%.0f";
                } else {
                    str2 = "%.2f";
                }
                sb.append(String.format(str2, new Object[]{Float.valueOf(floatValue)}));
            }
            accessibilityNodeInfoCompat.setContentDescription(sb.toString());
            this.slider.updateBoundsForVirturalViewId(i, this.virtualViewBounds);
            accessibilityNodeInfoCompat.setBoundsInParent(this.virtualViewBounds);
        }

        public AccessibilityHelper(BaseSlider<?, ?, ?> baseSlider) {
            super(baseSlider);
            this.slider = baseSlider;
        }
    }

    public static class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SliderState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SliderState[i];
            }
        };
        public boolean hasFocus;
        public float stepSize;
        public float valueFrom;
        public float valueTo;
        public ArrayList<Float> values;

        public SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        public SliderState(Parcel parcel) {
            super(parcel);
            this.valueFrom = parcel.readFloat();
            this.valueTo = parcel.readFloat();
            ArrayList<Float> arrayList = new ArrayList<>();
            this.values = arrayList;
            parcel.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = parcel.readFloat();
            this.hasFocus = parcel.createBooleanArray()[0];
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.valueFrom);
            parcel.writeFloat(this.valueTo);
            parcel.writeList(this.values);
            parcel.writeFloat(this.stepSize);
            parcel.writeBooleanArray(new boolean[]{this.hasFocus});
        }
    }

    public BaseSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final ValueAnimator createLabelAnimator(boolean z) {
        float f;
        ValueAnimator valueAnimator;
        long j;
        TimeInterpolator timeInterpolator;
        float f2 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (z) {
            valueAnimator = this.labelsOutAnimator;
        } else {
            valueAnimator = this.labelsInAnimator;
        }
        if (valueAnimator != null && valueAnimator.isRunning()) {
            f = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            valueAnimator.cancel();
        }
        if (z) {
            f2 = 1.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
        if (z) {
            j = 83;
        } else {
            j = 117;
        }
        ofFloat.setDuration(j);
        if (z) {
            timeInterpolator = AnimationUtils.DECELERATE_INTERPOLATOR;
        } else {
            timeInterpolator = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        }
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Iterator it = BaseSlider.this.labels.iterator();
                while (it.hasNext()) {
                    TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
                    Objects.requireNonNull(tooltipDrawable);
                    tooltipDrawable.tooltipPivotY = 1.2f;
                    tooltipDrawable.tooltipScaleX = floatValue;
                    tooltipDrawable.tooltipScaleY = floatValue;
                    tooltipDrawable.labelOpacity = AnimationUtils.lerp(0.0f, 1.0f, 0.19f, 1.0f, floatValue);
                    tooltipDrawable.invalidateSelf();
                }
                BaseSlider baseSlider = BaseSlider.this;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(baseSlider);
            }
        });
        return ofFloat;
    }

    public float getMinSeparation() {
        return 0.0f;
    }

    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.isLongPress = false;
        return super.onKeyUp(i, keyEvent);
    }

    public final void setValueForLabel(TooltipDrawable tooltipDrawable, float f) {
        String str;
        ViewOverlayApi18 viewOverlayApi18;
        if (((float) ((int) f)) == f) {
            str = "%.0f";
        } else {
            str = "%.2f";
        }
        String format = String.format(str, new Object[]{Float.valueOf(f)});
        Objects.requireNonNull(tooltipDrawable);
        if (!TextUtils.equals(tooltipDrawable.text, format)) {
            tooltipDrawable.text = format;
            TextDrawableHelper textDrawableHelper = tooltipDrawable.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper);
            textDrawableHelper.textWidthDirty = true;
            tooltipDrawable.invalidateSelf();
        }
        int normalizeValue = (this.trackSidePadding + ((int) (normalizeValue(f) * ((float) this.trackWidth)))) - (tooltipDrawable.getIntrinsicWidth() / 2);
        int calculateTop = calculateTop() - (this.labelPadding + this.thumbRadius);
        tooltipDrawable.setBounds(normalizeValue, calculateTop - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + normalizeValue, calculateTop);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewGroup contentView = ViewUtils.getContentView(this);
        if (contentView == null) {
            viewOverlayApi18 = null;
        } else {
            viewOverlayApi18 = new ViewOverlayApi18(contentView);
        }
        Objects.requireNonNull(viewOverlayApi18);
        viewOverlayApi18.viewOverlay.add(tooltipDrawable);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BaseSlider(android.content.Context r20, android.util.AttributeSet r21, int r22) {
        /*
            r19 = this;
            r0 = r19
            r2 = r21
            r1 = 2130969744(0x7f040490, float:1.7548179E38)
            r3 = 2132018702(0x7f14060e, float:1.9675718E38)
            r4 = r20
            android.content.Context r3 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r4, r2, r1, r3)
            r0.<init>(r3, r2, r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.labels = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.changeListeners = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.touchListeners = r1
            r7 = 0
            r0.labelsAreAnimatedIn = r7
            r0.thumbIsPressed = r7
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.values = r1
            r1 = -1
            r0.activeThumbIdx = r1
            r0.focusedThumbIdx = r1
            r8 = 0
            r0.stepSize = r8
            r9 = 1
            r0.tickVisible = r9
            r0.isLongPress = r7
            com.google.android.material.shape.MaterialShapeDrawable r10 = new com.google.android.material.shape.MaterialShapeDrawable
            r10.<init>()
            r0.thumbDrawable = r10
            r0.separationUnit = r7
            android.content.Context r11 = r19.getContext()
            android.graphics.Paint r12 = new android.graphics.Paint
            r12.<init>()
            r0.inactiveTrackPaint = r12
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r12.setStyle(r1)
            android.graphics.Paint$Cap r1 = android.graphics.Paint.Cap.ROUND
            r12.setStrokeCap(r1)
            android.graphics.Paint r13 = new android.graphics.Paint
            r13.<init>()
            r0.activeTrackPaint = r13
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r13.setStyle(r1)
            android.graphics.Paint$Cap r1 = android.graphics.Paint.Cap.ROUND
            r13.setStrokeCap(r1)
            android.graphics.Paint r1 = new android.graphics.Paint
            r1.<init>(r9)
            r0.thumbPaint = r1
            android.graphics.Paint$Style r3 = android.graphics.Paint.Style.FILL
            r1.setStyle(r3)
            android.graphics.PorterDuffXfermode r3 = new android.graphics.PorterDuffXfermode
            android.graphics.PorterDuff$Mode r4 = android.graphics.PorterDuff.Mode.CLEAR
            r3.<init>(r4)
            r1.setXfermode(r3)
            android.graphics.Paint r14 = new android.graphics.Paint
            r14.<init>(r9)
            r0.haloPaint = r14
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.FILL
            r14.setStyle(r1)
            android.graphics.Paint r15 = new android.graphics.Paint
            r15.<init>()
            r0.inactiveTicksPaint = r15
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r15.setStyle(r1)
            android.graphics.Paint$Cap r1 = android.graphics.Paint.Cap.ROUND
            r15.setStrokeCap(r1)
            android.graphics.Paint r6 = new android.graphics.Paint
            r6.<init>()
            r0.activeTicksPaint = r6
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r6.setStyle(r1)
            android.graphics.Paint$Cap r1 = android.graphics.Paint.Cap.ROUND
            r6.setStrokeCap(r1)
            android.content.res.Resources r1 = r11.getResources()
            r3 = 2131166556(0x7f07055c, float:1.794736E38)
            int r3 = r1.getDimensionPixelSize(r3)
            r0.widgetHeight = r3
            r3 = 2131166554(0x7f07055a, float:1.7947357E38)
            int r3 = r1.getDimensionPixelOffset(r3)
            r0.minTrackSidePadding = r3
            r0.trackSidePadding = r3
            r3 = 2131166552(0x7f070558, float:1.7947353E38)
            int r3 = r1.getDimensionPixelSize(r3)
            r0.defaultThumbRadius = r3
            r3 = 2131166555(0x7f07055b, float:1.7947359E38)
            int r3 = r1.getDimensionPixelOffset(r3)
            r0.trackTop = r3
            r3 = 2131166548(0x7f070554, float:1.7947344E38)
            int r1 = r1.getDimensionPixelSize(r3)
            r0.labelPadding = r1
            com.google.android.material.slider.BaseSlider$1 r1 = new com.google.android.material.slider.BaseSlider$1
            r1.<init>(r2)
            r0.labelMaker = r1
            int[] r3 = com.google.android.material.R$styleable.Slider
            int[] r5 = new int[r7]
            r4 = 2130969744(0x7f040490, float:1.7548179E38)
            r16 = 2132018702(0x7f14060e, float:1.9675718E38)
            r1 = r11
            r17 = r5
            r5 = r16
            r18 = r6
            r6 = r17
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 3
            float r2 = r1.getFloat(r2, r8)
            r0.valueFrom = r2
            r2 = 4
            r3 = 1065353216(0x3f800000, float:1.0)
            float r2 = r1.getFloat(r2, r3)
            r0.valueTo = r2
            java.lang.Float[] r2 = new java.lang.Float[r9]
            float r3 = r0.valueFrom
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            r2[r7] = r3
            r0.setValues(r2)
            r2 = 2
            float r3 = r1.getFloat(r2, r8)
            r0.stepSize = r3
            r3 = 18
            boolean r4 = r1.hasValue(r3)
            if (r4 == 0) goto L_0x0133
            r5 = r3
            goto L_0x0135
        L_0x0133:
            r5 = 20
        L_0x0135:
            if (r4 == 0) goto L_0x0138
            goto L_0x013a
        L_0x0138:
            r3 = 19
        L_0x013a:
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r5)
            if (r4 == 0) goto L_0x0141
            goto L_0x0148
        L_0x0141:
            r4 = 2131100373(0x7f0602d5, float:1.7813126E38)
            android.content.res.ColorStateList r4 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r4)
        L_0x0148:
            android.content.res.ColorStateList r5 = r0.trackColorInactive
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x0151
            goto L_0x015d
        L_0x0151:
            r0.trackColorInactive = r4
            int r4 = r0.getColorForState(r4)
            r12.setColor(r4)
            r19.invalidate()
        L_0x015d:
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)
            if (r3 == 0) goto L_0x0164
            goto L_0x016b
        L_0x0164:
            r3 = 2131100370(0x7f0602d2, float:1.781312E38)
            android.content.res.ColorStateList r3 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r3)
        L_0x016b:
            android.content.res.ColorStateList r4 = r0.trackColorActive
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0174
            goto L_0x0180
        L_0x0174:
            r0.trackColorActive = r3
            int r3 = r0.getColorForState(r3)
            r13.setColor(r3)
            r19.invalidate()
        L_0x0180:
            r3 = 9
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)
            r10.setFillColor(r3)
            r3 = 12
            boolean r4 = r1.hasValue(r3)
            if (r4 == 0) goto L_0x019b
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)
            r10.setStrokeColor(r3)
            r19.postInvalidate()
        L_0x019b:
            r3 = 13
            float r3 = r1.getDimension(r3, r8)
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r4 = r10.drawableState
            r4.strokeWidth = r3
            r10.invalidateSelf()
            r19.postInvalidate()
            r3 = 5
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)
            if (r3 == 0) goto L_0x01b3
            goto L_0x01ba
        L_0x01b3:
            r3 = 2131100371(0x7f0602d3, float:1.7813122E38)
            android.content.res.ColorStateList r3 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r3)
        L_0x01ba:
            android.content.res.ColorStateList r4 = r0.haloColor
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x01c3
            goto L_0x01e8
        L_0x01c3:
            r0.haloColor = r3
            android.graphics.drawable.Drawable r4 = r19.getBackground()
            boolean r5 = r19.shouldDrawCompatHalo()
            if (r5 != 0) goto L_0x01d9
            boolean r5 = r4 instanceof android.graphics.drawable.RippleDrawable
            if (r5 == 0) goto L_0x01d9
            android.graphics.drawable.RippleDrawable r4 = (android.graphics.drawable.RippleDrawable) r4
            r4.setColor(r3)
            goto L_0x01e8
        L_0x01d9:
            int r3 = r0.getColorForState(r3)
            r14.setColor(r3)
            r3 = 63
            r14.setAlpha(r3)
            r19.invalidate()
        L_0x01e8:
            r3 = 17
            boolean r3 = r1.getBoolean(r3, r9)
            r0.tickVisible = r3
            r3 = 14
            boolean r4 = r1.hasValue(r3)
            if (r4 == 0) goto L_0x01fa
            r5 = r3
            goto L_0x01fc
        L_0x01fa:
            r5 = 16
        L_0x01fc:
            if (r4 == 0) goto L_0x01ff
            goto L_0x0201
        L_0x01ff:
            r3 = 15
        L_0x0201:
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r5)
            if (r4 == 0) goto L_0x0208
            goto L_0x020f
        L_0x0208:
            r4 = 2131100372(0x7f0602d4, float:1.7813124E38)
            android.content.res.ColorStateList r4 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r4)
        L_0x020f:
            android.content.res.ColorStateList r5 = r0.tickColorInactive
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x0218
            goto L_0x0224
        L_0x0218:
            r0.tickColorInactive = r4
            int r4 = r0.getColorForState(r4)
            r15.setColor(r4)
            r19.invalidate()
        L_0x0224:
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)
            if (r3 == 0) goto L_0x022b
            goto L_0x0232
        L_0x022b:
            r3 = 2131100369(0x7f0602d1, float:1.7813118E38)
            android.content.res.ColorStateList r3 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r3)
        L_0x0232:
            android.content.res.ColorStateList r4 = r0.tickColorActive
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x023d
            r4 = r18
            goto L_0x024b
        L_0x023d:
            r0.tickColorActive = r3
            int r3 = r0.getColorForState(r3)
            r4 = r18
            r4.setColor(r3)
            r19.invalidate()
        L_0x024b:
            r3 = 11
            int r3 = r1.getDimensionPixelSize(r3, r7)
            int r5 = r0.thumbRadius
            if (r3 != r5) goto L_0x0256
            goto L_0x02b1
        L_0x0256:
            r0.thumbRadius = r3
            int r5 = r0.defaultThumbRadius
            int r3 = r3 - r5
            int r3 = java.lang.Math.max(r3, r7)
            int r5 = r0.minTrackSidePadding
            int r5 = r5 + r3
            r0.trackSidePadding = r5
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r3 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r19)
            if (r3 == 0) goto L_0x027d
            int r3 = r19.getWidth()
            int r5 = r0.trackSidePadding
            int r5 = r5 * r2
            int r3 = r3 - r5
            int r3 = java.lang.Math.max(r3, r7)
            r0.trackWidth = r3
            r19.maybeCalculateTicksCoordinates()
        L_0x027d:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r3 = new com.google.android.material.shape.ShapeAppearanceModel$Builder
            r3.<init>()
            int r5 = r0.thumbRadius
            float r5 = (float) r5
            androidx.leanback.R$drawable r6 = androidx.mediarouter.R$bool.createCornerTreatment(r7)
            r3.topLeftCorner = r6
            com.google.android.material.shape.ShapeAppearanceModel.Builder.compatCornerTreatmentSize(r6)
            r3.topRightCorner = r6
            com.google.android.material.shape.ShapeAppearanceModel.Builder.compatCornerTreatmentSize(r6)
            r3.bottomRightCorner = r6
            com.google.android.material.shape.ShapeAppearanceModel.Builder.compatCornerTreatmentSize(r6)
            r3.bottomLeftCorner = r6
            com.google.android.material.shape.ShapeAppearanceModel.Builder.compatCornerTreatmentSize(r6)
            r3.setAllCornerSizes(r5)
            com.google.android.material.shape.ShapeAppearanceModel r5 = new com.google.android.material.shape.ShapeAppearanceModel
            r5.<init>(r3)
            r10.setShapeAppearanceModel(r5)
            int r3 = r0.thumbRadius
            int r3 = r3 * r2
            r10.setBounds(r7, r7, r3, r3)
            r19.postInvalidate()
        L_0x02b1:
            r2 = 6
            int r2 = r1.getDimensionPixelSize(r2, r7)
            int r3 = r0.haloRadius
            if (r2 != r3) goto L_0x02bb
            goto L_0x02d6
        L_0x02bb:
            r0.haloRadius = r2
            android.graphics.drawable.Drawable r2 = r19.getBackground()
            boolean r3 = r19.shouldDrawCompatHalo()
            if (r3 != 0) goto L_0x02d3
            boolean r3 = r2 instanceof android.graphics.drawable.RippleDrawable
            if (r3 == 0) goto L_0x02d3
            android.graphics.drawable.RippleDrawable r2 = (android.graphics.drawable.RippleDrawable) r2
            int r3 = r0.haloRadius
            r2.setRadius(r3)
            goto L_0x02d6
        L_0x02d3:
            r19.postInvalidate()
        L_0x02d6:
            r2 = 10
            float r2 = r1.getDimension(r2, r8)
            r10.setElevation(r2)
            r2 = 21
            int r2 = r1.getDimensionPixelSize(r2, r7)
            int r3 = r0.trackHeight
            if (r3 == r2) goto L_0x0308
            r0.trackHeight = r2
            float r2 = (float) r2
            r12.setStrokeWidth(r2)
            int r2 = r0.trackHeight
            float r2 = (float) r2
            r13.setStrokeWidth(r2)
            int r2 = r0.trackHeight
            float r2 = (float) r2
            r3 = 1073741824(0x40000000, float:2.0)
            float r2 = r2 / r3
            r15.setStrokeWidth(r2)
            int r2 = r0.trackHeight
            float r2 = (float) r2
            float r2 = r2 / r3
            r4.setStrokeWidth(r2)
            r19.postInvalidate()
        L_0x0308:
            r2 = 7
            int r2 = r1.getInt(r2, r7)
            r0.labelBehavior = r2
            boolean r2 = r1.getBoolean(r7, r9)
            if (r2 != 0) goto L_0x0318
            r0.setEnabled(r7)
        L_0x0318:
            r1.recycle()
            r0.setFocusable(r9)
            r0.setClickable(r9)
            r10.setShadowCompatibilityMode()
            android.view.ViewConfiguration r1 = android.view.ViewConfiguration.get(r11)
            int r1 = r1.getScaledTouchSlop()
            r0.scaledTouchSlop = r1
            com.google.android.material.slider.BaseSlider$AccessibilityHelper r1 = new com.google.android.material.slider.BaseSlider$AccessibilityHelper
            r1.<init>(r0)
            r0.accessibilityHelper = r1
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r0, r1)
            android.content.Context r1 = r19.getContext()
            java.lang.String r2 = "accessibility"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.view.accessibility.AccessibilityManager r1 = (android.view.accessibility.AccessibilityManager) r1
            r0.accessibilityManager = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final int calculateTop() {
        int i = this.trackTop;
        int i2 = 0;
        if (this.labelBehavior == 1) {
            i2 = ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight();
        }
        return i + i2;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.accessibilityHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    public final void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            ValueAnimator createLabelAnimator = createLabelAnimator(false);
            this.labelsOutAnimator = createLabelAnimator;
            this.labelsInAnimator = null;
            createLabelAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ViewOverlayApi18 viewOverlayApi18;
                    super.onAnimationEnd(animator);
                    Iterator it = BaseSlider.this.labels.iterator();
                    while (it.hasNext()) {
                        TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
                        ViewGroup contentView = ViewUtils.getContentView(BaseSlider.this);
                        if (contentView == null) {
                            viewOverlayApi18 = null;
                        } else {
                            viewOverlayApi18 = new ViewOverlayApi18(contentView);
                        }
                        Objects.requireNonNull(viewOverlayApi18);
                        viewOverlayApi18.viewOverlay.remove(tooltipDrawable);
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    public final CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        AccessibilityHelper accessibilityHelper2 = this.accessibilityHelper;
        Objects.requireNonNull(accessibilityHelper2);
        return accessibilityHelper2.mAccessibilityFocusedVirtualViewId;
    }

    public ArrayList getValues() {
        return new ArrayList(this.values);
    }

    public final boolean isMultipleOfStepSize(float f) {
        double doubleValue = new BigDecimal(Float.toString(f)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        if (Math.abs(((double) Math.round(doubleValue)) - doubleValue) < 1.0E-4d) {
            return true;
        }
        return false;
    }

    public final boolean isRtl() {
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
            return true;
        }
        return false;
    }

    public final void maybeCalculateTicksCoordinates() {
        if (this.stepSize > 0.0f) {
            validateConfigurationIfDirty();
            int min = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / (this.trackHeight * 2)) + 1);
            float[] fArr = this.ticksCoordinates;
            if (fArr == null || fArr.length != min * 2) {
                this.ticksCoordinates = new float[(min * 2)];
            }
            float f = ((float) this.trackWidth) / ((float) (min - 1));
            for (int i = 0; i < min * 2; i += 2) {
                float[] fArr2 = this.ticksCoordinates;
                fArr2[i] = (((float) (i / 2)) * f) + ((float) this.trackSidePadding);
                fArr2[i + 1] = (float) calculateTop();
            }
        }
    }

    public final boolean moveFocus(int i) {
        int i2 = this.focusedThumbIdx;
        long j = ((long) i2) + ((long) i);
        long size = (long) (this.values.size() - 1);
        if (j < 0) {
            j = 0;
        } else if (j > size) {
            j = size;
        }
        int i3 = (int) j;
        this.focusedThumbIdx = i3;
        if (i3 == i2) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = i3;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    public final float normalizeValue(float f) {
        float f2 = this.valueFrom;
        float f3 = (f - f2) / (this.valueTo - f2);
        if (isRtl()) {
            return 1.0f - f3;
        }
        return f3;
    }

    public final void onDetachedFromWindow() {
        ViewOverlayApi18 viewOverlayApi18;
        BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 != null) {
            removeCallbacks(accessibilityEventSender2);
        }
        this.labelsAreAnimatedIn = false;
        Iterator it = this.labels.iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            ViewGroup contentView = ViewUtils.getContentView(this);
            if (contentView == null) {
                viewOverlayApi18 = null;
            } else {
                viewOverlayApi18 = new ViewOverlayApi18(contentView);
            }
            if (viewOverlayApi18 != null) {
                viewOverlayApi18.viewOverlay.remove(tooltipDrawable);
                ViewGroup contentView2 = ViewUtils.getContentView(this);
                if (contentView2 == null) {
                    Objects.requireNonNull(tooltipDrawable);
                } else {
                    contentView2.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                }
            }
        }
        super.onDetachedFromWindow();
    }

    public final void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            maybeCalculateTicksCoordinates();
        }
        super.onDraw(canvas);
        int calculateTop = calculateTop();
        int i = this.trackWidth;
        float[] activeRange = getActiveRange();
        int i2 = this.trackSidePadding;
        float f = (float) i;
        float f2 = (activeRange[1] * f) + ((float) i2);
        float f3 = (float) (i2 + i);
        if (f2 < f3) {
            float f4 = (float) calculateTop;
            canvas.drawLine(f2, f4, f3, f4, this.inactiveTrackPaint);
        }
        float f5 = (float) this.trackSidePadding;
        float f6 = (activeRange[0] * f) + f5;
        if (f6 > f5) {
            float f7 = (float) calculateTop;
            canvas.drawLine(f5, f7, f6, f7, this.inactiveTrackPaint);
        }
        if (((Float) Collections.max(getValues())).floatValue() > this.valueFrom) {
            int i3 = this.trackWidth;
            float[] activeRange2 = getActiveRange();
            float f8 = (float) this.trackSidePadding;
            float f9 = (float) i3;
            float f10 = (float) calculateTop;
            canvas.drawLine((activeRange2[0] * f9) + f8, f10, (activeRange2[1] * f9) + f8, f10, this.activeTrackPaint);
        }
        if (this.tickVisible && this.stepSize > 0.0f) {
            float[] activeRange3 = getActiveRange();
            int round = Math.round(activeRange3[0] * ((float) ((this.ticksCoordinates.length / 2) - 1)));
            int round2 = Math.round(activeRange3[1] * ((float) ((this.ticksCoordinates.length / 2) - 1)));
            int i4 = round * 2;
            canvas.drawPoints(this.ticksCoordinates, 0, i4, this.inactiveTicksPaint);
            int i5 = round2 * 2;
            canvas.drawPoints(this.ticksCoordinates, i4, i5 - i4, this.activeTicksPaint);
            float[] fArr = this.ticksCoordinates;
            canvas.drawPoints(fArr, i5, fArr.length - i5, this.inactiveTicksPaint);
        }
        if ((this.thumbIsPressed || isFocused()) && isEnabled()) {
            int i6 = this.trackWidth;
            if (shouldDrawCompatHalo()) {
                canvas.drawCircle((float) ((int) ((normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) i6)) + ((float) this.trackSidePadding))), (float) calculateTop, (float) this.haloRadius, this.haloPaint);
            }
            if (!(this.activeThumbIdx == -1 || this.labelBehavior == 2)) {
                if (!this.labelsAreAnimatedIn) {
                    this.labelsAreAnimatedIn = true;
                    ValueAnimator createLabelAnimator = createLabelAnimator(true);
                    this.labelsInAnimator = createLabelAnimator;
                    this.labelsOutAnimator = null;
                    createLabelAnimator.start();
                }
                Iterator it = this.labels.iterator();
                for (int i7 = 0; i7 < this.values.size() && it.hasNext(); i7++) {
                    if (i7 != this.focusedThumbIdx) {
                        setValueForLabel((TooltipDrawable) it.next(), this.values.get(i7).floatValue());
                    }
                }
                if (it.hasNext()) {
                    setValueForLabel((TooltipDrawable) it.next(), this.values.get(this.focusedThumbIdx).floatValue());
                } else {
                    throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", new Object[]{Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size())}));
                }
            }
        }
        int i8 = this.trackWidth;
        if (!isEnabled()) {
            Iterator<Float> it2 = this.values.iterator();
            while (it2.hasNext()) {
                canvas.drawCircle((normalizeValue(it2.next().floatValue()) * ((float) i8)) + ((float) this.trackSidePadding), (float) calculateTop, (float) this.thumbRadius, this.thumbPaint);
            }
        }
        Iterator<Float> it3 = this.values.iterator();
        while (it3.hasNext()) {
            canvas.save();
            int normalizeValue = this.trackSidePadding + ((int) (normalizeValue(it3.next().floatValue()) * ((float) i8)));
            int i9 = this.thumbRadius;
            canvas.translate((float) (normalizeValue - i9), (float) (calculateTop - i9));
            this.thumbDrawable.draw(canvas);
            canvas.restore();
        }
    }

    public final void onMeasure(int i, int i2) {
        int i3 = this.widgetHeight;
        int i4 = 0;
        if (this.labelBehavior == 1) {
            i4 = ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight();
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(i3 + i4, 1073741824));
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SliderState sliderState = (SliderState) parcelable;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        this.trackWidth = Math.max(i - (this.trackSidePadding * 2), 0);
        maybeCalculateTicksCoordinates();
        updateHaloHotspot();
    }

    public final void onStartTrackingTouch() {
        Iterator it = this.touchListeners.iterator();
        while (it.hasNext()) {
            ((BaseOnSliderTouchListener) it.next()).onStartTrackingTouch();
        }
    }

    public boolean pickActiveThumb() {
        boolean z;
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float f = this.touchPosition;
        if (isRtl()) {
            f = 1.0f - f;
        }
        float f2 = this.valueTo;
        float f3 = this.valueFrom;
        float m = MotionController$$ExternalSyntheticOutline0.m7m(f2, f3, f, f3);
        float normalizeValue = (normalizeValue(m) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
        this.activeThumbIdx = 0;
        float abs = Math.abs(this.values.get(0).floatValue() - m);
        for (int i = 1; i < this.values.size(); i++) {
            float abs2 = Math.abs(this.values.get(i).floatValue() - m);
            float normalizeValue2 = (normalizeValue(this.values.get(i).floatValue()) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
            if (Float.compare(abs2, abs) > 1) {
                break;
            }
            if (!isRtl() ? normalizeValue2 - normalizeValue >= 0.0f : normalizeValue2 - normalizeValue <= 0.0f) {
                z = false;
            } else {
                z = true;
            }
            if (Float.compare(abs2, abs) < 0) {
                this.activeThumbIdx = i;
            } else {
                if (Float.compare(abs2, abs) != 0) {
                    continue;
                } else if (Math.abs(normalizeValue2 - normalizeValue) < ((float) this.scaledTouchSlop)) {
                    this.activeThumbIdx = -1;
                    return false;
                } else if (z) {
                    this.activeThumbIdx = i;
                }
            }
            abs = abs2;
        }
        if (this.activeThumbIdx != -1) {
            return true;
        }
        return false;
    }

    public void setValues(Float... fArr) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, fArr);
        setValuesInternal(arrayList);
    }

    public final boolean shouldDrawCompatHalo() {
        if (this.forceDrawCompatHalo || !(getBackground() instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }

    public final boolean snapThumbToValue(int i, float f) {
        float f2;
        float f3;
        this.focusedThumbIdx = i;
        if (((double) Math.abs(f - this.values.get(i).floatValue())) < 1.0E-4d) {
            return false;
        }
        float minSeparation = getMinSeparation();
        if (this.separationUnit == 0) {
            if (minSeparation == 0.0f) {
                minSeparation = 0.0f;
            } else {
                float f4 = (minSeparation - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
                float f5 = this.valueFrom;
                minSeparation = MotionController$$ExternalSyntheticOutline0.m7m(f5, this.valueTo, f4, f5);
            }
        }
        if (isRtl()) {
            minSeparation = -minSeparation;
        }
        int i2 = i + 1;
        if (i2 >= this.values.size()) {
            f2 = this.valueTo;
        } else {
            f2 = this.values.get(i2).floatValue() - minSeparation;
        }
        int i3 = i - 1;
        if (i3 < 0) {
            f3 = this.valueFrom;
        } else {
            f3 = minSeparation + this.values.get(i3).floatValue();
        }
        this.values.set(i, Float.valueOf(AtomicFU.clamp(f, f3, f2)));
        Iterator it = this.changeListeners.iterator();
        while (it.hasNext()) {
            this.values.get(i).floatValue();
            ((BaseOnChangeListener) it.next()).onValueChange();
        }
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 == null || !accessibilityManager2.isEnabled()) {
            return true;
        }
        BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            removeCallbacks(accessibilityEventSender2);
        }
        BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender3 = this.accessibilityEventSender;
        Objects.requireNonNull(accessibilityEventSender3);
        accessibilityEventSender3.virtualViewId = i;
        postDelayed(this.accessibilityEventSender, 200);
        return true;
    }

    public final boolean snapTouchPosition() {
        double d;
        float f = this.touchPosition;
        float f2 = this.stepSize;
        if (f2 > 0.0f) {
            int i = (int) ((this.valueTo - this.valueFrom) / f2);
            d = ((double) Math.round(f * ((float) i))) / ((double) i);
        } else {
            d = (double) f;
        }
        if (isRtl()) {
            d = 1.0d - d;
        }
        float f3 = this.valueTo;
        float f4 = this.valueFrom;
        return snapThumbToValue(this.activeThumbIdx, (float) ((d * ((double) (f3 - f4))) + ((double) f4)));
    }

    public final void updateBoundsForVirturalViewId(int i, Rect rect) {
        int normalizeValue = this.trackSidePadding + ((int) (normalizeValue(((Float) getValues().get(i)).floatValue()) * ((float) this.trackWidth)));
        int calculateTop = calculateTop();
        int i2 = this.thumbRadius;
        rect.set(normalizeValue - i2, calculateTop - i2, normalizeValue + i2, calculateTop + i2);
    }

    public final void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            float f = this.valueFrom;
            float f2 = this.valueTo;
            if (f >= f2) {
                throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", new Object[]{Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
            } else if (f2 <= f) {
                throw new IllegalStateException(String.format("valueTo(%s) must be greater than valueFrom(%s)", new Object[]{Float.valueOf(this.valueTo), Float.valueOf(this.valueFrom)}));
            } else if (this.stepSize <= 0.0f || isMultipleOfStepSize(f2 - f)) {
                Iterator<Float> it = this.values.iterator();
                while (it.hasNext()) {
                    Float next = it.next();
                    if (next.floatValue() < this.valueFrom || next.floatValue() > this.valueTo) {
                        throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", new Object[]{next, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
                    } else if (this.stepSize > 0.0f && !isMultipleOfStepSize(next.floatValue() - this.valueFrom)) {
                        throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", new Object[]{next, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
                    }
                }
                float minSeparation = getMinSeparation();
                if (minSeparation >= 0.0f) {
                    float f3 = this.stepSize;
                    if (f3 > 0.0f && minSeparation > 0.0f) {
                        if (this.separationUnit != 1) {
                            throw new IllegalStateException(String.format("minSeparation(%s) cannot be set as a dimension when using stepSize(%s)", new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize)}));
                        } else if (minSeparation < f3 || !isMultipleOfStepSize(minSeparation)) {
                            throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)", new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
                        }
                    }
                    float f4 = this.stepSize;
                    if (f4 != 0.0f) {
                        if (((float) ((int) f4)) != f4) {
                            Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"stepSize", Float.valueOf(f4)}));
                        }
                        float f5 = this.valueFrom;
                        if (((float) ((int) f5)) != f5) {
                            Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"valueFrom", Float.valueOf(f5)}));
                        }
                        float f6 = this.valueTo;
                        if (((float) ((int) f6)) != f6) {
                            Log.w("BaseSlider", String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"valueTo", Float.valueOf(f6)}));
                        }
                    }
                    this.dirtyConfig = false;
                    return;
                }
                throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal to 0", new Object[]{Float.valueOf(minSeparation)}));
            } else {
                throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", new Object[]{Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
            }
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        Iterator it = this.labels.iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            if (tooltipDrawable.isStateful()) {
                tooltipDrawable.setState(getDrawableState());
            }
        }
        if (this.thumbDrawable.isStateful()) {
            this.thumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    public final float[] getActiveRange() {
        float floatValue = ((Float) Collections.max(getValues())).floatValue();
        float floatValue2 = ((Float) Collections.min(getValues())).floatValue();
        if (this.values.size() == 1) {
            floatValue2 = this.valueFrom;
        }
        float normalizeValue = normalizeValue(floatValue2);
        float normalizeValue2 = normalizeValue(floatValue);
        if (isRtl()) {
            return new float[]{normalizeValue2, normalizeValue};
        }
        return new float[]{normalizeValue, normalizeValue2};
    }

    public final int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    public final boolean isInVerticalScrollingContainer() {
        ViewParent parent = getParent();
        while (true) {
            boolean z = false;
            if (!(parent instanceof ViewGroup)) {
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) {
                z = true;
            }
            if (z && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
    }

    public final boolean moveFocusInAbsoluteDirection(int i) {
        if (isRtl()) {
            if (i == Integer.MIN_VALUE) {
                i = Integer.MAX_VALUE;
            } else {
                i = -i;
            }
        }
        return moveFocus(i);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Iterator it = this.labels.iterator();
        while (it.hasNext()) {
            TooltipDrawable tooltipDrawable = (TooltipDrawable) it.next();
            ViewGroup contentView = ViewUtils.getContentView(this);
            if (contentView == null) {
                Objects.requireNonNull(tooltipDrawable);
            } else {
                Objects.requireNonNull(tooltipDrawable);
                int[] iArr = new int[2];
                contentView.getLocationOnScreen(iArr);
                tooltipDrawable.locationOnScreenX = iArr[0];
                contentView.getWindowVisibleDisplayFrame(tooltipDrawable.displayFrame);
                contentView.addOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
            }
        }
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            this.activeThumbIdx = -1;
            ensureLabelsRemoved();
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        if (i == 1) {
            moveFocus(Integer.MAX_VALUE);
        } else if (i == 2) {
            moveFocus(Integer.MIN_VALUE);
        } else if (i == 17) {
            moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
        } else if (i == 66) {
            moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
        }
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.lang.Float} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Boolean} */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: type inference failed for: r5v19 */
    /* JADX WARNING: type inference failed for: r5v20 */
    /* JADX WARNING: type inference failed for: r5v21 */
    /* JADX WARNING: type inference failed for: r5v22 */
    /* JADX WARNING: type inference failed for: r5v23 */
    /* JADX WARNING: type inference failed for: r5v24 */
    /* JADX WARNING: type inference failed for: r5v25 */
    /* JADX WARNING: type inference failed for: r5v26 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onKeyDown(int r14, android.view.KeyEvent r15) {
        /*
            r13 = this;
            boolean r0 = r13.isEnabled()
            if (r0 != 0) goto L_0x000b
            boolean r13 = super.onKeyDown(r14, r15)
            return r13
        L_0x000b:
            java.util.ArrayList<java.lang.Float> r0 = r13.values
            int r0 = r0.size()
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L_0x0017
            r13.activeThumbIdx = r1
        L_0x0017:
            int r0 = r13.activeThumbIdx
            r3 = 66
            r4 = 61
            r5 = 0
            r6 = 81
            r7 = 70
            r8 = 69
            r9 = -1
            if (r0 != r9) goto L_0x0083
            if (r14 == r4) goto L_0x0057
            if (r14 == r3) goto L_0x004d
            if (r14 == r6) goto L_0x0047
            if (r14 == r8) goto L_0x0041
            if (r14 == r7) goto L_0x0047
            switch(r14) {
                case 21: goto L_0x003b;
                case 22: goto L_0x0035;
                case 23: goto L_0x004d;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x0077
        L_0x0035:
            r13.moveFocusInAbsoluteDirection(r2)
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0077
        L_0x003b:
            r13.moveFocusInAbsoluteDirection(r9)
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0077
        L_0x0041:
            r13.moveFocus(r9)
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0077
        L_0x0047:
            r13.moveFocus(r2)
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0077
        L_0x004d:
            int r0 = r13.focusedThumbIdx
            r13.activeThumbIdx = r0
            r13.postInvalidate()
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0077
        L_0x0057:
            boolean r0 = r15.hasNoModifiers()
            if (r0 == 0) goto L_0x0066
            boolean r0 = r13.moveFocus(r2)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
            goto L_0x0077
        L_0x0066:
            boolean r0 = r15.isShiftPressed()
            if (r0 == 0) goto L_0x0075
            boolean r0 = r13.moveFocus(r9)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
            goto L_0x0077
        L_0x0075:
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
        L_0x0077:
            if (r5 == 0) goto L_0x007e
            boolean r13 = r5.booleanValue()
            goto L_0x0082
        L_0x007e:
            boolean r13 = super.onKeyDown(r14, r15)
        L_0x0082:
            return r13
        L_0x0083:
            boolean r0 = r13.isLongPress
            boolean r10 = r15.isLongPress()
            r0 = r0 | r10
            r13.isLongPress = r0
            r10 = 1065353216(0x3f800000, float:1.0)
            r11 = 0
            if (r0 == 0) goto L_0x00af
            float r0 = r13.stepSize
            int r11 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r11 != 0) goto L_0x0098
            goto L_0x0099
        L_0x0098:
            r10 = r0
        L_0x0099:
            float r0 = r13.valueTo
            float r11 = r13.valueFrom
            float r0 = r0 - r11
            float r0 = r0 / r10
            r11 = 20
            float r11 = (float) r11
            int r12 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r12 > 0) goto L_0x00a7
            goto L_0x00b7
        L_0x00a7:
            float r0 = r0 / r11
            int r0 = java.lang.Math.round(r0)
            float r0 = (float) r0
            float r10 = r10 * r0
            goto L_0x00b7
        L_0x00af:
            float r0 = r13.stepSize
            int r11 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r11 != 0) goto L_0x00b6
            goto L_0x00b7
        L_0x00b6:
            r10 = r0
        L_0x00b7:
            r0 = 21
            if (r14 == r0) goto L_0x00dd
            r0 = 22
            if (r14 == r0) goto L_0x00d1
            if (r14 == r8) goto L_0x00cb
            if (r14 == r7) goto L_0x00c6
            if (r14 == r6) goto L_0x00c6
            goto L_0x00e9
        L_0x00c6:
            java.lang.Float r5 = java.lang.Float.valueOf(r10)
            goto L_0x00e9
        L_0x00cb:
            float r0 = -r10
            java.lang.Float r5 = java.lang.Float.valueOf(r0)
            goto L_0x00e9
        L_0x00d1:
            boolean r0 = r13.isRtl()
            if (r0 == 0) goto L_0x00d8
            float r10 = -r10
        L_0x00d8:
            java.lang.Float r5 = java.lang.Float.valueOf(r10)
            goto L_0x00e9
        L_0x00dd:
            boolean r0 = r13.isRtl()
            if (r0 == 0) goto L_0x00e4
            goto L_0x00e5
        L_0x00e4:
            float r10 = -r10
        L_0x00e5:
            java.lang.Float r5 = java.lang.Float.valueOf(r10)
        L_0x00e9:
            if (r5 == 0) goto L_0x010d
            java.util.ArrayList<java.lang.Float> r14 = r13.values
            int r15 = r13.activeThumbIdx
            java.lang.Object r14 = r14.get(r15)
            java.lang.Float r14 = (java.lang.Float) r14
            float r14 = r14.floatValue()
            float r15 = r5.floatValue()
            float r15 = r15 + r14
            int r14 = r13.activeThumbIdx
            boolean r14 = r13.snapThumbToValue(r14, r15)
            if (r14 == 0) goto L_0x010c
            r13.updateHaloHotspot()
            r13.postInvalidate()
        L_0x010c:
            return r2
        L_0x010d:
            r0 = 23
            if (r14 == r0) goto L_0x0131
            if (r14 == r4) goto L_0x011a
            if (r14 == r3) goto L_0x0131
            boolean r13 = super.onKeyDown(r14, r15)
            return r13
        L_0x011a:
            boolean r14 = r15.hasNoModifiers()
            if (r14 == 0) goto L_0x0125
            boolean r13 = r13.moveFocus(r2)
            return r13
        L_0x0125:
            boolean r14 = r15.isShiftPressed()
            if (r14 == 0) goto L_0x0130
            boolean r13 = r13.moveFocus(r9)
            return r13
        L_0x0130:
            return r1
        L_0x0131:
            r13.activeThumbIdx = r9
            r13.ensureLabelsRemoved()
            r13.postInvalidate()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        float f = (x - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
        this.touchPosition = f;
        float max = Math.max(0.0f, f);
        this.touchPosition = max;
        this.touchPosition = Math.min(1.0f, max);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.touchDownX = x;
            if (!isInVerticalScrollingContainer()) {
                getParent().requestDisallowInterceptTouchEvent(true);
                if (pickActiveThumb()) {
                    requestFocus();
                    this.thumbIsPressed = true;
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                    onStartTrackingTouch();
                }
            }
        } else if (actionMasked == 1) {
            this.thumbIsPressed = false;
            MotionEvent motionEvent2 = this.lastEvent;
            if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - motionEvent.getX()) <= ((float) this.scaledTouchSlop) && Math.abs(this.lastEvent.getY() - motionEvent.getY()) <= ((float) this.scaledTouchSlop) && pickActiveThumb()) {
                onStartTrackingTouch();
            }
            if (this.activeThumbIdx != -1) {
                snapTouchPosition();
                this.activeThumbIdx = -1;
                Iterator it = this.touchListeners.iterator();
                while (it.hasNext()) {
                    ((BaseOnSliderTouchListener) it.next()).onStopTrackingTouch();
                }
            }
            ensureLabelsRemoved();
            invalidate();
        } else if (actionMasked == 2) {
            if (!this.thumbIsPressed) {
                if (isInVerticalScrollingContainer() && Math.abs(x - this.touchDownX) < ((float) this.scaledTouchSlop)) {
                    return false;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                onStartTrackingTouch();
            }
            if (pickActiveThumb()) {
                this.thumbIsPressed = true;
                snapTouchPosition();
                updateHaloHotspot();
                invalidate();
            }
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(motionEvent);
        return true;
    }

    public final void setEnabled(boolean z) {
        int i;
        super.setEnabled(z);
        if (z) {
            i = 0;
        } else {
            i = 2;
        }
        setLayerType(i, (Paint) null);
    }

    public final void setValuesInternal(ArrayList<Float> arrayList) {
        TextAppearance textAppearance;
        ViewGroup contentView;
        int resourceId;
        ViewOverlayApi18 viewOverlayApi18;
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            if (this.values.size() != arrayList.size() || !this.values.equals(arrayList)) {
                this.values = arrayList;
                int i = 1;
                this.dirtyConfig = true;
                this.focusedThumbIdx = 0;
                updateHaloHotspot();
                if (this.labels.size() > this.values.size()) {
                    List<TooltipDrawable> subList = this.labels.subList(this.values.size(), this.labels.size());
                    for (TooltipDrawable tooltipDrawable : subList) {
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        if (ViewCompat.Api19Impl.isAttachedToWindow(this)) {
                            ViewGroup contentView2 = ViewUtils.getContentView(this);
                            if (contentView2 == null) {
                                viewOverlayApi18 = null;
                            } else {
                                viewOverlayApi18 = new ViewOverlayApi18(contentView2);
                            }
                            if (viewOverlayApi18 != null) {
                                viewOverlayApi18.viewOverlay.remove(tooltipDrawable);
                                ViewGroup contentView3 = ViewUtils.getContentView(this);
                                if (contentView3 == null) {
                                    Objects.requireNonNull(tooltipDrawable);
                                } else {
                                    contentView3.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                                }
                            }
                        }
                    }
                    subList.clear();
                }
                while (this.labels.size() < this.values.size()) {
                    C20801 r1 = this.labelMaker;
                    Objects.requireNonNull(r1);
                    TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(BaseSlider.this.getContext(), r2, R$styleable.Slider, r1.val$defStyleAttr, 2132018702, new int[0]);
                    Context context = BaseSlider.this.getContext();
                    int resourceId2 = obtainStyledAttributes.getResourceId(8, 2132018736);
                    TooltipDrawable tooltipDrawable2 = new TooltipDrawable(context, resourceId2);
                    TypedArray obtainStyledAttributes2 = ThemeEnforcement.obtainStyledAttributes(tooltipDrawable2.context, (AttributeSet) null, R$styleable.Tooltip, 0, resourceId2, new int[0]);
                    tooltipDrawable2.arrowSize = tooltipDrawable2.context.getResources().getDimensionPixelSize(C1777R.dimen.mtrl_tooltip_arrowSize);
                    ShapeAppearanceModel shapeAppearanceModel = tooltipDrawable2.drawableState.shapeAppearanceModel;
                    Objects.requireNonNull(shapeAppearanceModel);
                    ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
                    builder.bottomEdge = tooltipDrawable2.createMarkerEdge();
                    tooltipDrawable2.setShapeAppearanceModel(new ShapeAppearanceModel(builder));
                    CharSequence text = obtainStyledAttributes2.getText(6);
                    if (!TextUtils.equals(tooltipDrawable2.text, text)) {
                        tooltipDrawable2.text = text;
                        TextDrawableHelper textDrawableHelper = tooltipDrawable2.textDrawableHelper;
                        Objects.requireNonNull(textDrawableHelper);
                        textDrawableHelper.textWidthDirty = true;
                        tooltipDrawable2.invalidateSelf();
                    }
                    Context context2 = tooltipDrawable2.context;
                    if (!obtainStyledAttributes2.hasValue(0) || (resourceId = obtainStyledAttributes2.getResourceId(0, 0)) == 0) {
                        textAppearance = null;
                    } else {
                        textAppearance = new TextAppearance(context2, resourceId);
                    }
                    if (textAppearance != null && obtainStyledAttributes2.hasValue(1)) {
                        textAppearance.textColor = MaterialResources.getColorStateList(tooltipDrawable2.context, obtainStyledAttributes2, 1);
                    }
                    tooltipDrawable2.textDrawableHelper.setTextAppearance(textAppearance, tooltipDrawable2.context);
                    tooltipDrawable2.setFillColor(ColorStateList.valueOf(obtainStyledAttributes2.getColor(7, ColorUtils.compositeColors(ColorUtils.setAlphaComponent(MaterialAttributes.resolveOrThrow(tooltipDrawable2.context, C1777R.attr.colorOnBackground, TooltipDrawable.class.getCanonicalName()), 153), ColorUtils.setAlphaComponent(MaterialAttributes.resolveOrThrow(tooltipDrawable2.context, 16842801, TooltipDrawable.class.getCanonicalName()), 229)))));
                    tooltipDrawable2.setStrokeColor(ColorStateList.valueOf(MaterialAttributes.resolveOrThrow(tooltipDrawable2.context, C1777R.attr.colorSurface, TooltipDrawable.class.getCanonicalName())));
                    tooltipDrawable2.padding = obtainStyledAttributes2.getDimensionPixelSize(2, 0);
                    tooltipDrawable2.minWidth = obtainStyledAttributes2.getDimensionPixelSize(4, 0);
                    tooltipDrawable2.minHeight = obtainStyledAttributes2.getDimensionPixelSize(5, 0);
                    tooltipDrawable2.layoutMargin = obtainStyledAttributes2.getDimensionPixelSize(3, 0);
                    obtainStyledAttributes2.recycle();
                    obtainStyledAttributes.recycle();
                    this.labels.add(tooltipDrawable2);
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isAttachedToWindow(this) && (contentView = ViewUtils.getContentView(this)) != null) {
                        int[] iArr = new int[2];
                        contentView.getLocationOnScreen(iArr);
                        tooltipDrawable2.locationOnScreenX = iArr[0];
                        contentView.getWindowVisibleDisplayFrame(tooltipDrawable2.displayFrame);
                        contentView.addOnLayoutChangeListener(tooltipDrawable2.attachedViewLayoutChangeListener);
                    }
                }
                if (this.labels.size() == 1) {
                    i = 0;
                }
                Iterator it = this.labels.iterator();
                while (it.hasNext()) {
                    TooltipDrawable tooltipDrawable3 = (TooltipDrawable) it.next();
                    Objects.requireNonNull(tooltipDrawable3);
                    tooltipDrawable3.drawableState.strokeWidth = (float) i;
                    tooltipDrawable3.invalidateSelf();
                }
                Iterator it2 = this.changeListeners.iterator();
                while (it2.hasNext()) {
                    BaseOnChangeListener baseOnChangeListener = (BaseOnChangeListener) it2.next();
                    Iterator<Float> it3 = this.values.iterator();
                    while (it3.hasNext()) {
                        it3.next().floatValue();
                        baseOnChangeListener.onValueChange();
                    }
                }
                postInvalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    public final void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int normalizeValue = (int) ((normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) this.trackWidth)) + ((float) this.trackSidePadding));
                int calculateTop = calculateTop();
                int i = this.haloRadius;
                background.setHotspotBounds(normalizeValue - i, calculateTop - i, normalizeValue + i, calculateTop + i);
            }
        }
    }

    public void forceDrawCompatHalo(boolean z) {
        this.forceDrawCompatHalo = z;
    }

    static {
        Class<BaseSlider> cls = BaseSlider.class;
    }
}
