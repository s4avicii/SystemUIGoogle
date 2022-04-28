package com.android.p012wm.shell.bubbles;

/* renamed from: com.android.wm.shell.bubbles.ManageEducationView$hide$2 */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView$hide$2 implements Runnable {
    public final /* synthetic */ ManageEducationView this$0;

    public ManageEducationView$hide$2(ManageEducationView manageEducationView) {
        this.this$0 = manageEducationView;
    }

    public final void run() {
        ManageEducationView manageEducationView = this.this$0;
        manageEducationView.isHiding = false;
        manageEducationView.setVisibility(8);
    }
}
