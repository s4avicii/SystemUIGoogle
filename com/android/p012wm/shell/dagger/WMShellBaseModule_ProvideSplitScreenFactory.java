package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.statusbar.notification.row.RowContentBindStageLogger;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda3;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideSplitScreenFactory */
public final class WMShellBaseModule_ProvideSplitScreenFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider splitScreenControllerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideSplitScreenFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.splitScreenControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional map = ((Optional) this.splitScreenControllerProvider.get()).map(WifiEntry$$ExternalSyntheticLambda3.INSTANCE$1);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            case 1:
                KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) ((KeyguardHostView) this.splitScreenControllerProvider.get()).findViewById(C1777R.C1779id.keyguard_security_container);
                Objects.requireNonNull(keyguardSecurityContainer, "Cannot return null from a non-@Nullable @Provides method");
                return keyguardSecurityContainer;
            case 2:
                SubscriptionManager subscriptionManager = (SubscriptionManager) ((Context) this.splitScreenControllerProvider.get()).getSystemService(SubscriptionManager.class);
                Objects.requireNonNull(subscriptionManager, "Cannot return null from a non-@Nullable @Provides method");
                return subscriptionManager;
            case 3:
                return ((LogBufferFactory) this.splitScreenControllerProvider.get()).create("NotifInteractionLog", 50);
            case 4:
                return Boolean.valueOf(ColorDisplayManager.isReduceBrightColorsAvailable((Context) this.splitScreenControllerProvider.get()));
            case 5:
                return new RowContentBindStageLogger((LogBuffer) this.splitScreenControllerProvider.get());
            default:
                return new ExecutorImpl((Looper) this.splitScreenControllerProvider.get());
        }
    }
}
