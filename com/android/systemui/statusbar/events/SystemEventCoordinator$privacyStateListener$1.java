package com.android.systemui.statusbar.events;

import android.animation.AnimatorSet;
import android.os.Process;
import android.provider.DeviceConfig;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.privacy.PrivacyApplication;
import com.android.systemui.privacy.PrivacyChipBuilder;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemEventCoordinator.kt */
public final class SystemEventCoordinator$privacyStateListener$1 implements PrivacyItemController.Callback {
    public List<PrivacyItem> currentPrivacyItems;
    public List<PrivacyItem> previousPrivacyItems;
    public final /* synthetic */ SystemEventCoordinator this$0;
    public long timeLastEmpty;

    public SystemEventCoordinator$privacyStateListener$1(SystemEventCoordinator systemEventCoordinator) {
        this.this$0 = systemEventCoordinator;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.currentPrivacyItems = emptyList;
        this.previousPrivacyItems = emptyList;
        this.timeLastEmpty = systemEventCoordinator.systemClock.elapsedRealtime();
    }

    public static boolean uniqueItemsMatch(List list, List list2) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PrivacyItem privacyItem = (PrivacyItem) it.next();
            Objects.requireNonNull(privacyItem);
            PrivacyApplication privacyApplication = privacyItem.application;
            Objects.requireNonNull(privacyApplication);
            arrayList.add(new Pair(Integer.valueOf(privacyApplication.uid), privacyItem.privacyType.getPermGroupName()));
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, 10));
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            PrivacyItem privacyItem2 = (PrivacyItem) it2.next();
            Objects.requireNonNull(privacyItem2);
            PrivacyApplication privacyApplication2 = privacyItem2.application;
            Objects.requireNonNull(privacyApplication2);
            arrayList2.add(new Pair(Integer.valueOf(privacyApplication2.uid), privacyItem2.privacyType.getPermGroupName()));
        }
        return Intrinsics.areEqual(set, CollectionsKt___CollectionsKt.toSet(arrayList2));
    }

    public final void onPrivacyItemsChanged(List<PrivacyItem> list) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        if (!uniqueItemsMatch(list, this.currentPrivacyItems)) {
            if (list.isEmpty()) {
                this.previousPrivacyItems = this.currentPrivacyItems;
                this.timeLastEmpty = this.this$0.systemClock.elapsedRealtime();
            }
            this.currentPrivacyItems = list;
            boolean z3 = false;
            SystemStatusAnimationScheduler systemStatusAnimationScheduler = null;
            if (list.isEmpty()) {
                SystemEventCoordinator systemEventCoordinator = this.this$0;
                Objects.requireNonNull(systemEventCoordinator);
                SystemStatusAnimationScheduler systemStatusAnimationScheduler2 = systemEventCoordinator.scheduler;
                if (systemStatusAnimationScheduler2 != null) {
                    systemStatusAnimationScheduler = systemStatusAnimationScheduler2;
                }
                Objects.requireNonNull(systemStatusAnimationScheduler);
                if (systemStatusAnimationScheduler.hasPersistentDot && DeviceConfig.getBoolean("privacy", "enable_immersive_indicator", true)) {
                    systemStatusAnimationScheduler.hasPersistentDot = false;
                    LinkedHashSet<SystemStatusAnimationCallback> linkedHashSet = systemStatusAnimationScheduler.listeners;
                    ArrayList arrayList = new ArrayList();
                    for (SystemStatusAnimationCallback onHidePersistentDot : linkedHashSet) {
                        onHidePersistentDot.onHidePersistentDot();
                    }
                    if (systemStatusAnimationScheduler.animationState == 4) {
                        systemStatusAnimationScheduler.animationState = 0;
                    }
                    if (!arrayList.isEmpty()) {
                        new AnimatorSet().playTogether(arrayList);
                        return;
                    }
                    return;
                }
                return;
            }
            if (!DeviceConfig.getBoolean("privacy", "privacy_chip_animation_enabled", true) || (uniqueItemsMatch(this.currentPrivacyItems, this.previousPrivacyItems) && this.this$0.systemClock.elapsedRealtime() - this.timeLastEmpty < 3000)) {
                z = false;
            } else {
                z = true;
            }
            SystemEventCoordinator systemEventCoordinator2 = this.this$0;
            Objects.requireNonNull(systemEventCoordinator2);
            PrivacyEvent privacyEvent = new PrivacyEvent(z);
            SystemEventCoordinator$privacyStateListener$1 systemEventCoordinator$privacyStateListener$1 = systemEventCoordinator2.privacyStateListener;
            Objects.requireNonNull(systemEventCoordinator$privacyStateListener$1);
            List<PrivacyItem> list2 = systemEventCoordinator$privacyStateListener$1.currentPrivacyItems;
            privacyEvent.privacyItems = list2;
            privacyEvent.contentDescription = systemEventCoordinator2.context.getString(C1777R.string.ongoing_privacy_chip_content_multiple_apps, new Object[]{new PrivacyChipBuilder(systemEventCoordinator2.context, list2).joinTypes()});
            SystemStatusAnimationScheduler systemStatusAnimationScheduler3 = systemEventCoordinator2.scheduler;
            if (systemStatusAnimationScheduler3 == null) {
                systemStatusAnimationScheduler3 = null;
            }
            Objects.requireNonNull(systemStatusAnimationScheduler3);
            if (systemStatusAnimationScheduler3.systemClock.uptimeMillis() - Process.getStartUptimeMillis() < 5000) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2 && DeviceConfig.getBoolean("privacy", "enable_immersive_indicator", true)) {
                Assert.isMainThread();
                int priority = privacyEvent.getPriority();
                StatusEvent statusEvent = systemStatusAnimationScheduler3.scheduledEvent;
                if (statusEvent == null) {
                    i = -1;
                } else {
                    i = statusEvent.getPriority();
                }
                if (priority <= i || (i2 = systemStatusAnimationScheduler3.animationState) == 3 || i2 == 4 || !privacyEvent.getForceVisible()) {
                    StatusEvent statusEvent2 = systemStatusAnimationScheduler3.scheduledEvent;
                    if (statusEvent2 != null && statusEvent2.shouldUpdateFromEvent(privacyEvent)) {
                        z3 = true;
                    }
                    if (z3) {
                        StatusEvent statusEvent3 = systemStatusAnimationScheduler3.scheduledEvent;
                        if (statusEvent3 != null) {
                            statusEvent3.updateFromEvent(privacyEvent);
                        }
                        if (privacyEvent.getForceVisible()) {
                            systemStatusAnimationScheduler3.hasPersistentDot = true;
                            systemStatusAnimationScheduler3.notifyTransitionToPersistentDot();
                            return;
                        }
                        return;
                    }
                    return;
                }
                systemStatusAnimationScheduler3.scheduledEvent = privacyEvent;
                if (privacyEvent.getForceVisible()) {
                    systemStatusAnimationScheduler3.hasPersistentDot = true;
                }
                if (privacyEvent.getShowAnimation() || !privacyEvent.getForceVisible()) {
                    systemStatusAnimationScheduler3.executor.executeDelayed(new SystemStatusAnimationScheduler$scheduleEvent$1(systemStatusAnimationScheduler3), 0);
                    return;
                }
                systemStatusAnimationScheduler3.notifyTransitionToPersistentDot();
                systemStatusAnimationScheduler3.scheduledEvent = null;
            }
        }
    }
}
