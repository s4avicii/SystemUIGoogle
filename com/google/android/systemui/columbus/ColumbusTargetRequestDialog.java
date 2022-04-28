package com.google.android.systemui.columbus;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.wallet.p011ui.WalletScreenController$$ExternalSyntheticLambda0;
import com.google.android.systemui.columbus.C2193x675ce2c1;

public final class ColumbusTargetRequestDialog extends SystemUIDialog {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mContent;
    public Button mNegativeButton;
    public Button mPositiveButton;
    public TextView mTitle;

    public final void setMessage(CharSequence charSequence) {
        this.mContent.setText(charSequence);
    }

    public final void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButton.setText(C1777R.string.columbus_target_request_dialog_deny);
        this.mNegativeButton.setOnClickListener(new ColumbusTargetRequestDialog$$ExternalSyntheticLambda0(this, (C2193x675ce2c1.C21941) onClickListener));
    }

    public final void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveButton.setText(i);
        this.mPositiveButton.setOnClickListener(new WalletScreenController$$ExternalSyntheticLambda0(this, onClickListener, 1));
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.columbus_target_request_dialog);
        this.mTitle = (TextView) requireViewById(C1777R.C1779id.title);
        this.mContent = (TextView) requireViewById(C1777R.C1779id.content);
        this.mPositiveButton = (Button) requireViewById(C1777R.C1779id.positive_button);
        this.mNegativeButton = (Button) requireViewById(C1777R.C1779id.negative_button);
    }

    public final void setTitle(CharSequence charSequence) {
        getWindow().setTitle(charSequence);
        getWindow().getAttributes().setTitle(charSequence);
        this.mTitle.setText(charSequence);
    }

    public ColumbusTargetRequestDialog(Context context) {
        super(context);
    }
}
