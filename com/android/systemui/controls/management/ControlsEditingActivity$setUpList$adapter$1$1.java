package com.android.systemui.controls.management;

import androidx.mediarouter.R$bool;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity$setUpList$adapter$1$1 extends RecyclerView.AdapterDataObserver {
    public final /* synthetic */ RecyclerView $recyclerView;
    public boolean hasAnimated;

    public ControlsEditingActivity$setUpList$adapter$1$1(RecyclerView recyclerView) {
        this.$recyclerView = recyclerView;
    }

    public final void onChanged() {
        if (!this.hasAnimated) {
            this.hasAnimated = true;
            R$bool.enterAnimation(this.$recyclerView).start();
        }
    }
}
