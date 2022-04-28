package com.android.systemui.settings;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* compiled from: UserTrackerImpl.kt */
public final class UserTrackerImpl extends BroadcastReceiver implements UserTracker, Dumpable {
    public static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    public final Handler backgroundHandler;
    public final ArrayList callbacks;
    public final Context context;
    public final DumpManager dumpManager;
    public boolean initialized;
    public final Object mutex = new Object();
    public final SynchronizedDelegate userContext$delegate;
    public final SynchronizedDelegate userHandle$delegate;
    public final SynchronizedDelegate userId$delegate;
    public final UserManager userManager;
    public final SynchronizedDelegate userProfiles$delegate;

    /* compiled from: UserTrackerImpl.kt */
    public static final class SynchronizedDelegate<T> {
        public T value;

        public final T getValue(UserTrackerImpl userTrackerImpl, KProperty<?> kProperty) {
            T t;
            if (userTrackerImpl.initialized) {
                synchronized (userTrackerImpl.mutex) {
                    t = this.value;
                }
                return t;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Must initialize before getting ", kProperty.getName()));
        }

        public SynchronizedDelegate(T t) {
            this.value = t;
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(UserTrackerImpl.class, "userId", "getUserId()I");
        Objects.requireNonNull(Reflection.factory);
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, new MutablePropertyReference1Impl(UserTrackerImpl.class, "userHandle", "getUserHandle()Landroid/os/UserHandle;"), new MutablePropertyReference1Impl(UserTrackerImpl.class, "userContext", "getUserContext()Landroid/content/Context;"), new MutablePropertyReference1Impl(UserTrackerImpl.class, "userProfiles", "getUserProfiles()Ljava/util/List;")};
    }

    public final void addCallback(UserTracker.Callback callback, Executor executor) {
        synchronized (this.callbacks) {
            this.callbacks.add(new DataItem(new WeakReference(callback), executor));
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        List<DataItem> list;
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.initialized, "Initialized: ", printWriter);
        if (this.initialized) {
            printWriter.println(Intrinsics.stringPlus("userId: ", Integer.valueOf(getUserId())));
            List<UserInfo> userProfiles = getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(userProfiles, 10));
            for (UserInfo userInfo : userProfiles) {
                arrayList.add(Integer.valueOf(userInfo.id));
            }
            printWriter.println(Intrinsics.stringPlus("userProfiles: ", arrayList));
        }
        synchronized (this.callbacks) {
            list = CollectionsKt___CollectionsKt.toList(this.callbacks);
        }
        printWriter.println("Callbacks:");
        for (DataItem dataItem : list) {
            Objects.requireNonNull(dataItem);
            UserTracker.Callback callback = dataItem.callback.get();
            if (callback != null) {
                printWriter.println(Intrinsics.stringPlus("  ", callback));
            }
        }
    }

    public final Context getUserContext() {
        return (Context) this.userContext$delegate.getValue(this, $$delegatedProperties[2]);
    }

    public final UserHandle getUserHandle() {
        return (UserHandle) this.userHandle$delegate.getValue(this, $$delegatedProperties[1]);
    }

    public final int getUserId() {
        return ((Number) this.userId$delegate.getValue(this, $$delegatedProperties[0])).intValue();
    }

    public final List<UserInfo> getUserProfiles() {
        return (List) this.userProfiles$delegate.getValue(this, $$delegatedProperties[3]);
    }

    public final void removeCallback(UserTracker.Callback callback) {
        synchronized (this.callbacks) {
            this.callbacks.removeIf(new UserTrackerImpl$removeCallback$1$1(callback));
        }
    }

