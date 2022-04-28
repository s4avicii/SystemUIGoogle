package com.android.p012wm.shell.animation;

import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__ReversedViewsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimator$startAction$1 */
/* compiled from: PhysicsAnimator.kt */
public /* synthetic */ class PhysicsAnimator$startAction$1 extends FunctionReferenceImpl implements Function0<Unit> {
    public PhysicsAnimator$startAction$1(Object obj) {
        super(0, obj, PhysicsAnimator.class, "startInternal", "startInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell()V", 0);
    }

    public final Object invoke() {
        PhysicsAnimator physicsAnimator = (PhysicsAnimator) this.receiver;
        Objects.requireNonNull(physicsAnimator);
        T t = physicsAnimator.weakTarget.get();
        if (t == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed object.");
        } else {
            ArrayList arrayList = new ArrayList();
            Set<FloatPropertyCompat<? super T>> keySet = physicsAnimator.springConfigs.keySet();
            Set<FloatPropertyCompat<? super T>> keySet2 = physicsAnimator.flingConfigs.keySet();
            Set<FloatPropertyCompat> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(keySet);
            CollectionsKt__ReversedViewsKt.addAll((Collection) mutableSet, (Collection) keySet2);
            for (FloatPropertyCompat floatPropertyCompat : mutableSet) {
                PhysicsAnimator.FlingConfig flingConfig = physicsAnimator.flingConfigs.get(floatPropertyCompat);
                PhysicsAnimator.SpringConfig springConfig = physicsAnimator.springConfigs.get(floatPropertyCompat);
                float value = floatPropertyCompat.getValue(t);
                if (flingConfig != null) {
                    arrayList.add(new PhysicsAnimator$startInternal$1(flingConfig, physicsAnimator, floatPropertyCompat, t, value));
                }
                if (springConfig != null) {
                    if (flingConfig == null) {
                        ArrayMap<FloatPropertyCompat<? super T>, SpringAnimation> arrayMap = physicsAnimator.springAnimations;
                        SpringAnimation springAnimation = arrayMap.get(floatPropertyCompat);
                        if (springAnimation == null) {
                            springAnimation = new SpringAnimation(t, floatPropertyCompat);
                            PhysicsAnimator$configureDynamicAnimation$1 physicsAnimator$configureDynamicAnimation$1 = new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator, floatPropertyCompat);
                            if (!springAnimation.mRunning) {
                                if (!springAnimation.mUpdateListeners.contains(physicsAnimator$configureDynamicAnimation$1)) {
                                    springAnimation.mUpdateListeners.add(physicsAnimator$configureDynamicAnimation$1);
                                }
                                springAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator, floatPropertyCompat, springAnimation));
                                arrayMap.put(floatPropertyCompat, springAnimation);
                            } else {
                                throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
                            }
                        }
                        SpringAnimation springAnimation2 = springAnimation;
                        if (physicsAnimator.customAnimationHandler != null && !Intrinsics.areEqual(springAnimation2.getAnimationHandler(), physicsAnimator.customAnimationHandler)) {
                            if (springAnimation2.mRunning) {
                                physicsAnimator.cancel(floatPropertyCompat);
                            }
                            AnimationHandler animationHandler = physicsAnimator.customAnimationHandler;
                            if (animationHandler == null) {
                                animationHandler = springAnimation2.getAnimationHandler();
                            }
                            if (!springAnimation2.mRunning) {
                                springAnimation2.mAnimationHandler = animationHandler;
                            } else {
                                throw new AndroidRuntimeException("Animations are still running and the animationhandler should not be set at this timming");
                            }
                        }
                        springConfig.mo15889xe3030f23(springAnimation2);
                        arrayList.add(new PhysicsAnimator$startInternal$2(springAnimation2));
                    } else {
                        physicsAnimator.endListeners.add(0, new PhysicsAnimator$startInternal$3(floatPropertyCompat, flingConfig.min, flingConfig.max, springConfig, physicsAnimator));
                    }
                }
            }
            ArrayList<PhysicsAnimator<T>.InternalListener> arrayList2 = physicsAnimator.internalListeners;
            Set<FloatPropertyCompat<? super T>> keySet3 = physicsAnimator.springConfigs.keySet();
            Set<FloatPropertyCompat<? super T>> keySet4 = physicsAnimator.flingConfigs.keySet();
            Set mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(keySet3);
            CollectionsKt__ReversedViewsKt.addAll((Collection) mutableSet2, (Collection) keySet4);
            arrayList2.add(new PhysicsAnimator.InternalListener(t, mutableSet2, new ArrayList(physicsAnimator.updateListeners), new ArrayList(physicsAnimator.endListeners), new ArrayList(physicsAnimator.endActions)));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            physicsAnimator.springConfigs.clear();
            physicsAnimator.flingConfigs.clear();
            physicsAnimator.updateListeners.clear();
            physicsAnimator.endListeners.clear();
            physicsAnimator.endActions.clear();
        }
        return Unit.INSTANCE;
    }
}
