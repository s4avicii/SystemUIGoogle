package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ StatusBarNotificationActivityStarter f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ ExpandableNotificationRow f$2;
    public final /* synthetic */ PendingIntent f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ boolean f$5;

    public /* synthetic */ StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3(StatusBarNotificationActivityStarter statusBarNotificationActivityStarter, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, PendingIntent pendingIntent, boolean z, boolean z2) {
        this.f$0 = statusBarNotificationActivityStarter;
        this.f$1 = notificationEntry;
        this.f$2 = expandableNotificationRow;
        this.f$3 = pendingIntent;
        this.f$4 = z;
        this.f$5 = z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01d0, code lost:
        if (r15 != false) goto L_0x01d2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r15 = this;
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter r0 = r15.f$0
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r15.f$1
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = r15.f$2
            android.app.PendingIntent r3 = r15.f$3
            boolean r4 = r15.f$4
            boolean r15 = r15.f$5
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.log.LogLevel r5 = com.android.systemui.log.LogLevel.DEBUG
            java.util.Objects.requireNonNull(r1)
            java.lang.String r6 = r1.mKey
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r7 = r0.mLogger
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.log.LogBuffer r7 = r7.buffer
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2 r8 = com.android.systemui.statusbar.phone.C1573xe49d9e41.INSTANCE
            java.util.Objects.requireNonNull(r7)
            boolean r9 = r7.frozen
            java.lang.String r10 = "NotifActivityStarter"
            if (r9 != 0) goto L_0x0031
            com.android.systemui.log.LogMessageImpl r8 = r7.obtain(r10, r5, r8)
            r8.str1 = r6
            r7.push(r8)
        L_0x0031:
            android.app.IActivityManager r7 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x0038 }
            r7.resumeAppSwitches()     // Catch:{ RemoteException -> 0x0038 }
        L_0x0038:
            if (r4 == 0) goto L_0x0084
            android.os.UserHandle r7 = r3.getCreatorUserHandle()
            int r7 = r7.getIdentifier()
            com.android.internal.widget.LockPatternUtils r8 = r0.mLockPatternUtils
            boolean r8 = r8.isSeparateProfileChallengeEnabled(r7)
            if (r8 == 0) goto L_0x0084
            android.app.KeyguardManager r8 = r0.mKeyguardManager
            boolean r8 = r8.isDeviceLocked(r7)
            if (r8 == 0) goto L_0x0084
            com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback r8 = r0.mStatusBarRemoteInputCallback
            android.content.IntentSender r9 = r3.getIntentSender()
            boolean r7 = r8.startWorkChallengeIfNecessary(r7, r9, r6)
            if (r7 == 0) goto L_0x0084
            r0.removeHunAfterClick(r2)
            android.os.Looper r15 = android.os.Looper.getMainLooper()
            boolean r15 = r15.isCurrentThread()
            if (r15 == 0) goto L_0x0072
            com.android.systemui.statusbar.phone.ShadeController r15 = r0.mShadeController
            r15.collapsePanel()
            goto L_0x01de
        L_0x0072:
            android.os.Handler r15 = r0.mMainThreadHandler
            com.android.systemui.statusbar.phone.ShadeController r0 = r0.mShadeController
            java.util.Objects.requireNonNull(r0)
            com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1 r1 = new com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1
            r2 = 4
            r1.<init>(r0, r2)
            r15.post(r1)
            goto L_0x01de
        L_0x0084:
            java.lang.CharSequence r7 = r1.remoteInputText
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            r8 = 0
            if (r7 != 0) goto L_0x0090
            java.lang.CharSequence r7 = r1.remoteInputText
            goto L_0x0091
        L_0x0090:
            r7 = r8
        L_0x0091:
            boolean r9 = android.text.TextUtils.isEmpty(r7)
            if (r9 != 0) goto L_0x00af
            com.android.systemui.statusbar.NotificationRemoteInputManager r9 = r0.mRemoteInputManager
            boolean r9 = r9.isSpinning(r6)
            if (r9 != 0) goto L_0x00af
            android.content.Intent r9 = new android.content.Intent
            r9.<init>()
            java.lang.String r7 = r7.toString()
            java.lang.String r11 = "android.remoteInputDraft"
            android.content.Intent r7 = r9.putExtra(r11, r7)
            goto L_0x00b0
        L_0x00af:
            r7 = r8
        L_0x00b0:
            android.service.notification.NotificationListenerService$Ranking r9 = r1.mRanking
            boolean r9 = r9.canBubble()
            if (r9 == 0) goto L_0x010e
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r15 = r0.mLogger
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.log.LogBuffer r15 = r15.buffer
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2 r3 = com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logExpandingBubble$2.INSTANCE
            java.util.Objects.requireNonNull(r15)
            boolean r7 = r15.frozen
            if (r7 != 0) goto L_0x00d1
            com.android.systemui.log.LogMessageImpl r3 = r15.obtain(r10, r5, r3)
            r3.str1 = r6
            r15.push(r3)
        L_0x00d1:
            r0.removeHunAfterClick(r2)
            java.util.Optional<com.android.systemui.wmshell.BubblesManager> r15 = r0.mBubblesManagerOptional
            boolean r15 = r15.isPresent()
            if (r15 != 0) goto L_0x00de
            goto L_0x0171
        L_0x00de:
            android.os.Looper r15 = android.os.Looper.getMainLooper()
            boolean r15 = r15.isCurrentThread()
            if (r15 == 0) goto L_0x0102
            java.util.Optional<com.android.systemui.wmshell.BubblesManager> r15 = r0.mBubblesManagerOptional
            java.lang.Object r15 = r15.get()
            com.android.systemui.wmshell.BubblesManager r15 = (com.android.systemui.wmshell.BubblesManager) r15
            java.util.Objects.requireNonNull(r15)
            com.android.wm.shell.bubbles.Bubbles r15 = r15.mBubbles
            com.android.wm.shell.bubbles.BubbleEntry r2 = com.android.systemui.wmshell.BubblesManager.notifToBubbleEntry(r1)
            r15.expandStackAndSelectBubble((com.android.p012wm.shell.bubbles.BubbleEntry) r2)
            com.android.systemui.statusbar.phone.ShadeController r15 = r0.mShadeController
            r15.collapsePanel()
            goto L_0x0171
        L_0x0102:
            android.os.Handler r15 = r0.mMainThreadHandler
            com.android.wm.shell.TaskView$$ExternalSyntheticLambda7 r2 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda7
            r3 = 2
            r2.<init>(r0, r1, r3)
            r15.post(r2)
            goto L_0x0171
        L_0x010e:
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r5 = r0.mLogger
            java.lang.String r11 = r1.mKey
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.log.LogBuffer r5 = r5.buffer
            com.android.systemui.log.LogLevel r12 = com.android.systemui.log.LogLevel.INFO
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2 r13 = com.android.systemui.statusbar.phone.C1577x1850e613.INSTANCE
            java.util.Objects.requireNonNull(r5)
            boolean r14 = r5.frozen
            if (r14 != 0) goto L_0x0135
            com.android.systemui.log.LogMessageImpl r12 = r5.obtain(r10, r12, r13)
            r12.str1 = r11
            android.content.Intent r11 = r3.getIntent()
            java.lang.String r11 = r11.toString()
            r12.str2 = r11
            r5.push(r12)
        L_0x0135:
            com.android.systemui.statusbar.phone.StatusBarLaunchAnimatorController r5 = new com.android.systemui.statusbar.phone.StatusBarLaunchAnimatorController     // Catch:{ CanceledException -> 0x0151 }
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r11 = r0.mNotificationAnimationProvider     // Catch:{ CanceledException -> 0x0151 }
            com.android.systemui.statusbar.notification.NotificationLaunchAnimatorController r11 = r11.getAnimatorController(r2)     // Catch:{ CanceledException -> 0x0151 }
            com.android.systemui.statusbar.phone.StatusBar r12 = r0.mStatusBar     // Catch:{ CanceledException -> 0x0151 }
            r5.<init>(r11, r12, r4)     // Catch:{ CanceledException -> 0x0151 }
            com.android.systemui.animation.ActivityLaunchAnimator r11 = r0.mActivityLaunchAnimator     // Catch:{ CanceledException -> 0x0151 }
            java.lang.String r12 = r3.getCreatorPackage()     // Catch:{ CanceledException -> 0x0151 }
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0 r13 = new com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0     // Catch:{ CanceledException -> 0x0151 }
            r13.<init>(r0, r2, r3, r7)     // Catch:{ CanceledException -> 0x0151 }
            r11.startPendingIntentWithAnimation(r5, r15, r12, r13)     // Catch:{ CanceledException -> 0x0151 }
            goto L_0x0171
        L_0x0151:
            r15 = move-exception
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger r2 = r0.mLogger
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.log.LogBuffer r2 = r2.buffer
            com.android.systemui.log.LogLevel r3 = com.android.systemui.log.LogLevel.WARNING
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2 r5 = com.android.systemui.statusbar.phone.C1576x40a889d.INSTANCE
            java.util.Objects.requireNonNull(r2)
            boolean r7 = r2.frozen
            if (r7 != 0) goto L_0x0171
            com.android.systemui.log.LogMessageImpl r3 = r2.obtain(r10, r3, r5)
            java.lang.String r15 = r15.toString()
            r3.str1 = r15
            r2.push(r3)
        L_0x0171:
            if (r4 != 0) goto L_0x0175
            if (r9 == 0) goto L_0x0180
        L_0x0175:
            dagger.Lazy<com.android.systemui.assist.AssistManager> r15 = r0.mAssistManagerLazy
            java.lang.Object r15 = r15.get()
            com.android.systemui.assist.AssistManager r15 = (com.android.systemui.assist.AssistManager) r15
            r15.hideAssist()
        L_0x0180:
            com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r15 = r0.mVisibilityProvider
            r2 = 1
            com.android.internal.statusbar.NotificationVisibility r15 = r15.obtain(r1, r2)
            android.service.notification.StatusBarNotification r3 = r1.mSbn
            android.app.Notification r3 = r3.getNotification()
            int r3 = r3.flags
            r4 = r3 & 16
            r5 = 16
            r7 = 0
            if (r4 == r5) goto L_0x0198
        L_0x0196:
            r3 = r7
            goto L_0x019e
        L_0x0198:
            r3 = r3 & 64
            if (r3 == 0) goto L_0x019d
            goto L_0x0196
        L_0x019d:
            r3 = r2
        L_0x019e:
            if (r3 == 0) goto L_0x01a6
            com.android.systemui.statusbar.notification.row.OnUserInteractionCallback r4 = r0.mOnUserInteractionCallback
            com.android.systemui.statusbar.notification.collection.NotificationEntry r8 = r4.getGroupSummaryToDismiss(r1)
        L_0x01a6:
            com.android.systemui.statusbar.NotificationClickNotifier r4 = r0.mClickNotifier
            java.util.Objects.requireNonNull(r4)
            com.android.internal.statusbar.IStatusBarService r5 = r4.barService     // Catch:{ RemoteException -> 0x01b0 }
            r5.onNotificationClick(r6, r15)     // Catch:{ RemoteException -> 0x01b0 }
        L_0x01b0:
            java.util.concurrent.Executor r15 = r4.mainExecutor
            com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1 r5 = new com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1
            r5.<init>(r4, r6)
            r15.execute(r5)
            if (r9 != 0) goto L_0x01dc
            if (r3 != 0) goto L_0x01d2
            com.android.systemui.statusbar.NotificationRemoteInputManager r15 = r0.mRemoteInputManager
            java.util.Objects.requireNonNull(r15)
            com.android.systemui.statusbar.NotificationRemoteInputManager$RemoteInputListener r15 = r15.mRemoteInputListener
            if (r15 == 0) goto L_0x01cf
            boolean r15 = r15.isNotificationKeptForRemoteInputHistory(r6)
            if (r15 == 0) goto L_0x01cf
            r15 = r2
            goto L_0x01d0
        L_0x01cf:
            r15 = r7
        L_0x01d0:
            if (r15 == 0) goto L_0x01dc
        L_0x01d2:
            android.os.Handler r15 = r0.mMainThreadHandler
            com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda1 r3 = new com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda1
            r3.<init>(r0, r1, r8, r2)
            r15.post(r3)
        L_0x01dc:
            r0.mIsCollapsingToShowActivityOverLockscreen = r7
        L_0x01de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3.run():void");
    }
}
