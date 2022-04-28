package com.android.keyguard;

import android.app.admin.IKeyguardCallback;
import android.app.admin.IKeyguardClient;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1;
import com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class AdminSecondaryLockScreenController {
    public final C04722 mCallback = new IKeyguardCallback.Stub() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onDismiss() {
            AdminSecondaryLockScreenController.this.mHandler.post(new PowerUI$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) {
            Handler handler = AdminSecondaryLockScreenController.this.mHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            if (surfacePackage != null) {
                AdminSecondaryLockScreenController.this.mView.setChildSurfacePackage(surfacePackage);
            } else {
                AdminSecondaryLockScreenController.this.mHandler.post(new ScreenDecorations$$ExternalSyntheticLambda1(this, 1));
            }
        }
    };
    public IKeyguardClient mClient;
    public final C04711 mConnection = new ServiceConnection() {
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AdminSecondaryLockScreenController.this.mClient = IKeyguardClient.Stub.asInterface(iBinder);
            if (AdminSecondaryLockScreenController.this.mView.isAttachedToWindow()) {
                AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
                if (adminSecondaryLockScreenController.mClient != null) {
                    AdminSecondaryLockScreenController.m156$$Nest$monSurfaceReady(adminSecondaryLockScreenController);
                    try {
                        iBinder.linkToDeath(AdminSecondaryLockScreenController.this.mKeyguardClientDeathRecipient, 0);
                    } catch (RemoteException e) {
                        Log.e("AdminSecondaryLockScreenController", "Lost connection to secondary lockscreen service", e);
                        AdminSecondaryLockScreenController.this.dismiss(KeyguardUpdateMonitor.getCurrentUser());
                    }
                }
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            AdminSecondaryLockScreenController.this.mClient = null;
        }
    };
    public final Context mContext;
    public Handler mHandler;
    public KeyguardSecurityCallback mKeyguardCallback;
    public final AdminSecondaryLockScreenController$$ExternalSyntheticLambda0 mKeyguardClientDeathRecipient = new AdminSecondaryLockScreenController$$ExternalSyntheticLambda0(this);
    public final KeyguardSecurityContainer mParent;
    @VisibleForTesting
    public SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {
        public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
            adminSecondaryLockScreenController.mUpdateMonitor.removeCallback(adminSecondaryLockScreenController.mUpdateCallback);
        }

        public final void surfaceCreated(SurfaceHolder surfaceHolder) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
            adminSecondaryLockScreenController.mUpdateMonitor.registerCallback(adminSecondaryLockScreenController.mUpdateCallback);
            AdminSecondaryLockScreenController adminSecondaryLockScreenController2 = AdminSecondaryLockScreenController.this;
            if (adminSecondaryLockScreenController2.mClient != null) {
                AdminSecondaryLockScreenController.m156$$Nest$monSurfaceReady(adminSecondaryLockScreenController2);
            }
            AdminSecondaryLockScreenController.this.mHandler.postDelayed(new AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0(this, currentUser), 500);
        }
    };
    public final C04733 mUpdateCallback = new KeyguardUpdateMonitorCallback() {
        public final void onSecondaryLockscreenRequirementChanged(int i) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = AdminSecondaryLockScreenController.this.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            if (((Intent) keyguardUpdateMonitor.mSecondaryLockscreenRequirement.get(Integer.valueOf(i))) == null) {
                AdminSecondaryLockScreenController.this.dismiss(i);
            }
        }
    };
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public AdminSecurityView mView;

    public class AdminSecurityView extends SurfaceView {
        public SurfaceHolder.Callback mSurfaceHolderCallback;

        public AdminSecurityView(Context context, SurfaceHolder.Callback callback) {
            super(context);
            this.mSurfaceHolderCallback = callback;
            setZOrderOnTop(true);
        }

        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            getHolder().addCallback(this.mSurfaceHolderCallback);
        }

        public final void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getHolder().removeCallback(this.mSurfaceHolderCallback);
        }
    }

    public static class Factory {
        public final Context mContext;
        public final Handler mHandler;
        public final KeyguardSecurityContainer mParent;
        public final KeyguardUpdateMonitor mUpdateMonitor;

        public Factory(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler) {
            this.mContext = context;
            this.mParent = keyguardSecurityContainer;
            this.mUpdateMonitor = keyguardUpdateMonitor;
            this.mHandler = handler;
        }
    }

    public final void dismiss(int i) {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (this.mView.isAttachedToWindow() && i == KeyguardUpdateMonitor.getCurrentUser()) {
            hide();
            KeyguardSecurityCallback keyguardSecurityCallback = this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.dismiss(i, true);
            }
        }
    }

    public final void hide() {
        if (this.mView.isAttachedToWindow()) {
            this.mParent.removeView(this.mView);
        }
        IKeyguardClient iKeyguardClient = this.mClient;
        if (iKeyguardClient != null) {
            try {
                iKeyguardClient.asBinder().unlinkToDeath(this.mKeyguardClientDeathRecipient, 0);
            } catch (NoSuchElementException unused) {
                Log.w("AdminSecondaryLockScreenController", "IKeyguardClient death recipient already released");
            }
            this.mContext.unbindService(this.mConnection);
            this.mClient = null;
        }
    }

    /* renamed from: -$$Nest$monSurfaceReady  reason: not valid java name */
    public static void m156$$Nest$monSurfaceReady(AdminSecondaryLockScreenController adminSecondaryLockScreenController) {
        Objects.requireNonNull(adminSecondaryLockScreenController);
        try {
            IBinder hostToken = adminSecondaryLockScreenController.mView.getHostToken();
            if (hostToken != null) {
                adminSecondaryLockScreenController.mClient.onCreateKeyguardSurface(hostToken, adminSecondaryLockScreenController.mCallback);
            } else {
                adminSecondaryLockScreenController.hide();
            }
        } catch (RemoteException e) {
            Log.e("AdminSecondaryLockScreenController", "Error in onCreateKeyguardSurface", e);
            adminSecondaryLockScreenController.dismiss(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public AdminSecondaryLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityContainerController.C05212 r5, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mParent = keyguardSecurityContainer;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardCallback = r5;
        this.mView = new AdminSecurityView(context, this.mSurfaceHolderCallback);
    }
}