    public final Pair<Context, List<UserInfo>> setUserIdInternal(int i) {
        List<UserInfo> profiles = this.userManager.getProfiles(i);
        T userHandle = new UserHandle(i);
        T createContextAsUser = this.context.createContextAsUser(userHandle, 0);
        synchronized (this.mutex) {
            SynchronizedDelegate synchronizedDelegate = this.userId$delegate;
            KProperty<Object>[] kPropertyArr = $$delegatedProperties;
            KProperty<Object> kProperty = kPropertyArr[0];
            T valueOf = Integer.valueOf(i);
            Objects.requireNonNull(synchronizedDelegate);
            synchronized (this.mutex) {
                synchronizedDelegate.value = valueOf;
            }
            SynchronizedDelegate synchronizedDelegate2 = this.userHandle$delegate;
            KProperty<Object> kProperty2 = kPropertyArr[1];
            Objects.requireNonNull(synchronizedDelegate2);
            synchronized (this.mutex) {
                synchronizedDelegate2.value = userHandle;
            }
            SynchronizedDelegate synchronizedDelegate3 = this.userContext$delegate;
            KProperty<Object> kProperty3 = kPropertyArr[2];
            Objects.requireNonNull(synchronizedDelegate3);
            synchronized (this.mutex) {
                synchronizedDelegate3.value = createContextAsUser;
            }
            T arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(profiles, 10));
            for (UserInfo userInfo : profiles) {
                arrayList.add(new UserInfo(userInfo));
            }
            SynchronizedDelegate synchronizedDelegate4 = this.userProfiles$delegate;
            KProperty<Object> kProperty4 = $$delegatedProperties[3];
            Objects.requireNonNull(synchronizedDelegate4);
            synchronized (this.mutex) {
                synchronizedDelegate4.value = arrayList;
            }
        }
        return new Pair<>(createContextAsUser, profiles);
    }

    public UserTrackerImpl(Context context2, UserManager userManager2, DumpManager dumpManager2, Handler handler) {
        this.context = context2;
        this.userManager = userManager2;
        this.dumpManager = dumpManager2;
        this.backgroundHandler = handler;
        this.userId$delegate = new SynchronizedDelegate(Integer.valueOf(context2.getUserId()));
        this.userHandle$delegate = new SynchronizedDelegate(context2.getUser());
        this.userContext$delegate = new SynchronizedDelegate(context2);
        this.userProfiles$delegate = new SynchronizedDelegate(EmptyList.INSTANCE);
        this.callbacks = new ArrayList();
    }

    public final ContentResolver getUserContentResolver() {
        return getUserContext().getContentResolver();
    }

    public final UserInfo getUserInfo() {
        boolean z;
        int userId = getUserId();
        for (UserInfo userInfo : getUserProfiles()) {
            if (userInfo.id == userId) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return userInfo;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public final void onReceive(Context context2, Intent intent) {
        List<DataItem> list;
        List<DataItem> list2;
        String action = intent.getAction();
        if (action != null) {
            int hashCode = action.hashCode();
            if (hashCode != -1238404651) {
                if (hashCode != -864107122) {
                    if (hashCode == 959232034 && action.equals("android.intent.action.USER_SWITCHED")) {
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        Assert.isNotMainThread();
                        if (intExtra == -10000) {
                            Log.w("UserTrackerImpl", "handleSwitchUser - Couldn't get new id from intent");
                            return;
                        } else if (intExtra != getUserId()) {
                            Log.i("UserTrackerImpl", Intrinsics.stringPlus("Switching to user ", Integer.valueOf(intExtra)));
                            Pair<Context, List<UserInfo>> userIdInternal = setUserIdInternal(intExtra);
                            Context component1 = userIdInternal.component1();
                            List component2 = userIdInternal.component2();
                            synchronized (this.callbacks) {
                                list2 = CollectionsKt___CollectionsKt.toList(this.callbacks);
                            }
                            for (DataItem dataItem : list2) {
                                Objects.requireNonNull(dataItem);
                                if (dataItem.callback.get() != null) {
                                    dataItem.executor.execute(new UserTrackerImpl$handleSwitchUser$$inlined$notifySubscribers$1(dataItem, intExtra, component1, component2));
                                }
                            }
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else if (!action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                    return;
                }
            } else if (!action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                return;
            }
            Assert.isNotMainThread();
            List<UserInfo> profiles = this.userManager.getProfiles(getUserId());
            synchronized (this.mutex) {
                T arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(profiles, 10));
                for (UserInfo userInfo : profiles) {
                    arrayList.add(new UserInfo(userInfo));
                }
                SynchronizedDelegate synchronizedDelegate = this.userProfiles$delegate;
                KProperty<Object> kProperty = $$delegatedProperties[3];
                Objects.requireNonNull(synchronizedDelegate);
                synchronized (this.mutex) {
                    synchronizedDelegate.value = arrayList;
                }
            }
            synchronized (this.callbacks) {
                list = CollectionsKt___CollectionsKt.toList(this.callbacks);
            }
            for (DataItem dataItem2 : list) {
                Objects.requireNonNull(dataItem2);
                if (dataItem2.callback.get() != null) {
                    dataItem2.executor.execute(new C1097xc58954eb(dataItem2, profiles));
                }
            }
        }
    }
}
