package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.systemui.controls.ControlsServiceInfo;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;

/* compiled from: AppAdapter.kt */
public final class AppAdapter extends RecyclerView.Adapter<Holder> {
    public final FavoritesRenderer favoritesRenderer;
    public final LayoutInflater layoutInflater;
    public List<ControlsServiceInfo> listOfServices = EmptyList.INSTANCE;
    public final Function1<ComponentName, Unit> onAppSelected;
    public final Resources resources;

    /* compiled from: AppAdapter.kt */
    public static final class Holder extends RecyclerView.ViewHolder {
        public final FavoritesRenderer favRenderer;
        public final TextView favorites;
        public final ImageView icon;
        public final TextView title;

        public Holder(View view, FavoritesRenderer favoritesRenderer) {
            super(view);
            this.favRenderer = favoritesRenderer;
            this.icon = (ImageView) view.requireViewById(16908294);
            this.title = (TextView) view.requireViewById(16908310);
            this.favorites = (TextView) view.requireViewById(C1777R.C1779id.favorites);
        }
    }

    public final int getItemCount() {
        return this.listOfServices.size();
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String str;
        Holder holder = (Holder) viewHolder;
        ControlsServiceInfo controlsServiceInfo = this.listOfServices.get(i);
        holder.icon.setImageDrawable(controlsServiceInfo.loadIcon());
        holder.title.setText(controlsServiceInfo.loadLabel());
        FavoritesRenderer favoritesRenderer2 = holder.favRenderer;
        ComponentName componentName = controlsServiceInfo.componentName;
        Objects.requireNonNull(favoritesRenderer2);
        int intValue = favoritesRenderer2.favoriteFunction.invoke(componentName).intValue();
        int i2 = 0;
        if (intValue != 0) {
            str = favoritesRenderer2.resources.getQuantityString(C1777R.plurals.controls_number_of_favorites, intValue, new Object[]{Integer.valueOf(intValue)});
        } else {
            str = null;
        }
        holder.favorites.setText(str);
        TextView textView = holder.favorites;
        if (str == null) {
            i2 = 8;
        }
        textView.setVisibility(i2);
        holder.itemView.setOnClickListener(new AppAdapter$onBindViewHolder$1(this, i));
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new Holder(this.layoutInflater.inflate(C1777R.layout.controls_app_item, recyclerView, false), this.favoritesRenderer);
    }

    public AppAdapter(Executor executor, Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater2, Function1 function1, FavoritesRenderer favoritesRenderer2, Resources resources2) {
        this.layoutInflater = layoutInflater2;
        this.onAppSelected = function1;
        this.favoritesRenderer = favoritesRenderer2;
        this.resources = resources2;
        controlsListingController.observe((androidx.lifecycle.Lifecycle) lifecycle, new AppAdapter$callback$1(executor, this, executor2));
    }
}
