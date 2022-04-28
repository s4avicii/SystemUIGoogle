package com.android.systemui.media;

import android.content.Context;
import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger;
import com.android.systemui.util.Utils;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaBrowserFactory_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ MediaBrowserFactory_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaBrowserFactory((Context) this.contextProvider.get());
            case 1:
                return Float.valueOf(((Resources) this.contextProvider.get()).getDimension(C1777R.dimen.double_tap_slop));
            case 2:
                return Long.valueOf((long) ((Resources) this.contextProvider.get()).getInteger(C1777R.integer.config_dreamOverlayBurnInProtectionUpdateIntervalMillis));
            case 3:
                return Boolean.valueOf(Utils.useQsMediaPlayer((Context) this.contextProvider.get()));
            default:
                return new HeadsUpCoordinatorLogger((LogBuffer) this.contextProvider.get());
        }
    }
}
