package com.android.systemui.statusbar.notification.row;

import com.android.systemui.media.MediaFeatureFlag;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl_Factory;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class NotificationContentInflater_Factory implements Factory<NotificationContentInflater> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<ConversationNotificationProcessor> conversationProcessorProvider;
    public final Provider<MediaFeatureFlag> mediaFeatureFlagProvider;
    public final Provider<NotificationRemoteInputManager> remoteInputManagerProvider;
    public final Provider<NotifRemoteViewCache> remoteViewCacheProvider;
    public final Provider<SmartReplyStateInflater> smartRepliesInflaterProvider;

    public static NotificationContentInflater_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, SmartReplyStateInflaterImpl_Factory smartReplyStateInflaterImpl_Factory) {
        return new NotificationContentInflater_Factory(provider, provider2, provider3, provider4, provider5, smartReplyStateInflaterImpl_Factory);
    }

    public final Object get() {
        return new NotificationContentInflater(this.remoteViewCacheProvider.get(), this.remoteInputManagerProvider.get(), this.conversationProcessorProvider.get(), this.mediaFeatureFlagProvider.get(), this.bgExecutorProvider.get(), this.smartRepliesInflaterProvider.get());
    }

    public NotificationContentInflater_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, SmartReplyStateInflaterImpl_Factory smartReplyStateInflaterImpl_Factory) {
        this.remoteViewCacheProvider = provider;
        this.remoteInputManagerProvider = provider2;
        this.conversationProcessorProvider = provider3;
        this.mediaFeatureFlagProvider = provider4;
        this.bgExecutorProvider = provider5;
        this.smartRepliesInflaterProvider = smartReplyStateInflaterImpl_Factory;
    }
}
