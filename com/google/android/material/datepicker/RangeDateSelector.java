package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import okio.Okio;

public class RangeDateSelector implements DateSelector<Pair<Long, Long>> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator<RangeDateSelector>() {
        public final Object createFromParcel(Parcel parcel) {
            Class<Long> cls = Long.class;
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            rangeDateSelector.selectedStartItem = (Long) parcel.readValue(cls.getClassLoader());
            rangeDateSelector.selectedEndItem = (Long) parcel.readValue(cls.getClassLoader());
            return rangeDateSelector;
        }

        public final Object[] newArray(int i) {
            return new RangeDateSelector[i];
        }
    };
    public String invalidRangeStartError;
    public Long proposedTextEnd = null;
    public Long proposedTextStart = null;
    public Long selectedEndItem = null;
    public Long selectedStartItem = null;

    public final int describeContents() {
        return 0;
    }

    public final ArrayList getSelectedDays() {
        ArrayList arrayList = new ArrayList();
        Long l = this.selectedStartItem;
        if (l != null) {
            arrayList.add(l);
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            arrayList.add(l2);
        }
        return arrayList;
    }

    public final ArrayList getSelectedRanges() {
        if (this.selectedStartItem == null || this.selectedEndItem == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Pair(this.selectedStartItem, this.selectedEndItem));
        return arrayList;
    }

    public final Object getSelection() {
        return new Pair(this.selectedStartItem, this.selectedEndItem);
    }

    public final boolean isSelectionComplete() {
        boolean z;
        Long l = this.selectedStartItem;
        if (!(l == null || this.selectedEndItem == null)) {
            if (l.longValue() <= this.selectedEndItem.longValue()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, CalendarConstraints calendarConstraints, MaterialTextInputPicker.C20081 r21) {
        View inflate = layoutInflater.inflate(C1777R.layout.mtrl_picker_text_input_date_range, viewGroup, false);
        TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(C1777R.C1779id.mtrl_picker_text_input_range_start);
        TextInputLayout textInputLayout2 = (TextInputLayout) inflate.findViewById(C1777R.C1779id.mtrl_picker_text_input_range_end);
        Objects.requireNonNull(textInputLayout);
        EditText editText = textInputLayout.editText;
        Objects.requireNonNull(textInputLayout2);
        EditText editText2 = textInputLayout2.editText;
        if (Okio.isDateInputKeyboardMissingSeparatorCharacters()) {
            editText.setInputType(17);
            editText2.setInputType(17);
        }
        this.invalidRangeStartError = inflate.getResources().getString(C1777R.string.mtrl_picker_invalid_range);
        SimpleDateFormat textInputFormat = UtcDates.getTextInputFormat();
        Long l = this.selectedStartItem;
        if (l != null) {
            editText.setText(textInputFormat.format(l));
            this.proposedTextStart = this.selectedStartItem;
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            editText2.setText(textInputFormat.format(l2));
            this.proposedTextEnd = this.selectedEndItem;
        }
        String textInputHint = UtcDates.getTextInputHint(inflate.getResources(), textInputFormat);
        textInputLayout.setPlaceholderText(textInputHint);
        textInputLayout2.setPlaceholderText(textInputHint);
        SimpleDateFormat simpleDateFormat = textInputFormat;
        CalendarConstraints calendarConstraints2 = calendarConstraints;
        final TextInputLayout textInputLayout3 = textInputLayout;
        C20111 r9 = r0;
        final TextInputLayout textInputLayout4 = textInputLayout2;
        String str = textInputHint;
        final MaterialTextInputPicker.C20081 r8 = r21;
        C20111 r0 = new DateFormatTextWatcher(textInputHint, simpleDateFormat, textInputLayout, calendarConstraints2) {
            public final void onInvalidDate() {
                RangeDateSelector rangeDateSelector = RangeDateSelector.this;
                rangeDateSelector.proposedTextStart = null;
                RangeDateSelector.access$100(rangeDateSelector, textInputLayout3, textInputLayout4, r8);
            }

            public final void onValidDate(Long l) {
                RangeDateSelector rangeDateSelector = RangeDateSelector.this;
                rangeDateSelector.proposedTextStart = l;
                RangeDateSelector.access$100(rangeDateSelector, textInputLayout3, textInputLayout4, r8);
            }
        };
        editText.addTextChangedListener(r9);
        editText2.addTextChangedListener(new DateFormatTextWatcher(str, simpleDateFormat, textInputLayout2, calendarConstraints2) {
            public final void onInvalidDate() {
                RangeDateSelector rangeDateSelector = RangeDateSelector.this;
                rangeDateSelector.proposedTextEnd = null;
                RangeDateSelector.access$100(rangeDateSelector, textInputLayout3, textInputLayout4, r8);
            }

            public final void onValidDate(Long l) {
                RangeDateSelector rangeDateSelector = RangeDateSelector.this;
                rangeDateSelector.proposedTextEnd = l;
                RangeDateSelector.access$100(rangeDateSelector, textInputLayout3, textInputLayout4, r8);
            }
        });
        editText.requestFocus();
        editText.post(new Runnable(editText) {
            public final /* synthetic */ View val$view;

            {
                this.val$view = r1;
            }

            public final void run() {
                ((InputMethodManager) this.val$view.getContext().getSystemService("input_method")).showSoftInput(this.val$view, 1);
            }
        });
        return inflate;
    }

    public final void select(long j) {
        boolean z;
        Long l = this.selectedStartItem;
        if (l == null) {
            this.selectedStartItem = Long.valueOf(j);
            return;
        }
        if (this.selectedEndItem == null) {
            if (l.longValue() <= j) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.selectedEndItem = Long.valueOf(j);
                return;
            }
        }
        this.selectedEndItem = null;
        this.selectedStartItem = Long.valueOf(j);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.selectedStartItem);
        parcel.writeValue(this.selectedEndItem);
    }

    public static void access$100(RangeDateSelector rangeDateSelector, TextInputLayout textInputLayout, TextInputLayout textInputLayout2, OnSelectionChangedListener onSelectionChangedListener) {
        boolean z;
        Objects.requireNonNull(rangeDateSelector);
        Long l = rangeDateSelector.proposedTextStart;
        if (l == null || rangeDateSelector.proposedTextEnd == null) {
            if (textInputLayout.getError() != null && rangeDateSelector.invalidRangeStartError.contentEquals(textInputLayout.getError())) {
                textInputLayout.setError((CharSequence) null);
            }
            if (textInputLayout2.getError() != null && " ".contentEquals(textInputLayout2.getError())) {
                textInputLayout2.setError((CharSequence) null);
            }
            onSelectionChangedListener.onIncompleteSelectionChanged();
            return;
        }
        if (l.longValue() <= rangeDateSelector.proposedTextEnd.longValue()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Long l2 = rangeDateSelector.proposedTextStart;
            rangeDateSelector.selectedStartItem = l2;
            Long l3 = rangeDateSelector.proposedTextEnd;
            rangeDateSelector.selectedEndItem = l3;
            onSelectionChangedListener.onSelectionChanged(new Pair(l2, l3));
            return;
        }
        textInputLayout.setError(rangeDateSelector.invalidRangeStartError);
        textInputLayout2.setError(" ");
        onSelectionChangedListener.onIncompleteSelectionChanged();
    }

    public final int getDefaultThemeResId(Context context) {
        int i;
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) > resources.getDimensionPixelSize(C1777R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis)) {
            i = C1777R.attr.materialCalendarTheme;
        } else {
            i = C1777R.attr.materialCalendarFullscreenTheme;
        }
        return MaterialAttributes.resolveOrThrow(context, i, MaterialDatePicker.class.getCanonicalName());
    }

    public final String getSelectionDisplayString(Context context) {
        Pair pair;
        Resources resources = context.getResources();
        Long l = this.selectedStartItem;
        if (l == null && this.selectedEndItem == null) {
            return resources.getString(C1777R.string.mtrl_picker_range_header_unselected);
        }
        Long l2 = this.selectedEndItem;
        if (l2 == null) {
            return resources.getString(C1777R.string.mtrl_picker_range_header_only_start_selected, new Object[]{DateStrings.getDateString$1(l.longValue())});
        } else if (l == null) {
            return resources.getString(C1777R.string.mtrl_picker_range_header_only_end_selected, new Object[]{DateStrings.getDateString$1(l2.longValue())});
        } else {
            Calendar todayCalendar = UtcDates.getTodayCalendar();
            Calendar utcCalendarOf = UtcDates.getUtcCalendarOf((Calendar) null);
            utcCalendarOf.setTimeInMillis(l.longValue());
            Calendar utcCalendarOf2 = UtcDates.getUtcCalendarOf((Calendar) null);
            utcCalendarOf2.setTimeInMillis(l2.longValue());
            if (utcCalendarOf.get(1) != utcCalendarOf2.get(1)) {
                pair = new Pair(DateStrings.getYearMonthDay(l.longValue(), Locale.getDefault()), DateStrings.getYearMonthDay(l2.longValue(), Locale.getDefault()));
            } else if (utcCalendarOf.get(1) == todayCalendar.get(1)) {
                pair = new Pair(DateStrings.getMonthDay(l.longValue(), Locale.getDefault()), DateStrings.getMonthDay(l2.longValue(), Locale.getDefault()));
            } else {
                pair = new Pair(DateStrings.getMonthDay(l.longValue(), Locale.getDefault()), DateStrings.getYearMonthDay(l2.longValue(), Locale.getDefault()));
            }
            return resources.getString(C1777R.string.mtrl_picker_range_header_selected, new Object[]{pair.first, pair.second});
        }
    }
}
