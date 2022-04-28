package com.android.systemui.screenrecord;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RecordingController implements CallbackController<RecordingStateChangeCallback> {
    public BroadcastDispatcher mBroadcastDispatcher;
    public C10673 mCountDownTimer = null;
    public boolean mIsRecording;
    public boolean mIsStarting;
    public CopyOnWriteArrayList<RecordingStateChangeCallback> mListeners = new CopyOnWriteArrayList<>();
    @VisibleForTesting
    public final BroadcastReceiver mStateChangeReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if (intent != null && "com.android.systemui.screenrecord.UPDATE_STATE".equals(intent.getAction())) {
                if (intent.hasExtra("extra_state")) {
                    RecordingController.this.updateState(intent.getBooleanExtra("extra_state", false));
                    return;
                }
                Log.e("RecordingController", "Received update intent with no state");
            }
        }
    };
    public PendingIntent mStopIntent;
    @VisibleForTesting
    public final BroadcastReceiver mUserChangeReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            RecordingController.this.stopRecording();
        }
    };
    public UserContextProvider mUserContextProvider;

    public interface RecordingStateChangeCallback {
        void onCountdown(long j) {
        }

        void onCountdownEnd() {
        }

        void onRecordingEnd() {
        }

        void onRecordingStart() {
        }
    }

    public final synchronized boolean isRecording() {
        return this.mIsRecording;
    }

    public final synchronized void updateState(boolean z) {
        if (!z) {
            if (this.mIsRecording) {
                this.mBroadcastDispatcher.unregisterReceiver(this.mUserChangeReceiver);
                this.mBroadcastDispatcher.unregisterReceiver(this.mStateChangeReceiver);
            }
        }
        this.mIsRecording = z;
        Iterator<RecordingStateChangeCallback> it = this.mListeners.iterator();
        while (it.hasNext()) {
            RecordingStateChangeCallback next = it.next();
            if (z) {
                next.onRecordingStart();
            } else {
                next.onRecordingEnd();
            }
        }
    }

    public final void addCallback(Object obj) {
        this.mListeners.add((RecordingStateChangeCallback) obj);
    }

    public final void cancelCountdown() {
        C10673 r0 = this.mCountDownTimer;
        if (r0 != null) {
            r0.cancel();
        } else {
            Log.e("RecordingController", "Timer was null");
        }
        this.mIsStarting = false;
        Iterator<RecordingStateChangeCallback> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCountdownEnd();
        }
    }

    public final void removeCallback(Object obj) {
        this.mListeners.remove((RecordingStateChangeCallback) obj);
    }

    public final void stopRecording() {
        try {
            PendingIntent pendingIntent = this.mStopIntent;
            if (pendingIntent != null) {
                pendingIntent.send();
            } else {
                Log.e("RecordingController", "Stop intent was null");
            }
            updateState(false);
        } catch (PendingIntent.CanceledException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Error stopping: ");
            m.append(e.getMessage());
            Log.e("RecordingController", m.toString());
        }
    }

    public RecordingController(BroadcastDispatcher broadcastDispatcher, UserContextProvider userContextProvider) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserContextProvider = userContextProvider;
    }
}
