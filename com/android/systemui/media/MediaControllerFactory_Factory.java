package com.android.systemui.media;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.provider.Settings;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.doze.DozeFalsingManagerAdapter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.google.android.systemui.assist.uihints.ConfigurationHandler;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class MediaControllerFactory_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ MediaControllerFactory_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new MediaControllerFactory((Context) this.contextProvider.get());
            case 1:
                return new AccessibilityButtonTargetsObserver((Context) this.contextProvider.get());
            case 2:
                DisplayManager displayManager = (DisplayManager) ((Context) this.contextProvider.get()).getSystemService(DisplayManager.class);
                Objects.requireNonNull(displayManager, "Cannot return null from a non-@Nullable @Provides method");
                return displayManager;
            case 3:
                return new DozeFalsingManagerAdapter((FalsingCollector) this.contextProvider.get());
            case 4:
                NodeController nodeController = ((SectionHeaderControllerSubcomponent) this.contextProvider.get()).getNodeController();
                Objects.requireNonNull(nodeController, "Cannot return null from a non-@Nullable @Provides method");
                return nodeController;
            case 5:
                View findViewById = ((PhoneStatusBarView) this.contextProvider.get()).findViewById(C1777R.C1779id.notification_lights_out);
                Objects.requireNonNull(findViewById, "Cannot return null from a non-@Nullable @Provides method");
                return findViewById;
            case FalsingManager.VERSION /*6*/:
                return new ConfigurationHandler((Context) this.contextProvider.get());
            default:
                String string = Settings.Global.getString(((Context) this.contextProvider.get()).getContentResolver(), "device_name");
                Objects.requireNonNull(string, "Cannot return null from a non-@Nullable @Provides method");
                return string;
        }
    }
}
