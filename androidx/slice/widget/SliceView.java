package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import androidx.lifecycle.Observer;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceMetadata;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import okio.Okio;

public class SliceView extends ViewGroup implements Observer<Slice>, View.OnClickListener {
    public static final C03703 SLICE_ACTION_PRIORITY_COMPARATOR = new Comparator<SliceAction>() {
        public final int compare(Object obj, Object obj2) {
            int priority = ((SliceAction) obj).getPriority();
            int priority2 = ((SliceAction) obj2).getPriority();
            if (priority < 0 && priority2 < 0) {
                return 0;
            }
            if (priority >= 0) {
                if (priority2 >= 0) {
                    if (priority2 >= priority) {
                        if (priority2 <= priority) {
                            return 0;
                        }
                    }
                }
                return -1;
            }
            return 1;
        }
    };
    public ActionRow mActionRow;
    public int mActionRowHeight;
    public ArrayList mActions;
    public Slice mCurrentSlice;
    public boolean mCurrentSliceLoggedVisible = false;
    public SliceMetricsWrapper mCurrentSliceMetrics;
    public TemplateView mCurrentView;
    public int mDownX;
    public int mDownY;
    public Handler mHandler;
    public boolean mInLongpress;
    public int mLargeHeight;
    public ListContent mListContent;
    public View.OnLongClickListener mLongClickListener;
    public C03681 mLongpressCheck = new Runnable() {
        public final void run() {
            View.OnLongClickListener onLongClickListener;
            SliceView sliceView = SliceView.this;
            if (sliceView.mPressing && (onLongClickListener = sliceView.mLongClickListener) != null) {
                sliceView.mInLongpress = true;
                onLongClickListener.onLongClick(sliceView);
                SliceView.this.performHapticFeedback(0);
            }
        }
    };
    public int mMinTemplateHeight;
    public View.OnClickListener mOnClickListener;
    public boolean mPressing;
    public C03692 mRefreshLastUpdated = new Runnable() {
        public final void run() {
            SliceMetadata sliceMetadata = SliceView.this.mSliceMetadata;
            if (sliceMetadata != null && sliceMetadata.isExpired()) {
                SliceView.this.mCurrentView.setShowLastUpdated(true);
                SliceView sliceView = SliceView.this;
                TemplateView templateView = sliceView.mCurrentView;
                ListContent listContent = sliceView.mListContent;
                Objects.requireNonNull(templateView);
                templateView.mListContent = listContent;
                listContent.getHeight(templateView.mSliceStyle, templateView.mViewPolicy);
                templateView.updateDisplayedItems();
            }
            SliceView.this.mHandler.postDelayed(this, 60000);
        }
    };
    public boolean mShowActionDividers = false;
    public boolean mShowHeaderDivider = false;
    public boolean mShowLastUpdated = true;
    public boolean mShowTitleItems = false;
    public SliceMetadata mSliceMetadata;
    public SliceStyle mSliceStyle;
    public int mThemeTintColor = -1;
    public int mTouchSlopSquared;
    public SliceViewPolicy mViewPolicy;

    public interface OnSliceActionListener {
        void onSliceAction();
    }

    public final int getTintColor() {
        int i = this.mThemeTintColor;
        if (i != -1) {
            return i;
        }
        SliceItem findSubtype = SliceQuery.findSubtype(this.mCurrentSlice, "int", "color");
        if (findSubtype != null) {
            return findSubtype.getInt();
        }
        return SliceViewUtil.getColorAttr(getContext(), 16843829);
    }

    public final void logSliceMetricsVisibilityChange(boolean z) {
        SliceMetricsWrapper sliceMetricsWrapper = this.mCurrentSliceMetrics;
        if (sliceMetricsWrapper != null) {
            if (z && !this.mCurrentSliceLoggedVisible) {
                sliceMetricsWrapper.mSliceMetrics.logVisible();
                this.mCurrentSliceLoggedVisible = true;
            }
            if (!z && this.mCurrentSliceLoggedVisible) {
                SliceMetricsWrapper sliceMetricsWrapper2 = this.mCurrentSliceMetrics;
                Objects.requireNonNull(sliceMetricsWrapper2);
                sliceMetricsWrapper2.mSliceMetrics.logHidden();
                this.mCurrentSliceLoggedVisible = false;
            }
        }
    }

