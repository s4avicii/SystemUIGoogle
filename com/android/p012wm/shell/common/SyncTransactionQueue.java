package com.android.p012wm.shell.common;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import android.window.WindowOrganizer;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.transition.LegacyTransitions$LegacyTransition;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.SyncTransactionQueue */
public final class SyncTransactionQueue {
    public SyncCallback mInFlight = null;
    public final ShellExecutor mMainExecutor;
    public final LockIconViewController$$ExternalSyntheticLambda2 mOnReplyTimeout = new LockIconViewController$$ExternalSyntheticLambda2(this, 7);
    public final ArrayList<SyncCallback> mQueue = new ArrayList<>();
    public final ArrayList<TransactionRunnable> mRunnables = new ArrayList<>();
    public final TransactionPool mTransactionPool;

    /* renamed from: com.android.wm.shell.common.SyncTransactionQueue$SyncCallback */
    public class SyncCallback extends WindowContainerTransactionCallback {
        public int mId = -1;
        public final LegacyTransitions$LegacyTransition mLegacyTransition;
        public final WindowContainerTransaction mWCT;

        public SyncCallback(WindowContainerTransaction windowContainerTransaction) {
            this.mWCT = windowContainerTransaction;
            this.mLegacyTransition = null;
        }

        public final void onTransactionReady(int i, SurfaceControl.Transaction transaction) {
            SyncTransactionQueue.this.mMainExecutor.execute(new SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0(this, i, transaction));
        }

        public final void send() {
            SyncTransactionQueue syncTransactionQueue = SyncTransactionQueue.this;
            SyncCallback syncCallback = syncTransactionQueue.mInFlight;
            if (syncCallback != this) {
                if (syncCallback == null) {
                    syncTransactionQueue.mInFlight = this;
                    if (this.mLegacyTransition != null) {
                        WindowOrganizer windowOrganizer = new WindowOrganizer();
                        LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = this.mLegacyTransition;
                        Objects.requireNonNull(legacyTransitions$LegacyTransition);
                        int i = legacyTransitions$LegacyTransition.mTransit;
                        LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition2 = this.mLegacyTransition;
                        Objects.requireNonNull(legacyTransitions$LegacyTransition2);
                        this.mId = windowOrganizer.startLegacyTransition(i, legacyTransitions$LegacyTransition2.mAdapter, this, this.mWCT);
                    } else {
                        this.mId = new WindowOrganizer().applySyncTransaction(this.mWCT, this);
                    }
                    SyncTransactionQueue syncTransactionQueue2 = SyncTransactionQueue.this;
                    syncTransactionQueue2.mMainExecutor.executeDelayed(syncTransactionQueue2.mOnReplyTimeout, 5300);
                    return;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Sync Transactions must be serialized. In Flight: ");
                m.append(SyncTransactionQueue.this.mInFlight.mId);
                m.append(" - ");
                m.append(SyncTransactionQueue.this.mInFlight.mWCT);
                throw new IllegalStateException(m.toString());
            }
        }

        public SyncCallback(SplitScreenController.C19181 r2, WindowContainerTransaction windowContainerTransaction) {
            this.mWCT = windowContainerTransaction;
            this.mLegacyTransition = new LegacyTransitions$LegacyTransition(r2);
        }
    }

    /* renamed from: com.android.wm.shell.common.SyncTransactionQueue$TransactionRunnable */
    public interface TransactionRunnable {
        void runWithTransaction(SurfaceControl.Transaction transaction);
    }

    public final void runInSync(TransactionRunnable transactionRunnable) {
        synchronized (this.mQueue) {
            if (this.mInFlight != null) {
                this.mRunnables.add(transactionRunnable);
                return;
            }
            SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
            transactionRunnable.runWithTransaction(acquire);
            acquire.apply();
            this.mTransactionPool.release(acquire);
        }
    }

    public SyncTransactionQueue(TransactionPool transactionPool, ShellExecutor shellExecutor) {
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
    }

    public final void queue(WindowContainerTransaction windowContainerTransaction) {
        if (!windowContainerTransaction.isEmpty()) {
            SyncCallback syncCallback = new SyncCallback(windowContainerTransaction);
            synchronized (this.mQueue) {
                this.mQueue.add(syncCallback);
                if (this.mQueue.size() == 1) {
                    syncCallback.send();
                }
            }
        }
    }
}
