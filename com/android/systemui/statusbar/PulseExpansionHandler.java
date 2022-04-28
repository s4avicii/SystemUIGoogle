package com.android.systemui.statusbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.util.IndentingPrintWriter;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PulseExpansionHandler.kt */
public final class PulseExpansionHandler implements Gefingerpoken, Dumpable {
    public boolean bouncerShowing;
    public final KeyguardBypassController bypassController;
    public final ConfigurationController configurationController;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public final HeadsUpManagerPhone headsUpManager;
    public boolean isExpanding;
    public boolean leavingLockscreen;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final PowerManager mPowerManager;
    public boolean mPulsing;
    public ExpandableView mStartingChild;
    public final int[] mTemp2 = new int[2];
    public Runnable pulseExpandAbortListener;
    public boolean qsExpanded;
    public final NotificationRoundnessManager roundnessManager;
    public NotificationStackScrollLayoutController stackScrollerController;
    public final StatusBarStateController statusBarStateController;
    public float touchSlop;
    public VelocityTracker velocityTracker;
    public final NotificationWakeUpCoordinator wakeUpCoordinator;

    public final void cancelExpansion() {
        setExpanding(false);
        this.falsingCollector.onExpansionFromPulseStopped();
        ExpandableView expandableView = this.mStartingChild;
        if (expandableView != null) {
            if (expandableView.mActualHeight != expandableView.getCollapsedHeight()) {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(expandableView, "actualHeight", new int[]{expandableView.mActualHeight, expandableView.getCollapsedHeight()});
                ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                ofInt.setDuration((long) 375);
                ofInt.addListener(new PulseExpansionHandler$reset$1(this, expandableView));
                ofInt.start();
            } else if (expandableView instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) expandableView).setUserLocked(false);
            }
            this.mStartingChild = null;
        }
        this.lockscreenShadeTransitionController.finishPulseAnimation(true);
        this.wakeUpCoordinator.setNotificationsVisibleForExpansion(false, true, false);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("PulseExpansionHandler:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("isExpanding: ", Boolean.valueOf(this.isExpanding)));
        indentingPrintWriter.println(Intrinsics.stringPlus("leavingLockscreen: ", Boolean.valueOf(this.leavingLockscreen)));
        indentingPrintWriter.println(Intrinsics.stringPlus("mPulsing: ", Boolean.valueOf(this.mPulsing)));
        indentingPrintWriter.println(Intrinsics.stringPlus("qsExpanded: ", Boolean.valueOf(this.qsExpanded)));
        indentingPrintWriter.println(Intrinsics.stringPlus("bouncerShowing: ", Boolean.valueOf(this.bouncerShowing)));
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (!this.wakeUpCoordinator.getCanShowPulsingHuns() || this.qsExpanded || this.bouncerShowing) {
            z = false;
        } else {
            z = true;
        }
        if (!z || !startExpansion(motionEvent)) {
            return false;
        }
        return true;
    }

    public final void setExpanding(boolean z) {
        boolean z2;
        if (this.isExpanding != z) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.isExpanding = z;
        KeyguardBypassController keyguardBypassController = this.bypassController;
        Objects.requireNonNull(keyguardBypassController);
        keyguardBypassController.isPulseExpanding = z;
        if (z2) {
            if (z) {
                NotificationEntry topEntry = this.headsUpManager.getTopEntry();
                if (topEntry != null) {
                    NotificationRoundnessManager notificationRoundnessManager = this.roundnessManager;
                    ExpandableNotificationRow expandableNotificationRow = topEntry.row;
                    Objects.requireNonNull(notificationRoundnessManager);
                    ExpandableNotificationRow expandableNotificationRow2 = notificationRoundnessManager.mTrackedHeadsUp;
                    notificationRoundnessManager.mTrackedHeadsUp = expandableNotificationRow;
                    if (expandableNotificationRow2 != null) {
                        notificationRoundnessManager.updateView(expandableNotificationRow2, true);
                    }
                }
                LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.lockscreenShadeTransitionController;
                Objects.requireNonNull(lockscreenShadeTransitionController2);
                lockscreenShadeTransitionController2.logger.logPulseExpansionStarted();
                ValueAnimator valueAnimator = lockscreenShadeTransitionController2.pulseHeightAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    lockscreenShadeTransitionController2.logger.logAnimationCancelled(true);
                    valueAnimator.cancel();
                }
            } else {
                NotificationRoundnessManager notificationRoundnessManager2 = this.roundnessManager;
                Objects.requireNonNull(notificationRoundnessManager2);
                ExpandableNotificationRow expandableNotificationRow3 = notificationRoundnessManager2.mTrackedHeadsUp;
                notificationRoundnessManager2.mTrackedHeadsUp = null;
                if (expandableNotificationRow3 != null) {
                    notificationRoundnessManager2.updateView(expandableNotificationRow3, true);
                }
                if (!this.leavingLockscreen) {
                    this.bypassController.maybePerformPendingUnlock();
                    Runnable runnable = this.pulseExpandAbortListener;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
            this.headsUpManager.unpinAll();
        }
    }

    public final boolean startExpansion(MotionEvent motionEvent) {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker2 = this.velocityTracker;
        Intrinsics.checkNotNull(velocityTracker2);
        velocityTracker2.addMovement(motionEvent);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        ExpandableNotificationRow expandableNotificationRow = null;
        if (actionMasked == 0) {
            setExpanding(false);
            this.leavingLockscreen = false;
            this.mStartingChild = null;
            this.mInitialTouchY = y;
            this.mInitialTouchX = x;
        } else if (actionMasked == 1) {
            VelocityTracker velocityTracker3 = this.velocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
            }
            this.velocityTracker = null;
            setExpanding(false);
        } else if (actionMasked == 2) {
            float f = y - this.mInitialTouchY;
            if (f > this.touchSlop && f > Math.abs(x - this.mInitialTouchX)) {
                this.falsingCollector.onStartExpandingFromPulse();
                setExpanding(true);
                float f2 = this.mInitialTouchX;
                float f3 = this.mInitialTouchY;
                if (this.mStartingChild == null && !this.bypassController.getBypassEnabled()) {
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
                    if (notificationStackScrollLayoutController == null) {
                        notificationStackScrollLayoutController = null;
                    }
                    int[] iArr = this.mTemp2;
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    notificationStackScrollLayoutController.mView.getLocationOnScreen(iArr);
                    int[] iArr2 = this.mTemp2;
                    float f4 = f2 + ((float) iArr2[0]);
                    float f5 = f3 + ((float) iArr2[1]);
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.stackScrollerController;
                    if (notificationStackScrollLayoutController2 == null) {
                        notificationStackScrollLayoutController2 = null;
                    }
                    Objects.requireNonNull(notificationStackScrollLayoutController2);
                    ExpandableView childAtRawPosition = notificationStackScrollLayoutController2.mView.getChildAtRawPosition(f4, f5);
                    if (childAtRawPosition != null && childAtRawPosition.isContentExpandable()) {
                        expandableNotificationRow = childAtRawPosition;
                    }
                    this.mStartingChild = expandableNotificationRow;
                    if (expandableNotificationRow != null && (expandableNotificationRow instanceof ExpandableNotificationRow)) {
                        expandableNotificationRow.setUserLocked(true);
                    }
                }
                this.mInitialTouchY = y;
                this.mInitialTouchX = x;
                return true;
            }
        } else if (actionMasked == 3) {
            VelocityTracker velocityTracker4 = this.velocityTracker;
            if (velocityTracker4 != null) {
                velocityTracker4.recycle();
            }
            this.velocityTracker = null;
            setExpanding(false);
        }
        return false;
    }

    public PulseExpansionHandler(final Context context, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, ConfigurationController configurationController2, StatusBarStateController statusBarStateController2, FalsingManager falsingManager2, LockscreenShadeTransitionController lockscreenShadeTransitionController2, FalsingCollector falsingCollector2, DumpManager dumpManager) {
        this.wakeUpCoordinator = notificationWakeUpCoordinator;
        this.bypassController = keyguardBypassController;
        this.headsUpManager = headsUpManagerPhone;
        this.roundnessManager = notificationRoundnessManager;
        this.configurationController = configurationController2;
        this.statusBarStateController = statusBarStateController2;
        this.falsingManager = falsingManager2;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController2;
        this.falsingCollector = falsingCollector2;
        context.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_drag_down_min_distance);
        this.touchSlop = (float) ViewConfiguration.get(context).getScaledTouchSlop();
        configurationController2.addCallback(new ConfigurationController.ConfigurationListener(this) {
            public final /* synthetic */ PulseExpansionHandler this$0;

            {
                this.this$0 = r1;
            }

            public final void onConfigChanged(Configuration configuration) {
                PulseExpansionHandler pulseExpansionHandler = this.this$0;
                Context context = context;
                Objects.requireNonNull(pulseExpansionHandler);
                context.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_drag_down_min_distance);
                pulseExpansionHandler.touchSlop = (float) ViewConfiguration.get(context).getScaledTouchSlop();
            }
        });
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        dumpManager.registerDumpable(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:76:0x013a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            int r0 = r9.getAction()
            r1 = 1
            r2 = 0
            r3 = 3
            if (r0 == r3) goto L_0x000f
            int r0 = r9.getAction()
            if (r0 != r1) goto L_0x0015
        L_0x000f:
            boolean r0 = r8.isExpanding
            if (r0 == 0) goto L_0x0015
            r0 = r1
            goto L_0x0016
        L_0x0015:
            r0 = r2
        L_0x0016:
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r4 = r8.wakeUpCoordinator
            boolean r4 = r4.getCanShowPulsingHuns()
            if (r4 == 0) goto L_0x0028
            boolean r4 = r8.qsExpanded
            if (r4 != 0) goto L_0x0028
            boolean r4 = r8.bouncerShowing
            if (r4 != 0) goto L_0x0028
            r4 = r1
            goto L_0x0029
        L_0x0028:
            r4 = r2
        L_0x0029:
            if (r4 != 0) goto L_0x002e
            if (r0 != 0) goto L_0x002e
            return r2
        L_0x002e:
            android.view.VelocityTracker r0 = r8.velocityTracker
            if (r0 == 0) goto L_0x0142
            boolean r0 = r8.isExpanding
            if (r0 == 0) goto L_0x0142
            int r0 = r9.getActionMasked()
            if (r0 != 0) goto L_0x003e
            goto L_0x0142
        L_0x003e:
            android.view.VelocityTracker r0 = r8.velocityTracker
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            r0.addMovement(r9)
            float r0 = r9.getY()
            float r4 = r8.mInitialTouchY
            float r0 = r0 - r4
            int r9 = r9.getActionMasked()
            r4 = 2
            r5 = 0
            r6 = 0
            if (r9 == r1) goto L_0x00a1
            if (r9 == r4) goto L_0x006b
            if (r9 == r3) goto L_0x005c
            goto L_0x013f
        L_0x005c:
            r8.cancelExpansion()
            android.view.VelocityTracker r9 = r8.velocityTracker
            if (r9 != 0) goto L_0x0064
            goto L_0x0067
        L_0x0064:
            r9.recycle()
        L_0x0067:
            r8.velocityTracker = r6
            goto L_0x013f
        L_0x006b:
            float r9 = java.lang.Math.max(r0, r5)
            com.android.systemui.statusbar.notification.row.ExpandableView r3 = r8.mStartingChild
            if (r3 == 0) goto L_0x0086
            int r0 = r3.getCollapsedHeight()
            float r0 = (float) r0
            float r0 = r0 + r9
            int r0 = (int) r0
            int r4 = r3.getMaxContentHeight()
            int r0 = java.lang.Math.min(r0, r4)
            r3.setActualHeight(r0, r1)
            goto L_0x009a
        L_0x0086:
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r3 = r8.wakeUpCoordinator
            com.android.systemui.statusbar.LockscreenShadeTransitionController r4 = r8.lockscreenShadeTransitionController
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.scrimTransitionDistance
            float r4 = (float) r4
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0096
            r0 = r1
            goto L_0x0097
        L_0x0096:
            r0 = r2
        L_0x0097:
            r3.setNotificationsVisibleForExpansion(r0, r1, r1)
        L_0x009a:
            com.android.systemui.statusbar.LockscreenShadeTransitionController r0 = r8.lockscreenShadeTransitionController
            r0.setPulseHeight(r9, r2)
            goto L_0x013f
        L_0x00a1:
            android.view.VelocityTracker r9 = r8.velocityTracker
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            r3 = 1000(0x3e8, float:1.401E-42)
            r9.computeCurrentVelocity(r3)
            int r9 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r9 <= 0) goto L_0x00c8
            android.view.VelocityTracker r9 = r8.velocityTracker
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            float r9 = r9.getYVelocity()
            r0 = -998637568(0xffffffffc47a0000, float:-1000.0)
            int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r9 <= 0) goto L_0x00c8
            com.android.systemui.plugins.statusbar.StatusBarStateController r9 = r8.statusBarStateController
            int r9 = r9.getState()
            if (r9 == 0) goto L_0x00c8
            r9 = r1
            goto L_0x00c9
        L_0x00c8:
            r9 = r2
        L_0x00c9:
            com.android.systemui.plugins.FalsingManager r0 = r8.falsingManager
            boolean r0 = r0.isUnlockingDisabled()
            if (r0 != 0) goto L_0x0132
            com.android.systemui.plugins.FalsingManager r0 = r8.falsingManager
            boolean r0 = r0.isFalseTouch(r4)
            if (r0 != 0) goto L_0x0132
            if (r9 == 0) goto L_0x0132
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mStartingChild
            if (r9 == 0) goto L_0x00eb
            boolean r0 = r9 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r0 == 0) goto L_0x00e9
            r0 = r9
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r0
            r0.setUserLocked(r2)
        L_0x00e9:
            r8.mStartingChild = r6
        L_0x00eb:
            com.android.systemui.plugins.statusbar.StatusBarStateController r0 = r8.statusBarStateController
            boolean r0 = r0.isDozing()
            if (r0 == 0) goto L_0x0114
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r0 = r8.wakeUpCoordinator
            java.util.Objects.requireNonNull(r0)
            float r3 = r0.mDozeAmount
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0100
            r3 = r1
            goto L_0x0101
        L_0x0100:
            r3 = r2
        L_0x0101:
            if (r3 != 0) goto L_0x0105
            r0.willWakeUp = r1
        L_0x0105:
            android.os.PowerManager r0 = r8.mPowerManager
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            long r3 = android.os.SystemClock.uptimeMillis()
            r5 = 4
            java.lang.String r7 = "com.android.systemui:PULSEDRAG"
            r0.wakeUp(r3, r5, r7)
        L_0x0114:
            com.android.systemui.statusbar.LockscreenShadeTransitionController r0 = r8.lockscreenShadeTransitionController
            r0.goToLockedShade(r9, r2)
            com.android.systemui.statusbar.LockscreenShadeTransitionController r9 = r8.lockscreenShadeTransitionController
            r9.finishPulseAnimation(r2)
            r8.leavingLockscreen = r1
            r8.setExpanding(r2)
            com.android.systemui.statusbar.notification.row.ExpandableView r9 = r8.mStartingChild
            boolean r0 = r9 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r0 == 0) goto L_0x0135
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r9 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r9
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            r9.onExpandedByGesture(r1)
            goto L_0x0135
        L_0x0132:
            r8.cancelExpansion()
        L_0x0135:
            android.view.VelocityTracker r9 = r8.velocityTracker
            if (r9 != 0) goto L_0x013a
            goto L_0x013d
        L_0x013a:
            r9.recycle()
        L_0x013d:
            r8.velocityTracker = r6
        L_0x013f:
            boolean r8 = r8.isExpanding
            return r8
        L_0x0142:
            boolean r8 = r8.startExpansion(r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.PulseExpansionHandler.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
