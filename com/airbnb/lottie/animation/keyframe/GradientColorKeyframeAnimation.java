package com.airbnb.lottie.animation.keyframe;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.leanback.R$style;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Objects;

public final class GradientColorKeyframeAnimation extends KeyframeAnimation<GradientColor> {
    public final GradientColor gradientColor;

    public final Object getValue(Keyframe keyframe, float f) {
        GradientColor gradientColor2 = this.gradientColor;
        GradientColor gradientColor3 = (GradientColor) keyframe.startValue;
        GradientColor gradientColor4 = (GradientColor) keyframe.endValue;
        Objects.requireNonNull(gradientColor2);
        if (gradientColor3.colors.length == gradientColor4.colors.length) {
            int i = 0;
            while (true) {
                int[] iArr = gradientColor3.colors;
                if (i >= iArr.length) {
                    return this.gradientColor;
                }
                float[] fArr = gradientColor2.positions;
                float f2 = gradientColor3.positions[i];
                float f3 = gradientColor4.positions[i];
                PointF pointF = MiscUtils.pathFromDataCurrentPoint;
                fArr[i] = MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, f, f2);
                gradientColor2.colors[i] = R$style.evaluate(f, iArr[i], gradientColor4.colors[i]);
                i++;
            }
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot interpolate between gradients. Lengths vary (");
            m.append(gradientColor3.colors.length);
            m.append(" vs ");
            m.append(gradientColor4.colors.length);
            m.append(")");
            throw new IllegalArgumentException(m.toString());
        }
    }

    public GradientColorKeyframeAnimation(List<Keyframe<GradientColor>> list) {
        super(list);
        int i = 0;
        GradientColor gradientColor2 = (GradientColor) list.get(0).startValue;
        i = gradientColor2 != null ? gradientColor2.colors.length : i;
        this.gradientColor = new GradientColor(new float[i], new int[i]);
    }
}
