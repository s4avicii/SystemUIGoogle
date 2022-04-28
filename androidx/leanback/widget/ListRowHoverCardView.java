package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public final class ListRowHoverCardView extends LinearLayout {
    public ListRowHoverCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        LayoutInflater.from(context).inflate(C1777R.layout.lb_list_row_hovercard, this);
        TextView textView = (TextView) findViewById(C1777R.C1779id.title);
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.description);
    }
}
