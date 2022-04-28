package com.android.p012wm.shell.common;

import android.view.SurfaceControl;
import com.android.p012wm.shell.common.SyncTransactionQueue;

/* renamed from: com.android.wm.shell.common.SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SyncTransactionQueue.SyncCallback f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;

    public /* synthetic */ SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0(SyncTransactionQueue.SyncCallback syncCallback, int i, SurfaceControl.Transaction transaction) {
        this.f$0 = syncCallback;
        this.f$1 = i;
        this.f$2 = transaction;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r7 = this;
            com.android.wm.shell.common.SyncTransactionQueue$SyncCallback r0 = r7.f$0
            int r1 = r7.f$1
            android.view.SurfaceControl$Transaction r7 = r7.f$2
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.common.SyncTransactionQueue r2 = com.android.p012wm.shell.common.SyncTransactionQueue.this
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r2 = r2.mQueue
            monitor-enter(r2)
            int r3 = r0.mId     // Catch:{ all -> 0x00b3 }
            if (r3 == r1) goto L_0x0035
            java.lang.String r7 = "SyncTransactionQueue"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b3 }
            r3.<init>()     // Catch:{ all -> 0x00b3 }
            java.lang.String r4 = "Got an unexpected onTransactionReady. Expected "
            r3.append(r4)     // Catch:{ all -> 0x00b3 }
            int r0 = r0.mId     // Catch:{ all -> 0x00b3 }
            r3.append(r0)     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = " but got "
            r3.append(r0)     // Catch:{ all -> 0x00b3 }
            r3.append(r1)     // Catch:{ all -> 0x00b3 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00b3 }
            android.util.Slog.e(r7, r0)     // Catch:{ all -> 0x00b3 }
            monitor-exit(r2)     // Catch:{ all -> 0x00b3 }
            goto L_0x00b2
        L_0x0035:
            com.android.wm.shell.common.SyncTransactionQueue r1 = com.android.p012wm.shell.common.SyncTransactionQueue.this     // Catch:{ all -> 0x00b3 }
            r3 = 0
            r1.mInFlight = r3     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.common.ShellExecutor r3 = r1.mMainExecutor     // Catch:{ all -> 0x00b3 }
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2 r1 = r1.mOnReplyTimeout     // Catch:{ all -> 0x00b3 }
            r3.removeCallbacks(r1)     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.common.SyncTransactionQueue r1 = com.android.p012wm.shell.common.SyncTransactionQueue.this     // Catch:{ all -> 0x00b3 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r1 = r1.mQueue     // Catch:{ all -> 0x00b3 }
            r1.remove(r0)     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.common.SyncTransactionQueue r1 = com.android.p012wm.shell.common.SyncTransactionQueue.this     // Catch:{ all -> 0x00b3 }
            java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00b3 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$TransactionRunnable> r3 = r1.mRunnables     // Catch:{ all -> 0x00b3 }
            int r3 = r3.size()     // Catch:{ all -> 0x00b3 }
            r4 = 0
            r5 = r4
        L_0x0055:
            if (r5 >= r3) goto L_0x0065
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$TransactionRunnable> r6 = r1.mRunnables     // Catch:{ all -> 0x00b3 }
            java.lang.Object r6 = r6.get(r5)     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.common.SyncTransactionQueue$TransactionRunnable r6 = (com.android.p012wm.shell.common.SyncTransactionQueue.TransactionRunnable) r6     // Catch:{ all -> 0x00b3 }
            r6.runWithTransaction(r7)     // Catch:{ all -> 0x00b3 }
            int r5 = r5 + 1
            goto L_0x0055
        L_0x0065:
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$TransactionRunnable> r1 = r1.mRunnables     // Catch:{ all -> 0x00b3 }
            java.util.List r1 = r1.subList(r4, r3)     // Catch:{ all -> 0x00b3 }
            r1.clear()     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.transition.LegacyTransitions$LegacyTransition r1 = r0.mLegacyTransition     // Catch:{ all -> 0x00b3 }
            if (r1 == 0) goto L_0x0094
            com.android.wm.shell.transition.LegacyTransitions$LegacyTransition$SyncCallback r1 = r1.mSyncCallback     // Catch:{ RemoteException -> 0x007a }
            int r3 = r0.mId     // Catch:{ RemoteException -> 0x007a }
            r1.onTransactionReady(r3, r7)     // Catch:{ RemoteException -> 0x007a }
            goto L_0x009a
        L_0x007a:
            r7 = move-exception
            java.lang.String r1 = "SyncTransactionQueue"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b3 }
            r3.<init>()     // Catch:{ all -> 0x00b3 }
            java.lang.String r5 = "Error sending callback to legacy transition: "
            r3.append(r5)     // Catch:{ all -> 0x00b3 }
            int r5 = r0.mId     // Catch:{ all -> 0x00b3 }
            r3.append(r5)     // Catch:{ all -> 0x00b3 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b3 }
            android.util.Slog.e(r1, r3, r7)     // Catch:{ all -> 0x00b3 }
            goto L_0x009a
        L_0x0094:
            r7.apply()     // Catch:{ all -> 0x00b3 }
            r7.close()     // Catch:{ all -> 0x00b3 }
        L_0x009a:
            com.android.wm.shell.common.SyncTransactionQueue r7 = com.android.p012wm.shell.common.SyncTransactionQueue.this     // Catch:{ all -> 0x00b3 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r7 = r7.mQueue     // Catch:{ all -> 0x00b3 }
            boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x00b3 }
            if (r7 != 0) goto L_0x00b1
            com.android.wm.shell.common.SyncTransactionQueue r7 = com.android.p012wm.shell.common.SyncTransactionQueue.this     // Catch:{ all -> 0x00b3 }
            java.util.ArrayList<com.android.wm.shell.common.SyncTransactionQueue$SyncCallback> r7 = r7.mQueue     // Catch:{ all -> 0x00b3 }
            java.lang.Object r7 = r7.get(r4)     // Catch:{ all -> 0x00b3 }
            com.android.wm.shell.common.SyncTransactionQueue$SyncCallback r7 = (com.android.p012wm.shell.common.SyncTransactionQueue.SyncCallback) r7     // Catch:{ all -> 0x00b3 }
            r7.send()     // Catch:{ all -> 0x00b3 }
        L_0x00b1:
            monitor-exit(r2)     // Catch:{ all -> 0x00b3 }
        L_0x00b2:
            return
        L_0x00b3:
            r7 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00b3 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0.run():void");
    }
}
