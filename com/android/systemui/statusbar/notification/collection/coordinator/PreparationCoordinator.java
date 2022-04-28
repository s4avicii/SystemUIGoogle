package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.phone.StatusBar$2$Callback$$ExternalSyntheticLambda0;
import com.google.android.systemui.lowlightclock.LowLightDockManager$$ExternalSyntheticLambda0;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

public final class PreparationCoordinator implements Coordinator {
    public final NotifUiAdjustmentProvider mAdjustmentProvider;
    public final BindEventManagerImpl mBindEventManager;
    public final int mChildBindCutoff;
    public final ArraySet<NotificationEntry> mInflatingNotifs = new ArraySet<>();
    public final ArrayMap<NotificationEntry, NotifUiAdjustment> mInflationAdjustments = new ArrayMap<>();
    public final C12744 mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() {
        public final void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) {
            NotifViewBarn notifViewBarn = PreparationCoordinator.this.mViewBarn;
            Objects.requireNonNull(notifViewBarn);
            notifViewBarn.rowMap.remove(notificationEntry.getKey());
            PreparationCoordinator.this.mInflationStates.put(notificationEntry, -1);
            try {
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                PreparationCoordinator.this.mStatusBarService.onNotificationError(statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), exc.getMessage(), statusBarNotification.getUser().getIdentifier());
            } catch (RemoteException unused) {
            }
            PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList();
        }

        public final void onNotifInflationErrorCleared() {
            PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList();
        }
    };
    public final ArrayMap<NotificationEntry, Integer> mInflationStates = new ArrayMap<>();
    public final PreparationCoordinatorLogger mLogger;
    public final long mMaxGroupInflationDelay;
    public final C12711 mNotifCollectionListener = new NotifCollectionListener() {
        public final void onEntryCleanUp(NotificationEntry notificationEntry) {
            PreparationCoordinator.this.mInflationStates.remove(notificationEntry);
            NotifViewBarn notifViewBarn = PreparationCoordinator.this.mViewBarn;
            Objects.requireNonNull(notifViewBarn);
            notifViewBarn.rowMap.remove(notificationEntry.getKey());
            PreparationCoordinator.this.mInflationAdjustments.remove(notificationEntry);
        }

        public final void onEntryInit(NotificationEntry notificationEntry) {
            PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
        }

        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
            preparationCoordinator.abortInflation(notificationEntry, "entryRemoved reason=" + i);
        }

        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            PreparationCoordinator.this.abortInflation(notificationEntry, "entryUpdated");
            int inflationState = PreparationCoordinator.this.getInflationState(notificationEntry);
            if (inflationState == 1) {
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, 2);
            } else if (inflationState == -1) {
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
            }
        }
    };
    public final NotifInflationErrorManager mNotifErrorManager;
    public final NotifInflater mNotifInflater;
    public final C12733 mNotifInflatingFilter = new NotifFilter() {
        public final ArrayMap mIsDelayedGroupCache = new ArrayMap();

        public final void onCleanup() {
            this.mIsDelayedGroupCache.clear();
        }

        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            boolean z2;
            boolean z3;
            GroupEntry parent = notificationEntry.getParent();
            Objects.requireNonNull(parent);
            Boolean bool = (Boolean) this.mIsDelayedGroupCache.get(parent);
            if (bool == null) {
                PreparationCoordinator preparationCoordinator = PreparationCoordinator.this;
                Objects.requireNonNull(preparationCoordinator);
                if (parent != GroupEntry.ROOT_ENTRY && !parent.wasAttachedInPreviousPass()) {
                    if (j - parent.mCreationTime > preparationCoordinator.mMaxGroupInflationDelay) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        PreparationCoordinatorLogger preparationCoordinatorLogger = preparationCoordinator.mLogger;
                        String str = parent.mKey;
                        Objects.requireNonNull(preparationCoordinatorLogger);
                        LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
                        LogLevel logLevel = LogLevel.WARNING;
                        PreparationCoordinatorLogger$logGroupInflationTookTooLong$2 preparationCoordinatorLogger$logGroupInflationTookTooLong$2 = PreparationCoordinatorLogger$logGroupInflationTookTooLong$2.INSTANCE;
                        Objects.requireNonNull(logBuffer);
                        if (!logBuffer.frozen) {
                            LogMessageImpl obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logGroupInflationTookTooLong$2);
                            obtain.str1 = str;
                            logBuffer.push(obtain);
                        }
                    } else {
                        if (preparationCoordinator.mInflatingNotifs.contains(parent.mSummary)) {
                            PreparationCoordinatorLogger preparationCoordinatorLogger2 = preparationCoordinator.mLogger;
                            String str2 = parent.mKey;
                            NotificationEntry notificationEntry2 = parent.mSummary;
                            Objects.requireNonNull(notificationEntry2);
                            preparationCoordinatorLogger2.logDelayingGroupRelease(str2, notificationEntry2.mKey);
                        } else {
                            for (NotificationEntry next : parent.mUnmodifiableChildren) {
                                if (preparationCoordinator.mInflatingNotifs.contains(next) && !next.wasAttachedInPreviousPass()) {
                                    preparationCoordinator.mLogger.logDelayingGroupRelease(parent.mKey, next.mKey);
                                }
                            }
                            PreparationCoordinatorLogger preparationCoordinatorLogger3 = preparationCoordinator.mLogger;
                            String str3 = parent.mKey;
                            Objects.requireNonNull(preparationCoordinatorLogger3);
                            LogBuffer logBuffer2 = preparationCoordinatorLogger3.buffer;
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 preparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 = PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2.INSTANCE;
                            Objects.requireNonNull(logBuffer2);
                            if (!logBuffer2.frozen) {
                                LogMessageImpl obtain2 = logBuffer2.obtain("PreparationCoordinator", logLevel2, preparationCoordinatorLogger$logDoneWaitingForGroupInflation$2);
                                obtain2.str1 = str3;
                                logBuffer2.push(obtain2);
                            }
                        }
                        z2 = true;
                        bool = Boolean.valueOf(z2);
                        this.mIsDelayedGroupCache.put(parent, bool);
                    }
                }
                z2 = false;
                bool = Boolean.valueOf(z2);
                this.mIsDelayedGroupCache.put(parent, bool);
            }
            PreparationCoordinator preparationCoordinator2 = PreparationCoordinator.this;
            Objects.requireNonNull(preparationCoordinator2);
            int inflationState = preparationCoordinator2.getInflationState(notificationEntry);
            if (inflationState == 1 || inflationState == 2) {
                z = true;
            } else {
                z = false;
            }
            if (!z || bool.booleanValue()) {
                return true;
            }
            return false;
        }
    };
    public final C12722 mNotifInflationErrorFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (PreparationCoordinator.this.getInflationState(notificationEntry) == -1) {
                return true;
            }
            return false;
        }
    };
    public final IStatusBarService mStatusBarService;
    public final NotifViewBarn mViewBarn;

    public final void abortInflation(NotificationEntry notificationEntry, String str) {
        PreparationCoordinatorLogger preparationCoordinatorLogger = this.mLogger;
        Objects.requireNonNull(notificationEntry);
        String str2 = notificationEntry.mKey;
        Objects.requireNonNull(preparationCoordinatorLogger);
        LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        PreparationCoordinatorLogger$logInflationAborted$2 preparationCoordinatorLogger$logInflationAborted$2 = PreparationCoordinatorLogger$logInflationAborted$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logInflationAborted$2);
            obtain.str1 = str2;
            obtain.str2 = str;
            logBuffer.push(obtain);
        }
        this.mNotifInflater.abortInflation(notificationEntry);
        this.mInflatingNotifs.remove(notificationEntry);
    }

    public final void attach(NotifPipeline notifPipeline) {
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        C12744 r1 = this.mInflationErrorListener;
        Objects.requireNonNull(notifInflationErrorManager);
        notifInflationErrorManager.mListeners.add(r1);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new PreparationCoordinator$$ExternalSyntheticLambda0(this));
        notifPipeline.addFinalizeFilter(this.mNotifInflationErrorFilter);
        notifPipeline.addFinalizeFilter(this.mNotifInflatingFilter);
    }

    public final int getInflationState(NotificationEntry notificationEntry) {
        Integer num = this.mInflationStates.get(notificationEntry);
        Objects.requireNonNull(num, "Asking state of a notification preparation coordinator doesn't know about");
        return num.intValue();
    }

    public final void inflateRequiredNotifViews(NotificationEntry notificationEntry) {
        boolean z;
        NotifUiAdjustmentProvider notifUiAdjustmentProvider = this.mAdjustmentProvider;
        Objects.requireNonNull(notifUiAdjustmentProvider);
        String str = notificationEntry.mKey;
        List<Notification.Action> smartActions = notificationEntry.mRanking.getSmartActions();
        List<CharSequence> smartReplies = notificationEntry.mRanking.getSmartReplies();
        boolean isConversation = notificationEntry.mRanking.isConversation();
        NotifSection section = notificationEntry.getSection();
        if (section != null) {
            GroupEntry parent = notificationEntry.getParent();
            if (parent != null) {
                SectionClassifier sectionClassifier = notifUiAdjustmentProvider.sectionClassifier;
                Objects.requireNonNull(sectionClassifier);
                Set<? extends NotifSectioner> set = sectionClassifier.lowPrioritySections;
                if (set == null) {
                    set = null;
                }
                boolean contains = set.contains(section.sectioner);
                boolean areEqual = Intrinsics.areEqual(parent, GroupEntry.ROOT_ENTRY);
                boolean areEqual2 = Intrinsics.areEqual(parent.mSummary, notificationEntry);
                if (!contains || (!areEqual && !areEqual2)) {
                    z = false;
                } else {
                    z = true;
                }
                NotifUiAdjustment notifUiAdjustment = new NotifUiAdjustment(smartActions, smartReplies, isConversation, z);
                if (!this.mInflatingNotifs.contains(notificationEntry)) {
                    int intValue = this.mInflationStates.get(notificationEntry).intValue();
                    if (intValue != -1) {
                        if (intValue == 0) {
                            inflateEntry(notificationEntry, notifUiAdjustment, "entryAdded");
                        } else if (intValue != 1) {
                            if (intValue == 2) {
                                this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
                                this.mInflatingNotifs.add(notificationEntry);
                                this.mNotifInflater.rebindViews(notificationEntry, new NotifInflater.Params(z), new LowLightDockManager$$ExternalSyntheticLambda0(this));
                            }
                        } else if (needToReinflate(notificationEntry, notifUiAdjustment, "Fully inflated notification has no adjustments")) {
                            this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
                            this.mInflatingNotifs.add(notificationEntry);
                            this.mNotifInflater.rebindViews(notificationEntry, new NotifInflater.Params(z), new LowLightDockManager$$ExternalSyntheticLambda0(this));
                        }
                    } else if (needToReinflate(notificationEntry, notifUiAdjustment, (String) null)) {
                        inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed after error");
                    }
                } else if (needToReinflate(notificationEntry, notifUiAdjustment, "Inflating notification has no adjustments")) {
                    inflateEntry(notificationEntry, notifUiAdjustment, "adjustment changed while inflating");
                }
            } else {
                throw new IllegalStateException("Entry must have a parent to determine if minimized".toString());
            }
        } else {
            throw new IllegalStateException("Entry must have a section to determine if minimized".toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x01b7  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01c8 A[EDGE_INSN: B:87:0x01c8->B:81:0x01c8 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01bf A[EDGE_INSN: B:92:0x01bf->B:76:0x01bf ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean needToReinflate(com.android.systemui.statusbar.notification.collection.NotificationEntry r13, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment r14, java.lang.String r15) {
        /*
            r12 = this;
            android.util.ArrayMap<com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment> r12 = r12.mInflationAdjustments
            java.lang.Object r12 = r12.get(r13)
            com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment r12 = (com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment) r12
            r13 = 1
            if (r12 != 0) goto L_0x0014
            if (r15 != 0) goto L_0x000e
            return r13
        L_0x000e:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            r12.<init>(r15)
            throw r12
        L_0x0014:
            r15 = 0
            if (r12 != r14) goto L_0x0019
            goto L_0x01d9
        L_0x0019:
            boolean r0 = r12.isConversation
            boolean r1 = r14.isConversation
            if (r0 == r1) goto L_0x0021
            goto L_0x01da
        L_0x0021:
            boolean r0 = r12.isMinimized
            boolean r1 = r14.isMinimized
            if (r0 == r1) goto L_0x0029
            goto L_0x01da
        L_0x0029:
            java.util.List<android.app.Notification$Action> r0 = r12.smartActions
            java.util.List<android.app.Notification$Action> r1 = r14.smartActions
            if (r0 != r1) goto L_0x0031
            goto L_0x01ca
        L_0x0031:
            int r2 = r0.size()
            int r3 = r1.size()
            if (r2 == r3) goto L_0x003d
            goto L_0x01c8
        L_0x003d:
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r2 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r2.<init>(r0)
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r0 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r0.<init>(r1)
            kotlin.sequences.MergingSequence r0 = kotlin.sequences.SequencesKt___SequencesKt.zip(r2, r0)
            kotlin.sequences.Sequence<T1> r1 = r0.sequence1
            java.util.Iterator r1 = r1.iterator()
            kotlin.sequences.Sequence<T2> r2 = r0.sequence2
            java.util.Iterator r2 = r2.iterator()
        L_0x0057:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0065
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0065
            r3 = r13
            goto L_0x0066
        L_0x0065:
            r3 = r15
        L_0x0066:
            if (r3 == 0) goto L_0x01ca
            kotlin.jvm.functions.Function2<T1, T2, V> r3 = r0.transform
            java.lang.Object r4 = r1.next()
            java.lang.Object r5 = r2.next()
            java.lang.Object r3 = r3.invoke(r4, r5)
            kotlin.Pair r3 = (kotlin.Pair) r3
            java.lang.Object r4 = r3.getFirst()
            android.app.Notification$Action r4 = (android.app.Notification.Action) r4
            java.lang.CharSequence r4 = r4.title
            java.lang.Object r5 = r3.getSecond()
            android.app.Notification$Action r5 = (android.app.Notification.Action) r5
            java.lang.CharSequence r5 = r5.title
            boolean r4 = android.text.TextUtils.equals(r4, r5)
            if (r4 == 0) goto L_0x01c5
            java.lang.Object r4 = r3.getFirst()
            android.app.Notification$Action r4 = (android.app.Notification.Action) r4
            android.graphics.drawable.Icon r4 = r4.getIcon()
            java.lang.Object r5 = r3.getSecond()
            android.app.Notification$Action r5 = (android.app.Notification.Action) r5
            android.graphics.drawable.Icon r5 = r5.getIcon()
            if (r4 != r5) goto L_0x00a5
            goto L_0x00b1
        L_0x00a5:
            if (r4 == 0) goto L_0x00b3
            if (r5 != 0) goto L_0x00aa
            goto L_0x00b3
        L_0x00aa:
            boolean r4 = r4.sameAs(r5)
            if (r4 != 0) goto L_0x00b1
            goto L_0x00b3
        L_0x00b1:
            r4 = r15
            goto L_0x00b4
        L_0x00b3:
            r4 = r13
        L_0x00b4:
            if (r4 != 0) goto L_0x01c5
            java.lang.Object r4 = r3.getFirst()
            android.app.Notification$Action r4 = (android.app.Notification.Action) r4
            android.app.PendingIntent r4 = r4.actionIntent
            java.lang.Object r5 = r3.getSecond()
            android.app.Notification$Action r5 = (android.app.Notification.Action) r5
            android.app.PendingIntent r5 = r5.actionIntent
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L_0x01c5
            java.lang.Object r4 = r3.getFirst()
            android.app.Notification$Action r4 = (android.app.Notification.Action) r4
            android.app.RemoteInput[] r4 = r4.getRemoteInputs()
            java.lang.Object r3 = r3.getSecond()
            android.app.Notification$Action r3 = (android.app.Notification.Action) r3
            android.app.RemoteInput[] r3 = r3.getRemoteInputs()
            if (r4 != r3) goto L_0x00e4
            goto L_0x01bd
        L_0x00e4:
            if (r4 == 0) goto L_0x01bf
            if (r3 != 0) goto L_0x00ea
            goto L_0x01bf
        L_0x00ea:
            int r5 = r4.length
            int r6 = r3.length
            if (r5 == r6) goto L_0x00f0
            goto L_0x01bf
        L_0x00f0:
            kotlin.sequences.Sequence r4 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r4)
            kotlin.sequences.Sequence r3 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r3)
            kotlin.sequences.MergingSequence r3 = kotlin.sequences.SequencesKt___SequencesKt.zip(r4, r3)
            kotlin.sequences.Sequence<T1> r4 = r3.sequence1
            java.util.Iterator r4 = r4.iterator()
            kotlin.sequences.Sequence<T2> r5 = r3.sequence2
            java.util.Iterator r5 = r5.iterator()
        L_0x0108:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x0116
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0116
            r6 = r13
            goto L_0x0117
        L_0x0116:
            r6 = r15
        L_0x0117:
            if (r6 == 0) goto L_0x01bd
            kotlin.jvm.functions.Function2<T1, T2, V> r6 = r3.transform
            java.lang.Object r7 = r4.next()
            java.lang.Object r8 = r5.next()
            java.lang.Object r6 = r6.invoke(r7, r8)
            kotlin.Pair r6 = (kotlin.Pair) r6
            java.lang.Object r7 = r6.getFirst()
            android.app.RemoteInput r7 = (android.app.RemoteInput) r7
            java.lang.CharSequence r7 = r7.getLabel()
            java.lang.Object r8 = r6.getSecond()
            android.app.RemoteInput r8 = (android.app.RemoteInput) r8
            java.lang.CharSequence r8 = r8.getLabel()
            boolean r7 = android.text.TextUtils.equals(r7, r8)
            if (r7 == 0) goto L_0x01b9
            java.lang.Object r7 = r6.getFirst()
            android.app.RemoteInput r7 = (android.app.RemoteInput) r7
            java.lang.CharSequence[] r7 = r7.getChoices()
            java.lang.Object r6 = r6.getSecond()
            android.app.RemoteInput r6 = (android.app.RemoteInput) r6
            java.lang.CharSequence[] r6 = r6.getChoices()
            if (r7 != r6) goto L_0x015a
            goto L_0x01b1
        L_0x015a:
            if (r7 == 0) goto L_0x01b3
            if (r6 != 0) goto L_0x015f
            goto L_0x01b3
        L_0x015f:
            int r8 = r7.length
            int r9 = r6.length
            if (r8 == r9) goto L_0x0164
            goto L_0x01b3
        L_0x0164:
            kotlin.sequences.Sequence r7 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r7)
            kotlin.sequences.Sequence r6 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r6)
            kotlin.sequences.MergingSequence r6 = kotlin.sequences.SequencesKt___SequencesKt.zip(r7, r6)
            kotlin.sequences.Sequence<T1> r7 = r6.sequence1
            java.util.Iterator r7 = r7.iterator()
            kotlin.sequences.Sequence<T2> r8 = r6.sequence2
            java.util.Iterator r8 = r8.iterator()
        L_0x017c:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x018a
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x018a
            r9 = r13
            goto L_0x018b
        L_0x018a:
            r9 = r15
        L_0x018b:
            if (r9 == 0) goto L_0x01b1
            kotlin.jvm.functions.Function2<T1, T2, V> r9 = r6.transform
            java.lang.Object r10 = r7.next()
            java.lang.Object r11 = r8.next()
            java.lang.Object r9 = r9.invoke(r10, r11)
            kotlin.Pair r9 = (kotlin.Pair) r9
            java.lang.Object r10 = r9.getFirst()
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10
            java.lang.Object r9 = r9.getSecond()
            java.lang.CharSequence r9 = (java.lang.CharSequence) r9
            boolean r9 = android.text.TextUtils.equals(r10, r9)
            r9 = r9 ^ r13
            if (r9 == 0) goto L_0x017c
            goto L_0x01b3
        L_0x01b1:
            r6 = r15
            goto L_0x01b4
        L_0x01b3:
            r6 = r13
        L_0x01b4:
            if (r6 == 0) goto L_0x01b7
            goto L_0x01b9
        L_0x01b7:
            r6 = r15
            goto L_0x01ba
        L_0x01b9:
            r6 = r13
        L_0x01ba:
            if (r6 == 0) goto L_0x0108
            goto L_0x01bf
        L_0x01bd:
            r3 = r15
            goto L_0x01c0
        L_0x01bf:
            r3 = r13
        L_0x01c0:
            if (r3 == 0) goto L_0x01c3
            goto L_0x01c5
        L_0x01c3:
            r3 = r15
            goto L_0x01c6
        L_0x01c5:
            r3 = r13
        L_0x01c6:
            if (r3 == 0) goto L_0x0057
        L_0x01c8:
            r0 = r13
            goto L_0x01cb
        L_0x01ca:
            r0 = r15
        L_0x01cb:
            if (r0 == 0) goto L_0x01ce
            goto L_0x01da
        L_0x01ce:
            java.util.List<java.lang.CharSequence> r14 = r14.smartReplies
            java.util.List<java.lang.CharSequence> r12 = r12.smartReplies
            boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual(r14, r12)
            if (r12 != 0) goto L_0x01d9
            goto L_0x01da
        L_0x01d9:
            r13 = r15
        L_0x01da:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.needToReinflate(com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment, java.lang.String):boolean");
    }

    public static void $r8$lambda$OBZgOZcphwYSTtPwW4dGUoKs3OA(PreparationCoordinator preparationCoordinator, NotificationEntry notificationEntry, ExpandableNotificationRowController expandableNotificationRowController) {
        Objects.requireNonNull(preparationCoordinator);
        PreparationCoordinatorLogger preparationCoordinatorLogger = preparationCoordinator.mLogger;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        Objects.requireNonNull(preparationCoordinatorLogger);
        LogBuffer logBuffer = preparationCoordinatorLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        PreparationCoordinatorLogger$logNotifInflated$2 preparationCoordinatorLogger$logNotifInflated$2 = PreparationCoordinatorLogger$logNotifInflated$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("PreparationCoordinator", logLevel, preparationCoordinatorLogger$logNotifInflated$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        preparationCoordinator.mInflatingNotifs.remove(notificationEntry);
        NotifViewBarn notifViewBarn = preparationCoordinator.mViewBarn;
        Objects.requireNonNull(notifViewBarn);
        notifViewBarn.rowMap.put(notificationEntry.mKey, expandableNotificationRowController);
        preparationCoordinator.mInflationStates.put(notificationEntry, 1);
        preparationCoordinator.mBindEventManager.notifyViewBound(notificationEntry);
        preparationCoordinator.mNotifInflatingFilter.invalidateList();
    }

    @VisibleForTesting
    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, int i, long j) {
        this.mLogger = preparationCoordinatorLogger;
        this.mNotifInflater = notifInflater;
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mViewBarn = notifViewBarn;
        this.mAdjustmentProvider = notifUiAdjustmentProvider;
        this.mStatusBarService = iStatusBarService;
        this.mChildBindCutoff = i;
        this.mMaxGroupInflationDelay = j;
        this.mBindEventManager = bindEventManagerImpl;
    }

    public final void inflateEntry(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        abortInflation(notificationEntry, str);
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        this.mNotifInflater.inflateViews(notificationEntry, new NotifInflater.Params(notifUiAdjustment.isMinimized), new StatusBar$2$Callback$$ExternalSyntheticLambda0(this));
    }
}