    public final void onChanged(Object obj) {
        boolean z;
        SliceMetadata sliceMetadata;
        ListContent listContent;
        RowContent rowContent;
        View view;
        Slice slice = (Slice) obj;
        View findFocus = findFocus();
        if (findFocus != null) {
            new LocationBasedViewTracker(this, findFocus, LocationBasedViewTracker.INPUT_FOCUS);
        }
        boolean z2 = false;
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            ArrayList arrayList = new ArrayList();
            addFocusables(arrayList, 2, 0);
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    view = null;
                    break;
                }
                view = (View) it.next();
                if (view.isAccessibilityFocused()) {
                    break;
                }
            }
            if (view != null) {
                new LocationBasedViewTracker(this, view, LocationBasedViewTracker.A11Y_FOCUS);
            }
        }
        if (slice == null || slice.getUri() == null) {
            logSliceMetricsVisibilityChange(false);
            this.mCurrentSliceMetrics = null;
        } else {
            Slice slice2 = this.mCurrentSlice;
            if (slice2 == null || !slice2.getUri().equals(slice.getUri())) {
                logSliceMetricsVisibilityChange(false);
                this.mCurrentSliceMetrics = new SliceMetricsWrapper(getContext(), slice.getUri());
            }
        }
        if (slice == null || this.mCurrentSlice == null || !slice.getUri().equals(this.mCurrentSlice.getUri())) {
            z = false;
        } else {
            z = true;
        }
        SliceMetadata sliceMetadata2 = this.mSliceMetadata;
        this.mCurrentSlice = slice;
        if (slice != null) {
            sliceMetadata = new SliceMetadata(getContext(), this.mCurrentSlice);
        } else {
            sliceMetadata = null;
        }
        this.mSliceMetadata = sliceMetadata;
        if (!z) {
            this.mCurrentView.resetView();
        } else if (sliceMetadata2.getLoadingState() == 2 && sliceMetadata.getLoadingState() == 0) {
            return;
        }
        SliceMetadata sliceMetadata3 = this.mSliceMetadata;
        if (sliceMetadata3 != null) {
            listContent = sliceMetadata3.mListContent;
        } else {
            listContent = null;
        }
        this.mListContent = listContent;
        if (this.mShowTitleItems) {
            this.mShowTitleItems = true;
            if (!(listContent == null || (rowContent = listContent.mHeaderContent) == null)) {
                rowContent.mShowTitleItems = true;
            }
        }
        if (this.mShowHeaderDivider) {
            this.mShowHeaderDivider = true;
            if (!(listContent == null || listContent.mHeaderContent == null || listContent.mRowItems.size() <= 1)) {
                RowContent rowContent2 = listContent.mHeaderContent;
                Objects.requireNonNull(rowContent2);
                rowContent2.mShowBottomDivider = true;
            }
        }
        if (this.mShowActionDividers) {
            this.mShowActionDividers = true;
            ListContent listContent2 = this.mListContent;
            if (listContent2 != null) {
                Iterator<SliceContent> it2 = listContent2.mRowItems.iterator();
                while (it2.hasNext()) {
                    SliceContent next = it2.next();
                    if (next instanceof RowContent) {
                        RowContent rowContent3 = (RowContent) next;
                        Objects.requireNonNull(rowContent3);
                        rowContent3.mShowActionDivider = true;
                    }
                }
            }
        }
        ListContent listContent3 = this.mListContent;
        if (listContent3 == null || !listContent3.isValid()) {
            this.mActions = null;
            this.mCurrentView.resetView();
            updateActions();
            return;
        }
        this.mCurrentView.setLoadingActions((Set<SliceItem>) null);
        SliceMetadata sliceMetadata4 = this.mSliceMetadata;
        Objects.requireNonNull(sliceMetadata4);
        this.mActions = sliceMetadata4.mSliceActions;
        TemplateView templateView = this.mCurrentView;
        SliceMetadata sliceMetadata5 = this.mSliceMetadata;
        Objects.requireNonNull(sliceMetadata5);
        templateView.setLastUpdated(sliceMetadata5.mLastUpdated);
        TemplateView templateView2 = this.mCurrentView;
        if (this.mShowLastUpdated && this.mSliceMetadata.isExpired()) {
            z2 = true;
        }
        templateView2.setShowLastUpdated(z2);
        TemplateView templateView3 = this.mCurrentView;
        SliceMetadata sliceMetadata6 = this.mSliceMetadata;
        Objects.requireNonNull(sliceMetadata6);
        Slice slice3 = sliceMetadata6.mSlice;
        Objects.requireNonNull(slice3);
        templateView3.setAllowTwoLines(Okio.contains(slice3.mHints, "permission_request"));
        this.mCurrentView.setTint(getTintColor());
        if (this.mListContent.getLayoutDir() != -1) {
            this.mCurrentView.setLayoutDirection(this.mListContent.getLayoutDir());
        } else {
            this.mCurrentView.setLayoutDirection(2);
        }
        TemplateView templateView4 = this.mCurrentView;
        ListContent listContent4 = this.mListContent;
        Objects.requireNonNull(templateView4);
        templateView4.mListContent = listContent4;
        listContent4.getHeight(templateView4.mSliceStyle, templateView4.mViewPolicy);
        templateView4.updateDisplayedItems();
        updateActions();
        logSliceMetricsVisibilityChange(true);
        refreshLastUpdatedLabel(true);
    }

    public final void onClick(View view) {
        boolean z;
        ListContent listContent = this.mListContent;
        if (listContent == null || listContent.getShortcut(getContext()) == null) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(this);
                return;
            }
            return;
        }
        try {
            SliceActionImpl sliceActionImpl = (SliceActionImpl) this.mListContent.getShortcut(getContext());
            Objects.requireNonNull(sliceActionImpl);
            SliceItem sliceItem = sliceActionImpl.mActionItem;
            if (sliceItem == null || !sliceItem.fireActionInternal(getContext(), (Intent) null)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                TemplateView templateView = this.mCurrentView;
                SliceItem sliceItem2 = sliceActionImpl.mSliceItem;
                Objects.requireNonNull(templateView);
                templateView.mAdapter.onSliceActionLoading(sliceItem2, 0);
            }
        } catch (PendingIntent.CanceledException e) {
            Log.e("SliceView", "PendingIntent for slice cannot be sent", e);
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if ((this.mLongClickListener == null || !handleTouchForLongpress(motionEvent)) && !super.onInterceptTouchEvent(motionEvent)) {
            return false;
        }
        return true;
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TemplateView templateView = this.mCurrentView;
        templateView.layout(0, 0, templateView.getMeasuredWidth(), templateView.getMeasuredHeight());
        if (this.mActionRow.getVisibility() != 8) {
            int measuredHeight = templateView.getMeasuredHeight();
            ActionRow actionRow = this.mActionRow;
            actionRow.layout(0, measuredHeight, actionRow.getMeasuredWidth(), this.mActionRow.getMeasuredHeight() + measuredHeight);
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if ((this.mLongClickListener == null || !handleTouchForLongpress(motionEvent)) && !super.onTouchEvent(motionEvent)) {
            return false;
        }
        return true;
    }

    public final void refreshLastUpdatedLabel(boolean z) {
        SliceMetadata sliceMetadata;
        boolean z2;
        if (this.mShowLastUpdated && (sliceMetadata = this.mSliceMetadata) != null) {
            if (sliceMetadata.mExpiry == -1) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                return;
            }
            if (z) {
                Handler handler = this.mHandler;
                C03692 r1 = this.mRefreshLastUpdated;
                long j = 60000;
                if (!sliceMetadata.isExpired()) {
                    SliceMetadata sliceMetadata2 = this.mSliceMetadata;
                    Objects.requireNonNull(sliceMetadata2);
                    long currentTimeMillis = System.currentTimeMillis();
                    long j2 = sliceMetadata2.mExpiry;
                    long j3 = 0;
                    if (!(j2 == 0 || j2 == -1 || currentTimeMillis > j2)) {
                        j3 = j2 - currentTimeMillis;
                    }
                    j = 60000 + j3;
                }
                handler.postDelayed(r1, j);
                return;
            }
            this.mHandler.removeCallbacks(this.mRefreshLastUpdated);
        }
    }

    public final void updateActions() {
        if (this.mActions == null) {
            this.mActionRow.setVisibility(8);
            this.mCurrentView.setSliceActions((List<SliceAction>) null);
            this.mCurrentView.setInsets(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
            return;
        }
        ArrayList arrayList = new ArrayList(this.mActions);
        Collections.sort(arrayList, SLICE_ACTION_PRIORITY_COMPARATOR);
        this.mCurrentView.setSliceActions(arrayList);
        this.mCurrentView.setInsets(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
        this.mActionRow.setVisibility(8);
    }

    public SliceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1777R.attr.sliceViewStyle);
        SliceStyle sliceStyle = new SliceStyle(context, attributeSet);
        this.mSliceStyle = sliceStyle;
        this.mThemeTintColor = sliceStyle.mTintColor;
        getContext().getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_shortcut_size);
        this.mMinTemplateHeight = getContext().getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_min_height);
        this.mLargeHeight = getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_large_height);
        this.mActionRowHeight = getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_action_row_height);
        this.mViewPolicy = new SliceViewPolicy();
        TemplateView templateView = new TemplateView(getContext());
        this.mCurrentView = templateView;
        templateView.setPolicy(this.mViewPolicy);
        addView(this.mCurrentView, new ViewGroup.LayoutParams(-1, -1));
        TemplateView templateView2 = this.mCurrentView;
        Objects.requireNonNull(templateView2);
        templateView2.mObserver = null;
        SliceAdapter sliceAdapter = templateView2.mAdapter;
        if (sliceAdapter != null) {
            sliceAdapter.mSliceObserver = null;
        }
        TemplateView templateView3 = this.mCurrentView;
        SliceStyle sliceStyle2 = this.mSliceStyle;
        templateView3.setStyle(sliceStyle2, sliceStyle2.getRowStyle((SliceItem) null));
        this.mCurrentView.setTint(getTintColor());
        ListContent listContent = this.mListContent;
        if (listContent == null || listContent.getLayoutDir() == -1) {
            this.mCurrentView.setLayoutDirection(2);
        } else {
            this.mCurrentView.setLayoutDirection(this.mListContent.getLayoutDir());
        }
        ActionRow actionRow = new ActionRow(getContext());
        this.mActionRow = actionRow;
        actionRow.setBackground(new ColorDrawable(-1118482));
        addView(this.mActionRow, new ViewGroup.LayoutParams(-1, -1));
        updateActions();
        int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mTouchSlopSquared = scaledTouchSlop * scaledTouchSlop;
        this.mHandler = new Handler();
        setClipToPadding(false);
        super.setOnClickListener(this);
    }

    public final boolean handleTouchForLongpress(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int rawX = ((int) motionEvent.getRawX()) - this.mDownX;
                    int rawY = ((int) motionEvent.getRawY()) - this.mDownY;
                    if ((rawY * rawY) + (rawX * rawX) > this.mTouchSlopSquared) {
                        this.mPressing = false;
                        this.mHandler.removeCallbacks(this.mLongpressCheck);
                    }
                    return this.mInLongpress;
                } else if (actionMasked != 3) {
                    return false;
                }
            }
            boolean z = this.mInLongpress;
            this.mPressing = false;
            this.mInLongpress = false;
            this.mHandler.removeCallbacks(this.mLongpressCheck);
            return z;
        }
        this.mHandler.removeCallbacks(this.mLongpressCheck);
        this.mDownX = (int) motionEvent.getRawX();
        this.mDownY = (int) motionEvent.getRawY();
        this.mPressing = true;
        this.mInLongpress = false;
        this.mHandler.postDelayed(this.mLongpressCheck, (long) ViewConfiguration.getLongPressTimeout());
        return false;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isShown()) {
            logSliceMetricsVisibilityChange(true);
            refreshLastUpdatedLabel(true);
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        logSliceMetricsVisibilityChange(false);
        refreshLastUpdatedLabel(false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r8, int r9) {
        /*
            r7 = this;
            int r8 = android.view.View.MeasureSpec.getSize(r8)
            androidx.slice.widget.SliceViewPolicy r0 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r0)
            androidx.slice.widget.ActionRow r0 = r7.mActionRow
            int r0 = r0.getVisibility()
            r1 = 8
            r2 = 0
            if (r0 == r1) goto L_0x0017
            int r0 = r7.mActionRowHeight
            goto L_0x0018
        L_0x0017:
            r0 = r2
        L_0x0018:
            int r1 = android.view.View.MeasureSpec.getSize(r9)
            int r9 = android.view.View.MeasureSpec.getMode(r9)
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            if (r3 == 0) goto L_0x002b
            int r3 = r3.height
            r4 = -2
            if (r3 == r4) goto L_0x002d
        L_0x002b:
            if (r9 != 0) goto L_0x002f
        L_0x002d:
            r3 = -1
            goto L_0x0030
        L_0x002f:
            r3 = r1
        L_0x0030:
            androidx.slice.widget.ListContent r4 = r7.mListContent
            if (r4 == 0) goto L_0x009f
            boolean r4 = r4.isValid()
            if (r4 == 0) goto L_0x009f
            androidx.slice.widget.SliceViewPolicy r4 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r4)
            if (r3 <= 0) goto L_0x0068
            androidx.slice.widget.SliceStyle r4 = r7.mSliceStyle
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mRowMaxHeight
            if (r3 >= r4) goto L_0x0068
            int r4 = r7.mMinTemplateHeight
            if (r3 > r4) goto L_0x004f
            r3 = r4
        L_0x004f:
            androidx.slice.widget.SliceViewPolicy r4 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r4)
            int r5 = r4.mMaxSmallHeight
            if (r5 == r3) goto L_0x0080
            r4.mMaxSmallHeight = r3
            androidx.slice.widget.SliceViewPolicy$PolicyChangeListener r4 = r4.mListener
            if (r4 == 0) goto L_0x0080
            androidx.slice.widget.TemplateView r4 = (androidx.slice.widget.TemplateView) r4
            androidx.slice.widget.SliceAdapter r4 = r4.mAdapter
            if (r4 == 0) goto L_0x0080
            r4.notifyHeaderChanged()
            goto L_0x0080
        L_0x0068:
            androidx.slice.widget.SliceViewPolicy r4 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r4)
            int r5 = r4.mMaxSmallHeight
            if (r5 == 0) goto L_0x0080
            r4.mMaxSmallHeight = r2
            androidx.slice.widget.SliceViewPolicy$PolicyChangeListener r4 = r4.mListener
            if (r4 == 0) goto L_0x0080
            androidx.slice.widget.TemplateView r4 = (androidx.slice.widget.TemplateView) r4
            androidx.slice.widget.SliceAdapter r4 = r4.mAdapter
            if (r4 == 0) goto L_0x0080
            r4.notifyHeaderChanged()
        L_0x0080:
            androidx.slice.widget.SliceViewPolicy r4 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r4)
            int r5 = r4.mMaxHeight
            if (r3 == r5) goto L_0x009f
            r4.mMaxHeight = r3
            androidx.slice.widget.SliceViewPolicy$PolicyChangeListener r3 = r4.mListener
            if (r3 == 0) goto L_0x009f
            androidx.slice.widget.TemplateView r3 = (androidx.slice.widget.TemplateView) r3
            androidx.slice.widget.ListContent r4 = r3.mListContent
            if (r4 == 0) goto L_0x009f
            androidx.slice.widget.SliceStyle r5 = r3.mSliceStyle
            androidx.slice.widget.SliceViewPolicy r6 = r3.mViewPolicy
            r4.getHeight(r5, r6)
            r3.updateDisplayedItems()
        L_0x009f:
            int r3 = r7.getPaddingTop()
            int r1 = r1 - r3
            int r3 = r7.getPaddingBottom()
            int r1 = r1 - r3
            r3 = 1073741824(0x40000000, float:2.0)
            if (r9 == r3) goto L_0x00eb
            androidx.slice.widget.ListContent r4 = r7.mListContent
            if (r4 == 0) goto L_0x00ea
            boolean r4 = r4.isValid()
            if (r4 != 0) goto L_0x00b8
            goto L_0x00ea
        L_0x00b8:
            androidx.slice.widget.SliceViewPolicy r4 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r4)
            androidx.slice.widget.ListContent r4 = r7.mListContent
            androidx.slice.widget.SliceStyle r5 = r7.mSliceStyle
            androidx.slice.widget.SliceViewPolicy r6 = r7.mViewPolicy
            int r4 = r4.getHeight(r5, r6)
            int r4 = r4 + r0
            if (r1 > r4) goto L_0x00e8
            if (r9 != 0) goto L_0x00cd
            goto L_0x00e8
        L_0x00cd:
            androidx.slice.widget.SliceStyle r9 = r7.mSliceStyle
            java.util.Objects.requireNonNull(r9)
            boolean r9 = r9.mExpandToAvailableHeight
            if (r9 == 0) goto L_0x00d7
            goto L_0x00eb
        L_0x00d7:
            androidx.slice.widget.SliceViewPolicy r9 = r7.mViewPolicy
            java.util.Objects.requireNonNull(r9)
            int r9 = r7.mLargeHeight
            int r9 = r9 + r0
            if (r1 < r9) goto L_0x00e3
        L_0x00e1:
            r1 = r9
            goto L_0x00eb
        L_0x00e3:
            int r9 = r7.mMinTemplateHeight
            if (r1 > r9) goto L_0x00eb
            goto L_0x00e1
        L_0x00e8:
            r1 = r4
            goto L_0x00eb
        L_0x00ea:
            r1 = r0
        L_0x00eb:
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r3)
            if (r0 <= 0) goto L_0x00f7
            int r4 = r7.getPaddingBottom()
            int r4 = r4 + r0
            goto L_0x00f8
        L_0x00f7:
            r4 = r2
        L_0x00f8:
            androidx.slice.widget.ActionRow r5 = r7.mActionRow
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r3)
            r5.measure(r9, r4)
            int r4 = r7.getPaddingTop()
            int r4 = r4 + r1
            if (r0 <= 0) goto L_0x0109
            goto L_0x010d
        L_0x0109:
            int r2 = r7.getPaddingBottom()
        L_0x010d:
            int r4 = r4 + r2
            androidx.slice.widget.TemplateView r0 = r7.mCurrentView
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r3)
            r0.measure(r9, r1)
            androidx.slice.widget.TemplateView r9 = r7.mCurrentView
            int r9 = r9.getMeasuredHeight()
            androidx.slice.widget.ActionRow r0 = r7.mActionRow
            int r0 = r0.getMeasuredHeight()
            int r0 = r0 + r9
            r7.setMeasuredDimension(r8, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.SliceView.onMeasure(int, int):void");
    }

    public final void onVisibilityChanged(View view, int i) {
        boolean z;
        super.onVisibilityChanged(view, i);
        if (isAttachedToWindow()) {
            boolean z2 = true;
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            logSliceMetricsVisibilityChange(z);
            if (i != 0) {
                z2 = false;
            }
            refreshLastUpdatedLabel(z2);
        }
    }

    public final void onWindowVisibilityChanged(int i) {
        boolean z;
        super.onWindowVisibilityChanged(i);
        boolean z2 = true;
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        logSliceMetricsVisibilityChange(z);
        if (i != 0) {
            z2 = false;
        }
        refreshLastUpdatedLabel(z2);
    }

    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
        this.mLongClickListener = onLongClickListener;
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setSliceViewPolicy(SliceViewPolicy sliceViewPolicy) {
        this.mViewPolicy = sliceViewPolicy;
    }
}
