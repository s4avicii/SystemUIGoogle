package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.Sequence;

/* compiled from: ChannelEditorDialogController.kt */
final class ChannelEditorDialogController$getDisplayableChannels$channels$1 extends Lambda implements Function1<NotificationChannelGroup, Sequence<? extends NotificationChannel>> {
    public static final ChannelEditorDialogController$getDisplayableChannels$channels$1 INSTANCE = new ChannelEditorDialogController$getDisplayableChannels$channels$1();

    public ChannelEditorDialogController$getDisplayableChannels$channels$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return new FilteringSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((NotificationChannelGroup) obj).getChannels()), false, C13071.INSTANCE);
    }
}
