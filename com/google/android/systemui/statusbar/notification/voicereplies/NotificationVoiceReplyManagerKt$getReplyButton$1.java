package com.google.android.systemui.statusbar.notification.voicereplies;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$getReplyButton$1 extends Lambda implements Function1<View, Boolean> {
    public static final NotificationVoiceReplyManagerKt$getReplyButton$1 INSTANCE = new NotificationVoiceReplyManagerKt$getReplyButton$1();

    public NotificationVoiceReplyManagerKt$getReplyButton$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (((View) obj).getId() == 16908716) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
