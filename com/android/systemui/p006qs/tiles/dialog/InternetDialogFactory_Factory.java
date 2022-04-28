package com.android.systemui.p006qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogFactory_Factory */
public final class InternetDialogFactory_Factory implements Factory<InternetDialogFactory> {
    public final Provider<Context> contextProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<InternetDialogController> internetDialogControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public static InternetDialogFactory_Factory create(Provider provider, Provider provider2, InternetDialogController_Factory internetDialogController_Factory, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new InternetDialogFactory_Factory(provider, provider2, internetDialogController_Factory, provider3, provider4, provider5, provider6);
    }

    public final Object get() {
        return new InternetDialogFactory(this.handlerProvider.get(), this.executorProvider.get(), this.internetDialogControllerProvider.get(), this.contextProvider.get(), this.uiEventLoggerProvider.get(), this.dialogLaunchAnimatorProvider.get(), this.keyguardStateControllerProvider.get());
    }

    public InternetDialogFactory_Factory(Provider provider, Provider provider2, InternetDialogController_Factory internetDialogController_Factory, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.handlerProvider = provider;
        this.executorProvider = provider2;
        this.internetDialogControllerProvider = internetDialogController_Factory;
        this.contextProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.dialogLaunchAnimatorProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
    }
}
