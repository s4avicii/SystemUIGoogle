package com.android.systemui.usb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.SparseArray;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.systemui.CoreStartable;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Objects;

public class StorageNotification extends CoreStartable {
    public final C17013 mFinishReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            StorageNotification.this.mNotificationManager.cancelAsUser((String) null, 1397575510, UserHandle.ALL);
        }
    };
    public final C16991 mListener = new StorageEventListener() {
        public final void onDiskDestroyed(DiskInfo diskInfo) {
            StorageNotification storageNotification = StorageNotification.this;
            Objects.requireNonNull(storageNotification);
            storageNotification.mNotificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
        }

        public final void onDiskScanned(DiskInfo diskInfo, int i) {
            StorageNotification.this.onDiskScannedInternal(diskInfo, i);
        }

        public final void onVolumeForgotten(String str) {
            StorageNotification.this.mNotificationManager.cancelAsUser(str, 1397772886, UserHandle.ALL);
        }

        public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
            VolumeInfo findVolumeByUuid = StorageNotification.this.mStorageManager.findVolumeByUuid(volumeRecord.getFsUuid());
            if (findVolumeByUuid != null && findVolumeByUuid.isMountedReadable()) {
                StorageNotification.this.onVolumeStateChangedInternal(findVolumeByUuid);
            }
        }

        public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            StorageNotification.this.onVolumeStateChangedInternal(volumeInfo);
        }
    };
    public final C17024 mMoveCallback = new PackageManager.MoveCallback() {
        public final void onCreated(int i, Bundle bundle) {
            MoveInfo moveInfo = new MoveInfo(0);
            moveInfo.moveId = i;
            if (bundle != null) {
                moveInfo.packageName = bundle.getString("android.intent.extra.PACKAGE_NAME");
                moveInfo.label = bundle.getString("android.intent.extra.TITLE");
                moveInfo.volumeUuid = bundle.getString("android.os.storage.extra.FS_UUID");
            }
            StorageNotification.this.mMoves.put(i, moveInfo);
        }

        public final void onStatusChanged(int i, int i2, long j) {
            String str;
            CharSequence charSequence;
            PendingIntent pendingIntent;
            String str2;
            String str3;
            PendingIntent pendingIntent2;
            int i3 = i;
            int i4 = i2;
            MoveInfo moveInfo = StorageNotification.this.mMoves.get(i3);
            if (moveInfo == null) {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("Ignoring unknown move ", i3, "StorageNotification");
            } else if (PackageManager.isMoveStatusFinished(i2)) {
                StorageNotification storageNotification = StorageNotification.this;
                Objects.requireNonNull(storageNotification);
                String str4 = moveInfo.packageName;
                if (str4 != null) {
                    storageNotification.mNotificationManager.cancelAsUser(str4, 1397575510, UserHandle.ALL);
                    return;
                }
                VolumeInfo primaryStorageCurrentVolume = storageNotification.mContext.getPackageManager().getPrimaryStorageCurrentVolume();
                String bestVolumeDescription = storageNotification.mStorageManager.getBestVolumeDescription(primaryStorageCurrentVolume);
                if (i4 == -100) {
                    str3 = storageNotification.mContext.getString(17040220);
                    str2 = storageNotification.mContext.getString(17040219, new Object[]{bestVolumeDescription});
                } else {
                    str3 = storageNotification.mContext.getString(17040217);
                    str2 = storageNotification.mContext.getString(17040216);
                }
                if (primaryStorageCurrentVolume != null && primaryStorageCurrentVolume.getDisk() != null) {
                    DiskInfo disk = primaryStorageCurrentVolume.getDisk();
                    Intent intent = new Intent();
                    if (storageNotification.isTv()) {
                        intent.setPackage("com.android.tv.settings");
                        intent.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                    } else if (!storageNotification.isAutomotive()) {
                        intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageWizardReady");
                    }
                    intent.putExtra("android.os.storage.extra.DISK_ID", disk.getId());
                    pendingIntent2 = PendingIntent.getActivityAsUser(storageNotification.mContext, disk.getId().hashCode(), intent, 335544320, (Bundle) null, UserHandle.CURRENT);
                    Notification.Builder autoCancel = new Notification.Builder(storageNotification.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification.mContext.getColor(17170460)).setContentTitle(str3).setContentText(str2).setContentIntent(pendingIntent2).setStyle(new Notification.BigTextStyle().bigText(str2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setAutoCancel(true);
                    SystemUIApplication.overrideNotificationAppName(storageNotification.mContext, autoCancel, false);
                    storageNotification.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, autoCancel.build(), UserHandle.ALL);
                } else if (primaryStorageCurrentVolume != null) {
                    Intent intent2 = new Intent();
                    if (storageNotification.isTv()) {
                        intent2.setPackage("com.android.tv.settings");
                        intent2.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                    } else if (!storageNotification.isAutomotive()) {
                        int type = primaryStorageCurrentVolume.getType();
                        if (type == 0) {
                            intent2.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.Settings$PublicVolumeSettingsActivity");
                        } else if (type == 1) {
                            intent2.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.Settings$PrivateVolumeSettingsActivity");
                        }
                    }
                    intent2.putExtra("android.os.storage.extra.VOLUME_ID", primaryStorageCurrentVolume.getId());
                    pendingIntent2 = PendingIntent.getActivityAsUser(storageNotification.mContext, primaryStorageCurrentVolume.getId().hashCode(), intent2, 335544320, (Bundle) null, UserHandle.CURRENT);
                    Notification.Builder autoCancel2 = new Notification.Builder(storageNotification.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification.mContext.getColor(17170460)).setContentTitle(str3).setContentText(str2).setContentIntent(pendingIntent2).setStyle(new Notification.BigTextStyle().bigText(str2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setAutoCancel(true);
                    SystemUIApplication.overrideNotificationAppName(storageNotification.mContext, autoCancel2, false);
                    storageNotification.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, autoCancel2.build(), UserHandle.ALL);
                }
                pendingIntent2 = null;
                Notification.Builder autoCancel22 = new Notification.Builder(storageNotification.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification.mContext.getColor(17170460)).setContentTitle(str3).setContentText(str2).setContentIntent(pendingIntent2).setStyle(new Notification.BigTextStyle().bigText(str2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setAutoCancel(true);
                SystemUIApplication.overrideNotificationAppName(storageNotification.mContext, autoCancel22, false);
                storageNotification.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, autoCancel22.build(), UserHandle.ALL);
            } else {
                StorageNotification storageNotification2 = StorageNotification.this;
                Objects.requireNonNull(storageNotification2);
                if (!TextUtils.isEmpty(moveInfo.label)) {
                    str = storageNotification2.mContext.getString(17040218, new Object[]{moveInfo.label});
                } else {
                    str = storageNotification2.mContext.getString(17040221);
                }
                if (j < 0) {
                    charSequence = null;
                } else {
                    charSequence = DateUtils.formatDuration(j);
                }
                if (moveInfo.packageName != null) {
                    Intent intent3 = new Intent();
                    if (storageNotification2.isTv()) {
                        intent3.setPackage("com.android.tv.settings");
                        intent3.setAction("com.android.tv.settings.action.MOVE_APP");
                    } else if (!storageNotification2.isAutomotive()) {
                        intent3.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageWizardMoveProgress");
                    }
                    intent3.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                    pendingIntent = PendingIntent.getActivityAsUser(storageNotification2.mContext, moveInfo.moveId, intent3, 335544320, (Bundle) null, UserHandle.CURRENT);
                    Notification.Builder ongoing = new Notification.Builder(storageNotification2.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification2.mContext.getColor(17170460)).setContentTitle(str).setContentText(charSequence).setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(charSequence)).setVisibility(1).setLocalOnly(true).setCategory("progress").setProgress(100, i4, false).setOngoing(true);
                    SystemUIApplication.overrideNotificationAppName(storageNotification2.mContext, ongoing, false);
                    storageNotification2.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, ongoing.build(), UserHandle.ALL);
                }
                Intent intent4 = new Intent();
                if (storageNotification2.isTv()) {
                    intent4.setPackage("com.android.tv.settings");
                    intent4.setAction("com.android.tv.settings.action.MIGRATE_STORAGE");
                } else if (!storageNotification2.isAutomotive()) {
                    intent4.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageWizardMigrateProgress");
                }
                intent4.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                VolumeInfo findVolumeByQualifiedUuid = storageNotification2.mStorageManager.findVolumeByQualifiedUuid(moveInfo.volumeUuid);
                if (findVolumeByQualifiedUuid != null) {
                    intent4.putExtra("android.os.storage.extra.VOLUME_ID", findVolumeByQualifiedUuid.getId());
                }
                pendingIntent = PendingIntent.getActivityAsUser(storageNotification2.mContext, moveInfo.moveId, intent4, 335544320, (Bundle) null, UserHandle.CURRENT);
                Notification.Builder ongoing2 = new Notification.Builder(storageNotification2.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification2.mContext.getColor(17170460)).setContentTitle(str).setContentText(charSequence).setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(charSequence)).setVisibility(1).setLocalOnly(true).setCategory("progress").setProgress(100, i4, false).setOngoing(true);
                SystemUIApplication.overrideNotificationAppName(storageNotification2.mContext, ongoing2, false);
                storageNotification2.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, ongoing2.build(), UserHandle.ALL);
                pendingIntent = null;
                Notification.Builder ongoing22 = new Notification.Builder(storageNotification2.mContext, "DSK").setSmallIcon(17302834).setColor(storageNotification2.mContext.getColor(17170460)).setContentTitle(str).setContentText(charSequence).setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(charSequence)).setVisibility(1).setLocalOnly(true).setCategory("progress").setProgress(100, i4, false).setOngoing(true);
                SystemUIApplication.overrideNotificationAppName(storageNotification2.mContext, ongoing22, false);
                storageNotification2.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, ongoing22.build(), UserHandle.ALL);
            }
        }
    };
    public final SparseArray<MoveInfo> mMoves = new SparseArray<>();
    public NotificationManager mNotificationManager;
    public final C17002 mSnoozeReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            StorageNotification.this.mStorageManager.setVolumeSnoozed(intent.getStringExtra("android.os.storage.extra.FS_UUID"), true);
        }
    };
    public StorageManager mStorageManager;

    public static class MoveInfo {
        public String label;
        public int moveId;
        public String packageName;
        public String volumeUuid;

        public MoveInfo() {
        }

        public MoveInfo(int i) {
        }
    }

    public final PendingIntent buildInitPendingIntent(VolumeInfo volumeInfo) {
        Intent intent = new Intent();
        if (isTv()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.NEW_STORAGE");
        } else if (isAutomotive()) {
            return null;
        } else {
            intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageWizardInit");
        }
        intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
        return PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, (Bundle) null, UserHandle.CURRENT);
    }

    public final Notification.Builder buildNotificationBuilder(VolumeInfo volumeInfo, String str, String str2) {
        Notification.Builder builder = new Notification.Builder(this.mContext, "DSK");
        DiskInfo disk = volumeInfo.getDisk();
        volumeInfo.getState();
        int i = 17302834;
        if (!disk.isSd() && disk.isUsb()) {
            i = 17302876;
        }
        Notification.Builder extend = builder.setSmallIcon(i).setColor(this.mContext.getColor(17170460)).setContentTitle(str).setContentText(str2).setStyle(new Notification.BigTextStyle().bigText(str2)).setVisibility(1).setLocalOnly(true).extend(new Notification.TvExtender());
        SystemUIApplication.overrideNotificationAppName(this.mContext, extend, false);
        return extend;
    }

    public final PendingIntent buildSnoozeIntent(String str) {
        Intent intent = new Intent("com.android.systemui.action.SNOOZE_VOLUME");
        intent.putExtra("android.os.storage.extra.FS_UUID", str);
        return PendingIntent.getBroadcastAsUser(this.mContext, str.hashCode(), intent, 335544320, UserHandle.CURRENT);
    }

    public final PendingIntent buildUnmountPendingIntent(VolumeInfo volumeInfo) {
        Intent intent = new Intent();
        if (isTv()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.UNMOUNT_STORAGE");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, (Bundle) null, UserHandle.CURRENT);
        } else if (isAutomotive()) {
            intent.setClassName("com.android.car.settings", "com.android.car.settings.storage.StorageUnmountReceiver");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getBroadcastAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
        } else {
            intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageUnmountReceiver");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getBroadcastAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
        }
    }

    public final boolean isAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    public final boolean isTv() {
        return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    public final void start() {
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        this.mStorageManager = storageManager;
        storageManager.registerListener(this.mListener);
        this.mContext.registerReceiver(this.mSnoozeReceiver, new IntentFilter("com.android.systemui.action.SNOOZE_VOLUME"), "android.permission.MOUNT_UNMOUNT_FILESYSTEMS", (Handler) null, 2);
        this.mContext.registerReceiver(this.mFinishReceiver, new IntentFilter("com.android.systemui.action.FINISH_WIZARD"), "android.permission.MOUNT_UNMOUNT_FILESYSTEMS", (Handler) null, 2);
        for (DiskInfo diskInfo : this.mStorageManager.getDisks()) {
            onDiskScannedInternal(diskInfo, diskInfo.volumeCount);
        }
        for (VolumeInfo onVolumeStateChangedInternal : this.mStorageManager.getVolumes()) {
            onVolumeStateChangedInternal(onVolumeStateChangedInternal);
        }
        this.mContext.getPackageManager().registerMoveCallback(this.mMoveCallback, new Handler());
        updateMissingPrivateVolumes();
    }

    public final void updateMissingPrivateVolumes() {
        if (!isTv() && !isAutomotive()) {
            for (VolumeRecord volumeRecord : this.mStorageManager.getVolumeRecords()) {
                if (volumeRecord.getType() == 1) {
                    String fsUuid = volumeRecord.getFsUuid();
                    VolumeInfo findVolumeByUuid = this.mStorageManager.findVolumeByUuid(fsUuid);
                    if ((findVolumeByUuid == null || !findVolumeByUuid.isMountedWritable()) && !volumeRecord.isSnoozed()) {
                        String string = this.mContext.getString(17040215, new Object[]{volumeRecord.getNickname()});
                        String string2 = this.mContext.getString(17040214);
                        Notification.Builder contentText = new Notification.Builder(this.mContext, "DSK").setSmallIcon(17302834).setColor(this.mContext.getColor(17170460)).setContentTitle(string).setContentText(string2);
                        Intent intent = new Intent();
                        intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.Settings$PrivateVolumeForgetActivity");
                        intent.putExtra("android.os.storage.extra.FS_UUID", volumeRecord.getFsUuid());
                        Notification.Builder extend = contentText.setContentIntent(PendingIntent.getActivityAsUser(this.mContext, volumeRecord.getFsUuid().hashCode(), intent, 335544320, (Bundle) null, UserHandle.CURRENT)).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setDeleteIntent(buildSnoozeIntent(fsUuid)).extend(new Notification.TvExtender());
                        SystemUIApplication.overrideNotificationAppName(this.mContext, extend, false);
                        this.mNotificationManager.notifyAsUser(fsUuid, 1397772886, extend.build(), UserHandle.ALL);
                    } else {
                        this.mNotificationManager.cancelAsUser(fsUuid, 1397772886, UserHandle.ALL);
                    }
                }
            }
        }
    }

    public StorageNotification(Context context) {
        super(context);
    }

    public final void onDiskScannedInternal(DiskInfo diskInfo, int i) {
        PendingIntent pendingIntent;
        if (i != 0 || diskInfo.size <= 0) {
            this.mNotificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
            return;
        }
        String string = this.mContext.getString(17040245, new Object[]{diskInfo.getDescription()});
        String string2 = this.mContext.getString(17040244, new Object[]{diskInfo.getDescription()});
        Notification.Builder builder = new Notification.Builder(this.mContext, "DSK");
        int i2 = 17302834;
        if (!diskInfo.isSd() && diskInfo.isUsb()) {
            i2 = 17302876;
        }
        Notification.Builder contentText = builder.setSmallIcon(i2).setColor(this.mContext.getColor(17170460)).setContentTitle(string).setContentText(string2);
        Intent intent = new Intent();
        if (isTv()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.NEW_STORAGE");
        } else if (isAutomotive()) {
            pendingIntent = null;
            Notification.Builder extend = contentText.setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender());
            SystemUIApplication.overrideNotificationAppName(this.mContext, extend, false);
            this.mNotificationManager.notifyAsUser(diskInfo.getId(), 1396986699, extend.build(), UserHandle.ALL);
        } else {
            intent.setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.deviceinfo.StorageWizardInit");
        }
        intent.putExtra("android.os.storage.extra.DISK_ID", diskInfo.getId());
        pendingIntent = PendingIntent.getActivityAsUser(this.mContext, diskInfo.getId().hashCode(), intent, 335544320, (Bundle) null, UserHandle.CURRENT);
        Notification.Builder extend2 = contentText.setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender());
        SystemUIApplication.overrideNotificationAppName(this.mContext, extend2, false);
        this.mNotificationManager.notifyAsUser(diskInfo.getId(), 1396986699, extend2.build(), UserHandle.ALL);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00cd, code lost:
        r5 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onVolumeStateChangedInternal(android.os.storage.VolumeInfo r14) {
        /*
            r13 = this;
            int r0 = r14.getType()
            java.lang.String r1 = "StorageNotification"
            r2 = 1
            if (r0 == 0) goto L_0x0026
            if (r0 == r2) goto L_0x000d
            goto L_0x02e0
        L_0x000d:
            java.lang.String r0 = "Notifying about private volume: "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r14 = r14.toString()
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            android.util.Log.d(r1, r14)
            r13.updateMissingPrivateVolumes()
            goto L_0x02e0
        L_0x0026:
            java.lang.String r0 = "Notifying about public volume: "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r3 = r14.toString()
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
            int r0 = r14.getMountUserId()
            r3 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r0 != r3) goto L_0x0049
            java.lang.String r13 = "Ignore public volume state change event of removed user"
            android.util.Log.d(r1, r13)
            goto L_0x02e0
        L_0x0049:
            int r0 = r14.getState()
            java.lang.String r1 = "progress"
            java.lang.String r3 = "err"
            r4 = 0
            r5 = 0
            switch(r0) {
                case 1: goto L_0x0282;
                case 2: goto L_0x014f;
                case 3: goto L_0x014f;
                case 4: goto L_0x0056;
                case 5: goto L_0x0117;
                case 6: goto L_0x00d0;
                case 7: goto L_0x0093;
                case 8: goto L_0x0058;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x02b8
        L_0x0058:
            boolean r0 = r14.isPrimary()
            if (r0 != 0) goto L_0x0060
            goto L_0x02b8
        L_0x0060:
            android.os.storage.DiskInfo r0 = r14.getDisk()
            android.content.Context r1 = r13.mContext
            r5 = 17040209(0x1040351, float:2.424695E-38)
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = r0.getDescription()
            r6[r4] = r7
            java.lang.String r1 = r1.getString(r5, r6)
            android.content.Context r5 = r13.mContext
            r6 = 17040208(0x1040350, float:2.4246948E-38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r0 = r0.getDescription()
            r2[r4] = r0
            java.lang.String r0 = r5.getString(r6, r2)
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r1, r0)
            android.app.Notification$Builder r0 = r0.setCategory(r3)
            android.app.Notification r0 = r0.build()
            goto L_0x00cd
        L_0x0093:
            boolean r0 = r14.isPrimary()
            if (r0 != 0) goto L_0x009b
            goto L_0x02b8
        L_0x009b:
            android.os.storage.DiskInfo r0 = r14.getDisk()
            android.content.Context r1 = r13.mContext
            r5 = 17040225(0x1040361, float:2.4246995E-38)
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = r0.getDescription()
            r6[r4] = r7
            java.lang.String r1 = r1.getString(r5, r6)
            android.content.Context r5 = r13.mContext
            r6 = 17040224(0x1040360, float:2.4246992E-38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r0 = r0.getDescription()
            r2[r4] = r0
            java.lang.String r0 = r5.getString(r6, r2)
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r1, r0)
            android.app.Notification$Builder r0 = r0.setCategory(r3)
            android.app.Notification r0 = r0.build()
        L_0x00cd:
            r5 = r0
            goto L_0x02b8
        L_0x00d0:
            android.os.storage.DiskInfo r0 = r14.getDisk()
            android.content.Context r1 = r13.mContext
            java.lang.Object[] r5 = new java.lang.Object[r2]
            java.lang.String r6 = r0.getDescription()
            r5[r4] = r6
            r6 = 17040241(0x1040371, float:2.424704E-38)
            java.lang.String r1 = r1.getString(r6, r5)
            android.content.Context r5 = r13.mContext
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r0 = r0.getDescription()
            r2[r4] = r0
            r0 = 17040240(0x1040370, float:2.4247037E-38)
            java.lang.String r0 = r5.getString(r0, r2)
            boolean r2 = r13.isAutomotive()
            if (r2 == 0) goto L_0x0101
            android.app.PendingIntent r2 = r13.buildUnmountPendingIntent(r14)
            goto L_0x0105
        L_0x0101:
            android.app.PendingIntent r2 = r13.buildInitPendingIntent(r14)
        L_0x0105:
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r1, r0)
            android.app.Notification$Builder r0 = r0.setContentIntent(r2)
            android.app.Notification$Builder r0 = r0.setCategory(r3)
            android.app.Notification r5 = r0.build()
            goto L_0x02b8
        L_0x0117:
            android.os.storage.DiskInfo r0 = r14.getDisk()
            android.content.Context r3 = r13.mContext
            java.lang.Object[] r5 = new java.lang.Object[r2]
            java.lang.String r6 = r0.getDescription()
            r5[r4] = r6
            r6 = 17040243(0x1040373, float:2.4247046E-38)
            java.lang.String r3 = r3.getString(r6, r5)
            android.content.Context r5 = r13.mContext
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r0 = r0.getDescription()
            r6[r4] = r0
            r0 = 17040242(0x1040372, float:2.4247043E-38)
            java.lang.String r0 = r5.getString(r0, r6)
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r3, r0)
            android.app.Notification$Builder r0 = r0.setCategory(r1)
            android.app.Notification$Builder r0 = r0.setOngoing(r2)
            android.app.Notification r5 = r0.build()
            goto L_0x02b8
        L_0x014f:
            android.os.storage.StorageManager r0 = r13.mStorageManager
            java.lang.String r1 = r14.getFsUuid()
            android.os.storage.VolumeRecord r0 = r0.findRecordByUuid(r1)
            android.os.storage.DiskInfo r1 = r14.getDisk()
            boolean r3 = r0.isSnoozed()
            if (r3 == 0) goto L_0x016b
            boolean r3 = r1.isAdoptable()
            if (r3 == 0) goto L_0x016b
            goto L_0x02b8
        L_0x016b:
            boolean r3 = r1.isAdoptable()
            r5 = 17040239(0x104036f, float:2.4247034E-38)
            r6 = 17302433(0x10803a1, float:2.4981859E-38)
            if (r3 == 0) goto L_0x01f8
            boolean r0 = r0.isInited()
            if (r0 != 0) goto L_0x01f8
            java.lang.String r0 = r1.getDescription()
            android.content.Context r3 = r13.mContext
            r7 = 17040222(0x104035e, float:2.4246987E-38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r1 = r1.getDescription()
            r2[r4] = r1
            java.lang.String r1 = r3.getString(r7, r2)
            android.app.PendingIntent r2 = r13.buildInitPendingIntent(r14)
            android.app.PendingIntent r3 = r13.buildUnmountPendingIntent(r14)
            boolean r4 = r13.isAutomotive()
            if (r4 == 0) goto L_0x01ba
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r0, r1)
            android.app.Notification$Builder r0 = r0.setContentIntent(r3)
            java.lang.String r1 = r14.getFsUuid()
            android.app.PendingIntent r1 = r13.buildSnoozeIntent(r1)
            android.app.Notification$Builder r0 = r0.setDeleteIntent(r1)
            android.app.Notification r0 = r0.build()
            goto L_0x00cd
        L_0x01ba:
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r0, r1)
            android.app.Notification$Action r1 = new android.app.Notification$Action
            r4 = 17302840(0x1080538, float:2.4983E-38)
            android.content.Context r7 = r13.mContext
            r8 = 17040213(0x1040355, float:2.4246962E-38)
            java.lang.String r7 = r7.getString(r8)
            r1.<init>(r4, r7, r2)
            android.app.Notification$Builder r0 = r0.addAction(r1)
            android.app.Notification$Action r1 = new android.app.Notification$Action
            android.content.Context r4 = r13.mContext
            java.lang.String r4 = r4.getString(r5)
            r1.<init>(r6, r4, r3)
            android.app.Notification$Builder r0 = r0.addAction(r1)
            android.app.Notification$Builder r0 = r0.setContentIntent(r2)
            java.lang.String r1 = r14.getFsUuid()
            android.app.PendingIntent r1 = r13.buildSnoozeIntent(r1)
            android.app.Notification$Builder r0 = r0.setDeleteIntent(r1)
            android.app.Notification r0 = r0.build()
            goto L_0x00cd
        L_0x01f8:
            java.lang.String r0 = r1.getDescription()
            android.content.Context r3 = r13.mContext
            r7 = 17040226(0x1040362, float:2.4246998E-38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r8 = r1.getDescription()
            r2[r4] = r8
            java.lang.String r2 = r3.getString(r7, r2)
            android.os.StrictMode$VmPolicy r3 = android.os.StrictMode.allowVmViolations()
            int r4 = r14.getMountUserId()     // Catch:{ all -> 0x027d }
            android.content.Intent r9 = r14.buildBrowseIntentForUser(r4)     // Catch:{ all -> 0x027d }
            java.lang.String r4 = r14.getId()     // Catch:{ all -> 0x027d }
            int r8 = r4.hashCode()     // Catch:{ all -> 0x027d }
            android.content.Context r7 = r13.mContext     // Catch:{ all -> 0x027d }
            r10 = 335544320(0x14000000, float:6.4623485E-27)
            r11 = 0
            android.os.UserHandle r12 = android.os.UserHandle.CURRENT     // Catch:{ all -> 0x027d }
            android.app.PendingIntent r4 = android.app.PendingIntent.getActivityAsUser(r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x027d }
            android.os.StrictMode.setVmPolicy(r3)
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r0, r2)
            android.app.Notification$Action r2 = new android.app.Notification$Action
            r3 = 17302456(0x10803b8, float:2.4981923E-38)
            android.content.Context r7 = r13.mContext
            r8 = 17040210(0x1040352, float:2.4246953E-38)
            java.lang.String r7 = r7.getString(r8)
            r2.<init>(r3, r7, r4)
            android.app.Notification$Builder r0 = r0.addAction(r2)
            android.app.Notification$Action r2 = new android.app.Notification$Action
            android.content.Context r3 = r13.mContext
            java.lang.String r3 = r3.getString(r5)
            android.app.PendingIntent r5 = r13.buildUnmountPendingIntent(r14)
            r2.<init>(r6, r3, r5)
            android.app.Notification$Builder r0 = r0.addAction(r2)
            android.app.Notification$Builder r0 = r0.setContentIntent(r4)
            java.lang.String r2 = "sys"
            android.app.Notification$Builder r0 = r0.setCategory(r2)
            boolean r1 = r1.isAdoptable()
            if (r1 == 0) goto L_0x0277
            java.lang.String r1 = r14.getFsUuid()
            android.app.PendingIntent r1 = r13.buildSnoozeIntent(r1)
            r0.setDeleteIntent(r1)
        L_0x0277:
            android.app.Notification r0 = r0.build()
            goto L_0x00cd
        L_0x027d:
            r13 = move-exception
            android.os.StrictMode.setVmPolicy(r3)
            throw r13
        L_0x0282:
            android.os.storage.DiskInfo r0 = r14.getDisk()
            android.content.Context r3 = r13.mContext
            java.lang.Object[] r5 = new java.lang.Object[r2]
            java.lang.String r6 = r0.getDescription()
            r5[r4] = r6
            r6 = 17040212(0x1040354, float:2.424696E-38)
            java.lang.String r3 = r3.getString(r6, r5)
            android.content.Context r5 = r13.mContext
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r0 = r0.getDescription()
            r6[r4] = r0
            r0 = 17040211(0x1040353, float:2.4246956E-38)
            java.lang.String r0 = r5.getString(r0, r6)
            android.app.Notification$Builder r0 = r13.buildNotificationBuilder(r14, r3, r0)
            android.app.Notification$Builder r0 = r0.setCategory(r1)
            android.app.Notification$Builder r0 = r0.setOngoing(r2)
            android.app.Notification r5 = r0.build()
        L_0x02b8:
            r0 = 1397773634(0x53505542, float:8.9478359E11)
            if (r5 == 0) goto L_0x02cf
            android.app.NotificationManager r13 = r13.mNotificationManager
            java.lang.String r1 = r14.getId()
            int r14 = r14.getMountUserId()
            android.os.UserHandle r14 = android.os.UserHandle.of(r14)
            r13.notifyAsUser(r1, r0, r5, r14)
            goto L_0x02e0
        L_0x02cf:
            android.app.NotificationManager r13 = r13.mNotificationManager
            java.lang.String r1 = r14.getId()
            int r14 = r14.getMountUserId()
            android.os.UserHandle r14 = android.os.UserHandle.of(r14)
            r13.cancelAsUser(r1, r0, r14)
        L_0x02e0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.usb.StorageNotification.onVolumeStateChangedInternal(android.os.storage.VolumeInfo):void");
    }
}
