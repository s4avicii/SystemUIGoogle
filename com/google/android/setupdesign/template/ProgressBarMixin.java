package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.ProgressBar;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

public final class ProgressBarMixin implements Mixin {
    public ColorStateList color;
    public final TemplateLayout templateLayout;
    public final boolean useBottomProgressBar;

    public final ProgressBar peekProgressBar() {
        int i;
        TemplateLayout templateLayout2 = this.templateLayout;
        if (this.useBottomProgressBar) {
            i = C1777R.C1779id.sud_glif_progress_bar;
        } else {
            i = C1777R.C1779id.sud_layout_progress;
        }
        return (ProgressBar) templateLayout2.findManagedViewById(i);
    }

    public final void setShown(boolean z) {
        int i;
        if (z) {
            if (peekProgressBar() == null && !this.useBottomProgressBar) {
                ViewStub viewStub = (ViewStub) this.templateLayout.findManagedViewById(C1777R.C1779id.sud_layout_progress_stub);
                if (viewStub != null) {
                    viewStub.inflate();
                }
                ColorStateList colorStateList = this.color;
                this.color = colorStateList;
                ProgressBar peekProgressBar = peekProgressBar();
                if (peekProgressBar != null) {
                    peekProgressBar.setIndeterminateTintList(colorStateList);
                    peekProgressBar.setProgressBackgroundTintList(colorStateList);
                }
            }
            ProgressBar peekProgressBar2 = peekProgressBar();
            if (peekProgressBar2 != null) {
                peekProgressBar2.setVisibility(0);
                return;
            }
            return;
        }
        ProgressBar peekProgressBar3 = peekProgressBar();
        if (peekProgressBar3 != null) {
            if (this.useBottomProgressBar) {
                i = 4;
            } else {
                i = 8;
            }
            peekProgressBar3.setVisibility(i);
        }
    }

    public ProgressBarMixin(TemplateLayout templateLayout2, AttributeSet attributeSet, int i) {
        boolean z;
        this.templateLayout = templateLayout2;
        boolean z2 = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = templateLayout2.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudProgressBarMixin, i, 0);
            if (obtainStyledAttributes.hasValue(0)) {
                z = obtainStyledAttributes.getBoolean(0, false);
            } else {
                z = false;
            }
            obtainStyledAttributes.recycle();
            setShown(false);
            z2 = z;
        }
        this.useBottomProgressBar = z2;
    }
}
