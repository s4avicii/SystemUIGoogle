package com.google.android.systemui.assist.uihints;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.metrics.LogMaker;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.p012wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda2;
import com.android.systemui.DejankUtils;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;
import com.google.android.systemui.assist.uihints.edgelights.mode.FullListening;
import com.google.android.systemui.assist.uihints.edgelights.mode.Gone;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

public final class NgaUiController implements AssistManager.UiController, ViewTreeObserver.OnComputeInternalInsetsListener, StatusBarStateController.StateListener {
    public static final boolean VERBOSE;
    public static final PathInterpolator mProgressInterpolator = new PathInterpolator(0.83f, 0.0f, 0.84f, 1.0f);
    public final AssistLogger mAssistLogger;
    public final Lazy<AssistManager> mAssistManager;
    public final AssistantPresenceHandler mAssistantPresenceHandler;
    public final AssistantWarmer mAssistantWarmer;
    public final ColorChangeHandler mColorChangeHandler;
    public long mColorMonitoringStart;
    public final Context mContext;
    public final EdgeLightsController mEdgeLightsController;
    public final FlingVelocityWrapper mFlingVelocity;
    public final GlowController mGlowController;
    public boolean mHasDarkBackground;
    public final IconController mIconController;
    public ValueAnimator mInvocationAnimator;
    public boolean mInvocationInProgress;
    public AssistantInvocationLightsView mInvocationLightsView;
    public boolean mIsMonitoringColor;
    public float mLastInvocationProgress;
    public long mLastInvocationStartTime;
    public final LightnessProvider mLightnessProvider;
    public final NavBarFader mNavBarFader;
    public Runnable mPendingEdgeLightsModeChange;
    public PromptView mPromptView;
    public final ScrimController mScrimController;
    public boolean mShouldKeepWakeLock;
    public boolean mShowingAssistUi;
    public final TimeoutManager mTimeoutManager;
    public final TouchInsideHandler mTouchInsideHandler;
    public final TranscriptionController mTranscriptionController;
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final OverlayUiHost mUiHost;
    public PowerManager.WakeLock mWakeLock;

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo r7) {
        /*
            r6 = this;
            r0 = 3
            r7.setTouchableInsets(r0)
            android.graphics.Region r1 = new android.graphics.Region
            r1.<init>()
            com.google.android.systemui.assist.uihints.IconController r2 = r6.mIconController
            java.util.Optional r2 = r2.getTouchActionRegion()
            com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1 r3 = new com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda1
            r4 = 4
            r3.<init>(r1, r4)
            r2.ifPresent(r3)
            android.graphics.Region r2 = new android.graphics.Region
            r2.<init>()
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r3 = r6.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r3 = r3.getMode()
            boolean r5 = r3 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.FullListening
            if (r5 == 0) goto L_0x0032
            com.google.android.systemui.assist.uihints.edgelights.mode.FullListening r3 = (com.google.android.systemui.assist.uihints.edgelights.mode.FullListening) r3
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mFakeForHalfListening
            if (r3 == 0) goto L_0x0032
            r3 = 1
            goto L_0x0033
        L_0x0032:
            r3 = 0
        L_0x0033:
            if (r3 != 0) goto L_0x0043
            com.google.android.systemui.assist.uihints.GlowController r3 = r6.mGlowController
            java.util.Optional r3 = r3.getTouchInsideRegion()
            com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4 r5 = new com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4
            r5.<init>(r2, r4)
            r3.ifPresent(r5)
        L_0x0043:
            com.google.android.systemui.assist.uihints.ScrimController r3 = r6.mScrimController
            java.util.Optional r3 = r3.getTouchInsideRegion()
            com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0 r4 = new com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0
            r4.<init>(r2, r0)
            r3.ifPresent(r4)
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1 r0 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1
            r3 = 5
            r0.<init>(r2, r3)
            com.google.android.systemui.assist.uihints.TranscriptionController r3 = r6.mTranscriptionController
            java.util.Optional r3 = r3.getTouchInsideRegion()
            r3.ifPresent(r0)
            com.google.android.systemui.assist.uihints.TranscriptionController r6 = r6.mTranscriptionController
            java.util.Optional r6 = r6.getTouchActionRegion()
            r6.ifPresent(r0)
            android.graphics.Region$Op r6 = android.graphics.Region.Op.UNION
            r1.op(r2, r6)
            android.graphics.Region r6 = r7.touchableRegion
            r6.set(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.NgaUiController.onComputeInternalInsets(android.view.ViewTreeObserver$InternalInsetsInfo):void");
    }

    static {
        boolean z;
        Class<NgaUiController> cls = NgaUiController.class;
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        if (str.toLowerCase(locale).contains("debug") || str.toLowerCase(locale).equals("eng")) {
            z = true;
        } else {
            z = false;
        }
        VERBOSE = z;
    }

    public NgaUiController(Context context, TimeoutManager timeoutManager, AssistantPresenceHandler assistantPresenceHandler, TouchInsideHandler touchInsideHandler, ColorChangeHandler colorChangeHandler, OverlayUiHost overlayUiHost, EdgeLightsController edgeLightsController, GlowController glowController, ScrimController scrimController, TranscriptionController transcriptionController, IconController iconController, LightnessProvider lightnessProvider, StatusBarStateController statusBarStateController, Lazy<AssistManager> lazy, FlingVelocityWrapper flingVelocityWrapper, AssistantWarmer assistantWarmer, NavBarFader navBarFader, AssistLogger assistLogger) {
        Context context2 = context;
        TimeoutManager timeoutManager2 = timeoutManager;
        AssistantPresenceHandler assistantPresenceHandler2 = assistantPresenceHandler;
        TouchInsideHandler touchInsideHandler2 = touchInsideHandler;
        ColorChangeHandler colorChangeHandler2 = colorChangeHandler;
        OverlayUiHost overlayUiHost2 = overlayUiHost;
        EdgeLightsController edgeLightsController2 = edgeLightsController;
        GlowController glowController2 = glowController;
        ScrimController scrimController2 = scrimController;
        TranscriptionController transcriptionController2 = transcriptionController;
        LightnessProvider lightnessProvider2 = lightnessProvider;
        boolean z = false;
        this.mHasDarkBackground = false;
        this.mIsMonitoringColor = false;
        this.mInvocationInProgress = false;
        this.mShowingAssistUi = false;
        this.mShouldKeepWakeLock = false;
        this.mLastInvocationStartTime = 0;
        this.mLastInvocationProgress = 0.0f;
        this.mColorMonitoringStart = 0;
        this.mContext = context2;
        this.mAssistLogger = assistLogger;
        this.mColorChangeHandler = colorChangeHandler2;
        Objects.requireNonNull(colorChangeHandler);
        colorChangeHandler2.mIsDark = false;
        colorChangeHandler.sendColor();
        this.mTimeoutManager = timeoutManager2;
        this.mAssistantPresenceHandler = assistantPresenceHandler2;
        this.mTouchInsideHandler = touchInsideHandler2;
        this.mUiHost = overlayUiHost2;
        this.mEdgeLightsController = edgeLightsController2;
        this.mGlowController = glowController2;
        this.mScrimController = scrimController2;
        this.mTranscriptionController = transcriptionController2;
        this.mIconController = iconController;
        this.mLightnessProvider = lightnessProvider2;
        this.mAssistManager = lazy;
        this.mFlingVelocity = flingVelocityWrapper;
        this.mAssistantWarmer = assistantWarmer;
        this.mNavBarFader = navBarFader;
        NgaUiController$$ExternalSyntheticLambda1 ngaUiController$$ExternalSyntheticLambda1 = new NgaUiController$$ExternalSyntheticLambda1(this);
        Objects.requireNonNull(lightnessProvider);
        lightnessProvider2.mListener = ngaUiController$$ExternalSyntheticLambda1;
        NgaUiController$$ExternalSyntheticLambda4 ngaUiController$$ExternalSyntheticLambda4 = new NgaUiController$$ExternalSyntheticLambda4(this);
        Objects.requireNonNull(assistantPresenceHandler);
        assistantPresenceHandler2.mSysUiIsNgaUiChangeListeners.add(ngaUiController$$ExternalSyntheticLambda4);
        CarrierTextManager$$ExternalSyntheticLambda0 carrierTextManager$$ExternalSyntheticLambda0 = new CarrierTextManager$$ExternalSyntheticLambda0(this, 11);
        Objects.requireNonNull(touchInsideHandler);
        touchInsideHandler2.mFallback = carrierTextManager$$ExternalSyntheticLambda0;
        NgaUiController$$ExternalSyntheticLambda2 ngaUiController$$ExternalSyntheticLambda2 = new NgaUiController$$ExternalSyntheticLambda2(this);
        Objects.requireNonNull(edgeLightsController);
        edgeLightsController2.mThrottler = ngaUiController$$ExternalSyntheticLambda2;
        this.mWakeLock = ((PowerManager) context2.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER)).newWakeLock(805306378, "Assist (NGA)");
        PipTouchHandler$$ExternalSyntheticLambda2 pipTouchHandler$$ExternalSyntheticLambda2 = new PipTouchHandler$$ExternalSyntheticLambda2(this);
        Objects.requireNonNull(glowController);
        glowController2.mVisibilityListener = pipTouchHandler$$ExternalSyntheticLambda2;
        Objects.requireNonNull(scrimController);
        scrimController2.mVisibilityListener = pipTouchHandler$$ExternalSyntheticLambda2;
        Objects.requireNonNull(overlayUiHost);
        AssistUIView assistUIView = overlayUiHost2.mRoot;
        AssistantInvocationLightsView assistantInvocationLightsView = (AssistantInvocationLightsView) assistUIView.findViewById(C1777R.C1779id.invocation_lights);
        this.mInvocationLightsView = assistantInvocationLightsView;
        Objects.requireNonNull(assistantInvocationLightsView);
        int i = assistantInvocationLightsView.mColorBlue;
        int i2 = assistantInvocationLightsView.mColorRed;
        int i3 = assistantInvocationLightsView.mColorYellow;
        int i4 = assistantInvocationLightsView.mColorGreen;
        assistantInvocationLightsView.mUseNavBarColor = false;
        assistantInvocationLightsView.attemptUnregisterNavBarListener();
        EdgeLight edgeLight = assistantInvocationLightsView.mAssistInvocationLights.get(0);
        Objects.requireNonNull(edgeLight);
        edgeLight.mColor = i;
        EdgeLight edgeLight2 = assistantInvocationLightsView.mAssistInvocationLights.get(1);
        Objects.requireNonNull(edgeLight2);
        edgeLight2.mColor = i2;
        EdgeLight edgeLight3 = assistantInvocationLightsView.mAssistInvocationLights.get(2);
        Objects.requireNonNull(edgeLight3);
        edgeLight3.mColor = i3;
        EdgeLight edgeLight4 = assistantInvocationLightsView.mAssistInvocationLights.get(3);
        Objects.requireNonNull(edgeLight4);
        edgeLight4.mColor = i4;
        EdgeLightsView edgeLightsView = edgeLightsController2.mEdgeLightsView;
        Objects.requireNonNull(edgeLightsView);
        edgeLightsView.mListeners.add(glowController2);
        EdgeLightsView edgeLightsView2 = edgeLightsController2.mEdgeLightsView;
        Objects.requireNonNull(edgeLightsView2);
        edgeLightsView2.mListeners.add(scrimController2);
        Objects.requireNonNull(transcriptionController);
        transcriptionController2.mListener = scrimController2;
        z = transcriptionController2.mCurrentState != TranscriptionController.State.NONE ? true : z;
        if (scrimController2.mTranscriptionVisible != z) {
            scrimController2.mTranscriptionVisible = z;
            scrimController.refresh();
        }
        this.mPromptView = (PromptView) assistUIView.findViewById(C1777R.C1779id.prompt);
        dispatchHasDarkBackground();
        statusBarStateController.addCallback(this);
        refresh();
        NgaUiController$$ExternalSyntheticLambda3 ngaUiController$$ExternalSyntheticLambda3 = new NgaUiController$$ExternalSyntheticLambda3(new ScrimView$$ExternalSyntheticLambda0(this, 8));
        Objects.requireNonNull(timeoutManager);
        timeoutManager2.mTimeoutCallback = ngaUiController$$ExternalSyntheticLambda3;
    }

    public final void closeNgaUi() {
        this.mAssistManager.get().hideAssist();
        hide();
    }

    public final void completeInvocation(int i) {
        float f;
        AssistantPresenceHandler assistantPresenceHandler = this.mAssistantPresenceHandler;
        Objects.requireNonNull(assistantPresenceHandler);
        if (!assistantPresenceHandler.mSysUiIsNgaUi) {
            setProgress(i, 0.0f);
            this.mInvocationInProgress = false;
            this.mInvocationLightsView.hide();
            this.mLastInvocationProgress = 0.0f;
            ScrimController scrimController = this.mScrimController;
            Objects.requireNonNull(scrimController);
            float constrain = MathUtils.constrain(0.0f, 0.0f, 1.0f);
            if (scrimController.mInvocationProgress != constrain) {
                scrimController.mInvocationProgress = constrain;
                scrimController.refresh();
            }
            refresh();
            return;
        }
        TouchInsideHandler touchInsideHandler = this.mTouchInsideHandler;
        Objects.requireNonNull(touchInsideHandler);
        if (!touchInsideHandler.mInGesturalMode) {
            touchInsideHandler.mGuardLocked = true;
            touchInsideHandler.mGuarded = true;
            touchInsideHandler.mHandler.postDelayed(new WMShell$7$$ExternalSyntheticLambda1(touchInsideHandler, 8), 500);
        }
        TimeoutManager timeoutManager = this.mTimeoutManager;
        Objects.requireNonNull(timeoutManager);
        timeoutManager.mHandler.removeCallbacks(timeoutManager.mOnTimeout);
        timeoutManager.mHandler.postDelayed(timeoutManager.mOnTimeout, TimeoutManager.SESSION_TIMEOUT_MS);
        this.mPromptView.disable$1();
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            this.mInvocationAnimator.cancel();
        }
        FlingVelocityWrapper flingVelocityWrapper = this.mFlingVelocity;
        Objects.requireNonNull(flingVelocityWrapper);
        float f2 = flingVelocityWrapper.mVelocity;
        float f3 = 3.0f;
        if (f2 != 0.0f) {
            f3 = MathUtils.constrain((-f2) / 1.45f, 3.0f, 12.0f);
        }
        OvershootInterpolator overshootInterpolator = new OvershootInterpolator(f3);
        float f4 = this.mLastInvocationProgress;
        if (i == 2) {
            f = f4 * 0.95f;
        } else {
            f = mProgressInterpolator.getInterpolation(f4 * 0.8f);
        }
        Float valueOf = Float.valueOf(f);
        ArrayList arrayList = new ArrayList((int) 200.0f);
        for (float f5 = 0.0f; f5 < 1.0f; f5 += 0.005f) {
            arrayList.add(Float.valueOf(Math.min(1.0f, overshootInterpolator.getInterpolation(Float.valueOf(f5).floatValue()))));
        }
        int binarySearch = Collections.binarySearch(arrayList, valueOf);
        if (binarySearch < 0) {
            binarySearch = (binarySearch + 1) * -1;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{((float) binarySearch) * 0.005f, 1.0f});
        ofFloat.setDuration(600);
        ofFloat.setStartDelay(1);
        ofFloat.addUpdateListener(new NgaUiController$$ExternalSyntheticLambda0(this, i, overshootInterpolator));
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public static final /* synthetic */ int $r8$clinit = 0;
            public boolean mCancelled = false;

            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (!this.mCancelled) {
                    NgaUiController ngaUiController = NgaUiController.this;
                    Runnable runnable = ngaUiController.mPendingEdgeLightsModeChange;
                    if (runnable == null) {
                        EdgeLightsController edgeLightsController = ngaUiController.mEdgeLightsController;
                        Objects.requireNonNull(edgeLightsController);
                        edgeLightsController.getMode().onNewModeRequest(edgeLightsController.mEdgeLightsView, new FullListening(edgeLightsController.mContext, false));
                    } else {
                        runnable.run();
                        NgaUiController.this.mPendingEdgeLightsModeChange = null;
                    }
                }
                NgaUiController.this.mUiHandler.post(new WifiEntry$$ExternalSyntheticLambda2(this, 13));
            }
        });
        this.mInvocationAnimator = ofFloat;
        ofFloat.start();
    }

    public final void dispatchHasDarkBackground() {
        int i;
        int i2;
        int i3;
        TranscriptionController transcriptionController = this.mTranscriptionController;
        boolean z = this.mHasDarkBackground;
        Objects.requireNonNull(transcriptionController);
        for (TranscriptionController.TranscriptionSpaceView hasDarkBackground : transcriptionController.mViewMap.values()) {
            hasDarkBackground.setHasDarkBackground(z);
        }
        IconController iconController = this.mIconController;
        boolean z2 = this.mHasDarkBackground;
        Objects.requireNonNull(iconController);
        KeyboardIconView keyboardIconView = iconController.mKeyboardIcon;
        Objects.requireNonNull(keyboardIconView);
        ImageView imageView = keyboardIconView.mKeyboardIcon;
        if (z2) {
            i = keyboardIconView.COLOR_DARK_BACKGROUND;
        } else {
            i = keyboardIconView.COLOR_LIGHT_BACKGROUND;
        }
        imageView.setImageTintList(ColorStateList.valueOf(i));
        ZeroStateIconView zeroStateIconView = iconController.mZeroStateIcon;
        Objects.requireNonNull(zeroStateIconView);
        ImageView imageView2 = zeroStateIconView.mZeroStateIcon;
        if (z2) {
            i2 = zeroStateIconView.COLOR_DARK_BACKGROUND;
        } else {
            i2 = zeroStateIconView.COLOR_LIGHT_BACKGROUND;
        }
        imageView2.setImageTintList(ColorStateList.valueOf(i2));
        PromptView promptView = this.mPromptView;
        boolean z3 = this.mHasDarkBackground;
        Objects.requireNonNull(promptView);
        if (z3 != promptView.mHasDarkBackground) {
            if (z3) {
                i3 = promptView.mTextColorDark;
            } else {
                i3 = promptView.mTextColorLight;
            }
            promptView.setTextColor(i3);
            promptView.mHasDarkBackground = z3;
        }
    }

    public final void hide() {
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if (valueAnimator != null && valueAnimator.isStarted()) {
            this.mInvocationAnimator.cancel();
        }
        this.mInvocationInProgress = false;
        this.mTranscriptionController.onClear(false);
        EdgeLightsController edgeLightsController = this.mEdgeLightsController;
        Objects.requireNonNull(edgeLightsController);
        edgeLightsController.getMode().onNewModeRequest(edgeLightsController.mEdgeLightsView, new Gone());
        this.mPendingEdgeLightsModeChange = null;
        this.mPromptView.disable$1();
        this.mIconController.onHideKeyboard();
        this.mIconController.onHideZerostate();
        refresh();
    }

    public final void logInvocationProgressMetrics(int i, float f, boolean z) {
        if (f == 1.0f && VERBOSE) {
            Log.v("NgaUiController", "Invocation complete: type=" + i);
        }
        if (!z && f > 0.0f) {
            if (VERBOSE) {
                Log.v("NgaUiController", "Invocation started: type=" + i);
            }
            this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, false, (ComponentName) null, (Integer) null);
            LogMaker type = new LogMaker(1716).setType(4);
            AssistManager assistManager = this.mAssistManager.get();
            Objects.requireNonNull(assistManager);
            MetricsLogger.action(type.setSubtype((assistManager.mPhoneStateMonitor.getPhoneState() << 4) | 0 | (i << 1)));
        }
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && z && f == 0.0f) {
            if (VERBOSE) {
                Log.v("NgaUiController", "Invocation cancelled: type=" + i);
            }
            this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED);
            MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(1));
        }
    }

    public final void onGestureCompletion(float f) {
        if (!this.mEdgeLightsController.getMode().preventsInvocations()) {
            FlingVelocityWrapper flingVelocityWrapper = this.mFlingVelocity;
            Objects.requireNonNull(flingVelocityWrapper);
            flingVelocityWrapper.mVelocity = f;
            flingVelocityWrapper.mGuarded = false;
            completeInvocation(1);
            logInvocationProgressMetrics(1, 1.0f, this.mInvocationInProgress);
        } else if (VERBOSE) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ignoring invocation; mode is ");
            m.append(this.mEdgeLightsController.getMode().getClass().getSimpleName());
            Log.v("NgaUiController", m.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x00f0 A[SYNTHETIC, Splitter:B:64:0x00f0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInvocationProgress(int r11, float r12) {
        /*
            r10 = this;
            android.animation.ValueAnimator r0 = r10.mInvocationAnimator
            java.lang.String r1 = "NgaUiController"
            if (r0 == 0) goto L_0x0012
            boolean r0 = r0.isStarted()
            if (r0 == 0) goto L_0x0012
            java.lang.String r10 = "Already animating; ignoring invocation progress"
            android.util.Log.w(r1, r10)
            return
        L_0x0012:
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r0 = r10.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r0 = r0.getMode()
            boolean r0 = r0.preventsInvocations()
            if (r0 == 0) goto L_0x0041
            boolean r11 = VERBOSE
            if (r11 == 0) goto L_0x0040
            java.lang.String r11 = "ignoring invocation; mode is "
            java.lang.StringBuilder r11 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r11)
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r10 = r10.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r10 = r10.getMode()
            java.lang.Class r10 = r10.getClass()
            java.lang.String r10 = r10.getSimpleName()
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            android.util.Log.v(r1, r10)
        L_0x0040:
            return
        L_0x0041:
            boolean r0 = r10.mInvocationInProgress
            r1 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            r3 = 1
            r4 = 0
            r5 = 0
            if (r2 >= 0) goto L_0x009e
            r10.mLastInvocationProgress = r12
            if (r0 != 0) goto L_0x005a
            int r6 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r6 <= 0) goto L_0x005a
            long r6 = android.os.SystemClock.uptimeMillis()
            r10.mLastInvocationStartTime = r6
        L_0x005a:
            int r6 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r6 <= 0) goto L_0x0062
            if (r2 >= 0) goto L_0x0062
            r2 = r3
            goto L_0x0063
        L_0x0062:
            r2 = r5
        L_0x0063:
            r10.mInvocationInProgress = r2
            if (r2 != 0) goto L_0x006d
            com.google.android.systemui.assist.uihints.PromptView r2 = r10.mPromptView
            r2.disable$1()
            goto L_0x0088
        L_0x006d:
            r2 = 1063675494(0x3f666666, float:0.9)
            int r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0088
            long r6 = android.os.SystemClock.uptimeMillis()
            long r8 = r10.mLastInvocationStartTime
            long r6 = r6 - r8
            r8 = 200(0xc8, double:9.9E-322)
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x0088
            com.google.android.systemui.assist.uihints.PromptView r2 = r10.mPromptView
            java.util.Objects.requireNonNull(r2)
            r2.mEnabled = r3
        L_0x0088:
            r2 = 2
            if (r11 != r2) goto L_0x0090
            r2 = 1064514355(0x3f733333, float:0.95)
            float r2 = r2 * r12
            goto L_0x009a
        L_0x0090:
            android.view.animation.PathInterpolator r2 = mProgressInterpolator
            r6 = 1061997773(0x3f4ccccd, float:0.8)
            float r6 = r6 * r12
            float r2 = r2.getInterpolation(r6)
        L_0x009a:
            r10.setProgress(r11, r2)
            goto L_0x00b4
        L_0x009e:
            android.animation.ValueAnimator r2 = r10.mInvocationAnimator
            if (r2 == 0) goto L_0x00a8
            boolean r2 = r2.isStarted()
            if (r2 != 0) goto L_0x00b4
        L_0x00a8:
            com.google.android.systemui.assist.uihints.FlingVelocityWrapper r2 = r10.mFlingVelocity
            java.util.Objects.requireNonNull(r2)
            r2.mVelocity = r4
            r2.mGuarded = r5
            r10.completeInvocation(r11)
        L_0x00b4:
            com.google.android.systemui.assist.uihints.AssistantWarmer r2 = r10.mAssistantWarmer
            java.util.Objects.requireNonNull(r2)
            int r1 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r1 < 0) goto L_0x00c0
            r2.primed = r5
            goto L_0x00e0
        L_0x00c0:
            int r1 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r1 > 0) goto L_0x00cb
            boolean r1 = r2.primed
            if (r1 == 0) goto L_0x00cb
            r2.primed = r5
            goto L_0x00e1
        L_0x00cb:
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest r1 = r2.request
            if (r1 != 0) goto L_0x00d3
            r1 = 1036831949(0x3dcccccd, float:0.1)
            goto L_0x00d5
        L_0x00d3:
            float r1 = r1.threshold
        L_0x00d5:
            int r1 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x00e0
            boolean r1 = r2.primed
            if (r1 != 0) goto L_0x00e0
            r2.primed = r3
            goto L_0x00e1
        L_0x00e0:
            r3 = r5
        L_0x00e1:
            if (r3 == 0) goto L_0x0107
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest r1 = r2.request
            if (r1 != 0) goto L_0x00e8
            goto L_0x0107
        L_0x00e8:
            android.content.Context r3 = r2.context
            boolean r2 = r2.primed
            android.app.PendingIntent r1 = r1.onWarm
            if (r1 == 0) goto L_0x0107
            android.content.Intent r4 = new android.content.Intent     // Catch:{ CanceledException -> 0x00ff }
            r4.<init>()     // Catch:{ CanceledException -> 0x00ff }
            java.lang.String r6 = "primed"
            android.content.Intent r2 = r4.putExtra(r6, r2)     // Catch:{ CanceledException -> 0x00ff }
            r1.send(r3, r5, r2)     // Catch:{ CanceledException -> 0x00ff }
            goto L_0x0107
        L_0x00ff:
            r1 = move-exception
            java.lang.String r2 = "NgaMessageHandler"
            java.lang.String r3 = "Unable to warm assistant, PendingIntent cancelled"
            android.util.Log.e(r2, r3, r1)
        L_0x0107:
            r10.logInvocationProgressMetrics(r11, r12, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.NgaUiController.onInvocationProgress(int, float):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a0, code lost:
        if (r5 == null) goto L_0x0175;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0214  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void refresh() {
        /*
            r15 = this;
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r0 = r15.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r0 = r0.getMode()
            boolean r0 = r0 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.Gone
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0037
            com.google.android.systemui.assist.uihints.GlowController r0 = r15.mGlowController
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.assist.uihints.GlowView r0 = r0.mGlowView
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x001b
            r0 = r1
            goto L_0x001c
        L_0x001b:
            r0 = r2
        L_0x001c:
            if (r0 != 0) goto L_0x0037
            com.google.android.systemui.assist.uihints.ScrimController r0 = r15.mScrimController
            java.util.Objects.requireNonNull(r0)
            android.view.View r0 = r0.mScrimView
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x002d
            r0 = r1
            goto L_0x002e
        L_0x002d:
            r0 = r2
        L_0x002e:
            if (r0 != 0) goto L_0x0037
            boolean r0 = r15.mInvocationInProgress
            if (r0 == 0) goto L_0x0035
            goto L_0x0037
        L_0x0035:
            r0 = r2
            goto L_0x0038
        L_0x0037:
            r0 = r1
        L_0x0038:
            if (r0 != 0) goto L_0x006b
            com.google.android.systemui.assist.uihints.IconController r3 = r15.mIconController
            java.util.Objects.requireNonNull(r3)
            com.google.android.systemui.assist.uihints.KeyboardIconView r4 = r3.mKeyboardIcon
            int r4 = r4.getVisibility()
            if (r4 == 0) goto L_0x0052
            com.google.android.systemui.assist.uihints.ZeroStateIconView r3 = r3.mZeroStateIcon
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L_0x0050
            goto L_0x0052
        L_0x0050:
            r3 = r2
            goto L_0x0053
        L_0x0052:
            r3 = r1
        L_0x0053:
            if (r3 != 0) goto L_0x006b
            com.google.android.systemui.assist.uihints.IconController r3 = r15.mIconController
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.mKeyboardIconRequested
            if (r4 != 0) goto L_0x0065
            boolean r3 = r3.mZerostateIconRequested
            if (r3 == 0) goto L_0x0063
            goto L_0x0065
        L_0x0063:
            r3 = r2
            goto L_0x0066
        L_0x0065:
            r3 = r1
        L_0x0066:
            if (r3 == 0) goto L_0x0069
            goto L_0x006b
        L_0x0069:
            r3 = r2
            goto L_0x006c
        L_0x006b:
            r3 = r1
        L_0x006c:
            boolean r4 = r15.mIsMonitoringColor
            if (r4 != r3) goto L_0x0072
            goto L_0x0175
        L_0x0072:
            r4 = 0
            if (r3 == 0) goto L_0x00a4
            com.google.android.systemui.assist.uihints.ScrimController r5 = r15.mScrimController
            java.util.Objects.requireNonNull(r5)
            android.view.View r5 = r5.mScrimView
            int r5 = r5.getVisibility()
            if (r5 != 0) goto L_0x0084
            r5 = r1
            goto L_0x0085
        L_0x0084:
            r5 = r2
        L_0x0085:
            if (r5 == 0) goto L_0x00a4
            com.google.android.systemui.assist.uihints.ScrimController r5 = r15.mScrimController
            java.util.Objects.requireNonNull(r5)
            android.view.View r6 = r5.mScrimView
            android.view.ViewRootImpl r6 = r6.getViewRootImpl()
            if (r6 != 0) goto L_0x0096
            r5 = r4
            goto L_0x00a0
        L_0x0096:
            android.view.View r5 = r5.mScrimView
            android.view.ViewRootImpl r5 = r5.getViewRootImpl()
            android.view.SurfaceControl r5 = r5.getSurfaceControl()
        L_0x00a0:
            if (r5 != 0) goto L_0x00a4
            goto L_0x0175
        L_0x00a4:
            r15.mIsMonitoringColor = r3
            if (r3 == 0) goto L_0x013a
            android.content.Context r5 = r15.mContext
            android.view.Display r5 = androidx.leanback.R$color.getDefaultDisplay(r5)
            android.graphics.Point r6 = new android.graphics.Point
            r6.<init>()
            r5.getRealSize(r6)
            int r5 = r6.y
            android.content.Context r6 = r15.mContext
            android.content.res.Resources r6 = r6.getResources()
            r7 = 2131167243(0x7f07080b, float:1.7948754E38)
            float r6 = r6.getDimension(r7)
            int r6 = (int) r6
            int r5 = r5 - r6
            r6 = 1101004800(0x41a00000, float:20.0)
            android.content.Context r7 = r15.mContext
            android.content.res.Resources r7 = r7.getResources()
            android.util.DisplayMetrics r7 = r7.getDisplayMetrics()
            r8 = 2
            float r6 = android.util.TypedValue.applyDimension(r8, r6, r7)
            int r6 = (int) r6
            int r5 = r5 - r6
            r6 = 1126170624(0x43200000, float:160.0)
            android.content.Context r7 = r15.mContext
            android.view.Display r7 = androidx.leanback.R$color.getDefaultDisplay(r7)
            android.util.DisplayMetrics r8 = new android.util.DisplayMetrics
            r8.<init>()
            r7.getRealMetrics(r8)
            float r7 = r8.density
            float r6 = r6 * r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            int r6 = r5 - r6
            android.graphics.Rect r7 = new android.graphics.Rect
            android.content.Context r8 = r15.mContext
            android.view.Display r8 = androidx.leanback.R$color.getDefaultDisplay(r8)
            android.graphics.Point r9 = new android.graphics.Point
            r9.<init>()
            r8.getRealSize(r9)
            int r8 = r9.x
            r7.<init>(r2, r6, r8, r5)
            long r5 = android.os.SystemClock.elapsedRealtime()
            r15.mColorMonitoringStart = r5
            com.google.android.systemui.assist.uihints.LightnessProvider r5 = r15.mLightnessProvider
            com.google.android.systemui.assist.uihints.ScrimController r6 = r15.mScrimController
            java.util.Objects.requireNonNull(r6)
            android.view.View r8 = r6.mScrimView
            android.view.ViewRootImpl r8 = r8.getViewRootImpl()
            if (r8 != 0) goto L_0x0120
            goto L_0x012a
        L_0x0120:
            android.view.View r4 = r6.mScrimView
            android.view.ViewRootImpl r4 = r4.getViewRootImpl()
            android.view.SurfaceControl r4 = r4.getSurfaceControl()
        L_0x012a:
            java.util.Objects.requireNonNull(r5)
            boolean r6 = r5.mIsMonitoringColor
            if (r6 != r1) goto L_0x0132
            goto L_0x0175
        L_0x0132:
            r5.mIsMonitoringColor = r1
            com.google.android.systemui.assist.uihints.LightnessProvider$1 r5 = r5.mColorMonitor
            android.view.CompositionSamplingListener.register(r5, r2, r4, r7)
            goto L_0x0175
        L_0x013a:
            com.google.android.systemui.assist.uihints.LightnessProvider r4 = r15.mLightnessProvider
            java.util.Objects.requireNonNull(r4)
            boolean r5 = r4.mIsMonitoringColor
            if (r5 != 0) goto L_0x0144
            goto L_0x014b
        L_0x0144:
            r4.mIsMonitoringColor = r2
            com.google.android.systemui.assist.uihints.LightnessProvider$1 r4 = r4.mColorMonitor
            android.view.CompositionSamplingListener.unregister(r4)
        L_0x014b:
            com.google.android.systemui.assist.uihints.IconController r4 = r15.mIconController
            java.util.Objects.requireNonNull(r4)
            r4.mHasAccurateLuma = r2
            com.google.android.systemui.assist.uihints.KeyboardIconView r5 = r4.mKeyboardIcon
            boolean r6 = r4.mKeyboardIconRequested
            r4.maybeUpdateIconVisibility(r5, r6)
            com.google.android.systemui.assist.uihints.ZeroStateIconView r5 = r4.mZeroStateIcon
            boolean r6 = r4.mZerostateIconRequested
            r4.maybeUpdateIconVisibility(r5, r6)
            com.google.android.systemui.assist.uihints.ScrimController r4 = r15.mScrimController
            java.util.Objects.requireNonNull(r4)
            r4.mHaveAccurateLightness = r2
            r4.refresh()
            com.google.android.systemui.assist.uihints.TranscriptionController r4 = r15.mTranscriptionController
            java.util.Objects.requireNonNull(r4)
            boolean r5 = r4.mHasAccurateBackground
            if (r5 == 0) goto L_0x0175
            r4.mHasAccurateBackground = r2
        L_0x0175:
            boolean r4 = r15.mShowingAssistUi
            if (r4 == r3) goto L_0x0210
            r15.mShowingAssistUi = r3
            com.google.android.systemui.assist.uihints.OverlayUiHost r4 = r15.mUiHost
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r5 = r15.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r5 = r5.getMode()
            boolean r5 = r5 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.FullListening
            if (r3 == 0) goto L_0x01bd
            boolean r6 = r4.mAttached
            if (r6 != 0) goto L_0x01bd
            android.view.WindowManager$LayoutParams r6 = new android.view.WindowManager$LayoutParams
            r8 = -1
            r9 = -1
            r10 = 0
            r11 = 0
            r12 = 2024(0x7e8, float:2.836E-42)
            r13 = 262952(0x40328, float:3.68474E-40)
            r14 = -3
            r7 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13, r14)
            r4.mLayoutParams = r6
            r4.mFocusable = r5
            r5 = 80
            r6.gravity = r5
            r5 = 64
            r6.privateFlags = r5
            r6.setFitInsetsTypes(r2)
            android.view.WindowManager$LayoutParams r5 = r4.mLayoutParams
            java.lang.String r6 = "Assist"
            r5.setTitle(r6)
            android.view.WindowManager r5 = r4.mWindowManager
            com.google.android.systemui.assist.uihints.AssistUIView r6 = r4.mRoot
            android.view.WindowManager$LayoutParams r7 = r4.mLayoutParams
            r5.addView(r6, r7)
            r4.mAttached = r1
            goto L_0x01e2
        L_0x01bd:
            if (r3 != 0) goto L_0x01cd
            boolean r6 = r4.mAttached
            if (r6 == 0) goto L_0x01cd
            android.view.WindowManager r5 = r4.mWindowManager
            com.google.android.systemui.assist.uihints.AssistUIView r6 = r4.mRoot
            r5.removeViewImmediate(r6)
            r4.mAttached = r2
            goto L_0x01e2
        L_0x01cd:
            if (r3 == 0) goto L_0x01df
            boolean r6 = r4.mFocusable
            if (r6 == r5) goto L_0x01e2
            android.view.WindowManager r6 = r4.mWindowManager
            com.google.android.systemui.assist.uihints.AssistUIView r7 = r4.mRoot
            android.view.WindowManager$LayoutParams r8 = r4.mLayoutParams
            r6.updateViewLayout(r7, r8)
            r4.mFocusable = r5
            goto L_0x01e2
        L_0x01df:
            java.util.Objects.requireNonNull(r4)
        L_0x01e2:
            if (r3 == 0) goto L_0x01f3
            com.google.android.systemui.assist.uihints.OverlayUiHost r3 = r15.mUiHost
            java.util.Objects.requireNonNull(r3)
            com.google.android.systemui.assist.uihints.AssistUIView r3 = r3.mRoot
            android.view.ViewTreeObserver r3 = r3.getViewTreeObserver()
            r3.addOnComputeInternalInsetsListener(r15)
            goto L_0x0210
        L_0x01f3:
            com.google.android.systemui.assist.uihints.OverlayUiHost r3 = r15.mUiHost
            java.util.Objects.requireNonNull(r3)
            com.google.android.systemui.assist.uihints.AssistUIView r3 = r3.mRoot
            android.view.ViewTreeObserver r3 = r3.getViewTreeObserver()
            r3.removeOnComputeInternalInsetsListener(r15)
            android.animation.ValueAnimator r3 = r15.mInvocationAnimator
            if (r3 == 0) goto L_0x0210
            boolean r3 = r3.isStarted()
            if (r3 == 0) goto L_0x0210
            android.animation.ValueAnimator r3 = r15.mInvocationAnimator
            r3.cancel()
        L_0x0210:
            boolean r3 = r15.mShouldKeepWakeLock
            if (r3 == r0) goto L_0x0223
            r15.mShouldKeepWakeLock = r0
            if (r0 == 0) goto L_0x021e
            android.os.PowerManager$WakeLock r0 = r15.mWakeLock
            r0.acquire()
            goto L_0x0223
        L_0x021e:
            android.os.PowerManager$WakeLock r0 = r15.mWakeLock
            r0.release()
        L_0x0223:
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController r0 = r15.mEdgeLightsController
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView$Mode r0 = r0.getMode()
            boolean r3 = r15.mInvocationInProgress
            if (r3 != 0) goto L_0x0231
            boolean r0 = r0 instanceof com.google.android.systemui.assist.uihints.edgelights.mode.Gone
            if (r0 != 0) goto L_0x0232
        L_0x0231:
            r1 = r2
        L_0x0232:
            com.google.android.systemui.assist.uihints.NavBarFader r15 = r15.mNavBarFader
            r15.onVisibleRequest(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.NgaUiController.refresh():void");
    }

    public final void setProgress(int i, float f) {
        int i2;
        this.mInvocationLightsView.onInvocationProgress(f);
        GlowController glowController = this.mGlowController;
        Objects.requireNonNull(glowController);
        if (glowController.mEdgeLightsMode instanceof Gone) {
            if (f > 0.0f) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            glowController.setVisibility(i2);
            GlowView glowView = glowController.mGlowView;
            int lerp = (int) MathUtils.lerp(glowController.getBlurRadius(), glowController.mContext.getResources().getDimensionPixelSize(C1777R.dimen.glow_tall_blur), Math.min(1.0f, 5.0f * f));
            Objects.requireNonNull(glowView);
            if (glowView.mBlurRadius != lerp) {
                glowView.setBlurredImageOnViews(lerp);
            }
            int min = (int) MathUtils.min((int) MathUtils.lerp(glowController.getMinTranslationY(), glowController.mContext.getResources().getDimensionPixelSize(C1777R.dimen.glow_tall_min_y), f), glowController.mContext.getResources().getDimensionPixelSize(C1777R.dimen.glow_invocation_max));
            glowController.mGlowsY = min;
            glowController.mGlowsYDestination = min;
            glowController.mGlowView.setGlowsY(min, min, (EdgeLight[]) null);
            glowController.mGlowView.distributeEvenly();
        }
        ScrimController scrimController = this.mScrimController;
        Objects.requireNonNull(scrimController);
        float constrain = MathUtils.constrain(f, 0.0f, 1.0f);
        if (scrimController.mInvocationProgress != constrain) {
            scrimController.mInvocationProgress = constrain;
            scrimController.refresh();
        }
        PromptView promptView = this.mPromptView;
        if (f > 1.0f) {
            Objects.requireNonNull(promptView);
        } else if (f == 0.0f) {
            promptView.setVisibility(8);
            promptView.setAlpha(0.0f);
            promptView.setTranslationY(0.0f);
            promptView.mLastInvocationType = 0;
        } else if (promptView.mEnabled) {
            if (i != 1) {
                if (i != 2) {
                    promptView.mLastInvocationType = 0;
                    promptView.setText("");
                } else if (promptView.mLastInvocationType != i) {
                    promptView.mLastInvocationType = i;
                    promptView.setText(promptView.mSqueezeString);
                    promptView.announceForAccessibility(promptView.mSqueezeString);
                }
            } else if (promptView.mLastInvocationType != i) {
                promptView.mLastInvocationType = i;
                promptView.setText(promptView.mHandleString);
                promptView.announceForAccessibility(promptView.mHandleString);
            }
            promptView.setVisibility(0);
            promptView.setTranslationY((-promptView.mRiseDistance) * f);
            if (i != 2 && f > 0.8f) {
                promptView.setAlpha(0.0f);
            } else if (f > 0.32000002f) {
                promptView.setAlpha(1.0f);
            } else {
                promptView.setAlpha(promptView.mDecelerateInterpolator.getInterpolation(f / 0.32000002f));
            }
        }
        refresh();
    }

    public final void onDozingChanged(boolean z) {
        if (Looper.myLooper() != this.mUiHandler.getLooper()) {
            this.mUiHandler.post(new NgaUiController$$ExternalSyntheticLambda5(this, z));
            return;
        }
        ScrimController scrimController = this.mScrimController;
        Objects.requireNonNull(scrimController);
        scrimController.mIsDozing = z;
        scrimController.refresh();
        if (z && this.mShowingAssistUi) {
            DejankUtils.whitelistIpcs((Runnable) new BubbleStackView$$ExternalSyntheticLambda15(this, 11));
        }
    }
}
