package com.android.systemui.p006qs.tiles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p004ui.ControlsActivity;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.tiles.DeviceControlsTile */
/* compiled from: DeviceControlsTile.kt */
public final class DeviceControlsTile extends QSTileImpl<QSTile.State> {
    public final ControlsComponent controlsComponent;
    public AtomicBoolean hasControlsApps = new AtomicBoolean(false);
    public final KeyguardStateController keyguardStateController;
    public final DeviceControlsTile$listingCallback$1 listingCallback = new DeviceControlsTile$listingCallback$1(this);

    public static /* synthetic */ void getIcon$annotations() {
    }

    public final Intent getLongClickIntent() {
        return null;
    }

    public final int getMetricsCategory() {
        return 0;
    }

    public final void handleLongClick(View view) {
    }

    public final CharSequence getTileLabel() {
        Context context = this.mContext;
        ControlsComponent controlsComponent2 = this.controlsComponent;
        Objects.requireNonNull(controlsComponent2);
        return context.getText(controlsComponent2.controlsTileResourceConfiguration.getTileTitleId());
    }

    public final void handleClick(View view) {
        if (this.mState.state != 0) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.mContext, ControlsActivity.class));
            intent.addFlags(335544320);
            intent.putExtra("extra_animate", true);
            GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
            if (view != null) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                } else {
                    ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(view, (Integer) 32, 4);
                }
            }
            this.mUiHandler.post(new DeviceControlsTile$handleClick$1(this, intent, ghostedViewLaunchAnimatorController));
        }
    }

    public final boolean isAvailable() {
        return this.controlsComponent.getControlsController().isPresent();
    }

    public final QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.state = 0;
        state.handlesLongClick = false;
        return state;
    }

    public DeviceControlsTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ControlsComponent controlsComponent2, KeyguardStateController keyguardStateController2) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.controlsComponent = controlsComponent2;
        this.keyguardStateController = keyguardStateController2;
        controlsComponent2.getControlsListingController().ifPresent(new Consumer(this) {
            public final /* synthetic */ DeviceControlsTile this$0;

            {
                this.this$0 = r1;
            }

            public final void accept(Object obj) {
                DeviceControlsTile deviceControlsTile = this.this$0;
                ((ControlsListingController) obj).observe((LifecycleOwner) deviceControlsTile, deviceControlsTile.listingCallback);
            }
        });
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        CharSequence tileLabel = getTileLabel();
        state.label = tileLabel;
        state.contentDescription = tileLabel;
        ControlsComponent controlsComponent2 = this.controlsComponent;
        Objects.requireNonNull(controlsComponent2);
        state.icon = QSTileImpl.ResourceIcon.get(controlsComponent2.controlsTileResourceConfiguration.getTileImageId());
        ControlsComponent controlsComponent3 = this.controlsComponent;
        Objects.requireNonNull(controlsComponent3);
        if (!controlsComponent3.featureEnabled || !this.hasControlsApps.get()) {
            state.state = 0;
            return;
        }
        if (this.controlsComponent.getVisibility() == ControlsComponent.Visibility.AVAILABLE) {
            StructureInfo preferredStructure = this.controlsComponent.getControlsController().get().getPreferredStructure();
            Objects.requireNonNull(preferredStructure);
            CharSequence charSequence = preferredStructure.structure;
            state.state = 2;
            if (Intrinsics.areEqual(charSequence, getTileLabel())) {
                charSequence = null;
            }
            state.secondaryLabel = charSequence;
        } else {
            state.state = 1;
            state.secondaryLabel = this.mContext.getText(C1777R.string.controls_tile_locked);
        }
        state.stateDescription = state.secondaryLabel;
    }
}
