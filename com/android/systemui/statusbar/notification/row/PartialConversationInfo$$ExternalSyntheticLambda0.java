package com.android.systemui.statusbar.notification.row;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PartialConversationInfo$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ PartialConversationInfo f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PartialConversationInfo$$ExternalSyntheticLambda0(PartialConversationInfo partialConversationInfo, int i) {
        this.f$0 = partialConversationInfo;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        PartialConversationInfo partialConversationInfo = this.f$0;
        int i = this.f$1;
        int i2 = PartialConversationInfo.$r8$clinit;
        Objects.requireNonNull(partialConversationInfo);
        partialConversationInfo.mOnSettingsClickListener.onClick(partialConversationInfo.mNotificationChannel, i);
    }
}
