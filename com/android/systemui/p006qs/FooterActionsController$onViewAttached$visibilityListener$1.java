package com.android.systemui.p006qs;

import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.util.DualHeightHorizontalLinearLayout;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FooterActionsController$onViewAttached$visibilityListener$1 */
/* compiled from: FooterActionsController.kt */
public final class FooterActionsController$onViewAttached$visibilityListener$1 implements VisibilityChangedDispatcher$OnVisibilityChangedListener {
    public final /* synthetic */ View $fgsFooter;
    public final /* synthetic */ DualHeightHorizontalLinearLayout $securityFooter;
    public final /* synthetic */ FooterActionsController this$0;

    public FooterActionsController$onViewAttached$visibilityListener$1(FooterActionsController footerActionsController, DualHeightHorizontalLinearLayout dualHeightHorizontalLinearLayout, View view) {
        this.this$0 = footerActionsController;
        this.$securityFooter = dualHeightHorizontalLinearLayout;
        this.$fgsFooter = view;
    }

    public final void onVisibilityChanged(int i) {
        boolean z;
        int i2;
        float f;
        int i3 = 0;
        int i4 = 8;
        if (i == 8) {
            this.this$0.securityFootersSeparator.setVisibility(8);
        } else if (this.$securityFooter.getVisibility() == 0 && this.$fgsFooter.getVisibility() == 0) {
            this.this$0.securityFootersSeparator.setVisibility(0);
        } else {
            this.this$0.securityFootersSeparator.setVisibility(8);
        }
        QSFgsManagerFooter qSFgsManagerFooter = this.this$0.fgsManagerFooterController;
        if (this.$securityFooter.getVisibility() == 0) {
            z = true;
        } else {
            z = false;
        }
        Objects.requireNonNull(qSFgsManagerFooter);
        View view = qSFgsManagerFooter.mTextContainer;
        if (z) {
            i2 = 8;
        } else {
            i2 = 0;
        }
        view.setVisibility(i2);
        View view2 = qSFgsManagerFooter.mNumberContainer;
        if (z) {
            i4 = 0;
        }
        view2.setVisibility(i4);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) qSFgsManagerFooter.mRootView.getLayoutParams();
        if (z) {
            i3 = -2;
        }
        layoutParams.width = i3;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        layoutParams.weight = f;
        qSFgsManagerFooter.mRootView.setLayoutParams(layoutParams);
    }
}
