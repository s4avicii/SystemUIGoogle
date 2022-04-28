package com.android.systemui.statusbar.notification;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.internal.widget.MessagingImageMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ConversationNotifications.kt */
final class AnimatedImageNotificationManager$updateAnimatedImageDrawables$5 extends Lambda implements Function1<View, AnimatedImageDrawable> {
    public static final AnimatedImageNotificationManager$updateAnimatedImageDrawables$5 INSTANCE = new AnimatedImageNotificationManager$updateAnimatedImageDrawables$5();

    public AnimatedImageNotificationManager$updateAnimatedImageDrawables$5() {
        super(1);
    }

    public final Object invoke(Object obj) {
        MessagingImageMessage messagingImageMessage;
        MessagingImageMessage messagingImageMessage2 = (View) obj;
        if (messagingImageMessage2 instanceof MessagingImageMessage) {
            messagingImageMessage = messagingImageMessage2;
        } else {
            messagingImageMessage = null;
        }
        if (messagingImageMessage == null) {
            return null;
        }
        Drawable drawable = messagingImageMessage.getDrawable();
        if (drawable instanceof AnimatedImageDrawable) {
            return (AnimatedImageDrawable) drawable;
        }
        return null;
    }
}
