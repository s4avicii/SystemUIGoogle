package com.android.systemui.p006qs;

import android.animation.ValueAnimator;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.p006qs.PagedTileLayout;
import com.android.systemui.p006qs.PathInterpolatorBuilder;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.p006qs.TouchAnimator;
import com.android.systemui.p006qs.tileimpl.HeightOverrideable;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.p005qs.QSTileView;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.QSAnimator */
public final class QSAnimator implements QSHost.Callback, PagedTileLayout.PageListener, TouchAnimator.Listener, View.OnLayoutChangeListener, View.OnAttachStateChangeListener, TunerService.Tunable {
    public TouchAnimator mAllPagesDelayedAnimator;
    public final ArrayList<View> mAllViews = new ArrayList<>();
    public boolean mAllowFancy;
    public final ArrayList<View> mAnimatedQsViews = new ArrayList<>();
    public TouchAnimator mBrightnessAnimator;
    public int mCurrentPage;
    public final Executor mExecutor;
    public final QSFgsManagerFooter mFgsManagerFooter;
    public TouchAnimator mFirstPageAnimator;
    public boolean mFullRows;
    public final QSTileHost mHost;
    public float mLastPosition;
    public int mLastQQSTileHeight;
    public boolean mNeedsAnimatorUpdate;
    public final C09851 mNonFirstPageListener;
    public final SparseArray<Pair<HeightExpansionAnimator, TouchAnimator>> mNonFirstPageQSAnimators;
    public TouchAnimator mNonfirstPageAlphaAnimator;
    public int mNumQuickTiles;
    public boolean mOnFirstPage;
    public boolean mOnKeyguard;
    public HeightExpansionAnimator mOtherFirstPageTilesHeightAnimator;
    public PagedTileLayout mPagedLayout;
    public HeightExpansionAnimator mQQSTileHeightAnimator;
    public int mQQSTop;
    public TouchAnimator mQQSTranslationYAnimator;
    public final QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    public TouchAnimator mQSTileLayoutTranslatorAnimator;
    public final C0961QS mQs;
    public final QSPanelController mQsPanelController;
    public final QuickQSPanelController mQuickQSPanelController;
    public final QuickQSPanel mQuickQsPanel;
    public final QuickStatusBarHeader mQuickStatusBarHeader;
    public final QSSecurityFooter mSecurityFooter;
    public boolean mShowCollapsedOnKeyguard;
    public int[] mTmpLoc1;
    public int[] mTmpLoc2;
    public boolean mTranslateWhileExpanding;
    public TouchAnimator mTranslationXAnimator;
    public TouchAnimator mTranslationYAnimator;
    public final TunerService mTunerService;
    public final QSAnimator$$ExternalSyntheticLambda0 mUpdateAnimators;

    /* renamed from: com.android.systemui.qs.QSAnimator$HeightExpansionAnimator */
    public static class HeightExpansionAnimator {
        public final ValueAnimator mAnimator;
        public final TouchAnimator.Listener mListener;
        public final C09861 mUpdateListener;
        public final ArrayList mViews = new ArrayList();

        public final void resetViewsHeights() {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                View view = (View) this.mViews.get(i);
                if (view instanceof HeightOverrideable) {
                    ((HeightOverrideable) view).resetOverride();
                } else {
                    view.setBottom(view.getMeasuredHeight() + view.getTop());
                }
            }
        }

