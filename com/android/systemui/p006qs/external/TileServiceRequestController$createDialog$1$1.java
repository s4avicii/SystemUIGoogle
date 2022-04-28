package com.android.systemui.p006qs.external;

import android.content.DialogInterface;
import com.android.systemui.p006qs.external.TileServiceRequestController;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$1 */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController$createDialog$1$1 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ TileServiceRequestController.SingleShotConsumer<Integer> $responseHandler;

    public TileServiceRequestController$createDialog$1$1(TileServiceRequestController.SingleShotConsumer<Integer> singleShotConsumer) {
        this.$responseHandler = singleShotConsumer;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        this.$responseHandler.accept(3);
    }
}
