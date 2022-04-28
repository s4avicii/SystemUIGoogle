package androidx.emoji2.text;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class SpannableBuilder extends SpannableStringBuilder {
    public final Class<?> mWatcherClass;
    public final ArrayList mWatchers = new ArrayList();

    public static class WatcherWrapper implements TextWatcher, SpanWatcher {
        public final AtomicInteger mBlockCalls = new AtomicInteger(0);
        public final Object mObject;

        public final void afterTextChanged(Editable editable) {
            ((TextWatcher) this.mObject).afterTextChanged(editable);
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ((TextWatcher) this.mObject).beforeTextChanged(charSequence, i, i2, i3);
        }

        public final void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
            if (this.mBlockCalls.get() <= 0 || !(obj instanceof EmojiSpan)) {
                ((SpanWatcher) this.mObject).onSpanAdded(spannable, obj, i, i2);
            }
        }

        public final void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
            if (this.mBlockCalls.get() <= 0 || !(obj instanceof EmojiSpan)) {
                ((SpanWatcher) this.mObject).onSpanChanged(spannable, obj, i, i2, i3, i4);
            }
        }

        public final void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
            if (this.mBlockCalls.get() <= 0 || !(obj instanceof EmojiSpan)) {
                ((SpanWatcher) this.mObject).onSpanRemoved(spannable, obj, i, i2);
            }
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ((TextWatcher) this.mObject).onTextChanged(charSequence, i, i2, i3);
        }

        public WatcherWrapper(Object obj) {
            this.mObject = obj;
        }
    }

    public SpannableBuilder(Class<?> cls, CharSequence charSequence) {
        super(charSequence);
        Objects.requireNonNull(cls, "watcherClass cannot be null");
        this.mWatcherClass = cls;
    }

    public final Editable append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    public final void blockWatchers() {
        for (int i = 0; i < this.mWatchers.size(); i++) {
            WatcherWrapper watcherWrapper = (WatcherWrapper) this.mWatchers.get(i);
            Objects.requireNonNull(watcherWrapper);
            watcherWrapper.mBlockCalls.incrementAndGet();
        }
    }

    @SuppressLint({"UnknownNullness"})
    public final Editable delete(int i, int i2) {
        super.delete(i, i2);
        return this;
    }

    public final WatcherWrapper getWatcherFor(Object obj) {
        for (int i = 0; i < this.mWatchers.size(); i++) {
            WatcherWrapper watcherWrapper = (WatcherWrapper) this.mWatchers.get(i);
            if (watcherWrapper.mObject == obj) {
                return watcherWrapper;
            }
        }
        return null;
    }

    @SuppressLint({"UnknownNullness"})
    public final Editable insert(int i, CharSequence charSequence) {
        super.insert(i, charSequence);
        return this;
    }

    public final boolean isWatcher(Object obj) {
        boolean z;
        if (obj != null) {
            if (this.mWatcherClass == obj.getClass()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final void unblockwatchers() {
        for (int i = 0; i < this.mWatchers.size(); i++) {
            WatcherWrapper watcherWrapper = (WatcherWrapper) this.mWatchers.get(i);
            Objects.requireNonNull(watcherWrapper);
            watcherWrapper.mBlockCalls.decrementAndGet();
        }
    }

    /* renamed from: append  reason: collision with other method in class */
    public final SpannableStringBuilder m145append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    @SuppressLint({"UnknownNullness"})
    /* renamed from: delete  reason: collision with other method in class */
    public final SpannableStringBuilder m150delete(int i, int i2) {
        super.delete(i, i2);
        return this;
    }

    @SuppressLint({"UnknownNullness"})
    public final <T> T[] getSpans(int i, int i2, Class<T> cls) {
        boolean z;
        if (this.mWatcherClass == cls) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return super.getSpans(i, i2, cls);
        }
        WatcherWrapper[] watcherWrapperArr = (WatcherWrapper[]) super.getSpans(i, i2, WatcherWrapper.class);
        T[] tArr = (Object[]) Array.newInstance(cls, watcherWrapperArr.length);
        for (int i3 = 0; i3 < watcherWrapperArr.length; i3++) {
            tArr[i3] = watcherWrapperArr[i3].mObject;
        }
        return tArr;
    }

    @SuppressLint({"UnknownNullness"})
    /* renamed from: insert  reason: collision with other method in class */
    public final SpannableStringBuilder m151insert(int i, CharSequence charSequence) {
        super.insert(i, charSequence);
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        if (r0 != false) goto L_0x000b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int nextSpanTransition(int r2, int r3, java.lang.Class<androidx.emoji2.text.SpannableBuilder.WatcherWrapper> r4) {
        /*
            r1 = this;
            if (r4 == 0) goto L_0x000b
            java.lang.Class<?> r0 = r1.mWatcherClass
            if (r0 != r4) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            if (r0 == 0) goto L_0x000d
        L_0x000b:
            java.lang.Class<androidx.emoji2.text.SpannableBuilder$WatcherWrapper> r4 = androidx.emoji2.text.SpannableBuilder.WatcherWrapper.class
        L_0x000d:
            int r1 = super.nextSpanTransition(r2, r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.SpannableBuilder.nextSpanTransition(int, int, java.lang.Class):int");
    }

    @SuppressLint({"UnknownNullness"})
    public final CharSequence subSequence(int i, int i2) {
        return new SpannableBuilder(this.mWatcherClass, this, i, i2);
    }

    /* renamed from: append  reason: collision with other method in class */
    public final Appendable m148append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence) throws IOException {
        super.append(charSequence);
        return this;
    }

    public final void endBatchEdit() {
        unblockwatchers();
        for (int i = 0; i < this.mWatchers.size(); i++) {
            ((WatcherWrapper) this.mWatchers.get(i)).onTextChanged(this, 0, length(), length());
        }
    }

    public final int getSpanEnd(Object obj) {
        WatcherWrapper watcherFor;
        if (isWatcher(obj) && (watcherFor = getWatcherFor(obj)) != null) {
            obj = watcherFor;
        }
        return super.getSpanEnd(obj);
    }

    public final int getSpanFlags(Object obj) {
        WatcherWrapper watcherFor;
        if (isWatcher(obj) && (watcherFor = getWatcherFor(obj)) != null) {
            obj = watcherFor;
        }
        return super.getSpanFlags(obj);
    }

    public final int getSpanStart(Object obj) {
        WatcherWrapper watcherFor;
        if (isWatcher(obj) && (watcherFor = getWatcherFor(obj)) != null) {
            obj = watcherFor;
        }
        return super.getSpanStart(obj);
    }

    @SuppressLint({"UnknownNullness"})
    public final Editable insert(int i, CharSequence charSequence, int i2, int i3) {
        super.insert(i, charSequence, i2, i3);
        return this;
    }

    public final void removeSpan(Object obj) {
        WatcherWrapper watcherWrapper;
        if (isWatcher(obj)) {
            watcherWrapper = getWatcherFor(obj);
            if (watcherWrapper != null) {
                obj = watcherWrapper;
            }
        } else {
            watcherWrapper = null;
        }
        super.removeSpan(obj);
        if (watcherWrapper != null) {
            this.mWatchers.remove(watcherWrapper);
        }
    }

    @SuppressLint({"UnknownNullness"})
    public final SpannableStringBuilder replace(int i, int i2, CharSequence charSequence) {
        blockWatchers();
        super.replace(i, i2, charSequence);
        unblockwatchers();
        return this;
    }

    public final void setSpan(Object obj, int i, int i2, int i3) {
        if (isWatcher(obj)) {
            WatcherWrapper watcherWrapper = new WatcherWrapper(obj);
            this.mWatchers.add(watcherWrapper);
            obj = watcherWrapper;
        }
        super.setSpan(obj, i, i2, i3);
    }

    public final Editable append(char c) {
        super.append(c);
        return this;
    }

    @SuppressLint({"UnknownNullness"})
    /* renamed from: insert  reason: collision with other method in class */
    public final SpannableStringBuilder m152insert(int i, CharSequence charSequence, int i2, int i3) {
        super.insert(i, charSequence, i2, i3);
        return this;
    }

    public SpannableBuilder(Class<?> cls, CharSequence charSequence, int i, int i2) {
        super(charSequence, i, i2);
        Objects.requireNonNull(cls, "watcherClass cannot be null");
        this.mWatcherClass = cls;
    }

    /* renamed from: append  reason: collision with other method in class */
    public final SpannableStringBuilder m144append(char c) {
        super.append(c);
        return this;
    }

    /* renamed from: append  reason: collision with other method in class */
    public final Appendable m147append(char c) throws IOException {
        super.append(c);
        return this;
    }

    @SuppressLint({"UnknownNullness"})
    public final SpannableStringBuilder replace(int i, int i2, CharSequence charSequence, int i3, int i4) {
        blockWatchers();
        super.replace(i, i2, charSequence, i3, i4);
        unblockwatchers();
        return this;
    }

    public final Editable append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        return this;
    }

    /* renamed from: append  reason: collision with other method in class */
    public final SpannableStringBuilder m146append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        return this;
    }

    /* renamed from: append  reason: collision with other method in class */
    public final Appendable m149append(@SuppressLint({"UnknownNullness"}) CharSequence charSequence, int i, int i2) throws IOException {
        super.append(charSequence, i, i2);
        return this;
    }

    @SuppressLint({"UnknownNullness"})
    public final SpannableStringBuilder append(CharSequence charSequence, Object obj, int i) {
        super.append(charSequence, obj, i);
        return this;
    }
}
