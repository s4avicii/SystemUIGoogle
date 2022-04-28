package com.android.systemui.statusbar.notification.stack;

import android.view.ViewTreeObserver;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda0 implements ViewTreeObserver.OnPreDrawListener {
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda0(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.f$0 = notificationStackScrollLayout;
    }

    public final boolean onPreDraw() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
        boolean z = NotificationStackScrollLayout.SPEW;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.updateViewShadows();
        return true;
    }
}
