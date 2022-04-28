package com.android.systemui.statusbar;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.view.View;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationRemoteInputManager$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ NotificationRemoteInputManager f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ RemoteInput[] f$2;
    public final /* synthetic */ RemoteInput f$3;
    public final /* synthetic */ PendingIntent f$4;
    public final /* synthetic */ NotificationEntry.EditedSuggestionInfo f$5;
    public final /* synthetic */ String f$6;
    public final /* synthetic */ NotificationRemoteInputManager.AuthBypassPredicate f$7;

    public /* synthetic */ NotificationRemoteInputManager$$ExternalSyntheticLambda2(NotificationRemoteInputManager notificationRemoteInputManager, View view, RemoteInput[] remoteInputArr, RemoteInput remoteInput, PendingIntent pendingIntent, NotificationEntry.EditedSuggestionInfo editedSuggestionInfo, String str, NotificationRemoteInputManager.AuthBypassPredicate authBypassPredicate) {
        this.f$0 = notificationRemoteInputManager;
        this.f$1 = view;
        this.f$2 = remoteInputArr;
        this.f$3 = remoteInput;
        this.f$4 = pendingIntent;
        this.f$5 = editedSuggestionInfo;
        this.f$6 = str;
        this.f$7 = authBypassPredicate;
    }

    public final void run() {
        NotificationRemoteInputManager notificationRemoteInputManager = this.f$0;
        View view = this.f$1;
        RemoteInput[] remoteInputArr = this.f$2;
        RemoteInput remoteInput = this.f$3;
        PendingIntent pendingIntent = this.f$4;
        NotificationEntry.EditedSuggestionInfo editedSuggestionInfo = this.f$5;
        String str = this.f$6;
        NotificationRemoteInputManager.AuthBypassPredicate authBypassPredicate = this.f$7;
        Objects.requireNonNull(notificationRemoteInputManager);
        notificationRemoteInputManager.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo, str, authBypassPredicate);
    }
}
