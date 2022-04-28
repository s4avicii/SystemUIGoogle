package androidx.fragment.app;

import android.util.Log;
import android.view.SurfaceControl;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DialogFragment$$ExternalSyntheticOutline0 implements PipSurfaceTransactionHelper.SurfaceControlTransactionFactory {
    public static final /* synthetic */ DialogFragment$$ExternalSyntheticOutline0 INSTANCE = new DialogFragment$$ExternalSyntheticOutline0();

    public SurfaceControl.Transaction getTransaction() {
        return new SurfaceControl.Transaction();
    }

    /* renamed from: m */
    public static void m17m(String str, String str2, String str3) {
        Log.d(str3, str + str2);
    }
}
