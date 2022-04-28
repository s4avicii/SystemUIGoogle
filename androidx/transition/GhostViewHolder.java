package androidx.transition;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;

@SuppressLint({"ViewConstructor"})
public final class GhostViewHolder extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAttached = true;
    public ViewGroup mParent;

    public final void onViewAdded(View view) {
        if (this.mAttached) {
            super.onViewAdded(view);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    public GhostViewHolder(ViewGroup viewGroup) {
        super(viewGroup.getContext());
        setClipChildren(false);
        this.mParent = viewGroup;
        viewGroup.setTag(C1777R.C1779id.ghost_view_holder, this);
        this.mParent.getOverlay().add(this);
    }

    public static void getParents(View view, ArrayList<View> arrayList) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            getParents((View) parent, arrayList);
        }
        arrayList.add(view);
    }

    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if ((getChildCount() == 1 && getChildAt(0) == view) || getChildCount() == 0) {
            this.mParent.setTag(C1777R.C1779id.ghost_view_holder, (Object) null);
            this.mParent.getOverlay().remove(this);
            this.mAttached = false;
        }
    }
}
