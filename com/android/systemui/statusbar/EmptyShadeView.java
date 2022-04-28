package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;

public class EmptyShadeView extends StackScrollerDecorView {
    public TextView mEmptyText;
    public int mText = C1777R.string.empty_shade_text;

    public class EmptyShadeViewState extends ExpandableViewState {
        public EmptyShadeViewState() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0029, code lost:
            if (r3.mIsVisible != false) goto L_0x002d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void applyToView(android.view.View r3) {
            /*
                r2 = this;
                super.applyToView(r3)
                boolean r0 = r3 instanceof com.android.systemui.statusbar.EmptyShadeView
                if (r0 == 0) goto L_0x0030
                com.android.systemui.statusbar.EmptyShadeView r3 = (com.android.systemui.statusbar.EmptyShadeView) r3
                int r0 = r2.clipTopAmount
                float r0 = (float) r0
                com.android.systemui.statusbar.EmptyShadeView r2 = com.android.systemui.statusbar.EmptyShadeView.this
                android.widget.TextView r2 = r2.mEmptyText
                int r2 = r2.getPaddingTop()
                float r2 = (float) r2
                r1 = 1058642330(0x3f19999a, float:0.6)
                float r2 = r2 * r1
                int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r0 = 1
                r1 = 0
                if (r2 > 0) goto L_0x0021
                r2 = r0
                goto L_0x0022
            L_0x0021:
                r2 = r1
            L_0x0022:
                if (r2 == 0) goto L_0x002c
                java.util.Objects.requireNonNull(r3)
                boolean r2 = r3.mIsVisible
                if (r2 == 0) goto L_0x002c
                goto L_0x002d
            L_0x002c:
                r0 = r1
            L_0x002d:
                r3.setContentVisible(r0)
            L_0x0030:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.EmptyShadeView.EmptyShadeViewState.applyToView(android.view.View):void");
        }
    }

    public final View findSecondaryView() {
        return null;
    }

    public final ExpandableViewState createExpandableViewState() {
        return new EmptyShadeViewState();
    }

    public EmptyShadeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final View findContentView() {
        return findViewById(C1777R.C1779id.no_notifications);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mEmptyText.setText(this.mText);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mEmptyText = (TextView) findContentView();
    }
}
