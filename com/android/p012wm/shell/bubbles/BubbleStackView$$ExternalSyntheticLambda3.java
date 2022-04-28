package com.android.p012wm.shell.bubbles;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController;
import com.android.systemui.statusbar.notification.row.PartialConversationInfo;
import com.android.systemui.statusbar.notification.row.PartialConversationInfo$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ViewGroup f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda3(ViewGroup viewGroup, int i) {
        this.$r8$classId = i;
        this.f$0 = viewGroup;
    }

    public final void onClick(View view) {
        ChannelEditorDialogController channelEditorDialogController;
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                Objects.requireNonNull(bubbleStackView);
                bubbleStackView.showManageMenu(!bubbleStackView.mShowingManage);
                return;
            default:
                PartialConversationInfo partialConversationInfo = (PartialConversationInfo) this.f$0;
                int i = PartialConversationInfo.$r8$clinit;
                Objects.requireNonNull(partialConversationInfo);
                if (!partialConversationInfo.mPresentingChannelEditorDialog && (channelEditorDialogController = partialConversationInfo.mChannelEditorDialogController) != null) {
                    partialConversationInfo.mPresentingChannelEditorDialog = true;
                    channelEditorDialogController.prepareDialogForApp(partialConversationInfo.mAppName, partialConversationInfo.mPackageName, partialConversationInfo.mAppUid, partialConversationInfo.mUniqueChannelsInRow, partialConversationInfo.mPkgIcon, partialConversationInfo.mOnSettingsClickListener);
                    ChannelEditorDialogController channelEditorDialogController2 = partialConversationInfo.mChannelEditorDialogController;
                    PartialConversationInfo$$ExternalSyntheticLambda1 partialConversationInfo$$ExternalSyntheticLambda1 = new PartialConversationInfo$$ExternalSyntheticLambda1(partialConversationInfo);
                    Objects.requireNonNull(channelEditorDialogController2);
                    channelEditorDialogController2.onFinishListener = partialConversationInfo$$ExternalSyntheticLambda1;
                    ChannelEditorDialogController channelEditorDialogController3 = partialConversationInfo.mChannelEditorDialogController;
                    Objects.requireNonNull(channelEditorDialogController3);
                    if (channelEditorDialogController3.prepared) {
                        ChannelEditorDialog channelEditorDialog = channelEditorDialogController3.dialog;
                        if (channelEditorDialog == null) {
                            channelEditorDialog = null;
                        }
                        channelEditorDialog.show();
                        return;
                    }
                    throw new IllegalStateException("Must call prepareDialogForApp() before calling show()");
                }
                return;
        }
    }
}
