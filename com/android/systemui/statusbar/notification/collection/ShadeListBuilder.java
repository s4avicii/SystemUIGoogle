package com.android.systemui.statusbar.notification.collection;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.WindowMagnification$$ExternalSyntheticLambda0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.DefaultNotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.notifcollection.CollectionReadyForBuildListener;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import com.google.android.systemui.smartspace.InterceptingViewPager$$ExternalSyntheticLambda0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class ShadeListBuilder implements Dumpable {
    public static final C12482 DEFAULT_SECTIONER = new NotifSectioner() {
        public final boolean isInSection(ListEntry listEntry) {
            return true;
        }
    };
    public Collection<NotificationEntry> mAllEntries = Collections.emptyList();
    public final boolean mAlwaysLogList;
    public final NotifPipelineChoreographer mChoreographer;
    public boolean mForceReorderable = false;
    public final ShadeListBuilder$$ExternalSyntheticLambda0 mGroupChildrenComparator = new ShadeListBuilder$$ExternalSyntheticLambda0(this);
    public final ArrayMap mGroups = new ArrayMap();
    public final NotificationInteractionTracker mInteractionTracker;
    public int mIterationCount = 0;
    public final ShadeListBuilderLogger mLogger;
    public List<ListEntry> mNewNotifList = new ArrayList();
    public final ArrayList mNotifComparators = new ArrayList();
    public final ArrayList mNotifFinalizeFilters = new ArrayList();
    public List<ListEntry> mNotifList = new ArrayList();
    public final ArrayList mNotifPreGroupFilters = new ArrayList();
    public final ArrayList mNotifPromoters = new ArrayList();
    public final ArrayList mNotifSections = new ArrayList();
    public NotifStabilityManager mNotifStabilityManager;
    public final ArrayList mOnBeforeFinalizeFilterListeners = new ArrayList();
    public final ArrayList mOnBeforeRenderListListeners = new ArrayList();
    public final ArrayList mOnBeforeSortListeners = new ArrayList();
    public final ArrayList mOnBeforeTransformGroupsListeners = new ArrayList();
    public OnRenderListListener mOnRenderListListener;
    public final PipelineState mPipelineState = new PipelineState();
    public List<ListEntry> mReadOnlyNewNotifList = Collections.unmodifiableList(this.mNewNotifList);
    public List<ListEntry> mReadOnlyNotifList = Collections.unmodifiableList(this.mNotifList);
    public final C12471 mReadyForBuildListener = new CollectionReadyForBuildListener() {
    };
    public final SystemClock mSystemClock;
    public final ArrayList<ListEntry> mTempSectionMembers = new ArrayList<>();
    public final ShadeListBuilder$$ExternalSyntheticLambda1 mTopLevelComparator = new ShadeListBuilder$$ExternalSyntheticLambda1(this);

    public interface OnRenderListListener {
        void onRenderList(List<ListEntry> list);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0087, code lost:
        if (r4.mUnmodifiableChildren.contains(r3) == false) goto L_0x00a6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void annulAddition(com.android.systemui.statusbar.notification.collection.ListEntry r3, java.util.List r4) {
        /*
            com.android.systemui.statusbar.notification.collection.GroupEntry r0 = r3.getParent()
            java.lang.String r1 = "Cannot nullify addition of "
            if (r0 == 0) goto L_0x00aa
            com.android.systemui.statusbar.notification.collection.GroupEntry r0 = r3.getParent()
            com.android.systemui.statusbar.notification.collection.GroupEntry r2 = com.android.systemui.statusbar.notification.collection.GroupEntry.ROOT_ENTRY
            if (r0 != r2) goto L_0x0031
            boolean r4 = r4.contains(r3)
            if (r4 != 0) goto L_0x0017
            goto L_0x0031
        L_0x0017:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r3 = r3.getKey()
            r0.append(r3)
            java.lang.String r3 = ": it's still in the shade list."
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        L_0x0031:
            boolean r4 = r3 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
            if (r4 == 0) goto L_0x006b
            r4 = r3
            com.android.systemui.statusbar.notification.collection.GroupEntry r4 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r4
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r4.mSummary
            java.lang.String r1 = "Cannot nullify group "
            if (r0 != 0) goto L_0x0059
            java.util.List<com.android.systemui.statusbar.notification.collection.NotificationEntry> r0 = r4.mUnmodifiableChildren
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0047
            goto L_0x00a6
        L_0x0047:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r4 = r4.mKey
            java.lang.String r1 = ": still has children"
            java.lang.String r4 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1.m8m(r0, r4, r1)
            r3.<init>(r4)
            throw r3
        L_0x0059:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r4 = r4.mKey
            java.lang.String r1 = ": summary is not null"
            java.lang.String r4 = androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1.m8m(r0, r4, r1)
            r3.<init>(r4)
            throw r3
        L_0x006b:
            boolean r4 = r3 instanceof com.android.systemui.statusbar.notification.collection.NotificationEntry
            if (r4 == 0) goto L_0x00a6
            com.android.systemui.statusbar.notification.collection.GroupEntry r4 = r3.getParent()
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mSummary
            if (r3 == r4) goto L_0x008a
            com.android.systemui.statusbar.notification.collection.GroupEntry r4 = r3.getParent()
            java.util.Objects.requireNonNull(r4)
            java.util.List<com.android.systemui.statusbar.notification.collection.NotificationEntry> r4 = r4.mUnmodifiableChildren
            boolean r4 = r4.contains(r3)
            if (r4 != 0) goto L_0x008a
            goto L_0x00a6
        L_0x008a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "Cannot nullify addition of child "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r3 = r3.getKey()
            r0.append(r3)
            java.lang.String r3 = ": it's still attached to its parent."
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        L_0x00a6:
            annulAddition(r3)
            return
        L_0x00aa:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r3 = r3.getKey()
            r0.append(r3)
            java.lang.String r3 = ": no parent."
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.annulAddition(com.android.systemui.statusbar.notification.collection.ListEntry, java.util.List):void");
    }

    public static void callOnCleanup(List list) {
        for (int i = 0; i < list.size(); i++) {
            ((Pluggable) list.get(i)).onCleanup();
        }
    }

    public final boolean canReorder(ListEntry listEntry) {
        if (this.mForceReorderable || getStabilityManager().isEntryReorderingAllowed()) {
            return true;
        }
        return false;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\tShadeListBuilder shade notifications:");
        Assert.isMainThread();
        this.mPipelineState.requireState();
        if (this.mReadOnlyNotifList.size() == 0) {
            printWriter.println("\t\t None");
        }
        Assert.isMainThread();
        this.mPipelineState.requireState();
        List<ListEntry> list = this.mReadOnlyNotifList;
        NotificationInteractionTracker notificationInteractionTracker = this.mInteractionTracker;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ListEntry listEntry = list.get(i);
            ListDumper.dumpEntry(listEntry, Integer.toString(i), "\t\t", sb, true, notificationInteractionTracker.hasUserInteractedWith(listEntry.getKey()));
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    ListDumper.dumpEntry(notificationEntry, i + ":*", "\t\t  ", sb, true, notificationInteractionTracker.hasUserInteractedWith(notificationEntry.mKey));
                }
                List<NotificationEntry> list2 = groupEntry.mUnmodifiableChildren;
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    NotificationEntry notificationEntry2 = list2.get(i2);
                    Objects.requireNonNull(notificationEntry2);
                    ListDumper.dumpEntry(notificationEntry2, i + "." + i2, "\t\t  ", sb, true, notificationInteractionTracker.hasUserInteractedWith(notificationEntry2.mKey));
                }
            }
        }
        printWriter.println(sb.toString());
    }

    public final void filterNotifs(Collection collection, List list, ArrayList arrayList) {
        Trace.beginSection("ShadeListBuilder.filterNotifs");
        long uptimeMillis = this.mSystemClock.uptimeMillis();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                Objects.requireNonNull(groupEntry);
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (applyFilters(notificationEntry, uptimeMillis, arrayList)) {
                    groupEntry.mSummary = null;
                    annulAddition(notificationEntry);
                }
                ArrayList arrayList2 = groupEntry.mChildren;
                int size = arrayList2.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    NotificationEntry notificationEntry2 = (NotificationEntry) arrayList2.get(size);
                    if (applyFilters(notificationEntry2, uptimeMillis, arrayList)) {
                        arrayList2.remove(notificationEntry2);
                        annulAddition(notificationEntry2);
                    }
                }
                list.add(groupEntry);
            } else if (applyFilters((NotificationEntry) listEntry, uptimeMillis, arrayList)) {
                annulAddition(listEntry);
            } else {
                list.add(listEntry);
            }
        }
        Trace.endSection();
    }

    public final NotifStabilityManager getStabilityManager() {
        NotifStabilityManager notifStabilityManager = this.mNotifStabilityManager;
        if (notifStabilityManager == null) {
            return DefaultNotifStabilityManager.INSTANCE;
        }
        return notifStabilityManager;
    }

    public final void pruneIncompleteGroups(List<ListEntry> list) {
        ArraySet arraySet;
        boolean z;
        boolean z2;
        Trace.beginSection("ShadeListBuilder.pruneIncompleteGroups");
        if (getStabilityManager().isEveryChangeAllowed()) {
            arraySet = Collections.emptySet();
        } else {
            ArraySet arraySet2 = new ArraySet();
            for (int i = 0; i < list.size(); i++) {
                ListEntry listEntry = list.get(i);
                Objects.requireNonNull(listEntry);
                ListAttachState listAttachState = listEntry.mAttachState;
                Objects.requireNonNull(listAttachState);
                SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
                Objects.requireNonNull(suppressedAttachState);
                GroupEntry groupEntry = suppressedAttachState.parent;
                if (groupEntry != null) {
                    arraySet2.add(groupEntry.mKey);
                }
            }
            arraySet = arraySet2;
        }
        ArraySet arraySet3 = new ArraySet();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ListEntry listEntry2 = list.get(i2);
            Objects.requireNonNull(listEntry2);
            ListAttachState listAttachState2 = listEntry2.mAttachState;
            Objects.requireNonNull(listAttachState2);
            if (listAttachState2.promoter != null) {
                NotificationEntry representativeEntry = listEntry2.getRepresentativeEntry();
                Objects.requireNonNull(representativeEntry);
                arraySet3.add(representativeEntry.mSbn.getGroupKey());
            }
        }
        arraySet3.addAll(arraySet);
        int i3 = 0;
        while (i3 < list.size()) {
            ListEntry listEntry3 = list.get(i3);
            if (listEntry3 instanceof GroupEntry) {
                GroupEntry groupEntry2 = (GroupEntry) listEntry3;
                Objects.requireNonNull(groupEntry2);
                ArrayList arrayList = groupEntry2.mChildren;
                if (groupEntry2.mSummary != null) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z || arrayList.size() != 0) {
                    if (!z) {
                        pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, i3);
                    } else if (arrayList.size() < 2) {
                        Preconditions.checkState(z, "group must have summary at this point");
                        Preconditions.checkState(!arrayList.isEmpty(), "empty group should have been promoted");
                        if (arraySet.contains(groupEntry2.mKey)) {
                            ListAttachState listAttachState3 = groupEntry2.mAttachState;
                            Objects.requireNonNull(listAttachState3);
                            SuppressedAttachState suppressedAttachState2 = listAttachState3.suppressedChanges;
                            Objects.requireNonNull(suppressedAttachState2);
                            suppressedAttachState2.wasPruneSuppressed = true;
                        } else if (!groupEntry2.wasAttachedInPreviousPass() || getStabilityManager().isGroupChangeAllowed(groupEntry2.mSummary)) {
                            pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, i3);
                        } else {
                            Preconditions.checkState(!arrayList.isEmpty(), "empty group should have been pruned");
                            ListAttachState listAttachState4 = groupEntry2.mAttachState;
                            Objects.requireNonNull(listAttachState4);
                            SuppressedAttachState suppressedAttachState3 = listAttachState4.suppressedChanges;
                            Objects.requireNonNull(suppressedAttachState3);
                            suppressedAttachState3.wasPruneSuppressed = true;
                        }
                    }
                } else if (arraySet3.contains(groupEntry2.mKey)) {
                    pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, i3);
                } else {
                    Preconditions.checkArgument(groupEntry2.mUnmodifiableChildren.isEmpty(), "group should have no children");
                    NotificationEntry notificationEntry = groupEntry2.mSummary;
                    notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
                    if (list.set(i3, notificationEntry) == groupEntry2) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    Preconditions.checkState(z2);
                    groupEntry2.mSummary = null;
                    annulAddition(groupEntry2, list);
                    ListAttachState listAttachState5 = notificationEntry.mAttachState;
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SUMMARY with no children @ ");
                    m.append(this.mPipelineState.getStateName());
                    String sb = m.toString();
                    Objects.requireNonNull(listAttachState5);
                    listAttachState5.groupPruneReason = sb;
                }
                i3--;
            }
            i3++;
        }
        Trace.endSection();
    }

    public final void rebuildListIfBefore(int i) {
        boolean z;
        this.mPipelineState.requireIsBefore(i);
        PipelineState pipelineState = this.mPipelineState;
        Objects.requireNonNull(pipelineState);
        if (pipelineState.mState == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.mChoreographer.schedule();
        }
    }

    public ShadeListBuilder(DumpManager dumpManager, NotifPipelineChoreographer notifPipelineChoreographer, NotifPipelineFlags notifPipelineFlags, NotificationInteractionTracker notificationInteractionTracker, ShadeListBuilderLogger shadeListBuilderLogger, SystemClock systemClock) {
        Assert.isMainThread();
        this.mSystemClock = systemClock;
        this.mLogger = shadeListBuilderLogger;
        Objects.requireNonNull(notifPipelineFlags);
        this.mAlwaysLogList = notifPipelineFlags.featureFlags.isEnabled(Flags.NOTIFICATION_PIPELINE_DEVELOPER_LOGGING);
        this.mInteractionTracker = notificationInteractionTracker;
        this.mChoreographer = notifPipelineChoreographer;
        dumpManager.registerDumpable("ShadeListBuilder", this);
        setSectioners(Collections.emptyList());
    }

    public static boolean applyFilters(NotificationEntry notificationEntry, long j, ArrayList arrayList) {
        NotifFilter notifFilter;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                notifFilter = null;
                break;
            }
            notifFilter = (NotifFilter) arrayList.get(i);
            if (notifFilter.shouldFilterOut(notificationEntry, j)) {
                break;
            }
            i++;
        }
        Objects.requireNonNull(notificationEntry);
        ListAttachState listAttachState = notificationEntry.mAttachState;
        Objects.requireNonNull(listAttachState);
        listAttachState.excludingFilter = notifFilter;
        if (notifFilter != null) {
            notificationEntry.initializationTime = -1;
        }
        if (notifFilter != null) {
            return true;
        }
        return false;
    }

    public static <T> boolean isSorted(List<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) {
            return true;
        }
        Iterator<T> it = list.iterator();
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (comparator.compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    public static void setEntrySection(ListEntry listEntry, NotifSection notifSection) {
        Objects.requireNonNull(listEntry);
        ListAttachState listAttachState = listEntry.mAttachState;
        Objects.requireNonNull(listAttachState);
        listAttachState.section = notifSection;
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            ListAttachState listAttachState2 = representativeEntry.mAttachState;
            Objects.requireNonNull(listAttachState2);
            listAttachState2.section = notifSection;
            if (notifSection != null) {
                representativeEntry.mBucket = notifSection.bucket;
            }
        }
    }

    public final void logAttachStateChanges(ListEntry listEntry) {
        boolean z;
        NotifSection notifSection;
        NotifSection notifSection2;
        NotifPromoter notifPromoter;
        NotifPromoter notifPromoter2;
        Objects.requireNonNull(listEntry);
        ListAttachState listAttachState = listEntry.mAttachState;
        ListAttachState listAttachState2 = listEntry.mPreviousAttachState;
        if (!Objects.equals(listAttachState, listAttachState2)) {
            ShadeListBuilderLogger shadeListBuilderLogger = this.mLogger;
            int i = this.mIterationCount;
            String key = listEntry.getKey();
            Objects.requireNonNull(listAttachState2);
            GroupEntry groupEntry = listAttachState2.parent;
            Objects.requireNonNull(listAttachState);
            shadeListBuilderLogger.logEntryAttachStateChanged(i, key, groupEntry, listAttachState.parent);
            GroupEntry groupEntry2 = listAttachState.parent;
            GroupEntry groupEntry3 = listAttachState2.parent;
            if (groupEntry2 != groupEntry3) {
                this.mLogger.logParentChanged(this.mIterationCount, groupEntry3, groupEntry2);
            }
            SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
            Objects.requireNonNull(suppressedAttachState);
            if (suppressedAttachState.parent != null) {
                ShadeListBuilderLogger shadeListBuilderLogger2 = this.mLogger;
                int i2 = this.mIterationCount;
                SuppressedAttachState suppressedAttachState2 = listAttachState.suppressedChanges;
                Objects.requireNonNull(suppressedAttachState2);
                shadeListBuilderLogger2.logParentChangeSuppressed(i2, suppressedAttachState2.parent, listAttachState.parent);
            }
            SuppressedAttachState suppressedAttachState3 = listAttachState.suppressedChanges;
            Objects.requireNonNull(suppressedAttachState3);
            if (suppressedAttachState3.section != null) {
                ShadeListBuilderLogger shadeListBuilderLogger3 = this.mLogger;
                int i3 = this.mIterationCount;
                SuppressedAttachState suppressedAttachState4 = listAttachState.suppressedChanges;
                Objects.requireNonNull(suppressedAttachState4);
                shadeListBuilderLogger3.logSectionChangeSuppressed(i3, suppressedAttachState4.section, listAttachState.section);
            }
            SuppressedAttachState suppressedAttachState5 = listAttachState.suppressedChanges;
            Objects.requireNonNull(suppressedAttachState5);
            if (suppressedAttachState5.wasPruneSuppressed) {
                this.mLogger.logGroupPruningSuppressed(this.mIterationCount, listAttachState.parent);
            }
            if (!Objects.equals(listAttachState.groupPruneReason, listAttachState2.groupPruneReason)) {
                this.mLogger.logPrunedReasonChanged(this.mIterationCount, listAttachState2.groupPruneReason, listAttachState.groupPruneReason);
            }
            NotifFilter notifFilter = listAttachState.excludingFilter;
            NotifFilter notifFilter2 = listAttachState2.excludingFilter;
            if (notifFilter != notifFilter2) {
                this.mLogger.logFilterChanged(this.mIterationCount, notifFilter2, notifFilter);
            }
            if (listAttachState.parent != null || listAttachState2.parent == null) {
                z = false;
            } else {
                z = true;
            }
            if (!z && (notifPromoter = listAttachState.promoter) != (notifPromoter2 = listAttachState2.promoter)) {
                this.mLogger.logPromoterChanged(this.mIterationCount, notifPromoter2, notifPromoter);
            }
            if (!z && (notifSection = listAttachState.section) != (notifSection2 = listAttachState2.section)) {
                this.mLogger.logSectionChanged(this.mIterationCount, notifSection2, notifSection);
            }
        }
    }

    public final boolean maybeSuppressGroupChange(NotificationEntry notificationEntry, List<ListEntry> list) {
        if (!notificationEntry.wasAttachedInPreviousPass()) {
            return false;
        }
        ListAttachState listAttachState = notificationEntry.mPreviousAttachState;
        Objects.requireNonNull(listAttachState);
        GroupEntry groupEntry = listAttachState.parent;
        GroupEntry parent = notificationEntry.getParent();
        if (groupEntry == parent || getStabilityManager().isGroupChangeAllowed(notificationEntry)) {
            return false;
        }
        ListAttachState listAttachState2 = notificationEntry.mAttachState;
        Objects.requireNonNull(listAttachState2);
        SuppressedAttachState suppressedAttachState = listAttachState2.suppressedChanges;
        Objects.requireNonNull(suppressedAttachState);
        suppressedAttachState.parent = parent;
        notificationEntry.setParent(groupEntry);
        if (groupEntry == GroupEntry.ROOT_ENTRY) {
            list.add(notificationEntry);
            return true;
        } else if (groupEntry == null) {
            return true;
        } else {
            groupEntry.mChildren.add(notificationEntry);
            if (this.mGroups.containsKey(groupEntry.mKey)) {
                return true;
            }
            this.mGroups.put(groupEntry.mKey, groupEntry);
            return true;
        }
    }

    public final void pruneGroupAtIndexAndPromoteAnyChildren(List<ListEntry> list, GroupEntry groupEntry, int i) {
        boolean z;
        boolean z2;
        String str;
        if (list.remove(i) == groupEntry) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z);
        ArrayList arrayList = groupEntry.mChildren;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            groupEntry.mSummary = null;
            annulAddition(notificationEntry, list);
            ListAttachState listAttachState = notificationEntry.mAttachState;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SUMMARY with too few children @ ");
            m.append(this.mPipelineState.getStateName());
            String sb = m.toString();
            Objects.requireNonNull(listAttachState);
            listAttachState.groupPruneReason = sb;
        }
        if (!arrayList.isEmpty()) {
            if (z2) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CHILD with ");
                m2.append(arrayList.size() - 1);
                m2.append(" siblings @ ");
                m2.append(this.mPipelineState.getStateName());
                str = m2.toString();
            } else {
                StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CHILD with no summary @ ");
                m3.append(this.mPipelineState.getStateName());
                str = m3.toString();
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(i2);
                notificationEntry2.setParent(GroupEntry.ROOT_ENTRY);
                ListAttachState listAttachState2 = notificationEntry2.mAttachState;
                Objects.requireNonNull(str);
                Objects.requireNonNull(listAttachState2);
                listAttachState2.groupPruneReason = str;
            }
            list.addAll(i, arrayList);
            arrayList.clear();
        }
        annulAddition(groupEntry, list);
    }

    public final void setSectioners(List<NotifSectioner> list) {
        Assert.isMainThread();
        this.mPipelineState.requireState();
        this.mNotifSections.clear();
        for (NotifSectioner next : list) {
            NotifSection notifSection = new NotifSection(next, this.mNotifSections.size());
            NotifComparator notifComparator = notifSection.comparator;
            this.mNotifSections.add(notifSection);
            next.mListener = new WindowMagnification$$ExternalSyntheticLambda0(this);
            if (notifComparator != null) {
                notifComparator.mListener = new InterceptingViewPager$$ExternalSyntheticLambda0(this);
            }
        }
        ArrayList arrayList = this.mNotifSections;
        arrayList.add(new NotifSection(DEFAULT_SECTIONER, arrayList.size()));
    }

    public static void annulAddition(ListEntry listEntry) {
        listEntry.setParent((GroupEntry) null);
        ListAttachState listAttachState = listEntry.mAttachState;
        Objects.requireNonNull(listAttachState);
        listAttachState.section = null;
        ListAttachState listAttachState2 = listEntry.mAttachState;
        Objects.requireNonNull(listAttachState2);
        listAttachState2.promoter = null;
    }
}
