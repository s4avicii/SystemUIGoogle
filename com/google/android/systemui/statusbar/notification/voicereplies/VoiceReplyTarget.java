package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.os.PowerManager;
import android.widget.Button;
import androidx.preference.R$color;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget {
    public final PendingIntent actionIntent;
    public final Notification.Builder builder;
    public final NotificationEntry entry;
    public final Button expandedViewReplyButton;
    public final RemoteInput freeformInput;
    public final List<NotificationVoiceReplyHandler> handlers;
    public final NotificationVoiceReplyLogger logger;
    public final String notifKey;
    public final NotificationShadeWindowController notifShadeWindowController;
    public final NotificationRemoteInputManager notificationRemoteInputManager;
    public final long postTime;
    public final PowerManager powerManager;
    public final RemoteInput[] remoteInputs;
    public final LockscreenShadeTransitionController shadeTransitionController;
    public final StatusBar statusBar;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final SysuiStatusBarStateController statusBarStateController;
    public final int userId;

    public VoiceReplyTarget(NotificationEntry notificationEntry, Notification.Builder builder2, long j, List<? extends NotificationVoiceReplyHandler> list, PendingIntent pendingIntent, RemoteInput[] remoteInputArr, RemoteInput remoteInput, Button button, NotificationRemoteInputManager notificationRemoteInputManager2, LockscreenShadeTransitionController lockscreenShadeTransitionController, StatusBar statusBar2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationVoiceReplyLogger notificationVoiceReplyLogger, NotificationShadeWindowController notificationShadeWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager2, PowerManager powerManager2) {
        NotificationEntry notificationEntry2 = notificationEntry;
        this.entry = notificationEntry2;
        this.builder = builder2;
        this.postTime = j;
        this.handlers = list;
        this.actionIntent = pendingIntent;
        this.remoteInputs = remoteInputArr;
        this.freeformInput = remoteInput;
        this.expandedViewReplyButton = button;
        this.notificationRemoteInputManager = notificationRemoteInputManager2;
        this.shadeTransitionController = lockscreenShadeTransitionController;
        this.statusBar = statusBar2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.logger = notificationVoiceReplyLogger;
        this.notifShadeWindowController = notificationShadeWindowController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        this.powerManager = powerManager2;
        this.notifKey = notificationEntry2.mKey;
        this.userId = notificationEntry2.mSbn.getUser().getIdentifier();
    }

    public static Object awaitFocusState(RemoteInputView remoteInputView, boolean z, Continuation continuation) {
        boolean z2;
        RemoteInputView.RemoteEditText remoteEditText = remoteInputView.mEditText;
        if (remoteEditText == null || !remoteEditText.hasFocus()) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2 == z) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        VoiceReplyTarget$awaitFocusState$2$listener$1 voiceReplyTarget$awaitFocusState$2$listener$1 = new VoiceReplyTarget$awaitFocusState$2$listener$1(z, new AtomicBoolean(true), remoteInputView, cancellableContinuationImpl);
        remoteInputView.mEditTextFocusChangeListeners.add(voiceReplyTarget$awaitFocusState$2$listener$1);
        cancellableContinuationImpl.invokeOnCancellation(new VoiceReplyTarget$awaitFocusState$2$1(remoteInputView, voiceReplyTarget$awaitFocusState$2$listener$1));
        Object result = cancellableContinuationImpl.getResult();
        if (result == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return result;
        }
        return Unit.INSTANCE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object access$awaitKeyguardNotOccluded(com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r9, kotlin.coroutines.Continuation r10) {
        /*
            java.util.Objects.requireNonNull(r9)
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            boolean r1 = r10 instanceof com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardNotOccluded$1
            if (r1 == 0) goto L_0x0018
            r1 = r10
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardNotOccluded$1 r1 = (com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardNotOccluded$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            goto L_0x001d
        L_0x0018:
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardNotOccluded$1 r1 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardNotOccluded$1
            r1.<init>(r9, r10)
        L_0x001d:
            java.lang.Object r10 = r1.result
            int r2 = r1.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L_0x0040
            if (r2 == r4) goto L_0x0036
            if (r2 != r3) goto L_0x002e
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x00cc
        L_0x002e:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x0036:
            java.lang.Object r9 = r1.L$0
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r9 = (com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r1
            r1 = r0
            goto L_0x0078
        L_0x0040:
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r0
        L_0x0044:
            com.android.systemui.statusbar.NotificationShadeWindowController r2 = r9.notifShadeWindowController
            r1.L$0 = r9
            r1.label = r4
            kotlinx.coroutines.CancellableContinuationImpl r5 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r6 = androidx.preference.R$color.intercepted(r1)
            r5.<init>(r6, r4)
            r5.initCancellability()
            java.util.concurrent.atomic.AtomicBoolean r6 = new java.util.concurrent.atomic.AtomicBoolean
            r6.<init>(r4)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 r7 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1
            r7.<init>(r6, r2, r5)
            r2.registerCallback(r7)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$awaitStateChange$2$1 r6 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$awaitStateChange$2$1
            r6.<init>(r2, r7)
            r5.invokeOnCancellation(r6)
            java.lang.Object r2 = r5.getResult()
            if (r2 != r0) goto L_0x0073
            goto L_0x00fe
        L_0x0073:
            r8 = r0
            r0 = r10
            r10 = r2
            r2 = r1
            r1 = r8
        L_0x0078:
            com.google.android.systemui.statusbar.notification.voicereplies.StatusBarWindowState r10 = (com.google.android.systemui.statusbar.notification.voicereplies.StatusBarWindowState) r10
            java.util.Objects.requireNonNull(r10)
            boolean r5 = r10.bouncerShowing
            if (r5 != 0) goto L_0x00d4
            boolean r5 = r10.keyguardShowing
            if (r5 != 0) goto L_0x0086
            goto L_0x00d4
        L_0x0086:
            boolean r10 = r10.keyguardOccluded
            if (r10 != 0) goto L_0x00cf
            r10 = 0
            r2.L$0 = r10
            r2.label = r3
            java.util.Objects.requireNonNull(r9)
            kotlinx.coroutines.CancellableContinuationImpl r10 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r2 = androidx.preference.R$color.intercepted(r2)
            r10.<init>(r2, r4)
            r10.initCancellability()
            java.util.concurrent.atomic.AtomicBoolean r2 = new java.util.concurrent.atomic.AtomicBoolean
            r2.<init>(r4)
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardReset$2$callback$1 r3 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardReset$2$callback$1
            r3.<init>(r2, r9, r10)
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r2 = r9.statusBarKeyguardViewManager
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.phone.KeyguardBouncer r2 = r2.mBouncer
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.util.ListenerSet<com.android.systemui.statusbar.phone.KeyguardBouncer$KeyguardResetCallback> r2 = r2.mResetCallbacks
            r2.addIfAbsent(r3)
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardReset$2$1 r2 = new com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget$awaitKeyguardReset$2$1
            r2.<init>(r9, r3)
            r10.invokeOnCancellation(r2)
            java.lang.Object r9 = r10.getResult()
            if (r9 != r0) goto L_0x00c6
            goto L_0x00c8
        L_0x00c6:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
        L_0x00c8:
            if (r9 != r1) goto L_0x00cc
            r0 = r1
            goto L_0x00fe
        L_0x00cc:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            goto L_0x00fe
        L_0x00cf:
            r10 = r0
            r0 = r1
            r1 = r2
            goto L_0x0044
        L_0x00d4:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r9 = r9.logger
            java.util.Objects.requireNonNull(r9)
            com.android.internal.logging.UiEventLogger r0 = r9.eventLogger
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent r1 = com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyEvent.SESSION_FAILED_BAD_WINDOW_STATE
            r0.log(r1)
            com.android.systemui.log.LogBuffer r9 = r9.logBuffer
            com.android.systemui.log.LogLevel r0 = com.android.systemui.log.LogLevel.DEBUG
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logBadWindowState$2 r1 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logBadWindowState$2.INSTANCE
            java.util.Objects.requireNonNull(r9)
            boolean r2 = r9.frozen
            if (r2 != 0) goto L_0x00fc
            java.lang.String r2 = "NotifVoiceReply"
            com.android.systemui.log.LogMessageImpl r0 = r9.obtain(r2, r0, r1)
            java.lang.String r10 = r10.toString()
            r0.str1 = r10
            r9.push(r0)
        L_0x00fc:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
        L_0x00fe:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget.access$awaitKeyguardNotOccluded(com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Object access$expandShade(VoiceReplyTarget voiceReplyTarget, Continuation continuation) {
        Objects.requireNonNull(voiceReplyTarget);
        LogLevel logLevel = LogLevel.DEBUG;
        int state = voiceReplyTarget.statusBarStateController.getState();
        boolean z = true;
        if (state != 0) {
            if (state == 1) {
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(continuation), 1);
                cancellableContinuationImpl.initCancellability();
                NotificationVoiceReplyLogger notificationVoiceReplyLogger = voiceReplyTarget.logger;
                Objects.requireNonNull(notificationVoiceReplyLogger);
                LogBuffer logBuffer = notificationVoiceReplyLogger.logBuffer;
                NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("On keyguard, waiting for SHADE_LOCKED state");
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
                }
                VoiceReplyTarget$expandShade$2$callback$1 voiceReplyTarget$expandShade$2$callback$1 = new VoiceReplyTarget$expandShade$2$callback$1(new AtomicBoolean(true), voiceReplyTarget, cancellableContinuationImpl);
                cancellableContinuationImpl.invokeOnCancellation(new VoiceReplyTarget$expandShade$2$1(voiceReplyTarget, voiceReplyTarget$expandShade$2$callback$1));
                voiceReplyTarget.statusBarStateController.addCallback(voiceReplyTarget$expandShade$2$callback$1);
                LockscreenShadeTransitionController lockscreenShadeTransitionController = voiceReplyTarget.shadeTransitionController;
                NotificationEntry notificationEntry = voiceReplyTarget.entry;
                Objects.requireNonNull(notificationEntry);
                lockscreenShadeTransitionController.goToLockedShade(notificationEntry.row, true);
                return cancellableContinuationImpl.getResult();
            } else if (state != 2) {
                NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = voiceReplyTarget.logger;
                Objects.requireNonNull(notificationVoiceReplyLogger2);
                LogBuffer logBuffer2 = notificationVoiceReplyLogger2.logBuffer;
                NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$22 = new NotificationVoiceReplyLogger$logStatic$2("Unknown state, cancelling");
                Objects.requireNonNull(logBuffer2);
                if (!logBuffer2.frozen) {
                    logBuffer2.push(logBuffer2.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$22));
                }
                z = false;
            }
        } else if (!voiceReplyTarget.statusBarStateController.isExpanded()) {
            CancellableContinuationImpl cancellableContinuationImpl2 = new CancellableContinuationImpl(R$color.intercepted(continuation), 1);
            cancellableContinuationImpl2.initCancellability();
            NotificationVoiceReplyLogger notificationVoiceReplyLogger3 = voiceReplyTarget.logger;
            Objects.requireNonNull(notificationVoiceReplyLogger3);
            LogBuffer logBuffer3 = notificationVoiceReplyLogger3.logBuffer;
            NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$23 = new NotificationVoiceReplyLogger$logStatic$2("Shade collapsed, waiting for expansion");
            Objects.requireNonNull(logBuffer3);
            if (!logBuffer3.frozen) {
                logBuffer3.push(logBuffer3.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$23));
            }
            VoiceReplyTarget$expandShade$3$callback$1 voiceReplyTarget$expandShade$3$callback$1 = new VoiceReplyTarget$expandShade$3$callback$1(new AtomicBoolean(true), voiceReplyTarget, cancellableContinuationImpl2);
            cancellableContinuationImpl2.invokeOnCancellation(new VoiceReplyTarget$expandShade$3$1(voiceReplyTarget, voiceReplyTarget$expandShade$3$callback$1));
            voiceReplyTarget.statusBarStateController.addCallback(voiceReplyTarget$expandShade$3$callback$1);
            StatusBar statusBar2 = voiceReplyTarget.statusBar;
            Objects.requireNonNull(statusBar2);
            statusBar2.mCommandQueueCallbacks.animateExpandNotificationsPanel();
            return cancellableContinuationImpl2.getResult();
        }
        return Boolean.valueOf(z);
    }
}
