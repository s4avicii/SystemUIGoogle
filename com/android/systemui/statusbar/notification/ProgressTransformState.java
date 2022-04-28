package com.android.systemui.statusbar.notification;

import android.util.Pools;

public final class ProgressTransformState extends TransformState {
    public static Pools.SimplePool<ProgressTransformState> sInstancePool = new Pools.SimplePool<>(40);

    public final boolean sameAs(TransformState transformState) {
        if (transformState instanceof ProgressTransformState) {
            return true;
        }
        return this.mSameAsAny;
    }

    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }
}
