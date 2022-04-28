package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;

/* compiled from: RemoteInputQuickSettingsDisabler.kt */
public final class RemoteInputQuickSettingsDisabler implements ConfigurationController.ConfigurationListener {
    public final CommandQueue commandQueue;
    public final Context context;
    public boolean isLandscape;
    public boolean remoteInputActive;
    public boolean shouldUseSplitNotificationShade;

    public final void onConfigChanged(Configuration configuration) {
        boolean z;
        boolean z2 = false;
        if (configuration.orientation == 2) {
            z = true;
        } else {
            z = false;
        }
        if (z != this.isLandscape) {
            this.isLandscape = z;
            z2 = true;
        }
        boolean shouldUseSplitNotificationShade2 = Utils.shouldUseSplitNotificationShade(this.context.getResources());
        if (shouldUseSplitNotificationShade2 != this.shouldUseSplitNotificationShade) {
            this.shouldUseSplitNotificationShade = shouldUseSplitNotificationShade2;
            z2 = true;
        }
        if (z2) {
            this.commandQueue.recomputeDisableFlags(this.context.getDisplayId(), true);
        }
    }

    public RemoteInputQuickSettingsDisabler(Context context2, CommandQueue commandQueue2, ConfigurationController configurationController) {
        boolean z;
        this.context = context2;
        this.commandQueue = commandQueue2;
        if (context2.getResources().getConfiguration().orientation == 2) {
            z = true;
        } else {
            z = false;
        }
        this.isLandscape = z;
        this.shouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(context2.getResources());
        configurationController.addCallback(this);
    }
}
