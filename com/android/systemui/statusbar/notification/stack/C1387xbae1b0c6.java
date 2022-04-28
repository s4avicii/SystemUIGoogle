package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.media.KeyguardMediaController;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1387xbae1b0c6 implements Function1 {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ C1387xbae1b0c6(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    public final Object invoke(Object obj) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        if (((Boolean) obj).booleanValue()) {
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            KeyguardMediaController keyguardMediaController = notificationStackScrollLayoutController.mKeyguardMediaController;
            Objects.requireNonNull(keyguardMediaController);
            notificationStackScrollLayout.generateAddAnimation(keyguardMediaController.singlePaneContainer, false);
        } else {
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            KeyguardMediaController keyguardMediaController2 = notificationStackScrollLayoutController.mKeyguardMediaController;
            Objects.requireNonNull(keyguardMediaController2);
            notificationStackScrollLayout2.generateRemoveAnimation(keyguardMediaController2.singlePaneContainer);
        }
        notificationStackScrollLayoutController.mView.requestChildrenUpdate();
        return Unit.INSTANCE;
    }
}
