package com.android.systemui.p006qs.tileimpl;

import android.widget.ImageView;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSIconViewImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ QSIconViewImpl f$0;
    public final /* synthetic */ ImageView f$1;
    public final /* synthetic */ QSTile.State f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ QSIconViewImpl$$ExternalSyntheticLambda1(QSIconViewImpl qSIconViewImpl, ImageView imageView, QSTile.State state, boolean z) {
        this.f$0 = qSIconViewImpl;
        this.f$1 = imageView;
        this.f$2 = state;
        this.f$3 = z;
    }

    public final void run() {
        QSIconViewImpl qSIconViewImpl = this.f$0;
        ImageView imageView = this.f$1;
        QSTile.State state = this.f$2;
        boolean z = this.f$3;
        Objects.requireNonNull(qSIconViewImpl);
        qSIconViewImpl.updateIcon(imageView, state, z);
    }
}
