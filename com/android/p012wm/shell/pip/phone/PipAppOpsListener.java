package com.android.p012wm.shell.pip.phone;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Pair;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.pip.PipUtils;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipAppOpsListener */
public final class PipAppOpsListener {
    public C19001 mAppOpsChangedListener = new AppOpsManager.OnOpChangedListener() {
        public final void onOpChanged(String str, String str2) {
            try {
                Pair<ComponentName, Integer> topPipActivity = PipUtils.getTopPipActivity(PipAppOpsListener.this.mContext);
                if (topPipActivity.first != null) {
                    ApplicationInfo applicationInfoAsUser = PipAppOpsListener.this.mContext.getPackageManager().getApplicationInfoAsUser(str2, 0, ((Integer) topPipActivity.second).intValue());
                    if (applicationInfoAsUser.packageName.equals(((ComponentName) topPipActivity.first).getPackageName()) && PipAppOpsListener.this.mAppOpsManager.checkOpNoThrow(67, applicationInfoAsUser.uid, str2) != 0) {
                        PipAppOpsListener.this.mMainExecutor.execute(new PipMenuView$$ExternalSyntheticLambda7(this, 4));
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                PipAppOpsListener pipAppOpsListener = PipAppOpsListener.this;
                Objects.requireNonNull(pipAppOpsListener);
                pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
            }
        }
    };
    public AppOpsManager mAppOpsManager;
    public Callback mCallback;
    public Context mContext;
    public ShellExecutor mMainExecutor;

    /* renamed from: com.android.wm.shell.pip.phone.PipAppOpsListener$Callback */
    public interface Callback {
    }

    public PipAppOpsListener(Context context, PipMotionHelper pipMotionHelper, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mCallback = pipMotionHelper;
    }
}
