package com.android.systemui.backup;

import android.content.Context;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: BackupHelper.kt */
final class BackupHelperKt$getPPControlsFile$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ Context $context;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BackupHelperKt$getPPControlsFile$1(Context context) {
        super(0);
        this.$context = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r1, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ab, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ae, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00af, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b2, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invoke() {
        /*
            r5 = this;
            android.content.Context r0 = r5.$context
            java.io.File r0 = r0.getFilesDir()
            java.lang.String r1 = "controls_favorites.xml"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.io.File r1 = android.os.Environment.buildPath(r0, r1)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x00c1
            java.lang.String r2 = "aux_controls_favorites.xml"
            java.lang.String[] r2 = new java.lang.String[]{r2}
            java.io.File r0 = android.os.Environment.buildPath(r0, r2)
            r2 = 0
            r3 = 8192(0x2000, float:1.14794E-41)
            boolean r4 = r1.exists()
            if (r4 == 0) goto L_0x00bb
            boolean r4 = r0.exists()
            if (r4 != 0) goto L_0x00b3
            boolean r4 = r1.isDirectory()
            if (r4 == 0) goto L_0x0044
            boolean r2 = r0.mkdirs()
            if (r2 == 0) goto L_0x003c
            goto L_0x006f
        L_0x003c:
            kotlin.io.FileSystemException r5 = new kotlin.io.FileSystemException
            java.lang.String r2 = "Failed to create target directory."
            r5.<init>(r1, r0, r2)
            throw r5
        L_0x0044:
            java.io.File r4 = r0.getParentFile()
            if (r4 != 0) goto L_0x004b
            goto L_0x004e
        L_0x004b:
            r4.mkdirs()
        L_0x004e:
            java.io.FileInputStream r4 = new java.io.FileInputStream
            r4.<init>(r1)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x00ac }
            r1.<init>(r0)     // Catch:{ all -> 0x00ac }
            byte[] r0 = new byte[r3]     // Catch:{ all -> 0x00a5 }
            int r3 = r4.read(r0)     // Catch:{ all -> 0x00a5 }
        L_0x005e:
            if (r3 < 0) goto L_0x0068
            r1.write(r0, r2, r3)     // Catch:{ all -> 0x00a5 }
            int r3 = r4.read(r0)     // Catch:{ all -> 0x00a5 }
            goto L_0x005e
        L_0x0068:
            r0 = 0
            kotlin.p018io.CloseableKt.closeFinally(r1, r0)     // Catch:{ all -> 0x00ac }
            kotlin.p018io.CloseableKt.closeFinally(r4, r0)
        L_0x006f:
            android.content.Context r0 = r5.$context
            java.lang.Class<android.app.job.JobScheduler> r1 = android.app.job.JobScheduler.class
            java.lang.Object r0 = r0.getSystemService(r1)
            android.app.job.JobScheduler r0 = (android.app.job.JobScheduler) r0
            if (r0 != 0) goto L_0x007c
            goto L_0x00c1
        L_0x007c:
            int r1 = com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper.DeletionJobService.$r8$clinit
            android.content.Context r5 = r5.$context
            int r1 = r5.getUserId()
            int r1 = r1 + 1000
            android.content.ComponentName r2 = new android.content.ComponentName
            java.lang.Class<com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper$DeletionJobService> r3 = com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper.DeletionJobService.class
            r2.<init>(r5, r3)
            android.app.job.JobInfo$Builder r5 = new android.app.job.JobInfo$Builder
            r5.<init>(r1, r2)
            long r1 = com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper.DeletionJobService.WEEK_IN_MILLIS
            android.app.job.JobInfo$Builder r5 = r5.setMinimumLatency(r1)
            r1 = 1
            android.app.job.JobInfo$Builder r5 = r5.setPersisted(r1)
            android.app.job.JobInfo r5 = r5.build()
            r0.schedule(r5)
            goto L_0x00c1
        L_0x00a5:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x00a7 }
        L_0x00a7:
            r0 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r1, r5)     // Catch:{ all -> 0x00ac }
            throw r0     // Catch:{ all -> 0x00ac }
        L_0x00ac:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x00ae }
        L_0x00ae:
            r0 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r4, r5)
            throw r0
        L_0x00b3:
            kotlin.io.FileAlreadyExistsException r5 = new kotlin.io.FileAlreadyExistsException
            java.lang.String r2 = "The destination file already exists."
            r5.<init>(r1, r0, r2)
            throw r5
        L_0x00bb:
            kotlin.io.NoSuchFileException r5 = new kotlin.io.NoSuchFileException
            r5.<init>(r1)
            throw r5
        L_0x00c1:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.backup.BackupHelperKt$getPPControlsFile$1.invoke():java.lang.Object");
    }
}
