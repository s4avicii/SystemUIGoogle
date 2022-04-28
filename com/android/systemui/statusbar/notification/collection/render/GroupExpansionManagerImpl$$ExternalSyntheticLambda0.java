package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GroupExpansionManagerImpl$$ExternalSyntheticLambda0 implements OnBeforeRenderListListener {
    public final /* synthetic */ GroupExpansionManagerImpl f$0;

    public /* synthetic */ GroupExpansionManagerImpl$$ExternalSyntheticLambda0(GroupExpansionManagerImpl groupExpansionManagerImpl) {
        this.f$0 = groupExpansionManagerImpl;
    }

    public final void onBeforeRenderList(List list) {
        GroupExpansionManagerImpl groupExpansionManagerImpl = this.f$0;
        Objects.requireNonNull(groupExpansionManagerImpl);
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                hashSet.add(listEntry.getRepresentativeEntry());
            }
        }
        groupExpansionManagerImpl.mExpandedGroups.removeIf(new GroupExpansionManagerImpl$$ExternalSyntheticLambda1(hashSet));
    }
}
