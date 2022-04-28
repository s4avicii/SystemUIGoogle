package com.android.systemui.dreams;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.back.BackAnimationController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class DreamOverlayRegistrant_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider resourcesProvider;

    public /* synthetic */ DreamOverlayRegistrant_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.resourcesProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DreamOverlayRegistrant((Context) this.contextProvider.get(), (Resources) this.resourcesProvider.get());
            case 1:
                NotificationShelf notificationShelf = (NotificationShelf) ((LayoutInflater) this.contextProvider.get()).inflate(C1777R.layout.status_bar_notification_shelf, (NotificationStackScrollLayout) this.resourcesProvider.get(), false);
                if (notificationShelf != null) {
                    return notificationShelf;
                }
                throw new IllegalStateException("R.layout.status_bar_notification_shelf could not be properly inflated");
            default:
                Optional of = Optional.of(new BackAnimationController((ShellExecutor) this.resourcesProvider.get(), new SurfaceControl.Transaction(), ActivityTaskManager.getService(), (Context) this.contextProvider.get()));
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
        }
    }
}
