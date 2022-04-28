package com.android.systemui.tuner;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.android.systemui.tuner.PluginFragment;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PluginFragment$PluginPreference$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ PluginFragment.PluginPreference f$0;

    public /* synthetic */ PluginFragment$PluginPreference$$ExternalSyntheticLambda1(PluginFragment.PluginPreference pluginPreference) {
        this.f$0 = pluginPreference;
    }

    public final boolean onLongClick(View view) {
        PluginFragment.PluginPreference pluginPreference = this.f$0;
        Objects.requireNonNull(pluginPreference);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", pluginPreference.mInfo.packageName, (String) null));
        pluginPreference.mContext.startActivity(intent);
        return true;
    }
}
