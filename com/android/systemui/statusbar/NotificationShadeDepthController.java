package com.android.systemui.statusbar;

import android.animation.Animator;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.WallpaperController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController implements PanelExpansionListener, Dumpable {
    public final BiometricUnlockController biometricUnlockController;
    public View blurRoot;
    public final BlurUtils blurUtils;
    public boolean blursDisabledForAppLaunch;
    public boolean blursDisabledForUnlock;
    public DepthAnimation brightnessMirrorSpring = new DepthAnimation();
    public final Choreographer choreographer;
    public final DozeParameters dozeParameters;
    public boolean isBlurred;
    public boolean isClosed = true;
    public boolean isOpen;
    public Animator keyguardAnimator;
    public final KeyguardStateController keyguardStateController;
    public int lastAppliedBlur;
    public ArrayList listeners = new ArrayList();
    public final NotificationShadeWindowController notificationShadeWindowController;
    public float panelPullDownMinFraction;
    public int prevShadeDirection;
    public float prevShadeVelocity;
    public long prevTimestamp = -1;
    public boolean prevTracking;
    public float qsPanelExpansion;
    public View root;
    public boolean scrimsVisible;
    public DepthAnimation shadeAnimation = new DepthAnimation();
    public float shadeExpansion;
    public final StatusBarStateController statusBarStateController;
    public float transitionToFullShadeProgress;
    public final NotificationShadeDepthController$updateBlurCallback$1 updateBlurCallback = new NotificationShadeDepthController$updateBlurCallback$1(this);
    public boolean updateScheduled;
    public float wakeAndUnlockBlurRadius;
    public final WallpaperController wallpaperController;

    /* compiled from: NotificationShadeDepthController.kt */
    public final class DepthAnimation {
        public int pendingRadius = -1;
        public float radius;
        public SpringAnimation springAnimation;
        public View view;

        public static void animateTo$default(DepthAnimation depthAnimation, int i) {
            Objects.requireNonNull(depthAnimation);
            if (depthAnimation.pendingRadius != i || !Intrinsics.areEqual(depthAnimation.view, (Object) null)) {
                depthAnimation.view = null;
                depthAnimation.pendingRadius = i;
                depthAnimation.springAnimation.animateToFinalPosition((float) i);
            }
        }

        public DepthAnimation() {
            SpringAnimation springAnimation2 = new SpringAnimation(this, new C1174x870e2248(this, NotificationShadeDepthController.this));
            this.springAnimation = springAnimation2;
            SpringForce springForce = new SpringForce(0.0f);
            Objects.requireNonNull(springAnimation2);
            springAnimation2.mSpring = springForce;
            SpringAnimation springAnimation3 = this.springAnimation;
            Objects.requireNonNull(springAnimation3);
            springAnimation3.mSpring.setDampingRatio(1.0f);
            SpringAnimation springAnimation4 = this.springAnimation;
            Objects.requireNonNull(springAnimation4);
            springAnimation4.mSpring.setStiffness(10000.0f);
            this.springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener(this) {
                public final /* synthetic */ DepthAnimation this$0;

                {
                    this.this$0 = r1;
                }

                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    this.this$0.pendingRadius = -1;
                }
            });
        }
    }

    /* compiled from: NotificationShadeDepthController.kt */
    public interface DepthListener {
        void onBlurRadiusChanged(int i) {
        }

        void onWallpaperZoomOutChanged(float f);
    }

    public static /* synthetic */ void getBrightnessMirrorSpring$annotations() {
    }

    public static /* synthetic */ void getShadeExpansion$annotations() {
    }

    public static /* synthetic */ void getUpdateBlurCallback$annotations() {
    }

    public final void animateBlur(boolean z, float f) {
        float f2;
        this.isBlurred = z;
        if (!z || !shouldApplyShadeBlur()) {
            f2 = 0.0f;
        } else {
            f2 = 1.0f;
        }
        DepthAnimation depthAnimation = this.shadeAnimation;
        Objects.requireNonNull(depthAnimation);
        SpringAnimation springAnimation = depthAnimation.springAnimation;
        Objects.requireNonNull(springAnimation);
        springAnimation.mVelocity = f;
        DepthAnimation.animateTo$default(this.shadeAnimation, (int) this.blurUtils.blurRadiusOfRatio(f2));
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("StatusBarWindowBlurController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("shadeExpansion: ", Float.valueOf(this.shadeExpansion)));
        indentingPrintWriter.println(Intrinsics.stringPlus("shouldApplyShaeBlur: ", Boolean.valueOf(shouldApplyShadeBlur())));
        DepthAnimation depthAnimation = this.shadeAnimation;
        Objects.requireNonNull(depthAnimation);
        indentingPrintWriter.println(Intrinsics.stringPlus("shadeAnimation: ", Float.valueOf(depthAnimation.radius)));
        DepthAnimation depthAnimation2 = this.brightnessMirrorSpring;
        Objects.requireNonNull(depthAnimation2);
        indentingPrintWriter.println(Intrinsics.stringPlus("brightnessMirrorRadius: ", Float.valueOf(depthAnimation2.radius)));
        indentingPrintWriter.println(Intrinsics.stringPlus("wakeAndUnlockBlur: ", Float.valueOf(this.wakeAndUnlockBlurRadius)));
        indentingPrintWriter.println(Intrinsics.stringPlus("blursDisabledForAppLaunch: ", Boolean.valueOf(this.blursDisabledForAppLaunch)));
        indentingPrintWriter.println(Intrinsics.stringPlus("qsPanelExpansion: ", Float.valueOf(this.qsPanelExpansion)));
        indentingPrintWriter.println(Intrinsics.stringPlus("transitionToFullShadeProgress: ", Float.valueOf(this.transitionToFullShadeProgress)));
        indentingPrintWriter.println(Intrinsics.stringPlus("lastAppliedBlur: ", Integer.valueOf(this.lastAppliedBlur)));
    }

    public final void scheduleUpdate(View view) {
        if (!this.updateScheduled) {
            this.updateScheduled = true;
            this.blurRoot = view;
            this.choreographer.postFrameCallback(this.updateBlurCallback);
        }
    }

    public final boolean shouldApplyShadeBlur() {
        int state = this.statusBarStateController.getState();
        if ((state == 0 || state == 2) && !this.keyguardStateController.isKeyguardFadingAway()) {
            return true;
        }
        return false;
    }

    public NotificationShadeDepthController(StatusBarStateController statusBarStateController2, BlurUtils blurUtils2, BiometricUnlockController biometricUnlockController2, KeyguardStateController keyguardStateController2, Choreographer choreographer2, WallpaperController wallpaperController2, NotificationShadeWindowController notificationShadeWindowController2, DozeParameters dozeParameters2, DumpManager dumpManager) {
        this.statusBarStateController = statusBarStateController2;
        this.blurUtils = blurUtils2;
        this.biometricUnlockController = biometricUnlockController2;
        this.keyguardStateController = keyguardStateController2;
        this.choreographer = choreographer2;
        this.wallpaperController = wallpaperController2;
        this.notificationShadeWindowController = notificationShadeWindowController2;
        this.dozeParameters = dozeParameters2;
        NotificationShadeDepthController$keyguardStateCallback$1 notificationShadeDepthController$keyguardStateCallback$1 = new NotificationShadeDepthController$keyguardStateCallback$1(this);
        NotificationShadeDepthController$statusBarStateCallback$1 notificationShadeDepthController$statusBarStateCallback$1 = new NotificationShadeDepthController$statusBarStateCallback$1(this);
        dumpManager.registerDumpable(NotificationShadeDepthController.class.getName(), this);
        keyguardStateController2.addCallback(notificationShadeDepthController$keyguardStateCallback$1);
        statusBarStateController2.addCallback(notificationShadeDepthController$statusBarStateCallback$1);
        notificationShadeWindowController2.setScrimsVisibilityListener(new Consumer(this) {
            public final /* synthetic */ NotificationShadeDepthController this$0;

            {
                this.this$0 = r1;
            }

            public final void accept(Object obj) {
                boolean z;
                Integer num = (Integer) obj;
                NotificationShadeDepthController notificationShadeDepthController = this.this$0;
                if (num != null && num.intValue() == 2) {
                    z = true;
                } else {
                    z = false;
                }
                Objects.requireNonNull(notificationShadeDepthController);
                if (notificationShadeDepthController.scrimsVisible != z) {
                    notificationShadeDepthController.scrimsVisible = z;
                    notificationShadeDepthController.scheduleUpdate((View) null);
                }
            }
        });
        DepthAnimation depthAnimation = this.shadeAnimation;
        Objects.requireNonNull(depthAnimation);
        SpringAnimation springAnimation = depthAnimation.springAnimation;
        Objects.requireNonNull(springAnimation);
        springAnimation.mSpring.setStiffness(200.0f);
        DepthAnimation depthAnimation2 = this.shadeAnimation;
        Objects.requireNonNull(depthAnimation2);
        SpringAnimation springAnimation2 = depthAnimation2.springAnimation;
        Objects.requireNonNull(springAnimation2);
        springAnimation2.mSpring.setDampingRatio(1.0f);
    }

    public final void onPanelExpansionChanged(float f, boolean z, boolean z2) {
        boolean z3;
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        float f2 = this.panelPullDownMinFraction;
        float f3 = 1.0f;
        float saturate = MathUtils.saturate((f - f2) / (1.0f - f2));
        if (this.shadeExpansion == saturate) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (!z3 || this.prevTracking != z2) {
            long j = this.prevTimestamp;
            if (j < 0) {
                this.prevTimestamp = elapsedRealtimeNanos;
            } else {
                f3 = MathUtils.constrain((float) (((double) (elapsedRealtimeNanos - j)) / 1.0E9d), 1.0E-5f, 1.0f);
            }
            float f4 = saturate - this.shadeExpansion;
            int signum = (int) Math.signum(f4);
            float constrain = MathUtils.constrain((f4 * 100.0f) / f3, -3000.0f, 3000.0f);
            updateShadeAnimationBlur(saturate, z2, constrain, signum);
            this.prevShadeDirection = signum;
            this.prevShadeVelocity = constrain;
            this.shadeExpansion = saturate;
            this.prevTracking = z2;
            this.prevTimestamp = elapsedRealtimeNanos;
            scheduleUpdate((View) null);
            return;
        }
        this.prevTimestamp = elapsedRealtimeNanos;
    }

    public final void updateShadeAnimationBlur(float f, boolean z, float f2, int i) {
        boolean z2;
        if (!shouldApplyShadeBlur()) {
            animateBlur(false, 0.0f);
            this.isClosed = true;
            this.isOpen = false;
        } else if (f > 0.0f) {
            if (this.isClosed) {
                animateBlur(true, f2);
                this.isClosed = false;
            }
            if (z && !this.isBlurred) {
                animateBlur(true, 0.0f);
            }
            if (!z && i < 0 && this.isBlurred) {
                animateBlur(false, f2);
            }
            if (f == 1.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                this.isOpen = false;
            } else if (!this.isOpen) {
                this.isOpen = true;
                if (!this.isBlurred) {
                    animateBlur(true, f2);
                }
            }
        } else if (!this.isClosed) {
            this.isClosed = true;
            if (this.isBlurred) {
                animateBlur(false, f2);
            }
        }
    }
}
