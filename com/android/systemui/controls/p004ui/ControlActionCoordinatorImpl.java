package com.android.systemui.controls.p004ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.service.controls.Control;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.TaskViewFactory;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ControlActionCoordinatorImpl */
/* compiled from: ControlActionCoordinatorImpl.kt */
public final class ControlActionCoordinatorImpl implements ControlActionCoordinator {
    public LinkedHashSet actionsInProgress = new LinkedHashSet();
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public final DelayableExecutor bgExecutor;
    public final Context context;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public Dialog dialog;
    public final KeyguardStateController keyguardStateController;
    public Action pendingAction;
    public final Optional<TaskViewFactory> taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final VibratorHelper vibrator;

    /* renamed from: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$Action */
    /* compiled from: ControlActionCoordinatorImpl.kt */
    public final class Action {
        public final boolean blockable;
        public final String controlId;

        /* renamed from: f */
        public final Function0<Unit> f47f;

        public Action(String str, Function0<Unit> function0, boolean z) {
            this.controlId = str;
            this.f47f = function0;
            this.blockable = z;
        }

        public final void invoke() {
            boolean z;
            if (this.blockable) {
                ControlActionCoordinatorImpl controlActionCoordinatorImpl = ControlActionCoordinatorImpl.this;
                String str = this.controlId;
                Objects.requireNonNull(controlActionCoordinatorImpl);
                if (controlActionCoordinatorImpl.actionsInProgress.add(str)) {
                    controlActionCoordinatorImpl.uiExecutor.executeDelayed(new ControlActionCoordinatorImpl$shouldRunAction$1(controlActionCoordinatorImpl, str), 3000);
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return;
                }
            }
            this.f47f.invoke();
        }
    }

    public final void drag(boolean z) {
        if (z) {
            VibrationEffect vibrationEffect = Vibrations.rangeEdgeEffect;
            VibratorHelper vibratorHelper = this.vibrator;
            Objects.requireNonNull(vibratorHelper);
            if (vibratorHelper.hasVibrator()) {
                vibratorHelper.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda0(vibratorHelper, vibrationEffect, 0));
                return;
            }
            return;
        }
        VibrationEffect vibrationEffect2 = Vibrations.rangeMiddleEffect;
        VibratorHelper vibratorHelper2 = this.vibrator;
        Objects.requireNonNull(vibratorHelper2);
        if (vibratorHelper2.hasVibrator()) {
            vibratorHelper2.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda0(vibratorHelper2, vibrationEffect2, 0));
        }
    }

    @VisibleForTesting
    public final void bouncerOrRun(Action action, boolean z) {
        if (!this.keyguardStateController.isShowing() || !z) {
            action.invoke();
            return;
        }
        if (isLocked()) {
            this.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            this.pendingAction = action;
        }
        this.activityStarter.dismissKeyguardThenExecute(new ControlActionCoordinatorImpl$bouncerOrRun$1(action), new ControlActionCoordinatorImpl$bouncerOrRun$2(this), true);
    }

    public final void closeDialogs() {
        Dialog dialog2 = this.dialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
        this.dialog = null;
    }

    @VisibleForTesting
    public final Action createAction(String str, Function0<Unit> function0, boolean z) {
        return new Action(str, function0, z);
    }

    public final void enableActionOnTouch(String str) {
        this.actionsInProgress.remove(str);
    }

    public final boolean isLocked() {
        return !this.keyguardStateController.isUnlocked();
    }

    public final void longPress(ControlViewHolder controlViewHolder) {
        this.controlsMetricsLogger.longPress(controlViewHolder, isLocked());
        ControlWithState cws = controlViewHolder.getCws();
        Objects.requireNonNull(cws);
        ControlInfo controlInfo = cws.f48ci;
        Objects.requireNonNull(controlInfo);
        bouncerOrRun(createAction(controlInfo.controlId, new ControlActionCoordinatorImpl$longPress$1(controlViewHolder, this), false), isAuthRequired(controlViewHolder));
    }

    public final void setValue(ControlViewHolder controlViewHolder, String str, float f) {
        this.controlsMetricsLogger.drag(controlViewHolder, isLocked());
        ControlWithState cws = controlViewHolder.getCws();
        Objects.requireNonNull(cws);
        ControlInfo controlInfo = cws.f48ci;
        Objects.requireNonNull(controlInfo);
        bouncerOrRun(createAction(controlInfo.controlId, new ControlActionCoordinatorImpl$setValue$1(controlViewHolder, str, f), false), isAuthRequired(controlViewHolder));
    }

    public final void toggle(ControlViewHolder controlViewHolder, String str, boolean z) {
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        ControlWithState cws = controlViewHolder.getCws();
        Objects.requireNonNull(cws);
        ControlInfo controlInfo = cws.f48ci;
        Objects.requireNonNull(controlInfo);
        bouncerOrRun(createAction(controlInfo.controlId, new ControlActionCoordinatorImpl$toggle$1(controlViewHolder, str, z), true), isAuthRequired(controlViewHolder));
    }

    public final void touch(ControlViewHolder controlViewHolder, String str, Control control) {
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        boolean usePanel = controlViewHolder.usePanel();
        ControlWithState cws = controlViewHolder.getCws();
        Objects.requireNonNull(cws);
        ControlInfo controlInfo = cws.f48ci;
        Objects.requireNonNull(controlInfo);
        bouncerOrRun(createAction(controlInfo.controlId, new ControlActionCoordinatorImpl$touch$1(controlViewHolder, this, control, str), usePanel), isAuthRequired(controlViewHolder));
    }

    public ControlActionCoordinatorImpl(Context context2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter2, KeyguardStateController keyguardStateController2, Optional<TaskViewFactory> optional, ControlsMetricsLogger controlsMetricsLogger2, VibratorHelper vibratorHelper) {
        this.context = context2;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.activityStarter = activityStarter2;
        this.keyguardStateController = keyguardStateController2;
        this.taskViewFactory = optional;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        this.vibrator = vibratorHelper;
    }

    public static boolean isAuthRequired(ControlViewHolder controlViewHolder) {
        ControlWithState cws = controlViewHolder.getCws();
        Objects.requireNonNull(cws);
        Control control = cws.control;
        if (control == null) {
            return true;
        }
        return control.isAuthRequired();
    }

    public final void runPendingAction(String str) {
        String str2;
        if (!isLocked()) {
            Action action = this.pendingAction;
            if (action == null) {
                str2 = null;
            } else {
                str2 = action.controlId;
            }
            if (Intrinsics.areEqual(str2, str)) {
                Action action2 = this.pendingAction;
                if (action2 != null) {
                    action2.invoke();
                }
                this.pendingAction = null;
            }
        }
    }

    public final void setActivityContext(Context context2) {
        this.activityContext = context2;
    }
}
