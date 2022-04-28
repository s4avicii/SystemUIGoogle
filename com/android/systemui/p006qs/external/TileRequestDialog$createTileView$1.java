package com.android.systemui.p006qs.external;

import com.android.systemui.p006qs.tileimpl.QSTileViewImpl;

/* renamed from: com.android.systemui.qs.external.TileRequestDialog$createTileView$1 */
/* compiled from: TileRequestDialog.kt */
public final class TileRequestDialog$createTileView$1 implements Runnable {
    public final /* synthetic */ QSTileViewImpl $tile;

    public TileRequestDialog$createTileView$1(QSTileViewImpl qSTileViewImpl) {
        this.$tile = qSTileViewImpl;
    }

    public final void run() {
        this.$tile.setStateDescription("");
        this.$tile.setClickable(false);
        this.$tile.setSelected(true);
    }
}
