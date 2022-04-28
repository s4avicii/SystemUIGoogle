package com.android.systemui.statusbar.notification.stack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.NotificationRemoveInterceptor;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.icon.IconPack;
import com.android.systemui.statusbar.notification.row.DungeonRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.Assert;
import java.util.LinkedHashSet;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: ForegroundServiceSectionController.kt */
public final class ForegroundServiceSectionController {
    public final LinkedHashSet entries = new LinkedHashSet();
    public View entriesView;
    public final NotificationEntryManager entryManager;

    public ForegroundServiceSectionController(NotificationEntryManager notificationEntryManager, ForegroundServiceDismissalFeatureController foregroundServiceDismissalFeatureController) {
        this.entryManager = notificationEntryManager;
        if (foregroundServiceDismissalFeatureController.isForegroundServiceDismissalEnabled()) {
            C13471 r3 = new NotificationRemoveInterceptor() {
                public final boolean onNotificationRemoveRequested(NotificationEntry notificationEntry, int i) {
                    boolean z;
                    boolean z2;
                    boolean z3;
                    ForegroundServiceSectionController foregroundServiceSectionController = ForegroundServiceSectionController.this;
                    Objects.requireNonNull(foregroundServiceSectionController);
                    Assert.isMainThread();
                    if (i == 3) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (i == 2 || i == 1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (i == 12) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (notificationEntry != null) {
                        if (z2 && !notificationEntry.mSbn.isClearable()) {
                            Assert.isMainThread();
                            if (!foregroundServiceSectionController.entries.contains(notificationEntry)) {
                                Assert.isMainThread();
                                foregroundServiceSectionController.entries.add(notificationEntry);
                                foregroundServiceSectionController.update();
                            }
                            foregroundServiceSectionController.entryManager.updateNotifications("FgsSectionController.onNotificationRemoveRequested");
                            return true;
                        } else if ((z || z3) && !notificationEntry.mSbn.isClearable()) {
                            return true;
                        } else {
                            Assert.isMainThread();
                            if (foregroundServiceSectionController.entries.contains(notificationEntry)) {
                                Assert.isMainThread();
                                foregroundServiceSectionController.entries.remove(notificationEntry);
                                foregroundServiceSectionController.update();
                            }
                        }
                    }
                    return false;
                }
            };
            Objects.requireNonNull(notificationEntryManager);
            notificationEntryManager.mRemoveInterceptors.add(r3);
            notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener(this) {
                public final /* synthetic */ ForegroundServiceSectionController this$0;

                {
                    this.this$0 = r1;
                }

                public final void onPostEntryUpdated(NotificationEntry notificationEntry) {
                    if (this.this$0.entries.contains(notificationEntry)) {
                        ForegroundServiceSectionController foregroundServiceSectionController = this.this$0;
                        Objects.requireNonNull(foregroundServiceSectionController);
                        Assert.isMainThread();
                        foregroundServiceSectionController.entries.remove(notificationEntry);
                        ForegroundServiceSectionController foregroundServiceSectionController2 = this.this$0;
                        Objects.requireNonNull(foregroundServiceSectionController2);
                        Assert.isMainThread();
                        foregroundServiceSectionController2.entries.add(notificationEntry);
                        this.this$0.update();
                    }
                }
            });
        }
    }

    public final void update() {
        String str;
        IconPack iconPack;
        StatusBarIconView statusBarIconView;
        ExpandableNotificationRow expandableNotificationRow;
        Assert.isMainThread();
        View view = this.entriesView;
        if (view != null) {
            View findViewById = view.findViewById(C1777R.C1779id.entry_list);
            Objects.requireNonNull(findViewById, "null cannot be cast to non-null type android.widget.LinearLayout");
            LinearLayout linearLayout = (LinearLayout) findViewById;
            linearLayout.removeAllViews();
            for (NotificationEntry notificationEntry : CollectionsKt___CollectionsKt.sortedWith(this.entries, new C1349x9638ed8c())) {
                StatusBarIcon statusBarIcon = null;
                View inflate = LayoutInflater.from(linearLayout.getContext()).inflate(C1777R.layout.foreground_service_dungeon_row, (ViewGroup) null);
                Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.systemui.statusbar.notification.row.DungeonRow");
                DungeonRow dungeonRow = (DungeonRow) inflate;
                dungeonRow.entry = notificationEntry;
                View findViewById2 = dungeonRow.findViewById(C1777R.C1779id.app_name);
                Objects.requireNonNull(findViewById2, "null cannot be cast to non-null type android.widget.TextView");
                TextView textView = (TextView) findViewById2;
                NotificationEntry notificationEntry2 = dungeonRow.entry;
                if (notificationEntry2 == null || (expandableNotificationRow = notificationEntry2.row) == null) {
                    str = null;
                } else {
                    str = expandableNotificationRow.mAppName;
                }
                textView.setText(str);
                View findViewById3 = dungeonRow.findViewById(C1777R.C1779id.icon);
                Objects.requireNonNull(findViewById3, "null cannot be cast to non-null type com.android.systemui.statusbar.StatusBarIconView");
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) findViewById3;
                NotificationEntry notificationEntry3 = dungeonRow.entry;
                if (!(notificationEntry3 == null || (iconPack = notificationEntry3.mIcons) == null || (statusBarIconView = iconPack.mStatusBarIcon) == null)) {
                    statusBarIcon = statusBarIconView.mIcon;
                }
                statusBarIconView2.set(statusBarIcon);
                dungeonRow.setOnClickListener(new ForegroundServiceSectionController$update$1$2$1(this, dungeonRow, notificationEntry));
                linearLayout.addView(dungeonRow);
            }
            if (this.entries.isEmpty()) {
                View view2 = this.entriesView;
                if (view2 != null) {
                    view2.setVisibility(8);
                    return;
                }
                return;
            }
            View view3 = this.entriesView;
            if (view3 != null) {
                view3.setVisibility(0);
                return;
            }
            return;
        }
        throw new IllegalStateException("ForegroundServiceSectionController is trying to show dismissed fgs notifications without having been initialized!");
    }
}
