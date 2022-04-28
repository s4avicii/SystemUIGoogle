package okio;

import androidx.fragment.R$styleable;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.collections.ArrayAsCollection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Options.kt */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public final ByteString[] byteStrings;
    public final int[] trie;

    /* compiled from: Options.kt */
    public static final class Companion {
        public static void buildTrieRecursive(long j, Buffer buffer, int i, ArrayList arrayList, int i2, int i3, ArrayList arrayList2) {
            boolean z;
            int i4;
            int i5;
            boolean z2;
            int i6;
            long j2;
            long j3;
            Buffer buffer2;
            boolean z3;
            Buffer buffer3 = buffer;
            int i7 = i;
            ArrayList arrayList3 = arrayList;
            int i8 = i2;
            int i9 = i3;
            ArrayList arrayList4 = arrayList2;
            if (i8 < i9) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                int i10 = i8;
                while (i10 < i9) {
                    int i11 = i10 + 1;
                    ByteString byteString = (ByteString) arrayList3.get(i10);
                    Objects.requireNonNull(byteString);
                    if (byteString.getSize$external__okio__android_common__okio_lib() >= i7) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        i10 = i11;
                    } else {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                }
                ByteString byteString2 = (ByteString) arrayList.get(i2);
                ByteString byteString3 = (ByteString) arrayList3.get(i9 - 1);
                Objects.requireNonNull(byteString2);
                if (i7 == byteString2.getSize$external__okio__android_common__okio_lib()) {
                    int intValue = ((Number) arrayList4.get(i8)).intValue();
                    int i12 = i8 + 1;
                    ByteString byteString4 = (ByteString) arrayList3.get(i12);
                    i4 = i12;
                    i5 = intValue;
                    byteString2 = byteString4;
                } else {
                    i4 = i8;
                    i5 = -1;
                }
                Objects.requireNonNull(byteString2);
                byte internalGet$external__okio__android_common__okio_lib = byteString2.internalGet$external__okio__android_common__okio_lib(i7);
                Objects.requireNonNull(byteString3);
                if (internalGet$external__okio__android_common__okio_lib != byteString3.internalGet$external__okio__android_common__okio_lib(i7)) {
                    int i13 = i4 + 1;
                    int i14 = 1;
                    while (i13 < i9) {
                        int i15 = i13 + 1;
                        ByteString byteString5 = (ByteString) arrayList3.get(i13 - 1);
                        Objects.requireNonNull(byteString5);
                        byte internalGet$external__okio__android_common__okio_lib2 = byteString5.internalGet$external__okio__android_common__okio_lib(i7);
                        ByteString byteString6 = (ByteString) arrayList3.get(i13);
                        Objects.requireNonNull(byteString6);
                        if (internalGet$external__okio__android_common__okio_lib2 != byteString6.internalGet$external__okio__android_common__okio_lib(i7)) {
                            i14++;
                        }
                        i13 = i15;
                    }
                    long j4 = (long) 4;
                    long j5 = ((long) (i14 * 2)) + (buffer3.size / j4) + j + ((long) 2);
                    buffer3.writeInt(i14);
                    buffer3.writeInt(i5);
                    int i16 = i4;
                    while (i16 < i9) {
                        int i17 = i16 + 1;
                        ByteString byteString7 = (ByteString) arrayList3.get(i16);
                        Objects.requireNonNull(byteString7);
                        byte internalGet$external__okio__android_common__okio_lib3 = byteString7.internalGet$external__okio__android_common__okio_lib(i7);
                        if (i16 != i4) {
                            ByteString byteString8 = (ByteString) arrayList3.get(i16 - 1);
                            Objects.requireNonNull(byteString8);
                            if (internalGet$external__okio__android_common__okio_lib3 == byteString8.internalGet$external__okio__android_common__okio_lib(i7)) {
                                i16 = i17;
                            }
                        }
                        buffer3.writeInt(internalGet$external__okio__android_common__okio_lib3 & 255);
                        i16 = i17;
                    }
                    Buffer buffer4 = new Buffer();
                    while (i4 < i9) {
                        ByteString byteString9 = (ByteString) arrayList3.get(i4);
                        Objects.requireNonNull(byteString9);
                        byte internalGet$external__okio__android_common__okio_lib4 = byteString9.internalGet$external__okio__android_common__okio_lib(i7);
                        int i18 = i4 + 1;
                        int i19 = i18;
                        while (true) {
                            if (i19 >= i9) {
                                i6 = i9;
                                break;
                            }
                            int i20 = i19 + 1;
                            ByteString byteString10 = (ByteString) arrayList3.get(i19);
                            Objects.requireNonNull(byteString10);
                            if (internalGet$external__okio__android_common__okio_lib4 != byteString10.internalGet$external__okio__android_common__okio_lib(i7)) {
                                i6 = i19;
                                break;
                            }
                            i19 = i20;
                        }
                        if (i18 == i6) {
                            int i21 = i7 + 1;
                            ByteString byteString11 = (ByteString) arrayList3.get(i4);
                            Objects.requireNonNull(byteString11);
                            if (i21 == byteString11.getSize$external__okio__android_common__okio_lib()) {
                                buffer3.writeInt(((Number) arrayList4.get(i4)).intValue());
                                j3 = j4;
                                j2 = j5;
                                buffer2 = buffer4;
                                buffer4 = buffer2;
                                i4 = i6;
                                j4 = j3;
                                j5 = j2;
                            }
                        }
                        buffer3.writeInt(((int) ((buffer4.size / j4) + j5)) * -1);
                        j2 = j5;
                        buffer2 = buffer4;
                        j3 = j4;
                        buildTrieRecursive(j5, buffer4, i7 + 1, arrayList, i4, i6, arrayList2);
                        buffer4 = buffer2;
                        i4 = i6;
                        j4 = j3;
                        j5 = j2;
                    }
                    do {
                    } while (buffer4.read(buffer3, 8192) != -1);
                    return;
                }
                int min = Math.min(byteString2.getSize$external__okio__android_common__okio_lib(), byteString3.getSize$external__okio__android_common__okio_lib());
                int i22 = i7;
                int i23 = 0;
                while (i22 < min) {
                    int i24 = i22 + 1;
                    if (byteString2.internalGet$external__okio__android_common__okio_lib(i22) != byteString3.internalGet$external__okio__android_common__okio_lib(i22)) {
                        break;
                    }
                    i23++;
                    i22 = i24;
                }
                long j6 = (long) 4;
                ByteString byteString12 = byteString2;
                long j7 = 1 + (buffer3.size / j6) + j + ((long) 2) + ((long) i23);
                buffer3.writeInt(-i23);
                buffer3.writeInt(i5);
                int i25 = i7 + i23;
                while (i7 < i25) {
                    buffer3.writeInt(byteString12.internalGet$external__okio__android_common__okio_lib(i7) & 255);
                    i7++;
                }
                if (i4 + 1 == i9) {
                    ByteString byteString13 = (ByteString) arrayList3.get(i4);
                    Objects.requireNonNull(byteString13);
                    if (i25 == byteString13.getSize$external__okio__android_common__okio_lib()) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        buffer3.writeInt(((Number) arrayList4.get(i4)).intValue());
                        return;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                Buffer buffer5 = new Buffer();
                buffer3.writeInt(((int) ((buffer5.size / j6) + j7)) * -1);
                buildTrieRecursive(j7, buffer5, i25, arrayList, i4, i3, arrayList2);
                do {
                } while (buffer5.read(buffer3, 8192) != -1);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* renamed from: of */
    public static final Options m138of(ByteString... byteStringArr) {
        boolean z;
        ArrayList arrayList;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        int i2;
        if (byteStringArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return new Options(new ByteString[0], new int[]{0, -1});
        }
        ArrayList arrayList2 = new ArrayList(new ArrayAsCollection(byteStringArr, false));
        if (arrayList2.size() > 1) {
            Collections.sort(arrayList2);
        }
        ArrayList arrayList3 = new ArrayList(byteStringArr.length);
        int length = byteStringArr.length;
        int i3 = 0;
        while (i3 < length) {
            ByteString byteString = byteStringArr[i3];
            i3++;
            arrayList3.add(-1);
        }
        Object[] array = arrayList3.toArray(new Integer[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        Integer[] numArr = (Integer[]) array;
        Object[] copyOf = Arrays.copyOf(numArr, numArr.length);
        if (copyOf.length == 0) {
            arrayList = new ArrayList();
        } else {
            arrayList = new ArrayList(new ArrayAsCollection(copyOf, true));
        }
        int length2 = byteStringArr.length;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length2) {
            ByteString byteString2 = byteStringArr[i4];
            i4++;
            int i6 = i5 + 1;
            int size = arrayList2.size();
            int size2 = arrayList2.size();
            if (size < 0) {
                throw new IllegalArgumentException("fromIndex (" + 0 + ") is greater than toIndex (" + size + ").");
            } else if (size <= size2) {
                int i7 = size - 1;
                int i8 = 0;
                while (true) {
                    if (i8 > i7) {
                        i2 = -(i8 + 1);
                        break;
                    }
                    i2 = (i8 + i7) >>> 1;
                    int compareValues = R$styleable.compareValues((Comparable) arrayList2.get(i2), byteString2);
                    if (compareValues >= 0) {
                        if (compareValues <= 0) {
                            break;
                        }
                        i7 = i2 - 1;
                    } else {
                        i8 = i2 + 1;
                    }
                }
                arrayList.set(i2, Integer.valueOf(i5));
                i5 = i6;
            } else {
                throw new IndexOutOfBoundsException("toIndex (" + size + ") is greater than size (" + size2 + ").");
            }
        }
        ByteString byteString3 = (ByteString) arrayList2.get(0);
        Objects.requireNonNull(byteString3);
        if (byteString3.getSize$external__okio__android_common__okio_lib() > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            int i9 = 0;
            while (i9 < arrayList2.size()) {
                ByteString byteString4 = (ByteString) arrayList2.get(i9);
                int i10 = i9 + 1;
                int i11 = i10;
                while (i11 < arrayList2.size()) {
                    ByteString byteString5 = (ByteString) arrayList2.get(i11);
                    Objects.requireNonNull(byteString5);
                    if (!byteString5.rangeEquals(byteString4, byteString4.getSize$external__okio__android_common__okio_lib())) {
                        continue;
                        break;
                    }
                    if (byteString5.getSize$external__okio__android_common__okio_lib() != byteString4.getSize$external__okio__android_common__okio_lib()) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    if (!z4) {
                        throw new IllegalArgumentException(Intrinsics.stringPlus("duplicate option: ", byteString5).toString());
                    } else if (((Number) arrayList.get(i11)).intValue() > ((Number) arrayList.get(i9)).intValue()) {
                        arrayList2.remove(i11);
                        arrayList.remove(i11);
                    } else {
                        i11++;
                    }
                }
                i9 = i10;
            }
            Buffer buffer = new Buffer();
            Companion.buildTrieRecursive(0, buffer, 0, arrayList2, 0, arrayList2.size(), arrayList);
            int[] iArr = new int[((int) (buffer.size / ((long) 4)))];
            int i12 = 0;
            while (true) {
                long j = buffer.size;
                if (j == 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    return new Options((ByteString[]) Arrays.copyOf(byteStringArr, byteStringArr.length), iArr);
                }
                int i13 = i12 + 1;
                if (j >= 4) {
                    Segment segment = buffer.head;
                    Intrinsics.checkNotNull(segment);
                    int i14 = segment.pos;
                    int i15 = segment.limit;
                    if (((long) (i15 - i14)) < 4) {
                        i = ((buffer.readByte() & 255) << 24) | ((buffer.readByte() & 255) << 16) | ((buffer.readByte() & 255) << 8) | (buffer.readByte() & 255);
                    } else {
                        byte[] bArr = segment.data;
                        int i16 = i14 + 1;
                        int i17 = i16 + 1;
                        byte b = ((bArr[i14] & 255) << 24) | ((bArr[i16] & 255) << 16);
                        int i18 = i17 + 1;
                        byte b2 = b | ((bArr[i17] & 255) << 8);
                        int i19 = i18 + 1;
                        byte b3 = b2 | (bArr[i18] & 255);
                        buffer.size -= 4;
                        if (i19 == i15) {
                            buffer.head = segment.pop();
                            SegmentPool.recycle(segment);
                        } else {
                            segment.pos = i19;
                        }
                        i = b3;
                    }
                    iArr[i12] = i;
                    i12 = i13;
                } else {
                    throw new EOFException();
                }
            }
        } else {
            throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
        }
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (!(obj instanceof ByteString)) {
            return false;
        }
        return super.contains((ByteString) obj);
    }

    public final Object get(int i) {
        return this.byteStrings[i];
    }

    public final int getSize() {
        return this.byteStrings.length;
    }

    public final /* bridge */ int indexOf(Object obj) {
        if (!(obj instanceof ByteString)) {
            return -1;
        }
        return super.indexOf((ByteString) obj);
    }

    public final /* bridge */ int lastIndexOf(Object obj) {
        if (!(obj instanceof ByteString)) {
            return -1;
        }
        return super.lastIndexOf((ByteString) obj);
    }

    public Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }
}
