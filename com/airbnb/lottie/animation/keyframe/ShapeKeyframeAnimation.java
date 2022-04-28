package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public final class ShapeKeyframeAnimation extends BaseKeyframeAnimation<ShapeData, Path> {
    public final Path tempPath = new Path();
    public final ShapeData tempShapeData = new ShapeData();

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00dd A[LOOP:2: B:26:0x00db->B:27:0x00dd, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x01cb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object getValue(com.airbnb.lottie.value.Keyframe r14, float r15) {
        /*
            r13 = this;
            T r0 = r14.startValue
            com.airbnb.lottie.model.content.ShapeData r0 = (com.airbnb.lottie.model.content.ShapeData) r0
            T r14 = r14.endValue
            com.airbnb.lottie.model.content.ShapeData r14 = (com.airbnb.lottie.model.content.ShapeData) r14
            com.airbnb.lottie.model.content.ShapeData r1 = r13.tempShapeData
            java.util.Objects.requireNonNull(r1)
            android.graphics.PointF r2 = r1.initialPoint
            if (r2 != 0) goto L_0x0018
            android.graphics.PointF r2 = new android.graphics.PointF
            r2.<init>()
            r1.initialPoint = r2
        L_0x0018:
            java.util.Objects.requireNonNull(r0)
            boolean r2 = r0.closed
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x002b
            java.util.Objects.requireNonNull(r14)
            boolean r2 = r14.closed
            if (r2 == 0) goto L_0x0029
            goto L_0x002b
        L_0x0029:
            r2 = r3
            goto L_0x002c
        L_0x002b:
            r2 = r4
        L_0x002c:
            r1.closed = r2
            java.util.ArrayList r2 = r0.curves
            int r2 = r2.size()
            java.util.Objects.requireNonNull(r14)
            java.util.ArrayList r5 = r14.curves
            int r5 = r5.size()
            if (r2 == r5) goto L_0x0063
            java.lang.String r2 = "Curves must have the same number of control points. Shape 1: "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.util.ArrayList r5 = r0.curves
            int r5 = r5.size()
            r2.append(r5)
            java.lang.String r5 = "\tShape 2: "
            r2.append(r5)
            java.util.ArrayList r5 = r14.curves
            int r5 = r5.size()
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            com.airbnb.lottie.utils.Logger.warning(r2)
        L_0x0063:
            java.util.ArrayList r2 = r0.curves
            int r2 = r2.size()
            java.util.ArrayList r5 = r14.curves
            int r5 = r5.size()
            int r2 = java.lang.Math.min(r2, r5)
            java.util.ArrayList r5 = r1.curves
            int r5 = r5.size()
            if (r5 >= r2) goto L_0x0090
            java.util.ArrayList r5 = r1.curves
            int r5 = r5.size()
        L_0x0081:
            if (r5 >= r2) goto L_0x00ae
            java.util.ArrayList r6 = r1.curves
            com.airbnb.lottie.model.CubicCurveData r7 = new com.airbnb.lottie.model.CubicCurveData
            r7.<init>()
            r6.add(r7)
            int r5 = r5 + 1
            goto L_0x0081
        L_0x0090:
            java.util.ArrayList r5 = r1.curves
            int r5 = r5.size()
            if (r5 <= r2) goto L_0x00ae
            java.util.ArrayList r5 = r1.curves
            int r5 = r5.size()
            int r5 = r5 - r4
        L_0x009f:
            if (r5 < r2) goto L_0x00ae
            java.util.ArrayList r6 = r1.curves
            int r7 = r6.size()
            int r7 = r7 - r4
            r6.remove(r7)
            int r5 = r5 + -1
            goto L_0x009f
        L_0x00ae:
            android.graphics.PointF r2 = r0.initialPoint
            android.graphics.PointF r5 = r14.initialPoint
            float r6 = r2.x
            float r7 = r5.x
            android.graphics.PointF r8 = com.airbnb.lottie.utils.MiscUtils.pathFromDataCurrentPoint
            float r6 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r7, r6, r15, r6)
            float r2 = r2.y
            float r5 = r5.y
            float r2 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r5, r2, r15, r2)
            android.graphics.PointF r5 = r1.initialPoint
            if (r5 != 0) goto L_0x00cf
            android.graphics.PointF r5 = new android.graphics.PointF
            r5.<init>()
            r1.initialPoint = r5
        L_0x00cf:
            android.graphics.PointF r5 = r1.initialPoint
            r5.set(r6, r2)
            java.util.ArrayList r2 = r1.curves
            int r2 = r2.size()
            int r2 = r2 - r4
        L_0x00db:
            if (r2 < 0) goto L_0x0160
            java.util.ArrayList r4 = r0.curves
            java.lang.Object r4 = r4.get(r2)
            com.airbnb.lottie.model.CubicCurveData r4 = (com.airbnb.lottie.model.CubicCurveData) r4
            java.util.ArrayList r5 = r14.curves
            java.lang.Object r5 = r5.get(r2)
            com.airbnb.lottie.model.CubicCurveData r5 = (com.airbnb.lottie.model.CubicCurveData) r5
            java.util.Objects.requireNonNull(r4)
            android.graphics.PointF r6 = r4.controlPoint1
            android.graphics.PointF r7 = r4.controlPoint2
            android.graphics.PointF r4 = r4.vertex
            java.util.Objects.requireNonNull(r5)
            android.graphics.PointF r8 = r5.controlPoint1
            android.graphics.PointF r9 = r5.controlPoint2
            android.graphics.PointF r5 = r5.vertex
            java.util.ArrayList r10 = r1.curves
            java.lang.Object r10 = r10.get(r2)
            com.airbnb.lottie.model.CubicCurveData r10 = (com.airbnb.lottie.model.CubicCurveData) r10
            float r11 = r6.x
            float r12 = r8.x
            float r11 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r12, r11, r15, r11)
            float r6 = r6.y
            float r8 = r8.y
            float r8 = r8 - r6
            float r8 = r8 * r15
            float r8 = r8 + r6
            java.util.Objects.requireNonNull(r10)
            android.graphics.PointF r6 = r10.controlPoint1
            r6.set(r11, r8)
            java.util.ArrayList r6 = r1.curves
            java.lang.Object r6 = r6.get(r2)
            com.airbnb.lottie.model.CubicCurveData r6 = (com.airbnb.lottie.model.CubicCurveData) r6
            float r8 = r7.x
            float r10 = r9.x
            float r8 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r10, r8, r15, r8)
            float r7 = r7.y
            float r9 = r9.y
            float r9 = r9 - r7
            float r9 = r9 * r15
            float r9 = r9 + r7
            java.util.Objects.requireNonNull(r6)
            android.graphics.PointF r6 = r6.controlPoint2
            r6.set(r8, r9)
            java.util.ArrayList r6 = r1.curves
            java.lang.Object r6 = r6.get(r2)
            com.airbnb.lottie.model.CubicCurveData r6 = (com.airbnb.lottie.model.CubicCurveData) r6
            float r7 = r4.x
            float r8 = r5.x
            float r7 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0.m7m(r8, r7, r15, r7)
            float r4 = r4.y
            float r5 = r5.y
            float r5 = r5 - r4
            float r5 = r5 * r15
            float r5 = r5 + r4
            java.util.Objects.requireNonNull(r6)
            android.graphics.PointF r4 = r6.vertex
            r4.set(r7, r5)
            int r2 = r2 + -1
            goto L_0x00db
        L_0x0160:
            com.airbnb.lottie.model.content.ShapeData r14 = r13.tempShapeData
            android.graphics.Path r15 = r13.tempPath
            r15.reset()
            java.util.Objects.requireNonNull(r14)
            android.graphics.PointF r0 = r14.initialPoint
            float r1 = r0.x
            float r2 = r0.y
            r15.moveTo(r1, r2)
            android.graphics.PointF r1 = com.airbnb.lottie.utils.MiscUtils.pathFromDataCurrentPoint
            float r2 = r0.x
            float r0 = r0.y
            r1.set(r2, r0)
        L_0x017c:
            java.util.ArrayList r0 = r14.curves
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x01c7
            java.util.ArrayList r0 = r14.curves
            java.lang.Object r0 = r0.get(r3)
            com.airbnb.lottie.model.CubicCurveData r0 = (com.airbnb.lottie.model.CubicCurveData) r0
            java.util.Objects.requireNonNull(r0)
            android.graphics.PointF r1 = r0.controlPoint1
            android.graphics.PointF r2 = r0.controlPoint2
            android.graphics.PointF r0 = r0.vertex
            android.graphics.PointF r4 = com.airbnb.lottie.utils.MiscUtils.pathFromDataCurrentPoint
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x01ab
            boolean r4 = r2.equals(r0)
            if (r4 == 0) goto L_0x01ab
            float r1 = r0.x
            float r2 = r0.y
            r15.lineTo(r1, r2)
            goto L_0x01bb
        L_0x01ab:
            float r5 = r1.x
            float r6 = r1.y
            float r7 = r2.x
            float r8 = r2.y
            float r9 = r0.x
            float r10 = r0.y
            r4 = r15
            r4.cubicTo(r5, r6, r7, r8, r9, r10)
        L_0x01bb:
            android.graphics.PointF r1 = com.airbnb.lottie.utils.MiscUtils.pathFromDataCurrentPoint
            float r2 = r0.x
            float r0 = r0.y
            r1.set(r2, r0)
            int r3 = r3 + 1
            goto L_0x017c
        L_0x01c7:
            boolean r14 = r14.closed
            if (r14 == 0) goto L_0x01ce
            r15.close()
        L_0x01ce:
            android.graphics.Path r13 = r13.tempPath
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.keyframe.ShapeKeyframeAnimation.getValue(com.airbnb.lottie.value.Keyframe, float):java.lang.Object");
    }

    public ShapeKeyframeAnimation(List<Keyframe<ShapeData>> list) {
        super(list);
    }
}
