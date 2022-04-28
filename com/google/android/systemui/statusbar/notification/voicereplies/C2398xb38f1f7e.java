package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$5 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2398xb38f1f7e extends Lambda implements Function2<VoiceReplyTarget, VoiceReplyTarget, Boolean> {
    public static final C2398xb38f1f7e INSTANCE = new C2398xb38f1f7e();

    public C2398xb38f1f7e() {
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
