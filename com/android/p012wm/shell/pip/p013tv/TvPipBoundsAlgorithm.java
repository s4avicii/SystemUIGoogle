package com.android.p012wm.shell.pip.p013tv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Size;
import android.view.Gravity;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipBoundsAlgorithm */
public final class TvPipBoundsAlgorithm extends PipBoundsAlgorithm {
    public int mFixedExpandedHeightInPx;
    public int mFixedExpandedWidthInPx;
    public final TvPipBoundsState mTvPipBoundsState;

    public final Rect getAdjustedDestinationBounds(Rect rect, float f) {
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState);
        return getTvPipBounds(tvPipBoundsState.mIsTvPipExpanded);
    }

    public final Rect getEntryDestinationBounds() {
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState);
        if (tvPipBoundsState.mTvExpandedAspectRatio != 0.0f) {
            TvPipBoundsState tvPipBoundsState2 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState2);
            if (!tvPipBoundsState2.mTvPipManuallyCollapsed) {
                updatePositionOnExpandToggled(0, true);
            }
        }
        return getTvPipBounds(true);
    }

    public final Rect getTvNormalBounds() {
        Rect defaultBounds = getDefaultBounds(-1.0f, (Size) null);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        Rect transformBoundsToAspectRatioIfValid = transformBoundsToAspectRatioIfValid(defaultBounds, pipBoundsState.mAspectRatio, false, false);
        Rect rect = new Rect();
        getInsetBounds(rect);
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState);
        if (tvPipBoundsState.mIsImeShowing) {
            int i = rect.bottom;
            TvPipBoundsState tvPipBoundsState2 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState2);
            rect.bottom = i - tvPipBoundsState2.mImeHeight;
        }
        Rect rect2 = new Rect();
        TvPipBoundsState tvPipBoundsState3 = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState3);
        Gravity.apply(tvPipBoundsState3.mTvPipGravity, transformBoundsToAspectRatioIfValid.width(), transformBoundsToAspectRatioIfValid.height(), rect, rect2);
        TvPipBoundsState tvPipBoundsState4 = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState4);
        tvPipBoundsState4.mIsTvPipExpanded = false;
        return rect2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Rect getTvPipBounds(boolean r7) {
        /*
            r6 = this;
            com.android.wm.shell.pip.tv.TvPipBoundsState r0 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsTvExpandedPipEnabled
            if (r0 == 0) goto L_0x00d5
            if (r7 != 0) goto L_0x000d
            goto L_0x00d5
        L_0x000d:
            com.android.wm.shell.pip.tv.TvPipBoundsState r7 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r7)
            com.android.wm.shell.common.DisplayLayout r7 = r7.mDisplayLayout
            com.android.wm.shell.pip.tv.TvPipBoundsState r0 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r0)
            float r0 = r0.mTvExpandedAspectRatio
            r1 = 0
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x002c
            java.lang.String r7 = "TvPipBoundsAlgorithm"
            java.lang.String r0 = "Expanded mode not supported"
            android.util.Log.d(r7, r0)
            android.graphics.Rect r6 = r6.getTvNormalBounds()
            return r6
        L_0x002c:
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r2 = 1
            r3 = 2
            if (r1 >= 0) goto L_0x006a
            com.android.wm.shell.pip.tv.TvPipBoundsState r1 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mTvFixedPipOrientation
            if (r1 != r3) goto L_0x0045
            com.android.wm.shell.pip.tv.TvPipBoundsState r7 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r7)
            android.util.Size r7 = r7.mTvExpandedSize
            goto L_0x00a0
        L_0x0045:
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mHeight
            android.graphics.Point r1 = r6.mScreenEdgeInsets
            int r1 = r1.y
            int r1 = r1 * r3
            int r7 = r7 - r1
            int r1 = r6.mFixedExpandedWidthInPx
            float r1 = (float) r1
            float r1 = r1 / r0
            float r0 = (float) r7
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0062
            android.util.Size r7 = new android.util.Size
            int r0 = r6.mFixedExpandedWidthInPx
            int r1 = (int) r1
            r7.<init>(r0, r1)
            goto L_0x00a0
        L_0x0062:
            android.util.Size r0 = new android.util.Size
            int r1 = r6.mFixedExpandedWidthInPx
            r0.<init>(r1, r7)
            goto L_0x009f
        L_0x006a:
            com.android.wm.shell.pip.tv.TvPipBoundsState r1 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mTvFixedPipOrientation
            if (r1 != r2) goto L_0x007b
            com.android.wm.shell.pip.tv.TvPipBoundsState r7 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r7)
            android.util.Size r7 = r7.mTvExpandedSize
            goto L_0x00a0
        L_0x007b:
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mWidth
            android.graphics.Point r1 = r6.mScreenEdgeInsets
            int r1 = r1.x
            int r1 = r1 * r3
            int r7 = r7 - r1
            int r1 = r6.mFixedExpandedHeightInPx
            float r1 = (float) r1
            float r1 = r1 * r0
            float r0 = (float) r7
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0098
            android.util.Size r7 = new android.util.Size
            int r0 = (int) r1
            int r1 = r6.mFixedExpandedHeightInPx
            r7.<init>(r0, r1)
            goto L_0x00a0
        L_0x0098:
            android.util.Size r0 = new android.util.Size
            int r1 = r6.mFixedExpandedHeightInPx
            r0.<init>(r7, r1)
        L_0x009f:
            r7 = r0
        L_0x00a0:
            if (r7 != 0) goto L_0x00a7
            android.graphics.Rect r6 = r6.getTvNormalBounds()
            return r6
        L_0x00a7:
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r6.getInsetBounds(r0)
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            com.android.wm.shell.pip.tv.TvPipBoundsState r3 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.mTvPipGravity
            int r4 = r7.getWidth()
            int r5 = r7.getHeight()
            android.view.Gravity.apply(r3, r4, r5, r0, r1)
            com.android.wm.shell.pip.tv.TvPipBoundsState r0 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r0)
            r0.mTvExpandedSize = r7
            com.android.wm.shell.pip.tv.TvPipBoundsState r6 = r6.mTvPipBoundsState
            java.util.Objects.requireNonNull(r6)
            r6.mIsTvPipExpanded = r2
            return r1
        L_0x00d5:
            android.graphics.Rect r6 = r6.getTvNormalBounds()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm.getTvPipBounds(boolean):android.graphics.Rect");
    }

    public final int updatePositionOnExpandToggled(int i, boolean z) {
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState);
        int i2 = 0;
        if (!tvPipBoundsState.mIsTvExpandedPipEnabled) {
            return 0;
        }
        if (z) {
            TvPipBoundsState tvPipBoundsState2 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState2);
            if (tvPipBoundsState2.mTvFixedPipOrientation == 0) {
                TvPipBoundsState tvPipBoundsState3 = this.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState3);
                float f = tvPipBoundsState3.mTvExpandedAspectRatio;
                if (f == 0.0f) {
                    return 0;
                }
                if (f < 1.0f) {
                    TvPipBoundsState tvPipBoundsState4 = this.mTvPipBoundsState;
                    Objects.requireNonNull(tvPipBoundsState4);
                    tvPipBoundsState4.mTvFixedPipOrientation = 1;
                } else {
                    TvPipBoundsState tvPipBoundsState5 = this.mTvPipBoundsState;
                    Objects.requireNonNull(tvPipBoundsState5);
                    tvPipBoundsState5.mTvFixedPipOrientation = 2;
                }
            }
        }
        TvPipBoundsState tvPipBoundsState6 = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState6);
        int i3 = tvPipBoundsState6.mTvPipGravity;
        if (z) {
            TvPipBoundsState tvPipBoundsState7 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState7);
            i2 = tvPipBoundsState7.mTvPipGravity;
            TvPipBoundsState tvPipBoundsState8 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState8);
            if (tvPipBoundsState8.mTvFixedPipOrientation == 2) {
                i = (i3 & 112) | 1;
            } else {
                i = (i3 & 7) | 16;
            }
        } else if (i == 0) {
            TvPipBoundsState tvPipBoundsState9 = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState9);
            if (tvPipBoundsState9.mTvFixedPipOrientation == 2) {
                i = (i3 & 112) | 5;
            } else {
                i = (i3 & 7) | 80;
            }
        }
        TvPipBoundsState tvPipBoundsState10 = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState10);
        tvPipBoundsState10.mTvPipGravity = i;
        return i2;
    }

    public TvPipBoundsAlgorithm(Context context, TvPipBoundsState tvPipBoundsState, PipSnapAlgorithm pipSnapAlgorithm) {
        super(context, tvPipBoundsState, pipSnapAlgorithm);
        this.mTvPipBoundsState = tvPipBoundsState;
    }

    public final void reloadResources(Context context) {
        super.reloadResources(context);
        Resources resources = context.getResources();
        this.mFixedExpandedHeightInPx = resources.getDimensionPixelSize(17105091);
        this.mFixedExpandedWidthInPx = resources.getDimensionPixelSize(17105092);
    }
}
