package com.android.systemui.power;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.unfold.UnfoldTransitionModule;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class PowerNotificationWarnings_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object activityStarterProvider;
    public final Provider contextProvider;

    public /* synthetic */ PowerNotificationWarnings_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.activityStarterProvider = provider2;
    }

    public PowerNotificationWarnings_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider) {
        this.$r8$classId = 2;
        this.activityStarterProvider = unfoldTransitionModule;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PowerNotificationWarnings((Context) this.contextProvider.get(), (ActivityStarter) ((Provider) this.activityStarterProvider).get());
            case 1:
                Context context = (Context) this.contextProvider.get();
                Notification build = new Notification.Builder(context, "ALR").setSmallIcon(C1777R.C1778drawable.ic_settings).setContentTitle(context.getString(C1777R.string.dream_settings_notification_title)).setContentText(context.getString(C1777R.string.dream_settings_notification_text)).setContentIntent((PendingIntent) ((Provider) this.activityStarterProvider).get()).setAutoCancel(true).build();
                Objects.requireNonNull(build, "Cannot return null from a non-@Nullable @Provides method");
                return build;
            default:
                LifecycleScreenStatusProvider lifecycleScreenStatusProvider = (LifecycleScreenStatusProvider) this.contextProvider.get();
                Objects.requireNonNull((UnfoldTransitionModule) this.activityStarterProvider);
                return lifecycleScreenStatusProvider;
        }
    }
}
