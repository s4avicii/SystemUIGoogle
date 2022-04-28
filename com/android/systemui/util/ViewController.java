package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

public abstract class ViewController<T extends View> {
    public boolean mInited;
    public C17031 mOnAttachStateListener = new View.OnAttachStateChangeListener() {
        public final void onViewAttachedToWindow(View view) {
            ViewController.this.onViewAttached();
        }

        public final void onViewDetachedFromWindow(View view) {
            ViewController.this.onViewDetached();
        }
    };
    public final T mView;

    public void onInit() {
    }

    public abstract void onViewAttached();

    public abstract void onViewDetached();

    public final Context getContext() {
        return this.mView.getContext();
    }

    public final Resources getResources() {
        return this.mView.getResources();
    }

    public final void init() {
        if (!this.mInited) {
            onInit();
            boolean z = true;
            this.mInited = true;
            T t = this.mView;
            if (t == null || !t.isAttachedToWindow()) {
                z = false;
            }
            if (z) {
                this.mOnAttachStateListener.onViewAttachedToWindow(this.mView);
            }
            C17031 r0 = this.mOnAttachStateListener;
            T t2 = this.mView;
            if (t2 != null) {
                t2.addOnAttachStateChangeListener(r0);
            }
        }
    }

    public ViewController(T t) {
        this.mView = t;
    }
}
