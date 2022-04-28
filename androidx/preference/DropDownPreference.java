package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class DropDownPreference extends ListPreference {
    public final ArrayAdapter mAdapter;
    public final C02901 mItemSelectedListener;
    public Spinner mSpinner;

    public DropDownPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.dropdownPreferenceStyle);
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Spinner spinner = (Spinner) preferenceViewHolder.itemView.findViewById(C1777R.C1779id.spinner);
        this.mSpinner = spinner;
        spinner.setAdapter(this.mAdapter);
        this.mSpinner.setOnItemSelectedListener(this.mItemSelectedListener);
        Spinner spinner2 = this.mSpinner;
        String str = this.mValue;
        CharSequence[] charSequenceArr = this.mEntryValues;
        int i = -1;
        if (str != null && charSequenceArr != null) {
            int length = charSequenceArr.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                } else if (TextUtils.equals(charSequenceArr[length].toString(), str)) {
                    i = length;
                    break;
                } else {
                    length--;
                }
            }
        }
        spinner2.setSelection(i);
        super.onBindViewHolder(preferenceViewHolder);
    }

    public final void onClick() {
        this.mSpinner.performClick();
    }

    public final void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        this.mAdapter.clear();
        CharSequence[] charSequenceArr2 = this.mEntries;
        if (charSequenceArr2 != null) {
            for (CharSequence charSequence : charSequenceArr2) {
                this.mAdapter.add(charSequence.toString());
            }
        }
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void notifyChanged() {
        super.notifyChanged();
        ArrayAdapter arrayAdapter = this.mAdapter;
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            public final void onNothingSelected(AdapterView<?> adapterView) {
            }

            public final void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i >= 0) {
                    DropDownPreference dropDownPreference = DropDownPreference.this;
                    Objects.requireNonNull(dropDownPreference);
                    String charSequence = dropDownPreference.mEntryValues[i].toString();
                    DropDownPreference dropDownPreference2 = DropDownPreference.this;
                    Objects.requireNonNull(dropDownPreference2);
                    if (!charSequence.equals(dropDownPreference2.mValue) && DropDownPreference.this.callChangeListener(charSequence)) {
                        DropDownPreference.this.setValue(charSequence);
                    }
                }
            }
        };
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, 17367049);
        this.mAdapter = arrayAdapter;
        arrayAdapter.clear();
        CharSequence[] charSequenceArr = this.mEntries;
        if (charSequenceArr != null) {
            for (CharSequence charSequence : charSequenceArr) {
                this.mAdapter.add(charSequence.toString());
            }
        }
    }
}
