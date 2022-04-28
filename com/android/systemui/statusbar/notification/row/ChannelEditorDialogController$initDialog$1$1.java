package com.android.systemui.statusbar.notification.row;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialogController$initDialog$1$1 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ ChannelEditorDialogController this$0;

    public ChannelEditorDialogController$initDialog$1$1(ChannelEditorDialogController channelEditorDialogController) {
        this.this$0 = channelEditorDialogController;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        ChannelEditorDialogController channelEditorDialogController = this.this$0;
        Objects.requireNonNull(channelEditorDialogController);
        OnChannelEditorDialogFinishedListener onChannelEditorDialogFinishedListener = channelEditorDialogController.onFinishListener;
        if (onChannelEditorDialogFinishedListener != null) {
            onChannelEditorDialogFinishedListener.onChannelEditorDialogFinished();
        }
    }
}
