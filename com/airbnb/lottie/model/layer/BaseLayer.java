package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.collection.ArraySet;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import com.airbnb.lottie.C0438L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.PerformanceTracker;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.MeanCalculator;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class BaseLayer implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    public final ArrayList animations;
    public final Matrix boundsMatrix;
    public final LPaint clearPaint;
    public final LPaint contentPaint;
    public final String drawTraceName;
    public final LPaint dstInPaint;
    public final LPaint dstOutPaint;
    public final Layer layerModel;
    public final LottieDrawable lottieDrawable;
    public MaskKeyframeAnimation mask;
    public final RectF maskBoundsRect;
    public final Matrix matrix = new Matrix();
    public final RectF matteBoundsRect;
    public BaseLayer matteLayer;
    public final LPaint mattePaint;
    public BaseLayer parentLayer;
    public List<BaseLayer> parentLayers;
    public final Path path = new Path();
    public final RectF rect;
    public final RectF tempMaskBoundsRect;
    public final TransformKeyframeAnimation transform;
    public boolean visible;

    public abstract void drawLayer(Canvas canvas, Matrix matrix2, int i);

    public void resolveChildKeyPath(KeyPath keyPath, int i, ArrayList arrayList, KeyPath keyPath2) {
    }

    public final void setContents(List<Content> list, List<Content> list2) {
    }

    public final void addAnimation(BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
        if (baseKeyframeAnimation != null) {
            this.animations.add(baseKeyframeAnimation);
        }
    }

    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        this.transform.applyValueCallback(t, lottieValueCallback);
    }

    public final void buildParentLayerListIfNeeded() {
        if (this.parentLayers == null) {
            if (this.parentLayer == null) {
                this.parentLayers = Collections.emptyList();
                return;
            }
            this.parentLayers = new ArrayList();
            for (BaseLayer baseLayer = this.parentLayer; baseLayer != null; baseLayer = baseLayer.parentLayer) {
                this.parentLayers.add(baseLayer);
            }
        }
    }

    public final void clearCanvas(Canvas canvas) {
        RectF rectF = this.rect;
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.clearPaint);
        C0438L.endSection();
    }

    /* JADX WARNING: Removed duplicated region for block: B:116:0x03de A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0146  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x026d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void draw(android.graphics.Canvas r17, android.graphics.Matrix r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            boolean r3 = r0.visible
            if (r3 == 0) goto L_0x041b
            com.airbnb.lottie.model.layer.Layer r3 = r0.layerModel
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.hidden
            if (r3 == 0) goto L_0x0015
            goto L_0x041b
        L_0x0015:
            r16.buildParentLayerListIfNeeded()
            android.graphics.Matrix r3 = r0.matrix
            r3.reset()
            android.graphics.Matrix r3 = r0.matrix
            r3.set(r2)
            java.util.List<com.airbnb.lottie.model.layer.BaseLayer> r3 = r0.parentLayers
            int r3 = r3.size()
            r4 = 1
            int r3 = r3 - r4
        L_0x002a:
            if (r3 < 0) goto L_0x0042
            android.graphics.Matrix r5 = r0.matrix
            java.util.List<com.airbnb.lottie.model.layer.BaseLayer> r6 = r0.parentLayers
            java.lang.Object r6 = r6.get(r3)
            com.airbnb.lottie.model.layer.BaseLayer r6 = (com.airbnb.lottie.model.layer.BaseLayer) r6
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r6 = r6.transform
            android.graphics.Matrix r6 = r6.getMatrix()
            r5.preConcat(r6)
            int r3 = r3 + -1
            goto L_0x002a
        L_0x0042:
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r3 = r0.transform
            java.util.Objects.requireNonNull(r3)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r3 = r3.opacity
            if (r3 != 0) goto L_0x0051
            r3 = 100
            goto L_0x0062
        L_0x0051:
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r3 = r0.transform
            java.util.Objects.requireNonNull(r3)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r3 = r3.opacity
            java.lang.Object r3 = r3.getValue()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
        L_0x0062:
            r5 = r19
            float r5 = (float) r5
            r6 = 1132396544(0x437f0000, float:255.0)
            float r5 = r5 / r6
            float r3 = (float) r3
            float r5 = r5 * r3
            r3 = 1120403456(0x42c80000, float:100.0)
            float r5 = r5 / r3
            float r5 = r5 * r6
            int r3 = (int) r5
            com.airbnb.lottie.model.layer.BaseLayer r5 = r0.matteLayer
            r6 = 0
            if (r5 == 0) goto L_0x0076
            r5 = r4
            goto L_0x0077
        L_0x0076:
            r5 = r6
        L_0x0077:
            if (r5 != 0) goto L_0x0099
            boolean r5 = r16.hasMasksOnThisLayer()
            if (r5 != 0) goto L_0x0099
            android.graphics.Matrix r2 = r0.matrix
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r4 = r0.transform
            android.graphics.Matrix r4 = r4.getMatrix()
            r2.preConcat(r4)
            android.graphics.Matrix r2 = r0.matrix
            r0.drawLayer(r1, r2, r3)
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.C0438L.endSection()
            r16.recordRenderTime()
            return
        L_0x0099:
            android.graphics.RectF r5 = r0.rect
            android.graphics.Matrix r7 = r0.matrix
            r0.getBounds(r5, r7, r6)
            android.graphics.RectF r5 = r0.rect
            com.airbnb.lottie.model.layer.BaseLayer r7 = r0.matteLayer
            if (r7 == 0) goto L_0x00a8
            r7 = r4
            goto L_0x00a9
        L_0x00a8:
            r7 = r6
        L_0x00a9:
            r8 = 0
            if (r7 != 0) goto L_0x00ad
            goto L_0x00d0
        L_0x00ad:
            com.airbnb.lottie.model.layer.Layer r7 = r0.layerModel
            java.util.Objects.requireNonNull(r7)
            com.airbnb.lottie.model.layer.Layer$MatteType r7 = r7.matteType
            com.airbnb.lottie.model.layer.Layer$MatteType r9 = com.airbnb.lottie.model.layer.Layer.MatteType.INVERT
            if (r7 != r9) goto L_0x00b9
            goto L_0x00d0
        L_0x00b9:
            android.graphics.RectF r7 = r0.matteBoundsRect
            r7.set(r8, r8, r8, r8)
            com.airbnb.lottie.model.layer.BaseLayer r7 = r0.matteLayer
            android.graphics.RectF r9 = r0.matteBoundsRect
            r7.getBounds(r9, r2, r4)
            android.graphics.RectF r7 = r0.matteBoundsRect
            boolean r7 = r5.intersect(r7)
            if (r7 != 0) goto L_0x00d0
            r5.set(r8, r8, r8, r8)
        L_0x00d0:
            android.graphics.Matrix r5 = r0.matrix
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r7 = r0.transform
            android.graphics.Matrix r7 = r7.getMatrix()
            r5.preConcat(r7)
            android.graphics.RectF r5 = r0.rect
            android.graphics.Matrix r7 = r0.matrix
            android.graphics.RectF r9 = r0.maskBoundsRect
            r9.set(r8, r8, r8, r8)
            boolean r9 = r16.hasMasksOnThisLayer()
            r10 = 2
            r11 = 3
            if (r9 != 0) goto L_0x00ee
            goto L_0x0193
        L_0x00ee:
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r9 = r0.mask
            java.util.Objects.requireNonNull(r9)
            java.util.List<com.airbnb.lottie.model.content.Mask> r9 = r9.masks
            int r9 = r9.size()
            r12 = r6
        L_0x00fa:
            if (r12 >= r9) goto L_0x0188
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r13 = r0.mask
            java.util.Objects.requireNonNull(r13)
            java.util.List<com.airbnb.lottie.model.content.Mask> r13 = r13.masks
            java.lang.Object r13 = r13.get(r12)
            com.airbnb.lottie.model.content.Mask r13 = (com.airbnb.lottie.model.content.Mask) r13
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r14 = r0.mask
            java.util.Objects.requireNonNull(r14)
            java.util.ArrayList r14 = r14.maskAnimations
            java.lang.Object r14 = r14.get(r12)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r14 = (com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation) r14
            java.lang.Object r14 = r14.getValue()
            android.graphics.Path r14 = (android.graphics.Path) r14
            android.graphics.Path r15 = r0.path
            r15.set(r14)
            android.graphics.Path r14 = r0.path
            r14.transform(r7)
            java.util.Objects.requireNonNull(r13)
            com.airbnb.lottie.model.content.Mask$MaskMode r14 = r13.maskMode
            int r14 = r14.ordinal()
            if (r14 == 0) goto L_0x0138
            if (r14 == r4) goto L_0x0193
            if (r14 == r10) goto L_0x0138
            if (r14 == r11) goto L_0x0193
            goto L_0x013d
        L_0x0138:
            boolean r13 = r13.inverted
            if (r13 == 0) goto L_0x013d
            goto L_0x0193
        L_0x013d:
            android.graphics.Path r13 = r0.path
            android.graphics.RectF r14 = r0.tempMaskBoundsRect
            r13.computeBounds(r14, r6)
            if (r12 != 0) goto L_0x014e
            android.graphics.RectF r13 = r0.maskBoundsRect
            android.graphics.RectF r14 = r0.tempMaskBoundsRect
            r13.set(r14)
            goto L_0x0181
        L_0x014e:
            android.graphics.RectF r13 = r0.maskBoundsRect
            float r14 = r13.left
            android.graphics.RectF r15 = r0.tempMaskBoundsRect
            float r15 = r15.left
            float r14 = java.lang.Math.min(r14, r15)
            android.graphics.RectF r15 = r0.maskBoundsRect
            float r15 = r15.top
            android.graphics.RectF r6 = r0.tempMaskBoundsRect
            float r6 = r6.top
            float r6 = java.lang.Math.min(r15, r6)
            android.graphics.RectF r15 = r0.maskBoundsRect
            float r15 = r15.right
            android.graphics.RectF r11 = r0.tempMaskBoundsRect
            float r11 = r11.right
            float r11 = java.lang.Math.max(r15, r11)
            android.graphics.RectF r15 = r0.maskBoundsRect
            float r15 = r15.bottom
            android.graphics.RectF r10 = r0.tempMaskBoundsRect
            float r10 = r10.bottom
            float r10 = java.lang.Math.max(r15, r10)
            r13.set(r14, r6, r11, r10)
        L_0x0181:
            int r12 = r12 + 1
            r6 = 0
            r10 = 2
            r11 = 3
            goto L_0x00fa
        L_0x0188:
            android.graphics.RectF r6 = r0.maskBoundsRect
            boolean r6 = r5.intersect(r6)
            if (r6 != 0) goto L_0x0193
            r5.set(r8, r8, r8, r8)
        L_0x0193:
            android.graphics.RectF r5 = r0.rect
            int r6 = r17.getWidth()
            float r6 = (float) r6
            int r7 = r17.getHeight()
            float r7 = (float) r7
            boolean r5 = r5.intersect(r8, r8, r6, r7)
            if (r5 != 0) goto L_0x01aa
            android.graphics.RectF r5 = r0.rect
            r5.set(r8, r8, r8, r8)
        L_0x01aa:
            com.airbnb.lottie.C0438L.endSection()
            android.graphics.RectF r5 = r0.rect
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0414
            android.graphics.RectF r5 = r0.rect
            com.airbnb.lottie.animation.LPaint r6 = r0.contentPaint
            android.graphics.PathMeasure r7 = com.airbnb.lottie.utils.Utils.pathMeasure
            r1.saveLayer(r5, r6)
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.C0438L.endSection()
            r16.clearCanvas(r17)
            android.graphics.Matrix r5 = r0.matrix
            r0.drawLayer(r1, r5, r3)
            com.airbnb.lottie.C0438L.endSection()
            boolean r5 = r16.hasMasksOnThisLayer()
            if (r5 == 0) goto L_0x03e8
            android.graphics.Matrix r5 = r0.matrix
            android.graphics.RectF r6 = r0.rect
            com.airbnb.lottie.animation.LPaint r7 = r0.dstInPaint
            r1.saveLayer(r6, r7)
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.C0438L.endSection()
            r6 = 0
        L_0x01e5:
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r7 = r0.mask
            java.util.Objects.requireNonNull(r7)
            java.util.List<com.airbnb.lottie.model.content.Mask> r7 = r7.masks
            int r7 = r7.size()
            if (r6 >= r7) goto L_0x03e2
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r7 = r0.mask
            java.util.Objects.requireNonNull(r7)
            java.util.List<com.airbnb.lottie.model.content.Mask> r7 = r7.masks
            java.lang.Object r7 = r7.get(r6)
            com.airbnb.lottie.model.content.Mask r7 = (com.airbnb.lottie.model.content.Mask) r7
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r8 = r0.mask
            java.util.Objects.requireNonNull(r8)
            java.util.ArrayList r8 = r8.maskAnimations
            java.lang.Object r8 = r8.get(r6)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r8 = (com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation) r8
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r9 = r0.mask
            java.util.Objects.requireNonNull(r9)
            java.util.ArrayList r9 = r9.opacityAnimations
            java.lang.Object r9 = r9.get(r6)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r9 = (com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation) r9
            java.util.Objects.requireNonNull(r7)
            com.airbnb.lottie.model.content.Mask$MaskMode r10 = r7.maskMode
            int r10 = r10.ordinal()
            r11 = 1076048691(0x40233333, float:2.55)
            if (r10 == 0) goto L_0x036f
            r12 = 255(0xff, float:3.57E-43)
            if (r10 == r4) goto L_0x02fb
            r13 = 2
            if (r10 == r13) goto L_0x027b
            r14 = 3
            if (r10 == r14) goto L_0x0233
            goto L_0x03de
        L_0x0233:
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r7 = r0.mask
            java.util.Objects.requireNonNull(r7)
            java.util.ArrayList r7 = r7.maskAnimations
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L_0x0241
            goto L_0x0265
        L_0x0241:
            r7 = 0
        L_0x0242:
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r8 = r0.mask
            java.util.Objects.requireNonNull(r8)
            java.util.List<com.airbnb.lottie.model.content.Mask> r8 = r8.masks
            int r8 = r8.size()
            if (r7 >= r8) goto L_0x026a
            com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation r8 = r0.mask
            java.util.Objects.requireNonNull(r8)
            java.util.List<com.airbnb.lottie.model.content.Mask> r8 = r8.masks
            java.lang.Object r8 = r8.get(r7)
            com.airbnb.lottie.model.content.Mask r8 = (com.airbnb.lottie.model.content.Mask) r8
            java.util.Objects.requireNonNull(r8)
            com.airbnb.lottie.model.content.Mask$MaskMode r8 = r8.maskMode
            com.airbnb.lottie.model.content.Mask$MaskMode r9 = com.airbnb.lottie.model.content.Mask.MaskMode.MASK_MODE_NONE
            if (r8 == r9) goto L_0x0267
        L_0x0265:
            r7 = 0
            goto L_0x026b
        L_0x0267:
            int r7 = r7 + 1
            goto L_0x0242
        L_0x026a:
            r7 = r4
        L_0x026b:
            if (r7 == 0) goto L_0x03de
            com.airbnb.lottie.animation.LPaint r7 = r0.contentPaint
            r7.setAlpha(r12)
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r8 = r0.contentPaint
            r1.drawRect(r7, r8)
            goto L_0x03de
        L_0x027b:
            r14 = 3
            boolean r7 = r7.inverted
            if (r7 == 0) goto L_0x02c1
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.dstInPaint
            android.graphics.PathMeasure r12 = com.airbnb.lottie.utils.Utils.pathMeasure
            r1.saveLayer(r7, r10)
            com.airbnb.lottie.C0438L.endSection()
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            r1.drawRect(r7, r10)
            com.airbnb.lottie.animation.LPaint r7 = r0.dstOutPaint
            java.lang.Object r9 = r9.getValue()
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            float r9 = (float) r9
            float r9 = r9 * r11
            int r9 = (int) r9
            r7.setAlpha(r9)
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.dstOutPaint
            r1.drawPath(r7, r8)
            r17.restore()
            goto L_0x03de
        L_0x02c1:
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.dstInPaint
            android.graphics.PathMeasure r12 = com.airbnb.lottie.utils.Utils.pathMeasure
            r1.saveLayer(r7, r10)
            com.airbnb.lottie.C0438L.endSection()
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            com.airbnb.lottie.animation.LPaint r7 = r0.contentPaint
            java.lang.Object r8 = r9.getValue()
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            float r8 = (float) r8
            float r8 = r8 * r11
            int r8 = (int) r8
            r7.setAlpha(r8)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.contentPaint
            r1.drawPath(r7, r8)
            r17.restore()
            goto L_0x03de
        L_0x02fb:
            r13 = 2
            r14 = 3
            if (r6 != 0) goto L_0x0312
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            r15 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r10.setColor(r15)
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            r10.setAlpha(r12)
            android.graphics.RectF r10 = r0.rect
            com.airbnb.lottie.animation.LPaint r12 = r0.contentPaint
            r1.drawRect(r10, r12)
        L_0x0312:
            boolean r7 = r7.inverted
            if (r7 == 0) goto L_0x0357
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.dstOutPaint
            android.graphics.PathMeasure r12 = com.airbnb.lottie.utils.Utils.pathMeasure
            r1.saveLayer(r7, r10)
            com.airbnb.lottie.C0438L.endSection()
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            r1.drawRect(r7, r10)
            com.airbnb.lottie.animation.LPaint r7 = r0.dstOutPaint
            java.lang.Object r9 = r9.getValue()
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            float r9 = (float) r9
            float r9 = r9 * r11
            int r9 = (int) r9
            r7.setAlpha(r9)
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.dstOutPaint
            r1.drawPath(r7, r8)
            r17.restore()
            goto L_0x03de
        L_0x0357:
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.dstOutPaint
            r1.drawPath(r7, r8)
            goto L_0x03de
        L_0x036f:
            r13 = 2
            r14 = 3
            boolean r7 = r7.inverted
            if (r7 == 0) goto L_0x03b5
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            android.graphics.PathMeasure r12 = com.airbnb.lottie.utils.Utils.pathMeasure
            r1.saveLayer(r7, r10)
            com.airbnb.lottie.C0438L.endSection()
            android.graphics.RectF r7 = r0.rect
            com.airbnb.lottie.animation.LPaint r10 = r0.contentPaint
            r1.drawRect(r7, r10)
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            com.airbnb.lottie.animation.LPaint r7 = r0.contentPaint
            java.lang.Object r8 = r9.getValue()
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            float r8 = (float) r8
            float r8 = r8 * r11
            int r8 = (int) r8
            r7.setAlpha(r8)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.dstOutPaint
            r1.drawPath(r7, r8)
            r17.restore()
            goto L_0x03de
        L_0x03b5:
            java.lang.Object r7 = r8.getValue()
            android.graphics.Path r7 = (android.graphics.Path) r7
            android.graphics.Path r8 = r0.path
            r8.set(r7)
            android.graphics.Path r7 = r0.path
            r7.transform(r5)
            com.airbnb.lottie.animation.LPaint r7 = r0.contentPaint
            java.lang.Object r8 = r9.getValue()
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            float r8 = (float) r8
            float r8 = r8 * r11
            int r8 = (int) r8
            r7.setAlpha(r8)
            android.graphics.Path r7 = r0.path
            com.airbnb.lottie.animation.LPaint r8 = r0.contentPaint
            r1.drawPath(r7, r8)
        L_0x03de:
            int r6 = r6 + 1
            goto L_0x01e5
        L_0x03e2:
            r17.restore()
            com.airbnb.lottie.C0438L.endSection()
        L_0x03e8:
            com.airbnb.lottie.model.layer.BaseLayer r5 = r0.matteLayer
            if (r5 == 0) goto L_0x03ed
            goto L_0x03ee
        L_0x03ed:
            r4 = 0
        L_0x03ee:
            if (r4 == 0) goto L_0x040e
            android.graphics.RectF r4 = r0.rect
            com.airbnb.lottie.animation.LPaint r5 = r0.mattePaint
            r1.saveLayer(r4, r5)
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.C0438L.endSection()
            r16.clearCanvas(r17)
            com.airbnb.lottie.model.layer.BaseLayer r4 = r0.matteLayer
            r4.draw(r1, r2, r3)
            r17.restore()
            com.airbnb.lottie.C0438L.endSection()
            com.airbnb.lottie.C0438L.endSection()
        L_0x040e:
            r17.restore()
            com.airbnb.lottie.C0438L.endSection()
        L_0x0414:
            com.airbnb.lottie.C0438L.endSection()
            r16.recordRenderTime()
            return
        L_0x041b:
            com.airbnb.lottie.C0438L.endSection()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.BaseLayer.draw(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public void getBounds(RectF rectF, Matrix matrix2, boolean z) {
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        buildParentLayerListIfNeeded();
        this.boundsMatrix.set(matrix2);
        if (z) {
            List<BaseLayer> list = this.parentLayers;
            if (list != null) {
                int size = list.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    this.boundsMatrix.preConcat(this.parentLayers.get(size).transform.getMatrix());
                }
            } else {
                BaseLayer baseLayer = this.parentLayer;
                if (baseLayer != null) {
                    this.boundsMatrix.preConcat(baseLayer.transform.getMatrix());
                }
            }
        }
        this.boundsMatrix.preConcat(this.transform.getMatrix());
    }

    public final String getName() {
        Layer layer = this.layerModel;
        Objects.requireNonNull(layer);
        return layer.layerName;
    }

    public final boolean hasMasksOnThisLayer() {
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        if (maskKeyframeAnimation == null || maskKeyframeAnimation.maskAnimations.isEmpty()) {
            return false;
        }
        return true;
    }

    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public final void recordRenderTime() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        Objects.requireNonNull(lottieDrawable2);
        LottieComposition lottieComposition = lottieDrawable2.composition;
        Objects.requireNonNull(lottieComposition);
        PerformanceTracker performanceTracker = lottieComposition.performanceTracker;
        Layer layer = this.layerModel;
        Objects.requireNonNull(layer);
        String str = layer.layerName;
        Objects.requireNonNull(performanceTracker);
        if (performanceTracker.enabled) {
            MeanCalculator meanCalculator = (MeanCalculator) performanceTracker.layerRenderTimes.get(str);
            if (meanCalculator == null) {
                meanCalculator = new MeanCalculator();
                performanceTracker.layerRenderTimes.put(str, meanCalculator);
            }
            int i = meanCalculator.f29n + 1;
            meanCalculator.f29n = i;
            if (i == Integer.MAX_VALUE) {
                meanCalculator.f29n = i / 2;
            }
            if (str.equals("__container")) {
                ArraySet arraySet = performanceTracker.frameListeners;
                Objects.requireNonNull(arraySet);
                ArraySet.ElementIterator elementIterator = new ArraySet.ElementIterator();
                while (elementIterator.hasNext()) {
                    ((PerformanceTracker.FrameListener) elementIterator.next()).onFrameRendered();
                }
            }
        }
    }

    public final void removeAnimation(BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
        this.animations.remove(baseKeyframeAnimation);
    }

    public void setProgress(float f) {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
        Objects.requireNonNull(transformKeyframeAnimation);
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = transformKeyframeAnimation.opacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.setProgress(f);
        }
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = transformKeyframeAnimation.startOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.setProgress(f);
        }
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation3 = transformKeyframeAnimation.endOpacity;
        if (baseKeyframeAnimation3 != null) {
            baseKeyframeAnimation3.setProgress(f);
        }
        BaseKeyframeAnimation<PointF, PointF> baseKeyframeAnimation4 = transformKeyframeAnimation.anchorPoint;
        if (baseKeyframeAnimation4 != null) {
            baseKeyframeAnimation4.setProgress(f);
        }
        BaseKeyframeAnimation<?, PointF> baseKeyframeAnimation5 = transformKeyframeAnimation.position;
        if (baseKeyframeAnimation5 != null) {
            baseKeyframeAnimation5.setProgress(f);
        }
        BaseKeyframeAnimation<ScaleXY, ScaleXY> baseKeyframeAnimation6 = transformKeyframeAnimation.scale;
        if (baseKeyframeAnimation6 != null) {
            baseKeyframeAnimation6.setProgress(f);
        }
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation7 = transformKeyframeAnimation.rotation;
        if (baseKeyframeAnimation7 != null) {
            baseKeyframeAnimation7.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation = transformKeyframeAnimation.skew;
        if (floatKeyframeAnimation != null) {
            floatKeyframeAnimation.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation2 = transformKeyframeAnimation.skewAngle;
        if (floatKeyframeAnimation2 != null) {
            floatKeyframeAnimation2.setProgress(f);
        }
        if (this.mask != null) {
            int i = 0;
            while (true) {
                MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
                Objects.requireNonNull(maskKeyframeAnimation);
                if (i >= maskKeyframeAnimation.maskAnimations.size()) {
                    break;
                }
                MaskKeyframeAnimation maskKeyframeAnimation2 = this.mask;
                Objects.requireNonNull(maskKeyframeAnimation2);
                ((BaseKeyframeAnimation) maskKeyframeAnimation2.maskAnimations.get(i)).setProgress(f);
                i++;
            }
        }
        Layer layer = this.layerModel;
        Objects.requireNonNull(layer);
        if (layer.timeStretch != 0.0f) {
            Layer layer2 = this.layerModel;
            Objects.requireNonNull(layer2);
            f /= layer2.timeStretch;
        }
        BaseLayer baseLayer = this.matteLayer;
        if (baseLayer != null) {
            Layer layer3 = baseLayer.layerModel;
            Objects.requireNonNull(layer3);
            this.matteLayer.setProgress(layer3.timeStretch * f);
        }
        for (int i2 = 0; i2 < this.animations.size(); i2++) {
            ((BaseKeyframeAnimation) this.animations.get(i2)).setProgress(f);
        }
    }

    public BaseLayer(LottieDrawable lottieDrawable2, Layer layer) {
        boolean z = true;
        this.contentPaint = new LPaint(1);
        this.dstInPaint = new LPaint(PorterDuff.Mode.DST_IN, 0);
        this.dstOutPaint = new LPaint(PorterDuff.Mode.DST_OUT, 0);
        LPaint lPaint = new LPaint(1);
        this.mattePaint = lPaint;
        this.clearPaint = new LPaint(PorterDuff.Mode.CLEAR);
        this.rect = new RectF();
        this.maskBoundsRect = new RectF();
        this.matteBoundsRect = new RectF();
        this.tempMaskBoundsRect = new RectF();
        this.boundsMatrix = new Matrix();
        this.animations = new ArrayList();
        this.visible = true;
        this.lottieDrawable = lottieDrawable2;
        this.layerModel = layer;
        this.drawTraceName = MotionController$$ExternalSyntheticOutline1.m8m(new StringBuilder(), layer.layerName, "#draw");
        if (layer.matteType == Layer.MatteType.INVERT) {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        AnimatableTransform animatableTransform = layer.transform;
        Objects.requireNonNull(animatableTransform);
        TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
        this.transform = transformKeyframeAnimation;
        transformKeyframeAnimation.addListener(this);
        List<Mask> list = layer.masks;
        if (list != null && !list.isEmpty()) {
            MaskKeyframeAnimation maskKeyframeAnimation = new MaskKeyframeAnimation(layer.masks);
            this.mask = maskKeyframeAnimation;
            Iterator it = maskKeyframeAnimation.maskAnimations.iterator();
            while (it.hasNext()) {
                ((BaseKeyframeAnimation) it.next()).addUpdateListener(this);
            }
            MaskKeyframeAnimation maskKeyframeAnimation2 = this.mask;
            Objects.requireNonNull(maskKeyframeAnimation2);
            Iterator it2 = maskKeyframeAnimation2.opacityAnimations.iterator();
            while (it2.hasNext()) {
                BaseKeyframeAnimation baseKeyframeAnimation = (BaseKeyframeAnimation) it2.next();
                addAnimation(baseKeyframeAnimation);
                baseKeyframeAnimation.addUpdateListener(this);
            }
        }
        Layer layer2 = this.layerModel;
        Objects.requireNonNull(layer2);
        if (!layer2.inOutKeyframes.isEmpty()) {
            Layer layer3 = this.layerModel;
            Objects.requireNonNull(layer3);
            final FloatKeyframeAnimation floatKeyframeAnimation = new FloatKeyframeAnimation(layer3.inOutKeyframes);
            floatKeyframeAnimation.isDiscrete = true;
            floatKeyframeAnimation.addUpdateListener(new BaseKeyframeAnimation.AnimationListener() {
                public final void onValueChanged() {
                    boolean z;
                    BaseLayer baseLayer = BaseLayer.this;
                    if (floatKeyframeAnimation.getFloatValue() == 1.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Objects.requireNonNull(baseLayer);
                    if (z != baseLayer.visible) {
                        baseLayer.visible = z;
                        baseLayer.lottieDrawable.invalidateSelf();
                    }
                }
            });
            z = ((Float) floatKeyframeAnimation.getValue()).floatValue() != 1.0f ? false : z;
            if (z != this.visible) {
                this.visible = z;
                this.lottieDrawable.invalidateSelf();
            }
            addAnimation(floatKeyframeAnimation);
        } else if (true != this.visible) {
            this.visible = true;
            this.lottieDrawable.invalidateSelf();
        }
    }

    public final void resolveKeyPath(KeyPath keyPath, int i, ArrayList arrayList, KeyPath keyPath2) {
        if (keyPath.matches(getName(), i)) {
            if (!"__container".equals(getName())) {
                String name = getName();
                Objects.requireNonNull(keyPath2);
                KeyPath keyPath3 = new KeyPath(keyPath2);
                keyPath3.keys.add(name);
                if (keyPath.fullyResolvesTo(getName(), i)) {
                    KeyPath keyPath4 = new KeyPath(keyPath3);
                    keyPath4.resolvedElement = this;
                    arrayList.add(keyPath4);
                }
                keyPath2 = keyPath3;
            }
            if (keyPath.propagateToChildren(getName(), i)) {
                resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(getName(), i) + i, arrayList, keyPath2);
            }
        }
    }
}
