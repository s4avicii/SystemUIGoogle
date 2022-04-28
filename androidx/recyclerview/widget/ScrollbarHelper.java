package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public final class ScrollbarHelper {
    public static int computeScrollExtent(RecyclerView.State state, OrientationHelper orientationHelper, View view, View view2, RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(RecyclerView.LayoutManager.getPosition(view) - RecyclerView.LayoutManager.getPosition(view2)) + 1;
        }
        return Math.min(orientationHelper.getTotalSpace(), orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view));
    }

    public static int computeScrollOffset(RecyclerView.State state, OrientationHelper orientationHelper, View view, View view2, RecyclerView.LayoutManager layoutManager, boolean z, boolean z2) {
        int i;
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        int min = Math.min(RecyclerView.LayoutManager.getPosition(view), RecyclerView.LayoutManager.getPosition(view2));
        int max = Math.max(RecyclerView.LayoutManager.getPosition(view), RecyclerView.LayoutManager.getPosition(view2));
        if (z2) {
            i = Math.max(0, (state.getItemCount() - max) - 1);
        } else {
            i = Math.max(0, min);
        }
        if (!z) {
            return i;
        }
        return Math.round((((float) i) * (((float) Math.abs(orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view))) / ((float) (Math.abs(RecyclerView.LayoutManager.getPosition(view) - RecyclerView.LayoutManager.getPosition(view2)) + 1)))) + ((float) (orientationHelper.getStartAfterPadding() - orientationHelper.getDecoratedStart(view))));
    }

    public static int computeScrollRange(RecyclerView.State state, OrientationHelper orientationHelper, View view, View view2, RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return state.getItemCount();
        }
        return (int) ((((float) (orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view))) / ((float) (Math.abs(RecyclerView.LayoutManager.getPosition(view) - RecyclerView.LayoutManager.getPosition(view2)) + 1))) * ((float) state.getItemCount()));
    }
}
