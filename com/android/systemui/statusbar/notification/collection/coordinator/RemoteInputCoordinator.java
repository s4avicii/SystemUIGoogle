package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: RemoteInputCoordinator.kt */
public final class RemoteInputCoordinator implements Coordinator, NotificationRemoteInputManager.RemoteInputListener, Dumpable {
    public final RemoteInputCoordinator$mCollectionListener$1 mCollectionListener = new RemoteInputCoordinator$mCollectionListener$1(this);
    public final Handler mMainHandler;
    public NotifCollection$$ExternalSyntheticLambda0 mNotifUpdater;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final RemoteInputNotificationRebuilder mRebuilder;
    public final RemoteInputActiveExtender mRemoteInputActiveExtender;
    public final RemoteInputHistoryExtender mRemoteInputHistoryExtender;
    public final List<SelfTrackingLifetimeExtender> mRemoteInputLifetimeExtenders;
    public final SmartReplyController mSmartReplyController;
    public final SmartReplyHistoryExtender mSmartReplyHistoryExtender;

    /* compiled from: RemoteInputCoordinator.kt */
    public final class RemoteInputActiveExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputActiveExtender() {
            super("RemoteInputActive", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.isRemoteInputActive(notificationEntry);
        }
    }

    /* compiled from: RemoteInputCoordinator.kt */
    public final class RemoteInputHistoryExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputHistoryExtender() {
            super("RemoteInputHistory", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        public final void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = RemoteInputCoordinator.this.mRebuilder;
            Objects.requireNonNull(remoteInputNotificationRebuilder);
            CharSequence charSequence = notificationEntry.remoteInputText;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = notificationEntry.remoteInputTextWhenReset;
            }
            StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, false, notificationEntry.remoteInputMimeType, notificationEntry.remoteInputUri);
            notificationEntry.lastRemoteInputSent = -2000;
            NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda0 = null;
            notificationEntry.remoteInputTextWhenReset = null;
            NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda02 = RemoteInputCoordinator.this.mNotifUpdater;
            if (notifCollection$$ExternalSyntheticLambda02 != null) {
                notifCollection$$ExternalSyntheticLambda0 = notifCollection$$ExternalSyntheticLambda02;
            }
            notifCollection$$ExternalSyntheticLambda0.onInternalNotificationUpdate(rebuildWithRemoteInputInserted, "Extending lifetime of notification with remote input");
        }

        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForRemoteInputHistory(notificationEntry);
        }
    }

    /* compiled from: RemoteInputCoordinator.kt */
    public final class SmartReplyHistoryExtender extends SelfTrackingLifetimeExtender {
        public SmartReplyHistoryExtender() {
            super("SmartReplyHistory", RemoteInputCoordinatorKt.access$getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        public final void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
        }

        public final void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = RemoteInputCoordinator.this.mRebuilder;
            Objects.requireNonNull(remoteInputNotificationRebuilder);
            StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, (CharSequence) null, false, (String) null, (Uri) null);
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
            NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda0 = RemoteInputCoordinator.this.mNotifUpdater;
            if (notifCollection$$ExternalSyntheticLambda0 == null) {
                notifCollection$$ExternalSyntheticLambda0 = null;
            }
            notifCollection$$ExternalSyntheticLambda0.onInternalNotificationUpdate(rebuildWithRemoteInputInserted, "Extending lifetime of notification with smart reply");
        }

        public final boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForSmartReplyHistory(notificationEntry);
        }
    }

    public static /* synthetic */ void getMRemoteInputActiveExtender$annotations() {
    }

    public static /* synthetic */ void getMRemoteInputHistoryExtender$annotations() {
    }

    public static /* synthetic */ void getMSmartReplyHistoryExtender$annotations() {
    }

    public final void attach(NotifPipeline notifPipeline) {
        NotificationRemoteInputManager notificationRemoteInputManager = this.mNotificationRemoteInputManager;
        Objects.requireNonNull(notificationRemoteInputManager);
        if (notificationRemoteInputManager.mNotifPipelineFlags.isNewPipelineEnabled()) {
            if (notificationRemoteInputManager.mRemoteInputListener == null) {
                notificationRemoteInputManager.mRemoteInputListener = this;
                RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
                if (remoteInputController != null) {
                    setRemoteInputController(remoteInputController);
                }
            } else {
                throw new IllegalStateException("mRemoteInputListener is already set");
            }
        }
        for (SelfTrackingLifetimeExtender addNotificationLifetimeExtender : this.mRemoteInputLifetimeExtenders) {
            notifPipeline.addNotificationLifetimeExtender(addNotificationLifetimeExtender);
        }
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        Objects.requireNonNull(notifCollection);
        this.mNotifUpdater = new NotifCollection$$ExternalSyntheticLambda0(notifCollection);
        notifPipeline.addCollectionListener(this.mCollectionListener);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (SelfTrackingLifetimeExtender dump : this.mRemoteInputLifetimeExtenders) {
            dump.dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean isNotificationKeptForRemoteInputHistory(String str) {
        if (this.mRemoteInputHistoryExtender.isExtending(str) || this.mSmartReplyHistoryExtender.isExtending(str)) {
            return true;
        }
        return false;
    }

    public final void onPanelCollapsed() {
        RemoteInputActiveExtender remoteInputActiveExtender = this.mRemoteInputActiveExtender;
        Objects.requireNonNull(remoteInputActiveExtender);
        List<T> list = CollectionsKt___CollectionsKt.toList(remoteInputActiveExtender.mEntriesExtended.values());
        if (remoteInputActiveExtender.debug) {
            String str = remoteInputActiveExtender.tag;
            Log.d(str, remoteInputActiveExtender.name + ".endAllLifetimeExtensions() entries=" + list);
        }
        remoteInputActiveExtender.mEntriesExtended.clear();
        remoteInputActiveExtender.warnIfEnding();
        remoteInputActiveExtender.mEnding = true;
        for (T t : list) {
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = remoteInputActiveExtender.mCallback;
            if (onEndLifetimeExtensionCallback == null) {
                onEndLifetimeExtensionCallback = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda2) onEndLifetimeExtensionCallback).onEndLifetimeExtension(remoteInputActiveExtender, t);
        }
        remoteInputActiveExtender.mEnding = false;
    }

    public final void setRemoteInputController(RemoteInputController remoteInputController) {
        SmartReplyController smartReplyController = this.mSmartReplyController;
        RemoteInputCoordinator$setRemoteInputController$1 remoteInputCoordinator$setRemoteInputController$1 = new RemoteInputCoordinator$setRemoteInputController$1(this);
        Objects.requireNonNull(smartReplyController);
        smartReplyController.mCallback = remoteInputCoordinator$setRemoteInputController$1;
    }

    public RemoteInputCoordinator(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        this.mRebuilder = remoteInputNotificationRebuilder;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mMainHandler = handler;
        this.mSmartReplyController = smartReplyController;
        RemoteInputHistoryExtender remoteInputHistoryExtender = new RemoteInputHistoryExtender();
        this.mRemoteInputHistoryExtender = remoteInputHistoryExtender;
        SmartReplyHistoryExtender smartReplyHistoryExtender = new SmartReplyHistoryExtender();
        this.mSmartReplyHistoryExtender = smartReplyHistoryExtender;
        RemoteInputActiveExtender remoteInputActiveExtender = new RemoteInputActiveExtender();
        this.mRemoteInputActiveExtender = remoteInputActiveExtender;
        this.mRemoteInputLifetimeExtenders = SetsKt__SetsKt.listOf(remoteInputHistoryExtender, smartReplyHistoryExtender, remoteInputActiveExtender);
        dumpManager.registerDumpable(this);
    }

    public final void onRemoteInputSent(NotificationEntry notificationEntry) {
        if (RemoteInputCoordinatorKt.access$getDEBUG()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onRemoteInputSent(entry=");
            m.append(notificationEntry.mKey);
            m.append(')');
            Log.d("RemoteInputCoordinator", m.toString());
        }
        this.mRemoteInputHistoryExtender.endLifetimeExtension(notificationEntry.mKey);
        this.mSmartReplyHistoryExtender.endLifetimeExtension(notificationEntry.mKey);
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 500);
    }

    public final void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry) {
        if (RemoteInputCoordinatorKt.access$getDEBUG()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("releaseNotificationIfKeptForRemoteInputHistory(entry=");
            m.append(notificationEntry.mKey);
            m.append(')');
            Log.d("RemoteInputCoordinator", m.toString());
        }
        this.mRemoteInputHistoryExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 200);
        this.mSmartReplyHistoryExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 200);
        this.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 200);
    }
}
