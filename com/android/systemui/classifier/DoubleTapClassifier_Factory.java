package com.android.systemui.classifier;

import android.content.Context;
import android.os.Handler;
import android.view.IWindowManager;
import com.android.systemui.classifier.FalsingModule_ProvidesDoubleTapTimeoutMsFactory;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.media.MediaBrowserFactory_Factory;
import com.android.systemui.statusbar.phone.AutoHideController;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DoubleTapClassifier_Factory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Provider dataProvider;
    public final Provider doubleTapSlopProvider;
    public final Object doubleTapTimeMsProvider;
    public final Provider singleTapClassifierProvider;

    public DoubleTapClassifier_Factory(Provider provider, Provider provider2, MediaBrowserFactory_Factory mediaBrowserFactory_Factory) {
        FalsingModule_ProvidesDoubleTapTimeoutMsFactory falsingModule_ProvidesDoubleTapTimeoutMsFactory = FalsingModule_ProvidesDoubleTapTimeoutMsFactory.InstanceHolder.INSTANCE;
        this.dataProvider = provider;
        this.singleTapClassifierProvider = provider2;
        this.doubleTapSlopProvider = mediaBrowserFactory_Factory;
        this.doubleTapTimeMsProvider = falsingModule_ProvidesDoubleTapTimeoutMsFactory;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DoubleTapClassifier((FalsingDataProvider) this.dataProvider.get(), (SingleTapClassifier) this.singleTapClassifierProvider.get(), ((Float) this.doubleTapSlopProvider.get()).floatValue(), ((Long) ((Provider) this.doubleTapTimeMsProvider).get()).longValue());
            default:
                Objects.requireNonNull((DependencyProvider) this.doubleTapTimeMsProvider);
                return new AutoHideController((Context) this.dataProvider.get(), (Handler) this.singleTapClassifierProvider.get(), (IWindowManager) this.doubleTapSlopProvider.get());
        }
    }

    public DoubleTapClassifier_Factory(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3) {
        this.doubleTapTimeMsProvider = dependencyProvider;
        this.dataProvider = provider;
        this.singleTapClassifierProvider = provider2;
        this.doubleTapSlopProvider = provider3;
    }
}
