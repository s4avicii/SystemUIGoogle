package com.android.systemui.controls.management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.management.ControlsModel;
import java.util.List;
import java.util.Objects;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlAdapter.kt */
public final class ControlAdapter extends RecyclerView.Adapter<Holder> {
    public final float elevation;
    public ControlsModel model;
    public final ControlAdapter$spanSizeLookup$1 spanSizeLookup = new ControlAdapter$spanSizeLookup$1(this);

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            holder.bindData(controlsModel.getElements().get(i));
        }
    }

    public final int getItemCount() {
        List<ElementWrapper> elements;
        ControlsModel controlsModel = this.model;
        if (controlsModel == null || (elements = controlsModel.getElements()) == null) {
            return 0;
        }
        return elements.size();
    }

    public final int getItemViewType(int i) {
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            ElementWrapper elementWrapper = controlsModel.getElements().get(i);
            if (elementWrapper instanceof ZoneNameWrapper) {
                return 0;
            }
            if ((elementWrapper instanceof ControlStatusWrapper) || (elementWrapper instanceof ControlInfoWrapper)) {
                return 1;
            }
            if (elementWrapper instanceof DividerWrapper) {
                return 2;
            }
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException("Getting item type for null model");
    }

    public ControlAdapter(float f) {
        this.elevation = f;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        ControlsModel.MoveHelper moveHelper;
        LayoutInflater from = LayoutInflater.from(recyclerView.getContext());
        if (i == 0) {
            return new ZoneHolder(from.inflate(C1777R.layout.controls_zone_header, recyclerView, false));
        }
        if (i == 1) {
            View inflate = from.inflate(C1777R.layout.controls_base_item, recyclerView, false);
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.width = -1;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
            inflate.setElevation(this.elevation);
            inflate.setBackground(recyclerView.getContext().getDrawable(C1777R.C1778drawable.control_background_ripple));
            ControlsModel controlsModel = this.model;
            if (controlsModel == null) {
                moveHelper = null;
            } else {
                moveHelper = controlsModel.getMoveHelper();
            }
            return new ControlHolder(inflate, moveHelper, new ControlAdapter$onCreateViewHolder$2(this));
        } else if (i == 2) {
            return new DividerHolder(from.inflate(C1777R.layout.controls_horizontal_divider_with_empty, recyclerView, false));
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("Wrong viewType: ", Integer.valueOf(i)));
        }
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        Holder holder = (Holder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(holder, i);
            return;
        }
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            ElementWrapper elementWrapper = controlsModel.getElements().get(i);
            if (elementWrapper instanceof ControlInterface) {
                holder.updateFavorite(((ControlInterface) elementWrapper).getFavorite());
            }
        }
    }
}
