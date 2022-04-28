package com.android.systemui.p006qs;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda18 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda18 implements Callable {
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda18(QSSecurityFooter qSSecurityFooter, String str) {
        this.f$0 = qSSecurityFooter;
        this.f$1 = str;
    }

    public final Object call() {
        QSSecurityFooter qSSecurityFooter = this.f$0;
        String str = this.f$1;
        Objects.requireNonNull(qSSecurityFooter);
        return qSSecurityFooter.mContext.getString(C1777R.string.quick_settings_disclosure_management_named_vpn, new Object[]{str});
    }
}
