package com.android.systemui.statusbar.notification.dagger;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory */
public final class C1290x3fd4641 implements Factory<SectionHeaderControllerSubcomponent> {
    public final Provider<SectionHeaderControllerSubcomponent.Builder> builderProvider;

    public final Object get() {
        SectionHeaderControllerSubcomponent build = this.builderProvider.get().nodeLabel("alerting header").headerText(C1777R.string.notification_section_header_alerting).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
        Objects.requireNonNull(build, "Cannot return null from a non-@Nullable @Provides method");
        return build;
    }

    public C1290x3fd4641(Provider<SectionHeaderControllerSubcomponent.Builder> provider) {
        this.builderProvider = provider;
    }
}
