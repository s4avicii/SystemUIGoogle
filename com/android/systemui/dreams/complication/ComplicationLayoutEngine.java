package com.android.systemui.dreams.complication;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public final class ComplicationLayoutEngine {
    public final HashMap<ComplicationId, ViewEntry> mEntries = new HashMap<>();
    public final ConstraintLayout mLayout;
    public final int mMargin;
    public final HashMap<Integer, PositionGroup> mPositions = new HashMap<>();

    public static class PositionGroup implements DirectionGroup.Parent {
        public final HashMap<Integer, DirectionGroup> mDirectionGroups = new HashMap<>();

        public final void onEntriesChanged() {
            boolean z;
            ViewEntry viewEntry;
            ViewEntry viewEntry2 = null;
            for (DirectionGroup next : this.mDirectionGroups.values()) {
                Objects.requireNonNull(next);
                if (next.mViews.isEmpty()) {
                    viewEntry = null;
                } else {
                    viewEntry = next.mViews.get(0);
                }
                if (viewEntry2 == null || (viewEntry != null && viewEntry.compareTo(viewEntry2) > 0)) {
                    viewEntry2 = viewEntry;
                }
            }
            if (viewEntry2 != null) {
                for (DirectionGroup next2 : this.mDirectionGroups.values()) {
                    View view = viewEntry2.mView;
                    Objects.requireNonNull(next2);
                    Iterator<ViewEntry> it = next2.mViews.iterator();
                    View view2 = view;
                    while (it.hasNext()) {
                        ViewEntry next3 = it.next();
                        Objects.requireNonNull(next3);
                        ComplicationLayoutParams complicationLayoutParams = next3.mLayoutParams;
                        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(complicationLayoutParams.width, complicationLayoutParams.height);
                        ComplicationLayoutParams complicationLayoutParams2 = next3.mLayoutParams;
                        Objects.requireNonNull(complicationLayoutParams2);
                        int i = complicationLayoutParams2.mDirection;
                        ComplicationLayoutParams complicationLayoutParams3 = next3.mLayoutParams;
                        Objects.requireNonNull(complicationLayoutParams3);
                        boolean z2 = complicationLayoutParams3.mSnapToGuide;
                        if (view2 == next3.mView) {
                            z = true;
                        } else {
                            z = false;
                        }
                        ComplicationLayoutParams complicationLayoutParams4 = next3.mLayoutParams;
                        ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0 complicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0 = new ComplicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0(next3, z, i, layoutParams, view2, z2);
                        for (int i2 = 1; i2 <= 8; i2 <<= 1) {
                            if ((complicationLayoutParams4.mPosition & i2) == i2) {
                                complicationLayoutEngine$ViewEntry$$ExternalSyntheticLambda0.accept(Integer.valueOf(i2));
                            }
                        }
                        Objects.requireNonNull(complicationLayoutParams4);
                        next3.mView.setLayoutParams(layoutParams);
                        view2 = next3.mView;
                    }
                }
            }
        }
    }

    public static class ViewEntry implements Comparable<ViewEntry> {
        public final int mCategory;
        public final ComplicationLayoutParams mLayoutParams;
        public final int mMargin;
        public final Parent mParent;
        public final View mView;

        public interface Parent {
        }

        public final int compareTo(ViewEntry viewEntry) {
            int i = viewEntry.mCategory;
            int i2 = this.mCategory;
            if (i != i2) {
                return i2 == 2 ? 1 : -1;
            }
            ComplicationLayoutParams complicationLayoutParams = viewEntry.mLayoutParams;
            Objects.requireNonNull(complicationLayoutParams);
            int i3 = complicationLayoutParams.mWeight;
            ComplicationLayoutParams complicationLayoutParams2 = this.mLayoutParams;
            Objects.requireNonNull(complicationLayoutParams2);
            if (i3 == complicationLayoutParams2.mWeight) {
                return 0;
            }
            ComplicationLayoutParams complicationLayoutParams3 = this.mLayoutParams;
            Objects.requireNonNull(complicationLayoutParams3);
            int i4 = complicationLayoutParams3.mWeight;
            ComplicationLayoutParams complicationLayoutParams4 = viewEntry.mLayoutParams;
            Objects.requireNonNull(complicationLayoutParams4);
            if (i4 > complicationLayoutParams4.mWeight) {
                return 1;
            }
            return -1;
        }

        public ViewEntry(View view, ComplicationLayoutParams complicationLayoutParams, int i, Parent parent, int i2) {
            this.mView = view;
            view.setId(View.generateViewId());
            this.mLayoutParams = complicationLayoutParams;
            this.mCategory = i;
            this.mParent = parent;
            this.mMargin = i2;
        }
    }

    public static class DirectionGroup implements ViewEntry.Parent {
        public final Parent mParent;
        public final ArrayList<ViewEntry> mViews = new ArrayList<>();

        public interface Parent {
        }

        public DirectionGroup(PositionGroup positionGroup) {
            this.mParent = positionGroup;
        }
    }

    public final void removeComplication(ComplicationId complicationId) {
        if (!this.mEntries.containsKey(complicationId)) {
            Log.e("ComplicationLayoutEngine", "could not find id:" + complicationId);
            return;
        }
        ViewEntry viewEntry = this.mEntries.get(complicationId);
        Objects.requireNonNull(viewEntry);
        DirectionGroup directionGroup = (DirectionGroup) viewEntry.mParent;
        Objects.requireNonNull(directionGroup);
        directionGroup.mViews.remove(viewEntry);
        ((PositionGroup) directionGroup.mParent).onEntriesChanged();
        ((ViewGroup) viewEntry.mView.getParent()).removeView(viewEntry.mView);
    }

    public ComplicationLayoutEngine(ConstraintLayout constraintLayout, int i) {
        this.mLayout = constraintLayout;
        this.mMargin = i;
    }
}
