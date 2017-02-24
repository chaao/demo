package tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadUtils {

	public static ScheduledExecutorService ExecutorService(String name) {
        return Executors.newSingleThreadScheduledExecutor(newThreadFactory(name));
    }

    public static ExecutorService newCachedThreadPool(String name) {
        return Executors.newCachedThreadPool(newThreadFactory(name));
    }

    public static ExecutorService newFixedThreadPool(int size, String name) {
        return Executors.newFixedThreadPool(size, newThreadFactory(name));
    }

    public static ScheduledExecutorService newScheduledThreadPool(int size, String name) {
        return Executors.newScheduledThreadPool(size, newThreadFactory(name));
    }

    public static ExecutorService newSingleThreadExecutor(String name) {
        return Executors.newSingleThreadExecutor(newThreadFactory(name));
    }

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name + "-%d").setDaemon(true).build();
    }


}
