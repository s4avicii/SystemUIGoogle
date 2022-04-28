package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeParameters$$ExternalSyntheticLambda0 implements Function {
    public static final /* synthetic */ DozeParameters$$ExternalSyntheticLambda0 INSTANCE = new DozeParameters$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ DozeParameters$$ExternalSyntheticLambda0 INSTANCE$1 = new DozeParameters$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ DozeParameters$$ExternalSyntheticLambda0 INSTANCE$2 = new DozeParameters$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ DozeParameters$$ExternalSyntheticLambda0 INSTANCE$3 = new DozeParameters$$ExternalSyntheticLambda0(3);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DozeParameters$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((SysUIUnfoldComponent) obj).getFoldAodAnimationController();
            case 1:
                MotionEvent motionEvent = (MotionEvent) obj;
                return new BrightLineFalsingManager.XYDt((int) motionEvent.getX(), (int) motionEvent.getY(), (int) (motionEvent.getEventTime() - motionEvent.getDownTime()));
            case 2:
                ColorDrawable colorDrawable = InternetDialogController.EMPTY_DRAWABLE;
                return Integer.valueOf(((InternetDialogController.AnonymousClass1DisplayInfo) obj).subscriptionInfo.getSubscriptionId());
            default:
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                return ((SysUIUnfoldComponent) obj).getKeyguardUnfoldTransition();
        }
    }
}
