package com.android.systemui.p006qs.customize;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.android.systemui.p006qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p006qs.tileimpl.QSTileViewImpl;
import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.customize.CustomizeTileView */
/* compiled from: CustomizeTileView.kt */
public final class CustomizeTileView extends QSTileViewImpl {
    public boolean showAppLabel;
    public boolean showSideView = true;

    public CustomizeTileView(Context context, QSIconViewImpl qSIconViewImpl) {
        super(context, qSIconViewImpl, false);
    }

    public final boolean animationsEnabled() {
        return false;
    }

    public final boolean isLongClickable() {
        return false;
    }

    public final void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
        int i = 0;
        this.showRippleEffect = false;
        TextView secondaryLabel = getSecondaryLabel();
        CharSequence charSequence = state.secondaryLabel;
        if (!this.showAppLabel || TextUtils.isEmpty(charSequence)) {
            i = 8;
        }
        secondaryLabel.setVisibility(i);
        if (!this.showSideView) {
            getSideView().setVisibility(8);
        }
    }
}
