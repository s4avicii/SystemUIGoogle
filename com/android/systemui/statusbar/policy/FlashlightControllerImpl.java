package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.FlashlightController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public final class FlashlightControllerImpl implements FlashlightController {
    public static final boolean DEBUG = Log.isLoggable("FlashlightController", 3);
    public String mCameraId;
    public final CameraManager mCameraManager;
    public final Context mContext;
    public boolean mFlashlightEnabled;
    public Handler mHandler;
    public final ArrayList<WeakReference<FlashlightController.FlashlightListener>> mListeners = new ArrayList<>(1);
    public boolean mTorchAvailable;
    public final C16171 mTorchCallback = new CameraManager.TorchCallback() {
        public final void onTorchModeChanged(String str, boolean z) {
            boolean z2;
            if (TextUtils.equals(str, FlashlightControllerImpl.this.mCameraId)) {
                setCameraAvailable(true);
                synchronized (FlashlightControllerImpl.this) {
                    FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                    if (flashlightControllerImpl.mFlashlightEnabled != z) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    flashlightControllerImpl.mFlashlightEnabled = z;
                }
                if (z2) {
                    if (FlashlightControllerImpl.DEBUG) {
                        Log.d("FlashlightController", "dispatchModeChanged(" + z + ")");
                    }
                    FlashlightControllerImpl flashlightControllerImpl2 = FlashlightControllerImpl.this;
                    Objects.requireNonNull(flashlightControllerImpl2);
                    flashlightControllerImpl2.dispatchListeners(1, z);
                }
                Settings.Secure.putInt(FlashlightControllerImpl.this.mContext.getContentResolver(), "flashlight_available", 1);
                Settings.Secure.putInt(FlashlightControllerImpl.this.mContext.getContentResolver(), "flashlight_enabled", z ? 1 : 0);
                FlashlightControllerImpl.this.mContext.sendBroadcast(new Intent("com.android.settings.flashlight.action.FLASHLIGHT_CHANGED"));
            }
        }

        public final void onTorchModeUnavailable(String str) {
            if (TextUtils.equals(str, FlashlightControllerImpl.this.mCameraId)) {
                setCameraAvailable(false);
                Settings.Secure.putInt(FlashlightControllerImpl.this.mContext.getContentResolver(), "flashlight_available", 0);
            }
        }

        public final void setCameraAvailable(boolean z) {
            boolean z2;
            synchronized (FlashlightControllerImpl.this) {
                FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                if (flashlightControllerImpl.mTorchAvailable != z) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                flashlightControllerImpl.mTorchAvailable = z;
            }
            if (z2) {
                if (FlashlightControllerImpl.DEBUG) {
                    Log.d("FlashlightController", "dispatchAvailabilityChanged(" + z + ")");
                }
                FlashlightControllerImpl flashlightControllerImpl2 = FlashlightControllerImpl.this;
                Objects.requireNonNull(flashlightControllerImpl2);
                flashlightControllerImpl2.dispatchListeners(2, z);
            }
        }
    };

    public final synchronized boolean isAvailable() {
        return this.mTorchAvailable;
    }

    public final synchronized boolean isEnabled() {
        return this.mFlashlightEnabled;
    }

    public final void setFlashlight(boolean z) {
        boolean z2;
        synchronized (this) {
            String str = this.mCameraId;
            if (str != null) {
                if (this.mFlashlightEnabled != z) {
                    this.mFlashlightEnabled = z;
                    try {
                        this.mCameraManager.setTorchMode(str, z);
                    } catch (CameraAccessException e) {
                        Log.e("FlashlightController", "Couldn't set torch mode", e);
                        this.mFlashlightEnabled = false;
                        z2 = true;
                        dispatchListeners(1, this.mFlashlightEnabled);
                        if (z2) {
                            dispatchListeners(1, false);
                            return;
                        }
                        return;
                    }
                }
                z2 = false;
            }
        }
    }

    public final void addCallback(Object obj) {
        FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) obj;
        synchronized (this.mListeners) {
            if (this.mCameraId == null) {
                tryInitCamera();
            }
            cleanUpListenersLocked(flashlightListener);
            this.mListeners.add(new WeakReference(flashlightListener));
            flashlightListener.onFlashlightAvailabilityChanged(this.mTorchAvailable);
            flashlightListener.onFlashlightChanged(this.mFlashlightEnabled);
        }
    }

    public final void cleanUpListenersLocked(FlashlightController.FlashlightListener flashlightListener) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            FlashlightController.FlashlightListener flashlightListener2 = (FlashlightController.FlashlightListener) this.mListeners.get(size).get();
            if (flashlightListener2 == null || flashlightListener2 == flashlightListener) {
                this.mListeners.remove(size);
            }
        }
    }

    public final void dispatchListeners(int i, boolean z) {
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) this.mListeners.get(i2).get();
                if (flashlightListener == null) {
                    z2 = true;
                } else if (i == 0) {
                    flashlightListener.onFlashlightError();
                } else if (i == 1) {
                    flashlightListener.onFlashlightChanged(z);
                } else if (i == 2) {
                    flashlightListener.onFlashlightAvailabilityChanged(z);
                }
            }
            if (z2) {
                cleanUpListenersLocked((FlashlightController.FlashlightListener) null);
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("FlashlightController state:");
        printWriter.print("  mCameraId=");
        printWriter.println(this.mCameraId);
        printWriter.print("  mFlashlightEnabled=");
        printWriter.println(this.mFlashlightEnabled);
        printWriter.print("  mTorchAvailable=");
        printWriter.println(this.mTorchAvailable);
    }

    public final boolean hasFlashlight() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    public final void removeCallback(Object obj) {
        FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) obj;
        synchronized (this.mListeners) {
            cleanUpListenersLocked(flashlightListener);
        }
    }

    public final void tryInitCamera() {
        String str;
        try {
            String[] cameraIdList = this.mCameraManager.getCameraIdList();
            int length = cameraIdList.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    str = null;
                    break;
                }
                str = cameraIdList[i];
                CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
                Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (bool != null && bool.booleanValue() && num != null && num.intValue() == 1) {
                    break;
                }
                i++;
            }
            this.mCameraId = str;
            if (str != null) {
                synchronized (this) {
                    if (this.mHandler == null) {
                        HandlerThread handlerThread = new HandlerThread("FlashlightController", 10);
                        handlerThread.start();
                        this.mHandler = new Handler(handlerThread.getLooper());
                    }
                }
                this.mCameraManager.registerTorchCallback(this.mTorchCallback, this.mHandler);
            }
        } catch (Throwable th) {
            Log.e("FlashlightController", "Couldn't initialize.", th);
        }
    }

    public FlashlightControllerImpl(Context context, DumpManager dumpManager) {
        this.mContext = context;
        this.mCameraManager = (CameraManager) context.getSystemService("camera");
        dumpManager.registerDumpable("FlashlightControllerImpl", this);
        tryInitCamera();
    }
}
