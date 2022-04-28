package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class UnpinNotifications extends Action {
    public boolean mHasPinnedHeadsUp;
    public final C22441 mHeadsUpChangedListener = new OnHeadsUpChangedListener() {
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            UnpinNotifications unpinNotifications = UnpinNotifications.this;
            if (unpinNotifications.mHasPinnedHeadsUp != z) {
                unpinNotifications.mHasPinnedHeadsUp = z;
                unpinNotifications.notifyListener();
            }
        }
    };
    public final Optional<HeadsUpManager> mHeadsUpManagerOptional;
    public boolean mSilenceSettingEnabled;

    public UnpinNotifications(Context context, Optional<HeadsUpManager> optional) {
        super(context, (ArrayList) null);
        this.mHeadsUpManagerOptional = optional;
        if (optional.isPresent()) {
            updateHeadsUpListener();
            new UserContentObserver(this.mContext, Settings.Secure.getUriFor("assist_gesture_silence_alerts_enabled"), new UnpinNotifications$$ExternalSyntheticLambda0(this, 0), true);
            return;
        }
        Log.w("Elmyra/UnpinNotifications", "No HeadsUpManager");
    }

    public final boolean isAvailable() {
        if (this.mSilenceSettingEnabled) {
            return this.mHasPinnedHeadsUp;
        }
        return false;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.mHeadsUpManagerOptional.ifPresent(UnpinNotifications$$ExternalSyntheticLambda1.INSTANCE);
    }

    public final void updateHeadsUpListener() {
        if (this.mHeadsUpManagerOptional.isPresent()) {
            boolean z = true;
            if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_gesture_silence_alerts_enabled", 1, -2) == 0) {
                z = false;
            }
            if (this.mSilenceSettingEnabled != z) {
                this.mSilenceSettingEnabled = z;
                if (z) {
                    HeadsUpManager headsUpManager = this.mHeadsUpManagerOptional.get();
                    Objects.requireNonNull(headsUpManager);
                    this.mHasPinnedHeadsUp = headsUpManager.mHasPinnedNotification;
                    this.mHeadsUpManagerOptional.get().addListener(this.mHeadsUpChangedListener);
                } else {
                    this.mHasPinnedHeadsUp = false;
                    HeadsUpManager headsUpManager2 = this.mHeadsUpManagerOptional.get();
                    C22441 r1 = this.mHeadsUpChangedListener;
                    Objects.requireNonNull(headsUpManager2);
                    headsUpManager2.mListeners.remove(r1);
                }
                notifyListener();
            }
        }
    }

    public final String toString() {
        return super.toString();
    }
}
