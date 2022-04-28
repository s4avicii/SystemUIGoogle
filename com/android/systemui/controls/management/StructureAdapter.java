package com.android.systemui.controls.management;

import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import java.util.List;
import java.util.Objects;

/* compiled from: StructureAdapter.kt */
public final class StructureAdapter extends RecyclerView.Adapter<StructureHolder> {
    public final List<StructureContainer> models;

    /* compiled from: StructureAdapter.kt */
    public static final class StructureHolder extends RecyclerView.ViewHolder {
        public final ControlAdapter controlAdapter;

        public StructureHolder(View view) {
            super(view);
            RecyclerView recyclerView = (RecyclerView) view.requireViewById(C1777R.C1779id.listAll);
            ControlAdapter controlAdapter2 = new ControlAdapter(view.getContext().getResources().getFloat(C1777R.dimen.control_card_elevation));
            this.controlAdapter = controlAdapter2;
            int dimensionPixelSize = view.getContext().getResources().getDimensionPixelSize(C1777R.dimen.controls_card_margin);
            MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
            recyclerView.setAdapter(controlAdapter2);
            recyclerView.getContext();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(2);
            gridLayoutManager.mSpanSizeLookup = controlAdapter2.spanSizeLookup;
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(marginItemDecorator);
        }
    }

    public final int getItemCount() {
        return this.models.size();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        StructureContainer structureContainer = this.models.get(i);
        Objects.requireNonNull(structureContainer);
        ControlsModel controlsModel = structureContainer.model;
        ControlAdapter controlAdapter = ((StructureHolder) viewHolder).controlAdapter;
        Objects.requireNonNull(controlAdapter);
        controlAdapter.model = controlsModel;
        controlAdapter.notifyDataSetChanged();
    }

    public StructureAdapter(List<StructureContainer> list) {
        this.models = list;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new StructureHolder(LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.controls_structure_page, recyclerView, false));
    }
}
