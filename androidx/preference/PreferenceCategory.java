package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import com.android.p012wm.shell.C1777R;

public class PreferenceCategory extends PreferenceGroup {
    public final boolean isEnabled() {
        return false;
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, TypedArrayUtils.getAttr(context, C1777R.attr.preferenceCategoryStyle, 16842892), 0);
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setAccessibilityHeading(true);
    }

    public final boolean shouldDisableDependents() {
        return !super.isEnabled();
    }
}
