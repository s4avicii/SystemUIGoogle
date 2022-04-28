package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LockscreenFragment$$ExternalSyntheticLambda0 implements TunerService.Tunable {
    public final /* synthetic */ LockscreenFragment f$0;
    public final /* synthetic */ SwitchPreference f$1;
    public final /* synthetic */ Preference f$2;

    public /* synthetic */ LockscreenFragment$$ExternalSyntheticLambda0(LockscreenFragment lockscreenFragment, SwitchPreference switchPreference, Preference preference) {
        this.f$0 = lockscreenFragment;
        this.f$1 = switchPreference;
        this.f$2 = preference;
    }

    public final void onTuningChanged(String str, String str2) {
        ActivityInfo activityInfo;
        LockscreenFragment lockscreenFragment = this.f$0;
        SwitchPreference switchPreference = this.f$1;
        Preference preference = this.f$2;
        int i = LockscreenFragment.$r8$clinit;
        Objects.requireNonNull(lockscreenFragment);
        switchPreference.setVisible(!TextUtils.isEmpty(str2));
        if (str2 == null) {
            preference.setSummary$1();
            return;
        }
        CharSequence charSequence = null;
        if (str2.contains("::")) {
            ShortcutParser.Shortcut shortcutInfo = LockscreenFragment.getShortcutInfo(lockscreenFragment.getContext(), str2);
            if (shortcutInfo != null) {
                charSequence = shortcutInfo.label;
            }
            preference.setSummary(charSequence);
        } else if (str2.contains("/")) {
            try {
                activityInfo = lockscreenFragment.getContext().getPackageManager().getActivityInfo(ComponentName.unflattenFromString(str2), 0);
            } catch (PackageManager.NameNotFoundException unused) {
                activityInfo = null;
            }
            if (activityInfo != null) {
                charSequence = activityInfo.loadLabel(lockscreenFragment.getContext().getPackageManager());
            }
            preference.setSummary(charSequence);
        } else {
            preference.setSummary$1();
        }
    }
}
