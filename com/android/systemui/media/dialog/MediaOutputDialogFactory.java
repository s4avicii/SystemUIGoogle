package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;

/* compiled from: MediaOutputDialogFactory.kt */
public final class MediaOutputDialogFactory {
    public static MediaOutputDialog mediaOutputDialog;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final SystemUIDialogManager dialogManager;
    public final LocalBluetoothManager lbm;
    public final MediaSessionManager mediaSessionManager;
    public final CommonNotifCollection notifCollection;
    public final ShadeController shadeController;
    public final ActivityStarter starter;
    public final UiEventLogger uiEventLogger;

    public final void create(String str, boolean z, View view) {
        MediaOutputDialog mediaOutputDialog2 = mediaOutputDialog;
        if (mediaOutputDialog2 != null) {
            mediaOutputDialog2.dismiss();
        }
        MediaOutputController mediaOutputController = new MediaOutputController(this.context, str, this.mediaSessionManager, this.lbm, this.shadeController, this.starter, this.notifCollection, this.dialogLaunchAnimator);
        MediaOutputDialog mediaOutputDialog3 = new MediaOutputDialog(this.context, z, mediaOutputController, this.uiEventLogger, this.dialogManager);
        mediaOutputDialog = mediaOutputDialog3;
        if (view != null) {
            DialogLaunchAnimator dialogLaunchAnimator2 = this.dialogLaunchAnimator;
            LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
            dialogLaunchAnimator2.showFromView(mediaOutputDialog3, view, false);
            return;
        }
        mediaOutputDialog3.show();
    }

    public MediaOutputDialogFactory(Context context2, MediaSessionManager mediaSessionManager2, LocalBluetoothManager localBluetoothManager, ShadeController shadeController2, ActivityStarter activityStarter, CommonNotifCollection commonNotifCollection, UiEventLogger uiEventLogger2, DialogLaunchAnimator dialogLaunchAnimator2, SystemUIDialogManager systemUIDialogManager) {
        this.context = context2;
        this.mediaSessionManager = mediaSessionManager2;
        this.lbm = localBluetoothManager;
        this.shadeController = shadeController2;
        this.starter = activityStarter;
        this.notifCollection = commonNotifCollection;
        this.uiEventLogger = uiEventLogger2;
        this.dialogLaunchAnimator = dialogLaunchAnimator2;
        this.dialogManager = systemUIDialogManager;
    }
}
