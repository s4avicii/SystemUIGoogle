package com.android.systemui.media;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallFlags;
import com.google.android.systemui.columbus.feedback.UserActivity;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class MediaFlags_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider featureFlagsProvider;

    public /* synthetic */ MediaFlags_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.featureFlagsProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaFlags((FeatureFlags) this.featureFlagsProvider.get());
            case 1:
                NotificationManager notificationManager = (NotificationManager) ((Context) this.featureFlagsProvider.get()).getSystemService(NotificationManager.class);
                Objects.requireNonNull(notificationManager, "Cannot return null from a non-@Nullable @Provides method");
                return notificationManager;
            case 2:
                return new TargetSdkResolver((Context) this.featureFlagsProvider.get());
            case 3:
                return new OngoingCallFlags((FeatureFlags) this.featureFlagsProvider.get());
            default:
                return new UserActivity(DoubleCheck.lazy(this.featureFlagsProvider));
        }
    }
}
