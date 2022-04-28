package com.google.android.systemui.elmyra.feedback;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class OpaLockscreen_Factory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Object keyguardStateControllerProvider;
    public final Provider statusBarProvider;

    public OpaLockscreen_Factory(Provider provider, Provider provider2) {
        this.statusBarProvider = provider;
        this.keyguardStateControllerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new OpaLockscreen((StatusBar) this.statusBarProvider.get(), (KeyguardStateController) ((Provider) this.keyguardStateControllerProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.keyguardStateControllerProvider);
                return new LockPatternUtils((Context) this.statusBarProvider.get());
        }
    }

    public OpaLockscreen_Factory(DependencyProvider dependencyProvider, Provider provider) {
        this.keyguardStateControllerProvider = dependencyProvider;
        this.statusBarProvider = provider;
    }
}
