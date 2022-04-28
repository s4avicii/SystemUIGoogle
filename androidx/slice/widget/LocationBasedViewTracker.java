package androidx.slice.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public final class LocationBasedViewTracker implements Runnable, View.OnLayoutChangeListener {
    public static final C03602 A11Y_FOCUS = new SelectionLogic() {
        public final void selectView(View view) {
            view.performAccessibilityAction(64, (Bundle) null);
        }
    };
    public static final C03591 INPUT_FOCUS = new SelectionLogic() {
        public final void selectView(View view) {
            view.requestFocus();
        }
    };
    public final Rect mFocusRect;
    public final ViewGroup mParent;
    public final SelectionLogic mSelectionLogic;

    public interface SelectionLogic {
        void selectView(View view);
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mParent.removeOnLayoutChangeListener(this);
        this.mParent.post(this);
    }

    public final void run() {
        int abs;
        ArrayList arrayList = new ArrayList();
        this.mParent.addFocusables(arrayList, 2, 0);
        Rect rect = new Rect();
        Iterator it = arrayList.iterator();
        int i = Integer.MAX_VALUE;
        View view = null;
        while (it.hasNext()) {
            View view2 = (View) it.next();
            view2.getDrawingRect(rect);
            this.mParent.offsetDescendantRectToMyCoords(view2, rect);
            if (this.mFocusRect.intersect(rect) && i > (abs = Math.abs(this.mFocusRect.bottom - rect.bottom) + Math.abs(this.mFocusRect.top - rect.top) + Math.abs(this.mFocusRect.right - rect.right) + Math.abs(this.mFocusRect.left - rect.left))) {
                view = view2;
                i = abs;
            }
        }
        if (view != null) {
            this.mSelectionLogic.selectView(view);
        }
    }

    public LocationBasedViewTracker(ViewGroup viewGroup, View view, SelectionLogic selectionLogic) {
        Rect rect = new Rect();
        this.mFocusRect = rect;
        this.mParent = viewGroup;
        this.mSelectionLogic = selectionLogic;
        view.getDrawingRect(rect);
        viewGroup.offsetDescendantRectToMyCoords(view, rect);
        viewGroup.addOnLayoutChangeListener(this);
        viewGroup.requestLayout();
    }
}
