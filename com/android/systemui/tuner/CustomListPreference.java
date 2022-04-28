package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.preference.ListPreference;
import androidx.preference.ListPreferenceDialogFragment;
import java.util.Objects;

public class CustomListPreference extends ListPreference {
    public CustomListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onDialogClosed() {
    }

    public Dialog onDialogCreated(AlertDialog alertDialog) {
        return alertDialog;
    }

    public void onDialogStateRestored(Dialog dialog) {
    }

    public void onPrepareDialogBuilder(DialogInterface.OnClickListener onClickListener) {
    }

    public static class CustomListPreferenceDialogFragment extends ListPreferenceDialogFragment {
        public int mClickedDialogEntryIndex;

        /* renamed from: com.android.systemui.tuner.CustomListPreference$CustomListPreferenceDialogFragment$1 */
        public class C16671 implements DialogInterface.OnClickListener {
            public final /* synthetic */ CustomListPreferenceDialogFragment this$0;

            public final void onClick(DialogInterface dialogInterface, int i) {
                CustomListPreferenceDialogFragment customListPreferenceDialogFragment = this.this$0;
                Objects.requireNonNull(customListPreferenceDialogFragment);
                customListPreferenceDialogFragment.onClick(customListPreferenceDialogFragment.getDialog(), -1);
                customListPreferenceDialogFragment.getDialog().dismiss();
            }
        }

        public final CustomListPreference getCustomizablePreference() {
            return (CustomListPreference) getPreference();
        }

        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            getCustomizablePreference().onDialogStateRestored(getDialog());
        }

        public final Dialog onCreateDialog(Bundle bundle) {
            Dialog onCreateDialog = super.onCreateDialog(bundle);
            if (bundle != null) {
                this.mClickedDialogEntryIndex = bundle.getInt("settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX", this.mClickedDialogEntryIndex);
            }
            return getCustomizablePreference().onDialogCreated((AlertDialog) onCreateDialog);
        }

        public final void onDialogClosed(boolean z) {
            String str;
            getCustomizablePreference().onDialogClosed();
            CustomListPreference customizablePreference = getCustomizablePreference();
            CustomListPreference customizablePreference2 = getCustomizablePreference();
            if (this.mClickedDialogEntryIndex >= 0) {
                Objects.requireNonNull(customizablePreference2);
                CharSequence[] charSequenceArr = customizablePreference2.mEntryValues;
                if (charSequenceArr != null) {
                    str = charSequenceArr[this.mClickedDialogEntryIndex].toString();
                    if (z && str != null && customizablePreference.callChangeListener(str)) {
                        customizablePreference.setValue(str);
                        return;
                    }
                    return;
                }
            }
            str = null;
            if (z) {
            }
        }

        public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            super.onPrepareDialogBuilder(builder);
            CustomListPreference customizablePreference = getCustomizablePreference();
            CustomListPreference customizablePreference2 = getCustomizablePreference();
            Objects.requireNonNull(customizablePreference2);
            this.mClickedDialogEntryIndex = customizablePreference.findIndexOfValue(customizablePreference2.mValue);
            getCustomizablePreference().onPrepareDialogBuilder(new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CustomListPreferenceDialogFragment customListPreferenceDialogFragment = CustomListPreferenceDialogFragment.this;
                    Objects.requireNonNull(customListPreferenceDialogFragment);
                    customListPreferenceDialogFragment.mClickedDialogEntryIndex = i;
                    Objects.requireNonNull(CustomListPreferenceDialogFragment.this.getCustomizablePreference());
                    CustomListPreferenceDialogFragment customListPreferenceDialogFragment2 = CustomListPreferenceDialogFragment.this;
                    Objects.requireNonNull(customListPreferenceDialogFragment2);
                    customListPreferenceDialogFragment2.onClick(customListPreferenceDialogFragment2.getDialog(), -1);
                    customListPreferenceDialogFragment2.getDialog().dismiss();
                }
            });
            Objects.requireNonNull(getCustomizablePreference());
        }

        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putInt("settings.CustomListPrefDialog.KEY_CLICKED_ENTRY_INDEX", this.mClickedDialogEntryIndex);
        }
    }

    public CustomListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
