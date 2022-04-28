package com.google.android.systemui.communal.dreams.dagger;

import dagger.internal.Factory;

public final class SetupDreamModule_ProvidesDreamSettingFactory implements Factory<String> {

    public static final class InstanceHolder {
        public static final SetupDreamModule_ProvidesDreamSettingFactory INSTANCE = new SetupDreamModule_ProvidesDreamSettingFactory();
    }

    public final /* bridge */ /* synthetic */ Object get() {
        return "screensaver_components";
    }
}
