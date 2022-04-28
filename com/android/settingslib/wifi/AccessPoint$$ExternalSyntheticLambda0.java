package com.android.settingslib.wifi;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessPoint$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessPoint$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r0 = r4.retrieveFontInfo();
        java.util.Objects.requireNonNull(r0);
        r3 = r0.mResultCode;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c7, code lost:
        if (r3 != 2) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c9, code lost:
        r1 = r4.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cb, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d1, code lost:
        if (r3 != 0) goto L_0x012c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        android.os.Trace.beginSection("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
        r1 = r4.mFontProviderHelper;
        r3 = r4.mContext;
        java.util.Objects.requireNonNull(r1);
        r1 = androidx.core.graphics.TypefaceCompat.createFromFontInfo(r3, new androidx.core.provider.FontsContractCompat$FontInfo[]{r0}, 0);
        r0 = androidx.core.graphics.TypefaceCompatUtil.mmap(r4.mContext, r0.mUri);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f0, code lost:
        if (r0 == null) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f2, code lost:
        if (r1 == null) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        android.os.Trace.beginSection("EmojiCompat.MetadataRepo.create");
        r2 = new androidx.emoji2.text.MetadataRepo(r1, androidx.emoji2.text.MetadataListReader.read(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        android.os.Trace.endSection();
        r0 = r4.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x010a, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r1 = r4.mCallback;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x010d, code lost:
        if (r1 == null) goto L_0x0112;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x010f, code lost:
        r1.onLoaded(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0112, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        r4.cleanUp();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x011a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x011e, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0126, code lost:
        throw new java.lang.RuntimeException("Unable to open file.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0127, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x012b, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0147, code lost:
        throw new java.lang.RuntimeException("fetchFonts result is not OK. (" + r3 + ")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0148, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x014b, code lost:
        monitor-enter(r4.mLock);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:?, code lost:
        r2 = r4.mCallback;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x014e, code lost:
        if (r2 != null) goto L_0x0150;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0150, code lost:
        r2.onFailed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0154, code lost:
        r4.cleanUp();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            int r0 = r4.$r8$classId
            r1 = 2
            r2 = 1
            switch(r0) {
                case 0: goto L_0x015e;
                case 1: goto L_0x00ac;
                case 2: goto L_0x00a1;
                case 3: goto L_0x0099;
                case 4: goto L_0x008e;
                case 5: goto L_0x007f;
                case 6: goto L_0x006a;
                case 7: goto L_0x0049;
                case 8: goto L_0x0035;
                case 9: goto L_0x002b;
                case 10: goto L_0x0018;
                case 11: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0166
        L_0x0009:
            java.lang.Object r4 = r4.f$0
            com.android.wm.shell.pip.phone.PipDismissTargetHandler r4 = (com.android.p012wm.shell.pip.phone.PipDismissTargetHandler) r4
            java.util.Objects.requireNonNull(r4)
            android.widget.FrameLayout r4 = r4.mTargetViewContainer
            r0 = 8
            r4.setVisibility(r0)
            return
        L_0x0018:
            java.lang.Object r4 = r4.f$0
            com.android.wifitrackerlib.WifiEntry$ConnectActionListener r4 = (com.android.wifitrackerlib.WifiEntry.ConnectActionListener) r4
            java.util.Objects.requireNonNull(r4)
            com.android.wifitrackerlib.WifiEntry r4 = com.android.wifitrackerlib.WifiEntry.this
            com.android.wifitrackerlib.WifiEntry$ConnectCallback r4 = r4.mConnectCallback
            if (r4 == 0) goto L_0x002a
            com.android.systemui.qs.tiles.dialog.InternetDialogController$WifiEntryConnectCallback r4 = (com.android.systemui.p006qs.tiles.dialog.InternetDialogController.WifiEntryConnectCallback) r4
            r4.onConnectResult(r1)
        L_0x002a:
            return
        L_0x002b:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.statusbar.policy.LocationControllerImpl r4 = (com.android.systemui.statusbar.policy.LocationControllerImpl) r4
            int r0 = com.android.systemui.statusbar.policy.LocationControllerImpl.$r8$clinit
            r4.updateActiveLocationRequests()
            return
        L_0x0035:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$1 r4 = (com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.C15071) r4
            int r0 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.C15071.$r8$clinit
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r0 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.this
            r0.updateAlarm()
            com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r4 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.this
            r4.updateManagedProfile()
            return
        L_0x0049:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r4 = (com.android.systemui.statusbar.connectivity.NetworkControllerImpl) r4
            boolean r0 = com.android.systemui.statusbar.connectivity.NetworkControllerImpl.DEBUG
            java.util.Objects.requireNonNull(r4)
            android.telephony.ServiceState r0 = r4.mLastServiceState
            if (r0 != 0) goto L_0x0069
            android.telephony.TelephonyManager r0 = r4.mPhone
            android.telephony.ServiceState r0 = r0.getServiceState()
            r4.mLastServiceState = r0
            android.util.SparseArray<com.android.systemui.statusbar.connectivity.MobileSignalController> r0 = r4.mMobileSignalControllers
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0069
            r4.recalculateEmergency()
        L_0x0069:
            return
        L_0x006a:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.shared.rotation.FloatingRotationButton r4 = (com.android.systemui.shared.rotation.FloatingRotationButton) r4
            java.util.Objects.requireNonNull(r4)
            boolean r0 = r4.mIsShowing
            if (r0 == 0) goto L_0x007e
            com.android.systemui.shared.rotation.RotationButton$RotationButtonUpdatesCallback r4 = r4.mUpdatesCallback
            if (r4 == 0) goto L_0x007e
            com.android.systemui.navigationbar.NavigationBarView$2 r4 = (com.android.systemui.navigationbar.NavigationBarView.C09242) r4
            r4.onVisibilityChanged(r2)
        L_0x007e:
            return
        L_0x007f:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.qs.external.CustomTile r4 = (com.android.systemui.p006qs.external.CustomTile) r4
            int r0 = com.android.systemui.p006qs.external.CustomTile.$r8$clinit
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.qs.external.TileLifecycleManager r4 = r4.mService     // Catch:{ RemoteException -> 0x008d }
            r4.onUnlockComplete()     // Catch:{ RemoteException -> 0x008d }
        L_0x008d:
            return
        L_0x008e:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.qrcodescanner.controller.QRCodeScannerController r4 = (com.android.systemui.qrcodescanner.controller.QRCodeScannerController) r4
            java.util.Objects.requireNonNull(r4)
            r4.updateQRCodeScannerPreferenceDetails(r2)
            return
        L_0x0099:
            java.lang.Object r4 = r4.f$0
            com.android.systemui.dreams.DreamOverlayContainerViewController r4 = (com.android.systemui.dreams.DreamOverlayContainerViewController) r4
            com.android.systemui.dreams.DreamOverlayContainerViewController.$r8$lambda$Oxvj_GJUc06UJC_m7GrRwIKFrUA(r4)
            return
        L_0x00a1:
            java.lang.Object r4 = r4.f$0
            com.android.keyguard.KeyguardClockSwitchController r4 = (com.android.keyguard.KeyguardClockSwitchController) r4
            java.util.Objects.requireNonNull(r4)
            r4.displayClock(r2, r2)
            return
        L_0x00ac:
            java.lang.Object r4 = r4.f$0
            androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader r4 = (androidx.emoji2.text.FontRequestEmojiCompatConfig.FontRequestMetadataLoader) r4
            java.util.Objects.requireNonNull(r4)
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r3 = r4.mCallback     // Catch:{ all -> 0x015b }
            if (r3 != 0) goto L_0x00bd
            monitor-exit(r0)     // Catch:{ all -> 0x015b }
            goto L_0x0157
        L_0x00bd:
            monitor-exit(r0)     // Catch:{ all -> 0x015b }
            androidx.core.provider.FontsContractCompat$FontInfo r0 = r4.retrieveFontInfo()     // Catch:{ all -> 0x0148 }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0148 }
            int r3 = r0.mResultCode     // Catch:{ all -> 0x0148 }
            if (r3 != r1) goto L_0x00d1
            java.lang.Object r1 = r4.mLock     // Catch:{ all -> 0x0148 }
            monitor-enter(r1)     // Catch:{ all -> 0x0148 }
            monitor-exit(r1)     // Catch:{ all -> 0x00ce }
            goto L_0x00d1
        L_0x00ce:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ce }
            throw r0     // Catch:{ all -> 0x0148 }
        L_0x00d1:
            if (r3 != 0) goto L_0x012c
            java.lang.String r1 = "EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface"
            android.os.Trace.beginSection(r1)     // Catch:{ all -> 0x0127 }
            androidx.emoji2.text.FontRequestEmojiCompatConfig$FontProviderHelper r1 = r4.mFontProviderHelper     // Catch:{ all -> 0x0127 }
            android.content.Context r3 = r4.mContext     // Catch:{ all -> 0x0127 }
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x0127 }
            androidx.core.provider.FontsContractCompat$FontInfo[] r1 = new androidx.core.provider.FontsContractCompat$FontInfo[r2]     // Catch:{ all -> 0x0127 }
            r2 = 0
            r1[r2] = r0     // Catch:{ all -> 0x0127 }
            android.graphics.Typeface r1 = androidx.core.graphics.TypefaceCompat.createFromFontInfo(r3, r1, r2)     // Catch:{ all -> 0x0127 }
            android.content.Context r2 = r4.mContext     // Catch:{ all -> 0x0127 }
            android.net.Uri r0 = r0.mUri     // Catch:{ all -> 0x0127 }
            java.nio.MappedByteBuffer r0 = androidx.core.graphics.TypefaceCompatUtil.mmap(r2, r0)     // Catch:{ all -> 0x0127 }
            if (r0 == 0) goto L_0x011f
            if (r1 == 0) goto L_0x011f
            java.lang.String r2 = "EmojiCompat.MetadataRepo.create"
            android.os.Trace.beginSection(r2)     // Catch:{ all -> 0x011a }
            androidx.emoji2.text.MetadataRepo r2 = new androidx.emoji2.text.MetadataRepo     // Catch:{ all -> 0x011a }
            androidx.emoji2.text.flatbuffer.MetadataList r0 = androidx.emoji2.text.MetadataListReader.read(r0)     // Catch:{ all -> 0x011a }
            r2.<init>(r1, r0)     // Catch:{ all -> 0x011a }
            android.os.Trace.endSection()     // Catch:{ all -> 0x0127 }
            android.os.Trace.endSection()     // Catch:{ all -> 0x0148 }
            java.lang.Object r0 = r4.mLock     // Catch:{ all -> 0x0148 }
            monitor-enter(r0)     // Catch:{ all -> 0x0148 }
            androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r1 = r4.mCallback     // Catch:{ all -> 0x0117 }
            if (r1 == 0) goto L_0x0112
            r1.onLoaded(r2)     // Catch:{ all -> 0x0117 }
        L_0x0112:
            monitor-exit(r0)     // Catch:{ all -> 0x0117 }
            r4.cleanUp()     // Catch:{ all -> 0x0148 }
            goto L_0x0157
        L_0x0117:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0117 }
            throw r1     // Catch:{ all -> 0x0148 }
        L_0x011a:
            r0 = move-exception
            android.os.Trace.endSection()     // Catch:{ all -> 0x0127 }
            throw r0     // Catch:{ all -> 0x0127 }
        L_0x011f:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x0127 }
            java.lang.String r1 = "Unable to open file."
            r0.<init>(r1)     // Catch:{ all -> 0x0127 }
            throw r0     // Catch:{ all -> 0x0127 }
        L_0x0127:
            r0 = move-exception
            android.os.Trace.endSection()     // Catch:{ all -> 0x0148 }
            throw r0     // Catch:{ all -> 0x0148 }
        L_0x012c:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x0148 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r1.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r2 = "fetchFonts result is not OK. ("
            r1.append(r2)     // Catch:{ all -> 0x0148 }
            r1.append(r3)     // Catch:{ all -> 0x0148 }
            java.lang.String r2 = ")"
            r1.append(r2)     // Catch:{ all -> 0x0148 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0148 }
            r0.<init>(r1)     // Catch:{ all -> 0x0148 }
            throw r0     // Catch:{ all -> 0x0148 }
        L_0x0148:
            r0 = move-exception
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
            androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r2 = r4.mCallback     // Catch:{ all -> 0x0158 }
            if (r2 == 0) goto L_0x0153
            r2.onFailed(r0)     // Catch:{ all -> 0x0158 }
        L_0x0153:
            monitor-exit(r1)     // Catch:{ all -> 0x0158 }
            r4.cleanUp()
        L_0x0157:
            return
        L_0x0158:
            r4 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0158 }
            throw r4
        L_0x015b:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x015b }
            throw r4
        L_0x015e:
            java.lang.Object r4 = r4.f$0
            com.android.settingslib.wifi.AccessPoint r4 = (com.android.settingslib.wifi.AccessPoint) r4
            java.util.Objects.requireNonNull(r4)
            return
        L_0x0166:
            java.lang.Object r4 = r4.f$0
            com.android.wm.shell.splitscreen.StageCoordinator r4 = (com.android.p012wm.shell.splitscreen.StageCoordinator) r4
            java.util.Objects.requireNonNull(r4)
            com.android.wm.shell.splitscreen.MainStage r0 = r4.mMainStage
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsActive
            if (r0 != 0) goto L_0x0183
            com.android.wm.shell.common.split.SplitLayout r0 = r4.mSplitLayout
            r0.release()
            com.android.wm.shell.common.split.SplitLayout r0 = r4.mSplitLayout
            r0.resetDividerPosition()
            r0 = -1
            r4.mTopStageAfterFoldDismiss = r0
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0.run():void");
    }
}
