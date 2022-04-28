package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.leanback.R$raw;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.UdfpsKeyguardViewController;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.communal.CommunalHostView;
import com.android.systemui.communal.CommunalHostViewController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController implements Dumpable {
    public final AmbientState ambientState;
    public Function1<? super Long, Unit> animationHandlerOnKeyguardDismiss;
    public final Context context;
    public final NotificationShadeDepthController depthController;
    public float dragDownAmount;
    public ValueAnimator dragDownAnimator;
    public NotificationEntry draggedDownEntry;
    public final FalsingCollector falsingCollector;
    public boolean forceApplyAmount;
    public int fullTransitionDistance;
    public boolean isWakingToShadeLocked;
    public final KeyguardBypassController keyguardBypassController;
    public final NotificationLockscreenUserManager lockScreenUserManager;
    public final LSShadeTransitionLogger logger;
    public final MediaHierarchyManager mediaHierarchyManager;
    public boolean nextHideKeyguardNeedsNoAnimation;
    public NotificationPanelViewController notificationPanelController;
    public NotificationStackScrollLayoutController nsslController;
    public float pulseHeight;
    public ValueAnimator pulseHeightAnimator;

    /* renamed from: qS */
    public C0961QS f75qS;
    public float qSDragProgress;
    public final ScrimController scrimController;
    public int scrimTransitionDistance;
    public final SysuiStatusBarStateController statusBarStateController;
    public StatusBar statusbar;
    public final DragDownHelper touchHelper;
    public UdfpsKeyguardViewController udfpsKeyguardViewController;
    public boolean useSplitShade;

    /* renamed from: getDragDownAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m68x68daf339() {
    }

    /* renamed from: getPulseHeightAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m69xefeea8b7() {
    }

    public final void setPulseHeight(float f, boolean z) {
        if (z) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.pulseHeight, f});
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ofFloat.setDuration(375);
            ofFloat.addUpdateListener(new LockscreenShadeTransitionController$setPulseHeight$1(this));
            ofFloat.start();
            this.pulseHeightAnimator = ofFloat;
            return;
        }
        this.pulseHeight = f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController);
        float pulseHeight2 = notificationStackScrollLayoutController.mView.setPulseHeight(f);
        NotificationPanelViewController notificationPanelController2 = getNotificationPanelController();
        Objects.requireNonNull(notificationPanelController2);
        float height = pulseHeight2 / ((float) notificationPanelController2.mView.getHeight());
        PathInterpolator pathInterpolator = Interpolators.EMPHASIZED;
        notificationPanelController2.mOverStretchAmount = MathUtils.max(0.0f, (float) (1.0d - Math.exp((double) (height * -4.0f)))) * ((float) notificationPanelController2.mMaxOverscrollAmountForPulse);
        notificationPanelController2.positionClockAndNotifications(true);
        if (!this.keyguardBypassController.getBypassEnabled()) {
            f = 0.0f;
        }
        transitionToShadeAmountCommon(f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0018, code lost:
        if (r0.mDynamicPrivacyController.isInLockedDownShade() != false) goto L_0x001a;
     */
    /* renamed from: canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean mo11560x90582e2c() {
        /*
            r3 = this;
            com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r3.statusBarStateController
            int r0 = r0.getState()
            r1 = 0
            r2 = 1
            if (r0 == r2) goto L_0x001a
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r3.nsslController
            if (r0 != 0) goto L_0x000f
            r0 = r1
        L_0x000f:
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.DynamicPrivacyController r0 = r0.mDynamicPrivacyController
            boolean r0 = r0.isInLockedDownShade()
            if (r0 == 0) goto L_0x002a
        L_0x001a:
            com.android.systemui.plugins.qs.QS r0 = r3.f75qS
            if (r0 == 0) goto L_0x001f
            r1 = r0
        L_0x001f:
            boolean r0 = r1.isFullyCollapsed()
            if (r0 != 0) goto L_0x002b
            boolean r3 = r3.useSplitShade
            if (r3 == 0) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r2 = 0
        L_0x002b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.LockscreenShadeTransitionController.mo11560x90582e2c():boolean");
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("LSShadeTransitionController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("pulseHeight: ", Float.valueOf(this.pulseHeight)));
        indentingPrintWriter.println(Intrinsics.stringPlus("useSplitShade: ", Boolean.valueOf(this.useSplitShade)));
        indentingPrintWriter.println(Intrinsics.stringPlus("dragDownAmount: ", Float.valueOf(this.dragDownAmount)));
        indentingPrintWriter.println(Intrinsics.stringPlus("qSDragProgress: ", Float.valueOf(this.qSDragProgress)));
        indentingPrintWriter.println(Intrinsics.stringPlus("isDragDownAnywhereEnabled: ", Boolean.valueOf(mo11565x7adb072c())));
        boolean z2 = false;
        if (this.statusBarStateController.getState() == 1) {
            z = true;
        } else {
            z = false;
        }
        indentingPrintWriter.println(Intrinsics.stringPlus("isFalsingCheckNeeded: ", Boolean.valueOf(z)));
        indentingPrintWriter.println(Intrinsics.stringPlus("isWakingToShadeLocked: ", Boolean.valueOf(this.isWakingToShadeLocked)));
        if (this.animationHandlerOnKeyguardDismiss != null) {
            z2 = true;
        }
        indentingPrintWriter.println(Intrinsics.stringPlus("hasPendingHandlerOnKeyguardDismiss: ", Boolean.valueOf(z2)));
    }

    public final void finishPulseAnimation(boolean z) {
        this.logger.logPulseExpansionFinished(z);
        if (z) {
            setPulseHeight(0.0f, true);
            return;
        }
        NotificationPanelViewController notificationPanelController2 = getNotificationPanelController();
        Objects.requireNonNull(notificationPanelController2);
        notificationPanelController2.mAnimateNextNotificationBounds = true;
        notificationPanelController2.mNotificationBoundsAnimationDuration = 448;
        notificationPanelController2.mNotificationBoundsAnimationDelay = 0;
        notificationPanelController2.mIsPulseExpansionResetAnimator = true;
        setPulseHeight(0.0f, false);
    }

    public final NotificationPanelViewController getNotificationPanelController() {
        NotificationPanelViewController notificationPanelViewController = this.notificationPanelController;
        if (notificationPanelViewController != null) {
            return notificationPanelViewController;
        }
        return null;
    }

    public final void goToLockedShade(View view, boolean z) {
        LockscreenShadeTransitionController$goToLockedShade$1 lockscreenShadeTransitionController$goToLockedShade$1;
        boolean z2 = true;
        if (this.statusBarStateController.getState() != 1) {
            z2 = false;
        }
        this.logger.logTryGoToLockedShade(z2);
        if (z2) {
            if (z) {
                lockscreenShadeTransitionController$goToLockedShade$1 = null;
            } else {
                lockscreenShadeTransitionController$goToLockedShade$1 = new LockscreenShadeTransitionController$goToLockedShade$1(this);
            }
            goToLockedShadeInternal(view, lockscreenShadeTransitionController$goToLockedShade$1, (C1153xbbc01eb0) null);
        }
    }

    public final void goToLockedShadeInternal(View view, Function1 function1, C1153xbbc01eb0 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1) {
        boolean z;
        NotificationEntry notificationEntry;
        boolean z2;
        LockscreenShadeTransitionController$goToLockedShadeInternal$1 lockscreenShadeTransitionController$goToLockedShadeInternal$1;
        StatusBar statusBar = this.statusbar;
        if (statusBar == null) {
            statusBar = null;
        }
        Objects.requireNonNull(statusBar);
        boolean z3 = false;
        if ((statusBar.mDisabled2 & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 != null) {
                lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1.run();
            }
            this.logger.logShadeDisabledOnGoToLockedShade();
            return;
        }
        int currentUserId = this.lockScreenUserManager.getCurrentUserId();
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            Objects.requireNonNull(expandableNotificationRow);
            notificationEntry = expandableNotificationRow.mEntry;
            Objects.requireNonNull(notificationEntry);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.setUserExpanded(true, true);
            }
            ExpandableNotificationRow expandableNotificationRow3 = notificationEntry.row;
            if (expandableNotificationRow3 != null) {
                expandableNotificationRow3.mGroupExpansionChanging = true;
            }
            currentUserId = notificationEntry.mSbn.getUserId();
        } else {
            notificationEntry = null;
        }
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockScreenUserManager;
        if (!notificationLockscreenUserManager.userAllowsPrivateNotificationsInPublic(notificationLockscreenUserManager.getCurrentUserId()) || !this.lockScreenUserManager.shouldShowLockscreenNotifications()) {
            z2 = true;
        } else {
            this.falsingCollector.shouldEnforceBouncer();
            z2 = false;
        }
        if (this.keyguardBypassController.getBypassEnabled()) {
            z2 = false;
        }
        if (!this.lockScreenUserManager.isLockscreenPublicMode(currentUserId) || !z2) {
            LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
            if (function1 != null) {
                z3 = true;
            }
            lSShadeTransitionLogger.logGoingToLockedShade(z3);
            if (this.statusBarStateController.isDozing()) {
                this.isWakingToShadeLocked = true;
            }
            this.statusBarStateController.setState(2);
            if (function1 != null) {
                function1.invoke(0L);
            } else {
                performDefaultGoToFullShadeAnimation(0);
            }
        } else {
            this.statusBarStateController.setLeaveOpenOnKeyguardHide(true);
            if (function1 != null) {
                lockscreenShadeTransitionController$goToLockedShadeInternal$1 = new LockscreenShadeTransitionController$goToLockedShadeInternal$1(this, function1);
            } else {
                lockscreenShadeTransitionController$goToLockedShadeInternal$1 = null;
            }
            C1151x13216739 lockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1 = new C1151x13216739(this, lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1);
            this.logger.logShowBouncerOnGoToLockedShade();
            StatusBar statusBar2 = this.statusbar;
            if (statusBar2 == null) {
                statusBar2 = null;
            }
            Objects.requireNonNull(statusBar2);
            int i = statusBar2.mState;
            if (i == 1 || i == 2) {
                KeyguardViewMediator keyguardViewMediator = statusBar2.mKeyguardViewMediator;
                Objects.requireNonNull(keyguardViewMediator);
                if (!keyguardViewMediator.mHiding) {
                    StatusBarKeyguardViewManager statusBarKeyguardViewManager = statusBar2.mStatusBarKeyguardViewManager;
                    Objects.requireNonNull(statusBarKeyguardViewManager);
                    statusBarKeyguardViewManager.dismissWithAction(lockscreenShadeTransitionController$goToLockedShadeInternal$1, lockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1, false, (String) null);
                    this.draggedDownEntry = notificationEntry;
                }
            }
            lockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1.run();
            this.draggedDownEntry = notificationEntry;
        }
    }

    /* renamed from: isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final boolean mo11565x7adb072c() {
        if (this.statusBarStateController.getState() == 1 && !this.keyguardBypassController.getBypassEnabled()) {
            C0961QS qs = this.f75qS;
            if (qs == null) {
                qs = null;
            }
            if (qs.isFullyCollapsed() || this.useSplitShade) {
                return true;
            }
        }
        return false;
    }

    public final void performDefaultGoToFullShadeAnimation(long j) {
        this.logger.logDefaultGoToFullShadeAnimation(j);
        getNotificationPanelController().animateToFullShade(j);
        this.forceApplyAmount = true;
        mo11568xcfc05636(1.0f);
        setDragDownAmountAnimated((float) this.fullTransitionDistance, j, new LockscreenShadeTransitionController$animateAppear$1(this));
    }

    /* renamed from: setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo11568xcfc05636(float f) {
        boolean z;
        float f2;
        float f3;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (this.dragDownAmount == f) {
            z = true;
        } else {
            z = false;
        }
        if (!z || this.forceApplyAmount) {
            this.dragDownAmount = f;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            C0961QS qs = null;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            Objects.requireNonNull(notificationStackScrollLayoutController);
            if (notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
                if (this.dragDownAmount == 0.0f) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3 && !this.forceApplyAmount) {
                    return;
                }
            }
            float saturate = MathUtils.saturate(this.dragDownAmount / ((float) this.scrimTransitionDistance));
            this.qSDragProgress = saturate;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
            if (notificationStackScrollLayoutController2 == null) {
                notificationStackScrollLayoutController2 = null;
            }
            float f4 = this.dragDownAmount;
            Objects.requireNonNull(notificationStackScrollLayoutController2);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            NotificationShelf notificationShelf = notificationStackScrollLayout.mShelf;
            Objects.requireNonNull(notificationShelf);
            notificationShelf.mFractionToShade = saturate;
            notificationStackScrollLayout.requestChildrenUpdate();
            if (notificationStackScrollLayoutController2.mStatusBarStateController.getState() == 1) {
                float saturate2 = MathUtils.saturate(f4 / ((float) notificationStackScrollLayoutController2.mView.getHeight()));
                float height = ((float) notificationStackScrollLayoutController2.mTotalDistanceForFullShadeTransition) / ((float) notificationStackScrollLayoutController2.mView.getHeight());
                PathInterpolator pathInterpolator = Interpolators.EMPHASIZED;
                if (height != 0.0f) {
                    f2 = MathUtils.max(0.0f, ((float) (1.0d - Math.exp((double) ((-(MathUtils.log(2.6666665f) / height)) * saturate2)))) * 1.6f) * ((float) notificationStackScrollLayoutController2.mNotificationDragDownMovement);
                } else {
                    throw new IllegalArgumentException("Invalid values for overshoot");
                }
            } else {
                f2 = 0.0f;
            }
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController2.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mExtraTopInsetForFullShadeTransition = f2;
            notificationStackScrollLayout2.updateStackPosition(false);
            notificationStackScrollLayout2.requestChildrenUpdate();
            C0961QS qs2 = this.f75qS;
            if (qs2 != null) {
                qs = qs2;
            }
            qs.setTransitionToFullShadeAmount(this.dragDownAmount, this.qSDragProgress);
            getNotificationPanelController().setTransitionToFullShadeAmount(this.dragDownAmount, false, 0);
            if (this.useSplitShade) {
                f3 = 0.0f;
            } else {
                f3 = this.dragDownAmount;
            }
            MediaHierarchyManager mediaHierarchyManager2 = this.mediaHierarchyManager;
            Objects.requireNonNull(mediaHierarchyManager2);
            float saturate3 = MathUtils.saturate(f3 / ((float) mediaHierarchyManager2.distanceForFullShadeTransition));
            if (mediaHierarchyManager2.fullShadeTransitionProgress == saturate3) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                mediaHierarchyManager2.fullShadeTransitionProgress = saturate3;
                if (!mediaHierarchyManager2.bypassController.getBypassEnabled() && mediaHierarchyManager2.statusbarState == 1) {
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager2, mediaHierarchyManager2.isCurrentlyFading(), 2);
                    if (saturate3 >= 0.0f) {
                        mediaHierarchyManager2.updateTargetState();
                        float f5 = mediaHierarchyManager2.fullShadeTransitionProgress;
                        float f6 = 1.0f;
                        if (f5 <= 0.5f) {
                            f6 = 1.0f - (f5 / 0.5f);
                        }
                        if (mediaHierarchyManager2.carouselAlpha != f6) {
                            z4 = false;
                        }
                        if (!z4) {
                            mediaHierarchyManager2.carouselAlpha = f6;
                            R$raw.fadeIn((View) mediaHierarchyManager2.getMediaFrame(), f6, false);
                        }
                        mediaHierarchyManager2.applyTargetStateIfNotAnimating();
                    }
                }
            }
            transitionToShadeAmountCommon(this.dragDownAmount);
        }
    }

    public final void setDragDownAmountAnimated(float f, long j, Function0<Unit> function0) {
        this.logger.logDragDownAnimation(f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.dragDownAmount, f});
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.setDuration(375);
        ofFloat.addUpdateListener(new LockscreenShadeTransitionController$setDragDownAmountAnimated$1(this));
        if (j > 0) {
            ofFloat.setStartDelay(j);
        }
        if (function0 != null) {
            ofFloat.addListener(new LockscreenShadeTransitionController$setDragDownAmountAnimated$2(function0));
        }
        ofFloat.start();
        this.dragDownAnimator = ofFloat;
    }

    public final void transitionToShadeAmountCommon(float f) {
        boolean z;
        float saturate = MathUtils.saturate(f / ((float) this.scrimTransitionDistance));
        ScrimController scrimController2 = this.scrimController;
        Objects.requireNonNull(scrimController2);
        boolean z2 = false;
        if (saturate != scrimController2.mTransitionToFullShadeProgress) {
            scrimController2.mTransitionToFullShadeProgress = saturate;
            if (saturate > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            if (z != scrimController2.mTransitioningToFullShade) {
                scrimController2.mTransitioningToFullShade = z;
                if (z) {
                    ScrimState.SHADE_LOCKED.prepare(scrimController2.mState);
                }
            }
            scrimController2.applyAndDispatchState();
        }
        NotificationPanelViewController notificationPanelController2 = getNotificationPanelController();
        Objects.requireNonNull(notificationPanelController2);
        float interpolation = Interpolators.ALPHA_IN.getInterpolation(1.0f - saturate);
        notificationPanelController2.mKeyguardOnlyContentAlpha = interpolation;
        if (notificationPanelController2.mBarState == 1) {
            notificationPanelController2.mBottomAreaShadeAlpha = interpolation;
            notificationPanelController2.updateKeyguardBottomAreaAlpha();
        }
        notificationPanelController2.updateClock();
        CommunalHostViewController communalHostViewController = notificationPanelController2.mCommunalViewController;
        if (communalHostViewController != null) {
            float f2 = notificationPanelController2.mKeyguardOnlyContentAlpha;
            KeyguardVisibilityHelper keyguardVisibilityHelper = communalHostViewController.mKeyguardVisibilityHelper;
            Objects.requireNonNull(keyguardVisibilityHelper);
            if (!keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating) {
                ((CommunalHostView) communalHostViewController.mView).setAlpha(f2);
                int childCount = ((CommunalHostView) communalHostViewController.mView).getChildCount();
                while (true) {
                    childCount--;
                    if (childCount < 0) {
                        break;
                    }
                    ((CommunalHostView) communalHostViewController.mView).getChildAt(childCount).setAlpha(f2);
                }
            }
        }
        NotificationShadeDepthController notificationShadeDepthController = this.depthController;
        Objects.requireNonNull(notificationShadeDepthController);
        if (notificationShadeDepthController.transitionToFullShadeProgress == saturate) {
            z2 = true;
        }
        StatusBar statusBar = null;
        if (!z2) {
            notificationShadeDepthController.transitionToFullShadeProgress = saturate;
            notificationShadeDepthController.scheduleUpdate((View) null);
        }
        UdfpsKeyguardViewController udfpsKeyguardViewController2 = this.udfpsKeyguardViewController;
        if (udfpsKeyguardViewController2 != null) {
            udfpsKeyguardViewController2.mTransitionToFullShadeProgress = saturate;
            udfpsKeyguardViewController2.updateAlpha();
        }
        StatusBar statusBar2 = this.statusbar;
        if (statusBar2 != null) {
            statusBar = statusBar2;
        }
        Objects.requireNonNull(statusBar);
        statusBar.mTransitionToFullShadeProgress = saturate;
    }

    public final void updateResources() {
        this.scrimTransitionDistance = this.context.getResources().getDimensionPixelSize(C1777R.dimen.lockscreen_shade_scrim_transition_distance);
        this.fullTransitionDistance = this.context.getResources().getDimensionPixelSize(C1777R.dimen.lockscreen_shade_qs_transition_distance);
        this.useSplitShade = Utils.shouldUseSplitNotificationShade(this.context.getResources());
    }

    public LockscreenShadeTransitionController(SysuiStatusBarStateController sysuiStatusBarStateController, LSShadeTransitionLogger lSShadeTransitionLogger, KeyguardBypassController keyguardBypassController2, NotificationLockscreenUserManager notificationLockscreenUserManager, FalsingCollector falsingCollector2, AmbientState ambientState2, MediaHierarchyManager mediaHierarchyManager2, ScrimController scrimController2, NotificationShadeDepthController notificationShadeDepthController, Context context2, WakefulnessLifecycle wakefulnessLifecycle, ConfigurationController configurationController, FalsingManager falsingManager, DumpManager dumpManager) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.logger = lSShadeTransitionLogger;
        this.keyguardBypassController = keyguardBypassController2;
        this.lockScreenUserManager = notificationLockscreenUserManager;
        this.falsingCollector = falsingCollector2;
        this.ambientState = ambientState2;
        this.mediaHierarchyManager = mediaHierarchyManager2;
        this.scrimController = scrimController2;
        this.depthController = notificationShadeDepthController;
        this.context = context2;
        this.touchHelper = new DragDownHelper(falsingManager, falsingCollector2, this, context2);
        updateResources();
        configurationController.addCallback(new ConfigurationController.ConfigurationListener(this) {
            public final /* synthetic */ LockscreenShadeTransitionController this$0;

            {
                this.this$0 = r1;
            }

            public final void onConfigChanged(Configuration configuration) {
                this.this$0.updateResources();
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
                Objects.requireNonNull(lockscreenShadeTransitionController);
                lockscreenShadeTransitionController.touchHelper.updateResources(this.this$0.context);
            }
        });
        dumpManager.registerDumpable(this);
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener(this) {
            public final /* synthetic */ LockscreenShadeTransitionController this$0;

            {
                this.this$0 = r1;
            }

            public final void onExpandedChanged(boolean z) {
                boolean z2;
                boolean z3;
                boolean z4;
                if (!z) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
                    Objects.requireNonNull(lockscreenShadeTransitionController);
                    boolean z5 = true;
                    if (lockscreenShadeTransitionController.dragDownAmount == 0.0f) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!z2) {
                        LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.this$0;
                        Objects.requireNonNull(lockscreenShadeTransitionController2);
                        ValueAnimator valueAnimator = lockscreenShadeTransitionController2.dragDownAnimator;
                        if (valueAnimator != null && valueAnimator.isRunning()) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (!z4) {
                            this.this$0.logger.logDragDownAmountResetWhenFullyCollapsed();
                            this.this$0.mo11568xcfc05636(0.0f);
                        }
                    }
                    LockscreenShadeTransitionController lockscreenShadeTransitionController3 = this.this$0;
                    if (lockscreenShadeTransitionController3.pulseHeight == 0.0f) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (!z3) {
                        Objects.requireNonNull(lockscreenShadeTransitionController3);
                        ValueAnimator valueAnimator2 = lockscreenShadeTransitionController3.pulseHeightAnimator;
                        if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                            z5 = false;
                        }
                        if (!z5) {
                            this.this$0.logger.logPulseHeightNotResetWhenFullyCollapsed();
                            this.this$0.setPulseHeight(0.0f, false);
                        }
                    }
                }
            }
        });
        wakefulnessLifecycle.mObservers.add(new WakefulnessLifecycle.Observer(this) {
            public final /* synthetic */ LockscreenShadeTransitionController this$0;

            {
                this.this$0 = r1;
            }

            public final void onPostFinishedWakingUp() {
                this.this$0.isWakingToShadeLocked = false;
            }
        });
    }

    /* renamed from: isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final boolean mo11566x229f8ab3(ExpandableView expandableView) {
        if (mo11565x7adb072c()) {
            return true;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        Objects.requireNonNull(notificationStackScrollLayoutController);
        if (!notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
            return false;
        }
        if (expandableView == null) {
            return true;
        }
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        NotificationEntry notificationEntry = ((ExpandableNotificationRow) expandableView).mEntry;
        Objects.requireNonNull(notificationEntry);
        return notificationEntry.mSensitive;
    }
}
