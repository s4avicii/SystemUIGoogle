package com.android.systemui.statusbar.phone;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.util.function.Consumer;

/* compiled from: NotificationsQSContainerController.kt */
public final class NotificationsQSContainerController$windowInsetsListener$1<T> implements Consumer {
    public final /* synthetic */ NotificationsQSContainerController this$0;

    public NotificationsQSContainerController$windowInsetsListener$1(NotificationsQSContainerController notificationsQSContainerController) {
        this.this$0 = notificationsQSContainerController;
    }

    public final void accept(Object obj) {
        int i;
        WindowInsets windowInsets = (WindowInsets) obj;
        this.this$0.bottomStableInsets = windowInsets.getStableInsetBottom();
        NotificationsQSContainerController notificationsQSContainerController = this.this$0;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            i = 0;
        } else {
            i = displayCutout.getSafeInsetBottom();
        }
        notificationsQSContainerController.bottomCutoutInsets = i;
        this.this$0.updateBottomSpacing();
    }
}
