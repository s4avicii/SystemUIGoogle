package com.google.android.systemui.p016qs.tiles;

import android.content.Intent;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.util.Slog;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.google.android.systemui.qs.tiles.OverlayToggleTile */
/* compiled from: OverlayToggleTile.kt */
public final class OverlayToggleTile extends QSTileImpl<QSTile.BooleanState> {

    /* renamed from: om */
    public final OverlayManager f145om;
    public CharSequence overlayLabel;
    public String overlayPackage;

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return -1;
    }

    public final CharSequence getTileLabel() {
        return "Overlay";
    }

    public final void handleLongClick(View view) {
    }

    public final void handleClick(View view) {
        QSTile.BooleanState booleanState;
        boolean z;
        String str = this.overlayPackage;
        if (str != null && (booleanState = (QSTile.BooleanState) this.mState) != null) {
            if (booleanState.state != 2) {
                z = true;
            } else {
                z = false;
            }
            String str2 = this.TAG;
            Slog.v(str2, "Setting enable state of " + str + " to " + z);
            this.f145om.setEnabled(str, z, UserHandle.CURRENT);
            refreshState("Restarting...");
            Thread.sleep(250);
            Slog.v(this.TAG, "Restarting System UI to react to overlay changes");
            Process.killProcess(Process.myPid());
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        Object obj2;
        String str;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        PackageManager packageManager = this.mContext.getPackageManager();
        booleanState.state = 0;
        booleanState.label = "No overlay";
        List overlayInfosForTarget = this.f145om.getOverlayInfosForTarget(ThemeOverlayApplier.SYSUI_PACKAGE, UserHandle.CURRENT);
        if (overlayInfosForTarget != null) {
            Iterator it = overlayInfosForTarget.iterator();
            do {
                i = 2;
                obj2 = null;
                if (!it.hasNext()) {
                    break;
                }
                obj2 = it.next();
            } while (!((OverlayInfo) obj2).packageName.startsWith("com.google."));
            OverlayInfo overlayInfo = (OverlayInfo) obj2;
            if (overlayInfo != null) {
                if (!Intrinsics.areEqual(this.overlayPackage, overlayInfo.packageName)) {
                    String str2 = overlayInfo.packageName;
                    this.overlayPackage = str2;
                    this.overlayLabel = packageManager.getPackageInfo(str2, 0).applicationInfo.loadLabel(packageManager);
                }
                booleanState.value = overlayInfo.isEnabled();
                if (!overlayInfo.isEnabled()) {
                    i = 1;
                }
                booleanState.state = i;
                booleanState.icon = QSTileImpl.ResourceIcon.get(17303574);
                booleanState.label = this.overlayLabel;
                if (obj != null) {
                    str = String.valueOf(obj);
                } else if (overlayInfo.isEnabled()) {
                    str = "Enabled";
                } else {
                    str = "Disabled";
                }
                booleanState.secondaryLabel = str;
            }
        }
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public OverlayToggleTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, OverlayManager overlayManager) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.f145om = overlayManager;
    }

    public final boolean isAvailable() {
        return Build.IS_DEBUGGABLE;
    }
}
