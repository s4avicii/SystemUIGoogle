package androidx.slice.widget;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.SliceActionView;
import androidx.slice.widget.SliceView;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

public final class RowView extends SliceChildView implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public final View mActionDivider;
    public final ProgressBar mActionSpinner;
    public final ArrayMap<SliceActionImpl, SliceActionView> mActions = new ArrayMap<>();
    public boolean mAllowTwoLines;
    public final View mBottomDivider;
    public final LinearLayout mContent;
    public final LinearLayout mEndContainer;
    public Handler mHandler;
    public List<SliceAction> mHeaderActions;
    public int mIconSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_icon_size);
    public int mImageSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_small_image_size);
    public boolean mIsHeader;
    public boolean mIsRangeSliding;
    public boolean mIsStarRating;
    public long mLastSentRangeUpdate;
    public final TextView mLastUpdatedText;
    public Set<SliceItem> mLoadingActions = new HashSet();
    public int mMeasuredRangeHeight;
    public final TextView mPrimaryText;
    public View mRangeBar;
    public SliceItem mRangeItem;
    public int mRangeMaxValue;
    public int mRangeMinValue;
    public C03642 mRangeUpdater = new Runnable() {
        public final void run() {
            RowView.this.sendSliderValue();
            RowView.this.mRangeUpdaterRunning = false;
        }
    };
    public boolean mRangeUpdaterRunning;
    public int mRangeValue;
    public final C03664 mRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
        public final void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
            RowView rowView = RowView.this;
            rowView.mRangeValue = Math.round(f + ((float) rowView.mRangeMinValue));
            long currentTimeMillis = System.currentTimeMillis();
            RowView rowView2 = RowView.this;
            long j = rowView2.mLastSentRangeUpdate;
            if (j != 0 && currentTimeMillis - j > 200) {
                rowView2.mRangeUpdaterRunning = false;
                rowView2.mHandler.removeCallbacks(rowView2.mRangeUpdater);
                RowView.this.sendSliderValue();
            } else if (!rowView2.mRangeUpdaterRunning) {
                rowView2.mRangeUpdaterRunning = true;
                rowView2.mHandler.postDelayed(rowView2.mRangeUpdater, 200);
            }
        }
    };
    public final LinearLayout mRootView;
    public SliceActionImpl mRowAction;
    public RowContent mRowContent;
    public int mRowIndex;
    public final TextView mSecondaryText;
    public Button mSeeMoreView;
    public final C03653 mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            RowView rowView = RowView.this;
            rowView.mRangeValue = i + rowView.mRangeMinValue;
            long currentTimeMillis = System.currentTimeMillis();
            RowView rowView2 = RowView.this;
            long j = rowView2.mLastSentRangeUpdate;
            if (j != 0 && currentTimeMillis - j > 200) {
                rowView2.mRangeUpdaterRunning = false;
                rowView2.mHandler.removeCallbacks(rowView2.mRangeUpdater);
                RowView.this.sendSliderValue();
            } else if (!rowView2.mRangeUpdaterRunning) {
                rowView2.mRangeUpdaterRunning = true;
                rowView2.mHandler.postDelayed(rowView2.mRangeUpdater, 200);
            }
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
            RowView.this.mIsRangeSliding = true;
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            RowView rowView = RowView.this;
            rowView.mIsRangeSliding = false;
            if (rowView.mRangeUpdaterRunning) {
                rowView.mRangeUpdaterRunning = false;
                rowView.mHandler.removeCallbacks(rowView.mRangeUpdater);
                RowView rowView2 = RowView.this;
                int progress = seekBar.getProgress();
                RowView rowView3 = RowView.this;
                rowView2.mRangeValue = progress + rowView3.mRangeMinValue;
                rowView3.sendSliderValue();
            }
        }
    };
    public SliceItem mSelectionItem;
    public ArrayList<String> mSelectionOptionKeys;
    public ArrayList<CharSequence> mSelectionOptionValues;
    public Spinner mSelectionSpinner;
    public boolean mShowActionSpinner;
    public final LinearLayout mStartContainer;
    public SliceItem mStartItem;
    public final LinearLayout mSubContent;
    public final ArrayMap<SliceActionImpl, SliceActionView> mToggles = new ArrayMap<>();

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
                    sliceItem.fireActionInternal(RowView.this.getContext(), new Intent().addFlags(268435456).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    SliceView.OnSliceActionListener onSliceActionListener = RowView.this.mObserver;
                    if (onSliceActionListener != null) {
                        onSliceActionListener.onSliceAction();
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RowView", "PendingIntent for slice cannot be sent", e);
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
                    sliceItem.fireActionInternal(RowView.this.getContext(), new Intent().addFlags(268435456).putExtra("android.app.slice.extra.RANGE_VALUE", time.getTime()));
                    SliceView.OnSliceActionListener onSliceActionListener = RowView.this.mObserver;
                    if (onSliceActionListener != null) {
                        onSliceActionListener.onSliceAction();
                    }
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RowView", "PendingIntent for slice cannot be sent", e);
                }
            }
        }
    }

    public final void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a7, code lost:
        if (r2.mShowTitleItems != false) goto L_0x00a9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x037f  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x041f  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x018a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void populateViews(boolean r11) {
        /*
            r10 = this;
            r0 = 0
            r1 = 1
            if (r11 == 0) goto L_0x000a
            boolean r11 = r10.mIsRangeSliding
            if (r11 == 0) goto L_0x000a
            r11 = r1
            goto L_0x000b
        L_0x000a:
            r11 = r0
        L_0x000b:
            if (r11 != 0) goto L_0x0010
            r10.resetViewState()
        L_0x0010:
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            int r2 = r2.getLayoutDir()
            r3 = -1
            if (r2 == r3) goto L_0x0022
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            int r2 = r2.getLayoutDir()
            r10.setLayoutDirection(r2)
        L_0x0022:
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            boolean r2 = r2.isDefaultSeeMore()
            r4 = 8
            if (r2 == 0) goto L_0x006b
            android.content.Context r11 = r10.getContext()
            android.view.LayoutInflater r11 = android.view.LayoutInflater.from(r11)
            r2 = 2131623976(0x7f0e0028, float:1.8875119E38)
            android.view.View r11 = r11.inflate(r2, r10, r0)
            android.widget.Button r11 = (android.widget.Button) r11
            androidx.slice.widget.RowView$1 r0 = new androidx.slice.widget.RowView$1
            r0.<init>(r11)
            r11.setOnClickListener(r0)
            int r0 = r10.mTintColor
            if (r0 == r3) goto L_0x004c
            r11.setTextColor(r0)
        L_0x004c:
            r10.mSeeMoreView = r11
            android.widget.LinearLayout r0 = r10.mRootView
            r0.addView(r11)
            java.util.Set<androidx.slice.SliceItem> r0 = r10.mLoadingActions
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            androidx.slice.SliceItem r2 = r2.mSliceItem
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x006a
            r10.mShowActionSpinner = r1
            r11.setVisibility(r4)
            r10.updateActionSpinner()
        L_0x006a:
            return
        L_0x006b:
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            androidx.slice.SliceItem r2 = r2.mContentDescr
            r5 = 0
            if (r2 == 0) goto L_0x007a
            java.lang.Object r2 = r2.mObj
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            goto L_0x007b
        L_0x007a:
            r2 = r5
        L_0x007b:
            if (r2 == 0) goto L_0x0082
            android.widget.LinearLayout r6 = r10.mContent
            r6.setContentDescription(r2)
        L_0x0082:
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            boolean r6 = r2.mIsHeader
            if (r6 == 0) goto L_0x0091
            boolean r6 = r2.mShowTitleItems
            if (r6 != 0) goto L_0x0091
            r2 = r5
            goto L_0x0093
        L_0x0091:
            androidx.slice.SliceItem r2 = r2.mStartItem
        L_0x0093:
            r10.mStartItem = r2
            if (r2 == 0) goto L_0x00ab
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsHeader
            if (r2 == 0) goto L_0x00a9
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mShowTitleItems
            if (r2 == 0) goto L_0x00ab
        L_0x00a9:
            r2 = r1
            goto L_0x00ac
        L_0x00ab:
            r2 = r0
        L_0x00ac:
            if (r2 == 0) goto L_0x00b6
            androidx.slice.SliceItem r2 = r10.mStartItem
            int r6 = r10.mTintColor
            boolean r2 = r10.addItem(r2, r6, r1)
        L_0x00b6:
            android.widget.LinearLayout r6 = r10.mStartContainer
            if (r2 == 0) goto L_0x00bc
            r2 = r0
            goto L_0x00bd
        L_0x00bc:
            r2 = r4
        L_0x00bd:
            r6.setVisibility(r2)
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            androidx.slice.SliceItem r2 = r2.mTitleItem
            if (r2 == 0) goto L_0x00d2
            android.widget.TextView r6 = r10.mPrimaryText
            java.lang.CharSequence r7 = r2.getSanitizedText()
            r6.setText(r7)
        L_0x00d2:
            androidx.slice.widget.SliceStyle r6 = r10.mSliceStyle
            if (r6 == 0) goto L_0x00f0
            android.widget.TextView r7 = r10.mPrimaryText
            boolean r8 = r10.mIsHeader
            if (r8 == 0) goto L_0x00df
            int r6 = r6.mHeaderTitleSize
            goto L_0x00e1
        L_0x00df:
            int r6 = r6.mTitleSize
        L_0x00e1:
            float r6 = (float) r6
            r7.setTextSize(r0, r6)
            android.widget.TextView r6 = r10.mPrimaryText
            androidx.slice.widget.RowStyle r7 = r10.mRowStyle
            int r7 = r7.getTitleColor()
            r6.setTextColor(r7)
        L_0x00f0:
            android.widget.TextView r6 = r10.mPrimaryText
            if (r2 == 0) goto L_0x00f6
            r7 = r0
            goto L_0x00f7
        L_0x00f6:
            r7 = r4
        L_0x00f7:
            r6.setVisibility(r7)
            if (r2 == 0) goto L_0x00fe
            r2 = r1
            goto L_0x00ff
        L_0x00fe:
            r2 = r0
        L_0x00ff:
            r10.addSubtitle(r2)
            android.view.View r2 = r10.mBottomDivider
            androidx.slice.widget.RowContent r6 = r10.mRowContent
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.mShowBottomDivider
            if (r6 == 0) goto L_0x010f
            r6 = r0
            goto L_0x0110
        L_0x010f:
            r6 = r4
        L_0x0110:
            r2.setVisibility(r6)
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            androidx.slice.SliceItem r2 = r2.mPrimaryAction
            r6 = 2
            if (r2 == 0) goto L_0x0181
            androidx.slice.SliceItem r7 = r10.mStartItem
            if (r2 == r7) goto L_0x0181
            androidx.slice.core.SliceActionImpl r7 = new androidx.slice.core.SliceActionImpl
            r7.<init>(r2)
            r10.mRowAction = r7
            java.lang.String r2 = r7.getSubtype()
            if (r2 == 0) goto L_0x0181
            androidx.slice.core.SliceActionImpl r2 = r10.mRowAction
            java.lang.String r2 = r2.getSubtype()
            java.util.Objects.requireNonNull(r2)
            int r7 = r2.hashCode()
            switch(r7) {
                case -868304044: goto L_0x0157;
                case 759128640: goto L_0x014b;
                case 1250407999: goto L_0x0140;
                default: goto L_0x013e;
            }
        L_0x013e:
            r2 = r3
            goto L_0x0162
        L_0x0140:
            java.lang.String r7 = "date_picker"
            boolean r2 = r2.equals(r7)
            if (r2 != 0) goto L_0x0149
            goto L_0x013e
        L_0x0149:
            r2 = r6
            goto L_0x0162
        L_0x014b:
            java.lang.String r7 = "time_picker"
            boolean r2 = r2.equals(r7)
            if (r2 != 0) goto L_0x0155
            goto L_0x013e
        L_0x0155:
            r2 = r1
            goto L_0x0162
        L_0x0157:
            java.lang.String r7 = "toggle"
            boolean r2 = r2.equals(r7)
            if (r2 != 0) goto L_0x0161
            goto L_0x013e
        L_0x0161:
            r2 = r0
        L_0x0162:
            switch(r2) {
                case 0: goto L_0x0172;
                case 1: goto L_0x016c;
                case 2: goto L_0x0166;
                default: goto L_0x0165;
            }
        L_0x0165:
            goto L_0x0181
        L_0x0166:
            android.widget.LinearLayout r11 = r10.mRootView
            r10.setViewClickable(r11, r1)
            return
        L_0x016c:
            android.widget.LinearLayout r11 = r10.mRootView
            r10.setViewClickable(r11, r1)
            return
        L_0x0172:
            androidx.slice.core.SliceActionImpl r11 = r10.mRowAction
            int r2 = r10.mTintColor
            android.widget.LinearLayout r3 = r10.mEndContainer
            r10.addAction(r11, r2, r3, r0)
            android.widget.LinearLayout r11 = r10.mRootView
            r10.setViewClickable(r11, r1)
            return
        L_0x0181:
            androidx.slice.widget.RowContent r2 = r10.mRowContent
            java.util.Objects.requireNonNull(r2)
            androidx.slice.SliceItem r2 = r2.mRange
            if (r2 == 0) goto L_0x0376
            androidx.slice.core.SliceActionImpl r7 = r10.mRowAction
            if (r7 == 0) goto L_0x0193
            android.widget.LinearLayout r7 = r10.mRootView
            r10.setViewClickable(r7, r1)
        L_0x0193:
            r10.mRangeItem = r2
            java.lang.String r7 = "int"
            java.lang.String r8 = "range_mode"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r2, (java.lang.String) r7, (java.lang.String) r8)
            if (r2 == 0) goto L_0x01ab
            int r2 = r2.getInt()
            if (r2 != r6) goto L_0x01a8
            r2 = r1
            goto L_0x01a9
        L_0x01a8:
            r2 = r0
        L_0x01a9:
            r10.mIsStarRating = r2
        L_0x01ab:
            if (r11 != 0) goto L_0x0371
            androidx.slice.SliceItem r11 = r10.mRangeItem
            java.lang.String r2 = "min"
            androidx.slice.SliceItem r11 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r11, (java.lang.String) r7, (java.lang.String) r2)
            if (r11 == 0) goto L_0x01bc
            int r11 = r11.getInt()
            goto L_0x01bd
        L_0x01bc:
            r11 = r0
        L_0x01bd:
            r10.mRangeMinValue = r11
            androidx.slice.SliceItem r2 = r10.mRangeItem
            java.lang.String r9 = "max"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r2, (java.lang.String) r7, (java.lang.String) r9)
            boolean r9 = r10.mIsStarRating
            if (r9 == 0) goto L_0x01cd
            r9 = 5
            goto L_0x01cf
        L_0x01cd:
            r9 = 100
        L_0x01cf:
            if (r2 == 0) goto L_0x01d5
            int r9 = r2.getInt()
        L_0x01d5:
            r10.mRangeMaxValue = r9
            androidx.slice.SliceItem r2 = r10.mRangeItem
            java.lang.String r9 = "value"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r2, (java.lang.String) r7, (java.lang.String) r9)
            if (r2 == 0) goto L_0x01e8
            int r2 = r2.getInt()
            int r2 = r2 - r11
            goto L_0x01e9
        L_0x01e8:
            r2 = r0
        L_0x01e9:
            r10.mRangeValue = r2
            android.os.Handler r11 = r10.mHandler
            if (r11 != 0) goto L_0x01f6
            android.os.Handler r11 = new android.os.Handler
            r11.<init>()
            r10.mHandler = r11
        L_0x01f6:
            boolean r11 = r10.mIsStarRating
            r2 = -2
            if (r11 == 0) goto L_0x0252
            android.widget.RatingBar r11 = new android.widget.RatingBar
            android.content.Context r1 = r10.getContext()
            r11.<init>(r1)
            android.graphics.drawable.Drawable r1 = r11.getProgressDrawable()
            android.graphics.drawable.LayerDrawable r1 = (android.graphics.drawable.LayerDrawable) r1
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r6)
            int r4 = r10.mTintColor
            android.graphics.PorterDuff$Mode r5 = android.graphics.PorterDuff.Mode.SRC_IN
            r1.setColorFilter(r4, r5)
            r1 = 1065353216(0x3f800000, float:1.0)
            r11.setStepSize(r1)
            int r1 = r10.mRangeMaxValue
            r11.setNumStars(r1)
            int r1 = r10.mRangeValue
            float r1 = (float) r1
            r11.setRating(r1)
            r11.setVisibility(r0)
            android.widget.LinearLayout r1 = new android.widget.LinearLayout
            android.content.Context r4 = r10.getContext()
            r1.<init>(r4)
            r4 = 17
            r1.setGravity(r4)
            r1.setVisibility(r0)
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            r4.<init>(r2, r2)
            r1.addView(r11, r4)
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            r4.<init>(r3, r2)
            r10.addView(r1, r4)
            androidx.slice.widget.RowView$4 r2 = r10.mRatingBarChangeListener
            r11.setOnRatingBarChangeListener(r2)
            r10.mRangeBar = r1
            goto L_0x0371
        L_0x0252:
            androidx.slice.SliceItem r11 = r10.mRangeItem
            androidx.slice.SliceItem r11 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r11, (java.lang.String) r7, (java.lang.String) r8)
            if (r11 == 0) goto L_0x0262
            int r11 = r11.getInt()
            if (r11 != r1) goto L_0x0262
            r11 = r1
            goto L_0x0263
        L_0x0262:
            r11 = r0
        L_0x0263:
            androidx.slice.SliceItem r6 = r10.mRangeItem
            java.util.Objects.requireNonNull(r6)
            java.lang.String r6 = r6.mFormat
            java.lang.String r7 = "action"
            boolean r6 = r7.equals(r6)
            androidx.slice.SliceItem r7 = r10.mStartItem
            if (r7 != 0) goto L_0x0276
            r7 = r1
            goto L_0x0277
        L_0x0276:
            r7 = r0
        L_0x0277:
            if (r6 == 0) goto L_0x02ab
            if (r7 == 0) goto L_0x0286
            android.widget.SeekBar r5 = new android.widget.SeekBar
            android.content.Context r7 = r10.getContext()
            r5.<init>(r7)
            goto L_0x02f5
        L_0x0286:
            android.content.Context r5 = r10.getContext()
            android.view.LayoutInflater r5 = android.view.LayoutInflater.from(r5)
            r7 = 2131623978(0x7f0e002a, float:1.8875123E38)
            android.view.View r5 = r5.inflate(r7, r10, r0)
            android.widget.SeekBar r5 = (android.widget.SeekBar) r5
            androidx.slice.widget.RowStyle r7 = r10.mRowStyle
            if (r7 == 0) goto L_0x02f5
            int r7 = r7.mSeekBarInlineWidth
            if (r5 == 0) goto L_0x02f5
            if (r7 < 0) goto L_0x02f5
            android.view.ViewGroup$LayoutParams r8 = r5.getLayoutParams()
            r8.width = r7
            r5.setLayoutParams(r8)
            goto L_0x02f5
        L_0x02ab:
            if (r7 == 0) goto L_0x02bb
            android.widget.ProgressBar r7 = new android.widget.ProgressBar
            android.content.Context r8 = r10.getContext()
            r9 = 16842872(0x1010078, float:2.3693894E-38)
            r7.<init>(r8, r5, r9)
            r5 = r7
            goto L_0x02f0
        L_0x02bb:
            android.content.Context r5 = r10.getContext()
            android.view.LayoutInflater r5 = android.view.LayoutInflater.from(r5)
            r7 = 2131623970(0x7f0e0022, float:1.8875107E38)
            android.view.View r5 = r5.inflate(r7, r10, r0)
            android.widget.ProgressBar r5 = (android.widget.ProgressBar) r5
            androidx.slice.widget.RowStyle r7 = r10.mRowStyle
            if (r7 == 0) goto L_0x02f0
            int r7 = r7.mProgressBarInlineWidth
            if (r5 == 0) goto L_0x02df
            if (r7 < 0) goto L_0x02df
            android.view.ViewGroup$LayoutParams r8 = r5.getLayoutParams()
            r8.width = r7
            r5.setLayoutParams(r8)
        L_0x02df:
            androidx.slice.widget.RowStyle r7 = r10.mRowStyle
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mProgressBarStartPadding
            androidx.slice.widget.RowStyle r8 = r10.mRowStyle
            java.util.Objects.requireNonNull(r8)
            int r8 = r8.mProgressBarEndPadding
            setViewSidePaddings(r5, r7, r8)
        L_0x02f0:
            if (r11 == 0) goto L_0x02f5
            r5.setIndeterminate(r1)
        L_0x02f5:
            if (r11 == 0) goto L_0x02fc
            android.graphics.drawable.Drawable r7 = r5.getIndeterminateDrawable()
            goto L_0x0300
        L_0x02fc:
            android.graphics.drawable.Drawable r7 = r5.getProgressDrawable()
        L_0x0300:
            int r8 = r10.mTintColor
            if (r8 == r3) goto L_0x0312
            if (r7 == 0) goto L_0x0312
            r7.setTint(r8)
            if (r11 == 0) goto L_0x030f
            r5.setIndeterminateDrawable(r7)
            goto L_0x0312
        L_0x030f:
            r5.setProgressDrawable(r7)
        L_0x0312:
            int r11 = r10.mRangeMaxValue
            int r7 = r10.mRangeMinValue
            int r11 = r11 - r7
            r5.setMax(r11)
            int r11 = r10.mRangeValue
            r5.setProgress(r11)
            r5.setVisibility(r0)
            androidx.slice.SliceItem r11 = r10.mStartItem
            if (r11 != 0) goto L_0x032f
            android.widget.FrameLayout$LayoutParams r11 = new android.widget.FrameLayout$LayoutParams
            r11.<init>(r3, r2)
            r10.addView(r5, r11)
            goto L_0x0339
        L_0x032f:
            android.widget.LinearLayout r11 = r10.mSubContent
            r11.setVisibility(r4)
            android.widget.LinearLayout r11 = r10.mContent
            r11.addView(r5, r1)
        L_0x0339:
            r10.mRangeBar = r5
            if (r6 == 0) goto L_0x0371
            androidx.slice.widget.RowContent r11 = r10.mRowContent
            androidx.slice.SliceItem r11 = r11.getInputRangeThumb()
            android.view.View r1 = r10.mRangeBar
            android.widget.SeekBar r1 = (android.widget.SeekBar) r1
            if (r11 == 0) goto L_0x035c
            java.lang.Object r11 = r11.mObj
            androidx.core.graphics.drawable.IconCompat r11 = (androidx.core.graphics.drawable.IconCompat) r11
            if (r11 == 0) goto L_0x035c
            android.content.Context r2 = r10.getContext()
            android.graphics.drawable.Drawable r11 = r11.loadDrawable(r2)
            if (r11 == 0) goto L_0x035c
            r1.setThumb(r11)
        L_0x035c:
            android.graphics.drawable.Drawable r11 = r1.getThumb()
            int r2 = r10.mTintColor
            if (r2 == r3) goto L_0x036c
            if (r11 == 0) goto L_0x036c
            r11.setTint(r2)
            r1.setThumb(r11)
        L_0x036c:
            androidx.slice.widget.RowView$3 r11 = r10.mSeekBarChangeListener
            r1.setOnSeekBarChangeListener(r11)
        L_0x0371:
            androidx.slice.SliceItem r11 = r10.mStartItem
            if (r11 != 0) goto L_0x0376
            return
        L_0x0376:
            androidx.slice.widget.RowContent r11 = r10.mRowContent
            java.util.Objects.requireNonNull(r11)
            androidx.slice.SliceItem r11 = r11.mSelection
            if (r11 == 0) goto L_0x041f
            r10.mSelectionItem = r11
            android.os.Handler r1 = r10.mHandler
            if (r1 != 0) goto L_0x038c
            android.os.Handler r1 = new android.os.Handler
            r1.<init>()
            r10.mHandler = r1
        L_0x038c:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r10.mSelectionOptionKeys = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r10.mSelectionOptionValues = r1
            androidx.slice.Slice r11 = r11.getSlice()
            java.util.List r11 = r11.getItems()
            r1 = r0
        L_0x03a3:
            int r2 = r11.size()
            if (r1 >= r2) goto L_0x03e8
            java.lang.Object r2 = r11.get(r1)
            androidx.slice.SliceItem r2 = (androidx.slice.SliceItem) r2
            java.lang.String r3 = "selection_option"
            boolean r3 = r2.hasHint(r3)
            if (r3 != 0) goto L_0x03b9
            goto L_0x03e5
        L_0x03b9:
            java.lang.String r3 = "text"
            java.lang.String r4 = "selection_option_key"
            androidx.slice.SliceItem r4 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r2, (java.lang.String) r3, (java.lang.String) r4)
            java.lang.String r5 = "selection_option_value"
            androidx.slice.SliceItem r2 = androidx.slice.core.SliceQuery.findSubtype((androidx.slice.SliceItem) r2, (java.lang.String) r3, (java.lang.String) r5)
            if (r4 == 0) goto L_0x03e5
            if (r2 != 0) goto L_0x03cf
            goto L_0x03e5
        L_0x03cf:
            java.util.ArrayList<java.lang.String> r3 = r10.mSelectionOptionKeys
            java.lang.Object r4 = r4.mObj
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            java.lang.String r4 = r4.toString()
            r3.add(r4)
            java.util.ArrayList<java.lang.CharSequence> r3 = r10.mSelectionOptionValues
            java.lang.CharSequence r2 = r2.getSanitizedText()
            r3.add(r2)
        L_0x03e5:
            int r1 = r1 + 1
            goto L_0x03a3
        L_0x03e8:
            android.content.Context r11 = r10.getContext()
            android.view.LayoutInflater r11 = android.view.LayoutInflater.from(r11)
            r1 = 2131623973(0x7f0e0025, float:1.8875113E38)
            android.view.View r11 = r11.inflate(r1, r10, r0)
            android.widget.Spinner r11 = (android.widget.Spinner) r11
            r10.mSelectionSpinner = r11
            android.widget.ArrayAdapter r11 = new android.widget.ArrayAdapter
            android.content.Context r0 = r10.getContext()
            r1 = 2131623975(0x7f0e0027, float:1.8875117E38)
            java.util.ArrayList<java.lang.CharSequence> r2 = r10.mSelectionOptionValues
            r11.<init>(r0, r1, r2)
            r0 = 2131623974(0x7f0e0026, float:1.8875115E38)
            r11.setDropDownViewResource(r0)
            android.widget.Spinner r0 = r10.mSelectionSpinner
            r0.setAdapter(r11)
            android.widget.Spinner r11 = r10.mSelectionSpinner
            r10.addView(r11)
            android.widget.Spinner r11 = r10.mSelectionSpinner
            r11.setOnItemSelectedListener(r10)
            return
        L_0x041f:
            r10.updateEndItems()
            r10.updateActionSpinner()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowView.populateViews(boolean):void");
    }

    public final void setViewClickable(LinearLayout linearLayout, boolean z) {
        RowView rowView;
        Drawable drawable = null;
        if (z) {
            rowView = this;
        } else {
            rowView = null;
        }
        linearLayout.setOnClickListener(rowView);
        if (z) {
            drawable = SliceViewUtil.getDrawable(getContext(), 16843534);
        }
        linearLayout.setBackground(drawable);
        linearLayout.setClickable(z);
    }

    public static void setViewSidePaddings(View view, int i, int i2) {
        boolean z;
        if (i >= 0 || i2 >= 0) {
            z = false;
        } else {
            z = true;
        }
        if (view != null && !z) {
            if (i < 0) {
                i = view.getPaddingStart();
            }
            int paddingTop = view.getPaddingTop();
            if (i2 < 0) {
                i2 = view.getPaddingEnd();
            }
            view.setPaddingRelative(i, paddingTop, i2, view.getPaddingBottom());
        }
    }

    public final void addAction(SliceActionImpl sliceActionImpl, int i, LinearLayout linearLayout, boolean z) {
        int i2;
        SliceActionView sliceActionView = new SliceActionView(getContext(), this.mRowStyle);
        linearLayout.addView(sliceActionView);
        if (linearLayout.getVisibility() == 8) {
            linearLayout.setVisibility(0);
        }
        boolean isToggle = sliceActionImpl.isToggle();
        boolean z2 = !isToggle;
        if (isToggle) {
            i2 = 3;
        } else {
            i2 = 0;
        }
        EventInfo eventInfo = new EventInfo(z2 ? 1 : 0, i2, this.mRowIndex);
        if (z) {
            eventInfo.actionPosition = 0;
            eventInfo.actionIndex = 0;
            eventInfo.actionCount = 1;
        }
        sliceActionView.setAction(sliceActionImpl, eventInfo, this.mObserver, i, this.mLoadingListener);
        if (this.mLoadingActions.contains(sliceActionImpl.mSliceItem)) {
            sliceActionView.setLoading();
        }
        if (isToggle) {
            this.mToggles.put(sliceActionImpl, sliceActionView);
        } else {
            this.mActions.put(sliceActionImpl, sliceActionView);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.widget.TextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: android.widget.TextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.widget.TextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: android.widget.ImageView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.widget.TextView} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0121  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0139  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean addItem(androidx.slice.SliceItem r10, int r11, boolean r12) {
        /*
            r9 = this;
            if (r12 == 0) goto L_0x0005
            android.widget.LinearLayout r0 = r9.mStartContainer
            goto L_0x0007
        L_0x0005:
            android.widget.LinearLayout r0 = r9.mEndContainer
        L_0x0007:
            java.util.Objects.requireNonNull(r10)
            java.lang.String r1 = r10.mFormat
            java.lang.String r2 = "slice"
            boolean r1 = r2.equals(r1)
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0021
            java.lang.String r1 = r10.mFormat
            java.lang.String r4 = "action"
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L_0x0050
        L_0x0021:
            java.lang.String r1 = "shortcut"
            boolean r1 = r10.hasHint(r1)
            if (r1 == 0) goto L_0x0033
            androidx.slice.core.SliceActionImpl r1 = new androidx.slice.core.SliceActionImpl
            r1.<init>(r10)
            r9.addAction(r1, r11, r0, r12)
            return r3
        L_0x0033:
            androidx.slice.Slice r12 = r10.getSlice()
            java.util.List r12 = r12.getItems()
            int r12 = r12.size()
            if (r12 != 0) goto L_0x0042
            return r2
        L_0x0042:
            androidx.slice.Slice r10 = r10.getSlice()
            java.util.List r10 = r10.getItems()
            java.lang.Object r10 = r10.get(r2)
            androidx.slice.SliceItem r10 = (androidx.slice.SliceItem) r10
        L_0x0050:
            java.util.Objects.requireNonNull(r10)
            java.lang.String r12 = r10.mFormat
            java.lang.String r1 = "image"
            boolean r12 = r1.equals(r12)
            r1 = 0
            if (r12 == 0) goto L_0x0064
            java.lang.Object r12 = r10.mObj
            androidx.core.graphics.drawable.IconCompat r12 = (androidx.core.graphics.drawable.IconCompat) r12
            r4 = r1
            goto L_0x0073
        L_0x0064:
            java.lang.String r12 = r10.mFormat
            java.lang.String r4 = "long"
            boolean r12 = r4.equals(r12)
            if (r12 == 0) goto L_0x0071
            r4 = r10
            r12 = r1
            goto L_0x0073
        L_0x0071:
            r12 = r1
            r4 = r12
        L_0x0073:
            if (r12 == 0) goto L_0x013f
            java.lang.String r1 = "no_tint"
            boolean r1 = r10.hasHint(r1)
            r1 = r1 ^ r3
            java.lang.String r4 = "raw"
            boolean r4 = r10.hasHint(r4)
            android.content.res.Resources r5 = r9.getResources()
            android.util.DisplayMetrics r5 = r5.getDisplayMetrics()
            float r5 = r5.density
            android.widget.ImageView r6 = new android.widget.ImageView
            android.content.Context r7 = r9.getContext()
            r6.<init>(r7)
            android.content.Context r7 = r9.getContext()
            android.graphics.drawable.Drawable r12 = r12.loadDrawable(r7)
            androidx.slice.widget.SliceStyle r7 = r9.mSliceStyle
            if (r7 == 0) goto L_0x00b0
            float r7 = r7.mImageCornerRadius
            r8 = 0
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x00ab
            r7 = r3
            goto L_0x00ac
        L_0x00ab:
            r7 = r2
        L_0x00ac:
            if (r7 == 0) goto L_0x00b0
            r7 = r3
            goto L_0x00b1
        L_0x00b0:
            r7 = r2
        L_0x00b1:
            if (r7 == 0) goto L_0x00cb
            java.lang.String r7 = "large"
            boolean r10 = r10.hasHint(r7)
            if (r10 == 0) goto L_0x00cb
            androidx.slice.CornerDrawable r10 = new androidx.slice.CornerDrawable
            androidx.slice.widget.SliceStyle r7 = r9.mSliceStyle
            java.util.Objects.requireNonNull(r7)
            float r7 = r7.mImageCornerRadius
            r10.<init>(r12, r7)
            r6.setImageDrawable(r10)
            goto L_0x00ce
        L_0x00cb:
            r6.setImageDrawable(r12)
        L_0x00ce:
            r10 = -1
            if (r1 == 0) goto L_0x00d6
            if (r11 == r10) goto L_0x00d6
            r6.setColorFilter(r11)
        L_0x00d6:
            boolean r11 = r9.mIsRangeSliding
            if (r11 == 0) goto L_0x00e1
            r0.removeAllViews()
            r0.addView(r6)
            goto L_0x00e4
        L_0x00e1:
            r0.addView(r6)
        L_0x00e4:
            androidx.slice.widget.RowStyle r11 = r9.mRowStyle
            if (r11 == 0) goto L_0x00fd
            int r0 = r11.mIconSize
            if (r0 <= 0) goto L_0x00ed
            goto L_0x00ef
        L_0x00ed:
            int r0 = r9.mIconSize
        L_0x00ef:
            r9.mIconSize = r0
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mImageSize
            if (r11 <= 0) goto L_0x00f9
            goto L_0x00fb
        L_0x00f9:
            int r11 = r9.mImageSize
        L_0x00fb:
            r9.mImageSize = r11
        L_0x00fd:
            android.view.ViewGroup$LayoutParams r11 = r6.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r11 = (android.widget.LinearLayout.LayoutParams) r11
            if (r4 == 0) goto L_0x0110
            int r0 = r12.getIntrinsicWidth()
            float r0 = (float) r0
            float r0 = r0 / r5
            int r0 = java.lang.Math.round(r0)
            goto L_0x0112
        L_0x0110:
            int r0 = r9.mImageSize
        L_0x0112:
            r11.width = r0
            if (r4 == 0) goto L_0x0121
            int r12 = r12.getIntrinsicHeight()
            float r12 = (float) r12
            float r12 = r12 / r5
            int r12 = java.lang.Math.round(r12)
            goto L_0x0123
        L_0x0121:
            int r12 = r9.mImageSize
        L_0x0123:
            r11.height = r12
            r6.setLayoutParams(r11)
            if (r1 == 0) goto L_0x0139
            int r11 = r9.mImageSize
            if (r11 != r10) goto L_0x0133
            int r9 = r9.mIconSize
            int r9 = r9 / 2
            goto L_0x013a
        L_0x0133:
            int r9 = r9.mIconSize
            int r11 = r11 - r9
            int r9 = r11 / 2
            goto L_0x013a
        L_0x0139:
            r9 = r2
        L_0x013a:
            r6.setPadding(r9, r9, r9, r9)
            r1 = r6
            goto L_0x016f
        L_0x013f:
            if (r4 == 0) goto L_0x016f
            android.widget.TextView r1 = new android.widget.TextView
            android.content.Context r11 = r9.getContext()
            r1.<init>(r11)
            android.content.Context r11 = r9.getContext()
            long r4 = r10.getLong()
            java.lang.CharSequence r10 = androidx.slice.widget.SliceViewUtil.getTimestampString(r11, r4)
            r1.setText(r10)
            androidx.slice.widget.SliceStyle r10 = r9.mSliceStyle
            if (r10 == 0) goto L_0x016c
            int r10 = r10.mSubtitleSize
            float r10 = (float) r10
            r1.setTextSize(r2, r10)
            androidx.slice.widget.RowStyle r9 = r9.mRowStyle
            int r9 = r9.getSubtitleColor()
            r1.setTextColor(r9)
        L_0x016c:
            r0.addView(r1)
        L_0x016f:
            if (r1 == 0) goto L_0x0172
            r2 = r3
        L_0x0172:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowView.addItem(androidx.slice.SliceItem, int, boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x014c  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x015e  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0160  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0174  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addSubtitle(boolean r10) {
        /*
            r9 = this;
            androidx.slice.widget.RowContent r0 = r9.mRowContent
            if (r0 == 0) goto L_0x0187
            androidx.slice.SliceItem r1 = r0.mRange
            if (r1 == 0) goto L_0x000e
            androidx.slice.SliceItem r1 = r9.mStartItem
            if (r1 == 0) goto L_0x000e
            goto L_0x0187
        L_0x000e:
            java.util.Objects.requireNonNull(r0)
            androidx.slice.SliceItem r0 = r0.mSubtitleItem
            boolean r1 = r9.mShowLastUpdated
            r2 = 0
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L_0x0092
            long r5 = r9.mLastUpdated
            r7 = -1
            int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x0092
            long r7 = java.lang.System.currentTimeMillis()
            long r7 = r7 - r5
            r5 = 31449600000(0x7528ad000, double:1.55381669354E-313)
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x0046
            long r7 = r7 / r5
            int r1 = (int) r7
            android.content.res.Resources r5 = r9.getResources()
            r6 = 2131820546(0x7f110002, float:1.927381E38)
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
            r7[r4] = r8
            java.lang.String r1 = r5.getQuantityString(r6, r1, r7)
            goto L_0x0080
        L_0x0046:
            r5 = 86400000(0x5265c00, double:4.2687272E-316)
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x0062
            long r7 = r7 / r5
            int r1 = (int) r7
            android.content.res.Resources r5 = r9.getResources()
            r6 = 2131820544(0x7f110000, float:1.9273806E38)
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
            r7[r4] = r8
            java.lang.String r1 = r5.getQuantityString(r6, r1, r7)
            goto L_0x0080
        L_0x0062:
            r5 = 60000(0xea60, double:2.9644E-319)
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x007f
            long r7 = r7 / r5
            int r1 = (int) r7
            android.content.res.Resources r5 = r9.getResources()
            r6 = 2131820545(0x7f110001, float:1.9273808E38)
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)
            r7[r4] = r8
            java.lang.String r1 = r5.getQuantityString(r6, r1, r7)
            goto L_0x0080
        L_0x007f:
            r1 = r2
        L_0x0080:
            if (r1 == 0) goto L_0x0092
            android.content.res.Resources r5 = r9.getResources()
            r6 = 2131951653(0x7f130025, float:1.9539727E38)
            java.lang.Object[] r7 = new java.lang.Object[r3]
            r7[r4] = r1
            java.lang.String r1 = r5.getString(r6, r7)
            goto L_0x0093
        L_0x0092:
            r1 = r2
        L_0x0093:
            if (r0 == 0) goto L_0x0099
            java.lang.CharSequence r2 = r0.getSanitizedText()
        L_0x0099:
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            if (r5 == 0) goto L_0x00ac
            if (r0 == 0) goto L_0x00aa
            java.lang.String r5 = "partial"
            boolean r0 = r0.hasHint(r5)
            if (r0 == 0) goto L_0x00aa
            goto L_0x00ac
        L_0x00aa:
            r0 = r4
            goto L_0x00ad
        L_0x00ac:
            r0 = r3
        L_0x00ad:
            if (r0 == 0) goto L_0x00ea
            android.widget.TextView r5 = r9.mSecondaryText
            r5.setText(r2)
            androidx.slice.widget.SliceStyle r5 = r9.mSliceStyle
            if (r5 == 0) goto L_0x00ea
            android.widget.TextView r6 = r9.mSecondaryText
            boolean r7 = r9.mIsHeader
            if (r7 == 0) goto L_0x00c1
            int r5 = r5.mHeaderSubtitleSize
            goto L_0x00c3
        L_0x00c1:
            int r5 = r5.mSubtitleSize
        L_0x00c3:
            float r5 = (float) r5
            r6.setTextSize(r4, r5)
            android.widget.TextView r5 = r9.mSecondaryText
            androidx.slice.widget.RowStyle r6 = r9.mRowStyle
            int r6 = r6.getSubtitleColor()
            r5.setTextColor(r6)
            boolean r5 = r9.mIsHeader
            if (r5 == 0) goto L_0x00de
            androidx.slice.widget.SliceStyle r5 = r9.mSliceStyle
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mVerticalHeaderTextPadding
            goto L_0x00e5
        L_0x00de:
            androidx.slice.widget.SliceStyle r5 = r9.mSliceStyle
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mVerticalTextPadding
        L_0x00e5:
            android.widget.TextView r6 = r9.mSecondaryText
            r6.setPadding(r4, r5, r4, r4)
        L_0x00ea:
            r5 = 2
            if (r1 == 0) goto L_0x0138
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0104
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = " · "
            r2.append(r6)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
        L_0x0104:
            android.text.SpannableString r2 = new android.text.SpannableString
            r2.<init>(r1)
            android.text.style.StyleSpan r6 = new android.text.style.StyleSpan
            r6.<init>(r5)
            int r7 = r1.length()
            r2.setSpan(r6, r4, r7, r4)
            android.widget.TextView r6 = r9.mLastUpdatedText
            r6.setText(r2)
            androidx.slice.widget.SliceStyle r2 = r9.mSliceStyle
            if (r2 == 0) goto L_0x0138
            android.widget.TextView r6 = r9.mLastUpdatedText
            boolean r7 = r9.mIsHeader
            if (r7 == 0) goto L_0x0127
            int r2 = r2.mHeaderSubtitleSize
            goto L_0x0129
        L_0x0127:
            int r2 = r2.mSubtitleSize
        L_0x0129:
            float r2 = (float) r2
            r6.setTextSize(r4, r2)
            android.widget.TextView r2 = r9.mLastUpdatedText
            androidx.slice.widget.RowStyle r6 = r9.mRowStyle
            int r6 = r6.getSubtitleColor()
            r2.setTextColor(r6)
        L_0x0138:
            android.widget.TextView r2 = r9.mLastUpdatedText
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            r7 = 8
            if (r6 == 0) goto L_0x0144
            r6 = r7
            goto L_0x0145
        L_0x0144:
            r6 = r4
        L_0x0145:
            r2.setVisibility(r6)
            android.widget.TextView r2 = r9.mSecondaryText
            if (r0 == 0) goto L_0x014d
            r7 = r4
        L_0x014d:
            r2.setVisibility(r7)
            androidx.slice.widget.RowContent r2 = r9.mRowContent
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsHeader
            if (r2 == 0) goto L_0x0160
            boolean r2 = r9.mAllowTwoLines
            if (r2 == 0) goto L_0x015e
            goto L_0x0160
        L_0x015e:
            r2 = r4
            goto L_0x0161
        L_0x0160:
            r2 = r3
        L_0x0161:
            if (r2 == 0) goto L_0x016e
            if (r10 != 0) goto L_0x016e
            if (r0 == 0) goto L_0x016e
            boolean r10 = android.text.TextUtils.isEmpty(r1)
            if (r10 == 0) goto L_0x016e
            goto L_0x016f
        L_0x016e:
            r5 = r3
        L_0x016f:
            android.widget.TextView r10 = r9.mSecondaryText
            if (r5 != r3) goto L_0x0174
            goto L_0x0175
        L_0x0174:
            r3 = r4
        L_0x0175:
            r10.setSingleLine(r3)
            android.widget.TextView r10 = r9.mSecondaryText
            r10.setMaxLines(r5)
            android.widget.TextView r10 = r9.mSecondaryText
            r10.requestLayout()
            android.widget.TextView r9 = r9.mLastUpdatedText
            r9.requestLayout()
        L_0x0187:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowView.addSubtitle(boolean):void");
    }

    public final int getRowContentHeight() {
        int height = this.mRowContent.getHeight(this.mSliceStyle, this.mViewPolicy);
        if (this.mRangeBar != null && this.mStartItem == null) {
            SliceStyle sliceStyle = this.mSliceStyle;
            Objects.requireNonNull(sliceStyle);
            height -= sliceStyle.mRowRangeHeight;
        }
        if (this.mSelectionSpinner == null) {
            return height;
        }
        SliceStyle sliceStyle2 = this.mSliceStyle;
        Objects.requireNonNull(sliceStyle2);
        return height - sliceStyle2.mRowSelectionHeight;
    }

    public final void onClick(View view) {
        SliceActionView sliceActionView;
        SliceActionView.SliceActionLoadingListener sliceActionLoadingListener;
        SliceActionImpl sliceActionImpl;
        SliceActionImpl sliceActionImpl2 = this.mRowAction;
        if (sliceActionImpl2 != null && sliceActionImpl2.mActionItem != null) {
            if (sliceActionImpl2.getSubtype() != null) {
                String subtype = this.mRowAction.getSubtype();
                Objects.requireNonNull(subtype);
                char c = 65535;
                switch (subtype.hashCode()) {
                    case -868304044:
                        if (subtype.equals("toggle")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 759128640:
                        if (subtype.equals("time_picker")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1250407999:
                        if (subtype.equals("date_picker")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        sliceActionView = this.mToggles.get(this.mRowAction);
                        break;
                    case 1:
                        onClickPicker(false);
                        return;
                    case 2:
                        onClickPicker(true);
                        return;
                    default:
                        sliceActionView = this.mActions.get(this.mRowAction);
                        break;
                }
            } else {
                sliceActionView = this.mActions.get(this.mRowAction);
            }
            if (sliceActionView == null || (view instanceof SliceActionView)) {
                RowContent rowContent = this.mRowContent;
                Objects.requireNonNull(rowContent);
                if (rowContent.mIsHeader) {
                    performClick();
                    return;
                }
                try {
                    SliceActionImpl sliceActionImpl3 = this.mRowAction;
                    Objects.requireNonNull(sliceActionImpl3);
                    this.mShowActionSpinner = sliceActionImpl3.mActionItem.fireActionInternal(getContext(), (Intent) null);
                    SliceView.OnSliceActionListener onSliceActionListener = this.mObserver;
                    if (onSliceActionListener != null) {
                        Objects.requireNonNull(this.mRowAction);
                        onSliceActionListener.onSliceAction();
                    }
                    if (this.mShowActionSpinner && (sliceActionLoadingListener = this.mLoadingListener) != null) {
                        SliceActionImpl sliceActionImpl4 = this.mRowAction;
                        Objects.requireNonNull(sliceActionImpl4);
                        ((SliceAdapter) sliceActionLoadingListener).onSliceActionLoading(sliceActionImpl4.mSliceItem, this.mRowIndex);
                        Set<SliceItem> set = this.mLoadingActions;
                        SliceActionImpl sliceActionImpl5 = this.mRowAction;
                        Objects.requireNonNull(sliceActionImpl5);
                        set.add(sliceActionImpl5.mSliceItem);
                    }
                    updateActionSpinner();
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RowView", "PendingIntent for slice cannot be sent", e);
                }
            } else {
                SliceActionImpl sliceActionImpl6 = sliceActionView.mSliceAction;
                if (sliceActionImpl6 != null) {
                    if (!sliceActionImpl6.isToggle()) {
                        sliceActionView.sendActionInternal();
                    } else if (sliceActionView.mActionView != null && (sliceActionImpl = sliceActionView.mSliceAction) != null && sliceActionImpl.isToggle()) {
                        ((Checkable) sliceActionView.mActionView).toggle();
                    }
                }
            }
        }
    }

    public final void onClickPicker(boolean z) {
        if (this.mRowAction != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("ASDF");
            sb.append(z);
            sb.append(":");
            SliceActionImpl sliceActionImpl = this.mRowAction;
            Objects.requireNonNull(sliceActionImpl);
            sb.append(sliceActionImpl.mSliceItem);
            Log.d("ASDF", sb.toString());
            SliceActionImpl sliceActionImpl2 = this.mRowAction;
            Objects.requireNonNull(sliceActionImpl2);
            SliceItem findSubtype = SliceQuery.findSubtype(sliceActionImpl2.mSliceItem, "long", "millis");
            if (findSubtype != null) {
                int i = this.mRowIndex;
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(findSubtype.getLong()));
                if (z) {
                    Context context = getContext();
                    SliceActionImpl sliceActionImpl3 = this.mRowAction;
                    Objects.requireNonNull(sliceActionImpl3);
                    new DatePickerDialog(context, C1777R.style.DialogTheme, new DateSetListener(sliceActionImpl3.mSliceItem, i), instance.get(1), instance.get(2), instance.get(5)).show();
                    return;
                }
                Context context2 = getContext();
                SliceActionImpl sliceActionImpl4 = this.mRowAction;
                Objects.requireNonNull(sliceActionImpl4);
                new TimePickerDialog(context2, C1777R.style.DialogTheme, new TimeSetListener(sliceActionImpl4.mSliceItem, i), instance.get(11), instance.get(12), false).show();
            }
        }
    }

    public final void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mSelectionItem != null && adapterView == this.mSelectionSpinner && i >= 0 && i < this.mSelectionOptionKeys.size()) {
            SliceView.OnSliceActionListener onSliceActionListener = this.mObserver;
            if (onSliceActionListener != null) {
                onSliceActionListener.onSliceAction();
            }
            try {
                if (this.mSelectionItem.fireActionInternal(getContext(), new Intent().addFlags(268435456).putExtra("android.app.slice.extra.SELECTION", this.mSelectionOptionKeys.get(i)))) {
                    this.mShowActionSpinner = true;
                    SliceActionView.SliceActionLoadingListener sliceActionLoadingListener = this.mLoadingListener;
                    if (sliceActionLoadingListener != null) {
                        SliceActionImpl sliceActionImpl = this.mRowAction;
                        Objects.requireNonNull(sliceActionImpl);
                        ((SliceAdapter) sliceActionLoadingListener).onSliceActionLoading(sliceActionImpl.mSliceItem, this.mRowIndex);
                        Set<SliceItem> set = this.mLoadingActions;
                        SliceActionImpl sliceActionImpl2 = this.mRowAction;
                        Objects.requireNonNull(sliceActionImpl2);
                        set.add(sliceActionImpl2.mSliceItem);
                    }
                    updateActionSpinner();
                }
            } catch (PendingIntent.CanceledException e) {
                Log.e("RowView", "PendingIntent for slice cannot be sent", e);
            }
        }
    }

    public final void resetViewState() {
        this.mRootView.setVisibility(0);
        setLayoutDirection(2);
        setViewClickable(this.mRootView, false);
        setViewClickable(this.mContent, false);
        this.mStartContainer.removeAllViews();
        this.mEndContainer.removeAllViews();
        this.mEndContainer.setVisibility(8);
        this.mPrimaryText.setText((CharSequence) null);
        this.mSecondaryText.setText((CharSequence) null);
        this.mLastUpdatedText.setText((CharSequence) null);
        this.mLastUpdatedText.setVisibility(8);
        this.mToggles.clear();
        this.mActions.clear();
        this.mRowAction = null;
        this.mBottomDivider.setVisibility(8);
        this.mActionDivider.setVisibility(8);
        Button button = this.mSeeMoreView;
        if (button != null) {
            this.mRootView.removeView(button);
            this.mSeeMoreView = null;
        }
        this.mIsRangeSliding = false;
        this.mRangeItem = null;
        this.mRangeMinValue = 0;
        this.mRangeMaxValue = 0;
        this.mRangeValue = 0;
        this.mLastSentRangeUpdate = 0;
        this.mHandler = null;
        View view = this.mRangeBar;
        if (view != null) {
            if (this.mStartItem == null) {
                removeView(view);
            } else {
                this.mContent.removeView(view);
            }
            this.mRangeBar = null;
        }
        this.mSubContent.setVisibility(0);
        this.mStartItem = null;
        this.mActionSpinner.setVisibility(8);
        Spinner spinner = this.mSelectionSpinner;
        if (spinner != null) {
            removeView(spinner);
            this.mSelectionSpinner = null;
        }
        this.mSelectionItem = null;
    }

    public final void sendSliderValue() {
        if (this.mRangeItem != null) {
            try {
                this.mLastSentRangeUpdate = System.currentTimeMillis();
                SliceItem sliceItem = this.mRangeItem;
                Context context = getContext();
                Intent putExtra = new Intent().addFlags(268435456).putExtra("android.app.slice.extra.RANGE_VALUE", this.mRangeValue);
                Objects.requireNonNull(sliceItem);
                sliceItem.fireActionInternal(context, putExtra);
                SliceView.OnSliceActionListener onSliceActionListener = this.mObserver;
                if (onSliceActionListener != null) {
                    onSliceActionListener.onSliceAction();
                }
            } catch (PendingIntent.CanceledException e) {
                Log.e("RowView", "PendingIntent for slice cannot be sent", e);
            }
        }
    }

    public final void setAllowTwoLines(boolean z) {
        this.mAllowTwoLines = z;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    public final void setLastUpdated(long j) {
        boolean z;
        this.mLastUpdated = j;
        RowContent rowContent = this.mRowContent;
        if (rowContent != null) {
            if (rowContent.mTitleItem != null) {
                Objects.requireNonNull(rowContent);
                if (TextUtils.isEmpty(rowContent.mTitleItem.getSanitizedText())) {
                    z = true;
                    addSubtitle(z);
                }
            }
            z = false;
            addSubtitle(z);
        }
    }

    public final void setLoadingActions(Set<SliceItem> set) {
        if (set == null) {
            this.mLoadingActions.clear();
            this.mShowActionSpinner = false;
        } else {
            this.mLoadingActions = set;
        }
        updateEndItems();
        updateActionSpinner();
    }

    public final void setShowLastUpdated(boolean z) {
        this.mShowLastUpdated = z;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    public final void setSliceActions(List<SliceAction> list) {
        this.mHeaderActions = list;
        if (this.mRowContent != null) {
            updateEndItems();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0044, code lost:
        if (r2 != false) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setSliceItem(androidx.slice.widget.SliceContent r4, boolean r5, int r6, int r7, androidx.slice.widget.SliceView.OnSliceActionListener r8) {
        /*
            r3 = this;
            r3.mObserver = r8
            androidx.slice.widget.RowContent r7 = r3.mRowContent
            r8 = 0
            if (r7 == 0) goto L_0x0047
            boolean r7 = r7.isValid()
            if (r7 == 0) goto L_0x0047
            androidx.slice.widget.RowContent r7 = r3.mRowContent
            if (r7 == 0) goto L_0x0019
            androidx.slice.SliceStructure r0 = new androidx.slice.SliceStructure
            androidx.slice.SliceItem r7 = r7.mSliceItem
            r0.<init>((androidx.slice.SliceItem) r7)
            goto L_0x001a
        L_0x0019:
            r0 = 0
        L_0x001a:
            androidx.slice.SliceStructure r7 = new androidx.slice.SliceStructure
            androidx.slice.SliceItem r1 = r4.mSliceItem
            androidx.slice.Slice r1 = r1.getSlice()
            r7.<init>((androidx.slice.Slice) r1)
            r1 = 1
            if (r0 == 0) goto L_0x0030
            boolean r2 = r0.equals(r7)
            if (r2 == 0) goto L_0x0030
            r2 = r1
            goto L_0x0031
        L_0x0030:
            r2 = r8
        L_0x0031:
            if (r0 == 0) goto L_0x0041
            android.net.Uri r0 = r0.mUri
            if (r0 == 0) goto L_0x0041
            android.net.Uri r7 = r7.mUri
            boolean r7 = r0.equals(r7)
            if (r7 == 0) goto L_0x0041
            r7 = r1
            goto L_0x0042
        L_0x0041:
            r7 = r8
        L_0x0042:
            if (r7 == 0) goto L_0x0047
            if (r2 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r1 = r8
        L_0x0048:
            r3.mShowActionSpinner = r8
            r3.mIsHeader = r5
            androidx.slice.widget.RowContent r4 = (androidx.slice.widget.RowContent) r4
            r3.mRowContent = r4
            r3.mRowIndex = r6
            r3.populateViews(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowView.setSliceItem(androidx.slice.widget.SliceContent, boolean, int, int, androidx.slice.widget.SliceView$OnSliceActionListener):void");
    }

    public final void setStyle(SliceStyle sliceStyle, RowStyle rowStyle) {
        boolean z;
        this.mSliceStyle = sliceStyle;
        this.mRowStyle = rowStyle;
        if (sliceStyle != null) {
            setViewSidePaddings(this.mStartContainer, rowStyle.mTitleItemStartPadding, rowStyle.mTitleItemEndPadding);
            LinearLayout linearLayout = this.mContent;
            RowStyle rowStyle2 = this.mRowStyle;
            Objects.requireNonNull(rowStyle2);
            int i = rowStyle2.mContentStartPadding;
            RowStyle rowStyle3 = this.mRowStyle;
            Objects.requireNonNull(rowStyle3);
            setViewSidePaddings(linearLayout, i, rowStyle3.mContentEndPadding);
            TextView textView = this.mPrimaryText;
            RowStyle rowStyle4 = this.mRowStyle;
            Objects.requireNonNull(rowStyle4);
            int i2 = rowStyle4.mTitleStartPadding;
            RowStyle rowStyle5 = this.mRowStyle;
            Objects.requireNonNull(rowStyle5);
            setViewSidePaddings(textView, i2, rowStyle5.mTitleEndPadding);
            LinearLayout linearLayout2 = this.mSubContent;
            RowStyle rowStyle6 = this.mRowStyle;
            Objects.requireNonNull(rowStyle6);
            int i3 = rowStyle6.mSubContentStartPadding;
            RowStyle rowStyle7 = this.mRowStyle;
            Objects.requireNonNull(rowStyle7);
            setViewSidePaddings(linearLayout2, i3, rowStyle7.mSubContentEndPadding);
            LinearLayout linearLayout3 = this.mEndContainer;
            RowStyle rowStyle8 = this.mRowStyle;
            Objects.requireNonNull(rowStyle8);
            int i4 = rowStyle8.mEndItemStartPadding;
            RowStyle rowStyle9 = this.mRowStyle;
            Objects.requireNonNull(rowStyle9);
            setViewSidePaddings(linearLayout3, i4, rowStyle9.mEndItemEndPadding);
            View view = this.mBottomDivider;
            RowStyle rowStyle10 = this.mRowStyle;
            Objects.requireNonNull(rowStyle10);
            int i5 = rowStyle10.mBottomDividerStartPadding;
            RowStyle rowStyle11 = this.mRowStyle;
            Objects.requireNonNull(rowStyle11);
            int i6 = rowStyle11.mBottomDividerEndPadding;
            if (i5 >= 0 || i6 >= 0) {
                z = false;
            } else {
                z = true;
            }
            if (view != null && !z) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (i5 >= 0) {
                    marginLayoutParams.setMarginStart(i5);
                }
                if (i6 >= 0) {
                    marginLayoutParams.setMarginEnd(i6);
                }
                view.setLayoutParams(marginLayoutParams);
            }
            View view2 = this.mActionDivider;
            RowStyle rowStyle12 = this.mRowStyle;
            Objects.requireNonNull(rowStyle12);
            int i7 = rowStyle12.mActionDividerHeight;
            if (view2 != null && i7 >= 0) {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                layoutParams.height = i7;
                view2.setLayoutParams(layoutParams);
            }
            if (this.mRowStyle.getTintColor() != -1) {
                setTint(this.mRowStyle.getTintColor());
            }
        }
    }

    public final void setTint(int i) {
        this.mTintColor = i;
        if (this.mRowContent != null) {
            populateViews(true);
        }
    }

    public final void updateActionSpinner() {
        int i;
        ProgressBar progressBar = this.mActionSpinner;
        if (this.mShowActionSpinner) {
            i = 0;
        } else {
            i = 8;
        }
        progressBar.setVisibility(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c8, code lost:
        if (r7 != false) goto L_0x00ca;
     */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x014d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateEndItems() {
        /*
            r12 = this;
            androidx.slice.widget.RowContent r0 = r12.mRowContent
            if (r0 == 0) goto L_0x0153
            androidx.slice.SliceItem r0 = r0.mRange
            if (r0 == 0) goto L_0x000e
            androidx.slice.SliceItem r0 = r12.mStartItem
            if (r0 != 0) goto L_0x000e
            goto L_0x0153
        L_0x000e:
            android.widget.LinearLayout r0 = r12.mEndContainer
            r0.removeAllViews()
            androidx.slice.widget.RowContent r0 = r12.mRowContent
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList<androidx.slice.SliceItem> r0 = r0.mEndItems
            java.util.List<androidx.slice.core.SliceAction> r1 = r12.mHeaderActions
            if (r1 == 0) goto L_0x001f
            r0 = r1
        L_0x001f:
            androidx.slice.widget.RowContent r1 = r12.mRowContent
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mIsHeader
            if (r1 == 0) goto L_0x0040
            androidx.slice.SliceItem r1 = r12.mStartItem
            if (r1 == 0) goto L_0x0040
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x0040
            androidx.slice.widget.RowContent r1 = r12.mRowContent
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.mShowTitleItems
            if (r1 != 0) goto L_0x0040
            androidx.slice.SliceItem r1 = r12.mStartItem
            r0.add(r1)
        L_0x0040:
            r1 = 0
            r2 = 0
            r6 = r1
            r3 = r2
            r4 = r3
            r5 = r4
            r7 = r5
        L_0x0047:
            int r8 = r0.size()
            java.lang.String r9 = "action"
            r10 = 1
            if (r3 >= r8) goto L_0x00ab
            java.lang.Object r8 = r0.get(r3)
            boolean r8 = r8 instanceof androidx.slice.SliceItem
            if (r8 == 0) goto L_0x005f
            java.lang.Object r8 = r0.get(r3)
            androidx.slice.SliceItem r8 = (androidx.slice.SliceItem) r8
            goto L_0x006a
        L_0x005f:
            java.lang.Object r8 = r0.get(r3)
            androidx.slice.core.SliceActionImpl r8 = (androidx.slice.core.SliceActionImpl) r8
            java.util.Objects.requireNonNull(r8)
            androidx.slice.SliceItem r8 = r8.mSliceItem
        L_0x006a:
            r11 = 3
            if (r4 >= r11) goto L_0x00a8
            int r11 = r12.mTintColor
            boolean r11 = r12.addItem(r8, r11, r2)
            if (r11 == 0) goto L_0x00a8
            if (r6 != 0) goto L_0x007e
            androidx.slice.SliceItem r11 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r8, (java.lang.String) r9, (java.lang.String[]) r1, (java.lang.String[]) r1)
            if (r11 == 0) goto L_0x007e
            r6 = r8
        L_0x007e:
            int r4 = r4 + 1
            if (r4 != r10) goto L_0x00a8
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r5 = r12.mToggles
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0098
            androidx.slice.Slice r5 = r8.getSlice()
            java.lang.String r7 = "image"
            androidx.slice.SliceItem r5 = androidx.slice.core.SliceQuery.find((androidx.slice.Slice) r5, (java.lang.String) r7, (java.lang.String[]) r1, (java.lang.String[]) r1)
            if (r5 != 0) goto L_0x0098
            r5 = r10
            goto L_0x0099
        L_0x0098:
            r5 = r2
        L_0x0099:
            int r7 = r0.size()
            if (r7 != r10) goto L_0x00a7
            androidx.slice.SliceItem r7 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r8, (java.lang.String) r9, (java.lang.String[]) r1, (java.lang.String[]) r1)
            if (r7 == 0) goto L_0x00a7
            r7 = r10
            goto L_0x00a8
        L_0x00a7:
            r7 = r2
        L_0x00a8:
            int r3 = r3 + 1
            goto L_0x0047
        L_0x00ab:
            android.widget.LinearLayout r0 = r12.mEndContainer
            r3 = 8
            if (r4 <= 0) goto L_0x00b3
            r8 = r2
            goto L_0x00b4
        L_0x00b3:
            r8 = r3
        L_0x00b4:
            r0.setVisibility(r8)
            android.view.View r0 = r12.mActionDivider
            androidx.slice.core.SliceActionImpl r8 = r12.mRowAction
            if (r8 == 0) goto L_0x00cb
            if (r5 != 0) goto L_0x00ca
            androidx.slice.widget.RowContent r5 = r12.mRowContent
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mShowActionDivider
            if (r5 == 0) goto L_0x00cb
            if (r7 == 0) goto L_0x00cb
        L_0x00ca:
            r3 = r2
        L_0x00cb:
            r0.setVisibility(r3)
            androidx.slice.SliceItem r0 = r12.mStartItem
            if (r0 == 0) goto L_0x00da
            androidx.slice.SliceItem r0 = androidx.slice.core.SliceQuery.find((androidx.slice.SliceItem) r0, (java.lang.String) r9, (java.lang.String[]) r1, (java.lang.String[]) r1)
            if (r0 == 0) goto L_0x00da
            r0 = r10
            goto L_0x00db
        L_0x00da:
            r0 = r2
        L_0x00db:
            if (r6 == 0) goto L_0x00df
            r1 = r10
            goto L_0x00e0
        L_0x00df:
            r1 = r2
        L_0x00e0:
            androidx.slice.core.SliceActionImpl r3 = r12.mRowAction
            if (r3 == 0) goto L_0x00ea
            android.widget.LinearLayout r0 = r12.mRootView
            r12.setViewClickable(r0, r10)
            goto L_0x0131
        L_0x00ea:
            if (r1 == r0) goto L_0x0131
            if (r4 == r10) goto L_0x00f0
            if (r0 == 0) goto L_0x0131
        L_0x00f0:
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r0 = r12.mToggles
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x010b
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r0 = r12.mToggles
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
            java.lang.Object r0 = r0.next()
            androidx.slice.core.SliceActionImpl r0 = (androidx.slice.core.SliceActionImpl) r0
            r12.mRowAction = r0
            goto L_0x012a
        L_0x010b:
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r0 = r12.mActions
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x012a
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r0 = r12.mActions
            int r0 = r0.size()
            if (r0 != r10) goto L_0x012a
            android.util.ArrayMap<androidx.slice.core.SliceActionImpl, androidx.slice.widget.SliceActionView> r0 = r12.mActions
            java.lang.Object r0 = r0.valueAt(r2)
            androidx.slice.widget.SliceActionView r0 = (androidx.slice.widget.SliceActionView) r0
            java.util.Objects.requireNonNull(r0)
            androidx.slice.core.SliceActionImpl r0 = r0.mSliceAction
            r12.mRowAction = r0
        L_0x012a:
            android.widget.LinearLayout r0 = r12.mRootView
            r12.setViewClickable(r0, r10)
            r0 = r10
            goto L_0x0132
        L_0x0131:
            r0 = r2
        L_0x0132:
            androidx.slice.core.SliceActionImpl r1 = r12.mRowAction
            if (r1 == 0) goto L_0x0144
            if (r0 != 0) goto L_0x0144
            java.util.Set<androidx.slice.SliceItem> r0 = r12.mLoadingActions
            androidx.slice.SliceItem r1 = r1.mSliceItem
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0144
            r12.mShowActionSpinner = r10
        L_0x0144:
            android.widget.LinearLayout r12 = r12.mRootView
            boolean r0 = r12.isClickable()
            if (r0 == 0) goto L_0x014d
            goto L_0x014e
        L_0x014d:
            r2 = 2
        L_0x014e:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setImportantForAccessibility(r12, r2)
        L_0x0153:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.widget.RowView.updateEndItems():void");
    }

    public RowView(Context context) {
        super(context);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(C1777R.layout.abc_slice_small_template, this, false);
        this.mRootView = linearLayout;
        addView(linearLayout);
        this.mStartContainer = (LinearLayout) findViewById(C1777R.C1779id.icon_frame);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(16908290);
        this.mContent = linearLayout2;
        this.mSubContent = (LinearLayout) findViewById(C1777R.C1779id.subcontent);
        this.mPrimaryText = (TextView) findViewById(16908310);
        this.mSecondaryText = (TextView) findViewById(16908304);
        this.mLastUpdatedText = (TextView) findViewById(C1777R.C1779id.last_updated);
        this.mBottomDivider = findViewById(C1777R.C1779id.bottom_divider);
        this.mActionDivider = findViewById(C1777R.C1779id.action_divider);
        ProgressBar progressBar = (ProgressBar) findViewById(C1777R.C1779id.action_sent_indicator);
        this.mActionSpinner = progressBar;
        int colorAttr = SliceViewUtil.getColorAttr(getContext(), C1777R.attr.colorControlHighlight);
        Drawable indeterminateDrawable = progressBar.getIndeterminateDrawable();
        if (!(indeterminateDrawable == null || colorAttr == 0)) {
            indeterminateDrawable.setColorFilter(colorAttr, PorterDuff.Mode.MULTIPLY);
            progressBar.setProgressDrawable(indeterminateDrawable);
        }
        this.mEndContainer = (LinearLayout) findViewById(16908312);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 2);
        ViewCompat.Api16Impl.setImportantForAccessibility(linearLayout2, 2);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        LinearLayout linearLayout = this.mRootView;
        linearLayout.layout(paddingLeft, this.mInsetTop, linearLayout.getMeasuredWidth() + paddingLeft, getRowContentHeight() + this.mInsetTop);
        if (this.mRangeBar != null && this.mStartItem == null) {
            SliceStyle sliceStyle = this.mSliceStyle;
            Objects.requireNonNull(sliceStyle);
            int rowContentHeight = getRowContentHeight() + ((sliceStyle.mRowRangeHeight - this.mMeasuredRangeHeight) / 2) + this.mInsetTop;
            View view = this.mRangeBar;
            view.layout(paddingLeft, rowContentHeight, view.getMeasuredWidth() + paddingLeft, this.mMeasuredRangeHeight + rowContentHeight);
        } else if (this.mSelectionSpinner != null) {
            int rowContentHeight2 = getRowContentHeight() + this.mInsetTop;
            Spinner spinner = this.mSelectionSpinner;
            spinner.layout(paddingLeft, rowContentHeight2, spinner.getMeasuredWidth() + paddingLeft, this.mSelectionSpinner.getMeasuredHeight() + rowContentHeight2);
        }
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int rowContentHeight = getRowContentHeight();
        if (rowContentHeight != 0) {
            this.mRootView.setVisibility(0);
            measureChild(this.mRootView, i, View.MeasureSpec.makeMeasureSpec(rowContentHeight + this.mInsetTop + this.mInsetBottom, 1073741824));
            i3 = this.mRootView.getMeasuredWidth();
        } else {
            this.mRootView.setVisibility(8);
            i3 = 0;
        }
        View view = this.mRangeBar;
        if (view == null || this.mStartItem != null) {
            Spinner spinner = this.mSelectionSpinner;
            if (spinner != null) {
                SliceStyle sliceStyle = this.mSliceStyle;
                Objects.requireNonNull(sliceStyle);
                measureChild(spinner, i, View.MeasureSpec.makeMeasureSpec(sliceStyle.mRowSelectionHeight + this.mInsetTop + this.mInsetBottom, 1073741824));
                i3 = Math.max(i3, this.mSelectionSpinner.getMeasuredWidth());
            }
        } else {
            SliceStyle sliceStyle2 = this.mSliceStyle;
            Objects.requireNonNull(sliceStyle2);
            measureChild(view, i, View.MeasureSpec.makeMeasureSpec(sliceStyle2.mRowRangeHeight + this.mInsetTop + this.mInsetBottom, 1073741824));
            this.mMeasuredRangeHeight = this.mRangeBar.getMeasuredHeight();
            i3 = Math.max(i3, this.mRangeBar.getMeasuredWidth());
        }
        int max = Math.max(i3 + this.mInsetStart + this.mInsetEnd, getSuggestedMinimumWidth());
        RowContent rowContent = this.mRowContent;
        if (rowContent != null) {
            i4 = rowContent.getHeight(this.mSliceStyle, this.mViewPolicy);
        } else {
            i4 = 0;
        }
        setMeasuredDimension(View.resolveSizeAndState(max, i, 0), i4 + this.mInsetTop + this.mInsetBottom);
    }

    public final void setInsets(int i, int i2, int i3, int i4) {
        super.setInsets(i, i2, i3, i4);
        setPadding(i, i2, i3, i4);
    }
}
