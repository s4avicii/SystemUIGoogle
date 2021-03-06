package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.collection.SimpleArrayMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppCompatViewInflater {
    public static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    public static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap = new SimpleArrayMap<>();
    public static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    public static final int[] sOnClickAttrs = {16843375};
    public final Object[] mConstructorArgs = new Object[2];

    public static class DeclaredOnClickListener implements View.OnClickListener {
        public final View mHostView;
        public final String mMethodName;
        public Context mResolvedContext;
        public Method mResolvedMethod;

        public final void onClick(View view) {
            String str;
            Method method;
            if (this.mResolvedMethod == null) {
                Context context = this.mHostView.getContext();
                while (context != null) {
                    try {
                        if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class})) != null) {
                            this.mResolvedMethod = method;
                            this.mResolvedContext = context;
                        }
                    } catch (NoSuchMethodException unused) {
                    }
                    if (context instanceof ContextWrapper) {
                        context = ((ContextWrapper) context).getBaseContext();
                    } else {
                        context = null;
                    }
                }
                int id = this.mHostView.getId();
                if (id == -1) {
                    str = "";
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" with id '");
                    m.append(this.mHostView.getContext().getResources().getResourceEntryName(id));
                    m.append("'");
                    str = m.toString();
                }
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Could not find method ");
                m2.append(this.mMethodName);
                m2.append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
                m2.append(this.mHostView.getClass());
                m2.append(str);
                throw new IllegalStateException(m2.toString());
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{view});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        public DeclaredOnClickListener(View view, String str) {
            this.mHostView = view;
            this.mMethodName = str;
        }
    }

    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    public AppCompatButton createButton(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    public AppCompatTextView createTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    public final View createViewByPrefix(Context context, String str, String str2) throws ClassNotFoundException, InflateException {
        String str3;
        SimpleArrayMap<String, Constructor<? extends View>> simpleArrayMap = sConstructorMap;
        Constructor<? extends U> orDefault = simpleArrayMap.getOrDefault(str, null);
        if (orDefault == null) {
            if (str2 != null) {
                try {
                    str3 = str2 + str;
                } catch (Exception unused) {
                    return null;
                }
            } else {
                str3 = str;
            }
            orDefault = Class.forName(str3, false, context.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
            simpleArrayMap.put(str, orDefault);
        }
        orDefault.setAccessible(true);
        return (View) orDefault.newInstance(this.mConstructorArgs);
    }

    public final void verifyNotNull(TextView textView, String str) {
        if (textView == null) {
            throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + str + ">, but returned null");
        }
    }
}
