package com.google.android.material.textfield;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.internal.CheckableImageButton;
import java.util.Objects;

public final class NoEndIconDelegate extends EndIconDelegate {
    public NoEndIconDelegate(TextInputLayout textInputLayout) {
        super(textInputLayout, 0);
    }

    public final void initialize() {
        TextInputLayout textInputLayout = this.textInputLayout;
        Objects.requireNonNull(textInputLayout);
        CheckableImageButton checkableImageButton = textInputLayout.endIconView;
        View.OnLongClickListener onLongClickListener = textInputLayout.endIconOnLongClickListener;
        checkableImageButton.setOnClickListener((View.OnClickListener) null);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
        TextInputLayout textInputLayout2 = this.textInputLayout;
        Objects.requireNonNull(textInputLayout2);
        textInputLayout2.endIconView.setImageDrawable((Drawable) null);
        this.textInputLayout.setEndIconContentDescription((CharSequence) null);
    }
}
