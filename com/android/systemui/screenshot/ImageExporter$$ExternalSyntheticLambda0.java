package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageExporter$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageExporter f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ File f$2;
    public final /* synthetic */ Bitmap f$3;

    public /* synthetic */ ImageExporter$$ExternalSyntheticLambda0(ImageExporter imageExporter, Executor executor, File file, Bitmap bitmap) {
        this.f$0 = imageExporter;
        this.f$1 = executor;
        this.f$2 = file;
        this.f$3 = bitmap;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ImageExporter imageExporter = this.f$0;
        Executor executor = this.f$1;
        File file = this.f$2;
        Bitmap bitmap = this.f$3;
        Objects.requireNonNull(imageExporter);
        executor.execute(new ImageExporter$$ExternalSyntheticLambda2(imageExporter, file, bitmap, completer));
        return "Bitmap#compress";
    }
}
