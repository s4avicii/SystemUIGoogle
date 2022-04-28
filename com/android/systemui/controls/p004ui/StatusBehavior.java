package com.android.systemui.controls.p004ui;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.service.controls.Control;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.StatusBehavior */
/* compiled from: StatusBehavior.kt */
public final class StatusBehavior implements Behavior {
    public ControlViewHolder cvh;

    public final void bind(ControlWithState controlWithState, int i) {
        int i2;
        int i3;
        Control control = controlWithState.control;
        if (control == null) {
            i2 = 0;
        } else {
            i2 = control.getStatus();
        }
        if (i2 == 2) {
            ControlViewHolder cvh2 = getCvh();
            Objects.requireNonNull(cvh2);
            cvh2.layout.setOnClickListener(new StatusBehavior$bind$msg$1(this, controlWithState));
            ControlViewHolder cvh3 = getCvh();
            Objects.requireNonNull(cvh3);
            cvh3.layout.setOnLongClickListener(new StatusBehavior$bind$msg$2(this, controlWithState));
            i3 = C1777R.string.controls_error_removed;
        } else if (i2 == 3) {
            i3 = C1777R.string.controls_error_generic;
        } else if (i2 != 4) {
            ControlViewHolder cvh4 = getCvh();
            Objects.requireNonNull(cvh4);
            cvh4.isLoading = true;
            i3 = 17040572;
        } else {
            i3 = C1777R.string.controls_error_timeout;
        }
        ControlViewHolder cvh5 = getCvh();
        ControlViewHolder cvh6 = getCvh();
        Objects.requireNonNull(cvh6);
        cvh5.setStatusText(cvh6.context.getString(i3), false);
        getCvh().mo7819x3918d5b8(false, i, true);
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public static final void access$showNotFoundDialog(StatusBehavior statusBehavior, ControlViewHolder controlViewHolder, ControlWithState controlWithState) {
        Objects.requireNonNull(statusBehavior);
        Objects.requireNonNull(controlViewHolder);
        PackageManager packageManager = controlViewHolder.context.getPackageManager();
        Objects.requireNonNull(controlWithState);
        CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(controlWithState.componentName.getPackageName(), 128));
        AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder.context, 16974545);
        Resources resources = controlViewHolder.context.getResources();
        builder.setTitle(resources.getString(C1777R.string.controls_error_removed_title));
        builder.setMessage(resources.getString(C1777R.string.controls_error_removed_message, new Object[]{controlViewHolder.title.getText(), applicationLabel}));
        builder.setPositiveButton(C1777R.string.controls_open_app, new StatusBehavior$showNotFoundDialog$builder$1$1(controlWithState, builder, controlViewHolder));
        builder.setNegativeButton(17039360, StatusBehavior$showNotFoundDialog$builder$1$2.INSTANCE);
        AlertDialog create = builder.create();
        create.getWindow().setType(2020);
        create.show();
        controlViewHolder.visibleDialog = create;
    }

    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
