package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.FieldSet.FieldDescriptorLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.LazyField;
import com.google.protobuf.MessageLite;
import com.google.protobuf.SmallSortedMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class FieldSet<FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>> {
    public static final FieldSet DEFAULT_INSTANCE = new FieldSet(0);
    public final SmallSortedMap.C24871 fields;
    public boolean hasLazyField = false;
    public boolean isImmutable;

    public interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T> {
        WireFormat$JavaType getLiteJavaType();

        void getLiteType();

        GeneratedMessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite);

        void isRepeated();
    }

    public FieldSet() {
        int i = SmallSortedMap.$r8$clinit;
        this.fields = new SmallSortedMap<Object, Object>(16) {
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i = 0; i < getNumArrayEntries(); i++) {
                        ((FieldSet.FieldDescriptorLite) getArrayEntryAt(i).getKey()).isRepeated();
                    }
                    for (Map.Entry key : getOverflowEntries()) {
                        ((FieldSet.FieldDescriptorLite) key.getKey()).isRepeated();
                    }
                }
                SmallSortedMap.super.makeImmutable();
            }
        };
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            if (!isInitialized(this.fields.getArrayEntryAt(i))) {
                return false;
            }
        }
        for (Map.Entry isInitialized : this.fields.getOverflowEntries()) {
            if (!isInitialized(isInitialized)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
        if ((r3 instanceof byte[]) == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0019, code lost:
        if ((r3 instanceof com.google.protobuf.LazyField) == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if ((r3 instanceof com.google.protobuf.Internal.EnumLite) == false) goto L_0x002e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void verifyType(com.google.protobuf.WireFormat$FieldType r2, java.lang.Object r3) {
        /*
            java.nio.charset.Charset r0 = com.google.protobuf.Internal.UTF_8
            java.util.Objects.requireNonNull(r3)
            com.google.protobuf.WireFormat$JavaType r2 = r2.getJavaType()
            int r2 = r2.ordinal()
            r0 = 1
            r1 = 0
            switch(r2) {
                case 0: goto L_0x0040;
                case 1: goto L_0x003d;
                case 2: goto L_0x003a;
                case 3: goto L_0x0037;
                case 4: goto L_0x0034;
                case 5: goto L_0x0031;
                case 6: goto L_0x0025;
                case 7: goto L_0x001c;
                case 8: goto L_0x0013;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0042
        L_0x0013:
            boolean r2 = r3 instanceof com.google.protobuf.MessageLite
            if (r2 != 0) goto L_0x002f
            boolean r2 = r3 instanceof com.google.protobuf.LazyField
            if (r2 == 0) goto L_0x002e
            goto L_0x002f
        L_0x001c:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L_0x002f
            boolean r2 = r3 instanceof com.google.protobuf.Internal.EnumLite
            if (r2 == 0) goto L_0x002e
            goto L_0x002f
        L_0x0025:
            boolean r2 = r3 instanceof com.google.protobuf.ByteString
            if (r2 != 0) goto L_0x002f
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L_0x002e
            goto L_0x002f
        L_0x002e:
            r0 = r1
        L_0x002f:
            r1 = r0
            goto L_0x0042
        L_0x0031:
            boolean r1 = r3 instanceof java.lang.String
            goto L_0x0042
        L_0x0034:
            boolean r1 = r3 instanceof java.lang.Boolean
            goto L_0x0042
        L_0x0037:
            boolean r1 = r3 instanceof java.lang.Double
            goto L_0x0042
        L_0x003a:
            boolean r1 = r3 instanceof java.lang.Float
            goto L_0x0042
        L_0x003d:
            boolean r1 = r3 instanceof java.lang.Long
            goto L_0x0042
        L_0x0040:
            boolean r1 = r3 instanceof java.lang.Integer
        L_0x0042:
            if (r1 == 0) goto L_0x0045
            return
        L_0x0045:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.FieldSet.verifyType(com.google.protobuf.WireFormat$FieldType, java.lang.Object):void");
    }

    public final FieldSet<FieldDescriptorType> clone() {
        FieldSet<FieldDescriptorType> fieldSet = new FieldSet<>();
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry arrayEntryAt = this.fields.getArrayEntryAt(i);
            fieldSet.setField((FieldDescriptorLite) arrayEntryAt.getKey(), arrayEntryAt.getValue());
        }
        for (Map.Entry entry : this.fields.getOverflowEntries()) {
            fieldSet.setField((FieldDescriptorLite) entry.getKey(), entry.getValue());
        }
        fieldSet.hasLazyField = this.hasLazyField;
        return fieldSet;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FieldSet)) {
            return false;
        }
        return this.fields.equals(((FieldSet) obj).fields);
    }

    public final Object getField(FieldDescriptorType fielddescriptortype) {
        Object obj = this.fields.get(fielddescriptortype);
        if (!(obj instanceof LazyField)) {
            return obj;
        }
        LazyField lazyField = (LazyField) obj;
        Objects.requireNonNull(lazyField);
        return lazyField.getValue((MessageLite) null);
    }

    public final int hashCode() {
        return this.fields.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(((SmallSortedMap.EntrySet) this.fields.entrySet()).iterator());
        }
        return ((SmallSortedMap.EntrySet) this.fields.entrySet()).iterator();
    }

    public final void mergeFromField(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite) entry.getKey();
        byte[] value = entry.getValue();
        if (value instanceof LazyField) {
            LazyField lazyField = (LazyField) value;
            Objects.requireNonNull(lazyField);
            value = lazyField.getValue((MessageLite) null);
        }
        fieldDescriptorLite.isRepeated();
        if (fieldDescriptorLite.getLiteJavaType() == WireFormat$JavaType.MESSAGE) {
            Object field = getField(fieldDescriptorLite);
            if (field == null) {
                SmallSortedMap.C24871 r4 = this.fields;
                if (value instanceof byte[]) {
                    byte[] bArr = (byte[]) value;
                    byte[] bArr2 = new byte[bArr.length];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    value = bArr2;
                }
                r4.put(fieldDescriptorLite, value);
                return;
            }
            GeneratedMessageLite.Builder internalMergeFrom = fieldDescriptorLite.internalMergeFrom(((MessageLite) field).toBuilder$1(), (MessageLite) value);
            Objects.requireNonNull(internalMergeFrom);
            GeneratedMessageLite buildPartial = internalMergeFrom.buildPartial();
            if (buildPartial.isInitialized()) {
                this.fields.put(fieldDescriptorLite, buildPartial);
                return;
            }
            throw new UninitializedMessageException();
        }
        SmallSortedMap.C24871 r42 = this.fields;
        if (value instanceof byte[]) {
            byte[] bArr3 = (byte[]) value;
            byte[] bArr4 = new byte[bArr3.length];
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            value = bArr4;
        }
        r42.put(fieldDescriptorLite, value);
    }

    public final void setField(FieldDescriptorType fielddescriptortype, Object obj) {
        fielddescriptortype.isRepeated();
        fielddescriptortype.getLiteType();
        verifyType((WireFormat$FieldType) null, obj);
        if (obj instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put(fielddescriptortype, obj);
    }

    public static boolean isInitialized(Map.Entry entry) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite) entry.getKey();
        if (fieldDescriptorLite.getLiteJavaType() == WireFormat$JavaType.MESSAGE) {
            fieldDescriptorLite.isRepeated();
            Object value = entry.getValue();
            if (value instanceof MessageLite) {
                if (!((MessageLite) value).isInitialized()) {
                    return false;
                }
            } else if (value instanceof LazyField) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    public FieldSet(int i) {
        int i2 = SmallSortedMap.$r8$clinit;
        SmallSortedMap.C24871 r0 = new SmallSortedMap<Object, Object>(0) {
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i = 0; i < getNumArrayEntries(); i++) {
                        ((FieldSet.FieldDescriptorLite) getArrayEntryAt(i).getKey()).isRepeated();
                    }
                    for (Map.Entry key : getOverflowEntries()) {
                        ((FieldSet.FieldDescriptorLite) key.getKey()).isRepeated();
                    }
                }
                SmallSortedMap.super.makeImmutable();
            }
        };
        this.fields = r0;
        if (!this.isImmutable) {
            r0.makeImmutable();
            this.isImmutable = true;
        }
    }
}
