package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    public static final C20235 HEIGHT = new Property<View, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            return Float.valueOf((float) ((View) obj).getLayoutParams().height);
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            view.getLayoutParams().height = ((Float) obj2).intValue();
            view.requestLayout();
        }
    };
    public static final C20257 PADDING_END = new Property<View, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return Float.valueOf((float) ViewCompat.Api17Impl.getPaddingEnd((View) obj));
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(view, ViewCompat.Api17Impl.getPaddingStart(view), view.getPaddingTop(), ((Float) obj2).intValue(), view.getPaddingBottom());
        }
    };
    public static final C20246 PADDING_START = new Property<View, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return Float.valueOf((float) ViewCompat.Api17Impl.getPaddingStart((View) obj));
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            int intValue = ((Float) obj2).intValue();
            int paddingTop = view.getPaddingTop();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(view, intValue, paddingTop, ViewCompat.Api17Impl.getPaddingEnd(view), view.getPaddingBottom());
        }
    };
    public static final C20224 WIDTH = new Property<View, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            return Float.valueOf((float) ((View) obj).getLayoutParams().width);
        }

        public final void set(Object obj, Object obj2) {
            View view = (View) obj;
            view.getLayoutParams().width = ((Float) obj2).intValue();
            view.requestLayout();
        }
    };
    public int animState;
    public final ExtendedFloatingActionButtonBehavior behavior;
    public final int collapsedSize;
    public final ChangeSizeStrategy extendStrategy;
    public int extendedPaddingEnd;
    public int extendedPaddingStart;
    public final HideStrategy hideStrategy;
    public boolean isExtended;
    public boolean isTransforming;
    public ColorStateList originalTextCsl;
    public final ShowStrategy showStrategy;
    public final ChangeSizeStrategy shrinkStrategy;

    public class ChangeSizeStrategy extends BaseMotionStrategy {
        public final boolean extending;
        public final Size size;

        public final void onChange() {
        }

        public ChangeSizeStrategy(CodedOutputByteBufferNano codedOutputByteBufferNano, Size size2, boolean z) {
            super(ExtendedFloatingActionButton.this, codedOutputByteBufferNano);
            this.size = size2;
            this.extending = z;
        }

        public final AnimatorSet createAnimator() {
            float f;
            MotionSpec motionSpec = this.motionSpec;
            if (motionSpec == null) {
                if (this.defaultMotionSpec == null) {
                    this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
                }
                motionSpec = this.defaultMotionSpec;
                Objects.requireNonNull(motionSpec);
            }
            if (motionSpec.hasPropertyValues("width")) {
                PropertyValuesHolder[] propertyValues = motionSpec.getPropertyValues("width");
                propertyValues[0].setFloatValues(new float[]{(float) ExtendedFloatingActionButton.this.getWidth(), (float) this.size.getWidth()});
                motionSpec.setPropertyValues("width", propertyValues);
            }
            if (motionSpec.hasPropertyValues("height")) {
                PropertyValuesHolder[] propertyValues2 = motionSpec.getPropertyValues("height");
                propertyValues2[0].setFloatValues(new float[]{(float) ExtendedFloatingActionButton.this.getHeight(), (float) this.size.getHeight()});
                motionSpec.setPropertyValues("height", propertyValues2);
            }
            if (motionSpec.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] propertyValues3 = motionSpec.getPropertyValues("paddingStart");
                PropertyValuesHolder propertyValuesHolder = propertyValues3[0];
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder.setFloatValues(new float[]{(float) ViewCompat.Api17Impl.getPaddingStart(extendedFloatingActionButton), (float) this.size.getPaddingStart()});
                motionSpec.setPropertyValues("paddingStart", propertyValues3);
            }
            if (motionSpec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] propertyValues4 = motionSpec.getPropertyValues("paddingEnd");
                PropertyValuesHolder propertyValuesHolder2 = propertyValues4[0];
                ExtendedFloatingActionButton extendedFloatingActionButton2 = ExtendedFloatingActionButton.this;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder2.setFloatValues(new float[]{(float) ViewCompat.Api17Impl.getPaddingEnd(extendedFloatingActionButton2), (float) this.size.getPaddingEnd()});
                motionSpec.setPropertyValues("paddingEnd", propertyValues4);
            }
            if (motionSpec.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] propertyValues5 = motionSpec.getPropertyValues("labelOpacity");
                boolean z = this.extending;
                float f2 = 0.0f;
                if (z) {
                    f = 0.0f;
                } else {
                    f = 1.0f;
                }
                if (z) {
                    f2 = 1.0f;
                }
                propertyValues5[0].setFloatValues(new float[]{f, f2});
                motionSpec.setPropertyValues("labelOpacity", propertyValues5);
            }
            return createAnimator(motionSpec);
        }

        public final int getDefaultMotionSpecResource() {
            if (this.extending) {
                return C1777R.animator.mtrl_extended_fab_change_size_expand_motion_spec;
            }
            return C1777R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        public final void onAnimationEnd() {
            CodedOutputByteBufferNano codedOutputByteBufferNano = this.tracker;
            Objects.requireNonNull(codedOutputByteBufferNano);
            codedOutputByteBufferNano.buffer = null;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isTransforming = false;
            extendedFloatingActionButton.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
            }
        }

        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = this.extending;
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
                ExtendedFloatingActionButton extendedFloatingActionButton2 = ExtendedFloatingActionButton.this;
                int paddingStart = this.size.getPaddingStart();
                int paddingTop = ExtendedFloatingActionButton.this.getPaddingTop();
                int paddingEnd = this.size.getPaddingEnd();
                int paddingBottom = ExtendedFloatingActionButton.this.getPaddingBottom();
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(extendedFloatingActionButton2, paddingStart, paddingTop, paddingEnd, paddingBottom);
                ExtendedFloatingActionButton.this.requestLayout();
            }
        }

        public final boolean shouldCancel() {
            boolean z = this.extending;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (z != extendedFloatingActionButton.isExtended) {
                Objects.requireNonNull(extendedFloatingActionButton);
                if (extendedFloatingActionButton.icon == null || TextUtils.isEmpty(ExtendedFloatingActionButton.this.getText())) {
                    return true;
                }
                return false;
            }
            return true;
        }

        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = this.extending;
            extendedFloatingActionButton.isTransforming = true;
            extendedFloatingActionButton.setHorizontallyScrolling(true);
        }
    }

    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior<T> {
        public boolean autoHideEnabled;
        public boolean autoShrinkEnabled;
        public Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = true;
        }

        public void setInternalAutoHideCallback(OnChangedCallback onChangedCallback) {
        }

        public void setInternalAutoShrinkCallback(OnChangedCallback onChangedCallback) {
        }

        public final /* bridge */ /* synthetic */ boolean getInsetDodgeRect(View view, Rect rect) {
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            return false;
        }

        public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            boolean z;
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            if (view2 instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton);
            } else {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                    CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) layoutParams;
                    Objects.requireNonNull(layoutParams2);
                    z = layoutParams2.mBehavior instanceof BottomSheetBehavior;
                } else {
                    z = false;
                }
                if (z) {
                    updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton);
                }
            }
            return false;
        }

        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            boolean z;
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            List<View> dependencies = coordinatorLayout.getDependencies(extendedFloatingActionButton);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view2 = dependencies.get(i2);
                if (!(view2 instanceof AppBarLayout)) {
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) layoutParams;
                        Objects.requireNonNull(layoutParams2);
                        z = layoutParams2.mBehavior instanceof BottomSheetBehavior;
                    } else {
                        z = false;
                    }
                    if (z && updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton)) {
                        break;
                    }
                } else if (updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton)) {
                    break;
                }
            }
            coordinatorLayout.onLayoutChild(extendedFloatingActionButton, i);
            return true;
        }

        public final boolean shouldUpdateVisibility(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams();
            if (!this.autoHideEnabled && !this.autoShrinkEnabled) {
                return false;
            }
            Objects.requireNonNull(layoutParams);
            if (layoutParams.mAnchorId != view.getId()) {
                return false;
            }
            return true;
        }

        public final boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, ExtendedFloatingActionButton extendedFloatingActionButton) {
            BaseMotionStrategy baseMotionStrategy;
            BaseMotionStrategy baseMotionStrategy2;
            if (!shouldUpdateVisibility(appBarLayout, extendedFloatingActionButton)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                if (this.autoShrinkEnabled) {
                    baseMotionStrategy2 = extendedFloatingActionButton.shrinkStrategy;
                } else {
                    baseMotionStrategy2 = extendedFloatingActionButton.hideStrategy;
                }
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, baseMotionStrategy2);
                return true;
            }
            if (this.autoShrinkEnabled) {
                baseMotionStrategy = extendedFloatingActionButton.extendStrategy;
            } else {
                baseMotionStrategy = extendedFloatingActionButton.showStrategy;
            }
            ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, baseMotionStrategy);
            return true;
        }

        public final boolean updateFabVisibilityForBottomSheet(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            BaseMotionStrategy baseMotionStrategy;
            BaseMotionStrategy baseMotionStrategy2;
            if (!shouldUpdateVisibility(view, extendedFloatingActionButton)) {
                return false;
            }
            if (view.getTop() < (extendedFloatingActionButton.getHeight() / 2) + ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams()).topMargin) {
                if (this.autoShrinkEnabled) {
                    baseMotionStrategy2 = extendedFloatingActionButton.shrinkStrategy;
                } else {
                    baseMotionStrategy2 = extendedFloatingActionButton.hideStrategy;
                }
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, baseMotionStrategy2);
                return true;
            }
            if (this.autoShrinkEnabled) {
                baseMotionStrategy = extendedFloatingActionButton.extendStrategy;
            } else {
                baseMotionStrategy = extendedFloatingActionButton.showStrategy;
            }
            ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, baseMotionStrategy);
            return true;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = obtainStyledAttributes.getBoolean(0, false);
            this.autoShrinkEnabled = obtainStyledAttributes.getBoolean(1, true);
            obtainStyledAttributes.recycle();
        }
    }

    public class HideStrategy extends BaseMotionStrategy {
        public boolean isCancelled;

        public final int getDefaultMotionSpecResource() {
            return C1777R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        public final void onChange() {
        }

        public HideStrategy(CodedOutputByteBufferNano codedOutputByteBufferNano) {
            super(ExtendedFloatingActionButton.this, codedOutputByteBufferNano);
        }

        public final void onAnimationEnd() {
            CodedOutputByteBufferNano codedOutputByteBufferNano = this.tracker;
            Objects.requireNonNull(codedOutputByteBufferNano);
            codedOutputByteBufferNano.buffer = null;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.animState = 0;
            if (!this.isCancelled) {
                extendedFloatingActionButton.setVisibility(8);
            }
        }

        public final void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        public final boolean shouldCancel() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            C20224 r0 = ExtendedFloatingActionButton.WIDTH;
            Objects.requireNonNull(extendedFloatingActionButton);
            if (extendedFloatingActionButton.getVisibility() == 0) {
                if (extendedFloatingActionButton.animState != 1) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 2) {
                return false;
            }
            return true;
        }

        public final void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.isCancelled = false;
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 1;
        }
    }

    public static abstract class OnChangedCallback {
    }

    public class ShowStrategy extends BaseMotionStrategy {
        public final int getDefaultMotionSpecResource() {
            return C1777R.animator.mtrl_extended_fab_show_motion_spec;
        }

        public final void onChange() {
        }

        public ShowStrategy(CodedOutputByteBufferNano codedOutputByteBufferNano) {
            super(ExtendedFloatingActionButton.this, codedOutputByteBufferNano);
        }

        public final void onAnimationEnd() {
            CodedOutputByteBufferNano codedOutputByteBufferNano = this.tracker;
            Objects.requireNonNull(codedOutputByteBufferNano);
            codedOutputByteBufferNano.buffer = null;
            ExtendedFloatingActionButton.this.animState = 0;
        }

        public final void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.setAlpha(1.0f);
            ExtendedFloatingActionButton.this.setScaleY(1.0f);
            ExtendedFloatingActionButton.this.setScaleX(1.0f);
        }

        public final boolean shouldCancel() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            C20224 r0 = ExtendedFloatingActionButton.WIDTH;
            Objects.requireNonNull(extendedFloatingActionButton);
            if (extendedFloatingActionButton.getVisibility() != 0) {
                if (extendedFloatingActionButton.animState != 2) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 1) {
                return false;
            }
            return true;
        }

        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 2;
        }
    }

    public interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void setTextColor(int i) {
        super.setTextColor(i);
        this.originalTextCsl = getTextColors();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExtendedFloatingActionButton(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = 2130969036(0x7f0401cc, float:1.7546743E38)
            r9 = 2132018661(0x7f1405e5, float:1.9675635E38)
            r1 = r17
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r1, r7, r8)
            r10 = 0
            r0.animState = r10
            com.android.framework.protobuf.nano.CodedOutputByteBufferNano r1 = new com.android.framework.protobuf.nano.CodedOutputByteBufferNano
            r1.<init>()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ShowStrategy r11 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ShowStrategy
            r11.<init>(r1)
            r0.showStrategy = r11
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$HideStrategy r12 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$HideStrategy
            r12.<init>(r1)
            r0.hideStrategy = r12
            r13 = 1
            r0.isExtended = r13
            r0.isTransforming = r10
            android.content.Context r14 = r16.getContext()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ExtendedFloatingActionButtonBehavior r1 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ExtendedFloatingActionButtonBehavior
            r1.<init>(r14, r7)
            r0.behavior = r1
            int[] r3 = com.google.android.material.R$styleable.ExtendedFloatingActionButton
            int[] r6 = new int[r10]
            r4 = 2130969036(0x7f0401cc, float:1.7546743E38)
            r5 = 2132018661(0x7f1405e5, float:1.9675635E38)
            r1 = r14
            r2 = r18
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 4
            com.google.android.material.animation.MotionSpec r2 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r2)
            r3 = 3
            com.google.android.material.animation.MotionSpec r3 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r3)
            r4 = 2
            com.google.android.material.animation.MotionSpec r4 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r4)
            r5 = 5
            com.google.android.material.animation.MotionSpec r5 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r5)
            r6 = -1
            int r6 = r1.getDimensionPixelSize(r10, r6)
            r0.collapsedSize = r6
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r6 = androidx.core.view.ViewCompat.Api17Impl.getPaddingStart(r16)
            r0.extendedPaddingStart = r6
            int r6 = androidx.core.view.ViewCompat.Api17Impl.getPaddingEnd(r16)
            r0.extendedPaddingEnd = r6
            com.android.framework.protobuf.nano.CodedOutputByteBufferNano r6 = new com.android.framework.protobuf.nano.CodedOutputByteBufferNano
            r6.<init>()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy r15 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$1 r8 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$1
            r8.<init>()
            r15.<init>(r6, r8, r13)
            r0.extendStrategy = r15
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy r8 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$2 r13 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$2
            r13.<init>()
            r8.<init>(r6, r13, r10)
            r0.shrinkStrategy = r8
            r11.motionSpec = r2
            r12.motionSpec = r3
            r15.motionSpec = r4
            r8.motionSpec = r5
            r1.recycle()
            com.google.android.material.shape.RelativeCornerSize r1 = com.google.android.material.shape.ShapeAppearanceModel.PILL
            r2 = 2130969036(0x7f0401cc, float:1.7546743E38)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r1 = com.google.android.material.shape.ShapeAppearanceModel.builder(r14, r7, r2, r9, r1)
            com.google.android.material.shape.ShapeAppearanceModel r2 = new com.google.android.material.shape.ShapeAppearanceModel
            r2.<init>(r1)
            r0.setShapeAppearanceModel(r2)
            android.content.res.ColorStateList r1 = r16.getTextColors()
            r0.originalTextCsl = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public int getCollapsedSize() {
        int i = this.collapsedSize;
        if (i >= 0) {
            return i;
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return (Math.min(ViewCompat.Api17Impl.getPaddingStart(this), ViewCompat.Api17Impl.getPaddingEnd(this)) * 2) + this.iconSize;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void access$400(com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton r2, final com.google.android.material.floatingactionbutton.BaseMotionStrategy r3) {
        /*
            boolean r0 = r3.shouldCancel()
            if (r0 == 0) goto L_0x0007
            goto L_0x004e
        L_0x0007:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r0 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r2)
            r1 = 0
            if (r0 != 0) goto L_0x0014
            r2.getVisibility()
            goto L_0x001c
        L_0x0014:
            boolean r0 = r2.isInEditMode()
            if (r0 != 0) goto L_0x001c
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = r1
        L_0x001d:
            if (r0 != 0) goto L_0x0026
            r3.performNow()
            r3.onChange()
            goto L_0x004e
        L_0x0026:
            r2.measure(r1, r1)
            android.animation.AnimatorSet r2 = r3.createAnimator()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$3 r0 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$3
            r0.<init>(r3)
            r2.addListener(r0)
            java.util.ArrayList<android.animation.Animator$AnimatorListener> r3 = r3.listeners
            java.util.Iterator r3 = r3.iterator()
        L_0x003b:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x004b
            java.lang.Object r0 = r3.next()
            android.animation.Animator$AnimatorListener r0 = (android.animation.Animator.AnimatorListener) r0
            r2.addListener(r0)
            goto L_0x003b
        L_0x004b:
            r2.start()
        L_0x004e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.access$400(com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton, com.google.android.material.floatingactionbutton.BaseMotionStrategy):void");
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isExtended && TextUtils.isEmpty(getText()) && this.icon != null) {
            this.isExtended = false;
            this.shrinkStrategy.performNow();
        }
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        if (this.isExtended && !this.isTransforming) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.extendedPaddingStart = ViewCompat.Api17Impl.getPaddingStart(this);
            this.extendedPaddingEnd = ViewCompat.Api17Impl.getPaddingEnd(this);
        }
    }

    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        if (this.isExtended && !this.isTransforming) {
            this.extendedPaddingStart = i;
            this.extendedPaddingEnd = i3;
        }
    }

    public final void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        this.originalTextCsl = getTextColors();
    }

    public final void silentlyUpdateTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
    }

    public final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.behavior;
    }
}
