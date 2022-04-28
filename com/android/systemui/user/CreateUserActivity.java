package com.android.systemui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.R$dimen;
import com.android.internal.util.UserIcons;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.users.EditUserInfoController;
import com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda0;
import com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda1;
import com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda2;
import com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda3;
import com.android.settingslib.users.EditUserPhotoController;
import com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class CreateUserActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityManager mActivityManager;
    public final EditUserInfoController mEditUserInfoController;
    public AlertDialog mSetupUserDialog;
    public final UserCreator mUserCreator;

    public final void onSaveInstanceState(Bundle bundle) {
        EditUserPhotoController editUserPhotoController;
        AlertDialog alertDialog = this.mSetupUserDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            bundle.putBundle("create_user_dialog_state", this.mSetupUserDialog.onSaveInstanceState());
        }
        EditUserInfoController editUserInfoController = this.mEditUserInfoController;
        Objects.requireNonNull(editUserInfoController);
        if (!(editUserInfoController.mEditUserInfoDialog == null || (editUserPhotoController = editUserInfoController.mEditUserPhotoController) == null)) {
            File file = null;
            if (editUserPhotoController.mNewUserPhotoBitmap != null) {
                try {
                    File file2 = new File(editUserPhotoController.mImagesDir, "NewUserPhoto.png");
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    editUserPhotoController.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    file = file2;
                } catch (IOException e) {
                    Log.e("EditUserPhotoController", "Cannot create temp file", e);
                }
            }
            if (file != null) {
                bundle.putString("pending_photo", file.getPath());
            }
        }
        bundle.putBoolean("awaiting_result", editUserInfoController.mWaitingForActivityResult);
        super.onSaveInstanceState(bundle);
    }

    public CreateUserActivity(UserCreator userCreator, EditUserInfoController editUserInfoController, IActivityManager iActivityManager) {
        this.mUserCreator = userCreator;
        this.mEditUserInfoController = editUserInfoController;
        this.mActivityManager = iActivityManager;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        EditUserInfoController editUserInfoController = this.mEditUserInfoController;
        Objects.requireNonNull(editUserInfoController);
        editUserInfoController.mWaitingForActivityResult = false;
        EditUserPhotoController editUserPhotoController = editUserInfoController.mEditUserPhotoController;
        if (editUserPhotoController != null && editUserInfoController.mEditUserInfoDialog != null && i2 == -1 && i == 1004) {
            if (intent.hasExtra("default_icon_tint_color")) {
                try {
                    R$dimen.postOnBackgroundThread(new OverviewProxyService$1$$ExternalSyntheticLambda3(editUserPhotoController, intent.getIntExtra("default_icon_tint_color", -1), 1)).get();
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("EditUserPhotoController", "Error processing default icon", e);
                }
            } else if (intent.getData() != null) {
                R$dimen.postOnBackgroundThread(new NavigationBarView$$ExternalSyntheticLambda0(editUserPhotoController, intent.getData(), 1));
            }
        }
    }

    public final void onBackPressed() {
        super.onBackPressed();
        AlertDialog alertDialog = this.mSetupUserDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setContentView(C1777R.layout.activity_create_new_user);
        if (bundle != null) {
            EditUserInfoController editUserInfoController = this.mEditUserInfoController;
            Objects.requireNonNull(editUserInfoController);
            String string = bundle.getString("pending_photo");
            if (string != null) {
                editUserInfoController.mSavedPhoto = BitmapFactory.decodeFile(new File(string).getAbsolutePath());
            }
            editUserInfoController.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
        }
        String string2 = getString(C1777R.string.user_new_user_name);
        EditUserInfoController editUserInfoController2 = this.mEditUserInfoController;
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        String string3 = getString(C1777R.string.user_add_user);
        CreateUserActivity$$ExternalSyntheticLambda3 createUserActivity$$ExternalSyntheticLambda3 = new CreateUserActivity$$ExternalSyntheticLambda3(this);
        CreateUserActivity$$ExternalSyntheticLambda1 createUserActivity$$ExternalSyntheticLambda1 = new CreateUserActivity$$ExternalSyntheticLambda1(this, 0);
        Objects.requireNonNull(editUserInfoController2);
        View inflate = LayoutInflater.from(this).inflate(C1777R.layout.edit_user_info_dialog_content, (ViewGroup) null);
        EditText editText = (EditText) inflate.findViewById(C1777R.C1779id.user_name);
        editText.setText(string2);
        ImageView imageView = (ImageView) inflate.findViewById(C1777R.C1779id.user_photo);
        Drawable defaultUserIcon = UserIcons.getDefaultUserIcon(getResources(), -10000, false);
        Bitmap bitmap = editUserInfoController2.mSavedPhoto;
        if (bitmap != null) {
            int i = CircleFramedDrawable.$r8$clinit;
            defaultUserIcon = new CircleFramedDrawable(bitmap, getResources().getDimensionPixelSize(17105621));
        }
        imageView.setImageDrawable(defaultUserIcon);
        if (editUserInfoController2.isChangePhotoRestrictedByBase(this)) {
            inflate.findViewById(C1777R.C1779id.add_a_photo_icon).setVisibility(8);
        } else {
            RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = editUserInfoController2.getChangePhotoAdminRestriction(this);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(new EditUserInfoController$$ExternalSyntheticLambda3(this, changePhotoAdminRestriction, 0));
            } else {
                editUserInfoController2.mEditUserPhotoController = editUserInfoController2.createEditUserPhotoController(this, createUserActivity$$ExternalSyntheticLambda0, imageView);
            }
        }
        AlertDialog create = new AlertDialog.Builder(this).setTitle(string3).setView(inflate).setCancelable(true).setPositiveButton(17039370, new EditUserInfoController$$ExternalSyntheticLambda1(editUserInfoController2, editText, string2, createUserActivity$$ExternalSyntheticLambda3)).setNegativeButton(17039360, new EditUserInfoController$$ExternalSyntheticLambda2(editUserInfoController2, createUserActivity$$ExternalSyntheticLambda1)).setOnCancelListener(new EditUserInfoController$$ExternalSyntheticLambda0(editUserInfoController2, createUserActivity$$ExternalSyntheticLambda1)).create();
        editUserInfoController2.mEditUserInfoDialog = create;
        create.getWindow().setSoftInputMode(4);
        AlertDialog alertDialog = editUserInfoController2.mEditUserInfoDialog;
        this.mSetupUserDialog = alertDialog;
        alertDialog.show();
    }

    public final void onRestoreInstanceState(Bundle bundle) {
        AlertDialog alertDialog;
        super.onRestoreInstanceState(bundle);
        Bundle bundle2 = bundle.getBundle("create_user_dialog_state");
        if (bundle2 != null && (alertDialog = this.mSetupUserDialog) != null) {
            alertDialog.onRestoreInstanceState(bundle2);
        }
    }
}
