package com.airbnb.lottie.model.animatable;

import android.graphics.Path;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ShapeKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeData;
import java.util.ArrayList;

public final class AnimatableShapeValue extends BaseAnimatableValue<ShapeData, Path> {
    public final BaseKeyframeAnimation<ShapeData, Path> createAnimation() {
        return new ShapeKeyframeAnimation(this.keyframes);
    }

    public AnimatableShapeValue(ArrayList arrayList) {
        super(arrayList);
    }
}
