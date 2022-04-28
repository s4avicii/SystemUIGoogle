package com.android.systemui.accessibility;

import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MagnificationModeSwitch$$ExternalSyntheticLambda1 implements View.OnApplyWindowInsetsListener {
    public final /* synthetic */ MagnificationModeSwitch f$0;

    public /* synthetic */ MagnificationModeSwitch$$ExternalSyntheticLambda1(MagnificationModeSwitch magnificationModeSwitch) {
        this.f$0 = magnificationModeSwitch;
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        MagnificationModeSwitch magnificationModeSwitch = this.f$0;
        Objects.requireNonNull(magnificationModeSwitch);
        if (!magnificationModeSwitch.mImageView.getHandler().hasCallbacks(magnificationModeSwitch.mWindowInsetChangeRunnable)) {
            magnificationModeSwitch.mImageView.getHandler().post(magnificationModeSwitch.mWindowInsetChangeRunnable);
        }
        return view.onApplyWindowInsets(windowInsets);
    }
}
