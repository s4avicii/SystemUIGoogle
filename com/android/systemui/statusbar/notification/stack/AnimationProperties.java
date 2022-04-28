package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import java.util.function.Consumer;

public class AnimationProperties {
    public long delay;
    public long duration;
    public Consumer<Property> mAnimationEndAction;
    public ArrayMap<Property, Interpolator> mInterpolatorMap;

    public boolean wasAdded(View view) {
        return false;
    }

    public AnimationFilter getAnimationFilter() {
        return new AnimationFilter() {
            public final boolean shouldAnimateProperty(Property property) {
                return true;
            }
        };
    }

    public AnimatorListenerAdapter getAnimationFinishListener(final Property property) {
        final Consumer<Property> consumer = this.mAnimationEndAction;
        if (consumer == null) {
            return null;
        }
        return new AnimatorListenerAdapter() {
            public boolean mCancelled;

            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                if (!this.mCancelled) {
                    consumer.accept(property);
                }
            }
        };
    }

    public final AnimationProperties setCustomInterpolator(Property property, PathInterpolator pathInterpolator) {
        if (this.mInterpolatorMap == null) {
            this.mInterpolatorMap = new ArrayMap<>();
        }
        this.mInterpolatorMap.put(property, pathInterpolator);
        return this;
    }
}
