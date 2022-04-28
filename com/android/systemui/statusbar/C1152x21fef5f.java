package com.android.systemui.statusbar;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$animationHandler$1 */
/* compiled from: LockscreenShadeTransitionController.kt */
final class C1152x21fef5f extends Lambda implements Function1<Long, Unit> {
    public final /* synthetic */ View $startingChild;
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1152x21fef5f(ExpandableView expandableView, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        super(1);
        this.$startingChild = expandableView;
        this.this$0 = lockscreenShadeTransitionController;
    }

    public final Object invoke(Object obj) {
        long longValue = ((Number) obj).longValue();
        View view = this.$startingChild;
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).onExpandedByGesture(true);
        }
        this.this$0.getNotificationPanelController().animateToFullShade(longValue);
        this.this$0.getNotificationPanelController().setTransitionToFullShadeAmount(0.0f, true, longValue);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
        lockscreenShadeTransitionController.forceApplyAmount = true;
        lockscreenShadeTransitionController.mo11568xcfc05636(0.0f);
        this.this$0.forceApplyAmount = false;
        return Unit.INSTANCE;
    }
}
