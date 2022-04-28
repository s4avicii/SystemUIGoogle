package com.android.systemui.controls.management;

import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.management.FavoritesModel;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity$favoritesModelCallback$1 implements FavoritesModel.FavoritesModelCallback {
    public final /* synthetic */ ControlsEditingActivity this$0;

    public final void onNoneChanged(boolean z) {
        TextView textView = null;
        if (z) {
            TextView textView2 = this.this$0.subtitle;
            if (textView2 != null) {
                textView = textView2;
            }
            int i = ControlsEditingActivity.$r8$clinit;
            textView.setText(C1777R.string.controls_favorite_removed);
            return;
        }
        TextView textView3 = this.this$0.subtitle;
        if (textView3 != null) {
            textView = textView3;
        }
        int i2 = ControlsEditingActivity.$r8$clinit;
        textView.setText(C1777R.string.controls_favorite_rearrange);
    }

    public ControlsEditingActivity$favoritesModelCallback$1(ControlsEditingActivity controlsEditingActivity) {
        this.this$0 = controlsEditingActivity;
    }

    public final void onFirstChange() {
        View view = this.this$0.saveButton;
        if (view == null) {
            view = null;
        }
        view.setEnabled(true);
    }
}
