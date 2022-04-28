package com.android.systemui.communal.conditions;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.condition.Condition;
import com.android.systemui.util.settings.SecureSettings;

public final class CommunalSettingCondition extends Condition {
    public final C07351 mCommunalSettingContentObserver;
    public final SecureSettings mSecureSettings;

    public final void start() {
        this.mSecureSettings.registerContentObserverForUser(this.mCommunalSettingContentObserver);
        this.mCommunalSettingContentObserver.onChange(false);
    }

    public final void stop() {
        this.mSecureSettings.unregisterContentObserver(this.mCommunalSettingContentObserver);
    }

    public CommunalSettingCondition(Handler handler, SecureSettings secureSettings) {
        this.mSecureSettings = secureSettings;
        this.mCommunalSettingContentObserver = new ContentObserver(handler) {
            public final void onChange(boolean z) {
                boolean z2 = false;
                if (CommunalSettingCondition.this.mSecureSettings.getIntForUser("communal_mode_enabled", 0, 0) == 1) {
                    z2 = true;
                }
                CommunalSettingCondition.this.updateCondition(z2);
            }
        };
    }
}
