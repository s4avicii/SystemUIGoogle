package com.android.systemui.p006qs.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.AnimatedDialog;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.p006qs.PseudoGridView;
import com.android.systemui.p006qs.QSUserSwitcherEvent;
import com.android.systemui.p006qs.tiles.UserDetailView;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Iterator;
import java.util.Objects;
import javax.inject.Provider;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.user.UserSwitchDialogController */
/* compiled from: UserSwitchDialogController.kt */
public final class UserSwitchDialogController {
    public static final Intent USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");
    public final ActivityStarter activityStarter;
    public final Function1<Context, SystemUIDialog> dialogFactory;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FalsingManager falsingManager;
    public final UiEventLogger uiEventLogger;
    public final Provider<UserDetailView.Adapter> userDetailViewAdapterProvider;

    /* renamed from: com.android.systemui.qs.user.UserSwitchDialogController$DialogShower */
    /* compiled from: UserSwitchDialogController.kt */
    public interface DialogShower extends DialogInterface {
        void showDialog(SystemUIDialog systemUIDialog);
    }

    /* renamed from: com.android.systemui.qs.user.UserSwitchDialogController$DialogShowerImpl */
    /* compiled from: UserSwitchDialogController.kt */
    public static final class DialogShowerImpl implements DialogInterface, DialogShower {
        public final Dialog animateFrom;
        public final DialogLaunchAnimator dialogLaunchAnimator;

        public final void cancel() {
            this.animateFrom.cancel();
        }

        public final void dismiss() {
            this.animateFrom.dismiss();
        }

        public final void showDialog(SystemUIDialog systemUIDialog) {
            ViewGroup viewGroup;
            AnimatedDialog animatedDialog;
            DialogLaunchAnimator dialogLaunchAnimator2 = this.dialogLaunchAnimator;
            Dialog dialog = this.animateFrom;
            LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
            Objects.requireNonNull(dialogLaunchAnimator2);
            Iterator<AnimatedDialog> it = dialogLaunchAnimator2.openedDialogs.iterator();
            while (true) {
                viewGroup = null;
                if (!it.hasNext()) {
                    animatedDialog = null;
                    break;
                }
                animatedDialog = it.next();
                AnimatedDialog animatedDialog2 = animatedDialog;
                Objects.requireNonNull(animatedDialog2);
                if (Intrinsics.areEqual(animatedDialog2.dialog, dialog)) {
                    break;
                }
            }
            AnimatedDialog animatedDialog3 = animatedDialog;
            if (animatedDialog3 != null) {
                viewGroup = animatedDialog3.dialogContentWithBackground;
            }
            if (viewGroup != null) {
                dialogLaunchAnimator2.showFromView(systemUIDialog, viewGroup, false);
                return;
            }
            throw new IllegalStateException("The animateFrom dialog was not animated using DialogLaunchAnimator.showFrom(View|Dialog)");
        }

        public DialogShowerImpl(SystemUIDialog systemUIDialog, DialogLaunchAnimator dialogLaunchAnimator2) {
            this.animateFrom = systemUIDialog;
            this.dialogLaunchAnimator = dialogLaunchAnimator2;
        }
    }

    public UserSwitchDialogController() {
        throw null;
    }

    public UserSwitchDialogController(Provider<UserDetailView.Adapter> provider, ActivityStarter activityStarter2, FalsingManager falsingManager2, DialogLaunchAnimator dialogLaunchAnimator2, UiEventLogger uiEventLogger2, Function1<? super Context, ? extends SystemUIDialog> function1) {
        this.userDetailViewAdapterProvider = provider;
        this.activityStarter = activityStarter2;
        this.falsingManager = falsingManager2;
        this.dialogLaunchAnimator = dialogLaunchAnimator2;
        this.uiEventLogger = uiEventLogger2;
        this.dialogFactory = function1;
    }

    public final void showDialog(View view) {
        SystemUIDialog invoke = this.dialogFactory.invoke(view.getContext());
        Objects.requireNonNull(invoke);
        SystemUIDialog.setShowForAllUsers(invoke);
        invoke.setCanceledOnTouchOutside(true);
        invoke.setTitle(C1777R.string.qs_user_switch_dialog_title);
        invoke.setPositiveButton(C1777R.string.quick_settings_done, new UserSwitchDialogController$showDialog$1$1(this));
        invoke.setButton(-3, C1777R.string.quick_settings_more_user_settings, new UserSwitchDialogController$showDialog$1$2(this, invoke), false);
        View inflate = LayoutInflater.from(invoke.getContext()).inflate(C1777R.layout.qs_user_dialog_content, (ViewGroup) null);
        invoke.setView(inflate);
        UserDetailView.Adapter adapter = this.userDetailViewAdapterProvider.get();
        Objects.requireNonNull(adapter);
        new PseudoGridView.ViewGroupAdapterBridge((ViewGroup) inflate.findViewById(C1777R.C1779id.grid), adapter);
        DialogLaunchAnimator dialogLaunchAnimator2 = this.dialogLaunchAnimator;
        LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
        dialogLaunchAnimator2.showFromView(invoke, view, false);
        this.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_DETAIL_OPEN);
        adapter.mDialogShower = new DialogShowerImpl(invoke, this.dialogLaunchAnimator);
    }
}
