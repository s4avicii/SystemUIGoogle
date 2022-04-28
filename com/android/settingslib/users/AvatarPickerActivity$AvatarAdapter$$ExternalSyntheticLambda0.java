package com.android.settingslib.users;

import android.view.View;
import com.android.settingslib.users.AvatarPickerActivity;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ AvatarPickerActivity.AvatarAdapter f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0(AvatarPickerActivity.AvatarAdapter avatarAdapter, int i) {
        this.f$0 = avatarAdapter;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        AvatarPickerActivity.AvatarAdapter avatarAdapter = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(avatarAdapter);
        int i2 = avatarAdapter.mSelectedPosition;
        if (i2 == i) {
            avatarAdapter.mSelectedPosition = -1;
            avatarAdapter.notifyItemChanged(i);
            AvatarPickerActivity.this.mDoneButton.setEnabled(false);
            return;
        }
        avatarAdapter.mSelectedPosition = i;
        avatarAdapter.notifyItemChanged(i);
        if (i2 != -1) {
            avatarAdapter.notifyItemChanged(i2);
        } else {
            AvatarPickerActivity.this.mDoneButton.setEnabled(true);
        }
    }
}
