package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dreams.dagger.DreamOverlayModule$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.policy.ExtensionController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class AssistModule_ProvideAssistUtilsFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ AssistModule_ProvideAssistUtilsFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new AssistUtils((Context) this.contextProvider.get());
            case 1:
                return new DreamOverlayModule$$ExternalSyntheticLambda0(DoubleCheck.lazy(this.contextProvider));
            case 2:
                return new NotifLiveDataStoreImpl((Executor) this.contextProvider.get());
            case 3:
                return new NotificationPersonExtractorPluginBoundary((ExtensionController) this.contextProvider.get());
            default:
                NotificationShadeWindowView notificationShadeWindowView = (NotificationShadeWindowView) this.contextProvider.get();
                Objects.requireNonNull(notificationShadeWindowView);
                NotificationPanelView notificationPanelView = (NotificationPanelView) notificationShadeWindowView.findViewById(C1777R.C1779id.notification_panel);
                Objects.requireNonNull(notificationPanelView, "Cannot return null from a non-@Nullable @Provides method");
                return notificationPanelView;
        }
    }
}
