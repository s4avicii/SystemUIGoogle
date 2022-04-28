package com.android.systemui.statusbar.notification.row;

import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelEditorListView$updateAppControlRow$1 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ ChannelEditorListView this$0;

    public ChannelEditorListView$updateAppControlRow$1(ChannelEditorListView channelEditorListView) {
        this.this$0 = channelEditorListView;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i;
        ChannelEditorListView channelEditorListView = this.this$0;
        Objects.requireNonNull(channelEditorListView);
        ChannelEditorDialogController channelEditorDialogController = channelEditorListView.controller;
        ChannelEditorDialog channelEditorDialog = null;
        if (channelEditorDialogController == null) {
            channelEditorDialogController = null;
        }
        Objects.requireNonNull(channelEditorDialogController);
        channelEditorDialogController.appNotificationsEnabled = z;
        ChannelEditorDialog channelEditorDialog2 = channelEditorDialogController.dialog;
        if (channelEditorDialog2 != null) {
            channelEditorDialog = channelEditorDialog2;
        }
        boolean z2 = true;
        if (!(!channelEditorDialogController.edits.isEmpty()) && Intrinsics.areEqual(Boolean.valueOf(channelEditorDialogController.appNotificationsEnabled), channelEditorDialogController.appNotificationsCurrentlyEnabled)) {
            z2 = false;
        }
        Objects.requireNonNull(channelEditorDialog);
        TextView textView = (TextView) channelEditorDialog.findViewById(C1777R.C1779id.done_button);
        if (textView != null) {
            if (z2) {
                i = C1777R.string.inline_ok_button;
            } else {
                i = C1777R.string.inline_done_button;
            }
            textView.setText(i);
        }
        this.this$0.updateRows();
    }
}
