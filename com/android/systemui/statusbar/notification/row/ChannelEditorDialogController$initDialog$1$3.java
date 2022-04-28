package com.android.systemui.statusbar.notification.row;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.content.DialogInterface;
import com.android.systemui.util.Assert;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialogController$initDialog$1$3 implements DialogInterface.OnShowListener {
    public final /* synthetic */ ChannelEditorListView $listView;
    public final /* synthetic */ ChannelEditorDialogController this$0;

    public ChannelEditorDialogController$initDialog$1$3(ChannelEditorDialogController channelEditorDialogController, ChannelEditorListView channelEditorListView) {
        this.this$0 = channelEditorDialogController;
        this.$listView = channelEditorListView;
    }

    public final void onShow(DialogInterface dialogInterface) {
        Iterator it = this.this$0.providedChannels.iterator();
        while (it.hasNext()) {
            NotificationChannel notificationChannel = (NotificationChannel) it.next();
            ChannelEditorListView channelEditorListView = this.$listView;
            if (channelEditorListView != null) {
                Assert.isMainThread();
                Iterator it2 = channelEditorListView.channelRows.iterator();
                while (it2.hasNext()) {
                    ChannelRow channelRow = (ChannelRow) it2.next();
                    Objects.requireNonNull(channelRow);
                    if (Intrinsics.areEqual(channelRow.channel, notificationChannel)) {
                        ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{0, Integer.valueOf(channelRow.highlightColor)});
                        ofObject.setDuration(200);
                        ofObject.addUpdateListener(new ChannelRow$playHighlight$1(channelRow));
                        ofObject.setRepeatMode(2);
                        ofObject.setRepeatCount(5);
                        ofObject.start();
                    }
                }
            }
        }
    }
}
