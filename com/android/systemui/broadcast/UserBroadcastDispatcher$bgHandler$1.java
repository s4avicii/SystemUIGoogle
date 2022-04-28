package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__ReversedViewsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: UserBroadcastDispatcher.kt */
public final class UserBroadcastDispatcher$bgHandler$1 extends Handler {
    public final /* synthetic */ UserBroadcastDispatcher this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UserBroadcastDispatcher$bgHandler$1(UserBroadcastDispatcher userBroadcastDispatcher, Looper looper) {
        super(looper);
        this.this$0 = userBroadcastDispatcher;
    }

    public final void handleMessage(Message message) {
        Sequence<T> sequence;
        Sequence<T> sequence2;
        int i = message.what;
        if (i == 0) {
            UserBroadcastDispatcher userBroadcastDispatcher = this.this$0;
            Object obj = message.obj;
            Objects.requireNonNull(obj, "null cannot be cast to non-null type com.android.systemui.broadcast.ReceiverData");
            ReceiverData receiverData = (ReceiverData) obj;
            int i2 = message.arg1;
            Objects.requireNonNull(userBroadcastDispatcher);
            Preconditions.checkState(userBroadcastDispatcher.bgHandler.getLooper().isCurrentThread(), "This method should only be called from BG thread");
            ArrayMap<BroadcastReceiver, Set<String>> arrayMap = userBroadcastDispatcher.receiverToActions;
            BroadcastReceiver broadcastReceiver = receiverData.receiver;
            Set<String> set = arrayMap.get(broadcastReceiver);
            if (set == null) {
                set = new ArraySet<>();
                arrayMap.put(broadcastReceiver, set);
            }
            Collection collection = set;
            Iterator<String> actionsIterator = receiverData.filter.actionsIterator();
            if (actionsIterator == null) {
                sequence = null;
            } else {
                sequence = SequencesKt__SequencesKt.asSequence(actionsIterator);
            }
            if (sequence == null) {
                sequence = EmptySequence.INSTANCE;
            }
            CollectionsKt__ReversedViewsKt.addAll(collection, (Sequence) sequence);
            Iterator<String> actionsIterator2 = receiverData.filter.actionsIterator();
            while (actionsIterator2.hasNext()) {
                String next = actionsIterator2.next();
                ArrayMap<Pair<String, Integer>, ActionReceiver> arrayMap2 = userBroadcastDispatcher.actionsToActionsReceivers;
                Pair pair = new Pair(next, Integer.valueOf(i2));
                ActionReceiver actionReceiver = arrayMap2.get(pair);
                if (actionReceiver == null) {
                    actionReceiver = userBroadcastDispatcher.mo7360xe87e3b27(next, i2);
                    arrayMap2.put(pair, actionReceiver);
                }
                ActionReceiver actionReceiver2 = actionReceiver;
                Objects.requireNonNull(actionReceiver2);
                if (receiverData.filter.hasAction(actionReceiver2.action)) {
                    ArraySet<String> arraySet = actionReceiver2.activeCategories;
                    Iterator<String> categoriesIterator = receiverData.filter.categoriesIterator();
                    if (categoriesIterator == null) {
                        sequence2 = null;
                    } else {
                        sequence2 = SequencesKt__SequencesKt.asSequence(categoriesIterator);
                    }
                    if (sequence2 == null) {
                        sequence2 = EmptySequence.INSTANCE;
                    }
                    boolean addAll = CollectionsKt__ReversedViewsKt.addAll((Collection) arraySet, (Sequence) sequence2);
                    if (actionReceiver2.receiverDatas.add(receiverData) && actionReceiver2.receiverDatas.size() == 1) {
                        actionReceiver2.registerAction.invoke(actionReceiver2, actionReceiver2.createFilter());
                        actionReceiver2.registered = true;
                    } else if (addAll) {
                        actionReceiver2.unregisterAction.invoke(actionReceiver2);
                        actionReceiver2.registerAction.invoke(actionReceiver2, actionReceiver2.createFilter());
                    }
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Trying to attach to ");
                    m.append(actionReceiver2.action);
                    m.append(" without correct action,receiver: ");
                    m.append(receiverData.receiver);
                    throw new IllegalArgumentException(m.toString());
                }
            }
            userBroadcastDispatcher.logger.logReceiverRegistered(userBroadcastDispatcher.userId, receiverData.receiver, i2);
        } else if (i == 1) {
            UserBroadcastDispatcher userBroadcastDispatcher2 = this.this$0;
            Object obj2 = message.obj;
            Objects.requireNonNull(obj2, "null cannot be cast to non-null type android.content.BroadcastReceiver");
            BroadcastReceiver broadcastReceiver2 = (BroadcastReceiver) obj2;
            Objects.requireNonNull(userBroadcastDispatcher2);
            Preconditions.checkState(userBroadcastDispatcher2.bgHandler.getLooper().isCurrentThread(), "This method should only be called from BG thread");
            for (String str : userBroadcastDispatcher2.receiverToActions.getOrDefault(broadcastReceiver2, new LinkedHashSet())) {
                for (Map.Entry next2 : userBroadcastDispatcher2.actionsToActionsReceivers.entrySet()) {
                    ActionReceiver actionReceiver3 = (ActionReceiver) next2.getValue();
                    if (Intrinsics.areEqual(((Pair) next2.getKey()).getFirst(), str)) {
                        Objects.requireNonNull(actionReceiver3);
                        if (CollectionsKt__ReversedViewsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(actionReceiver3.receiverDatas, new ActionReceiver$removeReceiver$1(broadcastReceiver2)) && actionReceiver3.receiverDatas.isEmpty() && actionReceiver3.registered) {
                            actionReceiver3.unregisterAction.invoke(actionReceiver3);
                            actionReceiver3.registered = false;
                            actionReceiver3.activeCategories.clear();
                        }
                    }
                }
            }
            userBroadcastDispatcher2.receiverToActions.remove(broadcastReceiver2);
            userBroadcastDispatcher2.logger.logReceiverUnregistered(userBroadcastDispatcher2.userId, broadcastReceiver2);
        }
    }
}
