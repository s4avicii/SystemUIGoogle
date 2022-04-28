package com.android.systemui.controls.p004ui;

import android.view.View;
import android.view.WindowInsets;

/* renamed from: com.android.systemui.controls.ui.ControlsActivity$onCreate$1$1 */
/* compiled from: ControlsActivity.kt */
public final class ControlsActivity$onCreate$1$1 implements View.OnApplyWindowInsetsListener {
    public static final ControlsActivity$onCreate$1$1 INSTANCE = new ControlsActivity$onCreate$1$1();

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom);
        return WindowInsets.CONSUMED;
    }
}
