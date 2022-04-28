package com.google.protobuf;

public final class Utf8 {
    public static final Processor processor;

    public static abstract class Processor {
        public abstract int partialIsValidUtf8(byte[] bArr, int i, int i2);
    }

    public static final class SafeProcessor extends Processor {
        public final int partialIsValidUtf8(byte[] bArr, int i, int i2) {
            while (r6 < i2 && bArr[r6] >= 0) {
                i = r6 + 1;
            }
            if (r6 < i2) {
                while (r6 < i2) {
                    int i3 = r6 + 1;
                    byte b = bArr[r6];
                    if (b < 0) {
                        if (b < -32) {
                            if (i3 >= i2) {
                                return b;
                            }
                            if (b >= -62) {
                                r6 = i3 + 1;
                                if (bArr[i3] > -65) {
                                }
                            }
                        } else if (b < -16) {
                            if (i3 >= i2 - 1) {
                                return Utf8.access$1100(bArr, i3, i2);
                            }
                            int i4 = i3 + 1;
                            byte b2 = bArr[i3];
                            if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                                r6 = i4 + 1;
                                if (bArr[i4] > -65) {
                                }
                            }
                        } else if (i3 >= i2 - 2) {
                            return Utf8.access$1100(bArr, i3, i2);
                        } else {
                            int i5 = i3 + 1;
                            byte b3 = bArr[i3];
                            if (b3 <= -65) {
                                if ((((b3 + 112) + (b << 28)) >> 30) == 0) {
                                    int i6 = i5 + 1;
                                    if (bArr[i5] <= -65) {
                                        r6 = i6 + 1;
                                        if (bArr[i6] > -65) {
                                        }
                                    }
                                }
                            }
                        }
                        return -1;
                    }
                    r6 = i3;
                }
            }
            return 0;
        }
    }

    public static final class UnsafeProcessor extends Processor {
        public static int unsafeIncompleteStateFor(byte[] bArr, int i, long j, int i2) {
            if (i2 == 0) {
                Processor processor = Utf8.processor;
                if (i > -12) {
                    return -1;
                }
                return i;
            } else if (i2 == 1) {
                return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(bArr, j));
            } else {
                if (i2 == 2) {
                    return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(bArr, j), UnsafeUtil.getByte(bArr, j + 1));
                }
                throw new AssertionError();
            }
        }

        public final int partialIsValidUtf8(byte[] bArr, int i, int i2) {
            int i3;
            long j;
            byte[] bArr2 = bArr;
            int i4 = i;
            int i5 = i2;
            if ((i4 | i5 | (bArr2.length - i5)) >= 0) {
                long j2 = (long) i4;
                int i6 = (int) (((long) i5) - j2);
                if (i6 >= 16) {
                    long j3 = j2;
                    i3 = 0;
                    while (true) {
                        if (i3 >= i6) {
                            i3 = i6;
                            break;
                        }
                        long j4 = j3 + 1;
                        if (UnsafeUtil.getByte(bArr2, j3) < 0) {
                            break;
                        }
                        i3++;
                        j3 = j4;
                    }
                } else {
                    i3 = 0;
                }
                int i7 = i6 - i3;
                long j5 = j2 + ((long) i3);
                while (true) {
                    byte b = 0;
                    while (true) {
                        if (i7 <= 0) {
                            break;
                        }
                        long j6 = j5 + 1;
                        b = UnsafeUtil.getByte(bArr2, j5);
                        if (b < 0) {
                            j5 = j6;
                            break;
                        }
                        i7--;
                        j5 = j6;
                    }
                    if (i7 == 0) {
                        return 0;
                    }
                    int i8 = i7 - 1;
                    if (b >= -32) {
                        if (b >= -16) {
                            if (i8 >= 3) {
                                i7 = i8 - 3;
                                long j7 = j5 + 1;
                                byte b2 = UnsafeUtil.getByte(bArr2, j5);
                                if (b2 > -65) {
                                    break;
                                }
                                if ((((b2 + 112) + (b << 28)) >> 30) != 0) {
                                    break;
                                }
                                long j8 = j7 + 1;
                                if (UnsafeUtil.getByte(bArr2, j7) > -65) {
                                    break;
                                }
                                j = j8 + 1;
                                if (UnsafeUtil.getByte(bArr2, j8) > -65) {
                                    break;
                                }
                            } else {
                                return unsafeIncompleteStateFor(bArr2, b, j5, i8);
                            }
                        } else if (i8 >= 2) {
                            i7 = i8 - 2;
                            long j9 = j5 + 1;
                            byte b3 = UnsafeUtil.getByte(bArr2, j5);
                            if (b3 > -65 || ((b == -32 && b3 < -96) || (b == -19 && b3 >= -96))) {
                                break;
                            }
                            j5 = j9 + 1;
                            if (UnsafeUtil.getByte(bArr2, j9) > -65) {
                                break;
                            }
                        } else {
                            return unsafeIncompleteStateFor(bArr2, b, j5, i8);
                        }
                    } else if (i8 != 0) {
                        i7 = i8 - 1;
                        if (b < -62) {
                            break;
                        }
                        j = j5 + 1;
                        if (UnsafeUtil.getByte(bArr2, j5) > -65) {
                            break;
                        }
                    } else {
                        return b;
                    }
                    j5 = j;
                }
                return -1;
            }
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(bArr2.length), Integer.valueOf(i), Integer.valueOf(i2)}));
        }
    }

    public static int incompleteStateFor(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    public static int incompleteStateFor(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    static {
        boolean z;
        Processor processor2;
        boolean z2 = true;
        if (!UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS || !UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            if (Android.MEMORY_CLASS == null || Android.IS_ROBOLECTRIC) {
                z2 = false;
            }
            if (!z2) {
                processor2 = new UnsafeProcessor();
                processor = processor2;
            }
        }
        processor2 = new SafeProcessor();
        processor = processor2;
    }

    public static int access$1100(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            if (b > -12) {
                b = -1;
            }
            return b;
        } else if (i3 == 1) {
            return incompleteStateFor(b, bArr[i]);
        } else {
            if (i3 == 2) {
                return incompleteStateFor(b, bArr[i], bArr[i + 1]);
            }
            throw new AssertionError();
        }
    }
}
