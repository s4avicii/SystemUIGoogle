package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.function.BiFunction;
import kotlin.jvm.functions.Function0;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$getOrPut$1<T, U, R> implements BiFunction {
    public final /* synthetic */ Function0<Object> $default;

    public NotificationVoiceReplyManagerKt$getOrPut$1(Function0<Object> function0) {
        this.$default = function0;
    }

    public final Object apply(Object obj, Object obj2) {
        if (obj2 == null) {
            return this.$default.invoke();
        }
        return obj2;
    }
}
