package com.android.settingslib.suggestions;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.service.settings.suggestions.Suggestion;
import java.util.List;

@Deprecated
public final class SuggestionLoader extends AsyncTaskLoader<List<Suggestion>> {
    public List<Suggestion> mResult;

    public SuggestionLoader() {
        super((Context) null);
    }

    /* renamed from: onStartLoading$com$android$settingslib$utils$AsyncLoader */
    public final void onStartLoading() {
        List<Suggestion> list = this.mResult;
        if (list != null) {
            deliverResult(list);
        }
        if (takeContentChanged() || this.mResult == null) {
            forceLoad();
        }
    }

    /* renamed from: deliverResult$com$android$settingslib$utils$AsyncLoader */
    public final void deliverResult(List<Suggestion> list) {
        if (!isReset()) {
            List<Suggestion> list2 = this.mResult;
            this.mResult = list;
            if (isStarted()) {
                super.deliverResult(list);
            }
            if (list2 != null && list2 != this.mResult) {
                List list3 = list2;
            }
        } else if (list != null) {
            List list4 = list;
        }
    }

    /* renamed from: onCanceled$com$android$settingslib$utils$AsyncLoader */
    public final void onCanceled(List<Suggestion> list) {
        super.onCanceled(list);
        if (list != null) {
            List list2 = list;
        }
    }

    /* renamed from: onReset$com$android$settingslib$utils$AsyncLoader */
    public final void onReset() {
        super.onReset();
        cancelLoad();
        List<Suggestion> list = this.mResult;
        if (list != null) {
            List list2 = list;
        }
        this.mResult = null;
    }

    public final Object loadInBackground() {
        throw null;
    }

    public final void onStopLoading() {
        cancelLoad();
    }
}
