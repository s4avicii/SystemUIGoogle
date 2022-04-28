package com.android.systemui.statusbar.notification;

import android.graphics.Paint;
import android.view.View;

public interface NotificationFadeAware {

    public interface FadeOptimizedNotification extends NotificationFadeAware {
        boolean isNotificationFaded();
    }

    void setNotificationFaded(boolean z);

    static void setLayerTypeForFaded(View view, boolean z) {
        int i;
        if (view != null) {
            if (z) {
                i = 2;
            } else {
                i = 0;
            }
            view.setLayerType(i, (Paint) null);
        }
    }
}
