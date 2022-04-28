package com.android.wifitrackerlib;

import android.animation.ValueAnimator;
import android.view.ViewRootImpl;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.p012wm.shell.legacysplitscreen.DividerImeController;
import com.android.p012wm.shell.legacysplitscreen.DividerView;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel;
import com.android.systemui.p006qs.QSFgsManagerFooter$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.wifitrackerlib.WifiPickerTracker;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        ValueAnimator valueAnimator;
        switch (this.$r8$classId) {
            case 0:
                BaseWifiTracker baseWifiTracker = (BaseWifiTracker) this.f$0;
                Objects.requireNonNull(baseWifiTracker);
                if (!baseWifiTracker.mIsStarted) {
                    baseWifiTracker.mIsStarted = true;
                    baseWifiTracker.handleOnStart();
                    return;
                }
                return;
            case 1:
                MagnificationModeSwitch magnificationModeSwitch = (MagnificationModeSwitch) this.f$0;
                Objects.requireNonNull(magnificationModeSwitch);
                magnificationModeSwitch.removeButton();
                return;
            case 2:
                ((NavigationBarView) this.f$0).updateStates();
                return;
            case 3:
                NavigationBarEdgePanel navigationBarEdgePanel = (NavigationBarEdgePanel) this.f$0;
                PathInterpolator pathInterpolator = NavigationBarEdgePanel.RUBBER_BAND_INTERPOLATOR;
                Objects.requireNonNull(navigationBarEdgePanel);
                navigationBarEdgePanel.mAngleOffset = Math.max(0.0f, navigationBarEdgePanel.mAngleOffset + 8.0f);
                navigationBarEdgePanel.updateAngle(true);
                SpringAnimation springAnimation = navigationBarEdgePanel.mTranslationAnimation;
                SpringForce springForce = navigationBarEdgePanel.mTriggerBackSpring;
                Objects.requireNonNull(springAnimation);
                springAnimation.mSpring = springForce;
                navigationBarEdgePanel.setDesiredTranslation(navigationBarEdgePanel.mDesiredTranslation - (navigationBarEdgePanel.mDensity * 32.0f), true);
                navigationBarEdgePanel.animate().alpha(0.0f).setDuration(80).withEndAction(new QSFgsManagerFooter$$ExternalSyntheticLambda0(navigationBarEdgePanel, 3));
                navigationBarEdgePanel.mArrowDisappearAnimation.start();
                navigationBarEdgePanel.mHandler.removeCallbacks(navigationBarEdgePanel.mFailsafeRunnable);
                navigationBarEdgePanel.mHandler.postDelayed(navigationBarEdgePanel.mFailsafeRunnable, 200);
                return;
            case 4:
                ScreenshotController screenshotController = (ScreenshotController) this.f$0;
                ScreenshotController.C10731 r0 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Objects.requireNonNull(screenshotController);
                screenshotController.requestScrollCapture();
                screenshotController.mWindow.peekDecorView().getViewRootImpl().setActivityConfigCallback(new ViewRootImpl.ActivityConfigCallback() {
                    public final void onConfigurationChanged(
/*
Method generation error in method: com.android.systemui.screenshot.ScreenshotController.4.onConfigurationChanged(android.content.res.Configuration, int):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.screenshot.ScreenshotController.4.onConfigurationChanged(android.content.res.Configuration, int):void, class status: UNLOADED
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
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
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

                    public final void requestCompatCameraControl(
/*
Method generation error in method: com.android.systemui.screenshot.ScreenshotController.4.requestCompatCameraControl(boolean, boolean, android.app.ICompatCameraControlCallback):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.screenshot.ScreenshotController.4.requestCompatCameraControl(boolean, boolean, android.app.ICompatCameraControlCallback):void, class status: UNLOADED
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
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:298)
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
                return;
            case 5:
                StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = (StatusBarHeadsUpChangeListener) this.f$0;
                Objects.requireNonNull(statusBarHeadsUpChangeListener);
                statusBarHeadsUpChangeListener.mNotificationShadeWindowController.setForceWindowCollapsed(false);
                return;
            case FalsingManager.VERSION:
                Objects.requireNonNull((WifiPickerTracker.WifiPickerTrackerCallback) this.f$0);
                return;
            case 7:
                DividerImeController dividerImeController = (DividerImeController) this.f$0;
                Objects.requireNonNull(dividerImeController);
                if (dividerImeController.mPaused) {
                    dividerImeController.mPaused = false;
                    dividerImeController.mTargetAdjusted = dividerImeController.mPausedTargetAdjusted;
                    dividerImeController.updateDimTargets();
                    LegacySplitScreenController legacySplitScreenController = dividerImeController.mSplits.mSplitScreenController;
                    Objects.requireNonNull(legacySplitScreenController);
                    DividerView dividerView = legacySplitScreenController.mView;
                    if (dividerImeController.mTargetAdjusted != dividerImeController.mAdjusted) {
                        LegacySplitScreenController legacySplitScreenController2 = dividerImeController.mSplits.mSplitScreenController;
                        Objects.requireNonNull(legacySplitScreenController2);
                        if (!(legacySplitScreenController2.mMinimized || dividerView == null || (valueAnimator = dividerView.mCurrentAnimator) == null)) {
                            valueAnimator.end();
                        }
                    }
                    dividerImeController.updateImeAdjustState(false);
                    dividerImeController.startAsyncAnimation();
                    return;
                }
                return;
            case 8:
                ((OneHandedController) this.f$0).onSwipeToNotificationEnabledChanged();
                return;
            default:
                ((ValueAnimator) this.f$0).start();
                return;
        }
    }
}
