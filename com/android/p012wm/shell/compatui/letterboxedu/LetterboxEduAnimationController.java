package com.android.p012wm.shell.compatui.letterboxedu;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.IntProperty;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import com.android.internal.R;
import com.android.internal.policy.TransitionAnimation;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;

/* renamed from: com.android.wm.shell.compatui.letterboxedu.LetterboxEduAnimationController */
public final class LetterboxEduAnimationController {
    public static final C18593 DRAWABLE_ALPHA = new IntProperty<Drawable>() {
        public final Object get(Object obj) {
            return Integer.valueOf(((Drawable) obj).getAlpha());
        }

        public final void setValue(Object obj, int i) {
            ((Drawable) obj).setAlpha(i);
        }
    };
    public final int mAnimStyleResId;
    public Animator mBackgroundDimAnimator;
    public Animation mDialogAnimation;
    public final String mPackageName;
    public final TransitionAnimation mTransitionAnimation;

    public final void cancelAnimation() {
        Animation animation = this.mDialogAnimation;
        if (animation != null) {
            animation.cancel();
            this.mDialogAnimation = null;
        }
        Animator animator = this.mBackgroundDimAnimator;
        if (animator != null) {
            animator.cancel();
            this.mBackgroundDimAnimator = null;
        }
    }

    public final Animation loadAnimation(int i) {
        Animation loadAnimationAttr = this.mTransitionAnimation.loadAnimationAttr(this.mPackageName, this.mAnimStyleResId, i, false);
        if (loadAnimationAttr == null) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Failed to load animation ", i, "LetterboxEduAnimation");
        }
        return loadAnimationAttr;
    }

    public LetterboxEduAnimationController(Context context) {
        this.mTransitionAnimation = new TransitionAnimation(context, false, "LetterboxEduAnimation");
        this.mAnimStyleResId = new ContextThemeWrapper(context, 16974550).getTheme().obtainStyledAttributes(R.styleable.Window).getResourceId(8, 0);
        this.mPackageName = context.getPackageName();
    }
}
