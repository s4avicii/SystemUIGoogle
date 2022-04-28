package com.android.systemui.controls.p004ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.controls.ui.ItemAdapter */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ItemAdapter extends ArrayAdapter<SelectionItem> {
    public final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    public final int resource = C1777R.layout.controls_spinner_item;

    public ItemAdapter(Context context) {
        super(context, C1777R.layout.controls_spinner_item);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        SelectionItem selectionItem = (SelectionItem) getItem(i);
        if (view == null) {
            view = this.layoutInflater.inflate(this.resource, viewGroup, false);
        }
        ((TextView) view.requireViewById(C1777R.C1779id.controls_spinner_item)).setText(selectionItem.getTitle());
        ((ImageView) view.requireViewById(C1777R.C1779id.app_icon)).setImageDrawable(selectionItem.icon);
        return view;
    }
}
