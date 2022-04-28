package com.android.systemui.p006qs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFooterViewController$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ QSFooterViewController f$0;

    public /* synthetic */ QSFooterViewController$$ExternalSyntheticLambda1(QSFooterViewController qSFooterViewController) {
        this.f$0 = qSFooterViewController;
    }

    public final boolean onLongClick(View view) {
        QSFooterViewController qSFooterViewController = this.f$0;
        Objects.requireNonNull(qSFooterViewController);
        CharSequence text = qSFooterViewController.mBuildText.getText();
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        ((ClipboardManager) qSFooterViewController.mUserTracker.getUserContext().getSystemService(ClipboardManager.class)).setPrimaryClip(ClipData.newPlainText(qSFooterViewController.getResources().getString(C1777R.string.build_number_clip_data_label), text));
        Toast.makeText(qSFooterViewController.getContext(), C1777R.string.build_number_copy_toast, 0).show();
        return true;
    }
}
