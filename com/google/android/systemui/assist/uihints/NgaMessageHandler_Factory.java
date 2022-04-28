package com.google.android.systemui.assist.uihints;

import android.os.Handler;
import com.android.systemui.navigationbar.NavigationModeController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.internal.Factory;
import dagger.internal.SetFactory;
import java.util.Set;
import javax.inject.Provider;

public final class NgaMessageHandler_Factory implements Factory<NgaMessageHandler> {
    public final Provider<AssistantPresenceHandler> assistantPresenceHandlerProvider;
    public final Provider<Set<NgaMessageHandler.AudioInfoListener>> audioInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.CardInfoListener>> cardInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.ChipsInfoListener>> chipsInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.ClearListener>> clearListenersProvider;
    public final Provider<Set<NgaMessageHandler.ConfigInfoListener>> configInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.EdgeLightsInfoListener>> edgeLightsInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.GoBackListener>> goBackListenersProvider;
    public final Provider<Set<NgaMessageHandler.GreetingInfoListener>> greetingInfoListenersProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<Set<NgaMessageHandler.KeepAliveListener>> keepAliveListenersProvider;
    public final Provider<Set<NgaMessageHandler.KeyboardInfoListener>> keyboardInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.NavBarVisibilityListener>> navBarVisibilityListenersProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<NgaUiController> ngaUiControllerProvider;
    public final Provider<Set<NgaMessageHandler.StartActivityInfoListener>> startActivityInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.SwipeListener>> swipeListenersProvider;
    public final Provider<Set<NgaMessageHandler.TakeScreenshotListener>> takeScreenshotListenersProvider;
    public final Provider<Set<NgaMessageHandler.TranscriptionInfoListener>> transcriptionInfoListenersProvider;
    public final Provider<Set<NgaMessageHandler.WarmingListener>> warmingListenersProvider;
    public final Provider<Set<NgaMessageHandler.ZerostateInfoListener>> zerostateInfoListenersProvider;

    public NgaMessageHandler_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, SetFactory setFactory, Provider provider20) {
        this.ngaUiControllerProvider = provider;
        this.assistantPresenceHandlerProvider = provider2;
        this.navigationModeControllerProvider = provider3;
        this.keepAliveListenersProvider = provider4;
        this.audioInfoListenersProvider = provider5;
        this.cardInfoListenersProvider = provider6;
        this.configInfoListenersProvider = provider7;
        this.edgeLightsInfoListenersProvider = provider8;
        this.transcriptionInfoListenersProvider = provider9;
        this.greetingInfoListenersProvider = provider10;
        this.chipsInfoListenersProvider = provider11;
        this.clearListenersProvider = provider12;
        this.startActivityInfoListenersProvider = provider13;
        this.keyboardInfoListenersProvider = provider14;
        this.zerostateInfoListenersProvider = provider15;
        this.goBackListenersProvider = provider16;
        this.swipeListenersProvider = provider17;
        this.takeScreenshotListenersProvider = provider18;
        this.warmingListenersProvider = provider19;
        this.navBarVisibilityListenersProvider = setFactory;
        this.handlerProvider = provider20;
    }

    public static NgaMessageHandler_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, SetFactory setFactory, Provider provider20) {
        return new NgaMessageHandler_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, setFactory, provider20);
    }

    public final Object get() {
        return new NgaMessageHandler(this.ngaUiControllerProvider.get(), this.assistantPresenceHandlerProvider.get(), this.navigationModeControllerProvider.get(), this.keepAliveListenersProvider.get(), this.audioInfoListenersProvider.get(), this.cardInfoListenersProvider.get(), this.configInfoListenersProvider.get(), this.edgeLightsInfoListenersProvider.get(), this.transcriptionInfoListenersProvider.get(), this.greetingInfoListenersProvider.get(), this.chipsInfoListenersProvider.get(), this.clearListenersProvider.get(), this.startActivityInfoListenersProvider.get(), this.keyboardInfoListenersProvider.get(), this.zerostateInfoListenersProvider.get(), this.goBackListenersProvider.get(), this.swipeListenersProvider.get(), this.takeScreenshotListenersProvider.get(), this.warmingListenersProvider.get(), this.navBarVisibilityListenersProvider.get(), this.handlerProvider.get());
    }
}
