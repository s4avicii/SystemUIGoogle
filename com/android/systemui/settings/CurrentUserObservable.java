package com.android.systemui.settings;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.broadcast.BroadcastDispatcher;

public final class CurrentUserObservable {
    public final C10951 mCurrentUser = new MutableLiveData<Integer>() {
        public final void onActive() {
            CurrentUserObservable.this.mTracker.startTracking();
        }

        public final void onInactive() {
            CurrentUserObservable.this.mTracker.stopTracking();
        }
    };
    public final C10962 mTracker;

    public final C10951 getCurrentUser() {
        if (this.mCurrentUser.getValue() == null) {
            this.mCurrentUser.setValue(Integer.valueOf(this.mTracker.getCurrentUserId()));
        }
        return this.mCurrentUser;
    }

    public CurrentUserObservable(BroadcastDispatcher broadcastDispatcher) {
        this.mTracker = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                CurrentUserObservable.this.mCurrentUser.setValue(Integer.valueOf(i));
            }
        };
    }
}
