package com.android.settingslib.notification;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.p012wm.shell.C1777R;

public final class ConversationIconFactory extends BaseIconFactory {
    public final LauncherApps mLauncherApps;
    public final PackageManager mPackageManager;

    static {
        Math.sqrt(288.0d);
    }

    public ConversationIconFactory(Context context, LauncherApps launcherApps, PackageManager packageManager, int i) {
        super(context, context.getResources().getConfiguration().densityDpi, i, false);
        this.mLauncherApps = launcherApps;
        this.mPackageManager = packageManager;
        context.getResources().getColor(C1777R.color.important_conversation, (Resources.Theme) null);
    }
}
