package com.android.systemui.people;

import android.app.Notification;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationHelper$$ExternalSyntheticLambda0 implements Function {
    public static final /* synthetic */ NotificationHelper$$ExternalSyntheticLambda0 INSTANCE = new NotificationHelper$$ExternalSyntheticLambda0();

    public final Object apply(Object obj) {
        return Long.valueOf(((Notification.MessagingStyle.Message) obj).getTimestamp());
    }
}
