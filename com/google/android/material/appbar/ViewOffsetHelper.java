package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.WeakHashMap;

public final class ViewOffsetHelper {
    public int layoutLeft;
    public int layoutTop;
    public int offsetLeft;
    public int offsetTop;
    public final View view;

    public final void applyOffsets() {
        View view2 = this.view;
        int top = this.offsetTop - (view2.getTop() - this.layoutTop);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view2.offsetTopAndBottom(top);
        View view3 = this.view;
        view3.offsetLeftAndRight(this.offsetLeft - (view3.getLeft() - this.layoutLeft));
    }

    public final boolean setTopAndBottomOffset(int i) {
        if (this.offsetTop == i) {
            return false;
        }
        this.offsetTop = i;
        applyOffsets();
        return true;
    }

    public ViewOffsetHelper(View view2) {
        this.view = view2;
    }
}
