package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.ReduceBrightColorsTile */
public final class ReduceBrightColorsTile extends QSTileImpl<QSTile.BooleanState> implements ReduceBrightColorsController.Listener {
    public final QSTile.Icon mIcon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_reduce_bright_colors);
    public final boolean mIsAvailable;
    public final ReduceBrightColorsController mReduceBrightColorsController;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ReduceBrightColorsTile(boolean z, ReduceBrightColorsController reduceBrightColorsController, QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mReduceBrightColorsController = reduceBrightColorsController;
        reduceBrightColorsController.observe((Lifecycle) this.mLifecycle, this);
        this.mIsAvailable = z;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    public final void onActivated(boolean z) {
        refreshState((Object) null);
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.REDUCE_BRIGHT_COLORS_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(17041351);
    }

    public final void handleClick(View view) {
        ReduceBrightColorsController reduceBrightColorsController = this.mReduceBrightColorsController;
        Objects.requireNonNull(reduceBrightColorsController);
        reduceBrightColorsController.mManager.setReduceBrightColorsActivated(!((QSTile.BooleanState) this.mState).value);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        ReduceBrightColorsController reduceBrightColorsController = this.mReduceBrightColorsController;
        Objects.requireNonNull(reduceBrightColorsController);
        boolean isReduceBrightColorsActivated = reduceBrightColorsController.mManager.isReduceBrightColorsActivated();
        booleanState.value = isReduceBrightColorsActivated;
        if (isReduceBrightColorsActivated) {
            i = 2;
        } else {
            i = 1;
        }
        booleanState.state = i;
        booleanState.label = this.mContext.getString(17041351);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
        booleanState.icon = this.mIcon;
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void handleDestroy() {
        super.handleDestroy();
    }

    public final boolean isAvailable() {
        return this.mIsAvailable;
    }
}
