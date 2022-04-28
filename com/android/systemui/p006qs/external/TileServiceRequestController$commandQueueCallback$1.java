package com.android.systemui.p006qs.external;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1 */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController$commandQueueCallback$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ TileServiceRequestController this$0;

    public TileServiceRequestController$commandQueueCallback$1(TileServiceRequestController tileServiceRequestController) {
        this.this$0 = tileServiceRequestController;
    }

    public final void cancelRequestAddTile(String str) {
        Function1<? super String, Unit> function1 = this.this$0.dialogCanceller;
        if (function1 != null) {
            function1.invoke(str);
        }
    }

    public final void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        this.this$0.mo10516x2d391cca(componentName, charSequence, charSequence2, icon, new C1028x11805f9(iAddTileResultCallback));
    }
}
