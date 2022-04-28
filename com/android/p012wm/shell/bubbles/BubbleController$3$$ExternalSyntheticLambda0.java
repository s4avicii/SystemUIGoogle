package com.android.p012wm.shell.bubbles;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.window.BackEvent;
import android.window.BackNavigationInfo;
import android.window.IOnBackInvokedCallback;
import com.android.p012wm.shell.back.BackAnimationController;
import com.android.p012wm.shell.back.BackAnimationController$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$3$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ BubbleController$3$$ExternalSyntheticLambda0(Object obj, Object obj2, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = i;
    }

    public final void run() {
        BackNavigationInfo backNavigationInfo;
        boolean z;
        IOnBackInvokedCallback iOnBackInvokedCallback;
        float f;
        IOnBackInvokedCallback iOnBackInvokedCallback2;
        int i;
        switch (this.$r8$classId) {
            case 0:
                BubbleController.C17933 r1 = (BubbleController.C17933) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                int i2 = this.f$2;
                Objects.requireNonNull(r1);
                BubbleStackView bubbleStackView = BubbleController.this.mStackView;
                if (!(bubbleStackView == null || bubbleStackView.getExpandedBubble() == null || !BubbleController.this.isStackExpanded())) {
                    BubbleStackView bubbleStackView2 = BubbleController.this.mStackView;
                    Objects.requireNonNull(bubbleStackView2);
                    if (!bubbleStackView2.mIsExpansionAnimating && !bool.booleanValue()) {
                        i = BubbleController.this.mStackView.getExpandedBubble().getTaskId();
                        if (i != -1 && i != i2) {
                            BubbleController.this.mBubbleData.setExpanded(false);
                            return;
                        }
                        return;
                    }
                }
                i = -1;
                if (i != -1) {
                    return;
                }
                return;
            default:
                BackAnimationController.BackAnimationImpl backAnimationImpl = (BackAnimationController.BackAnimationImpl) this.f$0;
                MotionEvent motionEvent = (MotionEvent) this.f$1;
                int i3 = this.f$2;
                Objects.requireNonNull(backAnimationImpl);
                BackAnimationController backAnimationController = BackAnimationController.this;
                Objects.requireNonNull(backAnimationController);
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                        ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1188911440, 3, "initAnimation mMotionStarted=%b", Boolean.valueOf(backAnimationController.mBackGestureStarted));
                    }
                    if (backAnimationController.mBackGestureStarted) {
                        Log.e("BackAnimationController", "Animation is being initialized but is already started.");
                        return;
                    }
                    if (backAnimationController.mBackNavigationInfo != null) {
                        backAnimationController.finishAnimation();
                    }
                    backAnimationController.mInitTouchLocation.set(motionEvent.getX(), motionEvent.getY());
                    backAnimationController.mBackGestureStarted = true;
                    try {
                        BackNavigationInfo startBackNavigation = backAnimationController.mActivityTaskManager.startBackNavigation();
                        backAnimationController.mBackNavigationInfo = startBackNavigation;
                        backAnimationController.onBackNavigationInfoReceived(startBackNavigation);
                        return;
                    } catch (RemoteException e) {
                        Log.e("BackAnimationController", "Failed to initAnimation", e);
                        backAnimationController.finishAnimation();
                        return;
                    }
                } else if (actionMasked == 2) {
                    if (backAnimationController.mBackGestureStarted && backAnimationController.mBackNavigationInfo != null) {
                        int round = Math.round(motionEvent.getX() - backAnimationController.mInitTouchLocation.x);
                        int round2 = Math.round(motionEvent.getY() - backAnimationController.mInitTouchLocation.y);
                        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1295653292, 5, "Runner move: %d %d", Long.valueOf((long) round), Long.valueOf((long) round2));
                        }
                        int i4 = BackAnimationController.PROGRESS_THRESHOLD;
                        if (i4 >= 0) {
                            f = (float) i4;
                        } else {
                            f = backAnimationController.mProgressThreshold;
                        }
                        float min = Math.min(Math.max(((float) Math.abs(round)) / f, 0.0f), 1.0f);
                        int type = backAnimationController.mBackNavigationInfo.getType();
                        RemoteAnimationTarget departingAnimationTarget = backAnimationController.mBackNavigationInfo.getDepartingAnimationTarget();
                        BackEvent backEvent = new BackEvent(0, 0, min, i3, departingAnimationTarget);
                        if (type == 1) {
                            iOnBackInvokedCallback2 = backAnimationController.mBackToLauncherCallback;
                        } else {
                            if (type == 3 || type == 2) {
                                if (departingAnimationTarget != null) {
                                    backAnimationController.mTransaction.setPosition(departingAnimationTarget.leash, (float) round, (float) round2);
                                    backAnimationController.mTouchEventDelta.set(round, round2);
                                    backAnimationController.mTransaction.apply();
                                }
                            } else if (type == 4) {
                                iOnBackInvokedCallback2 = backAnimationController.mBackNavigationInfo.getOnBackInvokedCallback();
                            }
                            iOnBackInvokedCallback2 = null;
                        }
                        if (iOnBackInvokedCallback2 != null) {
                            try {
                                iOnBackInvokedCallback2.onBackProgressed(backEvent);
                                return;
                            } catch (RemoteException e2) {
                                Log.e("BackAnimationController", "dispatchOnBackProgressed error: ", e2);
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else if (actionMasked == 1 || actionMasked == 3) {
                    if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                        ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -14660627, 0, "onGestureFinished() mTriggerBack == %s", String.valueOf(backAnimationController.mTriggerBack));
                    }
                    if (backAnimationController.mBackGestureStarted && (backNavigationInfo = backAnimationController.mBackNavigationInfo) != null) {
                        int type2 = backNavigationInfo.getType();
                        if (type2 != 1 || backAnimationController.mBackToLauncherCallback == null) {
                            z = false;
                        } else {
                            z = true;
                        }
                        if (z) {
                            iOnBackInvokedCallback = backAnimationController.mBackToLauncherCallback;
                        } else {
                            iOnBackInvokedCallback = backAnimationController.mBackNavigationInfo.getOnBackInvokedCallback();
                        }
                        if (backAnimationController.mTriggerBack) {
                            if (iOnBackInvokedCallback != null) {
                                try {
                                    iOnBackInvokedCallback.onBackInvoked();
                                } catch (RemoteException e3) {
                                    Log.e("BackAnimationController", "dispatchOnBackInvoked error: ", e3);
                                }
                            }
                        } else if (iOnBackInvokedCallback != null) {
                            try {
                                iOnBackInvokedCallback.onBackCancelled();
                            } catch (RemoteException e4) {
                                Log.e("BackAnimationController", "dispatchOnBackCancelled error: ", e4);
                            }
                        }
                        if (type2 == 4) {
                            backAnimationController.finishAnimation();
                            return;
                        } else if (type2 == 1 && !z) {
                            backAnimationController.finishAnimation();
                            return;
                        } else if (type2 != 2 && type2 != 3) {
                            return;
                        } else {
                            if (backAnimationController.mTriggerBack) {
                                if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                                    ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1275042685, 0, "prepareTransition()", (Object[]) null);
                                }
                                backAnimationController.mTriggerBack = false;
                                backAnimationController.mBackGestureStarted = false;
                                return;
                            }
                            backAnimationController.mBackGestureStarted = false;
                            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                                ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -742132644, 0, "Runner: Back not triggered, cancelling animation mLastPos=%s mInitTouch=%s", String.valueOf(backAnimationController.mTouchEventDelta), String.valueOf(backAnimationController.mInitTouchLocation));
                            }
                            ValueAnimator duration = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}).setDuration(200);
                            duration.addUpdateListener(new BackAnimationController$$ExternalSyntheticLambda0(backAnimationController, 0));
                            duration.addListener(new AnimatorListenerAdapter() {
                                public final void onAnimationEnd(
/*
Method generation error in method: com.android.wm.shell.back.BackAnimationController.1.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.wm.shell.back.BackAnimationController.1.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                                	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                                	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
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
                                	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                                	at jadx.core.codegen.RegionGen.connectElseIf(RegionGen.java:175)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:152)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:302)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:64)
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
                            duration.start();
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
        }
    }
}
