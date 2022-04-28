package com.android.systemui.p006qs;

import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.UserAwareController;
import com.android.systemui.util.settings.SecureSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* renamed from: com.android.systemui.qs.AutoAddTracker */
/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker implements UserAwareController, Dumpable {
    public static final IntentFilter FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    public final ArraySet<String> autoAdded = new ArraySet<>();
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final AutoAddTracker$contentObserver$1 contentObserver;
    public final DumpManager dumpManager;
    public final QSHost qsHost;
    public final AutoAddTracker$restoreReceiver$1 restoreReceiver;
    public Set<String> restoredTiles;
    public final SecureSettings secureSettings;
    public int userId;

    /* renamed from: com.android.systemui.qs.AutoAddTracker$Builder */
    /* compiled from: AutoAddTracker.kt */
    public static final class Builder {
        public final BroadcastDispatcher broadcastDispatcher;
        public final DumpManager dumpManager;
        public final Executor executor;
        public final Handler handler;
        public final QSHost qsHost;
        public final SecureSettings secureSettings;
        public int userId;

        public Builder(SecureSettings secureSettings2, BroadcastDispatcher broadcastDispatcher2, QSHost qSHost, DumpManager dumpManager2, Handler handler2, Executor executor2) {
            this.secureSettings = secureSettings2;
            this.broadcastDispatcher = broadcastDispatcher2;
            this.qsHost = qSHost;
            this.dumpManager = dumpManager2;
            this.handler = handler2;
            this.executor = executor2;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("Current user: ", Integer.valueOf(this.userId)));
        printWriter.println(Intrinsics.stringPlus("Added tiles: ", this.autoAdded));
    }

    public final boolean isAdded(String str) {
        boolean contains;
        synchronized (this.autoAdded) {
            contains = this.autoAdded.contains(str);
        }
        return contains;
    }

    public final void loadTiles() {
        Collection collection;
        synchronized (this.autoAdded) {
            this.autoAdded.clear();
            ArraySet<String> arraySet = this.autoAdded;
            String stringForUser = this.secureSettings.getStringForUser("qs_auto_tiles", this.userId);
            if (stringForUser == null) {
                collection = null;
            } else {
                collection = StringsKt__StringsKt.split$default(stringForUser, new String[]{","});
            }
            if (collection == null) {
                collection = EmptySet.INSTANCE;
            }
            arraySet.addAll(collection);
        }
    }

    public final void setTileAdded(String str) {
        String str2;
        synchronized (this.autoAdded) {
            if (this.autoAdded.add(str)) {
                str2 = TextUtils.join(",", this.autoAdded);
            } else {
                str2 = null;
            }
        }
        if (str2 != null) {
            this.secureSettings.putStringForUser$1("qs_auto_tiles", str2, this.userId);
        }
    }

    public AutoAddTracker(SecureSettings secureSettings2, BroadcastDispatcher broadcastDispatcher2, QSHost qSHost, DumpManager dumpManager2, Handler handler, Executor executor, int i) {
        this.secureSettings = secureSettings2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.qsHost = qSHost;
        this.dumpManager = dumpManager2;
        this.backgroundExecutor = executor;
        this.userId = i;
        this.contentObserver = new AutoAddTracker$contentObserver$1(this, handler);
        this.restoreReceiver = new AutoAddTracker$restoreReceiver$1(this);
    }

    public final void changeUser(UserHandle userHandle) {
        if (userHandle.getIdentifier() != this.userId) {
            this.broadcastDispatcher.unregisterReceiver(this.restoreReceiver);
            this.userId = userHandle.getIdentifier();
            this.restoredTiles = null;
            loadTiles();
            BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.restoreReceiver, FILTER, this.backgroundExecutor, UserHandle.of(this.userId), 16);
        }
    }
}
