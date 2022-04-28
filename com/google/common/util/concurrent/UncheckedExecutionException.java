package com.google.common.util.concurrent;

public class UncheckedExecutionException extends RuntimeException {
    private static final long serialVersionUID = 0;

    public UncheckedExecutionException() {
    }

    public UncheckedExecutionException(String str) {
        super(str);
    }

    public UncheckedExecutionException(Throwable th) {
        super(th);
    }
}
