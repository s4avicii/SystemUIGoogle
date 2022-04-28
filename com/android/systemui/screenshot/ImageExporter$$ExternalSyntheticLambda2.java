package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageExporter$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ImageExporter f$0;
    public final /* synthetic */ File f$1;
    public final /* synthetic */ Bitmap f$2;
    public final /* synthetic */ CallbackToFutureAdapter.Completer f$3;

    public /* synthetic */ ImageExporter$$ExternalSyntheticLambda2(ImageExporter imageExporter, File file, Bitmap bitmap, CallbackToFutureAdapter.Completer completer) {
        this.f$0 = imageExporter;
        this.f$1 = file;
        this.f$2 = bitmap;
        this.f$3 = completer;
    }

    public final void run() {
        FileOutputStream fileOutputStream;
        ImageExporter imageExporter = this.f$0;
        File file = this.f$1;
        Bitmap bitmap = this.f$2;
        CallbackToFutureAdapter.Completer completer = this.f$3;
        Objects.requireNonNull(imageExporter);
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(imageExporter.mCompressFormat, imageExporter.mQuality, fileOutputStream);
            completer.set(file);
            fileOutputStream.close();
            return;
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
            completer.setException(e);
            return;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }
}
