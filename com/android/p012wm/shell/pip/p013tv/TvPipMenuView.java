package com.android.p012wm.shell.pip.p013tv;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuView */
public class TvPipMenuView extends FrameLayout implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LinearLayout mActionButtonsContainer;
    public final ArrayList mAdditionalButtons = new ArrayList();
    public final ImageView mArrowDown;
    public final ImageView mArrowLeft;
    public final ImageView mArrowRight;
    public final ImageView mArrowUp;
    public Rect mCurrentBounds;
    public final TvPipMenuActionButton mExpandButton;
    public IBinder mFocusGrantToken = null;
    public final ViewGroup mHorizontalScrollView;
    public Listener mListener;
    public final View mMenuFrameView;
    public final ViewGroup mScrollView;

    /* renamed from: com.android.wm.shell.pip.tv.TvPipMenuView$Listener */
    public interface Listener {
    }

    public TvPipMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        View.inflate(context, C1777R.layout.tv_pip_menu, this);
        LinearLayout linearLayout = (LinearLayout) findViewById(C1777R.C1779id.tv_pip_menu_action_buttons);
        this.mActionButtonsContainer = linearLayout;
        linearLayout.findViewById(C1777R.C1779id.tv_pip_menu_fullscreen_button).setOnClickListener(this);
        linearLayout.findViewById(C1777R.C1779id.tv_pip_menu_close_button).setOnClickListener(this);
        linearLayout.findViewById(C1777R.C1779id.tv_pip_menu_move_button).setOnClickListener(this);
        TvPipMenuActionButton tvPipMenuActionButton = (TvPipMenuActionButton) findViewById(C1777R.C1779id.tv_pip_menu_expand_button);
        this.mExpandButton = tvPipMenuActionButton;
        tvPipMenuActionButton.setOnClickListener(this);
        this.mScrollView = (ViewGroup) findViewById(C1777R.C1779id.tv_pip_menu_scroll);
        this.mHorizontalScrollView = (ViewGroup) findViewById(C1777R.C1779id.tv_pip_menu_horizontal_scroll);
        this.mMenuFrameView = findViewById(C1777R.C1779id.tv_pip_menu_frame);
        this.mArrowUp = (ImageView) findViewById(C1777R.C1779id.tv_pip_menu_arrow_up);
        this.mArrowRight = (ImageView) findViewById(C1777R.C1779id.tv_pip_menu_arrow_right);
        this.mArrowDown = (ImageView) findViewById(C1777R.C1779id.tv_pip_menu_arrow_down);
        this.mArrowLeft = (ImageView) findViewById(C1777R.C1779id.tv_pip_menu_arrow_left);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0052, code lost:
        if (r2 == 20) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        if (r2 == 21) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0096, code lost:
        r7 = r7 | r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r9) {
        /*
            r8 = this;
            com.android.wm.shell.pip.tv.TvPipMenuView$Listener r0 = r8.mListener
            if (r0 == 0) goto L_0x013d
            int r0 = r9.getAction()
            r1 = 1
            if (r0 != r1) goto L_0x013d
            int r0 = r9.getKeyCode()
            r2 = 4
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 0
            if (r0 == r2) goto L_0x0106
            r2 = 66
            if (r0 == r2) goto L_0x00c8
            switch(r0) {
                case 19: goto L_0x001f;
                case 20: goto L_0x001f;
                case 21: goto L_0x001f;
                case 22: goto L_0x001f;
                case 23: goto L_0x00c8;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x013d
        L_0x001f:
            com.android.wm.shell.pip.tv.TvPipMenuView$Listener r0 = r8.mListener
            int r2 = r9.getKeyCode()
            com.android.wm.shell.pip.tv.TvPipMenuController r0 = (com.android.p012wm.shell.pip.p013tv.TvPipMenuController) r0
            java.util.Objects.requireNonNull(r0)
            boolean r3 = r0.mInMoveMode
            if (r3 == 0) goto L_0x00bb
            com.android.wm.shell.pip.tv.TvPipMenuController$Delegate r3 = r0.mDelegate
            com.android.wm.shell.pip.tv.TvPipController r3 = (com.android.p012wm.shell.pip.p013tv.TvPipController) r3
            java.util.Objects.requireNonNull(r3)
            com.android.wm.shell.pip.tv.TvPipBoundsAlgorithm r4 = r3.mTvPipBoundsAlgorithm
            java.util.Objects.requireNonNull(r4)
            com.android.wm.shell.pip.tv.TvPipBoundsState r6 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mIsTvPipExpanded
            if (r6 == 0) goto L_0x0060
            com.android.wm.shell.pip.tv.TvPipBoundsState r6 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.mTvFixedPipOrientation
            if (r6 != r1) goto L_0x0054
            r7 = 19
            if (r2 == r7) goto L_0x00a2
            r7 = 20
            if (r2 == r7) goto L_0x00a2
        L_0x0054:
            r7 = 2
            if (r6 != r7) goto L_0x0060
            r6 = 22
            if (r2 == r6) goto L_0x00a2
            r6 = 21
            if (r2 != r6) goto L_0x0060
            goto L_0x00a2
        L_0x0060:
            com.android.wm.shell.pip.tv.TvPipBoundsState r6 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.mTvPipGravity
            switch(r2) {
                case 19: goto L_0x0073;
                case 20: goto L_0x0070;
                case 21: goto L_0x006e;
                case 22: goto L_0x006c;
                default: goto L_0x006a;
            }
        L_0x006a:
            r7 = r6
            goto L_0x0075
        L_0x006c:
            r7 = 5
            goto L_0x0075
        L_0x006e:
            r7 = 3
            goto L_0x0075
        L_0x0070:
            r7 = 80
            goto L_0x0075
        L_0x0073:
            r7 = 48
        L_0x0075:
            switch(r2) {
                case 19: goto L_0x0088;
                case 20: goto L_0x0088;
                case 21: goto L_0x0079;
                case 22: goto L_0x0079;
                default: goto L_0x0078;
            }
        L_0x0078:
            goto L_0x0097
        L_0x0079:
            com.android.wm.shell.pip.tv.TvPipBoundsState r2 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsTvPipExpanded
            if (r2 == 0) goto L_0x0085
            r7 = r7 | 16
            goto L_0x0097
        L_0x0085:
            r2 = r6 & 112(0x70, float:1.57E-43)
            goto L_0x0096
        L_0x0088:
            com.android.wm.shell.pip.tv.TvPipBoundsState r2 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsTvPipExpanded
            if (r2 == 0) goto L_0x0094
            r7 = r7 | 1
            goto L_0x0097
        L_0x0094:
            r2 = r6 & 7
        L_0x0096:
            r7 = r7 | r2
        L_0x0097:
            if (r7 == r6) goto L_0x00a2
            com.android.wm.shell.pip.tv.TvPipBoundsState r2 = r4.mTvPipBoundsState
            java.util.Objects.requireNonNull(r2)
            r2.mTvPipGravity = r7
            r2 = r1
            goto L_0x00a3
        L_0x00a2:
            r2 = r5
        L_0x00a3:
            if (r2 == 0) goto L_0x00bb
            com.android.wm.shell.pip.tv.TvPipMenuController r2 = r3.mTvPipMenuController
            com.android.wm.shell.pip.tv.TvPipBoundsState r4 = r3.mTvPipBoundsState
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mTvPipGravity
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.pip.tv.TvPipMenuView r2 = r2.mPipMenuView
            r2.showMovementHints(r4)
            r3.mPreviousGravity = r5
            r3.movePinnedStack()
        L_0x00bb:
            boolean r0 = r0.mInMoveMode
            if (r0 != 0) goto L_0x00c7
            boolean r8 = super.dispatchKeyEvent(r9)
            if (r8 == 0) goto L_0x00c6
            goto L_0x00c7
        L_0x00c6:
            r1 = r5
        L_0x00c7:
            return r1
        L_0x00c8:
            com.android.wm.shell.pip.tv.TvPipMenuView$Listener r0 = r8.mListener
            com.android.wm.shell.pip.tv.TvPipMenuController r0 = (com.android.p012wm.shell.pip.p013tv.TvPipMenuController) r0
            java.util.Objects.requireNonNull(r0)
            boolean r2 = r0.mInMoveMode
            if (r2 == 0) goto L_0x00fa
            r0.mInMoveMode = r5
            com.android.wm.shell.pip.tv.TvPipMenuView r2 = r0.mPipMenuView
            java.util.Objects.requireNonNull(r2)
            android.widget.LinearLayout r2 = r2.mActionButtonsContainer
            animateAlphaTo(r4, r2)
            com.android.wm.shell.pip.tv.TvPipMenuView r0 = r0.mPipMenuView
            java.util.Objects.requireNonNull(r0)
            android.widget.ImageView r2 = r0.mArrowUp
            animateAlphaTo(r3, r2)
            android.widget.ImageView r2 = r0.mArrowRight
            animateAlphaTo(r3, r2)
            android.widget.ImageView r2 = r0.mArrowDown
            animateAlphaTo(r3, r2)
            android.widget.ImageView r0 = r0.mArrowLeft
            animateAlphaTo(r3, r0)
            r0 = r1
            goto L_0x00fb
        L_0x00fa:
            r0 = r5
        L_0x00fb:
            if (r0 != 0) goto L_0x0105
            boolean r8 = super.dispatchKeyEvent(r9)
            if (r8 == 0) goto L_0x0104
            goto L_0x0105
        L_0x0104:
            r1 = r5
        L_0x0105:
            return r1
        L_0x0106:
            com.android.wm.shell.pip.tv.TvPipMenuView$Listener r8 = r8.mListener
            com.android.wm.shell.pip.tv.TvPipMenuController r8 = (com.android.p012wm.shell.pip.p013tv.TvPipMenuController) r8
            java.util.Objects.requireNonNull(r8)
            boolean r9 = r8.mInMoveMode
            if (r9 == 0) goto L_0x0137
            r8.mInMoveMode = r5
            com.android.wm.shell.pip.tv.TvPipMenuView r9 = r8.mPipMenuView
            java.util.Objects.requireNonNull(r9)
            android.widget.LinearLayout r9 = r9.mActionButtonsContainer
            animateAlphaTo(r4, r9)
            com.android.wm.shell.pip.tv.TvPipMenuView r9 = r8.mPipMenuView
            java.util.Objects.requireNonNull(r9)
            android.widget.ImageView r0 = r9.mArrowUp
            animateAlphaTo(r3, r0)
            android.widget.ImageView r0 = r9.mArrowRight
            animateAlphaTo(r3, r0)
            android.widget.ImageView r0 = r9.mArrowDown
            animateAlphaTo(r3, r0)
            android.widget.ImageView r9 = r9.mArrowLeft
            animateAlphaTo(r3, r9)
            r5 = r1
        L_0x0137:
            if (r5 != 0) goto L_0x013c
            r8.hideMenu()
        L_0x013c:
            return r1
        L_0x013d:
            boolean r8 = super.dispatchKeyEvent(r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.p013tv.TvPipMenuView.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final void onClick(View view) {
        if (this.mListener != null) {
            int id = view.getId();
            if (id == C1777R.C1779id.tv_pip_menu_fullscreen_button) {
                TvPipMenuController tvPipMenuController = (TvPipMenuController) this.mListener;
                Objects.requireNonNull(tvPipMenuController);
                TvPipController tvPipController = (TvPipController) tvPipMenuController.mDelegate;
                Objects.requireNonNull(tvPipController);
                tvPipController.mPipTaskOrganizer.exitPip(tvPipController.mResizeAnimationDuration, false);
                tvPipController.onPipDisappeared();
            } else if (id == C1777R.C1779id.tv_pip_menu_move_button) {
                TvPipMenuController tvPipMenuController2 = (TvPipMenuController) this.mListener;
                Objects.requireNonNull(tvPipMenuController2);
                tvPipMenuController2.mInMoveMode = true;
                TvPipMenuView tvPipMenuView = tvPipMenuController2.mPipMenuView;
                Objects.requireNonNull(tvPipMenuView);
                animateAlphaTo(0.0f, tvPipMenuView.mActionButtonsContainer);
                TvPipMenuView tvPipMenuView2 = tvPipMenuController2.mPipMenuView;
                TvPipController tvPipController2 = (TvPipController) tvPipMenuController2.mDelegate;
                Objects.requireNonNull(tvPipController2);
                TvPipBoundsState tvPipBoundsState = tvPipController2.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState);
                tvPipMenuView2.showMovementHints(tvPipBoundsState.mTvPipGravity);
            } else if (id == C1777R.C1779id.tv_pip_menu_close_button) {
                TvPipMenuController tvPipMenuController3 = (TvPipMenuController) this.mListener;
                Objects.requireNonNull(tvPipMenuController3);
                ((TvPipController) tvPipMenuController3.mDelegate).closePip();
            } else if (id == C1777R.C1779id.tv_pip_menu_expand_button) {
                TvPipMenuController tvPipMenuController4 = (TvPipMenuController) this.mListener;
                Objects.requireNonNull(tvPipMenuController4);
                TvPipController tvPipController3 = (TvPipController) tvPipMenuController4.mDelegate;
                Objects.requireNonNull(tvPipController3);
                TvPipBoundsState tvPipBoundsState2 = tvPipController3.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState2);
                boolean z = !tvPipBoundsState2.mIsTvPipExpanded;
                int updatePositionOnExpandToggled = tvPipController3.mTvPipBoundsAlgorithm.updatePositionOnExpandToggled(tvPipController3.mPreviousGravity, z);
                if (updatePositionOnExpandToggled != 0) {
                    tvPipController3.mPreviousGravity = updatePositionOnExpandToggled;
                }
                TvPipBoundsState tvPipBoundsState3 = tvPipController3.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState3);
                tvPipBoundsState3.mTvPipManuallyCollapsed = !z;
                TvPipBoundsState tvPipBoundsState4 = tvPipController3.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState4);
                tvPipBoundsState4.mIsTvPipExpanded = z;
                tvPipController3.movePinnedStack();
            } else {
                RemoteAction remoteAction = (RemoteAction) view.getTag();
                if (remoteAction != null) {
                    try {
                        remoteAction.getActionIntent().send();
                    } catch (PendingIntent.CanceledException e) {
                        Log.w("TvPipMenuView", "Failed to send action", e);
                    }
                } else {
                    Log.w("TvPipMenuView", "RemoteAction is null");
                }
            }
        }
    }

    public final void showMovementHints(int i) {
        boolean z;
        float f;
        boolean z2;
        float f2;
        boolean z3;
        float f3;
        boolean z4 = true;
        if ((i & 80) == 80) {
            z = true;
        } else {
            z = false;
        }
        float f4 = 1.0f;
        if (z) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        animateAlphaTo(f, this.mArrowUp);
        if ((i & 48) == 48) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            f2 = 1.0f;
        } else {
            f2 = 0.0f;
        }
        animateAlphaTo(f2, this.mArrowDown);
        if ((i & 5) == 5) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3) {
            f3 = 1.0f;
        } else {
            f3 = 0.0f;
        }
        animateAlphaTo(f3, this.mArrowLeft);
        if ((i & 3) != 3) {
            z4 = false;
        }
        if (!z4) {
            f4 = 0.0f;
        }
        animateAlphaTo(f4, this.mArrowRight);
    }

    public static void animateAlphaTo(float f, View view) {
        PathInterpolator pathInterpolator;
        ViewPropertyAnimator alpha = view.animate().alpha(f);
        if (f == 0.0f) {
            pathInterpolator = TvPipInterpolators.EXIT;
        } else {
            pathInterpolator = TvPipInterpolators.ENTER;
        }
        alpha.setInterpolator(pathInterpolator).setDuration(500).withStartAction(new TvPipMenuView$$ExternalSyntheticLambda1(f, view)).withEndAction(new TvPipMenuView$$ExternalSyntheticLambda2(f, view));
    }

    public final void setAdditionalActions(List<RemoteAction> list, Handler handler) {
        int size = list.size();
        int size2 = this.mAdditionalButtons.size();
        if (size > size2) {
            while (size > size2) {
                TvPipMenuActionButton tvPipMenuActionButton = new TvPipMenuActionButton(this.mContext);
                tvPipMenuActionButton.setOnClickListener(this);
                LinearLayout linearLayout = this.mActionButtonsContainer;
                linearLayout.addView(tvPipMenuActionButton, linearLayout.getChildCount() - 1);
                this.mAdditionalButtons.add(tvPipMenuActionButton);
                size2++;
            }
        } else if (size < size2) {
            while (size < size2) {
                size2--;
                View view = (View) this.mAdditionalButtons.get(size2);
                view.setVisibility(8);
                view.setTag((Object) null);
            }
        }
        for (int i = 0; i < size; i++) {
            RemoteAction remoteAction = list.get(i);
            TvPipMenuActionButton tvPipMenuActionButton2 = (TvPipMenuActionButton) this.mAdditionalButtons.get(i);
            tvPipMenuActionButton2.setVisibility(0);
            tvPipMenuActionButton2.mButtonView.setContentDescription(remoteAction.getContentDescription());
            tvPipMenuActionButton2.setEnabled(remoteAction.isEnabled());
            tvPipMenuActionButton2.setTag(remoteAction);
            remoteAction.getIcon().loadDrawableAsync(this.mContext, new TvPipMenuView$$ExternalSyntheticLambda0(tvPipMenuActionButton2), handler);
        }
    }
}
