package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotifPipeline_Factory implements Factory<NotifPipeline> {
    public final Provider<NotifCollection> mNotifCollectionProvider;
    public final Provider<RenderStageManager> mRenderStageManagerProvider;
    public final Provider<ShadeListBuilder> mShadeListBuilderProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;

    public final Object get() {
        return new NotifPipeline(this.notifPipelineFlagsProvider.get(), this.mNotifCollectionProvider.get(), this.mShadeListBuilderProvider.get(), this.mRenderStageManagerProvider.get());
    }

    public NotifPipeline_Factory(Provider<NotifPipelineFlags> provider, Provider<NotifCollection> provider2, Provider<ShadeListBuilder> provider3, Provider<RenderStageManager> provider4) {
        this.notifPipelineFlagsProvider = provider;
        this.mNotifCollectionProvider = provider2;
        this.mShadeListBuilderProvider = provider3;
        this.mRenderStageManagerProvider = provider4;
    }
}
