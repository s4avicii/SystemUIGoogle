package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;

/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager$decorViewHeaderState$1 implements NotificationSectionsManager.SectionUpdateState<Object> {
    public final /* synthetic */ NotificationSectionsManager.SectionUpdateState<Object> $$delegate_0;
    public final /* synthetic */ Object $header;
    public final /* synthetic */ NotificationSectionsManager.SectionUpdateState<Object> $inner;

    public final Integer getCurrentPosition() {
        return this.$$delegate_0.getCurrentPosition();
    }

    public final Integer getTargetPosition() {
        return this.$$delegate_0.getTargetPosition();
    }

    public final void setCurrentPosition(Integer num) {
        this.$$delegate_0.setCurrentPosition(num);
    }

    public final void setTargetPosition(Integer num) {
        this.$$delegate_0.setTargetPosition(num);
    }

    public NotificationSectionsManager$decorViewHeaderState$1(NotificationSectionsManager$expandableViewHeaderState$1 notificationSectionsManager$expandableViewHeaderState$1, SectionHeaderView sectionHeaderView) {
        this.$inner = notificationSectionsManager$expandableViewHeaderState$1;
        this.$header = sectionHeaderView;
        this.$$delegate_0 = notificationSectionsManager$expandableViewHeaderState$1;
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.Object, com.android.systemui.statusbar.notification.row.StackScrollerDecorView] */
    public final void adjustViewPosition() {
        this.$inner.adjustViewPosition();
        if (getTargetPosition() != null && getCurrentPosition() == null) {
            this.$header.setContentVisible(true);
        }
    }
}
