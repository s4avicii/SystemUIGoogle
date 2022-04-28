package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.unfold.C1696x68e7f52c;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class NotificationPanelUnfoldAnimationController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Provider contextProvider;
    public final Object progressProvider;

    public NotificationPanelUnfoldAnimationController_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.progressProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new NotificationPanelUnfoldAnimationController((Context) this.contextProvider.get(), (NaturalRotationUnfoldProgressProvider) ((Provider) this.progressProvider).get());
            default:
                Objects.requireNonNull((UnfoldTransitionModule) this.progressProvider);
                return ((Optional) this.contextProvider.get()).map(C1696x68e7f52c.INSTANCE);
        }
    }

    public NotificationPanelUnfoldAnimationController_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider) {
        this.progressProvider = unfoldTransitionModule;
        this.contextProvider = provider;
    }
}
