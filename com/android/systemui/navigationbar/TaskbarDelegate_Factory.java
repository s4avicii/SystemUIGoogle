package com.android.systemui.navigationbar;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.recents.OverviewProxyRecentsImpl;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class TaskbarDelegate_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ TaskbarDelegate_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TaskbarDelegate((Context) this.contextProvider.get());
            case 1:
                return (FingerprintManager) ((Context) this.contextProvider.get()).getSystemService(FingerprintManager.class);
            case 2:
                return new OverviewProxyRecentsImpl(DoubleCheck.lazy(this.contextProvider));
            default:
                return new TaskViewTransitions((Transitions) this.contextProvider.get());
        }
    }
}
