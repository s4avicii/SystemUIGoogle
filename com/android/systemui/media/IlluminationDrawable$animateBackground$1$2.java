package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: IlluminationDrawable.kt */
public final class IlluminationDrawable$animateBackground$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ IlluminationDrawable this$0;

    public IlluminationDrawable$animateBackground$1$2(IlluminationDrawable illuminationDrawable) {
        this.this$0 = illuminationDrawable;
    }

    public final void onAnimationEnd(Animator animator) {
        this.this$0.backgroundAnimation = null;
    }
}
