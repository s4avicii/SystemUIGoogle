package com.android.systemui.media;

import android.content.Context;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.p006qs.customize.QSCustomizerController_Factory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class MediaControlPanel_Factory implements Factory<MediaControlPanel> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<MediaCarouselController> mediaCarouselControllerProvider;
    public final Provider<MediaDataManager> mediaDataManagerProvider;
    public final Provider<MediaFlags> mediaFlagsProvider;
    public final Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
    public final Provider<MediaViewController> mediaViewControllerProvider;
    public final Provider<SeekBarViewModel> seekBarViewModelProvider;
    public final Provider<SystemClock> systemClockProvider;

    public static MediaControlPanel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, QSCustomizerController_Factory qSCustomizerController_Factory, DelegateFactory delegateFactory, Provider provider7, Provider provider8, Provider provider9) {
        return new MediaControlPanel_Factory(provider, provider2, provider3, provider4, provider5, provider6, qSCustomizerController_Factory, delegateFactory, provider7, provider8, provider9);
    }

    public final Object get() {
        return new MediaControlPanel(this.contextProvider.get(), this.backgroundExecutorProvider.get(), this.activityStarterProvider.get(), this.mediaViewControllerProvider.get(), this.seekBarViewModelProvider.get(), DoubleCheck.lazy(this.mediaDataManagerProvider), this.mediaOutputDialogFactoryProvider.get(), this.mediaCarouselControllerProvider.get(), this.falsingManagerProvider.get(), this.mediaFlagsProvider.get(), this.systemClockProvider.get());
    }

    public MediaControlPanel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, QSCustomizerController_Factory qSCustomizerController_Factory, DelegateFactory delegateFactory, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.activityStarterProvider = provider3;
        this.mediaViewControllerProvider = provider4;
        this.seekBarViewModelProvider = provider5;
        this.mediaDataManagerProvider = provider6;
        this.mediaOutputDialogFactoryProvider = qSCustomizerController_Factory;
        this.mediaCarouselControllerProvider = delegateFactory;
        this.falsingManagerProvider = provider7;
        this.mediaFlagsProvider = provider8;
        this.systemClockProvider = provider9;
    }
}
