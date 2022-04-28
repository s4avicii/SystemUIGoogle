package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.InsetsVisibilities;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.util.ViewController;

public final class LightsOutNotifController extends ViewController<View> {
    @VisibleForTesting
    public int mAppearance;
    public final C14592 mCallback = new CommandQueue.Callbacks() {
        public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str) {
            LightsOutNotifController lightsOutNotifController = LightsOutNotifController.this;
            if (i == lightsOutNotifController.mDisplayId) {
                lightsOutNotifController.mAppearance = i2;
                lightsOutNotifController.updateLightsOutView();
            }
        }
    };
    public final CommandQueue mCommandQueue;
    public int mDisplayId;
    public final NotifLiveDataStore mNotifDataStore;
    public final LightsOutNotifController$$ExternalSyntheticLambda0 mObserver = new LightsOutNotifController$$ExternalSyntheticLambda0(this);
    public final WindowManager mWindowManager;

    @VisibleForTesting
    public boolean areLightsOut() {
        if ((this.mAppearance & 4) != 0) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean isShowingDot() {
        if (this.mView.getVisibility() == 0 && this.mView.getAlpha() == 1.0f) {
            return true;
        }
        return false;
    }

    public final void onViewAttached() {
        this.mView.setVisibility(8);
        this.mView.setAlpha(0.0f);
        this.mDisplayId = this.mWindowManager.getDefaultDisplay().getDisplayId();
        this.mNotifDataStore.getHasActiveNotifs().addSyncObserver(this.mObserver);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this.mCallback);
        updateLightsOutView();
    }

    public final void onViewDetached() {
        this.mNotifDataStore.getHasActiveNotifs().removeObserver(this.mObserver);
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this.mCallback);
    }

    @VisibleForTesting
    public boolean shouldShowDot() {
        if (!((Boolean) this.mNotifDataStore.getHasActiveNotifs().getValue()).booleanValue() || !areLightsOut()) {
            return false;
        }
        return true;
    }

    public LightsOutNotifController(View view, WindowManager windowManager, NotifLiveDataStore notifLiveDataStore, CommandQueue commandQueue) {
        super(view);
        this.mWindowManager = windowManager;
        this.mNotifDataStore = notifLiveDataStore;
        this.mCommandQueue = commandQueue;
    }

    @VisibleForTesting
    public void updateLightsOutView() {
        long j;
        final boolean shouldShowDot = shouldShowDot();
        if (shouldShowDot != isShowingDot()) {
            float f = 0.0f;
            if (shouldShowDot) {
                this.mView.setAlpha(0.0f);
                this.mView.setVisibility(0);
            }
            ViewPropertyAnimator animate = this.mView.animate();
            if (shouldShowDot) {
                f = 1.0f;
            }
            ViewPropertyAnimator alpha = animate.alpha(f);
            if (shouldShowDot) {
                j = 750;
            } else {
                j = 250;
            }
            alpha.setDuration(j).setInterpolator(new AccelerateInterpolator(2.0f)).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    float f;
                    int i;
                    T t = LightsOutNotifController.this.mView;
                    if (shouldShowDot) {
                        f = 1.0f;
                    } else {
                        f = 0.0f;
                    }
                    t.setAlpha(f);
                    T t2 = LightsOutNotifController.this.mView;
                    if (shouldShowDot) {
                        i = 0;
                    } else {
                        i = 8;
                    }
                    t2.setVisibility(i);
                    LightsOutNotifController.this.mView.animate().setListener((Animator.AnimatorListener) null);
                }
            }).start();
        }
    }
}
