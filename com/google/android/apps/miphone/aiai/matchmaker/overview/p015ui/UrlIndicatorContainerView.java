package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.UrlIndicatorContainerView */
public class UrlIndicatorContainerView extends LinearLayout {
    public UrlIndicatorContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        LinearLayout linearLayout = (LinearLayout) View.inflate(context, C1777R.layout.url_container, this);
        TextView textView = (TextView) linearLayout.findViewById(C1777R.C1779id.url_text);
        ImageButton imageButton = (ImageButton) linearLayout.findViewById(C1777R.C1779id.url_indicator);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
