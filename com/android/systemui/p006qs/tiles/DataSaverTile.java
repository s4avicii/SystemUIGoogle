package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.ShellExecutor$$ExternalSyntheticLambda0;
import com.android.systemui.Prefs;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.DataSaverController;

/* renamed from: com.android.systemui.qs.tiles.DataSaverTile */
public final class DataSaverTile extends QSTileImpl<QSTile.BooleanState> implements DataSaverController.Listener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DataSaverController mDataSaverController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;

    public final int getMetricsCategory() {
        return 284;
    }

    public final Intent getLongClickIntent() {
        return new Intent("android.settings.DATA_SAVER_SETTINGS");
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.data_saver);
    }

    public final void handleClick(View view) {
        if (((QSTile.BooleanState) this.mState).value || Prefs.getBoolean(this.mContext, "QsDataSaverDialogShown")) {
            toggleDataSaver();
        } else {
            this.mUiHandler.post(new ShellExecutor$$ExternalSyntheticLambda0(this, view, 2));
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        int i;
        int i2;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj instanceof Boolean) {
            z = ((Boolean) obj).booleanValue();
        } else {
            z = this.mDataSaverController.isDataSaverEnabled();
        }
        booleanState.value = z;
        if (z) {
            i = 2;
        } else {
            i = 1;
        }
        booleanState.state = i;
        String string = this.mContext.getString(C1777R.string.data_saver);
        booleanState.label = string;
        booleanState.contentDescription = string;
        if (booleanState.value) {
            i2 = C1777R.C1778drawable.ic_data_saver;
        } else {
            i2 = C1777R.C1778drawable.ic_data_saver_off;
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(i2);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void toggleDataSaver() {
        ((QSTile.BooleanState) this.mState).value = !this.mDataSaverController.isDataSaverEnabled();
        this.mDataSaverController.setDataSaverEnabled(((QSTile.BooleanState) this.mState).value);
        refreshState(Boolean.valueOf(((QSTile.BooleanState) this.mState).value));
    }

    public DataSaverTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, DataSaverController dataSaverController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDataSaverController = dataSaverController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        dataSaverController.observe((Lifecycle) this.mLifecycle, this);
    }

    public final void onDataSaverChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
    }
}
