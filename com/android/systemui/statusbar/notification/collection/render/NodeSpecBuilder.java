package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.util.Utils;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NodeSpecBuilder.kt */
public final class NodeSpecBuilder {
    public final MediaContainerController mediaContainerController;
    public final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;
    public final NotifViewBarn viewBarn;

    public final NodeSpecImpl buildNodeSpec(RootNodeController rootNodeController, List list) {
        NodeController nodeController;
        Trace.beginSection("NodeSpecBuilder.buildNodeSpec");
        try {
            NodeSpecImpl nodeSpecImpl = new NodeSpecImpl((NodeSpecImpl) null, rootNodeController);
            NotificationSectionsFeatureManager notificationSectionsFeatureManager = this.sectionsFeatureManager;
            Objects.requireNonNull(notificationSectionsFeatureManager);
            if (Utils.useQsMediaPlayer(notificationSectionsFeatureManager.context)) {
                nodeSpecImpl.children.add(new NodeSpecImpl(nodeSpecImpl, this.mediaContainerController));
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider2 = this.sectionHeaderVisibilityProvider;
            Objects.requireNonNull(sectionHeaderVisibilityProvider2);
            boolean z = sectionHeaderVisibilityProvider2.sectionHeadersVisible;
            Iterator it = list.iterator();
            NotifSection notifSection = null;
            while (it.hasNext()) {
                ListEntry listEntry = (ListEntry) it.next();
                NotifSection section = listEntry.getSection();
                Intrinsics.checkNotNull(section);
                if (!linkedHashSet.contains(section)) {
                    if (!Intrinsics.areEqual(section, notifSection)) {
                        NodeController nodeController2 = section.headerController;
                        if (notifSection == null) {
                            nodeController = null;
                        } else {
                            nodeController = notifSection.headerController;
                        }
                        if (!Intrinsics.areEqual(nodeController2, nodeController) && z) {
                            NodeController nodeController3 = section.headerController;
                            if (nodeController3 != null) {
                                nodeSpecImpl.children.add(new NodeSpecImpl(nodeSpecImpl, nodeController3));
                            }
                        }
                        linkedHashSet.add(notifSection);
                        notifSection = section;
                    }
                    nodeSpecImpl.children.add(buildNotifNode(nodeSpecImpl, listEntry));
                } else {
                    throw new RuntimeException("Section " + section.getLabel() + " has been duplicated");
                }
            }
            return nodeSpecImpl;
        } finally {
            Trace.endSection();
        }
    }

    public final NodeSpecImpl buildNotifNode(NodeSpecImpl nodeSpecImpl, ListEntry listEntry) {
        if (listEntry instanceof NotificationEntry) {
            return new NodeSpecImpl(nodeSpecImpl, this.viewBarn.requireNodeController(listEntry));
        }
        if (listEntry instanceof GroupEntry) {
            NotifViewBarn notifViewBarn = this.viewBarn;
            GroupEntry groupEntry = (GroupEntry) listEntry;
            NotificationEntry notificationEntry = groupEntry.mSummary;
            if (notificationEntry != null) {
                NodeSpecImpl nodeSpecImpl2 = new NodeSpecImpl(nodeSpecImpl, notifViewBarn.requireNodeController(notificationEntry));
                for (NotificationEntry buildNotifNode : groupEntry.mUnmodifiableChildren) {
                    nodeSpecImpl2.children.add(buildNotifNode(nodeSpecImpl2, buildNotifNode));
                }
                return nodeSpecImpl2;
            }
            throw new IllegalStateException("Required value was null.".toString());
        }
        throw new RuntimeException(Intrinsics.stringPlus("Unexpected entry: ", listEntry));
    }

    public NodeSpecBuilder(MediaContainerController mediaContainerController2, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider2, NotifViewBarn notifViewBarn) {
        this.mediaContainerController = mediaContainerController2;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider2;
        this.viewBarn = notifViewBarn;
    }
}
