package com.android.systemui.accessibility.floatingmenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.List;

public final class AccessibilityTargetAdapter extends RecyclerView.Adapter<ViewHolder> {
    public int mIconWidthHeight;
    public int mItemPadding;
    public final List<AccessibilityTarget> mTargets;

    public static class BottomViewHolder extends ViewHolder {
        public final void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, i);
        }

        public BottomViewHolder(View view) {
            super(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mIconView;

        public void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, 0);
        }

        public ViewHolder(View view) {
            super(view);
            this.mIconView = view.findViewById(C1777R.C1779id.icon_view);
        }
    }

    public final int getItemCount() {
        return this.mTargets.size();
    }

    public final int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == getItemCount() - 1) {
            return 2;
        }
        return 1;
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String str;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        viewHolder2.mIconView.setBackground(accessibilityTarget.getIcon());
        int i2 = this.mIconWidthHeight;
        ViewGroup.LayoutParams layoutParams = viewHolder2.mIconView.getLayoutParams();
        if (layoutParams.width != i2) {
            layoutParams.width = i2;
            layoutParams.height = i2;
            viewHolder2.mIconView.setLayoutParams(layoutParams);
        }
        viewHolder2.updateItemPadding(this.mItemPadding, getItemCount());
        viewHolder2.itemView.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda4(accessibilityTarget, 2));
        viewHolder2.itemView.setStateDescription(accessibilityTarget.getStateDescription());
        viewHolder2.itemView.setContentDescription(accessibilityTarget.getLabel());
        if (accessibilityTarget.getFragmentType() == 2) {
            str = viewHolder2.itemView.getResources().getString(C1777R.string.accessibility_floating_button_action_double_tap_to_toggle);
        } else {
            str = null;
        }
        ViewCompat.replaceAccessibilityAction(viewHolder2.itemView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, str, (AccessibilityViewCommand) null);
    }

    public AccessibilityTargetAdapter(ArrayList arrayList) {
        this.mTargets = arrayList;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.accessibility_floating_menu_item, recyclerView, false);
        if (i == 0) {
            return new TopViewHolder(inflate);
        }
        if (i == 2) {
            return new BottomViewHolder(inflate);
        }
        return new ViewHolder(inflate);
    }

    public static class TopViewHolder extends ViewHolder {
        public final void updateItemPadding(int i, int i2) {
            int i3;
            if (i2 <= 1) {
                i3 = i;
            } else {
                i3 = 0;
            }
            this.itemView.setPaddingRelative(i, i, i, i3);
        }

        public TopViewHolder(View view) {
            super(view);
        }
    }
}
