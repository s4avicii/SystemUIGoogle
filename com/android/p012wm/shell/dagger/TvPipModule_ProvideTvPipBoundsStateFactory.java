package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.hardware.SensorManager;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.settingslib.dream.DreamBackend;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.p006qs.QSFooterViewController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvideTvPipBoundsStateFactory */
public final class TvPipModule_ProvideTvPipBoundsStateFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ TvPipModule_ProvideTvPipBoundsStateFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TvPipBoundsState((Context) this.contextProvider.get());
            case 1:
                SensorManager sensorManager = (SensorManager) ((Context) this.contextProvider.get()).getSystemService(SensorManager.class);
                Objects.requireNonNull(sensorManager, "Cannot return null from a non-@Nullable @Provides method");
                return sensorManager;
            case 2:
                Context context = (Context) this.contextProvider.get();
                if (DreamBackend.sInstance == null) {
                    DreamBackend.sInstance = new DreamBackend(context);
                }
                DreamBackend dreamBackend = DreamBackend.sInstance;
                Objects.requireNonNull(dreamBackend, "Cannot return null from a non-@Nullable @Provides method");
                return dreamBackend;
            case 3:
                QSFooterViewController qSFooterViewController = (QSFooterViewController) this.contextProvider.get();
                qSFooterViewController.init();
                return qSFooterViewController;
            default:
                return new NotificationStackScrollLogger((LogBuffer) this.contextProvider.get());
        }
    }
}
