package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Function;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager {
    public final Context context;
    public final NotifPipelineFlags featureFlags;
    public final Handler mainHandler;
    public final CommonNotifCollection notifCollection;
    public boolean notifPanelCollapsed = true;
    public final NotificationGroupManagerLegacy notificationGroupManager;
    public final ConcurrentHashMap<String, ConversationState> states = new ConcurrentHashMap<>();

    /* compiled from: ConversationNotifications.kt */
    public static final class ConversationState {
        public final Notification notification;
        public final int unreadCount;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationState)) {
                return false;
            }
            ConversationState conversationState = (ConversationState) obj;
            return this.unreadCount == conversationState.unreadCount && Intrinsics.areEqual(this.notification, conversationState.notification);
        }

        public final int hashCode() {
            return this.notification.hashCode() + (Integer.hashCode(this.unreadCount) * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ConversationState(unreadCount=");
            m.append(this.unreadCount);
            m.append(", notification=");
            m.append(this.notification);
            m.append(')');
            return m.toString();
        }

        public ConversationState(int i, Notification notification2) {
            this.unreadCount = i;
            this.notification = notification2;
        }
    }

    public static final void onEntryViewBound$updateCount(ConversationNotificationManager conversationNotificationManager, NotificationEntry notificationEntry, boolean z) {
        boolean z2;
        if (z) {
            if (conversationNotificationManager.notifPanelCollapsed) {
                Objects.requireNonNull(notificationEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                boolean z3 = false;
                if (expandableNotificationRow != null) {
                    if (!expandableNotificationRow.mIsPinned) {
                        z2 = false;
                    } else {
                        z2 = expandableNotificationRow.mExpandedWhenPinned;
                    }
                    if (z2) {
                        z3 = true;
                    }
                }
                if (!z3) {
                    return;
                }
            }
            Objects.requireNonNull(notificationEntry);
            conversationNotificationManager.states.compute(notificationEntry.mKey, ConversationNotificationManager$resetCount$1.INSTANCE);
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                resetBadgeUi(expandableNotificationRow2);
            }
        }
    }

    public final void onNotificationPanelExpandStateChanged(boolean z) {
        this.notifPanelCollapsed = z;
        if (!z) {
            FilteringSequence mapNotNull = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.states.entrySet()), new C1229x7388b338(this));
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(mapNotNull);
            while (filteringSequence$iterator$1.hasNext()) {
                Pair pair = (Pair) filteringSequence$iterator$1.next();
                linkedHashMap.put(pair.component1(), pair.component2());
            }
            Map optimizeReadOnlyMap = MapsKt___MapsKt.optimizeReadOnlyMap(linkedHashMap);
            this.states.replaceAll(new C1227x5e24d3bf(optimizeReadOnlyMap));
            FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(optimizeReadOnlyMap.values()), C1228x5e24d3c0.INSTANCE));
            while (filteringSequence$iterator$12.hasNext()) {
                resetBadgeUi((ExpandableNotificationRow) filteringSequence$iterator$12.next());
            }
        }
    }

    public ConversationNotificationManager(BindEventManager bindEventManager, NotificationGroupManagerLegacy notificationGroupManagerLegacy, Context context2, CommonNotifCollection commonNotifCollection, NotifPipelineFlags notifPipelineFlags, Handler handler) {
        this.notificationGroupManager = notificationGroupManagerLegacy;
        this.context = context2;
        this.notifCollection = commonNotifCollection;
        this.featureFlags = notifPipelineFlags;
        this.mainHandler = handler;
        commonNotifCollection.addCollectionListener(new NotifCollectionListener(this) {
            public final /* synthetic */ ConversationNotificationManager this$0;

            {
                this.this$0 = r1;
            }

            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                ConversationNotificationManager conversationNotificationManager = this.this$0;
                Objects.requireNonNull(conversationNotificationManager);
                conversationNotificationManager.states.remove(notificationEntry.mKey);
            }

            public final void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                ConversationNotificationManager conversationNotificationManager = this.this$0;
                Objects.requireNonNull(conversationNotificationManager);
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(conversationNotificationManager.states.keySet()), new C1230x2916e3cd(conversationNotificationManager)));
                while (filteringSequence$iterator$1.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
                    Objects.requireNonNull(notificationEntry);
                    if (rankingMap.getRanking(notificationEntry.mSbn.getKey(), ranking) && ranking.isConversation()) {
                        boolean isImportantConversation = ranking.getChannel().isImportantConversation();
                        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                        boolean z = false;
                        if (expandableNotificationRow != null) {
                            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
                            NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
                            if (notificationContentViewArr2 != null) {
                                FilteringSequence$iterator$1 filteringSequence$iterator$12 = new FilteringSequence$iterator$1(new FilteringSequence(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(ArraysKt___ArraysKt.asSequence(notificationContentViewArr2), ConversationNotificationManager$updateNotificationRanking$1.INSTANCE), ConversationNotificationManager$updateNotificationRanking$2.INSTANCE), false, new ConversationNotificationManager$updateNotificationRanking$3(isImportantConversation)));
                                boolean z2 = false;
                                while (filteringSequence$iterator$12.hasNext()) {
                                    ConversationLayout conversationLayout = (ConversationLayout) filteringSequence$iterator$12.next();
                                    if (!isImportantConversation || !notificationEntry.mIsMarkedForUserTriggeredMovement) {
                                        conversationLayout.setIsImportantConversation(isImportantConversation, false);
                                    } else {
                                        conversationNotificationManager.mainHandler.postDelayed(new ConversationNotificationManager$updateNotificationRanking$4$1(conversationLayout, isImportantConversation), 960);
                                    }
                                    z2 = true;
                                }
                                z = z2;
                            }
                        }
                        if (z && !conversationNotificationManager.featureFlags.isNewPipelineEnabled()) {
                            conversationNotificationManager.notificationGroupManager.updateIsolation(notificationEntry);
                        }
                    }
                }
            }
        });
        C12252 r2 = new Object() {
            public final boolean equals(Object obj) {
                if (!(obj instanceof BindEventManager.Listener) || !(obj instanceof FunctionAdapter)) {
                    return false;
                }
                return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
            }

            public final Function<?> getFunctionDelegate() {
                return new FunctionReferenceImpl(1, ConversationNotificationManager.this, ConversationNotificationManager.class, "onEntryViewBound", "onEntryViewBound(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)V", 0);
            }

            public final int hashCode() {
                return getFunctionDelegate().hashCode();
            }

            public final void onViewBound(NotificationEntry notificationEntry) {
                ConversationNotificationManager conversationNotificationManager = ConversationNotificationManager.this;
                Objects.requireNonNull(conversationNotificationManager);
                if (notificationEntry.mRanking.isConversation()) {
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.mExpansionChangedListener = new ConversationNotificationManager$onEntryViewBound$1(notificationEntry, conversationNotificationManager);
                    }
                    boolean z = true;
                    if (expandableNotificationRow == null || !expandableNotificationRow.isExpanded(false)) {
                        z = false;
                    }
                    ConversationNotificationManager.onEntryViewBound$updateCount(conversationNotificationManager, notificationEntry, z);
                }
            }
        };
        Objects.requireNonNull(bindEventManager);
        bindEventManager.listeners.addIfAbsent(r2);
    }

    public static void resetBadgeUi(ExpandableNotificationRow expandableNotificationRow) {
        Sequence sequence;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
        NotificationContentView[] notificationContentViewArr2 = (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
        if (notificationContentViewArr2 == null) {
            sequence = null;
        } else {
            sequence = ArraysKt___ArraysKt.asSequence(notificationContentViewArr2);
        }
        if (sequence == null) {
            sequence = EmptySequence.INSTANCE;
        }
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(sequence, ConversationNotificationManager$resetBadgeUi$1.INSTANCE), ConversationNotificationManager$resetBadgeUi$2.INSTANCE));
        while (filteringSequence$iterator$1.hasNext()) {
            ((ConversationLayout) filteringSequence$iterator$1.next()).setUnreadCount(0);
        }
    }
}
