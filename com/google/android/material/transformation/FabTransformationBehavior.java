package com.google.android.material.transformation;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.expandable.ExpandableWidgetHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

@Deprecated
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    public float dependencyOriginalTranslationX;
    public float dependencyOriginalTranslationY;
    public final int[] tmpArray = new int[2];
    public final Rect tmpRect = new Rect();
    public final RectF tmpRectF1 = new RectF();
    public final RectF tmpRectF2 = new RectF();

    public static class FabTransformationSpec {
        public Positioning positioning;
        public MotionSpec timings;
    }

    public FabTransformationBehavior() {
    }

    public static Pair calculateMotionTiming(float f, float f2, boolean z, FabTransformationSpec fabTransformationSpec) {
        MotionTiming motionTiming;
        MotionTiming motionTiming2;
        int i;
        if (f == 0.0f || f2 == 0.0f) {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXLinear");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYLinear");
        } else if ((!z || f2 >= 0.0f) && (z || i <= 0)) {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXCurveDownwards");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYCurveDownwards");
        } else {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXCurveUpwards");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYCurveUpwards");
        }
        return new Pair(motionTiming2, motionTiming);
    }

    public abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);

    public final float calculateTranslationX(View view, View view2, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        Objects.requireNonNull(positioning);
        return (rectF2.centerX() - rectF.centerX()) + 0.0f;
    }

    public final float calculateTranslationY(View view, View view2, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
        calculateWindowBounds(view2, rectF2);
        Objects.requireNonNull(positioning);
        return (rectF2.centerY() - rectF.centerY()) + 0.0f;
    }

    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    /* JADX WARNING: type inference failed for: r0v11, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x03d9 A[LOOP:1: B:100:0x03d7->B:101:0x03d9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0198  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x02fb  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0354  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.animation.AnimatorSet onCreateExpandedStateChangeAnimation(android.view.View r27, android.view.View r28, boolean r29, boolean r30) {
        /*
            r26 = this;
            r0 = r26
            r1 = r27
            r2 = r28
            r3 = r29
            android.content.Context r4 = r28.getContext()
            com.google.android.material.transformation.FabTransformationBehavior$FabTransformationSpec r4 = r0.onCreateMotionSpec(r4, r3)
            if (r3 == 0) goto L_0x001e
            float r5 = r27.getTranslationX()
            r0.dependencyOriginalTranslationX = r5
            float r5 = r27.getTranslationY()
            r0.dependencyOriginalTranslationY = r5
        L_0x001e:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r7 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            float r7 = androidx.core.view.ViewCompat.Api21Impl.getElevation(r28)
            float r8 = androidx.core.view.ViewCompat.Api21Impl.getElevation(r27)
            float r7 = r7 - r8
            r8 = 0
            r9 = 1
            r10 = 0
            if (r3 == 0) goto L_0x0049
            if (r30 != 0) goto L_0x003e
            float r7 = -r7
            r2.setTranslationZ(r7)
        L_0x003e:
            android.util.Property r7 = android.view.View.TRANSLATION_Z
            float[] r11 = new float[r9]
            r11[r10] = r8
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r2, r7, r11)
            goto L_0x0054
        L_0x0049:
            android.util.Property r11 = android.view.View.TRANSLATION_Z
            float[] r12 = new float[r9]
            float r7 = -r7
            r12[r10] = r7
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r2, r11, r12)
        L_0x0054:
            com.google.android.material.animation.MotionSpec r11 = r4.timings
            java.lang.String r12 = "elevation"
            com.google.android.material.animation.MotionTiming r11 = r11.getTiming(r12)
            r11.apply(r7)
            r5.add(r7)
            android.graphics.RectF r7 = r0.tmpRectF1
            com.google.android.material.animation.Positioning r11 = r4.positioning
            float r11 = r0.calculateTranslationX(r1, r2, r11)
            com.google.android.material.animation.Positioning r12 = r4.positioning
            float r12 = r0.calculateTranslationY(r1, r2, r12)
            android.util.Pair r13 = calculateMotionTiming(r11, r12, r3, r4)
            java.lang.Object r14 = r13.first
            com.google.android.material.animation.MotionTiming r14 = (com.google.android.material.animation.MotionTiming) r14
            java.lang.Object r13 = r13.second
            com.google.android.material.animation.MotionTiming r13 = (com.google.android.material.animation.MotionTiming) r13
            if (r3 == 0) goto L_0x00c6
            if (r30 != 0) goto L_0x0088
            float r15 = -r11
            r2.setTranslationX(r15)
            float r15 = -r12
            r2.setTranslationY(r15)
        L_0x0088:
            android.util.Property r15 = android.view.View.TRANSLATION_X
            r16 = r6
            float[] r6 = new float[r9]
            r6[r10] = r8
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofFloat(r2, r15, r6)
            android.util.Property r15 = android.view.View.TRANSLATION_Y
            r17 = r6
            float[] r6 = new float[r9]
            r6[r10] = r8
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofFloat(r2, r15, r6)
            float r11 = -r11
            float r12 = -r12
            float r11 = calculateValueOfAnimationAtEndOfExpansion(r4, r14, r11)
            float r12 = calculateValueOfAnimationAtEndOfExpansion(r4, r13, r12)
            android.graphics.Rect r15 = r0.tmpRect
            r2.getWindowVisibleDisplayFrame(r15)
            android.graphics.RectF r8 = r0.tmpRectF1
            r8.set(r15)
            android.graphics.RectF r15 = r0.tmpRectF2
            r0.calculateWindowBounds(r2, r15)
            r15.offset(r11, r12)
            r15.intersect(r8)
            r7.set(r15)
            r8 = r6
            r6 = r17
            goto L_0x00de
        L_0x00c6:
            r16 = r6
            android.util.Property r6 = android.view.View.TRANSLATION_X
            float[] r8 = new float[r9]
            float r11 = -r11
            r8[r10] = r11
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofFloat(r2, r6, r8)
            android.util.Property r8 = android.view.View.TRANSLATION_Y
            float[] r11 = new float[r9]
            float r12 = -r12
            r11[r10] = r12
            android.animation.ObjectAnimator r8 = android.animation.ObjectAnimator.ofFloat(r2, r8, r11)
        L_0x00de:
            r14.apply(r6)
            r13.apply(r8)
            r5.add(r6)
            r5.add(r8)
            float r6 = r7.width()
            float r7 = r7.height()
            com.google.android.material.animation.Positioning r8 = r4.positioning
            float r8 = r0.calculateTranslationX(r1, r2, r8)
            com.google.android.material.animation.Positioning r11 = r4.positioning
            float r11 = r0.calculateTranslationY(r1, r2, r11)
            android.util.Pair r12 = calculateMotionTiming(r8, r11, r3, r4)
            java.lang.Object r13 = r12.first
            com.google.android.material.animation.MotionTiming r13 = (com.google.android.material.animation.MotionTiming) r13
            java.lang.Object r12 = r12.second
            com.google.android.material.animation.MotionTiming r12 = (com.google.android.material.animation.MotionTiming) r12
            android.util.Property r14 = android.view.View.TRANSLATION_X
            float[] r15 = new float[r9]
            if (r3 == 0) goto L_0x0111
            goto L_0x0113
        L_0x0111:
            float r8 = r0.dependencyOriginalTranslationX
        L_0x0113:
            r15[r10] = r8
            android.animation.ObjectAnimator r8 = android.animation.ObjectAnimator.ofFloat(r1, r14, r15)
            android.util.Property r14 = android.view.View.TRANSLATION_Y
            float[] r15 = new float[r9]
            if (r3 == 0) goto L_0x0120
            goto L_0x0122
        L_0x0120:
            float r11 = r0.dependencyOriginalTranslationY
        L_0x0122:
            r15[r10] = r11
            android.animation.ObjectAnimator r11 = android.animation.ObjectAnimator.ofFloat(r1, r14, r15)
            r13.apply(r8)
            r12.apply(r11)
            r5.add(r8)
            r5.add(r11)
            boolean r8 = r2 instanceof com.google.android.material.circularreveal.CircularRevealWidget
            if (r8 == 0) goto L_0x018c
            boolean r11 = r1 instanceof android.widget.ImageView
            if (r11 != 0) goto L_0x013d
            goto L_0x018c
        L_0x013d:
            r11 = r2
            com.google.android.material.circularreveal.CircularRevealWidget r11 = (com.google.android.material.circularreveal.CircularRevealWidget) r11
            r12 = r1
            android.widget.ImageView r12 = (android.widget.ImageView) r12
            android.graphics.drawable.Drawable r12 = r12.getDrawable()
            if (r12 != 0) goto L_0x014a
            goto L_0x018c
        L_0x014a:
            r12.mutate()
            r13 = 255(0xff, float:3.57E-43)
            if (r3 == 0) goto L_0x0161
            if (r30 != 0) goto L_0x0156
            r12.setAlpha(r13)
        L_0x0156:
            com.google.android.material.animation.DrawableAlphaProperty r13 = com.google.android.material.animation.DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT
            int[] r14 = new int[r9]
            r14[r10] = r10
            android.animation.ObjectAnimator r13 = android.animation.ObjectAnimator.ofInt(r12, r13, r14)
            goto L_0x016b
        L_0x0161:
            com.google.android.material.animation.DrawableAlphaProperty r14 = com.google.android.material.animation.DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT
            int[] r15 = new int[r9]
            r15[r10] = r13
            android.animation.ObjectAnimator r13 = android.animation.ObjectAnimator.ofInt(r12, r14, r15)
        L_0x016b:
            com.google.android.material.transformation.FabTransformationBehavior$2 r14 = new com.google.android.material.transformation.FabTransformationBehavior$2
            r14.<init>(r2)
            r13.addUpdateListener(r14)
            com.google.android.material.animation.MotionSpec r14 = r4.timings
            java.lang.String r15 = "iconFade"
            com.google.android.material.animation.MotionTiming r14 = r14.getTiming(r15)
            r14.apply(r13)
            r5.add(r13)
            com.google.android.material.transformation.FabTransformationBehavior$3 r13 = new com.google.android.material.transformation.FabTransformationBehavior$3
            r13.<init>(r12)
            r11 = r16
            r11.add(r13)
            goto L_0x018e
        L_0x018c:
            r11 = r16
        L_0x018e:
            if (r8 != 0) goto L_0x0198
            r21 = r4
            r1 = r5
            r18 = r8
            r3 = r11
            goto L_0x02f4
        L_0x0198:
            r12 = r2
            com.google.android.material.circularreveal.CircularRevealWidget r12 = (com.google.android.material.circularreveal.CircularRevealWidget) r12
            com.google.android.material.animation.Positioning r13 = r4.positioning
            android.graphics.RectF r14 = r0.tmpRectF1
            android.graphics.RectF r15 = r0.tmpRectF2
            r0.calculateWindowBounds(r1, r14)
            float r9 = r0.dependencyOriginalTranslationX
            float r10 = r0.dependencyOriginalTranslationY
            r14.offset(r9, r10)
            r0.calculateWindowBounds(r2, r15)
            float r9 = r0.calculateTranslationX(r1, r2, r13)
            float r9 = -r9
            r10 = 0
            r15.offset(r9, r10)
            float r9 = r14.centerX()
            float r10 = r15.left
            float r9 = r9 - r10
            com.google.android.material.animation.Positioning r10 = r4.positioning
            android.graphics.RectF r13 = r0.tmpRectF1
            android.graphics.RectF r14 = r0.tmpRectF2
            r0.calculateWindowBounds(r1, r13)
            float r15 = r0.dependencyOriginalTranslationX
            r18 = r8
            float r8 = r0.dependencyOriginalTranslationY
            r13.offset(r15, r8)
            r0.calculateWindowBounds(r2, r14)
            float r8 = r0.calculateTranslationY(r1, r2, r10)
            float r8 = -r8
            r10 = 0
            r14.offset(r10, r8)
            float r8 = r13.centerY()
            float r10 = r14.top
            float r8 = r8 - r10
            r10 = r1
            com.google.android.material.floatingactionbutton.FloatingActionButton r10 = (com.google.android.material.floatingactionbutton.FloatingActionButton) r10
            android.graphics.Rect r13 = r0.tmpRect
            boolean r14 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r10)
            if (r14 == 0) goto L_0x01fd
            int r14 = r10.getWidth()
            int r15 = r10.getHeight()
            r1 = 0
            r13.set(r1, r1, r14, r15)
            r10.offsetRectWithShadow(r13)
        L_0x01fd:
            android.graphics.Rect r0 = r0.tmpRect
            int r0 = r0.width()
            float r0 = (float) r0
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            com.google.android.material.animation.MotionSpec r1 = r4.timings
            java.lang.String r10 = "expansion"
            com.google.android.material.animation.MotionTiming r1 = r1.getTiming(r10)
            if (r3 == 0) goto L_0x0255
            if (r30 != 0) goto L_0x021b
            com.google.android.material.circularreveal.CircularRevealWidget$RevealInfo r10 = new com.google.android.material.circularreveal.CircularRevealWidget$RevealInfo
            r10.<init>(r9, r8, r0)
            r12.setRevealInfo(r10)
        L_0x021b:
            if (r30 == 0) goto L_0x0223
            com.google.android.material.circularreveal.CircularRevealWidget$RevealInfo r0 = r12.getRevealInfo()
            float r0 = r0.radius
        L_0x0223:
            float r6 = androidx.startup.R$string.distanceToFurthestCorner(r9, r8, r6, r7)
            android.animation.AnimatorSet r6 = com.google.android.material.circularreveal.CircularRevealCompat.createCircularReveal(r12, r9, r8, r6)
            com.google.android.material.transformation.FabTransformationBehavior$4 r7 = new com.google.android.material.transformation.FabTransformationBehavior$4
            r7.<init>()
            r6.addListener(r7)
            java.util.Objects.requireNonNull(r1)
            long r13 = r1.delay
            int r7 = (int) r9
            int r8 = (int) r8
            r9 = 0
            int r15 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
            if (r15 <= 0) goto L_0x024d
            android.animation.Animator r0 = android.view.ViewAnimationUtils.createCircularReveal(r2, r7, r8, r0, r0)
            r0.setStartDelay(r9)
            r0.setDuration(r13)
            r5.add(r0)
        L_0x024d:
            r0 = r1
            r21 = r4
            r1 = r5
            r19 = r11
            goto L_0x02e4
        L_0x0255:
            com.google.android.material.circularreveal.CircularRevealWidget$RevealInfo r6 = r12.getRevealInfo()
            float r6 = r6.radius
            android.animation.AnimatorSet r7 = com.google.android.material.circularreveal.CircularRevealCompat.createCircularReveal(r12, r9, r8, r0)
            java.util.Objects.requireNonNull(r1)
            long r13 = r1.delay
            int r9 = (int) r9
            int r8 = (int) r8
            r15 = r11
            r10 = 0
            int r19 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r19 <= 0) goto L_0x027a
            android.animation.Animator r6 = android.view.ViewAnimationUtils.createCircularReveal(r2, r9, r8, r6, r6)
            r6.setStartDelay(r10)
            r6.setDuration(r13)
            r5.add(r6)
        L_0x027a:
            long r13 = r1.delay
            long r10 = r1.duration
            com.google.android.material.animation.MotionSpec r6 = r4.timings
            java.util.Objects.requireNonNull(r6)
            r26 = r7
            androidx.collection.SimpleArrayMap<java.lang.String, com.google.android.material.animation.MotionTiming> r7 = r6.timings
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mSize
            r21 = r4
            r19 = r15
            r3 = 0
            r15 = 0
        L_0x0293:
            if (r15 >= r7) goto L_0x02c0
            r20 = r7
            androidx.collection.SimpleArrayMap<java.lang.String, com.google.android.material.animation.MotionTiming> r7 = r6.timings
            java.lang.Object r7 = r7.valueAt(r15)
            com.google.android.material.animation.MotionTiming r7 = (com.google.android.material.animation.MotionTiming) r7
            java.util.Objects.requireNonNull(r7)
            r22 = r5
            r23 = r6
            long r5 = r7.delay
            r25 = r0
            r24 = r1
            long r0 = r7.duration
            long r5 = r5 + r0
            long r3 = java.lang.Math.max(r3, r5)
            int r15 = r15 + 1
            r7 = r20
            r5 = r22
            r6 = r23
            r1 = r24
            r0 = r25
            goto L_0x0293
        L_0x02c0:
            r25 = r0
            r24 = r1
            r22 = r5
            long r13 = r13 + r10
            int r0 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x02de
            r0 = r25
            android.animation.Animator r0 = android.view.ViewAnimationUtils.createCircularReveal(r2, r9, r8, r0, r0)
            r0.setStartDelay(r13)
            long r3 = r3 - r13
            r0.setDuration(r3)
            r1 = r22
            r1.add(r0)
            goto L_0x02e0
        L_0x02de:
            r1 = r22
        L_0x02e0:
            r6 = r26
            r0 = r24
        L_0x02e4:
            r0.apply(r6)
            r1.add(r6)
            com.google.android.material.circularreveal.CircularRevealCompat$1 r0 = new com.google.android.material.circularreveal.CircularRevealCompat$1
            r0.<init>()
            r3 = r19
            r3.add(r0)
        L_0x02f4:
            if (r18 != 0) goto L_0x02fb
            r6 = r29
            r4 = r21
            goto L_0x034f
        L_0x02fb:
            r0 = r2
            com.google.android.material.circularreveal.CircularRevealWidget r0 = (com.google.android.material.circularreveal.CircularRevealWidget) r0
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            android.content.res.ColorStateList r4 = androidx.core.view.ViewCompat.Api21Impl.getBackgroundTintList(r27)
            if (r4 == 0) goto L_0x0313
            int[] r5 = r27.getDrawableState()
            int r6 = r4.getDefaultColor()
            int r4 = r4.getColorForState(r5, r6)
            goto L_0x0314
        L_0x0313:
            r4 = 0
        L_0x0314:
            r5 = 16777215(0xffffff, float:2.3509886E-38)
            r5 = r5 & r4
            r6 = r29
            if (r6 == 0) goto L_0x032e
            if (r30 != 0) goto L_0x0321
            r0.setCircularRevealScrimColor(r4)
        L_0x0321:
            com.google.android.material.circularreveal.CircularRevealWidget$CircularRevealScrimColorProperty r4 = com.google.android.material.circularreveal.CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR
            r7 = 1
            int[] r8 = new int[r7]
            r9 = 0
            r8[r9] = r5
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofInt(r0, r4, r8)
            goto L_0x033a
        L_0x032e:
            r7 = 1
            r9 = 0
            com.google.android.material.circularreveal.CircularRevealWidget$CircularRevealScrimColorProperty r5 = com.google.android.material.circularreveal.CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR
            int[] r8 = new int[r7]
            r8[r9] = r4
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofInt(r0, r5, r8)
        L_0x033a:
            com.google.android.material.animation.ArgbEvaluatorCompat r4 = com.google.android.material.animation.ArgbEvaluatorCompat.instance
            r0.setEvaluator(r4)
            r4 = r21
            com.google.android.material.animation.MotionSpec r5 = r4.timings
            java.lang.String r7 = "color"
            com.google.android.material.animation.MotionTiming r5 = r5.getTiming(r7)
            r5.apply(r0)
            r1.add(r0)
        L_0x034f:
            boolean r0 = r2 instanceof android.view.ViewGroup
            if (r0 != 0) goto L_0x0354
            goto L_0x0386
        L_0x0354:
            r5 = 2131428459(0x7f0b046b, float:1.8478563E38)
            android.view.View r5 = r2.findViewById(r5)
            r7 = 0
            if (r5 == 0) goto L_0x0366
            boolean r0 = r5 instanceof android.view.ViewGroup
            if (r0 == 0) goto L_0x0384
            r7 = r5
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            goto L_0x0384
        L_0x0366:
            boolean r5 = r2 instanceof com.google.android.material.transformation.TransformationChildLayout
            if (r5 != 0) goto L_0x0375
            boolean r5 = r2 instanceof com.google.android.material.transformation.TransformationChildCard
            if (r5 == 0) goto L_0x036f
            goto L_0x0375
        L_0x036f:
            if (r0 == 0) goto L_0x0384
            r7 = r2
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            goto L_0x0384
        L_0x0375:
            r0 = r2
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            r5 = 0
            android.view.View r0 = r0.getChildAt(r5)
            boolean r5 = r0 instanceof android.view.ViewGroup
            if (r5 == 0) goto L_0x0384
            r7 = r0
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
        L_0x0384:
            if (r7 != 0) goto L_0x0388
        L_0x0386:
            r9 = 0
            goto L_0x03c0
        L_0x0388:
            if (r6 == 0) goto L_0x03a5
            if (r30 != 0) goto L_0x0396
            com.google.android.material.animation.ChildrenAlphaProperty r0 = com.google.android.material.animation.ChildrenAlphaProperty.CHILDREN_ALPHA
            r5 = 0
            java.lang.Float r5 = java.lang.Float.valueOf(r5)
            r0.set(r7, r5)
        L_0x0396:
            com.google.android.material.animation.ChildrenAlphaProperty r0 = com.google.android.material.animation.ChildrenAlphaProperty.CHILDREN_ALPHA
            r5 = 1
            float[] r5 = new float[r5]
            r8 = 1065353216(0x3f800000, float:1.0)
            r9 = 0
            r5[r9] = r8
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r7, r0, r5)
            goto L_0x03b2
        L_0x03a5:
            r5 = 1
            r9 = 0
            com.google.android.material.animation.ChildrenAlphaProperty r0 = com.google.android.material.animation.ChildrenAlphaProperty.CHILDREN_ALPHA
            float[] r5 = new float[r5]
            r8 = 0
            r5[r9] = r8
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r7, r0, r5)
        L_0x03b2:
            com.google.android.material.animation.MotionSpec r4 = r4.timings
            java.lang.String r5 = "contentFade"
            com.google.android.material.animation.MotionTiming r4 = r4.getTiming(r5)
            r4.apply(r0)
            r1.add(r0)
        L_0x03c0:
            android.animation.AnimatorSet r0 = new android.animation.AnimatorSet
            r0.<init>()
            androidx.leanback.R$fraction.playTogether(r0, r1)
            com.google.android.material.transformation.FabTransformationBehavior$1 r1 = new com.google.android.material.transformation.FabTransformationBehavior$1
            r4 = r27
            r1.<init>(r6, r2, r4)
            r0.addListener(r1)
            int r1 = r3.size()
            r10 = r9
        L_0x03d7:
            if (r10 >= r1) goto L_0x03e5
            java.lang.Object r2 = r3.get(r10)
            android.animation.Animator$AnimatorListener r2 = (android.animation.Animator.AnimatorListener) r2
            r0.addListener(r2)
            int r10 = r10 + 1
            goto L_0x03d7
        L_0x03e5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.transformation.FabTransformationBehavior.onCreateExpandedStateChangeAnimation(android.view.View, android.view.View, boolean, boolean):android.animation.AnimatorSet");
    }

    public static float calculateValueOfAnimationAtEndOfExpansion(FabTransformationSpec fabTransformationSpec, MotionTiming motionTiming, float f) {
        Objects.requireNonNull(motionTiming);
        long j = motionTiming.delay;
        long j2 = motionTiming.duration;
        MotionTiming timing = fabTransformationSpec.timings.getTiming("expansion");
        Objects.requireNonNull(timing);
        float interpolation = motionTiming.getInterpolator().getInterpolation(((float) (((timing.delay + timing.duration) + 17) - j)) / ((float) j2));
        LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        return MotionController$$ExternalSyntheticOutline0.m7m(0.0f, f, interpolation, f);
    }

    public final void calculateWindowBounds(View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
        int[] iArr = this.tmpArray;
        view.getLocationInWindow(iArr);
        rectF.offsetTo((float) iArr[0], (float) iArr[1]);
        rectF.offset((float) ((int) (-view.getTranslationX())), (float) ((int) (-view.getTranslationY())));
    }

    public final boolean layoutDependsOn(View view, View view2) {
        if (view.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        } else if (!(view2 instanceof FloatingActionButton)) {
            return false;
        } else {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view2;
            Objects.requireNonNull(floatingActionButton);
            ExpandableWidgetHelper expandableWidgetHelper = floatingActionButton.expandableWidgetHelper;
            Objects.requireNonNull(expandableWidgetHelper);
            int i = expandableWidgetHelper.expandedComponentIdHint;
            if (i == 0 || i == view.getId()) {
                return true;
            }
            return false;
        }
    }

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
