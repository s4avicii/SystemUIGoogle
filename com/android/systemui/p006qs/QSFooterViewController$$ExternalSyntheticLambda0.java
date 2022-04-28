package com.android.systemui.p006qs;

import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda3;
import com.google.android.systemui.assist.uihints.IconController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFooterViewController$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSFooterViewController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                QSFooterViewController qSFooterViewController = (QSFooterViewController) this.f$0;
                Objects.requireNonNull(qSFooterViewController);
                if (!qSFooterViewController.mFalsingManager.isFalseTap(1)) {
                    qSFooterViewController.mActivityStarter.postQSRunnableDismissingKeyguard(new PipController$$ExternalSyntheticLambda3(qSFooterViewController, view, 1));
                    return;
                }
                return;
            default:
                IconController iconController = (IconController) this.f$0;
                Objects.requireNonNull(iconController);
                PendingIntent pendingIntent = iconController.mOnZerostateIconTap;
                if (pendingIntent != null) {
                    try {
                        pendingIntent.send();
                        return;
                    } catch (PendingIntent.CanceledException e) {
                        Log.e("IconController", "Pending intent cancelled", e);
                        return;
                    }
                } else {
                    return;
                }
        }
    }
}
