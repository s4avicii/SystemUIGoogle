package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.NotificationMediaManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationRankingManager.kt */
public final class NotificationRankingManager$mediaManager$2 extends Lambda implements Function0<NotificationMediaManager> {
    public final /* synthetic */ NotificationRankingManager this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationRankingManager$mediaManager$2(NotificationRankingManager notificationRankingManager) {
        super(0);
        this.this$0 = notificationRankingManager;
    }

    public final Object invoke() {
        return this.this$0.mediaManagerLazy.get();
    }
}
