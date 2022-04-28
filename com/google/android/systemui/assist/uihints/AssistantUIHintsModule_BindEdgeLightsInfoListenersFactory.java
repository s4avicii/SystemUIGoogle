package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;
import com.google.android.systemui.assist.uihints.input.NgaInputHandler;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class AssistantUIHintsModule_BindEdgeLightsInfoListenersFactory implements Factory<Set<NgaMessageHandler.EdgeLightsInfoListener>> {
    public final Provider<EdgeLightsController> edgeLightsControllerProvider;
    public final Provider<NgaInputHandler> ngaInputHandlerProvider;

    public final Object get() {
        return new HashSet(Arrays.asList(new NgaMessageHandler.EdgeLightsInfoListener[]{this.edgeLightsControllerProvider.get(), this.ngaInputHandlerProvider.get()}));
    }

    public AssistantUIHintsModule_BindEdgeLightsInfoListenersFactory(Provider<EdgeLightsController> provider, Provider<NgaInputHandler> provider2) {
        this.edgeLightsControllerProvider = provider;
        this.ngaInputHandlerProvider = provider2;
    }
}
