package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.service.dreams.IDreamManager;
import android.util.EventLog;
import androidx.coordinatorlayout.widget.DirectedAcyclicGraph;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import dagger.Lazy;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class StatusBarNotificationActivityStarter implements NotificationActivityStarter {
    public final ActivityIntentHelper mActivityIntentHelper;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final ActivityStarter mActivityStarter;
    public final Lazy<AssistManager> mAssistManagerLazy;
    public final Optional<BubblesManager> mBubblesManagerOptional;
    public final NotificationClickNotifier mClickNotifier;
    public final Context mContext;
    public final IDreamManager mDreamManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public boolean mIsCollapsingToShowActivityOverLockscreen;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final StatusBarNotificationActivityStarterLogger mLogger;
    public final Handler mMainThreadHandler;
    public final MetricsLogger mMetricsLogger;
    public final DirectedAcyclicGraph mNotificationAnimationProvider;
    public final NotificationInterruptStateProvider mNotificationInterruptStateProvider;
    public final NotificationPanelViewController mNotificationPanel;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public final NotificationPresenter mPresenter;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final ShadeController mShadeController;
    public final StatusBar mStatusBar;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarRemoteInputCallback mStatusBarRemoteInputCallback;
    public final StatusBarStateController mStatusBarStateController;
    public final Executor mUiBgExecutor;
    public final NotificationVisibilityProvider mVisibilityProvider;

    public static class Builder {
        public final ActivityIntentHelper mActivityIntentHelper;
        public final ActivityStarter mActivityStarter;
        public final Lazy<AssistManager> mAssistManagerLazy;
        public final Optional<BubblesManager> mBubblesManagerOptional;
        public final NotificationClickNotifier mClickNotifier;
        public final CommandQueue mCommandQueue;
        public final Context mContext;
        public final IDreamManager mDreamManager;
        public final NotificationEntryManager mEntryManager;
        public final GroupMembershipManager mGroupMembershipManager;
        public final HeadsUpManagerPhone mHeadsUpManager;
        public final KeyguardManager mKeyguardManager;
        public final KeyguardStateController mKeyguardStateController;
        public final LockPatternUtils mLockPatternUtils;
        public final NotificationLockscreenUserManager mLockscreenUserManager;
        public final StatusBarNotificationActivityStarterLogger mLogger;
        public final Handler mMainThreadHandler;
        public final MetricsLogger mMetricsLogger;
        public final NotifPipeline mNotifPipeline;
        public final NotifPipelineFlags mNotifPipelineFlags;
        public final NotificationInterruptStateProvider mNotificationInterruptStateProvider;
        public final OnUserInteractionCallback mOnUserInteractionCallback;
        public final StatusBarRemoteInputCallback mRemoteInputCallback;
        public final NotificationRemoteInputManager mRemoteInputManager;
        public final ShadeController mShadeController;
        public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
        public final StatusBarStateController mStatusBarStateController;
        public final Executor mUiBgExecutor;
        public final NotificationVisibilityProvider mVisibilityProvider;

        public Builder(Context context, CommandQueue commandQueue, Handler handler, Executor executor, NotificationEntryManager notificationEntryManager, NotifPipeline notifPipeline, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional<BubblesManager> optional, Lazy<AssistManager> lazy, NotificationRemoteInputManager notificationRemoteInputManager, GroupMembershipManager groupMembershipManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, NotificationInterruptStateProvider notificationInterruptStateProvider, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, NotifPipelineFlags notifPipelineFlags, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback) {
            this.mContext = context;
            this.mCommandQueue = commandQueue;
            this.mMainThreadHandler = handler;
            this.mUiBgExecutor = executor;
            this.mEntryManager = notificationEntryManager;
            this.mNotifPipeline = notifPipeline;
            this.mVisibilityProvider = notificationVisibilityProvider;
            this.mHeadsUpManager = headsUpManagerPhone;
            this.mActivityStarter = activityStarter;
            this.mClickNotifier = notificationClickNotifier;
            this.mStatusBarStateController = statusBarStateController;
            this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
            this.mKeyguardManager = keyguardManager;
            this.mDreamManager = iDreamManager;
            this.mBubblesManagerOptional = optional;
            this.mAssistManagerLazy = lazy;
            this.mRemoteInputManager = notificationRemoteInputManager;
            this.mGroupMembershipManager = groupMembershipManager;
            this.mLockscreenUserManager = notificationLockscreenUserManager;
            this.mShadeController = shadeController;
            this.mKeyguardStateController = keyguardStateController;
            this.mNotificationInterruptStateProvider = notificationInterruptStateProvider;
            this.mLockPatternUtils = lockPatternUtils;
            this.mRemoteInputCallback = statusBarRemoteInputCallback;
            this.mActivityIntentHelper = activityIntentHelper;
            this.mNotifPipelineFlags = notifPipelineFlags;
            this.mMetricsLogger = metricsLogger;
            this.mLogger = statusBarNotificationActivityStarterLogger;
            this.mOnUserInteractionCallback = onUserInteractionCallback;
        }
    }

    public StatusBarNotificationActivityStarter() {
        throw null;
    }

    public StatusBarNotificationActivityStarter(Context context, Handler handler, Executor executor, NotificationEntryManager notificationEntryManager, NotifPipeline notifPipeline, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional optional, Lazy lazy, NotificationRemoteInputManager notificationRemoteInputManager, GroupMembershipManager groupMembershipManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, NotificationInterruptStateProvider notificationInterruptStateProvider, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, NotifPipelineFlags notifPipelineFlags, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback, StatusBar statusBar, NotificationPresenter notificationPresenter, NotificationPanelViewController notificationPanelViewController, ActivityLaunchAnimator activityLaunchAnimator, DirectedAcyclicGraph directedAcyclicGraph) {
        this.mContext = context;
        this.mMainThreadHandler = handler;
        this.mUiBgExecutor = executor;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mActivityStarter = activityStarter;
        this.mClickNotifier = notificationClickNotifier;
        this.mStatusBarStateController = statusBarStateController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardManager = keyguardManager;
        this.mDreamManager = iDreamManager;
        this.mBubblesManagerOptional = optional;
        this.mAssistManagerLazy = lazy;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mShadeController = shadeController;
        this.mKeyguardStateController = keyguardStateController;
        this.mNotificationInterruptStateProvider = notificationInterruptStateProvider;
        this.mLockPatternUtils = lockPatternUtils;
        this.mStatusBarRemoteInputCallback = statusBarRemoteInputCallback;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mMetricsLogger = metricsLogger;
        this.mLogger = statusBarNotificationActivityStarterLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mStatusBar = statusBar;
        this.mPresenter = notificationPresenter;
        this.mNotificationPanel = notificationPanelViewController;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        this.mNotificationAnimationProvider = directedAcyclicGraph;
        if (!notifPipelineFlags.isNewPipelineEnabled()) {
            NotificationEntryManager notificationEntryManager2 = notificationEntryManager;
            notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener() {
                public final void onPendingEntryAdded(NotificationEntry notificationEntry) {
                    StatusBarNotificationActivityStarter.this.handleFullScreenIntent(notificationEntry);
                }
            });
            return;
        }
        NotifPipeline notifPipeline2 = notifPipeline;
        notifPipeline.addCollectionListener(new NotifCollectionListener() {
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                StatusBarNotificationActivityStarter.this.handleFullScreenIntent(notificationEntry);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x011a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onNotificationClicked(android.service.notification.StatusBarNotification r15, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r16) {
        /*
            r14 = this;
            r8 = r14
            r3 = r16
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r0 = r8.mLogger
            java.lang.String r1 = r15.getKey()
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.log.LogBuffer r0 = r0.buffer
            com.android.systemui.log.LogLevel r2 = com.android.systemui.log.LogLevel.DEBUG
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartingActivityFromClick$2 r4 = com.android.systemui.statusbar.phone.C1578xbe0e6f79.INSTANCE
            java.util.Objects.requireNonNull(r0)
            boolean r5 = r0.frozen
            java.lang.String r6 = "NotifActivityStarter"
            if (r5 != 0) goto L_0x0024
            com.android.systemui.log.LogMessageImpl r2 = r0.obtain(r6, r2, r4)
            r2.str1 = r1
            r0.push(r2)
        L_0x0024:
            java.util.Objects.requireNonNull(r16)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r3.mEntry
            com.android.systemui.statusbar.NotificationRemoteInputManager r0 = r8.mRemoteInputManager
            boolean r0 = r0.isRemoteInputActive(r2)
            r9 = 0
            if (r0 == 0) goto L_0x0066
            com.android.systemui.statusbar.notification.row.NotificationContentView r0 = r3.mPrivateLayout
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.policy.RemoteInputView r1 = r0.mExpandedRemoteInput
            if (r1 == 0) goto L_0x0048
            boolean r1 = r1.isActive()
            if (r1 == 0) goto L_0x0048
            com.android.systemui.statusbar.policy.RemoteInputView r0 = r0.mExpandedRemoteInput
            android.text.Editable r0 = r0.getText()
            goto L_0x005a
        L_0x0048:
            com.android.systemui.statusbar.policy.RemoteInputView r1 = r0.mHeadsUpRemoteInput
            if (r1 == 0) goto L_0x0059
            boolean r1 = r1.isActive()
            if (r1 == 0) goto L_0x0059
            com.android.systemui.statusbar.policy.RemoteInputView r0 = r0.mHeadsUpRemoteInput
            android.text.Editable r0 = r0.getText()
            goto L_0x005a
        L_0x0059:
            r0 = r9
        L_0x005a:
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0066
            com.android.systemui.statusbar.NotificationRemoteInputManager r0 = r8.mRemoteInputManager
            r0.closeRemoteInputs()
            return
        L_0x0066:
            android.app.Notification r0 = r15.getNotification()
            android.app.PendingIntent r1 = r0.contentIntent
            if (r1 == 0) goto L_0x0070
            r4 = r1
            goto L_0x0073
        L_0x0070:
            android.app.PendingIntent r0 = r0.fullScreenIntent
            r4 = r0
        L_0x0073:
            boolean r0 = r2.isBubble()
            if (r4 != 0) goto L_0x009b
            if (r0 != 0) goto L_0x009b
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r0 = r8.mLogger
            java.lang.String r1 = r15.getKey()
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.log.LogBuffer r0 = r0.buffer
            com.android.systemui.log.LogLevel r2 = com.android.systemui.log.LogLevel.ERROR
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logNonClickableNotification$2 r3 = com.android.systemui.statusbar.phone.C1574x823c33d2.INSTANCE
            java.util.Objects.requireNonNull(r0)
            boolean r4 = r0.frozen
            if (r4 != 0) goto L_0x009a
            com.android.systemui.log.LogMessageImpl r2 = r0.obtain(r6, r2, r3)
            r2.str1 = r1
            r0.push(r2)
        L_0x009a:
            return
        L_0x009b:
            r1 = 0
            r10 = 1
            if (r4 == 0) goto L_0x00a9
            boolean r5 = r4.isActivity()
            if (r5 == 0) goto L_0x00a9
            if (r0 != 0) goto L_0x00a9
            r5 = r10
            goto L_0x00aa
        L_0x00a9:
            r5 = r1
        L_0x00aa:
            if (r5 == 0) goto L_0x00c8
            com.android.systemui.ActivityIntentHelper r0 = r8.mActivityIntentHelper
            android.content.Intent r6 = r4.getIntent()
            com.android.systemui.statusbar.NotificationLockscreenUserManager r7 = r8.mLockscreenUserManager
            int r7 = r7.getCurrentUserId()
            java.util.Objects.requireNonNull(r0)
            android.content.pm.ActivityInfo r0 = r0.getTargetActivityInfo(r6, r7, r1)
            if (r0 != 0) goto L_0x00c3
            r0 = r10
            goto L_0x00c4
        L_0x00c3:
            r0 = r1
        L_0x00c4:
            if (r0 == 0) goto L_0x00c8
            r11 = r10
            goto L_0x00c9
        L_0x00c8:
            r11 = r1
        L_0x00c9:
            if (r11 != 0) goto L_0x00d8
            com.android.systemui.statusbar.phone.StatusBar r0 = r8.mStatusBar
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.shouldAnimateLaunch(r5, r1)
            if (r0 == 0) goto L_0x00d8
            r6 = r10
            goto L_0x00d9
        L_0x00d8:
            r6 = r1
        L_0x00d9:
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r8.mKeyguardStateController
            boolean r0 = r0.isShowing()
            if (r0 == 0) goto L_0x0107
            if (r4 == 0) goto L_0x0107
            com.android.systemui.ActivityIntentHelper r0 = r8.mActivityIntentHelper
            android.content.Intent r7 = r4.getIntent()
            com.android.systemui.statusbar.NotificationLockscreenUserManager r12 = r8.mLockscreenUserManager
            int r12 = r12.getCurrentUserId()
            java.util.Objects.requireNonNull(r0)
            android.content.pm.ActivityInfo r0 = r0.getTargetActivityInfo(r7, r12, r1)
            if (r0 == 0) goto L_0x0102
            int r0 = r0.flags
            r7 = 8389632(0x800400, float:1.1756378E-38)
            r0 = r0 & r7
            if (r0 <= 0) goto L_0x0102
            r0 = r10
            goto L_0x0103
        L_0x0102:
            r0 = r1
        L_0x0103:
            if (r0 == 0) goto L_0x0107
            r12 = r10
            goto L_0x0108
        L_0x0107:
            r12 = r1
        L_0x0108:
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3 r13 = new com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$3
            r0 = r13
            r1 = r14
            r3 = r16
            r7 = r12
            r0.<init>(r2, r3, r4, r5, r6, r7)
            if (r12 == 0) goto L_0x011a
            r8.mIsCollapsingToShowActivityOverLockscreen = r10
            r13.onDismiss()
            goto L_0x011f
        L_0x011a:
            com.android.systemui.plugins.ActivityStarter r0 = r8.mActivityStarter
            r0.dismissKeyguardThenExecute(r13, r9, r11)
        L_0x011f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.onNotificationClicked(android.service.notification.StatusBarNotification, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow):void");
    }

    public void handleFullScreenIntent(NotificationEntry notificationEntry) {
        boolean z;
        LogLevel logLevel = LogLevel.DEBUG;
        if (this.mNotificationInterruptStateProvider.shouldLaunchFullScreenIntentWhenAdded(notificationEntry)) {
            StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) this.mPresenter;
            Objects.requireNonNull(statusBarNotificationPresenter);
            if (statusBarNotificationPresenter.mVrMode) {
                z = true;
            } else {
                Objects.requireNonNull(notificationEntry);
                z = notificationEntry.shouldSuppressVisualEffect(4);
            }
            if (z) {
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger = this.mLogger;
                Objects.requireNonNull(notificationEntry);
                String str = notificationEntry.mKey;
                Objects.requireNonNull(statusBarNotificationActivityStarterLogger);
                LogBuffer logBuffer = statusBarNotificationActivityStarterLogger.buffer;
                C1571x7a36b302 statusBarNotificationActivityStarterLogger$logFullScreenIntentSuppressedByDnD$2 = C1571x7a36b302.INSTANCE;
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    LogMessageImpl obtain = logBuffer.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$logFullScreenIntentSuppressedByDnD$2);
                    obtain.str1 = str;
                    logBuffer.push(obtain);
                }
            } else if (notificationEntry.getImportance() < 4) {
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger2 = this.mLogger;
                String str2 = notificationEntry.mKey;
                Objects.requireNonNull(statusBarNotificationActivityStarterLogger2);
                LogBuffer logBuffer2 = statusBarNotificationActivityStarterLogger2.buffer;
                C1570x141720c8 statusBarNotificationActivityStarterLogger$logFullScreenIntentNotImportantEnough$2 = C1570x141720c8.INSTANCE;
                Objects.requireNonNull(logBuffer2);
                if (!logBuffer2.frozen) {
                    LogMessageImpl obtain2 = logBuffer2.obtain("NotifActivityStarter", logLevel, statusBarNotificationActivityStarterLogger$logFullScreenIntentNotImportantEnough$2);
                    obtain2.str1 = str2;
                    logBuffer2.push(obtain2);
                }
            } else {
                this.mUiBgExecutor.execute(new WifiEntry$$ExternalSyntheticLambda2(this, 8));
                PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().fullScreenIntent;
                StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger3 = this.mLogger;
                String str3 = notificationEntry.mKey;
                Objects.requireNonNull(statusBarNotificationActivityStarterLogger3);
                LogBuffer logBuffer3 = statusBarNotificationActivityStarterLogger3.buffer;
                LogLevel logLevel2 = LogLevel.INFO;
                C1575x57d5767b statusBarNotificationActivityStarterLogger$logSendingFullScreenIntent$2 = C1575x57d5767b.INSTANCE;
                Objects.requireNonNull(logBuffer3);
                if (!logBuffer3.frozen) {
                    LogMessageImpl obtain3 = logBuffer3.obtain("NotifActivityStarter", logLevel2, statusBarNotificationActivityStarterLogger$logSendingFullScreenIntent$2);
                    obtain3.str1 = str3;
                    obtain3.str2 = pendingIntent.getIntent().toString();
                    logBuffer3.push(obtain3);
                }
                try {
                    EventLog.writeEvent(36002, notificationEntry.mKey);
                    this.mStatusBar.wakeUpForFullScreenIntent();
                    pendingIntent.send();
                    notificationEntry.interruption = true;
                    notificationEntry.lastFullScreenIntentLaunchTime = SystemClock.elapsedRealtime();
                    this.mMetricsLogger.count("note_fullscreen", 1);
                } catch (PendingIntent.CanceledException unused) {
                }
            }
        }
    }

    public final void removeHunAfterClick(ExpandableNotificationRow expandableNotificationRow) {
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        String key = notificationEntry.mSbn.getKey();
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if (headsUpManagerPhone != null && headsUpManagerPhone.isAlerting(key)) {
            if (((StatusBarNotificationPresenter) this.mPresenter).isPresenterFullyCollapsed()) {
                expandableNotificationRow.setTag(C1777R.C1779id.is_clicked_heads_up_tag, Boolean.TRUE);
            }
            this.mHeadsUpManager.removeNotification(key, true);
        }
    }
}
