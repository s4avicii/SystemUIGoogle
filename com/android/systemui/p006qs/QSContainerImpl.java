package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.customize.QSCustomizer;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSContainerImpl */
public class QSContainerImpl extends FrameLayout implements Dumpable {
    public boolean mClippingEnabled;
    public int mContentPadding = -1;
    public int mFancyClippingBottom;
    public final Path mFancyClippingPath = new Path();
    public final float[] mFancyClippingRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public int mFancyClippingTop;
    public QuickStatusBarHeader mHeader;
    public int mHeightOverride = -1;
    public QSCustomizer mQSCustomizer;
    public NonInterceptingScrollView mQSPanelContainer;
    public boolean mQsDisabled;
    public float mQsExpansion;
    public int mSideMargins;

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean performClick() {
        return true;
    }

    public final void dispatchDraw(Canvas canvas) {
        if (!this.mFancyClippingPath.isEmpty()) {
            canvas.translate(0.0f, -getTranslationY());
            canvas.clipOutPath(this.mFancyClippingPath);
            canvas.translate(0.0f, getTranslationY());
        }
        super.dispatchDraw(canvas);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + " updateClippingPath: top(" + this.mFancyClippingTop + ") bottom(" + this.mFancyClippingBottom + ") mClippingEnabled(" + this.mClippingEnabled + ")");
    }

    public final boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        if (!this.mClippingEnabled || getTranslationY() + f2 <= ((float) this.mFancyClippingTop)) {
            return super.isTransformedTouchPointInView(f, f2, view, pointF);
        }
        return false;
    }

    public final void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        if (view != this.mQSPanelContainer) {
            super.measureChildWithMargins(view, i, i2, i3, i4);
        }
    }

    public final void onMeasure(int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mQSPanelContainer.getLayoutParams();
        int size = View.MeasureSpec.getSize(i2);
        int paddingBottom = ((size - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin) - getPaddingBottom();
        int i3 = this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
        this.mQSPanelContainer.measure(ViewGroup.getChildMeasureSpec(i, i3, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(paddingBottom, Integer.MIN_VALUE));
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mQSPanelContainer.getMeasuredWidth() + i3, 1073741824), View.MeasureSpec.makeMeasureSpec(size, 1073741824));
        this.mQSCustomizer.measure(i, View.MeasureSpec.makeMeasureSpec(size, 1073741824));
    }

    public final void updateClippingPath() {
        this.mFancyClippingPath.reset();
        if (!this.mClippingEnabled) {
            invalidate();
            return;
        }
        this.mFancyClippingPath.addRoundRect(0.0f, (float) this.mFancyClippingTop, (float) getWidth(), (float) this.mFancyClippingBottom, this.mFancyClippingRadii, Path.Direction.CW);
        invalidate();
    }

    public final void updateExpansion() {
        int i;
        int i2 = this.mHeightOverride;
        if (i2 == -1) {
            i2 = getMeasuredHeight();
        }
        if (this.mQSCustomizer.isCustomizing()) {
            i = this.mQSCustomizer.getHeight();
        } else {
            i = Math.round(this.mQsExpansion * ((float) (i2 - this.mHeader.getHeight()))) + this.mHeader.getHeight();
        }
        int i3 = this.mHeightOverride;
        if (i3 == -1) {
            i3 = getMeasuredHeight();
        }
        if (this.mQSCustomizer.isCustomizing()) {
            this.mQSCustomizer.getHeight();
        } else {
            Math.round(this.mQsExpansion * ((float) (((this.mQSPanelContainer.getScrollRange() + i3) - this.mQSPanelContainer.getScrollY()) - this.mHeader.getHeight())));
            this.mHeader.getHeight();
        }
        setBottom(getTop() + i);
    }

    public final void updateResources(QSPanelController qSPanelController, QuickStatusBarHeaderController quickStatusBarHeaderController) {
        int i;
        boolean z;
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
        int paddingStart = nonInterceptingScrollView.getPaddingStart();
        Context context = this.mContext;
        Resources resources = context.getResources();
        if (Utils.shouldUseSplitNotificationShade(resources)) {
            i = resources.getDimensionPixelSize(C1777R.dimen.qs_header_system_icons_area_height);
        } else {
            i = SystemBarUtils.getQuickQsOffsetHeight(context);
        }
        nonInterceptingScrollView.setPaddingRelative(paddingStart, i, this.mQSPanelContainer.getPaddingEnd(), this.mQSPanelContainer.getPaddingBottom());
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.notification_side_paddings);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.notification_shade_content_margin_horizontal);
        if (dimensionPixelSize2 == this.mContentPadding && dimensionPixelSize == this.mSideMargins) {
            z = false;
        } else {
            z = true;
        }
        this.mContentPadding = dimensionPixelSize2;
        this.mSideMargins = dimensionPixelSize;
        if (z) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt != this.mQSCustomizer) {
                    if (!(childAt instanceof FooterActionsView)) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                        int i3 = this.mSideMargins;
                        layoutParams.rightMargin = i3;
                        layoutParams.leftMargin = i3;
                    }
                    if (childAt == this.mQSPanelContainer) {
                        int i4 = this.mContentPadding;
                        Objects.requireNonNull(qSPanelController);
                        QSPanel qSPanel = (QSPanel) qSPanelController.mView;
                        UniqueObjectHostView hostView = qSPanelController.mMediaHost.getHostView();
                        Objects.requireNonNull(qSPanel);
                        qSPanel.mContentMarginEnd = i4;
                        qSPanel.updateMediaHostContentMargins(hostView);
                        int i5 = this.mSideMargins;
                        QSPanel qSPanel2 = (QSPanel) qSPanelController.mView;
                        Objects.requireNonNull(qSPanel2);
                        QSPanel.QSTileLayout qSTileLayout = qSPanel2.mTileLayout;
                        if (qSTileLayout instanceof PagedTileLayout) {
                            PagedTileLayout pagedTileLayout = (PagedTileLayout) qSTileLayout;
                            Objects.requireNonNull(pagedTileLayout);
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) pagedTileLayout.getLayoutParams();
                            int i6 = -i5;
                            marginLayoutParams.setMarginStart(i6);
                            marginLayoutParams.setMarginEnd(i6);
                            pagedTileLayout.setLayoutParams(marginLayoutParams);
                            int size = pagedTileLayout.mPages.size();
                            for (int i7 = 0; i7 < size; i7++) {
                                View view = pagedTileLayout.mPages.get(i7);
                                view.setPadding(i5, view.getPaddingTop(), i5, view.getPaddingBottom());
                            }
                        }
                    } else if (childAt == this.mHeader) {
                        int i8 = this.mContentPadding;
                        Objects.requireNonNull(quickStatusBarHeaderController);
                        QuickQSPanelController quickQSPanelController = quickStatusBarHeaderController.mQuickQSPanelController;
                        Objects.requireNonNull(quickQSPanelController);
                        QuickQSPanel quickQSPanel = (QuickQSPanel) quickQSPanelController.mView;
                        UniqueObjectHostView hostView2 = quickQSPanelController.mMediaHost.getHostView();
                        Objects.requireNonNull(quickQSPanel);
                        quickQSPanel.mContentMarginEnd = i8;
                        quickQSPanel.updateMediaHostContentMargins(hostView2);
                    } else {
                        childAt.setPaddingRelative(this.mContentPadding, childAt.getPaddingTop(), this.mContentPadding, childAt.getPaddingBottom());
                    }
                }
            }
        }
    }

    public QSContainerImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQSPanelContainer = (NonInterceptingScrollView) findViewById(C1777R.C1779id.expanded_qs_scroll_view);
        this.mHeader = (QuickStatusBarHeader) findViewById(C1777R.C1779id.header);
        this.mQSCustomizer = (QSCustomizer) findViewById(C1777R.C1779id.qs_customize);
        setImportantForAccessibility(2);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateExpansion();
        updateClippingPath();
    }
}
