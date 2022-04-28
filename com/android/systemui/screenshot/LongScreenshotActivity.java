package com.android.systemui.screenshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5;
import com.android.systemui.p006qs.QSPanel$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.wallet.p011ui.WalletView$$ExternalSyntheticLambda0;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public class LongScreenshotActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor mBackgroundExecutor;
    public CallbackToFutureAdapter.SafeFuture mCacheLoadFuture;
    public CallbackToFutureAdapter.SafeFuture mCacheSaveFuture;
    public View mCancel;
    public CropView mCropView;
    public View mEdit;
    public ImageView mEnterTransitionView;
    public final ImageExporter mImageExporter;
    public ScrollCaptureController.LongScreenshot mLongScreenshot;
    public final LongScreenshotData mLongScreenshotHolder;
    public MagnifierView mMagnifierView;
    public Bitmap mOutputBitmap;
    public ImageView mPreview;
    public View mSave;
    public File mSavedImagePath;
    public ScrollCaptureResponse mScrollCaptureResponse;
    public View mShare;
    public boolean mTransitionStarted;
    public ImageView mTransitionView;
    public final UiEventLogger mUiEventLogger;
    public final Executor mUiExecutor;

    public enum PendingAction {
        SHARE,
        EDIT,
        SAVE
    }

    public final void onCachedImageLoaded(ImageLoader$Result imageLoader$Result) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_CACHED_IMAGE_LOADED);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), imageLoader$Result.bitmap);
        this.mPreview.setImageDrawable(bitmapDrawable);
        this.mPreview.setAlpha(1.0f);
        MagnifierView magnifierView = this.mMagnifierView;
        int width = imageLoader$Result.bitmap.getWidth();
        int height = imageLoader$Result.bitmap.getHeight();
        Objects.requireNonNull(magnifierView);
        magnifierView.mDrawable = bitmapDrawable;
        bitmapDrawable.setBounds(0, 0, width, height);
        magnifierView.invalidate();
        this.mCropView.setVisibility(0);
        this.mSavedImagePath = imageLoader$Result.fileName;
        setButtonsEnabled(true);
    }

    public final void setButtonsEnabled(boolean z) {
        this.mSave.setEnabled(z);
        this.mEdit.setEnabled(z);
        this.mShare.setEnabled(z);
    }

    public final void startExport(PendingAction pendingAction) {
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            Log.e("Screenshot", "No drawable, skipping export!");
            return;
        }
        CropView cropView = this.mCropView;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Objects.requireNonNull(cropView);
        RectF rectF = cropView.mCrop;
        float f = (float) intrinsicWidth;
        float f2 = (float) intrinsicHeight;
        Rect rect = new Rect((int) (rectF.left * f), (int) (rectF.top * f2), (int) (rectF.right * f), (int) (rectF.bottom * f2));
        if (rect.isEmpty()) {
            Log.w("Screenshot", "Crop bounds empty, skipping export.");
            return;
        }
        updateImageDimensions();
        RenderNode renderNode = new RenderNode("Bitmap Export");
        renderNode.setPosition(0, 0, rect.width(), rect.height());
        RecordingCanvas beginRecording = renderNode.beginRecording();
        beginRecording.translate((float) (-rect.left), (float) (-rect.top));
        beginRecording.clipRect(rect);
        drawable.draw(beginRecording);
        renderNode.endRecording();
        this.mOutputBitmap = HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height());
        ImageExporter imageExporter = this.mImageExporter;
        Executor executor = this.mBackgroundExecutor;
        UUID randomUUID = UUID.randomUUID();
        Bitmap bitmap = this.mOutputBitmap;
        ZonedDateTime now = ZonedDateTime.now();
        Objects.requireNonNull(imageExporter);
        CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ImageExporter$$ExternalSyntheticLambda1(executor, new ImageExporter.Task(imageExporter.mResolver, randomUUID, bitmap, now, imageExporter.mCompressFormat, imageExporter.mQuality)));
        future.delegate.addListener(new LongScreenshotActivity$$ExternalSyntheticLambda1(this, pendingAction, future), this.mUiExecutor);
    }

    public final void updateImageDimensions() {
        float f;
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable != null) {
            Rect bounds = drawable.getBounds();
            float width = ((float) bounds.width()) / ((float) bounds.height());
            int width2 = (this.mPreview.getWidth() - this.mPreview.getPaddingLeft()) - this.mPreview.getPaddingRight();
            int height = (this.mPreview.getHeight() - this.mPreview.getPaddingTop()) - this.mPreview.getPaddingBottom();
            float f2 = (float) width2;
            float f3 = (float) height;
            float f4 = f2 / f3;
            int paddingLeft = this.mPreview.getPaddingLeft();
            int paddingTop = this.mPreview.getPaddingTop();
            int i = 0;
            if (width > f4) {
                int i2 = (int) ((f4 * f3) / width);
                i = (height - i2) / 2;
                CropView cropView = this.mCropView;
                Objects.requireNonNull(cropView);
                cropView.mExtraTopPadding = this.mPreview.getPaddingTop() + i;
                cropView.mExtraBottomPadding = this.mPreview.getPaddingBottom() + i;
                cropView.invalidate();
                paddingTop += i;
                CropView cropView2 = this.mCropView;
                Objects.requireNonNull(cropView2);
                cropView2.mExtraTopPadding = i;
                cropView2.mExtraBottomPadding = i;
                cropView2.invalidate();
                CropView cropView3 = this.mCropView;
                Objects.requireNonNull(cropView3);
                cropView3.mImageWidth = width2;
                cropView3.invalidate();
                f = f2 / ((float) this.mPreview.getDrawable().getIntrinsicWidth());
                height = i2;
            } else {
                int i3 = (int) ((f2 * width) / f4);
                paddingLeft += (width2 - i3) / 2;
                CropView cropView4 = this.mCropView;
                int paddingTop2 = this.mPreview.getPaddingTop();
                int paddingBottom = this.mPreview.getPaddingBottom();
                Objects.requireNonNull(cropView4);
                cropView4.mExtraTopPadding = paddingTop2;
                cropView4.mExtraBottomPadding = paddingBottom;
                cropView4.invalidate();
                CropView cropView5 = this.mCropView;
                Objects.requireNonNull(cropView5);
                cropView5.mImageWidth = (int) (width * f3);
                cropView5.invalidate();
                int i4 = i3;
                f = f3 / ((float) this.mPreview.getDrawable().getIntrinsicHeight());
                width2 = i4;
            }
            CropView cropView6 = this.mCropView;
            Objects.requireNonNull(cropView6);
            RectF rectF = cropView6.mCrop;
            float f5 = (float) width2;
            float f6 = (float) height;
            Rect rect = new Rect((int) (rectF.left * f5), (int) (rectF.top * f6), (int) (rectF.right * f5), (int) (rectF.bottom * f6));
            this.mTransitionView.setTranslationX((float) (paddingLeft + rect.left));
            this.mTransitionView.setTranslationY((float) (paddingTop + rect.top));
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTransitionView.getLayoutParams();
            layoutParams.width = rect.width();
            layoutParams.height = rect.height();
            this.mTransitionView.setLayoutParams(layoutParams);
            if (this.mLongScreenshot != null) {
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mEnterTransitionView.getLayoutParams();
                ScrollCaptureController.LongScreenshot longScreenshot = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot);
                ImageTileSet imageTileSet = longScreenshot.mImageTileSet;
                Objects.requireNonNull(imageTileSet);
                ScrollCaptureController.LongScreenshot longScreenshot2 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot2);
                float max = Math.max(0.0f, ((float) (-imageTileSet.mRegion.getBounds().top)) / ((float) longScreenshot2.mImageTileSet.getHeight()));
                layoutParams2.width = (int) (((float) drawable.getIntrinsicWidth()) * f);
                ScrollCaptureController.LongScreenshot longScreenshot3 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot3);
                layoutParams2.height = (int) (((float) longScreenshot3.mSession.getPageHeight()) * f);
                this.mEnterTransitionView.setLayoutParams(layoutParams2);
                Matrix matrix = new Matrix();
                matrix.setScale(f, f);
                matrix.postTranslate(0.0f, (-f) * ((float) drawable.getIntrinsicHeight()) * max);
                this.mEnterTransitionView.setImageMatrix(matrix);
                this.mEnterTransitionView.setTranslationY((max * f3) + ((float) this.mPreview.getPaddingTop()) + ((float) i));
            }
        }
    }

    public static void $r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity longScreenshotActivity, View view) {
        Objects.requireNonNull(longScreenshotActivity);
        int id = view.getId();
        view.setPressed(true);
        longScreenshotActivity.setButtonsEnabled(false);
        if (id == C1777R.C1779id.save) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SAVED);
            longScreenshotActivity.startExport(PendingAction.SAVE);
        } else if (id == C1777R.C1779id.edit) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EDIT);
            longScreenshotActivity.startExport(PendingAction.EDIT);
        } else if (id == C1777R.C1779id.share) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SHARE);
            longScreenshotActivity.startExport(PendingAction.SHARE);
        } else if (id == C1777R.C1779id.cancel) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EXIT);
            longScreenshotActivity.finishAndRemoveTask();
        }
    }

    public LongScreenshotActivity(UiEventLogger uiEventLogger, ImageExporter imageExporter, Executor executor, Executor executor2, LongScreenshotData longScreenshotData) {
        this.mUiEventLogger = uiEventLogger;
        this.mUiExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mImageExporter = imageExporter;
        this.mLongScreenshotHolder = longScreenshotData;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.long_screenshot);
        this.mPreview = (ImageView) requireViewById(C1777R.C1779id.preview);
        this.mSave = requireViewById(C1777R.C1779id.save);
        this.mEdit = requireViewById(C1777R.C1779id.edit);
        this.mShare = requireViewById(C1777R.C1779id.share);
        this.mCancel = requireViewById(C1777R.C1779id.cancel);
        this.mCropView = (CropView) requireViewById(C1777R.C1779id.crop_view);
        MagnifierView magnifierView = (MagnifierView) requireViewById(C1777R.C1779id.magnifier);
        this.mMagnifierView = magnifierView;
        CropView cropView = this.mCropView;
        Objects.requireNonNull(cropView);
        cropView.mCropInteractionListener = magnifierView;
        this.mTransitionView = (ImageView) requireViewById(C1777R.C1779id.transition);
        this.mEnterTransitionView = (ImageView) requireViewById(C1777R.C1779id.enter_transition);
        this.mSave.setOnClickListener(new WalletView$$ExternalSyntheticLambda0(this, 2));
        this.mCancel.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda5(this, 3));
        this.mEdit.setOnClickListener(new LayoutPreference$$ExternalSyntheticLambda0(this, 3));
        this.mShare.setOnClickListener(new LongScreenshotActivity$$ExternalSyntheticLambda0(this, 0));
        this.mPreview.addOnLayoutChangeListener(new QSPanel$$ExternalSyntheticLambda0(this, 1));
        this.mScrollCaptureResponse = getIntent().getParcelableExtra("capture-response");
        if (bundle != null) {
            String string = bundle.getString("saved-image-path");
            if (string == null) {
                Log.e("Screenshot", "Missing saved state entry with key 'saved-image-path'!");
                finishAndRemoveTask();
                return;
            }
            this.mSavedImagePath = new File(string);
            getContentResolver();
            this.mCacheLoadFuture = CallbackToFutureAdapter.getFuture(new ImageLoader$$ExternalSyntheticLambda0(this.mSavedImagePath));
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        File file = this.mSavedImagePath;
        if (file != null) {
            bundle.putString("saved-image-path", file.getPath());
        }
    }

    public final void onStart() {
        super.onStart();
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_STARTED);
        if (this.mPreview.getDrawable() == null) {
            if (this.mCacheLoadFuture != null) {
                Log.d("Screenshot", "mCacheLoadFuture != null");
                CallbackToFutureAdapter.SafeFuture safeFuture = this.mCacheLoadFuture;
                ScreenRecordTile$$ExternalSyntheticLambda1 screenRecordTile$$ExternalSyntheticLambda1 = new ScreenRecordTile$$ExternalSyntheticLambda1(this, safeFuture, 2);
                Executor executor = this.mUiExecutor;
                Objects.requireNonNull(safeFuture);
                safeFuture.delegate.addListener(screenRecordTile$$ExternalSyntheticLambda1, executor);
                this.mCacheLoadFuture = null;
                return;
            }
            LongScreenshotData longScreenshotData = this.mLongScreenshotHolder;
            Objects.requireNonNull(longScreenshotData);
            ScrollCaptureController.LongScreenshot andSet = longScreenshotData.mLongScreenshot.getAndSet((Object) null);
            if (andSet != null) {
                Log.i("Screenshot", "Completed: " + andSet);
                this.mLongScreenshot = andSet;
                ImageTileSet imageTileSet = andSet.mImageTileSet;
                Objects.requireNonNull(imageTileSet);
                TiledImageDrawable tiledImageDrawable = new TiledImageDrawable(imageTileSet);
                this.mPreview.setImageDrawable(tiledImageDrawable);
                MagnifierView magnifierView = this.mMagnifierView;
                ScrollCaptureController.LongScreenshot longScreenshot = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot);
                ImageTileSet imageTileSet2 = longScreenshot.mImageTileSet;
                Objects.requireNonNull(imageTileSet2);
                TiledImageDrawable tiledImageDrawable2 = new TiledImageDrawable(imageTileSet2);
                ScrollCaptureController.LongScreenshot longScreenshot2 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot2);
                int width = longScreenshot2.mImageTileSet.getWidth();
                ScrollCaptureController.LongScreenshot longScreenshot3 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot3);
                int height = longScreenshot3.mImageTileSet.getHeight();
                Objects.requireNonNull(magnifierView);
                magnifierView.mDrawable = tiledImageDrawable2;
                tiledImageDrawable2.setBounds(0, 0, width, height);
                magnifierView.invalidate();
                ScrollCaptureController.LongScreenshot longScreenshot4 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot4);
                ImageTileSet imageTileSet3 = longScreenshot4.mImageTileSet;
                Objects.requireNonNull(imageTileSet3);
                ScrollCaptureController.LongScreenshot longScreenshot5 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot5);
                final float max = Math.max(0.0f, ((float) (-imageTileSet3.mRegion.getBounds().top)) / ((float) longScreenshot5.mImageTileSet.getHeight()));
                ScrollCaptureController.LongScreenshot longScreenshot6 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot6);
                ImageTileSet imageTileSet4 = longScreenshot6.mImageTileSet;
                Objects.requireNonNull(imageTileSet4);
                int i = imageTileSet4.mRegion.getBounds().bottom;
                ScrollCaptureController.LongScreenshot longScreenshot7 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot7);
                ScrollCaptureController.LongScreenshot longScreenshot8 = this.mLongScreenshot;
                Objects.requireNonNull(longScreenshot8);
                final float min = Math.min(1.0f, 1.0f - (((float) (i - longScreenshot7.mSession.getPageHeight())) / ((float) longScreenshot8.mImageTileSet.getHeight())));
                this.mEnterTransitionView.setImageDrawable(tiledImageDrawable);
                this.mEnterTransitionView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public final boolean onPreDraw() {
                        LongScreenshotActivity.this.mEnterTransitionView.getViewTreeObserver().removeOnPreDrawListener(this);
                        LongScreenshotActivity.this.updateImageDimensions();
                        LongScreenshotActivity.this.mEnterTransitionView.post(new LongScreenshotActivity$1$$ExternalSyntheticLambda1(this, max, min));
                        return true;
                    }
                });
                ImageExporter imageExporter = this.mImageExporter;
                Executor executor2 = this.mBackgroundExecutor;
                Bitmap bitmap = this.mLongScreenshot.toBitmap();
                File file = new File(getCacheDir(), "long_screenshot_cache.png");
                Objects.requireNonNull(imageExporter);
                CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ImageExporter$$ExternalSyntheticLambda0(imageExporter, executor2, file, bitmap));
                this.mCacheSaveFuture = future;
                future.delegate.addListener(new KeyguardUpdateMonitor$$ExternalSyntheticLambda8(this, 1), this.mUiExecutor);
                return;
            }
            Log.e("Screenshot", "No long screenshot available!");
            finishAndRemoveTask();
        }
    }

    public final void onStop() {
        super.onStop();
        if (this.mTransitionStarted) {
            finish();
        }
        if (isFinishing()) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_FINISHED);
            ScrollCaptureResponse scrollCaptureResponse = this.mScrollCaptureResponse;
            if (scrollCaptureResponse != null) {
                scrollCaptureResponse.close();
            }
            CallbackToFutureAdapter.SafeFuture safeFuture = this.mCacheSaveFuture;
            if (safeFuture != null) {
                safeFuture.cancel(true);
            }
            File file = this.mSavedImagePath;
            if (file != null) {
                file.delete();
                this.mSavedImagePath = null;
            }
            ScrollCaptureController.LongScreenshot longScreenshot = this.mLongScreenshot;
            if (longScreenshot != null) {
                longScreenshot.mImageTileSet.clear();
                longScreenshot.mSession.release();
            }
        }
    }
}
