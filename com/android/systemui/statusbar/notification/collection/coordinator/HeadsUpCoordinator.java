package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator implements Coordinator {
    public NotifLifetimeExtender.OnEndLifetimeExtensionCallback mEndLifetimeExtension;
    public final ArrayMap<String, Long> mEntriesBindingUntil = new ArrayMap<>();
    public final DelayableExecutor mExecutor;
    public final HeadsUpManager mHeadsUpManager;
    public final HeadsUpViewBinder mHeadsUpViewBinder;
    public final NodeController mIncomingHeaderController;
    public final HeadsUpCoordinator$mLifetimeExtender$1 mLifetimeExtender = new HeadsUpCoordinator$mLifetimeExtender$1(this);
    public final HeadsUpCoordinatorLogger mLogger;
    public final HeadsUpCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new HeadsUpCoordinator$mNotifCollectionListener$1(this);
    public NotifPipeline mNotifPipeline;
    public final HeadsUpCoordinator$mNotifPromoter$1 mNotifPromoter = new HeadsUpCoordinator$mNotifPromoter$1(this);
    public final NotificationInterruptStateProvider mNotificationInterruptStateProvider;
    public final ArraySet<NotificationEntry> mNotifsExtendingLifetime = new ArraySet<>();
    public long mNow = -1;
    public final HeadsUpCoordinator$mOnHeadsUpChangedListener$1 mOnHeadsUpChangedListener = new HeadsUpCoordinator$mOnHeadsUpChangedListener$1(this);
    public final LinkedHashMap<String, PostedEntry> mPostedEntries = new LinkedHashMap<>();
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final SystemClock mSystemClock;
    public final HeadsUpCoordinator$sectioner$1 sectioner = new HeadsUpCoordinator$sectioner$1(this);

    /* compiled from: HeadsUpCoordinator.kt */
    public static final class PostedEntry {
        public final NotificationEntry entry;
        public boolean isAlerting;
        public boolean isBinding;
        public final String key;
        public boolean shouldHeadsUpAgain;
        public boolean shouldHeadsUpEver;
        public final boolean wasAdded;
        public boolean wasUpdated;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostedEntry)) {
                return false;
            }
            PostedEntry postedEntry = (PostedEntry) obj;
            return Intrinsics.areEqual(this.entry, postedEntry.entry) && this.wasAdded == postedEntry.wasAdded && this.wasUpdated == postedEntry.wasUpdated && this.shouldHeadsUpEver == postedEntry.shouldHeadsUpEver && this.shouldHeadsUpAgain == postedEntry.shouldHeadsUpAgain && this.isAlerting == postedEntry.isAlerting && this.isBinding == postedEntry.isBinding;
        }

        public final int hashCode() {
            int hashCode = this.entry.hashCode() * 31;
            boolean z = this.wasAdded;
            boolean z2 = true;
            if (z) {
                z = true;
            }
            int i = (hashCode + (z ? 1 : 0)) * 31;
            boolean z3 = this.wasUpdated;
            if (z3) {
                z3 = true;
            }
            int i2 = (i + (z3 ? 1 : 0)) * 31;
            boolean z4 = this.shouldHeadsUpEver;
            if (z4) {
                z4 = true;
            }
            int i3 = (i2 + (z4 ? 1 : 0)) * 31;
            boolean z5 = this.shouldHeadsUpAgain;
            if (z5) {
                z5 = true;
            }
            int i4 = (i3 + (z5 ? 1 : 0)) * 31;
            boolean z6 = this.isAlerting;
            if (z6) {
                z6 = true;
            }
            int i5 = (i4 + (z6 ? 1 : 0)) * 31;
            boolean z7 = this.isBinding;
            if (!z7) {
                z2 = z7;
            }
            return i5 + (z2 ? 1 : 0);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("PostedEntry(entry=");
            m.append(this.entry);
            m.append(", wasAdded=");
            m.append(this.wasAdded);
            m.append(", wasUpdated=");
            m.append(this.wasUpdated);
            m.append(", shouldHeadsUpEver=");
            m.append(this.shouldHeadsUpEver);
            m.append(", shouldHeadsUpAgain=");
            m.append(this.shouldHeadsUpAgain);
            m.append(", isAlerting=");
            m.append(this.isAlerting);
            m.append(", isBinding=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.isBinding, ')');
        }

        public PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.entry = notificationEntry;
            this.wasAdded = z;
            this.wasUpdated = z2;
            this.shouldHeadsUpEver = z3;
            this.shouldHeadsUpAgain = z4;
            this.isAlerting = z5;
            this.isBinding = z6;
            Objects.requireNonNull(notificationEntry);
            this.key = notificationEntry.mKey;
        }
    }

    public final void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        this.mHeadsUpManager.addListener(this.mOnHeadsUpChangedListener);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        HeadsUpCoordinator$attach$1 headsUpCoordinator$attach$1 = new HeadsUpCoordinator$attach$1(this);
        ShadeListBuilder shadeListBuilder = notifPipeline.mShadeListBuilder;
        Objects.requireNonNull(shadeListBuilder);
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnBeforeTransformGroupsListeners.add(headsUpCoordinator$attach$1);
        notifPipeline.addOnBeforeFinalizeFilterListener(new HeadsUpCoordinator$attach$2(this));
        notifPipeline.addPromoter(this.mNotifPromoter);
        notifPipeline.addNotificationLifetimeExtender(this.mLifetimeExtender);
    }

    public final void bindForAsyncHeadsUp(PostedEntry postedEntry) {
        ArrayMap<String, Long> arrayMap = this.mEntriesBindingUntil;
        Objects.requireNonNull(postedEntry);
        arrayMap.put(postedEntry.key, Long.valueOf(this.mNow + 1000));
        this.mHeadsUpViewBinder.bindHeadsUpView(postedEntry.entry, new HeadsUpCoordinator$bindForAsyncHeadsUp$1(this));
    }

    public final void handlePostedEntry(PostedEntry postedEntry, String str) {
        boolean z;
        HeadsUpCoordinatorLogger headsUpCoordinatorLogger = this.mLogger;
        Objects.requireNonNull(headsUpCoordinatorLogger);
        if (headsUpCoordinatorLogger.verbose) {
            LogBuffer logBuffer = headsUpCoordinatorLogger.buffer;
            LogLevel logLevel = LogLevel.VERBOSE;
            HeadsUpCoordinatorLogger$logPostedEntryWillEvaluate$2 headsUpCoordinatorLogger$logPostedEntryWillEvaluate$2 = HeadsUpCoordinatorLogger$logPostedEntryWillEvaluate$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("HeadsUpCoordinator", logLevel, headsUpCoordinatorLogger$logPostedEntryWillEvaluate$2);
                obtain.str1 = postedEntry.key;
                obtain.str2 = str;
                obtain.bool1 = postedEntry.shouldHeadsUpEver;
                obtain.bool2 = postedEntry.shouldHeadsUpAgain;
                logBuffer.push(obtain);
            }
        }
        if (!postedEntry.wasAdded) {
            boolean z2 = postedEntry.isAlerting;
            if (z2 || postedEntry.isBinding) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (postedEntry.shouldHeadsUpEver) {
                    if (z2) {
                        this.mHeadsUpManager.updateNotification(postedEntry.key, postedEntry.shouldHeadsUpAgain);
                    }
                } else if (z2) {
                    this.mHeadsUpManager.removeNotification(postedEntry.key, false);
                } else {
                    NotificationEntry notificationEntry = postedEntry.entry;
                    ArrayMap<String, Long> arrayMap = this.mEntriesBindingUntil;
                    Objects.requireNonNull(notificationEntry);
                    arrayMap.remove(notificationEntry.mKey);
                    this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
                }
            } else if (postedEntry.shouldHeadsUpEver && postedEntry.shouldHeadsUpAgain) {
                bindForAsyncHeadsUp(postedEntry);
            }
        } else if (postedEntry.shouldHeadsUpEver) {
            bindForAsyncHeadsUp(postedEntry);
        }
    }

    public final boolean isEntryBinding(ListEntry listEntry) {
        Long l = this.mEntriesBindingUntil.get(listEntry.getKey());
        if (l == null || l.longValue() < this.mNow) {
            return false;
        }
        return true;
    }

    public HeadsUpCoordinator(HeadsUpCoordinatorLogger headsUpCoordinatorLogger, SystemClock systemClock, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, NotificationInterruptStateProvider notificationInterruptStateProvider, NotificationRemoteInputManager notificationRemoteInputManager, NodeController nodeController, DelayableExecutor delayableExecutor) {
        this.mLogger = headsUpCoordinatorLogger;
        this.mSystemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mNotificationInterruptStateProvider = notificationInterruptStateProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mIncomingHeaderController = nodeController;
        this.mExecutor = delayableExecutor;
    }

    public static final boolean access$isGoingToShowHunNoRetract(HeadsUpCoordinator headsUpCoordinator, ListEntry listEntry) {
        Boolean bool;
        boolean z;
        boolean z2;
        Objects.requireNonNull(headsUpCoordinator);
        PostedEntry postedEntry = headsUpCoordinator.mPostedEntries.get(listEntry.getKey());
        if (postedEntry == null) {
            bool = null;
        } else {
            if (postedEntry.isAlerting || postedEntry.isBinding) {
                z = true;
            } else {
                z = false;
            }
            if (z || (postedEntry.shouldHeadsUpEver && (postedEntry.wasAdded || postedEntry.shouldHeadsUpAgain))) {
                z2 = true;
            } else {
                z2 = false;
            }
            bool = Boolean.valueOf(z2);
        }
        if (bool != null) {
            return bool.booleanValue();
        }
        if (headsUpCoordinator.mHeadsUpManager.isAlerting(listEntry.getKey()) || headsUpCoordinator.isEntryBinding(listEntry)) {
            return true;
        }
        return false;
    }
}
