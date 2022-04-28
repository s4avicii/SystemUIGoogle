package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

/* compiled from: SectionHeaderVisibilityProvider.kt */
public final class SectionHeaderVisibilityProvider {
    public boolean neverShowSectionHeaders;
    public boolean sectionHeadersVisible = true;

    public SectionHeaderVisibilityProvider(Context context) {
        this.neverShowSectionHeaders = context.getResources().getBoolean(C1777R.bool.config_notification_never_show_section_headers);
    }
}
