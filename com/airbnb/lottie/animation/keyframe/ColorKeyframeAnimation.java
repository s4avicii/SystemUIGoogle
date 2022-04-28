package com.airbnb.lottie.animation.keyframe;

import androidx.leanback.R$style;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public final class ColorKeyframeAnimation extends KeyframeAnimation<Integer> {
    public final int getIntValue(Keyframe<Integer> keyframe, float f) {
        T t = keyframe.startValue;
        if (t == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        int intValue = ((Integer) t).intValue();
        int intValue2 = ((Integer) keyframe.endValue).intValue();
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            keyframe.endFrame.floatValue();
            Integer valueOf = Integer.valueOf(intValue);
            Integer valueOf2 = Integer.valueOf(intValue2);
            getLinearCurrentKeyframeProgress();
            Integer num = (Integer) lottieValueCallback.getValueInternal(valueOf, valueOf2);
            if (num != null) {
                return num.intValue();
            }
        }
        return R$style.evaluate(MiscUtils.clamp(f, 0.0f, 1.0f), intValue, intValue2);
    }

    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }

    public ColorKeyframeAnimation(List<Keyframe<Integer>> list) {
        super(list);
    }
}
