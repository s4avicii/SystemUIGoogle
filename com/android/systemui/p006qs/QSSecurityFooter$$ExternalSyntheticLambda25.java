package com.android.systemui.p006qs;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* renamed from: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda25 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda25 implements Callable {
    public final /* synthetic */ QSSecurityFooter f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ QSSecurityFooter$$ExternalSyntheticLambda25(QSSecurityFooter qSSecurityFooter, String str, String str2) {
        this.f$0 = qSSecurityFooter;
        this.f$1 = str;
        this.f$2 = str2;
    }

    public final Object call() {
        QSSecurityFooter qSSecurityFooter = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        Objects.requireNonNull(qSSecurityFooter);
        return qSSecurityFooter.mContext.getString(C1777R.string.monitoring_description_two_named_vpns, new Object[]{str, str2});
    }
}
