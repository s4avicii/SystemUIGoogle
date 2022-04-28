package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import androidx.preference.PreferenceScreen;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.ShortcutPicker;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutPicker$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ ShortcutPicker f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ LauncherActivityInfo f$2;
    public final /* synthetic */ PreferenceScreen f$3;

    public /* synthetic */ ShortcutPicker$$ExternalSyntheticLambda0(ShortcutPicker shortcutPicker, Context context, LauncherActivityInfo launcherActivityInfo, PreferenceScreen preferenceScreen) {
        this.f$0 = shortcutPicker;
        this.f$1 = context;
        this.f$2 = launcherActivityInfo;
        this.f$3 = preferenceScreen;
    }

    public final void accept(Object obj) {
        ShortcutPicker shortcutPicker = this.f$0;
        Context context = this.f$1;
        LauncherActivityInfo launcherActivityInfo = this.f$2;
        PreferenceScreen preferenceScreen = this.f$3;
        int i = ShortcutPicker.$r8$clinit;
        Objects.requireNonNull(shortcutPicker);
        ShortcutPicker.ShortcutPreference shortcutPreference = new ShortcutPicker.ShortcutPreference(context, (ShortcutParser.Shortcut) obj, launcherActivityInfo.getLabel());
        shortcutPicker.mSelectablePreferences.add(shortcutPreference);
        preferenceScreen.addPreference(shortcutPreference);
    }
}
