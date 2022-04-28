package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.session.MediaController;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Slog;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class StatusBarNotificationPresenter implements NotificationPresenter, ConfigurationController.ConfigurationListener, NotificationRowBinderImpl.BindRowCallback, CommandQueue.Callbacks {
    public final AboveShelfObserver mAboveShelfObserver;
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityStarter mActivityStarter = ((ActivityStarter) Dependency.get(ActivityStarter.class));
    public final IStatusBarService mBarService;
    public final C15802 mCheckSaveListener;
    public final CommandQueue mCommandQueue;
    public boolean mDispatchUiModeChangeOnUserSwitched;
    public final DozeScrimController mDozeScrimController;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final NotificationEntryManager mEntryManager;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public final C15824 mInterruptSuppressor;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockscreenGestureLogger mLockscreenGestureLogger;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationMediaManager mMediaManager;
    public final NotifPipelineFlags mNotifPipelineFlags;
    public final NotifShadeEventSource mNotifShadeEventSource;
    public final NotificationPanelViewController mNotificationPanel;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final C15813 mOnSettingsClickListener;
    public boolean mReinflateNotificationsOnUserSwitched;
    public final ScrimController mScrimController;
    public final ShadeController mShadeController;
    public final LockscreenShadeTransitionController mShadeTransitionController;
    public final StatusBar mStatusBar;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final NotificationViewHierarchyManager mViewHierarchyManager;
    public boolean mVrMode;
    public final C15791 mVrStateCallbacks;

    public StatusBarNotificationPresenter(Context context, NotificationPanelViewController notificationPanelViewController, HeadsUpManagerPhone headsUpManagerPhone, NotificationShadeWindowView notificationShadeWindowView, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, ScrimController scrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, KeyguardIndicationController keyguardIndicationController, StatusBar statusBar, ShadeController shadeController, LockscreenShadeTransitionController lockscreenShadeTransitionController, CommandQueue commandQueue, NotificationViewHierarchyManager notificationViewHierarchyManager, NotificationLockscreenUserManager notificationLockscreenUserManager, SysuiStatusBarStateController sysuiStatusBarStateController, NotifShadeEventSource notifShadeEventSource, NotificationEntryManager notificationEntryManager, NotificationMediaManager notificationMediaManager, NotificationGutsManager notificationGutsManager, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenGestureLogger lockscreenGestureLogger, InitController initController, NotificationInterruptStateProvider notificationInterruptStateProvider, NotificationRemoteInputManager notificationRemoteInputManager, ConfigurationController configurationController, NotifPipelineFlags notifPipelineFlags) {
        Context context2 = context;
        InitController initController2 = initController;
        NotificationRemoteInputManager notificationRemoteInputManager2 = notificationRemoteInputManager;
        C15791 r5 = new IVrStateCallbacks.Stub() {
            public final void onVrStateChanged(boolean z) {
                StatusBarNotificationPresenter.this.mVrMode = z;
            }
        };
        this.mVrStateCallbacks = r5;
        this.mCheckSaveListener = new Object() {
        };
        this.mOnSettingsClickListener = new NotificationGutsManager.OnSettingsClickListener() {
        };
        this.mInterruptSuppressor = new NotificationInterruptSuppressor() {
            public final String getName() {
                return "StatusBarNotificationPresenter";
            }

            public final boolean suppressAwakeInterruptions() {
                StatusBarNotificationPresenter statusBarNotificationPresenter = StatusBarNotificationPresenter.this;
                Objects.requireNonNull(statusBarNotificationPresenter);
                return statusBarNotificationPresenter.mVrMode;
            }

            public final boolean suppressInterruptions() {
                StatusBar statusBar = StatusBarNotificationPresenter.this.mStatusBar;
                Objects.requireNonNull(statusBar);
                if ((statusBar.mDisabled1 & 262144) != 0) {
                    return true;
                }
                return false;
            }

            public final boolean suppressAwakeHeadsUp(NotificationEntry notificationEntry) {
                boolean z;
                Objects.requireNonNull(notificationEntry);
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                StatusBar statusBar = StatusBarNotificationPresenter.this.mStatusBar;
                Objects.requireNonNull(statusBar);
                if (statusBar.mIsOccluded) {
                    NotificationLockscreenUserManager notificationLockscreenUserManager = StatusBarNotificationPresenter.this.mLockscreenUserManager;
                    if (notificationLockscreenUserManager.isLockscreenPublicMode(notificationLockscreenUserManager.getCurrentUserId()) || StatusBarNotificationPresenter.this.mLockscreenUserManager.isLockscreenPublicMode(statusBarNotification.getUserId())) {
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean needsRedaction = StatusBarNotificationPresenter.this.mLockscreenUserManager.needsRedaction(notificationEntry);
                    if (z && needsRedaction) {
                        return true;
                    }
                }
                if (!StatusBarNotificationPresenter.this.mCommandQueue.panelsEnabled()) {
                    return true;
                }
                if (statusBarNotification.getNotification().fullScreenIntent != null) {
                    if (StatusBarNotificationPresenter.this.mKeyguardStateController.isShowing()) {
                        StatusBar statusBar2 = StatusBarNotificationPresenter.this.mStatusBar;
                        Objects.requireNonNull(statusBar2);
                        if (!statusBar2.mIsOccluded) {
                            return true;
                        }
                    }
                    if (StatusBarNotificationPresenter.this.mAccessibilityManager.isTouchExplorationEnabled()) {
                        return true;
                    }
                }
                return false;
            }
        };
        this.mKeyguardStateController = keyguardStateController;
        this.mNotificationPanel = notificationPanelViewController;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mStatusBar = statusBar;
        this.mShadeController = shadeController;
        this.mShadeTransitionController = lockscreenShadeTransitionController;
        this.mCommandQueue = commandQueue;
        this.mViewHierarchyManager = notificationViewHierarchyManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mNotifShadeEventSource = notifShadeEventSource;
        this.mEntryManager = notificationEntryManager;
        this.mMediaManager = notificationMediaManager;
        this.mGutsManager = notificationGutsManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockscreenGestureLogger = lockscreenGestureLogger;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        AboveShelfObserver aboveShelfObserver = new AboveShelfObserver(notificationStackScrollLayoutController.mView);
        this.mAboveShelfObserver = aboveShelfObserver;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mNotifPipelineFlags = notifPipelineFlags;
        NotificationShadeWindowView notificationShadeWindowView2 = notificationShadeWindowView;
        aboveShelfObserver.mListener = (AboveShelfObserver.HasViewAboveShelfChangedListener) notificationShadeWindowView.findViewById(C1777R.C1779id.notification_container_parent);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mDozeScrimController = dozeScrimController;
        this.mScrimController = scrimController;
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(r5);
            } catch (RemoteException e) {
                RemoteException remoteException = e;
                Slog.e("StatusBarNotificationPresenter", "Failed to register VR mode state listener: " + remoteException);
            }
        }
        NotificationPanelViewController notificationPanelViewController2 = this.mNotificationPanel;
        Objects.requireNonNull(notificationPanelViewController2);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController2.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController2);
        NotificationStackScrollLayoutController.C137114 r6 = new RemoteInputController.Delegate() {
        };
        Objects.requireNonNull(notificationRemoteInputManager);
        notificationRemoteInputManager2.mCallback = (NotificationRemoteInputManager.Callback) Dependency.get(NotificationRemoteInputManager.Callback.class);
        RemoteInputController remoteInputController = new RemoteInputController(r6, notificationRemoteInputManager2.mRemoteInputUriController);
        notificationRemoteInputManager2.mRemoteInputController = remoteInputController;
        NotificationRemoteInputManager.RemoteInputListener remoteInputListener = notificationRemoteInputManager2.mRemoteInputListener;
        if (remoteInputListener != null) {
            remoteInputListener.setRemoteInputController(remoteInputController);
        }
        Iterator it = notificationRemoteInputManager2.mControllerCallbacks.iterator();
        while (it.hasNext()) {
            RemoteInputController.Callback callback = (RemoteInputController.Callback) it.next();
            RemoteInputController remoteInputController2 = notificationRemoteInputManager2.mRemoteInputController;
            Objects.requireNonNull(remoteInputController2);
            Objects.requireNonNull(callback);
            remoteInputController2.mCallbacks.add(callback);
        }
        notificationRemoteInputManager2.mControllerCallbacks.clear();
        RemoteInputController remoteInputController3 = notificationRemoteInputManager2.mRemoteInputController;
        NotificationRemoteInputManager.C11713 r52 = new RemoteInputController.Callback() {
            public final void onRemoteInputSent(
/*
Method generation error in method: com.android.systemui.statusbar.NotificationRemoteInputManager.3.onRemoteInputSent(com.android.systemui.statusbar.notification.collection.NotificationEntry):void, dex: classes.dex
            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.statusbar.NotificationRemoteInputManager.3.onRemoteInputSent(com.android.systemui.statusbar.notification.collection.NotificationEntry):void, class status: UNLOADED
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
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
        };
        Objects.requireNonNull(remoteInputController3);
        remoteInputController3.mCallbacks.add(r52);
        if (!notificationRemoteInputManager2.mNotifPipelineFlags.isNewPipelineEnabled()) {
            SmartReplyController smartReplyController = notificationRemoteInputManager2.mSmartReplyController;
            NotificationRemoteInputManager$$ExternalSyntheticLambda1 notificationRemoteInputManager$$ExternalSyntheticLambda1 = new NotificationRemoteInputManager$$ExternalSyntheticLambda1(notificationRemoteInputManager2);
            Objects.requireNonNull(smartReplyController);
            smartReplyController.mCallback = notificationRemoteInputManager$$ExternalSyntheticLambda1;
        }
        StatusBarNotificationPresenter$$ExternalSyntheticLambda1 statusBarNotificationPresenter$$ExternalSyntheticLambda1 = new StatusBarNotificationPresenter$$ExternalSyntheticLambda1(this, notificationStackScrollLayoutController, notificationRemoteInputManager2, notificationInterruptStateProvider);
        Objects.requireNonNull(initController);
        if (!initController2.mTasksExecuted) {
            initController2.mTasks.add(statusBarNotificationPresenter$$ExternalSyntheticLambda1);
            configurationController.addCallback(this);
            return;
        }
        throw new IllegalStateException("post init tasks have already been executed!");
    }

    public final boolean isCollapsing() {
        if (this.mNotificationPanel.isCollapsing() || this.mNotificationShadeWindowController.isLaunchingActivity()) {
            return true;
        }
        return false;
    }

    public final boolean isPresenterFullyCollapsed() {
        return this.mNotificationPanel.isFullyCollapsed();
    }

    public final void onActivationReset(ActivatableNotificationView activatableNotificationView) {
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanel;
        Objects.requireNonNull(notificationPanelViewController);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (activatableNotificationView == ambientState.mActivatedChild) {
            NotificationPanelViewController notificationPanelViewController2 = this.mNotificationPanel;
            Objects.requireNonNull(notificationPanelViewController2);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController2.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            AmbientState ambientState2 = notificationStackScrollLayout2.mAmbientState;
            Objects.requireNonNull(ambientState2);
            ambientState2.mActivatedChild = null;
            if (notificationStackScrollLayout2.mAnimationsEnabled) {
                notificationStackScrollLayout2.mActivateNeedsAnimation = true;
                notificationStackScrollLayout2.mNeedsAnimation = true;
            }
            notificationStackScrollLayout2.requestChildrenUpdate();
            this.mKeyguardIndicationController.hideTransientIndication();
        }
    }

    public final void onDensityOrFontScaleChanged() {
        if (!this.mNotifPipelineFlags.isNewPipelineEnabled()) {
            MessagingMessage.dropCache();
            MessagingGroup.dropCache();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            if (!keyguardUpdateMonitor.mSwitchingUser) {
                updateNotificationsOnDensityOrFontScaleChanged();
            } else {
                this.mReinflateNotificationsOnUserSwitched = true;
            }
        }
    }

    public final void onExpandClicked(NotificationEntry notificationEntry, View view, boolean z) {
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        Objects.requireNonNull(headsUpManagerPhone);
        Objects.requireNonNull(notificationEntry);
        HeadsUpManager.HeadsUpEntry headsUpEntry = headsUpManagerPhone.getHeadsUpEntry(notificationEntry.mKey);
        if (headsUpEntry != null && notificationEntry.isRowPinned()) {
            headsUpEntry.setExpanded(z);
        }
        this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "NOTIFICATION_CLICK");
        if (!z) {
            return;
        }
        if (this.mStatusBarStateController.getState() == 1) {
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mShadeTransitionController;
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            Objects.requireNonNull(lockscreenShadeTransitionController);
            lockscreenShadeTransitionController.goToLockedShade(expandableNotificationRow, true);
        } else if (notificationEntry.mSensitive && this.mDynamicPrivacyController.isInLockedDownShade()) {
            this.mStatusBarStateController.setLeaveOpenOnKeyguardHide(true);
            this.mActivityStarter.dismissKeyguardThenExecute(NavBarTuner$$ExternalSyntheticLambda2.INSTANCE$1, (Runnable) null, false);
        }
    }

    public final void onUiModeChanged() {
        if (!this.mNotifPipelineFlags.isNewPipelineEnabled()) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            if (!keyguardUpdateMonitor.mSwitchingUser) {
                updateNotificationsOnUiModeChanged();
            } else {
                this.mDispatchUiModeChangeOnUserSwitched = true;
            }
        }
    }

    public final void onUserSwitched(int i) {
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        Objects.requireNonNull(headsUpManagerPhone);
        headsUpManagerPhone.mUser = i;
        this.mCommandQueue.animateCollapsePanels();
        if (!this.mNotifPipelineFlags.isNewPipelineEnabled()) {
            if (this.mReinflateNotificationsOnUserSwitched) {
                updateNotificationsOnDensityOrFontScaleChanged();
                this.mReinflateNotificationsOnUserSwitched = false;
            }
            if (this.mDispatchUiModeChangeOnUserSwitched) {
                updateNotificationsOnUiModeChanged();
                this.mDispatchUiModeChangeOnUserSwitched = false;
            }
            updateNotificationViews("user switched");
        }
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        Objects.requireNonNull(notificationMediaManager);
        notificationMediaManager.mMediaNotificationKey = null;
        Objects.requireNonNull(notificationMediaManager.mMediaArtworkProcessor);
        notificationMediaManager.mMediaMetadata = null;
        MediaController mediaController = notificationMediaManager.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
        }
        notificationMediaManager.mMediaController = null;
        this.mStatusBar.setLockscreenUser(i);
        updateMediaMetaData(true, false);
    }

    public final void updateMediaMetaData(boolean z, boolean z2) {
        this.mMediaManager.updateMediaMetaData(z, z2);
    }

    public final void updateNotificationViews(String str) {
        if (!this.mNotifPipelineFlags.checkLegacyPipelineEnabled() || this.mScrimController == null) {
            return;
        }
        if (isCollapsing()) {
            this.mShadeController.addPostCollapseAction(new NotificationIconAreaController$$ExternalSyntheticLambda0(this, str, 1));
            return;
        }
        this.mViewHierarchyManager.updateNotificationViews();
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanel;
        Objects.requireNonNull(notificationPanelViewController);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        if (!notificationStackScrollLayoutController.mNotifPipelineFlags.isNewPipelineEnabled()) {
            Trace.beginSection("NSSLC.updateSectionBoundaries");
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mSectionsManager.updateSectionBoundaries(str);
            Trace.endSection();
        }
        notificationPanelViewController.mNotificationStackScrollLayoutController.updateFooter();
        NotificationIconAreaController notificationIconAreaController = notificationPanelViewController.mNotificationIconAreaController;
        ArrayList arrayList = new ArrayList(notificationPanelViewController.mNotificationStackScrollLayoutController.getChildCount());
        for (int i = 0; i < notificationPanelViewController.mNotificationStackScrollLayoutController.getChildCount(); i++) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationPanelViewController.mNotificationStackScrollLayoutController;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayoutController2.mView.getChildAt(i);
            if (expandableView instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                Objects.requireNonNull(expandableNotificationRow);
                arrayList.add(expandableNotificationRow.mEntry);
            }
        }
        notificationIconAreaController.updateNotificationIcons(arrayList);
    }

    public final void updateNotificationsOnDensityOrFontScaleChanged() {
        NotificationGuts notificationGuts;
        if (!this.mNotifPipelineFlags.isNewPipelineEnabled()) {
            ArrayList activeNotificationsForCurrentUser = this.mEntryManager.getActiveNotificationsForCurrentUser();
            for (int i = 0; i < activeNotificationsForCurrentUser.size(); i++) {
                NotificationEntry notificationEntry = (NotificationEntry) activeNotificationsForCurrentUser.get(i);
                Objects.requireNonNull(notificationEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.onDensityOrFontScaleChanged();
                }
                if (notificationEntry.areGutsExposed()) {
                    NotificationGutsManager notificationGutsManager = this.mGutsManager;
                    Objects.requireNonNull(notificationGutsManager);
                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                    if (expandableNotificationRow2 != null) {
                        notificationGuts = expandableNotificationRow2.mGuts;
                    } else {
                        notificationGuts = null;
                    }
                    notificationGutsManager.mNotificationGutsExposed = notificationGuts;
                    Objects.requireNonNull(expandableNotificationRow2);
                    if (expandableNotificationRow2.mGuts == null) {
                        expandableNotificationRow2.mGutsStub.inflate();
                    }
                    notificationGutsManager.bindGuts(expandableNotificationRow2, notificationGutsManager.mGutsMenuItem);
                }
            }
        }
    }

    public final void updateNotificationsOnUiModeChanged() {
        if (!this.mNotifPipelineFlags.isNewPipelineEnabled()) {
            ArrayList activeNotificationsForCurrentUser = this.mEntryManager.getActiveNotificationsForCurrentUser();
            for (int i = 0; i < activeNotificationsForCurrentUser.size(); i++) {
                NotificationEntry notificationEntry = (NotificationEntry) activeNotificationsForCurrentUser.get(i);
                Objects.requireNonNull(notificationEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.onUiModeChanged();
                }
            }
        }
    }

    public final void onThemeChanged() {
        onDensityOrFontScaleChanged();
    }
}
