package com.android.systemui.p006qs.dagger;

import android.content.ContentResolver;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardHostView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogcatEchoTrackerDebug;
import com.android.systemui.log.LogcatEchoTrackerDebug$attach$1;
import com.android.systemui.log.LogcatEchoTrackerDebug$attach$2;
import com.android.systemui.log.LogcatEchoTrackerProd;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFlagsModule_IsPMLiteEnabledFactory */
public final class QSFlagsModule_IsPMLiteEnabledFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider featureFlagsProvider;
    public final Provider globalSettingsProvider;

    public /* synthetic */ QSFlagsModule_IsPMLiteEnabledFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.featureFlagsProvider = provider;
        this.globalSettingsProvider = provider2;
    }

    public final Object get() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                GlobalSettings globalSettings = (GlobalSettings) this.globalSettingsProvider.get();
                if (((FeatureFlags) this.featureFlagsProvider.get()).isEnabled(Flags.POWER_MENU_LITE) && globalSettings.getInt("sysui_pm_lite", 1) != 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 1:
                ViewGroup viewGroup = (ViewGroup) this.featureFlagsProvider.get();
                KeyguardHostView keyguardHostView = (KeyguardHostView) ((LayoutInflater) this.globalSettingsProvider.get()).inflate(C1777R.layout.keyguard_host_view, viewGroup, false);
                viewGroup.addView(keyguardHostView);
                Objects.requireNonNull(keyguardHostView, "Cannot return null from a non-@Nullable @Provides method");
                return keyguardHostView;
            case 2:
                ContentResolver contentResolver = (ContentResolver) this.featureFlagsProvider.get();
                Looper looper = (Looper) this.globalSettingsProvider.get();
                if (!Build.isDebuggable()) {
                    return new LogcatEchoTrackerProd();
                }
                LogcatEchoTrackerDebug logcatEchoTrackerDebug = new LogcatEchoTrackerDebug(contentResolver);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("systemui/buffer"), true, new LogcatEchoTrackerDebug$attach$1(logcatEchoTrackerDebug, new Handler(looper)));
                contentResolver.registerContentObserver(Settings.Global.getUriFor("systemui/tag"), true, new LogcatEchoTrackerDebug$attach$2(logcatEchoTrackerDebug, new Handler(looper)));
                return logcatEchoTrackerDebug;
            default:
                return new StatusBarWindowStateController(((Integer) this.featureFlagsProvider.get()).intValue(), (CommandQueue) this.globalSettingsProvider.get());
        }
    }
}
