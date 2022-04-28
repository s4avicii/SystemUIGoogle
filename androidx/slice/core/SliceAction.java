package androidx.slice.core;

public interface SliceAction {
    int getPriority();

    boolean isToggle();
}
