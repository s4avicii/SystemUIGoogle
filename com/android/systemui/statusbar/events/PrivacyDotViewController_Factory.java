package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.google.android.systemui.assist.GoogleAssistLogger;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class PrivacyDotViewController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider animationSchedulerProvider;
    public final Provider configurationControllerProvider;
    public final Provider contentInsetsProvider;
    public final Provider mainExecutorProvider;
    public final Provider stateControllerProvider;

    public /* synthetic */ PrivacyDotViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.mainExecutorProvider = provider;
        this.stateControllerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.contentInsetsProvider = provider4;
        this.animationSchedulerProvider = provider5;
    }

    public static PrivacyDotViewController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new PrivacyDotViewController_Factory(provider, provider2, provider3, provider4, provider5, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PrivacyDotViewController((Executor) this.mainExecutorProvider.get(), (StatusBarStateController) this.stateControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (StatusBarContentInsetsProvider) this.contentInsetsProvider.get(), (SystemStatusAnimationScheduler) this.animationSchedulerProvider.get());
            default:
                return new GoogleDefaultUiController((Context) this.mainExecutorProvider.get(), (GoogleAssistLogger) this.stateControllerProvider.get(), (WindowManager) this.configurationControllerProvider.get(), (MetricsLogger) this.contentInsetsProvider.get(), DoubleCheck.lazy(this.animationSchedulerProvider));
        }
    }
}
