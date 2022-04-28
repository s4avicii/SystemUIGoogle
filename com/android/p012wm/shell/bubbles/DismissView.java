package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.common.DismissCircleView;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.wm.shell.bubbles.DismissView */
/* compiled from: DismissView.kt */
public final class DismissView extends FrameLayout {
    public final int DISMISS_SCRIM_FADE_MS = 200;
    public final PhysicsAnimator<DismissCircleView> animator;
    public DismissCircleView circle;
    public boolean isShowing;
    public final PhysicsAnimator.SpringConfig spring = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);

    /* renamed from: wm */
    public WindowManager f120wm;

    public final void hide() {
        if (this.isShowing) {
            this.isShowing = false;
            Drawable background = getBackground();
            Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.TransitionDrawable");
            ((TransitionDrawable) background).reverseTransition(this.DISMISS_SCRIM_FADE_MS);
            PhysicsAnimator<DismissCircleView> physicsAnimator = this.animator;
            PhysicsAnimator.SpringConfig springConfig = this.spring;
            Objects.requireNonNull(physicsAnimator);
            physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, (float) getHeight(), 0.0f, springConfig);
            physicsAnimator.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(new Function0[]{new DismissView$hide$1(this)}));
            physicsAnimator.start();
        }
    }

    public final void updatePadding() {
        setPadding(0, 0, 0, getResources().getDimensionPixelSize(C1777R.dimen.floating_dismiss_bottom_margin) + this.f120wm.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars()).bottom);
    }

    public DismissView(Context context) {
        super(context);
        DismissCircleView dismissCircleView = new DismissCircleView(context);
        this.circle = dismissCircleView;
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        this.animator = PhysicsAnimator.Companion.getInstance(dismissCircleView);
        Object systemService = context.getSystemService("window");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.f120wm = (WindowManager) systemService;
        setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(C1777R.dimen.floating_dismiss_gradient_height), 80));
        updatePadding();
        setClipToPadding(false);
        setClipChildren(false);
        setVisibility(4);
        setBackgroundResource(C1777R.C1778drawable.floating_dismiss_gradient_transition);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.dismiss_circle_size);
        addView(this.circle, new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 81));
        this.circle.setTranslationY((float) getResources().getDimensionPixelSize(C1777R.dimen.floating_dismiss_gradient_height));
    }
}
