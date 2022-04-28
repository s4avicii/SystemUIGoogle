package com.android.systemui.p006qs.tileimpl;

import android.content.Context;
import android.os.Build;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.external.CustomTile;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.AirplaneModeTile;
import com.android.systemui.p006qs.tiles.AlarmTile;
import com.android.systemui.p006qs.tiles.BatterySaverTile;
import com.android.systemui.p006qs.tiles.BluetoothTile;
import com.android.systemui.p006qs.tiles.CameraToggleTile;
import com.android.systemui.p006qs.tiles.CastTile;
import com.android.systemui.p006qs.tiles.CellularTile;
import com.android.systemui.p006qs.tiles.ColorCorrectionTile;
import com.android.systemui.p006qs.tiles.ColorInversionTile;
import com.android.systemui.p006qs.tiles.DataSaverTile;
import com.android.systemui.p006qs.tiles.DeviceControlsTile;
import com.android.systemui.p006qs.tiles.DndTile;
import com.android.systemui.p006qs.tiles.FlashlightTile;
import com.android.systemui.p006qs.tiles.HotspotTile;
import com.android.systemui.p006qs.tiles.InternetTile;
import com.android.systemui.p006qs.tiles.LocationTile;
import com.android.systemui.p006qs.tiles.MicrophoneToggleTile;
import com.android.systemui.p006qs.tiles.NfcTile;
import com.android.systemui.p006qs.tiles.NightDisplayTile;
import com.android.systemui.p006qs.tiles.OneHandedModeTile;
import com.android.systemui.p006qs.tiles.QRCodeScannerTile;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile;
import com.android.systemui.p006qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.p006qs.tiles.RotationLockTile;
import com.android.systemui.p006qs.tiles.ScreenRecordTile;
import com.android.systemui.p006qs.tiles.UiModeNightTile;
import com.android.systemui.p006qs.tiles.WifiTile;
import com.android.systemui.p006qs.tiles.WorkModeTile;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.p005qs.QSFactory;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.p005qs.QSTileView;
import com.android.systemui.util.leak.GarbageMonitor;
import dagger.Lazy;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tileimpl.QSFactoryImpl */
public class QSFactoryImpl implements QSFactory {
    public final Provider<AirplaneModeTile> mAirplaneModeTileProvider;
    public final Provider<AlarmTile> mAlarmTileProvider;
    public final Provider<BatterySaverTile> mBatterySaverTileProvider;
    public final Provider<BluetoothTile> mBluetoothTileProvider;
    public final Provider<CameraToggleTile> mCameraToggleTileProvider;
    public final Provider<CastTile> mCastTileProvider;
    public final Provider<CellularTile> mCellularTileProvider;
    public final Provider<ColorCorrectionTile> mColorCorrectionTileProvider;
    public final Provider<ColorInversionTile> mColorInversionTileProvider;
    public final Provider<CustomTile.Builder> mCustomTileBuilderProvider;
    public final Provider<DataSaverTile> mDataSaverTileProvider;
    public final Provider<DeviceControlsTile> mDeviceControlsTileProvider;
    public final Provider<DndTile> mDndTileProvider;
    public final Provider<FlashlightTile> mFlashlightTileProvider;
    public final Provider<HotspotTile> mHotspotTileProvider;
    public final Provider<InternetTile> mInternetTileProvider;
    public final Provider<LocationTile> mLocationTileProvider;
    public final Provider<GarbageMonitor.MemoryTile> mMemoryTileProvider;
    public final Provider<MicrophoneToggleTile> mMicrophoneToggleTileProvider;
    public final Provider<NfcTile> mNfcTileProvider;
    public final Provider<NightDisplayTile> mNightDisplayTileProvider;
    public final Provider<OneHandedModeTile> mOneHandedModeTileProvider;
    public final Provider<QRCodeScannerTile> mQRCodeScannerTileProvider;
    public final Lazy<QSHost> mQsHostLazy;
    public final Provider<QuickAccessWalletTile> mQuickAccessWalletTileProvider;
    public final Provider<ReduceBrightColorsTile> mReduceBrightColorsTileProvider;
    public final Provider<RotationLockTile> mRotationLockTileProvider;
    public final Provider<ScreenRecordTile> mScreenRecordTileProvider;
    public final Provider<UiModeNightTile> mUiModeNightTileProvider;
    public final Provider<WifiTile> mWifiTileProvider;
    public final Provider<WorkModeTile> mWorkModeTileProvider;

