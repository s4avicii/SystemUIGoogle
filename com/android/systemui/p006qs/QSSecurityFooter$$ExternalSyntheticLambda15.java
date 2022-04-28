package com.android.systemui.p006qs;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda15 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda15 implements Callable {
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ CharSequence f$1;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda15(QSSecurityFooter qSSecurityFooter, CharSequence charSequence) {
        this.f$0 = qSSecurityFooter;
        this.f$1 = charSequence;
    }

    public final Object call() {
        QSSecurityFooter qSSecurityFooter = this.f$0;
        CharSequence charSequence = this.f$1;
        Objects.requireNonNull(qSSecurityFooter);
        return qSSecurityFooter.mContext.getString(C1777R.string.quick_settings_disclosure_named_managed_profile_monitoring, new Object[]{charSequence});
    }
}
