package com.android.systemui.p006qs;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda2 implements Callable {
    public final /* synthetic */ QSSecurityFooter f$0;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda2(QSSecurityFooter qSSecurityFooter) {
        this.f$0 = qSSecurityFooter;
    }

    public final Object call() {
        QSSecurityFooter qSSecurityFooter = this.f$0;
        Objects.requireNonNull(qSSecurityFooter);
        return qSSecurityFooter.mContext.getString(C1777R.string.quick_settings_disclosure_management_vpns);
    }
}
