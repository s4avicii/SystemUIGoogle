package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;

public class ProgressBarItem extends Item {
    public ProgressBarItem() {
    }

    public final int getDefaultLayoutResource() {
        return C1777R.layout.sud_items_progress_bar;
    }

    public final boolean isEnabled() {
        return false;
    }

    public final void onBindView(View view) {
    }

    public ProgressBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
