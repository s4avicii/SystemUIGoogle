package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.Objects;

public final class AdapterListUpdateCallback implements ListUpdateCallback {
    public final RecyclerView.Adapter mAdapter;

    public final void onChanged(int i, int i2, Object obj) {
        RecyclerView.Adapter adapter = this.mAdapter;
        Objects.requireNonNull(adapter);
        adapter.mObservable.notifyItemRangeChanged(i, i2, obj);
    }

    public final void onInserted(int i, int i2) {
        this.mAdapter.notifyItemRangeInserted(i, i2);
    }

    public final void onMoved(int i, int i2) {
        this.mAdapter.notifyItemMoved(i, i2);
    }

    public final void onRemoved(int i, int i2) {
        this.mAdapter.notifyItemRangeRemoved(i, i2);
    }

    public AdapterListUpdateCallback(FgsManagerController.AppListAdapter appListAdapter) {
        this.mAdapter = appListAdapter;
    }
}
