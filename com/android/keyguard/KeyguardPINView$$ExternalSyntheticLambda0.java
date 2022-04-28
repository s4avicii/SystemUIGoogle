package com.android.keyguard;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPINView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardPINView$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f4, code lost:
        if (r10 > 0) goto L_0x00f7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            int r0 = r10.$r8$classId
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x0107;
                case 1: goto L_0x008a;
                case 2: goto L_0x002a;
                case 3: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x011d
        L_0x0009:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.statusbar.phone.KeyguardBottomAreaView$2 r0 = (com.android.systemui.statusbar.phone.KeyguardBottomAreaView.C14282) r0
            java.lang.Object r10 = r10.f$1
            java.util.List r10 = (java.util.List) r10
            java.util.Objects.requireNonNull(r0)
            boolean r10 = r10.isEmpty()
            r10 = r10 ^ r2
            com.android.systemui.statusbar.phone.KeyguardBottomAreaView r1 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
            boolean r2 = r1.mControlServicesAvailable
            if (r10 == r2) goto L_0x0029
            r1.mControlServicesAvailable = r10
            r1.updateControlsVisibility()
            com.android.systemui.statusbar.phone.KeyguardBottomAreaView r10 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
            r10.updateAffordanceColors()
        L_0x0029:
            return
        L_0x002a:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.screenrecord.RecordingService r0 = (com.android.systemui.screenrecord.RecordingService) r0
            java.lang.Object r10 = r10.f$1
            android.os.UserHandle r10 = (android.os.UserHandle) r10
            int r2 = com.android.systemui.screenrecord.RecordingService.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            java.lang.String r2 = "RecordingService"
            r3 = 4275(0x10b3, float:5.99E-42)
            java.lang.String r4 = "saving recording"
            android.util.Log.d(r2, r4)     // Catch:{ IOException -> 0x005f }
            com.android.systemui.screenrecord.ScreenMediaRecorder r4 = r0.getRecorder()     // Catch:{ IOException -> 0x005f }
            com.android.systemui.screenrecord.ScreenMediaRecorder$SavedRecording r4 = r4.save()     // Catch:{ IOException -> 0x005f }
            android.app.Notification r4 = r0.createSaveNotification(r4)     // Catch:{ IOException -> 0x005f }
            com.android.systemui.screenrecord.RecordingController r5 = r0.mController     // Catch:{ IOException -> 0x005f }
            boolean r5 = r5.isRecording()     // Catch:{ IOException -> 0x005f }
            if (r5 != 0) goto L_0x007e
            android.app.NotificationManager r5 = r0.mNotificationManager     // Catch:{ IOException -> 0x005f }
            r6 = 4273(0x10b1, float:5.988E-42)
            r5.notifyAsUser(r1, r6, r4, r10)     // Catch:{ IOException -> 0x005f }
            goto L_0x007e
        L_0x005d:
            r2 = move-exception
            goto L_0x0084
        L_0x005f:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            r5.<init>()     // Catch:{ all -> 0x005d }
            java.lang.String r6 = "Error saving screen recording: "
            r5.append(r6)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r4.getMessage()     // Catch:{ all -> 0x005d }
            r5.append(r4)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x005d }
            android.util.Log.e(r2, r4)     // Catch:{ all -> 0x005d }
            r2 = 2131953203(0x7f130633, float:1.954287E38)
            r0.showErrorToast(r2)     // Catch:{ all -> 0x005d }
        L_0x007e:
            android.app.NotificationManager r0 = r0.mNotificationManager
            r0.cancelAsUser(r1, r3, r10)
            return
        L_0x0084:
            android.app.NotificationManager r0 = r0.mNotificationManager
            r0.cancelAsUser(r1, r3, r10)
            throw r2
        L_0x008a:
            java.lang.Object r0 = r10.f$0
            com.android.systemui.media.MediaControlPanel r0 = (com.android.systemui.media.MediaControlPanel) r0
            java.lang.Object r10 = r10.f$1
            android.media.session.MediaController r10 = (android.media.session.MediaController) r10
            android.content.Intent r3 = com.android.systemui.media.MediaControlPanel.SETTINGS_INTENT
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.media.SeekBarViewModel r0 = r0.mSeekBarViewModel
            java.util.Objects.requireNonNull(r0)
            r0.setController(r10)
            android.media.session.MediaController r10 = r0.controller
            if (r10 != 0) goto L_0x00a5
            r10 = r1
            goto L_0x00a9
        L_0x00a5:
            android.media.session.PlaybackState r10 = r10.getPlaybackState()
        L_0x00a9:
            r0.playbackState = r10
            android.media.session.MediaController r10 = r0.controller
            if (r10 != 0) goto L_0x00b1
            r10 = r1
            goto L_0x00b5
        L_0x00b1:
            android.media.MediaMetadata r10 = r10.getMetadata()
        L_0x00b5:
            android.media.session.PlaybackState r3 = r0.playbackState
            r4 = 0
            if (r3 != 0) goto L_0x00bd
            r6 = r4
            goto L_0x00c1
        L_0x00bd:
            long r6 = r3.getActions()
        L_0x00c1:
            r8 = 256(0x100, double:1.265E-321)
            long r6 = r6 & r8
            int r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            r4 = 0
            if (r3 == 0) goto L_0x00cb
            r3 = r2
            goto L_0x00cc
        L_0x00cb:
            r3 = r4
        L_0x00cc:
            android.media.session.PlaybackState r5 = r0.playbackState
            if (r5 != 0) goto L_0x00d1
            goto L_0x00da
        L_0x00d1:
            long r5 = r5.getPosition()
            int r1 = (int) r5
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
        L_0x00da:
            if (r10 != 0) goto L_0x00de
            r10 = r4
            goto L_0x00e5
        L_0x00de:
            java.lang.String r5 = "android.media.metadata.DURATION"
            long r5 = r10.getLong(r5)
            int r10 = (int) r5
        L_0x00e5:
            android.media.session.PlaybackState r5 = r0.playbackState
            if (r5 == 0) goto L_0x00f6
            int r5 = r5.getState()
            if (r5 != 0) goto L_0x00f1
            r5 = r2
            goto L_0x00f2
        L_0x00f1:
            r5 = r4
        L_0x00f2:
            if (r5 != 0) goto L_0x00f6
            if (r10 > 0) goto L_0x00f7
        L_0x00f6:
            r2 = r4
        L_0x00f7:
            com.android.systemui.media.SeekBarViewModel$Progress r4 = new com.android.systemui.media.SeekBarViewModel$Progress
            r4.<init>(r2, r3, r1, r10)
            r0._data = r4
            androidx.lifecycle.MutableLiveData<com.android.systemui.media.SeekBarViewModel$Progress> r10 = r0._progress
            r10.postValue(r4)
            r0.checkIfPollingNeeded()
            return
        L_0x0107:
            java.lang.Object r0 = r10.f$0
            com.android.keyguard.KeyguardPINView r0 = (com.android.keyguard.KeyguardPINView) r0
            java.lang.Object r10 = r10.f$1
            java.lang.Runnable r10 = (java.lang.Runnable) r10
            int r1 = com.android.keyguard.KeyguardPINView.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            r0.enableClipping(r2)
            if (r10 == 0) goto L_0x011c
            r10.run()
        L_0x011c:
            return
        L_0x011d:
            java.lang.Object r0 = r10.f$0
            com.google.android.systemui.power.AdaptiveChargingNotification r0 = (com.google.android.systemui.power.AdaptiveChargingNotification) r0
            java.lang.Object r10 = r10.f$1
            com.google.android.systemui.adaptivecharging.AdaptiveChargingManager$AdaptiveChargingStatusReceiver r10 = (com.google.android.systemui.adaptivecharging.AdaptiveChargingManager.AdaptiveChargingStatusReceiver) r10
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.adaptivecharging.AdaptiveChargingManager r0 = r0.mAdaptiveChargingManager
            r0.queryStatus(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0.run():void");
    }
}
