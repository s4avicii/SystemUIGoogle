package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotifCoordinatorsImpl_Factory implements Factory<NotifCoordinatorsImpl> {
    public final Provider<AppOpsCoordinator> appOpsCoordinatorProvider;
    public final Provider<BubbleCoordinator> bubbleCoordinatorProvider;
    public final Provider<CommunalCoordinator> communalCoordinatorProvider;
    public final Provider<ConversationCoordinator> conversationCoordinatorProvider;
    public final Provider<DataStoreCoordinator> dataStoreCoordinatorProvider;
    public final Provider<DebugModeCoordinator> debugModeCoordinatorProvider;
    public final Provider<DeviceProvisionedCoordinator> deviceProvisionedCoordinatorProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<GroupCountCoordinator> groupCountCoordinatorProvider;
    public final Provider<GutsCoordinator> gutsCoordinatorProvider;
    public final Provider<HeadsUpCoordinator> headsUpCoordinatorProvider;
    public final Provider<HideLocallyDismissedNotifsCoordinator> hideLocallyDismissedNotifsCoordinatorProvider;
    public final Provider<HideNotifsForOtherUsersCoordinator> hideNotifsForOtherUsersCoordinatorProvider;
    public final Provider<KeyguardCoordinator> keyguardCoordinatorProvider;
    public final Provider<MediaCoordinator> mediaCoordinatorProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
    public final Provider<PreparationCoordinator> preparationCoordinatorProvider;
    public final Provider<RankingCoordinator> rankingCoordinatorProvider;
    public final Provider<RemoteInputCoordinator> remoteInputCoordinatorProvider;
    public final Provider<RowAppearanceCoordinator> rowAppearanceCoordinatorProvider;
    public final Provider<SensitiveContentCoordinator> sensitiveContentCoordinatorProvider;
    public final Provider<ShadeEventCoordinator> shadeEventCoordinatorProvider;
    public final Provider<SmartspaceDedupingCoordinator> smartspaceDedupingCoordinatorProvider;
    public final Provider<StackCoordinator> stackCoordinatorProvider;
    public final Provider<ViewConfigCoordinator> viewConfigCoordinatorProvider;
    public final Provider<VisualStabilityCoordinator> visualStabilityCoordinatorProvider;

    public NotifCoordinatorsImpl_Factory(Provider<DumpManager> provider, Provider<NotifPipelineFlags> provider2, Provider<DataStoreCoordinator> provider3, Provider<HideLocallyDismissedNotifsCoordinator> provider4, Provider<HideNotifsForOtherUsersCoordinator> provider5, Provider<KeyguardCoordinator> provider6, Provider<RankingCoordinator> provider7, Provider<AppOpsCoordinator> provider8, Provider<DeviceProvisionedCoordinator> provider9, Provider<BubbleCoordinator> provider10, Provider<HeadsUpCoordinator> provider11, Provider<GutsCoordinator> provider12, Provider<CommunalCoordinator> provider13, Provider<ConversationCoordinator> provider14, Provider<DebugModeCoordinator> provider15, Provider<GroupCountCoordinator> provider16, Provider<MediaCoordinator> provider17, Provider<PreparationCoordinator> provider18, Provider<RemoteInputCoordinator> provider19, Provider<RowAppearanceCoordinator> provider20, Provider<StackCoordinator> provider21, Provider<ShadeEventCoordinator> provider22, Provider<SmartspaceDedupingCoordinator> provider23, Provider<ViewConfigCoordinator> provider24, Provider<VisualStabilityCoordinator> provider25, Provider<SensitiveContentCoordinator> provider26) {
        this.dumpManagerProvider = provider;
        this.notifPipelineFlagsProvider = provider2;
        this.dataStoreCoordinatorProvider = provider3;
        this.hideLocallyDismissedNotifsCoordinatorProvider = provider4;
        this.hideNotifsForOtherUsersCoordinatorProvider = provider5;
        this.keyguardCoordinatorProvider = provider6;
        this.rankingCoordinatorProvider = provider7;
        this.appOpsCoordinatorProvider = provider8;
        this.deviceProvisionedCoordinatorProvider = provider9;
        this.bubbleCoordinatorProvider = provider10;
        this.headsUpCoordinatorProvider = provider11;
        this.gutsCoordinatorProvider = provider12;
        this.communalCoordinatorProvider = provider13;
        this.conversationCoordinatorProvider = provider14;
        this.debugModeCoordinatorProvider = provider15;
        this.groupCountCoordinatorProvider = provider16;
        this.mediaCoordinatorProvider = provider17;
        this.preparationCoordinatorProvider = provider18;
        this.remoteInputCoordinatorProvider = provider19;
        this.rowAppearanceCoordinatorProvider = provider20;
        this.stackCoordinatorProvider = provider21;
        this.shadeEventCoordinatorProvider = provider22;
        this.smartspaceDedupingCoordinatorProvider = provider23;
        this.viewConfigCoordinatorProvider = provider24;
        this.visualStabilityCoordinatorProvider = provider25;
        this.sensitiveContentCoordinatorProvider = provider26;
    }

    public static NotifCoordinatorsImpl_Factory create(Provider<DumpManager> provider, Provider<NotifPipelineFlags> provider2, Provider<DataStoreCoordinator> provider3, Provider<HideLocallyDismissedNotifsCoordinator> provider4, Provider<HideNotifsForOtherUsersCoordinator> provider5, Provider<KeyguardCoordinator> provider6, Provider<RankingCoordinator> provider7, Provider<AppOpsCoordinator> provider8, Provider<DeviceProvisionedCoordinator> provider9, Provider<BubbleCoordinator> provider10, Provider<HeadsUpCoordinator> provider11, Provider<GutsCoordinator> provider12, Provider<CommunalCoordinator> provider13, Provider<ConversationCoordinator> provider14, Provider<DebugModeCoordinator> provider15, Provider<GroupCountCoordinator> provider16, Provider<MediaCoordinator> provider17, Provider<PreparationCoordinator> provider18, Provider<RemoteInputCoordinator> provider19, Provider<RowAppearanceCoordinator> provider20, Provider<StackCoordinator> provider21, Provider<ShadeEventCoordinator> provider22, Provider<SmartspaceDedupingCoordinator> provider23, Provider<ViewConfigCoordinator> provider24, Provider<VisualStabilityCoordinator> provider25, Provider<SensitiveContentCoordinator> provider26) {
        return new NotifCoordinatorsImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26);
    }

    public final Object get() {
        return new NotifCoordinatorsImpl(this.dumpManagerProvider.get(), this.notifPipelineFlagsProvider.get(), this.dataStoreCoordinatorProvider.get(), this.hideLocallyDismissedNotifsCoordinatorProvider.get(), this.hideNotifsForOtherUsersCoordinatorProvider.get(), this.keyguardCoordinatorProvider.get(), this.rankingCoordinatorProvider.get(), this.appOpsCoordinatorProvider.get(), this.deviceProvisionedCoordinatorProvider.get(), this.bubbleCoordinatorProvider.get(), this.headsUpCoordinatorProvider.get(), this.gutsCoordinatorProvider.get(), this.communalCoordinatorProvider.get(), this.conversationCoordinatorProvider.get(), this.debugModeCoordinatorProvider.get(), this.groupCountCoordinatorProvider.get(), this.mediaCoordinatorProvider.get(), this.preparationCoordinatorProvider.get(), this.remoteInputCoordinatorProvider.get(), this.rowAppearanceCoordinatorProvider.get(), this.stackCoordinatorProvider.get(), this.shadeEventCoordinatorProvider.get(), this.smartspaceDedupingCoordinatorProvider.get(), this.viewConfigCoordinatorProvider.get(), this.visualStabilityCoordinatorProvider.get(), this.sensitiveContentCoordinatorProvider.get());
    }
}
