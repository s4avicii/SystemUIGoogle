package com.google.protobuf;

import androidx.drawerlayout.R$styleable;
import com.google.protobuf.ByteString;
import java.util.List;
import java.util.Map;

public final class MessageLiteToString {
    public static final String camelCaseToSnakeCase(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(charAt));
        }
        return sb.toString();
    }

    public static final void printField(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            for (Object printField : (List) obj) {
                printField(sb, i, str, printField);
            }
        } else if (obj instanceof Map) {
            for (Map.Entry printField2 : ((Map) obj).entrySet()) {
                printField(sb, i, str, printField2);
            }
        } else {
            sb.append(10);
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(' ');
            }
            sb.append(str);
            if (obj instanceof String) {
                sb.append(": \"");
                ByteString byteString = ByteString.EMPTY;
                sb.append(R$styleable.escapeBytes(new ByteString.LiteralByteString(((String) obj).getBytes(Internal.UTF_8))));
                sb.append('\"');
            } else if (obj instanceof ByteString) {
                sb.append(": \"");
                sb.append(R$styleable.escapeBytes((ByteString) obj));
                sb.append('\"');
            } else if (obj instanceof GeneratedMessageLite) {
                sb.append(" {");
                reflectivePrintWithIndent((GeneratedMessageLite) obj, sb, i + 2);
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else if (obj instanceof Map.Entry) {
                sb.append(" {");
                Map.Entry entry = (Map.Entry) obj;
                int i4 = i + 2;
                printField(sb, i4, "key", entry.getKey());
                printField(sb, i4, "value", entry.getValue());
                sb.append("\n");
                while (i2 < i) {
                    sb.append(' ');
                    i2++;
                }
                sb.append("}");
            } else {
                sb.append(": ");
                sb.append(obj.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01ce, code lost:
        if (((java.lang.Integer) r11).intValue() == 0) goto L_0x0223;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01df, code lost:
        if (((java.lang.Float) r11).floatValue() == 0.0f) goto L_0x0223;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01f1, code lost:
        if (((java.lang.Double) r11).doubleValue() == 0.0d) goto L_0x0223;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void reflectivePrintWithIndent(com.google.protobuf.MessageLite r18, java.lang.StringBuilder r19, int r20) {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.HashMap r4 = new java.util.HashMap
            r4.<init>()
            java.util.TreeSet r5 = new java.util.TreeSet
            r5.<init>()
            java.lang.Class r6 = r18.getClass()
            java.lang.reflect.Method[] r6 = r6.getDeclaredMethods()
            int r7 = r6.length
            r8 = 0
            r9 = r8
        L_0x0020:
            java.lang.String r10 = "get"
            if (r9 >= r7) goto L_0x004f
            r11 = r6[r9]
            java.lang.String r12 = r11.getName()
            r4.put(r12, r11)
            java.lang.Class[] r12 = r11.getParameterTypes()
            int r12 = r12.length
            if (r12 != 0) goto L_0x004c
            java.lang.String r12 = r11.getName()
            r3.put(r12, r11)
            java.lang.String r12 = r11.getName()
            boolean r10 = r12.startsWith(r10)
            if (r10 == 0) goto L_0x004c
            java.lang.String r10 = r11.getName()
            r5.add(r10)
        L_0x004c:
            int r9 = r9 + 1
            goto L_0x0020
        L_0x004f:
            java.util.Iterator r5 = r5.iterator()
        L_0x0053:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0242
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = ""
            java.lang.String r9 = r6.replaceFirst(r10, r7)
            java.lang.String r11 = "List"
            boolean r12 = r9.endsWith(r11)
            r13 = 1
            if (r12 == 0) goto L_0x00bf
            java.lang.String r12 = "OrBuilderList"
            boolean r12 = r9.endsWith(r12)
            if (r12 != 0) goto L_0x00bf
            boolean r11 = r9.equals(r11)
            if (r11 != 0) goto L_0x00bf
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = r9.substring(r8, r13)
            java.lang.String r12 = r12.toLowerCase()
            r11.append(r12)
            int r12 = r9.length()
            int r12 = r12 + -4
            java.lang.String r12 = r9.substring(r13, r12)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            java.lang.Object r12 = r3.get(r6)
            java.lang.reflect.Method r12 = (java.lang.reflect.Method) r12
            if (r12 == 0) goto L_0x00bf
            java.lang.Class r14 = r12.getReturnType()
            java.lang.Class<java.util.List> r15 = java.util.List.class
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x00bf
            java.lang.String r6 = camelCaseToSnakeCase(r11)
            java.lang.Object[] r7 = new java.lang.Object[r8]
            java.lang.Object r7 = com.google.protobuf.GeneratedMessageLite.invokeOrDie(r12, r0, r7)
            printField(r1, r2, r6, r7)
            goto L_0x0053
        L_0x00bf:
            java.lang.String r11 = "Map"
            boolean r12 = r9.endsWith(r11)
            if (r12 == 0) goto L_0x0123
            boolean r11 = r9.equals(r11)
            if (r11 != 0) goto L_0x0123
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = r9.substring(r8, r13)
            java.lang.String r12 = r12.toLowerCase()
            r11.append(r12)
            int r12 = r9.length()
            int r12 = r12 + -3
            java.lang.String r12 = r9.substring(r13, r12)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            java.lang.Object r6 = r3.get(r6)
            java.lang.reflect.Method r6 = (java.lang.reflect.Method) r6
            if (r6 == 0) goto L_0x0123
            java.lang.Class r12 = r6.getReturnType()
            java.lang.Class<java.util.Map> r14 = java.util.Map.class
            boolean r12 = r12.equals(r14)
            if (r12 == 0) goto L_0x0123
            java.lang.Class<java.lang.Deprecated> r12 = java.lang.Deprecated.class
            boolean r12 = r6.isAnnotationPresent(r12)
            if (r12 != 0) goto L_0x0123
            int r12 = r6.getModifiers()
            boolean r12 = java.lang.reflect.Modifier.isPublic(r12)
            if (r12 == 0) goto L_0x0123
            java.lang.String r7 = camelCaseToSnakeCase(r11)
            java.lang.Object[] r9 = new java.lang.Object[r8]
            java.lang.Object r6 = com.google.protobuf.GeneratedMessageLite.invokeOrDie(r6, r0, r9)
            printField(r1, r2, r7, r6)
            goto L_0x0053
        L_0x0123:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = "set"
            r6.append(r11)
            r6.append(r9)
            java.lang.String r6 = r6.toString()
            java.lang.Object r6 = r4.get(r6)
            java.lang.reflect.Method r6 = (java.lang.reflect.Method) r6
            if (r6 != 0) goto L_0x013f
            goto L_0x0053
        L_0x013f:
            java.lang.String r6 = "Bytes"
            boolean r6 = r9.endsWith(r6)
            if (r6 == 0) goto L_0x0164
            java.lang.StringBuilder r6 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            int r11 = r9.length()
            int r11 = r11 + -5
            java.lang.String r11 = r9.substring(r8, r11)
            r6.append(r11)
            java.lang.String r6 = r6.toString()
            boolean r6 = r3.containsKey(r6)
            if (r6 == 0) goto L_0x0164
            goto L_0x0053
        L_0x0164:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r11 = r9.substring(r8, r13)
            java.lang.String r11 = r11.toLowerCase()
            r6.append(r11)
            java.lang.String r11 = r9.substring(r13)
            r6.append(r11)
            java.lang.String r6 = r6.toString()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r10)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            java.lang.Object r11 = r3.get(r11)
            java.lang.reflect.Method r11 = (java.lang.reflect.Method) r11
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "has"
            r12.append(r14)
            r12.append(r9)
            java.lang.String r9 = r12.toString()
            java.lang.Object r9 = r3.get(r9)
            java.lang.reflect.Method r9 = (java.lang.reflect.Method) r9
            if (r11 == 0) goto L_0x0053
            java.lang.Object[] r12 = new java.lang.Object[r8]
            java.lang.Object r11 = com.google.protobuf.GeneratedMessageLite.invokeOrDie(r11, r0, r12)
            if (r9 != 0) goto L_0x022b
            boolean r9 = r11 instanceof java.lang.Boolean
            if (r9 == 0) goto L_0x01c3
            r7 = r11
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            r7 = r7 ^ r13
            goto L_0x0226
        L_0x01c3:
            boolean r9 = r11 instanceof java.lang.Integer
            if (r9 == 0) goto L_0x01d1
            r7 = r11
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 != 0) goto L_0x0225
            goto L_0x0223
        L_0x01d1:
            boolean r9 = r11 instanceof java.lang.Float
            if (r9 == 0) goto L_0x01e2
            r7 = r11
            java.lang.Float r7 = (java.lang.Float) r7
            float r7 = r7.floatValue()
            r9 = 0
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 != 0) goto L_0x0225
            goto L_0x0223
        L_0x01e2:
            boolean r9 = r11 instanceof java.lang.Double
            if (r9 == 0) goto L_0x01f4
            r7 = r11
            java.lang.Double r7 = (java.lang.Double) r7
            double r14 = r7.doubleValue()
            r16 = 0
            int r7 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r7 != 0) goto L_0x0225
            goto L_0x0223
        L_0x01f4:
            boolean r9 = r11 instanceof java.lang.String
            if (r9 == 0) goto L_0x01fd
            boolean r7 = r11.equals(r7)
            goto L_0x0226
        L_0x01fd:
            boolean r7 = r11 instanceof com.google.protobuf.ByteString
            if (r7 == 0) goto L_0x0208
            com.google.protobuf.ByteString r7 = com.google.protobuf.ByteString.EMPTY
            boolean r7 = r11.equals(r7)
            goto L_0x0226
        L_0x0208:
            boolean r7 = r11 instanceof com.google.protobuf.MessageLite
            if (r7 == 0) goto L_0x0216
            r7 = r11
            com.google.protobuf.MessageLite r7 = (com.google.protobuf.MessageLite) r7
            com.google.protobuf.GeneratedMessageLite r7 = r7.getDefaultInstanceForType$1()
            if (r11 != r7) goto L_0x0225
            goto L_0x0223
        L_0x0216:
            boolean r7 = r11 instanceof java.lang.Enum
            if (r7 == 0) goto L_0x0225
            r7 = r11
            java.lang.Enum r7 = (java.lang.Enum) r7
            int r7 = r7.ordinal()
            if (r7 != 0) goto L_0x0225
        L_0x0223:
            r7 = r13
            goto L_0x0226
        L_0x0225:
            r7 = r8
        L_0x0226:
            if (r7 != 0) goto L_0x0229
            goto L_0x0237
        L_0x0229:
            r13 = r8
            goto L_0x0237
        L_0x022b:
            java.lang.Object[] r7 = new java.lang.Object[r8]
            java.lang.Object r7 = com.google.protobuf.GeneratedMessageLite.invokeOrDie(r9, r0, r7)
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r13 = r7.booleanValue()
        L_0x0237:
            if (r13 == 0) goto L_0x0053
            java.lang.String r6 = camelCaseToSnakeCase(r6)
            printField(r1, r2, r6, r11)
            goto L_0x0053
        L_0x0242:
            boolean r3 = r0 instanceof com.google.protobuf.GeneratedMessageLite.ExtendableMessage
            if (r3 == 0) goto L_0x026e
            r3 = r0
            com.google.protobuf.GeneratedMessageLite$ExtendableMessage r3 = (com.google.protobuf.GeneratedMessageLite.ExtendableMessage) r3
            com.google.protobuf.FieldSet<com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor> r3 = r3.extensions
            java.util.Iterator r3 = r3.iterator()
        L_0x024f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x026e
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor r5 = (com.google.protobuf.GeneratedMessageLite.ExtensionDescriptor) r5
            java.util.Objects.requireNonNull(r5)
            java.lang.Object r4 = r4.getValue()
            java.lang.String r5 = "[0]"
            printField(r1, r2, r5, r4)
            goto L_0x024f
        L_0x026e:
            com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
            com.google.protobuf.UnknownFieldSetLite r0 = r0.unknownFields
            if (r0 == 0) goto L_0x028c
        L_0x0274:
            int r3 = r0.count
            if (r8 >= r3) goto L_0x028c
            int[] r3 = r0.tags
            r3 = r3[r8]
            int r3 = r3 >>> 3
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.Object[] r4 = r0.objects
            r4 = r4[r8]
            printField(r1, r2, r3, r4)
            int r8 = r8 + 1
            goto L_0x0274
        L_0x028c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageLiteToString.reflectivePrintWithIndent(com.google.protobuf.MessageLite, java.lang.StringBuilder, int):void");
    }
}
