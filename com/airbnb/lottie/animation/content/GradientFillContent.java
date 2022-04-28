package com.airbnb.lottie.animation.content;

import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class GradientFillContent implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public final RectF boundsRect;
    public final int cacheSteps;
    public final GradientColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final PointKeyframeAnimation endPointAnimation;
    public final boolean hidden;
    public final BaseLayer layer;
    public final LongSparseArray<LinearGradient> linearGradientCache = new LongSparseArray<>();
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final IntegerKeyframeAnimation opacityAnimation;
    public final LPaint paint;
    public final Path path;
    public final ArrayList paths;
    public final LongSparseArray<RadialGradient> radialGradientCache = new LongSparseArray<>();
    public final PointKeyframeAnimation startPointAnimation;
    public final GradientType type;

    public final void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list2.size(); i++) {
            Content content = list2.get(i);
            if (content instanceof PathContent) {
                this.paths.add((PathContent) content);
            }
        }
    }

    public final <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        if (t == LottieProperty.OPACITY) {
            this.opacityAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.colorFilterAnimation = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            this.layer.addAnimation(this.colorFilterAnimation);
        } else if (t != LottieProperty.GRADIENT_COLOR) {
        } else {
            if (lottieValueCallback == null) {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = this.colorCallbackAnimation;
                if (valueCallbackKeyframeAnimation2 != null) {
                    this.layer.removeAnimation(valueCallbackKeyframeAnimation2);
                }
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            this.layer.addAnimation(this.colorCallbackAnimation);
        }
    }

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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.graphics.RadialGradient} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v1, resolved type: android.graphics.LinearGradient} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: android.graphics.RadialGradient} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: android.graphics.RadialGradient} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: android.graphics.RadialGradient} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: android.graphics.RadialGradient} */
    /* JADX WARNING: type inference failed for: r3v24, types: [android.graphics.LinearGradient] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void draw(android.graphics.Canvas r18, android.graphics.Matrix r19, int r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            boolean r2 = r0.hidden
            if (r2 == 0) goto L_0x0009
            return
        L_0x0009:
            android.graphics.Path r2 = r0.path
            r2.reset()
            r2 = 0
            r3 = r2
        L_0x0010:
            java.util.ArrayList r4 = r0.paths
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x002c
            android.graphics.Path r4 = r0.path
            java.util.ArrayList r5 = r0.paths
            java.lang.Object r5 = r5.get(r3)
            com.airbnb.lottie.animation.content.PathContent r5 = (com.airbnb.lottie.animation.content.PathContent) r5
            android.graphics.Path r5 = r5.getPath()
            r4.addPath(r5, r1)
            int r3 = r3 + 1
            goto L_0x0010
        L_0x002c:
            android.graphics.Path r3 = r0.path
            android.graphics.RectF r4 = r0.boundsRect
            r3.computeBounds(r4, r2)
            com.airbnb.lottie.model.content.GradientType r3 = r0.type
            com.airbnb.lottie.model.content.GradientType r4 = com.airbnb.lottie.model.content.GradientType.LINEAR
            r5 = 0
            if (r3 != r4) goto L_0x0088
            int r3 = r17.getGradientHash()
            androidx.collection.LongSparseArray<android.graphics.LinearGradient> r4 = r0.linearGradientCache
            long r6 = (long) r3
            java.util.Objects.requireNonNull(r4)
            java.lang.Object r3 = r4.get(r6, r5)
            android.graphics.LinearGradient r3 = (android.graphics.LinearGradient) r3
            if (r3 == 0) goto L_0x004e
            goto L_0x00e5
        L_0x004e:
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = r0.startPointAnimation
            java.lang.Object r3 = r3.getValue()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r4 = r0.endPointAnimation
            java.lang.Object r4 = r4.getValue()
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r5 = r0.colorAnimation
            java.lang.Object r5 = r5.getValue()
            com.airbnb.lottie.model.content.GradientColor r5 = (com.airbnb.lottie.model.content.GradientColor) r5
            java.util.Objects.requireNonNull(r5)
            int[] r8 = r5.colors
            int[] r14 = r0.applyDynamicColorsIfNeeded(r8)
            float[] r15 = r5.positions
            android.graphics.LinearGradient r5 = new android.graphics.LinearGradient
            float r10 = r3.x
            float r11 = r3.y
            float r12 = r4.x
            float r13 = r4.y
            android.graphics.Shader$TileMode r16 = android.graphics.Shader.TileMode.CLAMP
            r9 = r5
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)
            androidx.collection.LongSparseArray<android.graphics.LinearGradient> r3 = r0.linearGradientCache
            r3.put(r6, r5)
            r3 = r5
            goto L_0x00e5
        L_0x0088:
            int r3 = r17.getGradientHash()
            androidx.collection.LongSparseArray<android.graphics.RadialGradient> r4 = r0.radialGradientCache
            long r6 = (long) r3
            java.util.Objects.requireNonNull(r4)
            java.lang.Object r3 = r4.get(r6, r5)
            android.graphics.RadialGradient r3 = (android.graphics.RadialGradient) r3
            if (r3 == 0) goto L_0x009b
            goto L_0x00e5
        L_0x009b:
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = r0.startPointAnimation
            java.lang.Object r3 = r3.getValue()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r4 = r0.endPointAnimation
            java.lang.Object r4 = r4.getValue()
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r5 = r0.colorAnimation
            java.lang.Object r5 = r5.getValue()
            com.airbnb.lottie.model.content.GradientColor r5 = (com.airbnb.lottie.model.content.GradientColor) r5
            java.util.Objects.requireNonNull(r5)
            int[] r8 = r5.colors
            int[] r13 = r0.applyDynamicColorsIfNeeded(r8)
            float[] r14 = r5.positions
            float r10 = r3.x
            float r11 = r3.y
            float r3 = r4.x
            float r4 = r4.y
            float r3 = r3 - r10
            double r8 = (double) r3
            float r4 = r4 - r11
            double r3 = (double) r4
            double r3 = java.lang.Math.hypot(r8, r3)
            float r3 = (float) r3
            r4 = 0
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x00d7
            r3 = 981668463(0x3a83126f, float:0.001)
        L_0x00d7:
            r12 = r3
            android.graphics.RadialGradient r3 = new android.graphics.RadialGradient
            android.graphics.Shader$TileMode r15 = android.graphics.Shader.TileMode.CLAMP
            r9 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15)
            androidx.collection.LongSparseArray<android.graphics.RadialGradient> r4 = r0.radialGradientCache
            r4.put(r6, r3)
        L_0x00e5:
            r3.setLocalMatrix(r1)
            com.airbnb.lottie.animation.LPaint r1 = r0.paint
            r1.setShader(r3)
            com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation r1 = r0.colorFilterAnimation
            if (r1 == 0) goto L_0x00fc
            com.airbnb.lottie.animation.LPaint r3 = r0.paint
            java.lang.Object r1 = r1.getValue()
            android.graphics.ColorFilter r1 = (android.graphics.ColorFilter) r1
            r3.setColorFilter(r1)
        L_0x00fc:
            r1 = r20
            float r1 = (float) r1
            r3 = 1132396544(0x437f0000, float:255.0)
            float r1 = r1 / r3
            com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation r4 = r0.opacityAnimation
            java.lang.Object r4 = r4.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            float r4 = (float) r4
            float r1 = r1 * r4
            r4 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 / r4
            float r1 = r1 * r3
            int r1 = (int) r1
            com.airbnb.lottie.animation.LPaint r3 = r0.paint
            android.graphics.PointF r4 = com.airbnb.lottie.utils.MiscUtils.pathFromDataCurrentPoint
            r4 = 255(0xff, float:3.57E-43)
            int r1 = java.lang.Math.min(r4, r1)
            int r1 = java.lang.Math.max(r2, r1)
            r3.setAlpha(r1)
            android.graphics.Path r1 = r0.path
            com.airbnb.lottie.animation.LPaint r0 = r0.paint
            r2 = r18
            r2.drawPath(r1, r0)
            com.airbnb.lottie.C0438L.endSection()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.GradientFillContent.draw(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.path.reset();
        for (int i = 0; i < this.paths.size(); i++) {
            this.path.addPath(((PathContent) this.paths.get(i)).getPath(), matrix);
        }
        this.path.computeBounds(rectF, false);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
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

    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public GradientFillContent(LottieDrawable lottieDrawable2, BaseLayer baseLayer, GradientFill gradientFill) {
        Path path2 = new Path();
        this.path = path2;
        this.paint = new LPaint(1);
        this.boundsRect = new RectF();
        this.paths = new ArrayList();
        this.layer = baseLayer;
        Objects.requireNonNull(gradientFill);
        this.name = gradientFill.name;
        this.hidden = gradientFill.hidden;
        this.lottieDrawable = lottieDrawable2;
        this.type = gradientFill.gradientType;
        path2.setFillType(gradientFill.fillType);
        Objects.requireNonNull(lottieDrawable2);
        this.cacheSteps = (int) (lottieDrawable2.composition.getDuration() / 32.0f);
        BaseKeyframeAnimation<GradientColor, GradientColor> createAnimation = gradientFill.gradientColor.createAnimation();
        this.colorAnimation = (GradientColorKeyframeAnimation) createAnimation;
        createAnimation.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation);
        BaseKeyframeAnimation<Integer, Integer> createAnimation2 = gradientFill.opacity.createAnimation();
        this.opacityAnimation = (IntegerKeyframeAnimation) createAnimation2;
        createAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation2);
        BaseKeyframeAnimation<PointF, PointF> createAnimation3 = gradientFill.startPoint.createAnimation();
        this.startPointAnimation = (PointKeyframeAnimation) createAnimation3;
        createAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation3);
        BaseKeyframeAnimation<PointF, PointF> createAnimation4 = gradientFill.endPoint.createAnimation();
        this.endPointAnimation = (PointKeyframeAnimation) createAnimation4;
        createAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation4);
    }

    public final void resolveKeyPath(KeyPath keyPath, int i, ArrayList arrayList, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, arrayList, keyPath2, this);
    }

    public final String getName() {
        return this.name;
    }
}
