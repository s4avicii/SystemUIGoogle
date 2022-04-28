package com.android.p012wm.shell.compatui.letterboxedu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;

/* renamed from: com.android.wm.shell.compatui.letterboxedu.LetterboxEduDialogLayout */
class LetterboxEduDialogLayout extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Drawable mBackgroundDim;
    public View mDialogContainer;

    public LetterboxEduDialogLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void setDismissOnClickListener(TaskView$$ExternalSyntheticLambda5 taskView$$ExternalSyntheticLambda5) {
        LetterboxEduDialogLayout$$ExternalSyntheticLambda0 letterboxEduDialogLayout$$ExternalSyntheticLambda0;
        LetterboxEduDialogLayout$$ExternalSyntheticLambda1 letterboxEduDialogLayout$$ExternalSyntheticLambda1 = null;
        if (taskView$$ExternalSyntheticLambda5 == null) {
            letterboxEduDialogLayout$$ExternalSyntheticLambda0 = null;
        } else {
            letterboxEduDialogLayout$$ExternalSyntheticLambda0 = new LetterboxEduDialogLayout$$ExternalSyntheticLambda0(taskView$$ExternalSyntheticLambda5);
        }
        findViewById(C1777R.C1779id.letterbox_education_dialog_dismiss_button).setOnClickListener(letterboxEduDialogLayout$$ExternalSyntheticLambda0);
        setOnClickListener(letterboxEduDialogLayout$$ExternalSyntheticLambda0);
        View view = this.mDialogContainer;
        if (taskView$$ExternalSyntheticLambda5 != null) {
            letterboxEduDialogLayout$$ExternalSyntheticLambda1 = LetterboxEduDialogLayout$$ExternalSyntheticLambda1.INSTANCE;
        }
        view.setOnClickListener(letterboxEduDialogLayout$$ExternalSyntheticLambda1);
    }

    public LetterboxEduDialogLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LetterboxEduDialogLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDialogContainer = findViewById(C1777R.C1779id.letterbox_education_dialog_container);
        Drawable mutate = getBackground().mutate();
        this.mBackgroundDim = mutate;
        mutate.setAlpha(0);
    }

    public LetterboxEduDialogLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
