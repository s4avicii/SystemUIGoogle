package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

public class WidgetContainer extends LinearLayout {
    public int mWidgetCount = 0;

    public final int addWidget(WidgetView widgetView) {
        if (this.mWidgetCount > 0) {
            LayoutInflater.from(getContext()).inflate(C1777R.layout.game_menu_widget_spacing, this);
        }
        super.addView(widgetView);
        int i = this.mWidgetCount + 1;
        this.mWidgetCount = i;
        return i;
    }

    public WidgetContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
