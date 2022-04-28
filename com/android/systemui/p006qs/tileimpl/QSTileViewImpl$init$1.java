package com.android.systemui.p006qs.tileimpl;

import android.view.View;
import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$1 */
/* compiled from: QSTileViewImpl.kt */
public final class QSTileViewImpl$init$1 implements View.OnClickListener {
    public final /* synthetic */ QSTile $tile;
    public final /* synthetic */ QSTileViewImpl this$0;

    public QSTileViewImpl$init$1(QSTile qSTile, QSTileViewImpl qSTileViewImpl) {
        this.$tile = qSTile;
        this.this$0 = qSTileViewImpl;
    }

    public final void onClick(View view) {
        this.$tile.click(this.this$0);
    }
}
