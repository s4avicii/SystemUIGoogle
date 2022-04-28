package com.android.systemui.p008tv;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda3;

/* renamed from: com.android.systemui.tv.TvBottomSheetActivity */
public abstract class TvBottomSheetActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Drawable mBackgroundWithBlur;
    public Drawable mBackgroundWithoutBlur;
    public final ShellCommandHandlerImpl$$ExternalSyntheticLambda3 mBlurConsumer = new ShellCommandHandlerImpl$$ExternalSyntheticLambda3(this, 2);

    public final void finish() {
        super.finish();
        overridePendingTransition(0, C1777R.anim.tv_bottom_sheet_exit);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindowManager().addCrossWindowBlurEnabledListener(this.mBlurConsumer);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.tv_bottom_sheet);
        overridePendingTransition(C1777R.anim.tv_bottom_sheet_enter, 0);
        this.mBackgroundWithBlur = getResources().getDrawable(C1777R.C1778drawable.bottom_sheet_background_with_blur);
        this.mBackgroundWithoutBlur = getResources().getDrawable(C1777R.C1778drawable.bottom_sheet_background);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.bottom_sheet_margin);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = i - (dimensionPixelSize * 2);
        attributes.height = -2;
        attributes.gravity = 81;
        attributes.horizontalMargin = 0.0f;
        attributes.verticalMargin = ((float) dimensionPixelSize) / ((float) i2);
        attributes.format = -2;
        attributes.type = 2008;
        attributes.flags = attributes.flags | 128 | 16777216;
        getWindow().setAttributes(attributes);
        getWindow().setElevation(getWindow().getElevation() + 5.0f);
        getWindow().setBackgroundBlurRadius(getResources().getDimensionPixelSize(C1777R.dimen.bottom_sheet_background_blur_radius));
    }

    public final void onDetachedFromWindow() {
        getWindowManager().removeCrossWindowBlurEnabledListener(this.mBlurConsumer);
        super.onDetachedFromWindow();
    }
}
