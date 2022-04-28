package com.android.systemui.keyboard;

import android.content.Context;
import android.os.Looper;
import android.view.ViewConfiguration;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardSliceView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.screenshot.TimeoutHandler;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.statusbar.connectivity.CallbackHandler;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.ExpandableViewController;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class KeyboardUI_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object contextProvider;

    public /* synthetic */ KeyboardUI_Factory(Object obj, int i) {
        this.$r8$classId = i;
        this.contextProvider = obj;
    }

    public static KeyboardUI_Factory create$8(Provider provider) {
        return new KeyboardUI_Factory(provider, 8);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new KeyboardUI((Context) ((Provider) this.contextProvider).get());
            case 1:
                KeyguardSliceView keyguardSliceView = (KeyguardSliceView) ((KeyguardClockSwitch) ((Provider) this.contextProvider).get()).findViewById(C1777R.C1779id.keyguard_slice_view);
                Objects.requireNonNull(keyguardSliceView, "Cannot return null from a non-@Nullable @Provides method");
                return keyguardSliceView;
            case 2:
                ViewConfiguration viewConfiguration = ViewConfiguration.get((Context) ((Provider) this.contextProvider).get());
                Objects.requireNonNull(viewConfiguration, "Cannot return null from a non-@Nullable @Provides method");
                return viewConfiguration;
            case 3:
                return ((LogBufferFactory) ((Provider) this.contextProvider).get()).create("QSLog", 500, 10, false);
            case 4:
                OngoingPrivacyChip ongoingPrivacyChip = (OngoingPrivacyChip) ((QuickStatusBarHeader) ((Provider) this.contextProvider).get()).findViewById(C1777R.C1779id.privacy_chip);
                Objects.requireNonNull(ongoingPrivacyChip, "Cannot return null from a non-@Nullable @Provides method");
                return ongoingPrivacyChip;
            case 5:
                return new TimeoutHandler((Context) ((Provider) this.contextProvider).get());
            case FalsingManager.VERSION /*6*/:
                return new CallbackHandler((Looper) ((Provider) this.contextProvider).get());
            case 7:
                return new DataStoreCoordinator((NotifLiveDataStoreImpl) ((Provider) this.contextProvider).get());
            case 8:
                ExpandableView expandableView = (ExpandableView) ((Provider) this.contextProvider).get();
                return new ExpandableViewController();
            default:
                Objects.requireNonNull((DependencyProvider) this.contextProvider);
                return DevicePolicyManagerWrapper.sInstance;
        }
    }
}
