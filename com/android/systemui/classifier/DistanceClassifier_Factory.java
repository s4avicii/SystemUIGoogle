package com.android.systemui.classifier;

import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.util.DeviceConfigProxy;
import com.google.android.systemui.assist.uihints.NavBarFader;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DistanceClassifier_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dataProvider;
    public final Object deviceConfigProxyProvider;

    public /* synthetic */ DistanceClassifier_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.dataProvider = provider;
        this.deviceConfigProxyProvider = provider2;
    }

    public DistanceClassifier_Factory(DependencyProvider dependencyProvider, Provider provider) {
        this.$r8$classId = 3;
        this.deviceConfigProxyProvider = dependencyProvider;
        this.dataProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DistanceClassifier((FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) ((Provider) this.deviceConfigProxyProvider).get());
            case 1:
                return new NavBarFader((NavigationBarController) this.dataProvider.get(), (Handler) ((Provider) this.deviceConfigProxyProvider).get());
            case 2:
                return new SilenceAlertsDisabled((Context) this.dataProvider.get(), (ColumbusSettings) ((Provider) this.deviceConfigProxyProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.deviceConfigProxyProvider);
                return new AmbientDisplayConfiguration((Context) this.dataProvider.get());
        }
    }
}
