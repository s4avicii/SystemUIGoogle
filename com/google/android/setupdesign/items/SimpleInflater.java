package com.google.android.setupdesign.items;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.InflateException;
import com.google.android.setupdesign.items.ItemInflater;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

public abstract class SimpleInflater<T> {
    public final Resources resources;

    public abstract T onCreateItem(String str, AttributeSet attributeSet);

    public SimpleInflater(Resources resources2) {
        this.resources = resources2;
    }

    public final T createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            return onCreateItem(str, attributeSet);
        } catch (InflateException e) {
            throw e;
        } catch (Exception e2) {
            throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str, e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001d A[Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0011 A[Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object inflate(android.content.res.XmlResourceParser r5) {
        /*
            r4 = this;
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r5)
        L_0x0004:
            int r1 = r5.next()     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            r2 = 2
            if (r1 == r2) goto L_0x000f
            r3 = 1
            if (r1 == r3) goto L_0x000f
            goto L_0x0004
        L_0x000f:
            if (r1 != r2) goto L_0x001d
            java.lang.String r1 = r5.getName()     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            java.lang.Object r1 = r4.createItemFromTag(r1, r0)     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            r4.rInflate(r5, r1, r0)     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            return r1
        L_0x001d:
            android.view.InflateException r4 = new android.view.InflateException     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            r0.<init>()     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            java.lang.String r1 = r5.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            r0.append(r1)     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            java.lang.String r1 = ": No start tag found!"
            r0.append(r1)     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            java.lang.String r0 = r0.toString()     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            r4.<init>(r0)     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
            throw r4     // Catch:{ XmlPullParserException -> 0x005b, IOException -> 0x0038 }
        L_0x0038:
            r4 = move-exception
            android.view.InflateException r0 = new android.view.InflateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = r5.getPositionDescription()
            r1.append(r5)
            java.lang.String r5 = ": "
            r1.append(r5)
            java.lang.String r5 = r4.getMessage()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5, r4)
            throw r0
        L_0x005b:
            r4 = move-exception
            android.view.InflateException r5 = new android.view.InflateException
            java.lang.String r0 = r4.getMessage()
            r5.<init>(r0, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.items.SimpleInflater.inflate(android.content.res.XmlResourceParser):java.lang.Object");
    }

    public final void rInflate(XmlResourceParser xmlResourceParser, Object obj, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if ((next == 3 && xmlResourceParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                Object createItemFromTag = createItemFromTag(xmlResourceParser.getName(), attributeSet);
                ItemHierarchy itemHierarchy = (ItemHierarchy) obj;
                ItemHierarchy itemHierarchy2 = (ItemHierarchy) createItemFromTag;
                if (itemHierarchy instanceof ItemInflater.ItemParent) {
                    ((ItemInflater.ItemParent) itemHierarchy).addChild(itemHierarchy2);
                    rInflate(xmlResourceParser, createItemFromTag, attributeSet);
                } else {
                    throw new IllegalArgumentException("Cannot add child item to " + itemHierarchy);
                }
            }
        }
    }
}
