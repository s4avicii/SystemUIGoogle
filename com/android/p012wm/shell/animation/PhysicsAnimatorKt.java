package com.android.p012wm.shell.animation;

import com.android.p012wm.shell.animation.PhysicsAnimator;
import java.util.WeakHashMap;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimatorKt */
/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimatorKt {
    public static final WeakHashMap<Object, PhysicsAnimator<?>> animators = new WeakHashMap<>();
    public static final PhysicsAnimator.FlingConfig globalDefaultFling = new PhysicsAnimator.FlingConfig(1.0f, -3.4028235E38f, Float.MAX_VALUE, 0.0f);
    public static final PhysicsAnimator.SpringConfig globalDefaultSpring = new PhysicsAnimator.SpringConfig(1500.0f, 0.5f);
}
