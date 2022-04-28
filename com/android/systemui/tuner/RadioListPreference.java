package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.fragments.FragmentHostManager;
import java.util.Objects;

public class RadioListPreference extends CustomListPreference {
    public DialogInterface.OnClickListener mOnClickListener;
    public CharSequence mSummary;

    public static class RadioFragment extends TunerPreferenceFragment {
        public RadioListPreference mListPref;

        public final void onCreatePreferences(String str) {
            PreferenceManager preferenceManager = this.mPreferenceManager;
            Objects.requireNonNull(preferenceManager);
            Context context = preferenceManager.mContext;
            PreferenceManager preferenceManager2 = this.mPreferenceManager;
            Objects.requireNonNull(preferenceManager2);
            PreferenceScreen preferenceScreen = new PreferenceScreen(context, (AttributeSet) null);
            preferenceScreen.onAttachedToHierarchy(preferenceManager2);
            setPreferenceScreen(preferenceScreen);
            if (this.mListPref != null) {
                update();
            }
        }

        public final boolean onPreferenceTreeClick(Preference preference) {
            DialogInterface.OnClickListener onClickListener = this.mListPref.mOnClickListener;
            Objects.requireNonNull(preference);
            onClickListener.onClick((DialogInterface) null, Integer.parseInt(preference.mKey));
            return true;
        }

        public final void update() {
            PreferenceManager preferenceManager = this.mPreferenceManager;
            Objects.requireNonNull(preferenceManager);
            Context context = preferenceManager.mContext;
            RadioListPreference radioListPreference = this.mListPref;
            Objects.requireNonNull(radioListPreference);
            CharSequence[] charSequenceArr = radioListPreference.mEntries;
            RadioListPreference radioListPreference2 = this.mListPref;
            Objects.requireNonNull(radioListPreference2);
            CharSequence[] charSequenceArr2 = radioListPreference2.mEntryValues;
            RadioListPreference radioListPreference3 = this.mListPref;
            Objects.requireNonNull(radioListPreference3);
            String str = radioListPreference3.mValue;
            for (int i = 0; i < charSequenceArr.length; i++) {
                CharSequence charSequence = charSequenceArr[i];
                SelectablePreference selectablePreference = new SelectablePreference(context);
                getPreferenceScreen().addPreference(selectablePreference);
                selectablePreference.setTitle(charSequence);
                selectablePreference.setChecked(Objects.equals(str, charSequenceArr2[i]));
                selectablePreference.setKey(String.valueOf(i));
            }
        }
    }

    public final void onDialogClosed() {
    }

    public final CharSequence getSummary() {
        CharSequence charSequence = this.mSummary;
        if (charSequence == null || charSequence.toString().contains("%s")) {
            return super.getSummary();
        }
        return this.mSummary;
    }

    public final Dialog onDialogCreated(AlertDialog alertDialog) {
        Dialog dialog = new Dialog(this.mContext, 16974371);
        Toolbar toolbar = (Toolbar) dialog.findViewById(16908729);
        View view = new View(this.mContext);
        view.setId(C1777R.C1779id.content);
        dialog.setContentView(view);
        toolbar.setTitle(this.mTitle);
        TypedArray obtainStyledAttributes = dialog.getContext().obtainStyledAttributes(new int[]{16843531});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new RadioListPreference$$ExternalSyntheticLambda0(dialog, 0));
        RadioFragment radioFragment = new RadioFragment();
        radioFragment.mListPref = this;
        if (radioFragment.mPreferenceManager != null) {
            radioFragment.update();
        }
        FragmentHostManager.get(view).getFragmentManager().beginTransaction().add(16908290, radioFragment).commit();
        return dialog;
    }

    public final void onDialogStateRestored(Dialog dialog) {
        RadioFragment radioFragment = (RadioFragment) FragmentHostManager.get(dialog.findViewById(C1777R.C1779id.content)).getFragmentManager().findFragmentById(C1777R.C1779id.content);
        if (radioFragment != null) {
            radioFragment.mListPref = this;
            if (radioFragment.mPreferenceManager != null) {
                radioFragment.update();
            }
        }
    }

    public final void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        this.mSummary = charSequence;
    }

    public final void onPrepareDialogBuilder(DialogInterface.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public RadioListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
