package com.android.systemui.controls.controller;

import android.app.PendingIntent;
import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.controls.p004ui.ControlsUiControllerImpl$onSeedingComplete$1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl implements Dumpable, ControlsController {
    public AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper;
    public final ControlsBindingController bindingController;
    public final Context context;
    public UserHandle currentUser;
    public final DelayableExecutor executor;
    public final ControlsListingController listingController;
    public final ControlsFavoritePersistenceWrapper persistenceWrapper;
    public final ControlsControllerImpl$restoreFinishedReceiver$1 restoreFinishedReceiver;
    public final ArrayList seedingCallbacks = new ArrayList();
    public boolean seedingInProgress;
    public final ControlsUiController uiController;
    public boolean userChanging = true;
    public UserStructure userStructure;

    public ControlsControllerImpl(Context context2, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, ControlsBindingController controlsBindingController, ControlsListingController controlsListingController, BroadcastDispatcher broadcastDispatcher, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager, UserTracker userTracker) {
        DelayableExecutor delayableExecutor2 = delayableExecutor;
        ControlsListingController controlsListingController2 = controlsListingController;
        this.context = context2;
        this.executor = delayableExecutor2;
        this.uiController = controlsUiController;
        this.bindingController = controlsBindingController;
        this.listingController = controlsListingController2;
        UserHandle userHandle = userTracker.getUserHandle();
        this.currentUser = userHandle;
        this.userStructure = new UserStructure(context2, userHandle);
        this.persistenceWrapper = optional.orElseGet(new Supplier(this) {
            public final /* synthetic */ ControlsControllerImpl this$0;

            {
                this.this$0 = r1;
            }

            public final Object get() {
                UserStructure userStructure = this.this$0.userStructure;
                Objects.requireNonNull(userStructure);
                File file = userStructure.file;
                DelayableExecutor delayableExecutor = this.this$0.executor;
                UserStructure userStructure2 = this.this$0.userStructure;
                Objects.requireNonNull(userStructure2);
                return new ControlsFavoritePersistenceWrapper(file, delayableExecutor, new BackupManager(userStructure2.userContext));
            }
        });
        UserStructure userStructure2 = this.userStructure;
        Objects.requireNonNull(userStructure2);
        this.auxiliaryPersistenceWrapper = new AuxiliaryPersistenceWrapper(new ControlsFavoritePersistenceWrapper(userStructure2.auxiliaryFile, delayableExecutor, (BackupManager) null));
        ControlsControllerImpl$userSwitchReceiver$1 controlsControllerImpl$userSwitchReceiver$1 = new ControlsControllerImpl$userSwitchReceiver$1(this);
        ControlsControllerImpl$restoreFinishedReceiver$1 controlsControllerImpl$restoreFinishedReceiver$1 = new ControlsControllerImpl$restoreFinishedReceiver$1(this);
        this.restoreFinishedReceiver = controlsControllerImpl$restoreFinishedReceiver$1;
        new ControlsControllerImpl$settingObserver$1(this);
        ControlsControllerImpl$listingCallback$1 controlsControllerImpl$listingCallback$1 = new ControlsControllerImpl$listingCallback$1(this);
        dumpManager.registerDumpable(ControlsControllerImpl.class.getName(), this);
        resetFavorites();
        this.userChanging = false;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, controlsControllerImpl$userSwitchReceiver$1, new IntentFilter("android.intent.action.USER_SWITCHED"), delayableExecutor2, UserHandle.ALL, 16);
        context2.registerReceiver(controlsControllerImpl$restoreFinishedReceiver$1, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), "com.android.systemui.permission.SELF", (Handler) null, 4);
        controlsListingController2.addCallback(controlsControllerImpl$listingCallback$1);
    }

    @VisibleForTesting
    /* renamed from: getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m38x61774fea() {
    }

    @VisibleForTesting
    /* renamed from: getRestoreFinishedReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m39x34c469bf() {
    }

    @VisibleForTesting
    /* renamed from: getSettingObserver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m40x3a354bd6() {
    }

    public final boolean addSeedingFavoritesCallback(ControlsUiControllerImpl$onSeedingComplete$1 controlsUiControllerImpl$onSeedingComplete$1) {
        if (!this.seedingInProgress) {
            return false;
        }
        this.executor.execute(new ControlsControllerImpl$addSeedingFavoritesCallback$1(this, controlsUiControllerImpl$onSeedingComplete$1));
        return true;
    }

    public final boolean confirmAvailability() {
        if (!this.userChanging) {
            return true;
        }
        Log.w("ControlsControllerImpl", "Controls not available while user is changing");
        return false;
    }

    public final ControlStatus createRemovedStatus(ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(componentName.getPackageName());
        PendingIntent activity = PendingIntent.getActivity(this.context, componentName.hashCode(), intent, 67108864);
        Objects.requireNonNull(controlInfo);
        return new ControlStatus(new Control.StatelessBuilder(controlInfo.controlId, activity).setTitle(controlInfo.controlTitle).setSubtitle(controlInfo.controlSubtitle).setStructure(charSequence).setDeviceType(controlInfo.deviceType).build(), componentName, true, z);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsController state:");
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.userChanging, "  Changing users: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("  Current user: ", Integer.valueOf(this.currentUser.getIdentifier())));
        printWriter.println("  Favorites:");
        Iterator it = Favorites.getAllStructures().iterator();
        while (it.hasNext()) {
            StructureInfo structureInfo = (StructureInfo) it.next();
            printWriter.println(Intrinsics.stringPlus("    ", structureInfo));
            Objects.requireNonNull(structureInfo);
            for (ControlInfo stringPlus : structureInfo.controls) {
                printWriter.println(Intrinsics.stringPlus("      ", stringPlus));
            }
        }
        printWriter.println(this.bindingController.toString());
    }

    public final int getCurrentUserId() {
        return this.currentUser.getIdentifier();
    }

    public final StructureInfo getPreferredStructure() {
        return this.uiController.getPreferredStructure(Favorites.getAllStructures());
    }

    public final void resetFavorites() {
        Map<ComponentName, ? extends List<StructureInfo>> map = Favorites.favMap;
        Favorites.favMap = MapsKt___MapsKt.emptyMap();
        List<StructureInfo> readFavorites = this.persistenceWrapper.readFavorites();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T next : readFavorites) {
            StructureInfo structureInfo = (StructureInfo) next;
            Objects.requireNonNull(structureInfo);
            ComponentName componentName = structureInfo.componentName;
            Object obj = linkedHashMap.get(componentName);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(componentName, obj);
            }
            ((List) obj).add(next);
        }
        Favorites.favMap = linkedHashMap;
    }

    public final void seedFavoritesForComponents(List<ComponentName> list, Consumer<SeedResponse> consumer) {
        if (!this.seedingInProgress && !list.isEmpty()) {
            if (confirmAvailability()) {
                this.seedingInProgress = true;
                startSeeding(list, consumer, false);
            } else if (this.userChanging) {
                this.executor.executeDelayed(new ControlsControllerImpl$seedFavoritesForComponents$1(this, list, consumer), 500, TimeUnit.MILLISECONDS);
            } else {
                for (ComponentName packageName : list) {
                    consumer.accept(new SeedResponse(packageName.getPackageName(), false));
                }
            }
        }
    }

    public final void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction) {
        if (confirmAvailability()) {
            this.bindingController.action(componentName, controlInfo, controlAction);
        }
    }

    public final void addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo) {
        if (confirmAvailability()) {
            this.executor.execute(new ControlsControllerImpl$addFavorite$1(componentName, charSequence, controlInfo, this));
        }
    }

    public final int countFavoritesForComponent(ComponentName componentName) {
        return Favorites.getControlsForComponent(componentName).size();
    }

    public final ArrayList getFavorites() {
        return Favorites.getAllStructures();
    }

    public final List<StructureInfo> getFavoritesForComponent(ComponentName componentName) {
        return Favorites.getStructuresForComponent(componentName);
    }

    public final void loadForComponent(ComponentName componentName, Consumer<ControlsController.LoadData> consumer, Consumer<Runnable> consumer2) {
        if (!confirmAvailability()) {
            if (this.userChanging) {
                this.executor.executeDelayed(new ControlsControllerImpl$loadForComponent$1(this, componentName, consumer, consumer2), 500, TimeUnit.MILLISECONDS);
            }
            EmptyList emptyList = EmptyList.INSTANCE;
            consumer.accept(new ControlsControllerKt$createLoadDataObject$1(emptyList, emptyList, true));
        }
        consumer2.accept(this.bindingController.bindAndLoad(componentName, new ControlsControllerImpl$loadForComponent$2(this, componentName, consumer)));
    }

    public final void onActionResponse(ComponentName componentName, String str, int i) {
        if (confirmAvailability()) {
            this.uiController.onActionResponse(componentName, str, i);
        }
    }

    public final void refreshStatus(ComponentName componentName, Control control) {
        if (!confirmAvailability()) {
            Log.d("ControlsControllerImpl", "Controls not available");
            return;
        }
        if (control.getStatus() == 1) {
            this.executor.execute(new ControlsControllerImpl$refreshStatus$1(componentName, control, this));
        }
        this.uiController.onRefreshState(componentName, Collections.singletonList(control));
    }

    public final void startSeeding(List<ComponentName> list, Consumer<SeedResponse> consumer, boolean z) {
        if (list.isEmpty()) {
            boolean z2 = !z;
            this.seedingInProgress = false;
            Iterator it = this.seedingCallbacks.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Boolean.valueOf(z2));
            }
            this.seedingCallbacks.clear();
            return;
        }
        ComponentName componentName = list.get(0);
        Log.d("ControlsControllerImpl", Intrinsics.stringPlus("Beginning request to seed favorites for: ", componentName));
        this.bindingController.bindAndLoadSuggested(componentName, new ControlsControllerImpl$startSeeding$1(this, consumer, componentName, CollectionsKt___CollectionsKt.drop(list, 1), z));
    }

    public final void subscribeToFavorites(StructureInfo structureInfo) {
        if (confirmAvailability()) {
            this.bindingController.subscribe(structureInfo);
        }
    }

    public final void unsubscribe() {
        if (confirmAvailability()) {
            this.bindingController.unsubscribe();
        }
    }
}
