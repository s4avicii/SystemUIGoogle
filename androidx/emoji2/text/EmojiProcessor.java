package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.KeyEvent;
import androidx.core.graphics.PaintCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import androidx.emoji2.text.flatbuffer.MetadataItem;
import java.util.Objects;

public final class EmojiProcessor {
    public EmojiCompat.GlyphChecker mGlyphChecker;
    public final MetadataRepo mMetadataRepo;
    public final EmojiCompat.SpanFactory mSpanFactory;

    public static final class ProcessorSm {
        public int mCurrentDepth;
        public MetadataRepo.Node mCurrentNode;
        public MetadataRepo.Node mFlushNode;
        public int mLastCodepoint;
        public final MetadataRepo.Node mRootNode;
        public int mState = 1;

        public final void reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
        }

        public final int check(int i) {
            MetadataRepo.Node node;
            boolean z;
            MetadataRepo.Node node2 = this.mCurrentNode;
            Objects.requireNonNull(node2);
            SparseArray<MetadataRepo.Node> sparseArray = node2.mChildren;
            if (sparseArray == null) {
                node = null;
            } else {
                node = sparseArray.get(i);
            }
            int i2 = 3;
            if (this.mState != 2) {
                if (node == null) {
                    reset();
                    i2 = 1;
                    this.mLastCodepoint = i;
                    return i2;
                }
                this.mState = 2;
                this.mCurrentNode = node;
                this.mCurrentDepth = 1;
            } else if (node != null) {
                this.mCurrentNode = node;
                this.mCurrentDepth++;
            } else {
                boolean z2 = false;
                if (i == 65038) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    reset();
                } else {
                    if (i == 65039) {
                        z2 = true;
                    }
                    if (!z2) {
                        MetadataRepo.Node node3 = this.mCurrentNode;
                        Objects.requireNonNull(node3);
                        if (node3.mData != null) {
                            if (this.mCurrentDepth != 1) {
                                this.mFlushNode = this.mCurrentNode;
                                reset();
                            } else if (shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                                this.mFlushNode = this.mCurrentNode;
                                reset();
                            } else {
                                reset();
                            }
                            this.mLastCodepoint = i;
                            return i2;
                        }
                        reset();
                    }
                }
                i2 = 1;
                this.mLastCodepoint = i;
                return i2;
            }
            i2 = 2;
            this.mLastCodepoint = i;
            return i2;
        }

        public final boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            boolean z;
            boolean z2;
            MetadataRepo.Node node = this.mCurrentNode;
            Objects.requireNonNull(node);
            EmojiMetadata emojiMetadata = node.mData;
            Objects.requireNonNull(emojiMetadata);
            MetadataItem metadataItem = emojiMetadata.getMetadataItem();
            int __offset = metadataItem.__offset(6);
            if (__offset == 0 || metadataItem.f23bb.get(__offset + metadataItem.bb_pos) == 0) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return true;
            }
            if (this.mLastCodepoint == 65039) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                return true;
            }
            return false;
        }

        public ProcessorSm(MetadataRepo.Node node) {
            this.mRootNode = node;
            this.mCurrentNode = node;
        }
    }

    public static class DefaultGlyphChecker implements EmojiCompat.GlyphChecker {
        public static final ThreadLocal<StringBuilder> sStringBuilder = new ThreadLocal<>();
        public final TextPaint mTextPaint;

        public DefaultGlyphChecker() {
            TextPaint textPaint = new TextPaint();
            this.mTextPaint = textPaint;
            textPaint.setTextSize(10.0f);
        }
    }

    public EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, DefaultGlyphChecker defaultGlyphChecker) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = defaultGlyphChecker;
    }

    public static boolean delete(Editable editable, KeyEvent keyEvent, boolean z) {
        boolean z2;
        EmojiSpan[] emojiSpanArr;
        if (!KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState())) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (selectionStart == -1 || selectionEnd == -1 || selectionStart != selectionEnd) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 && (emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            int length = emojiSpanArr.length;
            int i = 0;
            while (i < length) {
                EmojiSpan emojiSpan = emojiSpanArr[i];
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                if ((!z || spanStart != selectionStart) && ((z || spanEnd != selectionStart) && (selectionStart <= spanStart || selectionStart >= spanEnd))) {
                    i++;
                } else {
                    editable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasGlyph(CharSequence charSequence, int i, int i2, EmojiMetadata emojiMetadata) {
        int i3;
        Objects.requireNonNull(emojiMetadata);
        if (emojiMetadata.mHasGlyph == 0) {
            EmojiCompat.GlyphChecker glyphChecker = this.mGlyphChecker;
            MetadataItem metadataItem = emojiMetadata.getMetadataItem();
            int __offset = metadataItem.__offset(8);
            if (__offset != 0) {
                metadataItem.f23bb.getShort(__offset + metadataItem.bb_pos);
            }
            DefaultGlyphChecker defaultGlyphChecker = (DefaultGlyphChecker) glyphChecker;
            Objects.requireNonNull(defaultGlyphChecker);
            ThreadLocal<StringBuilder> threadLocal = DefaultGlyphChecker.sStringBuilder;
            if (threadLocal.get() == null) {
                threadLocal.set(new StringBuilder());
            }
            StringBuilder sb = threadLocal.get();
            sb.setLength(0);
            while (i < i2) {
                sb.append(charSequence.charAt(i));
                i++;
            }
            TextPaint textPaint = defaultGlyphChecker.mTextPaint;
            String sb2 = sb.toString();
            int i4 = PaintCompat.$r8$clinit;
            if (textPaint.hasGlyph(sb2)) {
                i3 = 2;
            } else {
                i3 = 1;
            }
            emojiMetadata.mHasGlyph = i3;
        }
        if (emojiMetadata.mHasGlyph == 2) {
            return true;
        }
        return false;
    }
}
