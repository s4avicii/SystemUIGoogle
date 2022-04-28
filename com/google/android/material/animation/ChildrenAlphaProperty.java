package com.google.android.material.animation;

import android.util.Property;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;

public final class ChildrenAlphaProperty extends Property<ViewGroup, Float> {
    public static final ChildrenAlphaProperty CHILDREN_ALPHA = new ChildrenAlphaProperty();

    public ChildrenAlphaProperty() {
        super(Float.class, "childrenAlpha");
    }

    public final Object get(Object obj) {
        Float f = (Float) ((ViewGroup) obj).getTag(C1777R.C1779id.mtrl_internal_children_alpha_tag);
        if (f != null) {
            return f;
        }
        return Float.valueOf(1.0f);
    }

    public final void set(Object obj, Object obj2) {
        ViewGroup viewGroup = (ViewGroup) obj;
        float floatValue = ((Float) obj2).floatValue();
        viewGroup.setTag(C1777R.C1779id.mtrl_internal_children_alpha_tag, Float.valueOf(floatValue));
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            viewGroup.getChildAt(i).setAlpha(floatValue);
        }
    }
}
