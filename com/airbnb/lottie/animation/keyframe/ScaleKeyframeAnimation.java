package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;
import java.util.Objects;

public final class ScaleKeyframeAnimation extends KeyframeAnimation<ScaleXY> {
    public final ScaleXY scaleXY = new ScaleXY();

    public final Object getValue(Keyframe keyframe, float f) {
        T t;
        T t2 = keyframe.startValue;
        if (t2 == null || (t = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY scaleXY2 = (ScaleXY) t2;
        ScaleXY scaleXY3 = (ScaleXY) t;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            keyframe.endFrame.floatValue();
            getLinearCurrentKeyframeProgress();
            ScaleXY scaleXY4 = (ScaleXY) lottieValueCallback.getValueInternal(scaleXY2, scaleXY3);
            if (scaleXY4 != null) {
                return scaleXY4;
            }
        }
        ScaleXY scaleXY5 = this.scaleXY;
        float f2 = scaleXY2.scaleX;
        float f3 = scaleXY3.scaleX;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        float m = MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, f, f2);
        float f4 = scaleXY2.scaleY;
        Objects.requireNonNull(scaleXY5);
        scaleXY5.scaleX = m;
        scaleXY5.scaleY = ((scaleXY3.scaleY - f4) * f) + f4;
        return this.scaleXY;
    }

    public ScaleKeyframeAnimation(List<Keyframe<ScaleXY>> list) {
        super(list);
    }
}
