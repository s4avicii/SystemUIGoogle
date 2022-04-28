package com.android.systemui.user;

import android.graphics.drawable.Drawable;
import androidx.recyclerview.R$dimen;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.users.UserCreatingDialog;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CreateUserActivity$$ExternalSyntheticLambda3 implements BiConsumer {
    public final /* synthetic */ CreateUserActivity f$0;

    public /* synthetic */ CreateUserActivity$$ExternalSyntheticLambda3(CreateUserActivity createUserActivity) {
        this.f$0 = createUserActivity;
    }

    public final void accept(Object obj, Object obj2) {
        CreateUserActivity createUserActivity = this.f$0;
        String str = (String) obj;
        Drawable drawable = (Drawable) obj2;
        int i = CreateUserActivity.$r8$clinit;
        Objects.requireNonNull(createUserActivity);
        createUserActivity.mSetupUserDialog.dismiss();
        if (str == null || str.trim().isEmpty()) {
            str = createUserActivity.getString(C1777R.string.user_new_user_name);
        }
        UserCreator userCreator = createUserActivity.mUserCreator;
        CreateUserActivity$$ExternalSyntheticLambda4 createUserActivity$$ExternalSyntheticLambda4 = new CreateUserActivity$$ExternalSyntheticLambda4(createUserActivity, 0);
        CreateUserActivity$$ExternalSyntheticLambda2 createUserActivity$$ExternalSyntheticLambda2 = new CreateUserActivity$$ExternalSyntheticLambda2(createUserActivity, 0);
        Objects.requireNonNull(userCreator);
        UserCreatingDialog userCreatingDialog = new UserCreatingDialog(userCreator.mContext);
        userCreatingDialog.show();
        R$dimen.postOnMainThread(new UserCreator$$ExternalSyntheticLambda0(userCreator, str, userCreatingDialog, createUserActivity$$ExternalSyntheticLambda2, drawable, createUserActivity$$ExternalSyntheticLambda4));
    }
}
