package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: GroupCountCoordinator.kt */
public /* synthetic */ class GroupCountCoordinator$attach$1 implements OnBeforeFinalizeFilterListener {
    public final /* synthetic */ GroupCountCoordinator $tmp0;

    public GroupCountCoordinator$attach$1(GroupCountCoordinator groupCountCoordinator) {
        this.$tmp0 = groupCountCoordinator;
    }

    public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
        GroupCountCoordinator groupCountCoordinator = this.$tmp0;
        Objects.requireNonNull(groupCountCoordinator);
        groupCountCoordinator.untruncatedChildCounts.clear();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), C1259xdd467635.INSTANCE));
        while (filteringSequence$iterator$1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) filteringSequence$iterator$1.next();
            ArrayMap<GroupEntry, Integer> arrayMap = groupCountCoordinator.untruncatedChildCounts;
            Objects.requireNonNull(groupEntry);
            arrayMap.put(groupEntry, Integer.valueOf(groupEntry.mUnmodifiableChildren.size()));
        }
    }
}
