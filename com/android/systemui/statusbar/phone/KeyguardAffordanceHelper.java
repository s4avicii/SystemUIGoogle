package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.RenderNodeAnimator;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.animation.PathInterpolator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import java.util.Objects;

public final class KeyguardAffordanceHelper {
    public C14222 mAnimationEndRunnable = new Runnable() {
        public final void run() {
            NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) KeyguardAffordanceHelper.this.mCallback;
            Objects.requireNonNull(keyguardAffordanceHelperCallback);
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mIsLaunchTransitionRunning = false;
            notificationPanelViewController.mIsLaunchTransitionFinished = true;
            Runnable runnable = notificationPanelViewController.mLaunchAnimationEndRunnable;
            if (runnable != null) {
                runnable.run();
                NotificationPanelViewController.this.mLaunchAnimationEndRunnable = null;
            }
            StatusBar statusBar = NotificationPanelViewController.this.mStatusBar;
            Objects.requireNonNull(statusBar);
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = statusBar.mStatusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            statusBarKeyguardViewManager.mViewMediatorCallback.readyForKeyguardDone();
        }
    };
    public final Callback mCallback;
    public final Context mContext;
    public final FalsingManager mFalsingManager;
    public FlingAnimationUtils mFlingAnimationUtils;
    public C14211 mFlingEndListener = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            KeyguardAffordanceHelper keyguardAffordanceHelper = KeyguardAffordanceHelper.this;
            keyguardAffordanceHelper.mSwipeAnimator = null;
            keyguardAffordanceHelper.mSwipingInProgress = false;
            keyguardAffordanceHelper.mTargetedView = null;
        }
    };
    public int mHintGrowAmount;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public KeyguardAffordanceView mLeftIcon;
    public int mMinBackgroundRadius;
    public int mMinFlingVelocity;
    public int mMinTranslationAmount;
    public boolean mMotionCancelled;
    public KeyguardAffordanceView mRightIcon;
    public Animator mSwipeAnimator;
    public boolean mSwipingInProgress;
    public View mTargetedView;
    public int mTouchSlop;
    public boolean mTouchSlopExeeded;
    public int mTouchTargetSize;
    public float mTranslation;
    public float mTranslationOnDown;
    public VelocityTracker mVelocityTracker;

    public interface Callback {
    }

    public KeyguardAffordanceHelper(NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback, Context context, FalsingManager falsingManager) {
        this.mContext = context;
        this.mCallback = keyguardAffordanceHelperCallback;
        initIcons();
        KeyguardAffordanceView keyguardAffordanceView = this.mLeftIcon;
        Objects.requireNonNull(keyguardAffordanceView);
        updateIcon(keyguardAffordanceView, 0.0f, keyguardAffordanceView.mRestingAlpha, false, false, true, false);
        KeyguardAffordanceView keyguardAffordanceView2 = this.mRightIcon;
        Objects.requireNonNull(keyguardAffordanceView2);
        updateIcon(keyguardAffordanceView2, 0.0f, keyguardAffordanceView2.mRestingAlpha, false, false, true, false);
        this.mFalsingManager = falsingManager;
        initDimens();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x009f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void endMotion(boolean r9, float r10, float r11) {
        /*
            r8 = this;
            boolean r0 = r8.mSwipingInProgress
            r1 = 0
            if (r0 == 0) goto L_0x00b0
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            r2 = 0
            if (r0 != 0) goto L_0x000c
            r3 = r2
            goto L_0x0035
        L_0x000c:
            r3 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r3)
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            float r0 = r0.getXVelocity()
            android.view.VelocityTracker r3 = r8.mVelocityTracker
            float r3 = r3.getYVelocity()
            float r4 = r8.mInitialTouchX
            float r10 = r10 - r4
            float r4 = r8.mInitialTouchY
            float r11 = r11 - r4
            double r4 = (double) r10
            double r6 = (double) r11
            double r4 = java.lang.Math.hypot(r4, r6)
            float r4 = (float) r4
            float r0 = r0 * r10
            float r3 = r3 * r11
            float r3 = r3 + r0
            float r3 = r3 / r4
            android.view.View r10 = r8.mTargetedView
            com.android.systemui.statusbar.KeyguardAffordanceView r11 = r8.mRightIcon
            if (r10 != r11) goto L_0x0035
            float r3 = -r3
        L_0x0035:
            com.android.systemui.statusbar.phone.KeyguardAffordanceHelper$Callback r10 = r8.mCallback
            com.android.systemui.statusbar.phone.NotificationPanelViewController$KeyguardAffordanceHelperCallback r10 = (com.android.systemui.statusbar.phone.NotificationPanelViewController.KeyguardAffordanceHelperCallback) r10
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r10 = com.android.systemui.statusbar.phone.NotificationPanelViewController.this
            int r10 = r10.mBarState
            r11 = 0
            r0 = 1
            if (r10 != r0) goto L_0x0046
            r10 = r0
            goto L_0x0047
        L_0x0046:
            r10 = r11
        L_0x0047:
            if (r10 == 0) goto L_0x005c
            com.android.systemui.plugins.FalsingManager r10 = r8.mFalsingManager
            android.view.View r4 = r8.mTargetedView
            com.android.systemui.statusbar.KeyguardAffordanceView r5 = r8.mRightIcon
            if (r4 != r5) goto L_0x0053
            r4 = 6
            goto L_0x0054
        L_0x0053:
            r4 = 5
        L_0x0054:
            boolean r10 = r10.isFalseTouch(r4)
            if (r10 == 0) goto L_0x005c
            r10 = r0
            goto L_0x005d
        L_0x005c:
            r10 = r11
        L_0x005d:
            if (r10 != 0) goto L_0x007d
            float r10 = r8.mTranslation
            float r10 = java.lang.Math.abs(r10)
            float r4 = r8.mTranslationOnDown
            float r4 = java.lang.Math.abs(r4)
            int r5 = r8.getMinTranslationAmount()
            float r5 = (float) r5
            float r4 = r4 + r5
            int r10 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r10 >= 0) goto L_0x0077
            r10 = r0
            goto L_0x0078
        L_0x0077:
            r10 = r11
        L_0x0078:
            if (r10 == 0) goto L_0x007b
            goto L_0x007d
        L_0x007b:
            r10 = r11
            goto L_0x007e
        L_0x007d:
            r10 = r0
        L_0x007e:
            float r4 = r8.mTranslation
            float r4 = r4 * r3
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0087
            r4 = r0
            goto L_0x0088
        L_0x0087:
            r4 = r11
        L_0x0088:
            float r5 = java.lang.Math.abs(r3)
            int r6 = r8.mMinFlingVelocity
            float r6 = (float) r6
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0097
            if (r4 == 0) goto L_0x0097
            r5 = r0
            goto L_0x0098
        L_0x0097:
            r5 = r11
        L_0x0098:
            r10 = r10 | r5
            r4 = r4 ^ r10
            if (r4 == 0) goto L_0x009d
            r3 = r2
        L_0x009d:
            if (r10 != 0) goto L_0x00a4
            if (r9 == 0) goto L_0x00a2
            goto L_0x00a4
        L_0x00a2:
            r9 = r11
            goto L_0x00a5
        L_0x00a4:
            r9 = r0
        L_0x00a5:
            float r10 = r8.mTranslation
            int r10 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r10 >= 0) goto L_0x00ac
            r11 = r0
        L_0x00ac:
            r8.fling(r3, r9, r11)
            goto L_0x00b2
        L_0x00b0:
            r8.mTargetedView = r1
        L_0x00b2:
            android.view.VelocityTracker r9 = r8.mVelocityTracker
            if (r9 == 0) goto L_0x00bb
            r9.recycle()
            r8.mVelocityTracker = r1
        L_0x00bb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.endMotion(boolean, float, float):void");
    }

    public final void fling(float f, boolean z, boolean z2) {
        float f2;
        KeyguardAffordanceView keyguardAffordanceView;
        ValueAnimator valueAnimator;
        float f3 = f;
        boolean z3 = z2;
        if (z3) {
            f2 = -((NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback).getMaxTranslationDistance();
        } else {
            f2 = ((NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback).getMaxTranslationDistance();
        }
        if (z) {
            f2 = 0.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mTranslation, f2});
        this.mFlingAnimationUtils.apply(ofFloat, this.mTranslation, f2, f3);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardAffordanceHelper.this.mTranslation = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            }
        });
        ofFloat.addListener(this.mFlingEndListener);
        if (!z) {
            float f4 = 0.375f * f3;
            C14222 r14 = this.mAnimationEndRunnable;
            if (z3) {
                keyguardAffordanceView = this.mRightIcon;
            } else {
                keyguardAffordanceView = this.mLeftIcon;
            }
            KeyguardAffordanceView keyguardAffordanceView2 = keyguardAffordanceView;
            Objects.requireNonNull(keyguardAffordanceView2);
            KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView2.mCircleAnimator);
            KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView2.mPreviewClipper);
            keyguardAffordanceView2.mFinishing = true;
            keyguardAffordanceView2.mCircleStartRadius = keyguardAffordanceView2.mCircleRadius;
            float maxCircleSize = keyguardAffordanceView2.getMaxCircleSize();
            if (keyguardAffordanceView2.mSupportHardware) {
                keyguardAffordanceView2.mHwCenterX = CanvasProperty.createFloat((float) keyguardAffordanceView2.mCenterX);
                keyguardAffordanceView2.mHwCenterY = CanvasProperty.createFloat((float) keyguardAffordanceView2.mCenterY);
                keyguardAffordanceView2.mHwCirclePaint = CanvasProperty.createPaint(keyguardAffordanceView2.mCirclePaint);
                keyguardAffordanceView2.mHwCircleRadius = CanvasProperty.createFloat(keyguardAffordanceView2.mCircleRadius);
                valueAnimator = new RenderNodeAnimator(keyguardAffordanceView2.mHwCircleRadius, maxCircleSize);
                valueAnimator.setTarget(keyguardAffordanceView2);
                if (keyguardAffordanceView2.mCircleRadius == 0.0f && keyguardAffordanceView2.mPreviewView == null) {
                    Paint paint = new Paint(keyguardAffordanceView2.mCirclePaint);
                    paint.setColor(keyguardAffordanceView2.mCircleColor);
                    paint.setAlpha(0);
                    keyguardAffordanceView2.mHwCirclePaint = CanvasProperty.createPaint(paint);
                    RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(keyguardAffordanceView2.mHwCirclePaint, 1, 255.0f);
                    renderNodeAnimator.setTarget(keyguardAffordanceView2);
                    renderNodeAnimator.setInterpolator(Interpolators.ALPHA_IN);
                    renderNodeAnimator.setDuration(250);
                    renderNodeAnimator.start();
                }
            } else {
                valueAnimator = keyguardAffordanceView2.getAnimatorToRadius(maxCircleSize);
            }
            ValueAnimator valueAnimator2 = valueAnimator;
            ValueAnimator valueAnimator3 = valueAnimator2;
            float f5 = maxCircleSize;
            keyguardAffordanceView2.mFlingAnimationUtils.applyDismissing(valueAnimator2, keyguardAffordanceView2.mCircleRadius, maxCircleSize, f4, maxCircleSize);
            valueAnimator3.addListener(new AnimatorListenerAdapter(r14, maxCircleSize) {
                public final /* synthetic */ Runnable val$mAnimationEndRunnable;
                public final /* synthetic */ float val$maxCircleSize;

                public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.statusbar.KeyguardAffordanceView.5.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.statusbar.KeyguardAffordanceView.5.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
            valueAnimator3.start();
            keyguardAffordanceView2.setImageAlpha(0.0f, true);
            View view = keyguardAffordanceView2.mPreviewView;
            if (view != null) {
                view.setVisibility(0);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(keyguardAffordanceView2.mPreviewView, keyguardAffordanceView2.getLeft() + keyguardAffordanceView2.mCenterX, keyguardAffordanceView2.getTop() + keyguardAffordanceView2.mCenterY, keyguardAffordanceView2.mCircleRadius, maxCircleSize);
                keyguardAffordanceView2.mPreviewClipper = createCircularReveal;
                keyguardAffordanceView2.mFlingAnimationUtils.applyDismissing(createCircularReveal, keyguardAffordanceView2.mCircleRadius, maxCircleSize, f4, maxCircleSize);
                keyguardAffordanceView2.mPreviewClipper.addListener(keyguardAffordanceView2.mClipEndListener);
                keyguardAffordanceView2.mPreviewClipper.start();
                if (keyguardAffordanceView2.mSupportHardware) {
                    long duration = valueAnimator3.getDuration();
                    RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(keyguardAffordanceView2.mHwCirclePaint, 1, 0.0f);
                    renderNodeAnimator2.setDuration(duration);
                    renderNodeAnimator2.setInterpolator(Interpolators.ALPHA_OUT);
                    renderNodeAnimator2.setTarget(keyguardAffordanceView2);
                    renderNodeAnimator2.start();
                }
            }
            ((NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback).onAnimationToSideStarted(z3, this.mTranslation, f3);
        } else {
            reset(true);
        }
        ofFloat.start();
        this.mSwipeAnimator = ofFloat;
        if (z) {
            NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback;
            Objects.requireNonNull(keyguardAffordanceHelperCallback);
            NotificationPanelViewController.this.mFalsingCollector.onAffordanceSwipingAborted();
            NotificationPanelViewController.this.mKeyguardBottomArea.unbindCameraPrewarmService(false);
        }
    }

    public final ValueAnimator getAnimatorToRadius(final boolean z, int i) {
        final KeyguardAffordanceView keyguardAffordanceView;
        if (z) {
            keyguardAffordanceView = this.mRightIcon;
        } else {
            keyguardAffordanceView = this.mLeftIcon;
        }
        Objects.requireNonNull(keyguardAffordanceView);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{keyguardAffordanceView.mCircleRadius, (float) i});
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardAffordanceView keyguardAffordanceView = keyguardAffordanceView;
                Objects.requireNonNull(keyguardAffordanceView);
                KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView.mCircleAnimator);
                keyguardAffordanceView.setCircleRadius(floatValue, false, true);
                KeyguardAffordanceHelper keyguardAffordanceHelper = KeyguardAffordanceHelper.this;
                Objects.requireNonNull(keyguardAffordanceHelper);
                float f2 = (floatValue - ((float) keyguardAffordanceHelper.mMinBackgroundRadius)) / 0.25f;
                if (f2 > 0.0f) {
                    f = f2 + ((float) keyguardAffordanceHelper.mTouchSlop);
                } else {
                    f = 0.0f;
                }
                KeyguardAffordanceHelper keyguardAffordanceHelper2 = KeyguardAffordanceHelper.this;
                if (z) {
                    f = -f;
                }
                keyguardAffordanceHelper2.mTranslation = f;
                KeyguardAffordanceView keyguardAffordanceView2 = keyguardAffordanceView;
                float abs = Math.abs(f) / ((float) keyguardAffordanceHelper2.getMinTranslationAmount());
                float max = Math.max(0.0f, 1.0f - abs);
                KeyguardAffordanceView keyguardAffordanceView3 = keyguardAffordanceHelper2.mRightIcon;
                if (keyguardAffordanceView2 == keyguardAffordanceView3) {
                    keyguardAffordanceView3 = keyguardAffordanceHelper2.mLeftIcon;
                }
                Objects.requireNonNull(keyguardAffordanceView2);
                KeyguardAffordanceHelper.updateIconAlpha(keyguardAffordanceView2, (keyguardAffordanceView2.mRestingAlpha * max) + abs, false);
                Objects.requireNonNull(keyguardAffordanceView3);
                KeyguardAffordanceHelper.updateIconAlpha(keyguardAffordanceView3, max * keyguardAffordanceView3.mRestingAlpha, false);
            }
        });
        return ofFloat;
    }

    public final int getMinTranslationAmount() {
        float f;
        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback;
        Objects.requireNonNull(keyguardAffordanceHelperCallback);
        StatusBar statusBar = NotificationPanelViewController.this.mStatusBar;
        Objects.requireNonNull(statusBar);
        if (statusBar.mWakeUpComingFromTouch) {
            f = 1.5f;
        } else {
            f = 1.0f;
        }
        return (int) (((float) this.mMinTranslationAmount) * f);
    }

    public final void initDimens() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMinTranslationAmount = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_min_swipe_amount);
        this.mMinBackgroundRadius = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_min_background_radius);
        this.mTouchTargetSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_touch_target_size);
        this.mHintGrowAmount = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.hint_grow_amount_sideways);
        this.mFlingAnimationUtils = new FlingAnimationUtils(this.mContext.getResources().getDisplayMetrics(), 0.4f);
    }

    public final void initIcons() {
        this.mLeftIcon = ((NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback).getLeftIcon();
        this.mRightIcon = ((NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback).getRightIcon();
        updatePreviews();
    }

    public final void reset(boolean z) {
        Animator animator = this.mSwipeAnimator;
        if (animator != null) {
            animator.cancel();
        }
        setTranslation(0.0f, true, z);
        this.mMotionCancelled = true;
        if (this.mSwipingInProgress) {
            NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback;
            Objects.requireNonNull(keyguardAffordanceHelperCallback);
            NotificationPanelViewController.this.mFalsingCollector.onAffordanceSwipingAborted();
            NotificationPanelViewController.this.mKeyguardBottomArea.unbindCameraPrewarmService(false);
            this.mSwipingInProgress = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setTranslation(float r20, boolean r21, boolean r22) {
        /*
            r19 = this;
            r0 = r19
            com.android.systemui.statusbar.KeyguardAffordanceView r1 = r0.mRightIcon
            int r1 = r1.getVisibility()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x000e
            r1 = r2
            goto L_0x000f
        L_0x000e:
            r1 = r3
        L_0x000f:
            r4 = 0
            if (r1 == 0) goto L_0x0015
            r1 = r20
            goto L_0x001b
        L_0x0015:
            r1 = r20
            float r1 = java.lang.Math.max(r4, r1)
        L_0x001b:
            com.android.systemui.statusbar.KeyguardAffordanceView r5 = r0.mLeftIcon
            int r5 = r5.getVisibility()
            if (r5 != 0) goto L_0x0025
            r5 = r2
            goto L_0x0026
        L_0x0025:
            r5 = r3
        L_0x0026:
            if (r5 == 0) goto L_0x0029
            goto L_0x002d
        L_0x0029:
            float r1 = java.lang.Math.min(r4, r1)
        L_0x002d:
            r8 = r1
            float r1 = java.lang.Math.abs(r8)
            float r5 = r0.mTranslation
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x003a
            if (r21 == 0) goto L_0x00d8
        L_0x003a:
            int r5 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x0041
            com.android.systemui.statusbar.KeyguardAffordanceView r6 = r0.mLeftIcon
            goto L_0x0043
        L_0x0041:
            com.android.systemui.statusbar.KeyguardAffordanceView r6 = r0.mRightIcon
        L_0x0043:
            r9 = r6
            if (r5 <= 0) goto L_0x0049
            com.android.systemui.statusbar.KeyguardAffordanceView r5 = r0.mRightIcon
            goto L_0x004b
        L_0x0049:
            com.android.systemui.statusbar.KeyguardAffordanceView r5 = r0.mLeftIcon
        L_0x004b:
            int r6 = r19.getMinTranslationAmount()
            float r6 = (float) r6
            float r6 = r1 / r6
            r7 = 1065353216(0x3f800000, float:1.0)
            float r7 = r7 - r6
            float r7 = java.lang.Math.max(r7, r4)
            if (r21 == 0) goto L_0x0060
            if (r22 == 0) goto L_0x0060
            r16 = r2
            goto L_0x0062
        L_0x0060:
            r16 = r3
        L_0x0062:
            if (r21 == 0) goto L_0x0069
            if (r22 != 0) goto L_0x0069
            r17 = r2
            goto L_0x006b
        L_0x0069:
            r17 = r3
        L_0x006b:
            int r10 = r0.mTouchSlop
            float r10 = (float) r10
            int r11 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r11 > 0) goto L_0x0073
            goto L_0x007b
        L_0x0073:
            float r1 = r1 - r10
            r4 = 1048576000(0x3e800000, float:0.25)
            float r1 = r1 * r4
            int r4 = r0.mMinBackgroundRadius
            float r4 = (float) r4
            float r4 = r4 + r1
        L_0x007b:
            r10 = r4
            if (r21 == 0) goto L_0x009c
            float r1 = r0.mTranslation
            float r1 = java.lang.Math.abs(r1)
            float r4 = r0.mTranslationOnDown
            float r4 = java.lang.Math.abs(r4)
            int r11 = r19.getMinTranslationAmount()
            float r11 = (float) r11
            float r4 = r4 + r11
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x0096
            r1 = r2
            goto L_0x0097
        L_0x0096:
            r1 = r3
        L_0x0097:
            if (r1 == 0) goto L_0x009c
            r18 = r2
            goto L_0x009e
        L_0x009c:
            r18 = r3
        L_0x009e:
            if (r21 != 0) goto L_0x00b0
            java.util.Objects.requireNonNull(r9)
            float r1 = r9.mRestingAlpha
            float r1 = r1 * r7
            float r11 = r1 + r6
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            updateIcon(r9, r10, r11, r12, r13, r14, r15)
            goto L_0x00c2
        L_0x00b0:
            r10 = 0
            java.util.Objects.requireNonNull(r9)
            float r1 = r9.mRestingAlpha
            float r11 = r7 * r1
            r14 = 1
            r12 = r16
            r13 = r18
            r15 = r17
            updateIcon(r9, r10, r11, r12, r13, r14, r15)
        L_0x00c2:
            r2 = 0
            java.util.Objects.requireNonNull(r5)
            float r1 = r5.mRestingAlpha
            float r3 = r7 * r1
            r1 = r5
            r4 = r16
            r5 = r18
            r6 = r21
            r7 = r17
            updateIcon(r1, r2, r3, r4, r5, r6, r7)
            r0.mTranslation = r8
        L_0x00d8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardAffordanceHelper.setTranslation(float, boolean, boolean):void");
    }

    public final void startSwiping(KeyguardAffordanceView keyguardAffordanceView) {
        boolean z;
        Bundle bundle;
        String string;
        Callback callback = this.mCallback;
        if (keyguardAffordanceView == this.mRightIcon) {
            z = true;
        } else {
            z = false;
        }
        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) callback;
        Objects.requireNonNull(keyguardAffordanceHelperCallback);
        NotificationPanelViewController.this.mFalsingCollector.onAffordanceSwipingStarted();
        if (NotificationPanelViewController.this.mView.getLayoutDirection() == 1) {
            if (!z) {
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            KeyguardBottomAreaView keyguardBottomAreaView = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView);
            ActivityInfo targetActivityInfo = keyguardBottomAreaView.mActivityIntentHelper.getTargetActivityInfo(keyguardBottomAreaView.mRightButton.getIntent(), KeyguardUpdateMonitor.getCurrentUser(), true);
            if (!(targetActivityInfo == null || (bundle = targetActivityInfo.metaData) == null || (string = bundle.getString("android.media.still_image_camera_preview_service")) == null)) {
                Intent intent = new Intent();
                intent.setClassName(targetActivityInfo.packageName, string);
                intent.setAction("android.service.media.CameraPrewarmService.ACTION_PREWARM");
                try {
                    if (keyguardBottomAreaView.getContext().bindServiceAsUser(intent, keyguardBottomAreaView.mPrewarmConnection, 67108865, new UserHandle(-2))) {
                        keyguardBottomAreaView.mPrewarmBound = true;
                    }
                } catch (SecurityException e) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to bind to prewarm service package=");
                    m.append(targetActivityInfo.packageName);
                    m.append(" class=");
                    m.append(string);
                    Log.w("StatusBar/KeyguardBottomAreaView", m.toString(), e);
                }
            }
        }
        NotificationPanelViewController.this.mView.requestDisallowInterceptTouchEvent(true);
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        notificationPanelViewController.mOnlyAffordanceInThisMotion = true;
        notificationPanelViewController.mQsTracking = false;
        this.mSwipingInProgress = true;
        this.mTargetedView = keyguardAffordanceView;
    }

    public final void updatePreviews() {
        KeyguardPreviewContainer keyguardPreviewContainer;
        KeyguardPreviewContainer keyguardPreviewContainer2;
        int i;
        KeyguardAffordanceView keyguardAffordanceView = this.mLeftIcon;
        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback;
        Objects.requireNonNull(keyguardAffordanceHelperCallback);
        if (NotificationPanelViewController.this.mView.getLayoutDirection() == 1) {
            KeyguardBottomAreaView keyguardBottomAreaView = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView);
            keyguardPreviewContainer = keyguardBottomAreaView.mCameraPreview;
        } else {
            KeyguardBottomAreaView keyguardBottomAreaView2 = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView2);
            keyguardPreviewContainer = keyguardBottomAreaView2.mLeftPreview;
        }
        Objects.requireNonNull(keyguardAffordanceView);
        View view = keyguardAffordanceView.mPreviewView;
        int i2 = 4;
        if (view != keyguardPreviewContainer) {
            keyguardAffordanceView.mPreviewView = keyguardPreviewContainer;
            if (keyguardPreviewContainer != null) {
                if (keyguardAffordanceView.mLaunchingAffordance) {
                    i = view.getVisibility();
                } else {
                    i = 4;
                }
                keyguardPreviewContainer.setVisibility(i);
            }
        }
        KeyguardAffordanceView keyguardAffordanceView2 = this.mRightIcon;
        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback2 = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.mCallback;
        Objects.requireNonNull(keyguardAffordanceHelperCallback2);
        if (NotificationPanelViewController.this.mView.getLayoutDirection() == 1) {
            KeyguardBottomAreaView keyguardBottomAreaView3 = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView3);
            keyguardPreviewContainer2 = keyguardBottomAreaView3.mLeftPreview;
        } else {
            KeyguardBottomAreaView keyguardBottomAreaView4 = NotificationPanelViewController.this.mKeyguardBottomArea;
            Objects.requireNonNull(keyguardBottomAreaView4);
            keyguardPreviewContainer2 = keyguardBottomAreaView4.mCameraPreview;
        }
        Objects.requireNonNull(keyguardAffordanceView2);
        View view2 = keyguardAffordanceView2.mPreviewView;
        if (view2 != keyguardPreviewContainer2) {
            keyguardAffordanceView2.mPreviewView = keyguardPreviewContainer2;
            if (keyguardPreviewContainer2 != null) {
                if (keyguardAffordanceView2.mLaunchingAffordance) {
                    i2 = view2.getVisibility();
                }
                keyguardPreviewContainer2.setVisibility(i2);
            }
        }
    }

    public static void updateIcon(KeyguardAffordanceView keyguardAffordanceView, float f, float f2, boolean z, boolean z2, boolean z3, boolean z4) {
        if (keyguardAffordanceView.getVisibility() == 0 || z3) {
            if (z4) {
                KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView.mCircleAnimator);
                keyguardAffordanceView.setCircleRadius(f, false, true);
            } else {
                keyguardAffordanceView.setCircleRadius(f, z2, false);
            }
            updateIconAlpha(keyguardAffordanceView, f2, z);
        }
    }

    public static void updateIconAlpha(KeyguardAffordanceView keyguardAffordanceView, float f, boolean z) {
        PathInterpolator pathInterpolator;
        Objects.requireNonNull(keyguardAffordanceView);
        float min = Math.min(((f / keyguardAffordanceView.mRestingAlpha) * 0.2f) + 0.8f, 1.5f);
        keyguardAffordanceView.setImageAlpha(Math.min(1.0f, f), z);
        KeyguardAffordanceView.cancelAnimator(keyguardAffordanceView.mScaleAnimator);
        if (!z) {
            keyguardAffordanceView.mImageScale = min;
            keyguardAffordanceView.invalidate();
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{keyguardAffordanceView.mImageScale, min});
        keyguardAffordanceView.mScaleAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(
/*
Method generation error in method: com.android.systemui.statusbar.KeyguardAffordanceView.8.onAnimationUpdate(android.animation.ValueAnimator):void, dex: classes.dex
            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.statusbar.KeyguardAffordanceView.8.onAnimationUpdate(android.animation.ValueAnimator):void, class status: UNLOADED
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
        ofFloat.addListener(keyguardAffordanceView.mScaleEndListener);
        if (min == 0.0f) {
            pathInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        } else {
            pathInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        }
        ofFloat.setInterpolator(pathInterpolator);
        ofFloat.setDuration((long) (Math.min(1.0f, Math.abs(keyguardAffordanceView.mImageScale - min) / 0.19999999f) * 200.0f));
        ofFloat.start();
    }

    public final boolean isOnIcon(KeyguardAffordanceView keyguardAffordanceView, float f, float f2) {
        if (Math.hypot((double) (f - ((((float) keyguardAffordanceView.getWidth()) / 2.0f) + keyguardAffordanceView.getX())), (double) (f2 - ((((float) keyguardAffordanceView.getHeight()) / 2.0f) + keyguardAffordanceView.getY()))) <= ((double) (this.mTouchTargetSize / 2))) {
            return true;
        }
        return false;
    }
}
