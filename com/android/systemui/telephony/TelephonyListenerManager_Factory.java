package com.android.systemui.telephony;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.columbus.gates.TelephonyActivity;
import com.google.android.systemui.elmyra.feedback.AssistInvocationEffect;
import com.google.android.systemui.elmyra.feedback.OpaHomeButton;
import com.google.android.systemui.elmyra.feedback.OpaLockscreen;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class TelephonyListenerManager_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider executorProvider;
    public final Provider telephonyCallbackProvider;
    public final Provider telephonyManagerProvider;

    public /* synthetic */ TelephonyListenerManager_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.telephonyManagerProvider = provider;
        this.executorProvider = provider2;
        this.telephonyCallbackProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TelephonyListenerManager((TelephonyManager) this.telephonyManagerProvider.get(), (Executor) this.executorProvider.get(), (TelephonyCallback) this.telephonyCallbackProvider.get());
            case 1:
                return new TelephonyActivity((Context) this.telephonyManagerProvider.get(), DoubleCheck.lazy(this.executorProvider), DoubleCheck.lazy(this.telephonyCallbackProvider));
            default:
                return new AssistInvocationEffect((AssistManagerGoogle) this.telephonyManagerProvider.get(), (OpaHomeButton) this.executorProvider.get(), (OpaLockscreen) this.telephonyCallbackProvider.get());
        }
    }
}
