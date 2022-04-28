package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public class TitleView extends FrameLayout {
    public TitleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.browseTitleViewStyle);
    }

    public TitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View inflate = LayoutInflater.from(context).inflate(C1777R.layout.lb_title_view, this);
        ImageView imageView = (ImageView) inflate.findViewById(C1777R.C1779id.title_badge);
        TextView textView = (TextView) inflate.findViewById(C1777R.C1779id.title_text);
        SearchOrbView searchOrbView = (SearchOrbView) inflate.findViewById(C1777R.C1779id.title_orb);
        setClipToPadding(false);
        setClipChildren(false);
    }
}
