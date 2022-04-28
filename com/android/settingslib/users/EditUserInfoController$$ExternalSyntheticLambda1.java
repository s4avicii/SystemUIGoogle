package com.android.settingslib.users;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda3;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EditUserInfoController$$ExternalSyntheticLambda1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ EditUserInfoController f$0;
    public final /* synthetic */ Drawable f$1 = null;
    public final /* synthetic */ EditText f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ BiConsumer f$4;

    public /* synthetic */ EditUserInfoController$$ExternalSyntheticLambda1(EditUserInfoController editUserInfoController, EditText editText, String str, CreateUserActivity$$ExternalSyntheticLambda3 createUserActivity$$ExternalSyntheticLambda3) {
        this.f$0 = editUserInfoController;
        this.f$2 = editText;
        this.f$3 = str;
        this.f$4 = createUserActivity$$ExternalSyntheticLambda3;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        CircleFramedDrawable circleFramedDrawable;
        EditUserInfoController editUserInfoController = this.f$0;
        Drawable drawable = this.f$1;
        EditText editText = this.f$2;
        String str = this.f$3;
        BiConsumer biConsumer = this.f$4;
        Objects.requireNonNull(editUserInfoController);
        EditUserPhotoController editUserPhotoController = editUserInfoController.mEditUserPhotoController;
        if (editUserPhotoController != null) {
            circleFramedDrawable = editUserPhotoController.mNewUserPhotoDrawable;
        } else {
            circleFramedDrawable = null;
        }
        if (circleFramedDrawable != null) {
            drawable = circleFramedDrawable;
        }
        String trim = editText.getText().toString().trim();
        if (!trim.isEmpty()) {
            str = trim;
        }
        editUserInfoController.clear();
        if (biConsumer != null) {
            biConsumer.accept(str, drawable);
        }
    }
}
