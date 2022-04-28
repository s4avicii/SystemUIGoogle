package com.google.android.systemui.communal.dock.callbacks.mediashell;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda22;
import com.android.systemui.util.condition.Monitor;
import com.android.systemui.util.service.ObservableServiceConnection;
import com.android.systemui.util.service.PackageObserver;
import com.android.systemui.util.service.PersistentConnectionManager;
import com.google.android.systemui.communal.dock.callbacks.mediashell.dagger.MediaShellComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;
import javax.inject.Provider;

public final class MediaShellCallback implements Monitor.Callback {
    public C22031 mCallback;
    public final PersistentConnectionManager mConnectionManager;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final MediaShellComplication mMediaShellComplication;

    public final void onConditionsChanged(boolean z) {
        if (z) {
            PersistentConnectionManager persistentConnectionManager = this.mConnectionManager;
            Objects.requireNonNull(persistentConnectionManager);
            persistentConnectionManager.mConnection.addCallback(persistentConnectionManager.mConnectionCallback);
            persistentConnectionManager.mObserver.addCallback(persistentConnectionManager.mObserverCallback);
            persistentConnectionManager.mReconnectAttempts = 0;
            persistentConnectionManager.mConnection.bind();
            return;
        }
        PersistentConnectionManager persistentConnectionManager2 = this.mConnectionManager;
        Objects.requireNonNull(persistentConnectionManager2);
        ObservableServiceConnection<T> observableServiceConnection = persistentConnectionManager2.mConnection;
        PersistentConnectionManager.C17112 r0 = persistentConnectionManager2.mConnectionCallback;
        if (ObservableServiceConnection.DEBUG) {
            Objects.requireNonNull(observableServiceConnection);
            Log.d("ObservableSvcConn", "removeCallback:" + r0);
        }
        observableServiceConnection.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda22(observableServiceConnection, r0, 3));
        persistentConnectionManager2.mObserver.removeCallback(persistentConnectionManager2.mObserverCallback);
        persistentConnectionManager2.mConnection.unbind();
    }

    public MediaShellCallback(MediaShellComponent$Factory mediaShellComponent$Factory, DreamOverlayStateController dreamOverlayStateController, MediaShellComplication mediaShellComplication) {
        C22031 r0 = new ObservableServiceConnection.Callback<IBinder>() {
            public final void onConnected(Object obj) {
                IBinder iBinder = (IBinder) obj;
                MediaShellCallback mediaShellCallback = MediaShellCallback.this;
                mediaShellCallback.mDreamOverlayStateController.addComplication(mediaShellCallback.mMediaShellComplication);
            }

            public final void onDisconnected() {
                MediaShellCallback mediaShellCallback = MediaShellCallback.this;
                DreamOverlayStateController dreamOverlayStateController = mediaShellCallback.mDreamOverlayStateController;
                MediaShellComplication mediaShellComplication = mediaShellCallback.mMediaShellComplication;
                Objects.requireNonNull(dreamOverlayStateController);
                dreamOverlayStateController.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda0(dreamOverlayStateController, mediaShellComplication, 0));
            }
        };
        this.mCallback = r0;
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.MediaShellComponentImpl create = mediaShellComponent$Factory.create(r0);
        Objects.requireNonNull(create);
        DaggerTitanGlobalRootComponent daggerTitanGlobalRootComponent = DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0;
        Context context = daggerTitanGlobalRootComponent.context;
        Provider provider = DaggerTitanGlobalRootComponent.ABSENT_JDK_OPTIONAL_PROVIDER;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(daggerTitanGlobalRootComponent.mainResources().getString(C1777R.string.config_media_shell_service_component));
        Objects.requireNonNull(unflattenFromString, "Cannot return null from a non-@Nullable @Provides method");
        Intent intent = new Intent("android.intent.action.DOCK_EVENT");
        intent.setComponent(unflattenFromString);
        ObservableServiceConnection observableServiceConnection = new ObservableServiceConnection(context, intent, DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.provideMainExecutorProvider.get());
        observableServiceConnection.addCallback(create.callback);
        int integer = DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.mainResources().getInteger(C1777R.integer.config_communalSourceMaxReconnectAttempts);
        int integer2 = DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.mainResources().getInteger(C1777R.integer.config_communalSourceReconnectBaseDelay);
        int integer3 = DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.mainResources().getInteger(C1777R.integer.config_connectionMinDuration);
        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.mainResources().getString(C1777R.string.config_media_shell_service_component));
        Objects.requireNonNull(unflattenFromString2, "Cannot return null from a non-@Nullable @Provides method");
        this.mConnectionManager = new PersistentConnectionManager(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.bindSystemClockProvider.get(), DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.provideDelayableExecutorProvider.get(), observableServiceConnection, integer, integer2, integer3, new PackageObserver(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.this$0.context, unflattenFromString2));
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mMediaShellComplication = mediaShellComplication;
    }
}
