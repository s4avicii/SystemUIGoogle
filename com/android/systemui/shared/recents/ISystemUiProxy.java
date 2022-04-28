package com.android.systemui.shared.recents;

import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17;
import com.android.p012wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda4;
import com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda11;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda14;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda18;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda20;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda5;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda6;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda8;
import com.android.systemui.shared.recents.model.Task$TaskKey;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker$$ExternalSyntheticLambda0;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.systemui.elmyra.actions.Action$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

public interface ISystemUiProxy extends IInterface {

    public static abstract class Stub extends Binder implements ISystemUiProxy {
        public final IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.recents.ISystemUiProxy");
            }
            if (i != 1598968902) {
                if (i == 2) {
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r11 = (OverviewProxyService.C10571) this;
                    r11.verifyCallerAndClearCallingIdentityPostMain("startScreenPinning", new OverviewProxyService$1$$ExternalSyntheticLambda2(r11, readInt, 0));
                    parcel2.writeNoException();
                } else if (i == 10) {
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r112 = (OverviewProxyService.C10571) this;
                    r112.verifyCallerAndClearCallingIdentity("onStatusBarMotionEvent", new OverviewProxyService$1$$ExternalSyntheticLambda20(new VibratorHelper$$ExternalSyntheticLambda0(r112, (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR), 1)));
                    parcel2.writeNoException();
                } else if (i == 26) {
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r113 = (OverviewProxyService.C10571) this;
                    r113.verifyCallerAndClearCallingIdentityPostMain("notifyPrioritizedRotation", new OverviewProxyService$1$$ExternalSyntheticLambda7(r113, readInt2));
                    parcel2.writeNoException();
                } else if (i == 7) {
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r114 = (OverviewProxyService.C10571) this;
                    r114.verifyCallerAndClearCallingIdentityPostMain("onOverviewShown", new OverviewProxyService$1$$ExternalSyntheticLambda8(r114, readBoolean));
                    parcel2.writeNoException();
                } else if (i == 8) {
                    OverviewProxyService.C10571 r115 = (OverviewProxyService.C10571) this;
                    parcel2.writeNoException();
                    parcel2.writeTypedObject((Rect) r115.verifyCallerAndClearCallingIdentity("getNonMinimizedSplitScreenSecondaryBounds", new OverviewProxyService$1$$ExternalSyntheticLambda18(r115)), 1);
                } else if (i == 13) {
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r116 = (OverviewProxyService.C10571) this;
                    r116.verifyCallerAndClearCallingIdentityPostMain("onAssistantProgress", new OverviewProxyService$1$$ExternalSyntheticLambda5(r116, readFloat));
                    parcel2.writeNoException();
                } else if (i == 14) {
                    parcel.enforceNoDataAvail();
                    OverviewProxyService.C10571 r117 = (OverviewProxyService.C10571) this;
                    r117.verifyCallerAndClearCallingIdentityPostMain("startAssistant", new PipTaskOrganizer$$ExternalSyntheticLambda4(r117, (Bundle) parcel.readTypedObject(Bundle.CREATOR), 1));
                    parcel2.writeNoException();
                } else if (i == 29) {
                    Task$TaskKey task$TaskKey = (Task$TaskKey) parcel.readTypedObject(Task$TaskKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    OverviewProxyService overviewProxyService = OverviewProxyService.this;
                    overviewProxyService.mScreenshotHelper.provideScreenshot((Bundle) parcel.readTypedObject(Bundle.CREATOR), (Rect) parcel.readTypedObject(Rect.CREATOR), (Insets) parcel.readTypedObject(Insets.CREATOR), task$TaskKey.f74id, task$TaskKey.userId, task$TaskKey.sourceComponent, 3, overviewProxyService.mHandler, (Consumer) null);
                    parcel2.writeNoException();
                } else if (i != 30) {
                    switch (i) {
                        case 16:
                            int readInt3 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            OverviewProxyService.C10571 r118 = (OverviewProxyService.C10571) this;
                            r118.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonClicked", new OverviewProxyService$1$$ExternalSyntheticLambda20(new OverviewProxyService$1$$ExternalSyntheticLambda3(r118, readInt3, 0)));
                            parcel2.writeNoException();
                            break;
                        case 17:
                            OverviewProxyService.C10571 r119 = (OverviewProxyService.C10571) this;
                            r119.verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonLongClicked", new OverviewProxyService$1$$ExternalSyntheticLambda20(new BubbleStackView$$ExternalSyntheticLambda17(r119, 4)));
                            parcel2.writeNoException();
                            break;
                        case 18:
                            ((OverviewProxyService.C10571) this).verifyCallerAndClearCallingIdentityPostMain("stopScreenPinning", OverviewProxyService$1$$ExternalSyntheticLambda12.INSTANCE);
                            parcel2.writeNoException();
                            break;
                        case 19:
                            float readFloat2 = parcel.readFloat();
                            parcel.enforceNoDataAvail();
                            OverviewProxyService.C10571 r1110 = (OverviewProxyService.C10571) this;
                            r1110.verifyCallerAndClearCallingIdentityPostMain("onAssistantGestureCompletion", new OverviewProxyService$1$$ExternalSyntheticLambda1(r1110, readFloat2, 0));
                            parcel2.writeNoException();
                            break;
                        case 20:
                            float readFloat3 = parcel.readFloat();
                            boolean readBoolean2 = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            OverviewProxyService.C10571 r1111 = (OverviewProxyService.C10571) this;
                            r1111.verifyCallerAndClearCallingIdentityPostMain("setNavBarButtonAlpha", new OverviewProxyService$1$$ExternalSyntheticLambda6(r1111, readFloat3, readBoolean2));
                            parcel2.writeNoException();
                            break;
                        default:
                            switch (i) {
                                case 22:
                                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                                    Insets insets = (Insets) parcel.readTypedObject(Insets.CREATOR);
                                    parcel.readInt();
                                    parcel.enforceNoDataAvail();
                                    parcel2.writeNoException();
                                    break;
                                case 23:
                                    boolean readBoolean3 = parcel.readBoolean();
                                    parcel.enforceNoDataAvail();
                                    OverviewProxyService.this.mLegacySplitScreenOptional.ifPresent(new OverviewProxyService$1$$ExternalSyntheticLambda14(readBoolean3));
                                    parcel2.writeNoException();
                                    break;
                                case 24:
                                    OverviewProxyService.C10571 r1112 = (OverviewProxyService.C10571) this;
                                    r1112.verifyCallerAndClearCallingIdentity("notifySwipeToHomeFinished", new OverviewProxyService$1$$ExternalSyntheticLambda20(new DozeScreenState$$ExternalSyntheticLambda0(r1112, 5)));
                                    parcel2.writeNoException();
                                    break;
                                default:
                                    switch (i) {
                                        case 45:
                                            OverviewProxyService.C10571 r1113 = (OverviewProxyService.C10571) this;
                                            r1113.verifyCallerAndClearCallingIdentityPostMain("onBackPressed", new BaseWifiTracker$$ExternalSyntheticLambda0(r1113, 3));
                                            parcel2.writeNoException();
                                            break;
                                        case 46:
                                            boolean readBoolean4 = parcel.readBoolean();
                                            parcel.enforceNoDataAvail();
                                            OverviewProxyService.C10571 r1114 = (OverviewProxyService.C10571) this;
                                            r1114.verifyCallerAndClearCallingIdentityPostMain("setHomeRotationEnabled", new OverviewProxyService$1$$ExternalSyntheticLambda4(r1114, readBoolean4, 0));
                                            parcel2.writeNoException();
                                            break;
                                        case 47:
                                            OverviewProxyService.C10571 r1115 = (OverviewProxyService.C10571) this;
                                            r1115.verifyCallerAndClearCallingIdentityPostMain("notifySwipeUpGestureStarted", new OneHandedController$$ExternalSyntheticLambda1(r1115, 3));
                                            break;
                                        case 48:
                                            boolean readBoolean5 = parcel.readBoolean();
                                            boolean readBoolean6 = parcel.readBoolean();
                                            parcel.enforceNoDataAvail();
                                            OverviewProxyService.C10571 r1116 = (OverviewProxyService.C10571) this;
                                            r1116.verifyCallerAndClearCallingIdentityPostMain("notifyTaskbarStatus", new OverviewProxyService$1$$ExternalSyntheticLambda11(r1116, readBoolean5, readBoolean6));
                                            break;
                                        case 49:
                                            boolean readBoolean7 = parcel.readBoolean();
                                            parcel.enforceNoDataAvail();
                                            OverviewProxyService.C10571 r1117 = (OverviewProxyService.C10571) this;
                                            r1117.verifyCallerAndClearCallingIdentityPostMain("notifyTaskbarAutohideSuspend", new OverviewProxyService$1$$ExternalSyntheticLambda10(r1117, readBoolean7));
                                            break;
                                        case CustomEvent.MAX_STR_LENGTH /*50*/:
                                            OverviewProxyService.C10571 r1118 = (OverviewProxyService.C10571) this;
                                            ((InputMethodManager) OverviewProxyService.this.mContext.getSystemService(InputMethodManager.class)).showInputMethodPickerFromSystem(true, 0);
                                            OverviewProxyService.this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                                            parcel2.writeNoException();
                                            break;
                                        case 51:
                                            OverviewProxyService.C10571 r1119 = (OverviewProxyService.C10571) this;
                                            r1119.verifyCallerAndClearCallingIdentityPostMain("toggleNotificationPanel", new Action$$ExternalSyntheticLambda0(r1119, 2));
                                            parcel2.writeNoException();
                                            break;
                                        default:
                                            return super.onTransact(i, parcel, parcel2, i2);
                                    }
                            }
                    }
                } else {
                    OverviewProxyService.C10571 r1120 = (OverviewProxyService.C10571) this;
                    r1120.verifyCallerAndClearCallingIdentity("expandNotificationPanel", new OverviewProxyService$1$$ExternalSyntheticLambda20(new QSTileImpl$$ExternalSyntheticLambda0(r1120, 3)));
                    parcel2.writeNoException();
                }
                return true;
            }
            parcel2.writeString("com.android.systemui.shared.recents.ISystemUiProxy");
            return true;
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.shared.recents.ISystemUiProxy");
        }
    }
}
