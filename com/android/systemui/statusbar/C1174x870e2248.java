package com.android.systemui.statusbar;

import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.NotificationShadeDepthController$DepthAnimation$springAnimation$1 */
/* compiled from: NotificationShadeDepthController.kt */
public final class C1174x870e2248 extends FloatPropertyCompat<NotificationShadeDepthController.DepthAnimation> {
    public final /* synthetic */ NotificationShadeDepthController.DepthAnimation this$0;
    public final /* synthetic */ NotificationShadeDepthController this$1;

    public C1174x870e2248(NotificationShadeDepthController.DepthAnimation depthAnimation, NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = depthAnimation;
        this.this$1 = notificationShadeDepthController;
    }

    public final float getValue(Object obj) {
        NotificationShadeDepthController.DepthAnimation depthAnimation = (NotificationShadeDepthController.DepthAnimation) obj;
        NotificationShadeDepthController.DepthAnimation depthAnimation2 = this.this$0;
        Objects.requireNonNull(depthAnimation2);
        return depthAnimation2.radius;
    }

    public final void setValue(Object obj, float f) {
        NotificationShadeDepthController.DepthAnimation depthAnimation = (NotificationShadeDepthController.DepthAnimation) obj;
        NotificationShadeDepthController.DepthAnimation depthAnimation2 = this.this$0;
        Objects.requireNonNull(depthAnimation2);
        depthAnimation2.radius = f;
        this.this$1.scheduleUpdate(this.this$0.view);
    }
}
