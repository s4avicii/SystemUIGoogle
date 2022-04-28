package com.android.systemui.p006qs.customize;

import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TileAdapter$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TileAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TileAdapter$$ExternalSyntheticLambda0(TileAdapter tileAdapter, int i) {
        this.f$0 = tileAdapter;
        this.f$1 = i;
    }

    public final void run() {
        TileAdapter tileAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(tileAdapter);
        RecyclerView recyclerView = tileAdapter.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(i);
        }
    }
}
