package com.android.settingslib.users;

import android.content.DialogInterface;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EditUserInfoController$$ExternalSyntheticLambda0 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ EditUserInfoController f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ EditUserInfoController$$ExternalSyntheticLambda0(EditUserInfoController editUserInfoController, CreateUserActivity$$ExternalSyntheticLambda1 createUserActivity$$ExternalSyntheticLambda1) {
        this.f$0 = editUserInfoController;
        this.f$1 = createUserActivity$$ExternalSyntheticLambda1;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        EditUserInfoController editUserInfoController = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(editUserInfoController);
        editUserInfoController.clear();
        if (runnable != null) {
            runnable.run();
        }
    }
}
