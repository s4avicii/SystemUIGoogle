package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelRow$onFinishInflate$1 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ ChannelRow this$0;

    public ChannelRow$onFinishInflate$1(ChannelRow channelRow) {
        this.this$0 = channelRow;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i;
        int i2;
        ChannelRow channelRow = this.this$0;
        Objects.requireNonNull(channelRow);
        NotificationChannel notificationChannel = channelRow.channel;
        if (notificationChannel != null) {
            ChannelRow channelRow2 = this.this$0;
            Objects.requireNonNull(channelRow2);
            ChannelEditorDialogController channelEditorDialogController = channelRow2.controller;
            ChannelEditorDialog channelEditorDialog = null;
            if (channelEditorDialogController == null) {
                channelEditorDialogController = null;
            }
            boolean z2 = false;
            if (z) {
                i = notificationChannel.getImportance();
            } else {
                i = 0;
            }
            Objects.requireNonNull(channelEditorDialogController);
            if (notificationChannel.getImportance() == i) {
                channelEditorDialogController.edits.remove(notificationChannel);
            } else {
                channelEditorDialogController.edits.put(notificationChannel, Integer.valueOf(i));
            }
            ChannelEditorDialog channelEditorDialog2 = channelEditorDialogController.dialog;
            if (channelEditorDialog2 != null) {
                channelEditorDialog = channelEditorDialog2;
            }
            if ((!channelEditorDialogController.edits.isEmpty()) || !Intrinsics.areEqual(Boolean.valueOf(channelEditorDialogController.appNotificationsEnabled), channelEditorDialogController.appNotificationsCurrentlyEnabled)) {
                z2 = true;
            }
            Objects.requireNonNull(channelEditorDialog);
            TextView textView = (TextView) channelEditorDialog.findViewById(C1777R.C1779id.done_button);
            if (textView != null) {
                if (z2) {
                    i2 = C1777R.string.inline_ok_button;
                } else {
                    i2 = C1777R.string.inline_done_button;
                }
                textView.setText(i2);
            }
        }
    }
}
