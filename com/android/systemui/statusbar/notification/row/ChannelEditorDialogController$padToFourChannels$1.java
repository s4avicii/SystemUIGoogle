package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ChannelEditorDialogController.kt */
final class ChannelEditorDialogController$padToFourChannels$1 extends Lambda implements Function1<NotificationChannel, Boolean> {
    public final /* synthetic */ ChannelEditorDialogController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ChannelEditorDialogController$padToFourChannels$1(ChannelEditorDialogController channelEditorDialogController) {
        super(1);
        this.this$0 = channelEditorDialogController;
    }

    public final Object invoke(Object obj) {
        ChannelEditorDialogController channelEditorDialogController = this.this$0;
        Objects.requireNonNull(channelEditorDialogController);
        return Boolean.valueOf(channelEditorDialogController.paddedChannels.contains((NotificationChannel) obj));
    }
}
