package com.android.systemui.assist.p003ui;

import android.content.Context;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyInflaterImpl;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.assist.ui.DefaultUiController_Factory */
public final class DefaultUiController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider assistLoggerProvider;
    public final Provider assistManagerLazyProvider;
    public final Provider contextProvider;
    public final Provider metricsLoggerProvider;
    public final Provider windowManagerProvider;

    public /* synthetic */ DefaultUiController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.assistLoggerProvider = provider2;
        this.windowManagerProvider = provider3;
        this.metricsLoggerProvider = provider4;
        this.assistManagerLazyProvider = provider5;
    }

    public static DefaultUiController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new DefaultUiController_Factory(provider, provider2, provider3, provider4, provider5, 0);
    }

    public static DefaultUiController_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new DefaultUiController_Factory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public static DefaultUiController_Factory create$2(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new DefaultUiController_Factory(provider, provider2, provider3, provider4, provider5, 2);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DefaultUiController((Context) this.contextProvider.get(), (AssistLogger) this.assistLoggerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider));
            case 1:
                return new QRCodeScannerController((Context) this.contextProvider.get(), (Executor) this.assistLoggerProvider.get(), (SecureSettings) this.windowManagerProvider.get(), (DeviceConfigProxy) this.metricsLoggerProvider.get(), (UserTracker) this.assistManagerLazyProvider.get());
            default:
                return new SmartReplyInflaterImpl((SmartReplyConstants) this.contextProvider.get(), (KeyguardDismissUtil) this.assistLoggerProvider.get(), (NotificationRemoteInputManager) this.windowManagerProvider.get(), (SmartReplyController) this.metricsLoggerProvider.get(), (Context) this.assistManagerLazyProvider.get());
        }
    }
}
