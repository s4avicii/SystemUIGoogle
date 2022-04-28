package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class RowContentBindStage_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider binderProvider;
    public final Provider errorManagerProvider;
    public final Provider loggerProvider;

    public /* synthetic */ RowContentBindStage_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.binderProvider = provider;
        this.errorManagerProvider = provider2;
        this.loggerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new RowContentBindStage((NotificationRowContentBinder) this.binderProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (RowContentBindStageLogger) this.loggerProvider.get());
            default:
                return new BubbleCoordinator((Optional) this.binderProvider.get(), (Optional) this.errorManagerProvider.get(), (NotifCollection) this.loggerProvider.get());
        }
    }
}
