package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArraySet;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GutsCoordinator.kt */
public final class GutsCoordinator implements Coordinator, Dumpable {
    public final GutsCoordinatorLogger logger;
    public final GutsCoordinator$mGutsListener$1 mGutsListener;
    public final GutsCoordinator$mLifetimeExtender$1 mLifetimeExtender;
    public final NotifGutsViewManager notifGutsViewManager;
    public final ArraySet<String> notifsExtendingLifetime = new ArraySet<>();
    public final ArraySet<String> notifsWithOpenGuts = new ArraySet<>();
    public NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback;

    public final void attach(NotifPipeline notifPipeline) {
        this.notifGutsViewManager.setGutsListener(this.mGutsListener);
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("  notifsWithOpenGuts: ", Integer.valueOf(this.notifsWithOpenGuts.size())));
        Iterator<String> it = this.notifsWithOpenGuts.iterator();
        while (it.hasNext()) {
            printWriter.println(Intrinsics.stringPlus("   * ", it.next()));
        }
        printWriter.println(Intrinsics.stringPlus("  notifsExtendingLifetime: ", Integer.valueOf(this.notifsExtendingLifetime.size())));
        Iterator<String> it2 = this.notifsExtendingLifetime.iterator();
        while (it2.hasNext()) {
            printWriter.println(Intrinsics.stringPlus("   * ", it2.next()));
        }
        printWriter.println(Intrinsics.stringPlus("  onEndLifetimeExtensionCallback: ", this.onEndLifetimeExtensionCallback));
    }

    public GutsCoordinator(NotifGutsViewManager notifGutsViewManager2, GutsCoordinatorLogger gutsCoordinatorLogger, DumpManager dumpManager) {
        this.notifGutsViewManager = notifGutsViewManager2;
        this.logger = gutsCoordinatorLogger;
        dumpManager.registerDumpable("GutsCoordinator", this);
        this.mLifetimeExtender = new GutsCoordinator$mLifetimeExtender$1(this);
        this.mGutsListener = new GutsCoordinator$mGutsListener$1(this);
    }

    public static final void access$closeGutsAndEndLifetimeExtension(GutsCoordinator gutsCoordinator, NotificationEntry notificationEntry) {
        NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback2;
        Objects.requireNonNull(gutsCoordinator);
        ArraySet<String> arraySet = gutsCoordinator.notifsWithOpenGuts;
        Objects.requireNonNull(notificationEntry);
        arraySet.remove(notificationEntry.mKey);
        if (gutsCoordinator.notifsExtendingLifetime.remove(notificationEntry.mKey) && (onEndLifetimeExtensionCallback2 = gutsCoordinator.onEndLifetimeExtensionCallback) != null) {
            ((NotifCollection$$ExternalSyntheticLambda2) onEndLifetimeExtensionCallback2).onEndLifetimeExtension(gutsCoordinator.mLifetimeExtender, notificationEntry);
        }
    }
}
