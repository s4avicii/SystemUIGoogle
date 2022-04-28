package com.google.common.util.concurrent;

class MoreExecutors$Application {
    public void addShutdownHook(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
