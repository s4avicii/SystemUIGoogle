package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;
import java.util.Objects;

public final class PathKeyframeAnimation extends KeyframeAnimation<PointF> {
    public PathMeasure pathMeasure = new PathMeasure();
    public PathKeyframe pathMeasureKeyframe;
    public final PointF point = new PointF();
    public final float[] pos = new float[2];

    public final Object getValue(Keyframe keyframe, float f) {
        PathKeyframe pathKeyframe = (PathKeyframe) keyframe;
        Objects.requireNonNull(pathKeyframe);
        Path path = pathKeyframe.path;
        if (path == null) {
            return (PointF) keyframe.startValue;
        }
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            pathKeyframe.endFrame.floatValue();
            T t = pathKeyframe.startValue;
            T t2 = pathKeyframe.endValue;
            getLinearCurrentKeyframeProgress();
            PointF pointF = (PointF) lottieValueCallback.getValueInternal(t, t2);
            if (pointF != null) {
                return pointF;
            }
        }
        if (this.pathMeasureKeyframe != pathKeyframe) {
            this.pathMeasure.setPath(path, false);
            this.pathMeasureKeyframe = pathKeyframe;
        }
        PathMeasure pathMeasure2 = this.pathMeasure;
        pathMeasure2.getPosTan(pathMeasure2.getLength() * f, this.pos, (float[]) null);
        PointF pointF2 = this.point;
        float[] fArr = this.pos;
        pointF2.set(fArr[0], fArr[1]);
        return this.point;
    }

    public PathKeyframeAnimation(List<? extends Keyframe<PointF>> list) {
        super(list);
    }
}
