package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BroadcastDispatcher.kt */
public final class BroadcastDispatcher implements Dumpable {
    public final Executor bgExecutor;
    public final Looper bgLooper;
    public final Context context;
    public final BroadcastDispatcher$handler$1 handler;
    public final BroadcastDispatcherLogger logger;
    public final SparseArray<UserBroadcastDispatcher> receiversByUser = new SparseArray<>(20);
    public final UserTracker userTracker;

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, (Executor) null, (UserHandle) null, 28);
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle) {
        registerReceiver$default(this, broadcastReceiver, intentFilter, executor, userHandle, 16);
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler2) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler2, (UserHandle) null, 24);
    }

    public final void registerReceiverWithHandler(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler2, UserHandle userHandle) {
        registerReceiverWithHandler$default(this, broadcastReceiver, intentFilter, handler2, userHandle, 16);
    }

    public static /* synthetic */ void registerReceiver$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i) {
        Executor executor2;
        UserHandle userHandle2;
        int i2;
        if ((i & 4) != 0) {
            executor2 = null;
        } else {
            executor2 = executor;
        }
        if ((i & 8) != 0) {
            userHandle2 = null;
        } else {
            userHandle2 = userHandle;
        }
        if ((i & 16) != 0) {
            i2 = 2;
        } else {
            i2 = 0;
        }
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, executor2, userHandle2, i2);
    }

    public static void registerReceiverWithHandler$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Handler handler2, UserHandle userHandle, int i) {
        int i2;
        if ((i & 8) != 0) {
            userHandle = broadcastDispatcher.context.getUser();
        }
        UserHandle userHandle2 = userHandle;
        if ((i & 16) != 0) {
            i2 = 2;
        } else {
            i2 = 0;
        }
        Objects.requireNonNull(broadcastDispatcher);
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        BroadcastReceiver broadcastReceiver2 = broadcastReceiver;
        IntentFilter intentFilter2 = intentFilter;
        broadcastDispatcher2.registerReceiver(broadcastReceiver2, intentFilter2, new HandlerExecutor(handler2), userHandle2, i2);
    }

    @VisibleForTesting
    public UserBroadcastDispatcher createUBRForUser(int i) {
        return new UserBroadcastDispatcher(this.context, i, this.bgLooper, this.bgExecutor, this.logger);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        printWriter.println("Broadcast dispatcher:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        int size = this.receiversByUser.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            indentingPrintWriter.println(Intrinsics.stringPlus("User ", Integer.valueOf(this.receiversByUser.keyAt(i))));
            UserBroadcastDispatcher valueAt = this.receiversByUser.valueAt(i);
            Objects.requireNonNull(valueAt);
            indentingPrintWriter.increaseIndent();
            for (Map.Entry next : valueAt.actionsToActionsReceivers.entrySet()) {
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
                indentingPrintWriter.println(m.toString());
                Objects.requireNonNull(actionReceiver);
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(Intrinsics.stringPlus("Registered: ", Boolean.valueOf(actionReceiver.registered)));
                indentingPrintWriter.println("Receivers:");
                indentingPrintWriter.increaseIndent();
                Iterator<ReceiverData> it = actionReceiver.receiverDatas.iterator();
                while (it.hasNext()) {
                    ReceiverData next2 = it.next();
                    Objects.requireNonNull(next2);
                    indentingPrintWriter.println(next2.receiver);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println(Intrinsics.stringPlus("Categories: ", CollectionsKt___CollectionsKt.joinToString$default(actionReceiver.activeCategories, ", ", (String) null, (String) null, (Function1) null, 62)));
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
            i = i2;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i) {
        StringBuilder sb = new StringBuilder();
        if (intentFilter.countActions() == 0) {
            sb.append("Filter must contain at least one action. ");
        }
        if (intentFilter.countDataAuthorities() != 0) {
            sb.append("Filter cannot contain DataAuthorities. ");
        }
        if (intentFilter.countDataPaths() != 0) {
            sb.append("Filter cannot contain DataPaths. ");
        }
        if (intentFilter.countDataSchemes() != 0) {
            sb.append("Filter cannot contain DataSchemes. ");
        }
        if (intentFilter.countDataTypes() != 0) {
            sb.append("Filter cannot contain DataTypes. ");
        }
        if (intentFilter.getPriority() != 0) {
            sb.append("Filter cannot modify priority. ");
        }
        if (TextUtils.isEmpty(sb)) {
            if (executor == null) {
                executor = this.context.getMainExecutor();
            }
            if (userHandle == null) {
                userHandle = this.context.getUser();
            }
            this.handler.obtainMessage(0, i, 0, new ReceiverData(broadcastReceiver, intentFilter, executor, userHandle)).sendToTarget();
            return;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public final void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.handler.obtainMessage(1, broadcastReceiver).sendToTarget();
    }

    public BroadcastDispatcher(Context context2, Looper looper, Executor executor, DumpManager dumpManager, BroadcastDispatcherLogger broadcastDispatcherLogger, UserTracker userTracker2) {
        this.context = context2;
        this.bgLooper = looper;
        this.bgExecutor = executor;
        this.logger = broadcastDispatcherLogger;
        this.userTracker = userTracker2;
        this.handler = new BroadcastDispatcher$handler$1(this, looper);
    }
}
