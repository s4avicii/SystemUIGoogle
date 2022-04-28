package com.android.systemui.p006qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.QRCodeScannerTile */
public final class QRCodeScannerTile extends QSTileImpl<QSTile.State> {
    public final C10491 mCallback;
    public final String mLabel = this.mContext.getString(C1777R.string.qr_code_scanner_title);
    public final QRCodeScannerController mQRCodeScannerController;

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    public final void handleClick(View view) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        QRCodeScannerController qRCodeScannerController = this.mQRCodeScannerController;
        Objects.requireNonNull(qRCodeScannerController);
        Intent intent = qRCodeScannerController.mIntent;
        if (intent == null) {
            Log.e("QRCodeScanner", "Expected a non-null intent");
            return;
        }
        if (view == null) {
            ghostedViewLaunchAnimatorController = null;
        } else {
            ghostedViewLaunchAnimatorController = ActivityLaunchAnimator.Controller.fromView(view, 32);
        }
        this.mActivityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) ghostedViewLaunchAnimatorController, true);
    }

    public final void handleInitialize() {
        this.mQRCodeScannerController.registerQRCodeScannerChangeObservers(0);
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        String string = this.mContext.getString(C1777R.string.qr_code_scanner_title);
        state.label = string;
        state.contentDescription = string;
        state.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qr_code_scanner);
        QRCodeScannerController qRCodeScannerController = this.mQRCodeScannerController;
        Objects.requireNonNull(qRCodeScannerController);
        int i = 0;
        if (qRCodeScannerController.mIntent != null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i = 2;
        }
        state.state = i;
        state.secondaryLabel = this.mContext.getString(C1777R.string.qr_code_scanner_description);
    }

    public final boolean isAvailable() {
        return this.mQRCodeScannerController.isCameraAvailable();
    }

    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }

    public QRCodeScannerTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QRCodeScannerController qRCodeScannerController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        C10491 r1 = new QRCodeScannerController.Callback() {
            public final void onQRCodeScannerActivityChanged() {
                QRCodeScannerTile qRCodeScannerTile = QRCodeScannerTile.this;
                Objects.requireNonNull(qRCodeScannerTile);
                qRCodeScannerTile.refreshState((Object) null);
            }
        };
        this.mCallback = r1;
        this.mQRCodeScannerController = qRCodeScannerController;
        qRCodeScannerController.observe((Lifecycle) this.mLifecycle, r1);
    }

    public final void handleDestroy() {
        super.handleDestroy();
        this.mQRCodeScannerController.unregisterQRCodeScannerChangeObservers(0);
    }

    public final CharSequence getTileLabel() {
        return this.mLabel;
    }
}
