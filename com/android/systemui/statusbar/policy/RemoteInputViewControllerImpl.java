package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.util.ArraySet;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.C2394xcec46519;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: RemoteInputViewController.kt */
public final class RemoteInputViewControllerImpl implements RemoteInputViewController {
    public NotificationRemoteInputManager.BouncerChecker bouncerChecker;
    public final NotificationEntry entry;
    public boolean isBound;
    public final RemoteInputViewControllerImpl$onFocusChangeListener$1 onFocusChangeListener = new RemoteInputViewControllerImpl$onFocusChangeListener$1(this);
    public final ArraySet<OnSendRemoteInputListener> onSendListeners = new ArraySet<>();
    public final RemoteInputViewControllerImpl$onSendRemoteInputListener$1 onSendRemoteInputListener = new RemoteInputViewControllerImpl$onSendRemoteInputListener$1(this);
    public PendingIntent pendingIntent;
    public RemoteInput remoteInput;
    public final RemoteInputController remoteInputController;
    public final RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler;
    public RemoteInput[] remoteInputs;
    public final ShortcutManager shortcutManager;
    public final UiEventLogger uiEventLogger;
    public final RemoteInputView view;

    public final boolean updatePendingIntentFromActions(Notification.Action[] actionArr) {
        Intent intent;
        boolean z;
        RemoteInput[] remoteInputs2;
        RemoteInput remoteInput2;
        if (actionArr == null) {
            return false;
        }
        PendingIntent pendingIntent2 = this.pendingIntent;
        if (pendingIntent2 == null) {
            intent = null;
        } else {
            intent = pendingIntent2.getIntent();
        }
        if (intent == null) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i < actionArr.length) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
            int i2 = i + 1;
            try {
                Notification.Action action = actionArr[i];
                PendingIntent pendingIntent3 = action.actionIntent;
                if (!(pendingIntent3 == null || (remoteInputs2 = action.getRemoteInputs()) == null || !intent.filterEquals(pendingIntent3.getIntent()))) {
                    int length = remoteInputs2.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            remoteInput2 = null;
                            break;
                        }
                        remoteInput2 = remoteInputs2[i3];
                        i3++;
                        if (remoteInput2.getAllowFreeFormInput()) {
                            break;
                        }
                    }
                    if (remoteInput2 != null) {
                        this.pendingIntent = pendingIntent3;
                        this.remoteInput = remoteInput2;
                        this.remoteInputs = remoteInputs2;
                        RemoteInputView remoteInputView = this.view;
                        Objects.requireNonNull(remoteInputView);
                        remoteInputView.mPendingIntent = pendingIntent3;
                        this.view.setRemoteInput(remoteInputs2, remoteInput2, (NotificationEntry.EditedSuggestionInfo) null);
                        return true;
                    }
                }
                i = i2;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }
    }

    public final void addOnSendRemoteInputListener(C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1) {
        this.onSendListeners.add(notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);
    }

    public final void bind() {
        if (!this.isBound) {
            this.isBound = true;
            RemoteInputView remoteInputView = this.view;
            RemoteInputViewControllerImpl$onFocusChangeListener$1 remoteInputViewControllerImpl$onFocusChangeListener$1 = this.onFocusChangeListener;
            Objects.requireNonNull(remoteInputView);
            remoteInputView.mEditTextFocusChangeListeners.add(remoteInputViewControllerImpl$onFocusChangeListener$1);
            RemoteInputView remoteInputView2 = this.view;
            RemoteInputViewControllerImpl$onSendRemoteInputListener$1 remoteInputViewControllerImpl$onSendRemoteInputListener$1 = this.onSendRemoteInputListener;
            Objects.requireNonNull(remoteInputView2);
            remoteInputView2.mOnSendListeners.add(remoteInputViewControllerImpl$onSendRemoteInputListener$1);
        }
    }

    public final void removeOnSendRemoteInputListener(C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1) {
        this.onSendListeners.remove(notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);
    }

    public final void unbind() {
        if (this.isBound) {
            this.isBound = false;
            RemoteInputView remoteInputView = this.view;
            RemoteInputViewControllerImpl$onFocusChangeListener$1 remoteInputViewControllerImpl$onFocusChangeListener$1 = this.onFocusChangeListener;
            Objects.requireNonNull(remoteInputView);
            remoteInputView.mEditTextFocusChangeListeners.remove(remoteInputViewControllerImpl$onFocusChangeListener$1);
            RemoteInputView remoteInputView2 = this.view;
            RemoteInputViewControllerImpl$onSendRemoteInputListener$1 remoteInputViewControllerImpl$onSendRemoteInputListener$1 = this.onSendRemoteInputListener;
            Objects.requireNonNull(remoteInputView2);
            remoteInputView2.mOnSendListeners.remove(remoteInputViewControllerImpl$onSendRemoteInputListener$1);
        }
    }

    public RemoteInputViewControllerImpl(RemoteInputView remoteInputView, NotificationEntry notificationEntry, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler2, RemoteInputController remoteInputController2, ShortcutManager shortcutManager2, UiEventLogger uiEventLogger2) {
        this.view = remoteInputView;
        this.entry = notificationEntry;
        this.remoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler2;
        this.remoteInputController = remoteInputController2;
        this.shortcutManager = shortcutManager2;
        this.uiEventLogger = uiEventLogger2;
    }

    public final void setBouncerChecker(NotificationRemoteInputManager$$ExternalSyntheticLambda0 notificationRemoteInputManager$$ExternalSyntheticLambda0) {
        this.bouncerChecker = notificationRemoteInputManager$$ExternalSyntheticLambda0;
    }

    public final void setPendingIntent(PendingIntent pendingIntent2) {
        this.pendingIntent = pendingIntent2;
    }

    public final void setRemoteInput(RemoteInput remoteInput2) {
        this.remoteInput = remoteInput2;
    }

    public final void setRemoteInputs(RemoteInput[] remoteInputArr) {
        this.remoteInputs = remoteInputArr;
    }
}
