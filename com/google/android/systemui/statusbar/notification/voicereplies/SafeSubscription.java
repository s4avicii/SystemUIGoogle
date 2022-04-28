package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.people.Subscription;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class SafeSubscription implements Subscription {
    public final AtomicReference<Function0<Unit>> blockRef;

    public final void unsubscribe() {
        Function0 andSet = this.blockRef.getAndSet((Object) null);
        if (andSet != null) {
            andSet.invoke();
        }
    }

    public SafeSubscription(Function0<Unit> function0) {
        this.blockRef = new AtomicReference<>(function0);
    }
}
