package com.android.systemui.p006qs;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.view.animation.PathInterpolator;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipUtils;
import com.android.p012wm.shell.pip.phone.PipController;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.pip.phone.PipInputConsumer;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda0;
import com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda25;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda11;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.util.Collections;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFgsManagerFooter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFgsManagerFooter$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSFgsManagerFooter$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        float f = 0.0f;
        switch (this.$r8$classId) {
            case 0:
                QSFgsManagerFooter qSFgsManagerFooter = (QSFgsManagerFooter) this.f$0;
                Objects.requireNonNull(qSFgsManagerFooter);
                qSFgsManagerFooter.mMainExecutor.execute(new DozeScreenState$$ExternalSyntheticLambda0(qSFgsManagerFooter, 3));
                return;
            case 1:
                MagnificationModeSwitch magnificationModeSwitch = (MagnificationModeSwitch) this.f$0;
                Objects.requireNonNull(magnificationModeSwitch);
                magnificationModeSwitch.mImageView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, magnificationModeSwitch.mImageView.getWidth(), magnificationModeSwitch.mImageView.getHeight())));
                return;
            case 2:
                ClipboardOverlayController clipboardOverlayController = (ClipboardOverlayController) this.f$0;
                Objects.requireNonNull(clipboardOverlayController);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                clipboardOverlayController.mContainer.setAlpha(0.0f);
                clipboardOverlayController.mDismissButton.setVisibility(8);
                View findViewById = clipboardOverlayController.mView.findViewById(C1777R.C1779id.preview_border);
                Objects.requireNonNull(findViewById);
                View findViewById2 = clipboardOverlayController.mView.findViewById(C1777R.C1779id.actions_container_background);
                Objects.requireNonNull(findViewById2);
                clipboardOverlayController.mImagePreview.setVisibility(0);
                clipboardOverlayController.mActionContainerBackground.setVisibility(0);
                if (clipboardOverlayController.mAccessibilityManager.isEnabled()) {
                    clipboardOverlayController.mDismissButton.setVisibility(0);
                }
                ofFloat.addUpdateListener(new ClipboardOverlayController$$ExternalSyntheticLambda0(clipboardOverlayController, findViewById, findViewById2));
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.clipboardoverlay.ClipboardOverlayController.4.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
                    jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.clipboardoverlay.ClipboardOverlayController.4.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
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
                ofFloat.start();
                return;
            case 3:
                NavigationBarEdgePanel navigationBarEdgePanel = (NavigationBarEdgePanel) this.f$0;
                PathInterpolator pathInterpolator = NavigationBarEdgePanel.RUBBER_BAND_INTERPOLATOR;
                Objects.requireNonNull(navigationBarEdgePanel);
                navigationBarEdgePanel.setVisibility(8);
                return;
            case 4:
                StatusBar statusBar = (StatusBar) this.f$0;
                long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
                Objects.requireNonNull(statusBar);
                if (statusBar.mWallpaperManager.lockScreenWallpaperExists()) {
                    f = statusBar.mWallpaperManager.getWallpaperDimAmount();
                }
                statusBar.mMainExecutor.execute(new StatusBar$$ExternalSyntheticLambda25(statusBar, f));
                return;
            case 5:
                StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = (StatusBarHeadsUpChangeListener) this.f$0;
                Objects.requireNonNull(statusBarHeadsUpChangeListener);
                HeadsUpManagerPhone headsUpManagerPhone = statusBarHeadsUpChangeListener.mHeadsUpManager;
                Objects.requireNonNull(headsUpManagerPhone);
                if (!headsUpManagerPhone.mHasPinnedNotification) {
                    statusBarHeadsUpChangeListener.mNotificationShadeWindowController.setHeadsUpShowing(false);
                    statusBarHeadsUpChangeListener.mHeadsUpManager.setHeadsUpGoingAway(false);
                }
                NotificationRemoteInputManager notificationRemoteInputManager = statusBarHeadsUpChangeListener.mNotificationRemoteInputManager;
                Objects.requireNonNull(notificationRemoteInputManager);
                NotificationRemoteInputManager.RemoteInputListener remoteInputListener = notificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputListener != null) {
                    remoteInputListener.onPanelCollapsed();
                    return;
                }
                return;
            case FalsingManager.VERSION:
                ProximitySensorImpl.C17061 r8 = (ProximitySensorImpl.C17061) this.f$0;
                Objects.requireNonNull(r8);
                ProximitySensorImpl.this.mPrimaryThresholdSensor.pause();
                ProximitySensorImpl.this.mSecondaryThresholdSensor.resume();
                return;
            case 7:
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) ((BaseWifiTracker.BaseWifiTrackerCallback) this.f$0);
                Objects.requireNonNull(accessPointControllerImpl);
                accessPointControllerImpl.scanForAccessPoints();
                return;
            case 8:
                ((OneHandedController) this.f$0).onShortcutEnabledChanged();
                return;
            case 9:
                PipController pipController = (PipController) this.f$0;
                Objects.requireNonNull(pipController);
                pipController.mPipInputConsumer = new PipInputConsumer(WindowManagerGlobal.getWindowManagerService(), pipController.mMainExecutor);
                PipTransitionController pipTransitionController = pipController.mPipTransitionController;
                Objects.requireNonNull(pipTransitionController);
                pipTransitionController.mPipTransitionCallbacks.add(pipController);
                PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
                PipController$$ExternalSyntheticLambda5 pipController$$ExternalSyntheticLambda5 = new PipController$$ExternalSyntheticLambda5(pipController);
                Objects.requireNonNull(pipTaskOrganizer);
                pipTaskOrganizer.mOnDisplayIdChangeCallback = pipController$$ExternalSyntheticLambda5;
                PipBoundsState pipBoundsState = pipController.mPipBoundsState;
                QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0 = new QSTileImpl$$ExternalSyntheticLambda0(pipController, 9);
                Objects.requireNonNull(pipBoundsState);
                pipBoundsState.mOnMinimalSizeChangeCallback = qSTileImpl$$ExternalSyntheticLambda0;
                PipBoundsState pipBoundsState2 = pipController.mPipBoundsState;
                PipController$$ExternalSyntheticLambda0 pipController$$ExternalSyntheticLambda0 = new PipController$$ExternalSyntheticLambda0(pipController);
                Objects.requireNonNull(pipBoundsState2);
                pipBoundsState2.mOnShelfVisibilityChangeCallback = pipController$$ExternalSyntheticLambda0;
                PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
                if (pipTouchHandler != null) {
                    PipInputConsumer pipInputConsumer = pipController.mPipInputConsumer;
                    PipController$$ExternalSyntheticLambda1 pipController$$ExternalSyntheticLambda1 = new PipController$$ExternalSyntheticLambda1(pipTouchHandler);
                    Objects.requireNonNull(pipInputConsumer);
                    pipInputConsumer.mListener = pipController$$ExternalSyntheticLambda1;
                    PipInputConsumer pipInputConsumer2 = pipController.mPipInputConsumer;
                    PipTouchHandler pipTouchHandler2 = pipController.mTouchHandler;
                    Objects.requireNonNull(pipTouchHandler2);
                    ImageLoader$$ExternalSyntheticLambda0 imageLoader$$ExternalSyntheticLambda0 = new ImageLoader$$ExternalSyntheticLambda0(pipTouchHandler2);
                    Objects.requireNonNull(pipInputConsumer2);
                    pipInputConsumer2.mRegistrationListener = imageLoader$$ExternalSyntheticLambda0;
                    pipInputConsumer2.mMainExecutor.execute(new SuggestController$$ExternalSyntheticLambda1(pipInputConsumer2, 10));
                }
                pipController.mDisplayController.addDisplayChangingController(pipController.mRotationController);
                pipController.mDisplayController.addDisplayWindowListener(pipController.mDisplaysChangedListener);
                PipBoundsState pipBoundsState3 = pipController.mPipBoundsState;
                int displayId = pipController.mContext.getDisplayId();
                Objects.requireNonNull(pipBoundsState3);
                pipBoundsState3.mDisplayId = displayId;
                PipBoundsState pipBoundsState4 = pipController.mPipBoundsState;
                Context context = pipController.mContext;
                DisplayLayout displayLayout = new DisplayLayout(context, context.getDisplay());
                Objects.requireNonNull(pipBoundsState4);
                pipBoundsState4.mDisplayLayout.set(displayLayout);
                try {
                    pipController.mWindowManagerShellWrapper.addPinnedStackListener(pipController.mPinnedTaskListener);
                } catch (RemoteException e) {
                    Slog.e("PipController", "Failed to register pinned stack listener", e);
                }
                try {
                    if (ActivityTaskManager.getService().getRootTaskInfo(2, 0) != null) {
                        pipController.mPipInputConsumer.registerInputConsumer();
                    }
                } catch (RemoteException | UnsupportedOperationException e2) {
                    Log.e("PipController", "Failed to register pinned stack listener", e2);
                    e2.printStackTrace();
                }
                pipController.mTaskStackListener.addListener(new TaskStackListenerCallback() {
                    public final void onActivityPinned(String str) {
                        PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                        Objects.requireNonNull(pipTouchHandler);
                        pipTouchHandler.mPipDismissTargetHandler.createOrUpdateDismissTarget();
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        Objects.requireNonNull(pipResizeGestureHandler);
                        pipResizeGestureHandler.mIsAttached = true;
                        pipResizeGestureHandler.updateIsEnabled();
                        FloatingContentCoordinator floatingContentCoordinator = pipTouchHandler.mFloatingContentCoordinator;
                        PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
                        Objects.requireNonNull(floatingContentCoordinator);
                        floatingContentCoordinator.updateContentBounds();
                        floatingContentCoordinator.allContentBounds.put(pipMotionHelper, pipMotionHelper.getFloatingBoundsOnScreen());
                        floatingContentCoordinator.maybeMoveConflictingContent(pipMotionHelper);
                        PipMediaController pipMediaController = PipController.this.mMediaController;
                        Objects.requireNonNull(pipMediaController);
                        pipMediaController.resolveActiveMediaController(pipMediaController.mMediaSessionManager.getActiveSessionsForUser((ComponentName) null, UserHandle.CURRENT));
                        PipAppOpsListener pipAppOpsListener = PipController.this.mAppOpsListener;
                        Objects.requireNonNull(pipAppOpsListener);
                        pipAppOpsListener.mAppOpsManager.startWatchingMode(67, str, pipAppOpsListener.mAppOpsChangedListener);
                        PipController.this.mPipInputConsumer.registerInputConsumer();
                    }

                    public final void onActivityUnpinned() {
                        ComponentName componentName = (ComponentName) PipUtils.getTopPipActivity(PipController.this.mContext).first;
                        PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                        Objects.requireNonNull(pipTouchHandler);
                        if (componentName == null) {
                            PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
                            Objects.requireNonNull(pipDismissTargetHandler);
                            if (pipDismissTargetHandler.mTargetViewContainer.isAttachedToWindow()) {
                                pipDismissTargetHandler.mWindowManager.removeViewImmediate(pipDismissTargetHandler.mTargetViewContainer);
                            }
                            FloatingContentCoordinator floatingContentCoordinator = pipTouchHandler.mFloatingContentCoordinator;
                            PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
                            Objects.requireNonNull(floatingContentCoordinator);
                            floatingContentCoordinator.allContentBounds.remove(pipMotionHelper);
                        }
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        Objects.requireNonNull(pipResizeGestureHandler);
                        pipResizeGestureHandler.mIsAttached = false;
                        pipResizeGestureHandler.mUserResizeBounds.setEmpty();
                        pipResizeGestureHandler.updateIsEnabled();
                        PipAppOpsListener pipAppOpsListener = PipController.this.mAppOpsListener;
                        Objects.requireNonNull(pipAppOpsListener);
                        pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
                        PipInputConsumer pipInputConsumer = PipController.this.mPipInputConsumer;
                        Objects.requireNonNull(pipInputConsumer);
                        if (pipInputConsumer.mInputEventReceiver != null) {
                            try {
                                pipInputConsumer.mWindowManager.destroyInputConsumer(pipInputConsumer.mName, 0);
                            } catch (RemoteException e) {
                                Log.e("PipInputConsumer", "Failed to destroy input consumer", e);
                            }
                            pipInputConsumer.mInputEventReceiver.dispose();
                            pipInputConsumer.mInputEventReceiver = null;
                            pipInputConsumer.mMainExecutor.execute(new VolumeDialogImpl$$ExternalSyntheticLambda11(pipInputConsumer, 4));
                        }
                    }

                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                        if (runningTaskInfo.getWindowingMode() == 2) {
                            PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                            Objects.requireNonNull(pipTouchHandler);
                            PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
                            Objects.requireNonNull(pipMotionHelper);
                            pipMotionHelper.expandLeavePip(z, false);
                        }
                    }
                });
                pipController.mOneHandedController.ifPresent(new PipController$$ExternalSyntheticLambda4(pipController, 0));
                return;
            default:
                PipMotionHelper.$r8$lambda$QFpQr4PSFRGfS8YBsx6HKEKo4u4((PipMotionHelper) this.f$0);
                return;
        }
    }
}
