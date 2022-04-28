package com.android.p012wm.shell.animation;

import android.util.ArrayMap;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt__ReversedViewsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimator$configureDynamicAnimation$2 */
/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator$configureDynamicAnimation$2 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ DynamicAnimation<?> $anim;
    public final /* synthetic */ FloatPropertyCompat<Object> $property;
    public final /* synthetic */ PhysicsAnimator<Object> this$0;

    public PhysicsAnimator$configureDynamicAnimation$2(PhysicsAnimator<Object> physicsAnimator, FloatPropertyCompat<Object> floatPropertyCompat, DynamicAnimation<?> dynamicAnimation) {
        this.this$0 = physicsAnimator;
        this.$property = floatPropertyCompat;
        this.$anim = dynamicAnimation;
    }

    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        PhysicsAnimator<Object> physicsAnimator = this.this$0;
        Objects.requireNonNull(physicsAnimator);
        ArrayList<PhysicsAnimator<T>.InternalListener> arrayList = physicsAnimator.internalListeners;
        final FloatPropertyCompat<Object> floatPropertyCompat = this.$property;
        final DynamicAnimation<?> dynamicAnimation2 = this.$anim;
        final boolean z2 = z;
        final float f3 = f;
        final float f4 = f2;
        CollectionsKt__ReversedViewsKt.removeAll(arrayList, new Function1<PhysicsAnimator<Object>.InternalListener, Boolean>() {
            public final Object invoke(Object obj) {
                boolean z;
                PhysicsAnimator.InternalListener internalListener = (PhysicsAnimator.InternalListener) obj;
                FloatPropertyCompat<Object> floatPropertyCompat = floatPropertyCompat;
                boolean z2 = z2;
                float f = f3;
                float f2 = f4;
                boolean z3 = dynamicAnimation2 instanceof FlingAnimation;
                if (!internalListener.properties.contains(floatPropertyCompat)) {
                    z = false;
                } else {
                    internalListener.numPropertiesAnimating--;
                    internalListener.maybeDispatchUpdates();
                    if (internalListener.undispatchedUpdates.containsKey(floatPropertyCompat)) {
                        for (PhysicsAnimator.UpdateListener onAnimationUpdateForProperty : internalListener.updateListeners) {
                            T t = internalListener.target;
                            new ArrayMap().put(floatPropertyCompat, internalListener.undispatchedUpdates.get(floatPropertyCompat));
                            onAnimationUpdateForProperty.onAnimationUpdateForProperty(t);
                        }
                        internalListener.undispatchedUpdates.remove(floatPropertyCompat);
                    }
                    z = !PhysicsAnimator.this.arePropertiesAnimating(internalListener.properties);
                    List<? extends PhysicsAnimator.EndListener<T>> list = internalListener.endListeners;
                    PhysicsAnimator<T> physicsAnimator = PhysicsAnimator.this;
                    Iterator<T> it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ((PhysicsAnimator.EndListener) it.next()).onAnimationEnd(internalListener.target, floatPropertyCompat, z3, z2, f, f2);
                            if (physicsAnimator.isPropertyAnimating(floatPropertyCompat)) {
                                break;
                            }
                        } else if (z && !z2) {
                            for (Function0 invoke : internalListener.endActions) {
                                invoke.invoke();
                            }
                        }
                    }
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        if (Intrinsics.areEqual(this.this$0.springAnimations.get(this.$property), this.$anim)) {
            this.this$0.springAnimations.remove(this.$property);
        }
        if (Intrinsics.areEqual(this.this$0.flingAnimations.get(this.$property), this.$anim)) {
            this.this$0.flingAnimations.remove(this.$property);
        }
    }
}
