package com.android.systemui.controls.management;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Objects;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$setUpPager$1$1 extends ViewPager2.OnPageChangeCallback {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$setUpPager$1$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onPageScrolled(int i, float f, int i2) {
        ManagementPageIndicator managementPageIndicator = this.this$0.pageIndicator;
        if (managementPageIndicator == null) {
            managementPageIndicator = null;
        }
        managementPageIndicator.setLocation(((float) i) + f);
    }

    public final void onPageSelected(int i) {
        StructureContainer structureContainer = this.this$0.listOfStructures.get(i);
        Objects.requireNonNull(structureContainer);
        CharSequence charSequence = structureContainer.structureName;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = this.this$0.appName;
        }
        TextView textView = this.this$0.titleView;
        TextView textView2 = null;
        if (textView == null) {
            textView = null;
        }
        textView.setText(charSequence);
        TextView textView3 = this.this$0.titleView;
        if (textView3 != null) {
            textView2 = textView3;
        }
        textView2.requestFocus();
    }
}
