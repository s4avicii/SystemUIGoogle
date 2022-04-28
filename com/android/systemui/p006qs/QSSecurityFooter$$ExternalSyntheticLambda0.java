package com.android.systemui.p006qs;

import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.transition.DefaultTransitionHandler;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final Object call() {
        switch (this.$r8$classId) {
            case 0:
                QSSecurityFooter qSSecurityFooter = (QSSecurityFooter) this.f$0;
                Objects.requireNonNull(qSSecurityFooter);
                return qSSecurityFooter.mContext.getString(C1777R.string.monitoring_description_management_ca_certificate);
            default:
                DefaultTransitionHandler defaultTransitionHandler = (DefaultTransitionHandler) this.f$0;
                boolean z = DefaultTransitionHandler.sDisableCustomTaskAnimationProperty;
                Objects.requireNonNull(defaultTransitionHandler);
                return defaultTransitionHandler.mContext.getDrawable(17302392);
        }
    }
}