        public HeightExpansionAnimator(TouchAnimator.Listener listener, int i, int i2) {
            C09861 r0 = new ValueAnimator.AnimatorUpdateListener() {
                public float mLastT = -1.0f;

                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    int size = HeightExpansionAnimator.this.mViews.size();
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    for (int i = 0; i < size; i++) {
                        View view = (View) HeightExpansionAnimator.this.mViews.get(i);
                        if (view instanceof HeightOverrideable) {
                            ((HeightOverrideable) view).setHeightOverride(intValue);
                        } else {
                            view.setBottom(view.getTop() + intValue);
                        }
                    }
                    if (animatedFraction == 0.0f) {
                        HeightExpansionAnimator.this.mListener.onAnimationAtStart();
                    } else if (animatedFraction == 1.0f) {
                        HeightExpansionAnimator.this.mListener.onAnimationAtEnd();
                    } else {
                        float f = this.mLastT;
                        if (f <= 0.0f || f == 1.0f) {
                            HeightExpansionAnimator.this.mListener.onAnimationStarted();
                        }
                    }
                    this.mLastT = animatedFraction;
                }
            };
            this.mUpdateListener = r0;
            this.mListener = listener;
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{i, i2});
            this.mAnimator = ofInt;
            ofInt.setRepeatCount(-1);
            ofInt.setRepeatMode(2);
            ofInt.addUpdateListener(r0);
        }
    }

    public final void onPageChanged(boolean z, int i) {
        if (!(i == -1 || this.mCurrentPage == i)) {
            this.mCurrentPage = i;
            if (!z && !this.mNonFirstPageQSAnimators.contains(i)) {
                addNonFirstPageAnimators(i);
            }
        }
        if (this.mOnFirstPage != z) {
            if (!z) {
                clearAnimationState();
            }
            this.mOnFirstPage = z;
        }
    }

    public static void getRelativePositionInt(int[] iArr, View view, View view2) {
        if (view != view2 && view != null) {
            if (!view.getClass().equals(SideLabelTileLayout.class)) {
                iArr[0] = view.getLeft() + iArr[0];
                iArr[1] = view.getTop() + iArr[1];
            }
            if (!(view instanceof PagedTileLayout)) {
                iArr[0] = iArr[0] - view.getScrollX();
                iArr[1] = iArr[1] - view.getScrollY();
            }
            getRelativePositionInt(iArr, (View) view.getParent(), view2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x01a7  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x01bd A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addNonFirstPageAnimators(int r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            com.android.systemui.qs.PagedTileLayout r2 = r0.mPagedLayout
            if (r2 != 0) goto L_0x000b
            r3 = 0
            goto L_0x0214
        L_0x000b:
            com.android.systemui.qs.TouchAnimator$Builder r2 = new com.android.systemui.qs.TouchAnimator$Builder
            r2.<init>()
            com.android.systemui.qs.QSExpansionPathInterpolator r4 = r0.mQSExpansionPathInterpolator
            com.android.systemui.qs.PathInterpolatorBuilder$PathInterpolator r4 = r4.getYInterpolator()
            r2.mInterpolator = r4
            com.android.systemui.qs.TouchAnimator$Builder r4 = new com.android.systemui.qs.TouchAnimator$Builder
            r4.<init>()
            r5 = 1041865114(0x3e19999a, float:0.15)
            r4.mStartDelay = r5
            r5 = 1060320051(0x3f333333, float:0.7)
            r4.mEndDelay = r5
            com.android.systemui.qs.QuickQSPanel r5 = r0.mQuickQsPanel
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.qs.QSPanel$QSTileLayout r5 = r5.mTileLayout
            com.android.systemui.qs.SideLabelTileLayout r5 = (com.android.systemui.p006qs.SideLabelTileLayout) r5
            com.android.systemui.plugins.qs.QS r6 = r0.mQs
            android.view.View r6 = r6.getView()
            com.android.systemui.qs.PagedTileLayout r7 = r0.mPagedLayout
            java.util.Objects.requireNonNull(r7)
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r9 = 0
            r10 = 1
            if (r1 >= 0) goto L_0x0045
            goto L_0x007c
        L_0x0045:
            java.util.ArrayList<com.android.systemui.qs.TileLayout> r11 = r7.mPages
            java.lang.Object r11 = r11.get(r9)
            com.android.systemui.qs.TileLayout r11 = (com.android.systemui.p006qs.TileLayout) r11
            java.util.Objects.requireNonNull(r11)
            int r12 = r11.mColumns
            int r11 = r11.mRows
            int r12 = r12 * r11
            int r11 = java.lang.Math.max(r12, r10)
            int r12 = r1 * r11
            int r13 = r1 + 1
            int r13 = r13 * r11
        L_0x005e:
            if (r12 >= r13) goto L_0x007c
            java.util.ArrayList<com.android.systemui.qs.QSPanelControllerBase$TileRecord> r11 = r7.mTiles
            int r11 = r11.size()
            if (r12 >= r11) goto L_0x007c
            java.util.ArrayList<com.android.systemui.qs.QSPanelControllerBase$TileRecord> r11 = r7.mTiles
            java.lang.Object r11 = r11.get(r12)
            com.android.systemui.qs.QSPanelControllerBase$TileRecord r11 = (com.android.systemui.p006qs.QSPanelControllerBase.TileRecord) r11
            com.android.systemui.plugins.qs.QSTile r11 = r11.tile
            java.lang.String r11 = r11.getTileSpec()
            r8.add(r11)
            int r12 = r12 + 1
            goto L_0x005e
        L_0x007c:
            r7 = -1
            r11 = r7
            r12 = r9
            r13 = 0
        L_0x0080:
            int r14 = r8.size()
            if (r12 >= r14) goto L_0x01fc
            com.android.systemui.qs.QSPanelController r14 = r0.mQsPanelController
            java.lang.Object r16 = r8.get(r12)
            r3 = r16
            java.lang.String r3 = (java.lang.String) r3
            java.util.Objects.requireNonNull(r14)
            java.util.ArrayList<com.android.systemui.qs.QSPanelControllerBase$TileRecord> r14 = r14.mRecords
            java.util.Iterator r14 = r14.iterator()
        L_0x0099:
            boolean r16 = r14.hasNext()
            if (r16 == 0) goto L_0x00b8
            java.lang.Object r16 = r14.next()
            r9 = r16
            com.android.systemui.qs.QSPanelControllerBase$TileRecord r9 = (com.android.systemui.p006qs.QSPanelControllerBase.TileRecord) r9
            com.android.systemui.plugins.qs.QSTile r15 = r9.tile
            java.lang.String r15 = r15.getTileSpec()
            boolean r15 = java.util.Objects.equals(r15, r3)
            if (r15 == 0) goto L_0x00b6
            com.android.systemui.plugins.qs.QSTileView r3 = r9.tileView
            goto L_0x00b9
        L_0x00b6:
            r9 = 0
            goto L_0x0099
        L_0x00b8:
            r3 = 0
        L_0x00b9:
            int[] r9 = r0.mTmpLoc2
            getRelativePosition(r9, r3, r6)
            int[] r9 = r0.mTmpLoc2
            r9 = r9[r10]
            int r14 = r0.mQQSTop
            java.util.Objects.requireNonNull(r5)
            int r15 = r5.mColumns
            int r15 = r12 / r15
            float r15 = (float) r15
            int r10 = r5.mCellHeight
            float r10 = (float) r10
            r19 = r6
            float r6 = r5.mSquishinessFraction
            float r10 = r10 * r6
            int r6 = r5.mCellMarginVertical
            float r6 = (float) r6
            float r10 = r10 + r6
            float r10 = r10 * r15
            int r6 = (int) r10
            int r6 = r6 + r14
            int r9 = r9 - r6
            r6 = 2
            float[] r10 = new float[r6]
            int r9 = -r9
            float r9 = (float) r9
            r14 = 0
            r10[r14] = r9
            r9 = 0
            r14 = 1
            r10[r14] = r9
            java.lang.String r14 = "translationY"
            r2.addFloat(r3, r14, r10)
            int r10 = r3.getMeasuredHeight()
            int r15 = r0.mLastQQSTileHeight
            int r10 = r10 - r15
            int r10 = r10 / r6
            com.android.systemui.plugins.qs.QSIconView r15 = r3.getIcon()
            float[] r9 = new float[r6]
            int r6 = -r10
            float r6 = (float) r6
            r17 = 0
            r9[r17] = r6
            r18 = 0
            r20 = 1
            r9[r20] = r18
            r2.addFloat(r15, r14, r9)
            android.view.View r9 = r3.getSecondaryIcon()
            r21 = r5
            r15 = 2
            float[] r5 = new float[r15]
            r5[r17] = r6
            r5[r20] = r18
            r2.addFloat(r9, r14, r5)
            android.view.View r5 = r3.getSecondaryLabel()
            int r5 = r5.getVisibility()
            if (r5 != 0) goto L_0x012f
            android.view.View r5 = r3.getSecondaryLabel()
            int r5 = r5.getMeasuredHeight()
            int r5 = r5 / r15
            goto L_0x0130
        L_0x012f:
            r5 = 0
        L_0x0130:
            int r10 = r10 - r5
            android.view.View r5 = r3.getLabelContainer()
            float[] r6 = new float[r15]
            int r9 = -r10
            float r9 = (float) r9
            r10 = 0
            r6[r10] = r9
            r9 = 0
            r15 = 1
            r6[r15] = r9
            r2.addFloat(r5, r14, r6)
            android.view.View r5 = r3.getSecondaryLabel()
            r6 = 3
            float[] r6 = new float[r6]
            r6 = {0, 1050253722, 1065353216} // fill-array
            java.lang.String r9 = "alpha"
            r2.addFloat(r5, r9, r6)
            android.view.View r5 = r3.getLabelContainer()
            r6 = 2
            float[] r14 = new float[r6]
            r14 = {0, 1065353216} // fill-array
            r4.addFloat(r5, r9, r14)
            com.android.systemui.plugins.qs.QSIconView r5 = r3.getIcon()
            float[] r14 = new float[r6]
            r14 = {0, 1065353216} // fill-array
            r4.addFloat(r5, r9, r14)
            android.view.View r5 = r3.getSecondaryIcon()
            float[] r14 = new float[r6]
            r14 = {0, 1065353216} // fill-array
            r4.addFloat(r5, r9, r14)
            int r5 = r3.getTop()
            if (r5 == r7) goto L_0x0180
            int r11 = r11 + 1
            r7 = r5
        L_0x0180:
            com.android.systemui.qs.QuickQSPanel r5 = r0.mQuickQsPanel
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.qs.QSPanel$QSTileLayout r5 = r5.mTileLayout
            int r5 = r5.getNumVisibleTiles()
            if (r12 < r5) goto L_0x019c
            r5 = 2
            if (r11 < r5) goto L_0x019d
            float[] r5 = new float[r11]
            int r6 = r11 + -1
            r14 = 1065353216(0x3f800000, float:1.0)
            r5[r6] = r14
            r2.addFloat(r3, r9, r5)
            goto L_0x01a5
        L_0x019c:
            r5 = 2
        L_0x019d:
            float[] r5 = new float[r5]
            r5 = {1058642330, 1065353216} // fill-array
            r2.addFloat(r3, r9, r5)
        L_0x01a5:
            if (r13 != 0) goto L_0x01bd
            com.android.systemui.qs.QSAnimator$HeightExpansionAnimator r13 = new com.android.systemui.qs.QSAnimator$HeightExpansionAnimator
            int r5 = r0.mLastQQSTileHeight
            int r6 = r3.getMeasuredHeight()
            r13.<init>(r0, r5, r6)
            com.android.systemui.qs.QSExpansionPathInterpolator r5 = r0.mQSExpansionPathInterpolator
            com.android.systemui.qs.PathInterpolatorBuilder$PathInterpolator r5 = r5.getYInterpolator()
            android.animation.ValueAnimator r6 = r13.mAnimator
            r6.setInterpolator(r5)
        L_0x01bd:
            java.util.ArrayList r5 = r13.mViews
            r5.add(r3)
            r5 = 1
            r3.setClipChildren(r5)
            r3.setClipToPadding(r5)
            java.util.ArrayList<android.view.View> r6 = r0.mAllViews
            r6.add(r3)
            java.util.ArrayList<android.view.View> r6 = r0.mAllViews
            android.view.View r9 = r3.getSecondaryLabel()
            r6.add(r9)
            java.util.ArrayList<android.view.View> r6 = r0.mAllViews
            com.android.systemui.plugins.qs.QSIconView r9 = r3.getIcon()
            r6.add(r9)
            java.util.ArrayList<android.view.View> r6 = r0.mAllViews
            android.view.View r9 = r3.getSecondaryIcon()
            r6.add(r9)
            java.util.ArrayList<android.view.View> r6 = r0.mAllViews
            android.view.View r3 = r3.getLabelContainer()
            r6.add(r3)
            int r12 = r12 + 1
            r9 = r10
            r6 = r19
            r10 = r5
            r5 = r21
            goto L_0x0080
        L_0x01fc:
            com.android.systemui.qs.TouchAnimator r3 = r4.build()
            r4 = 2
            float[] r4 = new float[r4]
            r4 = {0, 1065353216} // fill-array
            java.lang.String r5 = "position"
            r2.addFloat(r3, r5, r4)
            android.util.Pair r3 = new android.util.Pair
            com.android.systemui.qs.TouchAnimator r2 = r2.build()
            r3.<init>(r13, r2)
        L_0x0214:
            android.util.SparseArray<android.util.Pair<com.android.systemui.qs.QSAnimator$HeightExpansionAnimator, com.android.systemui.qs.TouchAnimator>> r0 = r0.mNonFirstPageQSAnimators
            r0.put(r1, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.QSAnimator.addNonFirstPageAnimators(int):void");
    }

    public final void clearAnimationState() {
        int size = this.mAllViews.size();
        this.mQuickQsPanel.setAlpha(0.0f);
        for (int i = 0; i < size; i++) {
            View view = this.mAllViews.get(i);
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            view.setScaleY(1.0f);
            if (view instanceof SideLabelTileLayout) {
                SideLabelTileLayout sideLabelTileLayout = (SideLabelTileLayout) view;
                sideLabelTileLayout.setClipChildren(false);
                sideLabelTileLayout.setClipToPadding(false);
            }
        }
        HeightExpansionAnimator heightExpansionAnimator = this.mQQSTileHeightAnimator;
        if (heightExpansionAnimator != null) {
            heightExpansionAnimator.resetViewsHeights();
        }
        HeightExpansionAnimator heightExpansionAnimator2 = this.mOtherFirstPageTilesHeightAnimator;
        if (heightExpansionAnimator2 != null) {
            heightExpansionAnimator2.resetViewsHeights();
        }
        for (int i2 = 0; i2 < this.mNonFirstPageQSAnimators.size(); i2++) {
            ((HeightExpansionAnimator) this.mNonFirstPageQSAnimators.valueAt(i2).first).resetViewsHeights();
        }
        int size2 = this.mAnimatedQsViews.size();
        for (int i3 = 0; i3 < size2; i3++) {
            this.mAnimatedQsViews.get(i3).setVisibility(0);
        }
    }

    public final void onAnimationAtEnd() {
        this.mQuickQsPanel.setVisibility(4);
        int size = this.mAnimatedQsViews.size();
        for (int i = 0; i < size; i++) {
            this.mAnimatedQsViews.get(i).setVisibility(0);
        }
    }

    public final void onAnimationAtStart() {
        this.mQuickQsPanel.setVisibility(0);
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        boolean z;
        if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mExecutor.execute(this.mUpdateAnimators);
        }
    }

    public final void onTilesChanged() {
        this.mExecutor.execute(this.mUpdateAnimators);
    }

    public final void onViewAttachedToWindow(View view) {
        this.mTunerService.addTunable(this, "sysui_qs_fancy_anim", "sysui_qs_move_whole_rows");
    }

    public final void onViewDetachedFromWindow(View view) {
        QSTileHost qSTileHost = this.mHost;
        Objects.requireNonNull(qSTileHost);
        qSTileHost.mCallbacks.remove(this);
        this.mTunerService.removeTunable(this);
    }

    public final void setPosition(float f) {
        if (this.mNeedsAnimatorUpdate) {
            updateAnimators();
        }
        if (this.mFirstPageAnimator != null) {
            if (this.mOnKeyguard) {
                if (this.mShowCollapsedOnKeyguard) {
                    f = 0.0f;
                } else {
                    f = 1.0f;
                }
            }
            this.mLastPosition = f;
            if (this.mAllowFancy) {
                if (this.mOnFirstPage) {
                    this.mQuickQsPanel.setAlpha(1.0f);
                    this.mFirstPageAnimator.setPosition(f);
                    this.mTranslationYAnimator.setPosition(f);
                    this.mTranslationXAnimator.setPosition(f);
                    HeightExpansionAnimator heightExpansionAnimator = this.mOtherFirstPageTilesHeightAnimator;
                    if (heightExpansionAnimator != null) {
                        heightExpansionAnimator.mAnimator.setCurrentFraction(f);
                    }
                } else {
                    this.mNonfirstPageAlphaAnimator.setPosition(f);
                }
                for (int i = 0; i < this.mNonFirstPageQSAnimators.size(); i++) {
                    Pair valueAt = this.mNonFirstPageQSAnimators.valueAt(i);
                    if (valueAt != null) {
                        HeightExpansionAnimator heightExpansionAnimator2 = (HeightExpansionAnimator) valueAt.first;
                        Objects.requireNonNull(heightExpansionAnimator2);
                        heightExpansionAnimator2.mAnimator.setCurrentFraction(f);
                        ((TouchAnimator) valueAt.second).setPosition(f);
                    }
                }
                HeightExpansionAnimator heightExpansionAnimator3 = this.mQQSTileHeightAnimator;
                if (heightExpansionAnimator3 != null) {
                    heightExpansionAnimator3.mAnimator.setCurrentFraction(f);
                }
                this.mQSTileLayoutTranslatorAnimator.setPosition(f);
                this.mQQSTranslationYAnimator.setPosition(f);
                this.mAllPagesDelayedAnimator.setPosition(f);
                TouchAnimator touchAnimator = this.mBrightnessAnimator;
                if (touchAnimator != null) {
                    touchAnimator.setPosition(f);
                }
            }
        }
    }

    public final void updateAnimators() {
        QSPanel.QSTileLayout qSTileLayout;
        TouchAnimator.Builder builder;
        String str;
        TouchAnimator.Builder builder2;
        String str2;
        TouchAnimator.Builder builder3;
        QSPanel.QSTileLayout qSTileLayout2;
        boolean z;
        boolean z2;
        UniqueObjectHostView uniqueObjectHostView;
        QSTileView qSTileView;
        String str3;
        QSPanel.QSTileLayout qSTileLayout3;
        int i;
        TouchAnimator.Builder builder4;
        String str4;
        TouchAnimator.Builder builder5;
        int i2;
        QSTileView qSTileView2;
        String str5;
        TouchAnimator.Builder builder6;
        char c;
        int i3;
        boolean z3;
        int i4;
        QSTileView qSTileView3;
        int i5;
        this.mNeedsAnimatorUpdate = false;
        TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder9 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder10 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder11 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder12 = new TouchAnimator.Builder();
        builder12.mInterpolator = Interpolators.ACCELERATE;
        QSTileHost qSTileHost = this.mHost;
        Objects.requireNonNull(qSTileHost);
        Collection<QSTile> values = qSTileHost.mTiles.values();
        clearAnimationState();
        this.mNonFirstPageQSAnimators.clear();
        this.mAllViews.clear();
        this.mAnimatedQsViews.clear();
        this.mQQSTileHeightAnimator = null;
        this.mOtherFirstPageTilesHeightAnimator = null;
        QuickQSPanel quickQSPanel = this.mQuickQsPanel;
        Objects.requireNonNull(quickQSPanel);
        this.mNumQuickTiles = quickQSPanel.mMaxTiles;
        QSPanel.QSTileLayout tileLayout = this.mQsPanelController.getTileLayout();
        this.mAllViews.add((View) tileLayout);
        int heightDiff = this.mQs.getHeightDiff();
        if (!this.mTranslateWhileExpanding) {
            heightDiff = (int) (((float) heightDiff) * 0.1f);
        }
        int i6 = heightDiff;
        TouchAnimator.Builder builder13 = new TouchAnimator.Builder();
        String str6 = "translationY";
        builder13.addFloat(tileLayout, str6, (float) i6, 0.0f);
        this.mQSTileLayoutTranslatorAnimator = builder13.build();
        this.mLastQQSTileHeight = 0;
        QSPanelController qSPanelController = this.mQsPanelController;
        Objects.requireNonNull(qSPanelController);
        String str7 = "alpha";
        if (!qSPanelController.mRecords.isEmpty()) {
            int i7 = 0;
            for (QSTile next : values) {
                QSPanelController qSPanelController2 = this.mQsPanelController;
                Objects.requireNonNull(qSPanelController2);
                Iterator<QSPanelControllerBase.TileRecord> it = qSPanelController2.mRecords.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        qSTileView = null;
                        break;
                    }
                    QSPanelControllerBase.TileRecord next2 = it.next();
                    if (next2.tile == next) {
                        qSTileView = next2.tileView;
                        break;
                    }
                }
                QSTileView qSTileView4 = qSTileView;
                if (qSTileView4 == null) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("tileView is null ");
                    m.append(next.getTileSpec());
                    Log.e("QSAnimator", m.toString());
                    str3 = str7;
                } else {
                    PagedTileLayout pagedTileLayout = this.mPagedLayout;
                    if (pagedTileLayout != null) {
                        if (pagedTileLayout.mPages.size() == 0) {
                            i5 = 0;
                        } else {
                            i5 = pagedTileLayout.mPages.get(0).mRecords.size();
                        }
                        if (i7 >= i5) {
                            break;
                        }
                    }
                    View iconView = qSTileView4.getIcon().getIconView();
                    View view = this.mQs.getView();
                    str3 = str7;
                    if (i7 >= this.mQuickQSPanelController.getTileLayout().getNumVisibleTiles() || !this.mAllowFancy) {
                        View view2 = view;
                        int i8 = i7;
                        String str8 = str6;
                        i = i6;
                        qSTileLayout3 = tileLayout;
                        builder6 = builder12;
                        TouchAnimator.Builder builder14 = builder7;
                        qSTileView2 = qSTileView4;
                        str5 = str3;
                        if (this.mFullRows) {
                            PagedTileLayout pagedTileLayout2 = this.mPagedLayout;
                            if (pagedTileLayout2 == null) {
                                c = 1;
                                z3 = false;
                                i2 = i8;
                            } else {
                                if (pagedTileLayout2.mPages.size() == 0) {
                                    i4 = 0;
                                } else {
                                    i4 = pagedTileLayout2.mPages.get(0).mColumns;
                                }
                                c = 1;
                                int i9 = (((this.mNumQuickTiles + i4) - 1) / i4) * i4;
                                i2 = i8;
                                if (i2 < i9) {
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                            }
                            if (z3) {
                                float[] fArr = new float[2];
                                fArr[0] = (float) (-i);
                                fArr[c] = 0.0f;
                                builder5 = builder14;
                                str4 = str8;
                                builder5.addFloat(qSTileView2, str4, fArr);
                                this.mAllViews.add(iconView);
                                builder4 = builder11;
                            } else {
                                builder5 = builder14;
                                str4 = str8;
                                i3 = i;
                            }
                        } else {
                            builder5 = builder14;
                            i2 = i8;
                            str4 = str8;
                            i3 = i;
                            c = 1;
                        }
                        QuickQSPanel quickQSPanel2 = this.mQuickQsPanel;
                        Objects.requireNonNull(quickQSPanel2);
                        SideLabelTileLayout sideLabelTileLayout = (SideLabelTileLayout) quickQSPanel2.mTileLayout;
                        View view3 = view2;
                        getRelativePosition(this.mTmpLoc1, sideLabelTileLayout, view3);
                        this.mQQSTop = this.mTmpLoc1[c];
                        getRelativePosition(this.mTmpLoc2, qSTileView2, view3);
                        i = i3;
                        builder4 = builder11;
                        builder8.addFloat(qSTileView2, str4, (float) (-(this.mTmpLoc2[c] - (((int) (((((float) sideLabelTileLayout.mCellHeight) * sideLabelTileLayout.mSquishinessFraction) + ((float) sideLabelTileLayout.mCellMarginVertical)) * ((float) (i2 / sideLabelTileLayout.mColumns)))) + this.mTmpLoc1[c]))), 0.0f);
                        if (this.mOtherFirstPageTilesHeightAnimator == null) {
                            this.mOtherFirstPageTilesHeightAnimator = new HeightExpansionAnimator(this, this.mLastQQSTileHeight, qSTileView2.getMeasuredHeight());
                        }
                        HeightExpansionAnimator heightExpansionAnimator = this.mOtherFirstPageTilesHeightAnimator;
                        Objects.requireNonNull(heightExpansionAnimator);
                        heightExpansionAnimator.mViews.add(qSTileView2);
                        qSTileView2.setClipChildren(true);
                        qSTileView2.setClipToPadding(true);
                        builder5.addFloat(qSTileView2.getSecondaryLabel(), str5, 0.0f, 1.0f);
                        this.mAllViews.add(qSTileView2.getSecondaryLabel());
                    } else {
                        QuickQSPanelController quickQSPanelController = this.mQuickQSPanelController;
                        Objects.requireNonNull(quickQSPanelController);
                        Iterator<QSPanelControllerBase.TileRecord> it2 = quickQSPanelController.mRecords.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                qSTileView3 = null;
                                break;
                            }
                            QSPanelControllerBase.TileRecord next3 = it2.next();
                            Iterator<QSPanelControllerBase.TileRecord> it3 = it2;
                            if (next3.tile == next) {
                                qSTileView3 = next3.tileView;
                                break;
                            }
                            it2 = it3;
                        }
                        QSTileView qSTileView5 = qSTileView3;
                        if (qSTileView5 != null) {
                            getRelativePosition(this.mTmpLoc1, qSTileView5, view);
                            getRelativePosition(this.mTmpLoc2, qSTileView4, view);
                            int[] iArr = this.mTmpLoc2;
                            int i10 = iArr[1];
                            View view4 = view;
                            int[] iArr2 = this.mTmpLoc1;
                            int i11 = i10 - iArr2[1];
                            int i12 = iArr[0] - iArr2[0];
                            QuickStatusBarHeader quickStatusBarHeader = this.mQuickStatusBarHeader;
                            Objects.requireNonNull(quickStatusBarHeader);
                            int i13 = i11 - quickStatusBarHeader.mTopViewMeasureHeight;
                            int i14 = i7;
                            int i15 = i6;
                            builder9.addFloat(qSTileView5, str6, 0.0f, (float) i13);
                            builder8.addFloat(qSTileView4, str6, (float) (-i13), 0.0f);
                            builder10.addFloat(qSTileView5, "translationX", 0.0f, (float) i12);
                            builder10.addFloat(qSTileView4, "translationX", (float) (-i12), 0.0f);
                            if (this.mQQSTileHeightAnimator == null) {
                                this.mQQSTileHeightAnimator = new HeightExpansionAnimator(this, qSTileView5.getMeasuredHeight(), qSTileView4.getMeasuredHeight());
                                this.mLastQQSTileHeight = qSTileView5.getMeasuredHeight();
                            }
                            HeightExpansionAnimator heightExpansionAnimator2 = this.mQQSTileHeightAnimator;
                            Objects.requireNonNull(heightExpansionAnimator2);
                            heightExpansionAnimator2.mViews.add(qSTileView5);
                            QSIconView icon = qSTileView5.getIcon();
                            View view5 = view4;
                            int i16 = i12;
                            QSIconView icon2 = qSTileView4.getIcon();
                            QSTileView qSTileView6 = qSTileView4;
                            View view6 = view5;
                            QSTileView qSTileView7 = qSTileView6;
                            int i17 = i16;
                            String str9 = str3;
                            QSTileView qSTileView8 = qSTileView5;
                            int i18 = i11;
                            i = i15;
                            TouchAnimator.Builder builder15 = builder10;
                            qSTileLayout3 = tileLayout;
                            TouchAnimator.Builder builder16 = builder8;
                            TouchAnimator.Builder builder17 = builder12;
                            TouchAnimator.Builder builder18 = builder9;
                            translateContent(icon, icon2, view6, i17, i18, this.mTmpLoc1, builder15, builder16, builder18);
                            translateContent(qSTileView8.getLabelContainer(), qSTileView7.getLabelContainer(), view6, i17, i18, this.mTmpLoc1, builder15, builder16, builder18);
                            translateContent(qSTileView8.getSecondaryIcon(), qSTileView7.getSecondaryIcon(), view6, i17, i18, this.mTmpLoc1, builder15, builder16, builder18);
                            str5 = str9;
                            builder6 = builder17;
                            builder6.addFloat(qSTileView8.getSecondaryLabel(), str5, 0.0f, 1.0f);
                            builder11.addFloat(qSTileView8.getSecondaryLabel(), str5, 0.0f, 0.0f);
                            qSTileView2 = qSTileView7;
                            this.mAnimatedQsViews.add(qSTileView2);
                            QSTileView qSTileView9 = qSTileView8;
                            this.mAllViews.add(qSTileView9);
                            this.mAllViews.add(qSTileView9.getSecondaryLabel());
                            builder4 = builder11;
                            builder5 = builder7;
                            i2 = i14;
                            str4 = str6;
                        }
                    }
                    this.mAllViews.add(qSTileView2);
                    i7 = i2 + 1;
                    str7 = str5;
                    builder7 = builder5;
                    str6 = str4;
                    builder11 = builder4;
                    i6 = i;
                    tileLayout = qSTileLayout3;
                    builder12 = builder6;
                }
                str7 = str3;
            }
            str2 = str7;
            qSTileLayout = tileLayout;
            builder3 = builder12;
            builder2 = builder7;
            builder = builder11;
            str = str6;
            int i19 = this.mCurrentPage;
            if (i19 != 0) {
                addNonFirstPageAnimators(i19);
            }
        } else {
            str2 = str7;
            qSTileLayout = tileLayout;
            builder3 = builder12;
            builder2 = builder7;
            builder = builder11;
            str = str6;
        }
        if (this.mAllowFancy) {
            QSPanelController qSPanelController3 = this.mQsPanelController;
            Objects.requireNonNull(qSPanelController3);
            QSPanel qSPanel = (QSPanel) qSPanelController3.mView;
            Objects.requireNonNull(qSPanel);
            View view7 = qSPanel.mBrightnessView;
            QuickQSPanelController quickQSPanelController2 = this.mQuickQSPanelController;
            Objects.requireNonNull(quickQSPanelController2);
            QSPanel qSPanel2 = (QSPanel) quickQSPanelController2.mView;
            Objects.requireNonNull(qSPanel2);
            View view8 = qSPanel2.mBrightnessView;
            if (view8 != null && view8.getVisibility() == 0) {
                this.mAnimatedQsViews.add(view7);
                this.mAllViews.add(view8);
                int[] iArr3 = new int[2];
                int[] iArr4 = new int[2];
                View view9 = this.mQs.getView();
                getRelativePositionInt(iArr3, view7, view9);
                getRelativePositionInt(iArr4, view8, view9);
                int i20 = iArr3[1] - iArr4[1];
                QuickStatusBarHeader quickStatusBarHeader2 = this.mQuickStatusBarHeader;
                Objects.requireNonNull(quickStatusBarHeader2);
                int i21 = i20 - quickStatusBarHeader2.mTopViewMeasureHeight;
                TouchAnimator.Builder builder19 = new TouchAnimator.Builder();
                builder19.addFloat(view7, "sliderScaleY", 0.3f, 1.0f);
                builder19.addFloat(view8, str, 0.0f, (float) i21);
                this.mBrightnessAnimator = builder19.build();
                z2 = true;
                z = false;
            } else if (view7 != null) {
                builder2.addFloat(view7, str, ((float) view7.getMeasuredHeight()) * 0.5f, 0.0f);
                TouchAnimator.Builder builder20 = new TouchAnimator.Builder();
                builder20.addFloat(view7, str2, 0.0f, 1.0f);
                builder20.addFloat(view7, "sliderScaleY", 0.3f, 1.0f);
                builder20.mInterpolator = Interpolators.ALPHA_IN;
                builder20.mStartDelay = 0.3f;
                this.mBrightnessAnimator = builder20.build();
                this.mAllViews.add(view7);
                z2 = true;
                z = false;
            } else {
                this.mBrightnessAnimator = null;
                z2 = true;
                z = false;
            }
            qSTileLayout2 = qSTileLayout;
            builder2.addFloat(qSTileLayout2, str2, 0.0f, 1.0f);
            builder2.addFloat(builder3.build(), "position", 0.0f, 1.0f);
            builder2.mListener = this;
            this.mFirstPageAnimator = builder2.build();
            TouchAnimator.Builder builder21 = new TouchAnimator.Builder();
            builder21.mStartDelay = 0.86f;
            QSFgsManagerFooter qSFgsManagerFooter = this.mFgsManagerFooter;
            Objects.requireNonNull(qSFgsManagerFooter);
            builder21.addFloat(qSFgsManagerFooter.mRootView, str2, 0.0f, 1.0f);
            QSSecurityFooter qSSecurityFooter = this.mSecurityFooter;
            Objects.requireNonNull(qSSecurityFooter);
            builder21.addFloat(qSSecurityFooter.mRootView, str2, 0.0f, 1.0f);
            QSPanelController qSPanelController4 = this.mQsPanelController;
            Objects.requireNonNull(qSPanelController4);
            if (qSPanelController4.mShouldUseSplitNotificationShade || !qSPanelController4.mUsingMediaPlayer || !qSPanelController4.mMediaHost.getVisible() || qSPanelController4.mLastOrientation != 2) {
                z2 = z;
            }
            if (!z2 || (uniqueObjectHostView = this.mQsPanelController.mMediaHost.hostView) == null) {
                this.mQsPanelController.mMediaHost.hostView.setAlpha(1.0f);
            } else {
                builder21.addFloat(uniqueObjectHostView, str2, 0.0f, 1.0f);
            }
            this.mAllPagesDelayedAnimator = builder21.build();
            ArrayList<View> arrayList = this.mAllViews;
            QSFgsManagerFooter qSFgsManagerFooter2 = this.mFgsManagerFooter;
            Objects.requireNonNull(qSFgsManagerFooter2);
            arrayList.add(qSFgsManagerFooter2.mRootView);
            ArrayList<View> arrayList2 = this.mAllViews;
            QSSecurityFooter qSSecurityFooter2 = this.mSecurityFooter;
            Objects.requireNonNull(qSSecurityFooter2);
            arrayList2.add(qSSecurityFooter2.mRootView);
            builder8.mInterpolator = this.mQSExpansionPathInterpolator.getYInterpolator();
            builder9.mInterpolator = this.mQSExpansionPathInterpolator.getYInterpolator();
            QSExpansionPathInterpolator qSExpansionPathInterpolator = this.mQSExpansionPathInterpolator;
            Objects.requireNonNull(qSExpansionPathInterpolator);
            PathInterpolatorBuilder pathInterpolatorBuilder = qSExpansionPathInterpolator.pathInterpolatorBuilder;
            Objects.requireNonNull(pathInterpolatorBuilder);
            builder10.mInterpolator = new PathInterpolatorBuilder.PathInterpolator(pathInterpolatorBuilder.mDist, pathInterpolatorBuilder.f68mX);
            if (this.mOnFirstPage) {
                this.mQQSTranslationYAnimator = builder9.build();
            }
            this.mTranslationYAnimator = builder8.build();
            this.mTranslationXAnimator = builder10.build();
            HeightExpansionAnimator heightExpansionAnimator3 = this.mQQSTileHeightAnimator;
            if (heightExpansionAnimator3 != null) {
                heightExpansionAnimator3.mAnimator.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
            }
            HeightExpansionAnimator heightExpansionAnimator4 = this.mOtherFirstPageTilesHeightAnimator;
            if (heightExpansionAnimator4 != null) {
                heightExpansionAnimator4.mAnimator.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
            }
        } else {
            qSTileLayout2 = qSTileLayout;
        }
        TouchAnimator.Builder builder22 = builder;
        builder22.addFloat(this.mQuickQsPanel, str2, 1.0f, 0.0f);
        builder22.addFloat(qSTileLayout2, str2, 0.0f, 1.0f);
        builder22.mListener = this.mNonFirstPageListener;
        builder22.mEndDelay = 0.9f;
        this.mNonfirstPageAlphaAnimator = builder22.build();
    }

    public final void updateQQSVisibility() {
        int i;
        QuickQSPanel quickQSPanel = this.mQuickQsPanel;
        if (!this.mOnKeyguard || this.mShowCollapsedOnKeyguard) {
            i = 0;
        } else {
            i = 4;
        }
        quickQSPanel.setVisibility(i);
    }

    public QSAnimator(C0961QS qs, QuickQSPanel quickQSPanel, QuickStatusBarHeader quickStatusBarHeader, QSPanelController qSPanelController, QuickQSPanelController quickQSPanelController, QSTileHost qSTileHost, QSFgsManagerFooter qSFgsManagerFooter, QSSecurityFooter qSSecurityFooter, Executor executor, TunerService tunerService, QSExpansionPathInterpolator qSExpansionPathInterpolator) {
        boolean z = true;
        this.mOnFirstPage = true;
        this.mCurrentPage = 0;
        this.mNonFirstPageQSAnimators = new SparseArray<>();
        this.mNeedsAnimatorUpdate = false;
        this.mTmpLoc1 = new int[2];
        this.mTmpLoc2 = new int[2];
        this.mNonFirstPageListener = new TouchAnimator.ListenerAdapter() {
            public final void onAnimationAtEnd() {
                QSAnimator.this.mQuickQsPanel.setVisibility(4);
            }

            public final void onAnimationStarted() {
                QSAnimator.this.mQuickQsPanel.setVisibility(0);
            }
        };
        this.mUpdateAnimators = new QSAnimator$$ExternalSyntheticLambda0(this, 0);
        this.mQs = qs;
        this.mQuickQsPanel = quickQSPanel;
        this.mQsPanelController = qSPanelController;
        this.mQuickQSPanelController = quickQSPanelController;
        this.mQuickStatusBarHeader = quickStatusBarHeader;
        this.mFgsManagerFooter = qSFgsManagerFooter;
        this.mSecurityFooter = qSSecurityFooter;
        this.mHost = qSTileHost;
        this.mExecutor = executor;
        this.mTunerService = tunerService;
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        Objects.requireNonNull(qSTileHost);
        qSTileHost.mCallbacks.add(this);
        Objects.requireNonNull(qSPanelController);
        T t = qSPanelController.mView;
        if (t != null) {
            t.addOnAttachStateChangeListener(this);
        }
        qs.getView().addOnLayoutChangeListener(this);
        T t2 = qSPanelController.mView;
        if ((t2 == null || !t2.isAttachedToWindow()) ? false : z) {
            onViewAttachedToWindow((View) null);
        }
        QSPanel.QSTileLayout tileLayout = qSPanelController.getTileLayout();
        if (tileLayout instanceof PagedTileLayout) {
            this.mPagedLayout = (PagedTileLayout) tileLayout;
        } else {
            Log.w("QSAnimator", "QS Not using page layout");
        }
        QSPanel qSPanel = (QSPanel) qSPanelController.mView;
        Objects.requireNonNull(qSPanel);
        QSPanel.QSTileLayout qSTileLayout = qSPanel.mTileLayout;
        if (qSTileLayout instanceof PagedTileLayout) {
            PagedTileLayout pagedTileLayout = (PagedTileLayout) qSTileLayout;
            Objects.requireNonNull(pagedTileLayout);
            pagedTileLayout.mPageListener = this;
        }
    }

    public static void getRelativePosition(int[] iArr, View view, View view2) {
        iArr[0] = (view.getWidth() / 2) + 0;
        iArr[1] = 0;
        getRelativePositionInt(iArr, view, view2);
    }

    public final void onAnimationStarted() {
        updateQQSVisibility();
        if (this.mOnFirstPage) {
            int size = this.mAnimatedQsViews.size();
            for (int i = 0; i < size; i++) {
                this.mAnimatedQsViews.get(i).setVisibility(4);
            }
        }
    }

    public final void onTuningChanged(String str, String str2) {
        if ("sysui_qs_fancy_anim".equals(str)) {
            boolean parseIntegerSwitch = TunerService.parseIntegerSwitch(str2, true);
            this.mAllowFancy = parseIntegerSwitch;
            if (!parseIntegerSwitch) {
                clearAnimationState();
            }
        } else if ("sysui_qs_move_whole_rows".equals(str)) {
            this.mFullRows = TunerService.parseIntegerSwitch(str2, true);
        }
        updateAnimators();
    }

    public final void translateContent(View view, View view2, View view3, int i, int i2, int[] iArr, TouchAnimator.Builder builder, TouchAnimator.Builder builder2, TouchAnimator.Builder builder3) {
        getRelativePosition(iArr, view, view3);
        int i3 = iArr[0];
        int i4 = iArr[1];
        getRelativePosition(iArr, view2, view3);
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = (i5 - i3) - i;
        builder.addFloat(view, "translationX", 0.0f, (float) i7);
        builder.addFloat(view2, "translationX", (float) (-i7), 0.0f);
        int i8 = (i6 - i4) - i2;
        builder3.addFloat(view, "translationY", 0.0f, (float) i8);
        builder2.addFloat(view2, "translationY", (float) (-i8), 0.0f);
        this.mAllViews.add(view);
        this.mAllViews.add(view2);
    }
}
