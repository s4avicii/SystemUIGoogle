package com.google.android.systemui.assist.uihints.input;

import com.google.android.systemui.assist.uihints.TouchInsideHandler;
import dagger.internal.Factory;
import dagger.internal.SetFactory;
import java.util.Set;
import javax.inject.Provider;

public final class NgaInputHandler_Factory implements Factory<NgaInputHandler> {
    public final Provider<Set<TouchInsideRegion>> dismissablesProvider;
    public final Provider<TouchInsideHandler> touchInsideHandlerProvider;
    public final Provider<Set<TouchActionRegion>> touchablesProvider;

    public final Object get() {
        return new NgaInputHandler(this.touchInsideHandlerProvider.get(), this.touchablesProvider.get(), this.dismissablesProvider.get());
    }

    public NgaInputHandler_Factory(Provider provider, Provider provider2, SetFactory setFactory) {
        this.touchInsideHandlerProvider = provider;
        this.touchablesProvider = provider2;
        this.dismissablesProvider = setFactory;
    }
}
