package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class ListRowView extends LinearLayout {
    public ListRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        LayoutInflater.from(context).inflate(C1777R.layout.lb_list_row, this);
        HorizontalGridView horizontalGridView = (HorizontalGridView) findViewById(C1777R.C1779id.row_content);
        Objects.requireNonNull(horizontalGridView);
        horizontalGridView.mHasFixedSize = false;
        setOrientation(1);
        setDescendantFocusability(262144);
    }
}
