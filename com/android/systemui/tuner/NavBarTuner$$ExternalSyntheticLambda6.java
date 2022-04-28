package com.android.systemui.tuner;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.provider.Settings;
import android.util.Log;
import android.view.ScrollCaptureResponse;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.preference.ListPreference;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.p012wm.shell.splitscreen.SplitScreenTransitions;
import com.android.systemui.p006qs.QSFooterView$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.android.systemui.screenshot.ScrollCaptureClient$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.statusbar.notification.collection.coalescer.EventBatch;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.volume.C1716D;
import com.android.systemui.volume.Events;
import com.android.systemui.volume.Util;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import java.time.Duration;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda6(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                String str = (String) this.f$0;
                ListPreference listPreference = (ListPreference) this.f$1;
                int[][] iArr = NavBarTuner.ICONS;
                if (str == null) {
                    str = "default";
                }
                listPreference.setValue(str);
                return;
            case 1:
                CallbackToFutureAdapter.Completer completer = (CallbackToFutureAdapter.Completer) this.f$0;
                ImageExporter.Task task = (ImageExporter.Task) this.f$1;
                Duration duration = ImageExporter.PENDING_ENTRY_TTL;
                try {
                    completer.set(task.execute());
                    return;
                } catch (ImageExporter.ImageExportException | InterruptedException e) {
                    completer.setException(e);
                    return;
                }
            case 2:
                ScreenshotController screenshotController = (ScreenshotController) this.f$0;
                ScreenshotController.SavedImageData savedImageData = (ScreenshotController.SavedImageData) this.f$1;
                ScreenshotController.C10731 r1 = ScreenshotController.SCREENSHOT_REMOTE_RUNNER;
                Objects.requireNonNull(screenshotController);
                AnimatorSet animatorSet = screenshotController.mScreenshotAnimation;
                if (animatorSet == null || !animatorSet.isRunning()) {
                    screenshotController.mScreenshotView.setChipIntents(savedImageData);
                    return;
                } else {
                    screenshotController.mScreenshotAnimation.addListener(new AnimatorListenerAdapter(savedImageData) {
                        public final /* synthetic */ SavedImageData val$imageData;

                        public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.screenshot.ScreenshotController.7.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                        jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.screenshot.ScreenshotController.7.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
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
                }
            case 3:
                ScrollCaptureController scrollCaptureController = (ScrollCaptureController) this.f$0;
                ScrollCaptureResponse scrollCaptureResponse = (ScrollCaptureResponse) this.f$1;
                Objects.requireNonNull(scrollCaptureController);
                float f = Settings.Secure.getFloat(scrollCaptureController.mContext.getContentResolver(), "screenshot.scroll_max_pages", 3.0f);
                ScrollCaptureClient scrollCaptureClient = scrollCaptureController.mClient;
                Objects.requireNonNull(scrollCaptureClient);
                CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ScrollCaptureClient$$ExternalSyntheticLambda1(scrollCaptureClient, scrollCaptureResponse.getConnection(), scrollCaptureResponse, f));
                scrollCaptureController.mSessionFuture = future;
                future.delegate.addListener(new QSFooterView$$ExternalSyntheticLambda0(scrollCaptureController, 4), scrollCaptureController.mContext.getMainExecutor());
                return;
            case 4:
                GroupCoalescer groupCoalescer = (GroupCoalescer) this.f$0;
                EventBatch eventBatch = (EventBatch) this.f$1;
                Objects.requireNonNull(groupCoalescer);
                eventBatch.mCancelShortTimeout = null;
                groupCoalescer.emitBatch(eventBatch);
                return;
            case 5:
                VolumeDialogControllerImpl.RingerModeObservers.C17311 r0 = (VolumeDialogControllerImpl.RingerModeObservers.C17311) this.f$0;
                Objects.requireNonNull(r0);
                int intValue = ((Integer) this.f$1).intValue();
                RingerModeLiveData ringerModeLiveData = VolumeDialogControllerImpl.RingerModeObservers.this.mRingerMode;
                Objects.requireNonNull(ringerModeLiveData);
                if (ringerModeLiveData.initialSticky) {
                    VolumeDialogControllerImpl.this.mState.ringerModeExternal = intValue;
                }
                if (C1716D.BUG) {
                    String str2 = VolumeDialogControllerImpl.TAG;
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onChange ringer_mode rm=");
                    m.append(Util.ringerModeToString(intValue));
                    Log.d(str2, m.toString());
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                String str3 = VolumeDialogControllerImpl.TAG;
                Objects.requireNonNull(volumeDialogControllerImpl);
                VolumeDialogController.State state = volumeDialogControllerImpl.mState;
                boolean z = false;
                if (intValue != state.ringerModeExternal) {
                    state.ringerModeExternal = intValue;
                    Events.writeEvent(12, Integer.valueOf(intValue));
                    z = true;
                }
                if (z) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    return;
                }
                return;
            case FalsingManager.VERSION:
                DragAndDropController.PerDisplay perDisplay = (DragAndDropController.PerDisplay) this.f$1;
                int i = DragAndDropController.$r8$clinit;
                Objects.requireNonNull((DragAndDropController) this.f$0);
                if (perDisplay.activeDragCount == 0) {
                    DragAndDropController.setDropTargetWindowVisibility(perDisplay, 4);
                    return;
                }
                return;
            default:
                SplitScreenTransitions splitScreenTransitions = (SplitScreenTransitions) this.f$0;
                Objects.requireNonNull(splitScreenTransitions);
                splitScreenTransitions.mAnimations.remove((ValueAnimator) this.f$1);
                splitScreenTransitions.onFinish((WindowContainerTransaction) null);
                return;
        }
    }
}
