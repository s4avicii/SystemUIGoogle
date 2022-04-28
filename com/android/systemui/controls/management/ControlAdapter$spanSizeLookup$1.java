package com.android.systemui.controls.management;

import androidx.recyclerview.widget.GridLayoutManager;

/* compiled from: ControlAdapter.kt */
public final class ControlAdapter$spanSizeLookup$1 extends GridLayoutManager.SpanSizeLookup {
    public final /* synthetic */ ControlAdapter this$0;

    public ControlAdapter$spanSizeLookup$1(ControlAdapter controlAdapter) {
        this.this$0 = controlAdapter;
    }

    public final int getSpanSize(int i) {
        if (this.this$0.getItemViewType(i) != 1) {
            return 2;
        }
        return 1;
    }
}
