package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.leanback.R$fraction;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.MotionSpec;
import java.util.ArrayList;
import java.util.Objects;

public abstract class BaseMotionStrategy implements MotionStrategy {
    public final Context context;
    public MotionSpec defaultMotionSpec;
    public final ExtendedFloatingActionButton fab;
    public final ArrayList<Animator.AnimatorListener> listeners = new ArrayList<>();
    public MotionSpec motionSpec;
    public final CodedOutputByteBufferNano tracker;

    public AnimatorSet createAnimator() {
        MotionSpec motionSpec2 = this.motionSpec;
        if (motionSpec2 == null) {
            if (this.defaultMotionSpec == null) {
                this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
            }
            motionSpec2 = this.defaultMotionSpec;
            Objects.requireNonNull(motionSpec2);
        }
        return createAnimator(motionSpec2);
    }

    public void onAnimationCancel() {
        CodedOutputByteBufferNano codedOutputByteBufferNano = this.tracker;
        Objects.requireNonNull(codedOutputByteBufferNano);
        codedOutputByteBufferNano.buffer = null;
    }

    public void onAnimationStart(Animator animator) {
        CodedOutputByteBufferNano codedOutputByteBufferNano = this.tracker;
        Objects.requireNonNull(codedOutputByteBufferNano);
        Animator animator2 = (Animator) codedOutputByteBufferNano.buffer;
        if (animator2 != null) {
            animator2.cancel();
        }
        codedOutputByteBufferNano.buffer = animator;
    }

    public BaseMotionStrategy(ExtendedFloatingActionButton extendedFloatingActionButton, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        this.fab = extendedFloatingActionButton;
        this.context = extendedFloatingActionButton.getContext();
        this.tracker = codedOutputByteBufferNano;
    }

    public final AnimatorSet createAnimator(MotionSpec motionSpec2) {
        ArrayList arrayList = new ArrayList();
        if (motionSpec2.hasPropertyValues("opacity")) {
            arrayList.add(motionSpec2.getAnimator("opacity", this.fab, View.ALPHA));
        }
        if (motionSpec2.hasPropertyValues("scale")) {
            arrayList.add(motionSpec2.getAnimator("scale", this.fab, View.SCALE_Y));
            arrayList.add(motionSpec2.getAnimator("scale", this.fab, View.SCALE_X));
        }
        if (motionSpec2.hasPropertyValues("width")) {
            arrayList.add(motionSpec2.getAnimator("width", this.fab, ExtendedFloatingActionButton.WIDTH));
        }
        if (motionSpec2.hasPropertyValues("height")) {
            arrayList.add(motionSpec2.getAnimator("height", this.fab, ExtendedFloatingActionButton.HEIGHT));
        }
        if (motionSpec2.hasPropertyValues("paddingStart")) {
            arrayList.add(motionSpec2.getAnimator("paddingStart", this.fab, ExtendedFloatingActionButton.PADDING_START));
        }
        if (motionSpec2.hasPropertyValues("paddingEnd")) {
            arrayList.add(motionSpec2.getAnimator("paddingEnd", this.fab, ExtendedFloatingActionButton.PADDING_END));
        }
        if (motionSpec2.hasPropertyValues("labelOpacity")) {
            arrayList.add(motionSpec2.getAnimator("labelOpacity", this.fab, new Property<ExtendedFloatingActionButton, Float>(this) {
                public final /* synthetic */ BaseMotionStrategy this$0;

                {
                    Class<Float> cls = Float.class;
                    this.this$0 = r2;
                }

                public final Object get(Object obj) {
                    ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) obj;
                    float alpha = (((float) Color.alpha(extendedFloatingActionButton.getCurrentTextColor())) / 255.0f) / ((float) Color.alpha(extendedFloatingActionButton.originalTextCsl.getColorForState(extendedFloatingActionButton.getDrawableState(), this.this$0.fab.originalTextCsl.getDefaultColor())));
                    LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
                    return Float.valueOf((alpha * 1.0f) + 0.0f);
                }

                public final void set(Object obj, Object obj2) {
                    ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) obj;
                    Float f = (Float) obj2;
                    int colorForState = extendedFloatingActionButton.originalTextCsl.getColorForState(extendedFloatingActionButton.getDrawableState(), this.this$0.fab.originalTextCsl.getDefaultColor());
                    float floatValue = f.floatValue();
                    LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
                    ColorStateList valueOf = ColorStateList.valueOf(Color.argb((int) (((((((float) Color.alpha(colorForState)) / 255.0f) - 0.0f) * floatValue) + 0.0f) * 255.0f), Color.red(colorForState), Color.green(colorForState), Color.blue(colorForState)));
                    if (f.floatValue() == 1.0f) {
                        extendedFloatingActionButton.silentlyUpdateTextColor(extendedFloatingActionButton.originalTextCsl);
                    } else {
                        extendedFloatingActionButton.silentlyUpdateTextColor(valueOf);
                    }
                }
            }));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        R$fraction.playTogether(animatorSet, arrayList);
        return animatorSet;
    }
}
