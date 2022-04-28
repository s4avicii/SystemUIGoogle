package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.classifier.TypeClassifier_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class PreparationCoordinator_Factory implements Factory<PreparationCoordinator> {
    public final Provider<NotifUiAdjustmentProvider> adjustmentProvider;
    public final Provider<BindEventManagerImpl> bindEventManagerProvider;
    public final Provider<NotifInflationErrorManager> errorManagerProvider;
    public final Provider<PreparationCoordinatorLogger> loggerProvider;
    public final Provider<NotifInflater> notifInflaterProvider;
    public final Provider<IStatusBarService> serviceProvider;
    public final Provider<NotifViewBarn> viewBarnProvider;

    public static PreparationCoordinator_Factory create(TypeClassifier_Factory typeClassifier_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new PreparationCoordinator_Factory(typeClassifier_Factory, provider, provider2, provider3, provider4, provider5, provider6);
    }

    public final Object get() {
        return new PreparationCoordinator(this.loggerProvider.get(), this.notifInflaterProvider.get(), this.errorManagerProvider.get(), this.viewBarnProvider.get(), this.adjustmentProvider.get(), this.serviceProvider.get(), this.bindEventManagerProvider.get(), 9, 500);
    }

    public PreparationCoordinator_Factory(TypeClassifier_Factory typeClassifier_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.loggerProvider = typeClassifier_Factory;
        this.notifInflaterProvider = provider;
        this.errorManagerProvider = provider2;
        this.viewBarnProvider = provider3;
        this.adjustmentProvider = provider4;
        this.serviceProvider = provider5;
        this.bindEventManagerProvider = provider6;
    }
}
