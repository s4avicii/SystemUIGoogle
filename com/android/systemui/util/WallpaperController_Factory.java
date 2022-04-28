package com.android.systemui.util;

import android.app.KeyguardManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class WallpaperController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider wallpaperManagerProvider;

    public /* synthetic */ WallpaperController_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.wallpaperManagerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new WallpaperController((WallpaperManager) this.wallpaperManagerProvider.get());
            case 1:
                return new BroadcastDispatcherLogger((LogBuffer) this.wallpaperManagerProvider.get());
            case 2:
                KeyguardManager keyguardManager = (KeyguardManager) ((Context) this.wallpaperManagerProvider.get()).getSystemService(KeyguardManager.class);
                Objects.requireNonNull(keyguardManager, "Cannot return null from a non-@Nullable @Provides method");
                return keyguardManager;
            default:
                String[] stringArray = ((Resources) this.wallpaperManagerProvider.get()).getStringArray(17236100);
                Objects.requireNonNull(stringArray, "Cannot return null from a non-@Nullable @Provides method");
                return stringArray;
        }
    }
}
