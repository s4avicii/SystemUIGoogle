package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.view.View;
import com.android.systemui.tuner.PluginFragment;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PluginFragment$PluginPreference$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ PluginFragment.PluginPreference f$0;

    public /* synthetic */ PluginFragment$PluginPreference$$ExternalSyntheticLambda0(PluginFragment.PluginPreference pluginPreference) {
        this.f$0 = pluginPreference;
    }

    public final void onClick(View view) {
        PluginFragment.PluginPreference pluginPreference = this.f$0;
        Objects.requireNonNull(pluginPreference);
        ResolveInfo resolveActivity = view.getContext().getPackageManager().resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(pluginPreference.mInfo.packageName), 0);
        if (resolveActivity != null) {
            Context context = view.getContext();
            Intent intent = new Intent();
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            context.startActivity(intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name)));
        }
    }
}
