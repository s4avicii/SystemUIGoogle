package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarIconControllerImpl$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StatusBarIconControllerImpl$$ExternalSyntheticLambda3(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        int i2 = this.f$1;
        StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) obj;
        Objects.requireNonNull(iconManager);
        ImageView imageView = (ImageView) iconManager.mGroup.getChildAt(i);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = i2;
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).gravity = 16;
        }
        imageView.setLayoutParams(layoutParams);
    }
}
