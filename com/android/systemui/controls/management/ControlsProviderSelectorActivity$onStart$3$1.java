package com.android.systemui.controls.management;

import androidx.mediarouter.R$bool;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: ControlsProviderSelectorActivity.kt */
public final class ControlsProviderSelectorActivity$onStart$3$1 extends RecyclerView.AdapterDataObserver {
    public boolean hasAnimated;
    public final /* synthetic */ ControlsProviderSelectorActivity this$0;

    public ControlsProviderSelectorActivity$onStart$3$1(ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        this.this$0 = controlsProviderSelectorActivity;
    }

    public final void onChanged() {
        if (!this.hasAnimated) {
            this.hasAnimated = true;
            RecyclerView recyclerView = this.this$0.recyclerView;
            if (recyclerView == null) {
                recyclerView = null;
            }
            R$bool.enterAnimation(recyclerView).start();
        }
    }
}
