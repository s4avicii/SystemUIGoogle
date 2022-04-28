package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.Collections;

public final class AnimatableFloatValue extends BaseAnimatableValue<Float, Float> {
    public AnimatableFloatValue() {
        super(Collections.singletonList(new Keyframe(Float.valueOf(0.0f))));
    }

    public final BaseKeyframeAnimation<Float, Float> createAnimation() {
        return new FloatKeyframeAnimation(this.keyframes);
    }

    public AnimatableFloatValue(ArrayList arrayList) {
        super(arrayList);
    }
}
