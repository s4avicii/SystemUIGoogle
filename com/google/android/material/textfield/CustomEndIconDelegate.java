package com.google.android.material.textfield;

import android.view.View;
import com.google.android.material.internal.CheckableImageButton;
import java.util.Objects;

public final class CustomEndIconDelegate extends EndIconDelegate {
    public final void initialize() {
        this.textInputLayout.setEndIconDrawable(this.customEndIcon);
        TextInputLayout textInputLayout = this.textInputLayout;
        Objects.requireNonNull(textInputLayout);
        CheckableImageButton checkableImageButton = textInputLayout.endIconView;
        View.OnLongClickListener onLongClickListener = textInputLayout.endIconOnLongClickListener;
        checkableImageButton.setOnClickListener((View.OnClickListener) null);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
        TextInputLayout textInputLayout2 = this.textInputLayout;
        Objects.requireNonNull(textInputLayout2);
        textInputLayout2.endIconOnLongClickListener = null;
        CheckableImageButton checkableImageButton2 = textInputLayout2.endIconView;
        checkableImageButton2.setOnLongClickListener((View.OnLongClickListener) null);
        TextInputLayout.setIconClickable(checkableImageButton2, (View.OnLongClickListener) null);
    }

    public CustomEndIconDelegate(TextInputLayout textInputLayout, int i) {
        super(textInputLayout, i);
    }
}
