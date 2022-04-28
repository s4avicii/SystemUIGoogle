package com.android.systemui.statusbar.notification;

import android.util.Pools;

public final class ActionListTransformState extends TransformState {
    public static Pools.SimplePool<ActionListTransformState> sInstancePool = new Pools.SimplePool<>(40);

    public final void transformViewFullyFrom(TransformState transformState, float f) {
    }

    public final void transformViewFullyTo(TransformState transformState, float f) {
    }

    public final void resetTransformedView() {
        float translationY = this.mTransformedView.getTranslationY();
        super.resetTransformedView();
        this.mTransformedView.setTranslationY(translationY);
    }

    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    public final boolean sameAs(TransformState transformState) {
        return transformState instanceof ActionListTransformState;
    }
}
