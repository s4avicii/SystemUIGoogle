package com.android.p012wm.shell.animation;

import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimator$startInternal$3 */
/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator$startInternal$3 implements PhysicsAnimator.EndListener<Object> {
    public final /* synthetic */ FloatPropertyCompat<Object> $animatedProperty;
    public final /* synthetic */ float $flingMax;
    public final /* synthetic */ float $flingMin;
    public final /* synthetic */ PhysicsAnimator.SpringConfig $springConfig;
    public final /* synthetic */ PhysicsAnimator<Object> this$0;

    public PhysicsAnimator$startInternal$3(FloatPropertyCompat<Object> floatPropertyCompat, float f, float f2, PhysicsAnimator.SpringConfig springConfig, PhysicsAnimator<Object> physicsAnimator) {
        this.$animatedProperty = floatPropertyCompat;
        this.$flingMin = f;
        this.$flingMax = f2;
        this.$springConfig = springConfig;
        this.this$0 = physicsAnimator;
    }

    public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2) {
        boolean z3;
        boolean z4;
        float f3;
        if (Intrinsics.areEqual(floatPropertyCompat, this.$animatedProperty) && z && !z2) {
            boolean z5 = true;
            if (Math.abs(f2) > 0.0f) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (this.$flingMin > f || f > this.$flingMax) {
                z4 = false;
            } else {
                z4 = true;
            }
            boolean z6 = !z4;
            if (z3 || z6) {
                PhysicsAnimator.SpringConfig springConfig = this.$springConfig;
                Objects.requireNonNull(springConfig);
                springConfig.startVelocity = f2;
                PhysicsAnimator.SpringConfig springConfig2 = this.$springConfig;
                Objects.requireNonNull(springConfig2);
                float f4 = springConfig2.finalPosition;
                WeakHashMap<Object, PhysicsAnimator<?>> weakHashMap = PhysicsAnimatorKt.animators;
                if (f4 != -3.4028235E38f) {
                    z5 = false;
                }
                if (z5) {
                    if (z3) {
                        PhysicsAnimator.SpringConfig springConfig3 = this.$springConfig;
                        if (f2 < 0.0f) {
                            f3 = this.$flingMin;
                        } else {
                            f3 = this.$flingMax;
                        }
                        Objects.requireNonNull(springConfig3);
                        springConfig3.finalPosition = f3;
                    } else if (z6) {
                        PhysicsAnimator.SpringConfig springConfig4 = this.$springConfig;
                        float f5 = this.$flingMin;
                        if (f >= f5) {
                            f5 = this.$flingMax;
                        }
                        Objects.requireNonNull(springConfig4);
                        springConfig4.finalPosition = f5;
                    }
                }
                PhysicsAnimator<Object> physicsAnimator = this.this$0;
                FloatPropertyCompat<Object> floatPropertyCompat2 = this.$animatedProperty;
                Objects.requireNonNull(physicsAnimator);
                ArrayMap<FloatPropertyCompat<? super T>, SpringAnimation> arrayMap = physicsAnimator.springAnimations;
                SpringAnimation springAnimation = arrayMap.get(floatPropertyCompat2);
                if (springAnimation == null) {
                    springAnimation = new SpringAnimation(obj, floatPropertyCompat2);
                    PhysicsAnimator$configureDynamicAnimation$1 physicsAnimator$configureDynamicAnimation$1 = new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator, floatPropertyCompat2);
                    if (!springAnimation.mRunning) {
                        if (!springAnimation.mUpdateListeners.contains(physicsAnimator$configureDynamicAnimation$1)) {
                            springAnimation.mUpdateListeners.add(physicsAnimator$configureDynamicAnimation$1);
                        }
                        springAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator, floatPropertyCompat2, springAnimation));
                        arrayMap.put(floatPropertyCompat2, springAnimation);
                    } else {
                        throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
                    }
                }
                SpringAnimation springAnimation2 = springAnimation;
                AnimationHandler animationHandler = this.this$0.customAnimationHandler;
                if (animationHandler == null) {
                    animationHandler = springAnimation2.getAnimationHandler();
                }
                if (!springAnimation2.mRunning) {
                    springAnimation2.mAnimationHandler = animationHandler;
                    this.$springConfig.mo15889xe3030f23(springAnimation2);
                    springAnimation2.start();
                    return;
                }
                throw new AndroidRuntimeException("Animations are still running and the animationhandler should not be set at this timming");
            }
        }
    }
}
