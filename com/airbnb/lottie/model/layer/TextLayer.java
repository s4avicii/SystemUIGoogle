package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.HashMap;
import java.util.Objects;

public final class TextLayer extends BaseLayer {
    public final LongSparseArray<String> codePointCache = new LongSparseArray<>();
    public BaseKeyframeAnimation<Integer, Integer> colorAnimation;
    public final LottieComposition composition;
    public final HashMap contentsForCharacter = new HashMap();
    public final C04691 fillPaint = new Paint() {
        {
            setStyle(Paint.Style.FILL);
        }
    };
    public final LottieDrawable lottieDrawable;
    public final Matrix matrix = new Matrix();
    public final RectF rectF = new RectF();
    public final StringBuilder stringBuilder = new StringBuilder(2);
    public BaseKeyframeAnimation<Integer, Integer> strokeColorAnimation;
    public final C04702 strokePaint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
        }
    };
    public BaseKeyframeAnimation<Float, Float> strokeWidthAnimation;
    public final TextKeyframeAnimation textAnimation;
    public ValueCallbackKeyframeAnimation textSizeAnimation;
    public BaseKeyframeAnimation<Float, Float> trackingAnimation;

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x02a4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void drawLayer(android.graphics.Canvas r20, android.graphics.Matrix r21, int r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r20.save()
            com.airbnb.lottie.LottieDrawable r2 = r0.lottieDrawable
            java.util.Objects.requireNonNull(r2)
            com.airbnb.lottie.TextDelegate r3 = r2.textDelegate
            if (r3 != 0) goto L_0x001f
            com.airbnb.lottie.LottieComposition r2 = r2.composition
            java.util.Objects.requireNonNull(r2)
            androidx.collection.SparseArrayCompat<com.airbnb.lottie.model.FontCharacter> r2 = r2.characters
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x001f
            r2 = 1
            goto L_0x0020
        L_0x001f:
            r2 = 0
        L_0x0020:
            if (r2 != 0) goto L_0x0025
            r20.setMatrix(r21)
        L_0x0025:
            com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation r2 = r0.textAnimation
            java.lang.Object r2 = r2.getValue()
            com.airbnb.lottie.model.DocumentData r2 = (com.airbnb.lottie.model.DocumentData) r2
            com.airbnb.lottie.LottieComposition r3 = r0.composition
            java.util.Objects.requireNonNull(r3)
            java.util.Map<java.lang.String, com.airbnb.lottie.model.Font> r3 = r3.fonts
            java.lang.String r4 = r2.fontName
            java.lang.Object r3 = r3.get(r4)
            com.airbnb.lottie.model.Font r3 = (com.airbnb.lottie.model.Font) r3
            if (r3 != 0) goto L_0x0042
            r20.restore()
            return
        L_0x0042:
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r4 = r0.colorAnimation
            if (r4 == 0) goto L_0x0056
            com.airbnb.lottie.model.layer.TextLayer$1 r5 = r0.fillPaint
            java.lang.Object r4 = r4.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            r5.setColor(r4)
            goto L_0x005d
        L_0x0056:
            com.airbnb.lottie.model.layer.TextLayer$1 r4 = r0.fillPaint
            int r5 = r2.color
            r4.setColor(r5)
        L_0x005d:
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r4 = r0.strokeColorAnimation
            if (r4 == 0) goto L_0x0071
            com.airbnb.lottie.model.layer.TextLayer$2 r5 = r0.strokePaint
            java.lang.Object r4 = r4.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            r5.setColor(r4)
            goto L_0x0078
        L_0x0071:
            com.airbnb.lottie.model.layer.TextLayer$2 r4 = r0.strokePaint
            int r5 = r2.strokeColor
            r4.setColor(r5)
        L_0x0078:
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r4 = r0.transform
            java.util.Objects.requireNonNull(r4)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r4 = r4.opacity
            r5 = 100
            if (r4 != 0) goto L_0x0085
            r4 = r5
            goto L_0x0096
        L_0x0085:
            com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation r4 = r0.transform
            java.util.Objects.requireNonNull(r4)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Integer, java.lang.Integer> r4 = r4.opacity
            java.lang.Object r4 = r4.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
        L_0x0096:
            int r4 = r4 * 255
            int r4 = r4 / r5
            com.airbnb.lottie.model.layer.TextLayer$1 r5 = r0.fillPaint
            r5.setAlpha(r4)
            com.airbnb.lottie.model.layer.TextLayer$2 r5 = r0.strokePaint
            r5.setAlpha(r4)
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r4 = r0.strokeWidthAnimation
            if (r4 == 0) goto L_0x00b7
            com.airbnb.lottie.model.layer.TextLayer$2 r5 = r0.strokePaint
            java.lang.Object r4 = r4.getValue()
            java.lang.Float r4 = (java.lang.Float) r4
            float r4 = r4.floatValue()
            r5.setStrokeWidth(r4)
            goto L_0x00c8
        L_0x00b7:
            float r4 = com.airbnb.lottie.utils.Utils.getScale(r21)
            com.airbnb.lottie.model.layer.TextLayer$2 r5 = r0.strokePaint
            float r6 = r2.strokeWidth
            float r7 = com.airbnb.lottie.utils.Utils.dpScale()
            float r7 = r7 * r6
            float r7 = r7 * r4
            r5.setStrokeWidth(r7)
        L_0x00c8:
            com.airbnb.lottie.LottieDrawable r4 = r0.lottieDrawable
            java.util.Objects.requireNonNull(r4)
            com.airbnb.lottie.TextDelegate r5 = r4.textDelegate
            if (r5 != 0) goto L_0x00e0
            com.airbnb.lottie.LottieComposition r4 = r4.composition
            java.util.Objects.requireNonNull(r4)
            androidx.collection.SparseArrayCompat<com.airbnb.lottie.model.FontCharacter> r4 = r4.characters
            int r4 = r4.size()
            if (r4 <= 0) goto L_0x00e0
            r4 = 1
            goto L_0x00e1
        L_0x00e0:
            r4 = 0
        L_0x00e1:
            java.lang.String r5 = "\n"
            java.lang.String r6 = "\r"
            java.lang.String r7 = "\r\n"
            r8 = 0
            if (r4 == 0) goto L_0x02a4
            com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation r4 = r0.textSizeAnimation
            if (r4 != 0) goto L_0x00f1
            float r4 = r2.size
            goto L_0x00fb
        L_0x00f1:
            java.lang.Object r4 = r4.getValue()
            java.lang.Float r4 = (java.lang.Float) r4
            float r4 = r4.floatValue()
        L_0x00fb:
            r9 = 1120403456(0x42c80000, float:100.0)
            float r4 = r4 / r9
            float r9 = com.airbnb.lottie.utils.Utils.getScale(r21)
            java.lang.String r10 = r2.text
            float r11 = r2.lineHeight
            float r12 = com.airbnb.lottie.utils.Utils.dpScale()
            float r12 = r12 * r11
            java.lang.String r7 = r10.replaceAll(r7, r6)
            java.lang.String r5 = r7.replaceAll(r5, r6)
            java.lang.String[] r5 = r5.split(r6)
            java.util.List r5 = java.util.Arrays.asList(r5)
            int r6 = r5.size()
            r7 = 0
        L_0x0120:
            if (r7 >= r6) goto L_0x04dd
            java.lang.Object r10 = r5.get(r7)
            java.lang.String r10 = (java.lang.String) r10
            r11 = 0
            r13 = 0
        L_0x012a:
            int r14 = r10.length()
            if (r11 >= r14) goto L_0x0170
            char r14 = r10.charAt(r11)
            java.lang.String r15 = r3.family
            r22 = r5
            java.lang.String r5 = r3.style
            int r5 = com.airbnb.lottie.model.FontCharacter.hashFor(r14, r15, r5)
            com.airbnb.lottie.LottieComposition r14 = r0.composition
            java.util.Objects.requireNonNull(r14)
            androidx.collection.SparseArrayCompat<com.airbnb.lottie.model.FontCharacter> r14 = r14.characters
            java.util.Objects.requireNonNull(r14)
            java.lang.Object r5 = r14.get(r5, r8)
            com.airbnb.lottie.model.FontCharacter r5 = (com.airbnb.lottie.model.FontCharacter) r5
            if (r5 != 0) goto L_0x0154
            r16 = r6
            r15 = r7
            goto L_0x0167
        L_0x0154:
            double r13 = (double) r13
            r15 = r7
            double r7 = r5.width
            r16 = r6
            double r5 = (double) r4
            double r7 = r7 * r5
            float r5 = com.airbnb.lottie.utils.Utils.dpScale()
            double r5 = (double) r5
            double r7 = r7 * r5
            double r5 = (double) r9
            double r7 = r7 * r5
            double r7 = r7 + r13
            float r5 = (float) r7
            r13 = r5
        L_0x0167:
            int r11 = r11 + 1
            r8 = 0
            r5 = r22
            r7 = r15
            r6 = r16
            goto L_0x012a
        L_0x0170:
            r22 = r5
            r16 = r6
            r15 = r7
            r20.save()
            com.airbnb.lottie.model.DocumentData$Justification r5 = r2.justification
            int r5 = r5.ordinal()
            r6 = 1
            if (r5 == r6) goto L_0x018e
            r6 = 2
            if (r5 == r6) goto L_0x0185
            goto L_0x0193
        L_0x0185:
            float r5 = -r13
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            r6 = 0
            r1.translate(r5, r6)
            goto L_0x0193
        L_0x018e:
            r5 = 0
            float r6 = -r13
            r1.translate(r6, r5)
        L_0x0193:
            int r6 = r16 + -1
            float r5 = (float) r6
            float r5 = r5 * r12
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            r7 = r15
            float r6 = (float) r7
            float r6 = r6 * r12
            float r6 = r6 - r5
            r5 = 0
            r1.translate(r5, r6)
            r5 = 0
        L_0x01a3:
            int r6 = r10.length()
            if (r5 >= r6) goto L_0x0296
            char r6 = r10.charAt(r5)
            java.lang.String r8 = r3.family
            java.lang.String r11 = r3.style
            int r6 = com.airbnb.lottie.model.FontCharacter.hashFor(r6, r8, r11)
            com.airbnb.lottie.LottieComposition r8 = r0.composition
            java.util.Objects.requireNonNull(r8)
            androidx.collection.SparseArrayCompat<com.airbnb.lottie.model.FontCharacter> r8 = r8.characters
            java.util.Objects.requireNonNull(r8)
            r11 = 0
            java.lang.Object r6 = r8.get(r6, r11)
            com.airbnb.lottie.model.FontCharacter r6 = (com.airbnb.lottie.model.FontCharacter) r6
            if (r6 != 0) goto L_0x01ce
            r14 = r21
            r18 = r10
            goto L_0x0290
        L_0x01ce:
            java.util.HashMap r8 = r0.contentsForCharacter
            boolean r8 = r8.containsKey(r6)
            if (r8 == 0) goto L_0x01e1
            java.util.HashMap r8 = r0.contentsForCharacter
            java.lang.Object r8 = r8.get(r6)
            java.util.List r8 = (java.util.List) r8
            r18 = r10
            goto L_0x0212
        L_0x01e1:
            java.util.List<com.airbnb.lottie.model.content.ShapeGroup> r8 = r6.shapes
            int r11 = r8.size()
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>(r11)
            r14 = 0
        L_0x01ed:
            if (r14 >= r11) goto L_0x020a
            java.lang.Object r15 = r8.get(r14)
            com.airbnb.lottie.model.content.ShapeGroup r15 = (com.airbnb.lottie.model.content.ShapeGroup) r15
            r17 = r8
            com.airbnb.lottie.animation.content.ContentGroup r8 = new com.airbnb.lottie.animation.content.ContentGroup
            r18 = r10
            com.airbnb.lottie.LottieDrawable r10 = r0.lottieDrawable
            r8.<init>(r10, r0, r15)
            r13.add(r8)
            int r14 = r14 + 1
            r8 = r17
            r10 = r18
            goto L_0x01ed
        L_0x020a:
            r18 = r10
            java.util.HashMap r8 = r0.contentsForCharacter
            r8.put(r6, r13)
            r8 = r13
        L_0x0212:
            r10 = 0
        L_0x0213:
            int r11 = r8.size()
            if (r10 >= r11) goto L_0x0269
            java.lang.Object r11 = r8.get(r10)
            com.airbnb.lottie.animation.content.ContentGroup r11 = (com.airbnb.lottie.animation.content.ContentGroup) r11
            android.graphics.Path r11 = r11.getPath()
            android.graphics.RectF r13 = r0.rectF
            r14 = 0
            r11.computeBounds(r13, r14)
            android.graphics.Matrix r13 = r0.matrix
            r14 = r21
            r13.set(r14)
            android.graphics.Matrix r13 = r0.matrix
            float r15 = r2.baselineShift
            float r15 = -r15
            float r17 = com.airbnb.lottie.utils.Utils.dpScale()
            float r15 = r15 * r17
            r17 = r8
            r8 = 0
            r13.preTranslate(r8, r15)
            android.graphics.Matrix r8 = r0.matrix
            r8.preScale(r4, r4)
            android.graphics.Matrix r8 = r0.matrix
            r11.transform(r8)
            boolean r8 = r2.strokeOverFill
            if (r8 == 0) goto L_0x025a
            com.airbnb.lottie.model.layer.TextLayer$1 r8 = r0.fillPaint
            drawGlyph(r11, r8, r1)
            com.airbnb.lottie.model.layer.TextLayer$2 r8 = r0.strokePaint
            drawGlyph(r11, r8, r1)
            goto L_0x0264
        L_0x025a:
            com.airbnb.lottie.model.layer.TextLayer$2 r8 = r0.strokePaint
            drawGlyph(r11, r8, r1)
            com.airbnb.lottie.model.layer.TextLayer$1 r8 = r0.fillPaint
            drawGlyph(r11, r8, r1)
        L_0x0264:
            int r10 = r10 + 1
            r8 = r17
            goto L_0x0213
        L_0x0269:
            r14 = r21
            double r10 = r6.width
            float r6 = (float) r10
            float r6 = r6 * r4
            float r8 = com.airbnb.lottie.utils.Utils.dpScale()
            float r8 = r8 * r6
            float r8 = r8 * r9
            int r6 = r2.tracking
            float r6 = (float) r6
            r10 = 1092616192(0x41200000, float:10.0)
            float r6 = r6 / r10
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r10 = r0.trackingAnimation
            if (r10 == 0) goto L_0x028a
            java.lang.Object r10 = r10.getValue()
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            float r6 = r6 + r10
        L_0x028a:
            float r6 = r6 * r9
            float r6 = r6 + r8
            r8 = 0
            r1.translate(r6, r8)
        L_0x0290:
            int r5 = r5 + 1
            r10 = r18
            goto L_0x01a3
        L_0x0296:
            r14 = r21
            r20.restore()
            int r7 = r7 + 1
            r8 = 0
            r5 = r22
            r6 = r16
            goto L_0x0120
        L_0x02a4:
            r14 = r21
            float r4 = com.airbnb.lottie.utils.Utils.getScale(r21)
            com.airbnb.lottie.LottieDrawable r8 = r0.lottieDrawable
            java.lang.String r9 = r3.family
            java.lang.String r3 = r3.style
            java.util.Objects.requireNonNull(r8)
            android.graphics.drawable.Drawable$Callback r10 = r8.getCallback()
            if (r10 != 0) goto L_0x02bb
            r8 = 0
            goto L_0x02cc
        L_0x02bb:
            com.airbnb.lottie.manager.FontAssetManager r10 = r8.fontAssetManager
            if (r10 != 0) goto L_0x02ca
            com.airbnb.lottie.manager.FontAssetManager r10 = new com.airbnb.lottie.manager.FontAssetManager
            android.graphics.drawable.Drawable$Callback r11 = r8.getCallback()
            r10.<init>(r11)
            r8.fontAssetManager = r10
        L_0x02ca:
            com.airbnb.lottie.manager.FontAssetManager r8 = r8.fontAssetManager
        L_0x02cc:
            if (r8 == 0) goto L_0x0338
            com.airbnb.lottie.model.MutablePair<java.lang.String> r10 = r8.tempPair
            java.util.Objects.requireNonNull(r10)
            r10.first = r9
            r10.second = r3
            java.util.HashMap r10 = r8.fontMap
            com.airbnb.lottie.model.MutablePair<java.lang.String> r11 = r8.tempPair
            java.lang.Object r10 = r10.get(r11)
            android.graphics.Typeface r10 = (android.graphics.Typeface) r10
            if (r10 == 0) goto L_0x02e4
            goto L_0x0339
        L_0x02e4:
            java.util.HashMap r10 = r8.fontFamilies
            java.lang.Object r10 = r10.get(r9)
            android.graphics.Typeface r10 = (android.graphics.Typeface) r10
            if (r10 == 0) goto L_0x02ef
            goto L_0x0309
        L_0x02ef:
            java.lang.String r10 = "fonts/"
            java.lang.StringBuilder r10 = android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0.m2m(r10, r9)
            java.lang.String r11 = r8.defaultFontFileExtension
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.content.res.AssetManager r11 = r8.assetManager
            android.graphics.Typeface r10 = android.graphics.Typeface.createFromAsset(r11, r10)
            java.util.HashMap r11 = r8.fontFamilies
            r11.put(r9, r10)
        L_0x0309:
            java.lang.String r9 = "Italic"
            boolean r9 = r3.contains(r9)
            java.lang.String r11 = "Bold"
            boolean r3 = r3.contains(r11)
            if (r9 == 0) goto L_0x031b
            if (r3 == 0) goto L_0x031b
            r3 = 3
            goto L_0x0324
        L_0x031b:
            if (r9 == 0) goto L_0x031f
            r3 = 2
            goto L_0x0324
        L_0x031f:
            if (r3 == 0) goto L_0x0323
            r3 = 1
            goto L_0x0324
        L_0x0323:
            r3 = 0
        L_0x0324:
            int r9 = r10.getStyle()
            if (r9 != r3) goto L_0x032b
            goto L_0x0330
        L_0x032b:
            android.graphics.Typeface r3 = android.graphics.Typeface.create(r10, r3)
            r10 = r3
        L_0x0330:
            java.util.HashMap r3 = r8.fontMap
            com.airbnb.lottie.model.MutablePair<java.lang.String> r8 = r8.tempPair
            r3.put(r8, r10)
            goto L_0x0339
        L_0x0338:
            r10 = 0
        L_0x0339:
            if (r10 != 0) goto L_0x033d
            goto L_0x04dd
        L_0x033d:
            java.lang.String r3 = r2.text
            com.airbnb.lottie.LottieDrawable r8 = r0.lottieDrawable
            java.util.Objects.requireNonNull(r8)
            com.airbnb.lottie.TextDelegate r8 = r8.textDelegate
            if (r8 == 0) goto L_0x0366
            boolean r9 = r8.cacheText
            if (r9 == 0) goto L_0x035d
            java.util.HashMap r9 = r8.stringMap
            boolean r9 = r9.containsKey(r3)
            if (r9 == 0) goto L_0x035d
            java.util.HashMap r8 = r8.stringMap
            java.lang.Object r3 = r8.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            goto L_0x0366
        L_0x035d:
            boolean r9 = r8.cacheText
            if (r9 == 0) goto L_0x0366
            java.util.HashMap r8 = r8.stringMap
            r8.put(r3, r3)
        L_0x0366:
            com.airbnb.lottie.model.layer.TextLayer$1 r8 = r0.fillPaint
            r8.setTypeface(r10)
            com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation r8 = r0.textSizeAnimation
            if (r8 != 0) goto L_0x0372
            float r8 = r2.size
            goto L_0x037c
        L_0x0372:
            java.lang.Object r8 = r8.getValue()
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
        L_0x037c:
            com.airbnb.lottie.model.layer.TextLayer$1 r9 = r0.fillPaint
            float r10 = com.airbnb.lottie.utils.Utils.dpScale()
            float r10 = r10 * r8
            r9.setTextSize(r10)
            com.airbnb.lottie.model.layer.TextLayer$2 r8 = r0.strokePaint
            com.airbnb.lottie.model.layer.TextLayer$1 r9 = r0.fillPaint
            android.graphics.Typeface r9 = r9.getTypeface()
            r8.setTypeface(r9)
            com.airbnb.lottie.model.layer.TextLayer$2 r8 = r0.strokePaint
            com.airbnb.lottie.model.layer.TextLayer$1 r9 = r0.fillPaint
            float r9 = r9.getTextSize()
            r8.setTextSize(r9)
            float r8 = r2.lineHeight
            float r9 = com.airbnb.lottie.utils.Utils.dpScale()
            float r9 = r9 * r8
            java.lang.String r3 = r3.replaceAll(r7, r6)
            java.lang.String r3 = r3.replaceAll(r5, r6)
            java.lang.String[] r3 = r3.split(r6)
            java.util.List r3 = java.util.Arrays.asList(r3)
            int r5 = r3.size()
            r6 = 0
        L_0x03b8:
            if (r6 >= r5) goto L_0x04dd
            java.lang.Object r7 = r3.get(r6)
            java.lang.String r7 = (java.lang.String) r7
            com.airbnb.lottie.model.layer.TextLayer$2 r8 = r0.strokePaint
            float r8 = r8.measureText(r7)
            com.airbnb.lottie.model.DocumentData$Justification r10 = r2.justification
            int r10 = r10.ordinal()
            r11 = 1
            if (r10 == r11) goto L_0x03dc
            r11 = 2
            if (r10 == r11) goto L_0x03d3
            goto L_0x03e1
        L_0x03d3:
            float r8 = -r8
            r10 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r10
            r10 = 0
            r1.translate(r8, r10)
            goto L_0x03e1
        L_0x03dc:
            r10 = 0
            float r8 = -r8
            r1.translate(r8, r10)
        L_0x03e1:
            int r8 = r5 + -1
            float r8 = (float) r8
            float r8 = r8 * r9
            r10 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r10
            float r10 = (float) r6
            float r10 = r10 * r9
            float r10 = r10 - r8
            r8 = 0
            r1.translate(r8, r10)
            r8 = 0
        L_0x03f0:
            int r10 = r7.length()
            if (r8 >= r10) goto L_0x04d4
            int r10 = r7.codePointAt(r8)
            int r11 = java.lang.Character.charCount(r10)
            int r11 = r11 + r8
        L_0x03ff:
            int r12 = r7.length()
            if (r11 >= r12) goto L_0x0440
            int r12 = r7.codePointAt(r11)
            int r13 = java.lang.Character.getType(r12)
            r15 = 16
            if (r13 == r15) goto L_0x0433
            int r13 = java.lang.Character.getType(r12)
            r15 = 27
            if (r13 == r15) goto L_0x0433
            int r13 = java.lang.Character.getType(r12)
            r15 = 6
            if (r13 == r15) goto L_0x0433
            int r13 = java.lang.Character.getType(r12)
            r15 = 28
            if (r13 == r15) goto L_0x0433
            int r13 = java.lang.Character.getType(r12)
            r15 = 19
            if (r13 != r15) goto L_0x0431
            goto L_0x0433
        L_0x0431:
            r13 = 0
            goto L_0x0434
        L_0x0433:
            r13 = 1
        L_0x0434:
            if (r13 != 0) goto L_0x0437
            goto L_0x0440
        L_0x0437:
            int r13 = java.lang.Character.charCount(r12)
            int r11 = r11 + r13
            int r10 = r10 * 31
            int r10 = r10 + r12
            goto L_0x03ff
        L_0x0440:
            androidx.collection.LongSparseArray<java.lang.String> r12 = r0.codePointCache
            r22 = r9
            long r9 = (long) r10
            java.util.Objects.requireNonNull(r12)
            boolean r13 = r12.mGarbage
            if (r13 == 0) goto L_0x044f
            r12.mo1471gc()
        L_0x044f:
            long[] r13 = r12.mKeys
            int r12 = r12.mSize
            int r12 = androidx.recyclerview.R$dimen.binarySearch((long[]) r13, (int) r12, (long) r9)
            if (r12 < 0) goto L_0x045b
            r12 = 1
            goto L_0x045c
        L_0x045b:
            r12 = 0
        L_0x045c:
            if (r12 == 0) goto L_0x046b
            androidx.collection.LongSparseArray<java.lang.String> r11 = r0.codePointCache
            java.util.Objects.requireNonNull(r11)
            r12 = 0
            java.lang.Object r9 = r11.get(r9, r12)
            java.lang.String r9 = (java.lang.String) r9
            goto L_0x048f
        L_0x046b:
            java.lang.StringBuilder r12 = r0.stringBuilder
            r13 = 0
            r12.setLength(r13)
            r12 = r8
        L_0x0472:
            if (r12 >= r11) goto L_0x0483
            int r13 = r7.codePointAt(r12)
            java.lang.StringBuilder r15 = r0.stringBuilder
            r15.appendCodePoint(r13)
            int r13 = java.lang.Character.charCount(r13)
            int r12 = r12 + r13
            goto L_0x0472
        L_0x0483:
            java.lang.StringBuilder r11 = r0.stringBuilder
            java.lang.String r11 = r11.toString()
            androidx.collection.LongSparseArray<java.lang.String> r12 = r0.codePointCache
            r12.put(r9, r11)
            r9 = r11
        L_0x048f:
            int r10 = r9.length()
            int r8 = r8 + r10
            boolean r10 = r2.strokeOverFill
            if (r10 == 0) goto L_0x04a3
            com.airbnb.lottie.model.layer.TextLayer$1 r10 = r0.fillPaint
            drawCharacter(r9, r10, r1)
            com.airbnb.lottie.model.layer.TextLayer$2 r10 = r0.strokePaint
            drawCharacter(r9, r10, r1)
            goto L_0x04ad
        L_0x04a3:
            com.airbnb.lottie.model.layer.TextLayer$2 r10 = r0.strokePaint
            drawCharacter(r9, r10, r1)
            com.airbnb.lottie.model.layer.TextLayer$1 r10 = r0.fillPaint
            drawCharacter(r9, r10, r1)
        L_0x04ad:
            com.airbnb.lottie.model.layer.TextLayer$1 r10 = r0.fillPaint
            r11 = 1
            r12 = 0
            float r9 = r10.measureText(r9, r12, r11)
            int r10 = r2.tracking
            float r10 = (float) r10
            r11 = 1092616192(0x41200000, float:10.0)
            float r10 = r10 / r11
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation<java.lang.Float, java.lang.Float> r11 = r0.trackingAnimation
            if (r11 == 0) goto L_0x04ca
            java.lang.Object r11 = r11.getValue()
            java.lang.Float r11 = (java.lang.Float) r11
            float r11 = r11.floatValue()
            float r10 = r10 + r11
        L_0x04ca:
            float r10 = r10 * r4
            float r10 = r10 + r9
            r9 = 0
            r1.translate(r10, r9)
            r9 = r22
            goto L_0x03f0
        L_0x04d4:
            r22 = r9
            r20.setMatrix(r21)
            int r6 = r6 + 1
            goto L_0x03b8
        L_0x04dd:
            r20.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.TextLayer.drawLayer(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public TextLayer(LottieDrawable lottieDrawable2, Layer layer) {
        super(lottieDrawable2, layer);
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        this.lottieDrawable = lottieDrawable2;
        this.composition = layer.composition;
        AnimatableTextFrame animatableTextFrame = layer.text;
        Objects.requireNonNull(animatableTextFrame);
        TextKeyframeAnimation textKeyframeAnimation = new TextKeyframeAnimation(animatableTextFrame.keyframes);
        this.textAnimation = textKeyframeAnimation;
        textKeyframeAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimation);
        AnimatableTextProperties animatableTextProperties = layer.textProperties;
        if (!(animatableTextProperties == null || (animatableColorValue2 = animatableTextProperties.color) == null)) {
            BaseKeyframeAnimation<Integer, Integer> createAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = createAnimation;
            createAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (!(animatableTextProperties == null || (animatableColorValue = animatableTextProperties.stroke) == null)) {
            BaseKeyframeAnimation<Integer, Integer> createAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = createAnimation2;
            createAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (!(animatableTextProperties == null || (animatableFloatValue2 = animatableTextProperties.strokeWidth) == null)) {
            BaseKeyframeAnimation<Float, Float> createAnimation3 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = createAnimation3;
            createAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (animatableTextProperties != null && (animatableFloatValue = animatableTextProperties.tracking) != null) {
            BaseKeyframeAnimation<Float, Float> createAnimation4 = animatableFloatValue.createAnimation();
            this.trackingAnimation = createAnimation4;
            createAnimation4.addUpdateListener(this);
            addAnimation(this.trackingAnimation);
        }
    }

    public static void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
            }
        }
    }

    public static void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawPath(path, paint);
            }
        }
    }

    public final <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t, lottieValueCallback);
        if (t == LottieProperty.COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorAnimation;
            if (baseKeyframeAnimation != null) {
                baseKeyframeAnimation.setValueCallback(lottieValueCallback);
            } else if (lottieValueCallback == null) {
                if (baseKeyframeAnimation != null) {
                    removeAnimation(baseKeyframeAnimation);
                }
                this.colorAnimation = null;
            } else {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
                this.colorAnimation = valueCallbackKeyframeAnimation;
                valueCallbackKeyframeAnimation.addUpdateListener(this);
                addAnimation(this.colorAnimation);
            }
        } else if (t == LottieProperty.STROKE_COLOR) {
            BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.strokeColorAnimation;
            if (baseKeyframeAnimation2 != null) {
                baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            } else if (lottieValueCallback == null) {
                if (baseKeyframeAnimation2 != null) {
                    removeAnimation(baseKeyframeAnimation2);
                }
                this.strokeColorAnimation = null;
            } else {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
                this.strokeColorAnimation = valueCallbackKeyframeAnimation2;
                valueCallbackKeyframeAnimation2.addUpdateListener(this);
                addAnimation(this.strokeColorAnimation);
            }
        } else if (t == LottieProperty.STROKE_WIDTH) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = this.strokeWidthAnimation;
            if (baseKeyframeAnimation3 != null) {
                baseKeyframeAnimation3.setValueCallback(lottieValueCallback);
            } else if (lottieValueCallback == null) {
                if (baseKeyframeAnimation3 != null) {
                    removeAnimation(baseKeyframeAnimation3);
                }
                this.strokeWidthAnimation = null;
            } else {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
                this.strokeWidthAnimation = valueCallbackKeyframeAnimation3;
                valueCallbackKeyframeAnimation3.addUpdateListener(this);
                addAnimation(this.strokeWidthAnimation);
            }
        } else if (t == LottieProperty.TEXT_TRACKING) {
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation4 = this.trackingAnimation;
            if (baseKeyframeAnimation4 != null) {
                baseKeyframeAnimation4.setValueCallback(lottieValueCallback);
            } else if (lottieValueCallback == null) {
                if (baseKeyframeAnimation4 != null) {
                    removeAnimation(baseKeyframeAnimation4);
                }
                this.trackingAnimation = null;
            } else {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
                this.trackingAnimation = valueCallbackKeyframeAnimation4;
                valueCallbackKeyframeAnimation4.addUpdateListener(this);
                addAnimation(this.trackingAnimation);
            }
        } else if (t != LottieProperty.TEXT_SIZE) {
        } else {
            if (lottieValueCallback == null) {
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = this.textSizeAnimation;
                if (valueCallbackKeyframeAnimation5 != null) {
                    removeAnimation(valueCallbackKeyframeAnimation5);
                }
                this.textSizeAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.textSizeAnimation = valueCallbackKeyframeAnimation6;
            valueCallbackKeyframeAnimation6.addUpdateListener(this);
            addAnimation(this.textSizeAnimation);
        }
    }

    public final void getBounds(RectF rectF2, Matrix matrix2, boolean z) {
        super.getBounds(rectF2, matrix2, z);
        LottieComposition lottieComposition = this.composition;
        Objects.requireNonNull(lottieComposition);
        LottieComposition lottieComposition2 = this.composition;
        Objects.requireNonNull(lottieComposition2);
        rectF2.set(0.0f, 0.0f, (float) lottieComposition.bounds.width(), (float) lottieComposition2.bounds.height());
    }
}
