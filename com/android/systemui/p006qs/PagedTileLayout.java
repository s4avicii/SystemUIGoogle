package com.android.systemui.p006qs;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.PagedTileLayout */
public class PagedTileLayout extends ViewPager implements QSPanel.QSTileLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C09843 mAdapter;
    public AnimatorSet mBounceAnimatorSet;
    public boolean mDistributeTiles = false;
    public int mExcessHeight;
    public int mLastExcessHeight;
    public float mLastExpansion;
    public int mLastMaxHeight = -1;
    public int mLayoutDirection;
    public int mLayoutOrientation;
    public boolean mListening;
    public int mMaxColumns = 100;
    public int mMinRows = 1;
    public final C09832 mOnPageChangeListener;
    public PageIndicator mPageIndicator;
    public float mPageIndicatorPosition;
    public PageListener mPageListener;
    public int mPageToRestore = -1;
    public final ArrayList<TileLayout> mPages = new ArrayList<>();
    public Scroller mScroller;
    public final ArrayList<QSPanelControllerBase.TileRecord> mTiles = new ArrayList<>();
    public final UiEventLoggerImpl mUiEventLogger = QSEvents.qsUiEventsLogger;

    /* renamed from: com.android.systemui.qs.PagedTileLayout$PageListener */
    public interface PageListener {
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean updateResources() {
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            z |= this.mPages.get(i).updateResources();
        }
        if (z) {
            this.mDistributeTiles = true;
            requestLayout();
        }
        return z;
    }

    public final void addTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        this.mDistributeTiles = true;
        requestLayout();
    }

    public final void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (!this.mFakeDragging) {
                beginFakeDrag();
            }
            try {
                super.fakeDragBy((float) (getScrollX() - this.mScroller.getCurrX()));
                postInvalidateOnAnimation();
            } catch (NullPointerException e) {
                Log.e("PagedTileLayout", "FakeDragBy called before begin", e);
                post(new PagedTileLayout$$ExternalSyntheticLambda1(this, this.mPages.size() - 1));
            }
        } else if (this.mFakeDragging) {
            endFakeDrag();
            AnimatorSet animatorSet = this.mBounceAnimatorSet;
            if (animatorSet != null) {
                animatorSet.start();
            }
            setOffscreenPageLimit(1);
        }
        super.computeScroll();
    }

    public final int getNumPages() {
        int size = this.mTiles.size();
        TileLayout tileLayout = this.mPages.get(0);
        Objects.requireNonNull(tileLayout);
        int max = Math.max(size / Math.max(tileLayout.mColumns * tileLayout.mRows, 1), 1);
        TileLayout tileLayout2 = this.mPages.get(0);
        Objects.requireNonNull(tileLayout2);
        if (size > Math.max(tileLayout2.mColumns * tileLayout2.mRows, 1) * max) {
            return max + 1;
        }
        return max;
    }

    public final int getNumVisibleTiles() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        ArrayList<TileLayout> arrayList = this.mPages;
        int i = this.mCurItem;
        if (this.mLayoutDirection == 1) {
            i = (arrayList.size() - 1) - i;
        }
        return arrayList.get(i).mRecords.size();
    }

    public final int getTilesHeight() {
        TileLayout tileLayout = this.mPages.get(0);
        if (tileLayout == null) {
            return 0;
        }
        return tileLayout.getTilesHeight();
    }

    public final void onMeasure(int i, int i2) {
        int size = this.mTiles.size();
        if (!(!this.mDistributeTiles && this.mLastMaxHeight == View.MeasureSpec.getSize(i2) && this.mLastExcessHeight == this.mExcessHeight)) {
            int size2 = View.MeasureSpec.getSize(i2);
            this.mLastMaxHeight = size2;
            int i3 = this.mExcessHeight;
            this.mLastExcessHeight = i3;
            if (this.mPages.get(0).updateMaxRows(size2 - i3, size) || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                int numPages = getNumPages();
                int size3 = this.mPages.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    this.mPages.get(i4).removeAllViews();
                }
                if (size3 != numPages) {
                    while (this.mPages.size() < numPages) {
                        this.mPages.add(createTileLayout());
                    }
                    while (this.mPages.size() > numPages) {
                        ArrayList<TileLayout> arrayList = this.mPages;
                        arrayList.remove(arrayList.size() - 1);
                    }
                    this.mPageIndicator.setNumPages(this.mPages.size());
                    setAdapter(this.mAdapter);
                    this.mAdapter.notifyDataSetChanged();
                    int i5 = this.mPageToRestore;
                    if (i5 != -1) {
                        setCurrentItem(i5, false);
                        this.mPageToRestore = -1;
                    }
                }
                TileLayout tileLayout = this.mPages.get(0);
                Objects.requireNonNull(tileLayout);
                int max = Math.max(tileLayout.mColumns * tileLayout.mRows, 1);
                int size4 = this.mTiles.size();
                int i6 = 0;
                for (int i7 = 0; i7 < size4; i7++) {
                    QSPanelControllerBase.TileRecord tileRecord = this.mTiles.get(i7);
                    if (this.mPages.get(i6).mRecords.size() == max) {
                        i6++;
                    }
                    this.mPages.get(i6).addTile(tileRecord);
                }
            }
            int i8 = this.mPages.get(0).mRows;
            for (int i9 = 0; i9 < this.mPages.size(); i9++) {
                this.mPages.get(i9).mRows = i8;
            }
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            int measuredHeight = getChildAt(i11).getMeasuredHeight();
            if (measuredHeight > i10) {
                i10 = measuredHeight;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), getPaddingBottom() + i10);
    }

    public final void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        if (this.mTiles.remove(tileRecord)) {
            this.mDistributeTiles = true;
            requestLayout();
        }
    }

    public final void restoreInstanceState(Bundle bundle) {
        this.mPageToRestore = bundle.getInt("current_page", -1);
    }

    public final void saveInstanceState(Bundle bundle) {
        bundle.putInt("current_page", this.mCurItem);
    }

    public final void setExpansion(float f, float f2) {
        this.mLastExpansion = f;
        updateSelected();
    }

    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening != z) {
            this.mListening = z;
            updateListening();
        }
    }

    public final boolean setMaxColumns(int i) {
        this.mMaxColumns = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (this.mPages.get(i2).setMaxColumns(i)) {
                this.mDistributeTiles = true;
                z = true;
            }
        }
        return z;
    }

    public final boolean setMinRows(int i) {
        this.mMinRows = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (this.mPages.get(i2).setMinRows(i)) {
                this.mDistributeTiles = true;
                z = true;
            }
        }
        return z;
    }

    public final void setSquishinessFraction(float f) {
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            this.mPages.get(i).setSquishinessFraction(f);
        }
    }

    public final void updateListening() {
        boolean z;
        Iterator<TileLayout> it = this.mPages.iterator();
        while (it.hasNext()) {
            TileLayout next = it.next();
            if (next.getParent() == null || !this.mListening) {
                z = false;
            } else {
                z = true;
            }
            next.setListening(z, (UiEventLogger) null);
        }
    }

    public final void updateSelected() {
        boolean z;
        boolean z2;
        float f = this.mLastExpansion;
        if (f <= 0.0f || f >= 1.0f) {
            if (f == 1.0f) {
                z = true;
            } else {
                z = false;
            }
            setImportantForAccessibility(4);
            int i = this.mCurItem;
            if (this.mLayoutDirection == 1) {
                i = (this.mPages.size() - 1) - i;
            }
            for (int i2 = 0; i2 < this.mPages.size(); i2++) {
                TileLayout tileLayout = this.mPages.get(i2);
                if (i2 == i) {
                    z2 = z;
                } else {
                    z2 = false;
                }
                tileLayout.setSelected(z2);
                if (tileLayout.isSelected()) {
                    for (int i3 = 0; i3 < tileLayout.mRecords.size(); i3++) {
                        QSTile qSTile = tileLayout.mRecords.get(i3).tile;
                        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
                    }
                }
            }
            setImportantForAccessibility(0);
        }
    }

    public PagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        C09832 r0 = new ViewPager.SimpleOnPageChangeListener() {
            public int mCurrentScrollState = 0;
            public boolean mIsScrollJankTraceBegin = false;

            public final void onPageScrollStateChanged(int i) {
                if (i != this.mCurrentScrollState && i == 0) {
                    InteractionJankMonitor.getInstance().end(6);
                    this.mIsScrollJankTraceBegin = false;
                }
                this.mCurrentScrollState = i;
            }

            public final void onPageScrolled(int i, float f, int i2) {
                int i3;
                boolean z = true;
                if (!this.mIsScrollJankTraceBegin && this.mCurrentScrollState == 1) {
                    InteractionJankMonitor.getInstance().begin(PagedTileLayout.this, 6);
                    this.mIsScrollJankTraceBegin = true;
                }
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                PageIndicator pageIndicator = pagedTileLayout.mPageIndicator;
                if (pageIndicator != null) {
                    float f2 = ((float) i) + f;
                    pagedTileLayout.mPageIndicatorPosition = f2;
                    pageIndicator.setLocation(f2);
                    PagedTileLayout pagedTileLayout2 = PagedTileLayout.this;
                    if (pagedTileLayout2.mPageListener != null) {
                        if (pagedTileLayout2.isLayoutRtl()) {
                            i3 = (PagedTileLayout.this.mPages.size() - 1) - i;
                        } else {
                            i3 = i;
                        }
                        PagedTileLayout pagedTileLayout3 = PagedTileLayout.this;
                        PageListener pageListener = pagedTileLayout3.mPageListener;
                        if (i2 != 0 || (!pagedTileLayout3.isLayoutRtl() ? i != 0 : i != PagedTileLayout.this.mPages.size() - 1)) {
                            z = false;
                        }
                        if (i2 != 0) {
                            i3 = -1;
                        }
                        ((QSAnimator) pageListener).onPageChanged(z, i3);
                    }
                }
            }

            public final void onPageSelected(int i) {
                int i2;
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                int i3 = PagedTileLayout.$r8$clinit;
                pagedTileLayout.updateSelected();
                PagedTileLayout pagedTileLayout2 = PagedTileLayout.this;
                if (pagedTileLayout2.mPageIndicator != null && pagedTileLayout2.mPageListener != null) {
                    boolean z = true;
                    if (pagedTileLayout2.isLayoutRtl()) {
                        i2 = (PagedTileLayout.this.mPages.size() - 1) - i;
                    } else {
                        i2 = i;
                    }
                    PagedTileLayout pagedTileLayout3 = PagedTileLayout.this;
                    PageListener pageListener = pagedTileLayout3.mPageListener;
                    if (!pagedTileLayout3.isLayoutRtl() ? i != 0 : i != PagedTileLayout.this.mPages.size() - 1) {
                        z = false;
                    }
                    ((QSAnimator) pageListener).onPageChanged(z, i2);
                }
            }
        };
        this.mOnPageChangeListener = r0;
        C09843 r1 = new PagerAdapter() {
            public final boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }

            public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                viewGroup.removeView((View) obj);
                PagedTileLayout pagedTileLayout = PagedTileLayout.this;
                int i2 = PagedTileLayout.$r8$clinit;
                pagedTileLayout.updateListening();
            }

            public final int getCount() {
                return PagedTileLayout.this.mPages.size();
            }

            public final Object instantiateItem(ViewGroup viewGroup, int i) {
                if (PagedTileLayout.this.isLayoutRtl()) {
                    i = (PagedTileLayout.this.mPages.size() - 1) - i;
                }
                ViewGroup viewGroup2 = PagedTileLayout.this.mPages.get(i);
                if (viewGroup2.getParent() != null) {
                    viewGroup.removeView(viewGroup2);
                }
                viewGroup.addView(viewGroup2);
                PagedTileLayout.this.updateListening();
                return viewGroup2;
            }
        };
        this.mAdapter = r1;
        this.mScroller = new Scroller(context, PagedTileLayout$$ExternalSyntheticLambda0.INSTANCE);
        setAdapter(r1);
        this.mOnPageChangeListener = r0;
        setCurrentItem(0, false);
        this.mLayoutOrientation = getResources().getConfiguration().orientation;
        this.mLayoutDirection = getLayoutDirection();
    }

    public final TileLayout createTileLayout() {
        TileLayout tileLayout = (TileLayout) LayoutInflater.from(getContext()).inflate(C1777R.layout.qs_paged_page, this, false);
        tileLayout.setMinRows(this.mMinRows);
        tileLayout.setMaxColumns(this.mMaxColumns);
        return tileLayout;
    }

    public final void endFakeDrag() {
        try {
            super.endFakeDrag();
        } catch (NullPointerException e) {
            Log.e("PagedTileLayout", "endFakeDrag called without velocityTracker", e);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            View view = this.mPages.get(i);
            if (view.getParent() == null) {
                view.dispatchConfigurationChanged(configuration);
            }
        }
        int i2 = this.mLayoutOrientation;
        int i3 = configuration.orientation;
        if (i2 != i3) {
            this.mLayoutOrientation = i3;
            this.mDistributeTiles = true;
            setCurrentItem(0, false);
            this.mPageToRestore = 0;
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add(createTileLayout());
        this.mAdapter.notifyDataSetChanged();
    }

    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(0, false);
            this.mPageToRestore = 0;
        }
    }

    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }
}
