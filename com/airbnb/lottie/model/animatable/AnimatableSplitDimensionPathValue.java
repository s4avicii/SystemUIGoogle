package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.SplitDimensionPathKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public final class AnimatableSplitDimensionPathValue implements AnimatableValue<PointF, PointF> {
    public final AnimatableFloatValue animatableXDimension;
    public final AnimatableFloatValue animatableYDimension;

    public final BaseKeyframeAnimation<PointF, PointF> createAnimation() {
        return new SplitDimensionPathKeyframeAnimation((FloatKeyframeAnimation) this.animatableXDimension.createAnimation(), (FloatKeyframeAnimation) this.animatableYDimension.createAnimation());
    }

    public final List<Keyframe<PointF>> getKeyframes() {
        throw new UnsupportedOperationException("Cannot call getKeyframes on AnimatableSplitDimensionPathValue.");
    }

    public final boolean isStatic() {
        if (!this.animatableXDimension.isStatic() || !this.animatableYDimension.isStatic()) {
            return false;
        }
        return true;
    }

    public AnimatableSplitDimensionPathValue(AnimatableFloatValue animatableFloatValue, AnimatableFloatValue animatableFloatValue2) {
        this.animatableXDimension = animatableFloatValue;
        this.animatableYDimension = animatableFloatValue2;
    }
}
