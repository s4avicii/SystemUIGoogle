package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.template.RecyclerMixin;

public class GlifPreferenceLayout extends GlifRecyclerLayout {
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = C1777R.C1779id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = C1777R.layout.sud_glif_preference_template;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    public final void onTemplateInflated() {
        this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) LayoutInflater.from(getContext()).inflate(C1777R.layout.sud_glif_preference_recycler_view, this, false));
    }

    public GlifPreferenceLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
