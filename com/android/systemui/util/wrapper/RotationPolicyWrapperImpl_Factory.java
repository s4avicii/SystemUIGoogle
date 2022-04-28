package com.android.systemui.util.wrapper;

import android.content.Context;
import android.view.Choreographer;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractor;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class RotationPolicyWrapperImpl_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider secureSettingsProvider;

    public /* synthetic */ RotationPolicyWrapperImpl_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.secureSettingsProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new RotationPolicyWrapperImpl((Context) this.contextProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
            case 1:
                return new NotifPipelineChoreographerImpl((Choreographer) this.contextProvider.get(), (DelayableExecutor) this.secureSettingsProvider.get());
            case 2:
                return new NotificationVisibilityProviderImpl((NotifLiveDataStore) this.contextProvider.get(), (CommonNotifCollection) this.secureSettingsProvider.get());
            default:
                return new PeopleNotificationIdentifierImpl((NotificationPersonExtractor) this.contextProvider.get(), (GroupMembershipManager) this.secureSettingsProvider.get());
        }
    }
}
