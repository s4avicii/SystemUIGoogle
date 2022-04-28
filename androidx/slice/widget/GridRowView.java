package androidx.slice.widget;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.SliceView;
import com.android.p012wm.shell.C1777R;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class GridRowView extends SliceChildView implements View.OnClickListener, View.OnTouchListener {
    public final View mForeground;
    public GridContent mGridContent;
    public final int mGutter;
    public final int mIconSize;
    public final int mLargeImageHeight;
    public final int[] mLoc;
    public boolean mMaxCellUpdateScheduled;
    public int mMaxCells;
    public final C03582 mMaxCellsUpdater;
    public int mRowCount;
    public int mRowIndex;
    public final int mSmallImageMinWidth;
    public final int mSmallImageSize;
    public final int mTextPadding;
    public final LinearLayout mViewContainer;

    public class DateSetListener implements DatePickerDialog.OnDateSetListener {
        public final SliceItem mActionItem;
        public final int mRowIndex;

        public DateSetListener(SliceItem sliceItem, int i) {
            this.mActionItem = sliceItem;
            this.mRowIndex = i;
        }

        public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Calendar instance = Calendar.getInstance();
            instance.set(i, i2, i3);
            Date time = instance.getTime();
            SliceItem sliceItem = this.mActionItem;
            if (sliceItem != null) {
                try {
                    sliceItem.fireActionInternal(GridRowView.this.getContext(), new Intent().addFlags(268435456).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    SliceView.OnSliceActionListener onSliceActionListener = GridRowView.this.mObserver;
                    if (onSliceActionListener != null) {
                        onSliceActionListener.onSliceAction();
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    public class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        public final SliceItem mActionItem;
        public final int mRowIndex;

        public TimeSetListener(SliceItem sliceItem, int i) {
            this.mActionItem = sliceItem;
            this.mRowIndex = i;
        }

        public final void onTimeSet(TimePicker timePicker, int i, int i2) {
            Date time = Calendar.getInstance().getTime();
            time.setHours(i);
            time.setMinutes(i2);
            SliceItem sliceItem = this.mActionItem;
            if (sliceItem != null) {
                try {
                    sliceItem.fireActionInternal(GridRowView.this.getContext(), new Intent().addFlags(268435456).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    SliceView.OnSliceActionListener onSliceActionListener = GridRowView.this.mObserver;
                    if (onSliceActionListener != null) {
                        onSliceActionListener.onSliceAction();
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    public GridRowView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int determinePadding(SliceItem sliceItem) {
        SliceStyle sliceStyle;
        if (sliceItem == null) {
            return 0;
        }
        if ("image".equals(sliceItem.mFormat)) {
            return this.mTextPadding;
        }
        if ((!"text".equals(sliceItem.mFormat) && !"long".equals(sliceItem.mFormat)) || (sliceStyle = this.mSliceStyle) == null) {
            return 0;
        }
        Objects.requireNonNull(sliceStyle);
        return sliceStyle.mVerticalGridTextPadding;
    }

    public GridRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLoc = new int[2];
        this.mMaxCells = -1;
        this.mMaxCellsUpdater = new ViewTreeObserver.OnPreDrawListener() {
            public final boolean onPreDraw() {
                GridRowView gridRowView = GridRowView.this;
                gridRowView.mMaxCells = gridRowView.getMaxCells();
                GridRowView.this.populateViews();
                GridRowView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                GridRowView.this.mMaxCellUpdateScheduled = false;
                return true;
            }
        };
        Resources resources = getContext().getResources();
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.mViewContainer = linearLayout;
        linearLayout.setOrientation(0);
        addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
        linearLayout.setGravity(16);
        this.mIconSize = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_icon_size);
        this.mSmallImageSize = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_small_image_size);
        this.mLargeImageHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_image_only_height);
        this.mSmallImageMinWidth = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_image_min_width);
        this.mGutter = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_gutter);
        this.mTextPadding = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_text_padding);
        View view = new View(getContext());
        this.mForeground = view;
        addView(view, new FrameLayout.LayoutParams(-1, -1));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.CharSequence} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x022c  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0231  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0236  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addCell(androidx.slice.widget.GridContent.CellContent r24, int r25, int r26) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            android.widget.LinearLayout r4 = new android.widget.LinearLayout
            android.content.Context r5 = r23.getContext()
            r4.<init>(r5)
            r5 = 1
            r4.setOrientation(r5)
            r4.setGravity(r5)
            java.util.Objects.requireNonNull(r24)
            java.util.ArrayList<androidx.slice.SliceItem> r6 = r1.mCellItems
            androidx.slice.SliceItem r7 = r1.mContentIntent
            androidx.slice.SliceItem r8 = r1.mPicker
            androidx.slice.SliceItem r9 = r1.mToggleItem
            int r10 = r6.size()
            if (r10 != r5) goto L_0x002b
            r10 = r5
            goto L_0x002c
        L_0x002b:
            r10 = 0
        L_0x002c:
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
        L_0x0033:
            int r5 = r6.size()
            r11 = 2
            if (r13 >= r5) goto L_0x0256
            java.lang.Object r5 = r6.get(r13)
            androidx.slice.SliceItem r5 = (androidx.slice.SliceItem) r5
            java.util.Objects.requireNonNull(r5)
            java.lang.String r12 = r5.mFormat
            r20 = r6
            int r6 = r0.determinePadding(r14)
            r21 = r7
            java.lang.String r7 = "large"
            if (r15 >= r11) goto L_0x00f1
            java.lang.String r11 = "text"
            boolean r22 = r11.equals(r12)
            java.lang.String r2 = "long"
            if (r22 != 0) goto L_0x0062
            boolean r12 = r2.equals(r12)
            if (r12 == 0) goto L_0x00f1
        L_0x0062:
            java.lang.String r12 = r5.mFormat
            boolean r11 = r11.equals(r12)
            if (r11 != 0) goto L_0x0075
            boolean r11 = r2.equals(r12)
            if (r11 != 0) goto L_0x0075
            r18 = r9
            r2 = 0
            goto L_0x00e4
        L_0x0075:
            java.lang.String r11 = "title"
            java.lang.String[] r7 = new java.lang.String[]{r7, r11}
            boolean r7 = androidx.slice.core.SliceQuery.hasAnyHints(r5, r7)
            android.content.Context r11 = r23.getContext()
            android.view.LayoutInflater r11 = android.view.LayoutInflater.from(r11)
            if (r7 == 0) goto L_0x008e
            r19 = 2131623981(0x7f0e002d, float:1.8875129E38)
            goto L_0x0091
        L_0x008e:
            r19 = 2131623977(0x7f0e0029, float:1.887512E38)
        L_0x0091:
            r18 = r9
            r3 = r19
            r9 = 0
            android.view.View r3 = r11.inflate(r3, r9)
            android.widget.TextView r3 = (android.widget.TextView) r3
            androidx.slice.widget.SliceStyle r11 = r0.mSliceStyle
            if (r11 == 0) goto L_0x00c2
            androidx.slice.widget.RowStyle r9 = r0.mRowStyle
            if (r9 == 0) goto L_0x00c2
            if (r7 == 0) goto L_0x00a9
            int r9 = r11.mGridTitleSize
            goto L_0x00ab
        L_0x00a9:
            int r9 = r11.mGridSubtitleSize
        L_0x00ab:
            float r9 = (float) r9
            r11 = 0
            r3.setTextSize(r11, r9)
            if (r7 == 0) goto L_0x00b9
            androidx.slice.widget.RowStyle r7 = r0.mRowStyle
            int r7 = r7.getTitleColor()
            goto L_0x00bf
        L_0x00b9:
            androidx.slice.widget.RowStyle r7 = r0.mRowStyle
            int r7 = r7.getSubtitleColor()
        L_0x00bf:
            r3.setTextColor(r7)
        L_0x00c2:
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L_0x00d5
            android.content.Context r2 = r23.getContext()
            long r11 = r5.getLong()
            java.lang.CharSequence r2 = androidx.slice.widget.SliceViewUtil.getTimestampString(r2, r11)
            goto L_0x00d9
        L_0x00d5:
            java.lang.CharSequence r2 = r5.getSanitizedText()
        L_0x00d9:
            r3.setText(r2)
            r4.addView(r3)
            r2 = 0
            r3.setPadding(r2, r6, r2, r2)
            r2 = 1
        L_0x00e4:
            if (r2 == 0) goto L_0x00ed
            int r15 = r15 + 1
            r14 = r5
            r16 = 1
            goto L_0x0246
        L_0x00ed:
            r11 = r17
            goto L_0x0240
        L_0x00f1:
            r18 = r9
            r11 = r17
            r2 = 1
            if (r11 >= r2) goto L_0x0240
            java.lang.String r2 = r5.mFormat
            java.lang.String r3 = "image"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0240
            androidx.slice.SliceItem r2 = r1.mOverlayItem
            int r6 = r0.mTintColor
            java.lang.String r9 = r5.mFormat
            androidx.slice.widget.SliceStyle r12 = r0.mSliceStyle
            if (r12 == 0) goto L_0x011b
            float r12 = r12.mImageCornerRadius
            r17 = 0
            int r12 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1))
            if (r12 <= 0) goto L_0x0116
            r12 = 1
            goto L_0x0117
        L_0x0116:
            r12 = 0
        L_0x0117:
            if (r12 == 0) goto L_0x011b
            r12 = 1
            goto L_0x011c
        L_0x011b:
            r12 = 0
        L_0x011c:
            boolean r3 = r3.equals(r9)
            if (r3 == 0) goto L_0x0231
            java.lang.Object r3 = r5.mObj
            androidx.core.graphics.drawable.IconCompat r3 = (androidx.core.graphics.drawable.IconCompat) r3
            if (r3 != 0) goto L_0x012a
            goto L_0x0231
        L_0x012a:
            android.content.Context r9 = r23.getContext()
            android.graphics.drawable.Drawable r3 = r3.loadDrawable(r9)
            if (r3 != 0) goto L_0x0136
            goto L_0x0231
        L_0x0136:
            android.widget.ImageView r9 = new android.widget.ImageView
            r17 = r15
            android.content.Context r15 = r23.getContext()
            r9.<init>(r15)
            if (r12 == 0) goto L_0x0153
            androidx.slice.CornerDrawable r15 = new androidx.slice.CornerDrawable
            androidx.slice.widget.SliceStyle r1 = r0.mSliceStyle
            java.util.Objects.requireNonNull(r1)
            float r1 = r1.mImageCornerRadius
            r15.<init>(r3, r1)
            r9.setImageDrawable(r15)
            goto L_0x0156
        L_0x0153:
            r9.setImageDrawable(r3)
        L_0x0156:
            java.lang.String r1 = "raw"
            boolean r1 = r5.hasHint(r1)
            java.lang.String r3 = "no_tint"
            if (r1 == 0) goto L_0x0184
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_INSIDE
            r9.setScaleType(r1)
            android.widget.LinearLayout$LayoutParams r1 = new android.widget.LinearLayout$LayoutParams
            androidx.slice.widget.GridContent r7 = r0.mGridContent
            android.content.Context r12 = r23.getContext()
            android.graphics.Point r7 = r7.getFirstImageSize(r12)
            int r7 = r7.x
            androidx.slice.widget.GridContent r12 = r0.mGridContent
            android.content.Context r15 = r23.getContext()
            android.graphics.Point r12 = r12.getFirstImageSize(r15)
            int r12 = r12.y
            r1.<init>(r7, r12)
            goto L_0x01be
        L_0x0184:
            boolean r1 = r5.hasHint(r7)
            if (r1 == 0) goto L_0x01a2
            if (r12 == 0) goto L_0x018f
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_XY
            goto L_0x0191
        L_0x018f:
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_CROP
        L_0x0191:
            r9.setScaleType(r1)
            if (r10 == 0) goto L_0x0198
            r1 = -1
            goto L_0x019a
        L_0x0198:
            int r1 = r0.mLargeImageHeight
        L_0x019a:
            android.widget.LinearLayout$LayoutParams r7 = new android.widget.LinearLayout$LayoutParams
            r12 = -1
            r7.<init>(r12, r1)
            r1 = r12
            goto L_0x01c0
        L_0x01a2:
            boolean r1 = r5.hasHint(r3)
            r7 = 1
            r1 = r1 ^ r7
            if (r1 != 0) goto L_0x01ad
            int r7 = r0.mSmallImageSize
            goto L_0x01af
        L_0x01ad:
            int r7 = r0.mIconSize
        L_0x01af:
            if (r1 == 0) goto L_0x01b4
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_INSIDE
            goto L_0x01b6
        L_0x01b4:
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_CROP
        L_0x01b6:
            r9.setScaleType(r1)
            android.widget.LinearLayout$LayoutParams r1 = new android.widget.LinearLayout$LayoutParams
            r1.<init>(r7, r7)
        L_0x01be:
            r7 = r1
            r1 = -1
        L_0x01c0:
            if (r6 == r1) goto L_0x01cb
            boolean r1 = r5.hasHint(r3)
            if (r1 != 0) goto L_0x01cb
            r9.setColorFilter(r6)
        L_0x01cb:
            if (r2 == 0) goto L_0x022c
            android.widget.LinearLayout r1 = r0.mViewContainer
            int r1 = r1.getChildCount()
            int r3 = r0.mMaxCells
            r6 = 1
            int r3 = r3 - r6
            if (r1 != r3) goto L_0x01da
            goto L_0x022c
        L_0x01da:
            android.content.Context r1 = r23.getContext()
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
            r3 = 2131623966(0x7f0e001e, float:1.8875098E38)
            r6 = 0
            android.view.View r1 = r1.inflate(r3, r4, r6)
            android.widget.FrameLayout r1 = (android.widget.FrameLayout) r1
            android.widget.FrameLayout$LayoutParams r3 = new android.widget.FrameLayout$LayoutParams
            r12 = -2
            r3.<init>(r12, r12)
            r1.addView(r9, r6, r3)
            r3 = 2131429036(0x7f0b06ac, float:1.8479733E38)
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            java.lang.Object r2 = r2.mObj
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r3.setText(r2)
            r2 = 2131429055(0x7f0b06bf, float:1.8479772E38)
            android.view.View r2 = r1.findViewById(r2)
            androidx.slice.CornerDrawable r3 = new androidx.slice.CornerDrawable
            android.content.Context r6 = r23.getContext()
            r9 = 2131231565(0x7f08034d, float:1.8079215E38)
            java.lang.Object r12 = androidx.core.content.ContextCompat.sLock
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r9)
            androidx.slice.widget.SliceStyle r9 = r0.mSliceStyle
            java.util.Objects.requireNonNull(r9)
            float r9 = r9.mImageCornerRadius
            r3.<init>(r6, r9)
            r2.setBackground(r3)
            r4.addView(r1, r7)
            goto L_0x022f
        L_0x022c:
            r4.addView(r9, r7)
        L_0x022f:
            r1 = 1
            goto L_0x0234
        L_0x0231:
            r17 = r15
            r1 = 0
        L_0x0234:
            if (r1 == 0) goto L_0x0242
            int r1 = r11 + 1
            r14 = r5
            r15 = r17
            r16 = 1
            r17 = r1
            goto L_0x0246
        L_0x0240:
            r17 = r15
        L_0x0242:
            r15 = r17
            r17 = r11
        L_0x0246:
            int r13 = r13 + 1
            r1 = r24
            r2 = r25
            r3 = r26
            r9 = r18
            r6 = r20
            r7 = r21
            goto L_0x0033
        L_0x0256:
            r21 = r7
            r18 = r9
            if (r8 == 0) goto L_0x0284
            java.lang.String r1 = r8.mSubType
            java.lang.String r2 = "date_picker"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0270
            int r1 = r0.determinePadding(r14)
            r2 = 1
            boolean r16 = r0.addPickerItem(r8, r4, r1, r2)
            goto L_0x0284
        L_0x0270:
            java.lang.String r1 = r8.mSubType
            java.lang.String r2 = "time_picker"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0284
            int r1 = r0.determinePadding(r14)
            r2 = 0
            boolean r16 = r0.addPickerItem(r8, r4, r1, r2)
        L_0x0284:
            if (r18 == 0) goto L_0x0297
            androidx.slice.widget.SliceActionView r1 = new androidx.slice.widget.SliceActionView
            android.content.Context r2 = r23.getContext()
            androidx.slice.widget.RowStyle r3 = r0.mRowStyle
            r1.<init>(r2, r3)
            r4.addView(r1)
            r5 = r1
            r2 = 1
            goto L_0x029a
        L_0x0297:
            r2 = r16
            r5 = 0
        L_0x029a:
            if (r2 == 0) goto L_0x0311
            r1 = r24
            androidx.slice.SliceItem r1 = r1.mContentDescr
            if (r1 == 0) goto L_0x02a8
            java.lang.Object r1 = r1.mObj
            r12 = r1
            java.lang.CharSequence r12 = (java.lang.CharSequence) r12
            goto L_0x02a9
        L_0x02a8:
            r12 = 0
        L_0x02a9:
            if (r12 == 0) goto L_0x02ae
            r4.setContentDescription(r12)
        L_0x02ae:
            android.widget.LinearLayout r1 = r0.mViewContainer
            android.widget.LinearLayout$LayoutParams r2 = new android.widget.LinearLayout$LayoutParams
            r3 = 1065353216(0x3f800000, float:1.0)
            r6 = -2
            r7 = 0
            r2.<init>(r7, r6, r3)
            r1.addView(r4, r2)
            r1 = r26
            int r2 = r1 + -1
            r3 = r25
            if (r3 == r2) goto L_0x02d2
            android.view.ViewGroup$LayoutParams r2 = r4.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            int r6 = r0.mGutter
            r2.setMarginEnd(r6)
            r4.setLayoutParams(r2)
        L_0x02d2:
            if (r21 == 0) goto L_0x02ef
            androidx.slice.widget.EventInfo r2 = new androidx.slice.widget.EventInfo
            int r6 = r0.mRowIndex
            r7 = 1
            r2.<init>(r7, r7, r6)
            r2.actionPosition = r11
            r2.actionIndex = r3
            r2.actionCount = r1
            android.util.Pair r6 = new android.util.Pair
            r7 = r21
            r6.<init>(r7, r2)
            r4.setTag(r6)
            r0.makeClickable(r4)
        L_0x02ef:
            if (r18 == 0) goto L_0x0311
            androidx.slice.widget.EventInfo r2 = new androidx.slice.widget.EventInfo
            r4 = 3
            int r6 = r0.mRowIndex
            r7 = 0
            r2.<init>(r7, r4, r6)
            androidx.slice.core.SliceActionImpl r6 = new androidx.slice.core.SliceActionImpl
            r4 = r18
            r6.<init>(r4)
            androidx.slice.widget.SliceView$OnSliceActionListener r8 = r0.mObserver
            int r9 = r0.mTintColor
            androidx.slice.widget.SliceActionView$SliceActionLoadingListener r10 = r0.mLoadingListener
            r7 = r2
            r5.setAction(r6, r7, r8, r9, r10)
            r2.actionPosition = r11
            r2.actionIndex = r3
            r2.actionCount = r1
        L_0x0311:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.GridRowView.addCell(androidx.slice.widget.GridContent$CellContent, int, int):void");
    }

    public final boolean addPickerItem(SliceItem sliceItem, LinearLayout linearLayout, int i, boolean z) {
        SliceItem findSubtype = SliceQuery.findSubtype(sliceItem, "long", "millis");
        if (findSubtype == null) {
            return false;
        }
        long j = findSubtype.getLong();
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(C1777R.layout.abc_slice_title, (ViewGroup) null);
        SliceStyle sliceStyle = this.mSliceStyle;
        if (sliceStyle != null) {
            textView.setTextSize(0, (float) sliceStyle.mGridTitleSize);
            SliceStyle sliceStyle2 = this.mSliceStyle;
            Objects.requireNonNull(sliceStyle2);
            textView.setTextColor(sliceStyle2.mTitleColor);
        }
        final Date date = new Date(j);
        SliceItem find = SliceQuery.find(sliceItem, "text", "title");
        if (find != null) {
            textView.setText((CharSequence) find.mObj);
        }
        final int i2 = this.mRowIndex;
        final boolean z2 = z;
        final SliceItem sliceItem2 = sliceItem;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                if (z2) {
                    new DatePickerDialog(GridRowView.this.getContext(), C1777R.style.DialogTheme, new DateSetListener(sliceItem2, i2), instance.get(1), instance.get(2), instance.get(5)).show();
                } else {
                    new TimePickerDialog(GridRowView.this.getContext(), C1777R.style.DialogTheme, new TimeSetListener(sliceItem2, i2), instance.get(11), instance.get(12), false).show();
                }
            }
        });
        linearLayout.setClickable(true);
        linearLayout.setBackground(SliceViewUtil.getDrawable(getContext(), 16843868));
        linearLayout.addView(textView);
        textView.setPadding(0, i, 0, 0);
        return true;
    }

    public final int getExtraBottomPadding() {
        SliceStyle sliceStyle;
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.mAllImages || this.mRowIndex != this.mRowCount - 1 || (sliceStyle = this.mSliceStyle) == null) {
            return 0;
        }
        return sliceStyle.mGridBottomPadding;
    }

    public final int getMaxCells() {
        int i;
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.isValid() || getWidth() == 0) {
            return -1;
        }
        GridContent gridContent2 = this.mGridContent;
        Objects.requireNonNull(gridContent2);
        if (gridContent2.mGridContent.size() <= 1) {
            return 1;
        }
        GridContent gridContent3 = this.mGridContent;
        Objects.requireNonNull(gridContent3);
        int i2 = gridContent3.mLargestImageMode;
        if (i2 == 2) {
            i = this.mLargeImageHeight;
        } else if (i2 != 4) {
            i = this.mSmallImageMinWidth;
        } else {
            i = this.mGridContent.getFirstImageSize(getContext()).x;
        }
        return getWidth() / (i + this.mGutter);
    }

    public final void makeEntireGridClickable(boolean z) {
        GridRowView gridRowView;
        GridRowView gridRowView2;
        LinearLayout linearLayout = this.mViewContainer;
        Drawable drawable = null;
        if (z) {
            gridRowView = this;
        } else {
            gridRowView = null;
        }
        linearLayout.setOnTouchListener(gridRowView);
        LinearLayout linearLayout2 = this.mViewContainer;
        if (z) {
            gridRowView2 = this;
        } else {
            gridRowView2 = null;
        }
        linearLayout2.setOnClickListener(gridRowView2);
        View view = this.mForeground;
        if (z) {
            drawable = SliceViewUtil.getDrawable(getContext(), 16843534);
        }
        view.setBackground(drawable);
        this.mViewContainer.setClickable(z);
        setClickable(z);
    }

    public final void onMeasure(int i, int i2) {
        int height = this.mGridContent.getHeight(this.mSliceStyle, this.mViewPolicy) + this.mInsetTop + this.mInsetBottom;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, 1073741824);
        this.mViewContainer.getLayoutParams().height = height;
        super.onMeasure(i, makeMeasureSpec);
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        this.mForeground.getLocationOnScreen(this.mLoc);
        this.mForeground.getBackground().setHotspot((float) ((int) (motionEvent.getRawX() - ((float) this.mLoc[0]))), (float) ((int) (motionEvent.getRawY() - ((float) this.mLoc[1]))));
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mForeground.setPressed(true);
        } else if (actionMasked == 3 || actionMasked == 1 || actionMasked == 2) {
            this.mForeground.setPressed(false);
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r4v7, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void populateViews() {
        /*
            r15 = this;
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            if (r0 == 0) goto L_0x01d2
            boolean r0 = r0.isValid()
            if (r0 != 0) goto L_0x000c
            goto L_0x01d2
        L_0x000c:
            boolean r0 = r15.scheduleMaxCellsUpdate()
            if (r0 == 0) goto L_0x0013
            return
        L_0x0013:
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            int r0 = r0.getLayoutDir()
            r1 = -1
            if (r0 == r1) goto L_0x0025
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            int r0 = r0.getLayoutDir()
            r15.setLayoutDirection(r0)
        L_0x0025:
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            java.util.Objects.requireNonNull(r0)
            androidx.slice.SliceItem r0 = r0.mPrimaryAction
            r2 = 2
            r3 = 1
            if (r0 == 0) goto L_0x004c
            androidx.slice.widget.EventInfo r0 = new androidx.slice.widget.EventInfo
            r4 = 3
            int r5 = r15.mRowIndex
            r0.<init>(r4, r3, r5)
            android.util.Pair r4 = new android.util.Pair
            androidx.slice.widget.GridContent r5 = r15.mGridContent
            java.util.Objects.requireNonNull(r5)
            androidx.slice.SliceItem r5 = r5.mPrimaryAction
            r4.<init>(r5, r0)
            android.widget.LinearLayout r0 = r15.mViewContainer
            r0.setTag(r4)
            r15.makeEntireGridClickable(r3)
        L_0x004c:
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            java.util.Objects.requireNonNull(r0)
            androidx.slice.SliceItem r0 = r0.mContentDescr
            if (r0 == 0) goto L_0x005a
            java.lang.Object r0 = r0.mObj
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            goto L_0x005b
        L_0x005a:
            r0 = 0
        L_0x005b:
            if (r0 == 0) goto L_0x0062
            android.widget.LinearLayout r4 = r15.mViewContainer
            r4.setContentDescription(r0)
        L_0x0062:
            androidx.slice.widget.GridContent r0 = r15.mGridContent
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList<androidx.slice.widget.GridContent$CellContent> r0 = r0.mGridContent
            androidx.slice.widget.GridContent r4 = r15.mGridContent
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mLargestImageMode
            r5 = 4
            if (r4 == r2) goto L_0x0085
            androidx.slice.widget.GridContent r4 = r15.mGridContent
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mLargestImageMode
            if (r4 != r5) goto L_0x007d
            goto L_0x0085
        L_0x007d:
            android.widget.LinearLayout r4 = r15.mViewContainer
            r6 = 16
            r4.setGravity(r6)
            goto L_0x008c
        L_0x0085:
            android.widget.LinearLayout r4 = r15.mViewContainer
            r6 = 48
            r4.setGravity(r6)
        L_0x008c:
            int r4 = r15.mMaxCells
            androidx.slice.widget.GridContent r6 = r15.mGridContent
            java.util.Objects.requireNonNull(r6)
            androidx.slice.SliceItem r6 = r6.mSeeMoreItem
            r7 = 0
            if (r6 == 0) goto L_0x009a
            r6 = r3
            goto L_0x009b
        L_0x009a:
            r6 = r7
        L_0x009b:
            r8 = r7
        L_0x009c:
            int r9 = r0.size()
            if (r8 >= r9) goto L_0x01d1
            android.widget.LinearLayout r9 = r15.mViewContainer
            int r9 = r9.getChildCount()
            if (r9 < r4) goto L_0x01bc
            int r0 = r0.size()
            int r0 = r0 - r4
            if (r6 == 0) goto L_0x01d1
            android.widget.LinearLayout r4 = r15.mViewContainer
            int r6 = r4.getChildCount()
            int r6 = r6 - r3
            android.view.View r4 = r4.getChildAt(r6)
            android.widget.LinearLayout r6 = r15.mViewContainer
            r6.removeView(r4)
            androidx.slice.widget.GridContent r6 = r15.mGridContent
            java.util.Objects.requireNonNull(r6)
            androidx.slice.SliceItem r6 = r6.mSeeMoreItem
            android.widget.LinearLayout r8 = r15.mViewContainer
            int r8 = r8.getChildCount()
            int r9 = r15.mMaxCells
            java.util.Objects.requireNonNull(r6)
            java.lang.String r10 = r6.mFormat
            java.lang.String r11 = "slice"
            boolean r10 = r11.equals(r10)
            if (r10 != 0) goto L_0x00e8
            java.lang.String r10 = r6.mFormat
            java.lang.String r11 = "action"
            boolean r10 = r11.equals(r10)
            if (r10 == 0) goto L_0x0100
        L_0x00e8:
            androidx.slice.Slice r10 = r6.getSlice()
            java.util.List r10 = r10.getItems()
            int r10 = r10.size()
            if (r10 <= 0) goto L_0x0100
            androidx.slice.widget.GridContent$CellContent r0 = new androidx.slice.widget.GridContent$CellContent
            r0.<init>(r6)
            r15.addCell(r0, r8, r9)
            goto L_0x01d1
        L_0x0100:
            android.content.Context r10 = r15.getContext()
            android.view.LayoutInflater r10 = android.view.LayoutInflater.from(r10)
            androidx.slice.widget.GridContent r11 = r15.mGridContent
            java.util.Objects.requireNonNull(r11)
            boolean r11 = r11.mAllImages
            r12 = 2131429039(0x7f0b06af, float:1.847974E38)
            if (r11 == 0) goto L_0x014f
            r11 = 2131623965(0x7f0e001d, float:1.8875096E38)
            android.widget.LinearLayout r13 = r15.mViewContainer
            android.view.View r10 = r10.inflate(r11, r13, r7)
            android.widget.FrameLayout r10 = (android.widget.FrameLayout) r10
            android.widget.FrameLayout$LayoutParams r11 = new android.widget.FrameLayout$LayoutParams
            r11.<init>(r1, r1)
            r10.addView(r4, r7, r11)
            android.view.View r4 = r10.findViewById(r12)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11 = 2131428551(0x7f0b04c7, float:1.847875E38)
            android.view.View r11 = r10.findViewById(r11)
            androidx.slice.CornerDrawable r12 = new androidx.slice.CornerDrawable
            android.content.Context r13 = r15.getContext()
            r14 = 16842800(0x1010030, float:2.3693693E-38)
            android.graphics.drawable.Drawable r13 = androidx.slice.widget.SliceViewUtil.getDrawable(r13, r14)
            androidx.slice.widget.SliceStyle r14 = r15.mSliceStyle
            java.util.Objects.requireNonNull(r14)
            float r14 = r14.mImageCornerRadius
            r12.<init>(r13, r14)
            r11.setBackground(r12)
            goto L_0x0181
        L_0x014f:
            r4 = 2131623964(0x7f0e001c, float:1.8875094E38)
            android.widget.LinearLayout r11 = r15.mViewContainer
            android.view.View r4 = r10.inflate(r4, r11, r7)
            r10 = r4
            android.widget.LinearLayout r10 = (android.widget.LinearLayout) r10
            android.view.View r4 = r10.findViewById(r12)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r11 = 2131429038(0x7f0b06ae, float:1.8479737E38)
            android.view.View r11 = r10.findViewById(r11)
            android.widget.TextView r11 = (android.widget.TextView) r11
            androidx.slice.widget.SliceStyle r12 = r15.mSliceStyle
            if (r12 == 0) goto L_0x0181
            androidx.slice.widget.RowStyle r13 = r15.mRowStyle
            if (r13 == 0) goto L_0x0181
            int r12 = r12.mGridTitleSize
            float r12 = (float) r12
            r11.setTextSize(r7, r12)
            androidx.slice.widget.RowStyle r12 = r15.mRowStyle
            int r12 = r12.getTitleColor()
            r11.setTextColor(r12)
        L_0x0181:
            android.widget.LinearLayout r11 = r15.mViewContainer
            android.widget.LinearLayout$LayoutParams r12 = new android.widget.LinearLayout$LayoutParams
            r13 = 1065353216(0x3f800000, float:1.0)
            r12.<init>(r7, r1, r13)
            r11.addView(r10, r12)
            android.content.res.Resources r1 = r15.getResources()
            r11 = 2131951646(0x7f13001e, float:1.9539712E38)
            java.lang.Object[] r12 = new java.lang.Object[r3]
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r12[r7] = r0
            java.lang.String r0 = r1.getString(r11, r12)
            r4.setText(r0)
            androidx.slice.widget.EventInfo r0 = new androidx.slice.widget.EventInfo
            int r1 = r15.mRowIndex
            r0.<init>(r5, r3, r1)
            r0.actionPosition = r2
            r0.actionIndex = r8
            r0.actionCount = r9
            android.util.Pair r1 = new android.util.Pair
            r1.<init>(r6, r0)
            r10.setTag(r1)
            r15.makeClickable(r10)
            goto L_0x01d1
        L_0x01bc:
            java.lang.Object r9 = r0.get(r8)
            androidx.slice.widget.GridContent$CellContent r9 = (androidx.slice.widget.GridContent.CellContent) r9
            int r10 = r0.size()
            int r10 = java.lang.Math.min(r10, r4)
            r15.addCell(r9, r8, r10)
            int r8 = r8 + 1
            goto L_0x009c
        L_0x01d1:
            return
        L_0x01d2:
            r15.resetView()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.GridRowView.populateViews():void");
    }

    public final void resetView() {
        if (this.mMaxCellUpdateScheduled) {
            this.mMaxCellUpdateScheduled = false;
            getViewTreeObserver().removeOnPreDrawListener(this.mMaxCellsUpdater);
        }
        this.mViewContainer.removeAllViews();
        setLayoutDirection(2);
        makeEntireGridClickable(false);
    }

    public final boolean scheduleMaxCellsUpdate() {
        GridContent gridContent = this.mGridContent;
        if (gridContent == null || !gridContent.isValid()) {
            return true;
        }
        if (getWidth() == 0) {
            this.mMaxCellUpdateScheduled = true;
            getViewTreeObserver().addOnPreDrawListener(this.mMaxCellsUpdater);
            return true;
        }
        this.mMaxCells = getMaxCells();
        return false;
    }

    public final void setTint(int i) {
        this.mTintColor = i;
        if (this.mGridContent != null) {
            resetView();
            populateViews();
        }
    }

    public final void makeClickable(ViewGroup viewGroup) {
        viewGroup.setOnClickListener(this);
        viewGroup.setBackground(SliceViewUtil.getDrawable(getContext(), 16843868));
        viewGroup.setClickable(true);
    }

    public final void onClick(View view) {
        SliceItem find;
        Pair pair = (Pair) view.getTag();
        SliceItem sliceItem = (SliceItem) pair.first;
        EventInfo eventInfo = (EventInfo) pair.second;
        if (sliceItem != null && (find = SliceQuery.find(sliceItem, "action", (String) null)) != null) {
            try {
                find.fireActionInternal((Context) null, (Intent) null);
                SliceView.OnSliceActionListener onSliceActionListener = this.mObserver;
                if (onSliceActionListener != null) {
                    onSliceActionListener.onSliceAction();
                }
            } catch (PendingIntent.CanceledException e) {
                Log.e("GridRowView", "PendingIntent for slice cannot be sent", e);
            }
        }
    }

    public final void setInsets(int i, int i2, int i3, int i4) {
        SliceStyle sliceStyle;
        super.setInsets(i, i2, i3, i4);
        LinearLayout linearLayout = this.mViewContainer;
        GridContent gridContent = this.mGridContent;
        int i5 = 0;
        if (gridContent != null && gridContent.mAllImages && this.mRowIndex == 0 && (sliceStyle = this.mSliceStyle) != null) {
            i5 = sliceStyle.mGridTopPadding;
        }
        linearLayout.setPadding(i, i5 + i2, i3, getExtraBottomPadding() + i4);
    }

    public final void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, SliceView.OnSliceActionListener onSliceActionListener) {
        SliceStyle sliceStyle;
        resetView();
        this.mObserver = onSliceActionListener;
        this.mRowIndex = i;
        this.mRowCount = i2;
        this.mGridContent = (GridContent) sliceContent;
        if (!scheduleMaxCellsUpdate()) {
            populateViews();
        }
        LinearLayout linearLayout = this.mViewContainer;
        int i3 = this.mInsetStart;
        int i4 = this.mInsetTop;
        GridContent gridContent = this.mGridContent;
        int i5 = 0;
        if (gridContent != null && gridContent.mAllImages && this.mRowIndex == 0 && (sliceStyle = this.mSliceStyle) != null) {
            i5 = sliceStyle.mGridTopPadding;
        }
        linearLayout.setPadding(i3, i5 + i4, this.mInsetEnd, getExtraBottomPadding() + this.mInsetBottom);
    }
}
