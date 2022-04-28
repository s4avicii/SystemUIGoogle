package com.android.systemui.statusbar.notification.dagger;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory */
public final class C1291xb614d321 implements Factory<SectionHeaderControllerSubcomponent> {
    public final Provider<SectionHeaderControllerSubcomponent.Builder> builderProvider;

    public final Object get() {
        SectionHeaderControllerSubcomponent build = this.builderProvider.get().nodeLabel("incoming header").headerText(C1777R.string.notification_section_header_incoming).clickIntentAction("android.settings.NOTIFICATION_SETTINGS").build();
        Objects.requireNonNull(build, "Cannot return null from a non-@Nullable @Provides method");
        return build;
    }

    public C1291xb614d321(Provider<SectionHeaderControllerSubcomponent.Builder> provider) {
        this.builderProvider = provider;
    }
}
