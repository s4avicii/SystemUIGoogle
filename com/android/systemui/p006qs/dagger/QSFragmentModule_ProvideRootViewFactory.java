package com.android.systemui.p006qs.dagger;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentModule_ProvideRootViewFactory */
public final class QSFragmentModule_ProvideRootViewFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider qsFragmentProvider;

    public /* synthetic */ QSFragmentModule_ProvideRootViewFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.qsFragmentProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                View view = ((QSFragment) this.qsFragmentProvider.get()).getView();
                Objects.requireNonNull(view, "Cannot return null from a non-@Nullable @Provides method");
                return view;
            case 1:
                BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) this.qsFragmentProvider.get()).findViewById(C1777R.C1779id.battery);
                Objects.requireNonNull(batteryMeterView, "Cannot return null from a non-@Nullable @Provides method");
                return batteryMeterView;
            case 2:
                TelephonyManager telephonyManager = (TelephonyManager) ((Context) this.qsFragmentProvider.get()).getSystemService(TelephonyManager.class);
                Objects.requireNonNull(telephonyManager, "Cannot return null from a non-@Nullable @Provides method");
                return telephonyManager;
            case 3:
                return ((LogBufferFactory) this.qsFragmentProvider.get()).create("NotifSectionLog", 1000, 10, false);
            default:
                return new MessageRouterImpl((DelayableExecutor) this.qsFragmentProvider.get());
        }
    }
}
