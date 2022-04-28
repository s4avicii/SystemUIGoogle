package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.util.Log;
import android.util.Xml;
import androidx.core.graphics.drawable.IconCompat;
import com.android.systemui.backup.BackupHelper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: ControlsFavoritePersistenceWrapper.kt */
public final class ControlsFavoritePersistenceWrapper {
    public BackupManager backupManager;
    public final Executor executor;
    public File file;

    public static ArrayList parseXml(XmlPullParser xmlPullParser) {
        Integer num;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentName = null;
        String str = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return arrayList;
            }
            String name = xmlPullParser.getName();
            String str2 = "";
            if (name == null) {
                name = str2;
            }
            if (next == 2 && Intrinsics.areEqual(name, "structure")) {
                componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue((String) null, "component"));
                str = xmlPullParser.getAttributeValue((String) null, "structure");
                if (str == null) {
                    str = str2;
                }
            } else if (next == 2 && Intrinsics.areEqual(name, "control")) {
                String attributeValue = xmlPullParser.getAttributeValue((String) null, "id");
                String attributeValue2 = xmlPullParser.getAttributeValue((String) null, "title");
                String attributeValue3 = xmlPullParser.getAttributeValue((String) null, "subtitle");
                if (attributeValue3 != null) {
                    str2 = attributeValue3;
                }
                String attributeValue4 = xmlPullParser.getAttributeValue((String) null, IconCompat.EXTRA_TYPE);
                if (attributeValue4 == null) {
                    num = null;
                } else {
                    num = Integer.valueOf(Integer.parseInt(attributeValue4));
                }
                if (!(attributeValue == null || attributeValue2 == null || num == null)) {
                    arrayList2.add(new ControlInfo(attributeValue, attributeValue2, str2, num.intValue()));
                }
            } else if (next == 3 && Intrinsics.areEqual(name, "structure")) {
                Intrinsics.checkNotNull(componentName);
                Intrinsics.checkNotNull(str);
                arrayList.add(new StructureInfo(componentName, str, CollectionsKt___CollectionsKt.toList(arrayList2)));
                arrayList2.clear();
            }
        }
    }

    public final List<StructureInfo> readFavorites() {
        ArrayList parseXml;
        if (!this.file.exists()) {
            Log.d("ControlsFavoritePersistenceWrapper", "No favorites, returning empty list");
            return EmptyList.INSTANCE;
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.file));
            try {
                Log.d("ControlsFavoritePersistenceWrapper", Intrinsics.stringPlus("Reading data from file: ", this.file));
                synchronized (BackupHelper.controlsDataLock) {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(bufferedInputStream, (String) null);
                    parseXml = parseXml(newPullParser);
                }
                IoUtils.closeQuietly(bufferedInputStream);
                return parseXml;
            } catch (XmlPullParserException e) {
                throw new IllegalStateException(Intrinsics.stringPlus("Failed parsing favorites file: ", this.file), e);
            } catch (IOException e2) {
                try {
                    throw new IllegalStateException(Intrinsics.stringPlus("Failed parsing favorites file: ", this.file), e2);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(bufferedInputStream);
                    throw th;
                }
            }
        } catch (FileNotFoundException unused) {
            Log.i("ControlsFavoritePersistenceWrapper", "No file found");
            return EmptyList.INSTANCE;
        }
    }

    public ControlsFavoritePersistenceWrapper(File file2, Executor executor2, BackupManager backupManager2) {
        this.file = file2;
        this.executor = executor2;
        this.backupManager = backupManager2;
    }

    public final void storeFavorites(List<StructureInfo> list) {
        if (!list.isEmpty() || this.file.exists()) {
            this.executor.execute(new ControlsFavoritePersistenceWrapper$storeFavorites$1(this, list));
        }
    }
}
