package com.android.systemui.dagger;

import android.view.View;
import android.view.ViewStub;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DependencyProvider_ProvidesViewMediatorCallbackFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object module;
    public final Provider viewMediatorProvider;

    public /* synthetic */ DependencyProvider_ProvidesViewMediatorCallbackFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.viewMediatorProvider = provider;
        this.module = provider2;
    }

    public DependencyProvider_ProvidesViewMediatorCallbackFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.$r8$classId = 0;
        this.module = dependencyProvider;
        this.viewMediatorProvider = provider;
    }

    public final Object get() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.viewMediatorProvider.get();
                Objects.requireNonNull((DependencyProvider) this.module);
                Objects.requireNonNull(keyguardViewMediator);
                KeyguardViewMediator.C08564 r3 = keyguardViewMediator.mViewMediatorCallback;
                Objects.requireNonNull(r3, "Cannot return null from a non-@Nullable @Provides method");
                return r3;
            case 1:
                return new ForegroundServiceSectionController((NotificationEntryManager) this.viewMediatorProvider.get(), (ForegroundServiceDismissalFeatureController) ((Provider) this.module).get());
            default:
                ViewStub viewStub = (ViewStub) ((NotificationShadeWindowView) this.viewMediatorProvider.get()).findViewById(C1777R.C1779id.qs_header_stub);
                if (((FeatureFlags) ((Provider) this.module).get()).isEnabled(Flags.COMBINED_QS_HEADERS)) {
                    i = C1777R.layout.combined_qs_header;
                } else {
                    i = C1777R.layout.split_shade_header;
                }
                viewStub.setLayoutResource(i);
                View inflate = viewStub.inflate();
                Objects.requireNonNull(inflate, "Cannot return null from a non-@Nullable @Provides method");
                return inflate;
        }
    }
}
