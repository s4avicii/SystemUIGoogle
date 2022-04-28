package com.android.systemui.p006qs;

import android.view.View;

/* renamed from: com.android.systemui.qs.QSFooter */
public interface QSFooter {
    void disable(int i) {
    }

    void setExpandClickListener(View.OnClickListener onClickListener);

    void setExpanded(boolean z);

    void setExpansion(float f);

    void setKeyguardShowing(boolean z);

    void setVisibility(int i);
}
