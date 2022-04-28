package com.android.systemui.wmshell;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShell$7$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$7$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v27, resolved type: java.lang.Object[]} */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [vendor.google.google_battery.V1_2.IGoogleBattery] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v4, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r9 = this;
            java.lang.String r0 = "unlinkToDeath failed: "
            java.lang.String r1 = "BatteryDefenderNotification"
            int r2 = r9.$r8$classId
            r3 = 0
            r4 = 1
            r5 = 0
            switch(r2) {
                case 0: goto L_0x0212;
                case 1: goto L_0x01b7;
                case 2: goto L_0x0132;
                case 3: goto L_0x011c;
                case 4: goto L_0x007f;
                case 5: goto L_0x0068;
                case 6: goto L_0x0055;
                case 7: goto L_0x0021;
                case 8: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0226
        L_0x000f:
            java.lang.Object r9 = r9.f$0
            com.android.wm.shell.pip.PipTaskOrganizer r9 = (com.android.p012wm.shell.pip.PipTaskOrganizer) r9
            java.util.Objects.requireNonNull(r9)
            com.android.wm.shell.ShellTaskOrganizer r0 = r9.mTaskOrganizer
            int[] r1 = new int[r4]
            r2 = -4
            r1[r3] = r2
            r0.addListenerForType(r9, r1)
            return
        L_0x0021:
            java.lang.Object r9 = r9.f$0
            com.android.wm.shell.bubbles.BubbleStackView r9 = (com.android.p012wm.shell.bubbles.BubbleStackView) r9
            int r0 = com.android.p012wm.shell.bubbles.BubbleStackView.FLYOUT_HIDE_AFTER
            java.util.Objects.requireNonNull(r9)
            com.android.wm.shell.bubbles.BubbleViewProvider r0 = r9.mExpandedBubble
            r9.mIsExpansionAnimating = r4
            r9.hideFlyoutImmediate()
            r9.updateExpandedBubble()
            r9.updateExpandedView()
            com.android.wm.shell.bubbles.ManageEducationView r1 = r9.mManageEduView
            if (r1 == 0) goto L_0x003e
            r1.hide()
        L_0x003e:
            r9.updateOverflowVisibility()
            r9.updateZOrder()
            r9.updateBadges(r4)
            r9.mIsExpansionAnimating = r3
            r9.updateExpandedView()
            r9.requestUpdate()
            if (r0 == 0) goto L_0x0054
            r0.setTaskViewVisibility()
        L_0x0054:
            return
        L_0x0055:
            java.lang.Object r9 = r9.f$0
            com.android.systemui.statusbar.phone.ScrimController r9 = (com.android.systemui.statusbar.phone.ScrimController) r9
            boolean r0 = com.android.systemui.statusbar.phone.ScrimController.DEBUG
            java.util.Objects.requireNonNull(r9)
            r9.mBlankingTransitionRunnable = r5
            r9.mPendingFrameCallback = r5
            r9.mBlankScreen = r3
            r9.updateScrims()
            return
        L_0x0068:
            java.lang.Object r9 = r9.f$0
            r0 = r9
            com.android.systemui.statusbar.phone.NotificationIconAreaController r0 = (com.android.systemui.statusbar.phone.NotificationIconAreaController) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationIconAreaController$$ExternalSyntheticLambda2 r1 = com.android.systemui.statusbar.phone.NotificationIconAreaController$$ExternalSyntheticLambda2.INSTANCE
            com.android.systemui.statusbar.phone.NotificationIconContainer r2 = r0.mNotificationIcons
            boolean r4 = r0.mShowLowPriority
            r3 = 0
            r5 = 1
            r6 = 1
            r7 = 0
            r8 = 0
            r0.updateIconsForLayout(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        L_0x007f:
            java.lang.Object r9 = r9.f$0
            com.android.systemui.statusbar.KeyguardIndicationController r9 = (com.android.systemui.statusbar.KeyguardIndicationController) r9
            java.util.Objects.requireNonNull(r9)
            android.app.admin.DevicePolicyManager r0 = r9.mDevicePolicyManager
            boolean r0 = r0.isDeviceManaged()
            if (r0 == 0) goto L_0x0095
            android.app.admin.DevicePolicyManager r0 = r9.mDevicePolicyManager
            java.lang.CharSequence r5 = r0.getDeviceOwnerOrganizationName()
            goto L_0x00cc
        L_0x0095:
            android.app.admin.DevicePolicyManager r0 = r9.mDevicePolicyManager
            boolean r0 = r0.isOrganizationOwnedDeviceWithManagedProfile()
            if (r0 == 0) goto L_0x00cc
            int r0 = android.os.UserHandle.myUserId()
            android.os.UserManager r1 = r9.mUserManager
            java.util.List r0 = r1.getProfiles(r0)
            java.util.Iterator r0 = r0.iterator()
        L_0x00ab:
            boolean r1 = r0.hasNext()
            r2 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r1 == 0) goto L_0x00c2
            java.lang.Object r1 = r0.next()
            android.content.pm.UserInfo r1 = (android.content.pm.UserInfo) r1
            boolean r6 = r1.isManagedProfile()
            if (r6 == 0) goto L_0x00ab
            int r0 = r1.id
            goto L_0x00c3
        L_0x00c2:
            r0 = r2
        L_0x00c3:
            if (r0 != r2) goto L_0x00c6
            goto L_0x00cc
        L_0x00c6:
            android.app.admin.DevicePolicyManager r1 = r9.mDevicePolicyManager
            java.lang.CharSequence r5 = r1.getOrganizationNameForUser(r0)
        L_0x00cc:
            android.content.Context r0 = r9.mContext
            android.content.res.Resources r0 = r0.getResources()
            java.lang.String r1 = "SystemUi.KEYGUARD_MANAGEMENT_DISCLOSURE"
            if (r5 != 0) goto L_0x00e2
            android.app.admin.DevicePolicyManager r2 = r9.mDevicePolicyManager
            com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda2 r3 = new com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda2
            r3.<init>(r0)
            java.lang.String r0 = r2.getString(r1, r3)
            goto L_0x0111
        L_0x00e2:
            android.app.admin.DevicePolicyManager r2 = r9.mDevicePolicyManager
            boolean r2 = r2.isDeviceManaged()
            if (r2 == 0) goto L_0x0102
            android.app.admin.DevicePolicyManager r2 = r9.mDevicePolicyManager
            android.content.ComponentName r6 = r2.getDeviceOwnerComponentOnAnyUser()
            int r2 = r2.getDeviceOwnerType(r6)
            if (r2 != r4) goto L_0x0102
            r1 = 2131952292(0x7f1302a4, float:1.9541023E38)
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r2[r3] = r5
            java.lang.String r0 = r0.getString(r1, r2)
            goto L_0x0111
        L_0x0102:
            android.app.admin.DevicePolicyManager r2 = r9.mDevicePolicyManager
            com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda3 r6 = new com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda3
            r6.<init>(r0, r5)
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r3] = r5
            java.lang.String r0 = r2.getString(r1, r6, r0)
        L_0x0111:
            com.android.systemui.util.concurrency.DelayableExecutor r1 = r9.mExecutor
            com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10 r2 = new com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10
            r2.<init>(r9, r0, r4)
            r1.execute(r2)
            return
        L_0x011c:
            java.lang.Object r9 = r9.f$0
            com.android.systemui.screenshot.ScreenshotController r9 = (com.android.systemui.screenshot.ScreenshotController) r9
            com.android.systemui.screenshot.ScreenshotController$1 r0 = com.android.systemui.screenshot.ScreenshotController.SCREENSHOT_REMOTE_RUNNER
            java.util.Objects.requireNonNull(r9)
            com.android.internal.logging.UiEventLogger r0 = r9.mUiEventLogger
            com.android.systemui.screenshot.ScreenshotEvent r1 = com.android.systemui.screenshot.ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT
            java.lang.String r2 = r9.mPackageName
            r0.log(r1, r3, r2)
            r9.dismissScreenshot()
            return
        L_0x0132:
            java.lang.Object r9 = r9.f$0
            com.android.systemui.power.PowerNotificationWarnings r9 = (com.android.systemui.power.PowerNotificationWarnings) r9
            boolean r0 = com.android.systemui.power.PowerNotificationWarnings.DEBUG
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.phone.SystemUIDialog r0 = r9.mUsbHighTempDialog
            if (r0 == 0) goto L_0x0140
            goto L_0x01b6
        L_0x0140:
            com.android.systemui.statusbar.phone.SystemUIDialog r0 = new com.android.systemui.statusbar.phone.SystemUIDialog
            android.content.Context r1 = r9.mContext
            r2 = 2132018184(0x7f140408, float:1.9674667E38)
            r0.<init>(r1, r2)
            r0.setCancelable(r3)
            r1 = 16843605(0x1010355, float:2.369595E-38)
            r0.setIconAttribute(r1)
            r1 = 2131952441(0x7f130339, float:1.9541325E38)
            r0.setTitle(r1)
            com.android.systemui.statusbar.phone.SystemUIDialog.setShowForAllUsers(r0)
            android.content.Context r1 = r9.mContext
            r2 = 2131952440(0x7f130338, float:1.9541323E38)
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r6 = ""
            r5[r3] = r6
            java.lang.String r1 = r1.getString(r2, r5)
            r0.setMessage(r1)
            r1 = 17039370(0x104000a, float:2.42446E-38)
            com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda0 r2 = new com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda0
            r2.<init>(r9)
            r0.setPositiveButton(r1, r2)
            r1 = 2131952438(0x7f130336, float:1.9541319E38)
            com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda2 r2 = new com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda2
            r2.<init>(r9)
            r0.setNegativeButton(r1, r2)
            com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda7 r1 = new com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda7
            r1.<init>(r9)
            r0.setOnDismissListener(r1)
            android.view.Window r1 = r0.getWindow()
            r2 = 2097280(0x200080, float:2.938915E-39)
            r1.addFlags(r2)
            r0.show()
            r9.mUsbHighTempDialog = r0
            r0 = 19
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 3
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1[r3] = r2
            android.app.KeyguardManager r9 = r9.mKeyguard
            boolean r9 = r9.isKeyguardLocked()
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            r1[r4] = r9
            com.android.systemui.volume.Events.writeEvent(r0, r1)
        L_0x01b6:
            return
        L_0x01b7:
            java.lang.Object r9 = r9.f$0
            com.android.settingslib.applications.ApplicationsState$BackgroundHandler r9 = (com.android.settingslib.applications.ApplicationsState.BackgroundHandler) r9
            int r0 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.$r8$clinit
            java.util.Objects.requireNonNull(r9)
            com.android.settingslib.applications.ApplicationsState r0 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            android.app.usage.StorageStatsManager r1 = r0.mStats     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            java.util.UUID r2 = r0.mCurComputingSizeUuid     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            java.lang.String r3 = r0.mCurComputingSizePkg     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            int r0 = r0.mCurComputingSizeUserId     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            android.os.UserHandle r0 = android.os.UserHandle.of(r0)     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            android.app.usage.StorageStats r0 = r1.queryStatsForPackage(r2, r3, r0)     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            android.content.pm.PackageStats r1 = new android.content.pm.PackageStats     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            java.lang.String r3 = r2.mCurComputingSizePkg     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            int r2 = r2.mCurComputingSizeUserId     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            r1.<init>(r3, r2)     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            long r2 = r0.getAppBytes()     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            r1.codeSize = r2     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            long r2 = r0.getDataBytes()     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            r1.dataSize = r2     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            long r2 = r0.getCacheBytes()     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            r1.cacheSize = r2     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            com.android.settingslib.applications.ApplicationsState$BackgroundHandler$1 r0 = r9.mStatsObserver     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            r0.onGetStatsCompleted(r1, r4)     // Catch:{ NameNotFoundException | IOException -> 0x01f5 }
            goto L_0x0211
        L_0x01f5:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to query stats: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "ApplicationsState"
            android.util.Log.w(r1, r0)
            com.android.settingslib.applications.ApplicationsState$BackgroundHandler$1 r9 = r9.mStatsObserver     // Catch:{ RemoteException -> 0x0211 }
            java.util.Objects.requireNonNull(r9)     // Catch:{ RemoteException -> 0x0211 }
        L_0x0211:
            return
        L_0x0212:
            java.lang.Object r9 = r9.f$0
            com.android.systemui.wmshell.WMShell$7 r9 = (com.android.systemui.wmshell.WMShell.C17707) r9
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.wmshell.WMShell r9 = com.android.systemui.wmshell.WMShell.this
            com.android.systemui.model.SysUiState r9 = r9.mSysUiState
            r0 = 65536(0x10000, float:9.18355E-41)
            r9.setFlag(r0, r4)
            r9.commitUpdate(r3)
            return
        L_0x0226:
            java.lang.Object r9 = r9.f$0
            com.google.android.systemui.power.BatteryDefenderNotification r9 = (com.google.android.systemui.power.BatteryDefenderNotification) r9
            java.util.Objects.requireNonNull(r9)
            com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0 r9 = com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0.INSTANCE
            vendor.google.google_battery.V1_2.IGoogleBattery r2 = vendor.google.google_battery.V1_2.IGoogleBattery.getService()     // Catch:{ RemoteException | NoSuchElementException -> 0x023a }
            if (r2 == 0) goto L_0x0238
            r2.linkToDeath(r9)     // Catch:{ RemoteException | NoSuchElementException -> 0x023a }
        L_0x0238:
            r5 = r2
            goto L_0x0240
        L_0x023a:
            r9 = move-exception
            java.lang.String r2 = "failed to get Google Battery HAL: "
            android.util.Log.e(r1, r2, r9)
        L_0x0240:
            if (r5 != 0) goto L_0x0247
            java.lang.String r9 = "Can not init hal interface"
            android.util.Log.d(r1, r9)
        L_0x0247:
            r5.clearBatteryDefender()     // Catch:{ RemoteException -> 0x024d }
            goto L_0x0263
        L_0x024b:
            r9 = move-exception
            goto L_0x026e
        L_0x024d:
            r9 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x024b }
            r2.<init>()     // Catch:{ all -> 0x024b }
            java.lang.String r3 = "setProperty error: "
            r2.append(r3)     // Catch:{ all -> 0x024b }
            r2.append(r9)     // Catch:{ all -> 0x024b }
            java.lang.String r9 = r2.toString()     // Catch:{ all -> 0x024b }
            android.util.Log.e(r1, r9)     // Catch:{ all -> 0x024b }
        L_0x0263:
            com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0 r9 = com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0.INSTANCE
            r5.unlinkToDeath(r9)     // Catch:{ RemoteException -> 0x0269 }
            goto L_0x026d
        L_0x0269:
            r9 = move-exception
            android.util.Log.e(r1, r0, r9)
        L_0x026d:
            return
        L_0x026e:
            com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0 r2 = com.google.android.systemui.power.BatteryDefenderNotification$$ExternalSyntheticLambda0.INSTANCE
            r5.unlinkToDeath(r2)     // Catch:{ RemoteException -> 0x0274 }
            goto L_0x0278
        L_0x0274:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x0278:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0.run():void");
    }
}
