package com.airbnb.lottie.animation.content;

import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.Objects;

public final class GradientStrokeContent extends BaseStrokeContent {
    public final RectF boundsRect;
    public final int cacheSteps;
    public final GradientColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public final PointKeyframeAnimation endPointAnimation;
    public final boolean hidden;
    public final LongSparseArray<LinearGradient> linearGradientCache;
    public final String name;
    public final LongSparseArray<RadialGradient> radialGradientCache;
    public final PointKeyframeAnimation startPointAnimation;
    public final GradientType type;

    public final int[] applyDynamicColorsIfNeeded(int[] iArr) {
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            Integer[] numArr = (Integer[]) valueCallbackKeyframeAnimation.getValue();
            int i = 0;
            if (iArr.length == numArr.length) {
                while (i < iArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            } else {
                iArr = new int[numArr.length];
                while (i < numArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            }
        }
        return iArr;
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [android.graphics.Shader] */
    /* JADX WARNING: type inference failed for: r2v21 */
    /* JADX WARNING: type inference failed for: r8v2, types: [android.graphics.RadialGradient] */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: type inference failed for: r8v3, types: [android.graphics.LinearGradient] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void draw(android.graphics.Canvas r17, android.graphics.Matrix r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            boolean r2 = r0.hidden
            if (r2 == 0) goto L_0x0009
            return
        L_0x0009:
            android.graphics.RectF r2 = r0.boundsRect
            r3 = 0
            r0.getBounds(r2, r1, r3)
            com.airbnb.lottie.model.content.GradientType r2 = r0.type
            com.airbnb.lottie.model.content.GradientType r3 = com.airbnb.lottie.model.content.GradientType.LINEAR
            r4 = 0
            if (r2 != r3) goto L_0x0063
            int r2 = r16.getGradientHash()
            androidx.collection.LongSparseArray<android.graphics.LinearGradient> r3 = r0.linearGradientCache
            long r5 = (long) r2
            java.util.Objects.requireNonNull(r3)
            java.lang.Object r2 = r3.get(r5, r4)
            android.graphics.LinearGradient r2 = (android.graphics.LinearGradient) r2
            if (r2 == 0) goto L_0x002a
            goto L_0x00b7
        L_0x002a:
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r2 = r0.startPointAnimation
            java.lang.Object r2 = r2.getValue()
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = r0.endPointAnimation
            java.lang.Object r3 = r3.getValue()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r4 = r0.colorAnimation
            java.lang.Object r4 = r4.getValue()
            com.airbnb.lottie.model.content.GradientColor r4 = (com.airbnb.lottie.model.content.GradientColor) r4
            java.util.Objects.requireNonNull(r4)
            int[] r7 = r4.colors
            int[] r13 = r0.applyDynamicColorsIfNeeded(r7)
            float[] r14 = r4.positions
            float r9 = r2.x
            float r10 = r2.y
            float r11 = r3.x
            float r12 = r3.y
            android.graphics.LinearGradient r2 = new android.graphics.LinearGradient
            android.graphics.Shader$TileMode r15 = android.graphics.Shader.TileMode.CLAMP
            r8 = r2
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            androidx.collection.LongSparseArray<android.graphics.LinearGradient> r3 = r0.linearGradientCache
            r3.put(r5, r2)
            goto L_0x00b7
        L_0x0063:
            int r2 = r16.getGradientHash()
            androidx.collection.LongSparseArray<android.graphics.RadialGradient> r3 = r0.radialGradientCache
            long r5 = (long) r2
            java.util.Objects.requireNonNull(r3)
            java.lang.Object r2 = r3.get(r5, r4)
            android.graphics.RadialGradient r2 = (android.graphics.RadialGradient) r2
            if (r2 == 0) goto L_0x0076
            goto L_0x00b7
        L_0x0076:
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r2 = r0.startPointAnimation
            java.lang.Object r2 = r2.getValue()
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = r0.endPointAnimation
            java.lang.Object r3 = r3.getValue()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r4 = r0.colorAnimation
            java.lang.Object r4 = r4.getValue()
            com.airbnb.lottie.model.content.GradientColor r4 = (com.airbnb.lottie.model.content.GradientColor) r4
            java.util.Objects.requireNonNull(r4)
            int[] r7 = r4.colors
            int[] r12 = r0.applyDynamicColorsIfNeeded(r7)
            float[] r13 = r4.positions
            float r9 = r2.x
            float r10 = r2.y
            float r2 = r3.x
            float r3 = r3.y
            float r2 = r2 - r9
            double r7 = (double) r2
            float r3 = r3 - r10
            double r2 = (double) r3
            double r2 = java.lang.Math.hypot(r7, r2)
            float r11 = (float) r2
            android.graphics.RadialGradient r2 = new android.graphics.RadialGradient
            android.graphics.Shader$TileMode r14 = android.graphics.Shader.TileMode.CLAMP
            r8 = r2
            r8.<init>(r9, r10, r11, r12, r13, r14)
            androidx.collection.LongSparseArray<android.graphics.RadialGradient> r3 = r0.radialGradientCache
            r3.put(r5, r2)
        L_0x00b7:
            r2.setLocalMatrix(r1)
            com.airbnb.lottie.animation.LPaint r3 = r0.paint
            r3.setShader(r2)
            super.draw(r17, r18, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.GradientStrokeContent.draw(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public final int getGradientHash() {
        int i;
        PointKeyframeAnimation pointKeyframeAnimation = this.startPointAnimation;
        Objects.requireNonNull(pointKeyframeAnimation);
        int round = Math.round(pointKeyframeAnimation.progress * ((float) this.cacheSteps));
        PointKeyframeAnimation pointKeyframeAnimation2 = this.endPointAnimation;
        Objects.requireNonNull(pointKeyframeAnimation2);
        int round2 = Math.round(pointKeyframeAnimation2.progress * ((float) this.cacheSteps));
        GradientColorKeyframeAnimation gradientColorKeyframeAnimation = this.colorAnimation;
        Objects.requireNonNull(gradientColorKeyframeAnimation);
        int round3 = Math.round(gradientColorKeyframeAnimation.progress * ((float) this.cacheSteps));
        if (round != 0) {
            i = round * 527;
        } else {
            i = 17;
        }
        if (round2 != 0) {
            i = i * 31 * round2;
        }
        if (round3 != 0) {
            return i * 31 * round3;
        }
        return i;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GradientStrokeContent(com.airbnb.lottie.LottieDrawable r13, com.airbnb.lottie.model.layer.BaseLayer r14, com.airbnb.lottie.model.content.GradientStroke r15) {
        /*
            r12 = this;
            java.util.Objects.requireNonNull(r15)
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType r0 = r15.capType
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.ordinal()
            r1 = 1
            if (r0 == 0) goto L_0x0017
            if (r0 == r1) goto L_0x0014
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.SQUARE
            goto L_0x0019
        L_0x0014:
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.ROUND
            goto L_0x0019
        L_0x0017:
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.BUTT
        L_0x0019:
            r5 = r0
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType r0 = r15.joinType
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.ordinal()
            if (r0 == 0) goto L_0x0032
            if (r0 == r1) goto L_0x002f
            r1 = 2
            if (r0 == r1) goto L_0x002c
            r0 = 0
            goto L_0x0034
        L_0x002c:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.BEVEL
            goto L_0x0034
        L_0x002f:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.ROUND
            goto L_0x0034
        L_0x0032:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.MITER
        L_0x0034:
            r6 = r0
            float r7 = r15.miterLimit
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r8 = r15.opacity
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r9 = r15.width
            java.util.List<com.airbnb.lottie.model.animatable.AnimatableFloatValue> r10 = r15.lineDashPattern
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r11 = r15.dashOffset
            r2 = r12
            r3 = r13
            r4 = r14
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            androidx.collection.LongSparseArray r0 = new androidx.collection.LongSparseArray
            r0.<init>()
            r12.linearGradientCache = r0
            androidx.collection.LongSparseArray r0 = new androidx.collection.LongSparseArray
            r0.<init>()
            r12.radialGradientCache = r0
            android.graphics.RectF r0 = new android.graphics.RectF
            r0.<init>()
            r12.boundsRect = r0
            java.lang.String r0 = r15.name
            r12.name = r0
            com.airbnb.lottie.model.content.GradientType r0 = r15.gradientType
            r12.type = r0
            boolean r0 = r15.hidden
            r12.hidden = r0
            java.util.Objects.requireNonNull(r13)
            com.airbnb.lottie.LottieComposition r13 = r13.composition
            float r13 = r13.getDuration()
            r0 = 1107296256(0x42000000, float:32.0)
            float r13 = r13 / r0
            int r13 = (int) r13
            r12.cacheSteps = r13
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r13 = r15.gradientColor
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r13 = r13.createAnimation()
            r0 = r13
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r0 = (com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation) r0
            r12.colorAnimation = r0
            r13.addUpdateListener(r12)
            r14.addAnimation(r13)
            com.airbnb.lottie.model.animatable.AnimatablePointValue r13 = r15.startPoint
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r13 = r13.createAnimation()
            r0 = r13
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r0 = (com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation) r0
            r12.startPointAnimation = r0
            r13.addUpdateListener(r12)
            r14.addAnimation(r13)
            com.airbnb.lottie.model.animatable.AnimatablePointValue r13 = r15.endPoint
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r13 = r13.createAnimation()
            r15 = r13
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r15 = (com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation) r15
            r12.endPointAnimation = r15
            r13.addUpdateListener(r12)
            r14.addAnimation(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.GradientStrokeContent.<init>(com.airbnb.lottie.LottieDrawable, com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.content.GradientStroke):void");
    }

    public final <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t, lottieValueCallback);
        if (t != LottieProperty.GRADIENT_COLOR) {
            return;
        }
        if (lottieValueCallback == null) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
            if (valueCallbackKeyframeAnimation != null) {
                this.layer.removeAnimation(valueCallbackKeyframeAnimation);
            }
            this.colorCallbackAnimation = null;
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
        this.colorCallbackAnimation = valueCallbackKeyframeAnimation2;
        valueCallbackKeyframeAnimation2.addUpdateListener(this);
        this.layer.addAnimation(this.colorCallbackAnimation);
    }

    public final String getName() {
        return this.name;
    }
}
