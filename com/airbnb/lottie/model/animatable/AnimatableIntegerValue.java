package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import java.util.ArrayList;

public final class AnimatableIntegerValue extends BaseAnimatableValue<Integer, Integer> {
    public final BaseKeyframeAnimation<Integer, Integer> createAnimation() {
        return new IntegerKeyframeAnimation(this.keyframes);
    }

    public AnimatableIntegerValue(ArrayList arrayList) {
        super(arrayList);
    }
}
