package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ScaleKeyframeAnimation;
import com.airbnb.lottie.value.ScaleXY;
import java.util.ArrayList;

public final class AnimatableScaleValue extends BaseAnimatableValue<ScaleXY, ScaleXY> {
    public final BaseKeyframeAnimation<ScaleXY, ScaleXY> createAnimation() {
        return new ScaleKeyframeAnimation(this.keyframes);
    }

    public AnimatableScaleValue(ArrayList arrayList) {
        super(arrayList);
    }
}
