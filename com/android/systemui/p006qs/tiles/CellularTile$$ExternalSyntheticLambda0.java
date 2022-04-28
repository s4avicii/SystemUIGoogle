package com.android.systemui.p006qs.tiles;

import android.content.DialogInterface;
import com.android.systemui.Prefs;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.CellularTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CellularTile$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ CellularTile f$0;

    public /* synthetic */ CellularTile$$ExternalSyntheticLambda0(CellularTile cellularTile) {
        this.f$0 = cellularTile;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        CellularTile cellularTile = this.f$0;
        Objects.requireNonNull(cellularTile);
        cellularTile.mDataController.setMobileDataEnabled(false);
        Prefs.putBoolean(cellularTile.mContext, "QsHasTurnedOffMobileData", true);
    }
}
