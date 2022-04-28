package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;

public final class PreferenceManager {
    public Context mContext;
    public SharedPreferences.Editor mEditor;
    public long mNextId = 0;
    public boolean mNoCommit;
    public OnDisplayPreferenceDialogListener mOnDisplayPreferenceDialogListener;
    public OnNavigateToScreenListener mOnNavigateToScreenListener;
    public OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    public PreferenceScreen mPreferenceScreen;
    public SharedPreferences mSharedPreferences;
    public String mSharedPreferencesName;

    public interface OnDisplayPreferenceDialogListener {
        void onDisplayPreferenceDialog(Preference preference);
    }

    public interface OnNavigateToScreenListener {
    }

    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(Preference preference);
    }

    public static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    public final SharedPreferences.Editor getEditor() {
        if (!this.mNoCommit) {
            return getSharedPreferences().edit();
        }
        if (this.mEditor == null) {
            this.mEditor = getSharedPreferences().edit();
        }
        return this.mEditor;
    }

    public final SharedPreferences getSharedPreferences() {
        if (this.mSharedPreferences == null) {
            this.mSharedPreferences = this.mContext.getSharedPreferences(this.mSharedPreferencesName, 0);
        }
        return this.mSharedPreferences;
    }

    public PreferenceManager(ContextThemeWrapper contextThemeWrapper) {
        this.mContext = contextThemeWrapper;
        this.mSharedPreferencesName = getDefaultSharedPreferencesName(contextThemeWrapper);
        this.mSharedPreferences = null;
    }
}
