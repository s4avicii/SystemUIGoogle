package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RowAppearanceCoordinator.kt */
public /* synthetic */ class RowAppearanceCoordinator$attach$1 implements OnBeforeRenderListListener {
    public final /* synthetic */ RowAppearanceCoordinator $tmp0;

    public RowAppearanceCoordinator$attach$1(RowAppearanceCoordinator rowAppearanceCoordinator) {
        this.$tmp0 = rowAppearanceCoordinator;
    }

    public final void onBeforeRenderList(List<? extends ListEntry> list) {
        Object obj;
        NotificationEntry representativeEntry;
        RowAppearanceCoordinator rowAppearanceCoordinator = this.$tmp0;
        Objects.requireNonNull(rowAppearanceCoordinator);
        NotificationEntry notificationEntry = null;
        if (list.isEmpty()) {
            obj = null;
        } else {
            obj = list.get(0);
        }
        ListEntry listEntry = (ListEntry) obj;
        if (!(listEntry == null || (representativeEntry = listEntry.getRepresentativeEntry()) == null)) {
            SectionClassifier sectionClassifier = rowAppearanceCoordinator.mSectionClassifier;
            NotifSection section = representativeEntry.getSection();
            Intrinsics.checkNotNull(section);
            Objects.requireNonNull(sectionClassifier);
            Set<? extends NotifSectioner> set = sectionClassifier.lowPrioritySections;
            if (set == null) {
                set = null;
            }
            if (!set.contains(section.sectioner)) {
                notificationEntry = representativeEntry;
            }
        }
        rowAppearanceCoordinator.entryToExpand = notificationEntry;
    }
}
