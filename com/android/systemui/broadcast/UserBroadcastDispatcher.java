package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserBroadcastDispatcher.kt */
public final class UserBroadcastDispatcher implements Dumpable {
    public final ArrayMap<Pair<String, Integer>, ActionReceiver> actionsToActionsReceivers = new ArrayMap<>();
    public final Executor bgExecutor;
    public final UserBroadcastDispatcher$bgHandler$1 bgHandler;
    public final Context context;
    public final BroadcastDispatcherLogger logger;
    public final ArrayMap<BroadcastReceiver, Set<String>> receiverToActions = new ArrayMap<>();
    public final int userId;

    /* renamed from: getActionsToActionsReceivers$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m34xcb79c6f() {
    }

    static {
        new AtomicInteger(0);
    }

    /* renamed from: createActionReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public ActionReceiver mo7360xe87e3b27(String str, int i) {
        return new ActionReceiver(str, this.userId, new UserBroadcastDispatcher$createActionReceiver$1(this, i), new UserBroadcastDispatcher$createActionReceiver$2(this, str), this.bgExecutor, this.logger);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        boolean z2 = printWriter instanceof IndentingPrintWriter;
        if (z2) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        for (Map.Entry next : this.actionsToActionsReceivers.entrySet()) {
            Pair pair = (Pair) next.getKey();
            ActionReceiver actionReceiver = (ActionReceiver) next.getValue();
            StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('(');
            m.append((String) pair.getFirst());
            m.append(": ");
            int intValue = ((Number) pair.getSecond()).intValue();
            StringBuilder sb = new StringBuilder("");
            if ((intValue & 1) != 0) {
                sb.append("instant_apps,");
            }
            if ((intValue & 4) != 0) {
                sb.append("not_exported,");
            }
            if ((intValue & 2) != 0) {
                sb.append("exported");
            }
            if (sb.length() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                sb.append(intValue);
            }
            m.append(sb.toString());
            m.append("):");
            printWriter.println(m.toString());
            Objects.requireNonNull(actionReceiver);
            boolean z3 = printWriter instanceof IndentingPrintWriter;
            if (z3) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            printWriter.println(Intrinsics.stringPlus("Registered: ", Boolean.valueOf(actionReceiver.registered)));
            printWriter.println("Receivers:");
            if (z3) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            Iterator<ReceiverData> it = actionReceiver.receiverDatas.iterator();
            while (it.hasNext()) {
                ReceiverData next2 = it.next();
                Objects.requireNonNull(next2);
                printWriter.println(next2.receiver);
            }
            if (z3) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println(Intrinsics.stringPlus("Categories: ", CollectionsKt___CollectionsKt.joinToString$default(actionReceiver.activeCategories, ", ", (String) null, (String) null, (Function1) null, 62)));
            if (z3) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
        }
        if (z2) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050 A[SYNTHETIC] */
    /* renamed from: isReceiverReferenceHeld$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean mo7361xbd20662d(android.content.BroadcastReceiver r6) {
        /*
            r5 = this;
            android.util.ArrayMap<kotlin.Pair<java.lang.String, java.lang.Integer>, com.android.systemui.broadcast.ActionReceiver> r0 = r5.actionsToActionsReceivers
            java.util.Collection r0 = r0.values()
            boolean r1 = r0.isEmpty()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0010
        L_0x000e:
            r0 = r3
            goto L_0x0051
        L_0x0010:
            java.util.Iterator r0 = r0.iterator()
        L_0x0014:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x000e
            java.lang.Object r1 = r0.next()
            com.android.systemui.broadcast.ActionReceiver r1 = (com.android.systemui.broadcast.ActionReceiver) r1
            java.util.Objects.requireNonNull(r1)
            android.util.ArraySet<com.android.systemui.broadcast.ReceiverData> r1 = r1.receiverDatas
            boolean r4 = r1 instanceof java.util.Collection
            if (r4 == 0) goto L_0x0030
            boolean r4 = r1.isEmpty()
            if (r4 == 0) goto L_0x0030
            goto L_0x004d
        L_0x0030:
            java.util.Iterator r1 = r1.iterator()
        L_0x0034:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x004d
            java.lang.Object r4 = r1.next()
            com.android.systemui.broadcast.ReceiverData r4 = (com.android.systemui.broadcast.ReceiverData) r4
            java.util.Objects.requireNonNull(r4)
            android.content.BroadcastReceiver r4 = r4.receiver
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r6)
            if (r4 == 0) goto L_0x0034
            r1 = r2
            goto L_0x004e
        L_0x004d:
            r1 = r3
        L_0x004e:
            if (r1 == 0) goto L_0x0014
            r0 = r2
        L_0x0051:
            if (r0 != 0) goto L_0x005d
            android.util.ArrayMap<android.content.BroadcastReceiver, java.util.Set<java.lang.String>> r5 = r5.receiverToActions
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x005c
            goto L_0x005d
        L_0x005c:
            r2 = r3
        L_0x005d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.broadcast.UserBroadcastDispatcher.mo7361xbd20662d(android.content.BroadcastReceiver):boolean");
    }

    public UserBroadcastDispatcher(Context context2, int i, Looper looper, Executor executor, BroadcastDispatcherLogger broadcastDispatcherLogger) {
        this.context = context2;
        this.userId = i;
        this.bgExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.bgHandler = new UserBroadcastDispatcher$bgHandler$1(this, looper);
    }
}
