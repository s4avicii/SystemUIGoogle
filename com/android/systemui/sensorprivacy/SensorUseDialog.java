package com.android.systemui.sensorprivacy;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.android.internal.widget.DialogTitle;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SensorUseDialog.kt */
public final class SensorUseDialog extends SystemUIDialog {
    public SensorUseDialog(Context context, int i, DialogInterface.OnClickListener onClickListener, DialogInterface.OnDismissListener onDismissListener) {
        super(context);
        int i2;
        int i3;
        int i4;
        Window window = getWindow();
        Intrinsics.checkNotNull(window);
        window.addFlags(524288);
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        window2.addSystemFlags(524288);
        View inflate = LayoutInflater.from(context).inflate(C1777R.layout.sensor_use_started_title, (ViewGroup) null);
        DialogTitle requireViewById = inflate.requireViewById(C1777R.C1779id.sensor_use_started_title_message);
        if (i == 1) {
            i2 = C1777R.string.sensor_privacy_start_use_mic_dialog_title;
        } else if (i == 2) {
            i2 = C1777R.string.sensor_privacy_start_use_camera_dialog_title;
        } else if (i != Integer.MAX_VALUE) {
            i2 = 0;
        } else {
            i2 = C1777R.string.sensor_privacy_start_use_mic_camera_dialog_title;
        }
        requireViewById.setText(i2);
        ImageView imageView = (ImageView) inflate.requireViewById(C1777R.C1779id.sensor_use_microphone_icon);
        int i5 = 8;
        if (i == 1 || i == Integer.MAX_VALUE) {
            i3 = 0;
        } else {
            i3 = 8;
        }
        imageView.setVisibility(i3);
        ((ImageView) inflate.requireViewById(C1777R.C1779id.sensor_use_camera_icon)).setVisibility((i == 2 || i == Integer.MAX_VALUE) ? 0 : i5);
        setCustomTitle(inflate);
        if (i == 1) {
            i4 = C1777R.string.sensor_privacy_start_use_mic_dialog_content;
        } else if (i == 2) {
            i4 = C1777R.string.sensor_privacy_start_use_camera_dialog_content;
        } else if (i != Integer.MAX_VALUE) {
            i4 = 0;
        } else {
            i4 = C1777R.string.sensor_privacy_start_use_mic_camera_dialog_content;
        }
        setMessage(Html.fromHtml(context.getString(i4), 0));
        setButton(-1, context.getString(17041455), onClickListener);
        setButton(-2, context.getString(17039360), onClickListener);
        setOnDismissListener(onDismissListener);
        setCancelable(false);
    }
}
