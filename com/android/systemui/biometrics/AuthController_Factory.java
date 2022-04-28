package com.android.systemui.biometrics;

import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.UserManager;
import android.view.WindowManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AuthController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider activityTaskManagerProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider displayManagerProvider;
    public final Provider executionProvider;
    public final Provider faceManagerProvider;
    public final Provider fingerprintManagerProvider;
    public final Provider handlerProvider;
    public final Provider sidefpsControllerFactoryProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider udfpsControllerFactoryProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider windowManagerProvider;

    public /* synthetic */ AuthController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.commandQueueProvider = provider3;
        this.activityTaskManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.fingerprintManagerProvider = provider6;
        this.faceManagerProvider = provider7;
        this.udfpsControllerFactoryProvider = provider8;
        this.sidefpsControllerFactoryProvider = provider9;
        this.displayManagerProvider = provider10;
        this.wakefulnessLifecycleProvider = provider11;
        this.statusBarStateControllerProvider = provider12;
        this.handlerProvider = provider13;
    }

    public static AuthController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        return new AuthController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, 0);
    }

    public static AuthController_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        return new AuthController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new AuthController((Context) this.contextProvider.get(), (Execution) this.executionProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (ActivityTaskManager) this.activityTaskManagerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (FaceManager) this.faceManagerProvider.get(), this.udfpsControllerFactoryProvider, this.sidefpsControllerFactoryProvider, (DisplayManager) this.displayManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (Handler) this.handlerProvider.get());
            default:
                return new NotificationLockscreenUserManagerImpl((Context) this.contextProvider.get(), (BroadcastDispatcher) this.executionProvider.get(), (DevicePolicyManager) this.commandQueueProvider.get(), (UserManager) this.activityTaskManagerProvider.get(), DoubleCheck.lazy(this.windowManagerProvider), DoubleCheck.lazy(this.fingerprintManagerProvider), (NotificationClickNotifier) this.faceManagerProvider.get(), (KeyguardManager) this.udfpsControllerFactoryProvider.get(), (StatusBarStateController) this.sidefpsControllerFactoryProvider.get(), (Handler) this.displayManagerProvider.get(), (DeviceProvisionedController) this.wakefulnessLifecycleProvider.get(), (KeyguardStateController) this.statusBarStateControllerProvider.get(), (DumpManager) this.handlerProvider.get());
        }
    }
}
