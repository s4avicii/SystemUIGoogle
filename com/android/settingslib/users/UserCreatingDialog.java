package com.android.settingslib.users;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public final class UserCreatingDialog extends AlertDialog {
    public UserCreatingDialog(Context context) {
        super(context, 16974546);
        setCancelable(false);
        View inflate = LayoutInflater.from(getContext()).inflate(C1777R.layout.user_creation_progress_dialog, (ViewGroup) null);
        String string = getContext().getString(C1777R.string.creating_new_user_dialog_message);
        inflate.setAccessibilityPaneTitle(string);
        ((TextView) inflate.findViewById(C1777R.C1779id.message)).setText(string);
        setView(inflate);
        getWindow().setType(2010);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
    }
}
