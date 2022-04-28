package com.android.systemui.p006qs.external;

import android.util.Log;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$TileServiceRequestCommand$execute$1 */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController$TileServiceRequestCommand$execute$1<T> implements Consumer {
    public static final TileServiceRequestController$TileServiceRequestCommand$execute$1<T> INSTANCE = new TileServiceRequestController$TileServiceRequestCommand$execute$1<>();

    public final void accept(Object obj) {
        Log.d("TileServiceRequestController", Intrinsics.stringPlus("Response: ", Integer.valueOf(((Number) obj).intValue())));
    }
}
