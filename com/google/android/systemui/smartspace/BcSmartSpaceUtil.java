package com.google.android.systemui.smartspace;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Objects;

public final class BcSmartSpaceUtil {
    public static FalsingManager sFalsingManager;
    public static BcSmartspaceDataPlugin.IntentStarter sIntentStarter;

    public static void setOnClickListener(View view, SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, final String str, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        String str2 = str;
        if (view == null || smartspaceAction == null) {
            Log.e(str, "No tap action can be set up");
            return;
        }
        boolean z2 = false;
        if (smartspaceAction.getExtras() == null || !smartspaceAction.getExtras().getBoolean("show_on_lockscreen")) {
            z = false;
        } else {
            z = true;
        }
        if (smartspaceAction.getIntent() == null && smartspaceAction.getPendingIntent() == null) {
            z2 = true;
        }
        BcSmartspaceDataPlugin.IntentStarter intentStarter = sIntentStarter;
        if (intentStarter == null) {
            intentStarter = new BcSmartspaceDataPlugin.IntentStarter() {
                public final void startIntent(View view, Intent intent, boolean z) {
                    try {
                        view.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException | NullPointerException | SecurityException e) {
                        Log.e(str, "Cannot invoke smartspace intent", e);
                    }
                }

                public final void startPendingIntent(PendingIntent pendingIntent, boolean z) {
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        Log.e(str, "Cannot invoke canceled smartspace intent", e);
                    }
                }
            };
        }
        view.setOnClickListener(new BcSmartSpaceUtil$$ExternalSyntheticLambda0(bcSmartspaceCardLoggingInfo, z2, intentStarter, smartspaceAction, z, cardPagerAdapter$$ExternalSyntheticLambda0, str, smartspaceTarget));
    }

    public static Drawable getIconDrawable(Icon icon, Context context) {
        Drawable drawable;
        if (icon == null) {
            return null;
        }
        if (icon.getType() == 1 || icon.getType() == 5) {
            drawable = new BitmapDrawable(context.getResources(), icon.getBitmap());
        } else {
            drawable = icon.loadDrawable(context);
        }
        if (drawable != null) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(C1777R.dimen.enhanced_smartspace_icon_size);
            drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        }
        return drawable;
    }

    public static int getLoggingDisplaySurface(String str, float f) {
        Objects.requireNonNull(str);
        if (str.equals("com.google.android.apps.nexuslauncher")) {
            return 1;
        }
        if (!str.equals(ThemeOverlayApplier.SYSUI_PACKAGE)) {
            return 0;
        }
        if (f == 1.0f) {
            return 3;
        }
        if (f == 0.0f) {
            return 2;
        }
        return -1;
    }
}
