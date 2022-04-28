package com.android.systemui.p006qs.dagger;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.FooterActionsView;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSFooterActionsViewFactory */
public final class QSFragmentModule_ProvidesQSFooterActionsViewFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider featureFlagsProvider;
    public final Provider viewProvider;

    public /* synthetic */ QSFragmentModule_ProvidesQSFooterActionsViewFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.viewProvider = provider;
        this.featureFlagsProvider = provider2;
    }

    public final Object get() {
        ViewStub viewStub;
        LocalBluetoothAdapter localBluetoothAdapter;
        BluetoothAdapter defaultAdapter;
        switch (this.$r8$classId) {
            case 0:
                View view = (View) this.viewProvider.get();
                if (((FeatureFlags) this.featureFlagsProvider.get()).isEnabled(Flags.NEW_FOOTER)) {
                    viewStub = (ViewStub) view.requireViewById(C1777R.C1779id.container_stub);
                } else {
                    viewStub = (ViewStub) view.requireViewById(C1777R.C1779id.footer_stub);
                }
                viewStub.inflate();
                FooterActionsView footerActionsView = (FooterActionsView) view.findViewById(C1777R.C1779id.qs_footer_actions);
                Objects.requireNonNull(footerActionsView, "Cannot return null from a non-@Nullable @Provides method");
                return footerActionsView;
            case 1:
                Context context = (Context) this.viewProvider.get();
                Handler handler = (Handler) this.featureFlagsProvider.get();
                UserHandle userHandle = UserHandle.ALL;
                synchronized (LocalBluetoothAdapter.class) {
                    if (LocalBluetoothAdapter.sInstance == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                        LocalBluetoothAdapter.sInstance = new LocalBluetoothAdapter(defaultAdapter);
                    }
                    localBluetoothAdapter = LocalBluetoothAdapter.sInstance;
                }
                if (localBluetoothAdapter == null) {
                    return null;
                }
                return new LocalBluetoothManager(localBluetoothAdapter, context, handler, userHandle);
            default:
                return new StatusBarMoveFromCenterAnimationController((ScopedUnfoldTransitionProgressProvider) this.viewProvider.get(), (WindowManager) this.featureFlagsProvider.get());
        }
    }
}
