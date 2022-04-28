package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$3 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2395x3432e90b extends Lambda implements Function2<VoiceReplyTarget, VoiceReplyTarget, Boolean> {
    public static final C2395x3432e90b INSTANCE = new C2395x3432e90b();

    public C2395x3432e90b() {
        super(2);
    }

    public final Object invoke(Object obj, Object obj2) {
        Integer num;
        VoiceReplyTarget voiceReplyTarget = (VoiceReplyTarget) obj;
        VoiceReplyTarget voiceReplyTarget2 = (VoiceReplyTarget) obj2;
        Integer num2 = null;
        if (voiceReplyTarget == null) {
            num = null;
        } else {
            num = Integer.valueOf(voiceReplyTarget.userId);
        }
        if (voiceReplyTarget2 != null) {
            num2 = Integer.valueOf(voiceReplyTarget2.userId);
        }
        return Boolean.valueOf(Intrinsics.areEqual(num, num2));
    }
}
