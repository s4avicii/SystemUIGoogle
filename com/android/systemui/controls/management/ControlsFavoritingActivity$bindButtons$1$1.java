package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$bindButtons$1$1 implements View.OnClickListener {
    public final /* synthetic */ Button $this_apply;
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$bindButtons$1$1(ControlsFavoritingActivity controlsFavoritingActivity, Button button) {
        this.this$0 = controlsFavoritingActivity;
        this.$this_apply = button;
    }

    public final void onClick(View view) {
        View view2 = this.this$0.doneButton;
        if (view2 == null) {
            view2 = null;
        }
        if (view2.isEnabled()) {
            Toast.makeText(this.this$0.getApplicationContext(), C1777R.string.controls_favorite_toast_no_changes, 0).show();
        }
        this.this$0.startActivity(new Intent(this.$this_apply.getContext(), ControlsProviderSelectorActivity.class), ActivityOptions.makeSceneTransitionAnimation(this.this$0, new Pair[0]).toBundle());
        this.this$0.animateExitAndFinish();
    }
}
