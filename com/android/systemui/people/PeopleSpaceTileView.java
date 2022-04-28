package com.android.systemui.people;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public final class PeopleSpaceTileView extends LinearLayout {
    public TextView mNameView;
    public ImageView mPersonIconView;
    public View mTileView;

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mTileView.setOnClickListener(onClickListener);
    }

    public PeopleSpaceTileView(Context context, ViewGroup viewGroup, String str, boolean z) {
        super(context);
        View findViewWithTag = viewGroup.findViewWithTag(str);
        this.mTileView = findViewWithTag;
        if (findViewWithTag == null) {
            LayoutInflater from = LayoutInflater.from(context);
            View inflate = from.inflate(C1777R.layout.people_space_tile_view, viewGroup, false);
            this.mTileView = inflate;
            viewGroup.addView(inflate, -1, -1);
            this.mTileView.setTag(str);
            if (!z) {
                from.inflate(C1777R.layout.people_space_activity_list_divider, viewGroup, true);
            }
        }
        this.mNameView = (TextView) this.mTileView.findViewById(C1777R.C1779id.tile_view_name);
        this.mPersonIconView = (ImageView) this.mTileView.findViewById(C1777R.C1779id.tile_view_person_icon);
    }
}
