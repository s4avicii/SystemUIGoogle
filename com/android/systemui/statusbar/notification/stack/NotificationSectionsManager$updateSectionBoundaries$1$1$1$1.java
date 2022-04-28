package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationSectionsManager.kt */
final class NotificationSectionsManager$updateSectionBoundaries$1$1$1$1 extends Lambda implements Function1<NotificationSectionsManager.SectionUpdateState<? extends ExpandableView>, Boolean> {
    public final /* synthetic */ NotificationSectionsManager.SectionUpdateState<ExpandableView> $state;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationSectionsManager$updateSectionBoundaries$1$1$1$1(NotificationSectionsManager.SectionUpdateState<? extends ExpandableView> sectionUpdateState) {
        super(1);
        this.$state = sectionUpdateState;
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (((NotificationSectionsManager.SectionUpdateState) obj) == this.$state) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
