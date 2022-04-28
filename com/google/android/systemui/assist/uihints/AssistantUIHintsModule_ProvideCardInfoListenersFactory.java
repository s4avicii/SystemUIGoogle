package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class AssistantUIHintsModule_ProvideCardInfoListenersFactory implements Factory<Set<NgaMessageHandler.CardInfoListener>> {
    public final Provider<GlowController> glowControllerProvider;
    public final Provider<LightnessProvider> lightnessProvider;
    public final Provider<ScrimController> scrimControllerProvider;
    public final Provider<TranscriptionController> transcriptionControllerProvider;

    public final Object get() {
        return new HashSet(Arrays.asList(new NgaMessageHandler.CardInfoListener[]{this.glowControllerProvider.get(), this.scrimControllerProvider.get(), this.transcriptionControllerProvider.get(), this.lightnessProvider.get()}));
    }

    public AssistantUIHintsModule_ProvideCardInfoListenersFactory(Provider<GlowController> provider, Provider<ScrimController> provider2, Provider<TranscriptionController> provider3, Provider<LightnessProvider> provider4) {
        this.glowControllerProvider = provider;
        this.scrimControllerProvider = provider2;
        this.transcriptionControllerProvider = provider3;
        this.lightnessProvider = provider4;
    }
}
