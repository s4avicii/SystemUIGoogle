package com.android.systemui;

import android.app.INotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.ServiceManager;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.p006qs.customize.QSCustomizer;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class ActivityIntentHelper_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object contextProvider;

    public /* synthetic */ ActivityIntentHelper_Factory(Object obj, int i) {
        this.$r8$classId = i;
        this.contextProvider = obj;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ActivityIntentHelper((Context) ((Provider) this.contextProvider).get());
            case 1:
                return (WifiManager) ((Context) ((Provider) this.contextProvider).get()).getSystemService(WifiManager.class);
            case 2:
                return ((LogBufferFactory) ((Provider) this.contextProvider).get()).create("ToastLog", 50);
            case 3:
                return new MediaTttFlags((FeatureFlags) ((Provider) this.contextProvider).get());
            case 4:
                QSCustomizer qSCustomizer = (QSCustomizer) ((View) ((Provider) this.contextProvider).get()).findViewById(C1777R.C1779id.qs_customize);
                Objects.requireNonNull(qSCustomizer, "Cannot return null from a non-@Nullable @Provides method");
                return qSCustomizer;
            case 5:
                return new GlobalSettingsImpl((ContentResolver) ((Provider) this.contextProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.contextProvider);
                INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                Objects.requireNonNull(asInterface, "Cannot return null from a non-@Nullable @Provides method");
                return asInterface;
        }
    }
}
