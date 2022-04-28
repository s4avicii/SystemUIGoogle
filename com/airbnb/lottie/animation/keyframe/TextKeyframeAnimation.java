package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public final class TextKeyframeAnimation extends KeyframeAnimation<DocumentData> {
    public final Object getValue(Keyframe keyframe, float f) {
        return (DocumentData) keyframe.startValue;
    }

    public TextKeyframeAnimation(List<Keyframe<DocumentData>> list) {
        super(list);
    }
}
