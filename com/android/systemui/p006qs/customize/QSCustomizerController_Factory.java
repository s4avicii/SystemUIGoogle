package com.android.systemui.p006qs.customize;

import android.content.Context;
import android.media.session.MediaSessionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.customize.QSCustomizerController_Factory */
public final class QSCustomizerController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider configurationControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider lightBarControllerProvider;
    public final Provider qsTileHostProvider;
    public final Provider screenLifecycleProvider;
    public final Provider tileAdapterProvider;
    public final Provider tileQueryHelperProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider viewProvider;

    public /* synthetic */ QSCustomizerController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, int i) {
        this.$r8$classId = i;
        this.viewProvider = provider;
        this.tileQueryHelperProvider = provider2;
        this.qsTileHostProvider = provider3;
        this.tileAdapterProvider = provider4;
        this.screenLifecycleProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
        this.lightBarControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
    }

    public static QSCustomizerController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new QSCustomizerController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, 1);
    }

    public static QSCustomizerController_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new QSCustomizerController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new QSCustomizerController((QSCustomizer) this.viewProvider.get(), (TileQueryHelper) this.tileQueryHelperProvider.get(), (QSTileHost) this.qsTileHostProvider.get(), (TileAdapter) this.tileAdapterProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (LightBarController) this.lightBarControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
            default:
                return new MediaOutputDialogFactory((Context) this.viewProvider.get(), (MediaSessionManager) this.tileQueryHelperProvider.get(), (LocalBluetoothManager) this.qsTileHostProvider.get(), (ShadeController) this.tileAdapterProvider.get(), (ActivityStarter) this.screenLifecycleProvider.get(), (CommonNotifCollection) this.keyguardStateControllerProvider.get(), (UiEventLogger) this.lightBarControllerProvider.get(), (DialogLaunchAnimator) this.configurationControllerProvider.get(), (SystemUIDialogManager) this.uiEventLoggerProvider.get());
        }
    }
}
