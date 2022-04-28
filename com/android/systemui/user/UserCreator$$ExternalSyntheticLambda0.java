package com.android.systemui.user;

import android.app.Dialog;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.android.internal.util.UserIcons;
import com.android.settingslib.users.UserCreatingDialog;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UserCreator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UserCreator f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Dialog f$2;
    public final /* synthetic */ Runnable f$3;
    public final /* synthetic */ Drawable f$4;
    public final /* synthetic */ Consumer f$5;

    public /* synthetic */ UserCreator$$ExternalSyntheticLambda0(UserCreator userCreator, String str, UserCreatingDialog userCreatingDialog, CreateUserActivity$$ExternalSyntheticLambda2 createUserActivity$$ExternalSyntheticLambda2, Drawable drawable, CreateUserActivity$$ExternalSyntheticLambda4 createUserActivity$$ExternalSyntheticLambda4) {
        this.f$0 = userCreator;
        this.f$1 = str;
        this.f$2 = userCreatingDialog;
        this.f$3 = createUserActivity$$ExternalSyntheticLambda2;
        this.f$4 = drawable;
        this.f$5 = createUserActivity$$ExternalSyntheticLambda4;
    }

    public final void run() {
        UserCreator userCreator = this.f$0;
        String str = this.f$1;
        Dialog dialog = this.f$2;
        Runnable runnable = this.f$3;
        Drawable drawable = this.f$4;
        Consumer consumer = this.f$5;
        Objects.requireNonNull(userCreator);
        UserInfo createUser = userCreator.mUserManager.createUser(str, "android.os.usertype.full.SECONDARY", 0);
        if (createUser == null) {
            dialog.dismiss();
            runnable.run();
            return;
        }
        Resources resources = userCreator.mContext.getResources();
        if (drawable == null) {
            drawable = UserIcons.getDefaultUserIcon(resources, createUser.id, false);
        }
        userCreator.mUserManager.setUserIcon(createUser.id, UserIcons.convertToBitmapAtUserIconSize(resources, drawable));
        dialog.dismiss();
        consumer.accept(createUser);
    }
}
