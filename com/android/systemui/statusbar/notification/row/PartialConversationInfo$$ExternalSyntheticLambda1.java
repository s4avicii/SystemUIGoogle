package com.android.systemui.statusbar.notification.row;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PartialConversationInfo$$ExternalSyntheticLambda1 implements OnChannelEditorDialogFinishedListener {
    public final /* synthetic */ PartialConversationInfo f$0;

    public /* synthetic */ PartialConversationInfo$$ExternalSyntheticLambda1(PartialConversationInfo partialConversationInfo) {
        this.f$0 = partialConversationInfo;
    }

    public final void onChannelEditorDialogFinished() {
        PartialConversationInfo partialConversationInfo = this.f$0;
        int i = PartialConversationInfo.$r8$clinit;
        Objects.requireNonNull(partialConversationInfo);
        partialConversationInfo.mPresentingChannelEditorDialog = false;
        partialConversationInfo.mGutsContainer.closeControls(partialConversationInfo, false);
    }
}
