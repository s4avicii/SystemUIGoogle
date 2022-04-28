package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.util.MathUtils;
import android.view.View;
import androidx.preference.R$id;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

public final class UdfpsKeyguardViewController extends UdfpsAnimationViewController<UdfpsKeyguardView> {
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final C07136 mActivityLaunchAnimatorListener = new ActivityLaunchAnimator.Listener() {
        public final void onLaunchAnimationEnd() {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mIsLaunchingActivity = false;
            udfpsKeyguardViewController.updateAlpha();
        }

        public final void onLaunchAnimationProgress(float f) {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mActivityLaunchProgress = f;
            udfpsKeyguardViewController.updateAlpha();
        }

        public final void onLaunchAnimationStart() {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mIsLaunchingActivity = true;
            udfpsKeyguardViewController.mActivityLaunchProgress = 0.0f;
            udfpsKeyguardViewController.updateAlpha();
        }
    };
    public float mActivityLaunchProgress;
    public final C07092 mAlternateAuthInterceptor = new StatusBarKeyguardViewManager.AlternateAuthInterceptor() {
    };
    public final ConfigurationController mConfigurationController;
    public final C07103 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            ((UdfpsKeyguardView) UdfpsKeyguardViewController.this.mView).updateColor();
        }

        public final void onThemeChanged() {
            ((UdfpsKeyguardView) UdfpsKeyguardViewController.this.mView).updateColor();
        }

        public final void onUiModeChanged() {
            ((UdfpsKeyguardView) UdfpsKeyguardViewController.this.mView).updateColor();
        }
    };
    public float mInputBouncerHiddenAmount;
    public boolean mIsBouncerVisible;
    public boolean mIsLaunchingActivity;
    public final KeyguardStateController mKeyguardStateController;
    public final C07125 mKeyguardStateControllerCallback = new KeyguardStateController.Callback() {
        public final void onLaunchTransitionFadingAwayChanged() {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mLaunchTransitionFadingAway = udfpsKeyguardViewController.mKeyguardStateController.isLaunchTransitionFadingAway();
            UdfpsKeyguardViewController.this.updatePauseAuth();
        }
    };
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public float mLastDozeAmount;
    public long mLastUdfpsBouncerShowTime = -1;
    public boolean mLaunchTransitionFadingAway;
    public final LockscreenShadeTransitionController mLockScreenShadeTransitionController;
    public final C07114 mPanelExpansionListener = new PanelExpansionListener() {
        public final void onPanelExpansionChanged(float f, boolean z, boolean z2) {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mStatusBarExpansion = f;
            udfpsKeyguardViewController.updateAlpha();
        }
    };
    public boolean mQsExpanded;
    public boolean mShowingUdfpsBouncer;
    public final C07081 mStateListener = new StatusBarStateController.StateListener() {
        public final void onDozeAmountChanged(float f, float f2) {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            if (udfpsKeyguardViewController.mLastDozeAmount < f) {
                UdfpsKeyguardViewController.m170$$Nest$mshowUdfpsBouncer(udfpsKeyguardViewController, false);
            }
            UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) UdfpsKeyguardViewController.this.mView;
            Objects.requireNonNull(udfpsKeyguardView);
            udfpsKeyguardView.mInterpolatedDarkAmount = f2;
            udfpsKeyguardView.updateAlpha();
            udfpsKeyguardView.updateBurnInOffsets();
            UdfpsKeyguardViewController udfpsKeyguardViewController2 = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController2.mLastDozeAmount = f;
            udfpsKeyguardViewController2.updatePauseAuth();
        }

        public final void onStateChanged(int i) {
            UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
            udfpsKeyguardViewController.mStatusBarState = i;
            Objects.requireNonNull((UdfpsKeyguardView) udfpsKeyguardViewController.mView);
            UdfpsKeyguardViewController.this.updatePauseAuth();
        }
    };
    public float mStatusBarExpansion;
    public int mStatusBarState;
    public final SystemClock mSystemClock;
    public float mTransitionToFullShadeProgress;
    public final UdfpsController mUdfpsController;
    public boolean mUdfpsRequested;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public final UdfpsKeyguardViewController$$ExternalSyntheticLambda0 mUnlockedScreenOffCallback = new UdfpsKeyguardViewController$$ExternalSyntheticLambda0(this);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UdfpsKeyguardViewController(UdfpsKeyguardView udfpsKeyguardView, StatusBarStateController statusBarStateController, PanelExpansionStateManager panelExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, SystemClock systemClock, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, UdfpsController udfpsController, ActivityLaunchAnimator activityLaunchAnimator) {
        super(udfpsKeyguardView, statusBarStateController, panelExpansionStateManager, systemUIDialogManager, dumpManager);
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockScreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mConfigurationController = configurationController;
        this.mSystemClock = systemClock;
        this.mKeyguardStateController = keyguardStateController;
        this.mUdfpsController = udfpsController;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
    }

    public final String getTag() {
        return "UdfpsKeyguardViewController";
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("mNotificationShadeVisible=", Boolean.valueOf(this.notificationShadeVisible)));
        printWriter.println(Intrinsics.stringPlus("shouldPauseAuth()=", Boolean.valueOf(shouldPauseAuth())));
        printWriter.println(Intrinsics.stringPlus("isPauseAuth=", Boolean.valueOf(getView().mPauseAuth)));
        printWriter.println("mShowingUdfpsBouncer=" + this.mShowingUdfpsBouncer);
        printWriter.println("mFaceDetectRunning=false");
        printWriter.println("mStatusBarState=" + R$id.toShortString(this.mStatusBarState));
        StringBuilder sb = new StringBuilder();
        sb.append("mQsExpanded=");
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb, this.mQsExpanded, printWriter, "mIsBouncerVisible="), this.mIsBouncerVisible, printWriter, "mInputBouncerHiddenAmount=");
        m.append(this.mInputBouncerHiddenAmount);
        printWriter.println(m.toString());
        printWriter.println("mStatusBarExpansion=" + this.mStatusBarExpansion);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("unpausedAlpha=");
        UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) this.mView;
        Objects.requireNonNull(udfpsKeyguardView);
        sb2.append(udfpsKeyguardView.mAlpha);
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("mUdfpsRequested=");
        StringBuilder m2 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb3, this.mUdfpsRequested, printWriter, "mView.mUdfpsRequested="), ((UdfpsKeyguardView) this.mView).mUdfpsRequested, printWriter, "mLaunchTransitionFadingAway=");
        m2.append(this.mLaunchTransitionFadingAway);
        printWriter.println(m2.toString());
    }

    public final void onInit() {
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        C07092 r2 = this.mAlternateAuthInterceptor;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        if (!Objects.equals(statusBarKeyguardViewManager.mAlternateAuthInterceptor, r2)) {
            statusBarKeyguardViewManager.mAlternateAuthInterceptor = r2;
            statusBarKeyguardViewManager.resetAlternateAuth(false);
        }
    }

    public final void onTouchOutsideView() {
        boolean z;
        if (this.mShowingUdfpsBouncer) {
            if (this.mSystemClock.uptimeMillis() - this.mLastUdfpsBouncerShowTime > 200) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mKeyguardViewManager.showBouncer(true);
            }
        }
    }

    public final boolean shouldPauseAuth() {
        if (this.mShowingUdfpsBouncer) {
            return false;
        }
        if (this.mUdfpsRequested && !this.notificationShadeVisible && ((!this.mIsBouncerVisible || this.mInputBouncerHiddenAmount != 0.0f) && this.mKeyguardStateController.isShowing())) {
            return false;
        }
        if (!this.dialogManager.shouldHideAffordance() && !this.mLaunchTransitionFadingAway && this.mStatusBarState == 1 && !this.mQsExpanded && this.mInputBouncerHiddenAmount >= 0.5f && !this.mIsBouncerVisible) {
            return false;
        }
        return true;
    }

    public final void updateAlpha() {
        float f;
        int i;
        if (this.mUdfpsRequested) {
            f = this.mInputBouncerHiddenAmount;
        } else {
            f = this.mStatusBarExpansion;
        }
        if (this.mShowingUdfpsBouncer) {
            i = 255;
        } else {
            i = (int) MathUtils.constrain(MathUtils.map(0.5f, 0.9f, 0.0f, 255.0f, f), 0.0f, 255.0f);
        }
        if (!this.mShowingUdfpsBouncer) {
            i = (int) ((1.0f - this.mTransitionToFullShadeProgress) * ((float) i));
            if (this.mIsLaunchingActivity && !this.mUdfpsRequested) {
                i = (int) ((1.0f - this.mActivityLaunchProgress) * ((float) i));
            }
        }
        UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) this.mView;
        Objects.requireNonNull(udfpsKeyguardView);
        udfpsKeyguardView.mAlpha = i;
        udfpsKeyguardView.updateAlpha();
    }

    /* renamed from: -$$Nest$mshowUdfpsBouncer  reason: not valid java name */
    public static boolean m170$$Nest$mshowUdfpsBouncer(UdfpsKeyguardViewController udfpsKeyguardViewController, boolean z) {
        Objects.requireNonNull(udfpsKeyguardViewController);
        if (udfpsKeyguardViewController.mShowingUdfpsBouncer == z) {
            return false;
        }
        boolean shouldPauseAuth = udfpsKeyguardViewController.shouldPauseAuth();
        udfpsKeyguardViewController.mShowingUdfpsBouncer = z;
        if (z) {
            udfpsKeyguardViewController.mLastUdfpsBouncerShowTime = udfpsKeyguardViewController.mSystemClock.uptimeMillis();
        }
        if (udfpsKeyguardViewController.mShowingUdfpsBouncer) {
            if (shouldPauseAuth) {
                UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) udfpsKeyguardViewController.mView;
                Objects.requireNonNull(udfpsKeyguardView);
                if (!udfpsKeyguardView.mBackgroundInAnimator.isRunning() && udfpsKeyguardView.mFullyInflated) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    udfpsKeyguardView.mBackgroundInAnimator = animatorSet;
                    animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(udfpsKeyguardView.mBgProtection, View.ALPHA, new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(udfpsKeyguardView.mBgProtection, View.SCALE_X, new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(udfpsKeyguardView.mBgProtection, View.SCALE_Y, new float[]{0.0f, 1.0f})});
                    udfpsKeyguardView.mBackgroundInAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    udfpsKeyguardView.mBackgroundInAnimator.setDuration(500);
                    udfpsKeyguardView.mBackgroundInAnimator.addListener(new AnimatorListenerAdapter() {
                        public final /* synthetic */ Runnable val$onEndAnimation;

                        public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.biometrics.UdfpsKeyguardView.1.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                        jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.biometrics.UdfpsKeyguardView.1.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
                        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        
*/
                    });
                    udfpsKeyguardView.mBackgroundInAnimator.start();
                }
            }
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = udfpsKeyguardViewController.mKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            if (statusBarKeyguardViewManager.mOccluded) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = udfpsKeyguardViewController.mKeyguardUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor);
                keyguardUpdateMonitor.mOccludingAppRequestingFace = true;
                keyguardUpdateMonitor.updateFaceListeningState(2);
            }
            UdfpsKeyguardView udfpsKeyguardView2 = (UdfpsKeyguardView) udfpsKeyguardViewController.mView;
            udfpsKeyguardView2.announceForAccessibility(udfpsKeyguardView2.getContext().getString(C1777R.string.accessibility_fingerprint_bouncer));
        } else {
            KeyguardUpdateMonitor keyguardUpdateMonitor2 = udfpsKeyguardViewController.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor2);
            keyguardUpdateMonitor2.mOccludingAppRequestingFace = false;
            keyguardUpdateMonitor2.updateFaceListeningState(2);
        }
        udfpsKeyguardViewController.updateAlpha();
        udfpsKeyguardViewController.updatePauseAuth();
        return true;
    }

    public final void onViewAttached() {
        super.onViewAttached();
        float dozeAmount = this.statusBarStateController.getDozeAmount();
        this.mLastDozeAmount = dozeAmount;
        this.mStateListener.onDozeAmountChanged(dozeAmount, dozeAmount);
        this.statusBarStateController.addCallback(this.mStateListener);
        this.mUdfpsRequested = false;
        this.mLaunchTransitionFadingAway = this.mKeyguardStateController.isLaunchTransitionFadingAway();
        this.mKeyguardStateController.addCallback(this.mKeyguardStateControllerCallback);
        this.mStatusBarState = this.statusBarStateController.getState();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        this.mQsExpanded = statusBarKeyguardViewManager.mQsExpanded;
        this.mInputBouncerHiddenAmount = 1.0f;
        this.mIsBouncerVisible = this.mKeyguardViewManager.bouncerIsOrWillBeShowing();
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.panelExpansionStateManager.addExpansionListener(this.mPanelExpansionListener);
        updateAlpha();
        updatePauseAuth();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager2 = this.mKeyguardViewManager;
        C07092 r2 = this.mAlternateAuthInterceptor;
        Objects.requireNonNull(statusBarKeyguardViewManager2);
        if (!Objects.equals(statusBarKeyguardViewManager2.mAlternateAuthInterceptor, r2)) {
            statusBarKeyguardViewManager2.mAlternateAuthInterceptor = r2;
            statusBarKeyguardViewManager2.resetAlternateAuth(false);
        }
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockScreenShadeTransitionController;
        Objects.requireNonNull(lockscreenShadeTransitionController);
        lockscreenShadeTransitionController.udfpsKeyguardViewController = this;
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.mUnlockedScreenOffAnimationController;
        UdfpsKeyguardViewController$$ExternalSyntheticLambda0 udfpsKeyguardViewController$$ExternalSyntheticLambda0 = this.mUnlockedScreenOffCallback;
        Objects.requireNonNull(unlockedScreenOffAnimationController);
        unlockedScreenOffAnimationController.callbacks.add(udfpsKeyguardViewController$$ExternalSyntheticLambda0);
        ActivityLaunchAnimator activityLaunchAnimator = this.mActivityLaunchAnimator;
        C07136 r4 = this.mActivityLaunchAnimatorListener;
        Objects.requireNonNull(activityLaunchAnimator);
        activityLaunchAnimator.listeners.add(r4);
    }

    public final void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardStateController.removeCallback(this.mKeyguardStateControllerCallback);
        this.statusBarStateController.removeCallback(this.mStateListener);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        C07092 r1 = this.mAlternateAuthInterceptor;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        if (Objects.equals(statusBarKeyguardViewManager.mAlternateAuthInterceptor, r1)) {
            statusBarKeyguardViewManager.mAlternateAuthInterceptor = null;
            statusBarKeyguardViewManager.resetAlternateAuth(true);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        keyguardUpdateMonitor.mOccludingAppRequestingFace = false;
        keyguardUpdateMonitor.updateFaceListeningState(2);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        PanelExpansionStateManager panelExpansionStateManager = this.panelExpansionStateManager;
        C07114 r12 = this.mPanelExpansionListener;
        Objects.requireNonNull(panelExpansionStateManager);
        panelExpansionStateManager.expansionListeners.remove(r12);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockScreenShadeTransitionController;
        Objects.requireNonNull(lockscreenShadeTransitionController);
        if (lockscreenShadeTransitionController.udfpsKeyguardViewController == this) {
            LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.mLockScreenShadeTransitionController;
            Objects.requireNonNull(lockscreenShadeTransitionController2);
            lockscreenShadeTransitionController2.udfpsKeyguardViewController = null;
        }
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.mUnlockedScreenOffAnimationController;
        UdfpsKeyguardViewController$$ExternalSyntheticLambda0 udfpsKeyguardViewController$$ExternalSyntheticLambda0 = this.mUnlockedScreenOffCallback;
        Objects.requireNonNull(unlockedScreenOffAnimationController);
        unlockedScreenOffAnimationController.callbacks.remove(udfpsKeyguardViewController$$ExternalSyntheticLambda0);
        ActivityLaunchAnimator activityLaunchAnimator = this.mActivityLaunchAnimator;
        C07136 r3 = this.mActivityLaunchAnimatorListener;
        Objects.requireNonNull(activityLaunchAnimator);
        activityLaunchAnimator.listeners.remove(r3);
    }
}
