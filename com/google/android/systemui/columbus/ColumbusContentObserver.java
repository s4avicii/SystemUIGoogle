package com.google.android.systemui.columbus;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: ColumbusContentObserver.kt */
public final class ColumbusContentObserver extends ContentObserver {
    public final Function1<Uri, Unit> callback;
    public final ContentResolverWrapper contentResolver;
    public final Executor executor;
    public final Uri settingsUri;
    public final UserTracker userTracker;
    public final ColumbusContentObserver$userTrackerCallback$1 userTrackerCallback;

    public ColumbusContentObserver() {
        throw null;
    }

    public ColumbusContentObserver(ContentResolverWrapper contentResolverWrapper, Uri uri, Function1 function1, UserTracker userTracker2, Executor executor2, Handler handler) {
        super(handler);
        this.contentResolver = contentResolverWrapper;
        this.settingsUri = uri;
        this.callback = function1;
        this.userTracker = userTracker2;
        this.executor = executor2;
        this.userTrackerCallback = new ColumbusContentObserver$userTrackerCallback$1(this);
    }

    /* compiled from: ColumbusContentObserver.kt */
    public static final class Factory {
        public final ContentResolverWrapper contentResolver;
        public final Executor executor;
        public final Handler handler;
        public final UserTracker userTracker;

        public Factory(ContentResolverWrapper contentResolverWrapper, UserTracker userTracker2, Handler handler2, Executor executor2) {
            this.contentResolver = contentResolverWrapper;
            this.userTracker = userTracker2;
            this.handler = handler2;
            this.executor = executor2;
        }
    }

    public final void onChange(boolean z, Uri uri) {
        this.callback.invoke(uri);
    }

    public final void updateContentObserver() {
        ContentResolverWrapper contentResolverWrapper = this.contentResolver;
        Objects.requireNonNull(contentResolverWrapper);
        contentResolverWrapper.contentResolver.unregisterContentObserver(this);
        ContentResolverWrapper contentResolverWrapper2 = this.contentResolver;
        Uri uri = this.settingsUri;
        int userId = this.userTracker.getUserId();
        Objects.requireNonNull(contentResolverWrapper2);
        contentResolverWrapper2.contentResolver.registerContentObserver(uri, false, this, userId);
    }
}
