package com.android.systemui.p006qs;

import android.content.Context;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.FgsManagerController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.QSFgsManagerFooter */
public final class QSFgsManagerFooter implements View.OnClickListener, FgsManagerController.OnDialogDismissedListener, FgsManagerController.OnNumberOfPackagesChangedListener {
    public final Context mContext;
    public final ImageView mDotView;
    public final Executor mExecutor;
    public final FgsManagerController mFgsManagerController;
    public final TextView mFooterText;
    public boolean mIsInitialized = false;
    public final Executor mMainExecutor;
    public int mNumPackages;
    public final View mNumberContainer;
    public final TextView mNumberView;
    public final View mRootView;
    public final View mTextContainer;
    public VisibilityChangedDispatcher$OnVisibilityChangedListener mVisibilityChangedListener;

    public final void init() {
        if (!this.mIsInitialized) {
            FgsManagerController fgsManagerController = this.mFgsManagerController;
            Objects.requireNonNull(fgsManagerController);
            synchronized (fgsManagerController.lock) {
                if (!fgsManagerController.initialized) {
                    try {
                        fgsManagerController.activityManager.registerForegroundServiceObserver(fgsManagerController);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                    DeviceConfigProxy deviceConfigProxy = fgsManagerController.deviceConfigProxy;
                    Executor executor = fgsManagerController.backgroundExecutor;
                    FgsManagerController$init$1$1 fgsManagerController$init$1$1 = new FgsManagerController$init$1$1(fgsManagerController);
                    Objects.requireNonNull(deviceConfigProxy);
                    DeviceConfig.addOnPropertiesChangedListener("systemui", executor, fgsManagerController$init$1$1);
                    Objects.requireNonNull(fgsManagerController.deviceConfigProxy);
                    fgsManagerController.isAvailable = DeviceConfig.getBoolean("systemui", "task_manager_enabled", true);
                    fgsManagerController.dumpManager.registerDumpable(fgsManagerController);
                    fgsManagerController.initialized = true;
                }
            }
            this.mRootView.setOnClickListener(this);
            this.mIsInitialized = true;
        }
    }

    public final void onClick(View view) {
        FgsManagerController fgsManagerController = this.mFgsManagerController;
        View view2 = this.mRootView;
        Objects.requireNonNull(fgsManagerController);
        synchronized (fgsManagerController.lock) {
            if (fgsManagerController.dialog == null) {
                SystemUIDialog systemUIDialog = new SystemUIDialog(fgsManagerController.context);
                systemUIDialog.setTitle(C1777R.string.fgs_manager_dialog_title);
                RecyclerView recyclerView = new RecyclerView(systemUIDialog.getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(1));
                recyclerView.setAdapter(fgsManagerController.appListAdapter);
                systemUIDialog.setView(recyclerView);
                fgsManagerController.dialog = systemUIDialog;
                systemUIDialog.setOnDismissListener(new FgsManagerController$showDialog$1$1(fgsManagerController));
                fgsManagerController.mainExecutor.execute(new FgsManagerController$showDialog$1$2(view2, systemUIDialog, fgsManagerController));
                fgsManagerController.backgroundExecutor.execute(new FgsManagerController$showDialog$1$3(fgsManagerController));
            }
        }
    }

    public final void onDialogDismissed() {
        this.mExecutor.execute(new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 0));
    }

    public final void onNumberOfPackagesChanged(int i) {
        this.mNumPackages = i;
        this.mExecutor.execute(new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 0));
    }

    public final void setListening(boolean z) {
        if (z) {
            FgsManagerController fgsManagerController = this.mFgsManagerController;
            Objects.requireNonNull(fgsManagerController);
            synchronized (fgsManagerController.lock) {
                fgsManagerController.onDialogDismissedListeners.add(this);
            }
            FgsManagerController fgsManagerController2 = this.mFgsManagerController;
            Objects.requireNonNull(fgsManagerController2);
            synchronized (fgsManagerController2.lock) {
                fgsManagerController2.onNumberOfPackagesChangedListeners.add(this);
            }
            this.mNumPackages = this.mFgsManagerController.getNumRunningPackages();
            this.mExecutor.execute(new QSFgsManagerFooter$$ExternalSyntheticLambda0(this, 0));
            return;
        }
        FgsManagerController fgsManagerController3 = this.mFgsManagerController;
        Objects.requireNonNull(fgsManagerController3);
        synchronized (fgsManagerController3.lock) {
            fgsManagerController3.onDialogDismissedListeners.remove(this);
        }
        FgsManagerController fgsManagerController4 = this.mFgsManagerController;
        Objects.requireNonNull(fgsManagerController4);
        synchronized (fgsManagerController4.lock) {
            fgsManagerController4.onNumberOfPackagesChangedListeners.remove(this);
        }
    }

    public QSFgsManagerFooter(View view, Executor executor, Executor executor2, FgsManagerController fgsManagerController) {
        this.mRootView = view;
        this.mFooterText = (TextView) view.findViewById(C1777R.C1779id.footer_text);
        this.mTextContainer = view.findViewById(C1777R.C1779id.fgs_text_container);
        this.mNumberContainer = view.findViewById(C1777R.C1779id.fgs_number_container);
        this.mNumberView = (TextView) view.findViewById(C1777R.C1779id.fgs_number);
        this.mDotView = (ImageView) view.findViewById(C1777R.C1779id.fgs_new);
        this.mContext = view.getContext();
        this.mMainExecutor = executor;
        this.mExecutor = executor2;
        this.mFgsManagerController = fgsManagerController;
    }
}
