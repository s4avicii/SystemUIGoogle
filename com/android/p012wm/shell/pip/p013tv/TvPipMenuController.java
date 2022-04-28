package com.android.p012wm.shell.pip.p013tv;

import android.app.ActivityManager;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuController */
public final class TvPipMenuController implements PipMenuController, TvPipMenuView.Listener {
    public final ArrayList mAppActions = new ArrayList();
    public SyncRtSurfaceTransactionApplier mApplier;
    public final Context mContext;
    public Delegate mDelegate;
    public boolean mInMoveMode;
    public SurfaceControl mLeash;
    public final Handler mMainHandler;
    public final ArrayList mMediaActions = new ArrayList();
    public Matrix mMoveTransform = new Matrix();
    public TvPipMenuView mPipMenuView;
    public final SystemWindows mSystemWindows;
    public RectF mTmpDestinationRectF = new RectF();
    public RectF mTmpSourceRectF = new RectF();
    public final float[] mTmpValues = new float[9];
    public final TvPipBoundsState mTvPipBoundsState;
    public final LockIconViewController$$ExternalSyntheticLambda2 mUpdateEmbeddedMatrix = new LockIconViewController$$ExternalSyntheticLambda2(this, 9);

    /* renamed from: com.android.wm.shell.pip.tv.TvPipMenuController$Delegate */
    public interface Delegate {
    }

    public final void attach(SurfaceControl surfaceControl) {
        if (this.mDelegate != null) {
            this.mLeash = surfaceControl;
            TvPipMenuView tvPipMenuView = this.mPipMenuView;
            if (!(tvPipMenuView == null || tvPipMenuView == null)) {
                this.mApplier = null;
                SystemWindows systemWindows = this.mSystemWindows;
                Objects.requireNonNull(systemWindows);
                systemWindows.mViewRoots.remove(tvPipMenuView).release();
                this.mPipMenuView = null;
            }
            TvPipMenuView tvPipMenuView2 = new TvPipMenuView(this.mContext, (AttributeSet) null);
            this.mPipMenuView = tvPipMenuView2;
            tvPipMenuView2.mListener = this;
            this.mSystemWindows.addView(tvPipMenuView2, PipMenuController.getPipMenuLayoutParams(0, 0), 0, 1);
            TvPipMenuView tvPipMenuView3 = this.mPipMenuView;
            IBinder focusGrantToken = this.mSystemWindows.getFocusGrantToken(tvPipMenuView3);
            Objects.requireNonNull(tvPipMenuView3);
            tvPipMenuView3.mFocusGrantToken = focusGrantToken;
            return;
        }
        throw new IllegalStateException("Delegate is not set.");
    }

