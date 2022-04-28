package com.google.android.systemui.communal.dock.conditions;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.condition.Condition;
import com.android.systemui.util.settings.SecureSettings;
import javax.inject.Provider;

public final class TimeoutToUserZeroSettingCondition extends Condition {
    public final SecureSettings mSecureSettings;
    public final C22061 mSettingContentObserver;
    public final Provider<Integer> mTimeoutDurationSettingProvider;
    public final Provider<Integer> mUserIdProvider;

    public final void start() {
        this.mSettingContentObserver.onChange(true);
        this.mSecureSettings.registerContentObserverForUser("timeout_to_user_zero", (ContentObserver) this.mSettingContentObserver, this.mUserIdProvider.get().intValue());
    }

    public final void stop() {
        this.mSecureSettings.unregisterContentObserver(this.mSettingContentObserver);
    }

    public TimeoutToUserZeroSettingCondition(Handler handler, SecureSettings secureSettings, Provider<Integer> provider, Provider<Integer> provider2) {
        this.mSecureSettings = secureSettings;
        this.mTimeoutDurationSettingProvider = provider;
        this.mUserIdProvider = provider2;
        this.mSettingContentObserver = new ContentObserver(handler) {
            public final void onChange(boolean z) {
                boolean z2;
                TimeoutToUserZeroSettingCondition timeoutToUserZeroSettingCondition = TimeoutToUserZeroSettingCondition.this;
                if (timeoutToUserZeroSettingCondition.mTimeoutDurationSettingProvider.get().intValue() > 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                timeoutToUserZeroSettingCondition.updateCondition(z2);
            }
        };
    }
}
