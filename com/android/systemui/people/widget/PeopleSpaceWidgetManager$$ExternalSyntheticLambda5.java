package com.android.systemui.people.widget;

import android.app.people.ConversationChannel;
import java.util.HashMap;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda5 implements Function {
    public static final /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda5 INSTANCE = new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5();

    public final Object apply(Object obj) {
        HashMap hashMap = PeopleSpaceWidgetManager.mListeners;
        return ((ConversationChannel) obj).getShortcutInfo();
    }
}
