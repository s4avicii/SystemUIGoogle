package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;
import android.view.ViewGroup;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;

/* compiled from: ControlsProviderSelectorActivity.kt */
public final class ControlsProviderSelectorActivity$launchFavoritingActivity$1 implements Runnable {
    public final /* synthetic */ ComponentName $component;
    public final /* synthetic */ ControlsProviderSelectorActivity this$0;

    public ControlsProviderSelectorActivity$launchFavoritingActivity$1(ComponentName componentName, ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        this.$component = componentName;
        this.this$0 = controlsProviderSelectorActivity;
    }

    public final void run() {
        ComponentName componentName = this.$component;
        if (componentName != null) {
            ControlsProviderSelectorActivity controlsProviderSelectorActivity = this.this$0;
            Intent intent = new Intent(controlsProviderSelectorActivity.getApplicationContext(), ControlsFavoritingActivity.class);
            intent.putExtra("extra_app_label", controlsProviderSelectorActivity.listingController.getAppLabel(componentName));
            intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
            intent.putExtra("extra_from_provider_selector", true);
            controlsProviderSelectorActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(controlsProviderSelectorActivity, new Pair[0]).toBundle());
            R$bool.exitAnimation((ViewGroup) controlsProviderSelectorActivity.requireViewById(C1777R.C1779id.controls_management_root), new ControlsProviderSelectorActivity$animateExitAndFinish$1(controlsProviderSelectorActivity)).start();
        }
    }
}
