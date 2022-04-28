package com.android.systemui.p006qs.tileimpl;

import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileViewImpl$onStateChanged$1 */
/* compiled from: QSTileViewImpl.kt */
public final class QSTileViewImpl$onStateChanged$1 implements Runnable {
    public final /* synthetic */ QSTile.State $state;
    public final /* synthetic */ QSTileViewImpl this$0;

    public QSTileViewImpl$onStateChanged$1(QSTileViewImpl qSTileViewImpl, QSTile.State state) {
        this.this$0 = qSTileViewImpl;
        this.$state = state;
    }

    public final void run() {
        this.this$0.handleStateChanged(this.$state);
    }
}
