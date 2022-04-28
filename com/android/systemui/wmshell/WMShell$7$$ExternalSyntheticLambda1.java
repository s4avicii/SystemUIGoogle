package com.android.systemui.wmshell;

import android.graphics.Rect;
import android.os.Trace;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListAttachState;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.SuppressedAttachState;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeSortListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.wmshell.WMShell;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda26;
import com.google.android.systemui.assist.uihints.TouchInsideHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShell$7$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$7$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        boolean z;
        int i;
        boolean z2 = false;
        switch (this.$r8$classId) {
            case 0:
                WMShell.C17707 r13 = (WMShell.C17707) this.f$0;
                Objects.requireNonNull(r13);
                SysUiState sysUiState = WMShell.this.mSysUiState;
                sysUiState.setFlag(65536, false);
                sysUiState.commitUpdate(0);
                return;
            case 1:
                MagnificationGestureDetector magnificationGestureDetector = (MagnificationGestureDetector) this.f$0;
                Objects.requireNonNull(magnificationGestureDetector);
                magnificationGestureDetector.mDetectSingleTap = false;
                return;
            case 2:
                EdgeBackGestureHandler edgeBackGestureHandler = (EdgeBackGestureHandler) this.f$0;
                int i2 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                Objects.requireNonNull(edgeBackGestureHandler);
                if (!edgeBackGestureHandler.mIsEnabled || !edgeBackGestureHandler.mIsBackGestureAllowed) {
                    z = false;
                } else {
                    z = true;
                }
                edgeBackGestureHandler.updateCurrentUserResources();
                Runnable runnable = edgeBackGestureHandler.mStateChangeCallback;
                if (runnable != null) {
                    if (edgeBackGestureHandler.mIsEnabled && edgeBackGestureHandler.mIsBackGestureAllowed) {
                        z2 = true;
                    }
                    if (z != z2) {
                        runnable.run();
                        return;
                    }
                    return;
                }
                return;
            case 3:
                QSCarrierGroupController qSCarrierGroupController = (QSCarrierGroupController) this.f$0;
                Objects.requireNonNull(qSCarrierGroupController);
                if (qSCarrierGroupController.mListening) {
                    if (qSCarrierGroupController.mNetworkController.hasVoiceCallingFeature()) {
                        qSCarrierGroupController.mNetworkController.addCallback(qSCarrierGroupController.mSignalCallback);
                    }
                    CarrierTextManager carrierTextManager = qSCarrierGroupController.mCarrierTextManager;
                    QSCarrierGroupController.Callback callback = qSCarrierGroupController.mCallback;
                    Objects.requireNonNull(carrierTextManager);
                    carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(carrierTextManager, callback, 0));
                    return;
                }
                qSCarrierGroupController.mNetworkController.removeCallback(qSCarrierGroupController.mSignalCallback);
                CarrierTextManager carrierTextManager2 = qSCarrierGroupController.mCarrierTextManager;
                Objects.requireNonNull(carrierTextManager2);
                carrierTextManager2.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(carrierTextManager2, (Object) null, 0));
                return;
            case 4:
                ShadeListBuilder shadeListBuilder = (ShadeListBuilder) this.f$0;
                Objects.requireNonNull(shadeListBuilder);
                Trace.beginSection("ShadeListBuilder.buildList");
                shadeListBuilder.mPipelineState.requireIsBefore(1);
                if (!shadeListBuilder.mNotifStabilityManager.isPipelineRunAllowed()) {
                    shadeListBuilder.mLogger.logPipelineRunSuppressed();
                    Trace.endSection();
                    return;
                }
                PipelineState pipelineState = shadeListBuilder.mPipelineState;
                Objects.requireNonNull(pipelineState);
                pipelineState.mState = 1;
                shadeListBuilder.mPipelineState.incrementTo(2);
                for (GroupEntry groupEntry : shadeListBuilder.mGroups.values()) {
                    groupEntry.beginNewAttachState();
                    groupEntry.mChildren.clear();
                    groupEntry.mSummary = null;
                }
                for (NotificationEntry beginNewAttachState : shadeListBuilder.mAllEntries) {
                    beginNewAttachState.beginNewAttachState();
                }
                shadeListBuilder.mNotifList.clear();
                shadeListBuilder.getStabilityManager().onBeginRun();
                shadeListBuilder.mPipelineState.incrementTo(3);
                shadeListBuilder.filterNotifs(shadeListBuilder.mAllEntries, shadeListBuilder.mNotifList, shadeListBuilder.mNotifPreGroupFilters);
                shadeListBuilder.mPipelineState.incrementTo(4);
                List<ListEntry> list = shadeListBuilder.mNotifList;
                List<ListEntry> list2 = shadeListBuilder.mNewNotifList;
                Trace.beginSection("ShadeListBuilder.groupNotifs");
                Iterator<ListEntry> it = list.iterator();
                while (it.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) it.next();
                    Objects.requireNonNull(notificationEntry);
                    if (notificationEntry.mSbn.isGroup()) {
                        String groupKey = notificationEntry.mSbn.getGroupKey();
                        GroupEntry groupEntry2 = (GroupEntry) shadeListBuilder.mGroups.get(groupKey);
                        if (groupEntry2 == null) {
                            groupEntry2 = new GroupEntry(groupKey, shadeListBuilder.mSystemClock.uptimeMillis());
                            shadeListBuilder.mGroups.put(groupKey, groupEntry2);
                        }
                        if (groupEntry2.getParent() == null) {
                            groupEntry2.setParent(GroupEntry.ROOT_ENTRY);
                            list2.add(groupEntry2);
                        }
                        notificationEntry.setParent(groupEntry2);
                        if (notificationEntry.mSbn.getNotification().isGroupSummary()) {
                            NotificationEntry notificationEntry2 = groupEntry2.mSummary;
                            if (notificationEntry2 == null) {
                                groupEntry2.mSummary = notificationEntry;
                            } else {
                                shadeListBuilder.mLogger.logDuplicateSummary(shadeListBuilder.mIterationCount, groupEntry2.mKey, notificationEntry2.mKey, notificationEntry.mKey);
                                if (notificationEntry.mSbn.getPostTime() > notificationEntry2.mSbn.getPostTime()) {
                                    groupEntry2.mSummary = notificationEntry;
                                    ShadeListBuilder.annulAddition(notificationEntry2, list2);
                                } else {
                                    ShadeListBuilder.annulAddition(notificationEntry, list2);
                                }
                            }
                        } else {
                            groupEntry2.mChildren.add(notificationEntry);
                        }
                    } else {
                        String str = notificationEntry.mKey;
                        if (shadeListBuilder.mGroups.containsKey(str)) {
                            shadeListBuilder.mLogger.logDuplicateTopLevelKey(shadeListBuilder.mIterationCount, str);
                        } else {
                            notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
                            list2.add(notificationEntry);
                        }
                    }
                }
                Trace.endSection();
                shadeListBuilder.mNotifList.clear();
                List<ListEntry> list3 = shadeListBuilder.mNotifList;
                List<ListEntry> list4 = shadeListBuilder.mNewNotifList;
                shadeListBuilder.mNotifList = list4;
                shadeListBuilder.mNewNotifList = list3;
                List<ListEntry> list5 = shadeListBuilder.mReadOnlyNotifList;
                shadeListBuilder.mReadOnlyNotifList = shadeListBuilder.mReadOnlyNewNotifList;
                shadeListBuilder.mReadOnlyNewNotifList = list5;
                shadeListBuilder.pruneIncompleteGroups(list4);
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeTransformGroups");
                for (int i3 = 0; i3 < shadeListBuilder.mOnBeforeTransformGroupsListeners.size(); i3++) {
                    ((OnBeforeTransformGroupsListener) shadeListBuilder.mOnBeforeTransformGroupsListeners.get(i3)).onBeforeTransformGroups();
                }
                Trace.endSection();
                shadeListBuilder.mPipelineState.incrementTo(5);
                List<ListEntry> list6 = shadeListBuilder.mNotifList;
                Trace.beginSection("ShadeListBuilder.promoteNotifs");
                for (int i4 = 0; i4 < list6.size(); i4++) {
                    ListEntry listEntry = list6.get(i4);
                    if (listEntry instanceof GroupEntry) {
                        GroupEntry groupEntry3 = (GroupEntry) listEntry;
                        Objects.requireNonNull(groupEntry3);
                        groupEntry3.mChildren.removeIf(new ShadeListBuilder$$ExternalSyntheticLambda2(shadeListBuilder, list6));
                    }
                }
                Trace.endSection();
                shadeListBuilder.pruneIncompleteGroups(shadeListBuilder.mNotifList);
                shadeListBuilder.mPipelineState.incrementTo(6);
                List<ListEntry> list7 = shadeListBuilder.mNotifList;
                if (!shadeListBuilder.getStabilityManager().isEveryChangeAllowed()) {
                    Trace.beginSection("ShadeListBuilder.stabilizeGroupingNotifs");
                    int i5 = 0;
                    while (i5 < list7.size()) {
                        ListEntry listEntry2 = list7.get(i5);
                        if (listEntry2 instanceof GroupEntry) {
                            GroupEntry groupEntry4 = (GroupEntry) listEntry2;
                            Objects.requireNonNull(groupEntry4);
                            ArrayList arrayList = groupEntry4.mChildren;
                            int i6 = 0;
                            while (i6 < groupEntry4.mUnmodifiableChildren.size()) {
                                if (shadeListBuilder.maybeSuppressGroupChange((NotificationEntry) arrayList.get(i6), list7)) {
                                    arrayList.remove(i6);
                                    i6--;
                                }
                                i6++;
                            }
                        } else if (shadeListBuilder.maybeSuppressGroupChange(listEntry2.getRepresentativeEntry(), list7)) {
                            list7.remove(i5);
                            i5--;
                        }
                        i5++;
                    }
                    Trace.endSection();
                }
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeSort");
                for (int i7 = 0; i7 < shadeListBuilder.mOnBeforeSortListeners.size(); i7++) {
                    ((OnBeforeSortListener) shadeListBuilder.mOnBeforeSortListeners.get(i7)).onBeforeSort();
                }
                Trace.endSection();
                shadeListBuilder.mPipelineState.incrementTo(7);
                Trace.beginSection("ShadeListBuilder.assignSections");
                for (ListEntry next : shadeListBuilder.mNotifList) {
                    int i8 = 0;
                    while (i8 < shadeListBuilder.mNotifSections.size()) {
                        NotifSection notifSection = (NotifSection) shadeListBuilder.mNotifSections.get(i8);
                        Objects.requireNonNull(notifSection);
                        if (notifSection.sectioner.isInSection(next)) {
                            Objects.requireNonNull(next);
                            ListAttachState listAttachState = next.mPreviousAttachState;
                            if (next.wasAttachedInPreviousPass()) {
                                Objects.requireNonNull(listAttachState);
                                if (notifSection != listAttachState.section && !shadeListBuilder.getStabilityManager().isSectionChangeAllowed(next.getRepresentativeEntry())) {
                                    ListAttachState listAttachState2 = next.mAttachState;
                                    Objects.requireNonNull(listAttachState2);
                                    SuppressedAttachState suppressedAttachState = listAttachState2.suppressedChanges;
                                    Objects.requireNonNull(suppressedAttachState);
                                    suppressedAttachState.section = notifSection;
                                    notifSection = listAttachState.section;
                                }
                            }
                            ShadeListBuilder.setEntrySection(next, notifSection);
                            if (next instanceof GroupEntry) {
                                for (NotificationEntry entrySection : ((GroupEntry) next).mUnmodifiableChildren) {
                                    ShadeListBuilder.setEntrySection(entrySection, notifSection);
                                }
                            }
                        } else {
                            i8++;
                        }
                    }
                    throw new RuntimeException("Missing default sectioner!");
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.notifySectionEntriesUpdated");
                shadeListBuilder.mTempSectionMembers.clear();
                Iterator it2 = shadeListBuilder.mNotifSections.iterator();
                while (it2.hasNext()) {
                    NotifSection notifSection2 = (NotifSection) it2.next();
                    for (ListEntry next2 : shadeListBuilder.mNotifList) {
                        if (notifSection2 == next2.getSection()) {
                            shadeListBuilder.mTempSectionMembers.add(next2);
                        }
                    }
                    Objects.requireNonNull(notifSection2);
                    notifSection2.sectioner.onEntriesUpdated(shadeListBuilder.mTempSectionMembers);
                    shadeListBuilder.mTempSectionMembers.clear();
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.sortListAndGroups");
                for (ListEntry next3 : shadeListBuilder.mNotifList) {
                    if (next3 instanceof GroupEntry) {
                        GroupEntry groupEntry5 = (GroupEntry) next3;
                        ShadeListBuilder$$ExternalSyntheticLambda0 shadeListBuilder$$ExternalSyntheticLambda0 = shadeListBuilder.mGroupChildrenComparator;
                        Objects.requireNonNull(groupEntry5);
                        groupEntry5.mChildren.sort(shadeListBuilder$$ExternalSyntheticLambda0);
                    }
                }
                shadeListBuilder.mNotifList.sort(shadeListBuilder.mTopLevelComparator);
                List<ListEntry> list8 = shadeListBuilder.mNotifList;
                if (list8.size() != 0) {
                    NotifSection section = list8.get(0).getSection();
                    Objects.requireNonNull(section);
                    int i9 = 0;
                    for (int i10 = 0; i10 < list8.size(); i10++) {
                        ListEntry listEntry3 = list8.get(i10);
                        NotifSection section2 = listEntry3.getSection();
                        Objects.requireNonNull(section2);
                        if (section2.index != section.index) {
                            i9 = 0;
                            section = section2;
                        }
                        ListAttachState listAttachState3 = listEntry3.mAttachState;
                        Objects.requireNonNull(listAttachState3);
                        listAttachState3.stableIndex = i9;
                        if (listEntry3 instanceof GroupEntry) {
                            GroupEntry groupEntry6 = (GroupEntry) listEntry3;
                            for (int i11 = 0; i11 < groupEntry6.mUnmodifiableChildren.size(); i11++) {
                                ListEntry listEntry4 = groupEntry6.mUnmodifiableChildren.get(i11);
                                Objects.requireNonNull(listEntry4);
                                ListAttachState listAttachState4 = listEntry4.mAttachState;
                                Objects.requireNonNull(listAttachState4);
                                listAttachState4.stableIndex = i9;
                                i9++;
                            }
                        }
                        i9++;
                    }
                }
                if (!shadeListBuilder.getStabilityManager().isEveryChangeAllowed()) {
                    shadeListBuilder.mForceReorderable = true;
                    boolean isSorted = ShadeListBuilder.isSorted(shadeListBuilder.mNotifList, shadeListBuilder.mTopLevelComparator);
                    shadeListBuilder.mForceReorderable = false;
                    if (!isSorted) {
                        shadeListBuilder.getStabilityManager().onEntryReorderSuppressed();
                    }
                }
                Trace.endSection();
                List<ListEntry> list9 = shadeListBuilder.mReadOnlyNotifList;
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeFinalizeFilter");
                for (int i12 = 0; i12 < shadeListBuilder.mOnBeforeFinalizeFilterListeners.size(); i12++) {
                    ((OnBeforeFinalizeFilterListener) shadeListBuilder.mOnBeforeFinalizeFilterListeners.get(i12)).onBeforeFinalizeFilter(list9);
                }
                Trace.endSection();
                shadeListBuilder.mPipelineState.incrementTo(8);
                shadeListBuilder.filterNotifs(shadeListBuilder.mNotifList, shadeListBuilder.mNewNotifList, shadeListBuilder.mNotifFinalizeFilters);
                shadeListBuilder.mNotifList.clear();
                List<ListEntry> list10 = shadeListBuilder.mNotifList;
                List<ListEntry> list11 = shadeListBuilder.mNewNotifList;
                shadeListBuilder.mNotifList = list11;
                shadeListBuilder.mNewNotifList = list10;
                List<ListEntry> list12 = shadeListBuilder.mReadOnlyNotifList;
                shadeListBuilder.mReadOnlyNotifList = shadeListBuilder.mReadOnlyNewNotifList;
                shadeListBuilder.mReadOnlyNewNotifList = list12;
                shadeListBuilder.pruneIncompleteGroups(list11);
                shadeListBuilder.mPipelineState.incrementTo(9);
                Trace.beginSection("ShadeListBuilder.logChanges");
                for (NotificationEntry logAttachStateChanges : shadeListBuilder.mAllEntries) {
                    shadeListBuilder.logAttachStateChanges(logAttachStateChanges);
                }
                for (GroupEntry logAttachStateChanges2 : shadeListBuilder.mGroups.values()) {
                    shadeListBuilder.logAttachStateChanges(logAttachStateChanges2);
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.freeEmptyGroups");
                shadeListBuilder.mGroups.values().removeIf(WifiPickerTracker$$ExternalSyntheticLambda26.INSTANCE$1);
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.cleanupPluggables");
                ShadeListBuilder.callOnCleanup(shadeListBuilder.mNotifPreGroupFilters);
                ShadeListBuilder.callOnCleanup(shadeListBuilder.mNotifPromoters);
                ShadeListBuilder.callOnCleanup(shadeListBuilder.mNotifFinalizeFilters);
                ShadeListBuilder.callOnCleanup(shadeListBuilder.mNotifComparators);
                for (int i13 = 0; i13 < shadeListBuilder.mNotifSections.size(); i13++) {
                    NotifSection notifSection3 = (NotifSection) shadeListBuilder.mNotifSections.get(i13);
                    Objects.requireNonNull(notifSection3);
                    Objects.requireNonNull(notifSection3.sectioner);
                }
                ShadeListBuilder.callOnCleanup(List.of(shadeListBuilder.getStabilityManager()));
                Trace.endSection();
                List<ListEntry> list13 = shadeListBuilder.mReadOnlyNotifList;
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeRenderList");
                for (int i14 = 0; i14 < shadeListBuilder.mOnBeforeRenderListListeners.size(); i14++) {
                    ((OnBeforeRenderListListener) shadeListBuilder.mOnBeforeRenderListListeners.get(i14)).onBeforeRenderList(list13);
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.onRenderList");
                ShadeListBuilder.OnRenderListListener onRenderListListener = shadeListBuilder.mOnRenderListListener;
                if (onRenderListListener != null) {
                    onRenderListListener.onRenderList(shadeListBuilder.mReadOnlyNotifList);
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.logEndBuildList");
                ShadeListBuilderLogger shadeListBuilderLogger = shadeListBuilder.mLogger;
                int i15 = shadeListBuilder.mIterationCount;
                int size = shadeListBuilder.mReadOnlyNotifList.size();
                List<ListEntry> list14 = shadeListBuilder.mReadOnlyNotifList;
                int i16 = 0;
                for (int i17 = 0; i17 < list14.size(); i17++) {
                    ListEntry listEntry5 = list14.get(i17);
                    if (listEntry5 instanceof GroupEntry) {
                        GroupEntry groupEntry7 = (GroupEntry) listEntry5;
                        Objects.requireNonNull(groupEntry7);
                        i16 += groupEntry7.mUnmodifiableChildren.size();
                    }
                }
                shadeListBuilderLogger.logEndBuildList(i15, size, i16);
                if (shadeListBuilder.mAlwaysLogList || shadeListBuilder.mIterationCount % 10 == 0) {
                    Trace.beginSection("ShadeListBuilder.logFinalList");
                    shadeListBuilder.mLogger.logFinalList(shadeListBuilder.mNotifList);
                    Trace.endSection();
                }
                Trace.endSection();
                PipelineState pipelineState2 = shadeListBuilder.mPipelineState;
                Objects.requireNonNull(pipelineState2);
                pipelineState2.mState = 0;
                shadeListBuilder.mIterationCount++;
                Trace.endSection();
                return;
            case 5:
                NotificationPanelViewController.OnOverscrollTopChangedListener onOverscrollTopChangedListener = (NotificationPanelViewController.OnOverscrollTopChangedListener) this.f$0;
                Objects.requireNonNull(onOverscrollTopChangedListener);
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                Objects.requireNonNull(notificationPanelViewController);
                notificationPanelViewController.mStackScrollerOverscrolling = false;
                C0961QS qs = notificationPanelViewController.mQs;
                if (qs != null) {
                    qs.setOverscrolling(false);
                }
                NotificationPanelViewController.this.updateQsState();
                return;
            case FalsingManager.VERSION:
                ScrimController scrimController = (ScrimController) this.f$0;
                boolean z3 = ScrimController.DEBUG;
                Objects.requireNonNull(scrimController);
                ScrimController.Callback callback2 = scrimController.mCallback;
                if (callback2 != null) {
                    callback2.onDisplayBlanked();
                    scrimController.mScreenBlankingCallbackCalled = true;
                }
                scrimController.mBlankingTransitionRunnable = new WMShell$7$$ExternalSyntheticLambda0(scrimController, 6);
                if (scrimController.mScreenOn) {
                    i = 32;
                } else {
                    i = 500;
                }
                if (ScrimController.DEBUG) {
                    ExifInterface$$ExternalSyntheticOutline1.m14m("Fading out scrims with delay: ", i, "ScrimController");
                }
                scrimController.mHandler.postDelayed(scrimController.mBlankingTransitionRunnable, (long) i);
                return;
            case 7:
                ((StatusBar) this.f$0).checkBarModes();
                return;
            default:
                TouchInsideHandler touchInsideHandler = (TouchInsideHandler) this.f$0;
                Objects.requireNonNull(touchInsideHandler);
                touchInsideHandler.mGuardLocked = false;
                return;
        }
    }
}
