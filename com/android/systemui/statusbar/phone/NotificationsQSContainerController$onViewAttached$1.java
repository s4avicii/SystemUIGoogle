package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.p005qs.C0961QS;
import java.util.function.Consumer;

/* compiled from: NotificationsQSContainerController.kt */
public final class NotificationsQSContainerController$onViewAttached$1<T> implements Consumer {
    public final /* synthetic */ NotificationsQSContainerController this$0;

    public NotificationsQSContainerController$onViewAttached$1(NotificationsQSContainerController notificationsQSContainerController) {
        this.this$0 = notificationsQSContainerController;
    }

    public final void accept(Object obj) {
        ((C0961QS) obj).setContainerController(this.this$0);
    }
}
