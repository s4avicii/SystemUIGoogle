package com.google.android.systemui.communal.dreams.dagger;

import android.content.Intent;
import dagger.internal.Factory;

public final class SetupDreamModule_ProvidesDreamSettingIntentFactory implements Factory<Intent> {

    public static final class InstanceHolder {
        public static final SetupDreamModule_ProvidesDreamSettingIntentFactory INSTANCE = new SetupDreamModule_ProvidesDreamSettingIntentFactory();
    }

    public final Object get() {
        return new Intent("android.settings.DREAM_SETTINGS");
    }
}
