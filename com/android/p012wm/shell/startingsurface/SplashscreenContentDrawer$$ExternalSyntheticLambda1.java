package com.android.p012wm.shell.startingsurface;

import android.animation.ValueAnimator;
import android.graphics.BlendMode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.window.SplashScreenView;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.back.BackAnimationController$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.startingsurface.SplashScreenExitAnimation;
import com.android.systemui.p006qs.tiles.CastTile$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SplashscreenContentDrawer f$0;
    public final /* synthetic */ SplashScreenView f$1;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Rect f$3;
    public final /* synthetic */ Runnable f$4;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda1(SplashscreenContentDrawer splashscreenContentDrawer, SplashScreenView splashScreenView, SurfaceControl surfaceControl, Rect rect, CastTile$$ExternalSyntheticLambda1 castTile$$ExternalSyntheticLambda1) {
        this.f$0 = splashscreenContentDrawer;
        this.f$1 = splashScreenView;
        this.f$2 = surfaceControl;
        this.f$3 = rect;
        this.f$4 = castTile$$ExternalSyntheticLambda1;
    }

    public final void run() {
        SplashscreenContentDrawer splashscreenContentDrawer = this.f$0;
        SplashScreenView splashScreenView = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        Rect rect = this.f$3;
        Runnable runnable = this.f$4;
        Objects.requireNonNull(splashscreenContentDrawer);
        SplashScreenExitAnimation splashScreenExitAnimation = new SplashScreenExitAnimation(splashscreenContentDrawer.mContext, splashScreenView, surfaceControl, rect, splashscreenContentDrawer.mMainWindowShiftLength, splashscreenContentDrawer.mTransactionPool, runnable);
        int height = splashScreenExitAnimation.mSplashScreenView.getHeight() - 0;
        int width = splashScreenExitAnimation.mSplashScreenView.getWidth() / 2;
        int sqrt = (int) (((double) (((float) ((int) Math.sqrt((double) ((width * width) + (height * height))))) * 1.25f)) + 0.5d);
        SplashScreenExitAnimation.RadialVanishAnimation radialVanishAnimation = new SplashScreenExitAnimation.RadialVanishAnimation(splashScreenExitAnimation.mSplashScreenView);
        splashScreenExitAnimation.mRadialVanishAnimation = radialVanishAnimation;
        radialVanishAnimation.mCircleCenter.set(width, 0);
        SplashScreenExitAnimation.RadialVanishAnimation radialVanishAnimation2 = splashScreenExitAnimation.mRadialVanishAnimation;
        Objects.requireNonNull(radialVanishAnimation2);
        radialVanishAnimation2.mInitRadius = 0;
        radialVanishAnimation2.mFinishRadius = sqrt;
        SplashScreenExitAnimation.RadialVanishAnimation radialVanishAnimation3 = splashScreenExitAnimation.mRadialVanishAnimation;
        Objects.requireNonNull(radialVanishAnimation3);
        radialVanishAnimation3.mVanishPaint.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{-1, -1, 0}, new float[]{0.0f, 0.8f, 1.0f}, Shader.TileMode.CLAMP));
        radialVanishAnimation3.mVanishPaint.setBlendMode(BlendMode.DST_OUT);
        SurfaceControl surfaceControl2 = splashScreenExitAnimation.mFirstWindowSurface;
        if (surfaceControl2 != null && surfaceControl2.isValid()) {
            View view = new View(splashScreenExitAnimation.mSplashScreenView.getContext());
            view.setBackgroundColor(splashScreenExitAnimation.mSplashScreenView.getInitBackgroundColor());
            splashScreenExitAnimation.mSplashScreenView.addView(view, new ViewGroup.LayoutParams(-1, splashScreenExitAnimation.mMainWindowShiftLength));
            splashScreenExitAnimation.mShiftUpAnimation = new SplashScreenExitAnimation.ShiftUpAnimation((float) (-splashScreenExitAnimation.mMainWindowShiftLength), view);
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.setDuration((long) splashScreenExitAnimation.mAnimationDuration);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addListener(splashScreenExitAnimation);
        ofFloat.addUpdateListener(new BackAnimationController$$ExternalSyntheticLambda0(splashScreenExitAnimation, 1));
        ofFloat.start();
    }
}
