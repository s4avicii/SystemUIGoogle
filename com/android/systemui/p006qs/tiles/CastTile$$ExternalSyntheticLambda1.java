package com.android.systemui.p006qs.tiles;

import android.app.Dialog;
import android.view.View;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda8;
import com.android.p012wm.shell.common.split.SplitLayout;
import com.android.p012wm.shell.legacysplitscreen.DividerView;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthCredentialView;
import com.android.systemui.communal.CommunalSourceMonitor;
import com.android.systemui.communal.CommunalSourceMonitor$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tiles.CastTile;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.condition.Monitor;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda1(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CastTile castTile = (CastTile) this.f$0;
                Objects.requireNonNull(castTile);
                CastTile.DialogHolder dialogHolder = new CastTile.DialogHolder();
                Dialog createDialog = MediaRouteDialogPresenter.createDialog(castTile.mContext, 4, new CastTile$$ExternalSyntheticLambda0(castTile, dialogHolder));
                dialogHolder.mDialog = createDialog;
                SystemUIDialog.setShowForAllUsers(createDialog);
                SystemUIDialog.registerDismissListener(createDialog);
                SystemUIDialog.setWindowOnTop(createDialog, castTile.mKeyguard.isShowing());
                castTile.mUiHandler.post(new TaskView$$ExternalSyntheticLambda8(castTile, (View) this.f$1, createDialog, 1));
                return;
            case 1:
                AuthContainerView authContainerView = (AuthContainerView) this.f$0;
                int i = AuthContainerView.$r8$clinit;
                Objects.requireNonNull(authContainerView);
                authContainerView.mPanelView.animate().translationY(authContainerView.mTranslationY).setDuration(350).setInterpolator(authContainerView.mLinearOutSlowIn).withLayer().withEndAction((Runnable) this.f$1).start();
                authContainerView.mBiometricScrollView.animate().translationY(authContainerView.mTranslationY).setDuration(350).setInterpolator(authContainerView.mLinearOutSlowIn).withLayer().start();
                AuthCredentialView authCredentialView = authContainerView.mCredentialView;
                if (authCredentialView != null && authCredentialView.isAttachedToWindow()) {
                    authContainerView.mCredentialView.animate().translationY(authContainerView.mTranslationY).setDuration(350).setInterpolator(authContainerView.mLinearOutSlowIn).withLayer().start();
                }
                authContainerView.animate().alpha(0.0f).setDuration(350).setInterpolator(authContainerView.mLinearOutSlowIn).withLayer().start();
                return;
            case 2:
                CommunalSourceMonitor communalSourceMonitor = (CommunalSourceMonitor) this.f$0;
                CommunalSourceMonitor.Callback callback = (CommunalSourceMonitor.Callback) this.f$1;
                boolean z = CommunalSourceMonitor.DEBUG;
                Objects.requireNonNull(communalSourceMonitor);
                communalSourceMonitor.mCallbacks.add(new WeakReference(callback));
                if (communalSourceMonitor.mAllCommunalConditionsMet && communalSourceMonitor.mCurrentSource != null) {
                    callback.onSourceAvailable(new WeakReference(communalSourceMonitor.mCurrentSource));
                }
                if (!communalSourceMonitor.mListeningForConditions) {
                    Monitor monitor = communalSourceMonitor.mConditionsMonitor;
                    CommunalSourceMonitor$$ExternalSyntheticLambda0 communalSourceMonitor$$ExternalSyntheticLambda0 = communalSourceMonitor.mConditionsCallback;
                    Objects.requireNonNull(monitor);
                    monitor.mExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(monitor, communalSourceMonitor$$ExternalSyntheticLambda0, 3));
                    communalSourceMonitor.mListeningForConditions = true;
                    return;
                }
                return;
            case 3:
                SplitLayout splitLayout = (SplitLayout) this.f$0;
                Objects.requireNonNull(splitLayout);
                int i2 = ((DividerSnapAlgorithm.SnapTarget) this.f$1).position;
                splitLayout.mDividePosition = i2;
                splitLayout.updateBounds(i2);
                splitLayout.mSplitLayoutHandler.onLayoutSizeChanged(splitLayout);
                return;
            case 4:
                int i3 = DividerView.C18663.$r8$clinit;
                ((Consumer) this.f$0).accept((Boolean) this.f$1);
                return;
            default:
                StartingSurfaceDrawer startingSurfaceDrawer = (StartingSurfaceDrawer) this.f$0;
                Objects.requireNonNull(startingSurfaceDrawer);
                startingSurfaceDrawer.removeWindowInner(((StartingSurfaceDrawer.StartingWindowRecord) this.f$1).mDecorView, true);
                return;
        }
    }
}
