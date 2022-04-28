package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputViewController;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableSharedFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {793, 801, 805, 829, 834, 837}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class VoiceReplyTarget$focus$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ AuthStateRef $authState;
    public final /* synthetic */ MutableSharedFlow<Pair<String, RemoteInputViewController>> $remoteInputViewActivatedForVoiceReply;
    public final /* synthetic */ String $userMessageContent;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VoiceReplyTarget$focus$2(VoiceReplyTarget voiceReplyTarget, String str, MutableSharedFlow<Pair<String, RemoteInputViewController>> mutableSharedFlow, AuthStateRef authStateRef, Continuation<? super VoiceReplyTarget$focus$2> continuation) {
        super(2, continuation);
        this.this$0 = voiceReplyTarget;
        this.$userMessageContent = str;
        this.$remoteInputViewActivatedForVoiceReply = mutableSharedFlow;
        this.$authState = authStateRef;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        VoiceReplyTarget$focus$2 voiceReplyTarget$focus$2 = new VoiceReplyTarget$focus$2(this.this$0, this.$userMessageContent, this.$remoteInputViewActivatedForVoiceReply, this.$authState, continuation);
        voiceReplyTarget$focus$2.L$0 = obj;
        return voiceReplyTarget$focus$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((VoiceReplyTarget$focus$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0298, code lost:
        if (r5 != r2) goto L_0x029b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x029b, code lost:
        r5 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x029d, code lost:
        if (r5 != r2) goto L_0x02a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x029f, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02a0, code lost:
        r5 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r5);
        r5 = r5.logBuffer;
        r9 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Waiting for session end (RemoteInputView focus lost)");
        java.util.Objects.requireNonNull(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02b5, code lost:
        if (r5.frozen != false) goto L_0x02be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02b7, code lost:
        r5.push(r5.obtain("NotifVoiceReply", r0, r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02be, code lost:
        r0 = r1.this$0;
        r1.L$0 = null;
        r1.label = 6;
        java.util.Objects.requireNonNull(r0);
        r0 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.awaitFocusState(r3, false, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02cb, code lost:
        if (r0 != r2) goto L_0x02ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02ce, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02d0, code lost:
        if (r0 != r2) goto L_0x02d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02d2, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02d3, code lost:
        r0 = r1.$authState;
        java.util.Objects.requireNonNull(r0);
        r0.value = 0;
        r1.this$0.logger.logSessionEnd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02e3, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0126, code lost:
        r11 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r11);
        r11 = r11.logBuffer;
        r13 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Doze has ended");
        java.util.Objects.requireNonNull(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x013b, code lost:
        if (r11.frozen != false) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x013d, code lost:
        r11.push(r11.obtain("NotifVoiceReply", r0, r13));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0146, code lost:
        r11 = r1.this$0.statusBarKeyguardViewManager;
        java.util.Objects.requireNonNull(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x014f, code lost:
        if (r11.mOccluded == false) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0151, code lost:
        r11 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r11);
        r11 = r11.logBuffer;
        r13 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Waiting for keyguard occlusion to end");
        java.util.Objects.requireNonNull(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0166, code lost:
        if (r11.frozen != false) goto L_0x016f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0168, code lost:
        r11.push(r11.obtain("NotifVoiceReply", r0, r13));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x016f, code lost:
        r11 = r1.this$0;
        r1.L$0 = r3;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0179, code lost:
        if (com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.access$awaitKeyguardNotOccluded(r11, r1) != r2) goto L_0x017c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x017b, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x017c, code lost:
        r5 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r5);
        r5 = r5.logBuffer;
        r12 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Keyguard no longer occluded");
        java.util.Objects.requireNonNull(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0191, code lost:
        if (r5.frozen != false) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0193, code lost:
        r5.push(r5.obtain("NotifVoiceReply", r0, r12));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x019a, code lost:
        r5 = r1.this$0;
        r1.L$0 = r3;
        r1.label = 3;
        r5 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.access$expandShade(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01a4, code lost:
        if (r5 != r2) goto L_0x01a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01a6, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01ad, code lost:
        if (((java.lang.Boolean) r5).booleanValue() != false) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01af, code lost:
        r2 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r2);
        r2.eventLogger.log(com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_SHADE_STATE);
        r2 = r2.logBuffer;
        r3 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Could not expand shade, aborting");
        java.util.Objects.requireNonNull(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01cb, code lost:
        if (r2.frozen != false) goto L_0x01d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01cd, code lost:
        r2.push(r2.obtain("NotifVoiceReply", r0, r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01d4, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01d6, code lost:
        r2 = r1.$authState;
        java.util.Objects.requireNonNull(r2);
        r2.value = 0;
        r1.this$0.logger.logSessionEnd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01e4, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        r5 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r5);
        r5 = r5.logBuffer;
        r11 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Shade expanded");
        java.util.Objects.requireNonNull(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01fa, code lost:
        if (r5.frozen != false) goto L_0x0203;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01fc, code lost:
        r5.push(r5.obtain("NotifVoiceReply", r0, r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0203, code lost:
        r5 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2$authBypassCheck$1(r1.$authState);
        r6 = r1.this$0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0222, code lost:
        if (r6.notificationRemoteInputManager.activateRemoteInput(r6.expandedViewReplyButton, r6.remoteInputs, r6.freeformInput, r6.actionIntent, (com.android.systemui.statusbar.notification.collection.NotificationEntry.EditedSuggestionInfo) null, r1.$userMessageContent, r5) != false) goto L_0x0248;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0224, code lost:
        r0 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r0);
        android.util.Log.e("NotifVoiceReply", "Could not activate remote input for voice reply");
        r0.eventLogger.log(com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_REMOTE_INPUT_STATE);
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0239, code lost:
        r2 = r1.$authState;
        java.util.Objects.requireNonNull(r2);
        r2.value = 0;
        r1.this$0.logger.logSessionEnd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0247, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:?, code lost:
        java.util.Objects.requireNonNull(r3);
        r3 = r3.mExpandedRemoteInput;
        r5 = r1.$remoteInputViewActivatedForVoiceReply;
        r6 = r1.this$0;
        java.util.Objects.requireNonNull(r6);
        r6 = r6.notifKey;
        java.util.Objects.requireNonNull(r3);
        r12 = new kotlin.Pair(r6, r3.mViewController);
        r1.L$0 = r3;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0269, code lost:
        if (r5.emit(r12, r1) != r2) goto L_0x026c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x026b, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x026c, code lost:
        r5 = r1.this$0.logger;
        java.util.Objects.requireNonNull(r5);
        r5 = r5.logBuffer;
        r11 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2("Waiting for RemoteInputView focus");
        java.util.Objects.requireNonNull(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0281, code lost:
        if (r5.frozen != false) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0283, code lost:
        r5.push(r5.obtain("NotifVoiceReply", r0, r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x028a, code lost:
        r5 = r1.this$0;
        r1.L$0 = r3;
        r1.label = 5;
        java.util.Objects.requireNonNull(r5);
        r5 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.awaitFocusState(r3, true, r1);
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006c A[Catch:{ all -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006e A[Catch:{ all -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0080 A[Catch:{ all -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0082 A[Catch:{ all -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0088 A[Catch:{ all -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c2 A[SYNTHETIC, Splitter:B:47:0x00c2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r20) {
        /*
            r19 = this;
            r1 = r19
            com.android.systemui.log.LogLevel r0 = com.android.systemui.log.LogLevel.DEBUG
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r3 = r1.label
            r4 = 6
            r5 = 2
            r6 = 3
            r7 = 0
            r8 = 0
            r9 = 1
            java.lang.String r10 = "NotifVoiceReply"
            switch(r3) {
                case 0: goto L_0x004f;
                case 1: goto L_0x0046;
                case 2: goto L_0x003d;
                case 3: goto L_0x0032;
                case 4: goto L_0x0029;
                case 5: goto L_0x0020;
                case 6: goto L_0x001b;
                default: goto L_0x0013;
            }
        L_0x0013:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x001b:
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            goto L_0x02d3
        L_0x0020:
            java.lang.Object r3 = r1.L$0
            com.android.systemui.statusbar.policy.RemoteInputView r3 = (com.android.systemui.statusbar.policy.RemoteInputView) r3
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            goto L_0x02a0
        L_0x0029:
            java.lang.Object r3 = r1.L$0
            com.android.systemui.statusbar.policy.RemoteInputView r3 = (com.android.systemui.statusbar.policy.RemoteInputView) r3
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            goto L_0x026c
        L_0x0032:
            java.lang.Object r3 = r1.L$0
            com.android.systemui.statusbar.notification.row.NotificationContentView r3 = (com.android.systemui.statusbar.notification.row.NotificationContentView) r3
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            r5 = r20
            goto L_0x01a7
        L_0x003d:
            java.lang.Object r3 = r1.L$0
            com.android.systemui.statusbar.notification.row.NotificationContentView r3 = (com.android.systemui.statusbar.notification.row.NotificationContentView) r3
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            goto L_0x017c
        L_0x0046:
            java.lang.Object r3 = r1.L$0
            com.android.systemui.statusbar.notification.row.NotificationContentView r3 = (com.android.systemui.statusbar.notification.row.NotificationContentView) r3
            kotlin.ResultKt.throwOnFailure(r20)     // Catch:{ all -> 0x00ae }
            goto L_0x0126
        L_0x004f:
            kotlin.ResultKt.throwOnFailure(r20)
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.CoroutineScope r3 = (kotlinx.coroutines.CoroutineScope) r3
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r12 = r11.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r11 = r11.notifKey     // Catch:{ all -> 0x00ae }
            java.lang.String r13 = r1.$userMessageContent     // Catch:{ all -> 0x00ae }
            if (r13 == 0) goto L_0x0069
            int r13 = r13.length()     // Catch:{ all -> 0x00ae }
            if (r13 != 0) goto L_0x0067
            goto L_0x0069
        L_0x0067:
            r13 = r8
            goto L_0x006a
        L_0x0069:
            r13 = r9
        L_0x006a:
            if (r13 != 0) goto L_0x006e
            r13 = r9
            goto L_0x006f
        L_0x006e:
            r13 = r8
        L_0x006f:
            r12.logFocus(r11, r13)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r11.entry     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = r11.row     // Catch:{ all -> 0x00ae }
            if (r11 != 0) goto L_0x0082
            r11 = r7
            goto L_0x0086
        L_0x0082:
            com.android.systemui.statusbar.notification.row.NotificationContentView r11 = r11.getShowingLayout()     // Catch:{ all -> 0x00ae }
        L_0x0086:
            if (r11 != 0) goto L_0x00c2
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r2 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r2 = r2.logger     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x00ae }
            com.android.internal.logging.UiEventLogger r3 = r2.eventLogger     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent r4 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_NOTIF_STATE     // Catch:{ all -> 0x00ae }
            r3.log(r4)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r2 = r2.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r3 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            java.lang.String r4 = "Entry's \"showing layout\" is null"
            r3.<init>(r4)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x00ae }
            boolean r4 = r2.frozen     // Catch:{ all -> 0x00ae }
            if (r4 != 0) goto L_0x00b1
            com.android.systemui.log.LogMessageImpl r0 = r2.obtain(r10, r0, r3)     // Catch:{ all -> 0x00ae }
            r2.push(r0)     // Catch:{ all -> 0x00ae }
            goto L_0x00b1
        L_0x00ae:
            r0 = move-exception
            goto L_0x02e4
        L_0x00b1:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r2 = r1.$authState
            java.util.Objects.requireNonNull(r2)
            r2.value = r8
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r1 = r1.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r1 = r1.logger
            r1.logSessionEnd()
            return r0
        L_0x00c2:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r12 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.SysuiStatusBarStateController r12 = r12.statusBarStateController     // Catch:{ all -> 0x00ae }
            boolean r12 = r12.isDozing()     // Catch:{ all -> 0x00ae }
            if (r12 == 0) goto L_0x0145
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r12 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r12 = r12.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r13 = "Waiting for doze to end"
            java.util.Objects.requireNonNull(r12)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r12 = r12.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r14 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r14.<init>(r13)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r12)     // Catch:{ all -> 0x00ae }
            boolean r13 = r12.frozen     // Catch:{ all -> 0x00ae }
            if (r13 != 0) goto L_0x00ea
            com.android.systemui.log.LogMessageImpl r13 = r12.obtain(r10, r0, r14)     // Catch:{ all -> 0x00ae }
            r12.push(r13)     // Catch:{ all -> 0x00ae }
        L_0x00ea:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r12 = r1.this$0     // Catch:{ all -> 0x00ae }
            android.os.PowerManager r12 = r12.powerManager     // Catch:{ all -> 0x00ae }
            long r13 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00ae }
            java.lang.String r15 = "com.android.systemui:VOICE_REPLY_QUICK_PHRASE"
            r12.wakeUp(r13, r5, r15)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r12 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.SysuiStatusBarStateController r12 = r12.statusBarStateController     // Catch:{ all -> 0x00ae }
            r13 = 7
            kotlinx.coroutines.flow.SharedFlowImpl r13 = kotlinx.coroutines.flow.SharedFlowKt.MutableSharedFlow$default(r8, r13)     // Catch:{ all -> 0x00ae }
            r14 = -1
            kotlinx.coroutines.channels.AbstractChannel r14 = com.android.systemui.R$anim.Channel$default(r14, r4)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1 r15 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1     // Catch:{ all -> 0x00ae }
            r15.<init>(r12, r14, r7)     // Catch:{ all -> 0x00ae }
            kotlinx.coroutines.BuildersKt.launch$default(r3, r7, r7, r15, r6)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$2 r12 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$2     // Catch:{ all -> 0x00ae }
            r12.<init>(r14, r13, r7)     // Catch:{ all -> 0x00ae }
            kotlinx.coroutines.BuildersKt.launch$default(r3, r7, r7, r12, r6)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2$1 r3 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2$1     // Catch:{ all -> 0x00ae }
            r3.<init>(r7)     // Catch:{ all -> 0x00ae }
            r1.L$0 = r11     // Catch:{ all -> 0x00ae }
            r1.label = r9     // Catch:{ all -> 0x00ae }
            java.lang.Object r3 = kotlinx.coroutines.flow.FlowKt__ReduceKt.first(r13, r3, r1)     // Catch:{ all -> 0x00ae }
            if (r3 != r2) goto L_0x0125
            return r2
        L_0x0125:
            r3 = r11
        L_0x0126:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r11 = r11.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r12 = "Doze has ended"
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r11 = r11.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r13 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r13.<init>(r12)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            boolean r12 = r11.frozen     // Catch:{ all -> 0x00ae }
            if (r12 != 0) goto L_0x0146
            com.android.systemui.log.LogMessageImpl r12 = r11.obtain(r10, r0, r13)     // Catch:{ all -> 0x00ae }
            r11.push(r12)     // Catch:{ all -> 0x00ae }
            goto L_0x0146
        L_0x0145:
            r3 = r11
        L_0x0146:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r11 = r11.statusBarKeyguardViewManager     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            boolean r11 = r11.mOccluded     // Catch:{ all -> 0x00ae }
            if (r11 == 0) goto L_0x019a
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r11 = r11.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r12 = "Waiting for keyguard occlusion to end"
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r11 = r11.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r13 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r13.<init>(r12)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00ae }
            boolean r12 = r11.frozen     // Catch:{ all -> 0x00ae }
            if (r12 != 0) goto L_0x016f
            com.android.systemui.log.LogMessageImpl r12 = r11.obtain(r10, r0, r13)     // Catch:{ all -> 0x00ae }
            r11.push(r12)     // Catch:{ all -> 0x00ae }
        L_0x016f:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r11 = r1.this$0     // Catch:{ all -> 0x00ae }
            r1.L$0 = r3     // Catch:{ all -> 0x00ae }
            r1.label = r5     // Catch:{ all -> 0x00ae }
            java.lang.Object r5 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.access$awaitKeyguardNotOccluded(r11, r1)     // Catch:{ all -> 0x00ae }
            if (r5 != r2) goto L_0x017c
            return r2
        L_0x017c:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r5 = r5.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r11 = "Keyguard no longer occluded"
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r5 = r5.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r12 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r12.<init>(r11)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            boolean r11 = r5.frozen     // Catch:{ all -> 0x00ae }
            if (r11 != 0) goto L_0x019a
            com.android.systemui.log.LogMessageImpl r11 = r5.obtain(r10, r0, r12)     // Catch:{ all -> 0x00ae }
            r5.push(r11)     // Catch:{ all -> 0x00ae }
        L_0x019a:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            r1.L$0 = r3     // Catch:{ all -> 0x00ae }
            r1.label = r6     // Catch:{ all -> 0x00ae }
            java.lang.Object r5 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.access$expandShade(r5, r1)     // Catch:{ all -> 0x00ae }
            if (r5 != r2) goto L_0x01a7
            return r2
        L_0x01a7:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x00ae }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x00ae }
            if (r5 != 0) goto L_0x01e5
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r2 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r2 = r2.logger     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x00ae }
            com.android.internal.logging.UiEventLogger r3 = r2.eventLogger     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent r4 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_SHADE_STATE     // Catch:{ all -> 0x00ae }
            r3.log(r4)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r2 = r2.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r3 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            java.lang.String r4 = "Could not expand shade, aborting"
            r3.<init>(r4)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x00ae }
            boolean r4 = r2.frozen     // Catch:{ all -> 0x00ae }
            if (r4 != 0) goto L_0x01d4
            com.android.systemui.log.LogMessageImpl r0 = r2.obtain(r10, r0, r3)     // Catch:{ all -> 0x00ae }
            r2.push(r0)     // Catch:{ all -> 0x00ae }
        L_0x01d4:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r2 = r1.$authState
            java.util.Objects.requireNonNull(r2)
            r2.value = r8
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r1 = r1.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r1 = r1.logger
            r1.logSessionEnd()
            return r0
        L_0x01e5:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r5 = r5.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r6 = "Shade expanded"
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r5 = r5.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r11 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r11.<init>(r6)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            boolean r6 = r5.frozen     // Catch:{ all -> 0x00ae }
            if (r6 != 0) goto L_0x0203
            com.android.systemui.log.LogMessageImpl r6 = r5.obtain(r10, r0, r11)     // Catch:{ all -> 0x00ae }
            r5.push(r6)     // Catch:{ all -> 0x00ae }
        L_0x0203:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2$authBypassCheck$1 r5 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2$authBypassCheck$1     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r6 = r1.$authState     // Catch:{ all -> 0x00ae }
            r5.<init>(r6)     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r6 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.NotificationRemoteInputManager r11 = r6.notificationRemoteInputManager     // Catch:{ all -> 0x00ae }
            android.widget.Button r12 = r6.expandedViewReplyButton     // Catch:{ all -> 0x00ae }
            android.app.RemoteInput[] r13 = r6.remoteInputs     // Catch:{ all -> 0x00ae }
            android.app.RemoteInput r14 = r6.freeformInput     // Catch:{ all -> 0x00ae }
            android.app.PendingIntent r15 = r6.actionIntent     // Catch:{ all -> 0x00ae }
            r16 = 0
            java.lang.String r6 = r1.$userMessageContent     // Catch:{ all -> 0x00ae }
            r17 = r6
            r18 = r5
            boolean r5 = r11.activateRemoteInput(r12, r13, r14, r15, r16, r17, r18)     // Catch:{ all -> 0x00ae }
            if (r5 != 0) goto L_0x0248
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r0 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r0 = r0.logger     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x00ae }
            java.lang.String r2 = "Could not activate remote input for voice reply"
            android.util.Log.e(r10, r2)     // Catch:{ all -> 0x00ae }
            com.android.internal.logging.UiEventLogger r0 = r0.eventLogger     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent r2 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_REMOTE_INPUT_STATE     // Catch:{ all -> 0x00ae }
            r0.log(r2)     // Catch:{ all -> 0x00ae }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r2 = r1.$authState
            java.util.Objects.requireNonNull(r2)
            r2.value = r8
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r1 = r1.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r1 = r1.logger
            r1.logSessionEnd()
            return r0
        L_0x0248:
            java.util.Objects.requireNonNull(r3)     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.policy.RemoteInputView r3 = r3.mExpandedRemoteInput     // Catch:{ all -> 0x00ae }
            kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Pair<java.lang.String, com.android.systemui.statusbar.policy.RemoteInputViewController>> r5 = r1.$remoteInputViewActivatedForVoiceReply     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r6 = r1.this$0     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r6)     // Catch:{ all -> 0x00ae }
            java.lang.String r6 = r6.notifKey     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r3)     // Catch:{ all -> 0x00ae }
            com.android.systemui.statusbar.policy.RemoteInputViewController r11 = r3.mViewController     // Catch:{ all -> 0x00ae }
            kotlin.Pair r12 = new kotlin.Pair     // Catch:{ all -> 0x00ae }
            r12.<init>(r6, r11)     // Catch:{ all -> 0x00ae }
            r1.L$0 = r3     // Catch:{ all -> 0x00ae }
            r6 = 4
            r1.label = r6     // Catch:{ all -> 0x00ae }
            java.lang.Object r5 = r5.emit(r12, r1)     // Catch:{ all -> 0x00ae }
            if (r5 != r2) goto L_0x026c
            return r2
        L_0x026c:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r5 = r5.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r6 = "Waiting for RemoteInputView focus"
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r5 = r5.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r11 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r11.<init>(r6)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            boolean r6 = r5.frozen     // Catch:{ all -> 0x00ae }
            if (r6 != 0) goto L_0x028a
            com.android.systemui.log.LogMessageImpl r6 = r5.obtain(r10, r0, r11)     // Catch:{ all -> 0x00ae }
            r5.push(r6)     // Catch:{ all -> 0x00ae }
        L_0x028a:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            r1.L$0 = r3     // Catch:{ all -> 0x00ae }
            r6 = 5
            r1.label = r6     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            java.lang.Object r5 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.awaitFocusState(r3, r9, r1)     // Catch:{ all -> 0x00ae }
            if (r5 != r2) goto L_0x029b
            goto L_0x029d
        L_0x029b:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ae }
        L_0x029d:
            if (r5 != r2) goto L_0x02a0
            return r2
        L_0x02a0:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r1.this$0     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r5 = r5.logger     // Catch:{ all -> 0x00ae }
            java.lang.String r6 = "Waiting for session end (RemoteInputView focus lost)"
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            com.android.systemui.log.LogBuffer r5 = r5.logBuffer     // Catch:{ all -> 0x00ae }
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2 r9 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2     // Catch:{ all -> 0x00ae }
            r9.<init>(r6)     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00ae }
            boolean r6 = r5.frozen     // Catch:{ all -> 0x00ae }
            if (r6 != 0) goto L_0x02be
            com.android.systemui.log.LogMessageImpl r0 = r5.obtain(r10, r0, r9)     // Catch:{ all -> 0x00ae }
            r5.push(r0)     // Catch:{ all -> 0x00ae }
        L_0x02be:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r0 = r1.this$0     // Catch:{ all -> 0x00ae }
            r1.L$0 = r7     // Catch:{ all -> 0x00ae }
            r1.label = r4     // Catch:{ all -> 0x00ae }
            java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x00ae }
            java.lang.Object r0 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.awaitFocusState(r3, r8, r1)     // Catch:{ all -> 0x00ae }
            if (r0 != r2) goto L_0x02ce
            goto L_0x02d0
        L_0x02ce:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ae }
        L_0x02d0:
            if (r0 != r2) goto L_0x02d3
            return r2
        L_0x02d3:
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r0 = r1.$authState
            java.util.Objects.requireNonNull(r0)
            r0.value = r8
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r0 = r1.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r0 = r0.logger
            r0.logSessionEnd()
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x02e4:
            com.google.android.systemui.statusbar.notification.voicereplies.AuthStateRef r2 = r1.$authState
            java.util.Objects.requireNonNull(r2)
            r2.value = r8
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r1 = r1.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r1 = r1.logger
            r1.logSessionEnd()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$focus$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
