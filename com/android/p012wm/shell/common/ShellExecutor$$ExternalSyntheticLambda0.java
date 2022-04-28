package com.android.p012wm.shell.common;

import android.content.DialogInterface;
import android.telephony.PinResult;
import android.util.SparseArray;
import android.view.View;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.tiles.DataSaverTile;
import com.android.systemui.p006qs.tiles.DataSaverTile$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellExecutor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShellExecutor$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((Runnable) this.f$0).run();
                ((CountDownLatch) this.f$1).countDown();
                return;
            case 1:
                KeyguardSimPukViewController.C05293.$r8$lambda$D4YXQJ16o3cMhvtpU1r8aJ4UUVw((KeyguardSimPukViewController.C05293) this.f$0, (PinResult) this.f$1);
                return;
            case 2:
                DataSaverTile dataSaverTile = (DataSaverTile) this.f$0;
                View view = (View) this.f$1;
                int i = DataSaverTile.$r8$clinit;
                Objects.requireNonNull(dataSaverTile);
                SystemUIDialog systemUIDialog = new SystemUIDialog(dataSaverTile.mContext);
                systemUIDialog.setTitle(17040080);
                systemUIDialog.setMessage(17040078);
                systemUIDialog.setPositiveButton(17040079, new DataSaverTile$$ExternalSyntheticLambda0(dataSaverTile));
                systemUIDialog.setButton(-3, 17039360, (DialogInterface.OnClickListener) null, true);
                SystemUIDialog.setShowForAllUsers(systemUIDialog);
                if (view != null) {
                    DialogLaunchAnimator dialogLaunchAnimator = dataSaverTile.mDialogLaunchAnimator;
                    Objects.requireNonNull(dialogLaunchAnimator);
                    dialogLaunchAnimator.showFromView(systemUIDialog, view, false);
                    return;
                }
                systemUIDialog.show();
                return;
            case 3:
                BubblesManager.C17525 r0 = (BubblesManager.C17525) this.f$0;
                Objects.requireNonNull(r0);
                NotificationEntry entry = BubblesManager.this.mCommonNotifCollection.getEntry((String) this.f$1);
                if (entry != null) {
                    Iterator it = BubblesManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((BubblesManager.NotifCallback) it.next()).maybeCancelSummary(entry);
                    }
                    return;
                }
                return;
            default:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                Objects.requireNonNull(bubblesImpl);
                BubbleController bubbleController = BubbleController.this;
                Objects.requireNonNull(bubbleController);
                bubbleController.mCurrentProfiles = (SparseArray) this.f$1;
                return;
        }
    }
}