    public QSFactoryImpl(Lazy<QSHost> lazy, Provider<CustomTile.Builder> provider, Provider<WifiTile> provider2, Provider<InternetTile> provider3, Provider<BluetoothTile> provider4, Provider<CellularTile> provider5, Provider<DndTile> provider6, Provider<ColorInversionTile> provider7, Provider<AirplaneModeTile> provider8, Provider<WorkModeTile> provider9, Provider<RotationLockTile> provider10, Provider<FlashlightTile> provider11, Provider<LocationTile> provider12, Provider<CastTile> provider13, Provider<HotspotTile> provider14, Provider<BatterySaverTile> provider15, Provider<DataSaverTile> provider16, Provider<NightDisplayTile> provider17, Provider<NfcTile> provider18, Provider<GarbageMonitor.MemoryTile> provider19, Provider<UiModeNightTile> provider20, Provider<ScreenRecordTile> provider21, Provider<ReduceBrightColorsTile> provider22, Provider<CameraToggleTile> provider23, Provider<MicrophoneToggleTile> provider24, Provider<DeviceControlsTile> provider25, Provider<AlarmTile> provider26, Provider<QuickAccessWalletTile> provider27, Provider<QRCodeScannerTile> provider28, Provider<OneHandedModeTile> provider29, Provider<ColorCorrectionTile> provider30) {
        this.mQsHostLazy = lazy;
        this.mCustomTileBuilderProvider = provider;
        this.mWifiTileProvider = provider2;
        this.mInternetTileProvider = provider3;
        this.mBluetoothTileProvider = provider4;
        this.mCellularTileProvider = provider5;
        this.mDndTileProvider = provider6;
        this.mColorInversionTileProvider = provider7;
        this.mAirplaneModeTileProvider = provider8;
        this.mWorkModeTileProvider = provider9;
        this.mRotationLockTileProvider = provider10;
        this.mFlashlightTileProvider = provider11;
        this.mLocationTileProvider = provider12;
        this.mCastTileProvider = provider13;
        this.mHotspotTileProvider = provider14;
        this.mBatterySaverTileProvider = provider15;
        this.mDataSaverTileProvider = provider16;
        this.mNightDisplayTileProvider = provider17;
        this.mNfcTileProvider = provider18;
        this.mMemoryTileProvider = provider19;
        this.mUiModeNightTileProvider = provider20;
        this.mScreenRecordTileProvider = provider21;
        this.mReduceBrightColorsTileProvider = provider22;
        this.mCameraToggleTileProvider = provider23;
        this.mMicrophoneToggleTileProvider = provider24;
        this.mDeviceControlsTileProvider = provider25;
        this.mAlarmTileProvider = provider26;
        this.mQuickAccessWalletTileProvider = provider27;
        this.mQRCodeScannerTileProvider = provider28;
        this.mOneHandedModeTileProvider = provider29;
        this.mColorCorrectionTileProvider = provider30;
    }

    public final QSTile createTile(String str) {
        QSTileImpl createTileInternal = createTileInternal(str);
        if (createTileInternal != null) {
            createTileInternal.mHandler.sendEmptyMessage(12);
            createTileInternal.mHandler.sendEmptyMessage(11);
        }
        return createTileInternal;
    }

