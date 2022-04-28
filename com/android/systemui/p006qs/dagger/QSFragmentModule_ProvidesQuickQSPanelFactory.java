package com.android.systemui.p006qs.dagger;

import android.view.LayoutInflater;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthRippleView;
import com.android.systemui.communal.conditions.CommunalSettingCondition;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.p006qs.QuickQSPanel;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.notification.DynamicChildBindController;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import dagger.internal.Factory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQuickQSPanelFactory */
public final class QSFragmentModule_ProvidesQuickQSPanelFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object quickStatusBarHeaderProvider;

    public /* synthetic */ QSFragmentModule_ProvidesQuickQSPanelFactory(Object obj, int i) {
        this.$r8$classId = i;
        this.quickStatusBarHeaderProvider = obj;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                QuickQSPanel quickQSPanel = (QuickQSPanel) ((QuickStatusBarHeader) ((Provider) this.quickStatusBarHeaderProvider).get()).findViewById(C1777R.C1779id.quick_qs_panel);
                Objects.requireNonNull(quickQSPanel, "Cannot return null from a non-@Nullable @Provides method");
                return quickQSPanel;
            case 1:
                return Float.valueOf((float) ((ViewConfiguration) ((Provider) this.quickStatusBarHeaderProvider).get()).getScaledTouchSlop());
            case 2:
                return new HashSet(Collections.singletonList((CommunalSettingCondition) ((Provider) this.quickStatusBarHeaderProvider).get()));
            case 3:
                DreamOverlayContainerView dreamOverlayContainerView = (DreamOverlayContainerView) Preconditions.checkNotNull((DreamOverlayContainerView) ((LayoutInflater) ((Provider) this.quickStatusBarHeaderProvider).get()).inflate(C1777R.layout.dream_overlay_container, (ViewGroup) null), "R.layout.dream_layout_container could not be properly inflated");
                Objects.requireNonNull(dreamOverlayContainerView, "Cannot return null from a non-@Nullable @Provides method");
                return dreamOverlayContainerView;
            case 4:
                return new DynamicChildBindController((RowContentBindStage) ((Provider) this.quickStatusBarHeaderProvider).get());
            case 5:
                return (AuthRippleView) ((NotificationShadeWindowView) ((Provider) this.quickStatusBarHeaderProvider).get()).findViewById(C1777R.C1779id.auth_ripple);
            default:
                Objects.requireNonNull((DependencyProvider) this.quickStatusBarHeaderProvider);
                TaskStackChangeListeners taskStackChangeListeners = TaskStackChangeListeners.INSTANCE;
                Objects.requireNonNull(taskStackChangeListeners, "Cannot return null from a non-@Nullable @Provides method");
                return taskStackChangeListeners;
        }
    }
}
