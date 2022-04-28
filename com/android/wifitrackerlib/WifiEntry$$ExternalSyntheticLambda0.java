package com.android.wifitrackerlib;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiEntry$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01bb, code lost:
        r11.decreaseIndent();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r22 = this;
            r0 = r22
            int r1 = r0.$r8$classId
            r2 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            r4 = 5
            switch(r1) {
                case 0: goto L_0x028d;
                case 1: goto L_0x027f;
                case 2: goto L_0x000d;
                case 3: goto L_0x0270;
                case 4: goto L_0x0043;
                case 5: goto L_0x002b;
                case 6: goto L_0x001e;
                case 7: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x029c
        L_0x000f:
            java.lang.Object r0 = r0.f$0
            com.google.android.systemui.elmyra.gates.Gate r0 = (com.google.android.systemui.elmyra.gates.Gate) r0
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.elmyra.gates.Gate$Listener r1 = r0.mListener
            if (r1 == 0) goto L_0x001d
            r1.onGateChanged(r0)
        L_0x001d:
            return
        L_0x001e:
            java.lang.Object r0 = r0.f$0
            com.google.android.systemui.dreamliner.DockObserver r0 = (com.google.android.systemui.dreamliner.DockObserver) r0
            java.lang.String r1 = com.google.android.systemui.dreamliner.DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE
            java.util.Objects.requireNonNull(r0)
            r0.notifyDreamlinerLatestFanLevel()
            return
        L_0x002b:
            java.lang.Object r0 = r0.f$0
            com.android.wm.shell.bubbles.BubbleStackView r0 = (com.android.p012wm.shell.bubbles.BubbleStackView) r0
            int r1 = com.android.p012wm.shell.bubbles.BubbleStackView.FLYOUT_HIDE_AFTER
            java.util.Objects.requireNonNull(r0)
            com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1 r1 = new com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1
            r1.<init>(r0, r4)
            r0.mAnimateInFlyout = r1
            com.android.wm.shell.bubbles.BubbleFlyoutView r0 = r0.mFlyout
            r2 = 200(0xc8, double:9.9E-322)
            r0.postDelayed(r1, r2)
            return
        L_0x0043:
            java.lang.Object r0 = r0.f$0
            com.android.systemui.util.leak.GarbageMonitor r0 = (com.android.systemui.util.leak.GarbageMonitor) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.util.leak.TrackedGarbage r1 = r0.mTrackedGarbage
            int r1 = r1.countOldGarbage()
            if (r1 <= r4) goto L_0x026f
            com.android.systemui.util.leak.LeakReporter r0 = r0.mLeakReporter
            java.util.Objects.requireNonNull(r0)
            java.lang.String r4 = "leak"
            java.lang.String r5 = "LeakReporter"
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0268 }
            android.content.Context r7 = r0.mContext     // Catch:{ IOException -> 0x0268 }
            java.io.File r7 = r7.getCacheDir()     // Catch:{ IOException -> 0x0268 }
            r6.<init>(r7, r4)     // Catch:{ IOException -> 0x0268 }
            r6.mkdir()     // Catch:{ IOException -> 0x0268 }
            java.io.File r7 = new java.io.File     // Catch:{ IOException -> 0x0268 }
            java.lang.String r8 = "leak.hprof"
            r7.<init>(r6, r8)     // Catch:{ IOException -> 0x0268 }
            java.lang.String r8 = r7.getAbsolutePath()     // Catch:{ IOException -> 0x0268 }
            android.os.Debug.dumpHprofData(r8)     // Catch:{ IOException -> 0x0268 }
            java.io.File r8 = new java.io.File     // Catch:{ IOException -> 0x0268 }
            java.lang.String r9 = "leak.dump"
            r8.<init>(r6, r9)     // Catch:{ IOException -> 0x0268 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0268 }
            r6.<init>(r8)     // Catch:{ IOException -> 0x0268 }
            java.io.PrintWriter r9 = new java.io.PrintWriter     // Catch:{ all -> 0x0259 }
            r9.<init>(r6)     // Catch:{ all -> 0x0259 }
            java.lang.String r10 = "Build: "
            r9.print(r10)     // Catch:{ all -> 0x0259 }
            java.lang.String r10 = "ro.build.description"
            java.lang.String r10 = android.os.SystemProperties.get(r10)     // Catch:{ all -> 0x0259 }
            r9.println(r10)     // Catch:{ all -> 0x0259 }
            r9.println()     // Catch:{ all -> 0x0259 }
            r9.flush()     // Catch:{ all -> 0x0259 }
            com.android.systemui.util.leak.LeakDetector r10 = r0.mLeakDetector     // Catch:{ all -> 0x0259 }
            r6.getFD()     // Catch:{ all -> 0x0259 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x0259 }
            com.android.internal.util.IndentingPrintWriter r11 = new com.android.internal.util.IndentingPrintWriter     // Catch:{ all -> 0x0259 }
            java.lang.String r12 = "  "
            r11.<init>(r9, r12)     // Catch:{ all -> 0x0259 }
            java.lang.String r12 = "SYSUI LEAK DETECTOR"
            r11.println(r12)     // Catch:{ all -> 0x0259 }
            r11.increaseIndent()     // Catch:{ all -> 0x0259 }
            com.android.systemui.util.leak.TrackedCollections r12 = r10.mTrackedCollections     // Catch:{ all -> 0x0259 }
            if (r12 == 0) goto L_0x01cb
            com.android.systemui.util.leak.TrackedGarbage r12 = r10.mTrackedGarbage     // Catch:{ all -> 0x0259 }
            if (r12 == 0) goto L_0x01cb
            java.lang.String r12 = "TrackedCollections:"
            r11.println(r12)     // Catch:{ all -> 0x01c6 }
            r11.increaseIndent()     // Catch:{ all -> 0x01c6 }
            com.android.systemui.util.leak.TrackedCollections r12 = r10.mTrackedCollections     // Catch:{ all -> 0x01c6 }
            com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda0 r14 = com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda0.INSTANCE     // Catch:{ all -> 0x01c6 }
            r12.dump(r11, r14)     // Catch:{ all -> 0x01c6 }
            r11.decreaseIndent()     // Catch:{ all -> 0x01c6 }
            r11.println()     // Catch:{ all -> 0x01c6 }
            java.lang.String r12 = "TrackedObjects:"
            r11.println(r12)     // Catch:{ all -> 0x01c6 }
            r11.increaseIndent()     // Catch:{ all -> 0x01c6 }
            com.android.systemui.util.leak.TrackedCollections r12 = r10.mTrackedCollections     // Catch:{ all -> 0x01c6 }
            com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda1 r14 = com.android.systemui.util.leak.LeakDetector$$ExternalSyntheticLambda1.INSTANCE     // Catch:{ all -> 0x01c6 }
            r12.dump(r11, r14)     // Catch:{ all -> 0x01c6 }
            r11.decreaseIndent()     // Catch:{ all -> 0x01c6 }
            r11.println()     // Catch:{ all -> 0x01c6 }
            java.lang.String r12 = "TrackedGarbage:"
            r11.print(r12)     // Catch:{ all -> 0x01c6 }
            r11.increaseIndent()     // Catch:{ all -> 0x01c6 }
            com.android.systemui.util.leak.TrackedGarbage r10 = r10.mTrackedGarbage     // Catch:{ all -> 0x01c6 }
            java.util.Objects.requireNonNull(r10)     // Catch:{ all -> 0x01c6 }
            monitor-enter(r10)     // Catch:{ all -> 0x01c6 }
        L_0x00f4:
            java.lang.ref.ReferenceQueue<java.lang.Object> r12 = r10.mRefQueue     // Catch:{ all -> 0x01c1 }
            java.lang.ref.Reference r12 = r12.poll()     // Catch:{ all -> 0x01c1 }
            if (r12 == 0) goto L_0x0102
            java.util.HashSet<com.android.systemui.util.leak.TrackedGarbage$LeakReference> r14 = r10.mGarbage     // Catch:{ all -> 0x01c1 }
            r14.remove(r12)     // Catch:{ all -> 0x01c1 }
            goto L_0x00f4
        L_0x0102:
            long r14 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x01c1 }
            android.util.ArrayMap r12 = new android.util.ArrayMap     // Catch:{ all -> 0x01c1 }
            r12.<init>()     // Catch:{ all -> 0x01c1 }
            android.util.ArrayMap r2 = new android.util.ArrayMap     // Catch:{ all -> 0x01c1 }
            r2.<init>()     // Catch:{ all -> 0x01c1 }
            java.util.HashSet<com.android.systemui.util.leak.TrackedGarbage$LeakReference> r13 = r10.mGarbage     // Catch:{ all -> 0x01c1 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ all -> 0x01c1 }
        L_0x0116:
            boolean r16 = r13.hasNext()     // Catch:{ all -> 0x01c1 }
            if (r16 == 0) goto L_0x016f
            java.lang.Object r16 = r13.next()     // Catch:{ all -> 0x01c1 }
            r17 = r13
            r13 = r16
            com.android.systemui.util.leak.TrackedGarbage$LeakReference r13 = (com.android.systemui.util.leak.TrackedGarbage.LeakReference) r13     // Catch:{ all -> 0x01c1 }
            r16 = r5
            java.lang.Class<?> r5 = r13.clazz     // Catch:{ all -> 0x01bf }
            java.lang.Object r18 = r12.getOrDefault(r5, r3)     // Catch:{ all -> 0x01bf }
            java.lang.Integer r18 = (java.lang.Integer) r18     // Catch:{ all -> 0x01bf }
            int r18 = r18.intValue()     // Catch:{ all -> 0x01bf }
            r19 = 1
            int r18 = r18 + 1
            r19 = r7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r18)     // Catch:{ all -> 0x01bf }
            r12.put(r5, r7)     // Catch:{ all -> 0x01bf }
            r5 = r8
            long r7 = r13.createdUptimeMillis     // Catch:{ all -> 0x01bf }
            r20 = 60000(0xea60, double:2.9644E-319)
            long r7 = r7 + r20
            int r7 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r7 >= 0) goto L_0x014f
            r7 = 1
            goto L_0x0150
        L_0x014f:
            r7 = 0
        L_0x0150:
            if (r7 == 0) goto L_0x0167
            java.lang.Class<?> r7 = r13.clazz     // Catch:{ all -> 0x01bf }
            java.lang.Object r8 = r2.getOrDefault(r7, r3)     // Catch:{ all -> 0x01bf }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ all -> 0x01bf }
            int r8 = r8.intValue()     // Catch:{ all -> 0x01bf }
            r13 = 1
            int r8 = r8 + r13
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x01bf }
            r2.put(r7, r8)     // Catch:{ all -> 0x01bf }
        L_0x0167:
            r8 = r5
            r5 = r16
            r13 = r17
            r7 = r19
            goto L_0x0116
        L_0x016f:
            r16 = r5
            r19 = r7
            r5 = r8
            java.util.Set r7 = r12.entrySet()     // Catch:{ all -> 0x01bf }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x01bf }
        L_0x017c:
            boolean r8 = r7.hasNext()     // Catch:{ all -> 0x01bf }
            if (r8 == 0) goto L_0x01ba
            java.lang.Object r8 = r7.next()     // Catch:{ all -> 0x01bf }
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8     // Catch:{ all -> 0x01bf }
            java.lang.Object r12 = r8.getKey()     // Catch:{ all -> 0x01bf }
            java.lang.Class r12 = (java.lang.Class) r12     // Catch:{ all -> 0x01bf }
            java.lang.String r12 = r12.getName()     // Catch:{ all -> 0x01bf }
            r11.print(r12)     // Catch:{ all -> 0x01bf }
            java.lang.String r12 = ": "
            r11.print(r12)     // Catch:{ all -> 0x01bf }
            java.lang.Object r12 = r8.getValue()     // Catch:{ all -> 0x01bf }
            r11.print(r12)     // Catch:{ all -> 0x01bf }
            java.lang.String r12 = " total, "
            r11.print(r12)     // Catch:{ all -> 0x01bf }
            java.lang.Object r8 = r8.getKey()     // Catch:{ all -> 0x01bf }
            java.lang.Object r8 = r2.getOrDefault(r8, r3)     // Catch:{ all -> 0x01bf }
            r11.print(r8)     // Catch:{ all -> 0x01bf }
            java.lang.String r8 = " old"
            r11.print(r8)     // Catch:{ all -> 0x01bf }
            r11.println()     // Catch:{ all -> 0x01bf }
            goto L_0x017c
        L_0x01ba:
            monitor-exit(r10)     // Catch:{ all -> 0x0257 }
            r11.decreaseIndent()     // Catch:{ all -> 0x0257 }
            goto L_0x01d5
        L_0x01bf:
            r0 = move-exception
            goto L_0x01c4
        L_0x01c1:
            r0 = move-exception
            r16 = r5
        L_0x01c4:
            monitor-exit(r10)     // Catch:{ all -> 0x0257 }
            throw r0     // Catch:{ all -> 0x0257 }
        L_0x01c6:
            r0 = move-exception
            r16 = r5
            goto L_0x0254
        L_0x01cb:
            r16 = r5
            r19 = r7
            r5 = r8
            java.lang.String r2 = "disabled"
            r11.println(r2)     // Catch:{ all -> 0x0257 }
        L_0x01d5:
            r11.decreaseIndent()     // Catch:{ all -> 0x0257 }
            r11.println()     // Catch:{ all -> 0x0257 }
            r9.close()     // Catch:{ all -> 0x0253 }
            r6.close()     // Catch:{ IOException -> 0x024f }
            android.content.Context r2 = r0.mContext     // Catch:{ IOException -> 0x024f }
            java.lang.Class<android.app.NotificationManager> r3 = android.app.NotificationManager.class
            java.lang.Object r2 = r2.getSystemService(r3)     // Catch:{ IOException -> 0x024f }
            android.app.NotificationManager r2 = (android.app.NotificationManager) r2     // Catch:{ IOException -> 0x024f }
            android.app.NotificationChannel r3 = new android.app.NotificationChannel     // Catch:{ IOException -> 0x024f }
            java.lang.String r6 = "Leak Alerts"
            r7 = 4
            r3.<init>(r4, r6, r7)     // Catch:{ IOException -> 0x024f }
            r4 = 1
            r3.enableVibration(r4)     // Catch:{ IOException -> 0x024f }
            r2.createNotificationChannel(r3)     // Catch:{ IOException -> 0x024f }
            android.app.Notification$Builder r4 = new android.app.Notification$Builder     // Catch:{ IOException -> 0x024f }
            android.content.Context r6 = r0.mContext     // Catch:{ IOException -> 0x024f }
            java.lang.String r3 = r3.getId()     // Catch:{ IOException -> 0x024f }
            r4.<init>(r6, r3)     // Catch:{ IOException -> 0x024f }
            r3 = 1
            android.app.Notification$Builder r4 = r4.setAutoCancel(r3)     // Catch:{ IOException -> 0x024f }
            android.app.Notification$Builder r4 = r4.setShowWhen(r3)     // Catch:{ IOException -> 0x024f }
            java.lang.String r6 = "Memory Leak Detected"
            android.app.Notification$Builder r4 = r4.setContentTitle(r6)     // Catch:{ IOException -> 0x024f }
            java.lang.String r6 = "SystemUI has detected %d leaked objects. Tap to send"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x024f }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ IOException -> 0x024f }
            r7 = 0
            r3[r7] = r1     // Catch:{ IOException -> 0x024f }
            java.lang.String r1 = java.lang.String.format(r6, r3)     // Catch:{ IOException -> 0x024f }
            android.app.Notification$Builder r1 = r4.setContentText(r1)     // Catch:{ IOException -> 0x024f }
            r3 = 17303574(0x1080816, float:2.4985056E-38)
            android.app.Notification$Builder r1 = r1.setSmallIcon(r3)     // Catch:{ IOException -> 0x024f }
            android.content.Context r6 = r0.mContext     // Catch:{ IOException -> 0x024f }
            r7 = 0
            r3 = r19
            android.content.Intent r8 = r0.getIntent(r3, r5)     // Catch:{ IOException -> 0x024f }
            r9 = 201326592(0xc000000, float:9.8607613E-32)
            r10 = 0
            android.os.UserHandle r11 = android.os.UserHandle.CURRENT     // Catch:{ IOException -> 0x024f }
            android.app.PendingIntent r0 = android.app.PendingIntent.getActivityAsUser(r6, r7, r8, r9, r10, r11)     // Catch:{ IOException -> 0x024f }
            android.app.Notification$Builder r0 = r1.setContentIntent(r0)     // Catch:{ IOException -> 0x024f }
            android.app.Notification r0 = r0.build()     // Catch:{ IOException -> 0x024f }
            r1 = r16
            r3 = 0
            r2.notify(r1, r3, r0)     // Catch:{ IOException -> 0x0266 }
            goto L_0x026f
        L_0x024f:
            r0 = move-exception
            r1 = r16
            goto L_0x026a
        L_0x0253:
            r0 = move-exception
        L_0x0254:
            r1 = r16
            goto L_0x025b
        L_0x0257:
            r0 = move-exception
            goto L_0x0254
        L_0x0259:
            r0 = move-exception
            r1 = r5
        L_0x025b:
            r2 = r0
            r6.close()     // Catch:{ all -> 0x0260 }
            goto L_0x0265
        L_0x0260:
            r0 = move-exception
            r3 = r0
            r2.addSuppressed(r3)     // Catch:{ IOException -> 0x0266 }
        L_0x0265:
            throw r2     // Catch:{ IOException -> 0x0266 }
        L_0x0266:
            r0 = move-exception
            goto L_0x026a
        L_0x0268:
            r0 = move-exception
            r1 = r5
        L_0x026a:
            java.lang.String r2 = "Couldn't dump heap for leak"
            android.util.Log.e(r1, r2, r0)
        L_0x026f:
            return
        L_0x0270:
            java.lang.Object r0 = r0.f$0
            com.android.systemui.statusbar.phone.StatusBar$10 r0 = (com.android.systemui.statusbar.phone.StatusBar.C153310) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.StatusBar r0 = com.android.systemui.statusbar.phone.StatusBar.this
            com.android.systemui.statusbar.phone.StatusBarCommandQueueCallbacks r0 = r0.mCommandQueueCallbacks
            r0.onEmergencyActionLaunchGestureDetected()
            return
        L_0x027f:
            java.lang.Object r0 = r0.f$0
            com.android.keyguard.KeyguardUpdateMonitor$15 r0 = (com.android.keyguard.KeyguardUpdateMonitor.C054115) r0
            java.util.Objects.requireNonNull(r0)
            com.android.keyguard.KeyguardUpdateMonitor r0 = com.android.keyguard.KeyguardUpdateMonitor.this
            r1 = 2
            r0.updateBiometricListeningState(r1)
            return
        L_0x028d:
            java.lang.Object r0 = r0.f$0
            com.android.wifitrackerlib.WifiEntry r0 = (com.android.wifitrackerlib.WifiEntry) r0
            java.util.Objects.requireNonNull(r0)
            com.android.wifitrackerlib.WifiEntry$WifiEntryCallback r0 = r0.mListener
            if (r0 == 0) goto L_0x029b
            r0.onUpdated()
        L_0x029b:
            return
        L_0x029c:
            java.lang.Object r0 = r0.f$0
            com.google.android.systemui.input.TouchContextService r0 = (com.google.android.systemui.input.TouchContextService) r0
            java.lang.String r1 = com.google.android.systemui.input.TouchContextService.INTERFACE
            java.util.Objects.requireNonNull(r0)
            r1 = 0
            r0.onDisplayChanged(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda0.run():void");
    }
}
