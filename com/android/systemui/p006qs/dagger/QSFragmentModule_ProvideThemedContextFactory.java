package com.android.systemui.p006qs.dagger;

import android.app.trust.TrustManager;
import android.content.Context;
import android.view.View;
import com.android.keyguard.CarrierText;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p012wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm;
import com.android.systemui.clipboardoverlay.ClipboardOverlayControllerFactory;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentModule_ProvideThemedContextFactory */
public final class QSFragmentModule_ProvideThemedContextFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object viewProvider;

    public /* synthetic */ QSFragmentModule_ProvideThemedContextFactory(Object obj, int i) {
        this.$r8$classId = i;
        this.viewProvider = obj;
    }

    public final Object get() {
        Object obj;
        switch (this.$r8$classId) {
            case 0:
                Context context = ((View) ((Provider) this.viewProvider).get()).getContext();
                Objects.requireNonNull(context, "Cannot return null from a non-@Nullable @Provides method");
                return context;
            case 1:
                CarrierText carrierText = (CarrierText) ((KeyguardStatusBarView) ((Provider) this.viewProvider).get()).findViewById(C1777R.C1779id.keyguard_carrier_text);
                Objects.requireNonNull(carrierText, "Cannot return null from a non-@Nullable @Provides method");
                return carrierText;
            case 2:
                TrustManager trustManager = (TrustManager) ((Context) ((Provider) this.viewProvider).get()).getSystemService(TrustManager.class);
                Objects.requireNonNull(trustManager, "Cannot return null from a non-@Nullable @Provides method");
                return trustManager;
            case 3:
                return ((LogBufferFactory) ((Provider) this.viewProvider).get()).create("NotifLog", 1000, 10, false);
            case 4:
                return new MessageRouterImpl((DelayableExecutor) ((Provider) this.viewProvider).get());
            case 5:
                Optional optional = (Optional) ((Provider) this.viewProvider).get();
                if (optional.isPresent()) {
                    obj = (StartingWindowTypeAlgorithm) optional.get();
                } else {
                    obj = new PhoneStartingWindowTypeAlgorithm();
                }
                Objects.requireNonNull(obj, "Cannot return null from a non-@Nullable @Provides method");
                return obj;
            default:
                Objects.requireNonNull((DependencyProvider) this.viewProvider);
                return new ClipboardOverlayControllerFactory();
        }
    }
}
