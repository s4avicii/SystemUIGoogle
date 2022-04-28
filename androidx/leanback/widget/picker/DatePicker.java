package androidx.leanback.widget.picker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.picker.PickerUtility;
import com.android.p012wm.shell.C1777R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.WeakHashMap;

public class DatePicker extends Picker {
    public static final int[] DATE_FIELDS = {5, 2, 1};
    public int mColDayIndex;
    public int mColMonthIndex;
    public int mColYearIndex;
    public PickerUtility.DateConstant mConstant;
    public Calendar mCurrentDate;
    public final SimpleDateFormat mDateFormat;
    public String mDatePickerFormat;
    public PickerColumn mDayColumn;
    public Calendar mMaxDate;
    public Calendar mMinDate;
    public PickerColumn mMonthColumn;
    public Calendar mTempDate;
    public PickerColumn mYearColumn;

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.datePickerStyle);
    }

    /* JADX INFO: finally extract failed */
    @SuppressLint({"CustomViewStyleable"})
    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Locale locale = Locale.getDefault();
        getContext().getResources();
        this.mConstant = new PickerUtility.DateConstant(locale);
        this.mTempDate = PickerUtility.getCalendarForLocale(this.mTempDate, locale);
        this.mMinDate = PickerUtility.getCalendarForLocale(this.mMinDate, this.mConstant.locale);
        this.mMaxDate = PickerUtility.getCalendarForLocale(this.mMaxDate, this.mConstant.locale);
        this.mCurrentDate = PickerUtility.getCalendarForLocale(this.mCurrentDate, this.mConstant.locale);
        PickerColumn pickerColumn = this.mMonthColumn;
        if (pickerColumn != null) {
            pickerColumn.mStaticLabels = this.mConstant.months;
            setColumnAt(this.mColMonthIndex, pickerColumn);
        }
        int[] iArr = R$styleable.lbDatePicker;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        try {
            String string = obtainStyledAttributes.getString(0);
            String string2 = obtainStyledAttributes.getString(1);
            String string3 = obtainStyledAttributes.getString(2);
            obtainStyledAttributes.recycle();
            this.mTempDate.clear();
            if (TextUtils.isEmpty(string)) {
                this.mTempDate.set(1900, 0, 1);
            } else if (!parseDate(string, this.mTempDate)) {
                this.mTempDate.set(1900, 0, 1);
            }
            this.mMinDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            this.mTempDate.clear();
            if (TextUtils.isEmpty(string2)) {
                this.mTempDate.set(2100, 0, 1);
            } else if (!parseDate(string2, this.mTempDate)) {
                this.mTempDate.set(2100, 0, 1);
            }
            this.mMaxDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
            string3 = TextUtils.isEmpty(string3) ? new String(DateFormat.getDateFormatOrder(context)) : string3;
            string3 = TextUtils.isEmpty(string3) ? new String(DateFormat.getDateFormatOrder(getContext())) : string3;
            if (!TextUtils.equals(this.mDatePickerFormat, string3)) {
                this.mDatePickerFormat = string3;
                List<CharSequence> extractSeparators = extractSeparators();
                if (extractSeparators.size() == string3.length() + 1) {
                    this.mSeparators.clear();
                    this.mSeparators.addAll(extractSeparators);
                    this.mDayColumn = null;
                    this.mMonthColumn = null;
                    this.mYearColumn = null;
                    this.mColMonthIndex = -1;
                    this.mColDayIndex = -1;
                    this.mColYearIndex = -1;
                    String upperCase = string3.toUpperCase(this.mConstant.locale);
                    ArrayList arrayList = new ArrayList(3);
                    for (int i2 = 0; i2 < upperCase.length(); i2++) {
                        char charAt = upperCase.charAt(i2);
                        if (charAt != 'D') {
                            if (charAt != 'M') {
                                if (charAt != 'Y') {
                                    throw new IllegalArgumentException("datePicker format error");
                                } else if (this.mYearColumn == null) {
                                    PickerColumn pickerColumn2 = new PickerColumn();
                                    this.mYearColumn = pickerColumn2;
                                    arrayList.add(pickerColumn2);
                                    this.mColYearIndex = i2;
                                    PickerColumn pickerColumn3 = this.mYearColumn;
                                    Objects.requireNonNull(pickerColumn3);
                                    pickerColumn3.mLabelFormat = "%d";
                                } else {
                                    throw new IllegalArgumentException("datePicker format error");
                                }
                            } else if (this.mMonthColumn == null) {
                                PickerColumn pickerColumn4 = new PickerColumn();
                                this.mMonthColumn = pickerColumn4;
                                arrayList.add(pickerColumn4);
                                PickerColumn pickerColumn5 = this.mMonthColumn;
                                String[] strArr = this.mConstant.months;
                                Objects.requireNonNull(pickerColumn5);
                                pickerColumn5.mStaticLabels = strArr;
                                this.mColMonthIndex = i2;
                            } else {
                                throw new IllegalArgumentException("datePicker format error");
                            }
                        } else if (this.mDayColumn == null) {
                            PickerColumn pickerColumn6 = new PickerColumn();
                            this.mDayColumn = pickerColumn6;
                            arrayList.add(pickerColumn6);
                            PickerColumn pickerColumn7 = this.mDayColumn;
                            Objects.requireNonNull(pickerColumn7);
                            pickerColumn7.mLabelFormat = "%02d";
                            this.mColDayIndex = i2;
                        } else {
                            throw new IllegalArgumentException("datePicker format error");
                        }
                    }
                    setColumns(arrayList);
                    post(new Runnable() {
                        public final /* synthetic */ boolean val$animation = false;

                        /* JADX WARNING: Removed duplicated region for block: B:19:0x005d  */
                        /* JADX WARNING: Removed duplicated region for block: B:24:0x0070  */
                        /* JADX WARNING: Removed duplicated region for block: B:29:0x008f  */
                        /* JADX WARNING: Removed duplicated region for block: B:30:0x0091  */
                        /* JADX WARNING: Removed duplicated region for block: B:33:0x00a1  */
                        /* JADX WARNING: Removed duplicated region for block: B:34:0x00a3  */
                        /* JADX WARNING: Removed duplicated region for block: B:37:0x00a7  */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final void run() {
                            /*
                                r12 = this;
                                androidx.leanback.widget.picker.DatePicker r0 = androidx.leanback.widget.picker.DatePicker.this
                                boolean r12 = r12.val$animation
                                java.util.Objects.requireNonNull(r0)
                                r1 = 3
                                int[] r1 = new int[r1]
                                int r2 = r0.mColDayIndex
                                r3 = 0
                                r1[r3] = r2
                                int r2 = r0.mColMonthIndex
                                r4 = 1
                                r1[r4] = r2
                                int r2 = r0.mColYearIndex
                                r5 = 2
                                r1[r5] = r2
                                r2 = r4
                                r6 = r2
                            L_0x001b:
                                if (r5 < 0) goto L_0x00bb
                                r7 = r1[r5]
                                if (r7 >= 0) goto L_0x0023
                                goto L_0x00b7
                            L_0x0023:
                                int[] r7 = androidx.leanback.widget.picker.DatePicker.DATE_FIELDS
                                r7 = r7[r5]
                                r8 = r1[r5]
                                java.util.ArrayList<androidx.leanback.widget.picker.PickerColumn> r9 = r0.mColumns
                                if (r9 != 0) goto L_0x002f
                                r8 = 0
                                goto L_0x0035
                            L_0x002f:
                                java.lang.Object r8 = r9.get(r8)
                                androidx.leanback.widget.picker.PickerColumn r8 = (androidx.leanback.widget.picker.PickerColumn) r8
                            L_0x0035:
                                if (r2 == 0) goto L_0x004a
                                java.util.Calendar r9 = r0.mMinDate
                                int r9 = r9.get(r7)
                                java.util.Objects.requireNonNull(r8)
                                int r10 = r8.mMinValue
                                if (r9 == r10) goto L_0x0048
                                r8.mMinValue = r9
                            L_0x0046:
                                r9 = r4
                                goto L_0x005a
                            L_0x0048:
                                r9 = r3
                                goto L_0x005a
                            L_0x004a:
                                java.util.Calendar r9 = r0.mCurrentDate
                                int r9 = r9.getActualMinimum(r7)
                                java.util.Objects.requireNonNull(r8)
                                int r10 = r8.mMinValue
                                if (r9 == r10) goto L_0x0048
                                r8.mMinValue = r9
                                goto L_0x0046
                            L_0x005a:
                                r9 = r9 | r3
                                if (r6 == 0) goto L_0x0070
                                java.util.Calendar r10 = r0.mMaxDate
                                int r10 = r10.get(r7)
                                java.util.Objects.requireNonNull(r8)
                                int r11 = r8.mMaxValue
                                if (r10 == r11) goto L_0x006e
                                r8.mMaxValue = r10
                            L_0x006c:
                                r10 = r4
                                goto L_0x0080
                            L_0x006e:
                                r10 = r3
                                goto L_0x0080
                            L_0x0070:
                                java.util.Calendar r10 = r0.mCurrentDate
                                int r10 = r10.getActualMaximum(r7)
                                java.util.Objects.requireNonNull(r8)
                                int r11 = r8.mMaxValue
                                if (r10 == r11) goto L_0x006e
                                r8.mMaxValue = r10
                                goto L_0x006c
                            L_0x0080:
                                r9 = r9 | r10
                                java.util.Calendar r10 = r0.mCurrentDate
                                int r10 = r10.get(r7)
                                java.util.Calendar r11 = r0.mMinDate
                                int r11 = r11.get(r7)
                                if (r10 != r11) goto L_0x0091
                                r10 = r4
                                goto L_0x0092
                            L_0x0091:
                                r10 = r3
                            L_0x0092:
                                r2 = r2 & r10
                                java.util.Calendar r10 = r0.mCurrentDate
                                int r10 = r10.get(r7)
                                java.util.Calendar r11 = r0.mMaxDate
                                int r11 = r11.get(r7)
                                if (r10 != r11) goto L_0x00a3
                                r10 = r4
                                goto L_0x00a4
                            L_0x00a3:
                                r10 = r3
                            L_0x00a4:
                                r6 = r6 & r10
                                if (r9 == 0) goto L_0x00ac
                                r9 = r1[r5]
                                r0.setColumnAt(r9, r8)
                            L_0x00ac:
                                r8 = r1[r5]
                                java.util.Calendar r9 = r0.mCurrentDate
                                int r7 = r9.get(r7)
                                r0.setColumnValue(r8, r7, r12)
                            L_0x00b7:
                                int r5 = r5 + -1
                                goto L_0x001b
                            L_0x00bb:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.picker.DatePicker.C02411.run():void");
                        }
                    });
                    return;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Separators size: ");
                m.append(extractSeparators.size());
                m.append(" must equal the size of datePickerFormat: ");
                m.append(string3.length());
                m.append(" + 1");
                throw new IllegalStateException(m.toString());
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public List<CharSequence> extractSeparators() {
        String bestYearMonthDayPattern = getBestYearMonthDayPattern(this.mDatePickerFormat);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        char[] cArr = {'Y', 'y', 'M', 'm', 'D', 'd'};
        boolean z = false;
        char c = 0;
        for (int i = 0; i < bestYearMonthDayPattern.length(); i++) {
            char charAt = bestYearMonthDayPattern.charAt(i);
            boolean z2 = true;
            if (charAt != ' ') {
                if (charAt != '\'') {
                    if (z) {
                        sb.append(charAt);
                    } else {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= 6) {
                                z2 = false;
                                break;
                            } else if (charAt == cArr[i2]) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (!z2) {
                            sb.append(charAt);
                        } else if (charAt != c) {
                            arrayList.add(sb.toString());
                            sb.setLength(0);
                        }
                    }
                    c = charAt;
                } else if (!z) {
                    sb.setLength(0);
                    z = true;
                } else {
                    z = false;
                }
            }
        }
        arrayList.add(sb.toString());
        return arrayList;
    }

    public String getBestYearMonthDayPattern(String str) {
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(this.mConstant.locale, str);
        if (TextUtils.isEmpty(bestDateTimePattern)) {
            return "MM/dd/yyyy";
        }
        return bestDateTimePattern;
    }

    public final void onColumnValueChanged(int i, int i2) {
        PickerColumn pickerColumn;
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        ArrayList<PickerColumn> arrayList = this.mColumns;
        if (arrayList == null) {
            pickerColumn = null;
        } else {
            pickerColumn = arrayList.get(i);
        }
        Objects.requireNonNull(pickerColumn);
        int i3 = pickerColumn.mCurrentValue;
        boolean z = true;
        if (i == this.mColDayIndex) {
            this.mTempDate.add(5, i2 - i3);
        } else if (i == this.mColMonthIndex) {
            this.mTempDate.add(2, i2 - i3);
        } else if (i == this.mColYearIndex) {
            this.mTempDate.add(1, i2 - i3);
        } else {
            throw new IllegalArgumentException();
        }
        int i4 = this.mTempDate.get(1);
        int i5 = this.mTempDate.get(2);
        int i6 = this.mTempDate.get(5);
        if (this.mCurrentDate.get(1) == i4 && this.mCurrentDate.get(2) == i6 && this.mCurrentDate.get(5) == i5) {
            z = false;
        }
        if (z) {
            this.mCurrentDate.set(i4, i5, i6);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            } else if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            }
            post(new Runnable() {
                public final /* synthetic */ boolean val$animation = false;

                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r12 = this;
                        androidx.leanback.widget.picker.DatePicker r0 = androidx.leanback.widget.picker.DatePicker.this
                        boolean r12 = r12.val$animation
                        java.util.Objects.requireNonNull(r0)
                        r1 = 3
                        int[] r1 = new int[r1]
                        int r2 = r0.mColDayIndex
                        r3 = 0
                        r1[r3] = r2
                        int r2 = r0.mColMonthIndex
                        r4 = 1
                        r1[r4] = r2
                        int r2 = r0.mColYearIndex
                        r5 = 2
                        r1[r5] = r2
                        r2 = r4
                        r6 = r2
                    L_0x001b:
                        if (r5 < 0) goto L_0x00bb
                        r7 = r1[r5]
                        if (r7 >= 0) goto L_0x0023
                        goto L_0x00b7
                    L_0x0023:
                        int[] r7 = androidx.leanback.widget.picker.DatePicker.DATE_FIELDS
                        r7 = r7[r5]
                        r8 = r1[r5]
                        java.util.ArrayList<androidx.leanback.widget.picker.PickerColumn> r9 = r0.mColumns
                        if (r9 != 0) goto L_0x002f
                        r8 = 0
                        goto L_0x0035
                    L_0x002f:
                        java.lang.Object r8 = r9.get(r8)
                        androidx.leanback.widget.picker.PickerColumn r8 = (androidx.leanback.widget.picker.PickerColumn) r8
                    L_0x0035:
                        if (r2 == 0) goto L_0x004a
                        java.util.Calendar r9 = r0.mMinDate
                        int r9 = r9.get(r7)
                        java.util.Objects.requireNonNull(r8)
                        int r10 = r8.mMinValue
                        if (r9 == r10) goto L_0x0048
                        r8.mMinValue = r9
                    L_0x0046:
                        r9 = r4
                        goto L_0x005a
                    L_0x0048:
                        r9 = r3
                        goto L_0x005a
                    L_0x004a:
                        java.util.Calendar r9 = r0.mCurrentDate
                        int r9 = r9.getActualMinimum(r7)
                        java.util.Objects.requireNonNull(r8)
                        int r10 = r8.mMinValue
                        if (r9 == r10) goto L_0x0048
                        r8.mMinValue = r9
                        goto L_0x0046
                    L_0x005a:
                        r9 = r9 | r3
                        if (r6 == 0) goto L_0x0070
                        java.util.Calendar r10 = r0.mMaxDate
                        int r10 = r10.get(r7)
                        java.util.Objects.requireNonNull(r8)
                        int r11 = r8.mMaxValue
                        if (r10 == r11) goto L_0x006e
                        r8.mMaxValue = r10
                    L_0x006c:
                        r10 = r4
                        goto L_0x0080
                    L_0x006e:
                        r10 = r3
                        goto L_0x0080
                    L_0x0070:
                        java.util.Calendar r10 = r0.mCurrentDate
                        int r10 = r10.getActualMaximum(r7)
                        java.util.Objects.requireNonNull(r8)
                        int r11 = r8.mMaxValue
                        if (r10 == r11) goto L_0x006e
                        r8.mMaxValue = r10
                        goto L_0x006c
                    L_0x0080:
                        r9 = r9 | r10
                        java.util.Calendar r10 = r0.mCurrentDate
                        int r10 = r10.get(r7)
                        java.util.Calendar r11 = r0.mMinDate
                        int r11 = r11.get(r7)
                        if (r10 != r11) goto L_0x0091
                        r10 = r4
                        goto L_0x0092
                    L_0x0091:
                        r10 = r3
                    L_0x0092:
                        r2 = r2 & r10
                        java.util.Calendar r10 = r0.mCurrentDate
                        int r10 = r10.get(r7)
                        java.util.Calendar r11 = r0.mMaxDate
                        int r11 = r11.get(r7)
                        if (r10 != r11) goto L_0x00a3
                        r10 = r4
                        goto L_0x00a4
                    L_0x00a3:
                        r10 = r3
                    L_0x00a4:
                        r6 = r6 & r10
                        if (r9 == 0) goto L_0x00ac
                        r9 = r1[r5]
                        r0.setColumnAt(r9, r8)
                    L_0x00ac:
                        r8 = r1[r5]
                        java.util.Calendar r9 = r0.mCurrentDate
                        int r7 = r9.get(r7)
                        r0.setColumnValue(r8, r7, r12)
                    L_0x00b7:
                        int r5 = r5 + -1
                        goto L_0x001b
                    L_0x00bb:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.picker.DatePicker.C02411.run():void");
                }
            });
        }
    }

    public final boolean parseDate(String str, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (ParseException unused) {
            Log.w("DatePicker", "Date: " + str + " not in format: " + "MM/dd/yyyy");
            return false;
        }
    }
}
