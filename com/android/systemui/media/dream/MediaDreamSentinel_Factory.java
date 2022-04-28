package com.android.systemui.media.dream;

import android.content.Context;
import android.view.IWindowManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.UnfoldTransitionModule$provideNaturalRotationProgressProvider$1;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class MediaDreamSentinel_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object complicationProvider;
    public final Provider contextProvider;
    public final Provider dreamOverlayStateControllerProvider;
    public final Provider mediaDataManagerProvider;

    public /* synthetic */ MediaDreamSentinel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.mediaDataManagerProvider = provider2;
        this.dreamOverlayStateControllerProvider = provider3;
        this.complicationProvider = provider4;
    }

    public MediaDreamSentinel_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2, Provider provider3) {
        this.$r8$classId = 2;
        this.complicationProvider = unfoldTransitionModule;
        this.contextProvider = provider;
        this.mediaDataManagerProvider = provider2;
        this.dreamOverlayStateControllerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaDreamSentinel((Context) this.contextProvider.get(), (MediaDataManager) this.mediaDataManagerProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), (MediaDreamComplication) ((Provider) this.complicationProvider).get());
            case 1:
                return new ProximitySensorImpl((ThresholdSensor) this.contextProvider.get(), (ThresholdSensor) this.mediaDataManagerProvider.get(), (DelayableExecutor) this.dreamOverlayStateControllerProvider.get(), (Execution) ((Provider) this.complicationProvider).get());
            default:
                Objects.requireNonNull((UnfoldTransitionModule) this.complicationProvider);
                return ((Optional) this.dreamOverlayStateControllerProvider.get()).map(new UnfoldTransitionModule$provideNaturalRotationProgressProvider$1((Context) this.contextProvider.get(), (IWindowManager) this.mediaDataManagerProvider.get()));
        }
    }
}
