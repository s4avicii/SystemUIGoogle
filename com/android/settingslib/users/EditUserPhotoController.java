package com.android.settingslib.users;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.settingslib.drawable.CircleFramedDrawable;
import java.io.File;

public final class EditUserPhotoController {
    public final Activity mActivity;
    public final ActivityStarter mActivityStarter;
    public final String mFileAuthority;
    public final ImageView mImageView;
    public final File mImagesDir;
    public Bitmap mNewUserPhotoBitmap;
    public CircleFramedDrawable mNewUserPhotoDrawable;

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, String str) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        this.mFileAuthority = str;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(new EditUserPhotoController$$ExternalSyntheticLambda0(this));
        this.mNewUserPhotoBitmap = bitmap;
    }
}
