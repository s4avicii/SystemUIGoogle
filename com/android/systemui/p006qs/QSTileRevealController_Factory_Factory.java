package com.android.systemui.p006qs;

import android.content.Context;
import com.android.systemui.p006qs.QSTileRevealController;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSTileRevealController_Factory_Factory */
public final class QSTileRevealController_Factory_Factory implements Factory<QSTileRevealController.Factory> {
    public final Provider<Context> contextProvider;
    public final Provider<QSCustomizerController> qsCustomizerControllerProvider;

    public final Object get() {
        return new QSTileRevealController.Factory(this.contextProvider.get(), this.qsCustomizerControllerProvider.get());
    }

    public QSTileRevealController_Factory_Factory(Provider<Context> provider, Provider<QSCustomizerController> provider2) {
        this.contextProvider = provider;
        this.qsCustomizerControllerProvider = provider2;
    }
}
