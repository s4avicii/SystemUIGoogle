package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import okio.Okio;

public class SingleDateSelector implements DateSelector<Long> {
    public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator<SingleDateSelector>() {
        public final Object createFromParcel(Parcel parcel) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            singleDateSelector.selectedItem = (Long) parcel.readValue(Long.class.getClassLoader());
            return singleDateSelector;
        }

        public final Object[] newArray(int i) {
            return new SingleDateSelector[i];
        }
    };
    public Long selectedItem;

    public final int describeContents() {
        return 0;
    }

    public final int getDefaultThemeResId(Context context) {
        return MaterialAttributes.resolveOrThrow(context, C1777R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
    }

    public final ArrayList getSelectedDays() {
        ArrayList arrayList = new ArrayList();
        Long l = this.selectedItem;
        if (l != null) {
            arrayList.add(l);
        }
        return arrayList;
    }

    public final ArrayList getSelectedRanges() {
        return new ArrayList();
    }

    public final boolean isSelectionComplete() {
        if (this.selectedItem != null) {
            return true;
        }
        return false;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.selectedItem);
    }

    public final String getSelectionDisplayString(Context context) {
        Resources resources = context.getResources();
        Long l = this.selectedItem;
        if (l == null) {
            return resources.getString(C1777R.string.mtrl_picker_date_header_unselected);
        }
        return resources.getString(C1777R.string.mtrl_picker_date_header_selected, new Object[]{DateStrings.getYearMonthDay(l.longValue(), Locale.getDefault())});
    }

    public final View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, CalendarConstraints calendarConstraints, MaterialTextInputPicker.C20081 r12) {
        View inflate = layoutInflater.inflate(C1777R.layout.mtrl_picker_text_input_date, viewGroup, false);
        TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(C1777R.C1779id.mtrl_picker_text_input_date);
        Objects.requireNonNull(textInputLayout);
        EditText editText = textInputLayout.editText;
        if (Okio.isDateInputKeyboardMissingSeparatorCharacters()) {
            editText.setInputType(17);
        }
        SimpleDateFormat textInputFormat = UtcDates.getTextInputFormat();
        String textInputHint = UtcDates.getTextInputHint(inflate.getResources(), textInputFormat);
        textInputLayout.setPlaceholderText(textInputHint);
        Long l = this.selectedItem;
        if (l != null) {
            editText.setText(textInputFormat.format(l));
        }
        final MaterialTextInputPicker.C20081 r6 = r12;
        editText.addTextChangedListener(new DateFormatTextWatcher(textInputHint, textInputFormat, textInputLayout, calendarConstraints) {
            public final void onInvalidDate() {
                r6.onIncompleteSelectionChanged();
            }

            public final void onValidDate(Long l) {
                if (l == null) {
                    SingleDateSelector singleDateSelector = SingleDateSelector.this;
                    Objects.requireNonNull(singleDateSelector);
                    singleDateSelector.selectedItem = null;
                } else {
                    SingleDateSelector.this.select(l.longValue());
                }
                OnSelectionChangedListener onSelectionChangedListener = r6;
                SingleDateSelector singleDateSelector2 = SingleDateSelector.this;
                Objects.requireNonNull(singleDateSelector2);
                onSelectionChangedListener.onSelectionChanged(singleDateSelector2.selectedItem);
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
        this.selectedItem = Long.valueOf(j);
    }

    public final Object getSelection() {
        return this.selectedItem;
    }
}
