package com.android.systemui.globalactions;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.HardwareBgDrawable;
import java.util.Objects;

public class GlobalActionsLayoutLite extends GlobalActionsLayout {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final HardwareBgDrawable getBackgroundDrawable(int i) {
        return null;
    }

    @VisibleForTesting
    public boolean shouldReverseListItems() {
        return false;
    }

    public GlobalActionsLayoutLite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnClickListener(GlobalActionsLayoutLite$$ExternalSyntheticLambda0.INSTANCE);
    }

    public final void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        ((Flow) findViewById(C1777R.C1779id.list_flow)).addView(view);
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

    public final void onUpdateList() {
        super.onUpdateList();
        int integer = getResources().getInteger(C1777R.integer.power_menu_lite_max_columns);
        if (getListView().getChildCount() - 1 == integer + 1 && integer > 2) {
            integer--;
        }
        Flow flow = (Flow) findViewById(C1777R.C1779id.list_flow);
        Objects.requireNonNull(flow);
        androidx.constraintlayout.solver.widgets.Flow flow2 = flow.mFlow;
        Objects.requireNonNull(flow2);
        flow2.mMaxElementsWrap = integer;
        flow.requestLayout();
    }

    public final void removeAllListViews() {
        View findViewById = findViewById(C1777R.C1779id.list_flow);
        super.removeAllListViews();
        super.addToListView(findViewById, false);
    }
}
