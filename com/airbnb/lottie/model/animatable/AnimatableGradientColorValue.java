package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.model.content.GradientColor;
import java.util.ArrayList;

public final class AnimatableGradientColorValue extends BaseAnimatableValue<GradientColor, GradientColor> {
    public final BaseKeyframeAnimation<GradientColor, GradientColor> createAnimation() {
        return new GradientColorKeyframeAnimation(this.keyframes);
    }

    public AnimatableGradientColorValue(ArrayList arrayList) {
        super(arrayList);
    }
}
