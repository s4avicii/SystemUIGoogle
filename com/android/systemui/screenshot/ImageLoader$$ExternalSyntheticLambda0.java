package com.android.systemui.screenshot;

import android.graphics.BitmapFactory;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.onehanded.OneHandedTouchHandler;
import com.android.p012wm.shell.pip.phone.PipInputConsumer;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageLoader$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver, ExpandableNotificationRow.CoordinateOnClickListener, Bubbles.PendingIntentCanceledListener, OneHandedTouchHandler.OneHandedTouchEventCallback, PipInputConsumer.RegistrationListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ImageLoader$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        BufferedInputStream bufferedInputStream;
        File file = (File) this.f$0;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            ImageLoader$Result imageLoader$Result = new ImageLoader$Result();
            imageLoader$Result.fileName = file;
            imageLoader$Result.bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            completer.set(imageLoader$Result);
            bufferedInputStream.close();
            return "BitmapFactory#decodeStream";
        } catch (IOException e) {
            completer.setException(e);
            return "BitmapFactory#decodeStream";
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }
}
