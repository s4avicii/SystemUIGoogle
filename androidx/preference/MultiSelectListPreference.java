package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import com.android.p012wm.shell.C1777R;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiSelectListPreference extends DialogPreference {
    public CharSequence[] mEntries;
    public CharSequence[] mEntryValues;
    public HashSet mValues;

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, 0);
        this.mValues = new HashSet();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MultiSelectListPreference, i, 0);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(2);
        this.mEntries = textArray == null ? obtainStyledAttributes.getTextArray(0) : textArray;
        CharSequence[] textArray2 = obtainStyledAttributes.getTextArray(3);
        this.mEntryValues = textArray2 == null ? obtainStyledAttributes.getTextArray(1) : textArray2;
        obtainStyledAttributes.recycle();
    }

    public final Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.mPersistent) {
            return absSavedState;
        }
        SavedState savedState = new SavedState(absSavedState);
        savedState.mValues = this.mValues;
        return savedState;
    }

    public static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public HashSet mValues;

        public SavedState(Parcel parcel) {
            super(parcel);
            int readInt = parcel.readInt();
            this.mValues = new HashSet();
            String[] strArr = new String[readInt];
            parcel.readStringArray(strArr);
            Collections.addAll(this.mValues, strArr);
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mValues.size());
            HashSet hashSet = this.mValues;
            parcel.writeStringArray((String[]) hashSet.toArray(new String[hashSet.size()]));
        }

        public SavedState(AbsSavedState absSavedState) {
            super(absSavedState);
        }
    }

    public final void onSetInitialValue(Object obj) {
        Set<String> set = (Set) obj;
        if (shouldPersist()) {
            set = this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
        }
        setValues(set);
    }

    public final void setValues(Set<String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        if (shouldPersist()) {
            Set<String> set2 = null;
            if (shouldPersist()) {
                set2 = this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, (Set) null);
            }
            if (!set.equals(set2)) {
                SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
                editor.putStringSet(this.mKey, set);
                tryCommit(editor);
            }
        }
        notifyChanged();
    }

    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        CharSequence[] textArray = typedArray.getTextArray(i);
        HashSet hashSet = new HashSet();
        for (CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValues(savedState.mValues);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, C1777R.attr.dialogPreferenceStyle, 16842897), 0);
    }
}
