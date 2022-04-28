package com.android.systemui.media.dream;

import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.media.dream.dagger.MediaComplicationComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

public final class MediaDreamComplication implements Complication {
    public MediaComplicationComponent$Factory mComponentFactory;

    public final int getRequiredTypeAvailability() {
        return 16;
    }

    public final Complication.ViewHolder createView(ComplicationViewModel complicationViewModel) {
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.MediaComplicationComponentImpl create = this.mComponentFactory.create();
        Objects.requireNonNull(create);
        return new MediaViewHolder(create.provideComplicationContainerProvider.get(), new MediaComplicationViewController(create.provideComplicationContainerProvider.get(), DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.providesDreamMediaHostProvider.get()), create.provideLayoutParamsProvider.get());
    }

    public MediaDreamComplication(MediaComplicationComponent$Factory mediaComplicationComponent$Factory) {
        this.mComponentFactory = mediaComplicationComponent$Factory;
    }
}
