package androidx.emoji2.text;

import android.os.Handler;
import android.os.Looper;
import androidx.collection.ArraySet;
import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.EmojiProcessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class EmojiCompat {
    public static final Object INSTANCE_LOCK = new Object();
    public static volatile EmojiCompat sInstance;
    public final EmojiProcessor.DefaultGlyphChecker mGlyphChecker;
    public final CompatInternal19 mHelper;
    public final ArraySet mInitCallbacks;
    public final ReentrantReadWriteLock mInitLock;
    public volatile int mLoadState = 3;
    public final Handler mMainHandler;
    public final int mMetadataLoadStrategy;
    public final MetadataRepoLoader mMetadataLoader;

    public interface GlyphChecker {
    }

    public static abstract class InitCallback {
        public void onFailed() {
        }

        public void onInitialized() {
        }
    }

    public static class ListenerDispatcher implements Runnable {
        public final ArrayList mInitCallbacks;
        public final int mLoadState;
        public final Throwable mThrowable;

        public final void run() {
            int size = this.mInitCallbacks.size();
            int i = 0;
            if (this.mLoadState != 1) {
                while (i < size) {
                    ((InitCallback) this.mInitCallbacks.get(i)).onFailed();
                    i++;
                }
                return;
            }
            while (i < size) {
                ((InitCallback) this.mInitCallbacks.get(i)).onInitialized();
                i++;
            }
        }

        public ListenerDispatcher(List list, int i, Throwable th) {
            Objects.requireNonNull(list, "initCallbacks cannot be null");
            this.mInitCallbacks = new ArrayList(list);
            this.mLoadState = i;
            this.mThrowable = th;
        }
    }

    public interface MetadataRepoLoader {
        void load(MetadataRepoLoaderCallback metadataRepoLoaderCallback);
    }

    public static abstract class MetadataRepoLoaderCallback {
        public abstract void onFailed(Throwable th);

        public abstract void onLoaded(MetadataRepo metadataRepo);
    }

    public static class SpanFactory {
    }

    public static class CompatInternal {
        public final EmojiCompat mEmojiCompat;

        public CompatInternal(EmojiCompat emojiCompat) {
            this.mEmojiCompat = emojiCompat;
        }
    }

    public static abstract class Config {
        public EmojiProcessor.DefaultGlyphChecker mGlyphChecker = new EmojiProcessor.DefaultGlyphChecker();
        public int mMetadataLoadStrategy = 0;
        public final MetadataRepoLoader mMetadataLoader;

        public Config(MetadataRepoLoader metadataRepoLoader) {
            this.mMetadataLoader = metadataRepoLoader;
        }
    }

    public static EmojiCompat get() {
        EmojiCompat emojiCompat;
        boolean z;
        synchronized (INSTANCE_LOCK) {
            emojiCompat = sInstance;
            if (emojiCompat != null) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                throw new IllegalStateException("EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
            }
        }
        return emojiCompat;
    }

    public final int getLoadState() {
        this.mInitLock.readLock().lock();
        try {
            return this.mLoadState;
        } finally {
            this.mInitLock.readLock().unlock();
        }
    }

    public final void load() {
        boolean z;
        boolean z2 = true;
        if (this.mMetadataLoadStrategy == 1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (getLoadState() != 1) {
                z2 = false;
            }
            if (!z2) {
                this.mInitLock.writeLock().lock();
                try {
                    if (this.mLoadState != 0) {
                        this.mLoadState = 0;
                        this.mInitLock.writeLock().unlock();
                        CompatInternal19 compatInternal19 = this.mHelper;
                        Objects.requireNonNull(compatInternal19);
                        try {
                            compatInternal19.mEmojiCompat.mMetadataLoader.load(new MetadataRepoLoaderCallback() {
                                public final void onFailed(Throwable th) {
                                    CompatInternal19.this.mEmojiCompat.onMetadataLoadFailed(th);
                                }

                                public final void onLoaded(MetadataRepo metadataRepo) {
                                    CompatInternal19 compatInternal19 = CompatInternal19.this;
                                    Objects.requireNonNull(compatInternal19);
                                    compatInternal19.mMetadataRepo = metadataRepo;
                                    compatInternal19.mProcessor = new EmojiProcessor(compatInternal19.mMetadataRepo, new SpanFactory(), compatInternal19.mEmojiCompat.mGlyphChecker);
                                    compatInternal19.mEmojiCompat.onMetadataLoadSuccess();
                                }
                            });
                        } catch (Throwable th) {
                            compatInternal19.mEmojiCompat.onMetadataLoadFailed(th);
                        }
                    }
                } finally {
                    this.mInitLock.writeLock().unlock();
                }
            }
        } else {
            throw new IllegalStateException("Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
        }
    }

    /* JADX INFO: finally extract failed */
    public final void onMetadataLoadFailed(Throwable th) {
        ArrayList arrayList = new ArrayList();
        this.mInitLock.writeLock().lock();
        try {
            this.mLoadState = 2;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            this.mInitLock.writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, th));
        } catch (Throwable th2) {
            this.mInitLock.writeLock().unlock();
            throw th2;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void onMetadataLoadSuccess() {
        ArrayList arrayList = new ArrayList();
        this.mInitLock.writeLock().lock();
        try {
            this.mLoadState = 1;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            this.mInitLock.writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, (Throwable) null));
        } catch (Throwable th) {
            this.mInitLock.writeLock().unlock();
            throw th;
        }
    }

    public final void registerInitCallback(InitCallback initCallback) {
        Objects.requireNonNull(initCallback, "initCallback cannot be null");
        this.mInitLock.writeLock().lock();
        try {
            if (this.mLoadState != 1) {
                if (this.mLoadState != 2) {
                    this.mInitCallbacks.add(initCallback);
                }
            }
            this.mMainHandler.post(new ListenerDispatcher(Arrays.asList(new InitCallback[]{initCallback}), this.mLoadState, (Throwable) null));
        } finally {
            this.mInitLock.writeLock().unlock();
        }
    }

    public EmojiCompat(EmojiCompatInitializer.BackgroundDefaultConfig backgroundDefaultConfig) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mInitLock = reentrantReadWriteLock;
        MetadataRepoLoader metadataRepoLoader = backgroundDefaultConfig.mMetadataLoader;
        this.mMetadataLoader = metadataRepoLoader;
        int i = backgroundDefaultConfig.mMetadataLoadStrategy;
        this.mMetadataLoadStrategy = i;
        this.mGlyphChecker = backgroundDefaultConfig.mGlyphChecker;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mInitCallbacks = new ArraySet(0);
        CompatInternal19 compatInternal19 = new CompatInternal19(this);
        this.mHelper = compatInternal19;
        reentrantReadWriteLock.writeLock().lock();
        if (i == 0) {
            try {
                this.mLoadState = 0;
            } catch (Throwable th) {
                this.mInitLock.writeLock().unlock();
                throw th;
            }
        }
        reentrantReadWriteLock.writeLock().unlock();
        if (getLoadState() == 0) {
            try {
                metadataRepoLoader.load(new MetadataRepoLoaderCallback() {
                    public final void onFailed(Throwable th) {
                        CompatInternal19.this.mEmojiCompat.onMetadataLoadFailed(th);
                    }

                    public final void onLoaded(MetadataRepo metadataRepo) {
                        CompatInternal19 compatInternal19 = CompatInternal19.this;
                        Objects.requireNonNull(compatInternal19);
                        compatInternal19.mMetadataRepo = metadataRepo;
                        compatInternal19.mProcessor = new EmojiProcessor(compatInternal19.mMetadataRepo, new SpanFactory(), compatInternal19.mEmojiCompat.mGlyphChecker);
                        compatInternal19.mEmojiCompat.onMetadataLoadSuccess();
                    }
                });
            } catch (Throwable th2) {
                compatInternal19.mEmojiCompat.onMetadataLoadFailed(th2);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: java.lang.CharSequence} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.text.Spannable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.text.SpannableString} */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:126:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0091 A[Catch:{ all -> 0x0181 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ad A[Catch:{ all -> 0x0181 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x017a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.CharSequence process(java.lang.CharSequence r13, int r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.getLoadState()
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L_0x000a
            r0 = r2
            goto L_0x000b
        L_0x000a:
            r0 = r1
        L_0x000b:
            if (r0 == 0) goto L_0x019c
            if (r14 < 0) goto L_0x0193
            if (r15 < 0) goto L_0x018b
            if (r14 > r15) goto L_0x0015
            r0 = r2
            goto L_0x0016
        L_0x0015:
            r0 = r1
        L_0x0016:
            java.lang.String r3 = "start should be <= than end"
            androidx.mediarouter.R$bool.checkArgument(r0, r3)
            r0 = 0
            if (r13 != 0) goto L_0x0020
            return r0
        L_0x0020:
            int r3 = r13.length()
            if (r14 > r3) goto L_0x0028
            r3 = r2
            goto L_0x0029
        L_0x0028:
            r3 = r1
        L_0x0029:
            java.lang.String r4 = "start should be < than charSequence length"
            androidx.mediarouter.R$bool.checkArgument(r3, r4)
            int r3 = r13.length()
            if (r15 > r3) goto L_0x0037
            r3 = r2
            goto L_0x0038
        L_0x0037:
            r3 = r1
        L_0x0038:
            java.lang.String r4 = "end should be < than charSequence length"
            androidx.mediarouter.R$bool.checkArgument(r3, r4)
            int r3 = r13.length()
            if (r3 == 0) goto L_0x018a
            if (r14 != r15) goto L_0x0047
            goto L_0x018a
        L_0x0047:
            androidx.emoji2.text.EmojiCompat$CompatInternal19 r12 = r12.mHelper
            java.util.Objects.requireNonNull(r12)
            androidx.emoji2.text.EmojiProcessor r12 = r12.mProcessor
            java.util.Objects.requireNonNull(r12)
            boolean r3 = r13 instanceof androidx.emoji2.text.SpannableBuilder
            if (r3 == 0) goto L_0x005b
            r4 = r13
            androidx.emoji2.text.SpannableBuilder r4 = (androidx.emoji2.text.SpannableBuilder) r4
            r4.blockWatchers()
        L_0x005b:
            if (r3 != 0) goto L_0x007b
            boolean r4 = r13 instanceof android.text.Spannable     // Catch:{ all -> 0x0181 }
            if (r4 == 0) goto L_0x0062
            goto L_0x007b
        L_0x0062:
            boolean r4 = r13 instanceof android.text.Spanned     // Catch:{ all -> 0x0181 }
            if (r4 == 0) goto L_0x007e
            r4 = r13
            android.text.Spanned r4 = (android.text.Spanned) r4     // Catch:{ all -> 0x0181 }
            int r5 = r14 + -1
            int r6 = r15 + 1
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r7 = androidx.emoji2.text.EmojiSpan.class
            int r4 = r4.nextSpanTransition(r5, r6, r7)     // Catch:{ all -> 0x0181 }
            if (r4 > r15) goto L_0x007e
            android.text.SpannableString r0 = new android.text.SpannableString     // Catch:{ all -> 0x0181 }
            r0.<init>(r13)     // Catch:{ all -> 0x0181 }
            goto L_0x007e
        L_0x007b:
            r0 = r13
            android.text.Spannable r0 = (android.text.Spannable) r0     // Catch:{ all -> 0x0181 }
        L_0x007e:
            if (r0 == 0) goto L_0x00ab
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r4 = androidx.emoji2.text.EmojiSpan.class
            java.lang.Object[] r4 = r0.getSpans(r14, r15, r4)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiSpan[] r4 = (androidx.emoji2.text.EmojiSpan[]) r4     // Catch:{ all -> 0x0181 }
            if (r4 == 0) goto L_0x00ab
            int r5 = r4.length     // Catch:{ all -> 0x0181 }
            if (r5 <= 0) goto L_0x00ab
            int r5 = r4.length     // Catch:{ all -> 0x0181 }
            r6 = r1
        L_0x008f:
            if (r6 >= r5) goto L_0x00ab
            r7 = r4[r6]     // Catch:{ all -> 0x0181 }
            int r8 = r0.getSpanStart(r7)     // Catch:{ all -> 0x0181 }
            int r9 = r0.getSpanEnd(r7)     // Catch:{ all -> 0x0181 }
            if (r8 == r15) goto L_0x00a0
            r0.removeSpan(r7)     // Catch:{ all -> 0x0181 }
        L_0x00a0:
            int r14 = java.lang.Math.min(r8, r14)     // Catch:{ all -> 0x0181 }
            int r15 = java.lang.Math.max(r9, r15)     // Catch:{ all -> 0x0181 }
            int r6 = r6 + 1
            goto L_0x008f
        L_0x00ab:
            if (r14 == r15) goto L_0x0178
            int r4 = r13.length()     // Catch:{ all -> 0x0181 }
            if (r14 < r4) goto L_0x00b5
            goto L_0x0178
        L_0x00b5:
            r4 = 2147483647(0x7fffffff, float:NaN)
            androidx.emoji2.text.EmojiProcessor$ProcessorSm r5 = new androidx.emoji2.text.EmojiProcessor$ProcessorSm     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.MetadataRepo r6 = r12.mMetadataRepo     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r6)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.MetadataRepo$Node r6 = r6.mRootNode     // Catch:{ all -> 0x0181 }
            r5.<init>(r6)     // Catch:{ all -> 0x0181 }
            int r6 = java.lang.Character.codePointAt(r13, r14)     // Catch:{ all -> 0x0181 }
            r7 = r1
        L_0x00c9:
            r8 = r6
            r6 = r14
        L_0x00cb:
            r9 = 2
            r10 = 33
            if (r14 >= r15) goto L_0x0127
            if (r7 >= r4) goto L_0x0127
            int r11 = r5.check(r8)     // Catch:{ all -> 0x0181 }
            if (r11 == r2) goto L_0x0116
            if (r11 == r9) goto L_0x010a
            r9 = 3
            if (r11 == r9) goto L_0x00de
            goto L_0x00cb
        L_0x00de:
            androidx.emoji2.text.MetadataRepo$Node r9 = r5.mFlushNode     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r9)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiMetadata r9 = r9.mData     // Catch:{ all -> 0x0181 }
            boolean r9 = r12.hasGlyph(r13, r6, r14, r9)     // Catch:{ all -> 0x0181 }
            if (r9 != 0) goto L_0x0108
            if (r0 != 0) goto L_0x00f2
            android.text.SpannableString r0 = new android.text.SpannableString     // Catch:{ all -> 0x0181 }
            r0.<init>(r13)     // Catch:{ all -> 0x0181 }
        L_0x00f2:
            androidx.emoji2.text.MetadataRepo$Node r9 = r5.mFlushNode     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r9)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiMetadata r9 = r9.mData     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiCompat$SpanFactory r11 = r12.mSpanFactory     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.TypefaceEmojiSpan r11 = new androidx.emoji2.text.TypefaceEmojiSpan     // Catch:{ all -> 0x0181 }
            r11.<init>(r9)     // Catch:{ all -> 0x0181 }
            r0.setSpan(r11, r6, r14, r10)     // Catch:{ all -> 0x0181 }
            int r7 = r7 + 1
        L_0x0108:
            r6 = r8
            goto L_0x00c9
        L_0x010a:
            int r9 = java.lang.Character.charCount(r8)     // Catch:{ all -> 0x0181 }
            int r14 = r14 + r9
            if (r14 >= r15) goto L_0x00cb
            int r8 = java.lang.Character.codePointAt(r13, r14)     // Catch:{ all -> 0x0181 }
            goto L_0x00cb
        L_0x0116:
            int r14 = java.lang.Character.codePointAt(r13, r6)     // Catch:{ all -> 0x0181 }
            int r14 = java.lang.Character.charCount(r14)     // Catch:{ all -> 0x0181 }
            int r6 = r6 + r14
            if (r6 >= r15) goto L_0x0125
            int r8 = java.lang.Character.codePointAt(r13, r6)     // Catch:{ all -> 0x0181 }
        L_0x0125:
            r14 = r6
            goto L_0x00cb
        L_0x0127:
            int r15 = r5.mState     // Catch:{ all -> 0x0181 }
            if (r15 != r9) goto L_0x013f
            androidx.emoji2.text.MetadataRepo$Node r15 = r5.mCurrentNode     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r15)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiMetadata r15 = r15.mData     // Catch:{ all -> 0x0181 }
            if (r15 == 0) goto L_0x013f
            int r15 = r5.mCurrentDepth     // Catch:{ all -> 0x0181 }
            if (r15 > r2) goto L_0x013e
            boolean r15 = r5.shouldUseEmojiPresentationStyleForSingleCodepoint()     // Catch:{ all -> 0x0181 }
            if (r15 == 0) goto L_0x013f
        L_0x013e:
            r1 = r2
        L_0x013f:
            if (r1 == 0) goto L_0x016c
            if (r7 >= r4) goto L_0x016c
            androidx.emoji2.text.MetadataRepo$Node r15 = r5.mCurrentNode     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r15)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiMetadata r15 = r15.mData     // Catch:{ all -> 0x0181 }
            boolean r15 = r12.hasGlyph(r13, r6, r14, r15)     // Catch:{ all -> 0x0181 }
            if (r15 != 0) goto L_0x016c
            if (r0 != 0) goto L_0x0158
            android.text.SpannableString r15 = new android.text.SpannableString     // Catch:{ all -> 0x0181 }
            r15.<init>(r13)     // Catch:{ all -> 0x0181 }
            r0 = r15
        L_0x0158:
            androidx.emoji2.text.MetadataRepo$Node r15 = r5.mCurrentNode     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r15)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiMetadata r15 = r15.mData     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.EmojiCompat$SpanFactory r12 = r12.mSpanFactory     // Catch:{ all -> 0x0181 }
            java.util.Objects.requireNonNull(r12)     // Catch:{ all -> 0x0181 }
            androidx.emoji2.text.TypefaceEmojiSpan r12 = new androidx.emoji2.text.TypefaceEmojiSpan     // Catch:{ all -> 0x0181 }
            r12.<init>(r15)     // Catch:{ all -> 0x0181 }
            r0.setSpan(r12, r6, r14, r10)     // Catch:{ all -> 0x0181 }
        L_0x016c:
            if (r0 != 0) goto L_0x016f
            r0 = r13
        L_0x016f:
            if (r3 == 0) goto L_0x0176
            androidx.emoji2.text.SpannableBuilder r13 = (androidx.emoji2.text.SpannableBuilder) r13
            r13.endBatchEdit()
        L_0x0176:
            r13 = r0
            goto L_0x0180
        L_0x0178:
            if (r3 == 0) goto L_0x0180
            r12 = r13
            androidx.emoji2.text.SpannableBuilder r12 = (androidx.emoji2.text.SpannableBuilder) r12
            r12.endBatchEdit()
        L_0x0180:
            return r13
        L_0x0181:
            r12 = move-exception
            if (r3 == 0) goto L_0x0189
            androidx.emoji2.text.SpannableBuilder r13 = (androidx.emoji2.text.SpannableBuilder) r13
            r13.endBatchEdit()
        L_0x0189:
            throw r12
        L_0x018a:
            return r13
        L_0x018b:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.String r13 = "end cannot be negative"
            r12.<init>(r13)
            throw r12
        L_0x0193:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.String r13 = "start cannot be negative"
            r12.<init>(r13)
            throw r12
        L_0x019c:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "Not initialized yet"
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiCompat.process(java.lang.CharSequence, int, int):java.lang.CharSequence");
    }

    public static final class CompatInternal19 extends CompatInternal {
        public volatile MetadataRepo mMetadataRepo;
        public volatile EmojiProcessor mProcessor;

        public CompatInternal19(EmojiCompat emojiCompat) {
            super(emojiCompat);
        }
    }
}
