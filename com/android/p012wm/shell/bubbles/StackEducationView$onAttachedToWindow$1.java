package com.android.p012wm.shell.bubbles;

import android.view.KeyEvent;
import android.view.View;

/* renamed from: com.android.wm.shell.bubbles.StackEducationView$onAttachedToWindow$1 */
/* compiled from: StackEducationView.kt */
public final class StackEducationView$onAttachedToWindow$1 implements View.OnKeyListener {
    public final /* synthetic */ StackEducationView this$0;

    public StackEducationView$onAttachedToWindow$1(StackEducationView stackEducationView) {
        this.this$0 = stackEducationView;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 4) {
            StackEducationView stackEducationView = this.this$0;
            if (!stackEducationView.isHiding) {
                stackEducationView.hide(false);
                return true;
            }
        }
        return false;
    }
}
