package com.android.systemui.p006qs.customize;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.util.sensors.ProximitySensor;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.gates.FlagEnabled;
import com.google.android.systemui.gamedashboard.GameModeDndController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.customize.TileAdapter_Factory */
public final class TileAdapter_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider qsHostProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ TileAdapter_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.qsHostProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }

    public final Object get() {
        ProximitySensor proximitySensor;
        switch (this.$r8$classId) {
            case 0:
                return new TileAdapter((Context) this.contextProvider.get(), (QSTileHost) this.qsHostProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
            case 1:
                Lazy lazy = DoubleCheck.lazy(this.qsHostProvider);
                Lazy lazy2 = DoubleCheck.lazy(this.uiEventLoggerProvider);
                String[] stringArray = ((Resources) this.contextProvider.get()).getStringArray(C1777R.array.proximity_sensor_posture_mapping);
                boolean z = false;
                if (!(stringArray == null || stringArray.length == 0)) {
                    int length = stringArray.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            if (!TextUtils.isEmpty(stringArray[i])) {
                                z = true;
                            } else {
                                i++;
                            }
                        }
                    }
                }
                if (z) {
                    proximitySensor = (ProximitySensor) lazy.get();
                } else {
                    proximitySensor = (ProximitySensor) lazy2.get();
                }
                Objects.requireNonNull(proximitySensor, "Cannot return null from a non-@Nullable @Provides method");
                return proximitySensor;
            case 2:
                return new FlagEnabled((Context) this.contextProvider.get(), (ColumbusSettings) this.qsHostProvider.get(), (Handler) this.uiEventLoggerProvider.get());
            default:
                return new GameModeDndController((Context) this.contextProvider.get(), (NotificationManager) this.qsHostProvider.get(), (BroadcastDispatcher) this.uiEventLoggerProvider.get());
        }
    }
}
