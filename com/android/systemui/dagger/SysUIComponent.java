package com.android.systemui.dagger;

import com.android.p012wm.shell.ShellCommandHandler;
import com.android.p012wm.shell.TaskViewFactory;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.p012wm.shell.draganddrop.DragAndDrop;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutout;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.SystemUIAppComponentFactory;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper;
import com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver;
import com.android.systemui.media.taptotransfer.sender.MediaTttChipControllerSender;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.unfold.FoldStateLogger;
import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldLatencyTracker;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public interface SysUIComponent {

    public interface Builder {
        SysUIComponent build();

        Builder setAppPairs(Optional<Object> optional);

        Builder setBackAnimation(Optional<BackAnimation> optional);

        Builder setBubbles(Optional<Bubbles> optional);

        Builder setCompatUI(Optional<CompatUI> optional);

        Builder setDisplayAreaHelper(Optional<DisplayAreaHelper> optional);

        Builder setDragAndDrop(Optional<DragAndDrop> optional);

        Builder setHideDisplayCutout(Optional<HideDisplayCutout> optional);

        Builder setLegacySplitScreen(Optional<LegacySplitScreen> optional);

        Builder setOneHanded(Optional<OneHanded> optional);

        Builder setPip(Optional<Pip> optional);

        Builder setRecentTasks(Optional<RecentTasks> optional);

        Builder setShellCommandHandler(Optional<ShellCommandHandler> optional);

        Builder setSplitScreen(Optional<SplitScreen> optional);

        Builder setStartingSurface(Optional<StartingSurface> optional);

        Builder setTaskSurfaceHelper(Optional<TaskSurfaceHelper> optional);

        Builder setTaskViewFactory(Optional<TaskViewFactory> optional);

        Builder setTransitions(ShellTransitions shellTransitions);
    }

    Dependency createDependency();

    DumpManager createDumpManager();

    ConfigurationController getConfigurationController();

    ContextComponentHelper getContextComponentHelper();

    Optional<FoldStateLogger> getFoldStateLogger();

    Optional<FoldStateLoggingProvider> getFoldStateLoggingProvider();

    InitController getInitController();

    Optional<MediaMuteAwaitConnectionCli> getMediaMuteAwaitConnectionCli();

    Optional<MediaTttChipControllerReceiver> getMediaTttChipControllerReceiver();

    Optional<MediaTttChipControllerSender> getMediaTttChipControllerSender();

    Optional<MediaTttCommandLineHelper> getMediaTttCommandLineHelper();

    Optional<NaturalRotationUnfoldProgressProvider> getNaturalRotationUnfoldProgressProvider();

    Optional<NearbyMediaDevicesManager> getNearbyMediaDevicesManager();

    Map<Class<?>, Provider<CoreStartable>> getPerUserStartables();

    Map<Class<?>, Provider<CoreStartable>> getStartables();

    Optional<SysUIUnfoldComponent> getSysUIUnfoldComponent();

    UnfoldLatencyTracker getUnfoldLatencyTracker();

    void inject(SystemUIAppComponentFactory systemUIAppComponentFactory);

    BootCompleteCacheImpl provideBootCacheImpl();

    void init() {
        boolean z;
        getSysUIUnfoldComponent().ifPresent(SysUIComponent$$ExternalSyntheticLambda1.INSTANCE);
        getNaturalRotationUnfoldProgressProvider().ifPresent(SysUIComponent$$ExternalSyntheticLambda2.INSTANCE);
        getMediaTttChipControllerSender();
        getMediaTttChipControllerReceiver();
        getMediaTttCommandLineHelper();
        getMediaMuteAwaitConnectionCli();
        getNearbyMediaDevicesManager();
        UnfoldLatencyTracker unfoldLatencyTracker = getUnfoldLatencyTracker();
        Objects.requireNonNull(unfoldLatencyTracker);
        if (unfoldLatencyTracker.context.getResources().getIntArray(17236065).length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            unfoldLatencyTracker.deviceStateManager.registerCallback(unfoldLatencyTracker.uiBgExecutor, unfoldLatencyTracker.foldStateListener);
            ScreenLifecycle screenLifecycle = unfoldLatencyTracker.screenLifecycle;
            Objects.requireNonNull(screenLifecycle);
            screenLifecycle.mObservers.add(unfoldLatencyTracker);
        }
        getFoldStateLoggingProvider().ifPresent(SysUIComponent$$ExternalSyntheticLambda0.INSTANCE);
        getFoldStateLogger().ifPresent(SysUIComponent$$ExternalSyntheticLambda3.INSTANCE);
    }
}