    public final Rect getMenuBounds(Rect rect) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.pip_menu_outer_space);
        Rect rect2 = new Rect(rect);
        int i = -dimensionPixelSize;
        rect2.inset(i, i);
        return rect2;
    }

    public final boolean isMenuVisible() {
        boolean z;
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        if (tvPipMenuView != null) {
            Objects.requireNonNull(tvPipMenuView);
            if (tvPipMenuView.mMenuFrameView.getAlpha() == 0.0f && tvPipMenuView.mActionButtonsContainer.getAlpha() == 0.0f && tvPipMenuView.mArrowUp.getAlpha() == 0.0f && tvPipMenuView.mArrowRight.getAlpha() == 0.0f && tvPipMenuView.mArrowDown.getAlpha() == 0.0f && tvPipMenuView.mArrowLeft.getAlpha() == 0.0f) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean maybeCreateSyncApplier() {
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        if (tvPipMenuView == null || tvPipMenuView.getViewRootImpl() == null) {
            Log.v("TvPipMenuController", "Not going to move PiP, either menu or its parent is not created.");
            return false;
        } else if (this.mApplier != null) {
            return true;
        } else {
            this.mApplier = new SyncRtSurfaceTransactionApplier(this.mPipMenuView);
            return true;
        }
    }

    public final void maybeUpdateMenuViewActions() {
        if (this.mPipMenuView != null) {
            if (!this.mAppActions.isEmpty()) {
                this.mPipMenuView.setAdditionalActions(this.mAppActions, this.mMainHandler);
            } else {
                this.mPipMenuView.setAdditionalActions(this.mMediaActions, this.mMainHandler);
            }
        }
    }

    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Log.d("TvPipMenuController", "onFocusTaskChanged");
    }

    public final void updateAdditionalActionsList(List<RemoteAction> list, List<RemoteAction> list2) {
        int i;
        if (list2 != null) {
            i = list2.size();
        } else {
            i = 0;
        }
        if (i != 0 || !list.isEmpty()) {
            list.clear();
            if (i > 0) {
                list.addAll(list2);
            }
            maybeUpdateMenuViewActions();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateExpansionState() {
        /*
            r4 = this;
            com.android.wm.shell.pip.tv.TvPipMenuView r0 = r4.mPipMenuView
            com.android.wm.shell.pip.tv.TvPipBoundsState r1 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mIsTvExpandedPipEnabled
            r2 = 0
            if (r1 == 0) goto L_0x001a
            com.android.wm.shell.pip.tv.TvPipBoundsState r1 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r1)
            float r1 = r1.mTvExpandedAspectRatio
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x001a
            r1 = 1
            goto L_0x001b
        L_0x001a:
            r1 = r2
        L_0x001b:
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.pip.tv.TvPipMenuActionButton r0 = r0.mExpandButton
            if (r1 == 0) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r2 = 8
        L_0x0025:
            r0.setVisibility(r2)
            com.android.wm.shell.pip.tv.TvPipMenuView r0 = r4.mPipMenuView
            com.android.wm.shell.pip.tv.TvPipBoundsState r4 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.mIsTvPipExpanded
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.pip.tv.TvPipMenuActionButton r1 = r0.mExpandButton
            if (r4 == 0) goto L_0x003c
            r2 = 2131232553(0x7f080729, float:1.8081219E38)
            goto L_0x003f
        L_0x003c:
            r2 = 2131232554(0x7f08072a, float:1.808122E38)
        L_0x003f:
            if (r2 == 0) goto L_0x0047
            android.widget.ImageView r1 = r1.mIconImageView
            r1.setImageResource(r2)
            goto L_0x004a
        L_0x0047:
            java.util.Objects.requireNonNull(r1)
        L_0x004a:
            com.android.wm.shell.pip.tv.TvPipMenuActionButton r0 = r0.mExpandButton
            if (r4 == 0) goto L_0x0052
            r4 = 2131952974(0x7f13054e, float:1.9542406E38)
            goto L_0x0055
        L_0x0052:
            r4 = 2131952975(0x7f13054f, float:1.9542408E38)
        L_0x0055:
            java.util.Objects.requireNonNull(r0)
            android.content.Context r1 = r0.getContext()
            java.lang.String r4 = r1.getString(r4)
            android.view.View r0 = r0.mButtonView
            r0.setContentDescription(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.p013tv.TvPipMenuController.updateExpansionState():void");
    }

    public TvPipMenuController(Context context, TvPipBoundsState tvPipBoundsState, SystemWindows systemWindows, PipMediaController pipMediaController, Handler handler) {
        this.mContext = context;
        this.mTvPipBoundsState = tvPipBoundsState;
        this.mSystemWindows = systemWindows;
        this.mMainHandler = handler;
        context.registerReceiverForAllUsers(new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                TvPipMenuController.this.hideMenu();
            }
        }, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), (String) null, handler, 2);
        TvPipMenuController$$ExternalSyntheticLambda0 tvPipMenuController$$ExternalSyntheticLambda0 = new TvPipMenuController$$ExternalSyntheticLambda0(this);
        Objects.requireNonNull(pipMediaController);
        if (!pipMediaController.mActionListeners.contains(tvPipMenuController$$ExternalSyntheticLambda0)) {
            pipMediaController.mActionListeners.add(tvPipMenuController$$ExternalSyntheticLambda0);
            tvPipMenuController$$ExternalSyntheticLambda0.onMediaActionsChanged(pipMediaController.getMediaActions());
        }
    }

    public final void detach() {
        hideMenu();
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        if (tvPipMenuView != null) {
            this.mApplier = null;
            SystemWindows systemWindows = this.mSystemWindows;
            Objects.requireNonNull(systemWindows);
            systemWindows.mViewRoots.remove(tvPipMenuView).release();
            this.mPipMenuView = null;
        }
        this.mLeash = null;
    }

    public final void hideMenu() {
        if (isMenuVisible()) {
            TvPipMenuView tvPipMenuView = this.mPipMenuView;
            boolean z = this.mInMoveMode;
            Objects.requireNonNull(tvPipMenuView);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mActionButtonsContainer);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mMenuFrameView);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mArrowUp);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mArrowRight);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mArrowDown);
            TvPipMenuView.animateAlphaTo(0.0f, tvPipMenuView.mArrowLeft);
            if (!z) {
                try {
                    WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, tvPipMenuView.mFocusGrantToken, false);
                } catch (Exception e) {
                    Log.e("TvPipMenuView", "Unable to update focus", e);
                }
            }
            if (!this.mInMoveMode) {
                TvPipController tvPipController = (TvPipController) this.mDelegate;
                Objects.requireNonNull(tvPipController);
                tvPipController.setState(1);
            }
        }
    }

    public final void movePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
        if (!rect.isEmpty() && maybeCreateSyncApplier()) {
            Rect menuBounds = getMenuBounds(rect);
            Rect rect2 = new Rect();
            if (surfaceControl == null || transaction == null) {
                rect2.set(0, 0, menuBounds.width(), menuBounds.height());
            } else {
                this.mPipMenuView.getBoundsOnScreen(rect2);
            }
            this.mTmpSourceRectF.set(rect2);
            this.mTmpDestinationRectF.set(menuBounds);
            this.mMoveTransform.setRectToRect(this.mTmpSourceRectF, this.mTmpDestinationRectF, Matrix.ScaleToFit.FILL);
            SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(this.mSystemWindows.getViewSurface(this.mPipMenuView)).withMatrix(this.mMoveTransform).build();
            if (surfaceControl == null || transaction == null) {
                this.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
            } else {
                SyncRtSurfaceTransactionApplier.SurfaceParams build2 = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withMergeTransaction(transaction).build();
                this.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build, build2});
            }
            if (this.mPipMenuView.getViewRootImpl() != null) {
                this.mPipMenuView.getHandler().removeCallbacks(this.mUpdateEmbeddedMatrix);
                this.mPipMenuView.getHandler().post(this.mUpdateEmbeddedMatrix);
            }
            updateMenuBounds(rect);
        }
    }

    public final void resizePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
        if (!rect.isEmpty() && maybeCreateSyncApplier()) {
            SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(this.mSystemWindows.getViewSurface(this.mPipMenuView)).withWindowCrop(getMenuBounds(rect)).build();
            if (surfaceControl != null) {
                SyncRtSurfaceTransactionApplier.SurfaceParams build2 = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withMergeTransaction(transaction).build();
                this.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build, build2});
                return;
            }
            this.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
        }
    }

    public final void updateMenuBounds(Rect rect) {
        int i;
        int i2;
        Rect menuBounds = getMenuBounds(rect);
        this.mSystemWindows.updateViewLayout(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(menuBounds.width(), menuBounds.height()));
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        if (tvPipMenuView != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("update menu layout: ");
            m.append(rect.toShortString());
            Log.d("TvPipMenuView", m.toString());
            Rect rect2 = tvPipMenuView.mCurrentBounds;
            int i3 = 1;
            int i4 = 0;
            if (rect2 == null || rect2.height() <= tvPipMenuView.mCurrentBounds.width()) {
                i = 0;
            } else {
                i = 1;
            }
            if (rect.height() <= rect.width()) {
                i3 = 0;
            }
            tvPipMenuView.mCurrentBounds = rect;
            if (i != i3) {
                if (i3 != 0) {
                    tvPipMenuView.mHorizontalScrollView.removeView(tvPipMenuView.mActionButtonsContainer);
                    tvPipMenuView.mScrollView.addView(tvPipMenuView.mActionButtonsContainer);
                } else {
                    tvPipMenuView.mScrollView.removeView(tvPipMenuView.mActionButtonsContainer);
                    tvPipMenuView.mHorizontalScrollView.addView(tvPipMenuView.mActionButtonsContainer);
                }
                tvPipMenuView.mActionButtonsContainer.setOrientation(i3);
                ViewGroup viewGroup = tvPipMenuView.mScrollView;
                if (i3 != 0) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                viewGroup.setVisibility(i2);
                ViewGroup viewGroup2 = tvPipMenuView.mHorizontalScrollView;
                if (i3 != 0) {
                    i4 = 8;
                }
                viewGroup2.setVisibility(i4);
            }
        }
    }
}
