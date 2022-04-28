package com.android.systemui.people.widget;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.keyguard.LockIconView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class PeopleSpaceWidgetProvider_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider peopleSpaceWidgetManagerProvider;

    public /* synthetic */ PeopleSpaceWidgetProvider_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new PeopleSpaceWidgetProvider((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
            case 1:
                DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.peopleSpaceWidgetManagerProvider.get();
                deviceProvisionedControllerImpl.init();
                return deviceProvisionedControllerImpl;
            case 2:
                Lifecycle lifecycle = ((LifecycleOwner) this.peopleSpaceWidgetManagerProvider.get()).getLifecycle();
                Objects.requireNonNull(lifecycle, "Cannot return null from a non-@Nullable @Provides method");
                return lifecycle;
            case 3:
                return new NotifInflaterImpl((NotifInflationErrorManager) this.peopleSpaceWidgetManagerProvider.get());
            case 4:
                return new NotifRemoteViewCacheImpl((CommonNotifCollection) this.peopleSpaceWidgetManagerProvider.get());
            default:
                LockIconView lockIconView = (LockIconView) ((NotificationShadeWindowView) this.peopleSpaceWidgetManagerProvider.get()).findViewById(C1777R.C1779id.lock_icon_view);
                Objects.requireNonNull(lockIconView, "Cannot return null from a non-@Nullable @Provides method");
                return lockIconView;
        }
    }
}
