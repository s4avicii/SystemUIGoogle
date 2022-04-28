package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import androidx.core.graphics.drawable.IconCompat;
import com.android.systemui.backup.BackupHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: ControlsFavoritePersistenceWrapper.kt */
public final class ControlsFavoritePersistenceWrapper$storeFavorites$1 implements Runnable {
    public final /* synthetic */ List<StructureInfo> $structures;
    public final /* synthetic */ ControlsFavoritePersistenceWrapper this$0;

    public ControlsFavoritePersistenceWrapper$storeFavorites$1(ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper, List<StructureInfo> list) {
        this.this$0 = controlsFavoritePersistenceWrapper;
        this.$structures = list;
    }

    public final void run() {
        boolean z;
        BackupManager backupManager;
        Log.d("ControlsFavoritePersistenceWrapper", Intrinsics.stringPlus("Saving data to file: ", this.this$0.file));
        AtomicFile atomicFile = new AtomicFile(this.this$0.file);
        Object obj = BackupHelper.controlsDataLock;
        Object obj2 = BackupHelper.controlsDataLock;
        List<StructureInfo> list = this.$structures;
        synchronized (obj2) {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                z = true;
                try {
                    XmlSerializer newSerializer = Xml.newSerializer();
                    newSerializer.setOutput(startWrite, "utf-8");
                    newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    newSerializer.startDocument((String) null, Boolean.TRUE);
                    newSerializer.startTag((String) null, "version");
                    newSerializer.text("1");
                    newSerializer.endTag((String) null, "version");
                    newSerializer.startTag((String) null, "structures");
                    for (StructureInfo structureInfo : list) {
                        newSerializer.startTag((String) null, "structure");
                        Objects.requireNonNull(structureInfo);
                        newSerializer.attribute((String) null, "component", structureInfo.componentName.flattenToString());
                        newSerializer.attribute((String) null, "structure", structureInfo.structure.toString());
                        newSerializer.startTag((String) null, "controls");
                        for (ControlInfo controlInfo : structureInfo.controls) {
                            newSerializer.startTag((String) null, "control");
                            Objects.requireNonNull(controlInfo);
                            newSerializer.attribute((String) null, "id", controlInfo.controlId);
                            newSerializer.attribute((String) null, "title", controlInfo.controlTitle.toString());
                            newSerializer.attribute((String) null, "subtitle", controlInfo.controlSubtitle.toString());
                            newSerializer.attribute((String) null, IconCompat.EXTRA_TYPE, String.valueOf(controlInfo.deviceType));
                            newSerializer.endTag((String) null, "control");
                        }
                        newSerializer.endTag((String) null, "controls");
                        newSerializer.endTag((String) null, "structure");
                    }
                    newSerializer.endTag((String) null, "structures");
                    newSerializer.endDocument();
                    atomicFile.finishWrite(startWrite);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(startWrite);
                    throw th;
                }
                IoUtils.closeQuietly(startWrite);
            } catch (IOException e) {
                Log.e("ControlsFavoritePersistenceWrapper", "Failed to start write file", e);
                return;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (z && (backupManager = this.this$0.backupManager) != null) {
            backupManager.dataChanged();
        }
    }
}
