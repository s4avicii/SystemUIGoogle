package com.android.systemui.p006qs.tileimpl;

import android.view.View;
import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$2 */
/* compiled from: QSTileViewImpl.kt */
public final class QSTileViewImpl$init$2 implements View.OnLongClickListener {
    public final /* synthetic */ QSTile $tile;
    public final /* synthetic */ QSTileViewImpl this$0;

    public QSTileViewImpl$init$2(QSTile qSTile, QSTileViewImpl qSTileViewImpl) {
        this.$tile = qSTile;
        this.this$0 = qSTileViewImpl;
    }

    public final boolean onLongClick(View view) {
        this.$tile.longClick(this.this$0);
        return true;
    }
}
