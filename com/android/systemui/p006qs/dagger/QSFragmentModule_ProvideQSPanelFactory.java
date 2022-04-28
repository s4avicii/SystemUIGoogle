package com.android.systemui.p006qs.dagger;

import android.content.Context;
import android.os.Looper;
import android.telecom.TelecomManager;
import android.view.View;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.util.concurrency.ExecutorImpl;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentModule_ProvideQSPanelFactory */
public final class QSFragmentModule_ProvideQSPanelFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider viewProvider;

    public /* synthetic */ QSFragmentModule_ProvideQSPanelFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.viewProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                QSPanel qSPanel = (QSPanel) ((View) this.viewProvider.get()).findViewById(C1777R.C1779id.quick_settings_panel);
                Objects.requireNonNull(qSPanel, "Cannot return null from a non-@Nullable @Provides method");
                return qSPanel;
            case 1:
                KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = (KeyguardSecurityViewFlipper) ((KeyguardSecurityContainer) this.viewProvider.get()).findViewById(C1777R.C1779id.view_flipper);
                Objects.requireNonNull(keyguardSecurityViewFlipper, "Cannot return null from a non-@Nullable @Provides method");
                return keyguardSecurityViewFlipper;
            case 2:
                return (TelecomManager) ((Context) this.viewProvider.get()).getSystemService(TelecomManager.class);
            case 3:
                return ((LogBufferFactory) this.viewProvider.get()).create("NotifHeadsUpLog", 1000);
            case 4:
                return new ATraceLoggerTransitionProgressListener((String) this.viewProvider.get());
            case 5:
                return new ExecutorImpl((Looper) this.viewProvider.get());
            default:
                StartingWindowController startingWindowController = (StartingWindowController) this.viewProvider.get();
                Objects.requireNonNull(startingWindowController);
                Optional of = Optional.of(startingWindowController.mImpl);
                Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
                return of;
        }
    }
}
