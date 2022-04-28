package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;

/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager$expandableViewHeaderState$1 implements NotificationSectionsManager.SectionUpdateState<Object> {
    public final /* synthetic */ Object $header;
    public Integer currentPosition;
    public Integer targetPosition;
    public final /* synthetic */ NotificationSectionsManager this$0;

    public NotificationSectionsManager$expandableViewHeaderState$1(Object obj, NotificationSectionsManager notificationSectionsManager) {
        this.$header = obj;
        this.this$0 = notificationSectionsManager;
    }

    /* JADX WARNING: type inference failed for: r3v1, types: [java.lang.Object, com.android.systemui.statusbar.notification.row.ExpandableView] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object, com.android.systemui.statusbar.notification.row.ExpandableView] */
    /* JADX WARNING: type inference failed for: r3v2, types: [android.view.View, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v3, types: [android.view.View, java.lang.Object] */
    public final void adjustViewPosition() {
        this.this$0.notifPipelineFlags.checkLegacyPipelineEnabled();
        Integer num = this.targetPosition;
        Integer num2 = this.currentPosition;
        NotificationStackScrollLayout notificationStackScrollLayout = null;
        if (num == null) {
            if (num2 != null) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.this$0.parent;
                if (notificationStackScrollLayout2 != null) {
                    notificationStackScrollLayout = notificationStackScrollLayout2;
                }
                notificationStackScrollLayout.removeView(this.$header);
            }
        } else if (num2 == null) {
            this.$header.removeFromTransientContainer();
            NotificationStackScrollLayout notificationStackScrollLayout3 = this.this$0.parent;
            if (notificationStackScrollLayout3 != null) {
                notificationStackScrollLayout = notificationStackScrollLayout3;
            }
            notificationStackScrollLayout.addView(this.$header, num.intValue());
        } else {
            NotificationStackScrollLayout notificationStackScrollLayout4 = this.this$0.parent;
            if (notificationStackScrollLayout4 != null) {
                notificationStackScrollLayout = notificationStackScrollLayout4;
            }
            notificationStackScrollLayout.changeViewPosition(this.$header, num.intValue());
        }
    }

    public final void setCurrentPosition(Integer num) {
        this.currentPosition = num;
    }

    public final void setTargetPosition(Integer num) {
        this.targetPosition = num;
    }

    public final Integer getCurrentPosition() {
        return this.currentPosition;
    }

    public final Integer getTargetPosition() {
        return this.targetPosition;
    }
}
