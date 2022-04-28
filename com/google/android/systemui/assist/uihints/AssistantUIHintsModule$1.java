package com.google.android.systemui.assist.uihints;

import android.content.Intent;
import android.util.Log;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.Lazy;

public final class AssistantUIHintsModule$1 implements NgaMessageHandler.StartActivityInfoListener {
    public final /* synthetic */ Lazy val$statusBarLazy;

    public AssistantUIHintsModule$1(Lazy lazy) {
        this.val$statusBarLazy = lazy;
    }

    public final void onStartActivityInfo(Intent intent, boolean z) {
        if (intent == null) {
            Log.e("ActivityStarter", "Null intent; cannot start activity");
        } else {
            ((StatusBar) this.val$statusBarLazy.get()).startActivity(intent, z);
        }
    }
}
