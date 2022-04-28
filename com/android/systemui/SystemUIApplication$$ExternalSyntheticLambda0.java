package com.android.systemui;

import android.util.Dumpable;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIApplication$$ExternalSyntheticLambda0 implements Dumpable {
    public final /* synthetic */ Dumpable f$0;

    public /* synthetic */ SystemUIApplication$$ExternalSyntheticLambda0(Dumpable dumpable) {
        this.f$0 = dumpable;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Dumpable dumpable = this.f$0;
        int i = SystemUIApplication.$r8$clinit;
        dumpable.dump(printWriter, strArr);
    }
}
