package com.google.android.systemui.communal.dock.dagger;

import com.android.systemui.util.condition.Condition;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroFeatureCondition;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroSettingCondition;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class DockModule_ProvideTimeoutToUserZeroPreconditionsFactory implements Factory<Set<Condition>> {
    public final Provider<TimeoutToUserZeroFeatureCondition> featureEnabledConditionProvider;
    public final Provider<TimeoutToUserZeroSettingCondition> settingsEnabledConditionProvider;

    public final Object get() {
        return new HashSet(Arrays.asList(new Condition[]{this.featureEnabledConditionProvider.get(), this.settingsEnabledConditionProvider.get()}));
    }

    public DockModule_ProvideTimeoutToUserZeroPreconditionsFactory(Provider<TimeoutToUserZeroFeatureCondition> provider, Provider<TimeoutToUserZeroSettingCondition> provider2) {
        this.featureEnabledConditionProvider = provider;
        this.settingsEnabledConditionProvider = provider2;
    }
}
