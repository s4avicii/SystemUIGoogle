package com.android.systemui.util.time;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DateFormatUtil_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ DateFormatUtil_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DateFormatUtil((Context) this.contextProvider.get());
            case 1:
                ViewGroup viewGroup = (ViewGroup) Preconditions.checkNotNull((ViewGroup) ((DreamOverlayContainerView) this.contextProvider.get()).findViewById(C1777R.C1779id.dream_overlay_content), "R.id.dream_overlay_content must not be null");
                Objects.requireNonNull(viewGroup, "Cannot return null from a non-@Nullable @Provides method");
                return viewGroup;
            case 2:
                QuickStatusBarHeader quickStatusBarHeader = (QuickStatusBarHeader) ((View) this.contextProvider.get()).findViewById(C1777R.C1779id.header);
                Objects.requireNonNull(quickStatusBarHeader, "Cannot return null from a non-@Nullable @Provides method");
                return quickStatusBarHeader;
            default:
                return new NotifBindPipelineLogger((LogBuffer) this.contextProvider.get());
        }
    }
}
