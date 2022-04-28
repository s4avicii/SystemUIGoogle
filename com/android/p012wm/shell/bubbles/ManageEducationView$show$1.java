package com.android.p012wm.shell.bubbles;

import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.ManageEducationView$show$1 */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView$show$1 implements Runnable {
    public final /* synthetic */ BubbleExpandedView $expandedView;
    public final /* synthetic */ ManageEducationView this$0;

    public ManageEducationView$show$1(ManageEducationView manageEducationView, BubbleExpandedView bubbleExpandedView) {
        this.this$0 = manageEducationView;
        this.$expandedView = bubbleExpandedView;
    }

    public final void run() {
        Button manageButton = this.this$0.getManageButton();
        final ManageEducationView manageEducationView = this.this$0;
        final BubbleExpandedView bubbleExpandedView = this.$expandedView;
        manageButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                manageEducationView.hide();
                bubbleExpandedView.findViewById(C1777R.C1779id.manage_button).performClick();
            }
        });
        ManageEducationView manageEducationView2 = this.this$0;
        Objects.requireNonNull(manageEducationView2);
        final ManageEducationView manageEducationView3 = this.this$0;
        ((Button) manageEducationView2.gotItButton$delegate.getValue()).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                manageEducationView3.hide();
            }
        });
        final ManageEducationView manageEducationView4 = this.this$0;
        manageEducationView4.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                manageEducationView4.hide();
            }
        });
        Rect rect = new Rect();
        this.this$0.getManageButton().getDrawingRect(rect);
        this.this$0.getManageView().offsetDescendantRectToMyCoords(this.this$0.getManageButton(), rect);
        this.this$0.setTranslationX(0.0f);
        ManageEducationView manageEducationView5 = this.this$0;
        manageEducationView5.setTranslationY((float) (manageEducationView5.realManageButtonRect.top - rect.top));
        this.this$0.bringToFront();
        this.this$0.animate().setDuration(this.this$0.ANIMATE_DURATION).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
    }
}
