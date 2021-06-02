package dev.socketmods.socketmodels.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;

@SuppressWarnings("UnstableApiUsage")
public class Workers {

    public static final Executor DAEMON_SINGLE
        = MoreExecutors.getExitingExecutorService(newSingleThreadPool(), 5, TimeUnit.SECONDS);

    private static ThreadPoolExecutor newSingleThreadPool() {
        return newFixedThreadPool(1);
    }

    private static ThreadPoolExecutor newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

}
