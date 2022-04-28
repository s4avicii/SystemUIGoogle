package androidx.recyclerview.widget;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

public abstract class SnapHelper extends RecyclerView.OnFlingListener {
    public RecyclerView mRecyclerView;
    public final C03411 mScrollListener = new RecyclerView.OnScrollListener() {
        public boolean mScrolled = false;

        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0 && this.mScrolled) {
                this.mScrolled = false;
                SnapHelper.this.snapToTargetExistingView();
            }
        }

        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (i != 0 || i2 != 0) {
                this.mScrolled = true;
            }
        }
    };

    public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View view);

    public abstract RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager);

    public abstract View findSnapView(RecyclerView.LayoutManager layoutManager);

    public final void attachToRecyclerView(RecyclerView recyclerView) throws IllegalStateException {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                C03411 r1 = this.mScrollListener;
                ArrayList arrayList = recyclerView2.mScrollListeners;
                if (arrayList != null) {
                    arrayList.remove(r1);
                }
                RecyclerView recyclerView3 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView3);
                recyclerView3.mOnFlingListener = null;
            }
            this.mRecyclerView = recyclerView;
            if (recyclerView == null) {
                return;
            }
            if (recyclerView.mOnFlingListener == null) {
                recyclerView.addOnScrollListener(this.mScrollListener);
                RecyclerView recyclerView4 = this.mRecyclerView;
                Objects.requireNonNull(recyclerView4);
                recyclerView4.mOnFlingListener = this;
                new Scroller(this.mRecyclerView.getContext(), new DecelerateInterpolator());
                snapToTargetExistingView();
                return;
            }
            throw new IllegalStateException("An instance of OnFlingListener already set.");
        }
    }

    public final void snapToTargetExistingView() {
        View findSnapView;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            Objects.requireNonNull(recyclerView);
            RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
            if (layoutManager != null && (findSnapView = findSnapView(layoutManager)) != null) {
                int[] calculateDistanceToFinalSnap = calculateDistanceToFinalSnap(layoutManager, findSnapView);
                if (calculateDistanceToFinalSnap[0] != 0 || calculateDistanceToFinalSnap[1] != 0) {
                    this.mRecyclerView.smoothScrollBy(calculateDistanceToFinalSnap[0], calculateDistanceToFinalSnap[1]);
                }
            }
        }
    }
}
