package com.android.systemui.wmshell;

import android.content.Context;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.ShellCommandHandler;
import com.android.p012wm.shell.ShellInitImpl$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.draganddrop.DragAndDrop;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutout;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.nano.WmShellTraceProto;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.onehanded.OneHandedEventCallback;
import com.android.p012wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.shared.tracing.ProtoTraceable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceEntryProto;
import com.android.systemui.tracing.nano.SystemUiTraceFileProto;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.google.protobuf.nano.MessageNano;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class WMShell extends CoreStartable implements CommandQueue.Callbacks, ProtoTraceable<SystemUiTraceProto> {
    public final CommandQueue mCommandQueue;
    public C176315 mCompatUIKeyguardCallback;
    public final Optional<CompatUI> mCompatUIOptional;
    public final ConfigurationController mConfigurationController;
    public final Optional<DragAndDrop> mDragAndDropOptional;
    public final Optional<HideDisplayCutout> mHideDisplayCutoutOptional;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public C17674 mLegacySplitScreenKeyguardCallback;
    public final Optional<LegacySplitScreen> mLegacySplitScreenOptional;
    public C17729 mOneHandedKeyguardCallback;
    public final Optional<OneHanded> mOneHandedOptional;
    public C17652 mPipKeyguardCallback;
    public final Optional<Pip> mPipOptional;
    public final ProtoTracer mProtoTracer;
    public final ScreenLifecycle mScreenLifecycle;
    public final Optional<ShellCommandHandler> mShellCommandHandler;
    public C17685 mSplitScreenKeyguardCallback;
    public final Optional<SplitScreen> mSplitScreenOptional;
    public final Executor mSysUiMainExecutor;
    public final SysUiState mSysUiState;
    public final UserInfoController mUserInfoController;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public C175810 mWakefulnessObserver;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public WMShell(Context context, Optional optional, Optional optional2, Optional optional3, Optional optional4, Optional optional5, Optional optional6, Optional optional7, Optional optional8, CommandQueue commandQueue, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ScreenLifecycle screenLifecycle, SysUiState sysUiState, ProtoTracer protoTracer, WakefulnessLifecycle wakefulnessLifecycle, UserInfoController userInfoController, Executor executor) {
        super(context);
        this.mCommandQueue = commandQueue;
        this.mConfigurationController = configurationController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mScreenLifecycle = screenLifecycle;
        this.mSysUiState = sysUiState;
        this.mPipOptional = optional;
        this.mLegacySplitScreenOptional = optional2;
        this.mSplitScreenOptional = optional3;
        this.mOneHandedOptional = optional4;
        this.mHideDisplayCutoutOptional = optional5;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mProtoTracer = protoTracer;
        this.mShellCommandHandler = optional6;
        this.mCompatUIOptional = optional7;
        this.mDragAndDropOptional = optional8;
        this.mUserInfoController = userInfoController;
        this.mSysUiMainExecutor = executor;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if ((!this.mShellCommandHandler.isPresent() || !this.mShellCommandHandler.get().handleCommand(strArr, printWriter)) && !handleLoggingCommand(strArr, printWriter)) {
            this.mShellCommandHandler.ifPresent(new WMShell$$ExternalSyntheticLambda7(printWriter, 0));
        }
    }

    public final void handleWindowManagerLoggingCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) {
        PrintWriter printWriter = new PrintWriter(new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
        handleLoggingCommand(strArr, printWriter);
        printWriter.flush();
        printWriter.close();
    }

    @VisibleForTesting
    public void initCompatUi(final CompatUI compatUI) {
        C176315 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardOccludedChanged(boolean z) {
                CompatUI.this.onKeyguardOccludedChanged(z);
            }
        };
        this.mCompatUIKeyguardCallback = r0;
        this.mKeyguardUpdateMonitor.registerCallback(r0);
    }

    @VisibleForTesting
    public void initHideDisplayCutout(final HideDisplayCutout hideDisplayCutout) {
        this.mConfigurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onConfigChanged(Configuration configuration) {
                HideDisplayCutout.this.onConfigurationChanged(configuration);
            }
        });
    }

    @VisibleForTesting
    public void initLegacySplitScreen(final LegacySplitScreen legacySplitScreen) {
        C17674 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardVisibilityChanged(boolean z) {
                LegacySplitScreen.this.onKeyguardVisibilityChanged(z);
            }
        };
        this.mLegacySplitScreenKeyguardCallback = r0;
        this.mKeyguardUpdateMonitor.registerCallback(r0);
    }

    @VisibleForTesting
    public void initOneHanded(final OneHanded oneHanded) {
        oneHanded.registerTransitionCallback(new OneHandedTransitionCallback() {
            public final void onStartFinished(Rect rect) {
                WMShell.this.mSysUiMainExecutor.execute(new WMShell$7$$ExternalSyntheticLambda0(this, 0));
            }

            public final void onStartTransition() {
                WMShell.this.mSysUiMainExecutor.execute(new WMShell$7$$ExternalSyntheticLambda2(this, 0));
            }

            public final void onStopFinished(Rect rect) {
                WMShell.this.mSysUiMainExecutor.execute(new WMShell$7$$ExternalSyntheticLambda1(this, 0));
            }
        });
        oneHanded.registerEventCallback(new OneHandedEventCallback() {
        });
        C17729 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardVisibilityChanged(boolean z) {
                OneHanded.this.onKeyguardVisibilityChanged(z);
                OneHanded.this.stopOneHanded();
            }

            public final void onUserSwitchComplete(int i) {
                OneHanded.this.onUserSwitch(i);
            }
        };
        this.mOneHandedKeyguardCallback = r0;
        this.mKeyguardUpdateMonitor.registerCallback(r0);
        C175810 r02 = new WakefulnessLifecycle.Observer() {
            public final void onFinishedWakingUp() {
                OneHanded.this.setLockedDisabled(false);
            }

            public final void onStartedGoingToSleep() {
                OneHanded.this.stopOneHanded();
                OneHanded.this.setLockedDisabled(true);
            }
        };
        this.mWakefulnessObserver = r02;
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle);
        wakefulnessLifecycle.mObservers.add(r02);
        ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
        C175911 r1 = new ScreenLifecycle.Observer() {
            public final void onScreenTurningOff() {
                OneHanded.this.stopOneHanded(7);
            }
        };
        Objects.requireNonNull(screenLifecycle);
        screenLifecycle.mObservers.add(r1);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) new CommandQueue.Callbacks() {
            public final void onCameraLaunchGestureDetected(int i) {
                OneHanded.this.stopOneHanded();
            }

            public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
                if (i == 0 && (i2 & 2) != 0) {
                    OneHanded.this.stopOneHanded(3);
                }
            }
        });
        this.mConfigurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onConfigChanged(Configuration configuration) {
                OneHanded.this.onConfigChanged(configuration);
            }
        });
    }

    @VisibleForTesting
    public void initPip(final Pip pip) {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) new CommandQueue.Callbacks() {
            public final void showPictureInPictureMenu() {
                Pip.this.showPictureInPictureMenu();
            }
        });
        C17652 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    Pip.this.hidePipMenu();
                }
            }
        };
        this.mPipKeyguardCallback = r0;
        this.mKeyguardUpdateMonitor.registerCallback(r0);
        SysUiState sysUiState = this.mSysUiState;
        WMShell$$ExternalSyntheticLambda0 wMShell$$ExternalSyntheticLambda0 = new WMShell$$ExternalSyntheticLambda0(this, pip);
        Objects.requireNonNull(sysUiState);
        sysUiState.mCallbacks.add(wMShell$$ExternalSyntheticLambda0);
        wMShell$$ExternalSyntheticLambda0.onSystemUiStateChanged(sysUiState.mFlags);
        this.mConfigurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onConfigChanged(Configuration configuration) {
                Pip.this.onConfigurationChanged(configuration);
            }

            public final void onDensityOrFontScaleChanged() {
                Pip.this.onDensityOrFontScaleChanged();
            }

            public final void onThemeChanged() {
                Pip.this.onOverlayChanged();
            }
        });
        this.mUserInfoController.addCallback(new WMShell$$ExternalSyntheticLambda1(pip));
    }

    @VisibleForTesting
    public void initSplitScreen(final SplitScreen splitScreen) {
        C17685 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardVisibilityChanged(boolean z) {
                SplitScreen.this.onKeyguardVisibilityChanged(z);
            }
        };
        this.mSplitScreenKeyguardCallback = r0;
        this.mKeyguardUpdateMonitor.registerCallback(r0);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        C17696 r02 = new WakefulnessLifecycle.Observer() {
            public final void onFinishedWakingUp() {
                SplitScreen.this.onFinishedWakingUp();
            }
        };
        Objects.requireNonNull(wakefulnessLifecycle);
        wakefulnessLifecycle.mObservers.add(r02);
    }

    public final void start() {
        ProtoTracer protoTracer = this.mProtoTracer;
        Objects.requireNonNull(protoTracer);
        FrameProtoTracer<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> frameProtoTracer = protoTracer.mProtoTracer;
        Objects.requireNonNull(frameProtoTracer);
        synchronized (frameProtoTracer.mLock) {
            frameProtoTracer.mTraceables.add(this);
        }
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mPipOptional.ifPresent(new WMShell$$ExternalSyntheticLambda6(this, 0));
        this.mLegacySplitScreenOptional.ifPresent(new WMShell$$ExternalSyntheticLambda3(this, 0));
        this.mSplitScreenOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 0));
        this.mOneHandedOptional.ifPresent(new ShellInitImpl$$ExternalSyntheticLambda0(this, 2));
        this.mHideDisplayCutoutOptional.ifPresent(new WMShell$$ExternalSyntheticLambda4(this, 0));
        this.mCompatUIOptional.ifPresent(new WMShell$$ExternalSyntheticLambda8(this));
        this.mDragAndDropOptional.ifPresent(new WMShell$$ExternalSyntheticLambda5(this, 0));
    }

    public final void writeToProto(SystemUiTraceProto systemUiTraceProto) {
        if (systemUiTraceProto.wmShell == null) {
            systemUiTraceProto.wmShell = new WmShellTraceProto();
        }
    }

    public static boolean handleLoggingCommand(String[] strArr, PrintWriter printWriter) {
        ShellProtoLogImpl singleInstance = ShellProtoLogImpl.getSingleInstance();
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            Objects.requireNonNull(str);
            if (str.equals("enable-text")) {
                String[] strArr2 = (String[]) Arrays.copyOfRange(strArr, i + 1, strArr.length);
                if (singleInstance.startTextLogging(strArr2, printWriter) == 0) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Starting logging on groups: ");
                    m.append(Arrays.toString(strArr2));
                    printWriter.println(m.toString());
                }
                return true;
            } else if (!str.equals("disable-text")) {
                i++;
            } else {
                String[] strArr3 = (String[]) Arrays.copyOfRange(strArr, i + 1, strArr.length);
                if (singleInstance.stopTextLogging(strArr3, printWriter) == 0) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Stopping logging on groups: ");
                    m2.append(Arrays.toString(strArr3));
                    printWriter.println(m2.toString());
                }
                return true;
            }
        }
        return false;
    }
}
