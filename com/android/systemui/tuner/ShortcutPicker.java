package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.Process;
import android.util.AttributeSet;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShortcutPicker extends PreferenceFragment implements TunerService.Tunable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mKey;
    public SelectablePreference mNonePreference;
    public final ArrayList<SelectablePreference> mSelectablePreferences = new ArrayList<>();
    public TunerService mTunerService;

    public static class AppPreference extends SelectablePreference {
        public boolean mBinding;
        public final LauncherActivityInfo mInfo;

        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mInfo.getBadgedIcon(this.mContext.getResources().getConfiguration().densityDpi));
            }
            this.mBinding = false;
            super.onBindViewHolder(preferenceViewHolder);
        }

        public final void notifyChanged() {
            if (!this.mBinding) {
                super.notifyChanged();
            }
        }

        public final String toString() {
            return this.mInfo.getComponentName().flattenToString();
        }

        public AppPreference(Context context, LauncherActivityInfo launcherActivityInfo) {
            super(context);
            this.mInfo = launcherActivityInfo;
            setTitle((CharSequence) context.getString(C1777R.string.tuner_launch_app, new Object[]{launcherActivityInfo.getLabel()}));
            setSummary(context.getString(C1777R.string.tuner_app, new Object[]{launcherActivityInfo.getLabel()}));
        }
    }

    public static class ShortcutPreference extends SelectablePreference {
        public boolean mBinding;
        public final ShortcutParser.Shortcut mShortcut;

        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mShortcut.icon.loadDrawable(this.mContext));
            }
            this.mBinding = false;
            super.onBindViewHolder(preferenceViewHolder);
        }

        public final void notifyChanged() {
            if (!this.mBinding) {
                super.notifyChanged();
            }
        }

        public final String toString() {
            return this.mShortcut.toString();
        }

        public ShortcutPreference(Context context, ShortcutParser.Shortcut shortcut, CharSequence charSequence) {
            super(context);
            this.mShortcut = shortcut;
            setTitle((CharSequence) shortcut.label);
            setSummary(context.getString(C1777R.string.tuner_app, new Object[]{charSequence}));
        }
    }

    public final void onCreatePreferences(String str) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        Context context = preferenceManager.mContext;
        PreferenceManager preferenceManager2 = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager2);
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, (AttributeSet) null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager2);
        preferenceScreen.mOrderingAsAdded = true;
        PreferenceCategory preferenceCategory = new PreferenceCategory(context, (AttributeSet) null);
        preferenceCategory.setTitle((int) C1777R.string.tuner_other_apps);
        SelectablePreference selectablePreference = new SelectablePreference(context);
        this.mNonePreference = selectablePreference;
        this.mSelectablePreferences.add(selectablePreference);
        this.mNonePreference.setTitle((int) C1777R.string.lockscreen_none);
        SelectablePreference selectablePreference2 = this.mNonePreference;
        Objects.requireNonNull(selectablePreference2);
        selectablePreference2.setIcon(AppCompatResources.getDrawable(selectablePreference2.mContext, C1777R.C1778drawable.ic_remove_circle));
        selectablePreference2.mIconResId = C1777R.C1778drawable.ic_remove_circle;
        preferenceScreen.addPreference(this.mNonePreference);
        List<LauncherActivityInfo> activityList = ((LauncherApps) getContext().getSystemService(LauncherApps.class)).getActivityList((String) null, Process.myUserHandle());
        preferenceScreen.addPreference(preferenceCategory);
        activityList.forEach(new ShortcutPicker$$ExternalSyntheticLambda1(this, context, preferenceScreen, preferenceCategory));
        preferenceScreen.removePreference(preferenceCategory);
        for (int i = 0; i < preferenceCategory.getPreferenceCount(); i++) {
            Preference preference = preferenceCategory.getPreference(0);
            preferenceCategory.removePreference(preference);
            if (Integer.MAX_VALUE != preference.mOrder) {
                preference.mOrder = Integer.MAX_VALUE;
                Preference.OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = preference.mListener;
                if (onPreferenceChangeInternalListener != null) {
                    PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) onPreferenceChangeInternalListener;
                    preferenceGroupAdapter.mHandler.removeCallbacks(preferenceGroupAdapter.mSyncRunnable);
                    preferenceGroupAdapter.mHandler.post(preferenceGroupAdapter.mSyncRunnable);
                }
            }
            preferenceScreen.addPreference(preference);
        }
        setPreferenceScreen(preferenceScreen);
        this.mKey = getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        TunerService tunerService = (TunerService) Dependency.get(TunerService.class);
        this.mTunerService = tunerService;
        tunerService.addTunable(this, this.mKey);
    }

    public final boolean onPreferenceTreeClick(Preference preference) {
        this.mTunerService.setValue(this.mKey, preference.toString());
        getActivity().onBackPressed();
        return true;
    }

    public final void onTuningChanged(String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        this.mSelectablePreferences.forEach(new DozeTriggers$$ExternalSyntheticLambda4(str2, 3));
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if ("sysui_keyguard_left".equals(this.mKey)) {
            getActivity().setTitle(C1777R.string.lockscreen_shortcut_left);
        } else {
            getActivity().setTitle(C1777R.string.lockscreen_shortcut_right);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mTunerService.removeTunable(this);
    }
}
