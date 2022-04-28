package com.android.systemui.p006qs.external;

import android.content.DialogInterface;
import com.android.systemui.p006qs.external.TileServiceRequestController;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2 */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController$createDialog$1$2 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ TileServiceRequestController.SingleShotConsumer<Integer> $responseHandler;

    public TileServiceRequestController$createDialog$1$2(TileServiceRequestController.SingleShotConsumer<Integer> singleShotConsumer) {
        this.$responseHandler = singleShotConsumer;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        this.$responseHandler.accept(3);
    }
}
