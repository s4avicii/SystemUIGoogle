package com.android.systemui.globalactions;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.HardwareBgDrawable;
import java.util.Objects;

public class GlobalActionsFlatLayout extends GlobalActionsLayout {
    public final HardwareBgDrawable getBackgroundDrawable(int i) {
        return null;
    }

    public final void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        View findViewById = findViewById(C1777R.C1779id.global_actions_overflow_button);
        if (findViewById != null) {
            getListView().removeView(findViewById);
            super.addToListView(findViewById, z);
        }
    }

    @VisibleForTesting
    public float getAnimationDistance() {
        return getGridItemSize() / 2.0f;
    }

    @VisibleForTesting
    public float getGridItemSize() {
        return getContext().getResources().getDimension(C1777R.dimen.global_actions_grid_item_height);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ViewGroup listView = getListView();
        boolean z2 = false;
        for (int i5 = 0; i5 < listView.getChildCount(); i5++) {
            View childAt = listView.getChildAt(i5);
            if (childAt instanceof GlobalActionsItem) {
                GlobalActionsItem globalActionsItem = (GlobalActionsItem) childAt;
                if (z2 || globalActionsItem.isTruncated()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
        }
        if (z2) {
            for (int i6 = 0; i6 < listView.getChildCount(); i6++) {
                View childAt2 = listView.getChildAt(i6);
                if (childAt2 instanceof GlobalActionsItem) {
                    GlobalActionsItem globalActionsItem2 = (GlobalActionsItem) childAt2;
                    Objects.requireNonNull(globalActionsItem2);
                    TextView textView = (TextView) globalActionsItem2.findViewById(16908299);
                    textView.setSingleLine(true);
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                }
            }
        }
    }

    public final void removeAllListViews() {
        View findViewById = findViewById(C1777R.C1779id.global_actions_overflow_button);
        super.removeAllListViews();
        if (findViewById != null) {
            super.addToListView(findViewById, false);
        }
    }

    @VisibleForTesting
    public boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        if (currentRotation == 0) {
            return false;
        }
        if (getCurrentLayoutDirection() == 1) {
            if (currentRotation == 1) {
                return true;
            }
            return false;
        } else if (currentRotation == 3) {
            return true;
        } else {
            return false;
        }
    }

    public GlobalActionsFlatLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
