package com.android.systemui.assist.p003ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import dagger.Lazy;
import java.util.Locale;
import java.util.Objects;

/* renamed from: com.android.systemui.assist.ui.DefaultUiController */
public class DefaultUiController implements AssistManager.UiController {
    public static final boolean VERBOSE;
    public final AssistLogger mAssistLogger;
    public final Lazy<AssistManager> mAssistManagerLazy;
    public boolean mAttached = false;
    public ValueAnimator mInvocationAnimator = new ValueAnimator();
    public boolean mInvocationInProgress = false;
    public InvocationLightsView mInvocationLightsView;
    public float mLastInvocationProgress = 0.0f;
    public final WindowManager.LayoutParams mLayoutParams;
    public final MetricsLogger mMetricsLogger;
    public final PathInterpolator mProgressInterpolator = new PathInterpolator(0.83f, 0.0f, 0.84f, 1.0f);
    public final FrameLayout mRoot;
    public final WindowManager mWindowManager;

    public final void animateInvocationCompletion(int i) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mLastInvocationProgress, 1.0f});
        this.mInvocationAnimator = ofFloat;
        ofFloat.setStartDelay(1);
        this.mInvocationAnimator.setDuration(200);
        this.mInvocationAnimator.addUpdateListener(new DefaultUiController$$ExternalSyntheticLambda0(this, i));
        this.mInvocationAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DefaultUiController defaultUiController = DefaultUiController.this;
                defaultUiController.mInvocationInProgress = false;
                defaultUiController.mLastInvocationProgress = 0.0f;
                defaultUiController.hide();
            }
        });
        this.mInvocationAnimator.start();
    }

    public final void onGestureCompletion(float f) {
        animateInvocationCompletion(1);
        logInvocationProgressMetrics(1, 1.0f, this.mInvocationInProgress);
    }

    static {
        boolean z;
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        if (str.toLowerCase(locale).contains("debug") || str.toLowerCase(locale).equals("eng")) {
            z = true;
        } else {
            z = false;
        }
        VERBOSE = z;
    }

    public final void hide() {
        if (this.mAttached) {
            this.mWindowManager.removeViewImmediate(this.mRoot);
            this.mAttached = false;
        }
        if (this.mInvocationAnimator.isRunning()) {
            this.mInvocationAnimator.cancel();
        }
        this.mInvocationLightsView.hide();
        this.mInvocationInProgress = false;
    }

    public final void logInvocationProgressMetrics(int i, float f, boolean z) {
        if (f == 1.0f && VERBOSE) {
            Log.v("DefaultUiController", "Invocation complete: type=" + i);
        }
        if (!z && f > 0.0f) {
            if (VERBOSE) {
                Log.v("DefaultUiController", "Invocation started: type=" + i);
            }
            this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, false, (ComponentName) null, (Integer) null);
            MetricsLogger metricsLogger = this.mMetricsLogger;
            LogMaker type = new LogMaker(1716).setType(4);
            AssistManager assistManager = this.mAssistManagerLazy.get();
            Objects.requireNonNull(assistManager);
            metricsLogger.write(type.setSubtype(0 | (i << 1) | (assistManager.mPhoneStateMonitor.getPhoneState() << 4)));
        }
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && z && f == 0.0f) {
            if (VERBOSE) {
                Log.v("DefaultUiController", "Invocation cancelled: type=" + i);
            }
            this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED);
            MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(1));
        }
    }

    public final void onInvocationProgress(int i, float f) {
        boolean z = this.mInvocationInProgress;
        if (f == 1.0f) {
            animateInvocationCompletion(i);
        } else if (f == 0.0f) {
            hide();
        } else {
            if (!z) {
                if (!this.mAttached) {
                    this.mWindowManager.addView(this.mRoot, this.mLayoutParams);
                    this.mAttached = true;
                }
                this.mInvocationInProgress = true;
            }
            this.mInvocationLightsView.onInvocationProgress(this.mProgressInterpolator.getInterpolation(f));
        }
        this.mLastInvocationProgress = f;
        logInvocationProgressMetrics(i, f, z);
    }

    public DefaultUiController(Context context, AssistLogger assistLogger, WindowManager windowManager, MetricsLogger metricsLogger, Lazy<AssistManager> lazy) {
        this.mAssistLogger = assistLogger;
        FrameLayout frameLayout = new FrameLayout(context);
        this.mRoot = frameLayout;
        this.mWindowManager = windowManager;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 0, 0, 2024, 808, -3);
        this.mLayoutParams = layoutParams;
        layoutParams.privateFlags = 64;
        layoutParams.gravity = 80;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Assist");
        InvocationLightsView invocationLightsView = (InvocationLightsView) LayoutInflater.from(context).inflate(C1777R.layout.invocation_lights, frameLayout, false);
        this.mInvocationLightsView = invocationLightsView;
        frameLayout.addView(invocationLightsView);
    }
}
