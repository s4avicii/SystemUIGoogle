package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.splitscreen.StageTaskUnfoldController;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.p012wm.shell.unfold.UnfoldBackgroundController;
import dagger.Lazy;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellModule$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellModule$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ TransactionPool f$1;
    public final /* synthetic */ DisplayInsetsController f$2;
    public final /* synthetic */ Lazy f$3;
    public final /* synthetic */ ShellExecutor f$4;

    public /* synthetic */ WMShellModule$$ExternalSyntheticLambda0(Context context, TransactionPool transactionPool, DisplayInsetsController displayInsetsController, Lazy lazy, ShellExecutor shellExecutor) {
        this.f$0 = context;
        this.f$1 = transactionPool;
        this.f$2 = displayInsetsController;
        this.f$3 = lazy;
        this.f$4 = shellExecutor;
    }

    public final Object apply(Object obj) {
        Context context = this.f$0;
        TransactionPool transactionPool = this.f$1;
        DisplayInsetsController displayInsetsController = this.f$2;
        Lazy lazy = this.f$3;
        return new StageTaskUnfoldController(context, transactionPool, (ShellUnfoldProgressProvider) obj, displayInsetsController, (UnfoldBackgroundController) lazy.get(), this.f$4);
    }
}
