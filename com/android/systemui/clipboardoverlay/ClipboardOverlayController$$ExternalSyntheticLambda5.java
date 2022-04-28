package com.android.systemui.clipboardoverlay;

import android.app.RemoteAction;
import android.content.ComponentName;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17;
import com.android.systemui.screenshot.OverlayActionChip;
import com.android.systemui.screenshot.OverlayActionChip$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ ClipboardOverlayController f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda5(ClipboardOverlayController clipboardOverlayController, ArrayList arrayList, String str) {
        this.f$0 = clipboardOverlayController;
        this.f$1 = arrayList;
        this.f$2 = str;
    }

    public final void run() {
        ClipboardOverlayController clipboardOverlayController = this.f$0;
        ArrayList arrayList = this.f$1;
        String str = this.f$2;
        Objects.requireNonNull(clipboardOverlayController);
        clipboardOverlayController.resetActionChips();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            RemoteAction remoteAction = (RemoteAction) it.next();
            ComponentName component = remoteAction.getActionIntent().getIntent().getComponent();
            if (component != null && !TextUtils.equals(str, component.getPackageName())) {
                OverlayActionChip overlayActionChip = (OverlayActionChip) LayoutInflater.from(clipboardOverlayController.mContext).inflate(C1777R.layout.overlay_action_chip, clipboardOverlayController.mActionContainer, false);
                overlayActionChip.setText(remoteAction.getTitle());
                overlayActionChip.setContentDescription(remoteAction.getTitle());
                overlayActionChip.setIcon(remoteAction.getIcon(), false);
                overlayActionChip.setOnClickListener(new OverlayActionChip$$ExternalSyntheticLambda0(remoteAction.getActionIntent(), new BubbleStackView$$ExternalSyntheticLambda17(clipboardOverlayController, 3)));
                overlayActionChip.setAlpha(1.0f);
                clipboardOverlayController.mActionContainer.addView(overlayActionChip);
                clipboardOverlayController.mActionChips.add(overlayActionChip);
            }
        }
    }
}
