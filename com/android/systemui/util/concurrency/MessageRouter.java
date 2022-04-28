package com.android.systemui.util.concurrency;

import com.android.systemui.statusbar.phone.StatusBar;

public interface MessageRouter {

    public interface DataMessageListener<T> {
        void onMessage(T t);
    }

    public interface SimpleMessageListener {
        void onMessage();
    }

    void cancelMessages(int i);

    <T> void cancelMessages(Class<T> cls);

    void sendMessage(int i) {
        sendMessageDelayed(i, 0);
    }

    void sendMessageDelayed(int i, long j);

    void sendMessageDelayed(StatusBar.KeyboardShortcutsMessage keyboardShortcutsMessage);

    void subscribeTo(int i, SimpleMessageListener simpleMessageListener);

    <T> void subscribeTo(Class<T> cls, DataMessageListener<T> dataMessageListener);

    void sendMessage(StatusBar.KeyboardShortcutsMessage keyboardShortcutsMessage) {
        sendMessageDelayed(keyboardShortcutsMessage);
    }
}
