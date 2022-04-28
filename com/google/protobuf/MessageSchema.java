package com.google.protobuf;

import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.protobuf.UnsafeUtil;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import sun.misc.Unsafe;

public final class MessageSchema<T> implements Schema<T> {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Unsafe UNSAFE;
    public final int[] buffer;
    public final int checkInitializedCount;
    public final MessageLite defaultInstance;
    public final ExtensionSchema<?> extensionSchema;
    public final boolean hasExtensions;
    public final int[] intArray;
    public final ListFieldSchema listFieldSchema;
    public final boolean lite;
    public final MapFieldSchema mapFieldSchema;
    public final int maxFieldNumber;
    public final int minFieldNumber;
    public final NewInstanceSchema newInstanceSchema;
    public final Object[] objects;
    public final boolean proto3;
    public final int repeatedFieldOffsetStart;
    public final UnknownFieldSchema<?, ?> unknownFieldSchema;
    public final boolean useCachedSizeField;

    static {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                public final java.lang.Object run(
/*
Method generation error in method: com.google.protobuf.UnsafeUtil.1.run():java.lang.Object, dex: classes.dex
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.google.protobuf.UnsafeUtil.1.run():java.lang.Object, class status: UNLOADED
                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:291)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:311)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:68)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                
*/
            });
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
    }

    public final boolean isInitialized(T t) {
        int i;
        boolean z;
        boolean z2;
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            boolean z3 = true;
            if (i3 >= this.checkInitializedCount) {
                return !this.hasExtensions || this.extensionSchema.getExtensions(t).isInitialized();
            }
            int i5 = this.intArray[i3];
            int i6 = this.buffer[i5];
            int typeAndOffsetAt = typeAndOffsetAt(i5);
            if (!this.proto3) {
                int i7 = this.buffer[i5 + 2];
                int i8 = i7 & 1048575;
                i = 1 << (i7 >>> 20);
                if (i8 != i2) {
                    i4 = UNSAFE.getInt(t, (long) i8);
                    i2 = i8;
                }
            } else {
                i = 0;
            }
            if ((268435456 & typeAndOffsetAt) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                if (this.proto3) {
                    z2 = isFieldPresent(t, i5);
                } else if ((i4 & i) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    return false;
                }
            }
            int i9 = (267386880 & typeAndOffsetAt) >>> 20;
            if (i9 == 9 || i9 == 17) {
                if (this.proto3) {
                    z3 = isFieldPresent(t, i5);
                } else if ((i4 & i) == 0) {
                    z3 = false;
                }
                if (z3 && !getMessageFieldSchema(i5).isInitialized(UnsafeUtil.getObject(t, (long) (typeAndOffsetAt & 1048575)))) {
                    return false;
                }
            } else {
                if (i9 != 27) {
                    if (i9 == 60 || i9 == 68) {
                        if (isOneofPresent(t, i6, i5) && !getMessageFieldSchema(i5).isInitialized(UnsafeUtil.getObject(t, (long) (typeAndOffsetAt & 1048575)))) {
                            return false;
                        }
                    } else if (i9 != 49) {
                        if (i9 == 50 && !this.mapFieldSchema.forMapData(UnsafeUtil.getObject(t, (long) (typeAndOffsetAt & 1048575))).isEmpty()) {
                            this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i5));
                            throw null;
                        }
                    }
                }
                List list = (List) UnsafeUtil.getObject(t, (long) (typeAndOffsetAt & 1048575));
                if (!list.isEmpty()) {
                    Schema messageFieldSchema = getMessageFieldSchema(i5);
                    int i10 = 0;
                    while (true) {
                        if (i10 >= list.size()) {
                            break;
                        } else if (!messageFieldSchema.isInitialized(list.get(i10))) {
                            z3 = false;
                            break;
                        } else {
                            i10++;
                        }
                    }
                }
                if (!z3) {
                    return false;
                }
            }
            i3++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:125:0x0296  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0299  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x02b3  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x036a  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x03b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.protobuf.MessageSchema newSchema(com.google.protobuf.MessageInfo r35, com.google.protobuf.NewInstanceSchema r36, com.google.protobuf.ListFieldSchema r37, com.google.protobuf.UnknownFieldSchema r38, com.google.protobuf.ExtensionSchema r39, com.google.protobuf.MapFieldSchema r40) {
        /*
            r0 = r35
            boolean r1 = r0 instanceof com.google.protobuf.RawMessageInfo
            if (r1 == 0) goto L_0x0435
            com.google.protobuf.RawMessageInfo r0 = (com.google.protobuf.RawMessageInfo) r0
            com.google.protobuf.ProtoSyntax r1 = r0.getSyntax()
            com.google.protobuf.ProtoSyntax r2 = com.google.protobuf.ProtoSyntax.PROTO3
            r3 = 0
            if (r1 != r2) goto L_0x0013
            r11 = 1
            goto L_0x0014
        L_0x0013:
            r11 = r3
        L_0x0014:
            java.lang.String r1 = r0.info
            int r2 = r1.length()
            char r5 = r1.charAt(r3)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r6) goto L_0x003b
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r8 = 1
            r9 = 13
        L_0x0028:
            int r10 = r8 + 1
            char r8 = r1.charAt(r8)
            if (r8 < r6) goto L_0x0038
            r8 = r8 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r9
            r5 = r5 | r8
            int r9 = r9 + 13
            r8 = r10
            goto L_0x0028
        L_0x0038:
            int r8 = r8 << r9
            r5 = r5 | r8
            goto L_0x003c
        L_0x003b:
            r10 = 1
        L_0x003c:
            int r8 = r10 + 1
            char r9 = r1.charAt(r10)
            if (r9 < r6) goto L_0x005b
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0048:
            int r12 = r8 + 1
            char r8 = r1.charAt(r8)
            if (r8 < r6) goto L_0x0058
            r8 = r8 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r10
            r9 = r9 | r8
            int r10 = r10 + 13
            r8 = r12
            goto L_0x0048
        L_0x0058:
            int r8 = r8 << r10
            r9 = r9 | r8
            r8 = r12
        L_0x005b:
            if (r9 != 0) goto L_0x0068
            int[] r9 = EMPTY_INT_ARRAY
            r7 = r3
            r10 = r7
            r13 = r10
            r14 = r13
            r15 = r14
            r12 = r9
            r9 = r15
            goto L_0x0184
        L_0x0068:
            int r9 = r8 + 1
            char r8 = r1.charAt(r8)
            if (r8 < r6) goto L_0x0087
            r8 = r8 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0074:
            int r12 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x0084
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r8 = r8 | r9
            int r10 = r10 + 13
            r9 = r12
            goto L_0x0074
        L_0x0084:
            int r9 = r9 << r10
            r8 = r8 | r9
            r9 = r12
        L_0x0087:
            int r10 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00a6
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x0093:
            int r13 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00a3
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r12
            r9 = r9 | r10
            int r12 = r12 + 13
            r10 = r13
            goto L_0x0093
        L_0x00a3:
            int r10 = r10 << r12
            r9 = r9 | r10
            r10 = r13
        L_0x00a6:
            int r12 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00c5
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x00b2:
            int r14 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x00c2
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r10 = r10 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x00b2
        L_0x00c2:
            int r12 = r12 << r13
            r10 = r10 | r12
            r12 = r14
        L_0x00c5:
            int r13 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x00e4
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x00d1:
            int r15 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x00e1
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x00d1
        L_0x00e1:
            int r13 = r13 << r14
            r12 = r12 | r13
            r13 = r15
        L_0x00e4:
            int r14 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0105
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x00f0:
            int r16 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x0101
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x00f0
        L_0x0101:
            int r14 = r14 << r15
            r13 = r13 | r14
            r14 = r16
        L_0x0105:
            int r15 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x0128
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x0111:
            int r17 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0123
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r15 = r15 << r16
            r14 = r14 | r15
            int r16 = r16 + 13
            r15 = r17
            goto L_0x0111
        L_0x0123:
            int r15 = r15 << r16
            r14 = r14 | r15
            r15 = r17
        L_0x0128:
            int r16 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x014e
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r3 = r16
            r16 = 13
        L_0x0136:
            int r17 = r3 + 1
            char r3 = r1.charAt(r3)
            if (r3 < r6) goto L_0x0148
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r16
            r15 = r15 | r3
            int r16 = r16 + 13
            r3 = r17
            goto L_0x0136
        L_0x0148:
            int r3 = r3 << r16
            r15 = r15 | r3
            r3 = r17
            goto L_0x0150
        L_0x014e:
            r3 = r16
        L_0x0150:
            int r16 = r3 + 1
            char r3 = r1.charAt(r3)
            if (r3 < r6) goto L_0x0175
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            r7 = r16
            r16 = 13
        L_0x015e:
            int r18 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0170
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r16
            r3 = r3 | r7
            int r16 = r16 + 13
            r7 = r18
            goto L_0x015e
        L_0x0170:
            int r7 = r7 << r16
            r3 = r3 | r7
            r16 = r18
        L_0x0175:
            int r7 = r3 + r14
            int r7 = r7 + r15
            int[] r7 = new int[r7]
            int r15 = r8 * 2
            int r15 = r15 + r9
            r9 = r12
            r12 = r7
            r7 = r14
            r14 = r3
            r3 = r8
            r8 = r16
        L_0x0184:
            sun.misc.Unsafe r4 = UNSAFE
            java.lang.Object[] r6 = r0.objects
            r19 = r8
            com.google.protobuf.MessageLite r8 = r0.defaultInstance
            java.lang.Class r8 = r8.getClass()
            r20 = r15
            int r15 = r13 * 3
            int[] r15 = new int[r15]
            int r13 = r13 * 2
            java.lang.Object[] r13 = new java.lang.Object[r13]
            int r21 = r14 + r7
            r23 = r14
            r7 = r19
            r24 = r21
            r19 = 0
            r22 = 0
        L_0x01a6:
            if (r7 >= r2) goto L_0x040d
            int r25 = r7 + 1
            char r7 = r1.charAt(r7)
            r26 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r7 < r2) goto L_0x01da
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r2 = r25
            r25 = 13
        L_0x01bb:
            int r27 = r2 + 1
            char r2 = r1.charAt(r2)
            r28 = r14
            r14 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r14) goto L_0x01d4
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r2 = r2 << r25
            r7 = r7 | r2
            int r25 = r25 + 13
            r2 = r27
            r14 = r28
            goto L_0x01bb
        L_0x01d4:
            int r2 = r2 << r25
            r7 = r7 | r2
            r2 = r27
            goto L_0x01de
        L_0x01da:
            r28 = r14
            r2 = r25
        L_0x01de:
            int r14 = r2 + 1
            char r2 = r1.charAt(r2)
            r25 = r14
            r14 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r14) goto L_0x0210
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            r14 = r25
            r25 = 13
        L_0x01f1:
            int r27 = r14 + 1
            char r14 = r1.charAt(r14)
            r29 = r11
            r11 = 55296(0xd800, float:7.7486E-41)
            if (r14 < r11) goto L_0x020a
            r11 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r11 = r11 << r25
            r2 = r2 | r11
            int r25 = r25 + 13
            r14 = r27
            r11 = r29
            goto L_0x01f1
        L_0x020a:
            int r11 = r14 << r25
            r2 = r2 | r11
            r14 = r27
            goto L_0x0214
        L_0x0210:
            r29 = r11
            r14 = r25
        L_0x0214:
            r11 = r2 & 255(0xff, float:3.57E-43)
            r25 = r9
            r9 = r2 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L_0x0222
            int r9 = r19 + 1
            r12[r19] = r22
            r19 = r9
        L_0x0222:
            r9 = 51
            if (r11 < r9) goto L_0x02d0
            int r9 = r14 + 1
            char r14 = r1.charAt(r14)
            r27 = r9
            r9 = 55296(0xd800, float:7.7486E-41)
            if (r14 < r9) goto L_0x025a
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            r32 = 13
            r34 = r27
            r27 = r14
            r14 = r34
        L_0x023d:
            int r33 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r9) goto L_0x0253
            r9 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r32
            r27 = r27 | r9
            int r32 = r32 + 13
            r14 = r33
            r9 = 55296(0xd800, float:7.7486E-41)
            goto L_0x023d
        L_0x0253:
            int r9 = r14 << r32
            r14 = r27 | r9
            r9 = r33
            goto L_0x025c
        L_0x025a:
            r9 = r27
        L_0x025c:
            r27 = r9
            int r9 = r11 + -51
            r32 = r10
            r10 = 9
            if (r9 == r10) goto L_0x0280
            r10 = 17
            if (r9 != r10) goto L_0x026b
            goto L_0x0280
        L_0x026b:
            r10 = 12
            if (r9 != r10) goto L_0x028e
            r9 = r5 & 1
            r10 = 1
            if (r9 != r10) goto L_0x028e
            int r9 = r22 / 3
            int r9 = r9 * 2
            int r9 = r9 + r10
            int r10 = r20 + 1
            r20 = r6[r20]
            r13[r9] = r20
            goto L_0x028c
        L_0x0280:
            int r9 = r22 / 3
            int r9 = r9 * 2
            r10 = 1
            int r9 = r9 + r10
            int r10 = r20 + 1
            r20 = r6[r20]
            r13[r9] = r20
        L_0x028c:
            r20 = r10
        L_0x028e:
            int r14 = r14 * 2
            r9 = r6[r14]
            boolean r10 = r9 instanceof java.lang.reflect.Field
            if (r10 == 0) goto L_0x0299
            java.lang.reflect.Field r9 = (java.lang.reflect.Field) r9
            goto L_0x02a1
        L_0x0299:
            java.lang.String r9 = (java.lang.String) r9
            java.lang.reflect.Field r9 = reflectField(r8, r9)
            r6[r14] = r9
        L_0x02a1:
            long r9 = r4.objectFieldOffset(r9)
            int r9 = (int) r9
            int r14 = r14 + 1
            r10 = r6[r14]
            r30 = r9
            boolean r9 = r10 instanceof java.lang.reflect.Field
            if (r9 == 0) goto L_0x02b3
            java.lang.reflect.Field r10 = (java.lang.reflect.Field) r10
            goto L_0x02bb
        L_0x02b3:
            java.lang.String r10 = (java.lang.String) r10
            java.lang.reflect.Field r10 = reflectField(r8, r10)
            r6[r14] = r10
        L_0x02bb:
            long r9 = r4.objectFieldOffset(r10)
            int r9 = (int) r9
            r10 = r9
            r16 = r20
            r9 = r30
            r14 = 0
            r20 = r0
            r30 = r2
            r2 = r15
            r0 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03ce
        L_0x02d0:
            r32 = r10
            int r9 = r20 + 1
            r10 = r6[r20]
            java.lang.String r10 = (java.lang.String) r10
            java.lang.reflect.Field r10 = reflectField(r8, r10)
            r20 = r0
            r0 = 9
            if (r11 == r0) goto L_0x034d
            r0 = 17
            if (r11 != r0) goto L_0x02e8
            goto L_0x034d
        L_0x02e8:
            r0 = 27
            if (r11 == r0) goto L_0x033e
            r0 = 49
            if (r11 != r0) goto L_0x02f1
            goto L_0x033e
        L_0x02f1:
            r0 = 12
            if (r11 == r0) goto L_0x032b
            r0 = 30
            if (r11 == r0) goto L_0x032b
            r0 = 44
            if (r11 != r0) goto L_0x02fe
            goto L_0x032b
        L_0x02fe:
            r0 = 50
            if (r11 != r0) goto L_0x0327
            int r0 = r23 + 1
            r12[r23] = r22
            int r23 = r22 / 3
            int r23 = r23 * 2
            int r30 = r9 + 1
            r9 = r6[r9]
            r13[r23] = r9
            r9 = r2 & 2048(0x800, float:2.87E-42)
            if (r9 == 0) goto L_0x031f
            int r23 = r23 + 1
            int r9 = r30 + 1
            r30 = r6[r30]
            r13[r23] = r30
            r23 = r0
            goto L_0x0327
        L_0x031f:
            r23 = r0
            r16 = r30
            r30 = r2
            r2 = 1
            goto L_0x035d
        L_0x0327:
            r30 = r2
            r2 = 1
            goto L_0x035b
        L_0x032b:
            r0 = r5 & 1
            r30 = r2
            r2 = 1
            if (r0 != r2) goto L_0x035b
            int r0 = r22 / 3
            int r0 = r0 * 2
            int r0 = r0 + r2
            int r16 = r9 + 1
            r9 = r6[r9]
            r13[r0] = r9
            goto L_0x035d
        L_0x033e:
            r30 = r2
            r2 = 1
            int r0 = r22 / 3
            int r0 = r0 * 2
            int r0 = r0 + r2
            int r16 = r9 + 1
            r9 = r6[r9]
            r13[r0] = r9
            goto L_0x035d
        L_0x034d:
            r30 = r2
            r2 = 1
            int r0 = r22 / 3
            int r0 = r0 * 2
            int r0 = r0 + r2
            java.lang.Class r16 = r10.getType()
            r13[r0] = r16
        L_0x035b:
            r16 = r9
        L_0x035d:
            long r9 = r4.objectFieldOffset(r10)
            int r9 = (int) r9
            r0 = r5 & 1
            if (r0 != r2) goto L_0x03b6
            r0 = 17
            if (r11 > r0) goto L_0x03b6
            int r0 = r14 + 1
            char r10 = r1.charAt(r14)
            r14 = 55296(0xd800, float:7.7486E-41)
            if (r10 < r14) goto L_0x038f
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r18 = 13
        L_0x0379:
            int r31 = r0 + 1
            char r0 = r1.charAt(r0)
            if (r0 < r14) goto L_0x038b
            r0 = r0 & 8191(0x1fff, float:1.1478E-41)
            int r0 = r0 << r18
            r10 = r10 | r0
            int r18 = r18 + 13
            r0 = r31
            goto L_0x0379
        L_0x038b:
            int r0 = r0 << r18
            r10 = r10 | r0
            goto L_0x0391
        L_0x038f:
            r31 = r0
        L_0x0391:
            int r0 = r3 * 2
            int r18 = r10 / 32
            int r18 = r18 + r0
            r0 = r6[r18]
            boolean r2 = r0 instanceof java.lang.reflect.Field
            if (r2 == 0) goto L_0x03a0
            java.lang.reflect.Field r0 = (java.lang.reflect.Field) r0
            goto L_0x03a8
        L_0x03a0:
            java.lang.String r0 = (java.lang.String) r0
            java.lang.reflect.Field r0 = reflectField(r8, r0)
            r6[r18] = r0
        L_0x03a8:
            r2 = r15
            long r14 = r4.objectFieldOffset(r0)
            int r0 = (int) r14
            int r10 = r10 % 32
            r14 = r10
            r10 = r0
            r0 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03be
        L_0x03b6:
            r2 = r15
            r0 = 55296(0xd800, float:7.7486E-41)
            r31 = r14
            r10 = 0
            r14 = 0
        L_0x03be:
            r15 = 18
            if (r11 < r15) goto L_0x03cc
            r15 = 49
            if (r11 > r15) goto L_0x03cc
            int r15 = r24 + 1
            r12[r24] = r9
            r24 = r15
        L_0x03cc:
            r27 = r31
        L_0x03ce:
            int r15 = r22 + 1
            r2[r22] = r7
            int r7 = r15 + 1
            r22 = r1
            r0 = r30
            r1 = r0 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L_0x03df
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x03e0
        L_0x03df:
            r1 = 0
        L_0x03e0:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L_0x03e7
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x03e8
        L_0x03e7:
            r0 = 0
        L_0x03e8:
            r0 = r0 | r1
            int r1 = r11 << 20
            r0 = r0 | r1
            r0 = r0 | r9
            r2[r15] = r0
            int r0 = r7 + 1
            int r1 = r14 << 20
            r1 = r1 | r10
            r2[r7] = r1
            r15 = r2
            r1 = r22
            r9 = r25
            r2 = r26
            r7 = r27
            r14 = r28
            r11 = r29
            r10 = r32
            r22 = r0
            r0 = r20
            r20 = r16
            goto L_0x01a6
        L_0x040d:
            r20 = r0
            r25 = r9
            r32 = r10
            r29 = r11
            r28 = r14
            r2 = r15
            com.google.protobuf.MessageSchema r0 = new com.google.protobuf.MessageSchema
            r1 = r20
            com.google.protobuf.MessageLite r10 = r1.defaultInstance
            r5 = r0
            r6 = r2
            r7 = r13
            r8 = r32
            r13 = r28
            r14 = r21
            r15 = r36
            r16 = r37
            r17 = r38
            r18 = r39
            r19 = r40
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return r0
        L_0x0435:
            com.google.protobuf.StructuralMessageInfo r0 = (com.google.protobuf.StructuralMessageInfo) r0
            r0 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.newSchema(com.google.protobuf.MessageInfo, com.google.protobuf.NewInstanceSchema, com.google.protobuf.ListFieldSchema, com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, com.google.protobuf.MapFieldSchema):com.google.protobuf.MessageSchema");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003f, code lost:
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r10, r6), com.google.protobuf.UnsafeUtil.getObject(r11, r6)) != false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0071, code lost:
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r10, r6), com.google.protobuf.UnsafeUtil.getObject(r11, r6)) != false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0085, code lost:
        if (com.google.protobuf.UnsafeUtil.getLong(r10, r6) == com.google.protobuf.UnsafeUtil.getLong(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0097, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ab, code lost:
        if (com.google.protobuf.UnsafeUtil.getLong(r10, r6) == com.google.protobuf.UnsafeUtil.getLong(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bd, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cf, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e1, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f7, code lost:
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r10, r6), com.google.protobuf.UnsafeUtil.getObject(r11, r6)) != false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010d, code lost:
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r10, r6), com.google.protobuf.UnsafeUtil.getObject(r11, r6)) != false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0123, code lost:
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r10, r6), com.google.protobuf.UnsafeUtil.getObject(r11, r6)) != false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0135, code lost:
        if (com.google.protobuf.UnsafeUtil.getBoolean(r10, r6) == com.google.protobuf.UnsafeUtil.getBoolean(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0147, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x015b, code lost:
        if (com.google.protobuf.UnsafeUtil.getLong(r10, r6) == com.google.protobuf.UnsafeUtil.getLong(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x016d, code lost:
        if (com.google.protobuf.UnsafeUtil.getInt(r10, r6) == com.google.protobuf.UnsafeUtil.getInt(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0180, code lost:
        if (com.google.protobuf.UnsafeUtil.getLong(r10, r6) == com.google.protobuf.UnsafeUtil.getLong(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0193, code lost:
        if (com.google.protobuf.UnsafeUtil.getLong(r10, r6) == com.google.protobuf.UnsafeUtil.getLong(r11, r6)) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01ac, code lost:
        if (java.lang.Float.floatToIntBits(com.google.protobuf.UnsafeUtil.getFloat(r10, r6)) == java.lang.Float.floatToIntBits(com.google.protobuf.UnsafeUtil.getFloat(r11, r6))) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c7, code lost:
        if (java.lang.Double.doubleToLongBits(com.google.protobuf.UnsafeUtil.getDouble(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.protobuf.UnsafeUtil.getDouble(r11, r6))) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01ca, code lost:
        r3 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(T r10, T r11) {
        /*
            r9 = this;
            int[] r0 = r9.buffer
            int r0 = r0.length
            r1 = 0
            r2 = r1
        L_0x0005:
            r3 = 1
            if (r2 >= r0) goto L_0x01d2
            int r4 = r9.typeAndOffsetAt(r2)
            r5 = 1048575(0xfffff, float:1.469367E-39)
            r6 = r4 & r5
            long r6 = (long) r6
            r8 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r8
            int r4 = r4 >>> 20
            switch(r4) {
                case 0: goto L_0x01af;
                case 1: goto L_0x0196;
                case 2: goto L_0x0183;
                case 3: goto L_0x0170;
                case 4: goto L_0x015f;
                case 5: goto L_0x014b;
                case 6: goto L_0x0139;
                case 7: goto L_0x0127;
                case 8: goto L_0x0111;
                case 9: goto L_0x00fb;
                case 10: goto L_0x00e5;
                case 11: goto L_0x00d3;
                case 12: goto L_0x00c1;
                case 13: goto L_0x00af;
                case 14: goto L_0x009b;
                case 15: goto L_0x0089;
                case 16: goto L_0x0075;
                case 17: goto L_0x005f;
                case 18: goto L_0x0051;
                case 19: goto L_0x0051;
                case 20: goto L_0x0051;
                case 21: goto L_0x0051;
                case 22: goto L_0x0051;
                case 23: goto L_0x0051;
                case 24: goto L_0x0051;
                case 25: goto L_0x0051;
                case 26: goto L_0x0051;
                case 27: goto L_0x0051;
                case 28: goto L_0x0051;
                case 29: goto L_0x0051;
                case 30: goto L_0x0051;
                case 31: goto L_0x0051;
                case 32: goto L_0x0051;
                case 33: goto L_0x0051;
                case 34: goto L_0x0051;
                case 35: goto L_0x0051;
                case 36: goto L_0x0051;
                case 37: goto L_0x0051;
                case 38: goto L_0x0051;
                case 39: goto L_0x0051;
                case 40: goto L_0x0051;
                case 41: goto L_0x0051;
                case 42: goto L_0x0051;
                case 43: goto L_0x0051;
                case 44: goto L_0x0051;
                case 45: goto L_0x0051;
                case 46: goto L_0x0051;
                case 47: goto L_0x0051;
                case 48: goto L_0x0051;
                case 49: goto L_0x0051;
                case 50: goto L_0x0043;
                case 51: goto L_0x001c;
                case 52: goto L_0x001c;
                case 53: goto L_0x001c;
                case 54: goto L_0x001c;
                case 55: goto L_0x001c;
                case 56: goto L_0x001c;
                case 57: goto L_0x001c;
                case 58: goto L_0x001c;
                case 59: goto L_0x001c;
                case 60: goto L_0x001c;
                case 61: goto L_0x001c;
                case 62: goto L_0x001c;
                case 63: goto L_0x001c;
                case 64: goto L_0x001c;
                case 65: goto L_0x001c;
                case 66: goto L_0x001c;
                case 67: goto L_0x001c;
                case 68: goto L_0x001c;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x01cb
        L_0x001c:
            int[] r4 = r9.buffer
            int r8 = r2 + 2
            r4 = r4[r8]
            r4 = r4 & r5
            long r4 = (long) r4
            int r8 = com.google.protobuf.UnsafeUtil.getInt(r10, r4)
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r11, r4)
            if (r8 != r4) goto L_0x0030
            r4 = r3
            goto L_0x0031
        L_0x0030:
            r4 = r1
        L_0x0031:
            if (r4 == 0) goto L_0x01ca
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r5 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r4 = com.google.protobuf.SchemaUtil.safeEquals(r4, r5)
            if (r4 == 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0043:
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r3 = com.google.protobuf.SchemaUtil.safeEquals(r3, r4)
            goto L_0x01cb
        L_0x0051:
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r3 = com.google.protobuf.SchemaUtil.safeEquals(r3, r4)
            goto L_0x01cb
        L_0x005f:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r5 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r4 = com.google.protobuf.SchemaUtil.safeEquals(r4, r5)
            if (r4 == 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0075:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            long r4 = com.google.protobuf.UnsafeUtil.getLong(r10, r6)
            long r6 = com.google.protobuf.UnsafeUtil.getLong(r11, r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0089:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x009b:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            long r4 = com.google.protobuf.UnsafeUtil.getLong(r10, r6)
            long r6 = com.google.protobuf.UnsafeUtil.getLong(r11, r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x00af:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x00c1:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x00d3:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x00e5:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r5 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r4 = com.google.protobuf.SchemaUtil.safeEquals(r4, r5)
            if (r4 == 0) goto L_0x01ca
            goto L_0x01cb
        L_0x00fb:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r5 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r4 = com.google.protobuf.SchemaUtil.safeEquals(r4, r5)
            if (r4 == 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0111:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            java.lang.Object r4 = com.google.protobuf.UnsafeUtil.getObject(r10, r6)
            java.lang.Object r5 = com.google.protobuf.UnsafeUtil.getObject(r11, r6)
            boolean r4 = com.google.protobuf.SchemaUtil.safeEquals(r4, r5)
            if (r4 == 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0127:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            boolean r4 = com.google.protobuf.UnsafeUtil.getBoolean(r10, r6)
            boolean r5 = com.google.protobuf.UnsafeUtil.getBoolean(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x0139:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x014b:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            long r4 = com.google.protobuf.UnsafeUtil.getLong(r10, r6)
            long r6 = com.google.protobuf.UnsafeUtil.getLong(r11, r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x015f:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            int r4 = com.google.protobuf.UnsafeUtil.getInt(r10, r6)
            int r5 = com.google.protobuf.UnsafeUtil.getInt(r11, r6)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x0170:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            long r4 = com.google.protobuf.UnsafeUtil.getLong(r10, r6)
            long r6 = com.google.protobuf.UnsafeUtil.getLong(r11, r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0183:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            long r4 = com.google.protobuf.UnsafeUtil.getLong(r10, r6)
            long r6 = com.google.protobuf.UnsafeUtil.getLong(r11, r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x0196:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            float r4 = com.google.protobuf.UnsafeUtil.getFloat(r10, r6)
            int r4 = java.lang.Float.floatToIntBits(r4)
            float r5 = com.google.protobuf.UnsafeUtil.getFloat(r11, r6)
            int r5 = java.lang.Float.floatToIntBits(r5)
            if (r4 != r5) goto L_0x01ca
            goto L_0x01cb
        L_0x01af:
            boolean r4 = r9.arePresentForEquals(r10, r11, r2)
            if (r4 == 0) goto L_0x01ca
            double r4 = com.google.protobuf.UnsafeUtil.getDouble(r10, r6)
            long r4 = java.lang.Double.doubleToLongBits(r4)
            double r6 = com.google.protobuf.UnsafeUtil.getDouble(r11, r6)
            long r6 = java.lang.Double.doubleToLongBits(r6)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x01ca
            goto L_0x01cb
        L_0x01ca:
            r3 = r1
        L_0x01cb:
            if (r3 != 0) goto L_0x01ce
            return r1
        L_0x01ce:
            int r2 = r2 + 3
            goto L_0x0005
        L_0x01d2:
            com.google.protobuf.UnknownFieldSchema<?, ?> r0 = r9.unknownFieldSchema
            com.google.protobuf.UnknownFieldSetLite r0 = r0.getFromMessage(r10)
            com.google.protobuf.UnknownFieldSchema<?, ?> r2 = r9.unknownFieldSchema
            com.google.protobuf.UnknownFieldSetLite r2 = r2.getFromMessage(r11)
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x01e5
            return r1
        L_0x01e5:
            boolean r0 = r9.hasExtensions
            if (r0 == 0) goto L_0x01fa
            com.google.protobuf.ExtensionSchema<?> r0 = r9.extensionSchema
            com.google.protobuf.FieldSet r10 = r0.getExtensions(r10)
            com.google.protobuf.ExtensionSchema<?> r9 = r9.extensionSchema
            com.google.protobuf.FieldSet r9 = r9.getExtensions(r11)
            boolean r9 = r10.equals(r9)
            return r9
        L_0x01fa:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.equals(java.lang.Object, java.lang.Object):boolean");
    }

    public final Object getMapFieldDefaultEntry(int i) {
        return this.objects[(i / 3) * 2];
    }

    public final Schema getMessageFieldSchema(int i) {
        int i2 = (i / 3) * 2;
        Object[] objArr = this.objects;
        Schema schema = (Schema) objArr[i2];
        if (schema != null) {
            return schema;
        }
        Schema schemaFor = Protobuf.INSTANCE.schemaFor((Class) objArr[i2 + 1]);
        this.objects[i2] = schemaFor;
        return schemaFor;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00de, code lost:
        if (r3 != false) goto L_0x01f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01d9, code lost:
        r2 = (r2 * 53) + r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01f3, code lost:
        if (r3 != false) goto L_0x01f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01f6, code lost:
        r8 = 1237;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01f7, code lost:
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0241, code lost:
        r2 = r3 + r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0243, code lost:
        r1 = r1 + 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int hashCode(T r11) {
        /*
            r10 = this;
            int[] r0 = r10.buffer
            int r0 = r0.length
            r1 = 0
            r2 = r1
        L_0x0005:
            if (r1 >= r0) goto L_0x0247
            int r3 = r10.typeAndOffsetAt(r1)
            int[] r4 = r10.buffer
            r4 = r4[r1]
            r5 = 1048575(0xfffff, float:1.469367E-39)
            r5 = r5 & r3
            long r5 = (long) r5
            r7 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = r3 & r7
            int r3 = r3 >>> 20
            r7 = 37
            r8 = 1231(0x4cf, float:1.725E-42)
            r9 = 1237(0x4d5, float:1.733E-42)
            switch(r3) {
                case 0: goto L_0x0233;
                case 1: goto L_0x0228;
                case 2: goto L_0x021d;
                case 3: goto L_0x0212;
                case 4: goto L_0x020b;
                case 5: goto L_0x0200;
                case 6: goto L_0x01f9;
                case 7: goto L_0x01eb;
                case 8: goto L_0x01de;
                case 9: goto L_0x01cf;
                case 10: goto L_0x01c3;
                case 11: goto L_0x01bb;
                case 12: goto L_0x01b3;
                case 13: goto L_0x01ab;
                case 14: goto L_0x019f;
                case 15: goto L_0x0197;
                case 16: goto L_0x018b;
                case 17: goto L_0x0180;
                case 18: goto L_0x0174;
                case 19: goto L_0x0174;
                case 20: goto L_0x0174;
                case 21: goto L_0x0174;
                case 22: goto L_0x0174;
                case 23: goto L_0x0174;
                case 24: goto L_0x0174;
                case 25: goto L_0x0174;
                case 26: goto L_0x0174;
                case 27: goto L_0x0174;
                case 28: goto L_0x0174;
                case 29: goto L_0x0174;
                case 30: goto L_0x0174;
                case 31: goto L_0x0174;
                case 32: goto L_0x0174;
                case 33: goto L_0x0174;
                case 34: goto L_0x0174;
                case 35: goto L_0x0174;
                case 36: goto L_0x0174;
                case 37: goto L_0x0174;
                case 38: goto L_0x0174;
                case 39: goto L_0x0174;
                case 40: goto L_0x0174;
                case 41: goto L_0x0174;
                case 42: goto L_0x0174;
                case 43: goto L_0x0174;
                case 44: goto L_0x0174;
                case 45: goto L_0x0174;
                case 46: goto L_0x0174;
                case 47: goto L_0x0174;
                case 48: goto L_0x0174;
                case 49: goto L_0x0174;
                case 50: goto L_0x0168;
                case 51: goto L_0x014c;
                case 52: goto L_0x0134;
                case 53: goto L_0x0122;
                case 54: goto L_0x0110;
                case 55: goto L_0x0102;
                case 56: goto L_0x00f0;
                case 57: goto L_0x00e2;
                case 58: goto L_0x00ca;
                case 59: goto L_0x00b6;
                case 60: goto L_0x00a4;
                case 61: goto L_0x0092;
                case 62: goto L_0x0084;
                case 63: goto L_0x0076;
                case 64: goto L_0x0068;
                case 65: goto L_0x0056;
                case 66: goto L_0x0048;
                case 67: goto L_0x0036;
                case 68: goto L_0x0024;
                default: goto L_0x0022;
            }
        L_0x0022:
            goto L_0x0243
        L_0x0024:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r2 = r2 * 53
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x0036:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            long r3 = oneofLongAt(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0048:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x0056:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            long r3 = oneofLongAt(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0068:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x0076:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x0084:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x0092:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x00a4:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r2 = r2 * 53
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x00b6:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            java.lang.String r3 = (java.lang.String) r3
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x00ca:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            java.nio.charset.Charset r4 = com.google.protobuf.Internal.UTF_8
            if (r3 == 0) goto L_0x01f6
            goto L_0x01f7
        L_0x00e2:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x00f0:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            long r3 = oneofLongAt(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0102:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            int r3 = oneofIntAt(r11, r5)
            goto L_0x0241
        L_0x0110:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            long r3 = oneofLongAt(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0122:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            long r3 = oneofLongAt(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0134:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            int r3 = java.lang.Float.floatToIntBits(r3)
            goto L_0x0241
        L_0x014c:
            boolean r3 = r10.isOneofPresent(r11, r4, r1)
            if (r3 == 0) goto L_0x0243
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            java.lang.Double r3 = (java.lang.Double) r3
            double r3 = r3.doubleValue()
            long r3 = java.lang.Double.doubleToLongBits(r3)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0168:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x0174:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x0180:
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            if (r3 == 0) goto L_0x01d9
            int r7 = r3.hashCode()
            goto L_0x01d9
        L_0x018b:
            int r2 = r2 * 53
            long r3 = com.google.protobuf.UnsafeUtil.getLong(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0197:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x019f:
            int r2 = r2 * 53
            long r3 = com.google.protobuf.UnsafeUtil.getLong(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x01ab:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x01b3:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x01bb:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x01c3:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x01cf:
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            if (r3 == 0) goto L_0x01d9
            int r7 = r3.hashCode()
        L_0x01d9:
            int r2 = r2 * 53
            int r2 = r2 + r7
            goto L_0x0243
        L_0x01de:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.protobuf.UnsafeUtil.getObject(r11, r5)
            java.lang.String r3 = (java.lang.String) r3
            int r3 = r3.hashCode()
            goto L_0x0241
        L_0x01eb:
            int r2 = r2 * 53
            boolean r3 = com.google.protobuf.UnsafeUtil.getBoolean(r11, r5)
            java.nio.charset.Charset r4 = com.google.protobuf.Internal.UTF_8
            if (r3 == 0) goto L_0x01f6
            goto L_0x01f7
        L_0x01f6:
            r8 = r9
        L_0x01f7:
            r3 = r8
            goto L_0x0241
        L_0x01f9:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x0200:
            int r2 = r2 * 53
            long r3 = com.google.protobuf.UnsafeUtil.getLong(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x020b:
            int r2 = r2 * 53
            int r3 = com.google.protobuf.UnsafeUtil.getInt(r11, r5)
            goto L_0x0241
        L_0x0212:
            int r2 = r2 * 53
            long r3 = com.google.protobuf.UnsafeUtil.getLong(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x021d:
            int r2 = r2 * 53
            long r3 = com.google.protobuf.UnsafeUtil.getLong(r11, r5)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
            goto L_0x0241
        L_0x0228:
            int r2 = r2 * 53
            float r3 = com.google.protobuf.UnsafeUtil.getFloat(r11, r5)
            int r3 = java.lang.Float.floatToIntBits(r3)
            goto L_0x0241
        L_0x0233:
            int r2 = r2 * 53
            double r3 = com.google.protobuf.UnsafeUtil.getDouble(r11, r5)
            long r3 = java.lang.Double.doubleToLongBits(r3)
            int r3 = com.google.protobuf.Internal.hashLong(r3)
        L_0x0241:
            int r3 = r3 + r2
            r2 = r3
        L_0x0243:
            int r1 = r1 + 3
            goto L_0x0005
        L_0x0247:
            int r2 = r2 * 53
            com.google.protobuf.UnknownFieldSchema<?, ?> r0 = r10.unknownFieldSchema
            com.google.protobuf.UnknownFieldSetLite r0 = r0.getFromMessage(r11)
            int r0 = r0.hashCode()
            int r0 = r0 + r2
            boolean r1 = r10.hasExtensions
            if (r1 == 0) goto L_0x0265
            int r0 = r0 * 53
            com.google.protobuf.ExtensionSchema<?> r10 = r10.extensionSchema
            com.google.protobuf.FieldSet r10 = r10.getExtensions(r11)
            int r10 = r10.hashCode()
            int r0 = r0 + r10
        L_0x0265:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.hashCode(java.lang.Object):int");
    }

    public final boolean isFieldPresent(T t, int i) {
        boolean equals;
        if (this.proto3) {
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = (long) (typeAndOffsetAt & 1048575);
            switch ((typeAndOffsetAt & 267386880) >>> 20) {
                case 0:
                    if (UnsafeUtil.getDouble(t, j) != 0.0d) {
                        return true;
                    }
                    return false;
                case 1:
                    if (UnsafeUtil.getFloat(t, j) != 0.0f) {
                        return true;
                    }
                    return false;
                case 2:
                    if (UnsafeUtil.getLong(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 3:
                    if (UnsafeUtil.getLong(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 4:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 5:
                    if (UnsafeUtil.getLong(t, j) != 0) {
                        return true;
                    }
                    return false;
                case FalsingManager.VERSION:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 7:
                    return UnsafeUtil.getBoolean(t, j);
                case 8:
                    Object object = UnsafeUtil.getObject(t, j);
                    if (object instanceof String) {
                        equals = ((String) object).isEmpty();
                        break;
                    } else if (object instanceof ByteString) {
                        equals = ByteString.EMPTY.equals(object);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 9:
                    if (UnsafeUtil.getObject(t, j) != null) {
                        return true;
                    }
                    return false;
                case 10:
                    equals = ByteString.EMPTY.equals(UnsafeUtil.getObject(t, j));
                    break;
                case QSTileImpl.C1034H.STALE:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case C0961QS.VERSION:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 14:
                    if (UnsafeUtil.getLong(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 15:
                    if (UnsafeUtil.getInt(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 16:
                    if (UnsafeUtil.getLong(t, j) != 0) {
                        return true;
                    }
                    return false;
                case 17:
                    if (UnsafeUtil.getObject(t, j) != null) {
                        return true;
                    }
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
            return !equals;
        }
        int i2 = this.buffer[i + 2];
        if ((UnsafeUtil.getInt(t, (long) (i2 & 1048575)) & (1 << (i2 >>> 20))) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isOneofPresent(T t, int i, int i2) {
        if (UnsafeUtil.getInt(t, (long) (this.buffer[i2 + 2] & 1048575)) == i) {
            return true;
        }
        return false;
    }

    public final void makeImmutable(T t) {
        int i;
        int i2 = this.checkInitializedCount;
        while (true) {
            i = this.repeatedFieldOffsetStart;
            if (i2 >= i) {
                break;
            }
            long typeAndOffsetAt = (long) (typeAndOffsetAt(this.intArray[i2]) & 1048575);
            Object object = UnsafeUtil.getObject(t, typeAndOffsetAt);
            if (object != null) {
                UnsafeUtil.putObject(t, typeAndOffsetAt, this.mapFieldSchema.toImmutable(object));
            }
            i2++;
        }
        int length = this.intArray.length;
        while (i < length) {
            this.listFieldSchema.makeImmutableListAt(t, (long) this.intArray[i]);
            i++;
        }
        this.unknownFieldSchema.makeImmutable(t);
        if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(t);
        }
    }

    public final void setFieldPresent(T t, int i) {
        if (!this.proto3) {
            int i2 = this.buffer[i + 2];
            long j = (long) (i2 & 1048575);
            UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t, j) | (1 << (i2 >>> 20)));
        }
    }

    public final void setOneofPresent(T t, int i, int i2) {
        UnsafeUtil.putInt(t, (long) (this.buffer[i2 + 2] & 1048575), i);
    }

    public final int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    public MessageSchema(int[] iArr, Object[] objArr, int i, int i2, MessageLite messageLite, boolean z, int[] iArr2, int i3, int i4, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema unknownFieldSchema2, ExtensionSchema extensionSchema2, MapFieldSchema mapFieldSchema2) {
        boolean z2;
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.proto3 = z;
        if (extensionSchema2 == null || !extensionSchema2.hasExtensions(messageLite)) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.hasExtensions = z2;
        this.useCachedSizeField = false;
        this.intArray = iArr2;
        this.checkInitializedCount = i3;
        this.repeatedFieldOffsetStart = i4;
        this.newInstanceSchema = newInstanceSchema2;
        this.listFieldSchema = listFieldSchema2;
        this.unknownFieldSchema = unknownFieldSchema2;
        this.extensionSchema = extensionSchema2;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema2;
    }

    public static <T> int oneofIntAt(T t, long j) {
        return ((Integer) UnsafeUtil.getObject(t, j)).intValue();
    }

    public static <T> long oneofLongAt(T t, long j) {
        return ((Long) UnsafeUtil.getObject(t, j)).longValue();
    }

    public static Field reflectField(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Field ", str, " for ");
            m.append(cls.getName());
            m.append(" not found. Known fields are ");
            m.append(Arrays.toString(declaredFields));
            throw new RuntimeException(m.toString());
        }
    }

    public final boolean arePresentForEquals(T t, T t2, int i) {
        if (isFieldPresent(t, i) == isFieldPresent(t2, i)) {
            return true;
        }
        return false;
    }

    public final void mergeFrom(T t, T t2) {
        Objects.requireNonNull(t2);
        for (int i = 0; i < this.buffer.length; i += 3) {
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = (long) (1048575 & typeAndOffsetAt);
            int i2 = this.buffer[i];
            switch ((typeAndOffsetAt & 267386880) >>> 20) {
                case 0:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        double d = UnsafeUtil.getDouble(t2, j);
                        UnsafeUtil.JvmMemoryAccessor jvmMemoryAccessor = UnsafeUtil.MEMORY_ACCESSOR;
                        Objects.requireNonNull(jvmMemoryAccessor);
                        jvmMemoryAccessor.unsafe.putDouble(t, j, d);
                        setFieldPresent(t, i);
                        break;
                    }
                case 1:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        float f = UnsafeUtil.getFloat(t2, j);
                        UnsafeUtil.JvmMemoryAccessor jvmMemoryAccessor2 = UnsafeUtil.MEMORY_ACCESSOR;
                        Objects.requireNonNull(jvmMemoryAccessor2);
                        jvmMemoryAccessor2.unsafe.putFloat(t, j, f);
                        setFieldPresent(t, i);
                        break;
                    }
                case 2:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(t, j, UnsafeUtil.getLong(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 3:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(t, j, UnsafeUtil.getLong(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 4:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 5:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(t, j, UnsafeUtil.getLong(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case FalsingManager.VERSION:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 7:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        boolean z = UnsafeUtil.getBoolean(t2, j);
                        UnsafeUtil.JvmMemoryAccessor jvmMemoryAccessor3 = UnsafeUtil.MEMORY_ACCESSOR;
                        Objects.requireNonNull(jvmMemoryAccessor3);
                        jvmMemoryAccessor3.unsafe.putBoolean(t, j, z);
                        setFieldPresent(t, i);
                        break;
                    }
                case 8:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(t, j, UnsafeUtil.getObject(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 9:
                    mergeMessage(t, t2, i);
                    break;
                case 10:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(t, j, UnsafeUtil.getObject(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case QSTileImpl.C1034H.STALE:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case C0961QS.VERSION:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 14:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(t, j, UnsafeUtil.getLong(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 15:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(t, j, UnsafeUtil.getInt(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 16:
                    if (!isFieldPresent(t2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(t, j, UnsafeUtil.getLong(t2, j));
                        setFieldPresent(t, i);
                        break;
                    }
                case 17:
                    mergeMessage(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case SwipeRefreshLayout.CIRCLE_DIAMETER:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.listFieldSchema.mergeListsAt(t, t2, j);
                    break;
                case CustomEvent.MAX_STR_LENGTH:
                    MapFieldSchema mapFieldSchema2 = this.mapFieldSchema;
                    Class<?> cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    UnsafeUtil.putObject(t, j, mapFieldSchema2.mergeFrom(UnsafeUtil.getObject(t, j), UnsafeUtil.getObject(t2, j)));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case SwipeRefreshLayout.CIRCLE_DIAMETER_LARGE:
                case 57:
                case 58:
                case 59:
                    if (!isOneofPresent(t2, i2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(t, j, UnsafeUtil.getObject(t2, j));
                        setOneofPresent(t, i2, i);
                        break;
                    }
                case 60:
                    mergeOneofMessage(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (!isOneofPresent(t2, i2, i)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(t, j, UnsafeUtil.getObject(t2, j));
                        setOneofPresent(t, i2, i);
                        break;
                    }
                case 68:
                    mergeOneofMessage(t, t2, i);
                    break;
            }
        }
        if (!this.proto3) {
            UnknownFieldSchema<?, ?> unknownFieldSchema2 = this.unknownFieldSchema;
            Class<?> cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
            unknownFieldSchema2.setToMessage(t, unknownFieldSchema2.merge(unknownFieldSchema2.getFromMessage(t), unknownFieldSchema2.getFromMessage(t2)));
            if (this.hasExtensions) {
                SchemaUtil.mergeExtensions(this.extensionSchema, t, t2);
            }
        }
    }

    public final void mergeMessage(T t, T t2, int i) {
        long typeAndOffsetAt = (long) (typeAndOffsetAt(i) & 1048575);
        if (isFieldPresent(t2, i)) {
            Object object = UnsafeUtil.getObject(t, typeAndOffsetAt);
            Object object2 = UnsafeUtil.getObject(t2, typeAndOffsetAt);
            if (object != null && object2 != null) {
                UnsafeUtil.putObject(t, typeAndOffsetAt, Internal.mergeMessage(object, object2));
                setFieldPresent(t, i);
            } else if (object2 != null) {
                UnsafeUtil.putObject(t, typeAndOffsetAt, object2);
                setFieldPresent(t, i);
            }
        }
    }

    public final void mergeOneofMessage(T t, T t2, int i) {
        int typeAndOffsetAt = typeAndOffsetAt(i);
        int i2 = this.buffer[i];
        long j = (long) (typeAndOffsetAt & 1048575);
        if (isOneofPresent(t2, i2, i)) {
            Object object = UnsafeUtil.getObject(t, j);
            Object object2 = UnsafeUtil.getObject(t2, j);
            if (object != null && object2 != null) {
                UnsafeUtil.putObject(t, j, Internal.mergeMessage(object, object2));
                setOneofPresent(t, i2, i);
            } else if (object2 != null) {
                UnsafeUtil.putObject(t, j, object2);
                setOneofPresent(t, i2, i);
            }
        }
    }
}
