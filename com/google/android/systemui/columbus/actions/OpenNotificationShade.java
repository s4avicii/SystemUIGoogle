package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: OpenNotificationShade.kt */
public final class OpenNotificationShade extends UserAction {
    public final Lazy<NotificationShadeWindowController> notificationShadeWindowController;
    public final Lazy<StatusBar> statusBar;
    public final String tag = "Columbus/OpenNotif";
    public final UiEventLogger uiEventLogger;

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        if (this.notificationShadeWindowController.get().getPanelExpanded()) {
            StatusBar statusBar2 = this.statusBar.get();
            Objects.requireNonNull(statusBar2);
            DelayableExecutor delayableExecutor = statusBar2.mMainExecutor;
            ShadeController shadeController = statusBar2.mShadeController;
            Objects.requireNonNull(shadeController);
            delayableExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda18(shadeController, 6));
            this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_NOTIFICATION_SHADE_CLOSE);
            return;
        }
        StatusBar statusBar3 = this.statusBar.get();
        Objects.requireNonNull(statusBar3);
        statusBar3.mCommandQueueCallbacks.animateExpandNotificationsPanel();
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_NOTIFICATION_SHADE_OPEN);
    }

    public OpenNotificationShade(Context context, Lazy<NotificationShadeWindowController> lazy, Lazy<StatusBar> lazy2, UiEventLogger uiEventLogger2) {
        super(context);
        this.notificationShadeWindowController = lazy;
        this.statusBar = lazy2;
        this.uiEventLogger = uiEventLogger2;
        setAvailable(true);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
