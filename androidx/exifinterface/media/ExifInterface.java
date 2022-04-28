package androidx.exifinterface.media;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

public final class ExifInterface {
    public static final Charset ASCII;
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    public static final Pattern DATETIME_PRIMARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
    public static final Pattern DATETIME_SECONDARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
    public static final boolean DEBUG = Log.isLoggable("ExifInterface", 3);
    public static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    public static final ExifTag[] EXIF_POINTER_TAGS = {new ExifTag("SubIFDPointer", 330, 4), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag("CameraSettingsIFDPointer", 8224, 1), new ExifTag("ImageProcessingIFDPointer", 8256, 1)};
    public static final ExifTag[][] EXIF_TAGS;
    public static final List<Integer> FLIPPED_ROTATION_ORDER = Arrays.asList(new Integer[]{2, 7, 4, 5});
    public static final Pattern GPS_TIMESTAMP_PATTERN = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
    public static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    public static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, 49};
    public static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, 112};
    public static final byte[] IDENTIFIER_EXIF_APP1;
    public static final byte[] IDENTIFIER_XMP_APP1;
    public static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    public static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    public static final byte[] JPEG_SIGNATURE = {-1, -40, -1};
    public static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    public static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    public static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    public static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    public static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    public static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, 26, 10};
    public static final List<Integer> ROTATION_ORDER = Arrays.asList(new Integer[]{1, 6, 3, 8});
    public static final ExifTag TAG_RAF_IMAGE_SIZE = new ExifTag("StripOffsets", 273, 3);
    public static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
    public static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());
    public static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    public static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
    public static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
    public static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
    public static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    public static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    public static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, 42};
    public static final HashMap<Integer, Integer> sExifPointerTagMap = new HashMap<>();
    public static final HashMap<Integer, ExifTag>[] sExifTagMapsForReading = new HashMap[10];
    public static final HashMap<String, ExifTag>[] sExifTagMapsForWriting = new HashMap[10];
    public static final HashSet<String> sTagSetForCompatibility = new HashSet<>(Arrays.asList(new String[]{"FNumber", "DigitalZoomRatio", "ExposureTime", "SubjectDistance", "GPSTimeStamp"}));
    public boolean mAreThumbnailStripsConsecutive;
    public final HashMap<String, ExifAttribute>[] mAttributes;
    public HashSet mAttributesOffsets;
    public ByteOrder mExifByteOrder = ByteOrder.BIG_ENDIAN;
    public boolean mHasThumbnail;
    public boolean mHasThumbnailStrips;
    public int mMimeType;
    public int mOffsetToExifData;
    public int mOrfMakerNoteOffset;
    public int mOrfThumbnailLength;
    public int mOrfThumbnailOffset;
    public FileDescriptor mSeekableFileDescriptor;
    public byte[] mThumbnailBytes;
    public int mThumbnailCompression;
    public int mThumbnailLength;
    public int mThumbnailOffset;
    public boolean mXmpIsFromSeparateMarker;

    public static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        public ByteOrder mByteOrder;
        public final DataInputStream mDataInputStream;
        public int mPosition;
        public byte[] mSkipBuffer;

        public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
            this(new ByteArrayInputStream(bArr), ByteOrder.BIG_ENDIAN);
        }

        public final int read() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        public final void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mPosition += i2;
            this.mDataInputStream.readFully(bArr, i, i2);
        }

        public final void skipFully(int i) throws IOException {
            int i2 = 0;
            while (i2 < i) {
                int i3 = i - i2;
                int skip = (int) this.mDataInputStream.skip((long) i3);
                if (skip <= 0) {
                    if (this.mSkipBuffer == null) {
                        this.mSkipBuffer = new byte[8192];
                    }
                    skip = this.mDataInputStream.read(this.mSkipBuffer, 0, Math.min(8192, i3));
                    if (skip == -1) {
                        throw new EOFException(C0155xe8491b12.m16m("Reached EOF while skipping ", i, " bytes."));
                    }
                }
                i2 += skip;
            }
            this.mPosition += i2;
        }

        public ByteOrderedDataInputStream(BufferedInputStream bufferedInputStream) throws IOException {
            this(bufferedInputStream, ByteOrder.BIG_ENDIAN);
        }

        public final int available() throws IOException {
            return this.mDataInputStream.available();
        }

        public final void mark(int i) {
            throw new UnsupportedOperationException("Mark is currently unsupported");
        }

        public final boolean readBoolean() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        public final byte readByte() throws IOException {
            this.mPosition++;
            int read = this.mDataInputStream.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }

        public final char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        public final int readInt() throws IOException {
            this.mPosition += 4;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid byte order: ");
                m.append(this.mByteOrder);
                throw new IOException(m.toString());
            }
            throw new EOFException();
        }

        public final String readLine() throws IOException {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        public final long readLong() throws IOException {
            this.mPosition += 8;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            int read5 = this.mDataInputStream.read();
            int read6 = this.mDataInputStream.read();
            int read7 = this.mDataInputStream.read();
            int read8 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (((long) read8) << 56) + (((long) read7) << 48) + (((long) read6) << 40) + (((long) read5) << 32) + (((long) read4) << 24) + (((long) read3) << 16) + (((long) read2) << 8) + ((long) read);
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (((long) read) << 56) + (((long) read2) << 48) + (((long) read3) << 40) + (((long) read4) << 32) + (((long) read5) << 24) + (((long) read6) << 16) + (((long) read7) << 8) + ((long) read8);
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid byte order: ");
                m.append(this.mByteOrder);
                throw new IOException(m.toString());
            }
            throw new EOFException();
        }

        public final short readShort() throws IOException {
            int i;
            this.mPosition += 2;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    i = (read2 << 8) + read;
                } else if (byteOrder == BIG_ENDIAN) {
                    i = (read << 8) + read2;
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid byte order: ");
                    m.append(this.mByteOrder);
                    throw new IOException(m.toString());
                }
                return (short) i;
            }
            throw new EOFException();
        }

        public final String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        public final int readUnsignedByte() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        public final int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.mByteOrder;
                if (byteOrder == LITTLE_ENDIAN) {
                    return (read2 << 8) + read;
                }
                if (byteOrder == BIG_ENDIAN) {
                    return (read << 8) + read2;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid byte order: ");
                m.append(this.mByteOrder);
                throw new IOException(m.toString());
            }
            throw new EOFException();
        }

        public final void reset() {
            throw new UnsupportedOperationException("Reset is currently unsupported");
        }

        public final int skipBytes(int i) throws IOException {
            throw new UnsupportedOperationException("skipBytes is currently unsupported");
        }

        public ByteOrderedDataInputStream(InputStream inputStream, ByteOrder byteOrder) throws IOException {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.mDataInputStream = dataInputStream;
            dataInputStream.mark(0);
            this.mPosition = 0;
            this.mByteOrder = byteOrder;
        }

        public final int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.mDataInputStream.read(bArr, i, i2);
            this.mPosition += read;
            return read;
        }

        public final double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        public final float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        public final void readFully(byte[] bArr) throws IOException {
            this.mPosition += bArr.length;
            this.mDataInputStream.readFully(bArr);
        }
    }

    public static class ByteOrderedDataOutputStream extends FilterOutputStream {
        public ByteOrder mByteOrder;
        public final OutputStream mOutputStream;

        public final void write(byte[] bArr) throws IOException {
            this.mOutputStream.write(bArr);
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            this.mOutputStream.write(bArr, i, i2);
        }

        public final void writeByte(int i) throws IOException {
            this.mOutputStream.write(i);
        }

        public final void writeInt(int i) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((i >>> 0) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 0) & 255);
            }
        }

        public final void writeShort(short s) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((s >>> 0) & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write((s >>> 0) & 255);
            }
        }

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }
    }

    public static class ExifAttribute {
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        public ExifAttribute(int i, int i2, byte[] bArr) {
            this(i, i2, -1, bArr);
        }

        public static ExifAttribute createULong(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length)]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, wrap.array());
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length)]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, wrap.array());
        }

        public ExifAttribute(int i, int i2, long j, byte[] bArr) {
            this.format = i;
            this.numberOfComponents = i2;
            this.bytesOffset = j;
            this.bytes = bArr;
        }

        public static ExifAttribute createString(String str) {
            byte[] bytes2 = (str + 0).getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, bytes2.length, bytes2);
        }

        public static ExifAttribute createURational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * rationalArr.length)]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(5, rationalArr.length, wrap.array());
        }

        /* JADX WARNING: Removed duplicated region for block: B:164:0x01aa A[SYNTHETIC, Splitter:B:164:0x01aa] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object getValue(java.nio.ByteOrder r13) {
            /*
                r12 = this;
                java.lang.String r0 = "IOException occurred while closing InputStream"
                java.lang.String r1 = "ExifInterface"
                r2 = 0
                androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r3 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ IOException -> 0x0194, all -> 0x0192 }
                byte[] r4 = r12.bytes     // Catch:{ IOException -> 0x0194, all -> 0x0192 }
                r3.<init>((byte[]) r4)     // Catch:{ IOException -> 0x0194, all -> 0x0192 }
                r3.mByteOrder = r13     // Catch:{ IOException -> 0x00c8 }
                int r13 = r12.format     // Catch:{ IOException -> 0x00c8 }
                r4 = 0
                r5 = 4294967295(0xffffffff, double:2.1219957905E-314)
                r7 = 1
                switch(r13) {
                    case 1: goto L_0x0155;
                    case 2: goto L_0x010a;
                    case 3: goto L_0x00f0;
                    case 4: goto L_0x00d4;
                    case 5: goto L_0x00aa;
                    case 6: goto L_0x0155;
                    case 7: goto L_0x010a;
                    case 8: goto L_0x0090;
                    case 9: goto L_0x0076;
                    case 10: goto L_0x0051;
                    case 11: goto L_0x0036;
                    case 12: goto L_0x001c;
                    default: goto L_0x001a;
                }     // Catch:{ IOException -> 0x00c8 }
            L_0x001a:
                goto L_0x0189
            L_0x001c:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                double[] r13 = new double[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x0020:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x002d
                double r5 = r3.readDouble()     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r5     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x0020
            L_0x002d:
                r3.close()     // Catch:{ IOException -> 0x0031 }
                goto L_0x0035
            L_0x0031:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0035:
                return r13
            L_0x0036:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                double[] r13 = new double[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x003a:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x0048
                float r5 = r3.readFloat()     // Catch:{ IOException -> 0x00c8 }
                double r5 = (double) r5     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r5     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x003a
            L_0x0048:
                r3.close()     // Catch:{ IOException -> 0x004c }
                goto L_0x0050
            L_0x004c:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0050:
                return r13
            L_0x0051:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                androidx.exifinterface.media.ExifInterface$Rational[] r13 = new androidx.exifinterface.media.ExifInterface.Rational[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x0055:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x006d
                int r5 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                long r5 = (long) r5     // Catch:{ IOException -> 0x00c8 }
                int r7 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                long r7 = (long) r7     // Catch:{ IOException -> 0x00c8 }
                androidx.exifinterface.media.ExifInterface$Rational r9 = new androidx.exifinterface.media.ExifInterface$Rational     // Catch:{ IOException -> 0x00c8 }
                r9.<init>(r5, r7)     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r9     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x0055
            L_0x006d:
                r3.close()     // Catch:{ IOException -> 0x0071 }
                goto L_0x0075
            L_0x0071:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0075:
                return r13
            L_0x0076:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                int[] r13 = new int[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x007a:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x0087
                int r5 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r5     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x007a
            L_0x0087:
                r3.close()     // Catch:{ IOException -> 0x008b }
                goto L_0x008f
            L_0x008b:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x008f:
                return r13
            L_0x0090:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                int[] r13 = new int[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x0094:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x00a1
                short r5 = r3.readShort()     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r5     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x0094
            L_0x00a1:
                r3.close()     // Catch:{ IOException -> 0x00a5 }
                goto L_0x00a9
            L_0x00a5:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x00a9:
                return r13
            L_0x00aa:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                androidx.exifinterface.media.ExifInterface$Rational[] r13 = new androidx.exifinterface.media.ExifInterface.Rational[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x00ae:
                int r7 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r7) goto L_0x00cb
                int r7 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                long r7 = (long) r7     // Catch:{ IOException -> 0x00c8 }
                long r7 = r7 & r5
                int r9 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                long r9 = (long) r9     // Catch:{ IOException -> 0x00c8 }
                long r9 = r9 & r5
                androidx.exifinterface.media.ExifInterface$Rational r11 = new androidx.exifinterface.media.ExifInterface$Rational     // Catch:{ IOException -> 0x00c8 }
                r11.<init>(r7, r9)     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r11     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x00ae
            L_0x00c8:
                r12 = move-exception
                goto L_0x0196
            L_0x00cb:
                r3.close()     // Catch:{ IOException -> 0x00cf }
                goto L_0x00d3
            L_0x00cf:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x00d3:
                return r13
            L_0x00d4:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                long[] r13 = new long[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x00d8:
                int r7 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r7) goto L_0x00e7
                int r7 = r3.readInt()     // Catch:{ IOException -> 0x00c8 }
                long r7 = (long) r7     // Catch:{ IOException -> 0x00c8 }
                long r7 = r7 & r5
                r13[r4] = r7     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x00d8
            L_0x00e7:
                r3.close()     // Catch:{ IOException -> 0x00eb }
                goto L_0x00ef
            L_0x00eb:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x00ef:
                return r13
            L_0x00f0:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                int[] r13 = new int[r13]     // Catch:{ IOException -> 0x00c8 }
            L_0x00f4:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x0101
                int r5 = r3.readUnsignedShort()     // Catch:{ IOException -> 0x00c8 }
                r13[r4] = r5     // Catch:{ IOException -> 0x00c8 }
                int r4 = r4 + 1
                goto L_0x00f4
            L_0x0101:
                r3.close()     // Catch:{ IOException -> 0x0105 }
                goto L_0x0109
            L_0x0105:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0109:
                return r13
            L_0x010a:
                int r13 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                byte[] r5 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x00c8 }
                int r5 = r5.length     // Catch:{ IOException -> 0x00c8 }
                if (r13 < r5) goto L_0x0127
                r13 = r4
            L_0x0112:
                byte[] r5 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x00c8 }
                int r6 = r5.length     // Catch:{ IOException -> 0x00c8 }
                if (r13 >= r6) goto L_0x0124
                byte[] r6 = r12.bytes     // Catch:{ IOException -> 0x00c8 }
                byte r6 = r6[r13]     // Catch:{ IOException -> 0x00c8 }
                byte r8 = r5[r13]     // Catch:{ IOException -> 0x00c8 }
                if (r6 == r8) goto L_0x0121
                r7 = r4
                goto L_0x0124
            L_0x0121:
                int r13 = r13 + 1
                goto L_0x0112
            L_0x0124:
                if (r7 == 0) goto L_0x0127
                int r4 = r5.length     // Catch:{ IOException -> 0x00c8 }
            L_0x0127:
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c8 }
                r13.<init>()     // Catch:{ IOException -> 0x00c8 }
            L_0x012c:
                int r5 = r12.numberOfComponents     // Catch:{ IOException -> 0x00c8 }
                if (r4 >= r5) goto L_0x0148
                byte[] r5 = r12.bytes     // Catch:{ IOException -> 0x00c8 }
                byte r5 = r5[r4]     // Catch:{ IOException -> 0x00c8 }
                if (r5 != 0) goto L_0x0137
                goto L_0x0148
            L_0x0137:
                r6 = 32
                if (r5 < r6) goto L_0x0140
                char r5 = (char) r5     // Catch:{ IOException -> 0x00c8 }
                r13.append(r5)     // Catch:{ IOException -> 0x00c8 }
                goto L_0x0145
            L_0x0140:
                r5 = 63
                r13.append(r5)     // Catch:{ IOException -> 0x00c8 }
            L_0x0145:
                int r4 = r4 + 1
                goto L_0x012c
            L_0x0148:
                java.lang.String r12 = r13.toString()     // Catch:{ IOException -> 0x00c8 }
                r3.close()     // Catch:{ IOException -> 0x0150 }
                goto L_0x0154
            L_0x0150:
                r13 = move-exception
                android.util.Log.e(r1, r0, r13)
            L_0x0154:
                return r12
            L_0x0155:
                byte[] r12 = r12.bytes     // Catch:{ IOException -> 0x00c8 }
                int r13 = r12.length     // Catch:{ IOException -> 0x00c8 }
                if (r13 != r7) goto L_0x0179
                byte r13 = r12[r4]     // Catch:{ IOException -> 0x00c8 }
                if (r13 < 0) goto L_0x0179
                byte r13 = r12[r4]     // Catch:{ IOException -> 0x00c8 }
                if (r13 > r7) goto L_0x0179
                java.lang.String r13 = new java.lang.String     // Catch:{ IOException -> 0x00c8 }
                char[] r5 = new char[r7]     // Catch:{ IOException -> 0x00c8 }
                byte r12 = r12[r4]     // Catch:{ IOException -> 0x00c8 }
                int r12 = r12 + 48
                char r12 = (char) r12     // Catch:{ IOException -> 0x00c8 }
                r5[r4] = r12     // Catch:{ IOException -> 0x00c8 }
                r13.<init>(r5)     // Catch:{ IOException -> 0x00c8 }
                r3.close()     // Catch:{ IOException -> 0x0174 }
                goto L_0x0178
            L_0x0174:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0178:
                return r13
            L_0x0179:
                java.lang.String r13 = new java.lang.String     // Catch:{ IOException -> 0x00c8 }
                java.nio.charset.Charset r4 = androidx.exifinterface.media.ExifInterface.ASCII     // Catch:{ IOException -> 0x00c8 }
                r13.<init>(r12, r4)     // Catch:{ IOException -> 0x00c8 }
                r3.close()     // Catch:{ IOException -> 0x0184 }
                goto L_0x0188
            L_0x0184:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0188:
                return r13
            L_0x0189:
                r3.close()     // Catch:{ IOException -> 0x018d }
                goto L_0x0191
            L_0x018d:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x0191:
                return r2
            L_0x0192:
                r12 = move-exception
                goto L_0x01a8
            L_0x0194:
                r12 = move-exception
                r3 = r2
            L_0x0196:
                java.lang.String r13 = "IOException occurred during reading a value"
                android.util.Log.w(r1, r13, r12)     // Catch:{ all -> 0x01a6 }
                if (r3 == 0) goto L_0x01a5
                r3.close()     // Catch:{ IOException -> 0x01a1 }
                goto L_0x01a5
            L_0x01a1:
                r12 = move-exception
                android.util.Log.e(r1, r0, r12)
            L_0x01a5:
                return r2
            L_0x01a6:
                r12 = move-exception
                r2 = r3
            L_0x01a8:
                if (r2 == 0) goto L_0x01b2
                r2.close()     // Catch:{ IOException -> 0x01ae }
                goto L_0x01b2
            L_0x01ae:
                r13 = move-exception
                android.util.Log.e(r1, r0, r13)
            L_0x01b2:
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(");
            m.append(ExifInterface.IFD_FORMAT_NAMES[this.format]);
            m.append(", data length:");
            m.append(this.bytes.length);
            m.append(")");
            return m.toString();
        }

        public final double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] jArr = (long[]) value;
                    if (jArr.length == 1) {
                        return (double) jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] iArr = (int[]) value;
                    if (iArr.length == 1) {
                        return (double) iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof double[]) {
                    double[] dArr = (double[]) value;
                    if (dArr.length == 1) {
                        return dArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof Rational[]) {
                    Rational[] rationalArr = (Rational[]) value;
                    if (rationalArr.length == 1) {
                        Rational rational = rationalArr[0];
                        Objects.requireNonNull(rational);
                        return ((double) rational.numerator) / ((double) rational.denominator);
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public final int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] jArr = (long[]) value;
                    if (jArr.length == 1) {
                        return (int) jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] iArr = (int[]) value;
                    if (iArr.length == 1) {
                        return iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public final String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (!(value instanceof Rational[])) {
                return null;
            } else {
                Rational[] rationalArr = (Rational[]) value;
                while (i < rationalArr.length) {
                    sb.append(rationalArr[i].numerator);
                    sb.append('/');
                    sb.append(rationalArr[i].denominator);
                    i++;
                    if (i != rationalArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
        }

        public static ExifAttribute createULong(long j, ByteOrder byteOrder) {
            return createULong(new long[]{j}, byteOrder);
        }

        public static ExifAttribute createUShort(int i, ByteOrder byteOrder) {
            return createUShort(new int[]{i}, byteOrder);
        }
    }

    public static class Rational {
        public final long denominator;
        public final long numerator;

        public final String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0;
                this.denominator = 1;
                return;
            }
            this.numerator = j;
            this.denominator = j2;
        }
    }

    public static class SeekableByteOrderedDataInputStream extends ByteOrderedDataInputStream {
        public SeekableByteOrderedDataInputStream(byte[] bArr) throws IOException {
            super(bArr);
            this.mDataInputStream.mark(Integer.MAX_VALUE);
        }

        public final void seek(long j) throws IOException {
            int i = this.mPosition;
            if (((long) i) > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
            } else {
                j -= (long) i;
            }
            skipFully((int) j);
        }

        public SeekableByteOrderedDataInputStream(BufferedInputStream bufferedInputStream) throws IOException {
            super(bufferedInputStream);
            if (bufferedInputStream.markSupported()) {
                this.mDataInputStream.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }
    }

    static {
        ExifTag[] exifTagArr = {new ExifTag("NewSubfileType", 254, 4), new ExifTag("SubfileType", 255, 4), new ExifTag("ImageWidth", 256, 3, 4), new ExifTag("ImageLength", 257, 3, 4), new ExifTag("BitsPerSample", 258, 3), new ExifTag("Compression", 259, 3), new ExifTag("PhotometricInterpretation", 262, 3), new ExifTag("ImageDescription", 270, 2), new ExifTag("Make", 271, 2), new ExifTag("Model", 272, 2), new ExifTag("StripOffsets", 273, 3, 4), new ExifTag("Orientation", 274, 3), new ExifTag("SamplesPerPixel", 277, 3), new ExifTag("RowsPerStrip", 278, 3, 4), new ExifTag("StripByteCounts", 279, 3, 4), new ExifTag("XResolution", 282, 5), new ExifTag("YResolution", 283, 5), new ExifTag("PlanarConfiguration", 284, 3), new ExifTag("ResolutionUnit", 296, 3), new ExifTag("TransferFunction", 301, 3), new ExifTag("Software", 305, 2), new ExifTag("DateTime", 306, 2), new ExifTag("Artist", 315, 2), new ExifTag("WhitePoint", 318, 5), new ExifTag("PrimaryChromaticities", 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag("JPEGInterchangeFormat", 513, 4), new ExifTag("JPEGInterchangeFormatLength", 514, 4), new ExifTag("YCbCrCoefficients", 529, 5), new ExifTag("YCbCrSubSampling", 530, 3), new ExifTag("YCbCrPositioning", 531, 3), new ExifTag("ReferenceBlackWhite", 532, 5), new ExifTag("Copyright", 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("SensorTopBorder", 4, 4), new ExifTag("SensorLeftBorder", 5, 4), new ExifTag("SensorBottomBorder", 6, 4), new ExifTag("SensorRightBorder", 7, 4), new ExifTag("ISO", 23, 3), new ExifTag("JpgFromRaw", 46, 7), new ExifTag("Xmp", 700, 1)};
        EXIF_TAGS = new ExifTag[][]{exifTagArr, new ExifTag[]{new ExifTag("ExposureTime", 33434, 5), new ExifTag("FNumber", 33437, 5), new ExifTag("ExposureProgram", 34850, 3), new ExifTag("SpectralSensitivity", 34852, 2), new ExifTag("PhotographicSensitivity", 34855, 3), new ExifTag("OECF", 34856, 7), new ExifTag("SensitivityType", 34864, 3), new ExifTag("StandardOutputSensitivity", 34865, 4), new ExifTag("RecommendedExposureIndex", 34866, 4), new ExifTag("ISOSpeed", 34867, 4), new ExifTag("ISOSpeedLatitudeyyy", 34868, 4), new ExifTag("ISOSpeedLatitudezzz", 34869, 4), new ExifTag("ExifVersion", 36864, 2), new ExifTag("DateTimeOriginal", 36867, 2), new ExifTag("DateTimeDigitized", 36868, 2), new ExifTag("OffsetTime", 36880, 2), new ExifTag("OffsetTimeOriginal", 36881, 2), new ExifTag("OffsetTimeDigitized", 36882, 2), new ExifTag("ComponentsConfiguration", 37121, 7), new ExifTag("CompressedBitsPerPixel", 37122, 5), new ExifTag("ShutterSpeedValue", 37377, 10), new ExifTag("ApertureValue", 37378, 5), new ExifTag("BrightnessValue", 37379, 10), new ExifTag("ExposureBiasValue", 37380, 10), new ExifTag("MaxApertureValue", 37381, 5), new ExifTag("SubjectDistance", 37382, 5), new ExifTag("MeteringMode", 37383, 3), new ExifTag("LightSource", 37384, 3), new ExifTag("Flash", 37385, 3), new ExifTag("FocalLength", 37386, 5), new ExifTag("SubjectArea", 37396, 3), new ExifTag("MakerNote", 37500, 7), new ExifTag("UserComment", 37510, 7), new ExifTag("SubSecTime", 37520, 2), new ExifTag("SubSecTimeOriginal", 37521, 2), new ExifTag("SubSecTimeDigitized", 37522, 2), new ExifTag("FlashpixVersion", 40960, 7), new ExifTag("ColorSpace", 40961, 3), new ExifTag("PixelXDimension", 40962, 3, 4), new ExifTag("PixelYDimension", 40963, 3, 4), new ExifTag("RelatedSoundFile", 40964, 2), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag("FlashEnergy", 41483, 5), new ExifTag("SpatialFrequencyResponse", 41484, 7), new ExifTag("FocalPlaneXResolution", 41486, 5), new ExifTag("FocalPlaneYResolution", 41487, 5), new ExifTag("FocalPlaneResolutionUnit", 41488, 3), new ExifTag("SubjectLocation", 41492, 3), new ExifTag("ExposureIndex", 41493, 5), new ExifTag("SensingMethod", 41495, 3), new ExifTag("FileSource", 41728, 7), new ExifTag("SceneType", 41729, 7), new ExifTag("CFAPattern", 41730, 7), new ExifTag("CustomRendered", 41985, 3), new ExifTag("ExposureMode", 41986, 3), new ExifTag("WhiteBalance", 41987, 3), new ExifTag("DigitalZoomRatio", 41988, 5), new ExifTag("FocalLengthIn35mmFilm", 41989, 3), new ExifTag("SceneCaptureType", 41990, 3), new ExifTag("GainControl", 41991, 3), new ExifTag("Contrast", 41992, 3), new ExifTag("Saturation", 41993, 3), new ExifTag("Sharpness", 41994, 3), new ExifTag("DeviceSettingDescription", 41995, 7), new ExifTag("SubjectDistanceRange", 41996, 3), new ExifTag("ImageUniqueID", 42016, 2), new ExifTag("CameraOwnerName", 42032, 2), new ExifTag("BodySerialNumber", 42033, 2), new ExifTag("LensSpecification", 42034, 5), new ExifTag("LensMake", 42035, 2), new ExifTag("LensModel", 42036, 2), new ExifTag("Gamma", 42240, 5), new ExifTag("DNGVersion", 50706, 1), new ExifTag("DefaultCropSize", 50720, 3, 4)}, new ExifTag[]{new ExifTag("GPSVersionID", 0, 1), new ExifTag("GPSLatitudeRef", 1, 2), new ExifTag("GPSLatitude", 2, 5, 10), new ExifTag("GPSLongitudeRef", 3, 2), new ExifTag("GPSLongitude", 4, 5, 10), new ExifTag("GPSAltitudeRef", 5, 1), new ExifTag("GPSAltitude", 6, 5), new ExifTag("GPSTimeStamp", 7, 5), new ExifTag("GPSSatellites", 8, 2), new ExifTag("GPSStatus", 9, 2), new ExifTag("GPSMeasureMode", 10, 2), new ExifTag("GPSDOP", 11, 5), new ExifTag("GPSSpeedRef", 12, 2), new ExifTag("GPSSpeed", 13, 5), new ExifTag("GPSTrackRef", 14, 2), new ExifTag("GPSTrack", 15, 5), new ExifTag("GPSImgDirectionRef", 16, 2), new ExifTag("GPSImgDirection", 17, 5), new ExifTag("GPSMapDatum", 18, 2), new ExifTag("GPSDestLatitudeRef", 19, 2), new ExifTag("GPSDestLatitude", 20, 5), new ExifTag("GPSDestLongitudeRef", 21, 2), new ExifTag("GPSDestLongitude", 22, 5), new ExifTag("GPSDestBearingRef", 23, 2), new ExifTag("GPSDestBearing", 24, 5), new ExifTag("GPSDestDistanceRef", 25, 2), new ExifTag("GPSDestDistance", 26, 5), new ExifTag("GPSProcessingMethod", 27, 7), new ExifTag("GPSAreaInformation", 28, 7), new ExifTag("GPSDateStamp", 29, 2), new ExifTag("GPSDifferential", 30, 3), new ExifTag("GPSHPositioningError", 31, 5)}, new ExifTag[]{new ExifTag("InteroperabilityIndex", 1, 2)}, new ExifTag[]{new ExifTag("NewSubfileType", 254, 4), new ExifTag("SubfileType", 255, 4), new ExifTag("ThumbnailImageWidth", 256, 3, 4), new ExifTag("ThumbnailImageLength", 257, 3, 4), new ExifTag("BitsPerSample", 258, 3), new ExifTag("Compression", 259, 3), new ExifTag("PhotometricInterpretation", 262, 3), new ExifTag("ImageDescription", 270, 2), new ExifTag("Make", 271, 2), new ExifTag("Model", 272, 2), new ExifTag("StripOffsets", 273, 3, 4), new ExifTag("ThumbnailOrientation", 274, 3), new ExifTag("SamplesPerPixel", 277, 3), new ExifTag("RowsPerStrip", 278, 3, 4), new ExifTag("StripByteCounts", 279, 3, 4), new ExifTag("XResolution", 282, 5), new ExifTag("YResolution", 283, 5), new ExifTag("PlanarConfiguration", 284, 3), new ExifTag("ResolutionUnit", 296, 3), new ExifTag("TransferFunction", 301, 3), new ExifTag("Software", 305, 2), new ExifTag("DateTime", 306, 2), new ExifTag("Artist", 315, 2), new ExifTag("WhitePoint", 318, 5), new ExifTag("PrimaryChromaticities", 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag("JPEGInterchangeFormat", 513, 4), new ExifTag("JPEGInterchangeFormatLength", 514, 4), new ExifTag("YCbCrCoefficients", 529, 5), new ExifTag("YCbCrSubSampling", 530, 3), new ExifTag("YCbCrPositioning", 531, 3), new ExifTag("ReferenceBlackWhite", 532, 5), new ExifTag("Xmp", 700, 1), new ExifTag("Copyright", 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("DNGVersion", 50706, 1), new ExifTag("DefaultCropSize", 50720, 3, 4)}, exifTagArr, new ExifTag[]{new ExifTag("ThumbnailImage", 256, 7), new ExifTag("CameraSettingsIFDPointer", 8224, 4), new ExifTag("ImageProcessingIFDPointer", 8256, 4)}, new ExifTag[]{new ExifTag("PreviewImageStart", 257, 4), new ExifTag("PreviewImageLength", 258, 4)}, new ExifTag[]{new ExifTag("AspectFrame", 4371, 3)}, new ExifTag[]{new ExifTag("ColorSpace", 55, 3)}};
        Charset forName = Charset.forName("US-ASCII");
        ASCII = forName;
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(forName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(forName);
        Locale locale = Locale.US;
        new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).setTimeZone(TimeZone.getTimeZone("UTC"));
        int i = 0;
        while (true) {
            ExifTag[][] exifTagArr2 = EXIF_TAGS;
            if (i < exifTagArr2.length) {
                sExifTagMapsForReading[i] = new HashMap<>();
                sExifTagMapsForWriting[i] = new HashMap<>();
                for (ExifTag exifTag : exifTagArr2[i]) {
                    sExifTagMapsForReading[i].put(Integer.valueOf(exifTag.number), exifTag);
                    sExifTagMapsForWriting[i].put(exifTag.name, exifTag);
                }
                i++;
            } else {
                HashMap<Integer, Integer> hashMap = sExifPointerTagMap;
                ExifTag[] exifTagArr3 = EXIF_POINTER_TAGS;
                hashMap.put(Integer.valueOf(exifTagArr3[0].number), 5);
                hashMap.put(Integer.valueOf(exifTagArr3[1].number), 1);
                hashMap.put(Integer.valueOf(exifTagArr3[2].number), 2);
                hashMap.put(Integer.valueOf(exifTagArr3[3].number), 3);
                hashMap.put(Integer.valueOf(exifTagArr3[4].number), 7);
                hashMap.put(Integer.valueOf(exifTagArr3[5].number), 8);
                Pattern.compile(".*[1-9].*");
                return;
            }
        }
    }

    public static void copyChunksUpToGivenChunkType(ByteOrderedDataInputStream byteOrderedDataInputStream, ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr, byte[] bArr2) throws IOException {
        String str;
        while (true) {
            byte[] bArr3 = new byte[4];
            if (byteOrderedDataInputStream.read(bArr3) != 4) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Encountered invalid length while copying WebP chunks up tochunk type ");
                Charset charset = ASCII;
                m.append(new String(bArr, charset));
                if (bArr2 == null) {
                    str = "";
                } else {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" or ");
                    m2.append(new String(bArr2, charset));
                    str = m2.toString();
                }
                m.append(str);
                throw new IOException(m.toString());
            }
            int readInt = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.write(bArr3);
            byteOrderedDataOutputStream.writeInt(readInt);
            if (readInt % 2 == 1) {
                readInt++;
            }
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt);
            if (Arrays.equals(bArr3, bArr)) {
                return;
            }
            if (bArr2 != null && Arrays.equals(bArr3, bArr2)) {
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0035 A[Catch:{ IOException | UnsupportedOperationException -> 0x0062, all -> 0x0060 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0064 A[Catch:{ IOException | UnsupportedOperationException -> 0x0062, all -> 0x0060 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void loadAttributes(java.io.FileInputStream r7) {
        /*
            r6 = this;
            r0 = 0
            r1 = r0
        L_0x0002:
            androidx.exifinterface.media.ExifInterface$ExifTag[][] r2 = EXIF_TAGS     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            int r2 = r2.length     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            if (r1 >= r2) goto L_0x0013
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r6.mAttributes     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r3.<init>()     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r2[r1] = r3     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            int r1 = r1 + 1
            goto L_0x0002
        L_0x0013:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r2 = 5000(0x1388, float:7.006E-42)
            r1.<init>(r7, r2)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            int r7 = r6.getMimeType(r1)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r6.mMimeType = r7     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r2 = 14
            r3 = 13
            r4 = 9
            r5 = 4
            if (r7 == r5) goto L_0x0032
            if (r7 == r4) goto L_0x0032
            if (r7 == r3) goto L_0x0032
            if (r7 != r2) goto L_0x0030
            goto L_0x0032
        L_0x0030:
            r7 = 1
            goto L_0x0033
        L_0x0032:
            r7 = r0
        L_0x0033:
            if (r7 == 0) goto L_0x0064
            androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream r7 = new androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r7.<init>((java.io.BufferedInputStream) r1)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            int r0 = r6.mMimeType     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r1 = 12
            if (r0 != r1) goto L_0x0044
            r6.getHeifAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0056
        L_0x0044:
            r1 = 7
            if (r0 != r1) goto L_0x004b
            r6.getOrfAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0056
        L_0x004b:
            r1 = 10
            if (r0 != r1) goto L_0x0053
            r6.getRw2Attributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0056
        L_0x0053:
            r6.getRawAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
        L_0x0056:
            int r0 = r6.mOffsetToExifData     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            long r0 = (long) r0     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r7.seek(r0)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r6.setThumbnailData(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0082
        L_0x0060:
            r7 = move-exception
            goto L_0x009e
        L_0x0062:
            r7 = move-exception
            goto L_0x008a
        L_0x0064:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r7 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            r7.<init>((java.io.BufferedInputStream) r1)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            int r1 = r6.mMimeType     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            if (r1 != r5) goto L_0x0071
            r6.getJpegAttributes(r7, r0, r0)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0082
        L_0x0071:
            if (r1 != r3) goto L_0x0077
            r6.getPngAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0082
        L_0x0077:
            if (r1 != r4) goto L_0x007d
            r6.getRafAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
            goto L_0x0082
        L_0x007d:
            if (r1 != r2) goto L_0x0082
            r6.getWebpAttributes(r7)     // Catch:{ IOException | UnsupportedOperationException -> 0x0062 }
        L_0x0082:
            r6.addDefaultValuesForCompatibility()
            boolean r7 = DEBUG
            if (r7 == 0) goto L_0x009d
            goto L_0x009a
        L_0x008a:
            boolean r0 = DEBUG     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x0095
            java.lang.String r1 = "ExifInterface"
            java.lang.String r2 = "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface."
            android.util.Log.w(r1, r2, r7)     // Catch:{ all -> 0x0060 }
        L_0x0095:
            r6.addDefaultValuesForCompatibility()
            if (r0 == 0) goto L_0x009d
        L_0x009a:
            r6.printAttributes()
        L_0x009d:
            return
        L_0x009e:
            r6.addDefaultValuesForCompatibility()
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x00a8
            r6.printAttributes()
        L_0x00a8:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.loadAttributes(java.io.FileInputStream):void");
    }

    public final void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("The size of tag group[", i, "]: ");
            m.append(this.mAttributes[i].size());
            Log.d("ExifInterface", m.toString());
            for (Map.Entry next : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) next.getValue();
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("tagName: ");
                m2.append((String) next.getKey());
                m2.append(", tagType: ");
                m2.append(exifAttribute.toString());
                m2.append(", tagValue: '");
                m2.append(exifAttribute.getStringValue(this.mExifByteOrder));
                m2.append("'");
                Log.d("ExifInterface", m2.toString());
            }
        }
    }

    public final void removeAttribute(String str) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(str);
        }
    }

    public final void validateImages() throws IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = this.mAttributes[1].get("PixelXDimension");
        ExifAttribute exifAttribute2 = this.mAttributes[1].get("PixelYDimension");
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            this.mAttributes[0].put("ImageWidth", exifAttribute);
            this.mAttributes[0].put("ImageLength", exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap<String, ExifAttribute>[] hashMapArr = this.mAttributes;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap<>();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d("ExifInterface", "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, "ThumbnailOrientation", "Orientation");
        replaceInvalidTags(0, "ThumbnailImageLength", "ImageLength");
        replaceInvalidTags(0, "ThumbnailImageWidth", "ImageWidth");
        replaceInvalidTags(5, "ThumbnailOrientation", "Orientation");
        replaceInvalidTags(5, "ThumbnailImageLength", "ImageLength");
        replaceInvalidTags(5, "ThumbnailImageWidth", "ImageWidth");
        replaceInvalidTags(4, "Orientation", "ThumbnailOrientation");
        replaceInvalidTags(4, "ImageLength", "ThumbnailImageLength");
        replaceInvalidTags(4, "ImageWidth", "ThumbnailImageWidth");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:68|69|70) */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        java.lang.Double.parseDouble(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x015c, code lost:
        return new android.util.Pair<>(12, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0162, code lost:
        return new android.util.Pair<>(2, -1);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x014e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat(java.lang.String r12) {
        /*
            java.lang.String r0 = ","
            boolean r1 = r12.contains(r0)
            r2 = 0
            r3 = 1
            r4 = 2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r6 = -1
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
            if (r1 == 0) goto L_0x00a6
            java.lang.String[] r12 = r12.split(r0, r6)
            r0 = r12[r2]
            android.util.Pair r0 = guessDataFormat(r0)
            java.lang.Object r1 = r0.first
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r4) goto L_0x0029
            return r0
        L_0x0029:
            int r1 = r12.length
            if (r3 >= r1) goto L_0x00a5
            r1 = r12[r3]
            android.util.Pair r1 = guessDataFormat(r1)
            java.lang.Object r2 = r1.first
            java.lang.Integer r2 = (java.lang.Integer) r2
            java.lang.Object r4 = r0.first
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x004d
            java.lang.Object r2 = r1.second
            java.lang.Integer r2 = (java.lang.Integer) r2
            java.lang.Object r4 = r0.first
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x004b
            goto L_0x004d
        L_0x004b:
            r2 = r6
            goto L_0x0055
        L_0x004d:
            java.lang.Object r2 = r0.first
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
        L_0x0055:
            java.lang.Object r4 = r0.second
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 == r6) goto L_0x0080
            java.lang.Object r4 = r1.first
            java.lang.Integer r4 = (java.lang.Integer) r4
            java.lang.Object r8 = r0.second
            boolean r4 = r4.equals(r8)
            if (r4 != 0) goto L_0x0077
            java.lang.Object r1 = r1.second
            java.lang.Integer r1 = (java.lang.Integer) r1
            java.lang.Object r4 = r0.second
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0080
        L_0x0077:
            java.lang.Object r1 = r0.second
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            goto L_0x0081
        L_0x0080:
            r1 = r6
        L_0x0081:
            if (r2 != r6) goto L_0x008b
            if (r1 != r6) goto L_0x008b
            android.util.Pair r12 = new android.util.Pair
            r12.<init>(r5, r7)
            return r12
        L_0x008b:
            if (r2 != r6) goto L_0x0097
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0.<init>(r1, r7)
            goto L_0x00a2
        L_0x0097:
            if (r1 != r6) goto L_0x00a2
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
            r0.<init>(r1, r7)
        L_0x00a2:
            int r3 = r3 + 1
            goto L_0x0029
        L_0x00a5:
            return r0
        L_0x00a6:
            java.lang.String r0 = "/"
            boolean r1 = r12.contains(r0)
            r8 = 0
            if (r1 == 0) goto L_0x0105
            java.lang.String[] r12 = r12.split(r0, r6)
            int r0 = r12.length
            if (r0 != r4) goto L_0x00ff
            r0 = r12[r2]     // Catch:{ NumberFormatException -> 0x00ff }
            double r0 = java.lang.Double.parseDouble(r0)     // Catch:{ NumberFormatException -> 0x00ff }
            long r0 = (long) r0     // Catch:{ NumberFormatException -> 0x00ff }
            r12 = r12[r3]     // Catch:{ NumberFormatException -> 0x00ff }
            double r2 = java.lang.Double.parseDouble(r12)     // Catch:{ NumberFormatException -> 0x00ff }
            long r2 = (long) r2     // Catch:{ NumberFormatException -> 0x00ff }
            int r12 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            r4 = 10
            if (r12 < 0) goto L_0x00f5
            int r12 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r12 >= 0) goto L_0x00d0
            goto L_0x00f5
        L_0x00d0:
            r8 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r12 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            r0 = 5
            if (r12 > 0) goto L_0x00eb
            int r12 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r12 <= 0) goto L_0x00dd
            goto L_0x00eb
        L_0x00dd:
            android.util.Pair r12 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x00ff }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x00ff }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x00ff }
            r12.<init>(r1, r0)     // Catch:{ NumberFormatException -> 0x00ff }
            return r12
        L_0x00eb:
            android.util.Pair r12 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x00ff }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x00ff }
            r12.<init>(r0, r7)     // Catch:{ NumberFormatException -> 0x00ff }
            return r12
        L_0x00f5:
            android.util.Pair r12 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x00ff }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x00ff }
            r12.<init>(r0, r7)     // Catch:{ NumberFormatException -> 0x00ff }
            return r12
        L_0x00ff:
            android.util.Pair r12 = new android.util.Pair
            r12.<init>(r5, r7)
            return r12
        L_0x0105:
            long r0 = java.lang.Long.parseLong(r12)     // Catch:{ NumberFormatException -> 0x014e }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ NumberFormatException -> 0x014e }
            long r1 = r0.longValue()     // Catch:{ NumberFormatException -> 0x014e }
            int r1 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            r2 = 4
            if (r1 < 0) goto L_0x0130
            long r3 = r0.longValue()     // Catch:{ NumberFormatException -> 0x014e }
            r10 = 65535(0xffff, double:3.23786E-319)
            int r1 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r1 > 0) goto L_0x0130
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x014e }
            r1 = 3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x014e }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x014e }
            r0.<init>(r1, r2)     // Catch:{ NumberFormatException -> 0x014e }
            return r0
        L_0x0130:
            long r0 = r0.longValue()     // Catch:{ NumberFormatException -> 0x014e }
            int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r0 >= 0) goto L_0x0144
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x014e }
            r1 = 9
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x014e }
            r0.<init>(r1, r7)     // Catch:{ NumberFormatException -> 0x014e }
            return r0
        L_0x0144:
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x014e }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x014e }
            r0.<init>(r1, r7)     // Catch:{ NumberFormatException -> 0x014e }
            return r0
        L_0x014e:
            java.lang.Double.parseDouble(r12)     // Catch:{ NumberFormatException -> 0x015d }
            android.util.Pair r12 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x015d }
            r0 = 12
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x015d }
            r12.<init>(r0, r7)     // Catch:{ NumberFormatException -> 0x015d }
            return r12
        L_0x015d:
            android.util.Pair r12 = new android.util.Pair
            r12.<init>(r5, r7)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.guessDataFormat(java.lang.String):android.util.Pair");
    }

    public final void addDefaultValuesForCompatibility() {
        String attribute = getAttribute("DateTimeOriginal");
        if (attribute != null && getAttribute("DateTime") == null) {
            this.mAttributes[0].put("DateTime", ExifAttribute.createString(attribute));
        }
        if (getAttribute("ImageWidth") == null) {
            this.mAttributes[0].put("ImageWidth", ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute("ImageLength") == null) {
            this.mAttributes[0].put("ImageLength", ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute("Orientation") == null) {
            this.mAttributes[0].put("Orientation", ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute("LightSource") == null) {
            this.mAttributes[1].put("LightSource", ExifAttribute.createULong(0, this.mExifByteOrder));
        }
    }

    public final String getAttribute(String str) {
        String str2;
        ExifAttribute exifAttribute;
        if ("ISOSpeedRatings".equals(str)) {
            if (DEBUG) {
                Log.d("ExifInterface", "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str2 = "PhotographicSensitivity";
        } else {
            str2 = str;
        }
        int i = 0;
        while (true) {
            if (i >= EXIF_TAGS.length) {
                exifAttribute = null;
                break;
            }
            exifAttribute = this.mAttributes[i].get(str2);
            if (exifAttribute != null) {
                break;
            }
            i++;
        }
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            if (str.equals("GPSTimeStamp")) {
                int i2 = exifAttribute.format;
                if (i2 == 5 || i2 == 10) {
                    Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                    if (rationalArr == null || rationalArr.length != 3) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid GPS Timestamp array. array=");
                        m.append(Arrays.toString(rationalArr));
                        Log.w("ExifInterface", m.toString());
                        return null;
                    }
                    return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (((float) rationalArr[0].numerator) / ((float) rationalArr[0].denominator))), Integer.valueOf((int) (((float) rationalArr[1].numerator) / ((float) rationalArr[1].denominator))), Integer.valueOf((int) (((float) rationalArr[2].numerator) / ((float) rationalArr[2].denominator)))});
                }
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("GPS Timestamp format is not rational. format=");
                m2.append(exifAttribute.format);
                Log.w("ExifInterface", m2.toString());
                return null;
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void getJpegAttributes(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r18, int r19, int r20) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r20
            boolean r3 = DEBUG
            java.lang.String r4 = "ExifInterface"
            if (r3 == 0) goto L_0x0020
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "getJpegAttributes starting with: "
            r3.append(r5)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r4, r3)
        L_0x0020:
            java.nio.ByteOrder r3 = java.nio.ByteOrder.BIG_ENDIAN
            r1.mByteOrder = r3
            byte r3 = r18.readByte()
            java.lang.String r5 = "Invalid marker: "
            r6 = -1
            if (r3 != r6) goto L_0x01dd
            byte r7 = r18.readByte()
            r8 = -40
            if (r7 != r8) goto L_0x01c6
            r3 = 2
            r5 = r3
        L_0x0037:
            byte r7 = r18.readByte()
            if (r7 != r6) goto L_0x01ad
            r6 = 1
            int r3 = r3 + r6
            byte r7 = r18.readByte()
            boolean r8 = DEBUG
            if (r8 == 0) goto L_0x005d
            java.lang.String r9 = "Found JPEG segment indicator: "
            java.lang.StringBuilder r9 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
            r10 = r7 & 255(0xff, float:3.57E-43)
            java.lang.String r10 = java.lang.Integer.toHexString(r10)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r4, r9)
        L_0x005d:
            int r3 = r3 + r6
            r9 = -39
            if (r7 == r9) goto L_0x01a8
            r9 = -38
            if (r7 != r9) goto L_0x0068
            goto L_0x01a8
        L_0x0068:
            int r9 = r18.readUnsignedShort()
            int r9 = r9 - r5
            int r3 = r3 + r5
            if (r8 == 0) goto L_0x0095
            java.lang.String r5 = "JPEG segment: "
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            r8 = r7 & 255(0xff, float:3.57E-43)
            java.lang.String r8 = java.lang.Integer.toHexString(r8)
            r5.append(r8)
            java.lang.String r8 = " (length: "
            r5.append(r8)
            int r8 = r9 + 2
            r5.append(r8)
            java.lang.String r8 = ")"
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x0095:
            java.lang.String r5 = "Invalid length"
            if (r9 < 0) goto L_0x01a2
            r8 = -31
            r10 = 0
            if (r7 == r8) goto L_0x0115
            r8 = -2
            if (r7 == r8) goto L_0x00e9
            switch(r7) {
                case -64: goto L_0x00ae;
                case -63: goto L_0x00ae;
                case -62: goto L_0x00ae;
                case -61: goto L_0x00ae;
                default: goto L_0x00a4;
            }
        L_0x00a4:
            switch(r7) {
                case -59: goto L_0x00ae;
                case -58: goto L_0x00ae;
                case -57: goto L_0x00ae;
                default: goto L_0x00a7;
            }
        L_0x00a7:
            switch(r7) {
                case -55: goto L_0x00ae;
                case -54: goto L_0x00ae;
                case -53: goto L_0x00ae;
                default: goto L_0x00aa;
            }
        L_0x00aa:
            switch(r7) {
                case -51: goto L_0x00ae;
                case -50: goto L_0x00ae;
                case -49: goto L_0x00ae;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            goto L_0x00e6
        L_0x00ae:
            r1.skipFully(r6)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r6 = r0.mAttributes
            r6 = r6[r2]
            r7 = 4
            if (r2 == r7) goto L_0x00bb
            java.lang.String r8 = "ImageLength"
            goto L_0x00bd
        L_0x00bb:
            java.lang.String r8 = "ThumbnailImageLength"
        L_0x00bd:
            int r10 = r18.readUnsignedShort()
            long r10 = (long) r10
            java.nio.ByteOrder r12 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r10 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r10, (java.nio.ByteOrder) r12)
            r6.put(r8, r10)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r6 = r0.mAttributes
            r6 = r6[r2]
            if (r2 == r7) goto L_0x00d4
            java.lang.String r7 = "ImageWidth"
            goto L_0x00d6
        L_0x00d4:
            java.lang.String r7 = "ThumbnailImageWidth"
        L_0x00d6:
            int r8 = r18.readUnsignedShort()
            long r10 = (long) r8
            java.nio.ByteOrder r8 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r8 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r10, (java.nio.ByteOrder) r8)
            r6.put(r7, r8)
            int r9 = r9 + -5
        L_0x00e6:
            r10 = r9
            goto L_0x0190
        L_0x00e9:
            byte[] r7 = new byte[r9]
            int r8 = r1.read(r7)
            if (r8 != r9) goto L_0x010d
            java.lang.String r8 = "UserComment"
            java.lang.String r9 = r0.getAttribute(r8)
            if (r9 != 0) goto L_0x0190
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r9 = r0.mAttributes
            r6 = r9[r6]
            java.lang.String r9 = new java.lang.String
            java.nio.charset.Charset r11 = ASCII
            r9.<init>(r7, r11)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r7 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createString(r9)
            r6.put(r8, r7)
            goto L_0x0190
        L_0x010d:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Invalid exif"
            r0.<init>(r1)
            throw r0
        L_0x0115:
            byte[] r7 = new byte[r9]
            r1.readFully(r7)
            int r8 = r3 + r9
            byte[] r11 = IDENTIFIER_EXIF_APP1
            if (r11 != 0) goto L_0x0121
            goto L_0x012f
        L_0x0121:
            int r12 = r11.length
            if (r9 >= r12) goto L_0x0125
            goto L_0x012f
        L_0x0125:
            r12 = r10
        L_0x0126:
            int r13 = r11.length
            if (r12 >= r13) goto L_0x0134
            byte r13 = r7[r12]
            byte r14 = r11[r12]
            if (r13 == r14) goto L_0x0131
        L_0x012f:
            r12 = r10
            goto L_0x0135
        L_0x0131:
            int r12 = r12 + 1
            goto L_0x0126
        L_0x0134:
            r12 = r6
        L_0x0135:
            if (r12 == 0) goto L_0x014e
            int r6 = r11.length
            byte[] r6 = java.util.Arrays.copyOfRange(r7, r6, r9)
            int r3 = r19 + r3
            int r7 = r11.length
            int r3 = r3 + r7
            r0.mOffsetToExifData = r3
            r0.readExifSegment(r6, r2)
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r3 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream
            r3.<init>((byte[]) r6)
            r0.setThumbnailData(r3)
            goto L_0x018e
        L_0x014e:
            byte[] r11 = IDENTIFIER_XMP_APP1
            if (r11 != 0) goto L_0x0153
            goto L_0x0161
        L_0x0153:
            int r12 = r11.length
            if (r9 >= r12) goto L_0x0157
            goto L_0x0161
        L_0x0157:
            r12 = r10
        L_0x0158:
            int r13 = r11.length
            if (r12 >= r13) goto L_0x0166
            byte r13 = r7[r12]
            byte r14 = r11[r12]
            if (r13 == r14) goto L_0x0163
        L_0x0161:
            r6 = r10
            goto L_0x0166
        L_0x0163:
            int r12 = r12 + 1
            goto L_0x0158
        L_0x0166:
            if (r6 == 0) goto L_0x018e
            int r6 = r11.length
            int r3 = r3 + r6
            int r6 = r11.length
            byte[] r6 = java.util.Arrays.copyOfRange(r7, r6, r9)
            java.lang.String r7 = "Xmp"
            java.lang.String r9 = r0.getAttribute(r7)
            if (r9 != 0) goto L_0x018e
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r9 = r0.mAttributes
            r9 = r9[r10]
            androidx.exifinterface.media.ExifInterface$ExifAttribute r14 = new androidx.exifinterface.media.ExifInterface$ExifAttribute
            r12 = 1
            int r13 = r6.length
            long r2 = (long) r3
            r11 = r14
            r10 = r14
            r14 = r2
            r16 = r6
            r11.<init>(r12, r13, r14, r16)
            r9.put(r7, r10)
            r2 = 1
            r0.mXmpIsFromSeparateMarker = r2
        L_0x018e:
            r3 = r8
            r10 = 0
        L_0x0190:
            if (r10 < 0) goto L_0x019c
            r1.skipFully(r10)
            int r3 = r3 + r10
            r5 = 2
            r6 = -1
            r2 = r20
            goto L_0x0037
        L_0x019c:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r5)
            throw r0
        L_0x01a2:
            java.io.IOException r0 = new java.io.IOException
            r0.<init>(r5)
            throw r0
        L_0x01a8:
            java.nio.ByteOrder r0 = r0.mExifByteOrder
            r1.mByteOrder = r0
            return
        L_0x01ad:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Invalid marker:"
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            r2 = r7 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x01c6:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            r2 = r3 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x01dd:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            r2 = r3 & 255(0xff, float:3.57E-43)
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getJpegAttributes(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00cf, code lost:
        if (r8 == null) goto L_0x00d4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0143 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0146  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x018f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00c8 A[Catch:{ all -> 0x018a }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x010f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0111 A[SYNTHETIC, Splitter:B:99:0x0111] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getMimeType(java.io.BufferedInputStream r18) throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r2 = 5000(0x1388, float:7.006E-42)
            r0.mark(r2)
            byte[] r3 = new byte[r2]
            r0.read(r3)
            r18.reset()
            r4 = 0
            r0 = r4
        L_0x0013:
            byte[] r5 = JPEG_SIGNATURE
            int r6 = r5.length
            if (r0 >= r6) goto L_0x0023
            byte r6 = r3[r0]
            byte r5 = r5[r0]
            if (r6 == r5) goto L_0x0020
            r0 = r4
            goto L_0x0024
        L_0x0020:
            int r0 = r0 + 1
            goto L_0x0013
        L_0x0023:
            r0 = 1
        L_0x0024:
            r5 = 4
            if (r0 == 0) goto L_0x0028
            return r5
        L_0x0028:
            java.nio.charset.Charset r0 = java.nio.charset.Charset.defaultCharset()
            java.lang.String r6 = "FUJIFILMCCD-RAW"
            byte[] r0 = r6.getBytes(r0)
            r6 = r4
        L_0x0033:
            int r8 = r0.length
            if (r6 >= r8) goto L_0x0041
            byte r8 = r3[r6]
            byte r9 = r0[r6]
            if (r8 == r9) goto L_0x003e
            r0 = r4
            goto L_0x0042
        L_0x003e:
            int r6 = r6 + 1
            goto L_0x0033
        L_0x0041:
            r0 = 1
        L_0x0042:
            if (r0 == 0) goto L_0x0047
            r0 = 9
            return r0
        L_0x0047:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r8 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ Exception -> 0x00c2, all -> 0x00be }
            r8.<init>((byte[]) r3)     // Catch:{ Exception -> 0x00c2, all -> 0x00be }
            int r0 = r8.readInt()     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            long r9 = (long) r0     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            byte[] r0 = new byte[r5]     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            r8.read(r0)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            byte[] r11 = HEIF_TYPE_FTYP     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            boolean r0 = java.util.Arrays.equals(r0, r11)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r0 != 0) goto L_0x0060
            goto L_0x00d1
        L_0x0060:
            r11 = 1
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            r13 = 16
            r15 = 8
            if (r0 != 0) goto L_0x0074
            long r9 = r8.readLong()     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            int r0 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r0 >= 0) goto L_0x0075
            goto L_0x00d1
        L_0x0074:
            r13 = r15
        L_0x0075:
            long r6 = (long) r2     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            int r0 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x007b
            r9 = r6
        L_0x007b:
            long r9 = r9 - r13
            int r0 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r0 >= 0) goto L_0x0081
            goto L_0x00d1
        L_0x0081:
            byte[] r0 = new byte[r5]     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            r6 = 0
            r2 = r4
            r13 = r2
        L_0x0087:
            r14 = 4
            long r14 = r9 / r14
            int r14 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r14 >= 0) goto L_0x00d1
            int r14 = r8.read(r0)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r14 == r5) goto L_0x0096
            goto L_0x00d1
        L_0x0096:
            int r14 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r14 != 0) goto L_0x009b
            goto L_0x00b7
        L_0x009b:
            byte[] r14 = HEIF_BRAND_MIF1     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            boolean r14 = java.util.Arrays.equals(r0, r14)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r14 == 0) goto L_0x00a5
            r2 = 1
            goto L_0x00ae
        L_0x00a5:
            byte[] r14 = HEIF_BRAND_HEIC     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            boolean r14 = java.util.Arrays.equals(r0, r14)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r14 == 0) goto L_0x00ae
            r13 = 1
        L_0x00ae:
            if (r2 == 0) goto L_0x00b7
            if (r13 == 0) goto L_0x00b7
            r8.close()
            r0 = 1
            goto L_0x00d5
        L_0x00b7:
            long r6 = r6 + r11
            goto L_0x0087
        L_0x00b9:
            r0 = move-exception
            goto L_0x018d
        L_0x00bc:
            r0 = move-exception
            goto L_0x00c4
        L_0x00be:
            r0 = move-exception
            r6 = 0
            goto L_0x018c
        L_0x00c2:
            r0 = move-exception
            r8 = 0
        L_0x00c4:
            boolean r2 = DEBUG     // Catch:{ all -> 0x018a }
            if (r2 == 0) goto L_0x00cf
            java.lang.String r2 = "ExifInterface"
            java.lang.String r6 = "Exception parsing HEIF file type box."
            android.util.Log.d(r2, r6, r0)     // Catch:{ all -> 0x018a }
        L_0x00cf:
            if (r8 == 0) goto L_0x00d4
        L_0x00d1:
            r8.close()
        L_0x00d4:
            r0 = r4
        L_0x00d5:
            if (r0 == 0) goto L_0x00da
            r0 = 12
            return r0
        L_0x00da:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r2 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ Exception -> 0x0106, all -> 0x00fe }
            r2.<init>((byte[]) r3)     // Catch:{ Exception -> 0x0106, all -> 0x00fe }
            java.nio.ByteOrder r0 = readByteOrder(r2)     // Catch:{ Exception -> 0x0107, all -> 0x00fb }
            r1.mExifByteOrder = r0     // Catch:{ Exception -> 0x0107, all -> 0x00fb }
            r2.mByteOrder = r0     // Catch:{ Exception -> 0x0107, all -> 0x00fb }
            short r0 = r2.readShort()     // Catch:{ Exception -> 0x0107, all -> 0x00fb }
            r6 = 20306(0x4f52, float:2.8455E-41)
            if (r0 == r6) goto L_0x00f6
            r6 = 21330(0x5352, float:2.989E-41)
            if (r0 != r6) goto L_0x00f4
            goto L_0x00f6
        L_0x00f4:
            r0 = r4
            goto L_0x00f7
        L_0x00f6:
            r0 = 1
        L_0x00f7:
            r2.close()
            goto L_0x010d
        L_0x00fb:
            r0 = move-exception
            r6 = r2
            goto L_0x0100
        L_0x00fe:
            r0 = move-exception
            r6 = 0
        L_0x0100:
            if (r6 == 0) goto L_0x0105
            r6.close()
        L_0x0105:
            throw r0
        L_0x0106:
            r2 = 0
        L_0x0107:
            if (r2 == 0) goto L_0x010c
            r2.close()
        L_0x010c:
            r0 = r4
        L_0x010d:
            if (r0 == 0) goto L_0x0111
            r0 = 7
            return r0
        L_0x0111:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r2 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ Exception -> 0x013a, all -> 0x0132 }
            r2.<init>((byte[]) r3)     // Catch:{ Exception -> 0x013a, all -> 0x0132 }
            java.nio.ByteOrder r0 = readByteOrder(r2)     // Catch:{ Exception -> 0x0130, all -> 0x012d }
            r1.mExifByteOrder = r0     // Catch:{ Exception -> 0x0130, all -> 0x012d }
            r2.mByteOrder = r0     // Catch:{ Exception -> 0x0130, all -> 0x012d }
            short r0 = r2.readShort()     // Catch:{ Exception -> 0x0130, all -> 0x012d }
            r1 = 85
            if (r0 != r1) goto L_0x0128
            r0 = 1
            goto L_0x0129
        L_0x0128:
            r0 = r4
        L_0x0129:
            r2.close()
            goto L_0x0141
        L_0x012d:
            r0 = move-exception
            r6 = r2
            goto L_0x0134
        L_0x0130:
            r6 = r2
            goto L_0x013b
        L_0x0132:
            r0 = move-exception
            r6 = 0
        L_0x0134:
            if (r6 == 0) goto L_0x0139
            r6.close()
        L_0x0139:
            throw r0
        L_0x013a:
            r6 = 0
        L_0x013b:
            if (r6 == 0) goto L_0x0140
            r6.close()
        L_0x0140:
            r0 = r4
        L_0x0141:
            if (r0 == 0) goto L_0x0146
            r0 = 10
            return r0
        L_0x0146:
            r0 = r4
        L_0x0147:
            byte[] r1 = PNG_SIGNATURE
            int r2 = r1.length
            if (r0 >= r2) goto L_0x0157
            byte r2 = r3[r0]
            byte r1 = r1[r0]
            if (r2 == r1) goto L_0x0154
            r0 = r4
            goto L_0x0158
        L_0x0154:
            int r0 = r0 + 1
            goto L_0x0147
        L_0x0157:
            r0 = 1
        L_0x0158:
            if (r0 == 0) goto L_0x015d
            r0 = 13
            return r0
        L_0x015d:
            r0 = r4
        L_0x015e:
            byte[] r1 = WEBP_SIGNATURE_1
            int r2 = r1.length
            if (r0 >= r2) goto L_0x016d
            byte r2 = r3[r0]
            byte r1 = r1[r0]
            if (r2 == r1) goto L_0x016a
            goto L_0x017e
        L_0x016a:
            int r0 = r0 + 1
            goto L_0x015e
        L_0x016d:
            r0 = r4
        L_0x016e:
            byte[] r1 = WEBP_SIGNATURE_2
            int r2 = r1.length
            if (r0 >= r2) goto L_0x0183
            byte[] r2 = WEBP_SIGNATURE_1
            int r2 = r2.length
            int r2 = r2 + r0
            int r2 = r2 + r5
            byte r2 = r3[r2]
            byte r1 = r1[r0]
            if (r2 == r1) goto L_0x0180
        L_0x017e:
            r7 = r4
            goto L_0x0184
        L_0x0180:
            int r0 = r0 + 1
            goto L_0x016e
        L_0x0183:
            r7 = 1
        L_0x0184:
            if (r7 == 0) goto L_0x0189
            r0 = 14
            return r0
        L_0x0189:
            return r4
        L_0x018a:
            r0 = move-exception
            r6 = r8
        L_0x018c:
            r8 = r6
        L_0x018d:
            if (r8 == 0) goto L_0x0192
            r8.close()
        L_0x0192:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getMimeType(java.io.BufferedInputStream):int");
    }

    public final void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        if (DEBUG) {
            Log.d("ExifInterface", "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        byte[] bArr = PNG_SIGNATURE;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 0;
        while (true) {
            try {
                int readInt = byteOrderedDataInputStream.readInt();
                int i = length + 4;
                byte[] bArr2 = new byte[4];
                if (byteOrderedDataInputStream.read(bArr2) == 4) {
                    int i2 = i + 4;
                    if (i2 == 16) {
                        if (!Arrays.equals(bArr2, PNG_CHUNK_TYPE_IHDR)) {
                            throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                        }
                    }
                    if (!Arrays.equals(bArr2, PNG_CHUNK_TYPE_IEND)) {
                        if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_EXIF)) {
                            byte[] bArr3 = new byte[readInt];
                            if (byteOrderedDataInputStream.read(bArr3) == readInt) {
                                int readInt2 = byteOrderedDataInputStream.readInt();
                                CRC32 crc32 = new CRC32();
                                crc32.update(bArr2);
                                crc32.update(bArr3);
                                if (((int) crc32.getValue()) == readInt2) {
                                    this.mOffsetToExifData = i2;
                                    readExifSegment(bArr3, 0);
                                    validateImages();
                                    setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                                    return;
                                }
                                throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt2 + ", calculated CRC value: " + crc32.getValue());
                            }
                            throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(bArr2));
                        }
                        int i3 = readInt + 4;
                        byteOrderedDataInputStream.skipFully(i3);
                        length = i2 + i3;
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing PNG chunktype");
                }
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    public final void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        boolean z = DEBUG;
        if (z) {
            Log.d("ExifInterface", "getRafAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.skipFully(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[4];
        byteOrderedDataInputStream.read(bArr);
        byteOrderedDataInputStream.read(bArr2);
        byteOrderedDataInputStream.read(bArr3);
        int i = ByteBuffer.wrap(bArr).getInt();
        int i2 = ByteBuffer.wrap(bArr2).getInt();
        int i3 = ByteBuffer.wrap(bArr3).getInt();
        byte[] bArr4 = new byte[i2];
        byteOrderedDataInputStream.skipFully(i - byteOrderedDataInputStream.mPosition);
        byteOrderedDataInputStream.read(bArr4);
        getJpegAttributes(new ByteOrderedDataInputStream(bArr4), i, 5);
        byteOrderedDataInputStream.skipFully(i3 - byteOrderedDataInputStream.mPosition);
        byteOrderedDataInputStream.mByteOrder = ByteOrder.BIG_ENDIAN;
        int readInt = byteOrderedDataInputStream.readInt();
        if (z) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("numberOfDirectoryEntry: ", readInt, "ExifInterface");
        }
        for (int i4 = 0; i4 < readInt; i4++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                ExifAttribute createUShort = ExifAttribute.createUShort((int) readShort, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort((int) readShort2, this.mExifByteOrder);
                this.mAttributes[0].put("ImageLength", createUShort);
                this.mAttributes[0].put("ImageWidth", createUShort2);
                if (DEBUG) {
                    Log.d("ExifInterface", "Updated to length: " + readShort + ", width: " + readShort2);
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipFully(readUnsignedShort2);
        }
    }

    public final void getRw2Attributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        if (DEBUG) {
            Log.d("ExifInterface", "getRw2Attributes starting with: " + seekableByteOrderedDataInputStream);
        }
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = this.mAttributes[0].get("JpgFromRaw");
        if (exifAttribute != null) {
            getJpegAttributes(new ByteOrderedDataInputStream(exifAttribute.bytes), (int) exifAttribute.bytesOffset, 5);
        }
        ExifAttribute exifAttribute2 = this.mAttributes[0].get("ISO");
        ExifAttribute exifAttribute3 = this.mAttributes[1].get("PhotographicSensitivity");
        if (exifAttribute2 != null && exifAttribute3 == null) {
            this.mAttributes[1].put("PhotographicSensitivity", exifAttribute2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] getThumbnailBytes() {
        /*
            r7 = this;
            boolean r0 = r7.mHasThumbnail
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            byte[] r0 = r7.mThumbnailBytes
            if (r0 == 0) goto L_0x000b
            return r0
        L_0x000b:
            java.io.FileDescriptor r0 = r7.mSeekableFileDescriptor     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            java.io.FileDescriptor r0 = android.system.Os.dup(r0)     // Catch:{ Exception -> 0x0060, all -> 0x005d }
            r2 = 0
            int r4 = android.system.OsConstants.SEEK_SET     // Catch:{ Exception -> 0x005a, all -> 0x0058 }
            android.system.Os.lseek(r0, r2, r4)     // Catch:{ Exception -> 0x005a, all -> 0x0058 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x005a, all -> 0x0058 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x005a, all -> 0x0058 }
            int r3 = r7.mThumbnailOffset     // Catch:{ Exception -> 0x0056 }
            int r4 = r7.mOffsetToExifData     // Catch:{ Exception -> 0x0056 }
            int r3 = r3 + r4
            long r3 = (long) r3     // Catch:{ Exception -> 0x0056 }
            long r3 = r2.skip(r3)     // Catch:{ Exception -> 0x0056 }
            int r5 = r7.mThumbnailOffset     // Catch:{ Exception -> 0x0056 }
            int r6 = r7.mOffsetToExifData     // Catch:{ Exception -> 0x0056 }
            int r5 = r5 + r6
            long r5 = (long) r5
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            java.lang.String r4 = "Corrupted image"
            if (r3 != 0) goto L_0x0050
            int r3 = r7.mThumbnailLength     // Catch:{ Exception -> 0x0056 }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0056 }
            int r5 = r2.read(r3)     // Catch:{ Exception -> 0x0056 }
            int r6 = r7.mThumbnailLength     // Catch:{ Exception -> 0x0056 }
            if (r5 != r6) goto L_0x004a
            r7.mThumbnailBytes = r3     // Catch:{ Exception -> 0x0056 }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r2)
            if (r0 == 0) goto L_0x0049
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r0)
        L_0x0049:
            return r3
        L_0x004a:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ Exception -> 0x0056 }
            r7.<init>(r4)     // Catch:{ Exception -> 0x0056 }
            throw r7     // Catch:{ Exception -> 0x0056 }
        L_0x0050:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ Exception -> 0x0056 }
            r7.<init>(r4)     // Catch:{ Exception -> 0x0056 }
            throw r7     // Catch:{ Exception -> 0x0056 }
        L_0x0056:
            r7 = move-exception
            goto L_0x0063
        L_0x0058:
            r7 = move-exception
            goto L_0x0075
        L_0x005a:
            r7 = move-exception
            r2 = r1
            goto L_0x0063
        L_0x005d:
            r7 = move-exception
            r0 = r1
            goto L_0x0075
        L_0x0060:
            r7 = move-exception
            r0 = r1
            r2 = r0
        L_0x0063:
            java.lang.String r3 = "ExifInterface"
            java.lang.String r4 = "Encountered exception while getting thumbnail"
            android.util.Log.d(r3, r4, r7)     // Catch:{ all -> 0x0073 }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r2)
            if (r0 == 0) goto L_0x0072
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r0)
        L_0x0072:
            return r1
        L_0x0073:
            r7 = move-exception
            r1 = r2
        L_0x0075:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r0 == 0) goto L_0x007d
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r0)
        L_0x007d:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getThumbnailBytes():byte[]");
    }

    public final void getWebpAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        if (DEBUG) {
            Log.d("ExifInterface", "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mByteOrder = ByteOrder.LITTLE_ENDIAN;
        byteOrderedDataInputStream.skipFully(WEBP_SIGNATURE_1.length);
        int readInt = byteOrderedDataInputStream.readInt() + 8;
        byte[] bArr = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 8;
        while (true) {
            try {
                byte[] bArr2 = new byte[4];
                if (byteOrderedDataInputStream.read(bArr2) == 4) {
                    int readInt2 = byteOrderedDataInputStream.readInt();
                    int i = length + 4 + 4;
                    if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr2)) {
                        byte[] bArr3 = new byte[readInt2];
                        if (byteOrderedDataInputStream.read(bArr3) == readInt2) {
                            this.mOffsetToExifData = i;
                            readExifSegment(bArr3, 0);
                            setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                            return;
                        }
                        throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(bArr2));
                    }
                    if (readInt2 % 2 == 1) {
                        readInt2++;
                    }
                    length = i + readInt2;
                    if (length != readInt) {
                        if (length <= readInt) {
                            byteOrderedDataInputStream.skipFully(readInt2);
                        } else {
                            throw new IOException("Encountered WebP file with invalid chunk size");
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing WebP chunktype");
                }
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    public final void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get("JPEGInterchangeFormat");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get("JPEGInterchangeFormatLength");
        if (exifAttribute != null && exifAttribute2 != null) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            if (this.mMimeType == 7) {
                intValue += this.mOrfMakerNoteOffset;
            }
            if (intValue > 0 && intValue2 > 0) {
                this.mHasThumbnail = true;
                if (this.mSeekableFileDescriptor == null) {
                    byte[] bArr = new byte[intValue2];
                    byteOrderedDataInputStream.skip((long) intValue);
                    byteOrderedDataInputStream.read(bArr);
                    this.mThumbnailBytes = bArr;
                }
                this.mThumbnailOffset = intValue;
                this.mThumbnailLength = intValue2;
            }
            if (DEBUG) {
                Log.d("ExifInterface", "Setting thumbnail attributes with offset: " + intValue + ", length: " + intValue2);
            }
        }
    }

    public final boolean isThumbnail(HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get("ImageLength");
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get("ImageWidth");
        if (exifAttribute == null || exifAttribute2 == null) {
            return false;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        if (intValue > 512 || intValue2 > 512) {
            return false;
        }
        return true;
    }

    public final void readExifSegment(byte[] bArr, int i) throws IOException {
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(bArr);
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x01f8  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x024b  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x011b  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0120  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void readImageFileDirectory(androidx.exifinterface.media.ExifInterface.SeekableByteOrderedDataInputStream r22, int r23) throws java.io.IOException {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            java.util.HashSet r3 = r0.mAttributesOffsets
            int r4 = r1.mPosition
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.add(r4)
            short r3 = r22.readShort()
            boolean r4 = DEBUG
            java.lang.String r5 = "ExifInterface"
            if (r4 == 0) goto L_0x0020
            java.lang.String r4 = "numberOfDirectoryEntry: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r4, r3, r5)
        L_0x0020:
            if (r3 > 0) goto L_0x0023
            return
        L_0x0023:
            r4 = 0
            r6 = r4
        L_0x0025:
            r7 = 5
            if (r4 >= r3) goto L_0x02e5
            int r8 = r22.readUnsignedShort()
            int r9 = r22.readUnsignedShort()
            int r12 = r22.readInt()
            int r10 = r1.mPosition
            long r10 = (long) r10
            r13 = 4
            long r10 = r10 + r13
            java.util.HashMap<java.lang.Integer, androidx.exifinterface.media.ExifInterface$ExifTag>[] r15 = sExifTagMapsForReading
            r15 = r15[r2]
            java.lang.Integer r13 = java.lang.Integer.valueOf(r8)
            java.lang.Object r13 = r15.get(r13)
            r15 = r13
            androidx.exifinterface.media.ExifInterface$ExifTag r15 = (androidx.exifinterface.media.ExifInterface.ExifTag) r15
            boolean r13 = DEBUG
            r14 = 3
            if (r13 == 0) goto L_0x007e
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r18 = java.lang.Integer.valueOf(r23)
            r7[r6] = r18
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)
            r18 = 1
            r7[r18] = r6
            if (r15 == 0) goto L_0x0063
            java.lang.String r6 = r15.name
            goto L_0x0064
        L_0x0063:
            r6 = 0
        L_0x0064:
            r18 = 2
            r7[r18] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r9)
            r7[r14] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r12)
            r14 = 4
            r7[r14] = r6
            java.lang.String r6 = "ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d"
            java.lang.String r6 = java.lang.String.format(r6, r7)
            android.util.Log.d(r5, r6)
        L_0x007e:
            r6 = 7
            if (r15 != 0) goto L_0x008b
            if (r13 == 0) goto L_0x0088
            java.lang.String r6 = "Skip the tag entry since tag number is not defined: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r6, r8, r5)
        L_0x0088:
            r18 = r3
            goto L_0x00e3
        L_0x008b:
            if (r9 <= 0) goto L_0x010a
            int[] r7 = IFD_FORMAT_BYTES_PER_FORMAT
            int r14 = r7.length
            if (r9 < r14) goto L_0x0094
            goto L_0x010a
        L_0x0094:
            int r14 = r15.primaryFormat
            if (r14 == r6) goto L_0x00c5
            if (r9 != r6) goto L_0x009b
            goto L_0x00c5
        L_0x009b:
            if (r14 == r9) goto L_0x00c5
            int r6 = r15.secondaryFormat
            if (r6 != r9) goto L_0x00a2
            goto L_0x00c5
        L_0x00a2:
            r18 = r3
            r3 = 4
            if (r14 == r3) goto L_0x00a9
            if (r6 != r3) goto L_0x00ad
        L_0x00a9:
            r3 = 3
            if (r9 != r3) goto L_0x00ad
            goto L_0x00c7
        L_0x00ad:
            r3 = 9
            if (r14 == r3) goto L_0x00b3
            if (r6 != r3) goto L_0x00b8
        L_0x00b3:
            r3 = 8
            if (r9 != r3) goto L_0x00b8
            goto L_0x00c7
        L_0x00b8:
            r3 = 12
            if (r14 == r3) goto L_0x00be
            if (r6 != r3) goto L_0x00c3
        L_0x00be:
            r3 = 11
            if (r9 != r3) goto L_0x00c3
            goto L_0x00c7
        L_0x00c3:
            r3 = 0
            goto L_0x00c8
        L_0x00c5:
            r18 = r3
        L_0x00c7:
            r3 = 1
        L_0x00c8:
            if (r3 != 0) goto L_0x00e6
            if (r13 == 0) goto L_0x00e3
            java.lang.String r3 = "Skip the tag entry since data format ("
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            java.lang.String[] r6 = IFD_FORMAT_NAMES
            r6 = r6[r9]
            r3.append(r6)
            java.lang.String r6 = ") is unexpected for tag: "
            r3.append(r6)
            java.lang.String r6 = r15.name
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r3, r6, r5)
        L_0x00e3:
            r19 = r4
            goto L_0x0115
        L_0x00e6:
            r3 = 7
            r6 = r4
            if (r9 != r3) goto L_0x00eb
            r9 = r14
        L_0x00eb:
            long r3 = (long) r12
            r7 = r7[r9]
            r19 = r6
            long r6 = (long) r7
            long r6 = r6 * r3
            r3 = 0
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 < 0) goto L_0x0102
            r3 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x0100
            goto L_0x0102
        L_0x0100:
            r3 = 1
            goto L_0x0119
        L_0x0102:
            if (r13 == 0) goto L_0x0118
            java.lang.String r3 = "Skip the tag entry since the number of components is invalid: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r3, r12, r5)
            goto L_0x0118
        L_0x010a:
            r18 = r3
            r19 = r4
            if (r13 == 0) goto L_0x0115
            java.lang.String r3 = "Skip the tag entry since data format is invalid: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r3, r9, r5)
        L_0x0115:
            r3 = 0
            r6 = r3
        L_0x0118:
            r3 = 0
        L_0x0119:
            if (r3 != 0) goto L_0x0120
            r1.seek(r10)
            goto L_0x02db
        L_0x0120:
            r3 = 4
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            java.lang.String r4 = "Compression"
            if (r3 <= 0) goto L_0x0196
            int r3 = r22.readInt()
            if (r13 == 0) goto L_0x0134
            java.lang.String r14 = "seek to data offset: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r14, r3, r5)
        L_0x0134:
            int r14 = r0.mMimeType
            r16 = r10
            r10 = 7
            if (r14 != r10) goto L_0x018f
            java.lang.String r10 = r15.name
            java.lang.String r11 = "MakerNote"
            boolean r10 = r11.equals(r10)
            if (r10 == 0) goto L_0x0148
            r0.mOrfMakerNoteOffset = r3
            goto L_0x018f
        L_0x0148:
            r10 = 6
            if (r2 != r10) goto L_0x018f
            java.lang.String r11 = r15.name
            java.lang.String r14 = "ThumbnailImage"
            boolean r11 = r14.equals(r11)
            if (r11 == 0) goto L_0x018f
            r0.mOrfThumbnailOffset = r3
            r0.mOrfThumbnailLength = r12
            java.nio.ByteOrder r11 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r10 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort((int) r10, (java.nio.ByteOrder) r11)
            int r11 = r0.mOrfThumbnailOffset
            r14 = r12
            long r11 = (long) r11
            java.nio.ByteOrder r2 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r2 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r11, (java.nio.ByteOrder) r2)
            int r11 = r0.mOrfThumbnailLength
            long r11 = (long) r11
            r20 = r14
            java.nio.ByteOrder r14 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r11 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r11, (java.nio.ByteOrder) r14)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r12 = r0.mAttributes
            r14 = 4
            r12 = r12[r14]
            r12.put(r4, r10)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r10 = r0.mAttributes
            r10 = r10[r14]
            java.lang.String r12 = "JPEGInterchangeFormat"
            r10.put(r12, r2)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r0.mAttributes
            r2 = r2[r14]
            java.lang.String r10 = "JPEGInterchangeFormatLength"
            r2.put(r10, r11)
            goto L_0x0191
        L_0x018f:
            r20 = r12
        L_0x0191:
            long r2 = (long) r3
            r1.seek(r2)
            goto L_0x019a
        L_0x0196:
            r16 = r10
            r20 = r12
        L_0x019a:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r2 = sExifPointerTagMap
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            if (r13 == 0) goto L_0x01c4
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "nextIfdType: "
            r3.append(r8)
            r3.append(r2)
            java.lang.String r8 = " byteCount: "
            r3.append(r8)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r5, r3)
        L_0x01c4:
            if (r2 == 0) goto L_0x0268
            r3 = -1
            r6 = 3
            if (r9 == r6) goto L_0x01f1
            r6 = 4
            if (r9 == r6) goto L_0x01e5
            r6 = 8
            if (r9 == r6) goto L_0x01e0
            r6 = 9
            if (r9 == r6) goto L_0x01db
            r6 = 13
            if (r9 == r6) goto L_0x01db
            goto L_0x01f6
        L_0x01db:
            int r3 = r22.readInt()
            goto L_0x01f5
        L_0x01e0:
            short r3 = r22.readShort()
            goto L_0x01f5
        L_0x01e5:
            int r3 = r22.readInt()
            long r3 = (long) r3
            r6 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r3 = r3 & r6
            goto L_0x01f6
        L_0x01f1:
            int r3 = r22.readUnsignedShort()
        L_0x01f5:
            long r3 = (long) r3
        L_0x01f6:
            if (r13 == 0) goto L_0x0210
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.Long r7 = java.lang.Long.valueOf(r3)
            r8 = 0
            r6[r8] = r7
            java.lang.String r7 = r15.name
            r8 = 1
            r6[r8] = r7
            java.lang.String r7 = "Offset: %d, tagName: %s"
            java.lang.String r6 = java.lang.String.format(r7, r6)
            android.util.Log.d(r5, r6)
        L_0x0210:
            r6 = 0
            int r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x024b
            java.util.HashSet r6 = r0.mAttributesOffsets
            int r7 = (int) r3
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            boolean r6 = r6.contains(r7)
            if (r6 != 0) goto L_0x022e
            r1.seek(r3)
            int r2 = r2.intValue()
            r0.readImageFileDirectory(r1, r2)
            goto L_0x0261
        L_0x022e:
            if (r13 == 0) goto L_0x0261
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Skip jump into the IFD since it has already been read: IfdType "
            r6.append(r7)
            r6.append(r2)
            java.lang.String r2 = " (at "
            r6.append(r2)
            r6.append(r3)
            java.lang.String r2 = ")"
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r6, r2, r5)
            goto L_0x0261
        L_0x024b:
            if (r13 == 0) goto L_0x0261
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "Skip jump into the IFD since its offset is invalid: "
            r2.append(r6)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r5, r2)
        L_0x0261:
            r10 = r16
            r1.seek(r10)
            goto L_0x02db
        L_0x0268:
            r10 = r16
            int r2 = r1.mPosition
            int r3 = r0.mOffsetToExifData
            int r2 = r2 + r3
            int r3 = (int) r6
            byte[] r3 = new byte[r3]
            r1.readFully(r3)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r6 = new androidx.exifinterface.media.ExifInterface$ExifAttribute
            long r13 = (long) r2
            r7 = r10
            r10 = r6
            r11 = r9
            r12 = r20
            r2 = r15
            r15 = r3
            r10.<init>(r11, r12, r13, r15)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r3 = r0.mAttributes
            r3 = r3[r23]
            java.lang.String r9 = r2.name
            r3.put(r9, r6)
            java.lang.String r3 = r2.name
            java.lang.String r9 = "DNGVersion"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0298
            r3 = 3
            r0.mMimeType = r3
        L_0x0298:
            java.lang.String r3 = r2.name
            java.lang.String r9 = "Make"
            boolean r3 = r9.equals(r3)
            if (r3 != 0) goto L_0x02ac
            java.lang.String r3 = r2.name
            java.lang.String r9 = "Model"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x02ba
        L_0x02ac:
            java.nio.ByteOrder r3 = r0.mExifByteOrder
            java.lang.String r3 = r6.getStringValue(r3)
            java.lang.String r9 = "PENTAX"
            boolean r3 = r3.contains(r9)
            if (r3 != 0) goto L_0x02cd
        L_0x02ba:
            java.lang.String r2 = r2.name
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x02d1
            java.nio.ByteOrder r2 = r0.mExifByteOrder
            int r2 = r6.getIntValue(r2)
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r2 != r3) goto L_0x02d1
        L_0x02cd:
            r2 = 8
            r0.mMimeType = r2
        L_0x02d1:
            int r2 = r1.mPosition
            long r2 = (long) r2
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 == 0) goto L_0x02db
            r1.seek(r7)
        L_0x02db:
            int r4 = r19 + 1
            short r4 = (short) r4
            r6 = 0
            r2 = r23
            r3 = r18
            goto L_0x0025
        L_0x02e5:
            int r2 = r22.readInt()
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x0300
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            r8 = 0
            r4[r8] = r6
            java.lang.String r6 = "nextIfdOffset: %d"
            java.lang.String r4 = java.lang.String.format(r6, r4)
            android.util.Log.d(r5, r4)
        L_0x0300:
            long r8 = (long) r2
            r10 = 0
            int r4 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r4 <= 0) goto L_0x033b
            java.util.HashSet r4 = r0.mAttributesOffsets
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            boolean r4 = r4.contains(r6)
            if (r4 != 0) goto L_0x0333
            r1.seek(r8)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r0.mAttributes
            r3 = 4
            r2 = r2[r3]
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0325
            r0.readImageFileDirectory(r1, r3)
            goto L_0x0342
        L_0x0325:
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r0.mAttributes
            r2 = r2[r7]
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0342
            r0.readImageFileDirectory(r1, r7)
            goto L_0x0342
        L_0x0333:
            if (r3 == 0) goto L_0x0342
            java.lang.String r0 = "Stop reading file since re-reading an IFD may cause an infinite loop: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r0, r2, r5)
            goto L_0x0342
        L_0x033b:
            if (r3 == 0) goto L_0x0342
            java.lang.String r0 = "Stop reading file since a wrong offset may cause an infinite loop: "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r0, r2, r5)
        L_0x0342:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.readImageFileDirectory(androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream, int):void");
    }

    public final void replaceInvalidTags(int i, String str, String str2) {
        if (!this.mAttributes[i].isEmpty() && this.mAttributes[i].get(str) != null) {
            HashMap<String, ExifAttribute>[] hashMapArr = this.mAttributes;
            hashMapArr[i].put(str2, hashMapArr[i].get(str));
            this.mAttributes[i].remove(str);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v0, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: java.io.FileInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: java.io.FileInputStream} */
    /* JADX WARNING: type inference failed for: r9v2, types: [java.io.OutputStream, java.io.Closeable, java.io.FileOutputStream] */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00c1, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c3, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c4, code lost:
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00c5, code lost:
        r12 = null;
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00cb, code lost:
        r15 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00cc, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0136, code lost:
        r0.delete();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00cb A[ExcHandler: all (th java.lang.Throwable), Splitter:B:33:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0136  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void saveAttributes() throws java.io.IOException {
        /*
            r15 = this;
            int r0 = r15.mMimeType
            r1 = 0
            r2 = 3
            r3 = 14
            r4 = 13
            r5 = 1
            r6 = 4
            if (r0 == r6) goto L_0x0017
            if (r0 == r4) goto L_0x0017
            if (r0 == r3) goto L_0x0017
            if (r0 == r2) goto L_0x0017
            if (r0 != 0) goto L_0x0015
            goto L_0x0017
        L_0x0015:
            r0 = r1
            goto L_0x0018
        L_0x0017:
            r0 = r5
        L_0x0018:
            if (r0 == 0) goto L_0x0165
            java.io.FileDescriptor r0 = r15.mSeekableFileDescriptor
            if (r0 == 0) goto L_0x015d
            boolean r0 = r15.mHasThumbnail
            if (r0 == 0) goto L_0x0033
            boolean r0 = r15.mHasThumbnailStrips
            if (r0 == 0) goto L_0x0033
            boolean r0 = r15.mAreThumbnailStripsConsecutive
            if (r0 == 0) goto L_0x002b
            goto L_0x0033
        L_0x002b:
            java.io.IOException r15 = new java.io.IOException
            java.lang.String r0 = "ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips"
            r15.<init>(r0)
            throw r15
        L_0x0033:
            int r0 = r15.mThumbnailCompression
            r7 = 6
            r8 = 0
            if (r0 == r7) goto L_0x003f
            r7 = 7
            if (r0 != r7) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r0 = r8
            goto L_0x0043
        L_0x003f:
            byte[] r0 = r15.getThumbnailBytes()
        L_0x0043:
            r15.mThumbnailBytes = r0
            java.lang.String r0 = "temp"
            java.lang.String r7 = "tmp"
            java.io.File r0 = java.io.File.createTempFile(r0, r7)     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            java.io.FileDescriptor r7 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            int r9 = android.system.OsConstants.SEEK_SET     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            r10 = 0
            android.system.Os.lseek(r7, r10, r9)     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            java.io.FileDescriptor r9 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            r7.<init>(r9)     // Catch:{ Exception -> 0x014b, all -> 0x0148 }
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0144, all -> 0x0140 }
            r9.<init>(r0)     // Catch:{ Exception -> 0x0144, all -> 0x0140 }
            androidx.exifinterface.media.ExifInterfaceUtils.copy(r7, r9)     // Catch:{ Exception -> 0x013d, all -> 0x013a }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r7)
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r9)
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00cf, all -> 0x00cb }
            r7.<init>(r0)     // Catch:{ Exception -> 0x00cf, all -> 0x00cb }
            java.io.FileDescriptor r9 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            int r12 = android.system.OsConstants.SEEK_SET     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            android.system.Os.lseek(r9, r10, r12)     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            java.io.FileDescriptor r12 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            r9.<init>(r12)     // Catch:{ Exception -> 0x00c3, all -> 0x00cb }
            java.io.BufferedInputStream r12 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00c1, all -> 0x00cb }
            r12.<init>(r7)     // Catch:{ Exception -> 0x00c1, all -> 0x00cb }
            java.io.BufferedOutputStream r13 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x00be, all -> 0x00ba }
            r13.<init>(r9)     // Catch:{ Exception -> 0x00be, all -> 0x00ba }
            int r14 = r15.mMimeType     // Catch:{ Exception -> 0x00b8 }
            if (r14 != r6) goto L_0x0092
            r15.saveJpegAttributes(r12, r13)     // Catch:{ Exception -> 0x00b8 }
            goto L_0x00ac
        L_0x0092:
            if (r14 != r4) goto L_0x0098
            r15.savePngAttributes(r12, r13)     // Catch:{ Exception -> 0x00b8 }
            goto L_0x00ac
        L_0x0098:
            if (r14 != r3) goto L_0x009e
            r15.saveWebpAttributes(r12, r13)     // Catch:{ Exception -> 0x00b8 }
            goto L_0x00ac
        L_0x009e:
            if (r14 == r2) goto L_0x00a2
            if (r14 != 0) goto L_0x00ac
        L_0x00a2:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataOutputStream r2 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataOutputStream     // Catch:{ Exception -> 0x00b8 }
            java.nio.ByteOrder r3 = java.nio.ByteOrder.BIG_ENDIAN     // Catch:{ Exception -> 0x00b8 }
            r2.<init>(r13, r3)     // Catch:{ Exception -> 0x00b8 }
            r15.writeExifSegment(r2)     // Catch:{ Exception -> 0x00b8 }
        L_0x00ac:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r12)
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r13)
            r0.delete()
            r15.mThumbnailBytes = r8
            return
        L_0x00b8:
            r2 = move-exception
            goto L_0x00c7
        L_0x00ba:
            r15 = move-exception
            r13 = r8
            goto L_0x012d
        L_0x00be:
            r2 = move-exception
            r13 = r8
            goto L_0x00c7
        L_0x00c1:
            r2 = move-exception
            goto L_0x00c5
        L_0x00c3:
            r2 = move-exception
            r9 = r8
        L_0x00c5:
            r12 = r8
            r13 = r12
        L_0x00c7:
            r8 = r9
            r3 = r8
            r8 = r7
            goto L_0x00d3
        L_0x00cb:
            r15 = move-exception
            r13 = r8
            goto L_0x012e
        L_0x00cf:
            r2 = move-exception
            r3 = r8
            r12 = r3
            r13 = r12
        L_0x00d3:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0107, all -> 0x0105 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x0107, all -> 0x0105 }
            java.io.FileDescriptor r6 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            int r7 = android.system.OsConstants.SEEK_SET     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            android.system.Os.lseek(r6, r10, r7)     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            java.io.FileDescriptor r15 = r15.mSeekableFileDescriptor     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            r6.<init>(r15)     // Catch:{ Exception -> 0x0102, all -> 0x00ff }
            androidx.exifinterface.media.ExifInterfaceUtils.copy(r4, r6)     // Catch:{ Exception -> 0x00fb, all -> 0x00f7 }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r4)     // Catch:{ all -> 0x012c }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r6)     // Catch:{ all -> 0x012c }
            java.io.IOException r15 = new java.io.IOException     // Catch:{ all -> 0x012c }
            java.lang.String r3 = "Failed to save new file"
            r15.<init>(r3, r2)     // Catch:{ all -> 0x012c }
            throw r15     // Catch:{ all -> 0x012c }
        L_0x00f7:
            r15 = move-exception
            r8 = r4
            r3 = r6
            goto L_0x0125
        L_0x00fb:
            r15 = move-exception
            r8 = r4
            r3 = r6
            goto L_0x0108
        L_0x00ff:
            r15 = move-exception
            r8 = r4
            goto L_0x0125
        L_0x0102:
            r15 = move-exception
            r8 = r4
            goto L_0x0108
        L_0x0105:
            r15 = move-exception
            goto L_0x0125
        L_0x0107:
            r15 = move-exception
        L_0x0108:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0123 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0123 }
            r2.<init>()     // Catch:{ all -> 0x0123 }
            java.lang.String r4 = "Failed to save new file. Original file is stored in "
            r2.append(r4)     // Catch:{ all -> 0x0123 }
            java.lang.String r4 = r0.getAbsolutePath()     // Catch:{ all -> 0x0123 }
            r2.append(r4)     // Catch:{ all -> 0x0123 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0123 }
            r1.<init>(r2, r15)     // Catch:{ all -> 0x0123 }
            throw r1     // Catch:{ all -> 0x0123 }
        L_0x0123:
            r15 = move-exception
            r1 = r5
        L_0x0125:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r8)     // Catch:{ all -> 0x012c }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r3)     // Catch:{ all -> 0x012c }
            throw r15     // Catch:{ all -> 0x012c }
        L_0x012c:
            r15 = move-exception
        L_0x012d:
            r8 = r12
        L_0x012e:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r8)
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r13)
            if (r1 != 0) goto L_0x0139
            r0.delete()
        L_0x0139:
            throw r15
        L_0x013a:
            r15 = move-exception
            r8 = r9
            goto L_0x0141
        L_0x013d:
            r15 = move-exception
            r8 = r9
            goto L_0x0145
        L_0x0140:
            r15 = move-exception
        L_0x0141:
            r0 = r8
            r8 = r7
            goto L_0x0156
        L_0x0144:
            r15 = move-exception
        L_0x0145:
            r0 = r8
            r8 = r7
            goto L_0x014d
        L_0x0148:
            r15 = move-exception
            r0 = r8
            goto L_0x0156
        L_0x014b:
            r15 = move-exception
            r0 = r8
        L_0x014d:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0155 }
            java.lang.String r2 = "Failed to copy original file to temp file"
            r1.<init>(r2, r15)     // Catch:{ all -> 0x0155 }
            throw r1     // Catch:{ all -> 0x0155 }
        L_0x0155:
            r15 = move-exception
        L_0x0156:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r8)
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r0)
            throw r15
        L_0x015d:
            java.io.IOException r15 = new java.io.IOException
            java.lang.String r0 = "ExifInterface does not support saving attributes for the current input."
            r15.<init>(r0)
            throw r15
        L_0x0165:
            java.io.IOException r15 = new java.io.IOException
            java.lang.String r0 = "ExifInterface only supports saving attributes for JPEG, PNG, WebP, and DNG formats."
            r15.<init>(r0)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.saveAttributes():void");
    }

    public final void saveJpegAttributes(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        if (DEBUG) {
            Log.d("ExifInterface", "saveJpegAttributes starting with (inputStream: " + bufferedInputStream + ", outputStream: " + bufferedOutputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bufferedInputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(bufferedOutputStream, ByteOrder.BIG_ENDIAN);
        if (byteOrderedDataInputStream.readByte() == -1) {
            byteOrderedDataOutputStream.writeByte(-1);
            if (byteOrderedDataInputStream.readByte() == -40) {
                byteOrderedDataOutputStream.writeByte(-40);
                ExifAttribute exifAttribute = null;
                if (getAttribute("Xmp") != null && this.mXmpIsFromSeparateMarker) {
                    exifAttribute = this.mAttributes[0].remove("Xmp");
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(-31);
                writeExifSegment(byteOrderedDataOutputStream);
                if (exifAttribute != null) {
                    this.mAttributes[0].put("Xmp", exifAttribute);
                }
                byte[] bArr = new byte[4096];
                while (byteOrderedDataInputStream.readByte() == -1) {
                    byte readByte = byteOrderedDataInputStream.readByte();
                    if (readByte == -39 || readByte == -38) {
                        byteOrderedDataOutputStream.writeByte(-1);
                        byteOrderedDataOutputStream.writeByte(readByte);
                        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
                        return;
                    } else if (readByte != -31) {
                        byteOrderedDataOutputStream.writeByte(-1);
                        byteOrderedDataOutputStream.writeByte(readByte);
                        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                        byteOrderedDataOutputStream.writeShort((short) readUnsignedShort);
                        int i = readUnsignedShort - 2;
                        if (i >= 0) {
                            while (i > 0) {
                                int read = byteOrderedDataInputStream.read(bArr, 0, Math.min(i, 4096));
                                if (read < 0) {
                                    break;
                                }
                                byteOrderedDataOutputStream.write(bArr, 0, read);
                                i -= read;
                            }
                        } else {
                            throw new IOException("Invalid length");
                        }
                    } else {
                        int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort() - 2;
                        if (readUnsignedShort2 >= 0) {
                            byte[] bArr2 = new byte[6];
                            if (readUnsignedShort2 >= 6) {
                                if (byteOrderedDataInputStream.read(bArr2) != 6) {
                                    throw new IOException("Invalid exif");
                                } else if (Arrays.equals(bArr2, IDENTIFIER_EXIF_APP1)) {
                                    byteOrderedDataInputStream.skipFully(readUnsignedShort2 - 6);
                                }
                            }
                            byteOrderedDataOutputStream.writeByte(-1);
                            byteOrderedDataOutputStream.writeByte(readByte);
                            byteOrderedDataOutputStream.writeShort((short) (readUnsignedShort2 + 2));
                            if (readUnsignedShort2 >= 6) {
                                readUnsignedShort2 -= 6;
                                byteOrderedDataOutputStream.write(bArr2);
                            }
                            while (readUnsignedShort2 > 0) {
                                int read2 = byteOrderedDataInputStream.read(bArr, 0, Math.min(readUnsignedShort2, 4096));
                                if (read2 < 0) {
                                    break;
                                }
                                byteOrderedDataOutputStream.write(bArr, 0, read2);
                                readUnsignedShort2 -= read2;
                            }
                        } else {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    public final void savePngAttributes(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        if (DEBUG) {
            Log.d("ExifInterface", "savePngAttributes starting with (inputStream: " + bufferedInputStream + ", outputStream: " + bufferedOutputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bufferedInputStream);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(bufferedOutputStream, byteOrder);
        byte[] bArr = PNG_SIGNATURE;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr.length);
        int i = this.mOffsetToExifData;
        if (i == 0) {
            int readInt = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(readInt);
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt + 4 + 4);
        } else {
            ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, ((i - bArr.length) - 4) - 4);
            byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt() + 4 + 4);
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, byteOrder);
                writeExifSegment(byteOrderedDataOutputStream2);
                byte[] byteArray = ((ByteArrayOutputStream) byteOrderedDataOutputStream2.mOutputStream).toByteArray();
                byteOrderedDataOutputStream.write(byteArray);
                CRC32 crc32 = new CRC32();
                crc32.update(byteArray, 4, byteArray.length - 4);
                byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream);
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = byteArrayOutputStream2;
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
            throw th;
        }
    }

    public final void saveWebpAttributes(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        BufferedInputStream bufferedInputStream2 = bufferedInputStream;
        BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream;
        if (DEBUG) {
            Log.d("ExifInterface", "saveWebpAttributes starting with (inputStream: " + bufferedInputStream2 + ", outputStream: " + bufferedOutputStream2 + ")");
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bufferedInputStream2, byteOrder);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(bufferedOutputStream2, byteOrder);
        byte[] bArr = WEBP_SIGNATURE_1;
        ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr.length);
        byte[] bArr2 = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr2.length + 4);
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, byteOrder);
                int i7 = this.mOffsetToExifData;
                if (i7 != 0) {
                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, ((i7 - ((bArr.length + 4) + bArr2.length)) - 4) - 4);
                    byteOrderedDataInputStream.skipFully(4);
                    byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt());
                    writeExifSegment(byteOrderedDataOutputStream2);
                } else {
                    byte[] bArr3 = new byte[4];
                    if (byteOrderedDataInputStream.read(bArr3) == 4) {
                        byte[] bArr4 = WEBP_CHUNK_TYPE_VP8X;
                        boolean z = false;
                        if (Arrays.equals(bArr3, bArr4)) {
                            int readInt = byteOrderedDataInputStream.readInt();
                            if (readInt % 2 == 1) {
                                i6 = readInt + 1;
                            } else {
                                i6 = readInt;
                            }
                            byte[] bArr5 = new byte[i6];
                            byteOrderedDataInputStream.read(bArr5);
                            bArr5[0] = (byte) (8 | bArr5[0]);
                            if (((bArr5[0] >> 1) & 1) == 1) {
                                z = true;
                            }
                            byteOrderedDataOutputStream2.write(bArr4);
                            byteOrderedDataOutputStream2.writeInt(readInt);
                            byteOrderedDataOutputStream2.write(bArr5);
                            if (z) {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_ANIM, (byte[]) null);
                                while (true) {
                                    byte[] bArr6 = new byte[4];
                                    bufferedInputStream2.read(bArr6);
                                    if (!Arrays.equals(bArr6, WEBP_CHUNK_TYPE_ANMF)) {
                                        break;
                                    }
                                    int readInt2 = byteOrderedDataInputStream.readInt();
                                    byteOrderedDataOutputStream2.write(bArr6);
                                    byteOrderedDataOutputStream2.writeInt(readInt2);
                                    if (readInt2 % 2 == 1) {
                                        readInt2++;
                                    }
                                    ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, readInt2);
                                }
                                writeExifSegment(byteOrderedDataOutputStream2);
                            } else {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        } else {
                            byte[] bArr7 = WEBP_CHUNK_TYPE_VP8;
                            if (Arrays.equals(bArr3, bArr7) || Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                int readInt3 = byteOrderedDataInputStream.readInt();
                                if (readInt3 % 2 == 1) {
                                    i = readInt3 + 1;
                                } else {
                                    i = readInt3;
                                }
                                byte[] bArr8 = new byte[3];
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataInputStream.read(bArr8);
                                    byte[] bArr9 = new byte[3];
                                    if (byteOrderedDataInputStream.read(bArr9) != 3 || !Arrays.equals(WEBP_VP8_SIGNATURE, bArr9)) {
                                        throw new IOException("Encountered error while checking VP8 signature");
                                    }
                                    i5 = byteOrderedDataInputStream.readInt();
                                    i4 = (i5 << 18) >> 18;
                                    i3 = (i5 << 2) >> 18;
                                    i -= 10;
                                    i2 = 0;
                                } else if (!Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                    i5 = 0;
                                    i4 = 0;
                                    i3 = 0;
                                    i2 = 0;
                                } else if (byteOrderedDataInputStream.readByte() == 47) {
                                    int readInt4 = byteOrderedDataInputStream.readInt();
                                    i2 = readInt4 & 8;
                                    i -= 5;
                                    i3 = ((readInt4 << 4) >> 18) + 1;
                                    i5 = readInt4;
                                    i4 = ((readInt4 << 18) >> 18) + 1;
                                } else {
                                    throw new IOException("Encountered error while checking VP8L signature");
                                }
                                byteOrderedDataOutputStream2.write(bArr4);
                                byteOrderedDataOutputStream2.writeInt(10);
                                byte[] bArr10 = new byte[10];
                                bArr10[0] = (byte) (bArr10[0] | 8);
                                bArr10[0] = (byte) (bArr10[0] | (i2 << 4));
                                int i8 = i4 - 1;
                                int i9 = i3 - 1;
                                bArr10[4] = (byte) i8;
                                bArr10[5] = (byte) (i8 >> 8);
                                bArr10[6] = (byte) (i8 >> 16);
                                bArr10[7] = (byte) i9;
                                bArr10[8] = (byte) (i9 >> 8);
                                bArr10[9] = (byte) (i9 >> 16);
                                byteOrderedDataOutputStream2.write(bArr10);
                                byteOrderedDataOutputStream2.write(bArr3);
                                byteOrderedDataOutputStream2.writeInt(readInt3);
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataOutputStream2.write(bArr8);
                                    byteOrderedDataOutputStream2.write(WEBP_VP8_SIGNATURE);
                                    byteOrderedDataOutputStream2.writeInt(i5);
                                } else if (Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                    byteOrderedDataOutputStream2.write(47);
                                    byteOrderedDataOutputStream2.writeInt(i5);
                                }
                                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, i);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        }
                    } else {
                        throw new IOException("Encountered invalid length while parsing WebP chunk type");
                    }
                }
                ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2);
                int size = byteArrayOutputStream2.size();
                byte[] bArr11 = WEBP_SIGNATURE_2;
                byteOrderedDataOutputStream.writeInt(size + bArr11.length);
                byteOrderedDataOutputStream.write(bArr11);
                byteArrayOutputStream2.writeTo(byteOrderedDataOutputStream);
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = byteArrayOutputStream2;
                try {
                    throw new IOException("Failed to save WebP file", e);
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            throw new IOException("Failed to save WebP file", e);
        }
    }

    public final void setAttribute(String str, String str2) {
        ExifTag exifTag;
        int i;
        ExifAttribute exifAttribute;
        int i2;
        String str3;
        String str4 = str;
        String str5 = str2;
        if (("DateTime".equals(str4) || "DateTimeOriginal".equals(str4) || "DateTimeDigitized".equals(str4)) && str5 != null) {
            boolean find = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(str5).find();
            boolean find2 = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(str5).find();
            if (str2.length() != 19 || (!find && !find2)) {
                Log.w("ExifInterface", "Invalid value for " + str4 + " : " + str5);
                return;
            } else if (find2) {
                str5 = str5.replaceAll("-", ":");
            }
        }
        if ("ISOSpeedRatings".equals(str4)) {
            if (DEBUG) {
                Log.d("ExifInterface", "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str4 = "PhotographicSensitivity";
        }
        int i3 = 2;
        int i4 = 1;
        if (str5 != null && sTagSetForCompatibility.contains(str4)) {
            if (str4.equals("GPSTimeStamp")) {
                Matcher matcher = GPS_TIMESTAMP_PATTERN.matcher(str5);
                if (!matcher.find()) {
                    Log.w("ExifInterface", "Invalid value for " + str4 + " : " + str5);
                    return;
                }
                str5 = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            } else {
                try {
                    str5 = ((long) (Double.parseDouble(str5) * 10000.0d)) + "/" + 10000;
                } catch (NumberFormatException unused) {
                    Log.w("ExifInterface", "Invalid value for " + str4 + " : " + str5);
                    return;
                }
            }
        }
        int i5 = 0;
        int i6 = 0;
        while (i5 < EXIF_TAGS.length) {
            if ((i5 != 4 || this.mHasThumbnail) && (exifTag = sExifTagMapsForWriting[i5].get(str4)) != null) {
                if (str5 == null) {
                    this.mAttributes[i5].remove(str4);
                } else {
                    Pair<Integer, Integer> guessDataFormat = guessDataFormat(str5);
                    int i7 = -1;
                    if (exifTag.primaryFormat == ((Integer) guessDataFormat.first).intValue() || exifTag.primaryFormat == ((Integer) guessDataFormat.second).intValue()) {
                        i = exifTag.primaryFormat;
                    } else {
                        int i8 = exifTag.secondaryFormat;
                        if (i8 == -1 || !(i8 == ((Integer) guessDataFormat.first).intValue() || exifTag.secondaryFormat == ((Integer) guessDataFormat.second).intValue())) {
                            int i9 = exifTag.primaryFormat;
                            if (i9 == i4 || i9 == 7 || i9 == i3) {
                                i = i9;
                            } else if (DEBUG) {
                                StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Given tag (", str4, ") value didn't match with one of expected formats: ");
                                String[] strArr = IFD_FORMAT_NAMES;
                                m.append(strArr[exifTag.primaryFormat]);
                                String str6 = "";
                                if (exifTag.secondaryFormat == -1) {
                                    str3 = str6;
                                } else {
                                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(", ");
                                    m2.append(strArr[exifTag.secondaryFormat]);
                                    str3 = m2.toString();
                                }
                                m.append(str3);
                                m.append(" (guess: ");
                                m.append(strArr[((Integer) guessDataFormat.first).intValue()]);
                                if (((Integer) guessDataFormat.second).intValue() != -1) {
                                    StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(", ");
                                    m3.append(strArr[((Integer) guessDataFormat.second).intValue()]);
                                    str6 = m3.toString();
                                }
                                m.append(str6);
                                m.append(")");
                                Log.d("ExifInterface", m.toString());
                            }
                        } else {
                            i = exifTag.secondaryFormat;
                        }
                    }
                    switch (i) {
                        case 1:
                            HashMap<String, ExifAttribute> hashMap = this.mAttributes[i5];
                            i4 = 1;
                            if (str5.length() == 1) {
                                i2 = 0;
                                if (str5.charAt(0) >= '0' && str5.charAt(0) <= '1') {
                                    exifAttribute = new ExifAttribute(1, 1, new byte[]{(byte) (str5.charAt(0) - '0')});
                                    hashMap.put(str4, exifAttribute);
                                    i6 = i2;
                                    continue;
                                }
                            } else {
                                i2 = 0;
                            }
                            byte[] bytes = str5.getBytes(ASCII);
                            exifAttribute = new ExifAttribute(1, bytes.length, bytes);
                            hashMap.put(str4, exifAttribute);
                            i6 = i2;
                            continue;
                        case 2:
                        case 7:
                            this.mAttributes[i5].put(str4, ExifAttribute.createString(str5));
                            break;
                        case 3:
                            String[] split = str5.split(",", -1);
                            int[] iArr = new int[split.length];
                            for (int i10 = 0; i10 < split.length; i10++) {
                                iArr[i10] = Integer.parseInt(split[i10]);
                            }
                            this.mAttributes[i5].put(str4, ExifAttribute.createUShort(iArr, this.mExifByteOrder));
                            break;
                        case 4:
                            String[] split2 = str5.split(",", -1);
                            long[] jArr = new long[split2.length];
                            for (int i11 = 0; i11 < split2.length; i11++) {
                                jArr[i11] = Long.parseLong(split2[i11]);
                            }
                            this.mAttributes[i5].put(str4, ExifAttribute.createULong(jArr, this.mExifByteOrder));
                            break;
                        case 5:
                            String[] split3 = str5.split(",", -1);
                            Rational[] rationalArr = new Rational[split3.length];
                            for (int i12 = 0; i12 < split3.length; i12++) {
                                String[] split4 = split3[i12].split("/", -1);
                                rationalArr[i12] = new Rational((long) Double.parseDouble(split4[0]), (long) Double.parseDouble(split4[1]));
                            }
                            this.mAttributes[i5].put(str4, ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                            break;
                        case 9:
                            String[] split5 = str5.split(",", -1);
                            int length = split5.length;
                            int[] iArr2 = new int[length];
                            for (int i13 = 0; i13 < split5.length; i13++) {
                                iArr2[i13] = Integer.parseInt(split5[i13]);
                            }
                            HashMap<String, ExifAttribute> hashMap2 = this.mAttributes[i5];
                            ByteOrder byteOrder = this.mExifByteOrder;
                            ByteBuffer wrap = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[9] * length)]);
                            wrap.order(byteOrder);
                            for (int i14 = 0; i14 < length; i14++) {
                                wrap.putInt(iArr2[i14]);
                            }
                            hashMap2.put(str4, new ExifAttribute(9, length, wrap.array()));
                            break;
                        case 10:
                            String[] split6 = str5.split(",", -1);
                            int length2 = split6.length;
                            Rational[] rationalArr2 = new Rational[length2];
                            int i15 = i4;
                            int i16 = i6;
                            while (i6 < split6.length) {
                                String[] split7 = split6[i6].split("/", i7);
                                rationalArr2[i6] = new Rational((long) Double.parseDouble(split7[i16]), (long) Double.parseDouble(split7[i15]));
                                i6++;
                                i16 = 0;
                                i15 = 1;
                                i7 = -1;
                            }
                            HashMap<String, ExifAttribute> hashMap3 = this.mAttributes[i5];
                            ByteOrder byteOrder2 = this.mExifByteOrder;
                            ByteBuffer wrap2 = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[10] * length2)]);
                            wrap2.order(byteOrder2);
                            for (int i17 = 0; i17 < length2; i17++) {
                                Rational rational = rationalArr2[i17];
                                wrap2.putInt((int) rational.numerator);
                                wrap2.putInt((int) rational.denominator);
                            }
                            hashMap3.put(str4, new ExifAttribute(10, length2, wrap2.array()));
                            break;
                        case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                            String[] split8 = str5.split(",", -1);
                            int length3 = split8.length;
                            double[] dArr = new double[length3];
                            for (int i18 = i6; i18 < split8.length; i18++) {
                                dArr[i18] = Double.parseDouble(split8[i18]);
                            }
                            HashMap<String, ExifAttribute> hashMap4 = this.mAttributes[i5];
                            ByteOrder byteOrder3 = this.mExifByteOrder;
                            ByteBuffer wrap3 = ByteBuffer.wrap(new byte[(IFD_FORMAT_BYTES_PER_FORMAT[12] * length3)]);
                            wrap3.order(byteOrder3);
                            for (int i19 = i6; i19 < length3; i19++) {
                                wrap3.putDouble(dArr[i19]);
                            }
                            hashMap4.put(str4, new ExifAttribute(12, length3, wrap3.array()));
                            continue;
                        default:
                            if (DEBUG) {
                                ExifInterface$$ExternalSyntheticOutline1.m14m("Data format isn't one of expected formats: ", i, "ExifInterface");
                                break;
                            } else {
                                continue;
                            }
                    }
                    i6 = 0;
                    i4 = 1;
                }
            }
            i5++;
            i3 = 2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:76:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setThumbnailData(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r19) throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r0.mAttributes
            r3 = 4
            r2 = r2[r3]
            java.lang.String r3 = "Compression"
            java.lang.Object r3 = r2.get(r3)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r3 = (androidx.exifinterface.media.ExifInterface.ExifAttribute) r3
            r4 = 6
            if (r3 == 0) goto L_0x0163
            java.nio.ByteOrder r5 = r0.mExifByteOrder
            int r3 = r3.getIntValue(r5)
            r0.mThumbnailCompression = r3
            r5 = 1
            if (r3 == r5) goto L_0x002b
            if (r3 == r4) goto L_0x0026
            r6 = 7
            if (r3 == r6) goto L_0x002b
            goto L_0x0168
        L_0x0026:
            r0.handleThumbnailFromJfif(r1, r2)
            goto L_0x0168
        L_0x002b:
            java.lang.String r3 = "BitsPerSample"
            java.lang.Object r3 = r2.get(r3)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r3 = (androidx.exifinterface.media.ExifInterface.ExifAttribute) r3
            java.lang.String r6 = "ExifInterface"
            r7 = 0
            if (r3 == 0) goto L_0x0072
            java.nio.ByteOrder r8 = r0.mExifByteOrder
            java.lang.Object r3 = r3.getValue(r8)
            int[] r3 = (int[]) r3
            int[] r8 = BITS_PER_SAMPLE_RGB
            boolean r9 = java.util.Arrays.equals(r8, r3)
            if (r9 == 0) goto L_0x0049
            goto L_0x0070
        L_0x0049:
            int r9 = r0.mMimeType
            r10 = 3
            if (r9 != r10) goto L_0x0072
            java.lang.String r9 = "PhotometricInterpretation"
            java.lang.Object r9 = r2.get(r9)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r9 = (androidx.exifinterface.media.ExifInterface.ExifAttribute) r9
            if (r9 == 0) goto L_0x0072
            java.nio.ByteOrder r10 = r0.mExifByteOrder
            int r9 = r9.getIntValue(r10)
            if (r9 != r5) goto L_0x0068
            int[] r10 = BITS_PER_SAMPLE_GREYSCALE_2
            boolean r10 = java.util.Arrays.equals(r3, r10)
            if (r10 != 0) goto L_0x0070
        L_0x0068:
            if (r9 != r4) goto L_0x0072
            boolean r3 = java.util.Arrays.equals(r3, r8)
            if (r3 == 0) goto L_0x0072
        L_0x0070:
            r3 = r5
            goto L_0x007c
        L_0x0072:
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x007b
            java.lang.String r3 = "Unsupported data type value"
            android.util.Log.d(r6, r3)
        L_0x007b:
            r3 = r7
        L_0x007c:
            if (r3 == 0) goto L_0x0168
            java.lang.String r3 = "StripOffsets"
            java.lang.Object r3 = r2.get(r3)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r3 = (androidx.exifinterface.media.ExifInterface.ExifAttribute) r3
            java.lang.String r4 = "StripByteCounts"
            java.lang.Object r2 = r2.get(r4)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r2 = (androidx.exifinterface.media.ExifInterface.ExifAttribute) r2
            if (r3 == 0) goto L_0x0168
            if (r2 == 0) goto L_0x0168
            java.nio.ByteOrder r4 = r0.mExifByteOrder
            java.lang.Object r3 = r3.getValue(r4)
            long[] r3 = androidx.exifinterface.media.ExifInterfaceUtils.convertToLongArray(r3)
            java.nio.ByteOrder r4 = r0.mExifByteOrder
            java.lang.Object r2 = r2.getValue(r4)
            long[] r2 = androidx.exifinterface.media.ExifInterfaceUtils.convertToLongArray(r2)
            if (r3 == 0) goto L_0x015c
            int r4 = r3.length
            if (r4 != 0) goto L_0x00ad
            goto L_0x015c
        L_0x00ad:
            if (r2 == 0) goto L_0x0155
            int r4 = r2.length
            if (r4 != 0) goto L_0x00b4
            goto L_0x0155
        L_0x00b4:
            int r4 = r3.length
            int r8 = r2.length
            if (r4 == r8) goto L_0x00c0
            java.lang.String r0 = "stripOffsets and stripByteCounts should have same length."
            android.util.Log.w(r6, r0)
            goto L_0x0168
        L_0x00c0:
            r8 = 0
            int r4 = r2.length
            r10 = r7
        L_0x00c4:
            if (r10 >= r4) goto L_0x00cc
            r11 = r2[r10]
            long r8 = r8 + r11
            int r10 = r10 + 1
            goto L_0x00c4
        L_0x00cc:
            int r4 = (int) r8
            byte[] r8 = new byte[r4]
            r0.mAreThumbnailStripsConsecutive = r5
            r0.mHasThumbnailStrips = r5
            r0.mHasThumbnail = r5
            r9 = r7
            r10 = r9
            r11 = r10
        L_0x00d8:
            int r12 = r3.length
            if (r9 >= r12) goto L_0x0147
            r12 = r3[r9]
            int r12 = (int) r12
            r13 = r2[r9]
            int r13 = (int) r13
            int r14 = r3.length
            int r14 = r14 - r5
            if (r9 >= r14) goto L_0x00f2
            int r14 = r12 + r13
            long r14 = (long) r14
            int r16 = r9 + 1
            r16 = r3[r16]
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 == 0) goto L_0x00f2
            r0.mAreThumbnailStripsConsecutive = r7
        L_0x00f2:
            int r12 = r12 - r10
            if (r12 >= 0) goto L_0x00fb
            java.lang.String r0 = "Invalid strip offset value"
            android.util.Log.d(r6, r0)
            goto L_0x0168
        L_0x00fb:
            long r14 = (long) r12
            long r16 = r1.skip(r14)
            int r14 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            java.lang.String r15 = " bytes."
            if (r14 == 0) goto L_0x011e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to skip "
            r0.append(r1)
            r0.append(r12)
            r0.append(r15)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            goto L_0x0168
        L_0x011e:
            int r10 = r10 + r12
            byte[] r12 = new byte[r13]
            int r14 = r1.read(r12)
            if (r14 == r13) goto L_0x013f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to read "
            r0.append(r1)
            r0.append(r13)
            r0.append(r15)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r6, r0)
            goto L_0x0168
        L_0x013f:
            int r10 = r10 + r13
            java.lang.System.arraycopy(r12, r7, r8, r11, r13)
            int r11 = r11 + r13
            int r9 = r9 + 1
            goto L_0x00d8
        L_0x0147:
            r0.mThumbnailBytes = r8
            boolean r1 = r0.mAreThumbnailStripsConsecutive
            if (r1 == 0) goto L_0x0168
            r1 = r3[r7]
            int r1 = (int) r1
            r0.mThumbnailOffset = r1
            r0.mThumbnailLength = r4
            goto L_0x0168
        L_0x0155:
            java.lang.String r0 = "stripByteCounts should not be null or have zero length."
            android.util.Log.w(r6, r0)
            goto L_0x0168
        L_0x015c:
            java.lang.String r0 = "stripOffsets should not be null or have zero length."
            android.util.Log.w(r6, r0)
            goto L_0x0168
        L_0x0163:
            r0.mThumbnailCompression = r4
            r0.handleThumbnailFromJfif(r1, r2)
        L_0x0168:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.setThumbnailData(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream):void");
    }

    public final void swapBasedOnImageSize(int i, int i2) throws IOException {
        if (!this.mAttributes[i].isEmpty() && !this.mAttributes[i2].isEmpty()) {
            ExifAttribute exifAttribute = this.mAttributes[i].get("ImageLength");
            ExifAttribute exifAttribute2 = this.mAttributes[i].get("ImageWidth");
            ExifAttribute exifAttribute3 = this.mAttributes[i2].get("ImageLength");
            ExifAttribute exifAttribute4 = this.mAttributes[i2].get("ImageWidth");
            if (exifAttribute == null || exifAttribute2 == null) {
                if (DEBUG) {
                    Log.d("ExifInterface", "First image does not contain valid size information");
                }
            } else if (exifAttribute3 != null && exifAttribute4 != null) {
                int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
                int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
                int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
                int intValue4 = exifAttribute4.getIntValue(this.mExifByteOrder);
                if (intValue < intValue3 && intValue2 < intValue4) {
                    HashMap<String, ExifAttribute>[] hashMapArr = this.mAttributes;
                    HashMap<String, ExifAttribute> hashMap = hashMapArr[i];
                    hashMapArr[i] = hashMapArr[i2];
                    hashMapArr[i2] = hashMap;
                }
            } else if (DEBUG) {
                Log.d("ExifInterface", "Second image does not contain valid size information");
            }
        } else if (DEBUG) {
            Log.d("ExifInterface", "Cannot perform swap since only one image data exists");
        }
    }

    public final void updateImageSizeValues(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i) throws IOException {
        ExifAttribute exifAttribute;
        ExifAttribute exifAttribute2;
        ExifAttribute exifAttribute3 = this.mAttributes[i].get("DefaultCropSize");
        ExifAttribute exifAttribute4 = this.mAttributes[i].get("SensorTopBorder");
        ExifAttribute exifAttribute5 = this.mAttributes[i].get("SensorLeftBorder");
        ExifAttribute exifAttribute6 = this.mAttributes[i].get("SensorBottomBorder");
        ExifAttribute exifAttribute7 = this.mAttributes[i].get("SensorRightBorder");
        if (exifAttribute3 != null) {
            if (exifAttribute3.format == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute3.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 2) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid crop size values. cropSize=");
                    m.append(Arrays.toString(rationalArr));
                    Log.w("ExifInterface", m.toString());
                    return;
                }
                Rational rational = rationalArr[0];
                Rational[] rationalArr2 = {rational};
                exifAttribute2 = ExifAttribute.createURational(rationalArr2, this.mExifByteOrder);
                Rational rational2 = rationalArr[1];
                Rational[] rationalArr3 = {rational2};
                exifAttribute = ExifAttribute.createURational(rationalArr3, this.mExifByteOrder);
            } else {
                int[] iArr = (int[]) exifAttribute3.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 2) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid crop size values. cropSize=");
                    m2.append(Arrays.toString(iArr));
                    Log.w("ExifInterface", m2.toString());
                    return;
                }
                exifAttribute2 = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                exifAttribute = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            this.mAttributes[i].put("ImageWidth", exifAttribute2);
            this.mAttributes[i].put("ImageLength", exifAttribute);
        } else if (exifAttribute4 == null || exifAttribute5 == null || exifAttribute6 == null || exifAttribute7 == null) {
            ExifAttribute exifAttribute8 = this.mAttributes[i].get("ImageLength");
            ExifAttribute exifAttribute9 = this.mAttributes[i].get("ImageWidth");
            if (exifAttribute8 == null || exifAttribute9 == null) {
                ExifAttribute exifAttribute10 = this.mAttributes[i].get("JPEGInterchangeFormat");
                ExifAttribute exifAttribute11 = this.mAttributes[i].get("JPEGInterchangeFormatLength");
                if (exifAttribute10 != null && exifAttribute11 != null) {
                    int intValue = exifAttribute10.getIntValue(this.mExifByteOrder);
                    int intValue2 = exifAttribute10.getIntValue(this.mExifByteOrder);
                    seekableByteOrderedDataInputStream.seek((long) intValue);
                    byte[] bArr = new byte[intValue2];
                    seekableByteOrderedDataInputStream.read(bArr);
                    getJpegAttributes(new ByteOrderedDataInputStream(bArr), intValue, i);
                }
            }
        } else {
            int intValue3 = exifAttribute4.getIntValue(this.mExifByteOrder);
            int intValue4 = exifAttribute6.getIntValue(this.mExifByteOrder);
            int intValue5 = exifAttribute7.getIntValue(this.mExifByteOrder);
            int intValue6 = exifAttribute5.getIntValue(this.mExifByteOrder);
            if (intValue4 > intValue3 && intValue5 > intValue6) {
                ExifAttribute createUShort = ExifAttribute.createUShort(intValue4 - intValue3, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(intValue5 - intValue6, this.mExifByteOrder);
                this.mAttributes[i].put("ImageLength", createUShort);
                this.mAttributes[i].put("ImageWidth", createUShort2);
            }
        }
    }

    public final int writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream) throws IOException {
        short s;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = byteOrderedDataOutputStream;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        int[] iArr = new int[exifTagArr.length];
        int[] iArr2 = new int[exifTagArr.length];
        for (ExifTag exifTag : EXIF_POINTER_TAGS) {
            removeAttribute(exifTag.name);
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                removeAttribute("StripOffsets");
                removeAttribute("StripByteCounts");
            } else {
                removeAttribute("JPEGInterchangeFormat");
                removeAttribute("JPEGInterchangeFormatLength");
            }
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            for (Object obj : this.mAttributes[i].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[i].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put("StripOffsets", ExifAttribute.createUShort(0, this.mExifByteOrder));
                this.mAttributes[4].put("StripByteCounts", ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put("JPEGInterchangeFormat", ExifAttribute.createULong(0, this.mExifByteOrder));
                this.mAttributes[4].put("JPEGInterchangeFormatLength", ExifAttribute.createULong((long) this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        for (int i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            int i3 = 0;
            for (Map.Entry<String, ExifAttribute> value : this.mAttributes[i2].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) value.getValue();
                Objects.requireNonNull(exifAttribute);
                int i4 = IFD_FORMAT_BYTES_PER_FORMAT[exifAttribute.format] * exifAttribute.numberOfComponents;
                if (i4 > 4) {
                    i3 += i4;
                }
            }
            iArr2[i2] = iArr2[i2] + i3;
        }
        int i5 = 8;
        for (int i6 = 0; i6 < EXIF_TAGS.length; i6++) {
            if (!this.mAttributes[i6].isEmpty()) {
                iArr[i6] = i5;
                i5 = (this.mAttributes[i6].size() * 12) + 2 + 4 + iArr2[i6] + i5;
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put("StripOffsets", ExifAttribute.createUShort(i5, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put("JPEGInterchangeFormat", ExifAttribute.createULong((long) i5, this.mExifByteOrder));
            }
            this.mThumbnailOffset = i5;
            i5 += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            i5 += 8;
        }
        if (DEBUG) {
            for (int i7 = 0; i7 < EXIF_TAGS.length; i7++) {
                Log.d("ExifInterface", String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", new Object[]{Integer.valueOf(i7), Integer.valueOf(iArr[i7]), Integer.valueOf(this.mAttributes[i7].size()), Integer.valueOf(iArr2[i7]), Integer.valueOf(i5)}));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong((long) iArr[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong((long) iArr[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong((long) iArr[3], this.mExifByteOrder));
        }
        int i8 = this.mMimeType;
        if (i8 == 4) {
            byteOrderedDataOutputStream2.writeShort((short) i5);
            byteOrderedDataOutputStream2.write(IDENTIFIER_EXIF_APP1);
        } else if (i8 == 13) {
            byteOrderedDataOutputStream2.writeInt(i5);
            byteOrderedDataOutputStream2.write(PNG_CHUNK_TYPE_EXIF);
        } else if (i8 == 14) {
            byteOrderedDataOutputStream2.write(WEBP_CHUNK_TYPE_EXIF);
            byteOrderedDataOutputStream2.writeInt(i5);
        }
        if (this.mExifByteOrder == ByteOrder.BIG_ENDIAN) {
            s = 19789;
        } else {
            s = 18761;
        }
        byteOrderedDataOutputStream2.writeShort(s);
        byteOrderedDataOutputStream2.mByteOrder = this.mExifByteOrder;
        byteOrderedDataOutputStream2.writeShort((short) 42);
        byteOrderedDataOutputStream2.writeInt((int) 8);
        for (int i9 = 0; i9 < EXIF_TAGS.length; i9++) {
            if (!this.mAttributes[i9].isEmpty()) {
                byteOrderedDataOutputStream2.writeShort((short) this.mAttributes[i9].size());
                int size = (this.mAttributes[i9].size() * 12) + iArr[i9] + 2 + 4;
                for (Map.Entry next : this.mAttributes[i9].entrySet()) {
                    int i10 = sExifTagMapsForWriting[i9].get(next.getKey()).number;
                    ExifAttribute exifAttribute2 = (ExifAttribute) next.getValue();
                    Objects.requireNonNull(exifAttribute2);
                    int i11 = IFD_FORMAT_BYTES_PER_FORMAT[exifAttribute2.format] * exifAttribute2.numberOfComponents;
                    byteOrderedDataOutputStream2.writeShort((short) i10);
                    byteOrderedDataOutputStream2.writeShort((short) exifAttribute2.format);
                    byteOrderedDataOutputStream2.writeInt(exifAttribute2.numberOfComponents);
                    if (i11 > 4) {
                        byteOrderedDataOutputStream2.writeInt((int) ((long) size));
                        size += i11;
                    } else {
                        byteOrderedDataOutputStream2.write(exifAttribute2.bytes);
                        if (i11 < 4) {
                            while (i11 < 4) {
                                byteOrderedDataOutputStream2.writeByte(0);
                                i11++;
                            }
                        }
                    }
                }
                if (i9 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream2.writeInt((int) 0);
                } else {
                    byteOrderedDataOutputStream2.writeInt((int) ((long) iArr[4]));
                }
                for (Map.Entry<String, ExifAttribute> value2 : this.mAttributes[i9].entrySet()) {
                    byte[] bArr = ((ExifAttribute) value2.getValue()).bytes;
                    if (bArr.length > 4) {
                        byteOrderedDataOutputStream2.write(bArr, 0, bArr.length);
                    }
                }
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream2.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && i5 % 2 == 1) {
            byteOrderedDataOutputStream2.writeByte(0);
        }
        byteOrderedDataOutputStream2.mByteOrder = ByteOrder.BIG_ENDIAN;
        return i5;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0062  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExifInterface(java.io.FileDescriptor r6) throws java.io.IOException {
        /*
            r5 = this;
            r5.<init>()
            androidx.exifinterface.media.ExifInterface$ExifTag[][] r0 = EXIF_TAGS
            int r1 = r0.length
            java.util.HashMap[] r1 = new java.util.HashMap[r1]
            r5.mAttributes = r1
            java.util.HashSet r1 = new java.util.HashSet
            int r0 = r0.length
            r1.<init>(r0)
            r5.mAttributesOffsets = r1
            java.nio.ByteOrder r0 = java.nio.ByteOrder.BIG_ENDIAN
            r5.mExifByteOrder = r0
            java.lang.String r0 = "fileDescriptor cannot be null"
            java.util.Objects.requireNonNull(r6, r0)
            r0 = 1
            r1 = 0
            int r2 = android.system.OsConstants.SEEK_CUR     // Catch:{ Exception -> 0x0026 }
            r3 = 0
            android.system.Os.lseek(r6, r3, r2)     // Catch:{ Exception -> 0x0026 }
            r2 = r0
            goto L_0x0032
        L_0x0026:
            boolean r2 = DEBUG
            if (r2 == 0) goto L_0x0031
            java.lang.String r2 = "ExifInterface"
            java.lang.String r3 = "The file descriptor for the given input is not seekable"
            android.util.Log.d(r2, r3)
        L_0x0031:
            r2 = r1
        L_0x0032:
            r3 = 0
            if (r2 == 0) goto L_0x0045
            r5.mSeekableFileDescriptor = r6
            java.io.FileDescriptor r6 = android.system.Os.dup(r6)     // Catch:{ Exception -> 0x003c }
            goto L_0x0048
        L_0x003c:
            r5 = move-exception
            java.io.IOException r6 = new java.io.IOException
            java.lang.String r0 = "Failed to duplicate file descriptor"
            r6.<init>(r0, r5)
            throw r6
        L_0x0045:
            r5.mSeekableFileDescriptor = r3
            r0 = r1
        L_0x0048:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x005c }
            r1.<init>(r6)     // Catch:{ all -> 0x005c }
            r5.loadAttributes(r1)     // Catch:{ all -> 0x0059 }
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r1)
            if (r0 == 0) goto L_0x0058
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r6)
        L_0x0058:
            return
        L_0x0059:
            r5 = move-exception
            r3 = r1
            goto L_0x005d
        L_0x005c:
            r5 = move-exception
        L_0x005d:
            androidx.exifinterface.media.ExifInterfaceUtils.closeQuietly(r3)
            if (r0 == 0) goto L_0x0065
            androidx.exifinterface.media.ExifInterfaceUtils.closeFileDescriptor(r6)
        L_0x0065:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.<init>(java.io.FileDescriptor):void");
    }

    public static ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        short readShort = byteOrderedDataInputStream.readShort();
        if (readShort == 18761) {
            if (DEBUG) {
                Log.d("ExifInterface", "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        } else if (readShort == 19789) {
            if (DEBUG) {
                Log.d("ExifInterface", "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid byte order: ");
            m.append(Integer.toHexString(readShort));
            throw new IOException(m.toString());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:52|53|54) */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0134, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x013d, code lost:
        throw new java.lang.UnsupportedOperationException("Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x013e, code lost:
        r1.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0141, code lost:
        throw r12;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x0136 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void getHeifAttributes(final androidx.exifinterface.media.ExifInterface.SeekableByteOrderedDataInputStream r13) throws java.io.IOException {
        /*
            r12 = this;
            java.lang.String r0 = "yes"
            android.media.MediaMetadataRetriever r1 = new android.media.MediaMetadataRetriever
            r1.<init>()
            androidx.exifinterface.media.ExifInterface$1 r2 = new androidx.exifinterface.media.ExifInterface$1     // Catch:{ RuntimeException -> 0x0136 }
            r2.<init>()     // Catch:{ RuntimeException -> 0x0136 }
            r1.setDataSource(r2)     // Catch:{ RuntimeException -> 0x0136 }
            r2 = 33
            java.lang.String r2 = r1.extractMetadata(r2)     // Catch:{ RuntimeException -> 0x0136 }
            r3 = 34
            java.lang.String r3 = r1.extractMetadata(r3)     // Catch:{ RuntimeException -> 0x0136 }
            r4 = 26
            java.lang.String r4 = r1.extractMetadata(r4)     // Catch:{ RuntimeException -> 0x0136 }
            r5 = 17
            java.lang.String r5 = r1.extractMetadata(r5)     // Catch:{ RuntimeException -> 0x0136 }
            boolean r4 = r0.equals(r4)     // Catch:{ RuntimeException -> 0x0136 }
            r6 = 0
            if (r4 == 0) goto L_0x0042
            r0 = 29
            java.lang.String r6 = r1.extractMetadata(r0)     // Catch:{ RuntimeException -> 0x0136 }
            r0 = 30
            java.lang.String r0 = r1.extractMetadata(r0)     // Catch:{ RuntimeException -> 0x0136 }
            r4 = 31
            java.lang.String r4 = r1.extractMetadata(r4)     // Catch:{ RuntimeException -> 0x0136 }
            goto L_0x005d
        L_0x0042:
            boolean r0 = r0.equals(r5)     // Catch:{ RuntimeException -> 0x0136 }
            if (r0 == 0) goto L_0x005b
            r0 = 18
            java.lang.String r6 = r1.extractMetadata(r0)     // Catch:{ RuntimeException -> 0x0136 }
            r0 = 19
            java.lang.String r0 = r1.extractMetadata(r0)     // Catch:{ RuntimeException -> 0x0136 }
            r4 = 24
            java.lang.String r4 = r1.extractMetadata(r4)     // Catch:{ RuntimeException -> 0x0136 }
            goto L_0x005d
        L_0x005b:
            r0 = r6
            r4 = r0
        L_0x005d:
            r5 = 0
            if (r6 == 0) goto L_0x0073
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r7 = r12.mAttributes     // Catch:{ RuntimeException -> 0x0136 }
            r7 = r7[r5]     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r8 = "ImageWidth"
            int r9 = java.lang.Integer.parseInt(r6)     // Catch:{ RuntimeException -> 0x0136 }
            java.nio.ByteOrder r10 = r12.mExifByteOrder     // Catch:{ RuntimeException -> 0x0136 }
            androidx.exifinterface.media.ExifInterface$ExifAttribute r9 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort((int) r9, (java.nio.ByteOrder) r10)     // Catch:{ RuntimeException -> 0x0136 }
            r7.put(r8, r9)     // Catch:{ RuntimeException -> 0x0136 }
        L_0x0073:
            if (r0 == 0) goto L_0x0088
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r7 = r12.mAttributes     // Catch:{ RuntimeException -> 0x0136 }
            r7 = r7[r5]     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r8 = "ImageLength"
            int r9 = java.lang.Integer.parseInt(r0)     // Catch:{ RuntimeException -> 0x0136 }
            java.nio.ByteOrder r10 = r12.mExifByteOrder     // Catch:{ RuntimeException -> 0x0136 }
            androidx.exifinterface.media.ExifInterface$ExifAttribute r9 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort((int) r9, (java.nio.ByteOrder) r10)     // Catch:{ RuntimeException -> 0x0136 }
            r7.put(r8, r9)     // Catch:{ RuntimeException -> 0x0136 }
        L_0x0088:
            r7 = 6
            if (r4 == 0) goto L_0x00b2
            r8 = 1
            int r9 = java.lang.Integer.parseInt(r4)     // Catch:{ RuntimeException -> 0x0136 }
            r10 = 90
            if (r9 == r10) goto L_0x00a2
            r10 = 180(0xb4, float:2.52E-43)
            if (r9 == r10) goto L_0x00a0
            r10 = 270(0x10e, float:3.78E-43)
            if (r9 == r10) goto L_0x009d
            goto L_0x00a3
        L_0x009d:
            r8 = 8
            goto L_0x00a3
        L_0x00a0:
            r8 = 3
            goto L_0x00a3
        L_0x00a2:
            r8 = r7
        L_0x00a3:
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r9 = r12.mAttributes     // Catch:{ RuntimeException -> 0x0136 }
            r9 = r9[r5]     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r10 = "Orientation"
            java.nio.ByteOrder r11 = r12.mExifByteOrder     // Catch:{ RuntimeException -> 0x0136 }
            androidx.exifinterface.media.ExifInterface$ExifAttribute r8 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort((int) r8, (java.nio.ByteOrder) r11)     // Catch:{ RuntimeException -> 0x0136 }
            r9.put(r10, r8)     // Catch:{ RuntimeException -> 0x0136 }
        L_0x00b2:
            if (r2 == 0) goto L_0x0105
            if (r3 == 0) goto L_0x0105
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ RuntimeException -> 0x0136 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ RuntimeException -> 0x0136 }
            if (r3 <= r7) goto L_0x00fd
            long r8 = (long) r2     // Catch:{ RuntimeException -> 0x0136 }
            r13.seek(r8)     // Catch:{ RuntimeException -> 0x0136 }
            byte[] r8 = new byte[r7]     // Catch:{ RuntimeException -> 0x0136 }
            int r9 = r13.read(r8)     // Catch:{ RuntimeException -> 0x0136 }
            if (r9 != r7) goto L_0x00f5
            int r2 = r2 + r7
            int r3 = r3 + -6
            byte[] r7 = IDENTIFIER_EXIF_APP1     // Catch:{ RuntimeException -> 0x0136 }
            boolean r7 = java.util.Arrays.equals(r8, r7)     // Catch:{ RuntimeException -> 0x0136 }
            if (r7 == 0) goto L_0x00ed
            byte[] r7 = new byte[r3]     // Catch:{ RuntimeException -> 0x0136 }
            int r13 = r13.read(r7)     // Catch:{ RuntimeException -> 0x0136 }
            if (r13 != r3) goto L_0x00e5
            r12.mOffsetToExifData = r2     // Catch:{ RuntimeException -> 0x0136 }
            r12.readExifSegment(r7, r5)     // Catch:{ RuntimeException -> 0x0136 }
            goto L_0x0105
        L_0x00e5:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r13 = "Can't read exif"
            r12.<init>(r13)     // Catch:{ RuntimeException -> 0x0136 }
            throw r12     // Catch:{ RuntimeException -> 0x0136 }
        L_0x00ed:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r13 = "Invalid identifier"
            r12.<init>(r13)     // Catch:{ RuntimeException -> 0x0136 }
            throw r12     // Catch:{ RuntimeException -> 0x0136 }
        L_0x00f5:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r13 = "Can't read identifier"
            r12.<init>(r13)     // Catch:{ RuntimeException -> 0x0136 }
            throw r12     // Catch:{ RuntimeException -> 0x0136 }
        L_0x00fd:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r13 = "Invalid exif length"
            r12.<init>(r13)     // Catch:{ RuntimeException -> 0x0136 }
            throw r12     // Catch:{ RuntimeException -> 0x0136 }
        L_0x0105:
            boolean r12 = DEBUG     // Catch:{ RuntimeException -> 0x0136 }
            if (r12 == 0) goto L_0x0130
            java.lang.String r12 = "ExifInterface"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ RuntimeException -> 0x0136 }
            r13.<init>()     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r2 = "Heif meta: "
            r13.append(r2)     // Catch:{ RuntimeException -> 0x0136 }
            r13.append(r6)     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r2 = "x"
            r13.append(r2)     // Catch:{ RuntimeException -> 0x0136 }
            r13.append(r0)     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r0 = ", rotation "
            r13.append(r0)     // Catch:{ RuntimeException -> 0x0136 }
            r13.append(r4)     // Catch:{ RuntimeException -> 0x0136 }
            java.lang.String r13 = r13.toString()     // Catch:{ RuntimeException -> 0x0136 }
            android.util.Log.d(r12, r13)     // Catch:{ RuntimeException -> 0x0136 }
        L_0x0130:
            r1.release()
            return
        L_0x0134:
            r12 = move-exception
            goto L_0x013e
        L_0x0136:
            java.lang.UnsupportedOperationException r12 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0134 }
            java.lang.String r13 = "Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported."
            r12.<init>(r13)     // Catch:{ all -> 0x0134 }
            throw r12     // Catch:{ all -> 0x0134 }
        L_0x013e:
            r1.release()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getHeifAttributes(androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream):void");
    }

    public final void getOrfAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = this.mAttributes[1].get("MakerNote");
        if (exifAttribute != null) {
            SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
            seekableByteOrderedDataInputStream2.mByteOrder = this.mExifByteOrder;
            byte[] bArr = ORF_MAKER_NOTE_HEADER_1;
            byte[] bArr2 = new byte[bArr.length];
            seekableByteOrderedDataInputStream2.readFully(bArr2);
            seekableByteOrderedDataInputStream2.seek(0);
            byte[] bArr3 = ORF_MAKER_NOTE_HEADER_2;
            byte[] bArr4 = new byte[bArr3.length];
            seekableByteOrderedDataInputStream2.readFully(bArr4);
            if (Arrays.equals(bArr2, bArr)) {
                seekableByteOrderedDataInputStream2.seek(8);
            } else if (Arrays.equals(bArr4, bArr3)) {
                seekableByteOrderedDataInputStream2.seek(12);
            }
            readImageFileDirectory(seekableByteOrderedDataInputStream2, 6);
            ExifAttribute exifAttribute2 = this.mAttributes[7].get("PreviewImageStart");
            ExifAttribute exifAttribute3 = this.mAttributes[7].get("PreviewImageLength");
            if (!(exifAttribute2 == null || exifAttribute3 == null)) {
                this.mAttributes[5].put("JPEGInterchangeFormat", exifAttribute2);
                this.mAttributes[5].put("JPEGInterchangeFormatLength", exifAttribute3);
            }
            ExifAttribute exifAttribute4 = this.mAttributes[8].get("AspectFrame");
            if (exifAttribute4 != null) {
                int[] iArr = (int[]) exifAttribute4.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 4) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid aspect frame values. frame=");
                    m.append(Arrays.toString(iArr));
                    Log.w("ExifInterface", m.toString());
                } else if (iArr[2] > iArr[0] && iArr[3] > iArr[1]) {
                    int i = (iArr[2] - iArr[0]) + 1;
                    int i2 = (iArr[3] - iArr[1]) + 1;
                    if (i < i2) {
                        int i3 = i + i2;
                        i2 = i3 - i2;
                        i = i3 - i2;
                    }
                    ExifAttribute createUShort = ExifAttribute.createUShort(i, this.mExifByteOrder);
                    ExifAttribute createUShort2 = ExifAttribute.createUShort(i2, this.mExifByteOrder);
                    this.mAttributes[0].put("ImageWidth", createUShort);
                    this.mAttributes[0].put("ImageLength", createUShort2);
                }
            }
        }
    }

    public final void getRawAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        ExifAttribute exifAttribute;
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 5);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType == 8 && (exifAttribute = this.mAttributes[1].get("MakerNote")) != null) {
            SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
            seekableByteOrderedDataInputStream2.mByteOrder = this.mExifByteOrder;
            seekableByteOrderedDataInputStream2.skipFully(6);
            readImageFileDirectory(seekableByteOrderedDataInputStream2, 9);
            ExifAttribute exifAttribute2 = this.mAttributes[9].get("ColorSpace");
            if (exifAttribute2 != null) {
                this.mAttributes[1].put("ColorSpace", exifAttribute2);
            }
        }
    }

    public final void parseTiffHeaders(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        ByteOrder readByteOrder = readByteOrder(byteOrderedDataInputStream);
        this.mExifByteOrder = readByteOrder;
        byteOrderedDataInputStream.mByteOrder = readByteOrder;
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        int i = this.mMimeType;
        if (i == 7 || i == 10 || readUnsignedShort == 42) {
            int readInt = byteOrderedDataInputStream.readInt();
            if (readInt >= 8) {
                int i2 = readInt - 8;
                if (i2 > 0) {
                    byteOrderedDataInputStream.skipFully(i2);
                    return;
                }
                return;
            }
            throw new IOException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Invalid first Ifd offset: ", readInt));
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid start code: ");
        m.append(Integer.toHexString(readUnsignedShort));
        throw new IOException(m.toString());
    }

    public static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        public ExifTag(String str, int i, int i2) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = -1;
        }

        public ExifTag(String str, int i, int i2, int i3) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = i3;
        }
    }
}
