package com.android.systemui.statusbar;

import android.app.NotificationChannel;
import android.os.UserHandle;
import com.android.systemui.statusbar.NotificationListener;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ NotificationListener f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ UserHandle f$2;
    public final /* synthetic */ NotificationChannel f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ NotificationListener$$ExternalSyntheticLambda1(NotificationListener notificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        this.f$0 = notificationListener;
        this.f$1 = str;
        this.f$2 = userHandle;
        this.f$3 = notificationChannel;
        this.f$4 = i;
    }

    public final void run() {
        NotificationListener notificationListener = this.f$0;
        String str = this.f$1;
        UserHandle userHandle = this.f$2;
        NotificationChannel notificationChannel = this.f$3;
        int i = this.f$4;
        Objects.requireNonNull(notificationListener);
        Iterator it = notificationListener.mNotificationHandlers.iterator();
        while (it.hasNext()) {
            ((NotificationListener.NotificationHandler) it.next()).onNotificationChannelModified(str, userHandle, notificationChannel, i);
        }
    }
}
