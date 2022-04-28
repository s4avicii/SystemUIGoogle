package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

@Deprecated
public class DescriptionItem extends Item {
    public DescriptionItem() {
    }

    public DescriptionItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onBindView(View view) {
        super.onBindView(view);
        TextView textView = (TextView) view.findViewById(C1777R.C1779id.sud_items_title);
    }
}
