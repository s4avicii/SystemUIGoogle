package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.Prefs;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessibilityFloatingMenu$$ExternalSyntheticLambda0 implements AccessibilityFloatingMenuView.OnDragEndListener {
    public final /* synthetic */ AccessibilityFloatingMenu f$0;

    public /* synthetic */ AccessibilityFloatingMenu$$ExternalSyntheticLambda0(AccessibilityFloatingMenu accessibilityFloatingMenu) {
        this.f$0 = accessibilityFloatingMenu;
    }

    public final void onDragEnd(Position position) {
        AccessibilityFloatingMenu accessibilityFloatingMenu = this.f$0;
        Objects.requireNonNull(accessibilityFloatingMenu);
        Objects.requireNonNull(position);
        float f = position.mPercentageX;
        float f2 = position.mPercentageY;
        int i = accessibilityFloatingMenu.mContext.getResources().getConfiguration().orientation;
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(393);
        newBuilder.writeFloat(f);
        newBuilder.writeFloat(f2);
        newBuilder.writeInt(i);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        Context context = accessibilityFloatingMenu.mContext;
        boolean z = false;
        context.getSharedPreferences(context.getPackageName(), 0).edit().putString("AccessibilityFloatingMenuPosition", position.toString()).apply();
        Context context2 = accessibilityFloatingMenu.mContext;
        if (!context2.getSharedPreferences(context2.getPackageName(), 0).getBoolean("HasSeenAccessibilityFloatingMenuDockTooltip", false)) {
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
            Objects.requireNonNull(accessibilityFloatingMenuView);
            if (accessibilityFloatingMenuView.mShapeType == 0) {
                z = true;
            }
            if (z) {
                accessibilityFloatingMenu.mDockTooltipView.show();
            }
            Prefs.putBoolean(context2, "HasSeenAccessibilityFloatingMenuDockTooltip", true);
        }
    }
}
