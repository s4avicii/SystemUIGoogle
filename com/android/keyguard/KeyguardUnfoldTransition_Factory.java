package com.android.keyguard;

import android.content.Context;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.ZigZagClassifier;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardUnfoldTransition_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider unfoldProgressProvider;

    public /* synthetic */ KeyguardUnfoldTransition_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.unfoldProgressProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyguardUnfoldTransition((Context) this.contextProvider.get(), (NaturalRotationUnfoldProgressProvider) this.unfoldProgressProvider.get());
            default:
                return new ZigZagClassifier((FalsingDataProvider) this.contextProvider.get(), (DeviceConfigProxy) this.unfoldProgressProvider.get());
        }
    }
}
