package com.android.systemui.statusbar.policy;

/* compiled from: RemoteInputViewController.kt */
public final class RemoteInputViewControllerImpl$onSendRemoteInputListener$1 implements Runnable {
    public final /* synthetic */ RemoteInputViewControllerImpl this$0;

    public RemoteInputViewControllerImpl$onSendRemoteInputListener$1(RemoteInputViewControllerImpl remoteInputViewControllerImpl) {
        this.this$0 = remoteInputViewControllerImpl;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01ac  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r14 = this;
            com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl r0 = r14.this$0
            java.util.Objects.requireNonNull(r0)
            android.app.RemoteInput r0 = r0.remoteInput
            java.lang.String r1 = "RemoteInput"
            if (r0 != 0) goto L_0x0011
            java.lang.String r14 = "cannot send remote input, RemoteInput data is null"
            android.util.Log.e(r1, r14)
            return
        L_0x0011:
            com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl r2 = r14.this$0
            java.util.Objects.requireNonNull(r2)
            android.app.PendingIntent r2 = r2.pendingIntent
            if (r2 != 0) goto L_0x0020
            java.lang.String r14 = "cannot send remote input, PendingIntent is null"
            android.util.Log.e(r1, r14)
            return
        L_0x0020:
            com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl r3 = r14.this$0
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r3.entry
            android.view.ContentInfo r5 = r4.remoteInputAttachment
            r6 = 268435456(0x10000000, float:2.5243549E-29)
            r7 = 0
            r8 = 0
            r9 = 1
            if (r5 != 0) goto L_0x007a
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            java.lang.String r0 = r0.getResultKey()
            com.android.systemui.statusbar.policy.RemoteInputView r5 = r3.view
            android.text.Editable r5 = r5.getText()
            java.lang.String r5 = r5.toString()
            r4.putString(r0, r5)
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            android.content.Intent r0 = r0.addFlags(r6)
            android.app.RemoteInput[] r5 = r3.remoteInputs
            android.app.RemoteInput.addResultsToIntent(r5, r0, r4)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r3.entry
            com.android.systemui.statusbar.policy.RemoteInputView r5 = r3.view
            android.text.Editable r5 = r5.getText()
            r4.remoteInputText = r5
            com.android.systemui.statusbar.policy.RemoteInputView r4 = r3.view
            java.util.Objects.requireNonNull(r4)
            r4.setAttachment(r7)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.entry
            r3.remoteInputUri = r7
            r3.remoteInputMimeType = r7
            com.android.systemui.statusbar.notification.collection.NotificationEntry$EditedSuggestionInfo r3 = r3.editedSuggestionInfo
            if (r3 != 0) goto L_0x0075
            android.app.RemoteInput.setResultsSource(r0, r8)
            goto L_0x014a
        L_0x0075:
            android.app.RemoteInput.setResultsSource(r0, r9)
            goto L_0x014a
        L_0x007a:
            java.lang.String r5 = r4.remoteInputMimeType
            android.net.Uri r4 = r4.remoteInputUri
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            r10.put(r5, r4)
            com.android.systemui.statusbar.RemoteInputController r5 = r3.remoteInputController
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r3.entry
            java.util.Objects.requireNonNull(r11)
            android.service.notification.StatusBarNotification r11 = r11.mSbn
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.policy.RemoteInputUriController r5 = r5.mRemoteInputUriController
            java.util.Objects.requireNonNull(r5)
            com.android.internal.statusbar.IStatusBarService r5 = r5.mStatusBarManagerService     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r12 = r11.getKey()     // Catch:{ Exception -> 0x00a9 }
            android.os.UserHandle r13 = r11.getUser()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r11 = r11.getPackageName()     // Catch:{ Exception -> 0x00a9 }
            r5.grantInlineReplyUriPermission(r12, r4, r13, r11)     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00c0
        L_0x00a9:
            r4 = move-exception
            java.lang.String r5 = "Failed to grant URI permissions:"
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            java.lang.String r11 = r4.getMessage()
            r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.String r11 = "RemoteInputUriController"
            android.util.Log.e(r11, r5, r4)
        L_0x00c0:
            android.content.Intent r4 = new android.content.Intent
            r4.<init>()
            android.content.Intent r4 = r4.addFlags(r6)
            android.app.RemoteInput.addDataResultToIntent(r0, r4, r10)
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            java.lang.String r0 = r0.getResultKey()
            com.android.systemui.statusbar.policy.RemoteInputView r6 = r3.view
            android.text.Editable r6 = r6.getText()
            java.lang.String r6 = r6.toString()
            r5.putString(r0, r6)
            android.app.RemoteInput[] r0 = r3.remoteInputs
            android.app.RemoteInput.addResultsToIntent(r0, r4, r5)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r3.entry
            android.view.ContentInfo r0 = r0.remoteInputAttachment
            android.content.ClipData r0 = r0.getClip()
            android.content.ClipDescription r0 = r0.getDescription()
            java.lang.CharSequence r0 = r0.getLabel()
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 == 0) goto L_0x010a
            com.android.systemui.statusbar.policy.RemoteInputView r0 = r3.view
            android.content.res.Resources r0 = r0.getResources()
            r5 = 2131953152(0x7f130600, float:1.9542767E38)
            java.lang.String r0 = r0.getString(r5)
        L_0x010a:
            com.android.systemui.statusbar.policy.RemoteInputView r5 = r3.view
            android.text.Editable r5 = r5.getText()
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x0117
            goto L_0x0136
        L_0x0117:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r6 = 34
            r5.append(r6)
            r5.append(r0)
            java.lang.String r0 = "\" "
            r5.append(r0)
            com.android.systemui.statusbar.policy.RemoteInputView r0 = r3.view
            android.text.Editable r0 = r0.getText()
            r5.append(r0)
            java.lang.String r0 = r5.toString()
        L_0x0136:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.entry
            r3.remoteInputText = r0
            com.android.systemui.statusbar.notification.collection.NotificationEntry$EditedSuggestionInfo r0 = r3.editedSuggestionInfo
            if (r0 != 0) goto L_0x0142
            android.app.RemoteInput.setResultsSource(r4, r8)
            goto L_0x0149
        L_0x0142:
            android.view.ContentInfo r0 = r3.remoteInputAttachment
            if (r0 != 0) goto L_0x0149
            android.app.RemoteInput.setResultsSource(r4, r9)
        L_0x0149:
            r0 = r4
        L_0x014a:
            com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl r14 = r14.this$0
            java.util.Objects.requireNonNull(r14)
            com.android.systemui.statusbar.NotificationRemoteInputManager$BouncerChecker r3 = r14.bouncerChecker
            if (r3 != 0) goto L_0x0154
            goto L_0x0176
        L_0x0154:
            com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0 r3 = (com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0) r3
            com.android.systemui.statusbar.NotificationRemoteInputManager r4 = r3.f$0
            com.android.systemui.statusbar.NotificationRemoteInputManager$AuthBypassPredicate r5 = r3.f$1
            android.view.View r6 = r3.f$2
            android.app.PendingIntent r10 = r3.f$3
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r3.f$4
            java.util.Objects.requireNonNull(r4)
            boolean r5 = r5.canSendRemoteInputWithoutBouncer()
            if (r5 != 0) goto L_0x0171
            boolean r3 = r4.showBouncerForRemoteInput(r6, r10, r3)
            if (r3 == 0) goto L_0x0171
            r3 = r9
            goto L_0x0172
        L_0x0171:
            r3 = r8
        L_0x0172:
            if (r3 != r9) goto L_0x0176
            r3 = r9
            goto L_0x0177
        L_0x0176:
            r3 = r8
        L_0x0177:
            if (r3 == 0) goto L_0x01ac
            com.android.systemui.statusbar.policy.RemoteInputView r0 = r14.view
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText r0 = r0.mEditText
            int r1 = com.android.systemui.statusbar.policy.RemoteInputView.RemoteEditText.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            android.view.WindowInsetsController r0 = r0.getWindowInsetsController()
            if (r0 == 0) goto L_0x0192
            int r1 = android.view.WindowInsets.Type.ime()
            r0.hide(r1)
        L_0x0192:
            android.util.ArraySet<com.android.systemui.statusbar.policy.OnSendRemoteInputListener> r14 = r14.onSendListeners
            java.util.List r14 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r14)
            java.util.Iterator r14 = r14.iterator()
        L_0x019c:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x02b8
            java.lang.Object r0 = r14.next()
            com.android.systemui.statusbar.policy.OnSendRemoteInputListener r0 = (com.android.systemui.statusbar.policy.OnSendRemoteInputListener) r0
            r0.onSendRequestBounced()
            goto L_0x019c
        L_0x01ac:
            com.android.systemui.statusbar.policy.RemoteInputView r3 = r14.view
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText r4 = r3.mEditText
            r4.setEnabled(r8)
            android.widget.ImageButton r4 = r3.mSendButton
            r5 = 4
            r4.setVisibility(r5)
            android.widget.ProgressBar r4 = r3.mProgressBar
            r4.setVisibility(r8)
            com.android.systemui.statusbar.policy.RemoteInputView$RemoteEditText r3 = r3.mEditText
            r3.mShowImeOnInputConnection = r8
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r14.entry
            long r4 = android.os.SystemClock.elapsedRealtime()
            r3.lastRemoteInputSent = r4
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r14.entry
            r3.mRemoteEditImeAnimatingAway = r9
            com.android.systemui.statusbar.RemoteInputController r4 = r14.remoteInputController
            java.lang.String r3 = r3.mKey
            com.android.systemui.statusbar.policy.RemoteInputView r5 = r14.view
            java.lang.Object r5 = r5.mToken
            java.util.Objects.requireNonNull(r4)
            java.util.Objects.requireNonNull(r3)
            java.util.Objects.requireNonNull(r5)
            android.util.ArrayMap<java.lang.String, java.lang.Object> r4 = r4.mSpinning
            r4.put(r3, r5)
            com.android.systemui.statusbar.RemoteInputController r3 = r14.remoteInputController
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r14.entry
            com.android.systemui.statusbar.policy.RemoteInputView r5 = r14.view
            java.lang.Object r5 = r5.mToken
            r3.removeRemoteInput(r4, r5)
            com.android.systemui.statusbar.RemoteInputController r3 = r14.remoteInputController
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r14.entry
            java.util.Objects.requireNonNull(r3)
            java.util.ArrayList<com.android.systemui.statusbar.RemoteInputController$Callback> r5 = r3.mCallbacks
            int r5 = r5.size()
            r6 = r8
        L_0x0200:
            if (r6 >= r5) goto L_0x0210
            java.util.ArrayList<com.android.systemui.statusbar.RemoteInputController$Callback> r10 = r3.mCallbacks
            java.lang.Object r10 = r10.get(r6)
            com.android.systemui.statusbar.RemoteInputController$Callback r10 = (com.android.systemui.statusbar.RemoteInputController.Callback) r10
            r10.onRemoteInputSent(r4)
            int r6 = r6 + 1
            goto L_0x0200
        L_0x0210:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r14.entry
            java.util.Objects.requireNonNull(r3)
            r3.hasSentReply = r9
            android.util.ArraySet<com.android.systemui.statusbar.policy.OnSendRemoteInputListener> r3 = r14.onSendListeners
            java.util.List r3 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r3)
            java.util.Iterator r3 = r3.iterator()
        L_0x0221:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0231
            java.lang.Object r4 = r3.next()
            com.android.systemui.statusbar.policy.OnSendRemoteInputListener r4 = (com.android.systemui.statusbar.policy.OnSendRemoteInputListener) r4
            r4.onSendRemoteInput()
            goto L_0x0221
        L_0x0231:
            android.content.pm.ShortcutManager r3 = r14.shortcutManager
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r14.entry
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            java.lang.String r4 = r4.getPackageName()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r14.entry
            java.util.Objects.requireNonNull(r5)
            android.service.notification.StatusBarNotification r5 = r5.mSbn
            android.os.UserHandle r5 = r5.getUser()
            int r5 = r5.getIdentifier()
            r3.onApplicationActive(r4, r5)
            com.android.internal.logging.UiEventLogger r3 = r14.uiEventLogger
            com.android.systemui.statusbar.policy.RemoteInputView$NotificationRemoteInputEvent r4 = com.android.systemui.statusbar.policy.RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_SEND
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r14.entry
            java.util.Objects.requireNonNull(r5)
            android.service.notification.StatusBarNotification r5 = r5.mSbn
            int r5 = r5.getUid()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r14.entry
            java.util.Objects.requireNonNull(r6)
            android.service.notification.StatusBarNotification r6 = r6.mSbn
            java.lang.String r6 = r6.getPackageName()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r9 = r14.entry
            java.util.Objects.requireNonNull(r9)
            android.service.notification.StatusBarNotification r9 = r9.mSbn
            com.android.internal.logging.InstanceId r9 = r9.getInstanceId()
            r3.logWithInstanceId(r4, r5, r6, r9)
            com.android.systemui.statusbar.policy.RemoteInputView r3 = r14.view     // Catch:{ CanceledException -> 0x0282 }
            android.content.Context r3 = r3.getContext()     // Catch:{ CanceledException -> 0x0282 }
            r2.send(r3, r8, r0)     // Catch:{ CanceledException -> 0x0282 }
            goto L_0x02b0
        L_0x0282:
            r0 = move-exception
            java.lang.String r2 = "Unable to send remote input result"
            android.util.Log.i(r1, r2, r0)
            com.android.internal.logging.UiEventLogger r0 = r14.uiEventLogger
            com.android.systemui.statusbar.policy.RemoteInputView$NotificationRemoteInputEvent r1 = com.android.systemui.statusbar.policy.RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_FAILURE
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r14.entry
            java.util.Objects.requireNonNull(r2)
            android.service.notification.StatusBarNotification r2 = r2.mSbn
            int r2 = r2.getUid()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r14.entry
            java.util.Objects.requireNonNull(r3)
            android.service.notification.StatusBarNotification r3 = r3.mSbn
            java.lang.String r3 = r3.getPackageName()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r14.entry
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            com.android.internal.logging.InstanceId r4 = r4.getInstanceId()
            r0.logWithInstanceId(r1, r2, r3, r4)
        L_0x02b0:
            com.android.systemui.statusbar.policy.RemoteInputView r14 = r14.view
            java.util.Objects.requireNonNull(r14)
            r14.setAttachment(r7)
        L_0x02b8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onSendRemoteInputListener$1.run():void");
    }
}
