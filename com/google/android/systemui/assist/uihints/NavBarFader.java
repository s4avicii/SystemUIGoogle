package com.google.android.systemui.assist.uihints;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import java.util.Objects;

/* compiled from: NavBarFader.kt */
public final class NavBarFader implements NgaMessageHandler.NavBarVisibilityListener {
    public ObjectAnimator animator = new ObjectAnimator();
    public final Handler handler;
    public final NavigationBarController navigationBarController;
    public final NavBarFader$onTimeout$1 onTimeout = new NavBarFader$onTimeout$1(this);
    public float targetAlpha;

    public final void onVisibleRequest(boolean z) {
        float f;
        boolean z2;
        NavigationBarController navigationBarController2 = this.navigationBarController;
        Objects.requireNonNull(navigationBarController2);
        NavigationBarView navigationBarView = navigationBarController2.getNavigationBarView(0);
        if (navigationBarView != null) {
            this.handler.removeCallbacks(this.onTimeout);
            if (!z) {
                this.handler.postDelayed(this.onTimeout, 10000);
            }
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            if (f == this.targetAlpha) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                this.animator.cancel();
                float alpha = navigationBarView.getAlpha();
                this.targetAlpha = f;
                ObjectAnimator duration = ObjectAnimator.ofFloat(navigationBarView, View.ALPHA, new float[]{alpha, f}).setDuration((long) (Math.abs(f - alpha) * ((float) 80)));
                this.animator = duration;
                if (z) {
                    duration.setStartDelay(80);
                }
                this.animator.start();
            }
        }
    }

    public NavBarFader(NavigationBarController navigationBarController2, Handler handler2) {
        float f;
        this.navigationBarController = navigationBarController2;
        this.handler = handler2;
        Objects.requireNonNull(navigationBarController2);
        NavigationBarView navigationBarView = navigationBarController2.getNavigationBarView(0);
        if (navigationBarView == null) {
            f = 1.0f;
        } else {
            f = navigationBarView.getAlpha();
        }
        this.targetAlpha = f;
    }
}
