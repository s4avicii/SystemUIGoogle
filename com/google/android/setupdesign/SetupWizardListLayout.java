package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.template.ListMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import java.util.Objects;

public class SetupWizardListLayout extends SetupWizardLayout {
    public ListMixin listMixin;

    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = 16908298;
        }
        return super.findContainer(i);
    }

    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = C1777R.layout.sud_list_template;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    public SetupWizardListLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (!isInEditMode()) {
            ListMixin listMixin2 = new ListMixin(this, attributeSet);
            this.listMixin = listMixin2;
            registerMixin(ListMixin.class, listMixin2);
            ListMixin listMixin3 = this.listMixin;
            Objects.requireNonNull(listMixin3);
            listMixin3.getListViewInternal();
            Objects.requireNonNull((RequireScrollMixin) getMixin(RequireScrollMixin.class));
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ListMixin listMixin2 = this.listMixin;
        Objects.requireNonNull(listMixin2);
        if (listMixin2.divider == null) {
            listMixin2.updateDivider();
        }
    }
}
