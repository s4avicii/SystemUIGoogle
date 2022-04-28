package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import java.util.Objects;

public class GlifRecyclerLayout extends GlifLayout {
    public RecyclerMixin recyclerMixin;

    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = C1777R.C1779id.sud_recycler_view;
        }
        return super.findContainer(i);
    }

    public final <T extends View> T findManagedViewById(int i) {
        T findViewById;
        RecyclerMixin recyclerMixin2 = this.recyclerMixin;
        Objects.requireNonNull(recyclerMixin2);
        View view = recyclerMixin2.header;
        if (view == null || (findViewById = view.findViewById(i)) == null) {
            return super.findViewById(i);
        }
        return findViewById;
    }

    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = C1777R.layout.sud_glif_recycler_template;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    public GlifRecyclerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (!isInEditMode()) {
            this.recyclerMixin.parseAttributes(attributeSet);
            registerMixin(RecyclerMixin.class, this.recyclerMixin);
            Objects.requireNonNull(this.recyclerMixin);
            Objects.requireNonNull((RequireScrollMixin) getMixin(RequireScrollMixin.class));
            View findManagedViewById = findManagedViewById(C1777R.C1779id.sud_landscape_content_area);
            if (findManagedViewById != null) {
                tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
            }
            updateLandscapeMiddleHorizontalSpacing();
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        RecyclerMixin recyclerMixin2 = this.recyclerMixin;
        Objects.requireNonNull(recyclerMixin2);
        if (recyclerMixin2.divider == null) {
            recyclerMixin2.updateDivider();
        }
    }

    public void onTemplateInflated() {
        View findViewById = findViewById(C1777R.C1779id.sud_recycler_view);
        if (findViewById instanceof RecyclerView) {
            this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) findViewById);
            return;
        }
        throw new IllegalStateException("GlifRecyclerLayout should use a template with recycler view");
    }
}
