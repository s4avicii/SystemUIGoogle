package com.android.systemui.statusbar;

import android.view.Choreographer;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.WallpaperController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationShadeDepthController_Factory implements Factory<NotificationShadeDepthController> {
    public final Provider<BiometricUnlockController> biometricUnlockControllerProvider;
    public final Provider<BlurUtils> blurUtilsProvider;
    public final Provider<Choreographer> choreographerProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WallpaperController> wallpaperControllerProvider;

    public static NotificationShadeDepthController_Factory create(Provider<StatusBarStateController> provider, Provider<BlurUtils> provider2, Provider<BiometricUnlockController> provider3, Provider<KeyguardStateController> provider4, Provider<Choreographer> provider5, Provider<WallpaperController> provider6, Provider<NotificationShadeWindowController> provider7, Provider<DozeParameters> provider8, Provider<DumpManager> provider9) {
        return new NotificationShadeDepthController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public final Object get() {
        return new NotificationShadeDepthController(this.statusBarStateControllerProvider.get(), this.blurUtilsProvider.get(), this.biometricUnlockControllerProvider.get(), this.keyguardStateControllerProvider.get(), this.choreographerProvider.get(), this.wallpaperControllerProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.dozeParametersProvider.get(), this.dumpManagerProvider.get());
    }

    public NotificationShadeDepthController_Factory(Provider<StatusBarStateController> provider, Provider<BlurUtils> provider2, Provider<BiometricUnlockController> provider3, Provider<KeyguardStateController> provider4, Provider<Choreographer> provider5, Provider<WallpaperController> provider6, Provider<NotificationShadeWindowController> provider7, Provider<DozeParameters> provider8, Provider<DumpManager> provider9) {
        this.statusBarStateControllerProvider = provider;
        this.blurUtilsProvider = provider2;
        this.biometricUnlockControllerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.choreographerProvider = provider5;
        this.wallpaperControllerProvider = provider6;
        this.notificationShadeWindowControllerProvider = provider7;
        this.dozeParametersProvider = provider8;
        this.dumpManagerProvider = provider9;
    }
}
