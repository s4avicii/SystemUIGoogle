package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardInputView;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda7;
import com.android.settingslib.Utils;
import com.android.systemui.DejankUtils;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.p006qs.QSAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class KeyguardBouncer {
    public int mBouncerPromptReason;
    public final ViewMediatorCallback mCallback;
    public final ViewGroup mContainer;
    public final Context mContext;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public float mExpansion = 1.0f;
    public final ArrayList mExpansionCallbacks;
    public final FalsingCollector mFalsingCollector;
    public final Handler mHandler;
    public boolean mInitialized;
    public boolean mIsAnimatingAway;
    public boolean mIsScrimmed;
    public final KeyguardBouncerComponent.Factory mKeyguardBouncerComponentFactory;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public KeyguardHostViewController mKeyguardViewController;
    public final PipMenuView$$ExternalSyntheticLambda7 mRemoveViewRunnable = new PipMenuView$$ExternalSyntheticLambda7(this, 3);
    public final ListenerSet<KeyguardResetCallback> mResetCallbacks = new ListenerSet<>();
    public final QSAnimator$$ExternalSyntheticLambda0 mResetRunnable = new QSAnimator$$ExternalSyntheticLambda0(this, 4);
    public final C14392 mShowRunnable = new Runnable() {
        public final void run() {
            KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.None;
            KeyguardBouncer.this.setVisibility(0);
            KeyguardBouncer keyguardBouncer = KeyguardBouncer.this;
            keyguardBouncer.showPromptReason(keyguardBouncer.mBouncerPromptReason);
            CharSequence consumeCustomMessage = KeyguardBouncer.this.mCallback.consumeCustomMessage();
            if (consumeCustomMessage != null) {
                KeyguardHostViewController keyguardHostViewController = KeyguardBouncer.this.mKeyguardViewController;
                Objects.requireNonNull(keyguardHostViewController);
                ColorStateList colorAttr = Utils.getColorAttr(((KeyguardHostView) keyguardHostViewController.mView).getContext(), 16844099);
                KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController.mKeyguardSecurityContainerController;
                Objects.requireNonNull(keyguardSecurityContainerController);
                if (keyguardSecurityContainerController.mCurrentSecurityMode != securityMode) {
                    keyguardSecurityContainerController.getCurrentSecurityController().showMessage(consumeCustomMessage, colorAttr);
                }
            }
            KeyguardBouncer keyguardBouncer2 = KeyguardBouncer.this;
            KeyguardHostViewController keyguardHostViewController2 = keyguardBouncer2.mKeyguardViewController;
            int i = keyguardBouncer2.mStatusBarHeight;
            Objects.requireNonNull(keyguardHostViewController2);
            if (((KeyguardHostView) keyguardHostViewController2.mView).getHeight() == 0 || ((KeyguardHostView) keyguardHostViewController2.mView).getHeight() == i) {
                ((KeyguardHostView) keyguardHostViewController2.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public final boolean onPreDraw(
/*
Method generation error in method: com.android.keyguard.KeyguardHostViewController.3.onPreDraw():boolean, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.keyguard.KeyguardHostViewController.3.onPreDraw():boolean, class status: UNLOADED
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
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:98)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:480)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	at jadx.core.codegen.ClassGen.addInsnBody(ClassGen.java:437)
                    	at jadx.core.codegen.ClassGen.addField(ClassGen.java:378)
                    	at jadx.core.codegen.ClassGen.addFields(ClassGen.java:348)
                    	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
                    	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                    	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                    	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                    	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                    	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                    	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                    	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                    
*/
                });
                ((KeyguardHostView) keyguardHostViewController2.mView).requestLayout();
            } else {
                KeyguardSecurityContainerController keyguardSecurityContainerController2 = keyguardHostViewController2.mKeyguardSecurityContainerController;
                Objects.requireNonNull(keyguardSecurityContainerController2);
                KeyguardSecurityModel.SecurityMode securityMode2 = keyguardSecurityContainerController2.mCurrentSecurityMode;
                if (securityMode2 != securityMode) {
                    KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecurityContainerController2.mView;
                    Objects.requireNonNull(keyguardSecurityContainer);
                    keyguardSecurityContainer.mViewMode.startAppearAnimation(securityMode2);
                    keyguardSecurityContainerController2.getCurrentSecurityController().startAppearAnimation();
                }
            }
            KeyguardBouncer keyguardBouncer3 = KeyguardBouncer.this;
            keyguardBouncer3.mShowingSoon = false;
            if (keyguardBouncer3.mExpansion == 0.0f) {
                keyguardBouncer3.mKeyguardViewController.onResume();
                KeyguardBouncer.this.mKeyguardViewController.resetSecurityContainer();
                KeyguardBouncer keyguardBouncer4 = KeyguardBouncer.this;
                keyguardBouncer4.showPromptReason(keyguardBouncer4.mBouncerPromptReason);
            }
        }
    };
    public boolean mShowingSoon;
    public int mStatusBarHeight;
    public final C14381 mUpdateMonitorCallback;

    public interface BouncerExpansionCallback {
        void onExpansionChanged(float f) {
        }

        void onFullyHidden();

        void onFullyShown();

        void onStartingToHide();

        void onStartingToShow();

        void onVisibilityChanged(boolean z) {
        }
    }

    public interface KeyguardResetCallback {
        void onKeyguardReset();
    }

    public KeyguardBouncer(Context context, ViewMediatorCallback viewMediatorCallback, ViewGroup viewGroup, DismissCallbackRegistry dismissCallbackRegistry, FalsingCollector falsingCollector, StatusBarKeyguardViewManager.C15581 r12, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardBypassController keyguardBypassController, Handler handler, KeyguardSecurityModel keyguardSecurityModel, KeyguardBouncerComponent.Factory factory) {
        ArrayList arrayList = new ArrayList();
        this.mExpansionCallbacks = arrayList;
        C14381 r3 = new KeyguardUpdateMonitorCallback() {
            public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardBouncer keyguardBouncer = KeyguardBouncer.this;
                    keyguardBouncer.mBouncerPromptReason = keyguardBouncer.mCallback.getBouncerPromptReason();
                }
            }

            public final void onStrongAuthStateChanged(int i) {
                KeyguardBouncer keyguardBouncer = KeyguardBouncer.this;
                keyguardBouncer.mBouncerPromptReason = keyguardBouncer.mCallback.getBouncerPromptReason();
            }
        };
        this.mUpdateMonitorCallback = r3;
        this.mContext = context;
        this.mCallback = viewMediatorCallback;
        this.mContainer = viewGroup;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFalsingCollector = falsingCollector;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mHandler = handler;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mKeyguardBouncerComponentFactory = factory;
        keyguardUpdateMonitor.registerCallback(r3);
        this.mKeyguardBypassController = keyguardBypassController;
        StatusBarKeyguardViewManager.C15581 r0 = r12;
        arrayList.add(r12);
    }

    public static class Factory {
        public final ViewMediatorCallback mCallback;
        public final Context mContext;
        public final DismissCallbackRegistry mDismissCallbackRegistry;
        public final FalsingCollector mFalsingCollector;
        public final Handler mHandler;
        public final KeyguardBouncerComponent.Factory mKeyguardBouncerComponentFactory;
        public final KeyguardBypassController mKeyguardBypassController;
        public final KeyguardSecurityModel mKeyguardSecurityModel;
        public final KeyguardStateController mKeyguardStateController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(Context context, ViewMediatorCallback viewMediatorCallback, DismissCallbackRegistry dismissCallbackRegistry, FalsingCollector falsingCollector, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardBypassController keyguardBypassController, Handler handler, KeyguardSecurityModel keyguardSecurityModel, KeyguardBouncerComponent.Factory factory) {
            this.mContext = context;
            this.mCallback = viewMediatorCallback;
            this.mDismissCallbackRegistry = dismissCallbackRegistry;
            this.mFalsingCollector = falsingCollector;
            this.mKeyguardStateController = keyguardStateController;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mKeyguardBypassController = keyguardBypassController;
            this.mHandler = handler;
            this.mKeyguardSecurityModel = keyguardSecurityModel;
            this.mKeyguardBouncerComponentFactory = factory;
        }
    }

    public final void ensureView() {
        boolean hasCallbacks = this.mHandler.hasCallbacks(this.mRemoveViewRunnable);
        if (!this.mInitialized || hasCallbacks) {
            this.mContainer.removeAllViews();
            this.mInitialized = false;
            this.mHandler.removeCallbacks(this.mRemoveViewRunnable);
            KeyguardHostViewController keyguardHostViewController = this.mKeyguardBouncerComponentFactory.create(this.mContainer).getKeyguardHostViewController();
            this.mKeyguardViewController = keyguardHostViewController;
            keyguardHostViewController.init();
            this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
            setVisibility(4);
            WindowInsets rootWindowInsets = this.mContainer.getRootWindowInsets();
            if (rootWindowInsets != null) {
                this.mContainer.dispatchApplyWindowInsets(rootWindowInsets);
            }
            this.mInitialized = true;
        }
    }

    public final boolean isShowing() {
        if ((this.mShowingSoon || this.mContainer.getVisibility() == 0) && this.mExpansion == 0.0f && !this.mIsAnimatingAway) {
            return true;
        }
        return false;
    }

    public final boolean needsFullscreenBouncer() {
        KeyguardSecurityModel.SecurityMode securityMode = this.mKeyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        if (securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk) {
            return true;
        }
        return false;
    }

    public final void setExpansion(float f) {
        boolean z;
        String str;
        float f2 = this.mExpansion;
        if (f2 != f) {
            z = true;
        } else {
            z = false;
        }
        this.mExpansion = f;
        KeyguardHostViewController keyguardHostViewController = this.mKeyguardViewController;
        if (keyguardHostViewController != null && !this.mIsAnimatingAway) {
            ((KeyguardHostView) keyguardHostViewController.mView).setAlpha(MathUtils.constrain(MathUtils.map(0.95f, 1.0f, 1.0f, 0.0f, f), 0.0f, 1.0f));
            KeyguardHostView keyguardHostView = (KeyguardHostView) keyguardHostViewController.mView;
            keyguardHostView.setTranslationY(((float) keyguardHostView.getHeight()) * f);
        }
        int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        if (i == 0 && f2 != 0.0f) {
            this.mFalsingCollector.onBouncerShown();
            KeyguardHostViewController keyguardHostViewController2 = this.mKeyguardViewController;
            if (keyguardHostViewController2 == null) {
                Log.wtf("KeyguardBouncer", "onFullyShown when view was null");
            } else {
                keyguardHostViewController2.onResume();
                ViewGroup viewGroup = this.mContainer;
                KeyguardHostViewController keyguardHostViewController3 = this.mKeyguardViewController;
                Objects.requireNonNull(keyguardHostViewController3);
                KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController3.mKeyguardSecurityContainerController;
                Objects.requireNonNull(keyguardSecurityContainerController);
                KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecurityContainerController.mView;
                Objects.requireNonNull(keyguardSecurityContainer);
                KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = keyguardSecurityContainer.mSecurityViewFlipper;
                Objects.requireNonNull(keyguardSecurityViewFlipper);
                KeyguardInputView securityView = keyguardSecurityViewFlipper.getSecurityView();
                if (securityView != null) {
                    str = securityView.getTitle();
                } else {
                    str = "";
                }
                viewGroup.announceForAccessibility(str);
            }
            Iterator it = this.mExpansionCallbacks.iterator();
            while (it.hasNext()) {
                ((BouncerExpansionCallback) it.next()).onFullyShown();
            }
        } else if (f == 1.0f && f2 != 1.0f) {
            C14392 r9 = this.mShowRunnable;
            boolean z2 = DejankUtils.STRICT_MODE_ENABLED;
            Assert.isMainThread();
            DejankUtils.sPendingRunnables.remove(r9);
            DejankUtils.sHandler.removeCallbacks(r9);
            this.mHandler.removeCallbacks(this.mShowRunnable);
            this.mShowingSoon = false;
            setVisibility(4);
            this.mFalsingCollector.onBouncerHidden();
            DejankUtils.postAfterTraversal(this.mResetRunnable);
            Iterator it2 = this.mExpansionCallbacks.iterator();
            while (it2.hasNext()) {
                ((BouncerExpansionCallback) it2.next()).onFullyHidden();
            }
        } else if (i != 0 && f2 == 0.0f) {
            Iterator it3 = this.mExpansionCallbacks.iterator();
            while (it3.hasNext()) {
                ((BouncerExpansionCallback) it3.next()).onStartingToHide();
            }
            KeyguardHostViewController keyguardHostViewController4 = this.mKeyguardViewController;
            if (keyguardHostViewController4 != null) {
                keyguardHostViewController4.mKeyguardSecurityContainerController.onStartingToHide();
            }
        }
        if (z) {
            Iterator it4 = this.mExpansionCallbacks.iterator();
            while (it4.hasNext()) {
                ((BouncerExpansionCallback) it4.next()).onExpansionChanged(this.mExpansion);
            }
        }
    }

    public final void setVisibility(int i) {
        boolean z;
        this.mContainer.setVisibility(i);
        Iterator it = this.mExpansionCallbacks.iterator();
        while (it.hasNext()) {
            BouncerExpansionCallback bouncerExpansionCallback = (BouncerExpansionCallback) it.next();
            if (this.mContainer.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            bouncerExpansionCallback.onVisibilityChanged(z);
        }
    }

    public final void showPromptReason(int i) {
        KeyguardHostViewController keyguardHostViewController = this.mKeyguardViewController;
        if (keyguardHostViewController != null) {
            Objects.requireNonNull(keyguardHostViewController);
            KeyguardSecurityContainerController keyguardSecurityContainerController = keyguardHostViewController.mKeyguardSecurityContainerController;
            Objects.requireNonNull(keyguardSecurityContainerController);
            if (keyguardSecurityContainerController.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
                if (i != 0) {
                    Log.i("KeyguardSecurityView", "Strong auth required, reason: " + i);
                }
                keyguardSecurityContainerController.getCurrentSecurityController().showPromptReason(i);
                return;
            }
            return;
        }
        Log.w("KeyguardBouncer", "Trying to show prompt reason on empty bouncer");
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e3 A[LOOP:0: B:46:0x00dd->B:48:0x00e3, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void show(boolean r5, boolean r6) {
        /*
            r4 = this;
            int r0 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            if (r0 != 0) goto L_0x000d
            boolean r1 = android.os.UserManager.isSplitSystemUser()
            if (r1 == 0) goto L_0x000d
            return
        L_0x000d:
            r4.ensureView()
            r4.mIsScrimmed = r6
            if (r6 == 0) goto L_0x0018
            r6 = 0
            r4.setExpansion(r6)
        L_0x0018:
            r6 = 0
            if (r5 == 0) goto L_0x0031
            com.android.keyguard.KeyguardHostViewController r5 = r4.mKeyguardViewController
            boolean r1 = com.android.keyguard.KeyguardHostViewController.DEBUG
            if (r1 == 0) goto L_0x002c
            java.util.Objects.requireNonNull(r5)
            java.lang.String r1 = "KeyguardViewBase"
            java.lang.String r2 = "show()"
            android.util.Log.d(r1, r2)
        L_0x002c:
            com.android.keyguard.KeyguardSecurityContainerController r5 = r5.mKeyguardSecurityContainerController
            r5.showPrimarySecurityScreen(r6)
        L_0x0031:
            android.view.ViewGroup r5 = r4.mContainer
            int r5 = r5.getVisibility()
            if (r5 == 0) goto L_0x00ed
            boolean r5 = r4.mShowingSoon
            if (r5 == 0) goto L_0x003f
            goto L_0x00ed
        L_0x003f:
            int r5 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            boolean r1 = android.os.UserManager.isSplitSystemUser()
            r2 = 1
            if (r1 == 0) goto L_0x004e
            if (r5 != 0) goto L_0x004e
            r1 = r2
            goto L_0x004f
        L_0x004e:
            r1 = r6
        L_0x004f:
            if (r1 != 0) goto L_0x0055
            if (r5 != r0) goto L_0x0055
            r1 = r2
            goto L_0x0056
        L_0x0055:
            r1 = r6
        L_0x0056:
            if (r1 == 0) goto L_0x0066
            com.android.keyguard.KeyguardHostViewController r3 = r4.mKeyguardViewController
            java.util.Objects.requireNonNull(r3)
            com.android.keyguard.KeyguardHostViewController$2 r3 = r3.mSecurityCallback
            boolean r3 = r3.dismiss(r6, r5, r6)
            if (r3 == 0) goto L_0x0066
            return
        L_0x0066:
            if (r1 != 0) goto L_0x0086
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "User can't dismiss keyguard: "
            r1.append(r3)
            r1.append(r5)
            java.lang.String r5 = " != "
            r1.append(r5)
            r1.append(r0)
            java.lang.String r5 = r1.toString()
            java.lang.String r0 = "KeyguardBouncer"
            android.util.Log.w(r0, r5)
        L_0x0086:
            r4.mShowingSoon = r2
            com.android.systemui.qs.QSAnimator$$ExternalSyntheticLambda0 r5 = r4.mResetRunnable
            boolean r0 = com.android.systemui.DejankUtils.STRICT_MODE_ENABLED
            com.android.systemui.util.Assert.isMainThread()
            java.util.ArrayList<java.lang.Runnable> r0 = com.android.systemui.DejankUtils.sPendingRunnables
            r0.remove(r5)
            android.os.Handler r0 = com.android.systemui.DejankUtils.sHandler
            r0.removeCallbacks(r5)
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = r4.mKeyguardStateController
            boolean r5 = r5.isFaceAuthEnabled()
            if (r5 == 0) goto L_0x00cd
            boolean r5 = r4.needsFullscreenBouncer()
            if (r5 != 0) goto L_0x00cd
            com.android.keyguard.KeyguardUpdateMonitor r5 = r4.mKeyguardUpdateMonitor
            java.util.Objects.requireNonNull(r5)
            com.android.keyguard.KeyguardUpdateMonitor$StrongAuthTracker r5 = r5.mStrongAuthTracker
            int r0 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            int r5 = r5.getStrongAuthForUser(r0)
            if (r5 == 0) goto L_0x00b9
            r6 = r2
        L_0x00b9:
            if (r6 != 0) goto L_0x00cd
            com.android.systemui.statusbar.phone.KeyguardBypassController r5 = r4.mKeyguardBypassController
            boolean r5 = r5.getBypassEnabled()
            if (r5 != 0) goto L_0x00cd
            android.os.Handler r5 = r4.mHandler
            com.android.systemui.statusbar.phone.KeyguardBouncer$2 r6 = r4.mShowRunnable
            r0 = 1200(0x4b0, double:5.93E-321)
            r5.postDelayed(r6, r0)
            goto L_0x00d2
        L_0x00cd:
            com.android.systemui.statusbar.phone.KeyguardBouncer$2 r5 = r4.mShowRunnable
            com.android.systemui.DejankUtils.postAfterTraversal(r5)
        L_0x00d2:
            com.android.keyguard.ViewMediatorCallback r5 = r4.mCallback
            r5.onBouncerVisiblityChanged(r2)
            java.util.ArrayList r4 = r4.mExpansionCallbacks
            java.util.Iterator r4 = r4.iterator()
        L_0x00dd:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00ed
            java.lang.Object r5 = r4.next()
            com.android.systemui.statusbar.phone.KeyguardBouncer$BouncerExpansionCallback r5 = (com.android.systemui.statusbar.phone.KeyguardBouncer.BouncerExpansionCallback) r5
            r5.onStartingToShow()
            goto L_0x00dd
        L_0x00ed:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardBouncer.show(boolean, boolean):void");
    }
}
