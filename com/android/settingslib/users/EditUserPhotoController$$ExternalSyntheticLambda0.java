package com.android.settingslib.users;

import android.content.Intent;
import android.view.View;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EditUserPhotoController$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ EditUserPhotoController f$0;

    public /* synthetic */ EditUserPhotoController$$ExternalSyntheticLambda0(EditUserPhotoController editUserPhotoController) {
        this.f$0 = editUserPhotoController;
    }

    public final void onClick(View view) {
        EditUserPhotoController editUserPhotoController = this.f$0;
        Objects.requireNonNull(editUserPhotoController);
        Intent intent = new Intent(editUserPhotoController.mImageView.getContext(), AvatarPickerActivity.class);
        intent.putExtra("file_authority", editUserPhotoController.mFileAuthority);
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = (CreateUserActivity$$ExternalSyntheticLambda0) editUserPhotoController.mActivityStarter;
        Objects.requireNonNull(createUserActivity$$ExternalSyntheticLambda0);
        CreateUserActivity createUserActivity = createUserActivity$$ExternalSyntheticLambda0.f$0;
        int i = CreateUserActivity.$r8$clinit;
        Objects.requireNonNull(createUserActivity);
        EditUserInfoController editUserInfoController = createUserActivity.mEditUserInfoController;
        Objects.requireNonNull(editUserInfoController);
        editUserInfoController.mWaitingForActivityResult = true;
        createUserActivity.startActivityForResult(intent, 1004);
    }
}
