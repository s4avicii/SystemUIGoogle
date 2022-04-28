package com.google.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.LongScreenshotData;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class GoogleAssistLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider assistUtilsProvider;
    public final Provider assistantPresenceHandlerProvider;
    public final Provider contextProvider;
    public final Provider phoneStateMonitorProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ GoogleAssistLogger_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.assistUtilsProvider = provider3;
        this.phoneStateMonitorProvider = provider4;
        this.assistantPresenceHandlerProvider = provider5;
    }

    public static GoogleAssistLogger_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new GoogleAssistLogger_Factory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public static GoogleAssistLogger_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new GoogleAssistLogger_Factory(provider, provider2, provider3, provider4, provider5, 2);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new GoogleAssistLogger((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (AssistUtils) this.assistUtilsProvider.get(), (PhoneStateMonitor) this.phoneStateMonitorProvider.get(), (AssistantPresenceHandler) this.assistantPresenceHandlerProvider.get());
            case 1:
                return new LongScreenshotActivity((UiEventLogger) this.contextProvider.get(), (ImageExporter) this.uiEventLoggerProvider.get(), (Executor) this.assistUtilsProvider.get(), (Executor) this.phoneStateMonitorProvider.get(), (LongScreenshotData) this.assistantPresenceHandlerProvider.get());
            default:
                return new KeyguardStateControllerImpl((Context) this.contextProvider.get(), (KeyguardUpdateMonitor) this.uiEventLoggerProvider.get(), (LockPatternUtils) this.assistUtilsProvider.get(), DoubleCheck.lazy(this.phoneStateMonitorProvider), (DumpManager) this.assistantPresenceHandlerProvider.get());
        }
    }
}
