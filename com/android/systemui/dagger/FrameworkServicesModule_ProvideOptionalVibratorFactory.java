package com.android.systemui.dagger;

import android.content.Context;
import android.os.Vibrator;
import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationHandler;
import com.android.systemui.statusbar.p007tv.notifications.TvNotificationPanelActivity;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class FrameworkServicesModule_ProvideOptionalVibratorFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ FrameworkServicesModule_ProvideOptionalVibratorFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional ofNullable = Optional.ofNullable((Vibrator) ((Context) this.contextProvider.get()).getSystemService(Vibrator.class));
                Objects.requireNonNull(ofNullable, "Cannot return null from a non-@Nullable @Provides method");
                return ofNullable;
            case 1:
                return new NotifUiAdjustmentProvider((SectionClassifier) this.contextProvider.get());
            case 2:
                return new TvNotificationPanelActivity((TvNotificationHandler) this.contextProvider.get());
            default:
                Executor mainExecutor = ((Context) this.contextProvider.get()).getMainExecutor();
                Objects.requireNonNull(mainExecutor, "Cannot return null from a non-@Nullable @Provides method");
                return mainExecutor;
        }
    }
}