    public QSTileImpl createTileInternal(String str) {
        Objects.requireNonNull(str);
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2109393100:
                if (str.equals("onehanded")) {
                    c = 0;
                    break;
                }
                break;
            case -2016941037:
                if (str.equals("inversion")) {
                    c = 1;
                    break;
                }
                break;
            case -1183073498:
                if (str.equals("flashlight")) {
                    c = 2;
                    break;
                }
                break;
            case -805491779:
                if (str.equals("screenrecord")) {
                    c = 3;
                    break;
                }
                break;
            case -795192327:
                if (str.equals("wallet")) {
                    c = 4;
                    break;
                }
                break;
            case -677011630:
                if (str.equals("airplane")) {
                    c = 5;
                    break;
                }
                break;
            case -657925702:
                if (str.equals("color_correction")) {
                    c = 6;
                    break;
                }
                break;
            case -566933834:
                if (str.equals("controls")) {
                    c = 7;
                    break;
                }
                break;
            case -343519030:
                if (str.equals("reduce_brightness")) {
                    c = 8;
                    break;
                }
                break;
            case -331239923:
                if (str.equals("battery")) {
                    c = 9;
                    break;
                }
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    c = 10;
                    break;
                }
                break;
            case -37334949:
                if (str.equals("mictoggle")) {
                    c = 11;
                    break;
                }
                break;
            case 3154:
                if (str.equals("bt")) {
                    c = 12;
                    break;
                }
                break;
            case 99610:
                if (str.equals("dnd")) {
                    c = 13;
                    break;
                }
                break;
            case 108971:
                if (str.equals("nfc")) {
                    c = 14;
                    break;
                }
                break;
            case 3046207:
                if (str.equals("cast")) {
                    c = 15;
                    break;
                }
                break;
            case 3049826:
                if (str.equals("cell")) {
                    c = 16;
                    break;
                }
                break;
            case 3075958:
                if (str.equals("dark")) {
                    c = 17;
                    break;
                }
                break;
            case 3649301:
                if (str.equals("wifi")) {
                    c = 18;
                    break;
                }
                break;
            case 3655441:
                if (str.equals("work")) {
                    c = 19;
                    break;
                }
                break;
            case 6344377:
                if (str.equals("cameratoggle")) {
                    c = 20;
                    break;
                }
                break;
            case 92895825:
                if (str.equals("alarm")) {
                    c = 21;
                    break;
                }
                break;
            case 104817688:
                if (str.equals("night")) {
                    c = 22;
                    break;
                }
                break;
            case 109211285:
                if (str.equals("saver")) {
                    c = 23;
                    break;
                }
                break;
            case 570410817:
                if (str.equals("internet")) {
                    c = 24;
                    break;
                }
                break;
            case 876619530:
                if (str.equals("qr_code_scanner")) {
                    c = 25;
                    break;
                }
                break;
            case 1099603663:
                if (str.equals("hotspot")) {
                    c = 26;
                    break;
                }
                break;
            case 1901043637:
                if (str.equals("location")) {
                    c = 27;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return this.mOneHandedModeTileProvider.get();
            case 1:
                return this.mColorInversionTileProvider.get();
            case 2:
                return this.mFlashlightTileProvider.get();
            case 3:
                return this.mScreenRecordTileProvider.get();
            case 4:
                return this.mQuickAccessWalletTileProvider.get();
            case 5:
                return this.mAirplaneModeTileProvider.get();
            case FalsingManager.VERSION:
                return this.mColorCorrectionTileProvider.get();
            case 7:
                return this.mDeviceControlsTileProvider.get();
            case 8:
                return this.mReduceBrightColorsTileProvider.get();
            case 9:
                return this.mBatterySaverTileProvider.get();
            case 10:
                return this.mRotationLockTileProvider.get();
            case QSTileImpl.C1034H.STALE /*11*/:
                return this.mMicrophoneToggleTileProvider.get();
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                return this.mBluetoothTileProvider.get();
            case C0961QS.VERSION:
                return this.mDndTileProvider.get();
            case 14:
                return this.mNfcTileProvider.get();
            case 15:
                return this.mCastTileProvider.get();
            case 16:
                return this.mCellularTileProvider.get();
            case 17:
                return this.mUiModeNightTileProvider.get();
            case 18:
                return this.mWifiTileProvider.get();
            case 19:
                return this.mWorkModeTileProvider.get();
            case 20:
                return this.mCameraToggleTileProvider.get();
            case 21:
                return this.mAlarmTileProvider.get();
            case 22:
                return this.mNightDisplayTileProvider.get();
            case 23:
                return this.mDataSaverTileProvider.get();
            case 24:
                return this.mInternetTileProvider.get();
            case 25:
                return this.mQRCodeScannerTileProvider.get();
            case 26:
                return this.mHotspotTileProvider.get();
            case 27:
                return this.mLocationTileProvider.get();
            default:
                if (str.startsWith("custom(")) {
                    CustomTile.Builder builder = this.mCustomTileBuilderProvider.get();
                    Context userContext = this.mQsHostLazy.get().getUserContext();
                    int i = CustomTile.$r8$clinit;
                    Objects.requireNonNull(builder);
                    builder.mSpec = str;
                    builder.mUserContext = userContext;
                    return builder.build();
                } else if (Build.IS_DEBUGGABLE && str.equals("dbg:mem")) {
                    return this.mMemoryTileProvider.get();
                } else {
                    MotionLayout$$ExternalSyntheticOutline0.m9m("No stock tile spec: ", str, "QSFactory");
                    return null;
                }
        }
    }

    public final QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        return new QSTileViewImpl(context, qSTile.createTileView(context), z);
    }
}
