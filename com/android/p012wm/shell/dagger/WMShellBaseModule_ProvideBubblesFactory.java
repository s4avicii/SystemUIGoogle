package com.android.p012wm.shell.dagger;

import android.app.AlarmManager;
import android.content.Context;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsLogger;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideBubblesFactory */
public final class WMShellBaseModule_ProvideBubblesFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bubbleControllerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideBubblesFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.bubbleControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional map = ((Optional) this.bubbleControllerProvider.get()).map(WMShellBaseModule$$ExternalSyntheticLambda3.INSTANCE);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            case 1:
                AlarmManager alarmManager = (AlarmManager) ((Context) this.bubbleControllerProvider.get()).getSystemService(AlarmManager.class);
                Objects.requireNonNull(alarmManager, "Cannot return null from a non-@Nullable @Provides method");
                return alarmManager;
            case 2:
                return new NotificationSectionsLogger((LogBuffer) this.bubbleControllerProvider.get());
            default:
                StatusIconContainer statusIconContainer = (StatusIconContainer) ((View) this.bubbleControllerProvider.get()).findViewById(C1777R.C1779id.statusIcons);
                Objects.requireNonNull(statusIconContainer, "Cannot return null from a non-@Nullable @Provides method");
                return statusIconContainer;
        }
    }
}
