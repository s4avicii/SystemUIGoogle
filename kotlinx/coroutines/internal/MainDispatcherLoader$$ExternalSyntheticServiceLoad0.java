package kotlinx.coroutines.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import kotlinx.coroutines.android.AndroidDispatcherFactory;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MainDispatcherLoader$$ExternalSyntheticServiceLoad0 {
    /* renamed from: m */
    public static /* synthetic */ Iterator m136m() {
        try {
            return Arrays.asList(new MainDispatcherFactory[]{new AndroidDispatcherFactory()}).iterator();
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }
}
