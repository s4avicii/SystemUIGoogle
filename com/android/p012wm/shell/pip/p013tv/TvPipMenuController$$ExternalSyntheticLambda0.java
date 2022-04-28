package com.android.p012wm.shell.pip.p013tv;

import android.app.RemoteAction;
import com.android.p012wm.shell.pip.PipMediaController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TvPipMenuController$$ExternalSyntheticLambda0 implements PipMediaController.ActionListener {
    public final /* synthetic */ TvPipMenuController f$0;

    public /* synthetic */ TvPipMenuController$$ExternalSyntheticLambda0(TvPipMenuController tvPipMenuController) {
        this.f$0 = tvPipMenuController;
    }

    public final void onMediaActionsChanged(List list) {
        TvPipMenuController tvPipMenuController = this.f$0;
        Objects.requireNonNull(tvPipMenuController);
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RemoteAction remoteAction = (RemoteAction) it.next();
            if (remoteAction.isEnabled()) {
                arrayList.add(remoteAction);
            }
        }
        tvPipMenuController.updateAdditionalActionsList(tvPipMenuController.mMediaActions, arrayList);
    }
}
