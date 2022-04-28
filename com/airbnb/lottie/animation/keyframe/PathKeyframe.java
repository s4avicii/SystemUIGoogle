package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

public final class PathKeyframe extends Keyframe<PointF> {
    public Path path;
    public final Keyframe<PointF> pointKeyFrame;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe<PointF> keyframe) {
        super(lottieComposition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        this.pointKeyFrame = keyframe;
        createPath();
    }

    public final void createPath() {
        boolean z;
        T t;
        T t2 = this.endValue;
        if (t2 == null || (t = this.startValue) == null || !((PointF) t).equals(((PointF) t2).x, ((PointF) t2).y)) {
            z = false;
        } else {
            z = true;
        }
        T t3 = this.endValue;
        if (t3 != null && !z) {
            PointF pointF = (PointF) this.startValue;
            PointF pointF2 = (PointF) t3;
            Keyframe<PointF> keyframe = this.pointKeyFrame;
            PointF pointF3 = keyframe.pathCp1;
            PointF pointF4 = keyframe.pathCp2;
            PathMeasure pathMeasure = Utils.pathMeasure;
            Path path2 = new Path();
            path2.moveTo(pointF.x, pointF.y);
            if (pointF3 == null || pointF4 == null || (pointF3.length() == 0.0f && pointF4.length() == 0.0f)) {
                path2.lineTo(pointF2.x, pointF2.y);
            } else {
                float f = pointF.x;
                float f2 = pointF2.x;
                float f3 = pointF2.y;
                path2.cubicTo(pointF3.x + f, pointF.y + pointF3.y, f2 + pointF4.x, f3 + pointF4.y, f2, f3);
            }
            this.path = path2;
        }
    }
}
