package com.android.systemui.statusbar.policy;

import android.util.Pair;
import android.view.ContentInfo;
import android.view.OnReceiveContentListener;
import android.view.View;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0 implements OnReceiveContentListener {
    public final /* synthetic */ RemoteInputView.RemoteEditText f$0;

    public /* synthetic */ RemoteInputView$RemoteEditText$$ExternalSyntheticLambda0(RemoteInputView.RemoteEditText remoteEditText) {
        this.f$0 = remoteEditText;
    }

    public final ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
        RemoteInputView.RemoteEditText remoteEditText = this.f$0;
        int i = RemoteInputView.RemoteEditText.$r8$clinit;
        Objects.requireNonNull(remoteEditText);
        Pair partition = contentInfo.partition(RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1.INSTANCE);
        ContentInfo contentInfo2 = (ContentInfo) partition.first;
        ContentInfo contentInfo3 = (ContentInfo) partition.second;
        if (contentInfo2 != null) {
            remoteEditText.mRemoteInputView.setAttachment(contentInfo2);
        }
        return contentInfo3;
    }
}
