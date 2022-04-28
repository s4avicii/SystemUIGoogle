package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.systemui.tuner.ShortcutPicker;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutPicker$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ ShortcutPicker f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ PreferenceScreen f$2;
    public final /* synthetic */ PreferenceCategory f$3;

    public /* synthetic */ ShortcutPicker$$ExternalSyntheticLambda1(ShortcutPicker shortcutPicker, Context context, PreferenceScreen preferenceScreen, PreferenceCategory preferenceCategory) {
        this.f$0 = shortcutPicker;
        this.f$1 = context;
        this.f$2 = preferenceScreen;
        this.f$3 = preferenceCategory;
    }

    public final void accept(Object obj) {
        ShortcutPicker shortcutPicker = this.f$0;
        Context context = this.f$1;
        PreferenceScreen preferenceScreen = this.f$2;
        PreferenceCategory preferenceCategory = this.f$3;
        LauncherActivityInfo launcherActivityInfo = (LauncherActivityInfo) obj;
        int i = ShortcutPicker.$r8$clinit;
        Objects.requireNonNull(shortcutPicker);
        try {
            ArrayList shortcuts = new ShortcutParser(shortcutPicker.getContext(), launcherActivityInfo.getComponentName()).getShortcuts();
            ShortcutPicker.AppPreference appPreference = new ShortcutPicker.AppPreference(context, launcherActivityInfo);
            shortcutPicker.mSelectablePreferences.add(appPreference);
            if (shortcuts.size() != 0) {
                preferenceScreen.addPreference(appPreference);
                shortcuts.forEach(new ShortcutPicker$$ExternalSyntheticLambda0(shortcutPicker, context, launcherActivityInfo, preferenceScreen));
                return;
            }
            preferenceCategory.addPreference(appPreference);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }
}
