package com.android.systemui.p006qs.tiles;

import android.content.DialogInterface;
import com.android.systemui.Prefs;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DataSaverTile$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ DataSaverTile f$0;

    public /* synthetic */ DataSaverTile$$ExternalSyntheticLambda0(DataSaverTile dataSaverTile) {
        this.f$0 = dataSaverTile;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        DataSaverTile dataSaverTile = this.f$0;
        Objects.requireNonNull(dataSaverTile);
        dataSaverTile.toggleDataSaver();
        Prefs.putBoolean(dataSaverTile.mContext, "QsDataSaverDialogShown", true);
    }
}
