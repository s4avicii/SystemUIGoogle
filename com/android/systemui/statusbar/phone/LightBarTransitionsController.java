package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.MathUtils;
import android.util.TimeUtils;
import androidx.leanback.R$color;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LightBarTransitionsController implements Dumpable, CommandQueue.Callbacks, StatusBarStateController.StateListener {
    public final DarkIntensityApplier mApplier;
    public final CommandQueue mCommandQueue;
    public float mDarkIntensity;
    public int mDisplayId;
    public float mDozeAmount;
    public final Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public float mNextDarkIntensity;
    public float mPendingDarkIntensity;
    public final StatusBarStateController mStatusBarStateController;
    public ValueAnimator mTintAnimator;
    public boolean mTintChangePending;
    public boolean mTransitionDeferring;
    public final C14571 mTransitionDeferringDoneRunnable = new Runnable() {
        public final void run() {
            LightBarTransitionsController.this.mTransitionDeferring = false;
        }
    };
    public long mTransitionDeferringDuration;
    public long mTransitionDeferringStartTime;
    public boolean mTransitionPending;

    public interface DarkIntensityApplier {
        void applyDarkIntensity(float f);

        int getTintAnimationDuration();
    }

    public final void onStateChanged(int i) {
    }

    public final void setIconsDark(boolean z, boolean z2) {
        float f;
        float f2 = 0.0f;
        if (!z2) {
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            this.mDarkIntensity = f;
            this.mApplier.applyDarkIntensity(MathUtils.lerp(f, 0.0f, this.mDozeAmount));
            if (z) {
                f2 = 1.0f;
            }
            this.mNextDarkIntensity = f2;
        } else if (this.mTransitionPending) {
            if (z) {
                f2 = 1.0f;
            }
            if (!this.mTintChangePending || f2 != this.mPendingDarkIntensity) {
                this.mTintChangePending = true;
                this.mPendingDarkIntensity = f2;
            }
        } else if (this.mTransitionDeferring) {
            if (z) {
                f2 = 1.0f;
            }
            animateIconTint(f2, Math.max(0, this.mTransitionDeferringStartTime - SystemClock.uptimeMillis()), this.mTransitionDeferringDuration);
        } else {
            if (z) {
                f2 = 1.0f;
            }
            animateIconTint(f2, 0, (long) this.mApplier.getTintAnimationDuration());
        }
    }

    public final void animateIconTint(float f, long j, long j2) {
        if (this.mNextDarkIntensity != f) {
            ValueAnimator valueAnimator = this.mTintAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.mNextDarkIntensity = f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mDarkIntensity, f});
            this.mTintAnimator = ofFloat;
            ofFloat.addUpdateListener(new LightBarTransitionsController$$ExternalSyntheticLambda0(this));
            this.mTintAnimator.setDuration(j2);
            this.mTintAnimator.setStartDelay(j);
            this.mTintAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            this.mTintAnimator.start();
        }
    }

    public final void appTransitionCancelled(int i) {
        if (this.mDisplayId == i) {
            if (this.mTransitionPending && this.mTintChangePending) {
                this.mTintChangePending = false;
                animateIconTint(this.mPendingDarkIntensity, 0, (long) this.mApplier.getTintAnimationDuration());
            }
            this.mTransitionPending = false;
        }
    }

    public final void appTransitionPending(int i, boolean z) {
        if (this.mDisplayId != i) {
            return;
        }
        if (!this.mKeyguardStateController.isKeyguardGoingAway() || z) {
            this.mTransitionPending = true;
        }
    }

    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        if (this.mDisplayId != i) {
            return;
        }
        if (!this.mKeyguardStateController.isKeyguardGoingAway() || z) {
            boolean z2 = this.mTransitionPending;
            if (z2 && this.mTintChangePending) {
                this.mTintChangePending = false;
                animateIconTint(this.mPendingDarkIntensity, Math.max(0, j - SystemClock.uptimeMillis()), j2);
            } else if (z2) {
                this.mTransitionDeferring = true;
                this.mTransitionDeferringStartTime = j;
                this.mTransitionDeferringDuration = j2;
                this.mHandler.removeCallbacks(this.mTransitionDeferringDoneRunnable);
                this.mHandler.postAtTime(this.mTransitionDeferringDoneRunnable, j);
            }
            this.mTransitionPending = false;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mTransitionDeferring=");
        printWriter.print(this.mTransitionDeferring);
        if (this.mTransitionDeferring) {
            printWriter.println();
            printWriter.print("   mTransitionDeferringStartTime=");
            printWriter.println(TimeUtils.formatUptime(this.mTransitionDeferringStartTime));
            printWriter.print("   mTransitionDeferringDuration=");
            TimeUtils.formatDuration(this.mTransitionDeferringDuration, printWriter);
            printWriter.println();
        }
        printWriter.print("  mTransitionPending=");
        printWriter.print(this.mTransitionPending);
        printWriter.print(" mTintChangePending=");
        printWriter.println(this.mTintChangePending);
        printWriter.print("  mPendingDarkIntensity=");
        printWriter.print(this.mPendingDarkIntensity);
        printWriter.print(" mDarkIntensity=");
        printWriter.print(this.mDarkIntensity);
        printWriter.print(" mNextDarkIntensity=");
        printWriter.println(this.mNextDarkIntensity);
    }

    public final void onDozeAmountChanged(float f, float f2) {
        this.mDozeAmount = f2;
        this.mApplier.applyDarkIntensity(MathUtils.lerp(this.mDarkIntensity, 0.0f, f2));
    }

    public LightBarTransitionsController(Context context, DarkIntensityApplier darkIntensityApplier, CommandQueue commandQueue) {
        this.mApplier = darkIntensityApplier;
        this.mHandler = new Handler();
        this.mKeyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        StatusBarStateController statusBarStateController = (StatusBarStateController) Dependency.get(StatusBarStateController.class);
        this.mStatusBarStateController = statusBarStateController;
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        statusBarStateController.addCallback(this);
        this.mDozeAmount = statusBarStateController.getDozeAmount();
        this.mDisplayId = context.getDisplayId();
    }

    public boolean supportsIconTintForNavMode(int i) {
        return !R$color.isGesturalMode(i);
    }
}
