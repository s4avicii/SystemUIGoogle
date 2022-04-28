package com.android.systemui.colorextraction;

import android.app.WallpaperManager;
import android.content.Context;
import com.android.internal.colorextraction.types.Tonal;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SysuiColorExtractor_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;

    public /* synthetic */ SysuiColorExtractor_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.contextProvider.get();
                return new SysuiColorExtractor(context, new Tonal(context), (ConfigurationController) this.configurationControllerProvider.get(), (WallpaperManager) context.getSystemService(WallpaperManager.class), (DumpManager) this.dumpManagerProvider.get(), false);
            default:
                return new HeadsUpViewBinder((NotificationMessagingUtil) this.contextProvider.get(), (RowContentBindStage) this.configurationControllerProvider.get(), (HeadsUpViewBinderLogger) this.dumpManagerProvider.get());
        }
    }
}
