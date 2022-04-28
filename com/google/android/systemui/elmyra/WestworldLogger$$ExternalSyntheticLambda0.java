package com.google.android.systemui.elmyra;

import android.app.StatsManager;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WestworldLogger$$ExternalSyntheticLambda0 implements StatsManager.StatsPullAtomCallback {
    public final /* synthetic */ WestworldLogger f$0;

    public /* synthetic */ WestworldLogger$$ExternalSyntheticLambda0(WestworldLogger westworldLogger) {
        this.f$0 = westworldLogger;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
        r0 = r8.mSnapshotController;
        java.util.Objects.requireNonNull(r0);
        r2 = new com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$SnapshotHeader();
        r2.gestureType = 4;
        r2.identifier = 0;
        r0 = r0.mHandler;
        r0.sendMessage(r0.obtainMessage(1, r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r2 = java.lang.System.currentTimeMillis();
        r8.mCountDownLatch.await(50, java.util.concurrent.TimeUnit.MILLISECONDS);
        android.util.Log.d("Elmyra/Logger", "Snapshot took " + java.lang.Long.toString(java.lang.System.currentTimeMillis() - r2) + " milliseconds.");
        r2 = r8.mMutex;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007a, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007d, code lost:
        if (r8.mSnapshot == null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0081, code lost:
        if (r8.mChassis != null) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0084, code lost:
        r1 = r8.mGestureConfiguration.getSensitivity();
        r3 = r8.mSnapshot;
        r3.sensitivitySetting = r1;
        r10.add(android.util.StatsEvent.newBuilder().setAtomId(r9).writeByteArray(com.google.protobuf.nano.MessageNano.toByteArray(r3)).writeByteArray(com.google.protobuf.nano.MessageNano.toByteArray(r8.mChassis)).build());
        r8.mSnapshot = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b1, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b3, code lost:
        r8.mCountDownLatch = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b5, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ba, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bb, code lost:
        android.util.Log.d("Elmyra/Logger", r9.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c5, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c6, code lost:
        android.util.Log.d("Elmyra/Logger", r9.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int onPullAtom(int r9, java.util.List r10) {
        /*
            r8 = this;
            com.google.android.systemui.elmyra.WestworldLogger r8 = r8.f$0
            java.util.Objects.requireNonNull(r8)
            java.lang.String r0 = "Elmyra/Logger"
            java.lang.String r1 = "Receiving pull request from statsd."
            android.util.Log.d(r0, r1)
            com.google.android.systemui.elmyra.SnapshotController r0 = r8.mSnapshotController
            r1 = 1
            if (r0 != 0) goto L_0x001a
            java.lang.String r8 = "Elmyra/Logger"
            java.lang.String r9 = "Snapshot Controller is null, returning."
            android.util.Log.d(r8, r9)
            goto L_0x00d8
        L_0x001a:
            java.lang.Object r0 = r8.mMutex
            monitor-enter(r0)
            java.util.concurrent.CountDownLatch r2 = r8.mCountDownLatch     // Catch:{ all -> 0x00dc }
            if (r2 == 0) goto L_0x0024
            monitor-exit(r0)     // Catch:{ all -> 0x00dc }
            goto L_0x00d8
        L_0x0024:
            java.util.concurrent.CountDownLatch r2 = new java.util.concurrent.CountDownLatch     // Catch:{ all -> 0x00dc }
            r2.<init>(r1)     // Catch:{ all -> 0x00dc }
            r8.mCountDownLatch = r2     // Catch:{ all -> 0x00dc }
            monitor-exit(r0)     // Catch:{ all -> 0x00dc }
            com.google.android.systemui.elmyra.SnapshotController r0 = r8.mSnapshotController
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$SnapshotHeader r2 = new com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$SnapshotHeader
            r2.<init>()
            r3 = 4
            r2.gestureType = r3
            r3 = 0
            r2.identifier = r3
            com.google.android.systemui.elmyra.SnapshotController$1 r0 = r0.mHandler
            android.os.Message r2 = r0.obtainMessage(r1, r2)
            r0.sendMessage(r2)
            r0 = 0
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.util.concurrent.CountDownLatch r4 = r8.mCountDownLatch     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            r5 = 50
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            r4.await(r5, r7)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.lang.String r4 = "Elmyra/Logger"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            r5.<init>()     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.lang.String r6 = "Snapshot took "
            r5.append(r6)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            long r6 = r6 - r2
            java.lang.String r2 = java.lang.Long.toString(r6)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            r5.append(r2)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.lang.String r2 = " milliseconds."
            r5.append(r2)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.lang.String r2 = r5.toString()     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            android.util.Log.d(r4, r2)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            java.lang.Object r2 = r8.mMutex     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            monitor-enter(r2)     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
            com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$Snapshot r3 = r8.mSnapshot     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x00b3
            com.google.android.systemui.elmyra.proto.nano.ChassisProtos$Chassis r3 = r8.mChassis     // Catch:{ all -> 0x00b7 }
            if (r3 != 0) goto L_0x0084
            goto L_0x00b3
        L_0x0084:
            com.google.android.systemui.elmyra.sensors.config.GestureConfiguration r1 = r8.mGestureConfiguration     // Catch:{ all -> 0x00b7 }
            float r1 = r1.getSensitivity()     // Catch:{ all -> 0x00b7 }
            com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$Snapshot r3 = r8.mSnapshot     // Catch:{ all -> 0x00b7 }
            r3.sensitivitySetting = r1     // Catch:{ all -> 0x00b7 }
            com.google.android.systemui.elmyra.proto.nano.ChassisProtos$Chassis r1 = r8.mChassis     // Catch:{ all -> 0x00b7 }
            android.util.StatsEvent$Builder r4 = android.util.StatsEvent.newBuilder()     // Catch:{ all -> 0x00b7 }
            android.util.StatsEvent$Builder r9 = r4.setAtomId(r9)     // Catch:{ all -> 0x00b7 }
            byte[] r3 = com.google.protobuf.nano.MessageNano.toByteArray(r3)     // Catch:{ all -> 0x00b7 }
            android.util.StatsEvent$Builder r9 = r9.writeByteArray(r3)     // Catch:{ all -> 0x00b7 }
            byte[] r1 = com.google.protobuf.nano.MessageNano.toByteArray(r1)     // Catch:{ all -> 0x00b7 }
            android.util.StatsEvent$Builder r9 = r9.writeByteArray(r1)     // Catch:{ all -> 0x00b7 }
            android.util.StatsEvent r9 = r9.build()     // Catch:{ all -> 0x00b7 }
            r10.add(r9)     // Catch:{ all -> 0x00b7 }
            r8.mSnapshot = r0     // Catch:{ all -> 0x00b7 }
            monitor-exit(r2)     // Catch:{ all -> 0x00b7 }
            goto L_0x00cf
        L_0x00b3:
            r8.mCountDownLatch = r0     // Catch:{ all -> 0x00b7 }
            monitor-exit(r2)     // Catch:{ all -> 0x00b7 }
            goto L_0x00d8
        L_0x00b7:
            r9 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00b7 }
            throw r9     // Catch:{ InterruptedException -> 0x00c5, IllegalMonitorStateException -> 0x00ba }
        L_0x00ba:
            r9 = move-exception
            java.lang.String r10 = "Elmyra/Logger"
            java.lang.String r9 = r9.getMessage()
            android.util.Log.d(r10, r9)
            goto L_0x00cf
        L_0x00c5:
            r9 = move-exception
            java.lang.String r10 = "Elmyra/Logger"
            java.lang.String r9 = r9.getMessage()
            android.util.Log.d(r10, r9)
        L_0x00cf:
            java.lang.Object r9 = r8.mMutex
            monitor-enter(r9)
            r8.mCountDownLatch = r0     // Catch:{ all -> 0x00d9 }
            r8.mSnapshot = r0     // Catch:{ all -> 0x00d9 }
            monitor-exit(r9)     // Catch:{ all -> 0x00d9 }
            r1 = 0
        L_0x00d8:
            return r1
        L_0x00d9:
            r8 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00d9 }
            throw r8
        L_0x00dc:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00dc }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.elmyra.WestworldLogger$$ExternalSyntheticLambda0.onPullAtom(int, java.util.List):int");
    }
}
