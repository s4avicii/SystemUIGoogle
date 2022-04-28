package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import com.android.launcher3.icons.RoundDrawableWrapper;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda1;
import com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda0;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda3;
import com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda7;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BcSmartspaceCardDoorbell extends BcSmartspaceCardGenericImage {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mGifFrameDurationInMs = 200;
    public final HashMap mUriToDrawable = new HashMap();

    public static class DrawableWithUri extends RoundDrawableWrapper {
        public ContentResolver mContentResolver;
        public Drawable mDrawable;
        public int mHeightInPx;
        public Uri mUri;

        public DrawableWithUri(Uri uri, ContentResolver contentResolver, int i, float f) {
            super(new ColorDrawable(0), f);
            this.mUri = uri;
            this.mHeightInPx = i;
            this.mContentResolver = contentResolver;
        }
    }

    public static class LoadUriTask extends AsyncTask<DrawableWithUri, Void, DrawableWithUri> {
        public final Object doInBackground(Object[] objArr) {
            DrawableWithUri[] drawableWithUriArr = (DrawableWithUri[]) objArr;
            Drawable drawable = null;
            if (drawableWithUriArr.length <= 0) {
                return null;
            }
            DrawableWithUri drawableWithUri = drawableWithUriArr[0];
            try {
                InputStream openInputStream = drawableWithUri.mContentResolver.openInputStream(drawableWithUri.mUri);
                int i = drawableWithUri.mHeightInPx;
                int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                try {
                    drawable = ImageDecoder.decodeDrawable(ImageDecoder.createSource((Resources) null, openInputStream), new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0(i));
                } catch (IOException e) {
                    Log.e("BcSmartspaceCardBell", "Unable to decode stream: " + e);
                }
                drawableWithUri.mDrawable = drawable;
            } catch (Exception e2) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("open uri:");
                m.append(drawableWithUri.mUri);
                m.append(" got exception:");
                m.append(e2);
                Log.w("BcSmartspaceCardBell", m.toString());
            }
            return drawableWithUri;
        }

        public final void onPostExecute(Object obj) {
            Drawable drawable;
            DrawableWithUri drawableWithUri = (DrawableWithUri) obj;
            if (drawableWithUri != null && (drawable = drawableWithUri.mDrawable) != null) {
                drawableWithUri.setDrawable(drawable);
            }
        }
    }

    public BcSmartspaceCardDoorbell(Context context) {
        super(context);
    }

    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        Bundle bundle;
        if (!getContext().getPackageName().equals(ThemeOverlayApplier.SYSUI_PACKAGE)) {
            return false;
        }
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        if (baseAction == null) {
            bundle = null;
        } else {
            bundle = baseAction.getExtras();
        }
        List list = (List) smartspaceTarget.getIconGrid().stream().filter(ThemeOverlayApplier$$ExternalSyntheticLambda7.INSTANCE$2).map(ThemeOverlayApplier$$ExternalSyntheticLambda3.INSTANCE$1).map(WMShellBaseModule$$ExternalSyntheticLambda1.INSTANCE$1).collect(Collectors.toList());
        if (!list.isEmpty()) {
            if (bundle != null && bundle.containsKey("frameDurationMs")) {
                this.mGifFrameDurationInMs = bundle.getInt("frameDurationMs");
            }
            ContentResolver contentResolver = getContext().getApplicationContext().getContentResolver();
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(C1777R.dimen.enhanced_smartspace_height);
            float dimension = getResources().getDimension(C1777R.dimen.enhanced_smartspace_secondary_card_corner_radius);
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (Drawable addFrame : (List) list.stream().map(new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(this, contentResolver, dimensionPixelOffset, dimension)).filter(TvOngoingPrivacyChip$$ExternalSyntheticLambda0.INSTANCE$1).collect(Collectors.toList())) {
                animationDrawable.addFrame(addFrame, this.mGifFrameDurationInMs);
            }
            this.mImageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
            Log.d("BcSmartspaceCardBell", "imageUri is set");
            return true;
        } else if (bundle == null || !bundle.containsKey("imageBitmap")) {
            return false;
        } else {
            Bitmap bitmap = (Bitmap) bundle.get("imageBitmap");
            if (bitmap.getHeight() != 0) {
                int dimension2 = (int) getResources().getDimension(C1777R.dimen.enhanced_smartspace_height);
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (((float) dimension2) * (((float) bitmap.getWidth()) / ((float) bitmap.getHeight()))), dimension2, true);
            }
            RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(getResources(), bitmap);
            roundedBitmapDrawable21.setCornerRadius(getResources().getDimension(C1777R.dimen.enhanced_smartspace_secondary_card_corner_radius));
            this.mImageView.setImageDrawable(roundedBitmapDrawable21);
            Log.d("BcSmartspaceCardBell", "imageBitmap is set");
            return true;
        }
    }

    public BcSmartspaceCardDoorbell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
    }
}
