package com.android.systemui.util.service;

import android.util.Log;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.service.ObservableServiceConnection;
import com.android.systemui.util.time.SystemClock;

public final class PersistentConnectionManager<T> {
    public static final boolean DEBUG = Log.isLoggable("PersistentConnManager", 3);
    public final int mBaseReconnectDelayMs;
    public final C17101 mConnectRunnable = new Runnable() {
        public final void run() {
            PersistentConnectionManager persistentConnectionManager = PersistentConnectionManager.this;
            persistentConnectionManager.mCurrentReconnectCancelable = null;
            persistentConnectionManager.mConnection.bind();
        }
    };
    public final ObservableServiceConnection<T> mConnection;
    public final C17112 mConnectionCallback = new ObservableServiceConnection.Callback() {
        public long mStartTime;

        public final void onConnected(Object obj) {
            this.mStartTime = PersistentConnectionManager.this.mSystemClock.currentTimeMillis();
        }

        public final void onDisconnected() {
            PersistentConnectionManager persistentConnectionManager = PersistentConnectionManager.this;
            if (PersistentConnectionManager.this.mSystemClock.currentTimeMillis() - this.mStartTime > ((long) persistentConnectionManager.mMinConnectionDuration)) {
                persistentConnectionManager.mReconnectAttempts = 0;
                persistentConnectionManager.mConnection.bind();
                return;
            }
            Runnable runnable = persistentConnectionManager.mCurrentReconnectCancelable;
            if (runnable != null) {
                runnable.run();
                persistentConnectionManager.mCurrentReconnectCancelable = null;
            }
            int i = persistentConnectionManager.mReconnectAttempts;
            if (i < persistentConnectionManager.mMaxReconnectAttempts) {
                long scalb = (long) Math.scalb((float) persistentConnectionManager.mBaseReconnectDelayMs, i);
                if (PersistentConnectionManager.DEBUG) {
                    Log.d("PersistentConnManager", "scheduling connection attempt in " + scalb + "milliseconds");
                }
                persistentConnectionManager.mCurrentReconnectCancelable = persistentConnectionManager.mMainExecutor.executeDelayed(persistentConnectionManager.mConnectRunnable, scalb);
                persistentConnectionManager.mReconnectAttempts++;
            } else if (PersistentConnectionManager.DEBUG) {
                Log.d("PersistentConnManager", "exceeded max connection attempts.");
            }
        }
    };
    public Runnable mCurrentReconnectCancelable;
    public final DelayableExecutor mMainExecutor;
    public final int mMaxReconnectAttempts;
    public final int mMinConnectionDuration;
    public final Observer mObserver;
    public final PersistentConnectionManager$$ExternalSyntheticLambda0 mObserverCallback = new PersistentConnectionManager$$ExternalSyntheticLambda0(this);
    public int mReconnectAttempts = 0;
    public final SystemClock mSystemClock;

    public PersistentConnectionManager(SystemClock systemClock, DelayableExecutor delayableExecutor, ObservableServiceConnection observableServiceConnection, int i, int i2, int i3, PackageObserver packageObserver) {
        this.mSystemClock = systemClock;
        this.mMainExecutor = delayableExecutor;
        this.mConnection = observableServiceConnection;
        this.mObserver = packageObserver;
        this.mMaxReconnectAttempts = i;
        this.mBaseReconnectDelayMs = i2;
        this.mMinConnectionDuration = i3;
    }
}
