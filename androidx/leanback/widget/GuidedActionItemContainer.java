package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R$id;

class GuidedActionItemContainer extends NonOverlappingLinearLayoutWithForeground {
    public boolean mFocusOutAllowed;

    public GuidedActionItemContainer(Context context) {
        this(context, (AttributeSet) null);
    }

    public GuidedActionItemContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final View focusSearch(View view, int i) {
        if (this.mFocusOutAllowed || !R$id.isDescendant(this, view)) {
            return super.focusSearch(view, i);
        }
        View focusSearch = super.focusSearch(view, i);
        if (R$id.isDescendant(this, focusSearch)) {
            return focusSearch;
        }
        return null;
    }

    public GuidedActionItemContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFocusOutAllowed = true;
    }
}
