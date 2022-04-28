package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class AssistantUIHintsModule_ProvideAudioInfoListenersFactory implements Factory<Set<NgaMessageHandler.AudioInfoListener>> {
    public final Provider<EdgeLightsController> edgeLightsControllerProvider;
    public final Provider<GlowController> glowControllerProvider;

    public final Object get() {
        return new HashSet(Arrays.asList(new NgaMessageHandler.AudioInfoListener[]{this.edgeLightsControllerProvider.get(), this.glowControllerProvider.get()}));
    }

    public AssistantUIHintsModule_ProvideAudioInfoListenersFactory(Provider<EdgeLightsController> provider, Provider<GlowController> provider2) {
        this.edgeLightsControllerProvider = provider;
        this.glowControllerProvider = provider2;
    }
}
