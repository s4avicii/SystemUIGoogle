package com.android.systemui.p006qs.logging;

import android.content.Context;
import android.net.NetworkScoreManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.media.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.NotificationClickerLogger;
import com.android.systemui.usb.StorageNotification;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.logging.QSLogger_Factory */
public final class QSLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bufferProvider;

    public /* synthetic */ QSLogger_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.bufferProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new QSLogger((LogBuffer) this.bufferProvider.get());
            case 1:
                NetworkScoreManager networkScoreManager = (NetworkScoreManager) ((Context) this.bufferProvider.get()).getSystemService(NetworkScoreManager.class);
                Objects.requireNonNull(networkScoreManager, "Cannot return null from a non-@Nullable @Provides method");
                return networkScoreManager;
            case 2:
                return new MediaFeatureFlag((Context) this.bufferProvider.get());
            case 3:
                return new NotificationClickerLogger((LogBuffer) this.bufferProvider.get());
            default:
                return new StorageNotification((Context) this.bufferProvider.get());
        }
    }
}
