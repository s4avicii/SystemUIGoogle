package com.android.systemui.p006qs.external;

import android.os.RemoteException;
import android.util.Log;
import com.android.internal.statusbar.IAddTileResultCallback;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1$requestAddTile$1 */
/* compiled from: TileServiceRequestController.kt */
public final class C1028x11805f9<T> implements Consumer {
    public final /* synthetic */ IAddTileResultCallback $callback;

    public C1028x11805f9(IAddTileResultCallback iAddTileResultCallback) {
        this.$callback = iAddTileResultCallback;
    }

    public final void accept(Object obj) {
        try {
            this.$callback.onTileRequest(((Number) obj).intValue());
        } catch (RemoteException e) {
            Log.e("TileServiceRequestController", "Couldn't respond to request", e);
        }
    }
}
