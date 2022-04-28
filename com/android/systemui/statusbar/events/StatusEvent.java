package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.View;
import kotlin.jvm.functions.Function1;

/* compiled from: StatusEvent.kt */
public interface StatusEvent {
    String getContentDescription();

    int getPriority();

    Function1<Context, View> getViewCreator();

    boolean shouldUpdateFromEvent(StatusEvent statusEvent);

    void updateFromEvent(StatusEvent statusEvent);
}
