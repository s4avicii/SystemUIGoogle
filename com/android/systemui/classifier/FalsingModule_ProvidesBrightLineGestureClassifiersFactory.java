package com.android.systemui.classifier;

import com.android.keyguard.KeyguardUnfoldTransition_Factory;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class FalsingModule_ProvidesBrightLineGestureClassifiersFactory implements Factory<Set<FalsingClassifier>> {
    public final Provider<DiagonalClassifier> diagonalClassifierProvider;
    public final Provider<DistanceClassifier> distanceClassifierProvider;
    public final Provider<PointerCountClassifier> pointerCountClassifierProvider;
    public final Provider<ProximityClassifier> proximityClassifierProvider;
    public final Provider<TypeClassifier> typeClassifierProvider;
    public final Provider<ZigZagClassifier> zigZagClassifierProvider;

    public static FalsingModule_ProvidesBrightLineGestureClassifiersFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, KeyguardUnfoldTransition_Factory keyguardUnfoldTransition_Factory) {
        return new FalsingModule_ProvidesBrightLineGestureClassifiersFactory(provider, provider2, provider3, provider4, provider5, keyguardUnfoldTransition_Factory);
    }

    public final Object get() {
        DistanceClassifier distanceClassifier = this.distanceClassifierProvider.get();
        ProximityClassifier proximityClassifier = this.proximityClassifierProvider.get();
        return new HashSet(Arrays.asList(new FalsingClassifier[]{this.pointerCountClassifierProvider.get(), this.typeClassifierProvider.get(), this.diagonalClassifierProvider.get(), distanceClassifier, proximityClassifier, this.zigZagClassifierProvider.get()}));
    }

    public FalsingModule_ProvidesBrightLineGestureClassifiersFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, KeyguardUnfoldTransition_Factory keyguardUnfoldTransition_Factory) {
        this.distanceClassifierProvider = provider;
        this.proximityClassifierProvider = provider2;
        this.pointerCountClassifierProvider = provider3;
        this.typeClassifierProvider = provider4;
        this.diagonalClassifierProvider = provider5;
        this.zigZagClassifierProvider = keyguardUnfoldTransition_Factory;
    }
}
